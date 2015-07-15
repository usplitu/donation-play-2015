package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Candidate;
import models.Donation;
import models.Geolocation;
import models.User;

import org.json.simple.JSONObject;

import play.Logger;
import play.mvc.Controller;

/**
 * this class refactored for ajax call from make donate page
 * @author john
 * @version 2015-06-01 (yyyy-mm-dd)
 *
 */
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
        Logger.info("Donation ctrler : user is " + user.email);
        
        List<Candidate> candidates = Candidate.findAll();       
        render(user, candidates);
      }
    }

    private static String getProgress()
    {
      String currentCandidateEmail = session.get("currentCandidate");
      Candidate candidate = Candidate.findByEmail(currentCandidateEmail);
      String donationprogress = "0";
      if (candidate != null)
      {
        donationprogress = CandidateController.percentDonationTargetReached(candidate);
      }
      return donationprogress;
    }
    /**
     * Log and save to database amount donated and method of donation, eg.
     * paypal, direct payment
     * 
     * @param amountDonated
     * @param methodDonated
     * @param candidateEmail
     */
    public static void donate(long amountDonated, String methodDonated, String candidateEmail) 
    {

        Logger.info("amount donated " + amountDonated + " " + "method donated " + methodDonated);
        Logger.info("candidateEmail " + candidateEmail);
        
        session.put("currentCandidate", candidateEmail);
        User user = Accounts.getCurrentUser();
        if (user == null) 
        {
            Logger.info("Donation class : Unable to getCurrentuser");
            Accounts.login();
        } 
        else 
        {
            String progressPercent = getProgress();
            Candidate candidate = Candidate.findByEmail(candidateEmail);           
            if(!progressPercent.equalsIgnoreCase("100"))
            {
              Donation donation = new Donation(user, amountDonated, methodDonated, candidate);
              donation.save();
              candidate.addDonation(donation);
              candidate.save();
              user.addDonation(donation);
              user.save();
            } 
            JSONObject obj = new JSONObject();
            obj.put("progress", progressPercent);
            obj.put("candidate", candidate.firstName + " " + candidate.lastName);
            renderJSON(obj);
            
        }
    }
    
//    /**
//     * The list of user (donor) geolocations assembled in equivalent of json array
//     * comprise: user firstName, latitude, longitude
//     * renders all Geolocations as a json array
//     */
//    public static void listGeolocations()
//    {
//      List<Geolocation> geo = Geolocation.findAll();
//      List<ArrayList<String>> jsonArray = new ArrayList<ArrayList<String>>();
//      for (Geolocation g : geo)
//      {
//        Logger.info("Geolocations " + g.latitude + ", " + g.longitude);   
//        ArrayList<String> s = new ArrayList<String>();
//        s.add(g.user.firstName);
//        s.add(g.latitude);
//        s.add(g.longitude);
//        jsonArray.add(s);
//      }
//      renderJSON(jsonArray);
//    }
    
}