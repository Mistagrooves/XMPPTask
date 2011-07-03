package com.xmpptask.web;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.servlet.http.*;

import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.MessageBuilder;
import com.google.appengine.api.xmpp.MessageType;
import com.google.appengine.api.xmpp.Presence;
import com.google.appengine.api.xmpp.SendResponse;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;
import com.xmpptask.models.PMF;

@SuppressWarnings("serial")
//simple echo client for now
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
		try{
			
		}finally{
			pm.close();
		}
		
		//process message
		
		//execute command
		String result = "<body></body>"
			+ "<html xmlns='http://jabber.org/protocol/xhtml-im'>"
				+ "<body xmlns='http://www.w3.org/1999/xhtml'>"
					+ "<b>BOLD TEXT</b>"
				+ "</body>"
			+ "</html>";
		//return result
		
		Presence presence = xmppService.getPresence(fromId);
		
		Message msg = new MessageBuilder()
			.withBody(result)
			.withRecipientJids(fromId)
			.asXml(true)
			.withMessageType(MessageType.CHAT)
			.build();
		
		SendResponse response = xmppService.sendMessage(msg);
				
		//Presence presence = xmppService.getPresence(fromId);
		//String presenceString = presence.isAvailable() ? "" : "not";
		//SendResponse response = xmppService.sendMessage(
        /*new MessageBuilder()
        	.withBody(message.getBody() + " (you are " + presenceString + "available)")
        	.withRecipientJids(fromId)
        	.build());*/

		
	    for (Map.Entry<JID, SendResponse.Status> entry :
	        response.getStatusMap().entrySet()) {
	      resp.getWriter().println(entry.getKey() + "," + entry.getValue() + "<br>");
	    }

	    resp.getWriter().println("processed");
		
	}
}
