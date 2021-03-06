<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="funciones.tfg.aplicacion.Funciones"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Aplicaci&oacute;n web</title>
<link href="/aplicacion/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/aplicacion/script/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="/aplicacion/script/center.js"></script>
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
<jsp:include page="./jsp/lenguage.jsp" flush="true" />
    <div class="center" style="height:155px; width:400px;">
    <div class="title"><%if(leng.equals("SP")){ %>Recuperaci&oacute;n de contraseña<%}else{%>Recover password<%}%></div>
    <p><%if(leng.equals("SP")){ %>Por favor, introduce tu direcci&oacute;n de e-mail. En breve, recibir&aacute;s un 
    mensaje con las instrucciones necesarias para restablecer tu contrase&ntilde;a.<%}else{ %>Please enter your email address. In brief you will receive a message with instructions to reset your password.<%} %></p>
        <form action="/aplicacion/recover?cd=0" method="post" name="recuperar">
        	<fieldset>
            <span class="campo"><label class="log-i">E-mail</label><input type="email" name="email" maxlength="60" required="required" /></span><br>
            <span class="campo"><a class="but" href="/aplicacion/" style="margin-right:45%;"><%if(leng.equals("SP")){ %>Volver<%}else{%>Back<%} %></a><input type="submit" value="<%if(leng.equals("SP")){ %>Restablecer contrase&ntilde;a<%}else{%>Reset password<%}%>" /></span>
            </fieldset>
        </form>
    </div>
</body>
</html>