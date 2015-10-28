/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.Connection.Helper.Interfaces;

import java.util.ArrayList;

/**
 *
 * @author jordy
 */
public interface IDatabaseMetaDataHandler extends AutoCloseable{
    public ArrayList<String> getColumnNames(String tableName);
    public ArrayList<String> getPrimaryKeyColumnNames(String tableName);
}
