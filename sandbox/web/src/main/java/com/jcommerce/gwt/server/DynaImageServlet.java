package com.jcommerce.gwt.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jcommerce.core.io.FileManagerFactory;
import com.jcommerce.core.io.IFile;
import com.jcommerce.core.io.IFileManager;

public class DynaImageServlet extends HttpServlet {
//    private static final Logger log = Logger.getLogger(DynaImageServlet.class.getName());

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("fileName");
        writeImage(response, fileName);
    }

    private void writeImage(HttpServletResponse response, String fileName) throws IOException {
        System.out.println("");
        String type = fileName.substring(fileName.lastIndexOf(".") + 1);
        response.setHeader("Content-Type", "image/" + type);

        IFileManager fileManager = FileManagerFactory.getFileManager();
        IFile file = fileManager.getFile(fileName);

        if (!file.exists()) {
            InputStream is = this.getClass().getResourceAsStream("noPicture.gif");
            int leng = is.available();
            BufferedInputStream buff = new BufferedInputStream(is);
            byte[] mapObj = new byte[leng];
            buff.read(mapObj, 0, leng);
            response.getOutputStream().write(mapObj);
        } else {
            byte[] content = file.readContent();
            response.getOutputStream().write(content);
        }
    }
}
