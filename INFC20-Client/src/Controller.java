import java.util.ArrayList;

public class Controller {

	private DBConnection db;
	private GUI gui;
	private ArrayList<String[]> apartments;
	private ArrayList<String[]> timeslots;

	public Controller() {
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
		for (String[] apt : apartments) {
			if (apt[0].equals(aptNbr))
				return apt[1];
		}
		return "Unknown";
	}

	public void showAvailable(String date) {
		ArrayList<String[]> booked = db.getBookedByDate(date);
		ArrayList<String[]> timeslotsToShow = (ArrayList<String[]>) timeslots.clone();
		int nbrOfRemoved = 0;

		if (booked != null) {
			for (String[] slot : booked) {
				timeslotsToShow.remove(Integer.parseInt(slot[1]) - (1 + nbrOfRemoved));
				nbrOfRemoved++;
			}
		}

		gui.showAvailable(timeslotsToShow);
	}

	public void showBooked(String aptID) {
		gui.showBooked(db.getBookingsByApt(Integer.parseInt(aptID)));
	}

	public void createBooking(String date, String time, String aptID) {
		if ((date != null && date.contains("null") != true) && time != null && aptID != null) {
			int timeSlotId = 0;
			
			switch (time) {
			case "07:00:00":
				timeSlotId = 1;
				break;
			case "10:00:00":
				timeSlotId = 2;
				break;
			case "13:00:00":
				timeSlotId = 3;
				break;
			case "16:00:00":
				timeSlotId = 4;
				break;
			case "19:00:00":
				timeSlotId = 5;
				break;
			}

			db.createBooking(aptID, date, timeSlotId);
			showAvailable(date);
			showBooked(aptID);
		}
	}

	public void removeBooking(String date, String time, String aptID) {
		if ((date != null && date.contains("null") != true) && time != null && aptID != null) {
			int timeSlotId = 0;

			switch (time) {
			case "07:00:00":
				timeSlotId = 1;
				break;
			case "10:00:00":
				timeSlotId = 2;
				break;
			case "13:00:00":
				timeSlotId = 3;
				break;
			case "16:00:00":
				timeSlotId = 4;
				break;
			case "19:00:00":
				timeSlotId = 5;
				break;
			}

			db.removeBooking(date, timeSlotId);
			showAvailable(date);
			showBooked(aptID);
		}
	}
}