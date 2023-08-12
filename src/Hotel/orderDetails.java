package Hotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class orderDetails {

	public static DefaultTableModel model = null;
   
	

	public void orderDetail(String orderIdd) {

		JFrame frame = new JFrame("Orders Bill Detail");
		JPanel panel = new JPanel();


		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Order Details", TitledBorder.CENTER,
				TitledBorder.TOP));

		model = new DefaultTableModel();
		JTable table = new JTable(model);
		model.addColumn("Menu Name");
		model.addColumn("Quantity");

		panel.add(new JScrollPane(table));
		frame.add(panel);
		frame.setSize(550, 550);
		frame.setVisible(true);
		frame.setLayout(null);
		frame.setLocation(500, 200);

		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel","root","1234");
			Statement st = con.createStatement();

			String order = "select * from orderinfo where orderid = '"+orderIdd+"'";


			ResultSet rs = st.executeQuery(order);
			
            System.out.println(rs);

			Statement sta = con.createStatement();

			String detail= "select menu.menuname,orderinfo.quantity  from orderinfo join menu on orderinfo.menuid = menu.id where orderid = '"+orderIdd+"'";    

			ResultSet r = sta.executeQuery(detail);
			
			while(r.next()) {
				
				String menuName = r.getString(1);
				String quantity = r.getString(2);
				
				String[]row = new String[2];
				row[0] = menuName;
				row[1] = quantity;

				model.addRow(row);
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
