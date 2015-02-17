<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="/eprail/css/style.css" rel="stylesheet" type="text/css">
<link href="/eprail/css/dropzone.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/eprail/script/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="/eprail/script/center.js"></script>
<script type="text/javascript" src="/eprail/script/dropzone.js"></script>
</head>
<body>
	<jsp:include page="./top.jsp" flush="true" />
	<div class="center" style="height: 500px; width: 1000px;">
		<span class="title">A&ntilde;adir proyectos</span>&nbsp;&nbsp;&nbsp;&nbsp;<a
			class="subtitle" style="left:16%;" href="/eprail/jsp/inicio.jsp"><img src="/eprail/img/back.png" alt="back" width="30" height="30"></a>
		<form action="/eprail/upload" class="dropzone">
			<div class="fallback">
				<input name="file" type="file" multiple />
			</div>
		</form>
		<p>Arrastre o haga clic en el cuadro de arriba para a&ntilde;adir archivos. Tras completar la subida vuelva a la p&aacute;gina de incio para ver el resultado.</p>
	</div>
</body>
</html>