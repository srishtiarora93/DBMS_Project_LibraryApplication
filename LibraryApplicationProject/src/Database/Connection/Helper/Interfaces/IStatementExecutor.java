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
public interface IStatementExecutor extends AutoCloseable{
    void executeUpdate(String sqlStatement);
    IQueryResultSet executeQuery(String sqlQuery);
}
