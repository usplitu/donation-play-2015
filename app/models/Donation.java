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
    public String dateDonated;

    @ManyToOne
    public User from;

    public Donation(User from, long received, String methodDonated) 
    {
        this.received 		= received;
        this.methodDonated 	= methodDonated;
        this.from 			= from;
        this.dateDonated 	= new Date().toString();
    }
}