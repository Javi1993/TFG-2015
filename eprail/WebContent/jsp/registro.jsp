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
	<div class="center" style="height:200px; width:400px;">
		<div class="title">Muchas gracias por registrarte</div>
		<p>
			En breve recibir&aacute;s un correo electr&oacute;cnico en la
			direcci&oacute;n
			<%=request.getParameter("email") %>
			con las instrucciones para completar el proceso de registro.
		</p>
		<p>Si tienes cualquier problema con el registro, puedes escribirme
			a eduardo.pilo@eprail.com</p>
		<br><br> <span class="campo"><a class="but" href="/eprail/">Inicio</a></span>
	</div>
</body>
</html>