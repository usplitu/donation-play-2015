package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.Logger;
import play.db.jpa.JPA;
import play.db.jpa.Model;

//TODO for sum donations (ref RoseIndia)
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityManager;

import java.awt.Event;
import java.util.*;

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
