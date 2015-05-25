package models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import play.db.jpa.Model;

@Entity
public class Admin extends Model
{
  public String username;
  public String password;

//  @OneToMany(mappedBy = "administrator")
//  public List<Candidate> candidates = new ArrayList<Candidate>();

  public Admin(String username, String password)
  {
    this.username = username;
    this.password = password;
  }

  public static Admin findByUsername(String username)
  {
    return find("username", username).first();
  }

  public boolean checkPassword(String password)
  {
    return this.password.equals(password);
  }

}