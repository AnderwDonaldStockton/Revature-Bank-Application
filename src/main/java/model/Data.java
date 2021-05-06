package model;

// POJO of 
public class Data {

	private int id; //
	private String name; //
	private String email; //
	private String password; //
	private String joinDate; // user level: for determining
	private String transactionType; // transaction level: is it a withdraw or deposit
	private int amount; // transaction & account level: amount deposited or withdrawn
	private String description; // transaction level: description
	private int accountId; // transaction level: used for the account ID
	private int oldAmount; // account level: previous amount in the account

	public Data(String email) {
		this.email = email;
	}

// before it was this	
//	public Data(String accountType) {
//		this.setAccountType(accountType);
//	}

	public Data(int amount, int accountId, int oldAmount) { // for a deposit or withdrawal
		this.accountId = accountId;
		this.amount = amount;
		this.oldAmount = oldAmount;
	}

	public Data(String email, String password) {
		this.email = email;
		this.password = password;
	}

	// sets the table information into rs and then here into Data
	public Data(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public Data(int amount, String email, String password) {
		this.setAmount(amount);
		this.email = email;
		this.password = password;
	}

	public Data(int id, String name, String email, String joinDate, String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.setJoinDate(joinDate);
		this.password = password;
	}

	// this one is used for making the deposit or withdrawal
	public Data(String description, int amount, String transactionType, int accountId) {
		this.description = description;
		this.amount = amount;
		this.transactionType = transactionType;
		this.accountId = accountId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getOldAmount() {
		return oldAmount;
	}

	public void setOldAmount(int oldAmount) {
		this.oldAmount = oldAmount;
	}

}
