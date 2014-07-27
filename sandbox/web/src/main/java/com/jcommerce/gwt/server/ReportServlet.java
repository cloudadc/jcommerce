package com.jcommerce.gwt.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jcommerce.core.io.FileManagerFactory;

public class ReportServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2055943540120089839L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		resp.setContentType("application/x-msdownload");// x-msdownload
//		resp.setHeader("Content-disposition", "attachment; filename="
//				+ req.getParameter("fileName"));
	    String fileName = req.getPathInfo();

	    if (fileName.startsWith("/com.jcommerce.gwt.iShop/reportService")) {
	        fileName = fileName.substring("/com.jcommerce.gwt.iShop/reportService".length());
	    }
	    
	    System.out.println("fileName:"+fileName);
	    
		OutputStream oup = null;
		InputStream fis = null;
		try {
			oup = resp.getOutputStream();
			fis = FileManagerFactory.getFileManager().getFile(fileName).getInputStream();
			byte[] buf = new byte[1024];
			int len;
			while ((len = fis.read(buf)) != -1) {
				oup.write(buf, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
                if(fis!=null)
                    fis.close();
				if(oup!=null)
					oup.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
