package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Geolocation extends Model
{
  // format: latitude,longitude
  // example: 45.6555,-7.5679
  // public String latlng;
  public String latitude;
  public String longitude;
}
