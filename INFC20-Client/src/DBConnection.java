import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBConnection {

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
			e.printStackTrace();
			System.out.println("null");
			return null;
		}
	}
	
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
			e.printStackTrace();
			return null;
		}
		
	}
	
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
			e.printStackTrace();
			return null;
		}
	}
	
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
			e.printStackTrace();
			return null;
		}
	}
	
	public void createBooking(String aptID, String date, int timeSlotId) {
		String SPsql = "EXEC dbo.CreateBooking ?,?,?";
		String connectionUrl = "jdbc:sqlserver://infc20dev01.database.windows.net:1433;databaseName=Laundry_Booking;user=INFC20;password=DBpassword!";
		try (Connection con = DriverManager.getConnection(connectionUrl); PreparedStatement ps = con.prepareCall(SPsql)){
			ps.setQueryTimeout(5);
			ps.setDate(1, Date.valueOf(date));
			ps.setString(3, aptID);
			ps.setInt(2, timeSlotId);
			ps.executeQuery();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
