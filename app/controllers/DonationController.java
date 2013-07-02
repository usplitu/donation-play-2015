package controllers;

import play.*;
import play.db.jpa.JPA;
import play.mvc.*;

import java.util.*;

import javax.persistence.Query;

import models.*;

public class DonationController extends Controller {
	
	public static void index() {
		User user = Accounts.getCurrentUser();
		if (user == null) {
			Logger.info("Donation class : Unable to getCurrentuser");
			Accounts.login();
		} else {
			Logger.info("Donation ctrler : user is " + user.email);
			render(user);
		}
	}

	/**
	 * Log and save to database amount donated and method of donation, eg.
	 * paypal, direct payment
	 * 
	 * @param amountDonated
	 * @param methodDonated
	 */
	public static void donate(long amountDonated, 
							  String methodDonated
							  ) {
		Logger.info("amount donated " + amountDonated + " " + "method donated "
				+ methodDonated);
		
		User user = Accounts.getCurrentUser();
		if (user == null) {
			Logger.info("Donation class : Unable to getCurrentuser");
			Accounts.login();
		} else {
			addDonation(user, amountDonated,methodDonated);
		}
		index();
	}

	/**
	 * @param user
	 * @param amountDonated
	 */
	private static void addDonation(User user, long amountDonated,String methodDonated) {
		Donation bal = new Donation(user, amountDonated,methodDonated);
		bal.save();
	}
}
