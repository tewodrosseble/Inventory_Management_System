package MainApp;


import javax.swing.*;
import java.sql.*;


public class DataBase {
    Connection myConn;
    Statement myStatement;
    ResultSet myRs;
    boolean isConnected=false;

    public DataBase(){
        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost/JavaInventory","teddy1","PASSWORD@strong#8879");// substitute your user name and password
            myStatement = myConn.createStatement();
            isConnected = true;
        }
        catch (Exception e){
            isConnected = false;
            JOptionPane.showMessageDialog(null, "Check you database , it must be running", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public DataBase(String conn,String dbName,String password){

        try {
            myConn = DriverManager.getConnection(conn,dbName,password);
            myStatement = myConn.createStatement();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public ResultSet getQueryResult(String query){
        try {
            myRs = myStatement.executeQuery(query);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return myRs;
    }
    public void insertRow(String query){

        try {
            myStatement.executeUpdate(query);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void deleteRow(String query){
        try {
            myStatement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Remove Successful", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Unable to Remove Row", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public boolean isDataBaseConnected(){
        return isConnected;
    }

}

