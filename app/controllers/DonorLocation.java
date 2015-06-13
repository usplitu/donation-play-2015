package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Geolocation;
import models.User;
import play.Logger;
import play.mvc.Controller;

public class DonorLocation extends Controller
{
  public static void index()
  {
    List<User> users = User.findAll();
    
    render(users);
  }
  
  /**
   * The list of user (donor) geolocations assembled in equivalent of json array
   * comprise: user firstName, latitude, longitude
   * renders all Geolocations as a json array
   */
  public static void listGeolocations()
  {
    List<Geolocation> geo = Geolocation.findAll();
    List<ArrayList<String>> jsonArray = new ArrayList<ArrayList<String>>();
    for (Geolocation g : geo)
    {
      Logger.info("Geolocations " + g.latitude + ", " + g.longitude);   
      ArrayList<String> s = new ArrayList<String>();
      s.add(g.user.firstName);
      s.add(g.latitude);
      s.add(g.longitude);
      jsonArray.add(s);
    }
    renderJSON(jsonArray);
  }
}
