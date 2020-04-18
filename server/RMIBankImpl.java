/**
 * The implementation of the RMIBank interface. Similar in functionality to the Bank class of 
 * Assignment 1 with the difference being that it's used as the server for the final assessment
 * 
 * @author Abdulbasid Guled, 156024184
 * @since 12/04/2020
 */

package server;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import commonFiles.*;

public class RMIBankImpl extends UnicastRemoteObject implements RMIBank {

	private static final long serialVersionUID = 1L; // Default serialVersionUID which was generated
	private ArrayList<Account> user;
	private String name;
	
	public RMIBankImpl() throws RemoteException
	{
		this(null);
	}
	
	public RMIBankImpl(String name) throws RemoteException
	{
		this.name = name != null ? name : "Seneca@York";
		user = new ArrayList<Account>();
	}
	
	
	public synchronized String getName() throws RemoteException { return name; }
	
	public synchronized boolean addAccount(Account newAccount) throws RemoteException
	{
		synchronized(Account.class) {
		boolean result = false; // assume we haven't added any account yet
		
		if (newAccount == null)
		{
			return result; // if the added account is null, immediately return false
		}
				
		else 
		{
			for (Account account : user)
			{
				if (account.getAccountNumber().equals(newAccount.getAccountNumber()))
				{
					return result;
				}
			}
		
			user.add(newAccount);
			result = true;			
		}
		
		return result;
		}
	}
	
	public synchronized Account removeAccount(String accountNumber) throws RemoteException
	{
		if (accountNumber == null)
			return null;
		
		Account removed = null;
		
		int index = 0;
		
		for (int i = 0; i < user.size(); ++i)
		{
			if (user.get(i).getAccountNumber().equals(accountNumber))
			{
				removed = user.get(i);
				index = i;
				break;
			}
		}
		
		user.remove(index);
		
		return removed;
	}
	
	
	public String toString()
	{

		String result;

		result = "*** Welcome to the Bank of " + 
		this.name + " ***\n" + "It has " + this.user.size() + " user.\n";

		for (int i = 0; i < user.size(); i++) {
			result += i + 1 + ". number: " + this.user.get(i).getAccountNumber() + ", name: " + this.user.get(i).getFullName()
					+ ", balance: $" + String.format("%d", (int) this.user.get(i).getBalance().doubleValue()) + "\n";
		}
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		boolean result = false; // Assume they aren't equal
		
		if (obj instanceof RMIBank)
		{
			
			RMIBankImpl temp = (RMIBankImpl) obj;
			
			if(this.name.equals(temp.name))
			{
				for (int i = 0; i < user.size(); ++i)
				{
					if(this.user.get(i).equals(temp.user.get(i)))
					{
						result = true;
					}
				}
			}
		}
		return result;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	
	public synchronized Account[] searchByBalance(double balance) throws RemoteException
	{
		int count = 1;
		Account[] found = null;
		ArrayList<Account> toBeCopied = new ArrayList<Account>();
		
		if (balance < 0)
		{
			System.out.println("*** NO ACCOUNT FOUND ***");
			return found;
		}
		
		for (Account acc : user)
		{
			if (acc.getBalance().doubleValue() == balance)
			{	
				toBeCopied.add(acc);
			}
		}
		
		found = new Account[toBeCopied.size()];
		
		found = toBeCopied.toArray(found);
		
		System.out.println("We have found " + found.length + " accounts whose balance is " + String.format("%d", (int)balance) + ".");
		
		for (int i = 0; i < found.length; ++i)
		{
			System.out.println(count + ". number: " + found[i].getAccountNumber() + ", name: " + found[i].getFullName());
			count++;
		}
		
		
		return found;
	}
	
	
	public synchronized Account[] searchByAccountName(String accountName) throws RemoteException
	{
		ArrayList<Account> sameName = new ArrayList<>();

        for(Account a: user){
            if(a.getFullName().matches(accountName)){
                sameName.add(a);
            }
        }

        Account[] matchArray = new Account[sameName.size()];
        return sameName.toArray(matchArray);
	}
	
	public synchronized int getBankSize() throws RemoteException
	{
		return user.size();
	}
	
	public synchronized Account[] getAllAccounts() throws RemoteException
	{
		Account[] allAccounts = new Account[user.size()];
		
		for (int i = 0; i < user.size(); ++i)
		{
			allAccounts[i] = user.get(i);
		}
		
		return allAccounts;
	}
	
	
	public synchronized Account findAccountToEdit(String accountNumber) throws RemoteException
	{
		Account toBeReturned = null;
		
		for (int i = 0; i < user.size(); ++i)
		{
			if (user.get(i).getAccountNumber().equals(accountNumber))
			{
				toBeReturned = user.get(i);
				break;
			}
		}
		return toBeReturned;
	}
	
	public synchronized GIC findGICAccount(String fullName) throws RemoteException
	{
		for (int i = 0; i < user.size(); ++i)
		{
			if (user.get(i).getFullName().equals(fullName))
			{
				if (user.get(i) instanceof GIC)
				{
					return (GIC) user.get(i);
				}
			}
		}
		return null;
	}

	public synchronized String getBankName() throws RemoteException {
		return name;
	}
	
}
