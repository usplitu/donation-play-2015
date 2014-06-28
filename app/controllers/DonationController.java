package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;

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
      String progress = prog + "%";//a trailing % required to render in view progress bar

      Logger.info("Donation ctrler : user is " + user.email);
      Logger.info("Donation ctrler : percent target achieved " + progress);
      render(user, progress);
    }
  }

  /**
   * 
   * @param amountDonated individual user amount donated
   * @param methodDonated method by which donation made
   */
  public static void donate(long amountDonated, String methodDonated)
  {
    Logger.info("amount donated " + amountDonated + " " + "method donated " + methodDonated);

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
   * @param user  the donor
   * @param amountDonated the amount the user (donor) donates
   */
  private static void addDonation(User user, long amountDonated, String methodDonated)
  {
    Donation bal = new Donation(user, amountDonated, methodDonated);
    bal.save();
  }

  /*
   * @return the target amount to be collected from all donations
   */
  private static long getDonationTarget()
  {
    // TODO Input this value thro' html template admin controlled
    return 20000;
  }

  /*
   * @return the percentage of the cumulative target achieved to date as a float number in String format
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
    Logger.info("Percent of target achieved (string) " + progress + " percentachieved (long)= " + percentachieved);
    return progress;
  }

  public static void renderReport()
  {
    List<Donation> donations = Donation.findAll();
    render(donations);
  }
}
