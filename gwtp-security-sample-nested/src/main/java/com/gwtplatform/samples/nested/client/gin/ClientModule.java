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

package com.gwtplatform.samples.nested.client.gin;

import com.google.inject.Singleton;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.gwtplatform.samples.nested.client.CurrentUser;
import com.gwtplatform.samples.nested.client.GwtpnestedsamplePlaceManager;
import com.gwtplatform.samples.nested.client.IsAdminGatekeeper;
import com.gwtplatform.samples.nested.client.IsLoggedInGatekeeper;
import com.gwtplatform.samples.nested.client.NameTokens;
import com.gwtplatform.samples.nested.client.presenter.AboutUsPresenter;
import com.gwtplatform.samples.nested.client.presenter.AdministrationPresenter;
import com.gwtplatform.samples.nested.client.presenter.ContactPresenter;
import com.gwtplatform.samples.nested.client.presenter.ContactPresenterBase;
import com.gwtplatform.samples.nested.client.presenter.HomePresenter;
import com.gwtplatform.samples.nested.client.presenter.InfoPopupPresenterWidget;
import com.gwtplatform.samples.nested.client.presenter.MainPagePresenter;
import com.gwtplatform.samples.nested.client.presenter.SignInPagePresenter;
import com.gwtplatform.samples.nested.client.view.AboutUsView;
import com.gwtplatform.samples.nested.client.view.AdministrationView;
import com.gwtplatform.samples.nested.client.view.ContactView;
import com.gwtplatform.samples.nested.client.view.HomeView;
import com.gwtplatform.samples.nested.client.view.InfoPopupView;
import com.gwtplatform.samples.nested.client.view.MainPageView;
import com.gwtplatform.samples.nested.client.view.SignInPageView;
import com.gwtplatform.samples.nested.shared.Constants;


/**
 * @author Christian Goudreau
 */
public class ClientModule extends AbstractPresenterModule {
  @Override
  protected void configure() {
    // Default implementation of standard resources
    install(new DefaultModule(GwtpnestedsamplePlaceManager.class));

    bindConstant().annotatedWith(SecurityCookie.class).to(
			Constants.GWTP_SECURITY_SAMPLE_SECURITY_COOKIE_NAME);
    
    bind(CurrentUser.class).in(Singleton.class);
    bind(IsLoggedInGatekeeper.class).in(Singleton.class);
    bind(IsAdminGatekeeper.class).in(Singleton.class);
    
    // Constants
    bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.signInPage);

    // Presenters
    bindPresenter(SignInPagePresenter.class, SignInPagePresenter.MyView.class,
			SignInPageView.class, SignInPagePresenter.MyProxy.class);
    bindPresenter(MainPagePresenter.class, MainPagePresenter.MyView.class,
        MainPageView.class, MainPagePresenter.MyProxy.class);
    bindPresenter(HomePresenter.class, HomePresenter.MyView.class,
        HomeView.class, HomePresenter.MyProxy.class);
    bindPresenter(AboutUsPresenter.class, AboutUsPresenter.MyView.class,
        AboutUsView.class, AboutUsPresenter.MyProxy.class);
    bindPresenter(AdministrationPresenter.class, AdministrationPresenter.MyView.class,
            AdministrationView.class, AdministrationPresenter.MyProxy.class);
    
    bindPresenter(ContactPresenter.class, ContactPresenterBase.MyView.class,
        ContactView.class, ContactPresenter.MyProxy.class);
    
    bindSingletonPresenterWidget(InfoPopupPresenterWidget.class,
            InfoPopupPresenterWidget.MyView.class, InfoPopupView.class);
  }
}