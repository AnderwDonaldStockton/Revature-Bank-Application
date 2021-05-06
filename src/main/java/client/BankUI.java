package client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import buisiness.BankManager;
import model.Data;

public class BankUI {
	public static void main(String[] args) {

		Scanner input = new Scanner(System.in); // start the scanner input
		welcomeScreen(input); // show the welcome screen and while looping

		input.close(); // close the input of the screen
		System.exit(0); // exit the program
	} // end of main

	private static void welcomeScreen(Scanner input) {

		int welcomeChoice = 0;
		BankManager accountObject = new BankManager();
		List<String> clearMe = new ArrayList<String>();

		do {
			welcomeScreenDisplay(); // start the selection choice
			welcomeChoice = input.nextInt(); // read the next input

			if (welcomeChoice == 1) { // selected log in

				clearMe = logInInput(input, accountObject, clearMe);
				welcomeChoice = actions(input, accountObject, clearMe);

			} else if (welcomeChoice == 2) { // selected create an account

				createAccountInput(input, accountObject); // create account
				clearMe = logInInput(input, accountObject, clearMe); // log in screen
				// TODO implement actions method

			} else if (welcomeChoice == 3) { // selected exit
				welcomeChoice = 0; // break out of program
			}

		} while (welcomeChoice > 0 && welcomeChoice < 4);
		System.out.println("Thank you for choosing Professional Bank");
	}

	private static void welcomeScreenDisplay() {
		System.out.println("Welcome to the Professional Bank" + "\n");
		System.out.println("1.  Log in");
		System.out.println("2.  Apply for an account");
		System.out.println("3.  Exit");
	}

	private static void createAccountInput(Scanner input, BankManager accountObject) {
		boolean hoopla = true;
//		BankManager accountObject = new BankManager();

		do {
			// if name and email was not in the database then create an account
			// get that information and store into these strings
			String primaryNameOnAccount = recordName(input); // record the user name
			String emailOnAccount = recordEmail(input); // record the user email
			// TODO check to see if the email is in the system
			String passwordOnAccount = recordPassword(input); // record the user password

			// record the email and name into the database, 1 is for adding items
			accountObject.addItem(new Data(primaryNameOnAccount, emailOnAccount, passwordOnAccount), 1);

			// TODO need to add condition to attempt to stay in this check,
			hoopla = true; // this is just for testing purposes

		} while (hoopla != true);

		// now the account is created have the user go to the log in screen
//		logInInput(input, accountObject);

	}

	private static String recordName(Scanner input) {
		createAccountDisplayName(); // display text to user enter name
		return input.next(); // return name on account
	}

	private static void createAccountDisplayName() {
		System.out.println("Thank you for choosing Professional Bank" + "\n");
		System.out.println("To start please enter your name: ");
	}

	private static String recordEmail(Scanner input) {
//		boolean hoopla = true;
//		String AccountEmail = null;
		// not sure I have to have a reason for a while loop here
//		do {										// loop through to make sure email is unique
//			createAccountDisplayEmail();			// display enter email
//			AccountEmail= input.next();				// record email when user types it in
//			// code to check the email in the database, true if yes the email exists their,
//			boolean existingEmail = validEmail(AccountEmail,accountObject);
//			if (existingEmail == true) {					
//				hoopla = false;						// the user entered a new email
//				System.out.println("here if the email is new");
//				accountObject.addItem(new Data(AccountEmail)); // add the email to the server
//				
//			}else if (existingEmail == false) {
//				hoopla = true;						// the email already exists in the system
//				System.out.println("here if user allready has an account");
//			}
//		}while(hoopla != true);

		createAccountDisplayEmail();
//		AccountEmail = input.next();

		return input.next();
	}

	private static void createAccountDisplayEmail() {
		System.out.println("\nPlease enter an email: ");
	}

	private static String recordPassword(Scanner input) {
		String passwordOne = null;
		String passwordTwo = null;
		boolean breakloop = false;

		do {
			createAccountDisplayPassword(1); // prompt user to enter password first time
			passwordOne = input.next();
			createAccountDisplayPassword(2); // prompt user to enter password second time
			passwordTwo = input.next();

			if (passwordOne.equals(passwordTwo)) { // check to see if pass1 == pass2
				breakloop = true; // break the loop, the user entered the incorrect password
				createAccountDisplayPassword(4);
			} else { // the two passwords are not the same
				breakloop = false; // continue looping
				createAccountDisplayPassword(3); // display the passwords are not correct
			}
		} while (breakloop != true);

		return passwordTwo; // pass1==pass2 are the same return pass2
	}

	private static void createAccountDisplayPassword(int a) {
		if (a == 1) {
			System.out.println("Please enter a password: ");
		} else if (a == 2) {
			System.out.println("Please enter the password a second time: ");
		} else if (a == 3) {
			System.out.println("The second password did not match please try again.");
		} else if (a == 4) {
			System.out.println("the passwords match\n please keep the password for your records\n\n\n");
		}
	}

	private static List<String> logInInput(Scanner input, BankManager accountObject, List<String> clearMe) {

		String email = null;
		String password = null;
		boolean ce = false;
		boolean cp = false;
		do {
			logInDisplayScreen(1);
			email = input.next(); // store email as a string
			ce = validEmail(email, accountObject); // verify the entered email in the database
		} while (ce != true);

		do {
			logInDisplayScreen(2);
			password = input.next(); // store password as a string
			cp = validPassword(password, accountObject);// verify the entered password in the database
		} while (cp != true);

		// TODO need to store email and password
		clearMe.add(email);
		clearMe.add(password);
		return clearMe;
	}

	private static void logInDisplayScreen(int a) {
		if (a == 1) {
			System.out.println("Please enter the email on the account");
		} else if (a == 2) {
			System.out.println("Please enter the password on the account");
		}
	}

	private static boolean validEmail(String AccountEmail, BankManager accountObject) {
		boolean dummy = false;
		try {
			// TODO
			dummy = accountObject.checkExistingEmail(AccountEmail);
		} catch (Exception e) {
			// TODO here if user had existing email with us
			e.printStackTrace();
		} // an object of the class BankManager
		System.out.println("class: BankUI method:validEmail variable: dummy is: " + dummy);
		return dummy;
	}

	private static boolean validPassword(String AccountPassword, BankManager accountObject) {
		boolean dummy = false;
		try {
			// TODO change to new method for checking password
			dummy = accountObject.checkExistingEmail(AccountPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("class: BankUI method:validPassword variable: dummy is: " + dummy);
		return dummy;
	}

	private static int actions(Scanner input, BankManager accountObject, List<String> clearMe) {
		boolean a = false;
		int accountChoice = 0;
		do {
			actionsDisplayScreen(1); // display options for the customer
			accountChoice = input.nextInt();

			if (accountChoice == 1) { // Deposit Money
				deposit(input, accountObject, clearMe);
			} else if (accountChoice == 2) { // Quick withdraw 40 from checking
				withdraw(input, accountObject, clearMe, 1);
			} else if (accountChoice == 3) { // withdraw
				withdraw(input, accountObject, clearMe, 2);
			} else if (accountChoice == 4) { // Add a checking or savings account
				createSavingsOrChecking(input, accountObject, clearMe); // the method for creating a savings account
			} else if (accountChoice == 5) { // exit the banking application
				a = true; // customer is done with banking for now
			}
		} while (a != true);
		return 0; // 0 is passed to the welcome screen
	}

	private static void createSavingsOrChecking(Scanner input, BankManager accountObject, List<String> clearMe) {
		boolean b = false; // b is for continuing to create a savings account
		boolean c = false; // c is for checking if a Checking account exists
		int a = 0; // a is for selecting what to display
					// clearMe.get(0) is email
					// clearMe.get(1) is password
		do {
			actionsDisplayScreen(2);
			a = input.nextInt();

			if (a == 1) {
				// TODO add a feature to add checking account
				try {
					c = accountObject.existingChecking(clearMe.get(0)); // use email to get id
					if (c == false) {
						actionsDisplayScreen(4);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("cannot make connection sorry for the inconveinience");
				}
				System.out.println();
				if (c == true) {
					accountObject.addItem(new Data("Checking", clearMe.get(0), clearMe.get(1)), 2);
				}
			} else if (a == 2) {
				// TODO add feature to add savings account
				System.out.println();
				accountObject.addItem(new Data("Savings", clearMe.get(0), clearMe.get(1)), 3);
				actionsDisplayScreen(5);
			}
			b = continueAnotherAccountAdd(input, b, 3);
		} while (b != true);
	}

	private static boolean continueAnotherAccountAdd(Scanner input, boolean b, int a) {
		String c = null;
		actionsDisplayScreen(a);
		c = input.next();

		if (c.equalsIgnoreCase("y")) {
			b = false; // false means continue adding accounts
		} else if (c.equalsIgnoreCase("n")) {
			b = true; // true means dont add more accounts
		}
		return b;
	}

	private static void actionsDisplayScreen(int a) {
		if (a == 1) {
			System.out.println("Please select an action\n");
			System.out.println("1.  Deposite");
			System.out.println("2.  Quick 40");
			System.out.println("3.  Withdraw");
			System.out.println("4.  Add a Checking or Savings account");
			System.out.println("5.  Exit");
		} else if (a == 2) {
			System.out.println("\n" + "please select an account to add");
			System.out.println("1.  Checking");
			System.out.println("2.  Savings");
		} else if (a == 3) {
			System.out.println("\n" + "do you wish to add another Savings account y/n");
		} else if (a == 4) {
			System.out.println("\n" + "a checking account exists allready");
			System.out.println("only one checking account can be made");
		} else if (a == 5) {
			System.out.println("you have created another Savings account");
		} else if (a == 6) {
			System.out.println("Please select an action\n");
			System.out.println("1.  Deposit Cash");
			System.out.println("2.  Deposit Check");
		} else if (a == 7) {
			System.out.println("enter the amount you wish to deposit in whole dollar ammount\n");
		} else if (a == 8) {
			System.out.println("Amounts less than $1 cannot be deposited");
		} else if (a == 9) {
			System.out.println("Select the account associated with the deposit" + "\n");
		} else if (a == 10) {
			System.out.println("\n" + "Select the ID associated with the account");
		} else if (a == 11) {
			System.out.println("\n" + "Enter a description for this deposit");
		} else if (a == 12) {
			System.out.println("\n" + "Do you wish to make another deposit? (y/n)");
		} else if (a == 13) {
			System.out.println("Please select an amount to withdraw");
			System.out.println("1.  20");
			System.out.println("2.  40");
			System.out.println("3.  60");
			System.out.println("4.  80");
			System.out.println("5.  100");
		}
	}

	private static void deposit(Scanner input, BankManager accountObject, List<String> clearMe) {
		boolean d = false;
		boolean f = false;
		boolean g = false;
		int returnOfTransaction = 0;
		boolean returnOfAccountAdd = false;
		int e = 0;
		int accountSelected = 0;
		String cashOrCheck = null;
		String description = null;
		String deposit = "D";
		List<Integer> userIdList = new ArrayList<Integer>();
		// clearMe.get(0) is email
		// clearMe.get(1) is password
		do { // for continually depositing
			actionsDisplayScreen(6); // display to customer cash or check
			e = input.nextInt(); // get the input option, cash v check
			if (e == 1) { // if cash go here
				cashOrCheck = "Cash"; // store cash for description in transaction
			} else if (e == 2) {
				cashOrCheck = "Check"; // store check for description in transaction
			}
			// determine which checking or savings account
			do {
				actionsDisplayScreen(9); // select the account associated with the deposit
				userIdList = accountObject.displayCheckingAndSavings(new Data(clearMe.get(0))); // email associated with
																								// account id, also
																								// displays accounts
				actionsDisplayScreen(10); // enter the ID display
				e = input.nextInt();
				for (int i = 0; i < userIdList.size(); i++) { // loop through each item to see if that account was
																// selected
					if (e == userIdList.get(i))
						;
					{ // was this account selected
						accountSelected = e; // then the user is selecting this account
						// so e is the account selected
						System.out.println("accountSelected is: " + accountSelected);
						g = true; // break from loop
					} // end of if statement
						// TODO error handling if user did not enter correct number with account
				} // end of for loop

			} while (g != true);
			// now accountSelected is equal to the account the user picked
			do { // do while loop for making sure deposit amount is correct
				actionsDisplayScreen(7); // ask enter whole dollar amount
				e = input.nextInt(); // get the valid input
				if (e <= 0) { // Must be greater than 0 value
					actionsDisplayScreen(8);
				} else if (e > 0) {
					actionsDisplayScreen(11); // add a description
					description = input.next(); // now have the description
					returnOfTransaction = accountObject.selectItem(new Data(description, e, deposit, accountSelected));
					returnOfAccountAdd = accountObject.addItem(new Data(e, accountSelected, returnOfTransaction), 5); // update
																														// the
																														// account
																														// with
																														// the
																														// transaction
					if (f == true) {
						f = true; // break out of deposit
					}
				} // done with the
			} while (f != true); // done with the deposit action
			d = continueAnotherAccountAdd(input, true, 12); // check if user wants to make another deposit
		} while (d != true);
	}

	private static void withdraw(Scanner input, BankManager accountObject, List<String> clearMe, int selector) {
		boolean h = false;
		int withdrawAmount = 0;
		if (selector == 1) { // Here for quick 40 withdraw
			do {

			} while (h != true);
		} else if (selector == 2) { // Here for other amount withdraw
			do {
				actionsDisplayScreen(13); // pre determined amount to withdraw
				withdrawAmount = input.nextInt();

			} while (h != true);
		}
	}

} // end of BankUI
