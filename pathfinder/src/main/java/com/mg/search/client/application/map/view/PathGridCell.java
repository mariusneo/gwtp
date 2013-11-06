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

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author mga
 *
 */
public class PathGridCell extends FocusPanel{
    @UiTemplate("uibinder/PathGridCell.ui.xml")
    interface PathGridCellBinder extends UiBinder<Widget, PathGridCell> {
    }
    
    private static PathGridCellBinder uiBinder = GWT.create(PathGridCellBinder.class);
    
    @UiField
    Label fLabel;
    
    @UiField
    Label gLabel;
    
    @UiField
    Label hLabel;
    
    @UiField
    HTMLPanel imgContainer;
    
    
    public PathGridCell(){
        setWidget(uiBinder.createAndBindUi(this));
    }
    
    public void setFValue(String text){
        if (text ==null || text.isEmpty()){
            fLabel.setText("");
        }else{
            fLabel.setText(text);
        }
    }
    
    public void setGValue(String text){
        if (text==null || text.isEmpty()){
            gLabel.setText("");
        }
        gLabel.setText(text);
    }
    
    public void setHValue(String text){
        if (text==null || text.isEmpty()){
            hLabel.setText("");
        }
        hLabel.setText(text);        
    }
    
    public void setImage(final Image image){
        imgContainer.clear();
        imgContainer.add(image);
    }
    
    @Override
    public void clear(){
        fLabel.setText("");
        gLabel.setText("");
        hLabel.setText("");
        imgContainer.clear();
    }
}
