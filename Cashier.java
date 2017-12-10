import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;

public class Cashier {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTable table;
	Connection conn=null;
	 PreparedStatement pstmt=null;
	 ResultSet rs=null;
	 Statement stmt=null;
	 private JTextField textField_3;
	 int sum=0;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cashier window = new Cashier();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public Cashier(){
		
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(){
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setSize(1650, 1080);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
					
		JLabel lblNewLabel = new JLabel("BILL ID");
		lblNewLabel.setBounds(12, 38, 120, 22);
		lblNewLabel.setFont(new Font("Serif",Font.BOLD,20));
		panel.add(lblNewLabel);
				
		textField = new JTextField();
		textField.setBounds(150, 38, 120, 28);
		textField.setFont(new Font("Serif",Font.BOLD,20));
		panel.add(textField);
		textField.setColumns(10);
		
		
		JLabel lblCustomerName = new JLabel("CUSTOMER NAME");
		lblCustomerName.setBounds(480, 38, 250, 22);
		lblCustomerName.setFont(new Font("Serif",Font.BOLD,20));
		panel.add(lblCustomerName);
		
		textField_1 = new JTextField();
		textField_1.setBounds(735, 38, 200, 28);
		textField_1.setFont(new Font("Serif",Font.BOLD,20));
		panel.add(textField_1);
		textField_1.setColumns(10);		

		JLabel lblSelectItems = new JLabel("Items");
		lblSelectItems.setBounds(12, 102, 80, 22);
		lblSelectItems.setFont(new Font("Serif",Font.BOLD,20));
		panel.add(lblSelectItems);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(150, 102, 200, 28);
		comboBox.setFont(new Font("Serif",Font.BOLD,20));
		panel.add(comboBox);
		comboBox.addItem(" ");
		comboBox.addItem("Cake");
		comboBox.addItem("Pastry");
		comboBox.addItem("Tart");
		comboBox.addItem("Cookies");
		comboBox.addItem("Bread");
		comboBox.addItem("Buns");

		
		
		JLabel lblQty = new JLabel("QUANTITY");
		lblQty.setBounds(480, 102, 150, 22);
		lblQty.setFont(new Font("Serif",Font.BOLD,20));
		panel.add(lblQty);
		
		textField_2 = new JTextField();
		textField_2.setBounds(735, 102, 120, 28);
		textField_2.setFont(new Font("Serif",Font.BOLD,20));
		panel.add(textField_2);
		textField_2.setColumns(10);
			
		Object[] columns={"ItemName","Qty","UnitPrice","Total"};
		DefaultTableModel dm=new DefaultTableModel();
		dm.setColumnIdentifiers(columns);
		JTable table=new JTable();
		table.setModel(dm);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 252, 923, 455);
		panel.add(scrollPane);
		scrollPane.setViewportView(table);
		Font font=new Font("",1,22);
		table.setFont(font);
		table.setRowHeight(30);
			
		JLabel lblNewLabel_1 = new JLabel("TOTAL PRICE :");
		lblNewLabel_1.setBounds(620, 720, 200, 22);
		lblNewLabel_1.setFont(new Font("Serif",Font.BOLD,20));
		panel.add(lblNewLabel_1);
		
		textField_3 = new JTextField();
		textField_3.setBounds(815, 720, 120, 28);
		textField_3.setFont(new Font("Serif",Font.BOLD,20));
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		JButton addbtn = new JButton("ADD");
		addbtn.setBounds(1043, 38, 117, 25);
		addbtn.setFont(new Font("Serif",Font.BOLD,20));
		addbtn.setSize(138, 30);
		panel.add(addbtn);
		
		JButton delbtn = new JButton("DELETE");
		delbtn.setBounds(1043, 102, 117, 25);
		delbtn.setFont(new Font("Serif",Font.BOLD,20));
		delbtn.setSize(138, 30);
		panel.add(delbtn);
		
		dm.addTableModelListener(new TableModelListener() {

		    @Override
		    public void tableChanged(TableModelEvent e) {
		    	//Calculating Total;	
				int cnt=0;
				sum=0;
				cnt=table.getModel().getRowCount();
				
				for(int i=0;i<cnt;i++){
					
					sum=sum+Integer.parseInt(String.valueOf(table.getValueAt(i,3)));					
				}
		        textField_3.setText(String.valueOf(sum));
		      
		    }
		});

		//---------------------------------------------------------------------------------------
		
		 final String JDBC_DRIVER="com.mysql.jdbc.Driver";
	     final String DB_URL="jdbc:mysql://127.0.0.1:3306/aditi";
	     final String user="root";
	     final String pswd="adi";
	     
	try{
	    
		 Class.forName(JDBC_DRIVER);
		 conn=DriverManager.getConnection(DB_URL,user,pswd);
	}
	catch(Exception e)
	{
		System.out.println("sql exception");
	}
		
		
		//---------------------------------------------------------------------------------ADD----------
		
		Object [] row=new Object[4];
		addbtn.addActionListener(new ActionListener(){
			
			
			@Override
			public void actionPerformed(ActionEvent e){
				try{
				row[0]=comboBox.getSelectedItem();			
				row[1]=textField_2.getText();
				int p=0;
				String query="select price from product where pname=(?);";
				
					pstmt=conn.prepareStatement(query);
					String str=(String)comboBox.getSelectedItem();
					pstmt.setString(1, str);
					rs=pstmt.executeQuery();
					rs.first();
					p=rs.getInt(1);
					row[2]=p;
						
				int y=p*(Integer.parseInt(String.valueOf(textField_2.getText())));
				row[3]=y;
				dm.addRow(row);
				
			
				
				
				pstmt=conn.prepareStatement("insert into bill values(?,?,?,?,?);");
				int billno=Integer.parseInt(textField.getText());
				pstmt.setInt(1,billno);
				String cname=textField_1.getText();
				pstmt.setString(2,cname);
				pstmt.setString(3,(String)row[0]);
				int qty=Integer.parseInt(textField_2.getText());
				pstmt.setInt(4,qty);
				pstmt.setInt(5, y);
				pstmt.executeUpdate();
				
				textField_2.setText("");
				
			
			   
				
				}
			
			catch(Exception f)
			{
				//f.printStackTrace();
			}
			}
		});
	
		//------------------------------Delete----------------
		
		delbtn.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				
					int i=table.getSelectedRow();
					if(i>=0)
					{
						
						try{
						pstmt=conn.prepareStatement("delete from bill where bid=(?) and iname=(?);");
						int billno=Integer.parseInt(textField.getText());
						pstmt.setInt(1,billno);
						String item=(String)table.getModel().getValueAt(i,0);
						pstmt.setString(2, item);
						pstmt.executeUpdate();

						}
						catch(Exception l)
						{
							//l.printStackTrace();
						}
						dm.removeRow(i);
					}
					
					}
				
				});
	}
}
