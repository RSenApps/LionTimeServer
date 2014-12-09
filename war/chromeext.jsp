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
	String nextClass="";
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
  	<a href="https://itunes.apple.com/us/app/lion-time/id605924739?mt=8"> Get the new iPhone app!</a>
  <canvas id="myCanvas" width="300" height="300">
  Your browser does not support canvas
</canvas>
<script>
var canvas = document.getElementById('myCanvas');
var context = canvas.getContext('2d');

var clockImage = new Image();
var clockImageLoaded = false;
clockImage.onload = function(){
  clockImageLoaded = true;
}
clockImage.src = 'static/liontimeflat.png';

function addBackgroundImage(){
  context.drawImage(clockImage, canvas.width/2 * -1 ,canvas.height/2 * -1,canvas.width, canvas.height);
}

function degreesToRadians(degrees) {
  return (Math.PI / 180) * degrees
}

function drawHourHand(theDate){
  var hours = theDate.getHours() + theDate.getMinutes() / 60;

  var degrees = (hours * 360 / 12) % 360;

  context.save();
  context.fillStyle = 'b6a37b';
  context.strokeStyle = '#555';
  
  context.rotate( degreesToRadians(degrees));

  drawHand(110, 7, 3);

  context.restore();

}

function drawMinuteHand(theDate){
  var minutes = theDate.getMinutes() + theDate.getSeconds() / 60;

  context.save();
  context.fillStyle = 'b6a37b';
  context.strokeStyle = '#555';
  context.rotate( degreesToRadians(minutes * 6));

  drawHand(130, 7, 5);

  context.restore();
}

function drawHand(size, thickness, shadowOffset){
  thickness = thickness || 4;

  
  context.beginPath();
  context.moveTo(0,0); // center
  context.lineTo(thickness *-1, -10);
  context.lineTo(0, size * -1);
  context.lineTo(thickness,-10);
  context.lineTo(0,0);
  context.fill();
  context.stroke();
}

function drawSecondHand(theDate){
  var seconds = theDate.getSeconds();

  context.save();
  context.fillStyle = 'b6a37b';
  context.globalAlpha = 1.0;
  context.rotate( degreesToRadians(seconds * 6));

  drawHand(150, 4, 8);

  context.restore();
}


function createClock(){

  addBackgroundImage();

  var theDate = new Date();
  drawHourHand(theDate);
  drawMinuteHand(theDate);
  drawSecondHand(theDate);
}

function clockApp(){
  if(!clockImageLoaded){
    setTimeout('clockApp()', 100);
    return;
  }
  context.translate(canvas.width/2, canvas.height/2);
  createClock();
  setInterval('createClock()', 1000)
}

clockApp();
  
</script>
<%
 
 	  pageContext.setAttribute("nextClass", nextClass);
      pageContext.setAttribute("time", time);
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