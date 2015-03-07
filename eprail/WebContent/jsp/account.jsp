<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/eprail/script/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="/eprail/script/form.js"></script>
<script type="text/javascript" src="/eprail/script/center.js"></script>
<link href="/eprail/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%-- Cogemos el JavaBean del usuario de la session --%>
	<jsp:useBean id="userBean" class="modeldata.tfg.eprail.User"
		scope="session" />
	<jsp:include page="./top.jsp" flush="true" />
	<div class="center" style="height:230px; width:400px;">
		<span class="title">Modificar datos de registro</span>&nbsp;&nbsp;<a class="subtitle" style="left:45%;" href="/eprail/controller/login" title="Volver"><img src="/eprail/img/back.png" alt="back" width="30"
			height="30"></a>
		<form action="/eprail/controller/account" method="post" name="edit" id="edit-f">
			<fieldset>
				<span class="campo"> 
					<label class="log-i">E&#45;mail</label>
						<input type="email" name="email" maxlength="60" value="<jsp:getProperty name="userBean" property="email" />" disabled="disabled" />
				</span><br>
				<span class="campo">
					<label class="log-i">Nombre</label>
						<input type="text" name="name" maxlength="60" value="<jsp:getProperty name="userBean" property="firstName" />" required="required" />
				</span><br>
				<span class="campo">
					<label class="log-i">Apellidos</label>
						<input type="text" name="apellidos" maxlength="80" value="<jsp:getProperty name="userBean" property="familyName" />" required="required" />
				</span><br>
				<span class="campo">
					<label class="log-i">Contrase&ntilde;a</label>
						<input id="pass" type="password" name="pass"/>
				</span> <br>
				<span id="confirm" class="campo" style="display:none;">
					<label class="log-i">Repetir contrase&ntilde;a</label>
					<input id="newpass" class="reg-i" name="newpass" type="password"/>
				<br></span> 
				<br> <span class="campo"><input id="edit-s" type="submit"
					value="Guardar" /></span>
			</fieldset>
		</form>
	</div>
</body>
</html>