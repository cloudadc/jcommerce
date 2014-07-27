package com.jcommerce.gwt.server;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.fileupload.FileItem;

import com.jcommerce.core.io.DisposePictures;
import com.jcommerce.core.io.FileManagerFactory;
import com.jcommerce.core.io.IFile;

public class StoreOrderType {

	public String StoreFile(String storeType, FileItem item, String today){
		String name = null;
		if("thumb".equals(storeType)){
			name = storeFiles2Thumb(item, today);
		}else if("img_thumb".equals(storeType)){
			name = storeFiles2Img_thumb(item, today);
		}else if("img".equals(storeType)){
			name = storeFiles(item, today);
		}else if("csv".equals(storeType)){
			name = storeFiles(item, today);
		}else{
			System.out.println("-The storeType is not correct!(StoreOrderType)-");
		}
		return name;
	}

	private String storeFiles(FileItem item, String today) {
		if (item.isFormField() == false) {
			// 或直接保存成文件
			// String name = item.getName();
			// if (name.contains("\\")) {
			// name = name.substring(name.lastIndexOf("\\") + 1);
			// } else if (name.contains("/")) {
			// name = name.substring(name.lastIndexOf("/") + 1);
			// }
			String name = "images/" + today + "/" + getFileName(item);
//					+ UUID.randomUUID().toString();
            try {
    			IFile file = FileManagerFactory.getFileManager().createFile(name);
    			System.out.println("!!!!!!!!!!!!!!!!!!" + name);
				// 直接保存文件
			    file.saveContent(item.get());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return name;
		}
		return null;
	}

	private String storeFiles2Img_thumb(FileItem item, String today) {
		if (item.isFormField() == false) { 
        	String randomName_img = getFileName(item);
        	String randomName_thumb = "thumb_" + randomName_img;
            String name_img = "images/"+today+"/"+ randomName_img;
            String name_thumb = "images/"+today+"/thumb/"+ randomName_thumb;
            
            byte[] content = item.get();
            try {
                IFile file = FileManagerFactory.getFileManager().createFile(name_img);
                // 直接保存文件
                file.saveContent(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
			try {
				BufferedImage image_thumb = ImageIO.read(new ByteArrayInputStream(content));
				DisposePictures dp_thumb = new DisposePictures();
				image_thumb = dp_thumb.resize(image_thumb, 100, 100);
				ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
				ImageIO.write(image_thumb, name_img.substring(name_img.lastIndexOf(".")+1), baos);
				
                IFile file = FileManagerFactory.getFileManager().createFile(name_thumb);
                // 直接保存文件
                file.saveContent(baos.toByteArray());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return name_img;
        }
		return null;
	}

	private String storeFiles2Thumb(FileItem item, String today) {
		if (item.isFormField() == false) { 
        	String randomName = "thumb_" + getFileName(item);
            String name = "images/"+today+"/thumb/"+ randomName;
            try {
                IFile file = FileManagerFactory.getFileManager().createFile(name);
                // 直接保存文件
                file.saveContent(item.get());
			} catch (IOException e) {
				e.printStackTrace();
			}
            return name;
        }
		return null;
	}
	
	private String getFileName(FileItem item) {
	    String f = item.getName();
	    f = f.replace('\\', '/');
	    String s = f.substring(f.lastIndexOf('/')+1);
	    if (s.indexOf(".") > 0) {
	        return UUID.randomUUID().toString() + s.substring(s.lastIndexOf("."));
	    }
	    return UUID.randomUUID().toString();
	}
}
