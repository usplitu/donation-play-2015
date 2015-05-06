package controllers;

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
	      String progress = prog + "%";//a trailing % required to render in view progress bar

	      Logger.info("Donation ctrler : user is " + user.email);
	      Logger.info("Donation ctrler : percent target achieved " + progress);
	      render(user, progress);
	    }
	  }

    /**
     * Log and save to database amount donated and method of donation, eg.
     * paypal, direct payment
     * 
     * @param amountDonated
     * @param methodDonated
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
            addDonation(user, amountDonated,methodDonated);
        }
        index();
    }

    /**
     * @param user
     * @param amountDonated
     */
    private static void addDonation(User user, long amountDonated,String methodDonated) 
    {
        Donation bal = new Donation(user, amountDonated,methodDonated);
        bal.save();
    }
    
    /*
     * Hard codes an arbitrary donation target amount
     * @return the target donation amount
     */
	private static long getDonationTarget() 
	{
		// TODO Input this value thro' html template admin controlled
		return 20000;
	}

	/*
	 * 
	 * @return the percentage of donation target achieved
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
		Logger.info("Percent of target achieved (string) " + progress
				+ " percentachieved (long)= " + percentachieved);
		return progress;
	}
	
	  public static void renderReport()
	  {
	    List<Donation> donations = Donation.findAll();
	    Collections.shuffle(donations);
	    DonationDateComparator c = new DonationDateComparator();
	    //Collections.sort(donations, new DonationDateComparator());
	    render(donations);
	  }
}