package com.gwtplatform.samples.nested.shared.action;

import com.gwtplatform.dispatch.shared.ActionImpl;
import com.gwtplatform.samples.nested.server.handler.validator.PermissionsNeeded;
import com.gwtplatform.samples.nested.shared.Permissions;

@PermissionsNeeded({ Permissions.SEARCH_PERSON_WITH_GIVEN_NAME })
public class FindPersonsAction extends ActionImpl<FindPersonsResult> {
	private String lastName;
	
	public FindPersonsAction() {
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
