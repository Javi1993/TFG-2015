<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="funciones.tfg.eprail.Funciones"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Eprail: repositorio de casos</title>
<link href="/eprail/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/eprail/script/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="/eprail/script/form.js"></script>
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
    <div class="title"><%if(leng.equals("SP")){ %>Formulario de registro<%}else{%>Sign up form<%}%></div>
        <form action="/eprail/register" method="post" name="recuperar">
        	<fieldset>
            <span class="campo"><label class="log-i">E-mail</label><input class="reg-i" type="email" name="email" maxlength="60" minlength="3" required="required" /></span><br>
            <span class="campo"><label class="log-i"><%if(leng.equals("SP")){ %>Nombre<%}else{%>Name<%}%></label><input class="reg-i" type="text" name="nombre" maxlength="60" minlength="3" required="required" /></span><br>
            <span class="campo"><label class="log-i"><%if(leng.equals("SP")){ %>Apellidos<%}else{%>Last name<%}%></label><input class="reg-i" type="text" name="apellidos" maxlength="80" minlength="3" required="required" /></span><br>
            <span class="campo"><label class="log-i"><%if(leng.equals("SP")){ %>Contrase&ntilde;a<%}else{%>Password<%}%></label><input class="reg-i" type="password" name="pass" minlength="6" required="required"/></span><br>
            <span class="campo"><a class="but" href="/eprail/" style="margin-right:70%;"><%if(leng.equals("SP")){ %>Volver<%}else{%>Back<%}%></a><input type="submit" value="<%if(leng.equals("SP")){ %>Registrar<%}else{%>Sign up<%}%>" /></span>
            </fieldset>
        </form>
    </div>
</body>
</html>