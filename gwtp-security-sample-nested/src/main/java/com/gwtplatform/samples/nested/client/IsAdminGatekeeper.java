package com.gwtplatform.samples.nested.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;
import com.gwtplatform.samples.nested.shared.Permissions;

public class IsAdminGatekeeper implements Gatekeeper {

    private final CurrentUser currentUser;

    @Inject
    public IsAdminGatekeeper(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public boolean canReveal() {
    	Log.debug("canReveal() - contains admin right : " + currentUser.hasRight(Permissions.ACCESS_ADMINISTRATION_PAGE));
    	
        return currentUser.hasRight(Permissions.ACCESS_ADMINISTRATION_PAGE);
    }

}
