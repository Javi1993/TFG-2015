<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="funciones.tfg.eprail.Funciones, java.util.Locale, java.text.SimpleDateFormat, java.sql.Timestamp"%>
<!DOCTYPE html>
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
	Timestamp date = (Timestamp)request.getAttribute("fecha");
	String leng = (String) request.getSession().getAttribute("lenguage");
	if(leng == null)
	{
		Funciones.setLenaguage(request.getSession());
		leng = (String) request.getSession().getAttribute("lenguage");
	}
%>
	<jsp:include page="../jsp/lenguage.jsp" flush="true" />
	<div class="center" style="height:100px; width:400px;">
		<%
			if ((Boolean) request.getAttribute("active")) {
		%>
			<%if(leng.equals("SP")){ %>
			<div class="title">Cuenta activada</div>
			<p>Tu cuenta ha sido activa con &eacute;xito.</p>
			<p>Dir&iacute;gete a incio para loguearte y empezar a usar la
				aplicaci&oacute;n.</p>
			<%}else{ %>
			<div class="title">Account enabled</div>
			<p>Your account was enabled successful.</p>
			<p>Go to login page and start using the application.</p>
		<%}
			} else {
				if(leng.equals("SP")){
		%>
		<div class="title">Cuenta ya activada</div>
		<p>
			Esta cuenta ya fue activada el
			<%SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy"); %>
			<%=sdf.format(date)%>.
		</p>
		<p>Dir&iacute;gete a incio para loguearte.</p>
		<%}else{ %>
		<div class="title">Account already enabled</div>
		<p>
			This account was already enabled on
			<%SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy", Locale.UK); %>
			<%=sdf.format(date)%>.
		</p>
		<p>Go to login page.</p>
		<%}
			}
		%>
		<span class="campo"><a class="but" href="/eprail/"><%if(leng.equals("SP")){ %>Inicio<%}else{ %>Start<%} %></a></span>
	</div>
</body>
</html>