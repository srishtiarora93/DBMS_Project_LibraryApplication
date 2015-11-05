/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.Connection.Helper;

/**
 *
 * @author Prishu
 */
public class DBMSSchedulerJobs {
    private static final String m_hardCopyReservationClearanceJob = "HARD_COPY_RESERVATION_CLEARANCE_JOB";
    private static final String m_hardCopyReservationClearanceProgram = "HARD_COPY_RESERVATION_CLEARANCE_PROGRAM";
    private static final String m_dailyInterval = "DAILY_INTERVAL";
    private static final String m_dueDateNotificationJob = "DUE_DATE_NOTIFICATION_JOB";
    private static final String m_dueDateNotificationProgram = "DUE_DATE_NOTIFICATION_PROGRAM";
    
    public static String reservationClearanceJob(){
        
      String jobquery =  String.format("begin "+
                                       "dbms_scheduler.create_job "+
                                       "(job_name => '%s', "+
                                       "program_name=> '%s', "+
                                       "schedule_name=>'%s', "+
                                       "enabled=>true, "+
                                       "auto_drop=>false, "+
                                       "comments=>'Reservation clearance job'); "+
                                       "end; "
                                       ,m_hardCopyReservationClearanceJob, 
                                       m_hardCopyReservationClearanceProgram, m_dailyInterval );
      return jobquery;
    }
    public static String DueDateNotificationJob(){
    
        String jobquery =  String.format("begin "+
                                       "dbms_scheduler.create_job "+
                                       "(job_name => '%s', "+
                                       "program_name => '%s',"+
                                       "schedule_name =>'%s', "+
                                       "enabled=>true, "+
                                       "auto_drop=>false, "+
                                       "comments=>'Job that runs to send the notification to patron on the basis of schedule.'); "+
                                       "end; "
                                       ,m_dueDateNotificationJob, 
                                       m_dueDateNotificationProgram, 
                                       m_dailyInterval);
        
        return jobquery;
    }
    
}
