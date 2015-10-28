/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.Connection.Helper;

import Database.Connection.Helper.Interfaces.IHashService;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author jordy
 */
class HashService implements IHashService{

    @Override
    public String getHash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public Boolean isMatch(String password, String passwordHash) {
        return BCrypt.checkpw(password, passwordHash);
    }
    
}
