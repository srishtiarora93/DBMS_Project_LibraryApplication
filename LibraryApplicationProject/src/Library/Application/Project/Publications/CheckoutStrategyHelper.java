/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library.Application.Project.Publications;

import Database.Connection.Helper.DatabaseConnectionService;
import Database.Connection.Helper.Interfaces.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jordy
 */
class CheckoutStrategyHelper{
    private static final long HOUR = 3600*1000;
    private static final float MILLI_TO_HOUR = 1000*60*60;
    private static final String m_ReservesTableName = "RESERVES";
    private static final String m_BookIdColName = "ISBN";
    private static final String m_ExpiryDate = "EXPIRYDATE";
    
    static Boolean IsBookCheckedOut(String tableName,
            String colNameBookId, 
            String colNameUserId,
            String bookId, 
            String userId,
            String returnDateColName) throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String book = String.format("SELECT * FROM %s " + 
                        "WHERE %s = '%s' " + 
                        "AND %s = '%s' " + 
                        "AND %s is NULL", tableName, colNameBookId, 
                        bookId, colNameUserId, 
                        userId, returnDateColName);
                try(IQueryResultSet resultSet = stmtExecutor.executeQuery(book)){
                    return !resultSet.isEmpty();
                }
            }
        }
    }
    
    static void CheckoutEcopyBook(String tableName,
            String userId,
            String bookId) throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = simpleDateFormat.format(new Date());
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()) {
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String checkout = String.format("INSERT INTO %s " +
                        "VALUES ('%s', '%s', TO_DATE('%s', 'YYYY/MM/DD HH24:MI:SS'), NULL)",
                        tableName, userId, bookId, currentDateTime);
                stmtExecutor.executeUpdate(checkout);
            }
        }
    }
    
    static void IncrementHardCopyNoOfCopies(String tableName, 
            String colNoOfCopies, 
            String bookIdColName, 
            String bookId) throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()) {
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String updateQuery = String.format("UPDATE %s "
                        + "SET %s = %s + 1 "
                        + "WHERE %s = '%s'",
                        tableName, colNoOfCopies, colNoOfCopies, bookIdColName, bookId);
                stmtExecutor.executeUpdate(updateQuery);
            }
        }
    }
    
    static void DecrementHardCopyNoOfCopies(String tableName, 
            String colNoOfCopies, 
            String bookIdColName, 
            String bookId) throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()) {
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String updateQuery = String.format("UPDATE %s "
                        + "SET %s = %s - 1 "
                        + "WHERE %s = '%s'",
                        tableName, colNoOfCopies, colNoOfCopies, bookIdColName, bookId);
                stmtExecutor.executeUpdate(updateQuery);
            }
        }
    }
    
    static void CheckoutHardCopyBook(String tableName,
            String userId,
            String bookId,
            int checkoutHours) throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String currentDateTime = simpleDateFormat.format(currentDate);
        Date expectedReturnDate = new Date(currentDate.getTime() + checkoutHours * HOUR);
        String expReturnDateTime = simpleDateFormat.format(expectedReturnDate);
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()) {
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String checkout = String.format("INSERT INTO %s " +
                        "VALUES ('%s', '%s', TO_DATE('%s', 'YYYY/MM/DD HH24:MI:SS'), " +
                        "TO_DATE('%s', 'YYYY/MM/DD HH24:MI:SS'), NULL)",
                        tableName, userId, bookId, currentDateTime, expReturnDateTime);
                stmtExecutor.executeUpdate(checkout);
            }
        }
    }
    
    static void ReturnBook(String tableName,
            String userId,
            String bookId,
            String rDateTimeColumnName,
            String colNameUserId,
            String colNameBookId) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = simpleDateFormat.format(new Date());
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()) {
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String returnBook = String.format("UPDATE %s " +
                        "SET %s = TO_DATE('%s', 'YYYY/MM/DD HH24:MI:SS') " +
                        "WHERE %s = '%s' AND %s = '%s' AND %s is NULL",
                        tableName, rDateTimeColumnName, 
                        currentDateTime, colNameUserId, 
                        userId, colNameBookId, 
                        bookId, rDateTimeColumnName);
                stmtExecutor.executeUpdate(returnBook);
            }
        }
    }
    static int getNoOfAvailableCopiesOfBook(String tableName,
            String colNameNoOfCopies,
            String bookIdColoumnName,
            String bookId) throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()) {
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
               String noOfCopiesQuery = String.format("SELECT %s FROM %s "
                       + "WHERE %s = '%s'", 
                       colNameNoOfCopies, tableName, bookIdColoumnName, bookId);
               try(IQueryResultSet resultSet = stmtExecutor.executeQuery(noOfCopiesQuery)){
                   resultSet.moveToFirstRow();
                   int noOfCopies = Integer.parseInt(resultSet.getString(colNameNoOfCopies));
                   return noOfCopies;
               }
            }
        }
    }
    static Boolean IsBookReserved(String bookId) throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()) {
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String bookReserved = String.format("SELECT * FROM %s "
                        + "WHERE %s = '%s'"
                        , m_ReservesTableName, m_BookIdColName, bookId);
                try (IQueryResultSet resultSet = stmtExecutor.executeQuery(bookReserved)){
                    if(resultSet.isEmpty()) return false;
                    while(resultSet.getResultSet().next()){
                        String expiryDate = resultSet.getString(m_ExpiryDate);
                        Date exDate = simpleDateFormat.parse(expiryDate);
                        return currentDate.before(exDate);
                    }
                }
            }
        }
        return null;
    }

    static List<String> GetCourseIdForBook(String courseIdColName, 
            String reservesTable, 
            String colNameBookId, 
            String bookId) throws Exception{
        List<String> courseIdList = new ArrayList<>();
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()) {
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
               String courseIds = String.format("SELECT %s FROM %s "
                       + "WHERE %s = '%s'", 
                       courseIdColName, reservesTable, colNameBookId, bookId);
               try(IQueryResultSet resultSet = stmtExecutor.executeQuery(courseIds)){
                   while (resultSet.getResultSet().next()) {
                       courseIdList.add(resultSet.getString(courseIdColName));
                   }
               }
            }
        }
        return courseIdList;
    }

    static boolean IsStudentEnrolledForCourse(String courseId, 
            String userId, 
            String enrollmentTable,
            String courseIdColName,
            String userIdColName) throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()) {
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
               String enrollment = String.format("SELECT * FROM %s "
                       + "WHERE %s = '%s' AND "
                       + "%s = '%s'", 
                       enrollmentTable, courseIdColName, courseId, 
                       userIdColName, userId);
               try(IQueryResultSet resultSet = stmtExecutor.executeQuery(enrollment)){
                   return !resultSet.isEmpty();
               }
            }
        }
    }

    static Boolean CanBookBeRenewed(String tableName,
            String colNameBookId,
            String bookId,
            String colNameUserId,
            String userId,
            String returnDateColName,
            String expReturnDateColName,
            int renewalHours) throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()) {
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String book = String.format("SELECT * FROM %s " + 
                        "WHERE %s = '%s' " + 
                        "AND %s = '%s' " + 
                        "AND %s is NULL", tableName, colNameBookId, 
                        bookId, colNameUserId, 
                        userId, returnDateColName);
                try(IQueryResultSet resultSet = stmtExecutor.executeQuery(book)){
                    resultSet.moveToFirstRow();
                    String expReturnDate = resultSet.getString(expReturnDateColName);
                    Date date = simpleDateFormat.parse(expReturnDate);
                    if (!currentDate.before(date)) return false;
                    return (date.getTime() - currentDate.getTime())/MILLI_TO_HOUR <= renewalHours;
                }
            }
        }
    }

    static boolean IsBookWaitListed(String bookId, String colNameBookId, String tableName) 
    throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String book = String.format("SELECT * FROM %s " + 
                        "WHERE %s = '%s'", tableName, colNameBookId, 
                        bookId);
                try(IQueryResultSet resultSet = stmtExecutor.executeQuery(book)){
                    return !resultSet.isEmpty();
                }
            }
        }
    }

    static boolean IsBookWaitListed(String bookId, String colNameBookId,
            String userId, String colNameUserId, 
            String tableName) throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String book = String.format("SELECT * FROM %s " + 
                        "WHERE %s = '%s' " + 
                        "AND %s = '%s'", tableName, 
                        colNameBookId, bookId, 
                        colNameUserId, userId);
                try(IQueryResultSet resultSet = stmtExecutor.executeQuery(book)){
                    return !resultSet.isEmpty();
                }
            }
        }
    }

    static void AddBookToWaitlist(String waitlistTable, 
            String bookId, 
            String userId,
            String isFaculty) 
    throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String currentDateTime = simpleDateFormat.format(currentDate);
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()) {
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String waitlist = String.format("INSERT INTO %s " +
                        "VALUES ('%s', '%s', TO_DATE('%s', 'YYYY/MM/DD HH24:MI:SS'), '%s')",
                        waitlistTable, userId, 
                        bookId, currentDateTime, isFaculty);
                stmtExecutor.executeUpdate(waitlist);
            }
        }
    }

    static void RenewBook(String tableName, 
            String bookId, String colNameBookId, 
            String userId, String colNameUserId, 
            String colNameReturnDate, String colNameExpReturnDate,
            int checkoutHours) 
    throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()) {
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String checkout = String.format("SELECT * FROM %s " + 
                        "WHERE %s = '%s' " + 
                        "AND %s = '%s' " + 
                        "AND %s is NULL", tableName, colNameBookId, 
                        bookId, colNameUserId, 
                        userId, colNameReturnDate);
                try (IQueryResultSet resultSet = stmtExecutor.executeQuery(checkout)){
                    resultSet.moveToFirstRow();
                    String expReturnDate = resultSet.getString(colNameExpReturnDate);
                    Date date = simpleDateFormat.parse(expReturnDate);
                    Date newDate = new Date(date.getTime() + checkoutHours * HOUR);
                    String newDateString = simpleDateFormat.format(newDate);
                    String updateQuery = String.format("UPDATE %s "
                        + "SET %s = TO_DATE('%s', 'YYYY/MM/DD HH24:MI:SS') "
                        + "WHERE %s = '%s' " 
                        + "AND %s = '%s' " 
                        + "AND %s is NULL",
                        tableName, colNameExpReturnDate, newDateString, 
                        colNameBookId, bookId,
                        colNameUserId, userId, colNameReturnDate);
                    stmtExecutor.executeUpdate(updateQuery);
                }
            }
        }
    }

    //returns user id if a user has requested for the book with id = bookId
    //otherwise returns an empty string
    //never returns a null
    static String getUserWaitlistedIfAny(String waitlistTable, String bookId, 
            String colNameBookIdWaitlist, String colNameDateWaitlist,
            String colNameUserId) throws Exception {
        String userId = "";
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String faculty = String.format("select * "
                        + "FROM %s "
                        + "WHERE %s = '%s' AND ISFACULTY = 'Y' "
                        + "ORDER BY %s", waitlistTable, colNameBookIdWaitlist,
                        bookId, colNameDateWaitlist);
                try(IQueryResultSet resultSet = stmtExecutor.executeQuery(faculty)){
                    if (!resultSet.isEmpty()){
                        resultSet.moveToFirstRow();
                        return resultSet.getString(colNameUserId);
                    }
                }
                String student = String.format("select * "
                        + "FROM %s "
                        + "WHERE %s = '%s' AND ISFACULTY = 'N' "
                        + "ORDER BY %s", waitlistTable, colNameBookIdWaitlist,
                        bookId, colNameDateWaitlist);
                try(IQueryResultSet resultSet = stmtExecutor.executeQuery(student)){
                    if (!resultSet.isEmpty()){
                        resultSet.moveToFirstRow();
                        return resultSet.getString(colNameUserId);
                    }
                }
            }
        }
        return userId;
    }
}
