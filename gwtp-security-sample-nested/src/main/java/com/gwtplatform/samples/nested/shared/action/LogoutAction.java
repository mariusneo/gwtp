package com.gwtplatform.samples.nested.shared.action;

import com.gwtplatform.dispatch.shared.Action;

public class LogoutAction implements Action<LogoutResult> { 


	  public LogoutAction() {
	    // Possibly for serialization.
	  }

	  @Override
	  public String getServiceName() {
	    return "dispatch/";
	  }

	  @Override
	  public boolean isSecured() {
	    return false;
	  }


	  
	  @Override
	  public String toString() {
	    return "LogoutAction[]";
	  }
	}