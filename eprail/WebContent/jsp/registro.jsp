<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="/eprail/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="center" id="reg">
		<div class="title">Muchas gracias por registrarte</div>
		<p>
			En breve recibir&aacute;s un correo electr&oacute;cnico en la
			direcci&oacute;n
			<%=request.getParameter("email") %>
			con las instrucciones para completar el proceso de registro.
		</p>
		<p>Si tienes cualquier problema con el registro, puedes escribirme
			a eduardo.pilo@eprail.com</p>
		<br> <span class="campo"><a class="but" href="/eprail/">Inicio</a></span>
	</div>
</body>
</html>