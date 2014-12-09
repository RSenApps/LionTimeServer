package liontime;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sun.xml.internal.bind.v2.model.nav.Navigator;

public class AddIrregularScheduleServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
    	 
        resp.setContentType("text/plain");
        Gson gson = new Gson();
        String calendarJson = req.getParameter("cal");
        
        int scheduleType = Integer.parseInt(req.getParameter("code"));
        Calendar cal = gson.fromJson(calendarJson, Calendar.class);
        Schedule.addIrregularSchedule(cal, scheduleType);
        resp.getWriter().println("succeded for: " + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DATE) + " and " + scheduleType);
    }
}