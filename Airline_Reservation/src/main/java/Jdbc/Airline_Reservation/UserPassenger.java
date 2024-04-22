package Jdbc.Airline_Reservation;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

import database.DBconnection;
import user_Exception.EmailException;
import user_Exception.IdException;
import user_Exception.PasswordException;
import user_Exception.UsernameException;

public class UserPassenger {
	DBconnection objDBOpns = new DBconnection();

    Scanner scan = new Scanner(System.in);
    DBconnection connect = new DBconnection();

    public void UserMenu() throws IdException, UsernameException, EmailException, PasswordException {
        int input;
        do {
            System.out.println("------------------------------ Airline Reservation System ------------------------------");
            System.out.println(" ");
            System.out.println("Enter the specified login options below to proceed....");
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.println("1. Passenger Registration");
            System.out.println("2. Passenger Login");
            System.out.println("3. Back... ");
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.print("Enter your choice : ");
            input = scan.nextInt();

            switch (input) {
                case 1:
                   
                    PassengerRegistration();
                    break;
                case 2:
                   
                     PassengerLogin();
                    break;
                case 3:
                	 UserPassenger passenger = new UserPassenger();
                     passenger.UserMenu();
                	 System.out.println("Back... ");
                	
                    
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        } while (input != 3);
    }

    public void PassengerRegistration() {
        System.out.println("... User Passenger Registration ....");
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
            
            System.out.print("Enter your city : ");
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
                System.out.println("A new passenger was registered successfully!");
            }
         
        } catch (Exception e) {
            System.out.println(e);
        } 
        finally {
        	connect.CloseConnection();
        }

        
    }
    
    public void PassengerLogin() throws IdException, UsernameException, EmailException, PasswordException {
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

        if (PassengerLogin(uid, password)) {
            System.out.println("Login Successfull....");
            passengerDashboard();
            
        } else {
            System.out.println("Login Failed. Invalid username or Password.");
        }
    }

    public boolean PassengerLogin(String uid, String password) {
        boolean isValidUser = false;
        DBconnection connect = new DBconnection(); // Assuming DBConnection is the class that handles DB connections
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
    
    public void passengerDashboard() throws IdException, UsernameException, EmailException, PasswordException {
        int input;
        do {
            System.out.println("------------------------------------------------------");
            System.out.println("----------- Airline Reservation System -------------");
            System.out.println("------------------------------------------------------");
            System.out.println("Passenger Dashboard");
            System.out.println("1. Flight Management");
            System.out.println("2. Booking Management");
            System.out.println("3. Paymnet Method");
            System.out.println("4. Passenger Details");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            input = scan.nextInt();
            

            switch (input) {
                case 1:
                	flightBook();
                    break;
                case 2:
                	viewBookedFlights();
                	
                    break;
                case 3:
                	paymentInfo();
                    break;
                case 4:
                	PassengerDetails();
                	
                	break;
                case 5:
                	UserMenu();
                	System.exit(0);
                    
                    break;
                default:
                	 System.out.println("Invalid selection. Please try again.");
                	break;
            }
            
        } while (true);
    }

    public void flightBook() {
    	readAllFlights();
    	Bookflight();
       
    }

    public void FlightInfo() {
    	 System.out.println("------------------------------------------------------");
        try {
        	System.out.print("Enter Your flightid : ");
            String fid = scan.next();
            System.out.print("Enter Your Flight number : ");
            String number = scan.next();
            System.out.print("Enter your departure city : ");
            String depart = scan.next();
            System.out.print("Enter your arrival city: ");
            String arriva = scan.next();
            System.out.print("Enter your departure time: ");
            String depart_time = scan.next();
            System.out.print("Enter your arrival time  : ");
            String arriva_time = scan.next();
            System.out.print("Enter your Departure cost : ");
            String departure_cost = scan.next();

            connect.InitiateDB();
            String sql = "INSERT INTO FlightInfo (flightid, flight_number, departure_city, arrival_city, departure_time , arrival_time, departure_cost) VALUES (?, ?, ?, ?, ?, ?, ?)";
            Object[] argums = {fid, number, depart, arriva, depart_time, arriva_time, departure_cost};

            int rowsInserted = connect.CreateDB(sql, argums);
            if (rowsInserted > 0) {
                System.out.println(" Flight was registered successfully!");
            }
         
        } catch (Exception e) {
            System.out.println(e);
        } finally {
        	connect.CloseConnection();
        }

    }
    
    public void readFlight() {
        objDBOpns.InitiateDB();
        System.out.print("Enter Flight number: ");
        String flightNumber = scan.next();
        String sql = "SELECT * FROM FlightInfo WHERE flight_number=? ";
        Object[] params = { flightNumber };
        try {
            ResultSet resultSet = objDBOpns.ReadDB(sql, params);
            objDBOpns.printResultSet(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            objDBOpns.CloseConnection();
        }
    }
    public void readAllFlights() {
  		System.out.println("------------------------------------------------------");
  		
        objDBOpns.InitiateDB();
        String sql = "SELECT * FROM FlightInfo";
        try {
            ResultSet resultSet = objDBOpns.ReadAllDB(sql);
            objDBOpns.printResultSet(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            objDBOpns.CloseConnection();
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
                String  booking_date = scan.next();
                System.out.print("Enter your Payment status (Y/N) : ");
                
                String payment_status = scan.next();
                if(payment_status.equalsIgnoreCase("Y")) {
               	 System.out.print("Enter your Payment amount : ");
                    String payment_amount = scan.next();
                    
                    connect.InitiateDB();
                    String sql = "INSERT INTO bookInfo (booking_id, userid, flight_number, booking_date, payment_status, payment_amount) VALUES (?, ?, ?, ?, ?, ?)";
                    Object[] argums = {booking_id, userid, flight_number, booking_date, payment_status, payment_amount};

                    int rowsInserted = connect.CreateDB(sql, argums);
                    if (rowsInserted > 0) {
                        System.out.println(" Flight was Booked successfully!");
                    }
                }else {
                UserMenu();
            }
            }catch (Exception e) {
                    System.out.println(e);
            }
            finally {
            	connect.CloseConnection();
            }

        }
    	
        	
        	public void viewBookedFlights() {
                objDBOpns.InitiateDB();
                String sql = "SELECT * FROM bookInfo ";
                try {
                    ResultSet resultSet = objDBOpns.ReadAllDB(sql);
                    objDBOpns.printResultSet(resultSet);
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    objDBOpns.CloseConnection();
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
                    	passengerDashboard();
                    	break;
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                        paymentMethod = null; // Or provide a default method based on your logic
                }
               
               return paymentInfo();
               
            }
        	
        	public void PassengerDetails() {
          		System.out.println("------------------------------------------------------");
          		
                objDBOpns.InitiateDB();
                String sql = "SELECT * FROM bookInfo";
                try {
                    ResultSet resultSet = objDBOpns.ReadAllDB(sql);
                    objDBOpns.printResultSet(resultSet);
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    objDBOpns.CloseConnection();
                }
            }
        }

       

  
     



    