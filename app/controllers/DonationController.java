package controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import models.Candidate;
import models.Donation;
import models.User;
import play.Logger;
import play.mvc.Controller;
//import utils.DonationDateComparator;

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
	      String donationprogress = prog;// + "%";//a trailing % required to render in view progress bar

	      Logger.info("Donation ctrler : user is " + user.email);
	      Logger.info("Donation ctrler : percent target achieved " + donationprogress);
	     
	      render(user, donationprogress);
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
            addDonation(user, amountDonated, methodDonated);
        }
        index();
    }

    /**
     * @param user
     * @param amountDonated
     */
    private static void addDonation(User user, long amountDonated,String methodDonated) 
    {
        Donation bal = new Donation(user, amountDonated, methodDonated);
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
	    //Collections.shuffle(donations);
	    //Collections.sort(donations, new DonationDateComparator());
	    
      List<Candidate> candidates = Candidate.findAll();
      List<String> states = getStates();
      List<Donation> uniqueDonations = getDonors();
	    render(donations, candidates, states, uniqueDonations);
	  }
	  
	  public static void filterCandidates(String candidateEmail)
	  {
	    Logger.info("Filtering donations to " + candidateEmail);

	    // list users mapped to this candidate
	    List<User> users = User.findAll();
	    List<Donation> donations = new ArrayList<Donation>();
	    for (User user : users)
	    {
	      if (user.candidate.email.equalsIgnoreCase(candidateEmail))
	      {
	        donations.addAll(user.donations);
	      }
	    }
	    List<Candidate> candidates = Candidate.findAll();
	    List<String> states = getStates();
	    List<Donation> uniqueDonations = getDonors();
	    renderTemplate ("DonationController/renderReport.html", donations, candidates, states, uniqueDonations);
	    
	  }
	  
	   public static void filterDonors(String donorEmail)
	    {
	      Logger.info("Filtering donations from " + donorEmail);

	      List<Donation> donations = new ArrayList<Donation>();
	      List<Donation> allDonations = Donation.findAll();
	      for (Donation donation : allDonations)
	      {
	        if (donation.from.email.equalsIgnoreCase(donorEmail))
	        {
	          donations.add(donation);
	        }
	      }
	      List<Candidate> candidates = Candidate.findAll();
	      List<String> states = getStates();
	      List<Donation> uniqueDonations = getDonors();
	      renderTemplate ("DonationController/renderReport.html", donations, candidates, states, uniqueDonations);
	      
	    }
	   
     public static void filterStates(String state)
     {
       Logger.info("Filtering donations from donors resident in the stae of " + state);
       
       List<User> users = User.findAll();      
       List<Donation> donations = new ArrayList<Donation>();
      
       for (User user : users)
       {
         if (user.state.equalsIgnoreCase(state))
         {
           donations.addAll(user.donations);
         }
       }
       List<Candidate> candidates = Candidate.findAll();
       List<String> states = getStates();
       List<Donation> uniqueDonations = getDonors();
       renderTemplate ("DonationController/renderReport.html", donations, candidates, states, uniqueDonations);
       
     }
     
     private static List<String> getStates()
     {
       Set<String> states = new HashSet<String>();
       List<User> users = User.findAll();
       for (User user : users)
       {
         states.add(user.state);
       }
       
       List<String> listStates = new ArrayList<String>();
       listStates.addAll(states);
       
       return listStates;
     }
     
     /**
      * Obtain a set of donors - no duplicates, i.e. unique list users
      * This achieved by collection of Donation objects where
      * no duplicate donors, i.e. a donor is present only once in the 
      * list of Donations.
      * In other words, a subset of all the donations is 
      * assembled such that each donor is present once and once only.
      * @return set of Donation objects in a list
      */
     private static List<Donation> getDonors()
     {
       List<Donation> listDonations = new ArrayList<Donation>();
       Set<String> emailSet = new HashSet<String>();
       List<Donation> donations = Donation.findAll();
       for (Donation donation : donations)
       {
         // use set properties to filter out donation objects
         // containing same User email
         if(emailSet.add(donation.from.email))
         {
           listDonations.add(donation);
         }
       }      
       return listDonations;
     }
}