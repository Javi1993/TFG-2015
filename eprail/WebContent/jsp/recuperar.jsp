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
	<div class="center" style="height:155px; width:400px;">
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