package commonFiles;

public interface Taxable {

	double taxRate = 0.15;

	public void calculateTax();
	public double getTaxAmount();
	public String createTaxStatement();
	
}
