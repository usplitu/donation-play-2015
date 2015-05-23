  package models;

  import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

  /**
   * User has been escaped: This is necessary because User is a reserved word in PostGreSQL
   * However, if working in local host and wish to use localhost:9000/@db (for example) to view database
   * Then it is necessary to temporarily comment out the line (i.e. @Table(name = "`User`") while testing with local host
   *
   */
  @Entity
  // @Table(name="`User`")
  public class User extends Model
  {
    public boolean usaCitizen;
    public String  firstName ;
    public String  lastName  ;
    public String  age       ; // Story 1
    public String  address1  ; // Story 5
    public String  address2  ;
    public String  city      ;
    public String  state     ; // Story 1
    public String  zipcode   ; // Story 5
    public String  email     ;
    public String  password  ;
    
    @ManyToOne
    public Candidate  candidate ;
    
    @OneToMany(mappedBy="from")
    public List<Donation> donations;
    
    public static User findByEmail(String email) 
    {
        return find("email", email).first();
    }

    public boolean checkPassword(String password) 
    {
        return this.password.equals(password);
    }
    
    public void addCandidate(Candidate candidate)
    {
      this.candidate = candidate;
    }
  }
