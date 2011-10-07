/**
 * Copyright 2011 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gwtplatform.samples.nested.client;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.inject.Inject;

/**
 * This is a basic class that holds the privileges of the user currently logged
 * in.
 * 
 * @author Philippe Beaudoin
 */
public class CurrentUser implements HasHandlers {

	private boolean loggedIn = false;
	
	private String username;

	private List<Integer> rights;

	private final EventBus eventBus;

	@Inject
	public CurrentUser(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public List<Integer> getRights() {
		return rights;
	}

	public void setRights(List<Integer> rights) {
		this.rights = rights;
	}	
	
	public boolean hasRight(int rightId){
		if (rights != null && rights.contains(rightId)){
			return true;
		}
		return false;
	}
	
	public void reset(){
		this.loggedIn = false;
		this.username = null;
		this.rights = null;
	}
	
	@Override
	public void fireEvent(GwtEvent<?> event) {
		eventBus.fireEvent(event);
	}
}
