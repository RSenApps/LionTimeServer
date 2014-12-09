package liontime;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class TimeTillCalculator {

	public static String[] getTimeTill() {
		int[] schedule;
		// to change schedule us now.getDate() to specify changed schedules,
		// remember to change periods below!
		// regular M,T,F schedule = new Array(490,540,605,655,705,755,805,855,
		// 900);
		// regular W schedule = new Array(490, 575, 655, 735, 775, 830,905);
		// regular Th schedule = new Array(490, 575, 655, 735, 775, 855,905);
		// B schedule = new Array(490,540,590,640,690,740,790,810,860, 905);
		// B periods var periods = new Array("1","2","3","4", "5", "6A", "6B",
		// "7", "8", "End of School");
		Calendar now = Calendar.getInstance();
	
		int nowMin = now.get(Calendar.HOUR_OF_DAY) * 60
				+ now.get(Calendar.MINUTE);
		int timeTill = -1;
		String nextPeriod = "Period ";
		Calendar nextClass = Calendar.getInstance();

		schedule = Schedule.getScheduleTimes();
		int scheduleType = Schedule.getScheduleType( now);
		if (scheduleType == Schedule.TYPE_NOSCHOOL) {
			return new String[] { "-1", "No School", "-1"}; //min until, period(string), next time(string)
		}
		for (int ii = 0; ii < schedule.length; ii++) {
			if (schedule[ii] > nowMin) {
				timeTill = schedule[ii] - nowMin;
				int hours = schedule[ii]/60;
				int minutes = schedule[ii]%60;
				nextClass.set(Calendar.HOUR_OF_DAY, hours);
				nextClass.set(Calendar.MINUTE, minutes);
				if (scheduleType == Schedule.TYPE_B) {
					String[] periods = new String[] { "1", "2", "3", "4", "5",
							"6A", "6B", "7", "8", "End of School" };
					if (ii == periods.length - 1) // End of school != Period end
													// of school
					{
						nextPeriod = periods[ii];
					} else {
						nextPeriod += periods[ii];
					}
					
				} else if (scheduleType == Schedule.TYPE_WED) {
					String[] periods = new String[] { "1", "3", "5", "Lunch",
							"Assembly", "7", "End of School" };
					if (ii == periods.length - 1) // End of school != Period end
													// of school
					{
						nextPeriod = periods[ii];
					} else {
						nextPeriod += periods[ii];
					}

				} else if (scheduleType == Schedule.TYPE_THU) {
					String[] periods = new String[] { "2", "4", "6", "Lunch",
							"8", "Activity", "End of School" };
					if (ii == periods.length - 1) // End of school != Period end
													// of school
					{
						nextPeriod = periods[ii];
					} else {
						nextPeriod += periods[ii];
					}
				}
				// TYPE_REG
				else if (ii + 1 > 8) {
					nextPeriod = "End of School";
				}
				// advisory
				else if (ii == 2 && 590 - nowMin > 0) {
					timeTill = 590 - nowMin;
					nextPeriod = "Advisory";
				}

				else {
					nextPeriod += ii + 1;
				}
				break;
			}

		}
		 SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
		String nextClassString = sdf.format(nextClass.getTime());
		String[] returnArray = new String[] { timeTill + "", nextPeriod, nextClassString};
		return returnArray;
	}

}
