<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List, java.io.File, modeldata.tfg.eprail.Project, modeldata.tfg.eprail.Sharing"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="/eprail/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/eprail/script/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="/eprail/script/center.js"></script>
</head>
<body>
	<%-- Cogemos el JavaBean del usuario de la session --%>
	<jsp:useBean id="userBean" class="modeldata.tfg.eprail.User"
		scope="session" />
	<jsp:include page="./top.jsp" flush="true" />
	<div class="center" style="height:500px; width:1000px;">
	<%
		Project project =  (Project) request.getAttribute("project");
		String path = (String) request.getAttribute("path");
	%>
		<span class="title">Viendo <%=project.getProjectName() %> <%=request.getAttribute("name") %></span>&nbsp;&nbsp;<a class="subtitle" style="left:28%;" href="/eprail/controller/login" title="Volver"><img src="/eprail/img/back.png" alt="back" width="30"
			height="30"></a>
		<jsp:include page="<%=path %>" flush="true" />		
	</div>
</body>
</html>