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
    <title>Lion Time</title>
    
  </head>

  <body>
 
<table width="100%">
<tr valign="top">
<td>
        <canvas id="myCanvas" width="400" height="400">
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
clockImage.src = 'static/liontimeflat.jpg';

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
<iframe src="updatingpart.jsp" frameborder="0">
	</iframe>
<p> <button onclick="chrome.webstore.install()" id="install-button">Chrome Extension</button>


<p><a href="http://play.google.com/store/apps/details?id=com.RSen.LionTime">Android App</a>
<p><a href="http://play.google.com/store/apps/developer?id=RSen">Other Android Apps</a>
<p><a href="mailto:RSenApps+LionTime@gmail.com">Email Me</a>
</p>
</td>
        <td > 

        <div id="scheduleDiv" style="DISPLAY: none">
        <p>If your schedule is not shown, go to the <a target="_blank" href="https://thesouk.lakesideschool.org/">Souk</a> and login.
        <p> 
        <button onclick="document.getElementById('scheduleFrame').src+=''" id="tryagain">Try Again</button>
        </p>
        <iframe width="800px" height = "1163px" id="scheduleFrame"  frameborder="0">
		</iframe> </div>
		<h1>Please <a target="_blank" href="mailto:RSenApps+LionTime+iOS+Testing">email</a> me about testing the new iPhone beta!
        </h1>
        <img src="static/iosbeta.png"/>
		<form name="input" action="html_form_action.asp" method="get">
Student ID: <input type="text" name="id">
<input type="BUTTON" value="View Schedule" OnClick = "document.getElementById('scheduleFrame').src = 'https://thesouk.lakesideschool.org/ScheduleSearch/ExtendedSchedule.aspx?SRP=1,1,' + input.id.value + ',2'; scheduleDiv.style.display = 'block';">
</form>
		</div>
		
    </td>
    </tr>
    </table>
  </body>
</html>