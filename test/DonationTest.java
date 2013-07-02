import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

import play.Logger;
import play.test.*;
import models.*;

public class DonationTest extends UnitTest 
{
  private Donation d1,d2;
  private User u1,u2;
  
  @Before
  public void setup()
  {
	u1 = new User(true, "Homer","Simpson","homer@simpson.com","secret");
	u2 = new User(true, "Marge","Simpson","marge@simpson.com","secret");
	d1 = new Donation(u1,1000,"cash");
	d2 = new Donation(u2,150,"paypal");
    u1.save();
    d1.save();
    u2.save();
    d2.save();
  }
  
  @After
  public void teardown()
  {
    d1.delete();
  }

  @Test
  public void testCreate()
  {
	List<Donation> allDonations = Donation.findAll();
	for(Donation donation : allDonations) {
		User user = donation.from;
		assertNotNull(user);
		if(user.email == "homer@simpson") {
			assertEquals(donation.received,Long.parseLong("1000"));			
		}
		if(user.email == "marge@simpson") {
			assertEquals(donation.received,Long.parseLong("150"));			
		}	
		if(user.email == "homer@simpson") {
			assertThat(donation.received, not(equalTo(Long.parseLong("10"))));			
		}
	}
  }
  
  @Test
  public void testNotThere()
  {		boolean found = false;
		List<Donation> allDonations = Donation.findAll();
		for(Donation donation : allDonations) {
			User user = donation.from;
			assertNotNull(user);
			if(user.email == "oldfield@simpson") {
				found = true;
			}
		}
		assertFalse(found);
  }
  
}