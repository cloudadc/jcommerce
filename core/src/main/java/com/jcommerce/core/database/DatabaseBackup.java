package com.jcommerce.core.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import com.jcommerce.core.io.FileManagerFactory;
import com.jcommerce.core.io.IFile;

/**
 * Backup the data of database
 * @author Sun
 *
 */
public class DatabaseBackup {
	
	
	//表的主键的位置,排在表的第一位
	static final int primaryKeyLocation=1;

	private  String characterEncoding = "UTF-8";

	private  Connection conn = null;

	private  String TABLE_NAME = "TABLE_NAME";
	
	private  String BackupFilePath=DatabaseRelatedPath.BackupFilePath;
	
	
	public DatabaseBackup(String backUpFileName){
		BackupFilePath +="/" + backUpFileName;
	}
	
	public  void getConnection() {
		DataBaseConnection dataBaseConnection=new DataBaseConnection();
		conn = dataBaseConnection.getConnection();
	}
	
	public  void closeConnection()throws SQLException{
		try {
			conn.close();
		} catch (SQLException e) {
			throw e;
		}
	}
	
	public  void closeResultSet(ResultSet rs)throws SQLException{
		try{
			if(rs!=null){
				rs.close();
			}
		}catch(SQLException e){
			throw e;
		}
	}
	
	public  void closeStatement(Statement st)throws SQLException{
		try {
			if(st!=null){
				st.close();
			}
		} catch (SQLException e) {
			throw e;
		}
	
	}
	
	/**
	 * 
	 * Backup the database, and get the data from the database, generate the applicable string and write it to
	 * the file named backup.sql.
	 */
	public  void backup() throws SQLException, IOException{
		try {
			getConnection();
			// Get all the names of tables of database

			DatabaseMetaData dbmd = conn.getMetaData();
			ResultSet rs_tables = dbmd.getTables(null, null, null, null);
			Statement smt = conn.createStatement();
			String allInsertStrings=""; 
			String insertString="";
			String updateString="";
			while (rs_tables.next()) {

				String s = rs_tables.getString(TABLE_NAME);
				ResultSet rs_table = smt.executeQuery(getQueryString(s));
				
				insertString=getInsertString(s,rs_table);
				
				//如果是特殊表，则产生updateString
				if(isSpecialTable(s)){
					rs_table.beforeFirst(); //让rs_table重新回到第一条记录前
					updateString=getUpdateString(s,rs_table);
					allInsertStrings = allInsertStrings+insertString+updateString;
				}else{
					//不是特殊表，则不添加updateString
					allInsertStrings = allInsertStrings+insertString;
				}
				
				
				closeResultSet(rs_table);
				
			}
			closeResultSet(rs_tables);
			closeStatement(smt);
			WriteToFile(allInsertStrings);
		} catch (SQLException e) {
			throw e;
		} catch(IOException e){
			throw e;
		}finally{
			closeConnection();
		}
	}
	
	
	/**
	 * When backup the database, the applicable query string is needed to query the database to get the 
	 * corresponding resultSet.
	 * 
	 */
	public  String getQueryString(String table_name){
		String queryString="";
		if(table_name.equals("tis_region")){
			queryString="select * from tis_region order by cast(region_id as UNSIGNED) asc ";

		}else{
			queryString="select * from "+table_name;
		}
		return queryString;	
	}
	
	/**
	 * 
	 * When writing the applicable strings to the file named backup.sql, this method is used to 
	 * generate the the applicable strings. 
	 * 
	 * 
	 */
	public  String getInsertString(String table_name, ResultSet rs_table)throws SQLException {
		String insertString = "";
		String frontString="";
		String backString="";
		
		try {
			if (rs_table.next()) {
				rs_table.beforeFirst();

				// frontString is not changed. For example,"insert into "tis_region" () values()"
				frontString = "insert into `" + table_name + "` ( ";
				ResultSetMetaData rsmd = rs_table.getMetaData();	
				int columnCount = rsmd.getColumnCount();
				String colName="";
				for (int i = 1; i <= columnCount; i++) {
					colName = rsmd.getColumnName(i);
					if (i != columnCount) {
						frontString = frontString +"`"+ colName + "`,";
					} else {
						frontString = frontString + "`"+colName+ "`  ) values ";
					}
				}
				while (rs_table.next()) {
					//将backString重新置空
					backString="";
					backString = backString + "( ";
					String colValue = null;
					int colType = 0;
					for (int i = 1; i <= columnCount; i++) {

						colType = rsmd.getColumnType(i);
						colName = rsmd.getColumnName(i);
						colValue = rs_table.getString(i);
						// 如果不是最后一个字段
						if (i != columnCount) {
							if (colValue == null) {   
								backString = backString + "NULL" + ",";
							} else if (colType == Types.DOUBLE || colType == Types.FLOAT || colType == Types.INTEGER) {
								backString = backString + colValue + ",";
							} else {
								backString = backString + "'" + colValue+ "',";
							}
							
							
						} else {

							if (colValue == null) {
								backString = backString + "NULL";
							} else if (colType == Types.DOUBLE|| colType == Types.FLOAT|| colType == Types.INTEGER) {
								backString = backString + colValue;
							} else {
								backString = backString + "'" + colValue+ "'";
							}
							
						}

					}
					backString = backString + ");---insert---\n";
					insertString=insertString+frontString+backString;
				}
				
				insertString = insertString+"\n";
			}
		} catch (SQLException e) {
			throw e;
		}

		return insertString;
	}
	
	/**
	 *  判断是不是特殊表，例如表tis_region, 该表含有外键parent_id指向本表主键region_id
	 *  @param tableName 表名
	 */
	public Boolean isSpecialTable(String tableName){
		if(tableName.equals("tis_category")||tableName.equals("tis_group_goods")||tableName.equals("tis_users")
    			||tableName.equals("tis_comment")||tableName.equals("tis_feedback")
    			||tableName.equals("tis_admin_action")||tableName.equals("tis_order_info")
    			||tableName.equals("tis_order_goods")||tableName.equals("tis_article_cat")
    			||tableName.equals("tis_region")||tableName.equals("tis_shop_config")){
    		
			return true;
    	}else{
    		return false;
    	}
	}
	
	
	/**
	 * 得到特殊表的主键名
	 * @param tableName
	 * @return
	 */
	public String getPrimaryKeyNameForSpecialTable(String tableName){
		
		if(tableName.equals("tis_category")){
			return "cat_id";
		}else if(tableName.equals("tis_group_goods")){
			return "admin_id";
		}else if(tableName.equals("tis_users")){
			return "user_id";
		}else if(tableName.equals("tis_comment")){
			return "comment_id";
		}else if (tableName.equals("tis_feedback")){
			return "msg_id";
		}else if(tableName.equals("tis_admin_action")){
			return "action_id";
		}else if(tableName.equals("tis_order_info")){
			return "order_id";
		}else if(tableName.equals("tis_order_goods")){
			return "rec_id";
		}else if(tableName.equals("tis_article_cat")){
			return "cat_id";
		}else if(tableName.equals("tis_region")){
			return "region_id";
		}else if(tableName.equals("tis_shop_config")){
			return "id";
    	}
		return null;
	}
	
	public String getUpdateString(String tableName, ResultSet rs_table) throws SQLException{
		String updateString="";
		String OneRecordUpdateString=""; //一条记录
		if (rs_table.next()) {//判断表是否有记录
			rs_table.beforeFirst();//将指针重新回到第一条记录前
			ResultSetMetaData rsmd = rs_table.getMetaData();	
			int columnCount = rsmd.getColumnCount();
			updateString="";
			//得到主键名，主键类型
			String primaryKeyName = rsmd.getColumnName(primaryKeyLocation);
			int primaryKeyType = rsmd.getColumnType(primaryKeyLocation);
			while (rs_table.next()) {
				String colName = "";
				String colValue = "";
				//得到主键值
				String primaryKeyValue = rs_table.getString(primaryKeyLocation);
				
				int colType = 0;
				for (int i = 1; i <= columnCount; i++) {

					colType = rsmd.getColumnType(i);
					colName = rsmd.getColumnName(i);
					colValue = rs_table.getString(i);
				
				
				//{ 获得parent_id值组成部分语句
				if(colName.equals("parent_id")&& colValue == null){
					OneRecordUpdateString = "update "+tableName+" set parent_id = NULL ";
					break;
				}else if(colName.equals("parent_id") && colValue !=null){
					if(colType == Types.DOUBLE || colType == Types.FLOAT || colType == Types.INTEGER){
						OneRecordUpdateString = "update "+tableName+" set parent_id = " + colValue +" ";
						break;
					}else{
						OneRecordUpdateString = "update "+tableName+" set parent_id = '"+ colValue + "'" +" ";
						break;
					}
					
				}
				
			}//for结束
				
				//使用primaryKeyValue值组成全部语句
				if(primaryKeyType == Types.DOUBLE || primaryKeyType == Types.FLOAT || primaryKeyType == Types.INTEGER){
					OneRecordUpdateString = OneRecordUpdateString + " where " + primaryKeyName + " = " +  primaryKeyValue +";---update---\n";
				}else{
					OneRecordUpdateString = OneRecordUpdateString + " where "+ primaryKeyName + "= '"+ primaryKeyValue + "'" +";---update---\n";
				}
				
				
				//添加每一条记录
				updateString = updateString + OneRecordUpdateString;
				//}					
			} //while结束
			updateString = updateString + "\n";
		}
		return updateString;
		
	}
	
	
	/**
	 * Backup the data of the database to a file
	 * 
	 * 
	 */
	public  IFile WriteToFile(String s) throws IOException{
		System.out.println(BackupFilePath);
		IFile file = FileManagerFactory.getFileManager().createFile(BackupFilePath);
		//保证文件里的字符的编码为UTF-8
	    file.saveContent(s.getBytes("UTF-8"));
		return file;
	}
}