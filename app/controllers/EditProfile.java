package controllers;

// Story 3
import models.Geolocation;
import models.User;
import play.mvc.Controller;

public class EditProfile extends Controller 
{
    public static void index() 
    {
    	User user = Accounts.getCurrentUser();
    	if(user != null)
    	{
    		render(user);
    	}
    }
    
	public static void edit(User user)
    {
    	User currentUser = Accounts.getCurrentUser();
    	copyUser(user, currentUser);
    	currentUser.geolocation.save();
    	currentUser.save();
    	DonationController.index();
    }
	
	/**
	 * Exclude checkbox (usa citizen), email & password
	 * @param from
	 * @param to
	 */
	private static void copyUser(User from, User to)
	{
		to.firstName    = from.firstName ;
		to.lastName     = from.lastName  ;
		to.age          = from.age       ;
		to.address1     = from.address1  ; 
		to.address2     = from.address2  ;
		to.city         = from.city      ;
		to.state        = from.state     ;
		to.zipcode      = from.zipcode   ;	
		to.geolocation.latitude = from.geolocation.latitude;
		to.geolocation.longitude= from.geolocation.longitude;

	}

}
