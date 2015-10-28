/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library.Application.Project.UserAuthentication;

import Database.Connection.Helper.DatabaseConnectionService;
import Database.Connection.Helper.Interfaces.*;

/**
 *
 * @author jordy
 */
class UserAuthenticationHelper {
    
        
    static boolean IsUserStudent(String userId) throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){           
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
            String selectUser = String.format("SELECT * FROM Students " +
                    "WHERE studentNo= '%s'", userId);
                try (IQueryResultSet resultSet = stmtExecutor.executeQuery(selectUser)){
                    return !resultSet.isEmpty();
                }
            }
        }
    }

    static boolean IsUserFaculty(String userId) throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){           
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
            String selectUser = String.format("SELECT * FROM Faculty " +
                    "WHERE facultyNo= %s", userId);
                try (IQueryResultSet resultSet = stmtExecutor.executeQuery(selectUser)){
                    return !resultSet.isEmpty();
                }
            }
        }
    }


    
}
