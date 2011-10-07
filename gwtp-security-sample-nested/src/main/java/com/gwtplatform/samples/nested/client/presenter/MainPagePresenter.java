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
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.LockInteractionEvent;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;
import com.gwtplatform.samples.nested.client.CurrentUser;
import com.gwtplatform.samples.nested.client.NameTokens;
import com.gwtplatform.samples.nested.client.view.IPermissionEnabledView;
import com.gwtplatform.samples.nested.shared.action.LogoutAction;
import com.gwtplatform.samples.nested.shared.action.LogoutResult;
import com.gwtplatform.samples.nested.shared.exception.LogoutException;

/**
 * This is the top-level presenter of the hierarchy. Other presenters reveal
 * themselves within this presenter.
 * <p />
 * The goal of this sample is to show how to use nested presenters. These can be
 * useful to decouple multiple presenters that need to be displayed on the
 * screen simultaneously.
 * 
 * @author Christian Goudreau
 */
public class MainPagePresenter extends
		AbstractPresenter<MainPagePresenter.MyView, MainPagePresenter.MyProxy> {
	/**
	 * {@link MainPagePresenter}'s proxy.
	 */
	@ProxyStandard
	public interface MyProxy extends Proxy<MainPagePresenter> {
	}

	/**
	 * {@link MainPagePresenter}'s view.
	 */
	public interface MyView extends IPermissionEnabledView {
		void showLoading(boolean visibile);

		HasClickHandlers getLogoutButton();
	}

	/**
	 * Use this in leaf presenters, inside their {@link #revealInParent} method.
	 */
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_SetMainContent = new Type<RevealContentHandler<?>>();

	private final DispatchAsync dispatcher;

	private final PlaceManager placeManager;

	private final CurrentUser currentUser;

	@Inject
	public MainPagePresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy, final DispatchAsync dispatcher,
			final PlaceManager placeManager, final CurrentUser currentUser) {
		super(eventBus, view, proxy, currentUser);
		this.dispatcher = dispatcher;
		this.placeManager = placeManager;
		this.currentUser = currentUser;
	}

	@Override
	protected void onBind() {
		super.onBind();
		initHandlers();
	}

	private void initHandlers() {
		getView().getLogoutButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				dispatcher.execute(new LogoutAction(),
						new AsyncCallback<LogoutResult>() {

							@Override
							public void onFailure(Throwable caught) {
								String message = "onFailure() - "
										+ caught.getLocalizedMessage();

								if (caught instanceof LogoutException) {
									message = "onFailure() - "
											+ "Invalid User name or Password.";
								}

								Log.debug(message);
							}

							@Override
							public void onSuccess(LogoutResult result) {
								Log.debug("onSuccess() "
										+ result.isSuccessful());

								currentUser.reset();

								placeManager.revealPlace(new PlaceRequest(
										NameTokens.signInPage));

							}
						});
			}
		});

	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}

	/**
	 * We display a short lock message whenever navigation is in progress.
	 * 
	 * @param event
	 *            The {@link LockInteractionEvent}.
	 */
	@ProxyEvent
	public void onLockInteraction(LockInteractionEvent event) {
		getView().showLoading(event.shouldLock());
	}
}
