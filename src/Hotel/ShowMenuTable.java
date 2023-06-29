package Hotel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class ShowMenuTable {
	private static DefaultTableModel model = null;


	public static void main(String[] args) {


		JFrame frame = new JFrame("Hotel Management System");
		JPanel panel = new JPanel();


		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Show Menu", TitledBorder.CENTER,
				TitledBorder.TOP));

		model = new DefaultTableModel();
		JTable table = new JTable(model);
		model.addColumn("Number");
		model.addColumn("Menu Name");
		model.addColumn("Price");

		JButton insert = new JButton("Insert Menu");
		insert.setBounds(400,460, 110, 30);
		frame.add(insert);

		JButton showOrder = new JButton("Show Order Info");		
		showOrder.setBounds(700, 540, 130, 30);
		frame.add(showOrder);

		JButton edit = new JButton("Update Menu");
		edit.setBounds(600, 460, 120, 30);
		frame.add(edit);

		JButton deleteMenu = new JButton("Delete Menu");
		deleteMenu.setBounds(800,460, 110, 30);
		frame.add(deleteMenu);

		JButton createOrder = new JButton("Create Order");
		createOrder.setBounds(500, 540, 120, 30);
		frame.add(createOrder);    

		edit.addActionListener(null);

		panel.add(new JScrollPane(table));
		frame.add(panel);
		frame.setSize(1650, 1080);
		frame.setVisible(true);

		table.setRowSelectionAllowed(true);                                //Row select keleli Allowed

//-------------------------Create Order----------------------------	

		createOrder.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {

				CreateOrder orders = new CreateOrder();

				orders.order();

			}						
		});


//---------------------------Show order-------------------------------


		showOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

								ShowOrders orders = new ShowOrders();
								orders.showOrders();
			}
		});     


//----------------------------Delete Menu------------------------------
		

		deleteMenu.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {

				int num =table.getSelectedRow();

				String menuName = (String)table.getValueAt(num, 1);          //Type casting

				DeleteMenu obj = new DeleteMenu();
				obj.deleteMenu(menuName);
			}
		});	

//----------------------------Insert Menu------------------------------------

		insert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				MenuForm obj = new MenuForm();                   // create menuForm object and call insertMenu method
				obj.insertMenu();

			}
		});

//---------------------------Edit Menu----------------------------------

		edit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				int num =table.getSelectedRow();
				//	System.out.println(num);

				String menuName = (String)table.getValueAt(num, 1);          //Type casting

				System.out.println(menuName);
				//				System.out.println(num); s

				String menuPrice = (String)table.getValueAt(num, 2);
				System.out.println(menuPrice);


				EditMenu edit = new EditMenu(); 
				edit.udpadeMenu(menuName, menuPrice );

			}                                                                                  //
		});


//----------------------Take Menu List--------------------------------

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel","root","1234");

			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery("select * from menu order by menuname");

			String number;
			int i=1;

			while(rs.next()) {

				String menuName = rs.getString(2); 
				String price = rs.getString(3);
				int id = rs.getInt(1);

				number = Integer.toString(i);


				String [] row = new String[3];

				row[0]=number;
				row[1]=menuName;
				row[2]=price;

				model.addRow(row);

				i++;

			} 
		}		
		catch(Exception e) {
			System.out.println(e);
		}
	}

//-----------------------Referesh Table---------------------------------

	public static void refreshTable() {

		int numOfRows = ShowMenuTable.model.getRowCount();

		while(numOfRows >=1 ) {

			ShowMenuTable.model.removeRow(0);

			numOfRows--;
        }
		
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel","root","1234");

			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery("select * from menu order by menuname");


			String number;
			int i=1;

			while(rs.next()) {

				String mName = rs.getString(2);
				String mPrice = rs.getString(3);
				int idd = rs.getInt(1);

				number = Integer.toString(i);

				String [] row = new String[3];
				row[0]=number;
				row[1]=mName;
				row[2]=mPrice;


				model.addRow(row);

				i++;
			}
		}
		catch (Exception w) {
			System.out.println(w);

		}
	}
}















