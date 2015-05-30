package controllers;

import java.util.List;

import models.Candidate;
import models.Geolocation;
import models.User;
import play.Logger;
import play.mvc.Controller;

public class Accounts extends Controller
{

  public static void signup()
  {
    List<Candidate> candidates = Candidate.findAll();
    render(candidates);
  }

  public static void register(User user, Geolocation geolocation)
  {
    if (!isRegistered(user))
    {
      geolocation.save();
      user.save();
      login();      
    }
    renderText("You have already registered.\nPlease press back button and navigate to the log in page." );
  }

  private static boolean isRegistered(User currUser)
  {
    List<User> users = User.findAll();
    for (User user : users)
    {
      if (user.email.equalsIgnoreCase(currUser.email))
        return true;
    }
    return false;
  }
  
  public static void login()
  {
    render();
  }

  public static void logout()
  {

    session.clear();
    Welcome.index();
  }

  public static void authenticate(String email, String password)
  {
    Logger.info("Attempting to authenticate with " + email + ":" + password);

    User user = User.findByEmail(email);
    if ((user != null) && (user.checkPassword(password) == true))
    {
      Logger.info("Successfull authentication of  " + user.firstName + " " + user.lastName);
      session.put("logged_in_userid", user.id);
      DonationController.index();
    }
    else
    {
      Logger.info("Authentication failed");
      login();
    }
  }

  public static User getCurrentUser()
  {
    String userId = session.get("logged_in_userid");
    if (userId == null)
    {
      return null;
    }
    User logged_in_user = User.findById(Long.parseLong(userId));
    Logger.info("In Accounts controller: Logged in user is " + logged_in_user.firstName);
    return logged_in_user;
  }
}
