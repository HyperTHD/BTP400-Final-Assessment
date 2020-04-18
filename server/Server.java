/**
 * The server portion of the final assessment. Implemented in an RMI style so implementation is very short
 * Simply makes a RMIBankImpl object and states the server address and the server's PORT number
 * 
 * @author Abdulbasid Guled, 156024184
 * @since 12/04/2020
 */
package server;
import java.rmi.*;
import java.rmi.server.*;

import commonFiles.*;

public class Server {
	
	public static void main(String[] args) {
		
		try {
			System.out.println("Server is trying to start...");
			RMIBankImpl bank = new RMIBankImpl("Seneca@York");
			loadBank(bank);
			Naming.rebind("rmi://localhost:5678/Seneca", bank);
			System.out.println("Waiting for a client to enter...");
			
		} catch (Exception e) {
			System.out.println("Error has occurred!");
			System.out.println("Error is: " + e);
			System.out.println("Stack trace is as follows: ");
			e.printStackTrace();
		}	
	}

	public static void loadBank(RMIBank bank) throws RemoteException
	{
		try {
			bank.addAccount(new Chequing("Doe, John", "A1234", 1000.00, 0.25, 5));
			bank.addAccount(new GIC("Doe, John", "A7890", 6000.00, 2, 0.0150));
			bank.addAccount(new Chequing("Ryan, Mary", "S5678", 2000.00, 0.30, 4));
			bank.addAccount(new GIC("Ryan, Mary", "S4567", 15000.00, 4, 0.0250));
		} catch (Exception e) {
			System.out.println("Error:" + e);
		}
	}
}
