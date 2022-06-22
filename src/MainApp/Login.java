package MainApp;

import javax.swing.*;
import java.awt.event.*;
import java.sql.ResultSet;


public class Login extends JFrame{
    private JLabel label_user,label_password;
    private JButton button_signup,button_login;
    private JTextField tf_username;
    private JPasswordField pf_user_password;
    private Login reference;
    private Login lmgr = this;

    public Login(){
        super("Signup/Login Menu");
        reference = this;
        setLayout(null);
        EventHandler eh = new EventHandler();
        KeyboardHandler kl = new KeyboardHandler();
        addKeyListener(kl);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                String[] str = new String[]{"Yes","No"};
                int exit = JOptionPane.showOptionDialog(null, "Are you sure you want to exit", "Exit", JOptionPane.NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, str, null);

                if(exit == 0) {   //Delete Row From Table
                    lmgr.dispose();
                }
            }
        });

        label_user = new JLabel("USER NAME : ");
        add(label_user);
        label_user.setBounds(100,60,label_user.getPreferredSize().width,label_user.getPreferredSize().height);

        label_password = new JLabel("PASSWORD : ");
        add(label_password);
        label_password.setBounds(370,60,label_password.getPreferredSize().width,label_password.getPreferredSize().height);

        tf_username = new JTextField();
        add(tf_username);
        tf_username.addKeyListener(kl);
        tf_username.setBounds(180,57,130,25);

        pf_user_password = new JPasswordField();
        pf_user_password.addKeyListener(kl);
        add(pf_user_password);
        pf_user_password.setBounds(460, 57, 130, 25);

        button_login = new JButton("LOGIN");
        button_login.addActionListener(eh);
        add(button_login);
        button_login.setBounds(488,100,100,button_login.getPreferredSize().height);

        button_signup = new JButton("SIGN UP");
        add(button_signup);
        button_signup.addActionListener(eh);
        button_signup.setBounds(488,150,100,button_signup.getPreferredSize().height);



    }
    public void clearTextFields(){
        tf_username.setText("");
        pf_user_password.setText("");
    }

    public class KeyboardHandler implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                System.out.println("Enter Pressed");
                button_login.doClick();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    public class EventHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == button_login) {
                boolean nf1 = true;
                boolean nf2 = true;
                try{
                    if (nf1) {

                        ResultSet myRs2 = FrontPage.DataBase.getQueryResult("SELECT * FROM user");
                        while (myRs2.next()) {
                            if (tf_username.getText().equals(myRs2.getString("name")) && pf_user_password.getText().equals(myRs2.getString("password"))) {
                                JOptionPane.showMessageDialog(null, " You have Logged Successfully");
                                if (tf_username.getText().equals("admin")) {
                                    InventoryManager inventoryManager = new InventoryManager(lmgr);
                                    inventoryManager.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                                    inventoryManager.setBounds(180, 50, 1000,750);
                                    inventoryManager.setVisible(true);
                                    lmgr.setVisible(false);
                                }
//
                                nf2 = false;
                                break;
                            }

                        }
                        if (nf2 && nf1) {
                            JOptionPane.showMessageDialog(null, "Username/Password Incorrect");
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Can not access the database");
                }

            }

        }

    }
}
