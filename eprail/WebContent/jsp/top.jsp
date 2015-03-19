<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/eprail/script/jquery.qtip.min.js"></script>
<script type="text/javascript" src="/eprail/script/messages.js"></script>
<link href="/eprail/css/jquery.qtip.min.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%-- Cogemos el JavaBean del usuario de la session --%>
	<jsp:useBean id="userBean" class="modeldata.tfg.eprail.User"
		scope="session" />
	<div id="top" style="background-color: #FFF;">
		<img class="top" src="/eprail/img/off.png" alt="off" width="20"
			height="20" title="El simulador OlgaNG no est&aacute; operativo"><span class="top"><jsp:getProperty
				name="userBean" property="firstName" /></span><span class="top"><jsp:getProperty
				name="userBean" property="familyName" /></span> <span class="top"><a
			href="/eprail/controller/logout">Logout</a></span><span class="top"><a
			href="/eprail/jsp/account.jsp">Cuenta</a></span>
	</div>
</body>
</html>