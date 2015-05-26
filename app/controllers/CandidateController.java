package controllers;

import java.util.List;

import models.Candidate;
import models.Donation;
import models.Office;
import play.Logger;
import play.mvc.Controller;

public class CandidateController extends Controller
{
  public static void index()
  {
    List<Office> offices = Office.findAll();
    render(offices);
  }
  
  /**
   * Register an office to a candidate
   * @param candidate
   * @param officeTitle The office's title field
   */
  public static void register(Candidate candidate, String officeTitle)
  {
    Office office = getOffice(officeTitle);
    if(office == null) renderText("Null office object");
    Logger.info("Registering candidate " + candidate.firstName + " "+ candidate.lastName + "for office " + office.title);
    if(!isRegistered(candidate))
    {
      office.addCandidate(candidate);
      candidate.addOffice(office);
      office.save();
      candidate.save();
      Logger.info("registering candidate " + candidate.firstName + " for office " + office.title);
      Administrator.report();
    }
    renderText("Candidate already registered");
  }
  
  /**
   * Given the title of the office, return the Office object
   * @param title Office title
   * @return Reference to Object instance containing field 'title'
   */
  private static Office getOffice(String title)
  {
    List<Office> offices = Office.findAll();
    for (Office office : offices)
    {
      Logger.info("getOffice:  " + title + " office.title = " + office.title);
      if (office.title.equalsIgnoreCase(title))
      {
        return office;
      }
    }
    return null;
  }
  /**
   * Checks if proposed candidate already registered
   * @param proposedCandidate The candidate being propsed for registration
   * @return True if the candidate is already registered, else false.
   */
  private static boolean isRegistered(Candidate proposedCandidate)
  {
    List<Candidate> candidates = Candidate.findAll();
    for (Candidate candidate : candidates)
    {
      if (candidate.email.equalsIgnoreCase(proposedCandidate.email))
        return true;
    }
    return false;
  }
  
  /**
   * Calculates the percentage of the target donations achieved by particular candidate
   * If excess 100% achieved 100% returned
   * 
   * @param candidate The candidate for whom percentage target achieved being calculated
   * @return The percentage of target achieved as a string
   */
  public static String percentDonationTargetReached(Candidate candidate)
  {
    long sumDonations = 0;
    List<Donation> donations = candidate.donations;
    for (Donation donation : donations)
    {
      sumDonations += donation.received;
    }
    long percentTarget = (sumDonations*100)/candidate.donationTarget;
    Logger.info("percentTarget " + percentTarget);
    percentTarget = percentTarget > 100 ? 100 : percentTarget;
    return String.valueOf(percentTarget);
  }

}
