/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library.Application.Project.Publications;

import Database.Connection.Helper.DatabaseConnectionService;
import Database.Connection.Helper.Interfaces.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jordy
 */
public class PublicationsHelper {
    public static void AddBooksToTable(String bookIdColName, 
            String titleColName, 
            String tableName,
            PublicationType publicationType,
            DefaultTableModel model) throws Exception {
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String ebooks = String.format("SELECT %s, %s FROM %s", bookIdColName, titleColName, tableName);
                try (IQueryResultSet resultSet = stmtExecutor.executeQuery(ebooks)){
                    while (resultSet.getResultSet().next()) {
                        model.addRow(new String[] {resultSet.getString(bookIdColName), 
                            publicationType.toString(), 
                            resultSet.getString(titleColName)});
                    }
                }
            }
        }
    }
}
