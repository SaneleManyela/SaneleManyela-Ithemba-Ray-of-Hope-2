/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ithemba.ray_of_hope;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Sanele
 */
public class clsDatabaseInterface {
    
    public Connection mConnectToDatabase() {
        String strConnectionString = "jdbc:mysql://localhost:3306/burial_society_db";
        try {
            return DriverManager.getConnection(strConnectionString, "root", "password");
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    
    public boolean mCheckDetailsExistence(String strQuery) {
        Connection conMySQLConnectionString = mConnectToDatabase();
        Statement stStatement = null;
        ResultSet rs = null;
        boolean boolExist = false;
        
        try {
            stStatement = conMySQLConnectionString.prepareStatement(strQuery);
            rs = stStatement.executeQuery(strQuery);
            boolExist = rs.next();
            conMySQLConnectionString.close();
            stStatement.close();
            rs.close();
        } catch(SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                conMySQLConnectionString.close();
                stStatement.close();
                rs.close();
            } catch(SQLException | NullPointerException e) {
            }
        }
        return boolExist;
    }
    
    public boolean mCreateNewRecord(String strQuery) {
        Connection conMySQLConnectionString = mConnectToDatabase();
        Statement stStatement = null;
        try {
            stStatement = conMySQLConnectionString.prepareStatement(strQuery);
            stStatement.execute(strQuery);
            stStatement.close();
            conMySQLConnectionString.close();
            return true;
        } catch (SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                conMySQLConnectionString.close();
                stStatement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
        return false;
    }
    
    public Long mGetIdentityNumber(String strQuery) {
        Connection conMySQLConnectionString = mConnectToDatabase();
        Statement stStatement = null;
        ResultSet rs = null;
        
        try {
            stStatement = conMySQLConnectionString.prepareStatement(strQuery);
            rs = stStatement.executeQuery(strQuery);
            while(rs.next()) {
                return rs.getLong(1);
            }
            stStatement.close();
            rs.close();
            conMySQLConnectionString.close();
        } catch (SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                conMySQLConnectionString.close();
                stStatement.close();
                rs.close();
            } catch(SQLException | NullPointerException e) {
            }
        }
        return 0L;
    }
    
    public String mGetField(String strQuery) {
        Connection conMySQLConnectionString = mConnectToDatabase();
        Statement stStatement = null;
        ResultSet rs = null;
        
        try {
            stStatement = conMySQLConnectionString.prepareStatement(strQuery);
            rs = stStatement.executeQuery(strQuery);
            while(rs.next()) {
                return rs.getString(1);
            }
            stStatement.close();
            rs.close();
            conMySQLConnectionString.close();
        } catch(SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                conMySQLConnectionString.close();
                stStatement.close();
                rs.close();
            } catch(SQLException | NullPointerException e) {
            }
        }
        return null;
    }
    
    public String[] mFetchRecordDetails(String strQuery) {
        String[] arrRecordDetails = null;
        Connection conMySQLConnectionString = mConnectToDatabase();
        
        try {
            try(Statement stStatement = conMySQLConnectionString.prepareStatement(strQuery)) {
                try(ResultSet rs = stStatement.executeQuery(strQuery)) {
                    ResultSetMetaData rsmt = rs.getMetaData();
                    arrRecordDetails = new String[rsmt.getColumnCount() + 1];
                    
                    while(rs.next()) {
                        for(int i = 1; i < arrRecordDetails.length; i++) {
                            arrRecordDetails[i] = String.valueOf(rs.getString(i));
                        }
                    }
                    stStatement.close();
                    rs.close();
                    conMySQLConnectionString.close();
                }
                arrRecordDetails = new clsDataMethods().mRemoveEmptyIndices(arrRecordDetails).toArray(
                new String[new clsDataMethods().mRemoveEmptyIndices(arrRecordDetails).size()]);
                return arrRecordDetails;
            }
        } catch(SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                conMySQLConnectionString.close();
            } catch(SQLException | NullPointerException e) {
            }
        }
        return arrRecordDetails;
    }
    
    public boolean mUpdateRecordDetails(String strQuery) {
        Connection conMySQLConnectionString = mConnectToDatabase();
        try{
            try(Statement stStatement = conMySQLConnectionString.prepareCall(strQuery)) {
                stStatement.executeUpdate(strQuery);
                stStatement.close();
                conMySQLConnectionString.close();
                return true;
            }
        } catch(SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                conMySQLConnectionString.close();
            } catch(SQLException | NullPointerException e) {
            }
        }
        return false;
    }
    
    public boolean mDeleteRecord(String strQuery) {
        Connection conMySQLConnectionString = mConnectToDatabase();
        try(Statement stStatement = conMySQLConnectionString.prepareStatement(strQuery)) {
            stStatement.execute(strQuery);
            stStatement.close();
            conMySQLConnectionString.close();
            return true;
        } catch(SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                conMySQLConnectionString.close();
            } catch(SQLException | NullPointerException e) {
            }
        }
        return false;
    }
}
