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

import com.google.inject.Inject;

/**
 * @author mga
 *
 */
public class ResourceLoader {
    @Inject
    public ResourceLoader(AppResources resources) {
        resources.styles().ensureInjected();
    }
}
