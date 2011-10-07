
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
import com.gwtplatform.samples.nested.server.dao.UserDao;
import com.gwtplatform.samples.nested.server.domain.User;
import com.gwtplatform.samples.nested.shared.action.LoginAction;
import com.gwtplatform.samples.nested.shared.action.LoginResult;
import com.gwtplatform.samples.nested.shared.exception.LoginException;

// don't forget to update bindHandler() in ServerModule

public class LoginHandler implements ActionHandler<LoginAction, LoginResult> {
	public static final String CURRENT_USER_ATTRIBUTE_NAME = "current.user";
	
  private final Provider<HttpServletRequest> requestProvider;
  // private final ServletContext servletContext;

  @Inject
  LoginHandler(final ServletContext servletContext,
      final Provider<HttpServletRequest> requestProvider) {
    this.requestProvider = requestProvider;
  }

  @Override
  public LoginResult execute(final LoginAction action,
      final ExecutionContext context) throws ActionException {

    LoginResult result = null;
    UserDao userDao = new UserDao();

    Log.debug("LoginHandler - login: " + action.getLogin() + ", password: " + action.getPassword());

    try {
      User user = userDao.retrieveUser(action.getLogin());

      if (user != null && isValidLogin(action, user)) {
        Log.debug(action.getLogin() + " has logged in");

        HttpSession session = requestProvider.get().getSession();
        session.setAttribute(CURRENT_USER_ATTRIBUTE_NAME, user);

        result = new LoginResult(session.getId(), user.getLogin(), user.getRights());
      }
      else {
        // GWTP only includes the exception type and it's message?
        // Looks like it needs only a small change on the DispatchServiceImpl class, 
        // on the execute() method:
        // Instead of calling logger.warning(message), use logger.log(level, message, throwable). 
        throw new LoginException("Invalid User name or Password.");
      }
    }
    catch (Exception e) {
      throw new ActionException(e);
    }

    return result;
  }

  private Boolean isValidLogin(LoginAction action, User user) {
    return action.getPassword().equals(user.getPassword());
  }

  @Override
  public Class<LoginAction> getActionType() {
    return LoginAction.class;
  }

  @Override
  public void undo(LoginAction action, LoginResult result,
      ExecutionContext context) throws ActionException {
  }
}

