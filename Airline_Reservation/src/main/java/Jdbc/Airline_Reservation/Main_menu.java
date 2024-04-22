package Jdbc.Airline_Reservation;


import java.util.Scanner;

import database.DBconnection;
import user_Exception.*;

public class Main_menu 
{
	 Scanner scan = new Scanner(System.in);
	 DBconnection connect = new DBconnection();
     
    public static void main(String[] args) throws IdException, UsernameException, EmailException, PasswordException 
    {
    	 Scanner scan = new Scanner(System.in);
        
    	
    	int input;
    	do {
            System.out.println("------------------------------ Airline Reservation System ------------------------------");
            System.out.println(" ");
            System.out.println("Enter the specified login options below to proceed....");
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.println("1. Admin ");
            System.out.println("2. Passenger ");
            System.out.println("3. Exit");
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.print("Enter your choice : ");
            input = scan.nextInt();

           
            switch (input) {
                case 1:
                	Admin admin = new Admin();
                    admin.adminMenu();
                    System.out.println("Admin Registration Initiated....");
                    admin.adminRegistration();
                    
                    
                    break;
                
                case 2:
                	UserPassenger user = new UserPassenger();
                    user.UserMenu();
                	
                    System.out.println("Admin Login Initiated....");
                    
                    break;
                case 3:
                    System.out.println("Exiting..");
                    
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
            scan.close();
        } 
    	
    	while (true);
    	
    }
    
    public void registerPassenger()throws IdException,UsernameException,EmailException,PasswordException {
        System.out.println("Passenger Registration Initiated....");
        System.out.println(" ");

       
        	System.out.print("Enter Your userid : ");
            String uid = scan.next();
            
            	 if (!uid.matches("\\d+")) {
            	 throw new IdException("Invalid ID: ID must contain only digits."); 
            	
            	 }
            	 
            System.out.print("Enter Your Name : ");
            String name = scan.next();
            System.out.print("Enter your username : ");
            String uname = scan.next();
            
            	 if (!uname.matches("[a-zA-Z]+")) {
            	 throw new UsernameException("Invalid Name: Name must contain only alphabets.");
            	
            	 }
            	 
            
            System.out.print("Enter your password : ");
            String pword = scan.next();
            if (pword.length() < 8 || !pword.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
                throw new PasswordException("Invalid Password: Password must be at least 8 characters long and contain at least one lowercase letter, one uppercase letter, one digit, and one special character.");
            }
            
            System.out.print("Enter your City : ");
            String district = scan.next();

            
            System.out.print("Enter your email : ");
            String email = scan.next();
            
            if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) 
            {
             throw new EmailException("Invalid Email: Please enter a valid email address.");

             }

            System.out.print("Enter your gender (type 'Male' or 'Female') : ");
            String gender = scan.next();
            System.out.print("Enter your phonenumber : ");
            String phonenumber = scan.next();
            
            System.out.print("Enter your DOB : ");
            String dateofbirth = scan.next();
            System.out.print("Enter your Address : ");
            String address = scan.next();
            System.out.print("Enter your pincode : ");
            String pincode = scan.next();

            connect.InitiateDB();
            String sql = "INSERT INTO UsersInfo (userid, username, name, password, email, gender, city, phonenumber, dateofbirth, address, pincode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Object[] argums = {uid, uname, name, pword, email, gender, district, phonenumber, dateofbirth, address, pincode};

            int rowsInserted = connect.CreateDB(sql, argums);
            if (rowsInserted > 0) {
                System.out.println("A new passenger was registered successfully!");
            }
        
   
    }
    
}



  
