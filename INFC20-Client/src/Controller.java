import java.util.ArrayList;

public class Controller {
	
	private DBConnection db;
	private GUI gui;
	private ArrayList<String[]> apartments;
	private ArrayList<String[]> timeslots;
	
	public Controller(){
		db = new DBConnection();
		apartments = db.getApartments();
		timeslots = db.getTimeSlots();
		gui = new GUI(this);
	}
	
	public String[] getApartments() {
		ArrayList<String[]> data = db.getApartments();
		String[] list = new String[data.size()];
		for (int i = 0; i < data.size(); i++) {
			list[i] = data.get(i)[0];
		}
		return list;
	}
	
	public String getName(String aptNbr) {
		for(String[] apt : apartments) {
			if (apt[0].equals(aptNbr)) return apt[1];
		}
		return "Unknown";
	}
	
	public void showAvailable(String date) {
//		gui.showAvailable(db.getBookedByDate(date));
		
		ArrayList<String[]> booked = db.getBookedByDate(date);
		ArrayList<String[]> timeslotsToShow = (ArrayList<String[]>) timeslots.clone();
		int nbrOfRemoved = 0;
		
		if (booked != null) {
			for (String[] slot : booked) {
				timeslotsToShow.remove(Integer.parseInt(slot[1])-(1+nbrOfRemoved));
				nbrOfRemoved++;
			}
		}
		
		gui.showAvailable(timeslotsToShow);
	}
	
	public void showBooked(String aptID) {
		gui.showBooked(db.getBookingsByApt(Integer.parseInt(aptID)));
	}
}