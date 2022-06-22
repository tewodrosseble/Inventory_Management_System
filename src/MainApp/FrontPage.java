package MainApp;

import javax.swing.*;

public class FrontPage {
    public static DataBase DataBase;


    public static void main(String[] arg){
        Login loginManager = new Login();
        DataBase = new DataBase();

        if(DataBase.isDataBaseConnected()){
            loginManager.setBounds(180, 50, 1000,750);
            loginManager.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            loginManager.setVisible(true);
        }

    }

}
