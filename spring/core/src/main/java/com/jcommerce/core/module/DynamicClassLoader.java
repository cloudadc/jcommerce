/**
* Author: Bob Chen
*/

package com.jcommerce.core.module;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Vector;


public class DynamicClassLoader extends URLClassLoader {
    public DynamicClassLoader(Vector v) 
    {
        super(getUrls(v));//, parent);
    }

    public DynamicClassLoader(Vector v, ClassLoader parent) 
    {
        super(getUrls(v), parent);
    }

    private static final URL[] getUrls(Vector v)
    {
        URL[] urls = new URL[v.size()];
        try{
            for(int i = 0;i<urls.length;i++)
                urls[i] = ((File)v.elementAt(i)).toURL();
        }catch(Exception ex){
        }
        return urls;
    }
}
