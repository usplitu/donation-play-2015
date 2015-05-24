 package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Donation extends Model 
{
    public long received;
    public String methodDonated; 
    public Date dateDonated;

    @ManyToOne
    public User from;
    
    @ManyToOne
    public Candidate candidate;

    public Donation(User from, long received, String methodDonated, Candidate candidate) 
    {
        this.received 		    = received;
        this.methodDonated 	  = methodDonated;
        this.from 			      = from;
        this.candidate        = candidate;
        this.dateDonated 	    = new Date();
    }

}