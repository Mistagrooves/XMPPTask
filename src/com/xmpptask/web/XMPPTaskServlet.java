package com.xmpptask.web;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.*;

import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.MessageBuilder;
import com.google.appengine.api.xmpp.MessageType;
import com.google.appengine.api.xmpp.Presence;
import com.google.appengine.api.xmpp.SendResponse;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;

@SuppressWarnings("serial")
//simple echo client for now
public class XMPPTaskServlet extends HttpServlet {
	
	private XMPPService xmppService;
	
	@Override
	public void init(){
		this.xmppService = XMPPServiceFactory.getXMPPService();
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
		JID fromId = message.getFromJid();
		Presence presence = xmppService.getPresence(fromId);
		String presenceString = presence.isAvailable() ? "" : "not";
		SendResponse response = xmppService.sendMessage(
        new MessageBuilder()
        	.withBody(message.getBody() + " (you are " + presenceString + "available)")
        	.withRecipientJids(fromId)
        	.build());

	    for (Map.Entry<JID, SendResponse.Status> entry :
	        response.getStatusMap().entrySet()) {
	      resp.getWriter().println(entry.getKey() + "," + entry.getValue() + "<br>");
	    }

	    resp.getWriter().println("processed");
	}
}
