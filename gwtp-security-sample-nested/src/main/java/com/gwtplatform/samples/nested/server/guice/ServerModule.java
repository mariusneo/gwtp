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

package com.gwtplatform.samples.nested.server.guice;

import com.google.inject.Singleton;
import com.gwtplatform.dispatch.server.guice.HandlerModule;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.samples.nested.server.handler.FindAllPersonsHandler;
import com.gwtplatform.samples.nested.server.handler.FindPersonsHandler;
import com.gwtplatform.samples.nested.server.handler.LoginHandler;
import com.gwtplatform.samples.nested.server.handler.LogoutHandler;
import com.gwtplatform.samples.nested.server.handler.validator.LoggedInActionValidator;
import com.gwtplatform.samples.nested.server.handler.validator.PermissionsActionValidator;
import com.gwtplatform.samples.nested.shared.Constants;
import com.gwtplatform.samples.nested.shared.action.FindAllPersonsAction;
import com.gwtplatform.samples.nested.shared.action.FindPersonsAction;
import com.gwtplatform.samples.nested.shared.action.LoginAction;
import com.gwtplatform.samples.nested.shared.action.LogoutAction;

/**
 * Module which binds the handlers and configurations.
 * 
 * @author Philippe Beaudoin
 */
public class ServerModule extends HandlerModule {
	@Override
	protected void configureHandlers() {
		bindConstant().annotatedWith(SecurityCookie.class).to(
				Constants.GWTP_SECURITY_SAMPLE_SECURITY_COOKIE_NAME);

		bind(PermissionsActionValidator.class).in(Singleton.class);
		
		bindHandler(LoginAction.class, LoginHandler.class);
		bindHandler(LogoutAction.class, LogoutHandler.class,
				LoggedInActionValidator.class);
		
		bindHandler(FindAllPersonsAction.class, FindAllPersonsHandler.class,
				PermissionsActionValidator.class);
		bindHandler(FindPersonsAction.class, FindPersonsHandler.class,
				PermissionsActionValidator.class);

	}
}
