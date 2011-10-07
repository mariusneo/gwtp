package com.gwtplatform.samples.nested.shared.action;

import com.gwtplatform.dispatch.shared.Result;

public class LogoutResult implements Result {

	boolean successful;


	public LogoutResult(boolean successful) {
		this.successful = successful;
	}

	protected LogoutResult() {
		// Possibly for serialization.
	}


	public boolean isSuccessful() {
		return successful;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (successful ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogoutResult other = (LogoutResult) obj;
		if (successful != other.successful)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LogoutResult[" + isSuccessful() + "]";
	}
}

