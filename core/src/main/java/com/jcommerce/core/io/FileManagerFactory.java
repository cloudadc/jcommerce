/**
* Author: Bob Chen
*/

package com.jcommerce.core.io;

public class FileManagerFactory {
	private static IFileManager fileManager = null;
				
  public synchronized static IFileManager getFileManager() {
  	if (fileManager == null) {
    	String root = System.getProperty("JCOMMERCE_HOME");
    	System.out.println("File root:"+root);
    	fileManager = new LocalFileManager(root);
    }
    
    return fileManager;
  }
}
