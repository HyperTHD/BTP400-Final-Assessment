package commonFiles;

import java.math.BigDecimal;
import java.io.Serializable;

public class GIC extends Account implements Taxable, Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal interestIncomeTotal = new BigDecimal(0.00);
	private BigDecimal taxTotal = new BigDecimal(0.00);
	private BigDecimal interestRate;
	private int years;
	
	/**
	 * Default Constructor
	 * Initializes to a safe empty state
	 */
	public GIC()
	{
		this(null, null, 0.00, 1, 0.0125);
		
	}
	/**
	 * Constructor that creates a GIC object
	 * @param fullName
	 * @param accountNumber
	 * @param startingBalance
	 * @param years
	 * @param interestRate
	 */
	public GIC(String fullName, String accountNumber, double startingBalance, int years, double interestRate)
	{
		super(fullName, accountNumber, startingBalance);
		
		this.years = years >= 0 ? years : 0;
		this.interestRate = interestRate >= 0 ? new BigDecimal(interestRate) : new BigDecimal(0);
	}
	/**
	 * Implemented interface method for calculating the total tax
	 * @param Nothing
	 * @return Nothing
	 */
	@Override
	public void calculateTax() 
	{
		BigDecimal amount = this.getBalance().multiply(BigDecimal.valueOf(taxRate));
		taxTotal = amount;	
	}
	/**
	 * Overridden method to get the balance at maturity
	 * @param nothing
	 * @return BigDecimal
	 */
	@Override
	public BigDecimal getBalance()
	{
		BigDecimal initialBalance = super.getBalance(),
				   Maturity = BigDecimal.valueOf(0.00),
				   calcForMaturityPay = this.interestRate.add(BigDecimal.valueOf(1.00)).pow(years);
		
		Maturity = initialBalance.multiply(calcForMaturityPay);
		this.interestIncomeTotal = Maturity.subtract(initialBalance);
		
		return Maturity;
	}
	
	/**
	 * Implemented Interface method used to get the total tax amount
	 * @param nothing
	 * @return double
	 */
	@Override
	public double getTaxAmount() {
		calculateTax();
		return this.interestIncomeTotal.doubleValue();
	}
	
	/**
	 * Implemented Interface method used to create a tax statement
	 * @param nothing
	 * @return string
	 */
	@Override
	public String createTaxStatement() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("Account Number : ").append(this.getAccountNumber())
		  .append("\nInterest Income: ").append(String.format("%.2f", this.getBalance().doubleValue()))
		  .append("\nAmount of tax  : ").append(String.format("%.2f", this.getTaxAmount())).append("\n");
		return sb.toString();
	}
	
	/**
	 * Overridden toString method to describe how a GIC object is to be displayed
	 * @param nothing
	 * @return string
	 */
	@Override
	public String toString()
	{
		StringBuffer info = new StringBuffer("Account Type                : GIC\n");
		
		StringBuffer returned = new StringBuffer();
		
		BigDecimal finalBalance = getBalance();
	
		returned.append(super.toString()).append(info)
					   .append("Annual Interest Rate        : ").append(String.format("%.2f", this.interestRate.doubleValue() * 100.00)).append("%\n")
					   .append("Period of Investment        : ").append(years).append(" years\n")
					   .append("Interest Income at Maturity : $").append(String.format("%.2f", this.interestIncomeTotal.doubleValue())).append("\n")
					   .append("Balance at Maturity         : $").append(String.format("%.2f", finalBalance.doubleValue())).append("\n");
		
		return returned.toString();
	}
	
	/**
	 * Overridden equals method used to compare 2 GIC objects
	 * @param object
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj)
	{
		 boolean result = false; 
		   if (obj != null && obj instanceof GIC){
	            GIC other = (GIC) obj;
	            result = super.equals(other) 
	            		&& this.interestRate.equals(other.interestRate) 
	            		&& this.years == other.years && this.interestIncomeTotal.equals(other.interestIncomeTotal);
	        }
	     return result;
	}

	/**
	 * Overridden hash code method used to compare 2 GIC objects
	 * @param nothing
	 * @return int
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (int)(years ^ (years >>> 32));
        result = prime * result + interestRate.hashCode();
        result = prime * result + interestIncomeTotal.hashCode();
        result = prime * result + taxTotal.hashCode();
        return result;
	}
	
	/**
	 * A GIC account does not allow deposits so the deposit method is overriden to do nothing
	 * @param double
	 * @return nothing
	 */
	
	@Override
	public void deposit(double amount) {} 
	
	
	/**
	 * A GIC account does not allow withdraws so the withdraw method is overriden to always returns false
	 * @param double
	 * @return boolean
	 */
	
	public boolean withdraw(double amount) { return false; }
	
	
	/**
	 * Getter for Interest Rate
	 * @param nothing
	 * @return double
	 */
	
	public double getII()
	{
		return this.interestRate.doubleValue();
	}
	
	/**
	 * Getter for Interest Income Total
	 * @param nothing
	 * @return double
	 */
	public double getIIT()
	{
		return this.interestIncomeTotal.doubleValue();
	}
	
	/**
	 * Getter method to test deposit method
	 * @param nothing
	 * @return double
	 */
	public double getAccountBalance()
	{
		return super.getBalance().doubleValue();
	}
	
	/**
	 * Getter method for getting period of years
	 * @param nothing
	 * @return int
	 */
	
	public int getYears()
	{
		return years;
	}
}
