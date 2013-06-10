package com.jcommerce.core.database;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class test {
	private  Connection conn = null;
	
	
	public  void getConnection() {
		DataBaseConnection dataBaseConnection=new DataBaseConnection();

		conn = dataBaseConnection.getConnection();
	}
	
	private  final String TABLE_NAME = "TABLE_NAME";

	public List<String> getTableNamesTest(){
		getConnection();
		List<String> tableNames=new ArrayList<String>();
		try {
			DatabaseMetaData dbmd = conn.getMetaData();
			ResultSet rs_tables = dbmd.getTables(null, null, null, null);
			while(rs_tables.next()){
				String s = rs_tables.getString(TABLE_NAME);
				tableNames.add(s);
			}
			conn.close();
			return tableNames;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<String> getTableNamesFileTest(){
		try {
			String sequenceFilePath="D:/MyWorkSpace/jcommerce/core/src/com/jcommerce/core/database/tableSequenceForRestore.txt";
			List<String> tableNames = new ArrayList<String>();
			InputStream inputStream = new FileInputStream(sequenceFilePath);
			InputStreamReader reader = new InputStreamReader(inputStream,
					"UTF-8");
			BufferedReader bufferReader = new BufferedReader(reader);
			String tableName = "";
			tableName = bufferReader.readLine();
			while (tableName != null) {
				tableName = tableName.trim();
				tableNames.add(tableName);
				tableName = bufferReader.readLine();
			}
			return tableNames;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	
	public static void testZip(){
		
		File f = new File("D:/MyWorkSpace/jcommerce/core/src/com/jcommerce/core/database/DatabaseInitialize.zip");		
		try {
			ZipInputStream zipis=new ZipInputStream(new FileInputStream(f));
			ZipEntry entry=null;
			while((entry=zipis.getNextEntry())!=null){
				/*if(entry.getName().equals("DatabaseInitialize/ishopInitialize.sql")){
					InputStreamReader ir=new InputStreamReader(zipis);
					BufferedReader br=new BufferedReader(ir);
					String lineString="";
					while((lineString=br.readLine())!=null){
						System.out.println(lineString);
					}
				}*/
//				System.out.println(entry.getName());
				
				
				
				if(entry.getName().startsWith("DatabaseInitialize/images/")
						&& !entry.getName().equals("DatabaseInitialize/images/")&&entry.isDirectory()){
					
					System.out.println(getDirectoyName(entry.getName()));
				}
				if(entry.getName().startsWith("DatabaseInitialize/images/")
						&& !entry.getName().equals("DatabaseInitialize/images/")&&!entry.isDirectory()){
					System.out.println(getFileName(entry.getName()));
				}
				
			/*	if(entry.getName().equals("DatabaseInitialize/images/")){
					BufferedInputStream imageInput=new BufferedInputStream(zipis);
					File outf=new File("D:/MyWorkSpace/images");
					BufferedOutputStream imageOutput=new BufferedOutputStream(new FileOutputStream(outf));
					byte[] b=new byte[1024];
					int n=0;
					while((n=imageInput.read(b))!=-1){
						imageOutput.write(b);
					}
					
				}*/
			}
//			ZipFile zip=new ZipFile(f);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String getDirectoyName2(String entryName){
		
		int j=0;
		int k=0;
		//得到倒数第二个出现的"/"的索引
		System.out.println(entryName.lastIndexOf("/"));
		while((j=entryName.indexOf("/",j+1))<entryName.lastIndexOf("/")){
			k=j;
		}
		
		return entryName.substring(k+1,j);
		
	}
	
	
	//得到"DatabaseInitialize/images/"之后的目录路径
	public static String getDirectoyName(String entryName){
		int fromIndex="DatabaseInitialize/images/".length();
		return entryName.substring(fromIndex,entryName.lastIndexOf("/"));
	}
	
	public static String getFileName(String entryName){
		int fromIndex="DatabaseInitialize/images/".length();
		return entryName.substring(fromIndex,entryName.length());
	}
	
	
	
	public static void main(String args[]){
		
//	testZip();
		DataBaseInitialize init=new DataBaseInitialize();
		try {
			init.initialize();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
/*		test t=new test();
		List<String> tableNames=t.getTableNamesTest();
		List<String> tableNamesFile=t.getTableNamesFileTest();
		int flag=0;
		for(String tableName:tableNames){
			flag=0;
			for(String tableNameFile:tableNamesFile){
				
				if(tableName.equals(tableNameFile)){
					flag=1;
					break;
				}
				
			}
			if(flag==0){
				System.out.println(tableName);
			}	
		}*/
/*		DataBaseInitialize dbInit=new DataBaseInitialize();
		File sqlFile = new File("D:/jcommerce/core/src/com/jcommerce/core/database/ishopInitialize.sql");
//		dbInit.readFile2(sqlFile);
		dbInit.initialize();*/
		
		
	}
	
}
