/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.Connection.Helper.Interfaces;

/**
 *
 * @author jordy
 */
public interface IHashService {
    String getHash(String password);
    Boolean isMatch(String password, String passwordHash);
}
