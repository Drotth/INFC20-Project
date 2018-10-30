	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;

	public class DBConnection {
	    public static void main(String[] args) {

	        // Create a variable for the connection string.
	        String connectionUrl = "jdbc:sqlserver://INFC20DEV01:1433;databaseName=Laundry_Booking;user=Admin;password=password";

	        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
	            String SQL = "SELECT TOP 100 * FROM [User]";
	            ResultSet rs = stmt.executeQuery(SQL);

	            // Iterate through the data in the result set and display it.
	            while (rs.next()) {
	                System.out.println(rs.getString("ApartmentID") + " " + rs.getString("Name") + " " + rs.getString("Password"));
	            }
	        }
	        // Handle any errors that may have occurred.
	        catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}