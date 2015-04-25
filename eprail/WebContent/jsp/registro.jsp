<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="funciones.tfg.eprail.Funciones"%>
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
	String leng = (String) request.getSession().getAttribute("lenguage");
	if(leng == null)
	{
		Funciones.setLenaguage(request.getSession());
		leng = (String) request.getSession().getAttribute("lenguage");
	}
%>
<jsp:include page="./lenguage.jsp" flush="true" />
	<div class="center" style="height:200px; width:400px;">
		<div class="title"><%if(leng.equals("SP")){%>Muchas gracias por registrarte<%}else{%>Thank you very much for sign up.<%}%></div>
		<%if(leng.equals("SP")){%>
		<p>En breve recibir&aacute;s un correo electr&oacute;nico en la
			direcci&oacute;n <%=request.getParameter("email") %> con las instrucciones para completar el proceso de registro.</p>
		<p>Si tienes cualquier problema con el registro, puedes escribir a 100290698@alumnos.uc3m.es</p>
		<%}else{ %>
		<p>In brief you will receive an email at the address <%=request.getParameter("email") %> with instructions to complete the sign up process.</p>
		<p>If you have any problems with register, you can write me to 100290698@alumnos.uc3m.es</p>
		<%} %>
		<br><br> <span class="campo"><a class="but" href="/eprail/"><%if(leng.equals("SP")){%>Inicio<%}else{%>Start<%}%></a></span>
	</div>
</body>
</html>