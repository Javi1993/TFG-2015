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
	<div class="center" id="recup">
	<div class="title">Recuperaci&oacute;n de contrase&ntilde;a</div>
		<%
			if ((Boolean) request.getAttribute("user")) {
		%>
		
		<p>Se ha enviado un correo a <%=request.getParameter("email")%>.</p>
		<p>Sigue las intruncciones indicadas en él para restablecer tu contrase&ntilde;a.</p>
		<%
			} else {
		%>
		<p>
			El email <%=request.getParameter("email")%> no se encuentra en nuestra base de datos.
		</p>
		<p>Comprueba que los datos introducidos son correctos.</p>
		<%
			}
		%>
		<span class="campo"><a class="but" href="/eprail/">Inicio</a></span>
	</div>
</body>
</html>