package com.gwtplatform.samples.nested.client.ui;

import java.util.List;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.HTMLPanel;

public class PermissionEnabledHTMLPanel extends HTMLPanel implements IPermissionEnabledWidget{

	private Integer permissionNeeded;
	
	
	public PermissionEnabledHTMLPanel(SafeHtml safeHtml) {
		super(safeHtml);
	}

	 public PermissionEnabledHTMLPanel(String html) {
		 super(html);
	 }
	 
	 public PermissionEnabledHTMLPanel(String tag, String html){
		 super(tag, html);
	 }

	@Override
	public void updateWidgetAppearance(List<Integer> permissions) {
		if (permissionNeeded != null){
			if (permissions.contains(permissionNeeded)){
				this.setVisible(true);
			}else{
				this.setVisible(false);
			}
		}
	}

	@Override
	public void setViewPermissionNeeded(int permissionNeeded) {
		this.permissionNeeded = permissionNeeded;
		
	}

	@Override
	public int getViewPermissionNeeded() {
		return permissionNeeded;
	}
}
