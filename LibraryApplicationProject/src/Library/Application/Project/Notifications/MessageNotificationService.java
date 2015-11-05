/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library.Application.Project.Notifications;

import Database.Connection.Helper.DatabaseConnectionService;
import Database.Connection.Helper.Interfaces.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Prishu
 */
public class MessageNotificationService implements IMessageNotificationService{
    java.util.Date date= new java.util.Date();
    private static final String m_patronMessageTable = "PATRONMESSAGENOTIFICATIONS";
    private static final String m_patronMessageNo = "PATRONMESSAGENO";
    private static final String m_patronId = "PATRONID";
    private static final String m_messageGeneratedTime = "MESSAGEGENERATEDTIME";
    private static final String m_readFlag = "READFLAG";
    private static final String m_autoIncrementPatronMessageNo = "messageNoAutoSequence.nextval";
    private static final String m_masterMessageTable = "MASTERMESSAGES";
    private static final String m_masterMessageId = "MASTERMESSAGEID";
    private static final String m_masterMessageSubject = "MASTERMESSAGESUBJECT";
    private static final String m_masterMessageText = "MASTERMESSAGETEXT";

    @Override
    public void AddMessageNotification(String patronId,String messageId){
        try {
             AddMessageForPatrons(patronId,messageId);
        } 
        catch (Exception ex) {
            Logger.getLogger(MessageNotificationService.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    @Override
    public List<List<String>> GetMessageNotification(String patronId) {
        List<List<String>> listOfMessages = new ArrayList<>();
        try {
             listOfMessages= GetAllMessagesForPatrons(patronId);
        } catch (Exception ex) {
            Logger.getLogger(MessageNotificationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listOfMessages;
    }
    
    private List<List<String>> GetAllMessagesForPatrons(String patronId)throws Exception {
        List<List<String>> rows = new ArrayList<>();
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String getMessage = String.format("SELECT * FROM %s " +
                        "WHERE patronId = '%s'",m_patronMessageTable,patronId );
                try (IQueryResultSet resultSet = stmtExecutor.executeQuery(getMessage)){
                  while (resultSet.getResultSet().next()){
                        List<String> row = new ArrayList<>();
                        row.add(resultSet.getString(m_patronMessageNo));
                        row.add(resultSet.getString(m_masterMessageId));
                        row.add(getMessageSubject(resultSet.getString(m_masterMessageId)));
                        row.add(resultSet.getString(m_messageGeneratedTime));
                        row.add(resultSet.getString(m_readFlag));
                        rows.add(row);
                    }
                }
            }
        }
        return rows;
    }

    private void AddMessageForPatrons(String patronId, String masterMessageId) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = simpleDateFormat.format(new Date());
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String addMessageForPatrons = String.format("INSERT INTO %s "
                        +"(%s,%s,%s,%s,%s)"
                        +" VALUES (%s,'%s', '%s',TO_DATE('%s', 'YYYY/MM/DD HH24:MI:SS'),'N')",
                m_patronMessageTable,m_patronMessageNo,m_patronId,m_masterMessageId,m_messageGeneratedTime, m_readFlag ,m_autoIncrementPatronMessageNo,
                patronId,masterMessageId,currentDateTime );
                stmtExecutor.executeUpdate(addMessageForPatrons);
            }
        }
    }

    private String getMessageSubject(String masterMessageId)throws Exception  {
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String getMessageSubject = String.format("SELECT %s FROM %s " +
                        "WHERE %s = '%s'",m_masterMessageSubject,m_masterMessageTable,m_masterMessageId,masterMessageId );
                try (IQueryResultSet resultSet = stmtExecutor.executeQuery(getMessageSubject)){
                    resultSet.moveToFirstRow();
                    return resultSet.getString(m_masterMessageSubject);
                }
            }
        }
    }

    @Override
    public String ShowMessage(String messageId) {
        String messageText = null;
        try {
             messageText = ShowCompleteMessage(messageId);
        } catch (Exception ex) {
            Logger.getLogger(MessageNotificationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return messageText;
    }
        

    private String ShowCompleteMessage(String messageId) throws Exception {
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String getMessageText = String.format("SELECT %s FROM %s " +
                        "WHERE %s = '%s'",m_masterMessageText,m_masterMessageTable,m_masterMessageId,messageId );
                try (IQueryResultSet resultSet = stmtExecutor.executeQuery(getMessageText)){
                    resultSet.moveToFirstRow();
                    return resultSet.getString(m_masterMessageText);
                }
            }
        }
    }

}
    
    
    
    
    

