package liontime;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class IrregularSchedulesServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        HashMap<Calendar, Integer> hashMap = Schedule.getIrregulars();
        Set<Calendar> set = hashMap.keySet();
        Gson gson = new Gson();
        HashMap<String, Integer> jsonedHashMap = new HashMap<String, Integer>();
        for (Calendar cal : set)
        {
        	jsonedHashMap.put(gson.toJson(cal), hashMap.get(cal));
        }
        
        resp.getWriter().println(gson.toJson(jsonedHashMap));
    }
}