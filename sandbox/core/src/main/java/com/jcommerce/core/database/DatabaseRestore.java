/**
 * @author Sun Yang
 */
package com.jcommerce.core.database;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jcommerce.core.io.FileManagerFactory;
import com.jcommerce.core.io.IFile;

/**
 **It is used to restore database.
 */
public class DatabaseRestore {

    private Connection conn = null;

    private String retoreFilePath = DatabaseRelatedPath.BackupFilePath;

    private String sequenceFilePath = DatabaseRelatedPath.TableSequenceFilePath;


    public void getConnection() {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        conn = dataBaseConnection.getConnection();
    }

    public void closeConnection() throws SQLException {
        try {
            conn.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    public void closeStatement(Statement st) throws SQLException {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            throw e;
        }

    }
    
    public void closeInputStream(InputStream input) throws IOException{
    	if(input!=null){
    		input.close();
    	}
    		
    }
    
    /**
     *Get the table sequence from the file tableSequence.txt
     *@param  filePath  the path of file tableSequence.txt
     *@throws IOException  If the file is not exist, then throw IOException
     * 
     */
    public List<String> getTableSequenceFromFile(String filePath) throws IOException {
        InputStream is = getClass().getResourceAsStream(filePath);

        DataBaseInitialize dbInit = new DataBaseInitialize();

        List<String> tableNames = dbInit.getTableSequenceFromFile(is);

        List<String> tableNamesOpposite = new ArrayList<String>();

        for (int i = tableNames.size() - 1; i >= 0; i--) {
            tableNamesOpposite.add(tableNames.get(i));
        }

        return tableNamesOpposite;
    }

    /**
     * This function is used to delete all the data of all tables.
     * It invoke its overloaded function.
     * @throws IOException 
     * @throws SQLException 
     */
    public void deleteAllData() throws IOException, SQLException {
        List<String> tableNames = getTableSequenceFromFile(sequenceFilePath);
        deleteAllData(tableNames);
    }

    /**
     * This function is invoked by its overloaded function.It is used to delete all the data of tables.
     * @param tableNames which contains all the table names which are in correct sequence.
     * @throws SQLException 
     */
    public void deleteAllData(List<String> tableNames) throws SQLException {
        try {
			getConnection();
			Statement smt;
			conn.setAutoCommit(false);
			smt = conn.createStatement();
			for (String tableName : tableNames) {

				// If the table has the foreign key parent_id
				setTablesParentIDToNull(tableName, smt);
				
				//delete the data of tables
				String queryString = "truncate table " + tableName;
				smt.executeUpdate(queryString);

			}
			//After delete all the data, commit the transaction
			conn.commit();
			closeStatement(smt);
			
		} catch (SQLException e) {
			throw e;
		} finally{
			closeConnection();
		}

     

    }
    
    /**
     * Used to set column parent_id to null special tables like tis_region table, 
     * which has a foreign key parent_id to reference region_id.
     * @param tableName
     * @param stmt
     */
    public void setTablesParentIDToNull(String tableName, Statement stmt) throws SQLException{
    	if(tableName.equals("tis_category")||tableName.equals("tis_group_goods")||tableName.equals("tis_users")
    			||tableName.equals("tis_comment")||tableName.equals("tis_feedback")
    			||tableName.equals("tis_admin_action")||tableName.equals("tis_order_info")
    			||tableName.equals("tis_order_goods")||tableName.equals("tis_article_cat")
    			||tableName.equals("tis_region")||tableName.equals("tis_shop_config")){
    		
    		String sql="update "+tableName+" set parent_id=null"; 		
    		stmt.executeUpdate(sql);
    	}
    	  	
    }
    
    
    
    /**
     * restore database
     * 
     * @param fileName The file was backuped by user before. Now the user use it to restore databbase. 
     */
    public void restore(String fileName) throws UnsupportedEncodingException, IOException, SQLException {
       
			DataBaseInitialize dbInit = new DataBaseInitialize();
			String filePath = retoreFilePath + "/" + fileName;
			//删除数据库中所有数据
			deleteAllData();
			IFile file=FileManagerFactory.getFileManager().getFile(filePath);
			InputStream isInit = file.getInputStream();
			InputStream isSeq = getClass().getResourceAsStream(sequenceFilePath);
			//给数据库重新插入数据
			dbInit.insert(isInit, isSeq);
			//关闭insert方法中未关闭的输入流isInit
			closeInputStream(isInit);
		
    }


   
}
