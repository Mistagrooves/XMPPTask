package com.xmpptask.web;

import java.io.IOException;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.google.appengine.api.xmpp.XMPPServiceFactory;
import com.xmpptask.models.PMF;
import com.xmpptask.models.User;

public class UserServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(UserServlet.class.getName());
	
	@Override
	public void init(){
		
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		String xmppemail = req.getParameter("email");
		String password = req.getParameter("password");
		String passwordconfirm = req.getParameter("passwordConfirm");
		
		if(StringUtils.isBlank(xmppemail) || StringUtils.isBlank(password) || StringUtils.isBlank(passwordconfirm) || !password.equals(passwordconfirm)){
			req.getSession().setAttribute("message", "Invalid info");
			resp.sendRedirect("index.html");
		}
		
		User u = new User(xmppemail, "", "");
		u.setPassword(password);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			pm.makePersistent(u);
		}finally{
			pm.close();
		}
		resp.sendRedirect("/pages/user/Success.jsp");

	}
}
