/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library.Application.Project.CheckedoutResources;

import Database.Connection.Helper.DatabaseConnectionService;
import Database.Connection.Helper.Interfaces.*;
import Library.Application.Project.Publications.PublicationType;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jordy
 */
public class CheckedoutResourcesHelper {
    
    private static final String m_LibrariesTable = "LIBRARIES";
    private static final String m_LibrariesIdCol = "LIBRARYID";
    private static final String m_LibrariesNameCol = "NAME";


    static void AddBooksToTable(String bookIdCol, String useridCol, String userId,
            String tableName, String rdateCol, PublicationType publicationType, 
            DefaultTableModel model) throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String books = String.format("SELECT * from %s "
                        + "WHERE %s = '%s' AND "
                        + "%s is NULL", tableName, 
                        useridCol, userId,
                        rdateCol);
                try (IQueryResultSet resultSet = stmtExecutor.executeQuery(books)){
                    while (resultSet.getResultSet().next()) {
                        model.addRow(new String[] {resultSet.getString(bookIdCol), 
                            publicationType.toString()});
                    }
                }
            }
        }
    }

    static void AddCheckedoutRoomsToTable(String tableName,
            String userIdCol, String userId,
            String checkoutDateCol, String checkinDateCol,
            String isVoidCol, DefaultTableModel model, String roomNoCol,
            String startDateCol) 
    throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String rooms = String.format("SELECT * FROM %s "
                        + "WHERE %s = '%s' AND "
                        + "%s IS NOT NULL AND "
                        + "%s IS NULL AND "
                        + "%s = 'N'", tableName,
                        userIdCol, userId,
                        checkoutDateCol, checkinDateCol,
                        isVoidCol);
                try (IQueryResultSet resultSet = stmtExecutor.executeQuery(rooms)){
                    while (resultSet.getResultSet().next()) {
                        model.addRow(new String[] {resultSet.getString(roomNoCol), 
                            getLibraryName(connection, resultSet.getString(m_LibrariesIdCol)),
                            resultSet.getString(startDateCol)
                        });
                    }
                }
            }
        }
    }
    
    private static String getLibraryName(IDatabaseConnection connection, 
            String libId) {
        String libName = "";
        try{
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String library = String.format("SELECT * FROM %s "
                + "WHERE %s = '%s'", m_LibrariesTable, m_LibrariesIdCol, libId);
                try (IQueryResultSet result = stmtExecutor.executeQuery(library)){
                    result.moveToFirstRow();
                    libName = result.getString(m_LibrariesNameCol);
                }
            }
        }
        catch (Exception e) {
            Logger.getLogger(CheckedoutResourcesHelper.class.getName()).log(Level.SEVERE, null, e);
        }
        return libName;
    }

    static void AddCamerasToTable(String userId, DefaultTableModel model) 
    throws Exception{
         try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String cameras = String.format("SELECT * FROM %s "
                        + "WHERE %s = '%s' AND "
                        + "%s IS NOT NULL AND "
                        + "%s IS NULL", "CAMERAREQUESTS",
                        "USERID", userId,
                        "CHECKOUTTIME", "CHECKINDATETIME");
                try (IQueryResultSet resultSet = stmtExecutor.executeQuery(cameras)){
                    while (resultSet.getResultSet().next()) {
                        model.addRow(new String[] {
                            resultSet.getString("CAMERAID"),
                            resultSet.getString("STARTDATE")
                        });
                    }
                }
            }
         }
    }

    static void AddPubDetailToTable(String tableName, String userId, 
            String bookIdCol, String bookId, boolean isDueDatePresent,
            DefaultTableModel model) 
    throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String pubCheckout = String.format("SELECT * FROM %s "
                        + "WHERE %s = '%s' AND "
                        + "%s = '%s' AND "
                        + "%s IS NULL", tableName,
                        "USERID", userId,
                        bookIdCol, bookId,
                        "RDATETIME");
                try (IQueryResultSet resultSet = stmtExecutor.executeQuery(pubCheckout)){
                    while (resultSet.getResultSet().next()) {
                        model.addRow(new String[] {
                            "Publication Id",
                            resultSet.getString(bookIdCol)
                        });
                        model.addRow(new String[] {
                            "Date Checked out",
                            resultSet.getString("CDATETIME")
                        });
                        if (isDueDatePresent){
                            model.addRow(new String[] {
                            "Due Date",
                            resultSet.getString("ERDATETIME")
                            });
                        }
                    }
                }
            }
        }
    }

    static void AddRoomDetailToTable(String tableName, String userId, String roomNo,
            String startDate, DefaultTableModel model) 
    throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String roomCheckout = String.format("SELECT * FROM %s "
                        + "WHERE %s = '%s' AND "
                        + "%s = '%s' AND "
                        + "%s = TO_DATE('%s', 'YYYY/MM/DD HH24:MI:SS') AND "
                        + "%s IS NOT NULL AND "
                        + "%s IS NULL AND "
                        + "%s = 'N'", tableName,
                        "USERID", userId,
                        "ROOMNO", roomNo,
                        "STARTDATE", startDate,
                        "CHECKOUTDATETIME", "CHECKINDATETIME",
                        "ISVOID");
                try (IQueryResultSet resultSet = stmtExecutor.executeQuery(roomCheckout)){
                    if(!resultSet.isEmpty()){
                        resultSet.moveToFirstRow();
                        model.addRow(new String[] {"Room no", resultSet.getString("ROOMNO")});
                        model.addRow(new String[] {"Library name", getLibraryName(
                                connection, resultSet.getString("LIBRARYID"))});
                        model.addRow(new String[] {"Date of Booking", resultSet.getString("STARTDATE")});
                        model.addRow(new String[] {"End Date", resultSet.getString("ENDDATE")});
                        model.addRow(new String[] {"Checkout Date", resultSet.getString("CHECKOUTDATETIME")});
                    }
                }
            }
        }
    }

    static void AddCameraDetailToTable(String userId, String cameraId, DefaultTableModel model) 
    throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String cameraCheckout = String.format("SELECT * FROM %s "
                        + "WHERE %s = '%s' AND "
                        + "%s = '%s' AND "
                        + "%s IS NOT NULL AND "
                        + "%s IS NULL", "CAMERAREQUESTS",
                        "USERID", userId,
                        "CAMERAID", cameraId,
                        "CHECKOUTTIME", "CHECKINDATETIME");
                try (IQueryResultSet resultSet = stmtExecutor.executeQuery(cameraCheckout)){
                    if(!resultSet.isEmpty()){
                        resultSet.moveToFirstRow();
                        model.addRow(new String[] {"Camera ID", resultSet.getString("CAMERAID")});
                        model.addRow(new String[] {"Date of Booking", resultSet.getString("STARTDATE")});
                        model.addRow(new String[] {"Due Date", resultSet.getString("ENDDATE")});
                        model.addRow(new String[] {"Time of Checkout", resultSet.getString("CHECKOUTTIME")});
                    }
                }
            }
        }
    }
}
