/**
 * The client class, is the console application that the client uses to interact with the server application.
 * It is the FinancialApp class with modifications to send objects back and forth to the server.
 * 
 * @author Abdulbasid Guled, 156024184
 * @since 12/04/2020
 */
package client;
import commonFiles.*;
import java.rmi.*;
import java.util.Scanner;

public class Client {

public synchronized static void main(String[] args) throws Exception {

		int choice = 0;
		String userInput = "";
		String accType = "";
		Scanner input = new Scanner(System.in);
		boolean keepGoing = true;
		
		try {
			System.out.println("Loading client...");
			RMIBank bank = (RMIBank) Naming.lookup("rmi://localhost:5678/Seneca");
			
			while(keepGoing) {
			
			displayMenu(bank.getBankName());
			choice = menuChoice();
			if(choice == 1) {
				System.out.print("Please enter the account type (CHQ/GIC)>");
				accType = input.nextLine();
				accType.toUpperCase();

				if(accType.equals("CHQ")) {
					System.out.println("Please enter account information at one line\n" +
									   "(e.g. Doe,John;A1234;1000.00;0.25;5):");
					userInput = input.nextLine();
					String [] accountData = userInput.split(";");
					if(accountData.length == 5){	
						Chequing chequing = new Chequing(accountData[0].trim(), accountData[1].trim(), Double.parseDouble(accountData[2].trim()), 
							Double.parseDouble(accountData[3].trim()), Integer.parseInt(accountData[4].trim()));
						
					bank.addAccount(chequing);
				}
					
				}
				else if(accType.equals("GIC")) {
					System.out.println("Please enter account information at one line\n" +
									   "(e.g. Doe,John;A1234;1000.00;5;1.25):");
					userInput = input.nextLine();
					String[] accountData = userInput.split(";");
					if (accountData.length == 5)
					{
						GIC gic = new GIC(accountData[0].trim(), accountData[1].trim(), Double.parseDouble(accountData[2].trim()), 
								Integer.parseInt(accountData[3].trim()), Double.parseDouble(accountData[4].trim()));
						bank.addAccount(gic);
					}
					
				}
				else {
					System.out.println("Error! Invalid account type!");
				}
			}
			else if(choice == 2) {
				System.out.print("Please enter the account number to close:");
				//input.nextLine();
				userInput = input.nextLine();
				bank.removeAccount(userInput);
				System.out.println("The account " + userInput + " was deleted.\n");
			}
			else if(choice == 3) {
				System.out.println("Please enter the account number and a sum (e.g. S5555;1000):");
				userInput = input.nextLine();
				
				String[] toSplit = userInput.split(";");
				
				double amount = Double.parseDouble(toSplit[1]);
				
				Account toDepositTo = bank.findAccountToEdit(toSplit[0]);
				
				toDepositTo.deposit(amount);
				System.out.println("The money was deposited.\n");
			}
			else if(choice == 4) {
				System.out.println("Please enter the account number and a sum (e.g. S5555;1000):");
				userInput = input.nextLine();
				String[] toSplit = userInput.split(";");
				double amount = Double.parseDouble(toSplit[1]);
				
				if(bank.findAccountToEdit(toSplit[0]).withdraw(amount)) {
					System.out.println("The money was withdrawn.\n");
				}
				else 
					System.out.println("Couldn't withdraw the sum.\n");
			}
			else if(choice == 5) {
				System.out.println("Search by account name or balance, or display all accounts? (a/b/c):");
				char charInput = input.nextLine().charAt(0);
				if(charInput == 'a') {
					System.out.print("Please enter the name on the account (e.g. Doe,John)>");
					userInput = input.nextLine();
					Account[] found = bank.searchByAccountName(userInput);
					for(Account a : found){
						displayAccount(a);
					}
				}
				else if(charInput == 'b') {
					System.out.print("Please enter the balance on the account (e.g. 1000.00)>");
					double bal = Double.parseDouble(input.nextLine());
					Account[] found = bank.searchByBalance(bal);
					for(Account a : found){
						displayAccount(a);
					}
				} else if(charInput == 'c') {
					Account[] all = bank.getAllAccounts();
					for( Account a: all){
						displayAccount(a);
					}
				}
			}
			else if(choice == 6) {
				System.out.print("Please enter your name for a tax statement:>");
				userInput = input.nextLine();
				
				Account[] allAccounts = bank.getAllAccounts();
				int index = 0;
				
				for (int i = 0; i < allAccounts.length; ++i)
				{		
							if (allAccounts[i] instanceof Taxable && allAccounts[i].getFullName().equals(userInput))
							{
								System.out.println("\n" +
										"Name: " + allAccounts[i].getFullName() + "\n" + 
										"Tax Rate: " + (int)Taxable.taxRate * 100.00 + "%" + "\n" 
										+ "\n");
							}
					if (allAccounts[i] instanceof GIC)
					{
						System.out.println("[" + index++ + "]\n"
								+ ((GIC) allAccounts[i]).createTaxStatement());
					
					}
				}
					
			}
			else if(choice == 7) 
				keepGoing = false;
			else 
				System.out.println("Error! Invalid choice!");
		}
				System.out.println("Thank you!");
	
		} catch (Exception e) {
				System.out.println("Error has occurred!");
				System.out.println("Error is: " + e);
				System.out.println("Stack trace is as follows: ");
				e.printStackTrace();
	}
}

	public static void displayMenu(String bankName)
	{
		String s = "Welcome to " + bankName + " Bank!\n" +
				   "1. Open an account." + "\n" +
				   "2. Close an account." + "\n" +
				   "3. Deposit money." + "\n" +
				   "4. Withdraw money." + "\n" +
				   "5. Display accounts." + "\n" +
				   "6. Display a tax statement." + "\n" +
				   "7. Exit" + "\n";

		System.out.println(s);
	}
	
	public static int menuChoice()
	{
		System.out.print("Please enter your choice>");
		Scanner input = new Scanner(System.in);
		int num = Integer.parseInt(input.nextLine());
		return num;
	}
	
	public static void displayAccount(Account account)
	{
		System.out.println(account);
	}
	
}
