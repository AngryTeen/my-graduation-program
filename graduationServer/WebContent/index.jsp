<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<form action="UserInfoServlet" method="post">
	<input type="text" name="all" value="[{"account":"jonny0","password":"password0","email":"email0","schoolName":"schoolName0","studentId":"schoolName0","birth":"schoolName0","sex":"male0"},{"account":"jonny1","password":"password1","email":"email1","schoolName":"schoolName1","studentId":"schoolName1","birth":"schoolName1","sex":"male1"},{"account":"jonny2","password":"password2","email":"email2","schoolName":"schoolName2","studentId":"schoolName2","birth":"schoolName2","sex":"male2"},{"account":"jonny3","password":"password3","email":"email3","schoolName":"schoolName3","studentId":"schoolName3","birth":"schoolName3","sex":"male3"},{"account":"jonny4","password":"password4","email":"email4","schoolName":"schoolName4","studentId":"schoolName4","birth":"schoolName4","sex":"male4"},{"account":"jonny5","password":"password5","email":"email5","schoolName":"schoolName5","studentId":"schoolName5","birth":"schoolName5","sex":"male5"},{"account":"jonny6","password":"password6","email":"email6","schoolName":"schoolName6","studentId":"schoolName6","birth":"schoolName6","sex":"male6"},{"account":"jonny7","password":"password7","email":"email7","schoolName":"schoolName7","studentId":"schoolName7","birth":"schoolName7","sex":"male7"},{"account":"jonny8","password":"password8","email":"email8","schoolName":"schoolName8","studentId":"schoolName8","birth":"schoolName8","sex":"male8"},{"account":"jonny9","password":"password9","email":"email9","schoolName":"schoolName9","studentId":"schoolName9","birth":"schoolName9","sex":"male9"}]">
	<input type="submit" value="提交0">
</form>
<form action="UserInfoServlet" method="post">
	<input type="text" name="account" value = "jonny">
	<input type="password" name = "password" value = "011235">
	<input type="text" name="email" value="jonny.peng@tinno.com">
	<input type="text" name="schoolName" value="南京航空航天大学">
	<input type="text" name = "studentId" value = "161210316">
	<input type="text" name="birth" value = "1994-06-17">
	<input type="text" name="sex" value = "male">
	<input type="submit" value="提交1">
</form>

<form action="CallInfoServlet" method="post">

	<input type="text" name="ringTime" value = "2016-05-04 22:22:00">
	<input type="text" name="startTime" value="2016-05-04 22:22:20">
	<input type="text" name="stopTime" value="2016-05-04 22:23:20">
	<input type="submit" value="提交2">
</form>

<form action="MessageInfoServlet" method="post" >
	
	<input type="text" name="messageTime" value = "2016-05-06 09:08:06">
	<input type="text" name="messageType" value="5">
	<input type="submit" value="提交3">
</form>
<form action="AppUsedInfoServlet" method="post">

	<input type="text" name="appName" value = "信息收集器">
	<input type="text" name="appStartTime" value="2016-05-07 21:20:20">
	<input type="text" name="appStopTime" value="2016-05-07 22:20:23">
	<input type="submit" value="提交4">
</form>
<form action="SensorInfoServlet" method="post">

	<input type="text" name="sensorName" value = "1111">
	<input type="text" name="sensorGetTime" value="2016-05-07 15:23:23">
	<input type="text" name="sensorValue" value="12.7">
	<input type="submit" value="提交4">
</form>



</body>
</html>