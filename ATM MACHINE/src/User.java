
public class User {
	
	private int number;
	private int password;
	private double balance=0;
	private double savingBalance = 0;
	
	public User () {
		
	}
	public User(int number, int password) {
		this.number = number;
		this.password = password;
		this.balance = 0;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public void setPassword(int password) {
		this.password = password;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public int getPassword() {
		return this.password;
	}
	public double getSavingBalance() {
		return this.savingBalance;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public void addMoney(double cash) {
		this.balance = this.balance + cash;
	}

	public void addMoneyToSavingBalance(double cash) {
		double x = this.balance - cash;
		if(x>=0)
		this.savingBalance = this.savingBalance + cash;
		this.balance = x;
		if(x<0)
			System.out.println("You dont have enough funds");
	}
	
	public void transferMoneyFromSavingBalance (double cash) {
		double x = this.savingBalance - cash;
		if(x>=0)
		this.balance = this.savingBalance + cash;
		this.savingBalance = x;
		if(x<0)
			System.out.println("You dont have enough funds");
	}
}
