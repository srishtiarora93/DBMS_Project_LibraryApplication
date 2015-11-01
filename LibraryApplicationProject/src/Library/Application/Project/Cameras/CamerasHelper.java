/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library.Application.Project.Cameras;

import Database.Connection.Helper.DatabaseConnectionService;
import Database.Connection.Helper.Interfaces.*;
import Library.Application.Project.Rooms.RoomsHelper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jordy
 */
public class CamerasHelper {
    
    private static final String m_CamerasTable = "CAMERAS";
    private static final String m_ColId = "CAMERAID";
    private static final String m_ColMake = "MAKE";
    private static final String m_ColModel = "MODEL";
    private static final String m_ColMemory = "MEMORY";
    private static final String m_ColConfig = "CONFIGURATION";
    private static final String m_ColLibraryId = "LIBRARYID";
    
    private static final String m_CameraReqTable = "CAMERAREQUESTS";
    private static final String m_ColCamReqCamId = "CAMERAID";
    private static final String m_ColCamReqUserId = "USERID";
    private static final String m_ColCamReqStartDate = "STARTDATE";
    private static final String m_ColCamReqChkInDate = "CHECKINDATETIME";
    
    private static final String m_LibraryTable = "LIBRARIES";
    private static final String m_LibColLibId = "LIBRARYID";
    private static final String m_LibColName = "NAME";
    
    private static final String m_CameraQTable = "CAMERAWAITQUEUE";
    private static final String m_ColCamQCamId = "CAMERAID";
    private static final String m_ColCamQUserId = "USERID";
    private static final String m_ColCamQExDate = "EXPRESERVATIONDATE";
    private static final SimpleDateFormat m_SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static void AddCamerasToTable(DefaultTableModel model) 
    throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String cameras = String.format("SELECT * FROM %s", m_CamerasTable);
                try (IQueryResultSet resultSet = stmtExecutor.executeQuery(cameras)){
                    while (resultSet.getResultSet().next()) {
                        model.addRow(new String[] {
                        resultSet.getString(m_ColId),
                        resultSet.getString(m_ColMake),
                        resultSet.getString(m_ColModel),
                        resultSet.getString(m_ColMemory),
                        resultSet.getString(m_ColConfig),
                        resultSet.getString(m_ColLibraryId),
                        getLibraryName(connection, resultSet.getString(m_ColLibraryId))});
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
                + "WHERE %s = '%s'", m_LibraryTable, m_LibColLibId, libId);
                try (IQueryResultSet result = stmtExecutor.executeQuery(library)){
                    result.moveToFirstRow();
                    libName = result.getString(m_LibColName);
                }
            }
        }
        catch (Exception e) {
            Logger.getLogger(RoomsHelper.class.getName()).log(Level.SEVERE, null, e);
        }
        return libName;
    }

    static void AddCameraDetailsToTable(DefaultTableModel model, String cameraId) 
    throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String cameras = String.format("SELECT * FROM %s "
                        + "WHERE %s = '%s'", m_CamerasTable, m_ColId, cameraId);
                try (IQueryResultSet resultSet = stmtExecutor.executeQuery(cameras)){
                    resultSet.moveToFirstRow();
                    model.addRow(new String[] {m_ColId, resultSet.getString(m_ColId)});
                    model.addRow(new String[] {m_ColMake, resultSet.getString(m_ColMake)});
                    model.addRow(new String[] {m_ColModel, resultSet.getString(m_ColModel)});
                    model.addRow(new String[] {m_ColMemory, resultSet.getString(m_ColMemory)});
                    model.addRow(new String[] {m_ColConfig, resultSet.getString(m_ColConfig)});
                    model.addRow(new String[] {"Library Name", 
                        getLibraryName(connection, resultSet.getString(m_ColLibraryId))});
                }
            }
        }
    }

    static boolean IsCameraRequestedOnDate(String cameraId, String startDate) 
    throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String cameraRequest = String.format("SELECT * FROM %s " + 
                        "WHERE %s = '%s' " + 
                        "AND %s = TO_DATE('%s', 'YYYY/MM/DD HH24:MI:SS') " + 
                        "AND %s is NULL", m_CameraReqTable, m_ColCamReqCamId, 
                        cameraId, m_ColCamReqStartDate, 
                        startDate, m_ColCamReqChkInDate);
                try(IQueryResultSet resultSet = stmtExecutor.executeQuery(cameraRequest)){
                    return !resultSet.isEmpty();
                }
            }
        }
    }

    static void ReserveTheCamera(String userId, String cameraId, 
            String startDate, String endDate, 
            String checkoutEndTime) throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String reserve = String.format("INSERT INTO %s " + 
                        "VALUES ('%s', '%s', "
                        + "TO_DATE('%s', 'YYYY/MM/DD HH24:MI:SS'), "
                        + "TO_DATE('%s', 'YYYY/MM/DD HH24:MI:SS'), "
                        + "NULL, "
                        + "TO_DATE('%s', 'YYYY/MM/DD HH24:MI:SS'), "
                        + "NULL)", m_CameraReqTable, 
                        userId, cameraId, startDate,
                        endDate, checkoutEndTime);
                stmtExecutor.executeUpdate(reserve);
            }
        }
    }

    static boolean IsCameraRequestedOnDateByUser(String cameraId, String userId, 
            String startDate) throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String cameraRequest = String.format("SELECT * FROM %s " + 
                        "WHERE %s = '%s' "
                        + "AND %s = '%s' " + 
                        "AND %s = TO_DATE('%s', 'YYYY/MM/DD HH24:MI:SS') " + 
                        "AND %s is NULL", m_CameraReqTable, 
                        m_ColCamReqUserId, userId,
                        m_ColCamReqCamId, cameraId, 
                        m_ColCamReqStartDate, startDate, 
                        m_ColCamReqChkInDate);
                try(IQueryResultSet resultSet = stmtExecutor.executeQuery(cameraRequest)){
                    return !resultSet.isEmpty();
                }
            }
        }
    }

    static void AddCameraToWaitList(String userId, String cameraId,
            String exReservationDate) 
    throws Exception{
        String currentDateTime = m_SimpleDateFormat.format(new Date());
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String reserve = String.format("INSERT INTO %s " + 
                        "VALUES ('%s', '%s', "
                        + "TO_DATE('%s', 'YYYY/MM/DD HH24:MI:SS'), "
                        + "TO_DATE('%s', 'YYYY/MM/DD HH24:MI:SS'))", m_CameraQTable, 
                        userId, cameraId, currentDateTime, exReservationDate);
                stmtExecutor.executeUpdate(reserve);
            }
        }
    }

    static boolean IsCameraWaitlistedOnDateByUser(String cameraId, String userId, 
            String requestedCheckinDate) throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String cameraWaitlist = String.format("SELECT * FROM %s " + 
                        "WHERE %s = '%s' "
                        + "AND %s = '%s' " + 
                        "AND %s = TO_DATE('%s', 'YYYY/MM/DD HH24:MI:SS')", m_CameraQTable, 
                        m_ColCamQUserId, userId,
                        m_ColCamQCamId, cameraId, 
                        m_ColCamQExDate, requestedCheckinDate);
                try(IQueryResultSet resultSet = stmtExecutor.executeQuery(cameraWaitlist)){
                    return !resultSet.isEmpty();
                }
            }
        }
    }
}
