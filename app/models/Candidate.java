package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

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
  
  @ManyToOne
  public Admin administrator;
  
  public static Candidate findByEmail(String email)
  {
    return find("email", email).first();
  }
  
  public void addUser(User user)
  {
    users.add(user);
  }
}
