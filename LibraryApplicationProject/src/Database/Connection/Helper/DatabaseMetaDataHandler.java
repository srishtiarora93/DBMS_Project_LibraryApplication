/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.Connection.Helper;

import Database.Connection.Helper.Interfaces.IDatabaseConnection;
import Database.Connection.Helper.Interfaces.IDatabaseMetaDataHandler;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jordy
 */
class DatabaseMetaDataHandler implements IDatabaseMetaDataHandler{
    private DatabaseMetaData m_MetaData;
    
    public DatabaseMetaDataHandler(IDatabaseConnection connection) {
        if(connection == null) throw new IllegalArgumentException("connection is null");
        
        CreateDatabaseMetaData(connection);
    }
    
    @Override
    public void close() throws Exception {
        m_MetaData = null;
    }

    @Override
    public ArrayList<String> getColumnNames(String tableName) {
        ArrayList<String> columnNames = new ArrayList<>();
        try {
            ResultSet columns = m_MetaData.getColumns(null, 
                    m_MetaData.getUserName(), 
                    tableName.toUpperCase(), 
                    null);
            while(columns.next()){
                columnNames.add(columns.getString(4));
            }
        }
        catch (SQLException e){
            Logger.getLogger(DatabaseMetaDataHandler.class.getName()).log(Level.SEVERE, null, e);
        }
        return columnNames;
    }

    private void CreateDatabaseMetaData(IDatabaseConnection connection) {
        try {
            m_MetaData = connection.getDatabaseConnection().getMetaData();
        }
        catch (SQLException e){
            Logger.getLogger(DatabaseMetaDataHandler.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public ArrayList<String> getPrimaryKeyColumnNames(String tableName) {
        ArrayList<String> columnNames = new ArrayList<>();
        try {
            ResultSet columns = m_MetaData.getPrimaryKeys(null, 
                    m_MetaData.getUserName(), 
                    tableName.toUpperCase());
            while(columns.next()){
                columnNames.add(columns.getString(4));
            }
        }
        catch (SQLException e){
            Logger.getLogger(DatabaseMetaDataHandler.class.getName()).log(Level.SEVERE, null, e);
        }
        return columnNames;
    }
    
}
