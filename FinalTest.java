/*
 * Jacob Richardson
 * CSC 429 Spring 2019
 * Final Exam.
 */

import java.text.SimpleDateFormat;
import java.util.*;
import CalendarTool.*;

import org.junit.*;
import static org.junit.Assert.*;

public class FinalTest 
{
		
	/***********************************************************************************************************************
															BLACKOUT TESTS.
	************************************************************************************************************************/
	
	@Test
	public void normalDueDate()
	{	
		testDates("2019/05/06 17:00", "2019/05/07 17:00");
	}
	
	@Test
	public void fridayDueDate()
	{
		testDates("2019/05/10 17:00", "2019/05/13 17:00");	
	}
	
	@Test
	public void blackoutDueDate()
	{
		testDates("2019/04/09 17:00", "2019/04/11 17:00");
	}
	
	@Test
	public void weekdayFine()
	{
		testFines("2019/05/07 14:59", "2019/05/06 15:00", 5);
	}
	
	@Test
	public void weekenedFine() 
	{
		testFines("2019/05/13 14:59", "2019/05/10 15:00", 5);
	}
	
	@Test
	public void sundayFine()
	{
		testFines("2019/05/12 14:59", "2019/05/10 15:00", 5);
	}
	
	/***********************************************************************************************************************
															BANK TESTS.
	************************************************************************************************************************/
	
	@Test
	public void testDeposit()
	{
		//Create a new bank object.
		Bank b = new Bank();
		
		//Variable for the account number.
		int accountNo = 1;
		
		//Variable for the amount to be deposited into the account.
		double amount = 25;
		
		//Deposit 25 dollars into account 1 and store the result into new amount.
		double newTotal = b.depositeMoney(accountNo, amount);
		
		//Make sure the amount and new total are equal.
		assertEquals(amount, newTotal, 0);
	}
	
	@Test (expected = Exception.class)
	public void testInvalidWithdrawl() throws Exception
	{
		//Create a new bank object.
		Bank b = new Bank();
		
		//Variable for the amount to be withdrawn from the account.
		double amount = 100;
		
		//Make sure the account has less than the desired amount to be withdraw to make sure an exception occurs.
		assertTrue(b.checkBalance() < amount);
		
		//Withdraw the amount to cause an exception.
		b.withdraweMoney(1, 100);
		
	}
	
	@Test
	public void testValidWithdrawl() throws InvalidFundsException
	{
		//Create a new bank object.
		Bank b = new Bank();
		
		//Variable for the account number.
		int accountNo = 1;
		
		//Variable for the amount to be withdrawn into the account.
		double withdrawAmount = 100;
		
		//Variable for inital account balance.
		double initalAccountBalance = 200;
		
		//Deposit the inital amount balance into the account.
		b.depositeMoney(accountNo, initalAccountBalance);
		
		//Make sure the account has more than the desired amount to be withdraw to make sure an exception does not occur.
		assertTrue(b.checkBalance() > withdrawAmount);
		
		//Withdraw the amount from the account.
		b.withdraweMoney(accountNo, withdrawAmount);
		
	}
	
	/*
	 * This method makes sure the blackouts class returns the correct date for two given dates.
	 * @checkout This is the date the item was checkouted out.
	 * @expected This is the date the item is supposed to be returned.
	 */
	public void testDates(String checkout, String expected)
	{
		try
		{
			//Initialize a new blackout.
			Blackouts b = new Blackouts();
			
			//Set the format string to be years, month, days, hours, and minutes.
			String formatString = "yyyy/MM/dd HH:mm";
			
			//Initialize a new simple date formater using the date format string.
			SimpleDateFormat sdf = new SimpleDateFormat(formatString);
			
			//Get the checkout date using the date formater.
			Date checkouDate = sdf.parse(checkout);
			
			//Get the due date using the date formater.
			Date expectedDate = sdf.parse(expected);
			
			//Convert the expected due date to a string.
			String expectedDueDate = expectedDate.toString();
			
			//Get the actual due date using blackouts and the checkout date.
			String actualDueDate = b.computeDueDate(checkouDate).toString();
			
			//DEBUG: 
			//System.out.println("\nExpected Due Date: " + expectedDueDate);
			//System.out.println("Acutal Due Date: " + actualDueDate);
			
			//Make sure the expected due date and the actual due date are equal.
			assertEquals(expectedDueDate, actualDueDate);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * This method makes sure the blackouts class returns the correct fine amount for two dates and the expected fine amount.
	 * @retruned The date the item was returned.
	 * @due The date the item was due.
	 * @expectedFine The expected result of what the fine should be.
	 */
	public void testFines(String returned, String due, int expectedFine)
	{
		try
		{
			//Initialize a new blackout.
			Blackouts b = new Blackouts();
			
			//Set the format string to be years, month, days, hours, and minutes.
			String formatString = "yyyy/MM/dd HH:mm";
			
			//Initialize a new simple date formater using the date format string.
			SimpleDateFormat sdf = new SimpleDateFormat(formatString);
			
			//Get the returned date using the date formater.
			Date retrunedDate = sdf.parse(returned);
			
			//Get the due date using the date formater.
			Date dueDate = sdf.parse(due);

			int actualFine = b.calculateFine(retrunedDate, dueDate);
			
			//DEBUG:
			//System.out.println("\nExpected Fine Amount: " + expectedFine);
			//System.out.println("Actual Fine Amount: " + actualFine);
			
			//Make sure the fine amount equals the fine amount from blackouts.
			assertEquals(expectedFine, actualFine );
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
    public static junit.framework.Test suite() 
    {
        return new junit.framework.JUnit4TestAdapter(FinalTest.class);
    }
    
    public static void main(String args[]) 
    {
    	org.junit.runner.JUnitCore.main("FinalTest");
    }
}
