/**
 * Copyright 2012 ArcBees Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.mg.search.client.application.map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.mg.search.client.resources.AppResources;

public class EditMapView extends ViewWithUiHandlers<EditMapUiHandlers> implements EditMapPresenter.MyView {
    public interface Binder extends UiBinder<Widget, EditMapView> {
    }

    @UiField
    HTMLPanel gridPanel;
    
    @UiField
    ToggleButton toggleBlockButton;

    @UiField
    ToggleButton toggleFromButton;

    @UiField
    ToggleButton toggleToButton;

    @UiField
    Button findPathButton;
    
    Grid grid;
    
    private final AppResources appResources;
   
    @Inject
    public EditMapView(Binder uiBinder, AppResources appResources) {
        initWidget(uiBinder.createAndBindUi(this));
        this.appResources = appResources;
    }

    @Override
    public void createGrid(final int lines, final int columns) {
        if (grid != null) {
            gridPanel.remove(grid);
        }

        grid = new Grid(lines, columns);
        grid.setStyleName(appResources.styles().maze());
        
        for (int row=0;row<lines;row++){
            for (int col=0;col<columns;col++){
                final int frow = row;
                final int fcol = col;
                
                Image image = new Image(appResources.cell());
                image.addClickHandler(new ClickHandler() {
                    
                    @Override
                    public void onClick(ClickEvent event) {
                        if (toggleFromButton.isDown()){
                            getUiHandlers().setFromCell(frow, fcol);
                        }else if (toggleToButton.isDown()){
                            getUiHandlers().setToCell(frow, fcol);
                        }else{
                            getUiHandlers().setCellBlocked(frow, fcol, toggleBlockButton.isDown());
                        }
                    }
                });
                
                
                grid.setWidget(row, col, image);
            }
        }

        gridPanel.add(grid);
    }

    @UiHandler("findPathButton")
    public void onFindButtonClick(final ClickEvent event) {
        getUiHandlers().onFindPathButtonClick();
    }    
    
    @UiHandler("toggleBlockButton")
    public void onToggleBlockButtonClick(final ClickEvent event){
        if (toggleBlockButton.isDown()){
            toggleFromButton.setDown(false);
            toggleToButton.setDown(false);
        }
    }

    @UiHandler("toggleToButton")
    public void onToggleFromButtonClick(final ClickEvent event){
        if (toggleToButton.isDown()){
            toggleBlockButton.setDown(false);
            toggleFromButton.setDown(false);
        }
    }

    @UiHandler("toggleFromButton")
    public void onToggleToButtonClick(final ClickEvent event){
        if (toggleFromButton.isDown()){
            toggleBlockButton.setDown(false);
            toggleToButton.setDown(false);
        }
    }
    
    @Override
    public void setCellBlock(int row, int column, boolean isBlock) {
        Image image = (Image)grid.getWidget(row, column);
        ImageResource imageResource = isBlock ? appResources.block() : appResources.cell();
        image.setResource(imageResource);
    }
    
    @Override
    public void setCellFrom(int row, int column){
        Image image = (Image)grid.getWidget(row, column);
        image.setResource(appResources.from());
    }
    
    @Override
    public void setCellTo(int row, int column){
        Image image = (Image)grid.getWidget(row, column);
        image.setResource(appResources.to());
        
    }
    
    @Override
    public void setFindPathEnabled(boolean enabled){
        findPathButton.setEnabled(enabled);
    }

}
