package controllers;

import models.Candidate;
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
    candidate.save();
    Accounts.signup();
  }

}
