<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/aplicacion/script/jquery.dd.min.js"></script>
<link href="/aplicacion/css/dd.css" rel="stylesheet" type="text/css">
</head>
<body>
	<span id="idiom" style="background-color: #FFF;">
		<select id="lenguage" name="lenguage" style="width: 120px; font-size: 12px;">
			<%if(request.getSession(false).getAttribute("lenguage")!=null && request.getSession().getAttribute("lenguage").equals("SP")){ %>
				<option value='SP' data-image="/aplicacion/img/es.png" selected>Espa&ntilde;ol</option>
				<option value='EN' data-image="/aplicacion/img/uk.png">English</option>
			<%}else{ %>
				<option value='SP' data-image="/aplicacion/img/es.png">Espa&ntilde;ol</option>
				<option value='EN' data-image="/aplicacion/img/uk.png" selected>English</option>
			<%} %>
		</select>
	</span>
<script type="text/javascript">
$(document).ready(function(e) {		
	$("select#lenguage").msDropdown({roundedBorder:false});
	
	$("#lenguage").change(function() {
		window.location.assign("/aplicacion/lenguage?ln="+$("#lenguage").val());
	});
});
</script>
</body>
</html>