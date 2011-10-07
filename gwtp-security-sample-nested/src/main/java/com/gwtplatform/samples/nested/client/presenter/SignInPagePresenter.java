/**
 * (C) Copyright 2010, 2011 upTick Pty Ltd
 *
 * Licensed under the terms of the GNU General Public License version 3
 * as published by the Free Software Foundation. You may obtain a copy of the
 * License at: http://www.gnu.org/copyleft/gpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.gwtplatform.samples.nested.client.presenter;


import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;
import com.gwtplatform.samples.nested.client.CurrentUser;
import com.gwtplatform.samples.nested.client.NameTokens;
import com.gwtplatform.samples.nested.client.view.handler.SignInPageUiHandlers;
import com.gwtplatform.samples.nested.shared.action.LoginAction;
import com.gwtplatform.samples.nested.shared.action.LoginResult;
import com.gwtplatform.samples.nested.shared.exception.LoginException;

public class SignInPagePresenter extends
    Presenter<SignInPagePresenter.MyView, SignInPagePresenter.MyProxy> implements
        SignInPageUiHandlers {

  private final DispatchAsync dispatcher;
  private final PlaceManager placeManager;
  private final CurrentUser currentUser;
  
  private final InfoPopupPresenterWidget loginFailedInfoPopupWidget;

  // don't forget to update SerendipityGinjector & ClientModule
  @ProxyStandard
  @NameToken(NameTokens.signInPage)
  @NoGatekeeper
  public interface MyProxy extends Proxy<SignInPagePresenter>, Place {
  }

  public interface MyView extends View, HasUiHandlers<SignInPageUiHandlers> {
    String getUserName();
    String getPassword();
    void resetAndFocus();
  }

  @Inject
  public SignInPagePresenter(final EventBus eventBus, MyView view, MyProxy proxy,
      final DispatchAsync dispatcher, final PlaceManager placeManager, final CurrentUser currentUser,
      final InfoPopupPresenterWidget loginFailedInfoPopupWidget) {
    super(eventBus, view, proxy);

    getView().setUiHandlers(this);

    this.dispatcher = dispatcher;
    this.placeManager = placeManager;
    this.currentUser = currentUser;
    this.loginFailedInfoPopupWidget = loginFailedInfoPopupWidget;
  }

  @Override
  protected void onReset() {
    super.onReset();

    getView().resetAndFocus();
  }

  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
 }

  @Override
  public void onOkButtonClicked() {
    sendCredentialsToServer();
  }

  private void sendCredentialsToServer() {
    String login = getView().getUserName();
    String password = getView().getPassword();

    dispatcher.execute(new LoginAction(login, password),
        new AsyncCallback<LoginResult>() {
        
      @Override
      public void onFailure(Throwable caught) {
        String message = "onFailure() - " + caught.getLocalizedMessage();
        
        if (caught instanceof LoginException) {
          message = "onFailure() - " + "Invalid User name or Password.";          	  		
	    }
          
        addToPopupSlot(loginFailedInfoPopupWidget);
        
        getView().resetAndFocus();
        
        Log.debug(message);
      }

      @Override
      public void onSuccess(LoginResult result) {
    	Log.debug("onSuccess() - obtained username : " + result.getUsername());

    	currentUser.setUsername(result.getUsername());
        currentUser.setRights(result.getRights());
        currentUser.setLoggedIn(true);
        
        StringBuffer sb = new StringBuffer();
        for (int right : currentUser.getRights()){
        	sb.append(right).append(" ");
        }
        Log.debug("onSuccess() - obtained rights : " + sb.toString());
        
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.homePage);
        placeManager.revealPlace(placeRequest);
        
        Log.debug("onSuccess() - " + result.getSessionKey());        
      }
    });
  }

}