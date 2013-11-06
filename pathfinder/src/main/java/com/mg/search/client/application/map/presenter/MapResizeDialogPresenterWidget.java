/*
 * Copyright 2011 NTS New Technology Systems GmbH. All Rights reserved. NTS PROPRIETARY/CONFIDENTIAL. Use is
 * subject to NTS License Agreement. Address: Doernbacher Strasse 126, A-4073 Wilhering, Austria Homepage:
 * www.ntswincash.com
 */

/**
 * 
 */
package com.mg.search.client.application.map.presenter;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.mg.search.client.application.map.MapResizeDialogUiHandlers;

/**
 * <p>
 * This presenter incorporates the logic needed for displaying a popup in case that the user wants to resize
 * the map(matrix) on which the a* search algorithm has to be applied.
 * </p>
 * 
 * <p>
 * The {@link PresenterWidget} of a dialog box that is meant to be displayed no matter which presenter is
 * visible.
 * </p>
 */
public class MapResizeDialogPresenterWidget extends PresenterWidget<MapResizeDialogPresenterWidget.MyView>
        implements MapResizeDialogUiHandlers {
    public interface MyView extends PopupView, HasUiHandlers<MapResizeDialogUiHandlers> {

        void setRows(int rows);

        int getRows();

        void setColumns(int columns);

        int getColumns();
    }

    public interface IDialogCallback {

        void resizeMap(int rows, int columns);
    }

    private IDialogCallback callback;

    @Inject
    MapResizeDialogPresenterWidget(EventBus eventBus, MyView view) {
        super(eventBus, view);
        getView().setUiHandlers(this);
    }

    public void setDialogCallback(IDialogCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onOkClicked() {
        if (callback != null) {
            int rows = getView().getRows();
            int columns = getView().getColumns();
            if (rows > 0 && rows < 100 && columns > 0 && columns < 100) {
                callback.resizeMap(getView().getRows(), getView().getColumns());
            }
            getView().hide();
            // TODO notify the user about false input
        }
    }

}
