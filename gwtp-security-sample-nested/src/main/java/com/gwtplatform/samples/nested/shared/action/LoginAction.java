package com.gwtplatform.samples.nested.shared.action;

import com.gwtplatform.dispatch.shared.Action;

public class LoginAction implements Action<LoginResult> { 

  java.lang.String login;
  java.lang.String password;

  public LoginAction(java.lang.String login, java.lang.String password) {
    this.login = login;
    this.password = password;
  }

  protected LoginAction() {
    // Possibly for serialization.
  }

  public java.lang.String getLogin() {
    return login;
  }

  public java.lang.String getPassword() {
    return password;
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
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    LoginAction other = (LoginAction) obj;
    if (login == null) {
      if (other.login != null)
        return false;
    } else if (!login.equals(other.login))
      return false;
    if (password == null) {
      if (other.password != null)
        return false;
    } else if (!password.equals(other.password))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (login == null ? 1 : login.hashCode());
    hashCode = (hashCode * 37) + (password == null ? 1 : password.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "LoginAction["
                 + login
                 + ","
                 + password
    + "]";
  }
}
