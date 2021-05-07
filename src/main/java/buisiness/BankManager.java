package buisiness;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import common.UserSingleton;
import dao.UserDAO;
import model.Data;

public class BankManager {

	// don't change
	private UserDAO dao = new UserDAO();
	private UserSingleton clearMe = new UserSingleton();

	@SuppressWarnings("unused")
	public Boolean checkExistingEmail(final String AccountEmail) throws Exception {
		boolean result = false;
		List<Data> accountInfo = new ArrayList<Data>();
		accountInfo = dao.checkExistingEmail(AccountEmail); // now we use the user DAO to see if they are in the system
//		System.out.println("accountInfo is: " + accountInfo);
//		System.out.println("accountInfo size is: " + accountInfo.size());
//		System.out.println("accountInfo toArray is: " + accountInfo.toArray());
//		System.out.println("accountInfo toArray length is: " + accountInfo.toArray().length);
//		System.out.println("accountInfo itereator is: " + accountInfo.listIterator());
//		System.out.println("accountInfo itereator is: " + accountInfo.listIterator(0));
//		System.out.println("accountInfo itereator is: " + accountInfo);
		
		// TODO access the database and get a list of email's in the database to check
		
		
		// place the users entered email and compares it from the database
//		List<Object> emailUsersList = dao.checkExistingEmail(AccountEmail).parallelStream().filter(s -> s.equals(AccountEmail)).collect(Collectors.toList());
		List<Object> emailUsersList = accountInfo.parallelStream().filter(s -> s.equals(AccountEmail)).collect(Collectors.toList());
		
//		System.out.println("The size is before: " +emailUsersList.size());
		
		if (emailUsersList.size() > 0) {
			System.out.println("The size is: " +emailUsersList.size());
			for (int i = 0 ; i < emailUsersList.size() ; i++) {
				System.out.println("Print out information: " +emailUsersList.get(i));
			}
			result = true; // then the email is in the database
		} else if (emailUsersList == null) {
			System.out.println("do I get here");
			result = false; // then the email is not in the database
		}
		return result; // return a boolean if the email is in the database
	}

	public boolean existingChecking(String email) throws Exception {
		boolean c = true;
		String existingChecking = dao.checkExistingChecking(email, "Checking");
		System.out.println("existingChecking: " + existingChecking);
		
		if (existingChecking == null) {
			c = true; // a checking account does not exist make one
		} else if (existingChecking.equals("Checking")) {
			c = false; // a checking account exists
		}
		return c;
	}

	public boolean addItem(Data data, int selector) {
		return dao.addItem(data, selector); // go to UserDAO to save the data for now
	}

	public int selectItem(Data data) { // for accessing the amount in the desired account
		return dao.selectItem(data);
	}

	public void storeData(Data data) {
		clearMe.storeDataUS(data);
	}

	public List<Integer> displayCheckingAndSavings(Data data, boolean selector) {
		return dao.displayCheckingAndSavings(data, selector);
	}

}
