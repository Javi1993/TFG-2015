<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Login - Error</title>
<link href="/eprail/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/eprail/script/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="/eprail/script/center.js"></script>
</head>
<body>
<%
	String leng = (String) request.getSession().getAttribute("lenguage");
%>
<jsp:include page="../jsp/lenguage.jsp" flush="true" />
    <div class="center" style="height:165px; width:400px;">
    <div class="title"><%if(leng.equals("SP")){%>Error durante login<%}else{%>Error while login<%}%></div>
    <%if(leng.equals("SP")){%>
    <p>El email y/o contrase&ntilde;a no coinciden o la cuenta no fue activada.</p>
    <p>Comprueba que has introducido bien los datos. Si no has activado tu cuenta revisa la bandeja del correo.</p>
    <p>Si olvidastes tu contrase&ntilde;a reseteala <a href="/eprail/jsp/forgotten.jsp">aqu&iacute;</a>.</p>
    <%}else{%>
    <p>The email and/or password do not match or your account was not activated yet.</p>
    <p>Verify that you entered data well. If you haven't activated your account yet check your inbox.</p>
    <p>If you forgot your password you can reset it <a href="/eprail/jsp/forgotten.jsp">here</a>.</p>
    <%}%>
     <span class="campo"><a class="but" href="/eprail/" style="margin-right:70%;"><%if(leng.equals("SP")){%>Volver<%}else{%>Back<%}%></a></span>
    </div>
</body>
</html>