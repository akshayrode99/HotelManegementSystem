package Hotel;
import java.sql.*;
import java.util.*;
import java.util.Scanner;
import java.util.Map.Entry;

//import Map.Person;

public class Menu   {

	public void insert(String name,int price) {
		//	while(true) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel","root","1234");
			Statement st = con.createStatement();


		//	System.out.println("Enter menu name");
		//	String	name = new Scanner(System.in).next();

		//	System.out.println("Enter menu Price");
		//	int	price = new Scanner(System.in).nextInt();

			String q = "insert into menu(menuname , price) values('"+name+"','"+price+"')";
			//	System.out.println(q);
			st.executeUpdate(q);
			//	st.executeUpdate("insert into menu values ('"+numm+"','"+name+"','"+price+"')");

			System.out.println("Menu inserted Successfully");

		}
		catch(Exception e) {
			System.out.println(e);
		}
		ShowMenuTable obj = new ShowMenuTable();
		obj.refreshTable();

	}

	//--------------------------------------------------------------------------------------------------------------


	public void show() {

		String n;
		List<String> list = new ArrayList<String>();


		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel","root","1234");

			Statement st = con.createStatement();

			//			ResultSet rs = st.executeQuery("select id ,menuname , price from menu");
			ResultSet rs = st.executeQuery("select * from menu order by menuname");

			System.out.println("id , menuname , price");
						System.out.println("---------------------------");


			while(rs.next()) {
				System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getInt(3));    //variable create and then syso

								System.out.println("---------------------------------");

				n = rs.getString(2);


				list.add(n);	


			}
			System.out.println();
			System.out.println(list);

			con.close();

		}
		catch(Exception e) {  
			System.out.println(e);
		}
	}
	

//------------------------------------------------------------------------------------------------------------
	

	public void order() throws SQLException {                                  //order method

		Map<String,Integer>map = new HashMap<>();          //create map
		Connection con  = null;
		try{
			while(true) {
				System.out.println("do you want to order?");
				System.out.println("1. Yes");
				System.out.println("2. No");

				int order = new Scanner(System.in).nextInt();


				if(order==1) {
					System.out.println("Enter Menu?");

					String menu =new Scanner(System.in).next();

					System.out.println("Quantity?");

					int quantity =	new Scanner(System.in).nextInt();

					map.put(menu, quantity);

					System.out.println(map);


				}              //if end

				if(order==2) {

					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel","root","1234");    //create Connection
					Statement st = con.createStatement();

					String orders = "insert into orders(tablename)values('T1')";           // insert order entry in orders table 
					st.executeUpdate(orders);                                             
				//	System.out.println(orders);   
					
					
//-----------------------------------------------------------------------------------------------------------------	
					
					
					String max="select max(id) from orders";                  //order table cha id  execute(max)

					ResultSet r = st.executeQuery(max);

					int maxId= 0;

					while(r.next()) {
			//			System.out.println(r.getInt(1));

						maxId= r.getInt(1);
			//			System.out.println(maxId);
					}                                            //while end / maxId variable madhe id thevala
					

//-----------------------------------------------------------------------------------------------------------------
					
					
					while (true) {                                             //loop Start


						for(Entry<String, Integer> val : map.entrySet()) {               //Map madhe create zaleli entry one by one ghetali name and quantity variable madhe

							String name = val.getKey();
							int	 quantity = val.getValue();
							System.out.println(name+" "+quantity);




//----------------------------------------------------------------------------------------------------------------


							String x = "select id from menu where menuname = '"+name+"'";  // menu name varun menu id execute

							System.out.println(x);
							ResultSet rs = st.executeQuery(x);

							int menuId=0;

							while(rs.next()) {
								System.out.println(rs.getInt(1));

								menuId = rs.getInt(1);                          //menuId variable madhe menuid thevala 
							}  //while end last while
							//			rs.close();
							
							
//-----------------------------------------------------------------------------------------------------------------
							

							Statement sta = con.createStatement();

							String s = "insert into orderinfo(menuid,quantity,orderid)values("+menuId+","+quantity+","+maxId+")";    // orderinfo table madhe insert menuid,quantity,maxId.

							System.out.println(s);

							sta.executeUpdate(s);


//------------------------------------------------------------------------------------------------------------------	
							
							
							Statement b = con.createStatement();
							String bill= "select menu.menuname, menu.price, orderinfo.quantity from orderinfo join menu on orderinfo.menuid = menu.id where orderid = '"+maxId+"'";                                                        
							ResultSet z = b.executeQuery(bill);
							//		b.executeQuery(bill);
							int total=0;
							while(z.next()) {
								System.out.println(z.getString(1)+" "+z.getInt(2)+" "+z.getInt(3));

								int price = z.getInt(2);
								int quanti = z.getInt(3);

								int sum=0;

								sum =price*quanti;
					//			System.out.println(sum);

								total =total+sum;

							}
				//			System.out.println("Final Bill is =" +total);
				//			System.out.println("~Thanks -----> Visit agian~");
							
							
//------------------------------------------------------------------------------------------------------------------	
							

						}   //for end
						break;
					}    //while end
					break;
				}        //if end

			}            //while end

		}                //try end

		catch(Exception e) {
			System.out.println(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}                //order end
	
	
//-----------------------------------------------------------------------------------------------------------------

	public void bill() {
		try {
			
			System.out.println("Enter orderID");
			int orderId = new Scanner(System.in).nextInt();
			System.out.println();

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel","root","1234");
			Statement st = con.createStatement();
			
			String order = "select * from orderinfo where orderid = '"+orderId+"'";
			
//			System.out.println(order);
			
			ResultSet rs = st.executeQuery(order);
			
	   
			Statement sta = con.createStatement();
			
			String findBill= "select menu.menuname,orderinfo.quantity, menu.price  from orderinfo join menu on orderinfo.menuid = menu.id where orderid = '"+orderId+"'";    
			
			ResultSet r = sta.executeQuery(findBill);
		
			int totalBill=0;
			System.out.println("Sagar Hotel");
			System.out.println("Menu| Quantity| price");
			
			while(r.next()) {
                
				String name=r.getString(1);
				int quanti = r.getInt(2);
				int price = r.getInt(3);

				int sum=0;

				sum = price*quanti;
				System.out.println(name+" "+quanti+" "+sum);

				totalBill =totalBill+sum;

          
			}
			System.out.println("~~*****************************************~~");
			System.out.println("Order No = " +orderId+ "  Total Bill is ="+totalBill);
			System.out.println("              Thank You               ");
			System.out.println();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}




//-----------------------------------------------------------------------------------------------------------


	public static void main(String[] args) throws SQLException {

		while(true) {

			System.out.println("What do you want to do?");
			System.out.println();
			System.out.println("Please select option");
			System.out.println("1. Insert menu");
			System.out.println("2. Show menu card");
			System.out.println("3. Order Menu");
			System.out.println("4. Bill");
			System.out.println("5. Exit");

			System.out.println();

			Menu obj = new Menu();


			int num = new Scanner(System.in).nextInt();

			if(num==1) {
		//		obj.insert();
			}
			if(num==2) {
				obj.show();
			}

			if(num==3) {
				obj.order();
			}


			if(num==4) {
				obj.bill();
			}


			if(num==5) {
				System.out.println("Bby");
				break;
			}		
			if(num>5) {
				System.out.println("Enter Rong Entry");
			}
		}
	} 
}




