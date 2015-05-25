package controllers;

import java.util.List;

import models.Candidate;
import models.User;
import play.Logger;
import play.mvc.Controller;

public class CandidateController extends Controller
{
  public static void index()
  {
    render();
  }
  
  public static void register(Candidate candidate)
  {
    Logger.info("Registering candidate " + candidate.firstName + " "+ candidate.lastName);
    if (!isRegistered(candidate))
    {
      candidate.save();
      Administrator.report();
    }
    renderText("Candidate already registered");
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
