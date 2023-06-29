package Hotel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
public class EditMenu  {

	public void udpadeMenu(String menuName,String menuPrice) {

		JFrame frame = new JFrame("Edit Menu");
		frame.setSize(300, 300);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setLocation(500, 200);


		JLabel name = new JLabel("Menu Name ");
		name.setBounds(10, 10, 100, 100);
		frame.add(name);


		JTextField menu = new JTextField();
		menu.setBounds(100,50, 120, 25);
		frame.add(menu);
		menu.setText(menuName);                             //setmenuname from selected table row


		JLabel price = new JLabel("Menu Price ");
		price.setBounds(10, 50, 100, 100);
		frame.add(price);  


		JTextField value = new JTextField();
		value.setBounds(100, 90, 120, 25);
		value.setText(menuPrice);                        //setmenuPrice from selected table row
		frame.add(value);


		JButton update = new JButton("Update");
		update.setBounds(100, 150, 100, 25);
		frame.add(update);


//--------------------Update ActionListener---------------------------

		update.addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						String name = menu.getText();
						String menuPrice = value.getText();

						frame.dispose();

						try {

							Class.forName("com.mysql.jdbc.Driver");

							Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel","root","1234");

							Statement st = con.createStatement();

							String update = "update menu set menuname = '"+name+"',price = '"+menuPrice+"' where menuname='"+menuName+"'";

							st.executeUpdate(update);

						}
						catch(Exception i) {
							System.out.println(i);
						}

						ShowMenuTable obj = new ShowMenuTable();
						obj.refreshTable();
					}
				});		
	}

}





