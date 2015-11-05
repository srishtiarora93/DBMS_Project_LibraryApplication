/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.Connection.Helper;

import Database.Connection.Helper.Interfaces.ICallableStatement;
import Database.Connection.Helper.Interfaces.IDatabaseConnection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Prishu
 */
public class CallableStmt implements ICallableStatement{

    private static CallableStatement m_CallableStatement;
    public CallableStmt(IDatabaseConnection connection, String plSql) {
        if(connection == null) throw new IllegalArgumentException("connection is null");
        CreateCallableStmt(connection, plSql);
    }

    @Override
    public boolean Execute() {
        try{
            return m_CallableStatement.execute();
        }
        catch (SQLException e){
            Logger.getLogger(CallableStmt.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    @Override
    public void close() throws Exception {
        try{
            if(m_CallableStatement != null){
                m_CallableStatement.close();
                m_CallableStatement = null;
            }
        }
        catch (SQLException e){
            Logger.getLogger(CallableStmt.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void CreateCallableStmt(IDatabaseConnection connection, String jobQuery) {
        try{
            m_CallableStatement = connection.getDatabaseConnection().prepareCall(jobQuery);
        }
        catch (SQLException e){
            Logger.getLogger(CallableStmt.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
    
