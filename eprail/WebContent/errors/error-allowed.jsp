<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import="funciones.tfg.aplicacion.Funciones"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Error - Permissions</title>
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
<jsp:include page="../jsp/lenguage.jsp" flush="true" />
    <div class="center" style="height:165px; width:400px;">
    <%if(leng.equals("SP")){ %>
    <div class="title">Error de permisos</div>
    <p>Ocurri&oacute; un error al realizar la acci&oacute;n o no tiene permisos para realizar esta tarea.</p>
    <p>Si tiene permisos y aun as&iacute; sigue fallando p&oacute;ngase en contacto con el due&ntilde;o del proyecto.</p>
    <p>Disculpe las molestias.</p>
     <%}else{ %>
    <div class="title">Permission error</div>
    <p>An error occurred while processing the action or do not have permissions to perform this task.</p>
    <p>If you have permissions and still continues to fail, contact the project owner.</p>
    <p>Sorry for the inconvenience.</p>
     <%} %>
     <span class="campo"><a class="but" href="/aplicacion/controller/login" style="margin-right:70%;"><%if(leng.equals("SP")){ %>Volver<%}else{%>Back<%}%></a></span>
    </div>
</body>
</html>