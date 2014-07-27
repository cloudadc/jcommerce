/**
* Author: Bob Chen
*/

package com.jcommerce.core.module;

import java.net.URL;
import java.util.Map;

public interface IModule {
    String getCode();
    
    String getDescription();
    
    String getAuthor();
    
    URL getWebSite();
    
    String getVersion();
    
//    Map<String, Object> getConfig();
    
    public String getDefaultConfig();
    public String getSerializedConfig(Map<String, Object> props);
    // key -> value
    // lable -> key -> type -> (options)
    
    public IConfigMetaData getConfigMetaData(String serializedConfig);
}
