<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<c:set var="nav_first" value="dashboard" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<title>Dashboard</title>
</head>
<body>
	EJUZUO Admin
	<a href="/logout">Logout</a>
	<shiro:hasPermission name="ADMIN:ROLE:ADD">
	<a href="/user/create">User create</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="ADMIN:ROLE:EDIT">
	<a href="/user/update">User update</a>
	</shiro:hasPermission>
</body>
<footer-scripts>
</footer-scripts>
</html>