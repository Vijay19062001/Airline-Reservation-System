package Jdbc.Airline_Reservation;

import java.sql.*;
import java.util.Scanner;

import user_Exception.*;

import database.DBconnection;

public class Admin{
	

    Scanner scan = new Scanner(System.in);
    DBconnection connect = new DBconnection();

    public void adminMenu() throws IdException, UsernameException, EmailException, PasswordException {
        int input;
        do {
            System.out.println("------------------------------ Airline Reservation System ------------------------------");
            System.out.println(" ");
            System.out.println("Enter the specified login options below to proceed....");
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.println("1. Admin Registration");
            System.out.println("2. Admin Login");
            System.out.println("3. Back ");
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.print("Enter your choice : ");
            input = scan.nextInt();

            switch (input) {
                case 1:
                    
                    adminRegistration();
                    break;
                case 2:
                    
                   adminLogin();
                    break;
                case 3:
                    System.out.println("Back... ");
                   Admin menu = new Admin();
                   menu.adminMenu();
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        } while (input != 3);
    }

    public void adminRegistration() {
        System.out.println("Admin Registration ....");
        System.out.println(" ");

        try {
        	System.out.print("Enter Your userid : ");
            String uid = scan.next();
            System.out.print("Enter Your name : ");
            String name = scan.next();
            System.out.print("Enter your username : ");
            String uname = scan.next();
            System.out.print("Enter your password : ");
            String pword = scan.next();
            if (pword.length() < 8 || !pword.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
                throw new PasswordException("Invalid Password: Password must be at least 8 characters long and contain at least one lowercase letter, one uppercase letter, one digit, and one special character.");
            }
            
            System.out.print("Enter your City : ");
            String district = scan.next();
            System.out.print("Enter your email address : ");
            String email = scan.next();
            System.out.print("Enter your gender (type 'Male' or 'Female') : ");
            String gender = scan.next();
            System.out.print("Enter your phonenumber : ");
            String mobilenumber = scan.next();

            System.out.print("Enter your DOB : ");
            String dateofbirth = scan.next();
            System.out.print("Enter your Address : ");
            String address = scan.next();
            System.out.print("Enter your pincode : ");
            String pincode = scan.next();
       
            connect.InitiateDB();
            String sql = "INSERT INTO UsersInfo (userid, username, name, password, city, email, gender, phonenumber, dateofbirth, address, pincode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Object[] argums = {uid, uname, name, pword, district, email, gender, mobilenumber, dateofbirth, address, pincode};

            int rowsInserted = connect.CreateDB(sql, argums);
            if (rowsInserted > 0) {
                System.out.println("Admin was registered successfully!");
            }
         
        } catch (Exception e) {
            System.out.println(e);
        } 
        finally {
        	connect.CloseConnection();
        }

        
    }
    
    public void adminLogin() throws IdException, UsernameException, EmailException, PasswordException {
        System.out.println(".....Login ....");
        System.out.println(" ");

        System.out.print("Enter Your Userid : ");
        String uid = scan.next();
        if (!uid.matches("\\d+")) {
       	 throw new IdException("Invalid ID: ID must contain only digits."); 
       	
       	 }
       	 

        System.out.print("Enter Your Password : ");
        String password = scan.next();
        if (password.length() < 8 || !password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
            throw new PasswordException("Invalid Password: Password must be at least 8 characters long and contain at least one lowercase letter, one uppercase letter, one digit, and one special character.");
        }

        if (userLogin(uid, password)) {
            System.out.println("Login Successfull....");
            adminDashboard();
            
        } else {
            System.out.println("Login Failed. Invalid username or Password.");
        }
    }

    public boolean userLogin(String uid, String password) {
        boolean isValidUser = false;
       // DBconnection connect = new DBconnection(); // Assuming DBConnection is the class that handles DB connections
        connect.InitiateDB(); // Ensure the database connection is initiated

        try {
            // Prepare SQL query to select the user
            String sql = "SELECT * FROM UsersInfo WHERE userid = ? AND password = ?";
            PreparedStatement statement = connect.getConnection().prepareStatement(sql);

            // Set the parameters for the prepared statement
            statement.setString(1, uid);
            statement.setString(2, password);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // If the result set is not empty, then the user exists
            isValidUser = resultSet.next();

            // Close the resultSet and statement
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
        	connect.CloseConnection();
        }


        return isValidUser;
    }
    

        public void adminDashboard() throws IdException, UsernameException, EmailException, PasswordException {
           int input;
            do {
                System.out.println("------------------------------------------------------");
                System.out.println("--------------- Airline Reservation System ----------");
                System.out.println("------------------------------------------------------");
                System.out.println("Admin Dashboard");
                System.out.println("1. Flights Management");
                System.out.println("2. Passengers Booking Management");              
                System.out.println("3. Generate Reports");
                System.out.println("4. Logout");
                System.out.println("------------------------------------------------------");
                System.out.print("Enter your choice: ");
                input = scan.nextInt();
                
                switch (input) {
                    case 1:
                    
                        flightsManagement();
                        break;
                    case 2:
                        passengersBookingManagement();
                        break;
                        
                    case 3:
                        generateReports();
                        break;
                    case 4:
                    	adminMenu();
                        System.exit(0);
                        break;
                    default:
                    	 System.out.println("Invalid selection. Please try again.");
                    	break;
                }
            } while (true);
        }

        public void flightsManagement() throws IdException, UsernameException, EmailException, PasswordException {
        	System.out.println("------------------------------------------------------");
            int input;
            do {
            	System.out.println("------------------------------------------------------");
                System.out.println("Flights Management");
                System.out.println("1. Create Flight");
                System.out.println("2. Update Flight");
                System.out.println("3. View Flights");
                System.out.println("4. Delete Flights");
                System.out.println("5. Back");
                System.out.print("Enter your operation: ");
                input = scan.nextInt();
               
                switch (input) {
                    case 1:
                        FlightInfo();
                        break;
                    case 2:
                    	updateFlight();
                        break;
                    case 3:
                    	readFlight();
                        break;
                    case 4:
                    	deleteFlight();
                        break;
                    case 5:
                    	adminDashboard();
                        break;
                    default:
                        System.out.println("Invalid selection. Please try again.");
                        break;
                }
            } while (true);
        }

        public void passengersBookingManagement() throws IdException, UsernameException, EmailException, PasswordException {
        	System.out.println("------------------------------------------------------");
            int input;
            do {
                System.out.println("Passengers Booking Management");                
                System.out.println("1. View Booked Passengers");               
                System.out.println("2. Back");
                System.out.print("Enter your operation: ");
                input = scan.nextInt();
                switch (input) {
                    
                    case 1:
                  
                    	viewBookedFlights();
                    	System.out.println("------------------------------------------------------");
                    	break;
                   
                    case 2:
                    	adminDashboard();
                    	System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid selection. Please try again.");
                        break;
                }
            }  while (true);
        }
        
        public void generateReports() throws IdException, UsernameException, EmailException, PasswordException {
            System.out.println("------------------------------------------------------");
            int input;
            do {
                System.out.println("Report Management");
                System.out.println("1. Booking Details");
                System.out.println("2. Passenger Details");
                System.out.println("3. Payment Details");
                System.out.println("4. Back");
                System.out.print("Enter your operation: ");
                input = scan.nextInt();
                switch (input) {
                    case 1:
                    	viewBookedFlights();
                        break;
                    case 2:
                    	viewBookedFlights();
                    	PassengerDetails();
                        break;
                    case 3:
                    	paymentInfo();
                        break;
                    case 4:
                        return; // Return from the method, exiting the loop
                    default:
                        System.out.println("Invalid selection. Please try again.");
                        break;
                }
            } while (true);
        }
        
    public void FlightInfo() {
    	System.out.println("------------------------------------------------------");

            try {
            	System.out.print("Enter Your flightid : ");
                String fid = scan.next();
                System.out.print("Enter Your flight number : ");
                String number = scan.next();
                System.out.print("Enter your departure city : ");
                String depart = scan.next();
                System.out.print("Enter your arrival city: ");
                String arriva = scan.next();
                System.out.print("Enter your departure time: ");
                String depart_time = scan.next();
                System.out.print("Enter your arrival time  : ");
                String arriva_time = scan.next();
                System.out.print("Enter your Departure Cost : ");
                String cost = scan.next();
                

                connect.InitiateDB();
                String sql = "INSERT INTO FlightInfo (flightid, flight_number, departure_city, arrival_city, departure_time , arrival_time, departure_cost ) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
                Object[] argums = {fid, number, depart, arriva, depart_time, arriva_time, cost};

                int rowsInserted = connect.CreateDB(sql, argums);
                if (rowsInserted > 0) {
                    System.out.println(" Flight was registered successfully!");
                }
             
            } catch (Exception e) {
                System.out.println(e);
            } 
            finally {
            	connect.CloseConnection();
            }

            
        }
    
    public void updateFlight() {
    	System.out.println("------------------------------------------------------");
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter flight number you want to update: ");
            String flight_number = scanner.next();
            scanner.nextLine(); // Consume extra newline character

            System.out.print("Enter the new departure city: ");
            String departure_city = scanner.nextLine();

            System.out.print("Enter the new Arrival city: ");
            String arrival_city = scanner.nextLine();

            System.out.print("Enter the new departure time: ");
            String departure_time = scanner.nextLine();

            System.out.print("Enter the new arrival time: ");
            String arrival_time = scanner.nextLine();

            connect.InitiateDB(); // Assuming InitiateDB method initializes the database connection

            String sql = "UPDATE flightInfo SET Departure_city = ?, arrival_city = ?, Departure_time = ?, Arrival_time = ? WHERE Flight_number = ?";
            PreparedStatement statement = connect.getConnection().prepareStatement(sql);
            statement.setString(1, departure_city);
            statement.setString(2, arrival_city);
            statement.setString(3, departure_time);
            statement.setString(4, arrival_time);
            statement.setString(5, flight_number);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0)
                System.out.println("Flight updated successfully!");
            else
                System.out.println("Flight not found or no updates were made.");
            
            scanner.close(); 
        }
        
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        finally {
        	connect.CloseConnection();
        }

    }
   
    public void readFlight() {
        connect.InitiateDB();
        System.out.print("Enter Flight Number: ");
        String flight_number = scan.next();
        String sql = "SELECT * FROM FlightInfo WHERE Flight_number=?";
        Object[] params = { flight_number };
        try {
            ResultSet resultSet = connect.ReadDB(sql, params);
            connect.printResultSet(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	connect.CloseConnection();
        }
    }
    public void deleteFlight() {
        connect.InitiateDB();

        System.out.print("Enter Flight number: ");
        String flight_number = scan.next();

        String sql = "DELETE FROM FlightInfo WHERE flight_number=?";
        Object[] params = { flight_number };
        connect.DeleteDB(sql, params);

        connect.CloseConnection();
    }
      	
      	public void readAllFlights() {
      		System.out.println("------------------------------------------------------");
      		System.out.println("------------------------------------------------------");
      		connect.InitiateDB();
            String sql = "SELECT * FROM FlightInfo";
            try {
                ResultSet resultSet = connect.ReadAllDB(sql);
                connect.printResultSet(resultSet);
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
            	connect.CloseConnection();
            }
        }
      	
   
         
      	public void Bookflight() {
      	    System.out.println(" ");
      	    try {
      	        System.out.print("Enter Your Bookingid : ");
      	        String booking_id = scan.next();
      	        System.out.print("Enter Your user id : ");
      	        String userid = scan.next();
      	        System.out.print("Enter your flight number : ");
      	        String flight_number = scan.next();
      	        System.out.print("Enter your booking date : ");
      	        String booking_date = scan.next();
      	        System.out.print("Enter your Payment status (Y/N) : ");
      	        String payment_status = scan.next();
      	        
      	        if (payment_status.equalsIgnoreCase("Y")) {
      	            System.out.print("Enter your Payment amount : ");
      	            String payment_amount = scan.next();
      	            
      	            connect.InitiateDB();
      	            String sql = "INSERT INTO bookInfo (booking_id, userid, flight_number, booking_date, payment_status, payment_amount) VALUES (?, ?, ?, ?, ?, ?)";
      	            Object[] argums = {booking_id, userid, flight_number, booking_date, payment_status, payment_amount };
      	            
      	            int rowsInserted = connect.CreateDB(sql, argums);
      	            if (rowsInserted > 0) {
      	                System.out.println("Flight was Booked successfully!");
      	            }
      	        } else {
      	            System.out.print("Please Pay the Payment..")   ;   	        
      	        }
      	    } catch (Exception e) {
      	        System.out.println("An error occurred: " + e.getMessage());
      	        e.printStackTrace();
      	    } 
      	  finally {
          	connect.CloseConnection();
          }

      	}
      	
      	
      	
      	public void viewBookedFlights() {
            connect.InitiateDB();
            String sql = "SELECT * FROM bookInfo ";
            try {
                ResultSet resultSet = connect.ReadAllDB(sql);
                connect.printResultSet(resultSet);
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connect.CloseConnection();
            }
        }
      	public void PassengerDetails() {
      		System.out.println("------------------------------------------------------");
      		
            connect.InitiateDB();
            String sql = "SELECT * FROM UsersInfo";
            try {
                ResultSet resultSet = connect.ReadAllDB(sql);
                connect.printResultSet(resultSet);
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
            	connect.CloseConnection();
            }
        }
      	public String paymentInfo() throws IdException, UsernameException, EmailException, PasswordException {
            Scanner scan = new Scanner(System.in);

            System.out.println("Select Payment Method:");
            System.out.println("------------------------------------------------------");
            System.out.println("1. Debit card");
            System.out.println("2. Credit card");
            System.out.println("3. Gpay");
            System.out.println("4. Back");
            System.out.print("Enter your choice: ");
            
            int choice = scan.nextInt();
          
            String paymentMethod;
            switch (choice) {
                case 1:
                    paymentMethod = "Debit card";
                    System.out.print("Enter the Debit card number: ");
                    String DebitCardNumber = scan.next();
                    System.out.print("Enter the amount: ");
                    double amounts = scan.nextDouble();
                   
                    System.out.println("Payment successful with " + paymentMethod);
                    break;
                case 2:
                    paymentMethod = "credit card";
                    System.out.print("Enter the Credit card number: ");
                    String creditCardNumber = scan.next();
                    System.out.print("Enter the amount: ");
                    double amount = scan.nextDouble();
                   
                    System.out.println("Payment successful with " + paymentMethod);
                    break;
                case 3:
                    paymentMethod = "Gpay";
                    System.out.print("Enter the Gpay number: ");
                    String GpayNumber = scan.next();
                    System.out.print("Enter the amount: ");
                    double amoun = scan.nextDouble();
                   
                    System.out.println("Payment successful with " + paymentMethod);
                    break;
                case 4:
                	adminDashboard();
                	break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    paymentMethod = null; // Or provide a default method based on your logic
            }
           
           return paymentInfo();
           
        }
    	

}
            
          
   
  
  
        
    
