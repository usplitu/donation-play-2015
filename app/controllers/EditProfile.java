package controllers;

// Story 3
import models.User;
import play.mvc.Controller;

public class EditProfile extends Controller 
{
    public static void index() 
    {
    	User user = Accounts.getCurrentUser();
        render(user);
    }
    
	public static void edit(User user)
    {
    	User currentUser = Accounts.getCurrentUser();
    	copyUser(user, currentUser);
    	currentUser.save();
    	index();
    }
	
	private static void copyUser(User from, User to)
	{
		to.usaCitizen = from.usaCitizen;
		to.firstName  = from.firstName ;
		to.lastName   = from.lastName  ;
		to.age        = from.age       ;
		to.location   = from.location  ;
		to.email      = from.email     ;
		to.password   = from.password  ;		
	}

}
