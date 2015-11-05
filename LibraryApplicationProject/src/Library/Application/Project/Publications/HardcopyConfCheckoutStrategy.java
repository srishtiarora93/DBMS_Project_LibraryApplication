/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library.Application.Project.Publications;

import Library.Application.Project.UserAuthentication.UserType;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jordy
 */
class HardcopyConfCheckoutStrategy extends PublicationsCheckoutStrategy  {

    private static final String m_TableName = "CHECKSOUTHARDCOPYCONF";
    private static final String m_ColNameBookId = "CONFNO";
    private static final String m_ColNameUserId = "USERID";
    private static final String m_ColNameReturnDate = "RDATETIME";
    private static final String m_ColNameExpReturnDate = "ERDATETIME";
    private static final int m_CheckoutHours = 12;
    private static final String m_TableNameHardCopyConf = "HARDCOPYCONF";
    private static final String m_ColNameNoOfCopies = "NOOFCOPIES";
    private static final int m_RenewalHours = 1;
    
    private static final String m_WaitlistTable = "BOOKWAITLIST";
    private static final String m_ColNameBookIdWaitlist = "BOOKID";
    private static final String m_ColNameDateWaitlist = "DATEWAITLISTED";

    public HardcopyConfCheckoutStrategy(UserType userType) {
        super(userType);
    }

    @Override
    public void CheckoutBook(String bookId, String userId) {
        try{
            CheckoutStrategyHelper.CheckoutHardCopyBook(m_TableName, userId, bookId, m_CheckoutHours);
            CheckoutStrategyHelper.DecrementHardCopyNoOfCopies(m_TableNameHardCopyConf, 
                    m_ColNameNoOfCopies, m_ColNameBookId, bookId);
        }
        catch (Exception e) {
            Logger.getLogger(HardcopyConfCheckoutStrategy.class.getName()).log(Level.SEVERE, null, e);
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
            CheckoutStrategyHelper.IncrementHardCopyNoOfCopies(m_TableNameHardCopyConf, 
                    m_ColNameNoOfCopies, m_ColNameBookId, bookId);
                        
            String userName = CheckoutStrategyHelper.getUserWaitlistedIfAny(m_WaitlistTable,
                    bookId, m_ColNameBookIdWaitlist,
                    m_ColNameDateWaitlist, m_ColNameUserId);
            if(!userName.isEmpty()){
                CheckoutStrategyHelper.DeleteBookFromWaitlist(m_WaitlistTable, bookId, userName);
                this.CheckoutBook(bookId, userName);
            }
        }
        catch (Exception e) {
            Logger.getLogger(HardcopyConfCheckoutStrategy.class.getName()).log(Level.SEVERE, null, e);
        }
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
            Logger.getLogger(HardcopyConfCheckoutStrategy.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
     }
    
    private Boolean IsBookCopyAvailable(String bookId) throws Exception {
        return CheckoutStrategyHelper.getNoOfAvailableCopiesOfBook(m_TableNameHardCopyConf,
                m_ColNameNoOfCopies,
                m_ColNameBookId,
                bookId) > 0;
    }

    private Boolean IsBookAvailable(String bookId) {
        try{
            Boolean isBookCopyAvailable = IsBookCopyAvailable(bookId);
            return isBookCopyAvailable;
        }
        catch (Exception e) {
            Logger.getLogger(HardcopyConfCheckoutStrategy.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    Boolean CanBookBeCheckedout(String bookId, String userId) {
        if (IsBookCheckedOutByCurrentUser(bookId, userId)) return false;
        return IsBookAvailable(bookId);
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
            Logger.getLogger(HardcopyConfCheckoutStrategy.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    Boolean CanBookBeAddedToWaitList(String bookId, String userId) {
        try{
        if (CheckoutStrategyHelper.IsBookWaitListed(bookId, m_ColNameBookIdWaitlist, 
                userId, m_ColNameUserId, m_WaitlistTable)) return false;
        }
        catch (Exception e) {
            Logger.getLogger(HardcopyConfCheckoutStrategy.class.getName()).log(Level.SEVERE, null, e);
        }
        return !this.CanBookBeCheckedout(bookId, userId) && !this.CanBookBeReturned(bookId, userId);
    }

    @Override
    void AddBookToWaitlist(String bookId, String userId) {
        try{
            CheckoutStrategyHelper.AddBookToWaitlist(m_WaitlistTable, bookId, userId, IsFaculty());
        }
        catch (Exception e) {
            Logger.getLogger(HardcopyConfCheckoutStrategy.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    void RenewBook(String bookId, String userId) {
        try{
            CheckoutStrategyHelper.RenewBook(m_TableName , 
                bookId, m_ColNameBookId,
                userId, m_ColNameUserId,
                m_ColNameReturnDate, m_ColNameExpReturnDate,
                m_CheckoutHours);
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
