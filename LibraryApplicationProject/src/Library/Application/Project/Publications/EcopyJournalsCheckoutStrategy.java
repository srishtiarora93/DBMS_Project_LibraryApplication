/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library.Application.Project.Publications;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jordy
 */
class EcopyJournalsCheckoutStrategy  extends PublicationsCheckoutStrategy{
    private static final String m_Table_Name = "CHECKSOUTECOPYJOURNAL";
    private static final String m_ColNameBookId = "ISSN";
    private static final String m_ColNameUserId = "USERID";
    private static final String m_ColNameReturnDate = "RDATETIME";

    public EcopyJournalsCheckoutStrategy(UserType userType) {
        super(userType);
    }

    @Override
    public void CheckoutBook(String bookId, String userId) {
        try{
            CheckoutStrategyHelper.CheckoutEcopyBook(m_Table_Name, userId, bookId);
        }
        catch (Exception e) {
            Logger.getLogger(EcopyJournalsCheckoutStrategy.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void ReturnBook(String bookId, String userId) {
        try{
            CheckoutStrategyHelper.ReturnBook(m_Table_Name, 
                    userId, 
                    bookId,
                    m_ColNameReturnDate,
                    m_ColNameUserId,
                    m_ColNameBookId);
        }
        catch (Exception e) {
            Logger.getLogger(EcopyJournalsCheckoutStrategy.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    Boolean CanBookBeCheckedout(String bookId, String userId) {
        try{
            return !CheckoutStrategyHelper.IsBookCheckedOut(m_Table_Name, 
                    m_ColNameBookId, 
                    m_ColNameUserId, 
                    bookId, 
                    userId, 
                    m_ColNameReturnDate);
        }
        catch (Exception e) {
            Logger.getLogger(EcopyJournalsCheckoutStrategy.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    Boolean CanBookBeReturned(String bookId, String userId) {
        return !this.CanBookBeCheckedout(bookId, userId);
    }

    @Override
    Boolean CanBookBeRenewed(String bookId, String userId) {
        return false;
    }

    @Override
    Boolean CanBookBeAddedToWaitList(String bookId, String userId) {
        return false;
    }

    @Override
    void AddBookToWaitlist(String bookId, String userId) {
        //No concept of waitlist for ecopy books
        //violation of design principle Interface Segregation :-(
        //TODO: Move method to a different interface
    }

    @Override
    void RenewBook(String bookId, String userId) {
        //No concept of waitlist for ecopy books
        //violation of design principle Interface Segregation :-(
        //TODO: Move method to a different interface
    }
}
