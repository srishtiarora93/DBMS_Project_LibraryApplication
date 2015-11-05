/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library.Application.Project.Dues;

import Database.Connection.Helper.DatabaseConnectionService;
import Database.Connection.Helper.Interfaces.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jordy
 */
public class DuesHelper {
    
    private static final String m_TableName = "DUES";
    private static final String m_UserId = "USER_ID";
    private static final String m_Amount = "AMOUNT";
    private static final String m_ResourceId = "RESOURCEID";
    private static final String m_DueDate = "DUEDATE";
    private static final String m_IsPaid = "ISPAID";
    private static final String m_PaidDate = "PAIDDATE";

    static void AddDuesToTable(String userId, DefaultTableModel model) 
    throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String userDues = String.format("SELECT * FROM %s "
                        + "WHERE %s = '%s'", m_TableName, m_UserId, userId);
                try (IQueryResultSet resultSet = stmtExecutor.executeQuery(userDues)){
                    Integer totalDues = 0;
                    while (resultSet.getResultSet().next()){
                        if (resultSet.getString(m_IsPaid).equals("Y")){
                            model.addRow(new String[] {
                                resultSet.getString(m_ResourceId),
                                resultSet.getString(m_DueDate),
                                resultSet.getString(m_Amount),
                                "Paid",
                                resultSet.getString(m_PaidDate),
                                ""
                            });
                        }
                        else {
                            int amount = Integer.parseInt(resultSet.getString(m_Amount));
                            totalDues = totalDues + amount;
                            model.addRow(new String[] {
                                resultSet.getString(m_ResourceId),
                                resultSet.getString(m_DueDate),
                                resultSet.getString(m_Amount),
                                "Not Paid",
                                "",
                                ""
                            });
                        }
                    }
                    model.addRow(new String[] {
                                "",
                                "",
                                "",
                                "",
                                "",
                                totalDues.toString()
                    });
                }
            }
        }
        
    }

    static void UpdatePaymentofDues(String userId) 
    throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String currentDateTime = simpleDateFormat.format(currentDate);
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String userDues = String.format("UPDATE %s "
                        + "SET %s = 'Y',"
                        + "%s = TO_DATE('%s', 'YYYY/MM/DD HH24:MI:SS') "
                        + "WHERE %s = '%s' AND "
                        + "%s = 'N'", m_TableName, 
                        m_IsPaid, m_PaidDate, currentDateTime,
                        m_UserId, userId,
                        m_IsPaid);
                stmtExecutor.executeUpdate(userDues);
            }
        }
    }

    static void RemoveHoldsOnUser(String userId) 
    throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){           
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
            String deleteUser = String.format("DELETE FROM HOLDUSER " +
                    "WHERE USERID = '%s'", userId);
            stmtExecutor.executeUpdate(deleteUser);
            }
        }
    }
    
}
