package tomczak.product.backlog.helper.item;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import tomczak.product.backlog.model.ItemEvent;
import tomczak.product.backlog.model.Status;
import tomczak.product.backlog.rest.ChartData;

public final class ItemOpenessVector {

	private final TreeMap<Long, Integer> vector;
	
	public ItemOpenessVector(Date from, Date to) {
		this(null);
		Calendar calendarFrom = getMidnight(from);
		Calendar calendarTo = getMidnight(to);
		
		while (calendarFrom.compareTo(calendarTo) <= 0) {
			vector.put(calendarFrom.getTimeInMillis(), 0);
			calendarFrom.add(Calendar.DATE, 1);
		}
	}
	
	private ItemOpenessVector(TreeMap<Long, Integer> vector) {
		if (vector == null) {
			this.vector = new TreeMap<Long, Integer>();
		} else {
			this.vector = new TreeMap<Long, Integer>(vector);
		}
	}
	
	public ItemOpenessVector addOpenings(List<ItemEvent> itemEvents) {
		ItemOpenessVector v = new ItemOpenessVector(vector);
		Date startDate = null, closeDate = null;
		for (ItemEvent ie: itemEvents) {
			if (ie.getStatus().getId().equals(Status.OPEN_STATUS_ID)) {
				startDate = ie.getDate();
				closeDate = null;
			} else if (ie.getStatus().getId().equals(Status.CLOSE_STATUS_ID)) {
				if (startDate== null) {
					continue;
				}
				
				closeDate = ie.getDate();
				Calendar calendarFrom = getMidnight(startDate);
				Calendar calendarTo =  getMidnight(closeDate);
				
				while (calendarFrom.compareTo(calendarTo) < 0) {
					v.vector.put(calendarFrom.getTimeInMillis(), v.vector.get(calendarFrom.getTimeInMillis()) + 1);
					calendarFrom.add(Calendar.DATE, 1);
				}
			}
		}
		
		if (startDate != null && closeDate == null) {
			closeDate = v.getTo();
			Calendar calendarFrom = getMidnight(startDate);
			Calendar calendarTo = getMidnight(closeDate);
			
			while (calendarFrom.compareTo(calendarTo) <= 0) {
				v.vector.put(calendarFrom.getTimeInMillis(), v.vector.get(calendarFrom.getTimeInMillis()) + 1);
				calendarFrom.add(Calendar.DATE, 1);
			}
		}
		
		return v;
		
	}
	
	public Date getFrom() {
		return vector.isEmpty() ? null : new Date(vector.firstKey());
	}
	
	public Date getTo() {
		return vector.isEmpty() ? null : new Date(vector.lastKey());
	}
	
	public ChartData getChartData() {
		long [][] data = new long[vector.size()][2];
		int i = 0;
		for(Long l: vector.keySet()) {
			data[i][0] = l;
			data[i][1] = vector.get(l);
			i++;
		}
		return new ChartData(data);
	}
	
	public int size() {
		return vector.size();
	}
	
	private Calendar getMidnight(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c;
	}
	
	
}
