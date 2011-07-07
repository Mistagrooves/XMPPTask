package com.xmpptask.models;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * Utility class for accessing the persistence helper in a singleton manner
 * @author Eric
 *
 */
public final class PMF {
	private static final PersistenceManagerFactory pmfInstance = 
			JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	private PMF (){}
	
	public static PersistenceManagerFactory get(){
		return pmfInstance;
	}
}
