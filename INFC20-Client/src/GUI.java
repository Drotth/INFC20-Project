import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;

public class GUI extends JFrame {

	private Controller controller;
	private JPanel contentPane;
	private JTable tableData;
	private DefaultTableModel modelData;

	//******************** CONSTRUCTOR ********************//
	public GUI(Controller controller) {
		this.controller = controller;
		initialize();
		setVisible(true);
	}

	//******************** INITIALIZE CONTENTS OF THE FRAME ********************//
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 200, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(50, 50, 50, 50));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		//******************** LOGIN LAYOUT ********************//
		JLabel lblAptNbr = new JLabel("Apartment Number:");
		String[] listApts = {"1101", "1102", "1103"};
		JComboBox<String> cBoxApts = new JComboBox<String>(listApts);
		contentPane.add(lblAptNbr);
		contentPane.add(cBoxApts);
		
		JLabel lblPassword = new JLabel("Password:");
		JTextField txtPassword = new JTextField();
		contentPane.add(lblPassword);
		contentPane.add(txtPassword);
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
