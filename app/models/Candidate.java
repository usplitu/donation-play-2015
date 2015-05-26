package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Candidate extends Model
{
  public String firstName;
  public String lastName;
  public String email;
  public long donationTarget;
  
  @OneToMany(mappedBy="candidate")
  public List<Donation> donations;;
  
  @ManyToOne
  public Office office;
  
  public static Candidate findByEmail(String email)
  {
    return find("email", email).first();
  }
  
  public void addDonation(Donation donation)
  {
    this.donations.add(donation);
  }
  
  public void addOffice(Office office)
  {
    this.office = office;
  }
  
}
