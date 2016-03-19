package controllers;

import java.util.Date;
import java.util.Collections;
import java.util.List;

import models.Donation;
import models.User;
import play.Logger;
import play.mvc.Controller;
import utils.DonationDateComparator;

public class DonationController extends Controller 
{

	  public static void index()
	  {
	    User user = Accounts.getCurrentUser();
	    if (user == null)
	    {
	      Logger.info("Donation class : Unable to getCurrentuser");
	      Accounts.login();
	    }
	    else
	    {
	      String prog = getPercentTargetAchieved();
	      //a trailing % required to render progress bar (view)
	      String progress = prog + "%";
	      Logger.info("Donation ctrler : percent target achieved " + progress);
	      render(user, progress);
	    }
	  }

    /**
     * Log and save to database amount donated and method of donation.
     * @param amountDonated Dollars donation 
     * @param methodDonated Method used to donate (Paypal, Direct).
     */
    public static void donate(long amountDonated, String methodDonated) 
    {
        Logger.info("amount donated " + amountDonated + " " + "method donated "
                + methodDonated);

        User user = Accounts.getCurrentUser();
        if (user == null) 
        {
            Logger.info("Donation class : Unable to getCurrentuser");
            Accounts.login();
        } 
        else 
        {
            addDonation(user, amountDonated, methodDonated);
        }
        index();
    }

    /**
     * @param user The donor. 
     * @param amountDonated The amount donation.
     */
    private static void addDonation(User user, long amountDonated,String methodDonated) 
    {
        Donation bal = new Donation(user, amountDonated, methodDonated);
        bal.save();
    }
    
    /**
     * Hard codes an arbitrary donation target amount
     * @return The target donation amount
     */
	private static long getDonationTarget() 
	{
		return 20000;
	}

	/**
	 * 
	 * @return The percentage of donation target achieved to date.
	 */
	public static String getPercentTargetAchieved() 
	{
		List<Donation> allDonations = Donation.findAll();
		long total = 0;
		for (Donation donation : allDonations) 
		{
			total += donation.received;
		}
		long target = getDonationTarget();
		long percentachieved = (total * 100 / target);
		String progress = String.valueOf(percentachieved);
		return progress;
	}
	
	/**
	 * Render report summary
	 */
	public static void renderReport()
	{
	  List<Donation> donations = Donation.findAll();
	  Collections.shuffle(donations);
	  Collections.sort(donations, new DonationDateComparator());
	  render(donations);
	}
}