/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.Connection.Helper.Interfaces;

import java.sql.ResultSet;

/**
 *
 * @author jordy
 */
public interface IQueryResultSet extends AutoCloseable{
    String getString(String attributeName);
    Boolean isEmpty();
    Boolean moveToFirstRow();
    ResultSet  getResultSet();
}
