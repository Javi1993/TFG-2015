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
	<%-- Cogemos el JavaBean del usuario de la session --%>
	<jsp:useBean id="userBean" class="javabeans.tfg.eprail.User"
		scope="session" />
	<div id="top">
		<img class="top" src="/eprail/img/off.png" alt="off" width="20"
			height="20" /><span class="top"><jsp:getProperty
				name="userBean" property="firstName" /></span><span class="top"><jsp:getProperty
				name="userBean" property="familyName" /></span> <span class="top"><a
			href="/eprail/controller/logout">Logout</a></span><span class="top"><a
			href="/eprail/jsp/account.jsp">Cuenta</a></span>
	</div>
	<div class="center2" id="proyectos">
		<span class="title">Mis proyectos</span>&nbsp;&nbsp;<img id="title2" src="/eprail/img/add.png" alt="add" width="30" height="30">
		<p>Aqu&iacute; ir&iacute;an los proyectos. Imprimir lista de
			proyectos, primero la de lso propios y luego los que te han
			compartido (ver si usar overflow o p&aacute;ginas para cuando no de
			m&aacute;s de s&iacute; la caja)</p>
	</div>



</body>
</html>