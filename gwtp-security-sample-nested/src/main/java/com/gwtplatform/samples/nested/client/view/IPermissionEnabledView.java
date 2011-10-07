package com.gwtplatform.samples.nested.client.view;

import java.util.List;

import com.gwtplatform.mvp.client.View;

public interface IPermissionEnabledView extends View{
	void updateView(List<Integer> permissionsList);
}
