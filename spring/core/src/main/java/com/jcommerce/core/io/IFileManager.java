/**
* Author: Bob Chen
*/

package com.jcommerce.core.io;

import java.io.IOException;

public interface IFileManager {
    IFile getFile(String path);
    
    IFile[] getFiles(String dir);
    
    IFile createFile(String path) throws IOException;
    
    void deleteFile(String path);
}
