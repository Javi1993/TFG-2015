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
	<%-- Cogemos el JavaBean del usuario de la session --%>
	<jsp:useBean id="userBean" class="javabeans.tfg.eprail.User"
		scope="session" />
	<jsp:include page="./top.jsp" flush="true" />
	<div class="center" style="height:500px; width:1000px;">
		<span class="title">Mis proyectos</span>&nbsp;&nbsp;<a class="subtitle" style="left:14%;" href="/eprail/jsp/upload.jsp"><img src="/eprail/img/add.png" alt="add" width="30" height="30"></a>
		<p>	Aqu&iacute; ir&iacute;an los proyectos. Imprimir lista de
			proyectos, primero la de lso propios y luego los que te han
			compartido (ver si usar overflow o p&aacute;ginas para cuando no de
			m&aacute;s de s&iacute; la caja)</p>
	</div>
</body>
</html>