/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.Connection.Helper;

import Database.Connection.Helper.Interfaces.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;

/**
 *
 * @author jordy
 */
class StatementExecutor implements IStatementExecutor{
    
    private Statement m_statement;
    
    public StatementExecutor(IDatabaseConnection connection){
        if(connection == null) throw new IllegalArgumentException("connection is null");
        
        createStatement(connection);
    }
    
    private void createStatement(IDatabaseConnection connection) {
        try{
            m_statement = connection.getDatabaseConnection().createStatement(
            ResultSet.TYPE_SCROLL_SENSITIVE,
            ResultSet.CONCUR_UPDATABLE);
        }
        catch (SQLException e){
            Logger.getLogger(StatementExecutor.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void close() throws Exception {
        try{
            if(m_statement != null){
                m_statement.close();
                m_statement = null;
            }
        }
        catch (SQLException e){
            Logger.getLogger(StatementExecutor.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void executeUpdate(String sqlStatement) {
        try{
            m_statement.executeUpdate(sqlStatement);
        }
        catch (SQLException e){
            Logger.getLogger(StatementExecutor.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public IQueryResultSet executeQuery(String sqlQuery) {
        try{
            return new QueryResultSet(m_statement.executeQuery(sqlQuery));
        }
        catch (SQLException e){
            Logger.getLogger(StatementExecutor.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
