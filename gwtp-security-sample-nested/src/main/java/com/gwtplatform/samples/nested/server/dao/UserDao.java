package com.gwtplatform.samples.nested.server.dao;

import java.util.LinkedList;
import java.util.List;

import com.gwtplatform.samples.nested.server.domain.User;
import com.gwtplatform.samples.nested.shared.Permissions;


/**
 * Mock implementation of the User DAO object.
 * @author mga
 *
 */
public class UserDao {
	private static final String DEFAULT_PASSWORD = "N0More$ecrets";
	
	private static final String USER_BASIC = "Basic";
	
	private static final String USER_ADVANCED = "Advanced";
	
	private static final String ADMINISTRATOR_USER = "Administrator";
	
	public User retrieveUser(String login) {
	    User user = new User();
	    user.setLogin(login);
	    user.setPassword(DEFAULT_PASSWORD);
	    List<Integer> rights = new LinkedList<Integer>();
	    user.setRights(rights);
	    
	    if (login.equals(ADMINISTRATOR_USER)){
	    	rights.add(Permissions.ACCESS_ADMINISTRATION_PAGE);
	    	rights.add(Permissions.SEARCH_ALL_PERSONS);
	    	rights.add(Permissions.SEARCH_PERSON_WITH_GIVEN_NAME);
	    }else if (login.equals(USER_BASIC)){
	    	rights.add(Permissions.SEARCH_PERSON_WITH_GIVEN_NAME);
	    }else if (login.equals(USER_ADVANCED)){
	    	rights.add(Permissions.SEARCH_ALL_PERSONS);
	    	rights.add(Permissions.SEARCH_PERSON_WITH_GIVEN_NAME);
	    }
	    
	    return user;
	  }
}
