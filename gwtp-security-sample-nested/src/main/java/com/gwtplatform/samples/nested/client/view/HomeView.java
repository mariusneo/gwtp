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

package com.gwtplatform.samples.nested.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.gwtplatform.samples.nested.client.presenter.HomePresenter.MyView;

/**
 * @author Christian Goudreau
 */
public class HomeView extends AbstractPermissionEnabledView implements MyView {
	interface HomeViewUiBinder extends UiBinder<Widget, HomeView> {
	}

	private static HomeViewUiBinder uiBinder = GWT
			.create(HomeViewUiBinder.class);

	private final Widget widget;

	@UiField
	Button searchAllButton;

	@UiField
	Button searchButton;

	@UiField
	TextBox lastNameTextBox;

	@UiField
	Label searchResult;

	public HomeView() {
		widget = uiBinder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public HasClickHandlers getSearchButton() {
		return searchButton;
	}

	@Override
	public HasValue<String> getLastNameTextBox() {
		return lastNameTextBox;
	}

	@Override
	public HasClickHandlers getSearchAllButton() {
		return searchAllButton;
	}
	
	@Override
	public void setSearchResult(String result){
		searchResult.setText(result);
	}
}