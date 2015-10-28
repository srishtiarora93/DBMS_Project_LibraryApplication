/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.Connection.Helper;

import Database.Connection.Helper.Interfaces.IDatabaseConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jordy
 */
class DatabaseConnection implements IDatabaseConnection{
    
    private Connection m_Connection = null;
    static final String s_JdbcURL 
	= "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";
    static final String s_OracleDriver
        = "oracle.jdbc.driver.OracleDriver";
    static final String s_User = "jjose";
    static final String s_Password = "200104370";
    
    @Override
    public void close() throws Exception {
        try{
            if(m_Connection != null){
                m_Connection.close();
                m_Connection = null;
            }
        }
        catch (SQLException e){
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public Connection getDatabaseConnection() {
        if(m_Connection == null){
            m_Connection = connectToDatabase();
        }
        return m_Connection;
    }
    
    private Connection connectToDatabase() {
        try{
            Class.forName(s_OracleDriver);
            return DriverManager.getConnection(s_JdbcURL, s_User, s_Password);
        }
        catch (SQLException e){
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        catch (ClassNotFoundException e){
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
