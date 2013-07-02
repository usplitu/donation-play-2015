package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import play.db.jpa.Model;


@Entity
public class Donation extends Model {
	public long received;
  public String methodDonated;
	
	@ManyToOne
	public User from;

	public Donation(User from, long received, String methodDonated) {
		this.received = received;
		this.methodDonated = methodDonated;
		this.from = from;
	}
}
