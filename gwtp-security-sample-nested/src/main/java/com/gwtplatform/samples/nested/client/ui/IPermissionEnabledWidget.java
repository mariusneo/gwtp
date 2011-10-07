package com.gwtplatform.samples.nested.client.ui;

import java.util.List;

public interface IPermissionEnabledWidget {
	void updateWidgetAppearance(List<Integer> permissions);
	
	void setViewPermissionNeeded(int permissionNeeded);
	
	int getViewPermissionNeeded();
}
