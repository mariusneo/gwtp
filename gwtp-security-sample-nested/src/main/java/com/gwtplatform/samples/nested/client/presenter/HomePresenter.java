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

package com.gwtplatform.samples.nested.client.presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.samples.nested.client.CurrentUser;
import com.gwtplatform.samples.nested.client.IsLoggedInGatekeeper;
import com.gwtplatform.samples.nested.client.NameTokens;
import com.gwtplatform.samples.nested.client.view.IPermissionEnabledView;
import com.gwtplatform.samples.nested.shared.action.FindAllPersonsAction;
import com.gwtplatform.samples.nested.shared.action.FindAllPersonsResult;
import com.gwtplatform.samples.nested.shared.action.FindPersonsAction;
import com.gwtplatform.samples.nested.shared.action.FindPersonsResult;

/**
 * @author Christian Goudreau
 */
public class HomePresenter extends
		AbstractPresenter<HomePresenter.MyView, HomePresenter.MyProxy> {
	/**
	 * {@link HomePresenter}'s proxy.
	 */
	@ProxyCodeSplit
	@NameToken(NameTokens.homePage)
	@UseGatekeeper(IsLoggedInGatekeeper.class)
	public interface MyProxy extends ProxyPlace<HomePresenter> {
	}

	/**
	 * {@link HomePresenter}'s view.
	 */
	public interface MyView extends IPermissionEnabledView {
		HasClickHandlers getSearchButton();

		HasValue<String> getLastNameTextBox();

		HasClickHandlers getSearchAllButton();
		
		void setSearchResult(String result);
	}

	private final DispatchAsync dispatcher;

	@Inject
	public HomePresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy, final DispatchAsync dispatcher, final CurrentUser currentUser) {
		super(eventBus, view, proxy, currentUser);
		this.dispatcher = dispatcher;
	}

	@Override
	protected void onBind() {
		super.onBind();
		initHandlers();
	}

	private void initHandlers() {
		getView().getSearchAllButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				dispatcher.execute(new FindAllPersonsAction(),
						new AsyncCallback<FindAllPersonsResult>() {

							@Override
							public void onFailure(Throwable caught) {
								String message = "findall persons onFailure() - "
										+ caught.getLocalizedMessage();
								Log.debug(message);
								getView().setSearchResult(message);
							}

							@Override
							public void onSuccess(FindAllPersonsResult result) {
								String message = "find all persons action result  - persons obtained "
										+ result.getPersons().size();
								Log.debug(message);
								getView().setSearchResult(message);
							}
						});
			}
		});
		
		getView().getSearchButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				FindPersonsAction action = new FindPersonsAction();
				action.setLastName((String)getView().getLastNameTextBox().getValue());
				dispatcher.execute(action,
						new AsyncCallback<FindPersonsResult>() {

							@Override
							public void onFailure(Throwable caught) {
								String message = "onFailure() - "
										+ caught.getLocalizedMessage();
								Log.debug(message);
								getView().setSearchResult(message);
							}

							@Override
							public void onSuccess(FindPersonsResult result) {
								String message = "findpersons action result - persons obtained "
										+ result.getPersons().size();
								Log.debug(message);
								getView().setSearchResult(message);
							}
						});
			}
		});

	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, MainPagePresenter.TYPE_SetMainContent,
				this);
	}
}