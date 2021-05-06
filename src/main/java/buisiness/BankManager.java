package buisiness;

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
		dao.checkExistingEmail(AccountEmail); // now we use the user DAO to see if they are in the system

		// place the users entered email and compares it from the database
		List<Object> emailUsersList = dao.checkExistingEmail(AccountEmail).parallelStream()
				.filter(s -> s.equals(AccountEmail)).collect(Collectors.toList());

		if (emailUsersList != null) {
			result = true; // then the email is in the database
		} else if (emailUsersList == null) {
			result = false; // then the email is not in the database
		}
		return result; // return a boolean if the email is in the database
	}

	public boolean existingChecking(String email) throws Exception {
		boolean c = true;
		String existingChecking = dao.checkExistingChecking(email, "Checking");

		if (existingChecking.equals("Checking")) {
			c = false; // a checking account
		} else if (existingChecking.equals(null)) {
			c = true; // a checking account does not exist make one
		}
		return c;
	}

	// TODO not sure I need to return the boolean value
	public boolean addItem(Data data, int selector) { // get here from createAccount method in BankUI class
		// TODO, may use this same one for adding other items
		// to check what addItem method to go to
		return dao.addItem(data, selector); // go to UserDAO to save the data for now
	}

	public int selectItem(Data data) { // for accessing the amount in the desired account
		return dao.selectItem(data);
	}

	public void storeData(Data data) {
		clearMe.storeDataUS(data);
	}

	public List<Integer> displayCheckingAndSavings(Data data) {
		return dao.displayCheckingAndSavings(data);
	}

}
