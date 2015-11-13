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
    private static final String m_StudyRoomsColRoomNo = "ROOMNO";
    private static final String m_StudyRoomsColFloor = "FLOOR";
    private static final String m_StudyRoomsColCapacity = "CAPACITY";
    private static final String m_StudyRoomsColLibId = "LIBRARYID";
    
    private static final String m_ConfRoomsColRoomNo = "ROOMNO";
    private static final String m_ConfRoomsColFloor = "FLOOR";
    private static final String m_ConfRoomsColCapacity = "CAPACITY";
    private static final String m_ConfRoomsColLibId = "LIBRARYID";
    
    private static final String m_StudyRoomResTable = "STUDYROOMRESERVATION";
    private static final String m_StudyRoomResUserId = "USERID";
    private static final String m_StudyRoomResRoomNo = "ROOMNO";
    private static final String m_StudyRoomResLibId = "LIBRARYID";
    private static final String m_StudyRoomResStDate = "STARTDATE";
    private static final String m_StudyRoomResEndDate = "ENDDATE";
    private static final String m_StudyRoomResChkOutDeadlineDate = "CHECKOUTDEADLINE";
    private static final String m_StudyRoomResChkOutDate = "CHECKOUTDATETIME";
    private static final String m_StudyRoomResChkInDate = "CHECKINDATETIME";
    private static final String m_StudyRoomResIsVoid = "ISVOID";
    
    private static final String m_ConfRoomResTable = "CONFERENCEROOMRESERVATION";
    private static final String m_ConfRoomResUserId = "USERID";
    private static final String m_ConfRoomResRoomNo = "ROOMNO";
    private static final String m_ConfRoomResLibId = "LIBRARYID";
    private static final String m_ConfRoomResStDate = "STARTDATE";
    private static final String m_ConfRoomResEndDate = "ENDDATE";
    private static final String m_ConfRoomResChkOutDeadlineDate = "CHECKOUTDEADLINE";
    private static final String m_ConfRoomResChkOutDate = "CHECKOUTDATETIME";
    private static final String m_ConfRoomResChkInDate = "CHECKINDATETIME";
    private static final String m_ConfRoomResIsVoid = "ISVOID";

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
                    + "%s is NULL AND "
                    + "%s = 'N'", m_StudyRoomResTable, m_StudyRoomResRoomNo, roomNo,
                    m_StudyRoomResLibId, libId,
                    m_StudyRoomResChkInDate, m_StudyRoomResIsVoid);
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
                    + "%s is NULL AND "
                    + "%s = 'N'", m_ConfRoomResTable, m_ConfRoomResRoomNo, roomNo,
                    m_ConfRoomResLibId, libId,
                    m_ConfRoomResChkInDate, m_ConfRoomResIsVoid);
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
        return m_ConfRoomResTable;
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
                        + "NULL, NULL, 'N')", reservationTable, 
                        userId, roomNo, libId,
                        fromDate, toDate, checkout);
                stmtExecutor.executeUpdate(reserve);
            }
        }
    }

    static void AddReservedRoomsToTable(String roomsTableName, String userId, 
            DefaultTableModel model) throws Exception{
        String reservationTable = getResTableName(roomsTableName);
        switch (reservationTable){
            case m_StudyRoomResTable:
            try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
                try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                    String reservation = String.format("SELECT * FROM %s "
                        + "WHERE %s = '%s' AND "
                        + "%s is NULL AND "
                        + "%s = 'N'", m_StudyRoomResTable, m_StudyRoomResUserId, userId,
                        m_StudyRoomResChkInDate, m_StudyRoomResIsVoid);
                    try (IQueryResultSet result = stmtExecutor.executeQuery(reservation)){
                        while(result.getResultSet().next()){
                            String roomNo = result.getString(m_StudyRoomsColRoomNo);
                            String libId = result.getString(m_StudyRoomsColLibId);
                            AddRoomToTable(roomsTableName, roomNo, libId, model);
                        }
                    }
                }
            }
            break;
            case m_ConfRoomResTable:
            try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
                try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                    String reservation = String.format("SELECT * FROM %s "
                        + "WHERE %s = '%s' AND "
                        + "%s is NULL AND "
                        + "%s = 'N'", m_ConfRoomResTable, m_ConfRoomResUserId, userId,
                        m_ConfRoomResChkInDate, m_ConfRoomResIsVoid);
                    try (IQueryResultSet result = stmtExecutor.executeQuery(reservation)){
                        while(result.getResultSet().next()){
                            String roomNo = result.getString(m_ConfRoomsColRoomNo);
                            String libId = result.getString(m_ConfRoomsColLibId);
                            AddRoomToTable(roomsTableName, roomNo, libId, model);
                        }
                    }
                }
            }
            break;
        }
    }

    static boolean CheckoutRoom(String roomsTableName, String userId, 
        String roomNo) throws Exception{
        String reservationTable = getResTableName(roomsTableName);
        Date currentDate = new Date();
        String currentDateString = m_SimpleDateFormat.format(currentDate);
        switch (reservationTable){
            case m_StudyRoomResTable:
                try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
                try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                    String reservation = String.format("SELECT * FROM %s "
                        + "WHERE %s = '%s' AND "
                        + "%s = '%s' AND "
                        + "%s is NULL AND "
                        + "%s is NULL AND "
                        + "%s = 'N'", m_StudyRoomResTable, m_StudyRoomResUserId, userId,
                        m_StudyRoomResRoomNo, roomNo,
                        m_StudyRoomResChkOutDate,
                        m_StudyRoomResChkInDate, m_StudyRoomResIsVoid);
                    try (IQueryResultSet result = stmtExecutor.executeQuery(reservation)){
                        while(result.getResultSet().next()){
                            String start = result.getString(m_StudyRoomResStDate);
                            Date startDate = m_SimpleDateFormat.parse(start);
                            String checkoutDeadline = result.getString(m_StudyRoomResChkOutDeadlineDate);
                            Date checkoutDeadlineDate = m_SimpleDateFormat.parse(checkoutDeadline);
                            if (startDate.before(currentDate) && currentDate.before(checkoutDeadlineDate)){
                                String checkout = String.format("UPDATE %s "
                                + "SET %s = TO_DATE('%s', 'YYYY/MM/DD HH24:MI:SS') "
                                + "WHERE %s = '%s' AND "
                                + "%s = '%s' AND "
                                + "%s is NULL AND "
                                + "%s is NULL AND "
                                + "%s = 'N'", m_StudyRoomResTable, 
                                m_StudyRoomResChkOutDate, currentDateString,
                                m_StudyRoomResUserId, userId,
                                m_StudyRoomResRoomNo, roomNo,
                                m_StudyRoomResChkOutDate,
                                m_StudyRoomResChkInDate, m_StudyRoomResIsVoid);
                                stmtExecutor.executeUpdate(checkout);
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
            case m_ConfRoomResTable:
                try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
                try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                    String reservation = String.format("SELECT * FROM %s "
                        + "WHERE %s = '%s' AND "
                        + "%s = '%s' AND "
                        + "%s is NULL AND "
                        + "%s is NULL AND "
                        + "%s = 'N'", m_ConfRoomResTable, m_ConfRoomResUserId, userId,
                        m_ConfRoomResRoomNo, roomNo,
                        m_ConfRoomResChkOutDate,
                        m_ConfRoomResChkInDate, m_ConfRoomResIsVoid);
                    try (IQueryResultSet result = stmtExecutor.executeQuery(reservation)){
                        while(result.getResultSet().next()){
                            String start = result.getString(m_ConfRoomResStDate);
                            Date startDate = m_SimpleDateFormat.parse(start);
                            String checkoutDeadline = result.getString(m_ConfRoomResChkOutDeadlineDate);
                            Date checkoutDeadlineDate = m_SimpleDateFormat.parse(checkoutDeadline);
                            if (startDate.before(currentDate) && currentDate.before(checkoutDeadlineDate)){
                                String checkout = String.format("UPDATE %s "
                                + "SET %s = TO_DATE('%s', 'YYYY/MM/DD HH24:MI:SS') "
                                + "WHERE %s = '%s' AND "
                                + "%s = '%s' AND "
                                + "%s is NULL AND "
                                + "%s is NULL AND "
                                + "%s = 'N'", m_ConfRoomResTable, 
                                m_ConfRoomResChkOutDate, currentDateString,
                                m_ConfRoomResUserId, userId,
                                m_ConfRoomResRoomNo, roomNo,
                                m_ConfRoomResChkOutDate,
                                m_ConfRoomResChkInDate, m_ConfRoomResIsVoid);
                                stmtExecutor.executeUpdate(checkout);
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
        throw new IllegalStateException("Illegal State");
    }

    static boolean CheckinRoom(String roomsTableName, String userId, 
        String roomNo) throws Exception{
        String reservationTable = getResTableName(roomsTableName);
        Date currentDate = new Date();
        String currentDateString = m_SimpleDateFormat.format(currentDate);
        switch (reservationTable){
            case m_StudyRoomResTable:
                try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
                try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                    String reservation = String.format("SELECT * FROM %s "
                        + "WHERE %s = '%s' AND "
                        + "%s = '%s' AND "
                        + "%s is NOT NULL AND "
                        + "%s is NULL AND "
                        + "%s = 'N'", m_StudyRoomResTable, m_StudyRoomResUserId, userId,
                        m_StudyRoomResRoomNo, roomNo,
                        m_StudyRoomResChkOutDate,
                        m_StudyRoomResChkInDate, m_StudyRoomResIsVoid);
                    try (IQueryResultSet result = stmtExecutor.executeQuery(reservation)){
                        while(result.getResultSet().next()){
                            String checkout = String.format("UPDATE %s "
                            + "SET %s = TO_DATE('%s', 'YYYY/MM/DD HH24:MI:SS') "
                            + "WHERE %s = '%s' AND "
                            + "%s = '%s' AND "
                            + "%s is NOT NULL AND "
                            + "%s is NULL AND "
                            + "%s = 'N'", m_StudyRoomResTable, 
                            m_StudyRoomResChkInDate, currentDateString,
                            m_StudyRoomResUserId, userId,
                            m_StudyRoomResRoomNo, roomNo,
                            m_StudyRoomResChkOutDate,
                            m_StudyRoomResChkInDate, m_StudyRoomResIsVoid);
                            stmtExecutor.executeUpdate(checkout);
                            return true;
                        }
                    }
                }
            }
            return false;
            case m_ConfRoomResTable:
                try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
                try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                    String reservation = String.format("SELECT * FROM %s "
                        + "WHERE %s = '%s' AND "
                        + "%s = '%s' AND "
                        + "%s is NOT NULL AND "
                        + "%s is NULL AND "
                        + "%s = 'N'", m_ConfRoomResTable, m_ConfRoomResUserId, userId,
                        m_ConfRoomResRoomNo, roomNo,
                        m_ConfRoomResChkOutDate,
                        m_ConfRoomResChkInDate, m_ConfRoomResIsVoid);
                    try (IQueryResultSet result = stmtExecutor.executeQuery(reservation)){
                        while(result.getResultSet().next()){
                            String checkout = String.format("UPDATE %s "
                            + "SET %s = TO_DATE('%s', 'YYYY/MM/DD HH24:MI:SS') "
                            + "WHERE %s = '%s' AND "
                            + "%s = '%s' AND "
                            + "%s is NOT NULL AND "
                            + "%s is NULL AND "
                            + "%s = 'N'", m_ConfRoomResTable, 
                            m_ConfRoomResChkInDate, currentDateString,
                            m_ConfRoomResUserId, userId,
                            m_ConfRoomResRoomNo, roomNo,
                            m_ConfRoomResChkOutDate,
                            m_ConfRoomResChkInDate, m_ConfRoomResIsVoid);
                            stmtExecutor.executeUpdate(checkout);
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        throw new IllegalStateException("Illegal State");
    }

    private static void AddRoomToTable(String roomsTableName, String roomNo, 
            String libId, DefaultTableModel model) throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String room = String.format("SELECT * FROM %s "
                    + "WHERE %s = '%s' AND "
                    + "%s = '%s'", roomsTableName, 
                    m_StudyRoomsColRoomNo, roomNo,
                    m_StudyRoomsColLibId, libId);
                try (IQueryResultSet result = stmtExecutor.executeQuery(room)){
                    while(result.getResultSet().next()){
                        model.addRow(new String[] {
                        result.getString(m_StudyRoomsColRoomNo), 
                        result.getString(m_StudyRoomsColFloor),
                        result.getString(m_StudyRoomsColCapacity),
                        result.getString(m_StudyRoomsColLibId),
                        getLibraryName(connection, result.getString(m_StudyRoomsColLibId))});
                    }
                }
            }
        }
    }
}
