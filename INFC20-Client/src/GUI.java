import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class GUI extends JFrame {

	private Controller controller;
	private JPanel contentPane;
	
	// Calendar objects
	private Calendar cal = new GregorianCalendar();
	private JLabel labelMonthYear;
	private DefaultTableModel modelWeekDays;
	private JTable calTable;
	
	// Available bookings objects
	private DefaultTableModel modelAvailable;
	private JTable tableAvailable;
	
	// Personal bookings objects
	private DefaultTableModel modelBooked;
	private JTable tableBooked;
	
	// Header objects
	private JLabel lblHello;
	private String[] aptList;
	private JComboBox<String> boxApt;

	//******************** CONSTRUCTOR ********************//
	public GUI(Controller controller) {
		setResizable(false);
		this.controller = controller;
		initialize();
		setVisible(true);
		
		lblHello.setText("Hello, please select an apartment number!");
	}

	//******************** INITIALIZE CONTENTS OF THE FRAME ********************//
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 200, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBounds(20, 40, 354, 504);
		JPanel rightPanel = new JPanel();
		rightPanel.setBounds(384, 40, 390, 504);
		
		JPanel calendar = new JPanel(new BorderLayout());
		calendar.setBounds(0, 0, 354, 227);
		
		//****** HEADER OBJECTS ******//
		boxApt = new JComboBox<String>();
		boxApt.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        controller.showBooked(boxApt.getSelectedItem().toString());
		        lblHello.setText("Hello " + controller.getName(boxApt.getSelectedItem().toString()) + "!");
		    }
		});
		boxApt.setBounds(157, 11, 74, 20);
		contentPane.add(boxApt);
		
		JLabel lblChooseApartment = new JLabel("Choose apartment:");
		lblChooseApartment.setBounds(20, 14, 127, 14);
		contentPane.add(lblChooseApartment);
		
		lblHello = new JLabel("Hello");
		lblHello.setBounds(256, 14, 308, 14);
		contentPane.add(lblHello);
		
		//****** CALENDAR PANEL ******//
	    labelMonthYear = new JLabel();
	    labelMonthYear.setHorizontalAlignment(SwingConstants.CENTER);
	 
	    JButton btnNext = new JButton(">");
	    btnNext.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	        cal.add(Calendar.MONTH, +1);
	        updateCalendar();
	      }
	    });
	    
	    JButton btnPrev = new JButton("<");
	    btnPrev.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	        cal.add(Calendar.MONTH, -1);
	        updateCalendar();
	      }
	    });
	 
	    JPanel panelHeader = new JPanel();
	    panelHeader.setLayout(new BorderLayout());
	    panelHeader.add(btnPrev,BorderLayout.WEST);
	    panelHeader.add(labelMonthYear,BorderLayout.CENTER);
	    panelHeader.add(btnNext,BorderLayout.EAST);
	    
	    String [] columnsCal = {"Sun", "Mon","Tue","Wed","Thu","Fri","Sat"};
	    modelWeekDays = new DefaultTableModel(null, columnsCal);
	    calTable = new JTable(modelWeekDays){
			@Override
			public boolean isCellEditable(int row, int column){
			        return false;
			}
		};
		
	    calTable.addMouseListener(new java.awt.event.MouseAdapter() {
	        @Override
	        public void mouseClicked(java.awt.event.MouseEvent evt) {
	            int row = calTable.rowAtPoint(evt.getPoint());
	            int col = calTable.columnAtPoint(evt.getPoint());
	            
	            if (calTable.getValueAt(row, col) != null) {
		            controller.showAvailable(cal.get(Calendar.YEAR) + "-" + 
		            		(cal.get(Calendar.MONTH)+1) + "-" + 
		            		calTable.getValueAt(row, col));
	            }
	        }
	    });
	    JScrollPane scrollDates = new JScrollPane(calTable);
	    
	    calendar.add(panelHeader,BorderLayout.NORTH);
	    calendar.add(scrollDates,BorderLayout.CENTER);
	 
	    updateCalendar();
	    
	    //****** PERSONAL BOOKINGS PANEL ******//
		JLabel lblBooked = new JLabel("Booked timeslots");
		lblBooked.setHorizontalAlignment(SwingConstants.CENTER);
		lblBooked.setBounds(114, 238, 122, 14);
		leftPanel.add(lblBooked);
		contentPane.add(rightPanel);
	    
	    String [] columnsBooked = {"Date", "Start time", "End time"};
	    modelBooked = new DefaultTableModel(null, columnsBooked);
	    tableBooked = new JTable(modelBooked){
			@Override
			public boolean isCellEditable(int row, int column){
			        return false;
			}
		};
		
	    tableBooked.addMouseListener(new java.awt.event.MouseAdapter() {
	        @Override
	        public void mouseClicked(java.awt.event.MouseEvent evt) {
	            int row = tableBooked.rowAtPoint(evt.getPoint());
	            int col = tableBooked.columnAtPoint(evt.getPoint());
	        }
	    });
	    JScrollPane scrollBooked = new JScrollPane(tableBooked);
	    scrollBooked.setBounds(0, 261, 354, 218);
		
		JButton btnRemoveBooking = new JButton("Remove booking");
		btnRemoveBooking.setBounds(0, 481, 354, 23);
		leftPanel.add(btnRemoveBooking);
	    
	    //****** AVAILABLE BOOKINGS PANEL ******//
		JLabel lblAvailable = new JLabel("Available timeslots");
		lblAvailable.setBounds(0, 0, 385, 25);
		rightPanel.add(lblAvailable);
		lblAvailable.setHorizontalAlignment(SwingConstants.CENTER);
	    
	    String [] columnsAvailable = {"Start time", "End time"};
	    modelAvailable = new DefaultTableModel(null, columnsAvailable);
	    tableAvailable = new JTable(modelAvailable){
			@Override
			public boolean isCellEditable(int row, int column){
			        return false;
			}
		};
		
	    tableAvailable.addMouseListener(new java.awt.event.MouseAdapter() {
	        @Override
	        public void mouseClicked(java.awt.event.MouseEvent evt) {
	            int row = tableAvailable.rowAtPoint(evt.getPoint());
	            int col = tableAvailable.columnAtPoint(evt.getPoint());
	            
	            System.out.println(tableAvailable.getValueAt(row, col));
	        }
	    });
	    JScrollPane scrollAvailable = new JScrollPane(tableAvailable);
	    scrollAvailable.setBounds(0, 25, 385, 454);
	    
		JButton btnBook = new JButton("Book");
		btnBook.setBounds(0, 480, 385, 24);
		rightPanel.add(btnBook);
		contentPane.setLayout(null);
		contentPane.add(leftPanel);
	    
		//****** BIND EVERTHING TOGETHER ******//
		leftPanel.add(calendar);
		leftPanel.add(scrollBooked);
		rightPanel.add(scrollAvailable);
		leftPanel.setLayout(null);
		rightPanel.setLayout(null);
		
		aptList = controller.getApartments();
		boxApt.setModel(new DefaultComboBoxModel<String>(aptList));
	}
	
	//******************** FUNCTION TO UPDATE THE CALENDAR ********************//
	private void updateCalendar() {
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	 
	    String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
	    int year = cal.get(Calendar.YEAR);
	    labelMonthYear.setText(month + " " + year);
	 
	    int startDay = cal.get(Calendar.DAY_OF_WEEK);
	    int numberOfDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	    int weeks = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
	 
	    modelWeekDays.setRowCount(0);
	    modelWeekDays.setRowCount(weeks);
	 
	    int i = startDay-1;
	    for(int day=1;day<=numberOfDays;day++){
	      modelWeekDays.setValueAt(day, i/7 , i%7 );    
	      i = i + 1;
	    }
	  }
	
	//******************** FUNCTION TO CLEAR LIST OF DATA ********************//
	private void clearList(String list) {
		switch (list){
			case "available":
				for (int i = modelAvailable.getRowCount() - 1; i >= 0; i--) {
				    modelAvailable.removeRow(i);
				}
				break;
			case "booked":
				for (int i = modelBooked.getRowCount() - 1; i >= 0; i--) {
				    modelBooked.removeRow(i);
				}
				break;
		}
	}

	//******************** FUNCTION TO FILL AVAILABLE BOOKINGS LIST ********************//
	public void showAvailable(ArrayList<String[]> data) {
		clearList("available");
		
		for (int i = 0; i < data.size(); i++) {
			modelAvailable.addRow(data.get(i));
		}
	}
	
	//******************** FUNCTION TO FILL AVAILABLE BOOKINGS LIST ********************//
	public void showBooked(ArrayList<String[]> data) {
		clearList("booked");
		
		for (int i = 0; i < data.size(); i++) {
			modelBooked.addRow(data.get(i));
		}
	}
}
