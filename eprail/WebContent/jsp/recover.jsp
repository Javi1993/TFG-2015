<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="/eprail/script/jquery-2.1.3.min.js"></script>
<link href="/eprail/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="center" id="recup">
		<div class="title">Restablecer contrase&ntilde;a</div>
		<%
			if ((Boolean) request.getAttribute("valido")) {
		%>
		<p>A continuaci&oacute;n rellene los siguientes campos con su
			nueva contrase&ntilde;a.</p>
		<form action="/eprail/recover?cd=1" method="post">
			<fieldset>
				<input type="hidden" name="uid"
					value="<%=request.getParameter("op")%>"><span
					class="campo"><label class="log-i">Contrase&ntilde;a</label><input
					type="password" id="pass" name="pass" required /></span><br> <span
					class="campo"><label class="log-i">Repetir
						contrase&ntilde;a</label><input type="password" id="newpass" required /></span><br>
				<span class="campo"><input id="edit-s" type="submit"
					value="Guardar" /></span>
			</fieldset>
		</form>
		<%
			} else {
		%>
		<p>Hubo un problema, vuelva a acceder a la url que se le mando al
			correo.</p>
		<%
			}
		%>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		function comparePass() {
			if ($("#pass").val() != $("#newpass").val()) {
				$("#edit-s").attr("disabled", "disabled");
				alert("Las contrase\u00f1a no coinciden");
			} else {
				$("#edit-s").removeAttr("disabled");
			}
		}

		$("#pass").change(function() {
			if ($("#pass").val() != "" && $("#newpass").val() != "") {
				comparePass();
			}
		});

		$("#newpass").change(function() {
			if ($("#pass").val() != "" && $("#newpass").val() != "") {
				comparePass();
			}
		});
	});
</script>
</html>