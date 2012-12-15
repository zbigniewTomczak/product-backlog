package tomczak.product.backlog.test;

import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import tomczak.product.backlog.helper.item.ItemOpenessVector;

public class ItemOpenessVectorTest {
	@Test
	public void size_afterCreate_FromYesturday() {
		Date d = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, -1);
		ItemOpenessVector v = new ItemOpenessVector( c.getTime(), d);
		Assert.assertEquals(2, v.size());
	}
	
	//@Test
	//public void addOpenings_
}
