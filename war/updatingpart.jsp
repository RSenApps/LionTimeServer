<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="liontime.*" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
  <head>
  	<link rel="chrome-webstore-item" href="https://chrome.google.com/webstore/detail/aeelfcooaakdiijgfllekabkhmgackgl">
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
    <link rel="shortcut icon" href="static/favicon.ico">
    <%
 
 TimeZone.setDefault(TimeZone.getTimeZone("PST"));
	String[] timeInfo = TimeTillCalculator.getTimeTill();
	String time;
	String nextClass = "";
	if (timeInfo[0].equals("-1"))
	{
	
			time = "School is Over!";
		
	}
	else
	{
	  time = timeInfo[0] + " min until " + timeInfo[1];
	  nextClass = timeInfo[1] + ": " + timeInfo[2];
	}
      pageContext.setAttribute("title", time);
%>
    <title>${fn:escapeXml(title)}</title>
    
  </head>

  <body>
 

</script>
<%
 
 
      pageContext.setAttribute("time", time);
      pageContext.setAttribute("nextClass", nextClass);
      Calendar cal = Calendar.getInstance();
      response.setIntHeader("Refresh", 60-cal.get(Calendar.SECOND));
      SimpleDateFormat df = new SimpleDateFormat();
	  df.applyPattern("hh:mm a");
      String currentTime = df.format(cal.getTime());
      pageContext.setAttribute("currentTime", currentTime);
%>
<h1>${fn:escapeXml(time)}</h1>
<p>${fn:escapeXml(currentTime)}</p>
<p>${fn:escapeXml(nextClass)}</p>

  </body>
</html>