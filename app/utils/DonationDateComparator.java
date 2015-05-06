package utils;

import java.util.Comparator;

import play.Logger;
import models.Donation;

public class DonationDateComparator  implements Comparator<Donation>
{

	@Override
	public int compare(Donation o1, Donation o2) {
		Logger.info("In DonatationDateComparator.compareTo; amount donated Donation o1 " + o1.dateDonated);
		return o2.dateDonated.compareTo(o1.dateDonated);
	}

}
