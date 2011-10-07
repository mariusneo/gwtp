package com.gwtplatform.samples.nested.shared.action;

import java.util.List;

import com.gwtplatform.dispatch.shared.Result;

public class LoginResult implements Result {

	String sessionKey;

	String username;

	List<Integer> rights;

	public LoginResult(String sessionKey, String username, List<Integer> rights) {
		this.sessionKey = sessionKey;
		this.username = username;
		this.rights = rights;
	}

	protected LoginResult() {
		// Possibly for serialization.
	}

	public java.lang.String getSessionKey() {
		return sessionKey;
	}

	public String getUsername() {
		return username;
	}

	public List<Integer> getRights() {
		return rights;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginResult other = (LoginResult) obj;
		if (sessionKey == null) {
			if (other.sessionKey != null)
				return false;
		} else if (!sessionKey.equals(other.sessionKey))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int hashCode = 23;
		hashCode = (hashCode * 37)
				+ (sessionKey == null ? 1 : sessionKey.hashCode());
		return hashCode;
	}

	@Override
	public String toString() {
		return "LoginResult[" + sessionKey + "]";
	}
}
