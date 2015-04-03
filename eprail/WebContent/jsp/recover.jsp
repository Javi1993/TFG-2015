<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Locale"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/eprail/script/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="/eprail/script/center.js"></script>
<link href="/eprail/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<%
	String leng = (String) request.getSession().getAttribute("lenguage");
	if(leng == null)
	{
		switch (Locale.getDefault().getDisplayLanguage()) {//obtenemos el idioma del usuario
		case "spanish":
			request.getSession().setAttribute("lenguage", "SP");
			break;
		case "español":
			request.getSession().setAttribute("lenguage", "SP");
			break;
		default:
			request.getSession().setAttribute("lenguage", "EN");	
		}		
		leng = (String) request.getSession().getAttribute("lenguage");
	}
%>
	<jsp:include page="./lenguage.jsp" flush="true" />
	<div class="center" style="height:155px; width:400px;">
		<div class="title"><%if(leng.equals("SP")){ %>Restablecer contrase&ntilde;a<%}else{%>Restore password<%} %></div>
		<%
			if ((Boolean) request.getAttribute("valido")) {
		%>
		<p><%if(leng.equals("SP")){ %>A continuaci&oacute;n rellene los siguientes campos con su
			nueva contrase&ntilde;a.<%}else{ %>Complete the following fields with your new password.<%} %></p>
		<form action="/eprail/recover?cd=1" method="post">
			<fieldset>
				<input type="hidden" name="uid"
					value="<%=request.getParameter("op")%>"><span
					class="campo"><label class="log-i"><%if(leng.equals("SP")){ %>Contrase&ntilde;a<%}else{ %>Password<%} %></label><input
					type="password" id="pass" name="pass" minlength="6" required /></span><br> <span
					class="campo"><label class="log-i"><%if(leng.equals("SP")){ %>Repetir
						contrase&ntilde;a<%}else{ %>Repeat password<%} %></label><input type="password" id="newpass" minlength="6" required /></span><br>
				<span class="campo"><input id="edit-s" type="submit"
					value="<%if(leng.equals("SP")){ %>Guardar<%}else{ %>Save<%} %>" /></span>
			</fieldset>
		</form>
		<%
			} else {
		%>
		<p>There was a problem. Re-enter the email link or repeat the process.</p>
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
				idioma = "<%=leng%>";
				if(idioma =="SP"){
					alert("Las contrase\u00f1a no coinciden");
				}else{
					alert("Passwords don't match");
				}
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