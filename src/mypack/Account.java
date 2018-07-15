package mypack;

import java.util.ArrayList;

public class Account
{
	/*
	 *  name of the account
	 */
	
	private String name;
	
	/*
	 * the account ID number
	 */
	
	private String uuid;
	/*
	 * the user object that owns this account
	 */
	
	private User holder;
	/*
	 * the list of transactions of this account
	 */
	
	private ArrayList<Transaction> transactions;
/*
 * create a new account
 * @param name  name of the account
 * @param holder the user object that holds this account
 * @param theBank the bank that issues the account
 */
	public Account(String name, User holder,Bank theBank) {
		this.name = name;
		this.holder=holder;
		
		//get new account UUID
		
		this.uuid = theBank.getNewAccountUUID();
		
		//init transactions
		this.transactions= new ArrayList<Transaction>();
		
		
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public String getSummaryLine()
	{
		//get the accounts balance
		double balance = this.getBalance();
		
		if(balance >= 0)
		{
			return String.format("%s : $%.02f : %s",this.uuid,balance,this.name);
		}else
		{
			return String.format("%s : $(%.02f) : %s",this.uuid,balance,this.name);
		}
		
	}
	
	public double getBalance()
	{
		double balance = 0;
		for(Transaction t:this.transactions)
		{
			balance += t.getAmount();
		}
		
		return balance;
	}

	public void printTransHistory()
	{
		System.out.printf("\n Transaction history for accounts %s\n",this.uuid);
		for(int t = this.transactions.size()-1;t >= 0;t--)
		{
			System.out.println(this.transactions.get(t).getSummaryLine());
		}
		System.out.println();
	}

	public void addTransaction(double amount, String memo)
	{
		//create new transaction object and add it to our list
		Transaction trans=new Transaction(amount,memo,this);
		this.transactions.add(trans);
	}
	
	
}
