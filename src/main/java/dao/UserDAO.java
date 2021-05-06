package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Data; // importing the model Data from class Data
import util.DBConnection;

/**
 * This class and methods creates a new user
 * 
 * 
 * 
 * @author andrew
 */
public class UserDAO {

	public List<Data> checkExistingEmail(String AccountEmail) throws Exception {

		List<Data> userinfo = new ArrayList<Data>();

		Connection con = DBConnection.getInstance().getConnection(); // start connection

		String inDatabase = "Select id,name,email,join_date,password from \"BankAppUser\" where email = ?";
		PreparedStatement pstmt = con.prepareStatement(inDatabase);

		pstmt.setString(1, AccountEmail); // do logic check if the email is in the

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) { // get the data from the users contact list
			Integer DatabaseId = rs.getInt("id");
			String DatabaseName = rs.getString("name");
			String DatabaseEmail = rs.getString("email");
			String DatabaseJoinDate = rs.getString("join_date");
			String DatanasePassword = rs.getString("password");

			// make a new object in java to store the data in the database
			userinfo.add(new Data(DatabaseId, DatabaseName, DatabaseEmail, DatabaseJoinDate, DatanasePassword));
		}
		return userinfo;
	}

	public String checkExistingChecking(String email, String checking) throws Exception {
		String userInfo = null;
		int userInt = 0;
		// search for the account on BankAppUser
		Connection con = DBConnection.getInstance().getConnection(); // start connection
		String inDatabase = "Select id from \"BankAppUser\" where email = ?;";
		PreparedStatement pstmt1 = con.prepareStatement(inDatabase);
		pstmt1.setString(1, email);
		ResultSet rs1 = pstmt1.executeQuery();
		if (rs1.next()) {
			userInt = rs1.getInt("id");
		}

		// search for the account on BankAppAccount
		inDatabase = "Select name from \"BankAppAccount\" where (fk_customer_id = ? and name = ?);";
		PreparedStatement pstmt2 = con.prepareStatement(inDatabase);
		pstmt2.setInt(1, userInt);
		pstmt2.setString(2, checking);
		ResultSet rs2 = pstmt2.executeQuery();
		if (rs2.next()) {
			userInfo = rs2.getString("name");
		}
		return userInfo;
	}

	public boolean addItem(Data data, int selector) {
		String inDatabase = null;
		boolean result = false;
		try {
			Connection con = DBConnection.getInstance().getConnection(); // get connection
			if (selector == 1) { // this is for creating an account
				inDatabase = "insert into \"BankAppUser\" (\"name\", \"email\", \"join_date\", \"password\") values (?, ?, pg_catalog.clock_timestamp(), ?);";
			} else if (selector == 2) { // adding an account
				inDatabase = "insert into \"BankAppAccount\" (\"name\", \"balance\", \"fk_customer_id\") values (?, ?,(select id from \"BankAppUser\" where \"email\" = ?));";
			} else if (selector == 3) {
				inDatabase = "insert into \"BankAppAccount\" (\"name\",\"primary_account\", \"balance\", \"fk_customer_id\") values (?, ?, ?,(select id from \"BankAppUser\" where \"email\" = ?));";
			} else if (selector == 4) { // TODO adding a transaction to an account
				inDatabase = "insert into \"BankAppTransaction\" (\"trans_date\", \"description\", \"amount\", \"trans_type\", \"account_id\")"
						+ "      values (pg_catalog.clock_timestamp(),                 ?,               ?,         ?,             (select id from \"BankAppAccount\" where \"id\" = ?));";
			} else if (selector == 5) {
				inDatabase = "update \"BankAppAccount\" set \"balance\" = ? where \"id\" = ?;";
			}

			PreparedStatement pstmt = con.prepareStatement(inDatabase); // be able to store the data into the database

			if (selector == 1) { // selected create account
				pstmt.setString(1, data.getName()); // set the entered name in the database
				pstmt.setString(2, data.getEmail()); // set the entered email in the database
//				pstmt.setString(3, "pg_catalog.clock_timestamp()");			// set the created time	    in the database
				pstmt.setString(3, data.getPassword()); // set the entered password in the database
			} else if (selector == 2) {
				pstmt.setString(1, data.getName()); // set the entered name in the database
				pstmt.setInt(2, 0); // set the initial balance in the database
				System.out.println("email: " + data.getEmail());
				pstmt.setString(3, data.getEmail()); // link the email in the database
			} else if (selector == 3) {
				pstmt.setString(1, data.getName()); // set the entered name in the database
				pstmt.setString(2, "N");
				pstmt.setInt(3, 0); // set the initial balance in the database
				pstmt.setString(4, data.getEmail()); // link the email in the database
			} else if (selector == 4) {
				pstmt.setString(1, data.getDescription()); // set the entered desc in the database
				pstmt.setInt(2, data.getAmount());
				pstmt.setString(3, data.getTransactionType()); // set the initial balance in the database
				pstmt.setInt(5, data.getAccountId()); // link the email in the database
			} else if (selector == 5) { // this chuck here adds transaction amount to selected account
				pstmt.setInt(1, data.getOldAmount() + data.getAmount()); // add the two amounts up and store into
				pstmt.setInt(2, data.getAccountId());
			}

			int addItemTrue = pstmt.executeUpdate(); // this is a check to make sure the data was committed

			if (addItemTrue != 0) {
				result = true;
			} else {
				result = false;
				System.out.println("error in UserDAO");
			}

			// trying to do more than one option

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result; // return true if the data was added to the database
	}

	public int selectItem(Data data) { // so far used in the deposit transaction
		int previousBalance = 0;
		String inDatabase = null;
		try {
			Connection con = DBConnection.getInstance().getConnection(); // get connection
			inDatabase = "select \"balance\" from \"BankAppAccount\" where \"id\" = ?;";
			PreparedStatement pstmt = con.prepareStatement(inDatabase); // make prepared statement object
			pstmt.setInt(1, data.getAccountId()); // pass the account id into the select statement
			ResultSet rs = pstmt.executeQuery(); // update the query

			if (rs.next()) {
				previousBalance = rs.getInt("balance"); // get that previous amount and pass it along
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return previousBalance;
	}

	public List<Integer> displayCheckingAndSavings(Data data) {
		String userInfo = null;
		int userID = 0;
		int accountID = 0;
		String Savings = "Savings";
		String Checking = "Checking";
		List<Integer> userIdList = new ArrayList<Integer>();

		// search for the account on BankAppUser
		try {
			Connection con = DBConnection.getInstance().getConnection(); // start connection
			String inDatabase = "Select id from \"BankAppUser\" where email = ?;";
			PreparedStatement pstmt1 = con.prepareStatement(inDatabase);
			pstmt1.setString(1, data.getEmail());
			ResultSet rs1 = pstmt1.executeQuery();
			if (rs1.next()) {
				userID = rs1.getInt("id"); // now I have the id from the database
			}

			inDatabase = "Select name,id from \"BankAppAccount\" where (fk_customer_id = ? and name = ? or name = ?);";
			PreparedStatement pstmt2 = con.prepareStatement(inDatabase);
			pstmt2.setInt(1, userID);
			pstmt2.setString(2, Savings);
			pstmt2.setString(3, Checking);
			ResultSet rs2 = pstmt2.executeQuery();
			while (rs2.next()) {
				userInfo = rs2.getString("name");
				accountID = rs2.getInt("id");
				System.out.println(userInfo + " ID: " + accountID); // printing out each account the user has
				userIdList.add(accountID); // add the account number to the output list
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userIdList;

	}

}
