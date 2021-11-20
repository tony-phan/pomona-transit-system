package com.anthonytranphan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Scanner;

public class App 
{
	private static Connection connection = null;
	private static Statement statement = null;
	private static Scanner scanner = new Scanner(System.in);
	private static int input;
	
    public static void main( String[] args ) throws SQLException
    {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab4", "root", "password");
			statement = connection.createStatement();
			
			while(true) {
				showTransactions();
				System.out.print("Enter a number: ");
				input = scanner.nextInt();
				scanner.nextLine(); // consume the extra "\n" that scanner.nextInt() doesn't consume
				
				switch(input) {
					case 1:
						System.out.print("Start Location Name: ");
						String startLocationName1 = scanner.nextLine().trim();
						
						System.out.print("Destination Name: ");
						String destinationName1 = scanner.nextLine().trim();

						System.out.print("Date (YYYY-MM-DD): ");
						String date1 = scanner.nextLine().trim();
						
						showSchedule(startLocationName1, destinationName1, date1);
						
						break;
					case 2:
						System.out.println("Trip #: ");
						String tripNum2 = scanner.nextLine().trim();
						
						System.out.print("Date (YYYY-MM-DD): ");
						String date2 = scanner.nextLine().trim();
						
						System.out.println("Scheduled Start Time: ");
						String scheduledStartTime2 = scanner.nextLine().trim();
						
						deleteTrip(tripNum2, date2, scheduledStartTime2);
						
						break;
					case 3:
						System.out.println("Trip #: ");
						String tripNum3 = scanner.nextLine().trim();
						
						System.out.print("Date (YYYY-MM-DD): ");
						String date3 = scanner.nextLine().trim();
						
						System.out.println("Scheduled Start Time: ");
						String scheduledStartTime3 = scanner.nextLine().trim();
												
						System.out.println("Scheduled Arrival Time: ");
						String scheduledArrivalTime3 = scanner.nextLine().trim();
						
						System.out.println("Driver Name: ");
						String driverName3 = scanner.nextLine().trim();
						
						System.out.println("Bus ID: ");
						String busID3 = scanner.nextLine().trim();
						
						addTrip(tripNum3, date3, scheduledStartTime3, scheduledArrivalTime3, driverName3, busID3);
						
						break;
					case 4:
						System.out.println("Trip #: ");
						String tripNum4 = scanner.nextLine().trim();
						
						System.out.print("Date (YYYY-MM-DD): ");
						String date4 = scanner.nextLine().trim();
						
						System.out.println("Scheduled Start Time: ");
						String scheduledStartTime4 = scanner.nextLine().trim();
						
						System.out.print("New Driver: ");
						String newDriver4 = scanner.nextLine().trim();
						
						changeDriver(tripNum4, date4, scheduledStartTime4, newDriver4); 
						
						break;
					case 5:
						System.out.println("Trip #: ");
						String tripNum5 = scanner.nextLine().trim();
						
						System.out.print("Date (YYYY-MM-DD): ");
						String date5 = scanner.nextLine().trim();
						
						System.out.println("Scheduled Start Time: ");
						String scheduledStartTime5 = scanner.nextLine().trim();
						
						System.out.print("New Bus ID: ");
						String busID5 = scanner.nextLine().trim();
						
						changeBus(tripNum5, date5, scheduledStartTime5, busID5);

						break;
					case 6:
						System.out.println("Trip #: ");
						String tripNum6 = scanner.nextLine().trim();
						
						displayStops(tripNum6);
						
						break;
					case 7:
						System.out.println("Driver Name: ");
						String driverName7 = scanner.nextLine().trim();
						
						System.out.print("Date (YYYY-MM-DD): ");
						String date7 = scanner.nextLine().trim();
						
						weeklySchedule(driverName7, date7);
						
						break;
					case 8:
						System.out.println("Driver Name: ");
						String driverName8 = scanner.nextLine().trim();
						
						System.out.println("Driver Telephone #: ");
						String driverTelephoneNum8 = scanner.nextLine().trim();
						
						addDrive(driverName8, driverTelephoneNum8);

						break;
					case 9:
						System.out.println("Bus ID: ");
						String busID9 = scanner.nextLine().trim();
						
						System.out.println("Model: ");
						String model9 = scanner.nextLine().trim();
						
						System.out.println("Year: ");
						String year9 = scanner.nextLine().trim();
						
						addBus(busID9, model9, year9);
						
						break;
					case 10:
						System.out.println("Bus ID: ");
						String busID10 = scanner.nextLine().trim();
						
						deleteBus(busID10);
						
						break;
					case 11:
						System.out.println("Trip #: ");
						String tripNum11 = scanner.nextLine().trim();
						
						System.out.print("Date (YYYY-MM-DD): ");
						String date11 = scanner.nextLine().trim();
						
						System.out.println("Scheduled Start Time: ");
						String scheduledStartTime11 = scanner.nextLine().trim();
						
						insertTrip(tripNum11, date11, scheduledStartTime11);

						break;
					case 12:
						System.exit(0);
					default:
						System.out.println("Invalid option. Try again");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			statement.close();
	    	connection.close();
	    	scanner.close();
		}
    }
    
    private static void showTransactions() {
    	System.out.println("Available Transactions: \n"
    			+ "1. Show schedule\n"
    			+ "2. Delete a trip\n"
    			+ "3. Add a trip\n"
    			+ "4. Change a driver\n"
    			+ "5. Change a bus\n"
    			+ "6. Display stops\n"
    			+ "7. Display weekly schedule\n"
    			+ "8. Add a drive\n"
    			+ "9. Add a bus\n"
    			+ "10. Delete a bus\n"
    			+ "11. Insert trip\n"
    			+ "12. Exit");
    }
    
    private static void insertTrip(String tripNum, String date, String scheduledStartTime) {
		// TODO Auto-generated method stub
    	String query = "SELECT T.ScheduledArrivalTime " +
    			  	   "FROM TripOffering as T " +
					   "WHERE T.TripNumber = " + tripNum + " AND " +
					   "T.Date = '" + date + "' AND " + 
					   "T. ScheduledStartTime = '" + scheduledStartTime + "'";
    	try {
			ResultSet result = statement.executeQuery(query);
			String arriveTime = "";
			if(result == null) {
				System.out.println("Could not insert a new trip");
			}
			else {
				result.next();
				arriveTime = result.getString(1);
				System.out.println("The arrival time: " + arriveTime);
    			System.out.print("Enter number of stops: ");
    			int numStops = Integer.parseInt(scanner.nextLine());
    			for(int i = 1; i <= numStops; ++i) {
    				String temp;
    				if(i == numStops) {
    					System.out.println("The scheduled arrival time for stop " + numStops + " is " + arriveTime);
    					temp = arriveTime;
    				}
    				else {
    					System.out.print("The scheduled arrival time for stop " + i + "is ");
    					temp = scanner.nextLine().trim();
    				}
    				
    				System.out.print("Actual Start Time For Stop " + i + ": ");
    				String start1 = scanner.nextLine().trim();
    				
    				System.out.print("Actual Arrival Time For Stop " + i + ": ");
    				String arrive1 = scanner.nextLine().trim();
    				
    				System.out.print("Number of Passengers In For Stop " + i + ": ");
    				int passengersIn = Integer.parseInt(scanner.nextLine());
    				
    				System.out.print("Number of Passengers Out For Stop " + i + ": ");
    				int passengersOut = Integer.parseInt(scanner.nextLine());

    				query = "INSERT INTO ActualTripStopInfo VALUES('" + tripNum + 
    						"', '" + date + "', '" + scheduledStartTime + "', '" + i +
    						"', '" + temp + "', '" + start1 + "', '" + arrive1 + 
    						"', '" + passengersIn + "', '" + passengersOut + "')";
    				int res = statement.executeUpdate(query);
    				if(res == 0) {
    					System.out.println("Could not add a new trip");
    				}
    				else {
    					System.out.println("Successfully inserted a new trip");
    				}
    			}
			}
			System.out.println("---------------------------------------------------------");
			
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	private static void deleteBus(String busID) {
		// TODO Auto-generated method stub
		String query = "DELETE FROM Bus " + 
			   	   	   "WHERE BusID = " + busID;
		try {
			int result = statement.executeUpdate(query);
			if(result == 0) {
				System.out.println("Could not delete the bus");
			} 
			else {
				System.out.println("Bus successfully deleted");
			}
			System.out.println("---------------------------------------------------------");
			
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	private static void addBus(String busID, String model, String year) {
		// TODO Auto-generated method stub
		String query = "INSERT INTO Bus (BusID, Model, Year) " + 
				   	   "VALUES ('" + busID + "', '" + model + "', '" + year + "')";
		try {
			int result = statement.executeUpdate(query);
			if(result == 0) {
				System.out.println("Could not add a new bus");
			} 
			else {
				System.out.println("Bus successfully added");
			}
			System.out.println("---------------------------------------------------------");
			
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	private static void addDrive(String driverName, String driverTelephoneNum) {
		// TODO Auto-generated method stub
		String query = "INSERT INTO Driver (DriverName, DriverTelephoneNumber) " + 
					   "VALUES ('" + driverName + "', '" + driverTelephoneNum + "')";
		try {
			int result = statement.executeUpdate(query);
			if(result == 0) {
				System.out.println("Could not add the driver");
			} 
			else {
				System.out.println("Driver successfully added");
			}
			System.out.println("---------------------------------------------------------");
			
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	private static void weeklySchedule(String driverName, String date) {
		// TODO Auto-generated method stub
		String query = "SELECT Date, ScheduledStartTime, ScheduledArrivalTime " + 
					   "FROM TripOffering " + 
					   "WHERE DriverName = '" + driverName + "' AND " +
					   "Date BETWEEN '" + date + "' AND DATE_ADD('" + date + "', INTERVAL 7 DAY)";
		try {
			ResultSet result = statement.executeQuery(query);
			ResultSetMetaData resultColumns = result.getMetaData();
			
			while(result.next()) {
				String row = "";
				for(int i = 1; i < resultColumns.getColumnCount(); ++i) {
					row += (result.getInt(i) + " ");
				}
				System.out.println(row);
			}
			
			result.close();
			System.out.println("---------------------------------------------------------");
			
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

	private static void displayStops(String tripNum) {
		// TODO Auto-generated method stub
		String query = "SELECT * " + 
					   "FROM TripStopInfo as T " + 
					   "WHERE T.TripNumber = " + tripNum;
		try {
			ResultSet result = statement.executeQuery(query);
			ResultSetMetaData resultColumns = result.getMetaData();
			
			while(result.next()) {
				String row = "";
				for(int i = 1; i < resultColumns.getColumnCount(); ++i) {
					row += (result.getInt(i) + " ");
				}
				System.out.println(row);
			}
			
			result.close();
			System.out.println("---------------------------------------------------------");
			
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

	private static void changeBus(String tripNum, String date, String scheduledStartTime, String busID) {
		// TODO Auto-generated method stub
		String query = "UPDATE TripOffering" + 
					   "SET BusID = '" + busID + "' " + 
					   "WHERE TripNumber = '" + tripNum + "' AND " +
					   "Date = '" + date + "' AND " + 
					   "ScheduledStartTime = '" + scheduledStartTime + "'";
		try {
			int result = statement.executeUpdate(query);
			if(result == 0) {
				System.out.println("Could not change the bus");
			} 
			else {
				System.out.println("Bus sucessfully changed.");
			}
			System.out.println("---------------------------------------------------------");
			
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	private static void changeDriver(String tripNum, String date, String scheduledStartTime, String newDriver) {
		// TODO Auto-generated method stub
		String query = "UPDATE TripOffering" + 
					   "SET DriverName = '" + newDriver + "' " + 
					   "WHERE TripNumber = '" + tripNum + "' AND " +
					   "Date = '" + date + "' AND " + 
					   "ScheduledStartTime = '" + scheduledStartTime + "'";
		try {
			int result = statement.executeUpdate(query);
			if(result == 0) {
				System.out.println("Could not change the driver");
			} 
			else {
				System.out.println("Bus driver sucessfully changed.");
			}
			System.out.println("---------------------------------------------------------");
			
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	private static void addTrip(String tripNum, String date, String scheduledStartTime, String scheduledArrivalTime, String driverName, String busID) {
		// TODO Auto-generated method stub
		boolean flag = true;
		while(flag) {
			String query = "INSERT INTO TripOffering VALUES ('" + tripNum + "', '" + date + "', '" + scheduledStartTime + "', '" + scheduledArrivalTime +
			"', '" + driverName + "', '" + busID + "')";
			try {
				int result = statement.executeUpdate(query);
				if(result == 0) {
					System.out.println("Unable to add trip.");
				}
				else {
					System.out.println("Trip successfully added.");
				}
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("Unable to add trip.");
			}

			System.out.println("Do you want to add another trip? (yes/no): ");
			String input = scanner.nextLine();
			input.trim().toLowerCase();
			
			if(input.equals("yes") || input.equals("y")) {
				System.out.println("Trip #: ");
				tripNum = scanner.nextLine().trim();
				
				System.out.print("Date (YYYY-MM-DD): ");
				date = scanner.nextLine().trim();
				
				System.out.println("Scheduled Start Time: ");
				scheduledStartTime = scanner.nextLine().trim();
										
				System.out.println("Scheduled Arrival Time: ");
				scheduledArrivalTime = scanner.nextLine().trim();
				
				System.out.println("Driver Name: ");
				driverName = scanner.nextLine().trim();
				
				System.out.println("Bus ID: ");
				busID = scanner.nextLine().trim();
			}
			else {
				flag = false;
			}
		}
	}

	private static void deleteTrip(String tripNum, String date, String scheduledStartTime) {
		// TODO Auto-generated method stub
		String query = "DELETE FROM TripOffering as T " +
					   "WHERE T.TripNumber = '" + tripNum + "' AND " +
					   "T.Date = '" + date + "' AND " + 
					   " T.ScheduledStartTime = '" + scheduledStartTime + "'";
		try {
			int result = statement.executeUpdate(query);
			if(result == 0) {
				System.out.println("Unable to delete trip");
			}
			else {
				System.out.println("Trip successfully deleted");
			}
			System.out.println("---------------------------------------------------------");

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private static void showSchedule(String startLocationName, String destinationName, String date) {
		// TODO Auto-generated method stub
		String query = "SELECT * " +
					   "FROM Trip, TripOffering " +
					   "WHERE Trip.TripNumber = TripOffering.TripNumber AND " +
					   "Trip.StartLocationName LIKE '" + startLocationName + "' AND " + 
					   "Trip.DestinationName LIKE '" + destinationName + "' AND " + 
					   "TripOffering.Date = '" + date + "' " + 
					   "ORDER BY TripOffering.ScheduledStartTime";
		try {
			ResultSet result = statement.executeQuery(query);
			ResultSetMetaData resultColumns = result.getMetaData();
			
			while(result.next()) {
				String row = "";
				for(int i = 1; i < resultColumns.getColumnCount(); ++i) {
					row += (result.getInt(i) + " ");
				}
				System.out.println(row);
			}
			
			result.close();
			System.out.println("---------------------------------------------------------");
		
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
