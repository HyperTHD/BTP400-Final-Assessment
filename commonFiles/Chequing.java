package commonFiles;

import java.util.Arrays; // Required to use Array's hashCode method
import java.io.Serializable; // Required so Chequing objects can be sent across client/server applications

public class Chequing extends Account implements Serializable {

	private static final long serialVersionUID = 1L;
	private int maxTransNum;
	private int Pos;
	private double serviceChargeRate;
	private double totalChargeAmount;
	private double[] serviceChargeList;
	
	
	/**
	 * The default constructor for a Chequing object
	 * Sets the chequing object to default values
	 */
	public Chequing()
	{
		super();
		serviceChargeRate = 0.25;
		maxTransNum = 3;
	}
	
	/**
	 * A chequing account contains a full name, account number, starting balance, a service charge rate, and maximum number of transactions
	 * @param fullName
	 * @param accountNumber
	 * @param balance
	 * @param serviceChargeRate
	 * @param maxTransNum
	 */
	
	public Chequing(String fullName, String accountNumber, double balance, double serviceChargeRate, int maxTransNum)
	{
		super(fullName, accountNumber, balance);
		
		this.maxTransNum = maxTransNum > 0 ? maxTransNum : 0;
		this.Pos = 0;
		this.totalChargeAmount = 0.0;
		this.serviceChargeRate = serviceChargeRate > 0 ? serviceChargeRate : 0.0;
		
		this.serviceChargeList = new double[maxTransNum];
	}
	
	/**
	 * This method returns the final balance of a chequing account
	 * @return double
	 */
	public double getBalanceFinal()
	{
		double val = this.getBalance().doubleValue() - (this.Pos * this.serviceChargeRate);
		super.setBalance(val);
		return val;
	}
	
	
	/**
	 * This method describes how a Chequing object is supposed to look like when printed to the console
	 * @return String
	 */
	@Override
	public String toString()
	{
		StringBuffer info = new StringBuffer("Account Type        : CHQ");
		
		StringBuffer returned = new StringBuffer();
		
		returned.append(super.toString()).append(info).append("\n")
				.append("Service Charge      : $").append(String.format("%.2f", this.serviceChargeRate)).append("\n")
				.append("Total Charges       : $").append(String.format("%.2f", this.getMaxCharges())).append("\n")
				.append("List of Transactions: ");
		
		for (int i = 0; i < this.serviceChargeList.length; ++i)
		{
			if (serviceChargeList[i] > 0)
			{
				returned.append("+").append(String.format("%.2f", serviceChargeList[i])).append(", ");
			}
			else 
			{
				returned.append(String.format("%.2f", serviceChargeList[i])).append(", ");
			}
		}
		
		returned.append("\nFinal Balance       : ").append(String.format("%.2f", this.getBalance())).append("\n");
		
		
		return returned.toString();
	}
	
	/**
	 * Overridden equals method for Chequing objects
	 * @param Object
	 * @return Boolean
	 */
	
	public boolean equals(Object obj)
	{
		boolean result = false;
		
		if (obj instanceof Chequing && obj != null)
		{
			Chequing temp = (Chequing) obj;
			
			if (super.getFullName() != null && super.getAccountNumber() != null)
			{
				if(super.getFullName().equalsIgnoreCase(temp.getFullName())
						&& super.getAccountNumber().equalsIgnoreCase(temp.getAccountNumber())
						&& super.getBalance() == temp.getBalance()
						&& serviceChargeRate == temp.serviceChargeRate
						&& Arrays.equals(serviceChargeList, temp.serviceChargeList)
						&& Pos == temp.Pos
						&& totalChargeAmount == temp.totalChargeAmount)
				{
					result = true;
				}
			}
		}
		
		return result;
	}
	
	
	/**
	 * This is the overridden hashCode method to work for a Chequing object
	 * @return int
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxTransNum;
		long temp;
		temp = Double.doubleToLongBits(serviceChargeRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + Pos;
		result = prime * result + Arrays.hashCode(serviceChargeList);
		return result;
	}
	
	/**
	 * Getter method for returning service charge rate
	 * @param nothing
	 * @return double
	 */
	
	public double getServiceCharge()
	{
		return serviceChargeRate;
	}
	
	/**
	 * Getter method for returning maximum number of transactions
	 * @param nothing
	 * @return int
	 */
	
	public int getMaxTransNum()
	{
		return maxTransNum;
	}
	
	/**
	 * This overridden method handles the withdrawal of money from a Chequing account
	 * @param double
	 * @return boolean
	 */
	@Override
	public boolean withdraw(double amount)
	{
		boolean result = false;
		
		if (amount > 0 && Pos <= this.maxTransNum)
		{
			double finalWithdrawnValue = amount;
			if (super.withdraw(finalWithdrawnValue))
			{
				totalChargeAmount += this.serviceChargeRate;
				this.serviceChargeList[Pos] = -amount;
				Pos++;
				result = true;
			}
		}
		
		
		return result;
	}
	
	/**
	 * This overridden method handles the deposit of money into a Chequing account
	 * @param double 
	 */
	@Override
	public void deposit(double amount)
	{
		if (amount > 0 && Pos <= maxTransNum) {
			super.deposit(amount);
			totalChargeAmount += serviceChargeRate; 
			serviceChargeList[Pos] = amount;
			Pos++;
		}
	}
	
	
	/**
	 * This private method returns the maximum number of charges in the service charge list
	 * @return double
	 */
	public double getMaxCharges()
	{
		return this.totalChargeAmount;
	}
}
