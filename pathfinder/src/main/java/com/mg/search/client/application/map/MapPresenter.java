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
import com.gwtplatform.mvp.client.proxy.RevealRootPopupContentEvent;
import com.mg.search.client.application.ApplicationPresenter;
import com.mg.search.client.place.NameTokens;

public class MapPresenter extends Presenter<MapPresenter.MyView, MapPresenter.MyProxy> implements
        MapUiHandlers {
    public interface MyView extends View, HasUiHandlers<MapUiHandlers> {
        void createGrid(int lines, int columns);

        void setCellBlock(int row, int column, boolean isBlock);

        void setCellFrom(int row, int column);

        void setCellTo(int row, int column);

        void setFindPathButtonEnabled(boolean enabled);
        
        void setResizeMapButtonEnabled(boolean enabled);

        void setPath(List<Square> path);

        void resetPath();

        void addCellToOpenList(Square square);

        void updateOpenListCell(Square square);

        void addDestinationToOpenList(Square square);

        void addCellToClosedList(Square square);

    }

    @ProxyStandard
    @NameToken(NameTokens.HOME)
    public interface MyProxy extends ProxyPlace<MapPresenter> {
    }


    private static final int DEFAULT_ROWS_COUNT = 8;
    
    private static final int DEFAULT_COLUMNS_COUNT = 10;
    
    private int rows = 0;

    private int columns = 0;

    private boolean[][] matrix = null;

    private Cell fromCell = null;

    private Cell toCell = null;

    private List<Square> path = null;

    private AStarSearchService service;

    private Timer serviceTimer;

    /**
     * Semaphore field to avoid updating the view via the callback
     * from the search service after the operation of finding the path
     * was cancelled.
     */
    private Boolean serviceTimerCancelled;

    private MapResizeDialogPresenterWidget mapResizeDialog;
    
    @Inject
    public MapPresenter(EventBus eventBus, MyView view, MyProxy proxy, MapResizeDialogPresenterWidget mapResizeDialog) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);
        this.mapResizeDialog = mapResizeDialog;
        getView().setUiHandlers(this);
    }

    @Override
    public void prepareFromRequest(final PlaceRequest request) {
        rows = DEFAULT_ROWS_COUNT;
        columns = DEFAULT_COLUMNS_COUNT;

        fromCell = null;
        toCell = null;
        path = null;

        service = new AStarSearchService();
        serviceTimer = null;

        reset();
        
        buildDefaultMap();
    }

    private void buildDefaultMap(){
        setFromCell(0, 0);
        setToCell(0, DEFAULT_COLUMNS_COUNT - 1);
        
        setCellBlocked(0, 1, true); 
        setCellBlocked(0, 2, true);
        setCellBlocked(0, 3, true);
        
        setCellBlocked(2, 1, true); 
        setCellBlocked(2, 2, true);
        setCellBlocked(2, 3, true);
        
    }
    
    @Override
    public void onResetMap() {
        reset();
    }

    private void reset() {
        matrix = new boolean[rows][columns];
        fromCell = null;
        toCell = null;
        path = null;
        if (serviceTimer != null) {
            serviceTimer.cancel();
            serviceTimer = null;
            serviceTimerCancelled = true;
        }

        getView().createGrid(rows, columns);
        getView().setFindPathButtonEnabled(false);
        getView().setResizeMapButtonEnabled(true);

        serviceTimerCancelled = false;
    }

    private boolean isServiceTimerCancelled() {
        return serviceTimerCancelled;
    }

    @Override
    public void setCellBlocked(int row, int column, boolean isBlock) {
        resetPath();

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
        resetPath();

        AStarSearchService.FindPathCallback callback = new AStarSearchService.FindPathCallback() {

            @Override
            public void onSquareAddedToOpenList(final Square square) {
                if (!isServiceTimerCancelled()) {
                    if (square.getI() == toCell.getRow() && square.getJ() == toCell.getColumn()) {
                        getView().addDestinationToOpenList(square);
                    } else {
                        getView().addCellToOpenList(square);
                    }
                }

            }

            @Override
            public void onSquareAddedToClosedList(final Square square) {
                if (!isServiceTimerCancelled()) {
                    getView().addCellToClosedList(square);
                }
            }

            @Override
            public void onPathFound(List<Square> path) {
                if (!isServiceTimerCancelled()) {
                    if (path != null && !path.isEmpty()) {
                        MapPresenter.this.path = path;
                        getView().setPath(path);
                    }

                    getView().setFindPathButtonEnabled(true);
                    getView().setResizeMapButtonEnabled(true);
                }
            }

            @Override
            public void onPathNotFound() {
                if (!isServiceTimerCancelled()) {
                    getView().setFindPathButtonEnabled(true);
                    getView().setResizeMapButtonEnabled(true);
                }
            }

            @Override
            public void onOpenListSquareUpdated(final Square square) {
                if (!isServiceTimerCancelled()) {
                    getView().updateOpenListCell(square);
                }
            }

        };

        getView().setFindPathButtonEnabled(false);
        getView().setResizeMapButtonEnabled(false);
        serviceTimer = service.findPath(matrix, fromCell.getRow(), fromCell.getColumn(), toCell.getRow(),
                toCell.getColumn(), callback);

    }

    private void resetPath() {
        if (path != null) {
            getView().resetPath();
            getView().setCellFrom(fromCell.getRow(), fromCell.getColumn());
            getView().setCellTo(toCell.getRow(), toCell.getColumn());
            path = null;
        }

    }

    @Override
    public void setFromCell(int row, int column) {
        resetPath();
        if (fromCell != null) {
            getView().setCellBlock(fromCell.getRow(), fromCell.getColumn(), false);
        }

        fromCell = new Cell(row, column);
        getView().setCellFrom(row, column);

        toggleFindPathEnabledState();
    }

    private void toggleFindPathEnabledState() {
        if (fromCell != null && toCell != null) {
            getView().setFindPathButtonEnabled(true);
        } else {
            getView().setFindPathButtonEnabled(false);
        }
    }

    @Override
    public void setToCell(int row, int column) {
        resetPath();

        if (toCell != null) {
            getView().setCellBlock(toCell.getRow(), toCell.getColumn(), false);
        }
        toCell = new Cell(row, column);
        getView().setCellTo(row, column);

        toggleFindPathEnabledState();
    }

    @Override
    public void onSpeedChanged(int newSpeedFactor) {
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
    
    @Override
    public void showMapResizeDialog() {
        mapResizeDialog.setDialogCallback(new MapResizeDialogPresenterWidget.IDialogCallback() {
            
            @Override
            public void resizeMap(int rows, int columns) {
                MapPresenter.this.rows = rows;
                MapPresenter.this.columns = columns;
                MapPresenter.this.reset();
            }
        });
        RevealRootPopupContentEvent.fire(this, mapResizeDialog);
    }
}
