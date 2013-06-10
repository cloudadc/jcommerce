/**
 * Author: Sun Yang
 */
package com.jcommerce.core.database;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.jcommerce.core.io.FileManagerFactory;
import com.jcommerce.core.io.IFile;

public class DataBaseInitialize {
	
	
	final String initializeFileName="jcommerce.sql";

	private Connection conn = null;
	
	private  String initializeFilePath=DatabaseRelatedPath.InitializeFilePath;
	
	private  String sequenceFilePath=DatabaseRelatedPath.TableSequenceFilePath;
	
	DataBaseConnection dbConn;
	
	/**
	 * Get connection
	 */
	public DataBaseInitialize() {
	    dbConn = new DataBaseConnection();
		conn = dbConn.getConnection();
	}

	/**
	 * Close the connection
	 * 
	 * @param conn
	 */
	public void close(Connection conn) throws SQLException{
		if (conn != null) {
			conn.close();
		}
	}
	
	
	/**
	 * Execute the sql statements.
	 * 
	 * @param sqlfile
	 * @param tableSequenceFile
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public void insert(InputStream isInit, InputStream isSeq) throws FileNotFoundException,UnsupportedEncodingException,SQLException, IOException{
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			Map<String, List<String>> insertStringMap = readFileToMap(isInit);
			
			List<String> tableNamesSequence = getTableSequenceFromFile(isSeq);
			
			int record=0;
			for (String tableName : tableNamesSequence) {
				if (insertStringMap.containsKey(tableName)) {
				    for (String sql : insertStringMap.get(tableName)) {
				    		record++;
				    		
				    		st.executeUpdate(sql);
				   
				    		if(record%1000==0){
			    				conn.commit();
			    			}
				    }
				  
				}
			}
			if(record%1000!=0){
				conn.commit();
			}
			

		} catch (FileNotFoundException e) {
			throw e;
		} catch (UnsupportedEncodingException e) {
			throw e;
		} catch (SQLException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}finally{
			close(conn);
		}
	}
	
	
	/**
	 * Read file based on line
	 */
	public List<String> readFile(InputStream is) throws FileNotFoundException,UnsupportedEncodingException,IOException{
		List<String> sqlStrings = null;
		InputStream inputStream = null;
		Reader input = null;
		BufferedReader bufferInput = null;
		String sqlString="";
		sqlStrings = new ArrayList<String>();

		//暂不关闭输入流，在被调用的方法initialize()中关闭
		inputStream = is;

		input = new InputStreamReader(inputStream, "UTF-8");

		bufferInput = new BufferedReader(input);		
					
			//String of One line 
		String lineString="";
			
		while((lineString=bufferInput.readLine())!=null){
				
			sqlString=sqlString+lineString;
			
			
			if(lineString.endsWith(");---insert---") || lineString.endsWith("---update---")){
				sqlStrings.add(sqlString.substring(0, sqlString.length()-12)); //将sql语句去除句尾的"---insert---"和"---update---"
				sqlString="";
			}
			
		}
//		bufferInput.close();
		
		return sqlStrings;
		
	}
	
	/**
	 * Read file which contains sentences separated by colon return
	 * Map<String,String>.
	 * 
	 */
	public Map<String, List<String>> readFileToMap(InputStream is) throws FileNotFoundException,UnsupportedEncodingException,IOException{
		
		List<String> insertStrings = readFile(is);
		List<String> sqls=null;
		Map<String, List<String>> insertStringMap = new HashMap<String, List<String>>();
		//把有相同表名的insert语句放到List<String> sqls中
		for (String insertString : insertStrings) {
			String tableName = getTableName(insertString);
			sqls = insertStringMap.get(tableName);
			if (sqls == null) {
			    sqls = new ArrayList<String>();
			    insertStringMap.put(tableName, sqls);
			}
			sqls.add(insertString);
			
		}
		return insertStringMap;
	}

	/**
	 *Get the applicable table sequence from the file named
	 * tableSequenceForRestore
	 * @throws IOException 
	 * 
	 */
	public List<String> getTableSequenceFromFile(InputStream is) throws IOException {
			List<String> tableNames = new ArrayList<String>();
			InputStream inputStream = is;
			InputStreamReader reader = new InputStreamReader(inputStream,"UTF-8");
			BufferedReader bufferReader = new BufferedReader(reader);
			String tableName = "";
			tableName = bufferReader.readLine();
			while (tableName != null) {
				tableName = tableName.trim();
				tableNames.add(tableName);
				tableName = bufferReader.readLine();
			}
			is.close();
			return tableNames;

	}

	/**
	 * Get the tableName from the String sql
	 * 
	 * @param sql
	 * @return
	 */
	public String getTableName(String sql) {
		String tableName = null;
		char insertStringChars[] = null;
		if (sql.length() < 100) {
			insertStringChars = sql.toCharArray();
		} else {
			insertStringChars = sql.substring(0, 101).toCharArray();
		}
        tableName = getTableName(insertStringChars);
		return tableName;
	}

	/**
	 * use to get table name this method is invoked by its overloaded function
	 * getTableName(String sql)
	 * 
	 * @param InsertStringChars
	 * @return
	 */
	public String getTableName(char[] InsertStringChars) {
		String tableName = "";
		int start = 0;
		int end = 0;
		for (int i = 0; i < InsertStringChars.length; i++) {
			if (start == 0 && InsertStringChars[i] == '`') {
				start = i;
			} else if (end == 0 && InsertStringChars[i] == '`') {
				end = i;
			} else if (start != 0 && end != 0) {
				break;
			}
		}
		for (int j = start + 1; j < end; j++) {

			tableName = tableName + InsertStringChars[j];
		}
		return tableName;
	}
	
	
	
	
	
	/**
	 * initialize the database
	 */
	public void initialize()throws FileNotFoundException,UnsupportedEncodingException,IOException,SQLException{
	        InputStream isInit = getClass().getResourceAsStream(initializeFilePath);
	        InputStream isSeq = getClass().getResourceAsStream(sequenceFilePath);
	        
			ZipInputStream zipis=new ZipInputStream(isInit);
			ZipEntry entry=null;
			while((entry=zipis.getNextEntry())!=null){
				if(!entry.getName().equals(initializeFileName)){
					uploadPictures(zipis,entry);
				}
				else{
					//执行ishopInitialize.sql文件中的sql语句
					insert(zipis,isSeq);
//					readFileToMap(zipis);
				}
		

			}    
			zipis.close();
			
	}
	
	
	/**
	 * 用来把Zip文件中的图片传到指定的系统文件夹内
	 * @param zipInput
	 * @param entry
	 * @throws IOException
	 */
	public void uploadPictures(ZipInputStream zipInput,ZipEntry entry) throws IOException{
		IFile file=null;
		BufferedInputStream imageInput=null;
		BufferedOutputStream imageOutput=null;
	
	

		if(entry.getName().startsWith("images/")
					&& !entry.getName().equals("images/")&&!entry.isDirectory()){

			file=FileManagerFactory.getFileManager().createFile(entry.getName());
			imageInput=new BufferedInputStream(zipInput);
			imageOutput=new BufferedOutputStream(file.getOutputStream());
			byte[] b=new byte[1024];
			int n=0;
 			while((n=imageInput.read(b))!=-1){
				imageOutput.write(b);
			}
				imageOutput.close();
		}	
		
		

	}
				
			
			
			
			
}