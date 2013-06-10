/**
* Author: Bob Chen
*/

package com.jcommerce.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LocalFile implements IFile {
    private String path;
    private String root;
    private File file;
    
    public LocalFile(String root, String path) {
        this.root = root;
        this.path = path;
        file = getFile();
    }
    
    public LocalFile(String root, String dir, String name) {
        this.root = root;
        this.path = dir + "/" + name;
        file = getFile();
    }
    
    public String getDirectory() {
        if (path.contains("/")) {
            return path.substring(0, path.lastIndexOf("/"));
        }
        return null;
    }

    public String getName() {
        if (path.contains("/")) {
            return path.substring(path.lastIndexOf("/") + 1);
        }
        return path;
    }

    public byte[] readContent() throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] b = new byte[fis.available()];
        fis.read(b);
        fis.close();
        return b;
    }

    public void saveContent(byte[] bytes) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);
        fos.close();
    }
    
    private String getFullPath() {
        String fullPath = root;
        if (!fullPath.endsWith("/")) {
            fullPath += "/";
        }
        fullPath += path;
        return fullPath;
    }

    private File getFile() {
        return new File(getFullPath());
    }
    
    public boolean exists() {
        return file.exists();
    }

    public InputStream getInputStream() throws IOException{
        return new FileInputStream(file);
    }

    public OutputStream getOutputStream() throws IOException{
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        return new FileOutputStream(file);
    }

    public IFile[] listFiles() throws IOException {
        if (!file.exists() || !file.isDirectory()) {
            return null;
        }
        
        File[] files = file.listFiles();
        IFile[] _files = new IFile[files.length];
        
        if (_files == null) {
            return null;
        }
        
        int i = 0;
        for (File f : files) {            
            _files[i++] = new LocalFile(root, path + "/" + f.getName());  
        }
        
        return _files;
    }

    public boolean isFile() {
        return file.isFile();
    }

    public long lastModified() {
        return file.lastModified();
    }

    public long length() {
        return file.length();
    }
}
