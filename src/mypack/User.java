package mypack;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User
{
	/*
	 * first name of the user
	 */
	
	private String firstname;
	
	/*
	 * last name of the user
	 */
		
	private String lastName;
	/*
	 * id number of the user
	 */
	
	private String uuid;
	/*
	 * md5 hash of the user's pin number
	 */
	
	private byte pinHash[];
	
	/*
	 * list of account of this user
	 */
	
	private ArrayList<Account> accounts;

	public User(String firstname, String lastName,String pin,Bank theBank)
	{
		this.firstname = firstname;
		this.lastName = lastName;
	
	//Store the pin md5 hash ,rather than original value for security reason
		
		try {
			MessageDigest md=MessageDigest.getInstance("MD5");
			this.pinHash= md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.err.println("error , caught no such algorithm exception");
			e.printStackTrace();
			System.exit(1);
		}
		
		//get a new universal id for the user
		this.uuid = theBank.getNewUserUUID();
		
		//create empty list of accounts
		this.accounts =new ArrayList<Account>();
		
		//print log messgae
		System.out.printf("New user %s, %s with ID %s created.\n",lastName,firstname,this.uuid);
			
	}
	
/*
 * Add an account of the user
 * @param account  the account to add
 */
	public void addAccount(Account account)
    {
		this.accounts.add(account);
		
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public boolean validatePin(String apin)
	{
		
		try {
			MessageDigest md=MessageDigest.getInstance("MD5");
			
			return MessageDigest.isEqual(md.digest(apin.getBytes()),this.pinHash);
			
		} catch (NoSuchAlgorithmException e) {
			System.err.println("error , caught no such algorithm exception");
			e.printStackTrace();
			System.exit(1);
		}
		
		return false;
	}
	
	public String getFirstname() {
		return firstname;
	}

	public void printAccountSummary()
   {
	   System.out.printf("\n\n%s",this.getFirstname());
	   for(int a=0;a<this.accounts.size();a++)
	   {
		   System.out.printf("%d) %s\n",a+1,
				   this.accounts.get(a).getSummaryLine());
	   }
	   System.out.println();
		
	}

	public int numAccounts()
	{
		return this.accounts.size();
	}

	/*
	 * for particular account
	 */
	public void printAcctTransHistory(int theAcct)
	{
		this.accounts.get(theAcct).printTransHistory();
		
	}

	public double getAcctBalance(int acctIndx)
	{
		return this.accounts.get(acctIndx).getBalance();
	}

	public String getAcctUUID(int acctIndx)
	{
		return this.accounts.get(acctIndx).getUUID();
	}

	public void addAccTransaction(int acctIndx, double amount, String memo)
	{
		 this.accounts.get(acctIndx).addTransaction(amount,memo);
		
	}
	
	
	

}
