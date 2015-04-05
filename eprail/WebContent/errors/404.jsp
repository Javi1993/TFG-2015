<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import="funciones.tfg.eprail.Funciones"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>404 - Not found</title>
<link href="/eprail/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/eprail/script/jquery-2.1.3.min.js"></script>
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
<jsp:include page="../jsp/lenguage.jsp" flush="true" />
    <div class="center" style="height:165px; width:400px;">
    <div class="title">404 - Not found</div>
    <%if(leng.equals("SP")){ %>
    <p>La p&aacute;gina solicitada no existe o no se encuentra disponible en estos momentos.</p>
    <p>Si el error persiste p&oacute;ngase en contacto con el administrador de la p&aacute;gina.</p>
    <p>Disculpe las molestias.</p>
    <%}else{%>
    <p>The page that you requested does not exist or is not available right now.</p>
    <p>If the error persists contact the webmaster.</p>
    <p>Sorry for the inconvenience.</p>
    <%}%>
     <span class="campo"><a class="but" href="/eprail/controller/login" style="margin-right:50%;"><%if(leng.equals("SP")){ %>Volver<%}else{%>Back<%}%></a></span>
    </div>
</body>
</html>