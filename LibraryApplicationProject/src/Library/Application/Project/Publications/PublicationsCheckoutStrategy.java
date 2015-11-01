/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library.Application.Project.Publications;

import Library.Application.Project.UserAuthentication.UserType;

/**
 *
 * @author jordy
 */
public abstract class PublicationsCheckoutStrategy {
    private final UserType m_UserType;

    /**
     *
     * @param userType
     */
    public PublicationsCheckoutStrategy(UserType userType){
        this.m_UserType = userType;
    }
    
    public UserType getUserType(){
        return m_UserType;
    }
    
    abstract Boolean CanBookBeCheckedout(String bookId, String userId);
    abstract Boolean CanBookBeReturned(String bookId, String userId);
    abstract Boolean CanBookBeRenewed(String bookId, String userId);
    abstract Boolean CanBookBeAddedToWaitList(String bookId, String userId);
    
    abstract void CheckoutBook(String bookId, String userId);
    abstract void ReturnBook(String bookId, String userId);
    abstract void AddBookToWaitlist(String bookId, String userId);
    abstract void RenewBook(String bookId, String userId);
}
