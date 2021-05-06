package common;

import model.Data;

public class UserSingleton {
	private static UserSingleton instance;
	
	public UserSingleton() {
//		instance = this.username()
	}
	
	public static UserSingleton getInstance() {
		if (instance == null) {
			instance = new UserSingleton();
		}
		return instance;
	}
	
	public void setEmail(String email) {
		
	}
	
	
	
	
	
	public void storeDataUS(Data data) {
	}
	
}
