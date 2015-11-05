/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.Connection.Helper.Interfaces;

/**
 *
 * @author Prishu
 */
public interface ICallableStatement  extends AutoCloseable{
    boolean Execute();
}
