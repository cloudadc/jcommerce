/**
* Author: Bob Chen
*/

package com.jcommerce.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IFile {    
    String getName();
    
    String getDirectory();
    
    boolean exists();
    
    long lastModified();
    
    long length();
    
    boolean isFile();
    
    byte[] readContent() throws IOException;
    
    void saveContent(byte[] bytes) throws IOException;
    
    InputStream getInputStream() throws IOException;
    
    OutputStream getOutputStream() throws IOException;
    
    IFile[] listFiles() throws IOException;
}
