/**
 * Author: Bob Chen
 */

package com.jcommerce.core.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import freemarker.cache.TemplateLoader;

public class MailTemplateLoader implements TemplateLoader {

    public void closeTemplateSource(Object templateSource) throws IOException {
    }

    public Object findTemplateSource(String name) throws IOException {
        return name;
    }

    public long getLastModified(Object templateSource) {
        return 0;
    }

    public Reader getReader(Object templateSource, String encoding) throws IOException {
        return new StringReader((String)templateSource);
    }
}
