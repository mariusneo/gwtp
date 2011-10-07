package com.gwtplatform.samples.nested.server.handler;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.allen_sauer.gwt.log.client.Log;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.gwtplatform.samples.nested.server.domain.User;
import com.gwtplatform.samples.nested.shared.action.LogoutAction;
import com.gwtplatform.samples.nested.shared.action.LogoutResult;
import com.gwtplatform.samples.nested.shared.exception.LogoutException;

//don't forget to update bindHandler() in ServerModule

public class LogoutHandler implements ActionHandler<LogoutAction, LogoutResult> {

	private final Provider<HttpServletRequest> requestProvider;

	// private final ServletContext servletContext;

	@Inject
	LogoutHandler(final ServletContext servletContext,
			final Provider<HttpServletRequest> requestProvider) {
		this.requestProvider = requestProvider;
	}

	@Override
	public LogoutResult execute(final LogoutAction action,
			final ExecutionContext context) throws ActionException {

		LogoutResult result = null;

		try {
			HttpSession session = requestProvider.get().getSession();
			User currentUser = (User)session.getAttribute(LoginHandler.CURRENT_USER_ATTRIBUTE_NAME);

			if (currentUser != null) {
				Log.debug("LogoutHandler - logout: " + currentUser.getLogin());

				result = new LogoutResult(true);
				
				session.invalidate();
			} else {
				// GWTP only includes the exception type and it's message?
				// Looks like it needs only a small change on the
				// DispatchServiceImpl class,
				// on the execute() method:
				// Instead of calling logger.warning(message), use
				// logger.log(level, message, throwable).
				throw new LogoutException("User is not logged in.");
			}
		} catch (Exception e) {
			throw new ActionException(e);
		}

		return result;
	}

	@Override
	public Class<LogoutAction> getActionType() {
		return LogoutAction.class;
	}

	@Override
	public void undo(LogoutAction action, LogoutResult result,
			ExecutionContext context) throws ActionException {
	}
}
