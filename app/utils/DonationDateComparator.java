package utils;

import java.util.Comparator;
import models.Donation;
import play.Logger;
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

		Logger.info("In comparator: Donation o1 : " + o1.dateDonated.toString());
		Logger.info("In comparator: Donation o2 : " + o2.dateDonated.toString());
		return o1.dateDonated.compareTo(o2.dateDonated);
	}
	
}