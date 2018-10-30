import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private JTable availTable;
	
	// Personal bookings objects
	private DefaultTableModel modelBooked;
	private JTable bookedTable;

	//******************** CONSTRUCTOR ********************//
	public GUI(Controller controller) {
		this.controller = controller;
		initialize();
		setVisible(true);
		
		String[][] fakeData = new String[31][];
		
		for (int i = 0; i < 31; i++) {
			fakeData[i] = new String[]{"starttime " + i, "endtime " + i};
		}
		
		showAvailable(fakeData);
	}

	//******************** INITIALIZE CONTENTS OF THE FRAME ********************//
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 200, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		
		JPanel leftPanel = new JPanel(new BorderLayout());
		leftPanel.setBounds(0, 0, 374, 544);
		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.setBounds(389, 0, 389, 544);
		
		JPanel calendar = new JPanel(new BorderLayout());
		calendar.setSize(300, 300); //why doesn't this work!?
		JScrollPane scrollBTimes = new JScrollPane();
		
		//****** CALENDAR PANEL ******//
	    labelMonthYear = new JLabel();
	    labelMonthYear.setHorizontalAlignment(SwingConstants.CENTER);
	 
	    JButton btnNext = new JButton(">");
	    btnNext.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	        cal.add(Calendar.MONTH, +1);
	        updateMonth();
	      }
	    });
	    
	    JButton btnPrev = new JButton("<");
	    btnPrev.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	        cal.add(Calendar.MONTH, -1);
	        updateMonth();
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
	            
	            System.out.println(cal.get(Calendar.YEAR) + "-" + 
	            		(cal.get(Calendar.MONTH)+1) + "-" + 
	            		calTable.getValueAt(row, col));
	        }
	    });
	    JScrollPane scrollDates = new JScrollPane(calTable);
	    
	    calendar.add(panelHeader,BorderLayout.NORTH);
	    calendar.add(scrollDates,BorderLayout.CENTER);
	 
	    updateMonth();
	    
	    //****** AVAILABLE BOOKINGS PANEL ******//
	    String [] columnsTimeslots = {"Start time", "end time"};
	    modelAvailable = new DefaultTableModel(null, columnsTimeslots);
	    availTable = new JTable(modelAvailable){
			@Override
			public boolean isCellEditable(int row, int column){
			        return false;
			}
		};
		
	    availTable.addMouseListener(new java.awt.event.MouseAdapter() {
	        @Override
	        public void mouseClicked(java.awt.event.MouseEvent evt) {
	            int row = availTable.rowAtPoint(evt.getPoint());
	            int col = availTable.columnAtPoint(evt.getPoint());
	            
	            System.out.println(availTable.getValueAt(row, col));
	        }
	    });
	    JScrollPane scrollAvailable = new JScrollPane(availTable);
	    
		leftPanel.add(calendar, BorderLayout.NORTH);
		leftPanel.add(scrollBTimes, BorderLayout.SOUTH);
		rightPanel.add(scrollAvailable);
		contentPane.setLayout(null);
		contentPane.add(leftPanel);
		contentPane.add(rightPanel);
	    
	}
	
	private void updateMonth() {
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
	private void clearList() {
		int rowCount = modelAvailable.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
		    modelAvailable.removeRow(i);
		}
	}

	//******************** FUNCTION TO FILL AVAILABLE BOOKINGS LIST ********************//
	public void showAvailable(String[][] data) {
		clearList();
		
		for (int i = 0; i < data.length; i++) {
			modelAvailable.addRow(data[i]);
		}
	}
	
	//******************** FUNCTION TO FILL AVAILABLE BOOKINGS LIST ********************//
	public void showBooked(String[][] data) {
		clearList();
		
		for (int i = 0; i < data.length; i++) {
			modelBooked.addRow(data[i]);
		}
	}
	
	//******************** FUNCTION TO SHOW ERROR MESSAGE ********************//
	public void showError(String error) {
		String[] errortext = {error};
		String[] header = {"Felmeddelande"};
		String[][] show = {header, errortext};
		showAvailable(show);
	}
}
