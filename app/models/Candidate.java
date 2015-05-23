package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Candidate extends Model
{
  public String firstName;
  public String lastName;
  public String email;
  public String office;
  
  public static Candidate findByEmail(String email)
  {
    return find("email", email).first();
  }
}
