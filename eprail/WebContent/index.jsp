<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="java.util.Locale"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Pagina de inicio</title>
<link href="/eprail/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/eprail/script/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="/eprail/script/center.js"></script>
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
<jsp:include page="./jsp/lenguage.jsp" flush="true" />
<div class="center" style="height:130px; width:400px;">
	<form method="post" action="/eprail/controller/login" name="login">
    <fieldset>
    	<span class="campo">
        	<label class="log-i">E&#45;mail</label><input type="email" name="email" maxlength="60" required="required" />
        </span>
        <br>
        <span class="campo">
        	<label class="log-i"><%if(leng.equals("SP")){ %>Contrase&ntilde;a<%}else{ %>Password<%} %></label><input  type="password" name="pass" required="required" />
        	<br><a id="remem" href="/eprail/html/recuperar.html"><%if(leng.equals("SP")){ %>&iquest;No recuerdas t&uacute; contrase&ntilde;a?<%}else{ %>Forgotten your password?<%} %></a>
        </span>
		<br><br>
        <span class="campo"><a class="but" href="/eprail/html/registro.html" style="width:70%;"><%if(leng.equals("SP")){ %>Registrarse<%}else{ %>Sign In<%} %></a><input type="submit" value="Login" /></span>
    </fieldset>
    </form>
</div>
</body>
</html>