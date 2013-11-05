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

import java.util.List;

import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.mg.search.client.application.ApplicationPresenter;
import com.mg.search.client.place.NameTokens;

public class EditMapPresenter extends Presenter<EditMapPresenter.MyView, EditMapPresenter.MyProxy> implements
        EditMapUiHandlers {
    public interface MyView extends View, HasUiHandlers<EditMapUiHandlers> {
        void createGrid(int lines, int columns);

        void setCellBlock(int row, int column, boolean isBlock);

        void setCellFrom(int row, int column);

        void setCellTo(int row, int column);

        void setFindPathEnabled(boolean enabled);
        
        void setPath(List<Square> path);
        
        void resetPath(List<Square> path);
        
        void addCellToOpenList(Square square);
        
        void addCellToClosedList(Square square);
    }

    @ProxyStandard
    @NameToken(NameTokens.EDIT_MAP)
    public interface MyProxy extends ProxyPlace<EditMapPresenter> {
    }

    public static final String PARAMETER_COLUMNS = "c";
    public static final String PARAMETER_LINES = "l";


    private int lines = 0;

    private int columns = 0;

    private boolean[][] matrix = null;

    private Cell fromCell = null;

    private Cell toCell = null;
    
    private List<Square> path = null;
    
    private AStarSearchService service;

    @Inject
    public EditMapPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);
        getView().setUiHandlers(this);
    }

    @Override
    public void prepareFromRequest(final PlaceRequest request) {
        lines = Integer.parseInt(request.getParameter(PARAMETER_LINES, "0"));
        columns = Integer.parseInt(request.getParameter(PARAMETER_COLUMNS, "0"));
        // FIXME check for NumberFormatException

        fromCell = null;
        toCell = null;

        service = new AStarSearchService();
        
        reset();
    }
    
    @Override
    public void onReset(){
        reset();
    }

    private void reset(){
        matrix = new boolean[lines][columns];

        getView().createGrid(lines, columns);
        getView().setFindPathEnabled(false);
        
    }
    
    
    @Override
    public void setCellBlocked(int row, int column, boolean isBlock) {
        if (path!= null){
            getView().resetPath(path);
        }

        
        if (matrix[row][column] != isBlock) {
            matrix[row][column] = isBlock;

            if (fromCell != null && fromCell.getRow() == row && fromCell.getColumn() == column) {
                fromCell = null;
                toggleFindPathEnabledState();
            }

            if (toCell != null && toCell.getRow() == row && toCell.getColumn() == column) {
                toCell = null;
                toggleFindPathEnabledState();
            }

            getView().setCellBlock(row, column, isBlock);
        }
    }

    @Override
    public void onFindPathButtonClick() {
        if (path != null){
            getView().resetPath(path);
        }
        
        
        AStarSearchService.FindPathCallback callback = new AStarSearchService.FindPathCallback() {
            
            @Override
            public void onSquareAddedToOpenList(final Square square) {
                getView().addCellToOpenList(square);
            }
            
            @Override
            public void onSquareAddedToClosedList(final Square square) {
                getView().addCellToClosedList(square);
            }

            @Override
            public void onPathFound(List<Square> path) {
                if (path != null && !path.isEmpty()){
                    EditMapPresenter.this.path = path;
                    getView().setPath(path);
                }

            }
            
            
        };
        
        service.findPath(matrix, fromCell.getRow(), fromCell.getColumn(),
                        toCell.getRow(), toCell.getColumn(), callback);
        
    }

    @Override
    public void setFromCell(int row, int column) {
        if (path!= null){
            getView().resetPath(path);
        }
        if (fromCell != null) {
            getView().setCellBlock(fromCell.getRow(), fromCell.getColumn(), false);
        }

        fromCell = new Cell(row, column);
        getView().setCellFrom(row, column);

        toggleFindPathEnabledState();
    }

    private void toggleFindPathEnabledState() {
        if (fromCell != null && toCell != null) {
            getView().setFindPathEnabled(true);
        } else {
            getView().setFindPathEnabled(false);
        }
    }

    @Override
    public void setToCell(int row, int column) {
        if (path!= null){
            getView().resetPath(path);
        }

        if (toCell != null) {
            getView().setCellBlock(toCell.getRow(), toCell.getColumn(), false);
        }
        toCell = new Cell(row, column);
        getView().setCellTo(row, column);

        toggleFindPathEnabledState();
    }

    
    @Override
    public void onSpeedChanged(int newSpeedFactor){
        service.setSpeed(newSpeedFactor);
    }
    
    private class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }
    }
}
