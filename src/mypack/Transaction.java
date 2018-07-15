package mypack;

import java.util.Date;

public class Transaction
{
	/*
	 * the amount of this transaction
	 */
	
	private double amount;
	/*
	 * time and date of this transaction
	 */
	
	private Date timestamp;
	/*
	 * memo for this transaction
	 */
	
	private String memo;
	/*
	 * the account in which this transaction is performed
	 */
	
	private Account inAccount;
	
	public Transaction(double amount,Account inAccount)
	{
		this.amount=amount;
		this.inAccount=inAccount;
		this.timestamp=new Date();
		this.memo="";
	}
	
	public Transaction(double amount,String memo,Account inAccount)
	{
		//two args construtcor
		this(amount,inAccount);
		this.memo=memo;
	}
	
	public double getAmount() {
		return amount;
	}

	public String getSummaryLine() 
	{
		if(this.amount >=0 )
		{
           return String.format("%s : $%.02f : %s",this.timestamp.toString(),this.amount,this.memo);			
		}
		else
		{
			return String.format("%s : $(%.02f) : %s",this.timestamp.toString(),this.amount,this.memo);	
		}

	}
	

}
