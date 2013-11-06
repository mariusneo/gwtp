/*
 * Copyright 2011 NTS New Technology Systems GmbH. All Rights reserved.
 * NTS PROPRIETARY/CONFIDENTIAL. Use is subject to NTS License Agreement.
 * Address: Doernbacher Strasse 126, A-4073 Wilhering, Austria
 * Homepage: www.ntswincash.com
 */

/**
 * 
 */
package com.mg.search.client.resources;


import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

/**
 * @author mga
 *
 */
public interface AppResources extends ClientBundle{

    public interface Styles extends CssResource {
        String emptyCell();
        
        String blockCell();
        
        String gridCell();
        
        String gridBlockCell();
        
        String maze();
        
        String openedCell();
        
        String updatedOpenedCell();
        
        String closedCell();
        
        String pathCell();
    }
    
    Styles styles();
    
    @Source("icons48x48/selection.png")
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
    ImageResource cellBig();

    @Source("icons48x48/media_stop_red.png")
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
    ImageResource blockBig();

    @Source("icons48x48/nav_plain_green.png")
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
    ImageResource fromBig();

    @Source("icons48x48/nav_plain_red.png")
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
    ImageResource toBig();
    
    @Source("icons16x16/nav_plain_green.png")
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
    ImageResource fromSmall();

    @Source("icons16x16/nav_plain_red.png")
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
    ImageResource toSmall();

    
    
    @Source("icons16x16/nav_down_green.png")
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
    ImageResource downSmall();
    
    @Source("icons16x16/nav_down_left_green.png")
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
    ImageResource downLeftSmall();

    @Source("icons16x16/nav_down_right_green.png")
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
    ImageResource downRightSmall();
    
    @Source("icons16x16/nav_left_green.png")
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
    ImageResource leftSmall();
    
    @Source("icons16x16/nav_right_green.png")
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
    ImageResource rightSmall();
    
    @Source("icons16x16/nav_up_green.png")
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
    ImageResource upSmall();
    
    @Source("icons16x16/nav_up_left_green.png")
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
    ImageResource upLeftSmall();

    @Source("icons16x16/nav_up_right_green.png")
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
    ImageResource upRightSmall();
}
