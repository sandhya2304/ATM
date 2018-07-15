package mypack;

import java.util.Scanner;

public class ATM {

	public static void main(String[] args)
	{
		
		Scanner sc=new Scanner(System.in);
		
		//init bank
		Bank bank=new Bank("Syndicate Bank");
		
		//create a user,which also creates a saving account
		User aUser=bank.addUSer("Ram","Sharma","1234");
		
		//add a checking account for our user
		Account anAccount=new Account("checking",aUser,bank);
		aUser.addAccount(anAccount);
		bank.addAccount(anAccount);
		
		
		User curUser;
		
		while(true)
		{
			//stay in the login prompt until successful login
			curUser =ATM.mainMenuPrompt(bank,sc);
			
			//stay in main menu until user quits
			ATM.printUserMenu(curUser,sc);
			
			
		}
	}
	
	public static User mainMenuPrompt(Bank thebank,Scanner sc)
	{
		//inits
		String userId;
		String pin;
		User authUser;
		
		
		// prompt until correct user password
		do
		{
			System.out.printf("\n welcome to %s \n",thebank.getName());
			System.out.println("enter user id:");
			userId = sc.nextLine();
			System.out.println("enter user pin:");
			pin = sc.nextLine();
			
			
			authUser = thebank.userLogin(userId, pin);
			if(authUser == null)
			{
				System.out.println("Incorrect user id/pin combination ."+"please try again");
				
			}
			
			
		}while(authUser == null);
		
		return authUser;
	
	}
	
	public static void printUserMenu(User theUser,Scanner sc)
	{
		//print summary of user accounts
		
	     theUser.printAccountSummary();	
		
	     //inits
	     int choice;
	     
	     //user menu
	     do
	     {
	    	 System.out.printf("Welcome %s,What would you like to do?",theUser.getFirstname());
	    	 System.out.println("1) Show Account Transaction history");
	    	 System.out.println("2) Withdrwal");
	    	 System.out.println("3) Deposit");
	    	 System.out.println("4) Transfer");
	    	 System.out.println("5) Quit");
	    	 System.out.println();
	    	 System.out.println("Enter Choice :");
	    	 
	    	 choice =sc.nextInt();
	    	 
	    	 if(choice < 1 || choice > 5)
	    	 {
	    		 System.out.println("Invalid choice. Plz choose 1 to 5 ");
	    	 }
	    	 
	     }while(choice < 1 || choice > 5);
	     
	     //process the choice 
	     
	     switch(choice)
	     {
	     case 1:
	    	 ATM.showTransactionHistory(theUser,sc);
	         break;
	     case 2:
	    	 ATM.withdrawlFunds(theUser,sc);
	         break;
	     case 3:
	    	 ATM.depositFunds(theUser,sc);
	         break;
	     case 4:
	    	 ATM.transferFunds(theUser,sc);
	         break;
	        	
	     case 5:
	    	// gobble up rest of previous input
	     		sc.nextLine();
	            break;
	  
	     }
	     
	     //redisplay menu until the user wants to quit
	     if(choice !=5)
	     {
	    	 ATM.printUserMenu(theUser, sc);
	     }
	     
	}

	
	private static void showTransactionHistory(User theUser, Scanner sc)
	{
		int theAcct;
		
		//get account whose transaction history to look at
		
		do
		{
			System.out.printf("Enter the number (1-%d) of the account \n"+"whose transaction you want to see",theUser.numAccounts());
			
			theAcct = sc.nextInt()-1;
			if(theAcct < 0 || theAcct >= theUser.numAccounts())
			{
				System.out.println("Invalid account. plz try again!!");
			}
			
		}while(theAcct < 0 || theAcct >= theUser.numAccounts());
		
		//print the transaction history
		
		theUser.printAcctTransHistory(theAcct);
		
	}
	
	private static void transferFunds(User theUser, Scanner sc)
	{
		//inits
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;
		
		
		//get the account to transfer from
		
		do
		{
			System.out.printf("Enter the number (1-%d) of the account \n"+"to tranfer from",theUser.numAccounts());
			
			fromAcct = sc.nextInt()-1;
			if(fromAcct < 0 || fromAcct >=theUser.numAccounts())
			{
				System.out.println("Invalid account. plz try again!!");
			}
			
		}while(fromAcct < 0 || fromAcct >=theUser.numAccounts());
		acctBal = theUser.getAcctBalance(fromAcct);
		
		//get the account to transfer to
		do
		{
			System.out.printf("Enter the number (1-%d) of the account \n"+"to tranfer to",theUser.numAccounts());
			
			toAcct = sc.nextInt()-1;
			if(toAcct < 0 || toAcct >=theUser.numAccounts())
			{
				System.out.println("Invalid account. plz try again!!");
			}
			
		}while(toAcct < 0 || toAcct >=theUser.numAccounts());
		
		//get the amount to transfer
		
		do
		{
			System.out.printf("Enter the amount to transfer (max $%.02f): $",acctBal);
			amount = sc.nextDouble();
			if(amount < 0)
			{
				System.out.printf("Amount must be greater than zero");
			}
			else if(amount > acctBal)
			{
				System.out.printf("Amount must not be greater than \n"+"balance of $%.02f. \n ",acctBal);
			}
			
		}while(amount < 0 || amount > acctBal);
		
		//finally do the transfer
		//one is subtraction the amount
		theUser.addAccTransaction(fromAcct, -1 * amount,String.format("Transfer to account %s",theUser.getAcctUUID(toAcct)));
		
		
		//other is to add the amount
		theUser.addAccTransaction(toAcct,amount,String.format("Transfer to account %s",theUser.getAcctUUID(fromAcct)));
	}
	

	private static void withdrawlFunds(User theUser, Scanner sc)
	{
		//inits
		int fromAcct;
		double amount;
		double acctBal;
        String memo;
		
		// get the account to transfer from

		do {
			System.out.printf("Enter the number (1-%d) of the account \n" + "to withdraw from"
		                             ,theUser.numAccounts());

			fromAcct = sc.nextInt() - 1;
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. plz try again!!");
			}

		} while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(fromAcct);
		
		//get the amount to transfer
		
		do {
			System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.printf("Amount must be greater than zero");
			} else if (amount > acctBal) {
				System.out.printf("Amount must not be greater than \n" + "balance of $%.02f. \n ", acctBal);
			}

		} while (amount < 0 || amount > acctBal);
		
		//gobble up rest of previous input
		sc.nextLine();
		
		
		//get a memo
		System.out.print("Enter a memo:");
		memo=sc.nextLine();
		
		//do the withdrawl
		theUser.addAccTransaction(fromAcct, -1*amount, memo);
		
	}
	

	private static void depositFunds(User theUser, Scanner sc)
	{
		//inits
		int toAcct;
		double amount;
		double acctBal;
		String memo;

		// get the account to transfer from

		do {
			System.out.printf("Enter the number (1-%d) of the account \n" + "to deposit in:",
					theUser.numAccounts());

			toAcct = sc.nextInt() - 1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. plz try again!!");
			}

		} while (toAcct < 0 || toAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(toAcct);

		// get the amount to transfer

		do {
			System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.printf("Amount must be greater than zero");
			} 

		} while (amount < 0 );

		// gobble up rest of previous input
		sc.nextLine();

		// get a memo
		System.out.print("Enter a memo:");
		memo = sc.nextLine();

		// do the withdrawl
		theUser.addAccTransaction(toAcct,amount, memo);
		
	}



	
	
}
