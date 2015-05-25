package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Candidate;
import models.User;
import play.Logger;
import play.mvc.Controller;

public class Administrator extends Controller
{
  public static void index()
  {
    render();
  }
  
  public static void authenticate(String email, String password)
  {
    Logger.info("Administrator authenticating at " + (new Date()));
    if (email.equalsIgnoreCase("admin") && password.equalsIgnoreCase("secret"))
    {
      //renderTemplate("Administrator/report.html");
      report();
    }
    index();
  }
  
  public static void report()
  {
    List<User> users = User.findAll();
    List<Candidate> candidates = Candidate.findAll();
    render(users, candidates);
  }
  
  public static void logout()
  {
    session.clear();
    Welcome.index();
  }
}
