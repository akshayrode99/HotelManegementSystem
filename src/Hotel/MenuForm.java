package Hotel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MenuForm {
	JTextField menuTextBox, priceTextBox;
	
	public void insertMenu() {
		JFrame frame = new JFrame("Insert Menu");
		frame.setSize(450, 350);
		frame.setLocation(400, 200);
		frame.setLayout(null);


		JLabel label = new JLabel("Create Menu");
		label.setBounds(150, 0, 100, 100);
		frame.add(label);

		JLabel name = new JLabel("Menu Name"); 
		name.setBounds(10, 70, 100, 100);
		frame.add(name);

		menuTextBox = new JTextField();
		menuTextBox.setBounds(100, 110, 120, 25);
		frame.add(menuTextBox);

		JLabel price = new JLabel("Price");
		price.setBounds(10, 110, 100, 100);
		frame.add(price);

		priceTextBox = new JTextField();
		priceTextBox.setBounds(100, 150, 120, 25);
		frame.add(priceTextBox);

		JButton submit = new JButton("Submit");
		submit.setBounds(180, 250, 100, 25);
		frame.add(submit);

		frame.setVisible(true);
		
//-------------------sbmit ActionListner----------------------------		

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				onSubmit();
			}
			
			public void onSubmit() {
				
				String menuName = menuTextBox.getText();
				int price = Integer.parseInt(priceTextBox.getText());
				                                      
				frame.dispose();			                                                 

				Menu menu = new Menu();
				menu.insert(menuName, price); 
				
				menuTextBox.setText("");
				priceTextBox.setText(" ");
			}  
		});
	}
}

