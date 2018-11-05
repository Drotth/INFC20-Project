import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

	public class DBConnection {
		
    private Controller controller;
		
		public DBConnection(Controller controller) {
			this.controller = controller;
		}
	
	    public static void main(String[] args) {

	   // Create a variable for the connection string.
	  String connectionUrl = "jdbc:sqlserver://infc20dev01.database.windows.net:1433;databaseName=Laundry_Booking;user=INFC20;password=DBpassword!";
	  String SPsql = "EXEC dbo.GetBookingsByUser ?";   // for stored proc taking 3 parameters
	  
	try (Connection con = DriverManager.getConnection(connectionUrl); PreparedStatement ps = con.prepareCall(SPsql);) {
     
        ps.setEscapeProcessing(true);
        ps.setQueryTimeout(10);
        ps.setInt(1,102);
        ResultSet rs = ps.executeQuery();
        
        
        
        // Iterate through the data in the result set and display it.
        while (((ResultSet) rs).next()) {
            System.out.println(rs.getString("BookingDate") + " " + rs.getString("StartTime") + " " + rs.getString("ApartmentID"));
        }
    }
    // Handle any errors that may have occurred.
    catch (SQLException e) {
        e.printStackTrace();
   
    }
}}
	
	