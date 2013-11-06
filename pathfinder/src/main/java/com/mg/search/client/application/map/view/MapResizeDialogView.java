/*
 * Copyright 2011 NTS New Technology Systems GmbH. All Rights reserved.
 * NTS PROPRIETARY/CONFIDENTIAL. Use is subject to NTS License Agreement.
 * Address: Doernbacher Strasse 126, A-4073 Wilhering, Austria
 * Homepage: www.ntswincash.com
 */

/**
 * 
 */
package com.mg.search.client.application.map.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;
import com.mg.search.client.application.map.MapResizeDialogUiHandlers;
import com.mg.search.client.application.map.presenter.MapResizeDialogPresenterWidget;

/**
 * @author mga
 *
 */
public class MapResizeDialogView extends PopupViewWithUiHandlers<MapResizeDialogUiHandlers> implements MapResizeDialogPresenterWidget.MyView {

    @UiTemplate("uibinder/MapResizeDialogView.ui.xml")
    interface Binder extends UiBinder<PopupPanel, MapResizeDialogView> {
    }

    @UiField
    Button okButton;

    @UiField 
    Button cancelButton;
    
    @UiField
    TextBox rowsText;
    
    @UiField
    TextBox columnsText;
    
    @Inject
    MapResizeDialogView(Binder uiBinder,
                     EventBus eventBus) {
        super(eventBus);
        initWidget(uiBinder.createAndBindUi(this));
        this.asPopupPanel().setGlassEnabled(true);
    }

    @UiHandler("okButton")
    void okButtonClicked(ClickEvent event) {
        getUiHandlers().onOkClicked();
    }
    
    @UiHandler("cancelButton")
    void cancelButtonClicked(ClickEvent event){
        hide();
    }

    @Override
    public void setRows(int rows) {
        rowsText.setValue(Integer.toString(rows));
    }

    @Override
    public int getRows() {
        int rows = 0;
        
        try{
            rows = Integer.parseInt(rowsText.getValue());
        }catch(NumberFormatException e){}
        return rows;
    }

    @Override
    public void setColumns(int columns) {
        columnsText.setValue(Integer.toString(columns));
    }

    @Override
    public int getColumns() {
        int columns = 0;
        try{
            columns = Integer.parseInt(columnsText.getValue());
        }catch(NumberFormatException e){
            // ignore
        }
        return  columns;
    }
}

