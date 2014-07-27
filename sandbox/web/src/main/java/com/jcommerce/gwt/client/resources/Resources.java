/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.resources;

import com.google.gwt.core.client.GWT;

public class Resources {
    public static final iShopImages images = (iShopImages) GWT.create(iShopImages.class);
    public static final IShopConstants constants = (IShopConstants) GWT.create(IShopConstants.class);
    
    public static final IShopMessages messages = (IShopMessages) GWT.create(IShopMessages.class);
    
}
