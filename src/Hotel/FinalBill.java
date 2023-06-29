package Hotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class FinalBill {

	public static DefaultTableModel model = null;


	public void findBill(String orderId) {

		JFrame frame = new JFrame("Order Bill Detail");
		JPanel panel = new JPanel();


		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Orderd MenuList", TitledBorder.CENTER,
				TitledBorder.TOP));

		model = new DefaultTableModel();
		JTable table = new JTable(model);
		model.addColumn("Menu Name");
		model.addColumn("Quantity");
		model.addColumn("Menu Price");
		model.addColumn("sum");
		
		JLabel label = new JLabel("Total Bill is");
		label.setBounds(150, 430, 100, 100);
		frame.add(label);
		
		JTextField bill = new JTextField();
		bill.setBounds(250, 465, 70, 30);
		frame.add(bill);

		panel.add(new JScrollPane(table));
		frame.add(panel);
		frame.setSize(500, 550);
		frame.setVisible(true);
		frame.setLocation(500, 100);
		frame.setLayout(null);


		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel","root","1234");			
			Statement sta = con.createStatement();

			String detail= "select menu.menuname, menu.price, orderinfo.quantity  from orderinfo join menu on orderinfo.menuid = menu.id where orderid = '"+orderId+"'";    

			ResultSet r = sta.executeQuery(detail);
						
            int sum = 0;
            int totalBill = 0;  
            
			while(r.next()) {
				
				String menuName = r.getString(1); 
				String quantity = r.getString(2); 
				String price = r.getString(3);
				

				String[]row = new String[4];  
				row[0] = menuName;
				row[1] = price;
				row[2] = quantity;
				
	             
	            int quant = Integer.parseInt(quantity);                              //convert String to int.
	            int pricee = Integer.parseInt(price);
	            sum=quant*pricee;
	            
	            String multi = Integer.toString(sum);                                 //convert int to String	        
	            row[3] = multi;
	            model.addRow(row);

					            	            
	            System.out.println(menuName+" "+quant+" "+pricee);
	            totalBill=totalBill+sum;    
	            
	            String t = Integer.toString(totalBill);
	            
			    bill.setText(t);
			    
			}
			
			System.out.println("Order No = " +orderId+ "  Total Bill is ="+totalBill);
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
} 
    


