/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library.Application.Project.Publications;

import Library.Application.Project.UserAuthentication.UserType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jordy
 */
class HardcopyBookCheckoutStrategy extends PublicationsCheckoutStrategy{

    private static final String m_TableName = "CHECKSOUTHARDCOPYBOOK";
    private static final String m_ColNameBookId = "ISBN";
    private static final String m_ColNameUserId = "USERID";
    private static final String m_ColNameReturnDate = "RDATETIME";
    private static final String m_ColNameExpReturnDate = "ERDATETIME";
    private static final int m_RenewalHoursReservedBooks = 1;
    private static final int m_RenewalHours = 24;
    private static final int m_ReservedBookCheckoutHours = 4;
    //2 weeks = 2 * 7 * 24 hours
    private static final int m_BookCheckoutHoursStudent = 2 * 7 * 24;
    //30 days = 30 * 24 hours
    private static final int m_BookCheckoutHoursFaculty = 30 * 24;
    
    
    private static final String m_TableNameHardCopyBook = "HARDCOPYBOOKS";
    private static final String m_ColNameNoOfCopies = "NOOFCOPIES";
    private static final String m_ReservesTable = "RESERVES";
    private static final String m_CourseIdColName = "COURSEID";
    private static final String m_Enrollment = "ENROLLMENT";
    
    private static final String m_WaitlistTable = "BOOKWAITLIST";
    private static final String m_ColNameBookIdWaitlist = "BOOKID";
    private static final String m_ColNameDateWaitlist = "DATEWAITLISTED";

    public HardcopyBookCheckoutStrategy(UserType userType) {
        super(userType);
    }
    
    private Boolean IsBookCheckedOutByCurrentUser(String bookId, String userId) {
        try{
            Boolean isBookCheckedOutByUser = CheckoutStrategyHelper.IsBookCheckedOut(m_TableName, 
                    m_ColNameBookId,
                    m_ColNameUserId,
                    bookId,
                    userId, 
                    m_ColNameReturnDate);
            return isBookCheckedOutByUser;
        }
        catch (Exception e) {
            Logger.getLogger(HardcopyBookCheckoutStrategy.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    
    private Boolean IsBookCopyAvailable(String bookId) throws Exception {
        return CheckoutStrategyHelper.getNoOfAvailableCopiesOfBook(m_TableNameHardCopyBook,
                m_ColNameNoOfCopies,
                m_ColNameBookId,
                bookId) > 0;
    }

    @Override
    public void CheckoutBook(String bookId, String userId) {
        try{
            CheckoutStrategyHelper.CheckoutHardCopyBook(m_TableName, userId, bookId, getCheckoutHours(bookId));
            CheckoutStrategyHelper.DecrementHardCopyNoOfCopies(m_TableNameHardCopyBook, 
                    m_ColNameNoOfCopies, m_ColNameBookId, bookId);
        }
        catch (Exception e) {
            Logger.getLogger(HardcopyBookCheckoutStrategy.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void ReturnBook(String bookId, String userId) {
        try{
            CheckoutStrategyHelper.ReturnBook(m_TableName, 
                    userId, 
                    bookId,
                    m_ColNameReturnDate,
                    m_ColNameUserId,
                    m_ColNameBookId);
            CheckoutStrategyHelper.IncrementHardCopyNoOfCopies(m_TableNameHardCopyBook, 
                    m_ColNameNoOfCopies, m_ColNameBookId, bookId);
            
            String userName = CheckoutStrategyHelper.getUserWaitlistedIfAny(m_WaitlistTable,
                    bookId, m_ColNameBookIdWaitlist,
                    m_ColNameDateWaitlist, m_ColNameUserId);
            if(!userName.isEmpty()){
                this.CheckoutBook(bookId, userName);
            }
        }
        catch (Exception e) {
            Logger.getLogger(HardcopyBookCheckoutStrategy.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private int getCheckoutHours(String bookId){
        try{
            if (CheckoutStrategyHelper.IsBookReserved(bookId)){
                return m_ReservedBookCheckoutHours;
            }
        }
        catch (Exception e) {
            Logger.getLogger(HardcopyBookCheckoutStrategy.class.getName()).log(Level.SEVERE, null, e);
        }
        if (getUserType() == UserType.Faculty) return m_BookCheckoutHoursFaculty;
        return m_BookCheckoutHoursStudent;
    }

    private Boolean IsBookAvailable(String bookId) {
        try{
            Boolean isBookCopyAvailable = IsBookCopyAvailable(bookId);
            return isBookCopyAvailable;
        }
        catch (Exception e) {
            Logger.getLogger(HardcopyBookCheckoutStrategy.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    private Boolean IsStudentAuthorizedToCheckedOut(String bookId, String userId) {
        try{
            if (CheckoutStrategyHelper.IsBookReserved(bookId)) {
                List<String> courseIds = CheckoutStrategyHelper.GetCourseIdForBook(m_CourseIdColName, 
                        m_ReservesTable, 
                        m_ColNameBookId, 
                        bookId);
                for (String courseId : courseIds) {
                    if(CheckoutStrategyHelper.IsStudentEnrolledForCourse(courseId, 
                            userId, 
                            m_Enrollment,
                            m_CourseIdColName,
                            "STUDENTNO")){
                        return true;
                    }
                }
                return false;
            }
            return true;
        }
        catch (Exception e) {
            Logger.getLogger(HardcopyBookCheckoutStrategy.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    Boolean CanBookBeCheckedout(String bookId, String userId) {
        if (IsBookCheckedOutByCurrentUser(bookId, userId)) return false;
        if (!IsBookAvailable(bookId)) return false;
        if (this.getUserType() == UserType.Student){
            return IsStudentAuthorizedToCheckedOut(bookId, userId);
        }
        try{
            if (CheckoutStrategyHelper.IsBookReserved(bookId)){
                return false;
            }
        }
        catch (Exception e) {
            Logger.getLogger(HardcopyBookCheckoutStrategy.class.getName()).log(Level.SEVERE, null, e);
        }
        return true;
    }

    @Override
    Boolean CanBookBeReturned(String bookId, String userId) {
        return IsBookCheckedOutByCurrentUser(bookId, userId);
    }

    @Override
    Boolean CanBookBeRenewed(String bookId, String userId) {
        if (!IsBookCheckedOutByCurrentUser(bookId, userId)) return false;
        try{
            if (CheckoutStrategyHelper.IsBookWaitListed(bookId, 
                m_ColNameBookIdWaitlist, m_WaitlistTable)) return false;
            if (CheckoutStrategyHelper.IsBookReserved(bookId)){
            return CheckoutStrategyHelper.CanBookBeRenewed(m_TableName, 
                m_ColNameBookId,
                bookId,
                m_ColNameUserId,
                userId, 
                m_ColNameReturnDate,
                m_ColNameExpReturnDate,
                m_RenewalHoursReservedBooks);
            }
            return CheckoutStrategyHelper.CanBookBeRenewed(m_TableName, 
                m_ColNameBookId,
                bookId,
                m_ColNameUserId,
                userId, 
                m_ColNameReturnDate,
                m_ColNameExpReturnDate,
                m_RenewalHours);
        }
        catch (Exception e) {
            Logger.getLogger(HardcopyBookCheckoutStrategy.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    Boolean CanBookBeAddedToWaitList(String bookId, String userId) {
        if (!IsStudentAuthorizedToCheckedOut(bookId, userId)) return false;
        try{
        if (CheckoutStrategyHelper.IsBookWaitListed(bookId, m_ColNameBookIdWaitlist, 
                userId, m_ColNameUserId, m_WaitlistTable)) return false;
        }
        catch (Exception e) {
            Logger.getLogger(HardcopyBookCheckoutStrategy.class.getName()).log(Level.SEVERE, null, e);
        }
        return !this.CanBookBeCheckedout(bookId, userId) && !this.CanBookBeReturned(bookId, userId);
    }

    @Override
    void AddBookToWaitlist(String bookId, String userId) {
        try{
            CheckoutStrategyHelper.AddBookToWaitlist(m_WaitlistTable, bookId, userId, IsFaculty());
        }
        catch (Exception e) {
            Logger.getLogger(HardcopyBookCheckoutStrategy.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    void RenewBook(String bookId, String userId) {
        try{
            CheckoutStrategyHelper.RenewBook(m_TableName , 
                bookId, m_ColNameBookId,
                userId, m_ColNameUserId,
                m_ColNameReturnDate, m_ColNameExpReturnDate,
                getCheckoutHours(bookId));
        }
        catch (Exception e) {
            Logger.getLogger(HardcopyConfCheckoutStrategy.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    //returns "Y" is user is faculty; else returns "N"
    private String IsFaculty(){
        return IsUserFaculty() ? "Y" : "N";
    }
    
    private Boolean IsUserFaculty(){
        return this.getUserType() == UserType.Faculty;
    }
}
