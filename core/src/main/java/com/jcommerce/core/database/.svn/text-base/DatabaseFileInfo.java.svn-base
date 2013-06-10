package com.jcommerce.core.database;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jcommerce.core.io.FileManagerFactory;
import com.jcommerce.core.io.IFile;

public class DatabaseFileInfo {

	private String BackupFilePath = DatabaseRelatedPath.BackupFilePath;

	/**
	 * Get information of all files
	 * 
	 * @return
	 */
	public List<List<String>> getAllFileInfo()throws ParseException{
		List<List<String>> FilesInfo=new ArrayList<List<String>>();
		IFile directory = FileManagerFactory.getFileManager().getFile(BackupFilePath);
		
        try {
            IFile[] files = directory.listFiles();
            if (files != null) {
        		for(IFile file : files){
        			if(file.isFile()){
        				FilesInfo.add(getFileInfo(file));
        			}			
        		}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

		return FilesInfo;
	}

	/**
	 * determine if has the same backup file name
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException 
	 */
	public Boolean ifHasSameFile(String fileName) throws IOException {
		IFile directory = FileManagerFactory.getFileManager().getFile(BackupFilePath);

		IFile[] files = directory.listFiles();
		for(IFile file:files){
			if(file.isFile()==true){
				String backupFileName=file.getName();
				if(backupFileName.endsWith(".sql")){
					if(backupFileName.equals(fileName)){
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * get basic information of a file
	 * 
	 * @param file
	 * @return
	 * @throws
	 */
	public List<String> getFileInfo(IFile file) throws ParseException {

		List<String> fileInfo = new ArrayList<String>();
		String fileName = file.getName();
	
		long time=file.lastModified();
		Date createDate=new Date(time);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
		String createDateStr=df.format(createDate);
		
		fileInfo.add(fileName);
		fileInfo.add(String.valueOf(file.length() / 1024) + "k");
		fileInfo.add(createDateStr);
		return fileInfo;
	}

	
	/**
	 *delete one file
	 */
	public void deleteFile(String fileName) {
		String filePath = BackupFilePath + "/" + fileName;
		FileManagerFactory.getFileManager().deleteFile(filePath);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
