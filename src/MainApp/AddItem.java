package MainApp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;


public class AddItem extends JFrame {

    JTextField name,quantity,price;
    JFrame catFrame;
    JComboBox Categories;
    JLabel l_name,l_quantity,l_price,l_catagory;
    DefaultTableModel tableModel;
    JButton addB,addCatB;
    ListHandler lhand;
    InventoryManager im;
    public static String[] str;

    public AddItem(DefaultTableModel tableModel,InventoryManager im){
        super("Add Inventory ");
        setLayout(null);
        this.tableModel = tableModel;
        this.im = im;


        try {
            int ind=0;
            ResultSet myRs = FrontPage.DataBase.getQueryResult("SELECT * FROM Categories");
            while (myRs.next()){
                ind++;
            }
            myRs = FrontPage.DataBase.getQueryResult("SELECT * FROM Categories");
            str = new String[ind];
            ind=0;
            while (myRs.next()){
                str[ind++] = myRs.getString("Categories");
            }
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null,"Database - Categories Error");
        }

        Categories = new JComboBox(str);
        Categories.setBounds(100, 260, 150, 30);
        lhand = new ListHandler();
        Categories.addItemListener(lhand);
        add(Categories);


        name = new JTextField();
        name.setBounds(100, 80, 100, 25);
        add(name);

        quantity = new JTextField();
        quantity.setBounds(100, 140, 100, 25);
        add(quantity);

        price = new JTextField();
        price.setBounds(100, 200, 100, 25);
        add(price);

        l_name = new JLabel();
        l_name.setText("Item Name : ");
        l_name.setBounds(20, 82, l_name.getPreferredSize().width, l_name.getPreferredSize().height);
        add(l_name);

        l_quantity = new JLabel();
        l_quantity.setText("Quantity : ");
        l_quantity.setBounds(20, 142, l_quantity.getPreferredSize().width, l_quantity.getPreferredSize().height);
        add(l_quantity);

        l_price = new JLabel();
        l_price.setText("Price : ");
        l_price.setBounds(20, 202, l_price.getPreferredSize().width, l_price.getPreferredSize().height);
        add(l_price);

        l_catagory  = new JLabel();
        l_catagory.setText("Categories : ");
        l_catagory.setBounds(20, 262, l_catagory.getPreferredSize().width, l_catagory.getPreferredSize().height);
        add(l_catagory);

        ButtonHandler buttonHandler = new ButtonHandler();


        addB = new JButton("Add New Item");
        addB.setBounds(250, 120, 120, 70);
        addB.addActionListener(buttonHandler);
        add(addB);

        addCatB = new JButton("Add New Categories");
        addCatB.setBounds(100, 310, 150, 30);
        addCatB.addActionListener(buttonHandler);
        add(addCatB);


    }
    public class ButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == addCatB){
                catFrame = new JFrame();
                catFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                catFrame.setBounds(180, 50,200,200);
                catFrame.setLayout(null);
                catFrame.setVisible(true);

                JButton jButton = new JButton("Add");
                jButton.setBounds(50,90,80,30);
                catFrame.add(jButton);

                final JTextField jtf = new JTextField();
                jtf.setBounds(30,40,120,30);
                catFrame.add(jtf);

                jButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String[] option = new String[]{"Yes","No"};
                        if(!jtf.getText().equals("")) {
                            int add = JOptionPane.showOptionDialog(null, "Are you sure you want to add this Categories", "Add Categories", JOptionPane.NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, option, null);

                            if (add == 0) {
                                FrontPage.DataBase.insertRow("INSERT INTO `Categories`(`Categories`) VALUES ( '" + jtf.getText() + "');");
                                Categories.removeAllItems();
                                try {
                                    int ind=0;
                                    ResultSet myRs = FrontPage.DataBase.getQueryResult("SELECT * FROM Categories");
                                    while (myRs.next()){
                                        ind++;
                                    }
                                    myRs = FrontPage.DataBase.getQueryResult("SELECT * FROM Categories");
                                    str = new String[ind];
                                    ind=0;
                                    while (myRs.next()){
                                        Categories.addItem(myRs.getString("Categories"));
                                    }
                                }
                                catch (Exception ex){
                                    JOptionPane.showMessageDialog(null,"Database - Categories Error");
                                }
                            }
                        }
                        else
                            JOptionPane.showMessageDialog(null, "Please Enter Categories Name", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

            }
            else if(e.getSource() == addB) {
                boolean cnd1=true,cnd2=false;

                if (name.getText().equals("") || quantity.getText().equals("") || price.getText().equals("")) {
                    cnd1 = false;
                    JOptionPane.showMessageDialog(null, "Make sure no field is left Emptied", "Input Error", JOptionPane.ERROR_MESSAGE);
                } else if (lhand.getIndex() == 0) {
                    cnd1 = false;
                    //cnd2 = true;
                    JOptionPane.showMessageDialog(null, "Please Select a Categories", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    Integer.parseInt(quantity.getText());
                    Integer.parseInt(price.getText());
                    cnd2 = true;
                } catch (Exception ex) {
                    cnd2 = false;
                    JOptionPane.showMessageDialog(null, "Make sure Quantity/Price Field is a number", "Input Error", JOptionPane.ERROR_MESSAGE);
                }

                if(cnd1 && cnd2) {
                    String[] option = new String[]{"Yes","No"};
                    int add = JOptionPane.showOptionDialog(null, "Are you sure you want to add this Item", "Add Categories", JOptionPane.NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, option, null);

                    if (add == 0) {
                        String[] row = new String[5];
                        row[1] = name.getText();
                        row[2] = quantity.getText();
                        row[3] = price.getText();
                        row[4] = Categories.getSelectedItem().toString();

                        FrontPage.DataBase.insertRow("INSERT INTO `GeneralItems`(`id`,`itemname`,`quantity`,`price`,`Categories`) VALUES ( NULL,'" + row[1] + "','" + row[2] + "','" + row[3] + "','" + row[4] + "')");
                        JOptionPane.showMessageDialog(null, "Item Added", "Item Addition", JOptionPane.INFORMATION_MESSAGE);
                        im.showItems();
                    }

                }
            }
        }
    }

    public class ListHandler implements ItemListener{
        int ind=0;
        @Override
        public void itemStateChanged(ItemEvent e) {
            ind = Categories.getSelectedIndex();
        }
        public int getIndex(){
            return ind;
        }
    }
}
