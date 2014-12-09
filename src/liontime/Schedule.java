package liontime;

import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;


public class Schedule {
	// private static HashMap<Calendar, Integer> irregularSchedules = new
	// HashMap<Calendar, Integer>();
	public static final int TYPE_REGULAR = 0;
	public static final int TYPE_WED = 1;
	public static final int TYPE_THU = 2;
	public static final int TYPE_B = 3;
	public static final int TYPE_NOSCHOOL = 4;

	public static int getScheduleType(Calendar cal) {
		int irregularCode = checkIrregular(cal);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (irregularCode != -1) {
			return irregularCode;
		} else if (dayOfWeek == Calendar.WEDNESDAY) // wednesday
		{
			return TYPE_WED;
		} else if (dayOfWeek == Calendar.THURSDAY) // Thursday
		{
			return TYPE_THU;
		} else if (dayOfWeek == Calendar.SATURDAY
				|| dayOfWeek == Calendar.SUNDAY) {
			return TYPE_NOSCHOOL;
		} else {
			return TYPE_REGULAR;
		}
	}

	public static void addIrregularSchedule(Calendar cal,
			int scheduleType) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		//-1900 because of weird date thing
		Date date = new Date(cal.get(Calendar.YEAR) -1900, cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
		Entity employee = new Entity("Schedule", date.getTime());

		employee.setProperty("date", date);
		employee.setProperty("code", scheduleType);
		datastore.put(employee);
	}
	public static HashMap<Calendar, Integer> getIrregulars ()
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Calendar now = Calendar.getInstance();
		//-1900 because of weird date thing -1 to get today's also
		Date date = new Date(now.get(Calendar.YEAR)-1900, now.get(Calendar.MONTH), now.get(Calendar.DATE));
		Query q = new Query("Schedule").addFilter("date", Query.FilterOperator.GREATER_THAN_OR_EQUAL, date);
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> iterator = pq.asIterator();
		HashMap<Calendar, Integer> hashMap = new HashMap<Calendar, Integer>();
		while(iterator.hasNext())
		{
			Entity entity = iterator.next();
			Date scheduleDate = (Date) entity.getProperty("date");
			int scheduleType = Integer.parseInt(entity.getProperty("code").toString());
			Calendar scheduleCalendar = Calendar.getInstance();
			scheduleCalendar.clear();
			scheduleCalendar.setTime(scheduleDate);
			hashMap.put(scheduleCalendar, scheduleType);
		}
		return hashMap;
	}
	public static int checkIrregular(Calendar cal) {
		
		try {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
			Date date = new Date(cal.get(Calendar.YEAR)-1900, cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
			Filter filter =
					  new Query.FilterPredicate ("date",
					                      FilterOperator.EQUAL,
					                      date);
			Query q = new Query("Schedule")
            .setFilter(filter);

			PreparedQuery pq = datastore.prepare(q);
			Entity result = pq.asSingleEntity();
			if (result != null) {
				return Integer.parseInt(result.getProperty("code").toString());

			} else {
				return -1;
			}
		} catch (Exception e) {
			return -1;
		}
	
	}
	
	

	

	public static int[] getScheduleTimes() {
		Calendar now = Calendar.getInstance();
		int[] schedule;
		int irregularCode = checkIrregular(now);
		if (irregularCode == TYPE_B) {
			return new int[] { 490, 540, 590, 640, 690, 740, 790, 810, 860, 905 };
		} else if ((now.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY && irregularCode == -1)
				|| irregularCode == TYPE_WED) // wednesday
		{
			schedule = new int[] { 490, 575, 655, 735, 775, 830, 905 };
		} else if ((now.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY && irregularCode == -1)
				|| irregularCode == TYPE_THU) // Thursday
		{
			schedule = new int[] { 490, 575, 655, 735, 775, 855, 905 };
		} else {
			schedule = new int[] { 490, 540, 605, 655, 705, 755, 805, 855, 900 };
		}
		return schedule;

	}
}
