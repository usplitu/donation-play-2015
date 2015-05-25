package controllers;

import java.util.List;

import models.Office;
import play.mvc.Controller;

public class OfficeController extends Controller
{
  public static void index()
  {
    render();
  }
  
  public static void register(Office office)
  {
    if (!isRegistered(office))
    {
      office.save();
    }
    Administrator.report();
  }
  
  private static boolean isRegistered(Office currOffice)
  {
    List<Office> offices = Office.findAll();
    for (Office office : offices )
    {
      if (office.title.equalsIgnoreCase(currOffice.title))
        return true;
    }
    return false;
  }
}
