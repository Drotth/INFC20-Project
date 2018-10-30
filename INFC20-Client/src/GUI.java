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
	
	Calendar cal = new GregorianCalendar();
	JLabel labelMonthYear;
	DefaultTableModel modelWeekDays;
	
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
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		
		JPanel leftPanel = new JPanel(new BorderLayout());
		JPanel rightPanel = new JPanel(new BorderLayout());
		
		JPanel calendar = new JPanel(new BorderLayout());
		calendar.setSize(300, 300); //why doesn't this work!?
		JScrollPane scrollATimes = new JScrollPane();
		JScrollPane scrollBTimes = new JScrollPane();
		leftPanel.add(calendar, BorderLayout.NORTH);
		leftPanel.add(scrollBTimes, BorderLayout.SOUTH);
		rightPanel.add(scrollATimes);
		
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
	    
	    String [] columns = {"Sun", "Mon","Tue","Wed","Thu","Fri","Sat"};
	    modelWeekDays = new DefaultTableModel(null,columns);
	    tableData = new JTable(modelWeekDays){
			@Override
			public boolean isCellEditable(int row, int column){
			        return false;
			}
//            public boolean getScrollableTracksViewportWidth()
//            {
//                return getPreferredSize().width < getParent().getWidth();
//            }
		};
	    tableData.addMouseListener(new java.awt.event.MouseAdapter() {
	        @Override
	        public void mouseClicked(java.awt.event.MouseEvent evt) {
	            int row = tableData.rowAtPoint(evt.getPoint());
	            int col = tableData.columnAtPoint(evt.getPoint());
	            
	            System.out.println(cal.get(Calendar.YEAR) + "-" + 
	            		(cal.get(Calendar.MONTH)+1) + "-" + 
	            		tableData.getValueAt(row, col));
	        }
	    });
	    JScrollPane scrollDates = new JScrollPane(tableData);
	    
	    calendar.add(panelHeader,BorderLayout.NORTH);
	    calendar.add(scrollDates,BorderLayout.CENTER);
	    
	    contentPane.add(calendar);
	 
	    updateMonth();
	}
	
	  void updateMonth() {
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
