package com.jcommerce.core.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TempleteProcessor {

    private final Log logger = LogFactory.getLog(getClass());

    private Configuration cfg = null;

    protected Configuration getFreeMarkerCFG() {
        if (null == cfg) {
            // Initialize the FreeMarker configuration;
            // - Create a configuration instance
            cfg = new Configuration();
//                cfg.setDirectoryForTemplateLoading(new File("./template"));
            cfg.setTemplateLoader(new FileTemplateLoader());
        }

        return cfg;
    }

    /**
     * 生成静态文件.
     * 
     * @param templateFileName
     *            模板文件名
     * @param propMap
     *            用于处理模板的属性Object映射
     * @throws IOException
     */
    public String getText(String templateName, Map propMap) throws IOException {
        try {
            Template t = getFreeMarkerCFG().getTemplate(templateName);

            Writer out = new StringWriter();
            t.process(propMap, out);
            return out.toString();
        } catch (TemplateException e) {
            e.printStackTrace();
            throw new IOException("Generate template: "+ templateName+", " +e.toString());
        }
    }
}