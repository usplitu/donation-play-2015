package controllers;

import play.*;
import play.mvc.*;

public class Welcome extends Controller {
	public static void index()
	{
		Logger.info("Landed in Welcome class");
		render();
	}
}
