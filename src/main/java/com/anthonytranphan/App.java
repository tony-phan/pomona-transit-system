package com.anthonytranphan;

import java.sql.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee", "root", "password");
			Statement st= con.createStatement();
	    	ResultSet rs= st.executeQuery("SELECT * FROM Persons WHERE city = \"Los Angeles\"");
	    	rs.next();
	    	
	    	String LastName= rs.getString("LastName");
	    	String FirstName= rs.getString("FirstName");
	    	String City= rs.getString("City");
	    	
	    	System.out.println(FirstName+ " "+ LastName+ " "+ City);
	    	
	    	st.close();
	    	con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
