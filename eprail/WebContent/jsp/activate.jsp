<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="/eprail/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%if((Boolean)request.getAttribute("active")){ %>
	<div class="center" id="activ">
		<div class="title">Cuenta activada</div>
		<p>Tu cuenta ha sido activa con &eacute;xito.</p>
		<p>Dir&iacute;gete a incio para loguearte y empezar a usar la
			aplicaci&oacute;n.</p>
		<span class="campo"><a class="but" href="/eprail/">Inicio</a></span>
	</div>
	<%}else{ %>
	<div class="center" id="activ">
		<div class="title">Cuenta ya activada</div>
		<p>
			Esta cuenta ya fue activada en
			<%=request.getAttribute("fecha") %>.
		</p>
		<p>Dir&iacute;gete a incio para loguearte.</p>
		<span class="campo"><a class="but" href="/eprail/">Inicio</a></span>
	</div>
	<%} %>
</body>
</html>