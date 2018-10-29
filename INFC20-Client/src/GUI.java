import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

	private Controller controller;
	private JPanel contentPane;
	
	JPanel loginPanel;
	JPanel regPanel;
	
	private JTable tableData;
	private DefaultTableModel modelData;

	//******************** CONSTRUCTOR ********************//
	public GUI(Controller controller) {
		this.controller = controller;
		initialize();
		loginPanel();
		setVisible(true);
	}

	//******************** INITIALIZE CONTENTS OF THE FRAME ********************//
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 200, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		
		//******************** LOGIN LAYOUT ********************//
		loginPanel = new JPanel();	
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
		
		JLabel lblAptNbr = new JLabel("Apartment Number:");
		String[] listApts = {"1101", "1102", "1103"};
		JComboBox<String> cBoxApts = new JComboBox<String>();
		loginPanel.add(lblAptNbr);
		loginPanel.add(cBoxApts);
		
		JLabel lblPassword = new JLabel("Password:");
		JTextField txtPassword = new JTextField();
		loginPanel.add(lblPassword);
		loginPanel.add(txtPassword);
		
		JButton btnLogin = new JButton("Login");
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
			    registerPanel();
			  } 
			} );
		loginPanel.add(btnLogin);
		loginPanel.add(btnRegister);
		
		//******************** REGISTER LAYOUT ********************//
		regPanel = new JPanel();	
		regPanel.setLayout(new BoxLayout(regPanel, BoxLayout.Y_AXIS));
		
		JLabel lblAptNbr2 = new JLabel("Apartment Number:");
		String[] listApts2 = {"1101", "1102", "1103"};
		JComboBox<String> cBoxApts2 = new JComboBox<String>();
		regPanel.add(lblAptNbr2);
		regPanel.add(cBoxApts2);
		
		JLabel lblPassword2 = new JLabel("Password:");
		JTextField txtPassword2 = new JTextField();
		regPanel.add(lblPassword2);
		regPanel.add(txtPassword2);
		
		JButton btnRegister2 = new JButton("Register");
		regPanel.add(btnRegister2);
	}
	
	private void loginPanel() {
		contentPane.removeAll();
		contentPane.add(loginPanel);
	}
	
	private void registerPanel() {
		contentPane.remove(loginPanel);
		//contentPane.add(regPanel);
	}
	
	//******************** FUNCTION TO CLEAR LIST OF DATA ********************//
	private void clearList() {
		int rowCount = modelData.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
		    modelData.removeRow(i);
		}
	}
	
	//******************** FUNCTION TO SET COLUMN SIZES AND NAMES ********************//
	private void updateColumns(String[] names) {
		modelData.setColumnCount(names.length);
		for (int i = 0; i < names.length; i++) {
			tableData.getTableHeader().getColumnModel().getColumn(i).setHeaderValue(names[i]);
			tableData.getTableHeader().getColumnModel().getColumn(i).setMinWidth(100);
			tableData.getTableHeader().repaint();
		}
	}

	//******************** FUNCTION TO FILL LIST WITH DATA ********************//
	public void showData(String[][] data) {
		clearList();
		updateColumns(data[0]);
		
		for (int i = 1; i < data.length; i++) {
			modelData.addRow(data[i]);
		}
	}
	
	//******************** FUNCTION TO SHOW ERROR MESSAGE ********************//
	public void showError(String error) {
		String[] errortext = {error};
		String[] header = {"Felmeddelande"};
		String[][] show = {header, errortext};
		showData(show);
	}
}
