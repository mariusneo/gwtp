/*
 * Copyright 2011 NTS New Technology Systems GmbH. All Rights reserved.
 * NTS PROPRIETARY/CONFIDENTIAL. Use is subject to NTS License Agreement.
 * Address: Doernbacher Strasse 126, A-4073 Wilhering, Austria
 * Homepage: www.ntswincash.com
 */

/**
 * 
 */
package com.mg.search.client.application.map;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author mga
 *
 */
public interface EditMapUiHandlers extends UiHandlers{
    void setCellBlocked(int row, int column, boolean isBlock);
    
    void setFromCell(int row, int column);
    
    void setToCell(int row, int column);
    
    void onFindPathButtonClick();
    
    void onReset();
}
