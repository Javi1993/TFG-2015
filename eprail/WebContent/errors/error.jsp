<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import="funciones.tfg.aplicacion.Funciones"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Error</title>
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
   <%if(leng != null && leng.equals("SP")){ %>
    <div class="title">Ha ocurrido un error</div>
    <p>Lo sentimos, ha ocurrido un error al procesar su petici&oacute;n. Vuelva a intentarlo</p>
    <p>Si el error persiste p&oacute;ngase en contacto con el administrador de la p&aacute;gina.</p>
    <p>Disculpe las molestias.</p>
    <%}else{ %>
    <div class="title">An error occurred</div>
    <p>An error occurred while processing the action. Try again.</p>
    <p>If the error persists contact the webmaster.</p>
    <p>Sorry for the inconvenience.</p>
    <%} %>
     <span class="campo"><a class="but" href="/aplicacion/controller/login" style="margin-right:70%;"><%if(leng != null && leng.equals("SP")){%>Volver<%}else{%>Back<%}%></a></span>
    </div>
</body>
</html>