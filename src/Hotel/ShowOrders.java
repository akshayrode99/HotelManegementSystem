package Hotel;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;


public class ShowOrders {
	public static DefaultTableModel model = null;

	public void showOrders() {
		
		JFrame frame = new JFrame("Order Details");
		frame.setSize(500, 550);

		JPanel panel = new JPanel();

		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Orders Info", TitledBorder.CENTER,
				TitledBorder.TOP));

		model = new DefaultTableModel();
		JTable table = new JTable(model);
		model.addColumn("Order Id");
		model.addColumn("Table  Name");
		model.addColumn("Total Bill");

		JButton finalBill = new JButton("Orders Details");                     
		finalBill.setBounds(200, 450, 120, 30);
		frame.add(finalBill);

		panel.add(new JScrollPane(table));

		frame.add(panel);
		frame.setVisible(true);
		frame.setLocation(300, 100);
		frame.setLayout(null);

		ArrayList<String>ordersId = new ArrayList<>();
		String[]row = new String[3];

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel","root","1234");

			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery("select * from orders order by  id desc ");

			while(rs.next()) { 

				String orderId = rs.getString(1);
				String tableName = rs.getString(2); 

				ordersId.add(orderId);
				row[1]=tableName;
			}

			int num = ordersId.size();
			Statement sta = con.createStatement();

			for(int i = 0 ; i<num ; i++) {

				String n =   ordersId.get(i);
				 
				row[0]=n;

				String detail= "select menu.menuname, menu.price, orderinfo.quantity  from orderinfo join menu on orderinfo.menuid = menu.id where orderid = '"+n+"'";    

				ResultSet sr = sta.executeQuery(detail);

				int sum = 0;
				int totalBill = 0;
				while(sr.next()) {

					String menuName = sr.getString(1); 
					String quantity = sr.getString(2); 
					String price = sr.getString(3);

					int quant = Integer.parseInt(quantity);                           //convert String to int.
					int cost = Integer.parseInt(price);
					
					sum = quant*cost;
					totalBill = totalBill+sum;

				}
				String grandTotal = Integer.toString(totalBill);
				row[2]= grandTotal;
				model.addRow(row);
				
			}
		}
		
		catch(Exception w) {

			System.out.println(w);
		} 
				

//-------------------------Final Bill-----------------------------------
		
		
 		finalBill.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				int num = table.getSelectedRow();

				String orderId = (String)table.getValueAt(num, 0);
				
				System.out.println(orderId);
				
				
				FinalBill bill = new FinalBill();
				
			    bill.findBill(orderId);
			    
			    frame.dispose();								
			}
		}); 
	}
}
