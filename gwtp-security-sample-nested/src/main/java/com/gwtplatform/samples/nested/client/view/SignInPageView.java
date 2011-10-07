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

package com.gwtplatform.samples.nested.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.gwtplatform.samples.nested.client.presenter.SignInPagePresenter;
import com.gwtplatform.samples.nested.client.view.handler.SignInPageUiHandlers;
import com.gwtplatform.samples.nested.shared.FieldVerifier;

public class SignInPageView extends ViewWithUiHandlers<SignInPageUiHandlers>
		implements SignInPagePresenter.MyView {

	interface SignInPageViewUiBinder extends UiBinder<Widget, SignInPageView> {
	}

	private static SignInPageViewUiBinder uiBinder = GWT
			.create(SignInPageViewUiBinder.class);

	private static final String DEFAULT_USER_NAME = "Administrator";
	private static final String DEFAULT_PASSWORD = "N0More$ecrets";

	@UiField
	Button signInButton;
	@UiField
	PasswordTextBox passwordField;
	@UiField
	TextBox userNameField;

	public final Widget widget;

	@Inject
	public SignInPageView() {
		widget = uiBinder.createAndBindUi(this);

		userNameField.setText(DEFAULT_USER_NAME);

		// See FieldVerifier
		// Passwords must contain at least 8 characters with at least one digit,
		// one upper case letter, one lower case letter and one special symbol
		// (“@#$%”).
		passwordField.setText(DEFAULT_PASSWORD);
	}

	@UiHandler("signInButton")
	public void onSignInButtonClick(ClickEvent event) {

		if (FieldVerifier.isValidUserName(getUserName())
				&& (FieldVerifier.isValidPassword(getPassword()))) {
			if (getUiHandlers() != null) {
				getUiHandlers().onOkButtonClicked();
			}
		} else {
			// alertWidget("Sign In",
			// "You must enter a valid User name and Password.").center();
			Window.alert("You must enter a valid User name and Password.");
			resetAndFocus();
		}
	}

	public static DialogBox alertWidget(final String header,
			final String content) {
		final DialogBox box = new DialogBox();
		final VerticalPanel panel = new VerticalPanel();
		box.setText(header);
		panel.add(new Label(content));
		final Button buttonClose = new Button("Close", new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				box.hide();
			}
		});
		// few empty labels to make widget larger
		final Label emptyLabel = new Label("");
		emptyLabel.setSize("auto", "25px");
		panel.add(emptyLabel);
		panel.add(emptyLabel);
		buttonClose.setWidth("90px");
		panel.add(buttonClose);
		panel.setCellHorizontalAlignment(buttonClose, HasAlignment.ALIGN_RIGHT);
		box.add(panel);
		return box;
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public String getUserName() {
		return userNameField.getText();
	}

	@Override
	public String getPassword() {
		return passwordField.getText();
	}

	@Override
	public void resetAndFocus() {
		userNameField.setFocus(true);
		userNameField.selectAll();
	}
}
