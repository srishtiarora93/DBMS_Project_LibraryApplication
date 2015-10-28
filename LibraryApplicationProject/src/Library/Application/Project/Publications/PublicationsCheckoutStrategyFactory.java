/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library.Application.Project.Publications;

/**
 *
 * @author jordy
 */
public class PublicationsCheckoutStrategyFactory {
    private static final String s_EcopyBooks = "ECOPYBOOKS";
    private static final String s_EcopyConf = "ECOPYCONF";
    private static final String s_EcopyJournals = "ECOPYJOURNALS";
    private static final String s_HardCopyJournals = "HARDCOPYJOURNALS";
    private static final String s_HardCopyConf = "HARDCOPYCONF";
    private static final String s_HardCopyBook = "HARDCOPYBOOKS";
    
    
    public static PublicationsCheckoutStrategy CreatePublicationsCheckoutStrategy(String tableName,
            UserType userType){
        switch (tableName.toUpperCase()){
            case s_EcopyBooks:
                return new EcopyBooksCheckoutStrategy(userType);
            case s_EcopyConf:
                return new EcopyConfCheckoutStrategy(userType);
            case s_EcopyJournals:
                return new EcopyJournalsCheckoutStrategy(userType);
            case s_HardCopyJournals:
                return new HardcopyJournalCheckoutStrategy(userType);
            case s_HardCopyConf:
                return new HardcopyConfCheckoutStrategy(userType);
            case s_HardCopyBook:
                return new HardcopyBookCheckoutStrategy(userType);
            default:
                throw new IllegalStateException("Not a valid table name for book!");
        }
    }
}
