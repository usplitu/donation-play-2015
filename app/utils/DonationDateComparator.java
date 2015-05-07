package utils;

import java.util.Comparator;
import models.Donation;

/**
 * Order donations by datestamp
 * 
 * @author jfitzgerald
 *
 */
public class DonationDateComparator  implements Comparator<Donation>
{
	@Override
	public int compare(Donation o1, Donation o2) 
	{
		return o2.dateDonated.compareTo(o1.dateDonated);
	}

}