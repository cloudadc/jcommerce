/**
* Author: Bob Chen
*/

package com.jcommerce.core.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalFileManager implements IFileManager {
    private String root;
    
    public LocalFileManager(String root) {
        this.root = root;
    }

    public IFile createFile(String path) throws IOException {
        String fullPath = getFullPath(path);
        System.out.println("path:"+path+" fullPath:"+fullPath);
        File file = new File(fullPath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        file.createNewFile();
        IFile _file = new LocalFile(root, path);
        return _file;
    }

    public void deleteFile(String path) {
        String fullPath = getFullPath(path);
        File file = new File(fullPath);
        file.delete();
    }

    public IFile getFile(String path) {
        IFile file = new LocalFile(root, path);
        return file;
    }

    public IFile[] getFiles(String dir) {
        String fullPath = getFullPath(dir);
        File file = new File(fullPath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null || files.length ==0) {
                return null;
            }
            
            List<IFile> ret = new ArrayList<IFile>();
            for(File f : files) {
                if (f.isFile()) {
                    String path = f.getPath().substring(root.length());
                    path = path.replace('\\', '/');
                    if (path.startsWith("/")) {
                        path = path.substring(1);
                    }
                    IFile _f = new LocalFile(root, path);
                    ret.add(_f);
                }
            }
            
            return ret.toArray(new IFile[0]);
        }
        return null;
    }

    private String getFullPath(String path) {
        if (path == null) {
            return root;
        }
        
        String fullPath = root;
        if (!fullPath.endsWith("/")) {
            fullPath += "/";
        }
        fullPath += path;
        return fullPath;
    }
}
