package com;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Item {

//Database Connection	
public Connection connect(){
	
	Connection con = null;
	
	try{
		
		Class.forName("com.mysql.jdbc.Driver");
		con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/item_db","root", "root");
		//For testing
		System.out.print("Successfully connected");
		
	}
	
	catch(Exception e){
		
		e.printStackTrace();
	}
	
	return con;
	
}


//Insert
public String insertItem(String code, String name, String price, String desc){ 
	
	
    String output = ""; 
    
    try{
    	
       Connection con = connect(); 
       
       
    if (con == null) {
    	
    	return "Error while connecting to the database"; 
    } 
    
    //insert query
    String query = " insert into items(`itemID`,`itemCode`,`itemName`,`ItemPrice`,`itemDesc`)"+ " values(?, ?, ?, ?, ?)"; 
    PreparedStatement preparedStmt = con.prepareStatement(query); 
	
    
    
	 preparedStmt.setInt(1, 0); 
	 preparedStmt.setString(2, code); 
	 preparedStmt.setString(3, name); 
	 preparedStmt.setDouble(4, Double.parseDouble(price)); 
	 preparedStmt.setString(5, desc);
	 
	 
	 preparedStmt.execute(); 
	 con.close(); 
	 output = "Inserted successfully"; 
	 } 
	 catch (Exception e) 
	 { 
		 output = "Error while inserting"; 
		 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 
	 
}


//Read
public String readItems()
{ 
	 String output = ""; 
	 
	 try
	 { 
		 Connection con = connect(); 
		 
	 if (con == null) 
	 { 
		 return "Error while connecting to the database for reading."; 
	 } 
	 
	 //table craeation
	 output = "<table border='1'><tr><th>Item Code</th>" +"<th>Item Name</th><th>Item Price</th>"
	 + "<th>Item Description</th>" + "<th>Update</th><th>Remove</th></tr>"; 
	 String query = "select * from items"; 
	 
	 Statement stmt = (Statement) con.createStatement(); 
	 ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query); 
	 
	 while (rs.next()) 
		 
	 { 
	 String itemID = Integer.toString(rs.getInt("itemID")); 
	 String itemCode = rs.getString("itemCode"); 
	 String itemName = rs.getString("itemName"); 
	 String itemPrice = Double.toString(rs.getDouble("itemPrice")); 
	 String itemDesc = rs.getString("itemDesc"); 
	 
	 
	 
	 output += "<tr><td>" + itemCode + "</td>"; 
	 output += "<td>" + itemName + "</td>"; 
	 output += "<td>" + itemPrice + "</td>";
	 output += "<td>" + itemDesc + "</td>";
	 
	 
	 
	 
	 output += "<td><input name='btnUpdate' " 
	 + " type='button' value='Update' onclick='UpdateItems()'></td>"
	 + "<td><form method='post' action='items.jsp'>"
	 + "<input name='btnRemove' " 
	 + " type='submit' value='Remove' onclick='DeleteItems('"+itemID+"')'>"
	 + "<input name='itemID' type='hidden' " 
	 + " value='" + itemID + "'>" + "</form></td></tr>"; 
	 } 
	 
	 
	 con.close(); 
	
	 
	 
	 output += "</table>"; 
	 } 
	 
	 
	 catch (Exception e) 
	 { 
		 output = "Error while reading the items."; 
		 System.err.println(e.getMessage()); 
	 } 
	
	
	return output; 
}

//Update
public String UpdateItems(String itemID,String code, String name, String price, String desc)
{
	
	 String output = "";
	 
	 
		try
		 { 
		 Connection con = connect(); 
		 if (con == null) 
		 { 
		 return "Error while connecting to the database"; 
		 } 
		 
		 String query = " Update items set itemCode='"+code+"',itemName = '"+name+"',ItemPrice = '"+price+"',itemDesc = '"+desc+"'"
				 + "where itemID ='"+itemID+"'";
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		
		 
		 
		 preparedStmt.execute(); 
		 con.close(); 
		 output = "updated successfully"; 
		 } 
		catch (Exception e) 
		 { 
		 output = "Error while updating the items"; 
		 System.err.println(e.getMessage()); 
		 } 
		return output; 
	
}

//Delete
public String deleteItems(String itemID) {
	
		 String output = "";
//		 Connection connection = null;
//		 PreparedStatement preparedStatement = null;

			try
			{
				Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for deleting.";
			}
				// create a prepared statement
				String query = "delete from items where itemID=?";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				// binding values
				preparedStmt.setInt(1, Integer.parseInt(itemID));
				// execute the statement
				preparedStmt.execute();
				con.close();
				
				output = "Deleted successfully";
			}
			catch (Exception e)
			{
				output = "Error while deleting the item.";
				System.err.println(e.getMessage());
			}
			return output;
	
	}
	
}



	
