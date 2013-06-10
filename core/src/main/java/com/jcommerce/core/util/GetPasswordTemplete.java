/**
 * GetPwdTemplete
 * 
 * @version 0.1
 * 
 * @date 2008.12.25
 * 
 * @author KingZhao
 */
package com.jcommerce.core.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import freemarker.template.*;


public class GetPasswordTemplete {

 private final Log logger = LogFactory.getLog(getClass());

 private Configuration freemarker_cfg = null;

 protected Configuration getFreeMarkerCFG() {
  if (null == freemarker_cfg) {
   // Initialize the FreeMarker configuration;
   // - Create a configuration instance
   freemarker_cfg = new Configuration();  
   try {
    freemarker_cfg.setDirectoryForTemplateLoading(new File("./template"));
   } catch (Exception ex) {

    ex.printStackTrace();
   }
  }

  return freemarker_cfg;
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
 public boolean geneHtmlFile(String templateFileName, Map propMap,
   String htmlFilePath, String htmlFileName) throws IOException {
  // @todo 从配置中取得要静态文件存放的根路径:需要改为自己的属性类调用
  try {
   Template t = getFreeMarkerCFG().getTemplate(templateFileName);  

   Writer out = new StringWriter();

   t.process(propMap, out);
   String k = out.toString();
   return true;
  } catch (TemplateException e) {   
   return false;
  }
 
 }
}