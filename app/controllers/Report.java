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

public class Report extends Controller
{

  public static void index()
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
    renderTemplate ("Report/index.html", donations, candidates, states, uniqueDonations);
    
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
      renderTemplate ("Report/index.html", donations, candidates, states, uniqueDonations);
      
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
     renderTemplate ("Report/index.html", donations, candidates, states, uniqueDonations);
     
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
