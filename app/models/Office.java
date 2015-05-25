package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Office extends Model
{
  public String title;
  public String description;

  @OneToMany(mappedBy = "office")
  public List<Candidate> candidates = new ArrayList<Candidate>();
  
  public void addCandidate(Candidate candidate)
  {
    candidates.add(candidate);
  }
}
