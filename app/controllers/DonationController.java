package controllers;

import play.*;
import play.mvc.*;

public class DonationController extends Controller {
	
	public static void index() {
	  Logger.info("About to donate");
	  render();
	}
}
