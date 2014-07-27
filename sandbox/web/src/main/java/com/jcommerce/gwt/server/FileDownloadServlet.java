package com.jcommerce.gwt.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class FileDownloadServlet extends HttpServlet {

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
		resp.setContentType("application/x-msdownload");// x-msdownload
		resp.setHeader("Content-disposition", "attachment; filename="
				+ req.getParameter("fileName"));
		write(req, resp);
	}

	private void write(HttpServletRequest req, HttpServletResponse resp) {
		OutputStream oup = null;
		FileInputStream fis = null;
		try {
			oup = resp.getOutputStream();
			fis = new FileInputStream(
					".."
							+ File.separator + req.getParameter("fileName"));
			int buf = -1;
			while ((buf = fis.read()) != -1) {
				oup.write(buf);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(oup!=null)
					oup.flush();
				if(fis!=null)
					fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
