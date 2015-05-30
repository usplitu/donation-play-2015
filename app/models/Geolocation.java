package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class Geolocation extends Model
{
  // format: latitude,longitude
  // example: 45.6555,-7.5679
  // public String latlng;
  public String latitude;
  public String longitude;
  
  @OneToOne(mappedBy="geolocation")
  public User user;

  
  public void addUser(User user)
  {
    this.user = user;
  }
}
