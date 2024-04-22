package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserMenu {
    DBconnection objDBOpns = new DBconnection();
    Scanner scanner = new Scanner(System.in);

    public void createFlight() {
        objDBOpns.InitiateDB();

        System.out.print("Enter Flight Number: ");
        String flightNumber = scanner.next();
        System.out.print("Enter Departure City: ");
        String departureCity = scanner.next();
        System.out.print("Enter Destination City: ");
        String destinationCity = scanner.next();
        System.out.print("Enter Departure Time: ");
        String departureTime = scanner.next();
        System.out.print("Enter Arrival Time: ");
        String arrivalTime = scanner.next();

        String sql = "INSERT INTO Flight (FlightNumber, DepartureCity, DestinationCity, DepartureTime, ArrivalTime) VALUES (?, ?, ?, ?, ?)";

        Object[] params = { flightNumber, departureCity, destinationCity, departureTime, arrivalTime };
        int rowsInserted = objDBOpns.CreateDB(sql, params);
        if (rowsInserted > 0)
            System.out.println("Flight details inserted successfully!");
        else
            System.out.println("Flight details not inserted...");

        objDBOpns.CloseConnection();
    }

    public void readFlight() {
        objDBOpns.InitiateDB();
        System.out.print("Enter Flight Number: ");
        String flightNumber = scanner.next();
        String sql = "SELECT * FROM Flight WHERE FlightNumber=?";
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

    public void updateFlight() {
        objDBOpns.InitiateDB();

        System.out.print("Enter Flight Number: ");
        String flightNumber = scanner.next();
        System.out.print("Enter Departure City: ");
        String departureCity = scanner.next();
        System.out.print("Enter Destination City: ");
        String destinationCity = scanner.next();
        System.out.print("Enter Departure Time: ");
        String departureTime = scanner.next();
        System.out.print("Enter Arrival Time: ");
        String arrivalTime = scanner.next();

        String sql = "UPDATE Flight SET DepartureCity=?, DestinationCity=?, DepartureTime=?, ArrivalTime=? WHERE FlightNumber=?";
        Object[] params = { departureCity, destinationCity, departureTime, arrivalTime, flightNumber };
        int rowsUpdated = objDBOpns.UpdateDB(sql, params);
        if (rowsUpdated > 0)
            System.out.println("Flight updated successfully!");

        objDBOpns.CloseConnection();
    }

    public void deleteFlight() {
        objDBOpns.InitiateDB();

        System.out.print("Enter Flight Number: ");
        String flightNumber = scanner.next();

        String sql = "DELETE FROM Flight WHERE FlightNumber=?";
        Object[] params = { flightNumber };
        objDBOpns.DeleteDB(sql, params);

        objDBOpns.CloseConnection();
    }

    public static void main(String[] args) {
        UserMenu objAM = new UserMenu();
        Scanner scanner = new Scanner(System.in);
        int mainChoice;
        do {
            System.out.println("User Menu");
            System.out.println("1. Create Flight");
            System.out.println("2. Read Flight");
            System.out.println("3. Read All Flights");
            System.out.println("4. Update Flight");
            System.out.println("5. Delete Flight");
            System.out.println("6. Exit");
            System.out.print("Choose your Option: ");
            mainChoice = scanner.nextInt();
            switch (mainChoice) {
                case 1:
                    objAM.createFlight();
                    break;
                case 2:
                    objAM.readFlight();
                    break;
                case 3:
                    objAM.readAllFlights();
                    break;
                case 4:
                    objAM.updateFlight();
                    break;
                case 5:
                    objAM.deleteFlight();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
                    break;
            }
        } while (mainChoice != 6);
        scanner.close();
    }
}
