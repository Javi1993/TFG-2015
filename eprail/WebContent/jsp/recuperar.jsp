<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="funciones.tfg.eprail.Funciones"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Eprail: repositorio de casos</title>
<link href="/eprail/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/eprail/script/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="/eprail/script/center.js"></script>
</head>
<body>
<%
	String leng = (String) request.getSession().getAttribute("lenguage");
	if(leng == null)
	{
		Funciones.setLenaguage(request.getSession());
		leng = (String) request.getSession().getAttribute("lenguage");
	}
%>
<jsp:include page="./lenguage.jsp" flush="true" />
	<div class="center" style="height:155px; width:400px;">
	<div class="title"><%if(leng.equals("SP")){ %>Recuperaci&oacute;n de contrase&ntilde;a<%}else{%>Recover password<%}%></div>
		<%
			if ((Boolean) request.getAttribute("user")) {
				if(leng.equals("SP")){ 
		%>
		<p>Se ha enviado un correo a <%=request.getParameter("email")%>.</p>
		<p>Sigue las instrucciones indicadas en él para restablecer tu contrase&ntilde;a.</p>
		<%}else{ %>
		<p>Sent an email to <%=request.getParameter("email")%>.</p>
		<p>Follow the instructions on it to reset your password..</p>
		<%}
			} else {
				if(leng.equals("SP")){ 
		%>
		<p>El email <%=request.getParameter("email")%> no se encuentra en nuestra base de datos.</p>
		<p>Comprueba que los datos introducidos son correctos.</p>
		<%}else{ %>
		<p>The email  <%=request.getParameter("email")%> not in our database.</p>
		<p>Check that the details are correct.</p>
		<%}
			}
		%>
		<br><br>
		<span class="campo"><a class="but" href="/eprail/"><%if(leng.equals("SP")){ %>Inicio<%}else{%>Start<%} %></a></span>
	</div>
</body>
</html>