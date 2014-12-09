package liontime;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class TimeInfoServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
    	 TimeZone.setDefault(TimeZone.getTimeZone("PST"));
        resp.setContentType("text/plain");
        String[] timeInfo = TimeTillCalculator.getTimeTill();
        
        resp.getWriter().println(timeInfo[0] + "," + timeInfo[1]);
    }
}