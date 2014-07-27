/**
 * Author: Bob Chen
 */

package com.jcommerce.core.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import com.jcommerce.core.io.FileManagerFactory;

import freemarker.cache.TemplateLoader;

public class FileTemplateLoader implements TemplateLoader {

    public void closeTemplateSource(Object templateSource) throws IOException {
    }

    public Object findTemplateSource(String name) throws IOException {        
        return name;
    }

    public long getLastModified(Object templateSource) {
        return 0;
    }

    public Reader getReader(Object templateSource, String encoding) throws IOException {
        String fname = (String)templateSource;
        return new InputStreamReader(FileManagerFactory.getFileManager().getFile(fname).getInputStream(), encoding);
    }
}
