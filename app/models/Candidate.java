package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import play.db.jpa.Model;

@Entity
public class Candidate extends Model
{
  public String firstName;
  public String lastName;
  public String email;
  public String office;
  
  @ManyToMany(mappedBy="candidates")
  public List<User> users;
  
  public static Candidate findByEmail(String email)
  {
    return find("email", email).first();
  }
  
  public void addUser(User user)
  {
    users.add(user);
  }
}
