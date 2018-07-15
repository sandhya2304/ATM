package mypack;

import java.util.ArrayList;
import java.util.Random;

public class Bank
{
	
	private String name;
	
	private ArrayList<User> users;
	
	private ArrayList<Account> accounts;
	
	public Bank(String name)
	{
		this.name=name;
		this.users=new ArrayList<>();
		this.accounts=new ArrayList<>();
	}
	
	/*
	 *Generally a new universally id for a user
	 *@return the uuid 
	 * 
	 */
	public String getNewUserUUID()
	{
		//inits
		String uuid;
		Random random=new Random();
		int len = 6;
		boolean nonUnique = false;
		
		
		//continue loop until we get unique id
		do
		{
			
			//generate the number
			uuid = "";
			for(int c=0; c <len;c++)
			{
				uuid += ((Integer)random.nextInt(10)).toString();
			}
			
			//check to make sure its unique
			
			nonUnique = false;
			for(User u:this.users)
			{
				
				if(uuid.compareTo(u.getUUID())==0)
				{
					nonUnique = true;
					break;					
				}
				
			}
		
		}while(nonUnique);
		
		
		return uuid;
	}
	/*
	 *Generally a new universally id for a account
	 *@return the uuid 
	 * 
	 */
	public String getNewAccountUUID()
	{
		//inits
		String uuid;
		Random random=new Random();
		int len = 6;
		boolean nonUnique = false;
		
		
		//continue loop until we get unique id
		do
		{
			
			//generate the number
			uuid = "";
			for(int c=0; c <len;c++)
			{
				uuid += ((Integer)random.nextInt(10)).toString();
			}
			
			//check to make sure its unique
			
			nonUnique = false;
			for(Account a:this.accounts)
			{
				
				if(uuid.compareTo(a.getUUID())==0)
				{
					nonUnique = true;
					break;					
				}
				
			}
		
		}while(nonUnique);
		
		
		return uuid;
	}
	

	
	public void addAccount(Account account)
	{
		this.accounts.add(account);
		
	}
	

	public User addUSer(String firstName,String lastName,String pin)
	{
		//create a new user object and add it our list
		User newUser=new User(firstName,lastName,pin,this);
		this.users.add(newUser);
		
		
		//create a saving account for the user and add to User and bank account list
		Account newAccount=new Account("Saving",newUser,this);
		//add to holder and bank lists
		
	    newUser.addAccount(newAccount);
		this.accounts.add(newAccount);
				
		
		return newUser;
	}
	
	public User userLogin(String userID,String pin)
	{
		//search through list of users
		for(User u:this.users)
		{
			//check user id correct
			
			if(u.getUUID().compareTo(userID) == 0 && u.validatePin(pin))
			{
				return u;
			}
			
		}
		
		//if we haven't found the user or have an incorrect pin
		return null;
		
	}
	
	public String getName() {
		return name;
	}


}
