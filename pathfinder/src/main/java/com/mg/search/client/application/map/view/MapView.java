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

package com.mg.search.client.application.map.view;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.kiouri.sliderbar.client.event.BarValueChangedEvent;
import com.kiouri.sliderbar.client.event.BarValueChangedHandler;
import com.kiouri.sliderbar.client.solution.speedajuster.SpeedAjuster;
import com.mg.search.client.application.map.MapUiHandlers;
import com.mg.search.client.application.map.Square;
import com.mg.search.client.application.map.presenter.MapPresenter;
import com.mg.search.client.resources.AppResources;

public class MapView extends ViewWithUiHandlers<MapUiHandlers> implements MapPresenter.MyView {
    @UiTemplate("uibinder/MapView.ui.xml")
    public interface Binder extends UiBinder<Widget, MapView> {
    }

    @UiField
    HTMLPanel gridPanel;

    @UiField
    ToggleButton toggleBlockButton;

    @UiField
    ToggleButton toggleCellButton;

    @UiField
    ToggleButton toggleFromButton;

    @UiField
    ToggleButton toggleToButton;

    @UiField
    Button findPathButton;

    @UiField
    Button resetButton;

    @UiField
    Button resizeMapButton;

    @UiField
    SpeedAjuster speedAjuster;

    Grid grid;

    private final AppResources appResources;

    @Inject
    public MapView(Binder uiBinder, AppResources appResources) {
        initWidget(uiBinder.createAndBindUi(this));
        this.appResources = appResources;

        speedAjuster.addBarValueChangedHandler(new BarValueChangedHandler() {
            @Override
            public void onBarValueChanged(BarValueChangedEvent event) {
                getUiHandlers().onSpeedChanged(event.getValue());
            }
        });
    }

    @Override
    public void createGrid(final int lines, final int columns) {
        if (grid != null) {
            gridPanel.remove(grid);
        }

        grid = new Grid(lines, columns);
        grid.setStyleName(appResources.styles().maze());

        for (int row = 0; row < lines; row++) {
            for (int col = 0; col < columns; col++) {
                final int frow = row;
                final int fcol = col;

                FocusPanel cell = createGridCell(frow, fcol);
                grid.setWidget(row, col, cell);

            }
        }

        gridPanel.add(grid);
    }

    private FocusPanel createGridCell(final int row, final int column) {
        FocusPanel cell = new FocusPanel();
        cell.setStyleName(appResources.styles().gridCell());
        cell.addClickHandler(createGridCellClickHandler(row, column));

        return cell;

    }

    private ClickHandler createGridCellClickHandler(final int row, final int column) {
        return new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (toggleFromButton.isDown()) {
                    getUiHandlers().setFromCell(row, column);
                } else if (toggleToButton.isDown()) {
                    getUiHandlers().setToCell(row, column);
                } else {
                    getUiHandlers().setCellBlocked(row, column, toggleBlockButton.isDown());
                }
            }
        };
    }

    @UiHandler("findPathButton")
    public void onFindButtonClick(final ClickEvent event) {
        getUiHandlers().onFindPathButtonClick();
    }

    @UiHandler("toggleCellButton")
    public void onToggleCellButtonClick(final ClickEvent event) {
        if (toggleCellButton.isDown()) {
            toggleBlockButton.setDown(false);
            toggleFromButton.setDown(false);
            toggleToButton.setDown(false);
        }
    }

    @UiHandler("toggleBlockButton")
    public void onToggleBlockButtonClick(final ClickEvent event) {
        if (toggleBlockButton.isDown()) {
            toggleCellButton.setDown(false);
            toggleFromButton.setDown(false);
            toggleToButton.setDown(false);
        }
    }

    @UiHandler("toggleToButton")
    public void onToggleFromButtonClick(final ClickEvent event) {
        if (toggleToButton.isDown()) {
            toggleCellButton.setDown(false);
            toggleBlockButton.setDown(false);
            toggleFromButton.setDown(false);
        }
    }

    @UiHandler("toggleFromButton")
    public void onToggleToButtonClick(final ClickEvent event) {
        if (toggleFromButton.isDown()) {
            toggleCellButton.setDown(false);
            toggleBlockButton.setDown(false);
            toggleToButton.setDown(false);
        }
    }

    @UiHandler("resetButton")
    public void onResetButtonClick(final ClickEvent event) {
        getUiHandlers().onResetMap();
    }

    @UiHandler("resizeMapButton")
    public void onMapResizeButtonClick(final ClickEvent event) {
        getUiHandlers().showMapResizeDialog();
    }

    @Override
    public void setCellBlock(int row, int column, boolean isBlock) {
        Object cell = grid.getWidget(row, column);
        if (cell instanceof FocusPanel) {
            FocusPanel gridCell = (FocusPanel) cell;
            if (isBlock) {
                gridCell.setStyleName(appResources.styles().gridBlockCell());
            } else {
                gridCell.setStyleName(appResources.styles().gridCell());
            }
        } else if (cell instanceof PathGridCell) {
            FocusPanel gridCell = createGridCell(row, column);
            grid.setWidget(row, column, gridCell);
        }
    }

    @Override
    public void setCellFrom(int row, int column) {
        PathGridCell startCell = new PathGridCell();
        startCell.addClickHandler(createGridCellClickHandler(row, column));
        startCell.setImage(new Image(appResources.fromSmall()));
        grid.setWidget(row, column, startCell);
    }

    @Override
    public void setCellTo(int row, int column) {
        PathGridCell endCell = new PathGridCell();
        endCell.setImage(new Image(appResources.toSmall()));
        grid.setWidget(row, column, endCell);
    }

    @Override
    public void setFindPathButtonEnabled(boolean enabled) {
        findPathButton.setEnabled(enabled);
    }

    @Override
    public void setPath(List<Square> path) {
        // the first and the last element will not be taken into account for now
        Iterator<Square> squareIterator = path.iterator();
        if (squareIterator.hasNext()) {
            do {
                Square square = squareIterator.next();
                PathGridCell cell = (PathGridCell) grid.getWidget(square.getI(), square.getJ());
                cell.addStyleName(appResources.styles().pathCell());
            } while (squareIterator.hasNext());
        }
    }

    @Override
    public void resetPath() {
        // reset all the cells which were used for finding the previous path
        final int lines = grid.getRowCount();
        final int columns = grid.getColumnCount();
        for (int row = 0; row < lines; row++) {
            for (int col = 0; col < columns; col++) {
                if (grid.getWidget(row, col) instanceof PathGridCell) {
                    FocusPanel cell = createGridCell(row, col);
                    grid.setWidget(row, col, cell);

                }
            }
        }
    }

    @Override
    public void addCellToOpenList(Square square) {
        PathGridCell openedCell = new PathGridCell();
        openedCell.setImage(new Image(appResources.fromSmall()));

        openedCell.setGValue(Integer.toString(square.getGcost()));
        openedCell.setHValue(Integer.toString(square.getHcost()));

        int fValue = square.getGcost() + square.getHcost();
        openedCell.setFValue(Integer.toString(fValue));

        openedCell.setImage(new Image(getDirectionImageResource(square)));

        openedCell.addStyleName(appResources.styles().openedCell());

        grid.setWidget(square.getI(), square.getJ(), openedCell);

    }

    @Override
    public void updateOpenListCell(Square square) {
        PathGridCell openedCell = (PathGridCell) grid.getWidget(square.getI(), square.getJ());
        openedCell.setGValue(Integer.toString(square.getGcost()));
        openedCell.setHValue(Integer.toString(square.getHcost()));

        int fValue = square.getGcost() + square.getHcost();
        openedCell.setFValue(Integer.toString(fValue));

        openedCell.setImage(new Image(getDirectionImageResource(square)));

        openedCell.addStyleName(appResources.styles().updatedOpenedCell());
    }

    @Override
    public void addDestinationToOpenList(Square square) {
        PathGridCell destinationCell = (PathGridCell) grid.getWidget(square.getI(), square.getJ());

        destinationCell.setGValue(Integer.toString(square.getGcost()));
        destinationCell.setHValue(Integer.toString(square.getHcost()));

        int fValue = square.getGcost() + square.getHcost();
        destinationCell.setFValue(Integer.toString(fValue));
        destinationCell.addStyleName(appResources.styles().openedCell());
    }

    private ImageResource getDirectionImageResource(final Square square) {
        Square parentSquare = square.getParent();
        if (square.getI() == parentSquare.getI()) {
            // move on the line
            if (square.getJ() > parentSquare.getJ()) {
                return appResources.rightSmall();
            } else {
                return appResources.leftSmall();
            }
        } else if (square.getJ() == parentSquare.getJ()) {
            // move on the column
            if (square.getI() > parentSquare.getI()) {
                return appResources.downSmall();
            } else {
                return appResources.upSmall();
            }
        } else {
            // move on diagonal
            if (square.getI() < parentSquare.getI()) {
                if (square.getJ() < parentSquare.getJ()) {
                    return appResources.upLeftSmall();
                } else {
                    return appResources.upRightSmall();
                }
            } else {
                if (square.getJ() < parentSquare.getJ()) {
                    return appResources.downLeftSmall();
                } else {
                    return appResources.downRightSmall();
                }
            }
        }
    }

    @Override
    public void addCellToClosedList(Square square) {
        PathGridCell openedCell = (PathGridCell) grid.getWidget(square.getI(), square.getJ());
        openedCell.addStyleName(appResources.styles().closedCell());

    }

    @Override
    public void setResizeMapButtonEnabled(boolean enabled) {
        this.resizeMapButton.setEnabled(enabled);
    }

}
