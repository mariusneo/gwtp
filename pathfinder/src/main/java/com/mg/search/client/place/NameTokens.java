/**
 * Copyright 2012 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.mg.search.client.place;

public class NameTokens {
    public static final String HOME = "!home";
    public static final String CREATE_MAP = "!cm";
    public static final String EDIT_MAP = "!em";
    public static final String FIND_PATH = "!fp";
    
    
    public static String getHome() {
        return HOME;
    }
    
    public static String getCreateMap(){
        return CREATE_MAP;
    }
    
    public static String getEditMap(){
        return EDIT_MAP;
    }
    
    public static String getFindPath(){
        return FIND_PATH;
    }
}
