/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library.Application.Project.ResourceRequests;

import Database.Connection.Helper.DatabaseConnectionService;
import Database.Connection.Helper.Interfaces.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jordy
 */
public class ResourceRequestsHelper {

    static void AddPublicationsToTable(String userId, DefaultTableModel model) 
    throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                AddEcopyBooks(userId, stmtExecutor, model);
                AddEcopyConf(userId, stmtExecutor, model);
                AddEcopyJournal(userId, stmtExecutor, model);
                AddHardCopyBooks(userId, stmtExecutor, model);
                AddHardCopyConf(userId, stmtExecutor, model);
                AddHardCopyJournal(userId, stmtExecutor, model);
                AddWaitlistedBooks(userId, stmtExecutor, model);
            }
        }
    }

    private static void AddEcopyBooks(String userId, final IStatementExecutor stmtExecutor, 
            DefaultTableModel model) throws Exception {
        String eBooks = String.format("SELECT * FROM %s "
                + "WHERE %s = '%s'",
                "CHECKSOUTECOPYBOOK", "USERID", userId);
        try (IQueryResultSet resultSet = stmtExecutor.executeQuery(eBooks)){
            AddResultSetToTable(resultSet, model, "ISBN", "CDATETIME", "RDATETIME");
        }
    }

    private static void AddResultSetToTable(IQueryResultSet resultSet, DefaultTableModel model, 
            String idCol, String cDateTimeCol, String rDateTimeCol) throws Exception{
        while (resultSet.getResultSet().next()){
            model.addRow(new String[] {
                resultSet.getString(idCol),
                resultSet.getString(cDateTimeCol),
                resultSet.getString(rDateTimeCol)
            });
        }
    }

    private static void AddEcopyConf(String userId, IStatementExecutor stmtExecutor, 
            DefaultTableModel model) throws Exception{
        String eBooks = String.format("SELECT * FROM %s "
                + "WHERE %s = '%s'",
                "CHECKSOUTECOPYCONF", "USERID", userId);
        try (IQueryResultSet resultSet = stmtExecutor.executeQuery(eBooks)){
            AddResultSetToTable(resultSet, model, "CONFNO", "CDATETIME", "RDATETIME");
        }
    }

    private static void AddEcopyJournal(String userId, IStatementExecutor stmtExecutor, 
            DefaultTableModel model) throws Exception{
        String eBooks = String.format("SELECT * FROM %s "
                + "WHERE %s = '%s'",
                "CHECKSOUTECOPYJOURNAL", "USERID", userId);
        try (IQueryResultSet resultSet = stmtExecutor.executeQuery(eBooks)){
            AddResultSetToTable(resultSet, model, "ISSN", "CDATETIME", "RDATETIME");
        }
    }

    private static void AddHardCopyBooks(String userId, IStatementExecutor stmtExecutor, 
            DefaultTableModel model) throws Exception{
        String books = String.format("SELECT * FROM %s "
                + "WHERE %s = '%s'",
                "CHECKSOUTHARDCOPYBOOK", "USERID", userId);
        try (IQueryResultSet resultSet = stmtExecutor.executeQuery(books)){
            AddResultSetToTable(resultSet, model, "ISBN", "CDATETIME", "RDATETIME");
        }
    }

    private static void AddHardCopyConf(String userId, IStatementExecutor stmtExecutor, 
            DefaultTableModel model) throws Exception{
        String confPapers = String.format("SELECT * FROM %s "
                + "WHERE %s = '%s'",
                "CHECKSOUTHARDCOPYCONF", "USERID", userId);
        try (IQueryResultSet resultSet = stmtExecutor.executeQuery(confPapers)){
            AddResultSetToTable(resultSet, model, "CONFNO", "CDATETIME", "RDATETIME");
        }
    }

    private static void AddHardCopyJournal(String userId, IStatementExecutor stmtExecutor, 
            DefaultTableModel model) throws Exception{
        String journals = String.format("SELECT * FROM %s "
                + "WHERE %s = '%s'",
                "CHECKSOUTHARDCOPYJOURNAL", "USERID", userId);
        try (IQueryResultSet resultSet = stmtExecutor.executeQuery(journals)){
            AddResultSetToTable(resultSet, model, "ISSN", "CDATETIME", "RDATETIME");
        }
    }

    static void AddRoomsToTable(String userId, DefaultTableModel model) 
    throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                AddStudyRooms(userId, stmtExecutor, model);
                AddConferenceRooms(userId, stmtExecutor, model);
            }
        }
    }

    private static void AddStudyRooms(String userId, IStatementExecutor stmtExecutor, 
            DefaultTableModel model) throws Exception{
        String studyRooms = String.format("SELECT * FROM %s "
                + "WHERE %s = '%s'",
                "STUDYROOMRESERVATION", "USERID", userId);
        try (IQueryResultSet resultSet = stmtExecutor.executeQuery(studyRooms)){
            AddResultSetToTable(resultSet, model, "ROOMNO", "STARTDATE", "ENDDATE");
        }
    }

    private static void AddConferenceRooms(String userId, IStatementExecutor stmtExecutor, 
            DefaultTableModel model) throws Exception{
        String confRooms = String.format("SELECT * FROM %s "
                + "WHERE %s = '%s'",
                "CONFERENCEROOMRESERVATION", "USERID", userId);
        try (IQueryResultSet resultSet = stmtExecutor.executeQuery(confRooms)){
            AddResultSetToTable(resultSet, model, "ROOMNO", "STARTDATE", "ENDDATE");
        }
    }

    static void AddCamerasToTable(String userId, DefaultTableModel model) 
    throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                AddCameras(userId, stmtExecutor, model);
                AddWaitlistedCameras(userId, stmtExecutor, model);
            }
        }
    }

    private static void AddCameras(String userId, IStatementExecutor stmtExecutor, 
            DefaultTableModel model) throws Exception{
        String cameras = String.format("SELECT * FROM %s "
                + "WHERE %s = '%s'",
                "CAMERAREQUESTS", "USERID", userId);
        try (IQueryResultSet resultSet = stmtExecutor.executeQuery(cameras)){
            AddResultSetToTable(resultSet, model, "CAMERAID", "STARTDATE", "ENDDATE");
        }
    }

    private static void AddWaitlistedBooks(String userId, IStatementExecutor stmtExecutor, 
            DefaultTableModel model) throws Exception{
        String books = String.format("SELECT * FROM %s "
                + "WHERE %s = '%s'",
                "BOOKWAITLIST", "USERID", userId);
        try (IQueryResultSet resultSet = stmtExecutor.executeQuery(books)){
            while (resultSet.getResultSet().next()){
                model.addRow(new String[] {
                    resultSet.getString("BOOKID"),
                    "Waitlisted on - ",
                    resultSet.getString("DATEWAITLISTED")
                });
            }
        }
    }

    private static void AddWaitlistedCameras(String userId, IStatementExecutor stmtExecutor, 
            DefaultTableModel model) throws Exception{
        String cameras = String.format("SELECT * FROM %s "
                + "WHERE %s = '%s'",
                "CAMERAWAITQUEUE", "USERID", userId);
        try (IQueryResultSet resultSet = stmtExecutor.executeQuery(cameras)){
            while (resultSet.getResultSet().next()){
                model.addRow(new String[] {
                    resultSet.getString("BOOKID"),
                    "Waitlisted on - ",
                    resultSet.getString("DATEWAITLISTED")
                });
            }
        }
    }
    
}
