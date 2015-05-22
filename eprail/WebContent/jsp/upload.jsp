<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="funciones.tfg.aplicacion.Funciones"%>
<!DOCTYPE html>
<html>
<%
	String leng = (String) request.getSession().getAttribute("lenguage");
	if(leng == null)
	{
		Funciones.setLenaguage(request.getSession());
		leng = (String) request.getSession().getAttribute("lenguage");
	}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Aplicaci&oacute;n web</title>
<link href="/aplicacion/css/style.css" rel="stylesheet" type="text/css">
<link href="/aplicacion/css/dropzone.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/aplicacion/script/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="/aplicacion/script/center.js"></script>
<%if(leng.equals("SP")){%>
<script type="text/javascript" src="/aplicacion/script/dropzone-sp.js"></script>
<%}else{%>
<script type="text/javascript" src="/aplicacion/script/dropzone-eng.js"></script>
<%}%>
</head>
<body>
	<jsp:include page="./top.jsp" flush="true" />
	<div class="center" style="height: 500px; width: 1000px;">
		<span class="title"><%if(leng.equals("SP")){%>A&ntilde;adir proyectos<%}else{%>Add projects<%}%></span>&nbsp;&nbsp;&nbsp;&nbsp;<a
			class="subtitle" style="left:16%;" href="/aplicacion/controller/login" title="<%if(leng.equals("SP")){%>Volver<%}else{%>Back<%}%>"><img src="/aplicacion/img/back.png" alt="back" width="30" height="30"></a>
		<form action="/aplicacion/controller/upload" id="upload" class="dropzone">
			<div class="fallback">
				<input name="file" type="file" multiple />
			</div>
		</form>
		<p><%if(leng.equals("SP")){%>Arrastre o haga clic en el cuadro de arriba para a&ntilde;adir archivos. Tras completar la subida vuelva a la p&aacute;gina de incio para ver el resultado.<%}else{%>
		Drag & put or click in the window to add files. After completing the upload back to see the result.<%}%></p>
	</div>
</body>
</html>