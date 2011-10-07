package com.gwtplatform.samples.nested.client.presenter;

import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.samples.nested.client.CurrentUser;
import com.gwtplatform.samples.nested.client.view.IPermissionEnabledView;

public abstract class AbstractPresenter<V extends IPermissionEnabledView, P extends Proxy<?>>
		extends Presenter<V, P> {

	//TODO mga : instead of passing CurrentUser maybe there could be passed an
	// object which contains more information to be used in the presenter.
	private CurrentUser currentUser;

	public AbstractPresenter(EventBus eventBus, V view, P proxy,
			CurrentUser currentUser) {
		super(eventBus, view, proxy);
		this.currentUser = currentUser;
	}

	protected CurrentUser getCurrentUser() {
		return currentUser;
	}

	@Override
	protected void onReveal() {
		super.onReveal();

		updateViewDependingOnUserPermissions();
	}

	private void updateViewDependingOnUserPermissions(){
		//TODO mga : do the update of the view only when this is needed.
		getView().updateView(currentUser.getRights());
	}
}
