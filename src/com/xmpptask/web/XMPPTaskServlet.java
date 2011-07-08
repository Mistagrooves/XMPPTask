package com.xmpptask.web;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.servlet.http.*;

import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.MessageBuilder;
import com.google.appengine.api.xmpp.MessageType;
import com.google.appengine.api.xmpp.Presence;
import com.google.appengine.api.xmpp.SendResponse;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;
import com.xmpptask.commands.Command;
import com.xmpptask.commands.CommandResult;
import com.xmpptask.models.PMF;
import com.xmpptask.models.User;
import com.xmpptask.parser.ParseException;
import com.xmpptask.parser.XMPPParser;

@SuppressWarnings("serial")
public class XMPPTaskServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(XMPPTaskServlet.class.getName());

	private XMPPService xmppService;
	
	@Override
	public void init(){
		this.xmppService = XMPPServiceFactory.getXMPPService();
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		log.info(String.format("Received GET Message: From: %s\n To: %s\n Body: %s\n", req.getParameter("from"),
				req.getParameter("to"), req.getParameter("body")));
		
		String body = req.getParameter("body");
		
		if(body == null || body.equals("")){
			body = "NO BODY";
		}
		
		Message message = new MessageBuilder()
        .withMessageType(MessageType.CHAT)
        .withFromJid(new JID(req.getParameter("from")))
        .withRecipientJids(new JID(req.getParameter("to")))
        .withBody(req.getParameter("body"))
        .build();
		
		processMessage(message, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		processMessage(xmppService.parseMessage(req), resp);
	}
	
	public void processMessage(Message message, HttpServletResponse resp) throws IOException{
		JID[] recipient = message.getRecipientJids();
		log.info(String.format("Received POST Message: From: %s\n To: %s\n Body: %s\n Stanza: %s\n", message.getFromJid().toString(),
					recipient.length > 0 ? recipient[0].toString() : "None", message.getBody(), message.getStanza() ));
		
		JID fromId = message.getFromJid();
		
		String[] userIdParts = fromId.getId().split("/");
		String userid = userIdParts[0];
		
		//TODO look up user in memcached
		
		//look up user in datastore
		PersistenceManager pm = PMF.get().getPersistenceManager();
		User u = null;
		Query query = pm.newQuery(User.class);
		query.setFilter("email == emailParam");
		query.declareParameters("String emailParam");
		boolean auth = false;
		CommandResult result = null;
		try{
			List<User> results = (List<User>) query.execute(userid);
			if(results.isEmpty()){
				result = new CommandResult("Error.unknownuser");
			}else{
				u = results.get(0);
			}
		
			//process message
			Command cmd = null;
			if(u != null){
				try{
					XMPPParser parser = new XMPPParser(message.getBody());
					cmd = parser.parse();
					cmd.withUser(u);
					//execute command
					result = cmd.execute(pm);
				}catch(ParseException e){
					result = new CommandResult("Error.message", e.getMessage());
				}
			}
			
		}finally{
			pm.close();
		}
		//construct response
		StringBuilder sb = new StringBuilder();
		sb.append("<body>");
		sb.append(result.getPlainText());
		sb.append("</body>");
		sb.append("<html xmlns='http://jabber.org/protocol/xhtml-im'>");
		sb.append("<body xmlns='http://www.w3.org/1999/xhtml'>");
		sb.append(result.getHTML());
		sb.append("</body>");
		sb.append("</html>");
		
		//return result
		Presence presence = xmppService.getPresence(fromId);
		
		Message msg = new MessageBuilder()
			.withBody(sb.toString())
			.withRecipientJids(fromId)
			.asXml(true)
			.withMessageType(MessageType.CHAT)
			.build();
		
		SendResponse response = xmppService.sendMessage(msg);
	
	}
}
