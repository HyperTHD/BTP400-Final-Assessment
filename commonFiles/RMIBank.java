package commonFiles;

// Imports so I don't have to include them again
import java.math.BigDecimal;
import java.util.ArrayList;
import java.rmi.*;

/**
 * This is the interface of the RMI bank which will be used to operate the server portion of the final assessment. It will have all methods of the Bank class used in A1
 * @author Abdulbasid Guled, 156024184
 * @since 12/04/2020
 */
public interface RMIBank extends Remote {

	 public boolean addAccount(Account a) throws RemoteException;
	 public Account removeAccount(String accNo)  throws RemoteException;
	 public Account[] searchByBalance(double accBal) throws RemoteException;
	 public Account[] searchByAccountName(String name) throws RemoteException;
	  
	 public String getBankName() throws RemoteException;
	 public int getBankSize() throws RemoteException;
	 
	 public Account[] getAllAccounts() throws RemoteException;
	 public Account findAccountToEdit(String accountNumber) throws RemoteException;
	 public GIC findGICAccount(String fullName) throws RemoteException;
}
