/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.Connection.Helper;

import Database.Connection.Helper.Interfaces.IHashService;
import Database.Connection.Helper.Interfaces.*;

/**
 *
 * @author jordy
 */
public class DatabaseConnectionService {
    public static IDatabaseConnection createDatabaseConnection(){
        return new DatabaseConnection();
    }
    
    public static IStatementExecutor createStatementExecutor(IDatabaseConnection connection){
        return new StatementExecutor(connection);
    }

    public static IHashService createHashService(){
        return new HashService();
    }
    
    public static IDatabaseMetaDataHandler createDatabaseMetaDataHandler(IDatabaseConnection connection){
        return new DatabaseMetaDataHandler(connection);
    }
    
    public static ICallableStatement CreateCallableStmt(IDatabaseConnection connection, String jobquery) {
        return new CallableStmt(connection, jobquery);
    }
}
