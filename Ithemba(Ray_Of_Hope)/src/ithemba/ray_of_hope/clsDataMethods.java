/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ithemba.ray_of_hope;

import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sanele
 */
public class clsDataMethods {
    
    private final clsDatabaseInterface databaseInterface = new clsDatabaseInterface();
    
    public List<String> mRemoveEmptyIndices(String[] array) {
        List<String> values = new ArrayList<>();
        
        try {
            for(String element : array) {
                if(element != null) {
                    values.add(element);
                }
            }
        } catch(NullPointerException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return values;
    }
    
    private DefaultTableModel mTableModel(String strQuery, DefaultTableModel model) {
        Connection conMySQLConnectionString = databaseInterface.mConnectToDatabase();
        try{
            try(Statement stStatement = conMySQLConnectionString.prepareStatement(strQuery)) {
                
                ResultSet rs = stStatement.executeQuery(strQuery);
                ResultSetMetaData rsmt = rs.getMetaData();
                int intColumnCount = rsmt.getColumnCount();
                
                for(int i = 1; i <= intColumnCount; i++) {
                    model.addColumn(rsmt.getColumnName(i));
                }
                
                while(rs.next()) {
                    Object[] arrRow = new Object[intColumnCount + 1];
                    for(int i = 1; i <= intColumnCount; i++) {
                        arrRow[i] = rs.getObject(i);
                    }
                    
                    String[] arrRowData = new String[arrRow.length];
                    for(int i = 1; i < arrRow.length; i++) {
                        arrRowData[i] = arrRow[i].toString();
                    }
                    
                    model.addRow(mRemoveEmptyIndices(arrRowData).toArray(
                            new String[mRemoveEmptyIndices(arrRowData).size()]));
                }
                return model;
            }
        } catch(SQLException | NullPointerException e) {
        } finally {
            try {
                conMySQLConnectionString.close();
            } catch(SQLException | NullPointerException e) {
            }
        }
        return model;
    }
    
    public JTable mTable(String strQuery, JTable tblTable, DefaultTableModel model) {
        model = mTableModel(strQuery, model);
        tblTable.setModel(model);
        tblTable.setFillsViewportHeight(true);
        tblTable.validate();
        return tblTable;
    }
    
    public void mLoadToComboBox(String strQuery, JComboBox cbo) {
        Connection conMySQLConnectionString = databaseInterface.mConnectToDatabase();
        try{
          try(Statement stStatement = conMySQLConnectionString.prepareStatement(strQuery)) {
              
              try(ResultSet rs = stStatement.executeQuery(strQuery)) {
                  if(strQuery.contains(",")) {
                      while(rs.next()) {
                          cbo.addItem(rs.getString(1) + " " + rs.getString(2));
                      }
                  } else {
                      while(rs.next()) {
                          cbo.addItem(rs.getString(1));
                      }
                  }
                  
                  stStatement.close();
                  rs.close();
              }
          }  
        } catch(SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
