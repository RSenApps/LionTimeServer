package liontime;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AppleIrregularSchedulesServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        HashMap<Calendar, Integer> hashMap = Schedule.getIrregulars();
        Set<Calendar> set = hashMap.keySet();
        Boolean first = true;
        
        for (Calendar cal : set)
        {
        	if (!first)
        	{
        		resp.getWriter().print(",");
        	}
        	first=false;
        	String calRep = "Y" + cal.get(Calendar.YEAR) + "M" + (cal.get(Calendar.MONTH) + 1) + "D" + cal.get(Calendar.DATE);
        	String text = calRep + " " + hashMap.get(cal);
            resp.getWriter().print(text);
        }
        
    }
}