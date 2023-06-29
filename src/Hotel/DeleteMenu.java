package Hotel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class DeleteMenu {

	public void deleteMenu(String menuName) {

		JFrame frame = new JFrame("Delete Menu");
		frame.setSize(300, 200);
		frame.setLocation(450, 200);
		frame.setLayout(null);

		JLabel label = new JLabel("Do you want delete menu");
		label.setBounds(10, 00, 200, 100);
		frame.add(label);

		JTextField menu = new JTextField();
		menu.setBounds(180, 40, 90, 25);
		frame.add(menu);
		menu.setText(menuName);

		JButton yes = new JButton("Yes");
		yes.setBounds(50, 100, 60, 25);
		frame.add(yes);

		JButton no = new JButton("No");
		no.setBounds(120, 100, 55, 25);
		frame.add(no);

		frame.setVisible(true);

		
//--------------No ActionListener--------------------------
		
		
		no.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {

				frame.dispose();

			}
		});


//-----------------Yes ActionListener----------------------
		
		
		yes.addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						try {
							Class.forName("com.mysql.jdbc.Driver");

							Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel","root","1234");

							Statement st = con.createStatement();

							String delete = "delete from menu where menuname = '"+menuName+"'";

							st.executeUpdate(delete);

							frame.dispose();

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







