/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library.Application.Project.Rooms;

import Database.Connection.Helper.DatabaseConnectionService;
import Database.Connection.Helper.Interfaces.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jordy
 */
public class RoomsHelper {
    
    private static final long HOUR = 3600*1000;
    private static final String m_LibrariesTable = "LIBRARIES";
    private static final String m_LibrariesIdCol = "LIBRARYID";
    private static final String m_LibrariesNameCol = "NAME";
    private static final SimpleDateFormat m_SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String m_StudyRoomsTable = "STUDYROOMS";
    
    private static final String m_StudyRoomResTable = "STUDYROOMRESERVATION";
    private static final String m_StudyRoomResRoomNo = "ROOMNO";
    private static final String m_StudyRoomResLibId = "LIBRARYID";
    private static final String m_StudyRoomResStDate = "STARTDATE";
    private static final String m_StudyRoomResEndDate = "ENDDATE";
    private static final String m_StudyRoomResChkInDate = "CHECKINDATETIME";
    
    private static final String m_ConfRoomResTable = "CONFERENCEROOMRESERVATION";
    private static final String m_ConfRoomResRoomNo = "ROOMNO";
    private static final String m_ConfRoomResLibId = "LIBRARYID";
    private static final String m_ConfRoomResStDate = "STARTDATE";
    private static final String m_ConfRoomResEndDate = "ENDDATE";
    private static final String m_ConfRoomResChkInDate = "CHECKINDATETIME";

    static void AddRoomsToTable(String tableName, 
            String colRoomNo, 
            String colFloor,
            int capacity,
            String colCapacity,
            String colLibId,
            String date,
            DefaultTableModel model) 
    throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String rooms = String.format("SELECT * FROM %s " + 
                        "WHERE %s >= %s ", tableName, colCapacity, 
                        capacity);
                try (IQueryResultSet resultSet = stmtExecutor.executeQuery(rooms)){
                    while (resultSet.getResultSet().next()) {
                        String roomNo = resultSet.getString(colRoomNo);
                        String libId = resultSet.getString(colLibId);
                        if(!IsRoomReserved(connection, tableName, roomNo, libId, date)){
                            model.addRow(new String[] {roomNo, 
                            resultSet.getString(colFloor),
                            resultSet.getString(colCapacity),
                            resultSet.getString(colLibId),
                            getLibraryName(connection, resultSet.getString(colLibId))});
                        }
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
            Logger.getLogger(RoomsHelper.class.getName()).log(Level.SEVERE, null, e);
        }
        return libName;
    }

    static boolean AnyRoomsWithCapacityPresent(String roomsTableName, 
            int capacity,
            String capacityColName) 
    throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String rooms = String.format("SELECT * FROM %s " + 
                        "WHERE %s >= %s ", roomsTableName, capacityColName, 
                        capacity);
                try(IQueryResultSet resultSet = stmtExecutor.executeQuery(rooms)){
                    return !resultSet.isEmpty();
                }
            }
        }
    }

    private static boolean IsRoomReserved(IDatabaseConnection connection, String tableName, 
            String roomNo, String libId, String date) {
        try{
            String reservationTable = getResTableName(tableName);
            switch (reservationTable){
                case m_StudyRoomResTable:
                try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                    String reservation = String.format("SELECT * FROM %s "
                    + "WHERE %s = '%s' AND "
                    + "%s = '%s' AND "
                    + "%s is NULL", m_StudyRoomResTable, m_StudyRoomResRoomNo, roomNo,
                    m_StudyRoomResLibId, libId,
                    m_StudyRoomResChkInDate);
                    try (IQueryResultSet result = stmtExecutor.executeQuery(reservation)){
                        while(result.getResultSet().next()){
                            String start = result.getString(m_StudyRoomResStDate);
                            Date startDate = m_SimpleDateFormat.parse(start);
                            String end = result.getString(m_StudyRoomResEndDate);
                            Date endDate = m_SimpleDateFormat.parse(end);
                            Date bookingDate = m_SimpleDateFormat.parse(date);
                            if(startDate.equals(bookingDate)){
                                return true;
                            }
                            if(startDate.before(bookingDate) &&
                                    bookingDate.before(endDate)){
                                return true;
                            }
                        }
                    }
                }  
                return false;
                    
                case m_ConfRoomResTable:
                try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                    String reservation = String.format("SELECT * FROM %s "
                    + "WHERE %s = '%s' AND "
                    + "%s = '%s' AND "
                    + "%s is NULL", m_ConfRoomResTable, m_ConfRoomResRoomNo, roomNo,
                    m_ConfRoomResLibId, libId,
                    m_ConfRoomResChkInDate);
                    try (IQueryResultSet result = stmtExecutor.executeQuery(reservation)){
                        while(result.getResultSet().next()){
                            String start = result.getString(m_ConfRoomResStDate);
                            Date startDate = m_SimpleDateFormat.parse(start);
                            String end = result.getString(m_ConfRoomResEndDate);
                            Date endDate = m_SimpleDateFormat.parse(end);
                            Date bookingDate = m_SimpleDateFormat.parse(date);
                            if(startDate.equals(bookingDate)){
                                return true;
                            }
                            if(startDate.before(bookingDate) &&
                                    bookingDate.before(endDate)){
                                return true;
                            }
                        }
                    }
                }  
                return false;
                    
                default:  
                    throw new IllegalStateException("Invalid State!");
            }
        }
        catch (Exception e) {
            Logger.getLogger(RoomsHelper.class.getName()).log(Level.SEVERE, null, e);
        }
        throw new IllegalStateException("Invalid State!");
    }

    private static String getResTableName(String tableName) {
        if (tableName.equals(m_StudyRoomsTable)) return m_StudyRoomResTable;
        return m_ConfRoomResChkInDate;
    }

    static void ReserveRoom(String roomsTableName, 
            String userId, String roomNo, 
            String libId, String fromDate, String toDate) throws Exception{
        String reservationTable = getResTableName(roomsTableName);
        Date startDate = m_SimpleDateFormat.parse(fromDate);
        Date checkoutDate = new Date(startDate.getTime() + 1 * HOUR);
        String checkout = m_SimpleDateFormat.format(checkoutDate);
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String reserve = String.format("INSERT INTO %s " + 
                        "VALUES ('%s', '%s', '%s', "
                        + "TO_DATE('%s', 'YYYY/MM/DD HH24:MI:SS'), "
                        + "TO_DATE('%s', 'YYYY/MM/DD HH24:MI:SS'), "
                        + "TO_DATE('%s', 'YYYY/MM/DD HH24:MI:SS'), "
                        + "NULL, NULL)", reservationTable, 
                        userId, roomNo, libId,
                        fromDate, toDate, checkout);
                stmtExecutor.executeUpdate(reserve);
            }
        }
    }
}
