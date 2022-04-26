package model;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Billing { 

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/eg_project?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public String insertBilling(String cusName, String cusAcc, String cusDate, String units, String totalPrice) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into billing(`billid`,`cusName`,`cusAcc`,`cusDate`,`units`,`totalPrice`)" + " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, cusName);
			preparedStmt.setString(3, cusAcc);
			preparedStmt.setString(4, cusDate);
			preparedStmt.setString(5, units);
			preparedStmt.setString(6, totalPrice);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the billing.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readBilling() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Bill ID</th><th>Name</th><th>Account Number</th><th>Date</th><th>Monthly Use Units</th><th>Total Price</th></tr>";
			String query = "select * from billing";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String billid = Integer.toString(rs.getInt("billid"));
				String cusName = rs.getString("cusName");
				String cusAcc = rs.getString("cusAcc");
				String cusDate = rs.getString("cusDate");
				String units = rs.getString("units");
				String totalPrice = rs.getString("totalPrice");

				// Add into the html table
				output += "<tr><td>" + billid + "</td>";
				output += "<td>" + cusName + "</td>";
				output += "<td>" + cusAcc + "</td>";
				output += "<td>" + cusDate + "</td>";
				output += "<td>" + units + "</td>";
				output += "<td>" + totalPrice + "</td>";
				
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the billing.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateBilling(String billid, String cusName, String cusAcc, String cusDate, String units, String totalPrice) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE billing SET cusName=?,cusAcc=?,cusDate=?,units=?,totalPrice=?" + "WHERE billid=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, cusName);
			preparedStmt.setString(2, cusAcc);
			preparedStmt.setString(3, cusDate);
			preparedStmt.setString(4, units);
			preparedStmt.setString(5, totalPrice);
			preparedStmt.setInt(6, Integer.parseInt(billid));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the billing.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteBilling(String billid) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from billing where billid =?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(billid));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the billing.";
			System.err.println(e.getMessage());
		}
		return output;
	}

}
