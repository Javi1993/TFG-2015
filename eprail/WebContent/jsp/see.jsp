<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List, java.io.File, modeldata.tfg.aplicacionJPA.Project, modeldata.tfg.aplicacionJPA.Sharing"%>
<%@page %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>aplicacion: repositorio de casos</title>
<link href="/aplicacion/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/aplicacion/script/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="/aplicacion/script/center.js"></script>
</head>
<body>
	<%-- Cogemos el JavaBean del usuario de la session --%>
	<jsp:useBean id="userBean" class="modeldata.tfg.aplicacionJPA.User"
		scope="session" />
	<jsp:include page="./top.jsp" flush="true" />
	<div class="center" style="height:500px; width:1000px;">
	<%
		Project project =  (Project) request.getAttribute("project");
		String path = (String) request.getAttribute("path");
	%>
		<span class="title" class="title" style="text-align:center; width:160px; overflow:hidden; white-space:nowrap; text-overflow:ellipsis;"><%=project.getProjectName() %></span>&nbsp;&nbsp;<a class="subtitle" style="left:230px;" href="/aplicacion/controller/login" title="Volver"><img src="/aplicacion/img/back.png" alt="back" width="30"
			height="30"></a>
		<jsp:include page="<%=path %>" flush="true" />		
	</div>
</body>
</html>