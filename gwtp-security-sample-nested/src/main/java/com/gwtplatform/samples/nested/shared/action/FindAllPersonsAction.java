package com.gwtplatform.samples.nested.shared.action;

import com.gwtplatform.dispatch.shared.ActionImpl;
import com.gwtplatform.samples.nested.server.handler.validator.PermissionsNeeded;
import com.gwtplatform.samples.nested.shared.Permissions;

@PermissionsNeeded(Permissions.SEARCH_ALL_PERSONS)
public class FindAllPersonsAction extends ActionImpl<FindAllPersonsResult> {
	/**
	 * Constructor used only for serialization purposes.
	 */
	public FindAllPersonsAction() {
	}
}
