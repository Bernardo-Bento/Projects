
import java.util.HashMap;
import java.util.Scanner;

public class OptionMenu extends User{

	boolean login = false;
	Scanner menuInput = new Scanner(System.in);
	
	HashMap <Integer,Integer> data = new HashMap<>();

	public OptionMenu() {
		
	}
	public OptionMenu(int number, int password) {
		//super(number, password);
		// TODO Auto-generated constructor stub
	}
	
	private void goToPrincipal(){
		System.out.println("You´re in your principal account");
		System.out.println("1. Check Balance");
		System.out.println("2. Withdraw funds");
		System.out.println("3. Add funds");
		System.out.println("4. Change Account");
		System.out.println("5.EXIT");
		int selection = menuInput.nextInt();
		
		if(selection == 5)
			exit();
		
		else {
		switch(selection) {
		case 1: System.out.println("you have: "+this.getBalance()+"$");
		break;
		case 2: 
			System.out.println("how much money do you want to withdraw?");
		    this.addMoneyToSavingBalance(menuInput.nextDouble());
		break;
		case 3: System.out.println("How much money do you want to deposit?");
		this.addMoney(menuInput.nextDouble());
		break;
		case 4: goToMenu();
		break;
		
		}
		goToPrincipal();
		}
		
		
		
		
		
	}
	private void createNewUser(int number , int password) {
		data.put(number, password);
	}
	private void goToSaving(){
		System.out.println("You´re in your Saving account");
		System.out.println("1. Check Balance");
		System.out.println("2. Withdraw funds");
		System.out.println("3. Add funds");
		System.out.println("4. Change Account");
		System.out.println("5.EXIT");
		int selection = menuInput.nextInt();
		
		if(selection == 5)
			exit();
		
		else {
		switch(selection) {
		case 1: System.out.println("you have: "+this.getSavingBalance()+"$");
		break;
		case 2: 
			System.out.println("how much money do you want to withdraw?");
		    this.transferMoneyFromSavingBalance(menuInput.nextDouble());
		break;
		case 3: System.out.println("How much money do you want to deposit?");
		this.addMoneyToSavingBalance(menuInput.nextDouble());
		break;
		case 4: goToMenu();
		break;
		}
		goToSaving();
		}
		
		
	}
	public void goToMenu(){
		System.out.println("Welcome to the menu!");
		System.out.println("Wich Account do you want to use?");
		System.out.println("1. Principal Account");
		System.out.println("2. Saving Account");
		System.out.println("3. EXIT");
		int selection = menuInput.nextInt();
		switch(selection) {
		case 1: goToPrincipal();
		break;
		case 2: goToSaving();
		break;
		case 3: exit();
		break;
		}
    }
	private void exit() {
		System.out.println("See you later! Bye!");
		System.exit(0);
	}
	
	private void tryAgain() {
		System.out.println("Try again!");
		getLogin();
	}
	
	
	public void getLogin() {
	    data.put(14102001, 1410);
		data.put(27101973, 2710);
		data.put(15081973, 1508);
	
	System.out.println("Welcome to Bento´s Bank");
	System.out.println("1. Login");
	System.out.println("2. Create new Account");
	int selection = menuInput.nextInt();
	
	System.out.println("Please insert User Number:");
	int userNumber = menuInput.nextInt();
	int userPassword;
	for(int allUsers : data.keySet()) {
		if(userNumber == allUsers) {
			this.setNumber(userNumber);
			System.out.println("Enter your password: ");
			userPassword = menuInput.nextInt();
			for(int allPasswords : data.values()) {
				if(userPassword == allPasswords) {
					this.login = true;
					System.out.println("you are logged in!");
					goToMenu();
				}
			}
		}
		
	
		
   }
	tryAgain();
	
	
}
}
