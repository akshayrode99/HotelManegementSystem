package Hotel;

import Hotel.OrderItoms;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;

public class CreateOrder {
	public static DefaultTableModel model = null;

	JTextField menuTextBox, quantityTextBox;
	List<String> menuu = new ArrayList<String>();
	List<OrderItoms> list = new ArrayList<>();



	public void order() {

//----------------- Populating menu names from db------------------------

		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "1234");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select  * from menu");

			while (rs.next()) {

				String menuName = rs.getString(2);
				menuu.add(menuName);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		// part of  UI 
		String[] menu = menuu.toArray(new String[menuu.size()]); // aarayList to array convert

		JFrame frame = new JFrame("Create Order");
		
		JComboBox box = new JComboBox(menu);
		box.setBounds(600, 510, 120, 30);
		frame.add(box);
		box.addActionListener(box);
		box.getSelectedIndex(); 

		
		JLabel name = new JLabel("Menu Name : ");
		name.setBounds(500, 510, 120, 30);
		frame.add(name);

		JLabel quantity = new JLabel("Quantity :");
		quantity.setBounds(500, 550, 120, 30);
		frame.add(quantity);

		menuTextBox = new JTextField();
		menuTextBox.setBounds(600, 550, 120, 30);
		frame.add(menuTextBox);

		JButton save = new JButton("Save");
		save.setBounds(600, 600, 70, 30);
		frame.add(save);
		frame.setLocation(100, 0);

//---------------------Panel --------------------

		JPanel panel = new JPanel();

		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Order Details",
				TitledBorder.CENTER, TitledBorder.TOP));

		model = new DefaultTableModel();
		JTable table = new JTable(model);
		model.addColumn("Menu Name");
		model.addColumn("Quantity");

		JButton submit = new JButton("Submit");
		submit.setBounds(250, 450, 100, 30);
		frame.add(submit);

		panel.add(new JScrollPane(table));
		frame.add(panel);
		frame.setSize(550, 550);
		frame.setVisible(true);

		
		frame.setLayout(null);
		frame.setSize(800, 800);
		frame.setVisible(true);



//----------------------- Submit ActionListener--------------------------
		
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int num = list.size();
				
				for(int i=0 ; i<num ; i++) {
					
					OrderItoms P = list.get(i);
					
					String A = P.getMenuName();
					String B = P.getQuantity();
					
					System.out.println(A+" "+B);
					frame.dispose();
				}

				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection	con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel","root","1234");    //create Connection
					Statement st = con.createStatement();

								String orders = "insert into orders(tablename)values('T1')";           // insert order entry in orders table 
								st.executeUpdate(orders);                                             



//---------------------------Find maxId from orders---------------------------------


					String max="select max(id) from orders";                  //order table cha id  execute(max)

					ResultSet r = st.executeQuery(max);

					int maxId= 0;

					while(r.next()) {

						maxId= r.getInt(1);

					}                                           

					int index = 0;
					
					while(index < list.size()) {
						
						OrderItoms obj = list.get(index);

						String name =  obj.getMenuName();
						String quanti = obj.getQuantity();

						index++;

						String x = "select id from menu where menuname = '"+name+"'";       // menu name varun menu id execute

						ResultSet rs = st.executeQuery(x);

						int menuId=0;

						while(rs.next()) {
							
							menuId = rs.getInt(1);                                          //menuId variable madhe menuid thevala 

						}  
						Statement sta = con.createStatement();

						String s = "insert into orderinfo(menuid,quantity,orderid)values("+menuId+","+quanti+","+maxId+")";    // orderinfo table madhe insert menuid,quantity,maxId.

						sta.executeUpdate(s);
						
						frame.dispose();

					}
				} catch (Exception i) {
					System.out.println(i);
				}
			}
		});


//-----------------------Save ActionListener----------------------------	

		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String menuName = (String) box.getSelectedItem();

				String quantity = (String) menuTextBox.getText();

				String order[] = new String[2];

				order[0] = menuName;
				order[1] = quantity;

				model.addRow(order);
				
				OrderItoms obj = new OrderItoms();

				obj.setMenuName(menuName);
				obj.setQuantity(quantity);

				list.add(obj);
				
			}
		});	
	}
}
