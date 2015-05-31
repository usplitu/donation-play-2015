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


public class DonationController extends Controller 
{

    // TODO : requires refactoring as part of ajax impl
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
        String currentCandidateEmail = session.get("currentCandidate");
        Candidate candidate = Candidate.findByEmail(currentCandidateEmail);
        String currentCandidate = "";
        String donationprogress = "0";
        if (candidate != null)
        {
          currentCandidate = candidate.firstName + " " + candidate.lastName;
          donationprogress = CandidateController.percentDonationTargetReached(candidate);
        }
        
        Geolocation geolocation = user.geolocation;
        
        render(user, donationprogress, candidates, currentCandidate, geolocation.latitude, geolocation.longitude);
      }
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
            Candidate candidate = Candidate.findByEmail(candidateEmail);
            Donation donation = new Donation(user, amountDonated, methodDonated, candidate);
            donation.save();
 
            candidate.addDonation(donation);
            candidate.save();
            user.addDonation(donation);
            user.save();
            
            // TODO remove or modify this test code: consider returning progress %
            JSONObject obj = new JSONObject();
            obj.put("email", "homer@simpson.com");
            Logger.info("In Controller method Geojson.refreshTest: attempting to render json " + obj);  
            renderJSON(obj);
            
        }
        //index();
    }
    
    /**
     * TODO User JSONObject as in method donate
     * renders all Geolocations as a json array
     */
    public static void listGeolocations()
    {
      List<Geolocation> geo = Geolocation.findAll();
      List<ArrayList<String>> jsonArray = new ArrayList<ArrayList<String>>();
      for (Geolocation g : geo)
      {
        Logger.info("Geolocations " + g.latitude + ", " + g.longitude);   
        ArrayList<String> s = new ArrayList<String>();
        s.add(g.user.firstName);
        s.add(g.latitude);
        s.add(g.longitude);
        jsonArray.add(s);
      }
      //renderJSON(geo);
      renderJSON(jsonArray);
    }

}