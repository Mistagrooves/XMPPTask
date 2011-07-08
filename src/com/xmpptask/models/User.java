package com.xmpptask.models;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.jdo.annotations.*;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class User {
	private static final Logger log = Logger.getLogger(User.class.getName());
	private static final int STRETCHES = 3;
	private static final String PEPPER = "66eb5960-a5ad-11e0-8264-0800200c9a66";
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	@Unique
	private String email;
	
	@Persistent
	private String firstname;
	
	@Persistent
	private String lastname;
	
	@Persistent
	private String salt;
	
	@Persistent
	private String password;
	
	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="id asc"))
	private List<Task> tasks;
	
	public User(String email, String firstname, String lastname){
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[20];
		random.nextBytes(bytes);
		try {
			this.salt = new String(bytes, "utf8");
		} catch (UnsupportedEncodingException e) {
			log.severe("UTF8 is unavailable");
		}
	}
	
	private String doHashing(String password){
		try{
			String hash = User.PEPPER;
			Provider[] providers = Security.getProviders();
			Set<String> algos = Security.getAlgorithms("MessageDigest");
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			
			for(int i = 0; i < User.STRETCHES; i++){
				String round = salt + hash + password + User.PEPPER;
				hash = new String(digest.digest(round.getBytes("utf8")), "utf8");
			}
			
			return hash;
		}catch(UnsupportedEncodingException e){
			log.severe("UTF8 is unavailable");
		} catch (NoSuchAlgorithmException e) {
			log.severe("SHA512 is unavailable");
		}

		return null;
	}
	
	public void setPassword(String password){
		this.password = doHashing(password);
	}
	
	public boolean authenticate(String password){
		return this.password.equals(doHashing(password));
	}
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public List<Task> getTasks() {
		if(tasks == null)
			return new ArrayList<Task>();
		
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
}
