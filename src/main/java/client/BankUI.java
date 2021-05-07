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
			input.nextLine();	// this is to consume the rest after the integer is taken

			if (welcomeChoice == 1) { // selected log in

				clearMe = logInInput(input, accountObject, clearMe);
				welcomeChoice = actions(input, accountObject, clearMe);

			} else if (welcomeChoice == 2) { // selected create an account

				createAccountInput(input, accountObject); // create account
				clearMe = logInInput(input, accountObject, clearMe); // log in screen
				welcomeChoice = actions(input, accountObject, clearMe);

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
		boolean createdAccount = true;
		do {
			// if name and email was not in the database then create an account
			// get that information and store into these strings
			String primaryNameOnAccount = recordName(input); // record the user name
			
			String emailOnAccount = recordEmail(input); // record the user email
			// TODO check to see if the email is in the system
			String passwordOnAccount = recordPassword(input); // record the user password

			// record the email and name into the database, 1 is for adding items
			createdAccount = accountObject.addItem(new Data(primaryNameOnAccount, emailOnAccount, passwordOnAccount), 1);
			
		} while (createdAccount != true);

		// now the account is created have the user go to the log in screen
//		logInInput(input, accountObject);

	}

	private static String recordName(Scanner input) {
		createAccountDisplayName(); // display text to user enter name
		return input.nextLine(); // return name on account include spaces in name
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
			System.out.println("correct email: " + ce);
		} while (ce != true);

		do {
			logInDisplayScreen(2);
			password = input.next(); // store password as a string
			cp = validPassword(password, accountObject);// verify the entered password in the database
			System.out.println("correct password: " + cp);
		} while (cp != true);

		clearMe.add(email);				// store email
		clearMe.add(password);			// store password
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
//			dummy = accountObject.checkExistingEmail(AccountEmail);
			dummy = true;	// Override the email checking 
		} catch (Exception e) {
			// TODO here if user had existing email with us
			e.printStackTrace();
		} // an object of the class BankManager
		return dummy;		// if true the email is in the system, user has an account
	}

	private static boolean validPassword(String AccountPassword, BankManager accountObject) {
		boolean dummy = false;
		try {
			// TODO change to new method for checking password
			dummy = accountObject.checkExistingEmail(AccountPassword);
			dummy = true;	// Override the 
		} catch (Exception e) {
			e.printStackTrace();
		}
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
				try {
					c = accountObject.existingChecking(clearMe.get(0)); // use email to get id
					if (c == false) {
						actionsDisplayScreen(4);
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("cannot make connection sorry for the inconveinience");
				}
				System.out.println();
				if (c == true) {
					accountObject.addItem(new Data("Checking", clearMe.get(0), clearMe.get(1)), 2);
				}
			} else if (a == 2) {
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
			b = true; // true means don't add more accounts
		}
		return b;
	}

	private static void actionsDisplayScreen(int a) {
		if (a == 1) {
			System.out.println("Please select an action\n");
			System.out.println("1.  Deposit");
			System.out.println("2.  Quick 40");
			System.out.println("3.  Withdraw");
			System.out.println("4.  Add a Checking or Savings account");
			System.out.println("5.  Exit");
		} else if (a == 2) {
			System.out.println("\n" + "please select an account to add");
			System.out.println("1.  Checking");
			System.out.println("2.  Savings");
		} else if (a == 3) {
			System.out.println("\n" + "do you wish to add a Savings account y/n");
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
			System.out.println("6.  200");
			System.out.println("7.  300");
			System.out.println("8.  400");
			System.out.println("9.  500");
		} else if (a == 14) {
			System.out.println("enter the amount you wish to deposit\n");
		} else if (a ==15) {
			System.out.println("\n" + "Enter a description for this withdrawal");
		} else if (a ==16) {
			System.out.println("\n" + "insufficient funds in the selected account");
		} else if (a == 17) {
			System.out.println("\n" + "Do you wish to make another withdrawal? (y/n)");
		} else if (a == 18) {
			System.out.println("\n" + "insuficient funds in your checking account");
		} else if (a == 19) {
			System.out.println("\n" + "money has been dispensed");
		}
		
	}

	private static void deposit(Scanner input, BankManager accountObject, List<String> clearMe) {
		boolean d = false;
		boolean f = false;
		boolean g = false;
		boolean returnOfTransaction = false;
		int amountInAccount = 0;
		int e = 0;
		float h = 0;
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
				cashOrCheck = "Cash: "; // store cash for description in transaction
			} else if (e == 2) {
				cashOrCheck = "Check: "; // store check for description in transaction
			}
			do {							// determine which checking or savings account
				actionsDisplayScreen(9);	// select the account associated with the deposit
				// email associated with account id, also displays accounts
				userIdList = accountObject.displayCheckingAndSavings(new Data(clearMe.get(0)), true);  
				actionsDisplayScreen(10); // enter the ID display
				e = input.nextInt();
				// loop through each item to see if that account was selected
				for (int i = 0; i < userIdList.size(); i++) {
					if (e == userIdList.get(i))	{ // was this account selected
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
				if (cashOrCheck.contentEquals("Cash: ")) {
					actionsDisplayScreen(7); // ask enter whole dollar amount
					h = input.nextInt(); // get the valid input
					input.nextLine();	// this is to consume the rest after the integer is taken
				}else if (cashOrCheck.contentEquals("Check: ")) {
					actionsDisplayScreen(14); // ask enter amount on the check $123.45
					h = input.nextFloat();
					input.nextLine();	// this is to consume the rest after the number is taken
				}
				if (h <= 0) { // Must be greater than 0 value
					actionsDisplayScreen(8);
				} else if (h > 0) {
					actionsDisplayScreen(11); // add a description
					description = input.nextLine(); // now have the description
					description = cashOrCheck + description; // add the strings together for reasons
					returnOfTransaction = accountObject.addItem(new Data(description, h, deposit, accountSelected), 4); // returnOfTransaction inserts transaction into the table
					amountInAccount  = accountObject.selectItem(new Data(description, h, deposit, accountSelected));	 
					f = accountObject.addItem(new Data(h, accountSelected, amountInAccount ), 5); // update the account with the transaction
					if (f == true && returnOfTransaction == true) {
						f = true; // break out of deposit
					}
				} // done with the checking if the entered amount is greater than 0
			} while (f != true); // done with the deposit action
			d = continueAnotherAccountAdd(input, true, 12); // check if user wants to make another deposit
		} while (d != true);
	}

	private static void withdraw(Scanner input, BankManager accountObject, List<String> clearMe, int selector) {
		boolean h = false;
		boolean f = false;
		boolean d = false;
		int e = 0;
		int withdrawAmount = 0;
		int accountSelected = 0;
		int amountInAccount = 0;
		String description = null;
		String withdraw = "W";
		List<Integer> userIdList = new ArrayList<Integer>();
		boolean returnOfTransaction = false;
		if (selector == 1) { // Here for quick 40 withdraw
			withdrawAmount = 40;
			// userIdList.get(0) will be the checking account
			userIdList = accountObject.displayCheckingAndSavings(new Data(clearMe.get(0)), false); 
			// amountInAccount is the amount left over
			amountInAccount = accountObject.selectItem(new Data(description, withdrawAmount, withdraw, userIdList.get(0)));
			if (amountInAccount < withdrawAmount) {
				actionsDisplayScreen(16); // reject the entered amount for the specific account
			}
			actionsDisplayScreen(19);		// money has been dispensed
			
		} else if (selector == 2) {			// Here for other amount withdraw
			do {							// for continually withdrawing
				actionsDisplayScreen(13);	// pre determined amount to withdraw
				withdrawAmount = input.nextInt();
				if (withdrawAmount == 1) {
					withdrawAmount = 20;
				} else if (withdrawAmount == 2) {
					withdrawAmount = 40;
				} else if (withdrawAmount == 3) {
					withdrawAmount = 60;
				} else if (withdrawAmount == 4) {
					withdrawAmount = 80;
				} else if (withdrawAmount == 5) {
					withdrawAmount = 100;
				} else if (withdrawAmount == 6) {
					withdrawAmount = 200;
				} else if (withdrawAmount == 7) {
					withdrawAmount = 300;
				} else if (withdrawAmount == 8) {
					withdrawAmount = 400;
				} else if (withdrawAmount == 9) {
					withdrawAmount = 500;
				}
				userIdList = accountObject.displayCheckingAndSavings(new Data(clearMe.get(0)), true); // email associated with
				actionsDisplayScreen(10); // enter the ID display
				e = input.nextInt();
				input.nextLine();	// this is to consume the rest after the integer is taken
				
				for (int i = 0; i < userIdList.size(); i++) {
					if (e == userIdList.get(i))	{ // was this account selected
						accountSelected = e; // then the user is selecting this account
						// so e is the account selected
						System.out.println("accountSelected is: " + accountSelected);
						h = true; // break from loop
					} // end of if statement
						// TODO error handling if user did not enter correct number with account
				} // end of for loop
				
				actionsDisplayScreen(15); // add a description
				description = input.nextLine(); // now have the description
				amountInAccount = accountObject.selectItem(new Data(description, withdrawAmount, withdraw, accountSelected));	
				if (amountInAccount <= withdrawAmount) {
					actionsDisplayScreen(16); // reject the entered amount for the specific account
					h = false;
				} else if (amountInAccount >= withdrawAmount) {
					returnOfTransaction = accountObject.addItem(new Data(description, withdrawAmount, withdraw, accountSelected), 4); // returnOfTransaction is the amount in the selected account
					f = accountObject.addItem(new Data(withdrawAmount, accountSelected, amountInAccount ), 6); // update the account with the transaction
				}
				actionsDisplayScreen(19);		// money has been dispensed
				d = continueAnotherAccountAdd(input, true, 17); // check if user wants to make another deposit
			} while (h != true && f != true && d != true && returnOfTransaction != true);
		}
	}
} // end of BankUI