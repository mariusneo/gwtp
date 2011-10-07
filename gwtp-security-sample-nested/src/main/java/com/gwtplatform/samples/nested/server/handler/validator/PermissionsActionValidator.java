package com.gwtplatform.samples.nested.server.handler.validator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.allen_sauer.gwt.log.client.Log;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.gwtplatform.dispatch.shared.Action;
import com.gwtplatform.dispatch.shared.Result;
import com.gwtplatform.samples.nested.server.domain.User;
import com.gwtplatform.samples.nested.server.handler.LoginHandler;

public class PermissionsActionValidator extends LoggedInActionValidator {
	@Inject
	PermissionsActionValidator(
			final Provider<HttpServletRequest> requestProvider) {
		super(requestProvider);
	}

	@Override
	@Singleton
	public boolean isValid(Action<? extends Result> action) {
		boolean result = super.isValid(action);

		if (result) {
			Log.debug("Permissions Action Validator");

			HttpSession session = requestProvider.get().getSession();

			User currentUser = (User)session.getAttribute(LoginHandler.CURRENT_USER_ATTRIBUTE_NAME);
			
			List<Integer> rights = currentUser.getRights();

			PermissionsNeeded permissionsNeededAnnotation = action.getClass().getAnnotation(PermissionsNeeded.class);
			int[] permissionsNeeded = permissionsNeededAnnotation.value();
			
			for (int permissionNeeded : permissionsNeeded){
				if (!(rights!= null && rights.contains(permissionNeeded))){
					result = false;
				}
			}
		}

		return result;
	}
}
