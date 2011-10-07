package com.gwtplatform.samples.nested.client.view;

import java.util.List;

import com.gwtplatform.mvp.client.ViewImpl;
import com.gwtplatform.samples.nested.client.ui.WidgetPermissionEnabledUtils;

public abstract class AbstractPermissionEnabledView extends ViewImpl implements IPermissionEnabledView{
	public void updateView(List<Integer> permissionsList){
        WidgetPermissionEnabledUtils.updateWidget(asWidget(), permissionsList);
	}
}
