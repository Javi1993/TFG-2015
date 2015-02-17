<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	<div class="center" style="height:100px; width:400px;">
		<%
			if ((Boolean) request.getAttribute("active")) {
		%>
		<div class="title">Cuenta activada</div>
		<p>Tu cuenta ha sido activa con &eacute;xito.</p>
		<p>Dir&iacute;gete a incio para loguearte y empezar a usar la
			aplicaci&oacute;n.</p>
		<%
			} else {
		%>
		<div class="title">Cuenta ya activada</div>
		<p>
			Esta cuenta ya fue activada en
			<%=request.getAttribute("fecha")%>.
		</p>
		<p>Dir&iacute;gete a incio para loguearte.</p>
		<%
			}
		%>
		<span class="campo"><a class="but" href="/eprail/">Inicio</a></span>
	</div>
</body>
</html>