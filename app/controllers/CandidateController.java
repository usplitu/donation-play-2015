package controllers;

import java.util.List;

import models.Candidate;
import models.Office;
import models.User;
import play.Logger;
import play.mvc.Controller;

public class CandidateController extends Controller
{
  public static void index()
  {
    List<Office> offices = Office.findAll();
    //logoffices(offices);
    render(offices);
  }
  
//  private static void logoffices(List<Office> offices)
//  {
//    for (Office office : offices)
//      Logger.info("Registered office: " + office.title + " " + office.description);
//  }
  
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
  private static boolean isRegistered(Candidate currCandidate)
  {
    List<Candidate> candidates = Candidate.findAll();
    for (Candidate candidate : candidates)
    {
      if (candidate.email.equalsIgnoreCase(currCandidate.email))
        return true;
    }
    return false;
  }

}
