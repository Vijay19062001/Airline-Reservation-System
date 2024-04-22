package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class DBconnection {
	Connection connection;
	Scanner scanner = new Scanner(System.in);
	
	public void InitiateDB() {
		try {
			String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
			String username = "TABLES";
			String password = "19062001";

			Class.forName("oracle.jdbc.OracleDriver");
			connection = DriverManager.getConnection(jdbcUrl, username, password);
		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int CreateDB(String sql, Object[] params) {
		 int result = 0;
		 try {
		 PreparedStatement statement = connection.prepareStatement(sql);
		 for (int i = 0; i < params.length; i++) {
		 statement.setObject(i + 1, params[i]);
		 }
		 result = statement.executeUpdate();
		 statement.close();
		 } catch (SQLException e) {
		 e.printStackTrace();
		 }
		 return result;
		 }
	
	public ResultSet ReadDB(String sql, Object[] params) throws SQLException {
		 PreparedStatement statement = connection.prepareStatement(sql);
		 for (int i = 0; i < params.length; i++) {
		 statement.setObject(i + 1, params[i]);
		 }
		 return statement.executeQuery();
		 }
		 
		 public ResultSet ReadAllDB(String sql) throws SQLException {
		 PreparedStatement statement = connection.prepareStatement(sql);
		 return statement.executeQuery();
		 }
		 
		 public void printResultSet(ResultSet resultSet) throws SQLException {
		 ResultSetMetaData metaData = resultSet.getMetaData();
		 int columnCount = metaData.getColumnCount();
		 while (resultSet.next()) {
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("                                                                                                                                                                                                   ");
		 StringBuilder row = new StringBuilder();
		 for (int i = 1; i <= columnCount; i++) {
		 if (i > 1) {
		 row.append("\t");
		 }
		String columnName = metaData.getColumnName(i);
		 Object value = resultSet.getObject(i);
		 row.append(columnName).append(" : ").append(value); 
		 }
		 System.out.println(row.toString());
		 }
		 }
	
		 public void DeleteDB(String sql, Object[] params) {
			 int result = 0;
			 char opt;
			 try {
			 PreparedStatement statement = connection.prepareStatement(sql);
			 for (int i = 0; i < params.length; i++) {
			 statement.setObject(i + 1, params[i]);
			 }
			 System.out.println("Do you want to delete the data(y/n)?");
			 opt=scanner.next().charAt(0);
			 if(opt=='y') { 
			 result = statement.executeUpdate();
			 if (result > 0) 
			 System.out.println("User was deleted successfully!");
			 else
			 System.out.println("No user with the specified username found.");
			 }else {
			 System.out.println("User was not deleted");
			 }
			 statement.close();
			 } catch (SQLException e) {
			 e.printStackTrace();
			 }
			 }
			 
			 public void CloseConnection() {
			 try {
			 if (connection != null)
			 connection.close();
			 } catch (SQLException e) {
			 e.printStackTrace();
			 }
			 }
			
	
	public int UpdateDB(String sql, Object[] params) {
		 int result = 0;
		 try {
		 PreparedStatement statement = connection.prepareStatement(sql);
		 for (int i = 0; i < params.length; i++) {
		 statement.setObject(i + 1, params[i]);
		 }
		 result = statement.executeUpdate();
		 statement.close();
		 } catch (SQLException e) {
		 e.printStackTrace();
		 }
		 return result;
		 }

	public Connection getConnection() {

		return connection;
	}

}

/*
	public String retrieveBookingsByUserID(String tablename, String IdPrimary, String findrecord, String userID) {
	    String retrieve = null;
	    try {
	        String sql = "SELECT " + findrecord + " FROM " + tablename + " WHERE " + IdPrimary + " = ?";
	        PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setString(1, userID);

	        ResultSet result = statement.executeQuery();
	        if (result.next()) {
	            retrieve = result.getString(findrecord);
	        }
	    } catch (SQLException e) {
	        System.out.println("Error retrieving bookings for user ID: " + userID);
	        e.printStackTrace();
	    }

	    return retrieve;
	}

}
	*/