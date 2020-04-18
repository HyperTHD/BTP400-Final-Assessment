package commonFiles;

import java.io.Serializable;
import java.math.BigDecimal;

public class Account implements Serializable {

	private static final long serialVersionUID = 1L;
	private String fullName;
	private String accountNumber;
	private BigDecimal balance;
	
	/**
	 * @param: Nothing
	 * @return: An object in an empty state
	 */
	public Account()
	{
		this(null, null, -1);
	}
	
	/**
	 * @param: String, String, double
	 * @return: An object with the data passed in
	 */
	
	public Account(String fullName, String accountNumber, double balance)
	{

		this.fullName = fullName != null ? fullName : "";
		this.accountNumber = accountNumber != null ? accountNumber : "";
		
		// Sets an object to an empty state if any of the conditions for the fields are satisfied
		
		
		this.balance = new BigDecimal(0);
		
		this.balance = balance < 0 ? BigDecimal.valueOf(0) : BigDecimal.valueOf(balance);
	}
	
	/**
	 * Getter for the owner's full name
	 * @param: Nothing
	 * @return: The person's full-name
	 */
	
	public String getFullName()
	{
		return fullName;
	}
	
	
	/**
	 * This method returns the owner's first name
	 * @return
	 */
	
	public String getFirstName()
	{
		String[] neededForFirst = getFullName().split(",");
		return neededForFirst[1];
	}
	
	/**
	 * This method returns the owner's last name
	 * @return
	 */
	public String getLastName()
	{
		String[] neededForLast = getFullName().split(",");
		return neededForLast[0];
	}
	
	/**
	 * This method returns the owner's account number
	 * @param: Nothing
	 * @return: The owner's account number
	 */
	
	public String getAccountNumber()
	{
		return accountNumber;
	}
	
	/**
	 * Get's the owner's account balance
	 * @param: Nothing
	 * @return: The person's balance
	 */
	
	public BigDecimal getBalance()
	{
		return this.balance;	
	}
	
	/**
	 * Setter for the fullName
	 * @param: String
	 * @return: Nothing
	 */
	
	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}
	
	/**
	 * Setter for the account number
	 * @param: String
	 * @return: Nothing
	 */
	
	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}
	
	/**
	 * Setter for the balance
	 * @param: double
	 * @return: Nothing
	 */
	
	public void setBalance(double balance)
	{
		this.balance = BigDecimal.valueOf(balance);
	}

	/**
	 * Describes how an Account object is to be displayed
	 * @param: Nothing
	 * @return: A string representation of an Account object
	 */
	
	public String toString()
	{
		StringBuffer s, x, y, z;
		
		s = new StringBuffer(getLastName());
		
		x = new StringBuffer(getFirstName());
		
		y = new StringBuffer(getAccountNumber());
		
		z = new StringBuffer(String.format("%.2f", this.balance));
	
		StringBuffer start = new StringBuffer("Name           : ");
		
		String returned;
		returned = start.append(s).append(",").append(x).append("\n")
						.append("Number         : ").append(y).append("\n")
						.append("Current Balance: $").append(z).append("\n").toString();
		
		return returned;
		
	/*	return "Name: " + getLastName() + ", " + getFirstName() + "\n"
			+   "Number: " + getAccountNumber() + "\n"
			+ "Current Balance: $" + String.format("%.2f", this.balance) + "\n";
	*/
	}
	
	/**
	 * Equals method checks if 2 Account objects are equal to each other.
	 * 2 Account objects are equal if both of their full names, account number, and account balance are equal
	 * 
	 * @Param: An object that can be typecasted
	 * @return: A boolean that resolves to either true or false if the 2 objects are the same
	 */
	
	@Override
	public boolean equals(Object obj)
	{
		boolean result = false; // By default, 2 objects are not equal to each other
		
		if (this == obj)
			return true;
		
		if (obj instanceof Account)
		{
			Account temp = (Account) obj;
			
			if (fullName != null && accountNumber != null)
			{
				if (fullName.equals(temp.fullName) && accountNumber.equals(temp.accountNumber) && balance == temp.balance)
				{
					result = true;
				}
			}
		
		}
		
		return result;
	}
	
	/**
	 * Overridden hash code method for account objects
	 * @return int
	 */
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		return result;
		
	}
	/**
	 * Withdraw method for account objects
	 * @param double
	 * @return boolean
	 */
	public synchronized boolean withdraw(double amount)
	{
		boolean result = false;
		
		if (amount > 0) {
			BigDecimal toBeSubtracted = BigDecimal.valueOf(amount);
			
			double val = this.balance.subtract(toBeSubtracted).doubleValue();
			
			if (val > 0)
			{
				this.balance = this.balance.subtract(toBeSubtracted);
				result = true;
			}
		}		
		return result;
	}
	/**
	 * Deposit method for account objects 
	 * @param double
	 * @return void
	 */
	public synchronized void deposit(double amount)
	{
		if (amount > 0)
		{
			BigDecimal toAdd = BigDecimal.valueOf(amount);
			
			this.balance = this.balance.add(toAdd);
		}
	}

}