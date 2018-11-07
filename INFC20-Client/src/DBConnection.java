import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Database access layer for the Laundry Booking application. Based upon
 * an MVC structure. Here is all the communication with the server and 
 * database handled.
 */
public class DBConnection {
	
	private Controller controller;
	
	//******** CONSTRUCTOR ********//
	public DBConnection(Controller controller) {
		this.controller = controller;
	}
	
	//******** FUNCTION TO FETCH APARTMENTS FROM SERVER ********//
	public ArrayList<String[]> getApartments() {
		String SPsql = "EXEC dbo.GetUsers";
		String connectionUrl = "jdbc:sqlserver://infc20dev01.database.windows.net:1433;databaseName=Laundry_Booking;user=INFC20;password=DBpassword!";
		
		try (Connection con = DriverManager.getConnection(connectionUrl); PreparedStatement ps = con.prepareCall(SPsql)){
			ps.setQueryTimeout(5);
			ResultSet rs = ps.executeQuery();
			
			ArrayList<String[]> data = new ArrayList<String[]>();
			while (rs.next()) {
				String[] dataRow = {rs.getString("ApartmentId"), rs.getString("Name")};
				data.add(dataRow);
			}
			return data;
			
		}
		catch (SQLException e) {
			controller.showError("Connection error!");
			return null;
		}
	}
	
	//******** FUNCTION TO FETCH BOOKINGS BY APARTMENT/USER ********//
	public ArrayList<String[]> getBookingsByApt(int aptID) {
		String SPsql = "EXEC dbo.GetBookingsByUser ?";
		String connectionUrl = "jdbc:sqlserver://infc20dev01.database.windows.net:1433;databaseName=Laundry_Booking;user=INFC20;password=DBpassword!";
		try (Connection con = DriverManager.getConnection(connectionUrl); PreparedStatement ps = con.prepareCall(SPsql)){
			ps.setQueryTimeout(5);
			ps.setInt(1, aptID);
			ResultSet rs = ps.executeQuery();
			
			ArrayList<String[]> data = new ArrayList<String[]>();
			while (rs.next()) {
				String[] dataRow = {rs.getString("BookingDate"), rs.getString("StartTime"), rs.getString("EndTime")};
				data.add(dataRow);
			}
			return data;
			
		}
		catch (SQLException e) {
			controller.showError("Connection error!");
			return null;
		}
		
	}
	
	//******** FUNCTION TO FETCH BOOKINGS ON SPECIFIC DATE ********//
	public ArrayList<String[]> getBookedByDate(String date) {
		String SPsql = "EXEC dbo.GetBookingsByDate ?";
		String connectionUrl = "jdbc:sqlserver://infc20dev01.database.windows.net:1433;databaseName=Laundry_Booking;user=INFC20;password=DBpassword!";
		try (Connection con = DriverManager.getConnection(connectionUrl); PreparedStatement ps = con.prepareCall(SPsql)){
			ps.setQueryTimeout(5);
			ps.setDate(1, Date.valueOf(date));
			ResultSet rs = ps.executeQuery();
			
			ArrayList<String[]> data = new ArrayList<String[]>();
			while (rs.next()) {
				String[] dataRow = {rs.getString("BookingDate"), rs.getString("TimeSlotId"), rs.getString("ApartmentId")};
				data.add(dataRow);
			}
			return data;
			
		}
		catch (SQLException e) {
			controller.showError("Connection error!");
			return null;
		}
	}
	
	//******** FUNCTION TO FETCH TIMESLOT STRUCTURE ********//
	public ArrayList<String[]> getTimeSlots() {
		String SPsql = "EXEC dbo.GetTimeSlots";
		String connectionUrl = "jdbc:sqlserver://infc20dev01.database.windows.net:1433;databaseName=Laundry_Booking;user=INFC20;password=DBpassword!";
		try (Connection con = DriverManager.getConnection(connectionUrl); PreparedStatement ps = con.prepareCall(SPsql)){
			ps.setQueryTimeout(5);
			ResultSet rs = ps.executeQuery();
			
			ArrayList<String[]> data = new ArrayList<String[]>();
			while (rs.next()) {
				String[] dataRow = {rs.getString("StartTime"), rs.getString("EndTime")};
				data.add(dataRow);
			}
			return data;
			
		}
		catch (SQLException e) {
			controller.showError("Connection error!");
			return null;
		}
	}
	
	//******** FUNCTION TO PERFORM A NEW BOOKING ********//
	public void createBooking(String aptID, String date, int timeSlotId) {
		String SPsql = "EXEC dbo.CreateBooking ?,?,?";
		String connectionUrl = "jdbc:sqlserver://infc20dev01.database.windows.net:1433;databaseName=Laundry_Booking;user=INFC20;password=DBpassword!";
		try (Connection con = DriverManager.getConnection(connectionUrl); PreparedStatement ps = con.prepareCall(SPsql)){
			ps.setQueryTimeout(5);
			ps.setDate(1, Date.valueOf(date));
			ps.setInt(2, timeSlotId);
			ps.setString(3, aptID);
			ps.execute();
			
		}
		catch (SQLException e) {
			controller.showError("Connection error!");
		}
	}
	
	//******** FUNCTION TO REMOVE AN EXISTING BOOKING ********//
	public void removeBooking(String date, int timeSlotId) {
		String SPsql = "EXEC dbo.DeleteBooking ?,?";
		String connectionUrl = "jdbc:sqlserver://infc20dev01.database.windows.net:1433;databaseName=Laundry_Booking;user=INFC20;password=DBpassword!";
		try (Connection con = DriverManager.getConnection(connectionUrl); PreparedStatement ps = con.prepareCall(SPsql)){
			ps.setQueryTimeout(5);
			ps.setDate(1, Date.valueOf(date));
			ps.setInt(2, timeSlotId);
			ps.execute();
			
		}
		catch (SQLException e) {
			controller.showError("Connection error!");
		}
	}
}
