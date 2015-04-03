<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/eprail/script/jquery.dd.min.js"></script>
<link href="/eprail/css/dd.css" rel="stylesheet" type="text/css">
</head>
<body>
	<span id="idiom" style="background-color: #FFF;">
		<select id="lenguage" name="lenguage" style="width: 120px; font-size: 12px;">
			<%if(request.getSession(false).getAttribute("lenguage")!=null && request.getSession().getAttribute("lenguage").equals("SP")){ %>
				<option value='SP' data-image="/eprail/img/es.png" selected>Espa&ntilde;ol</option>
				<option value='EN' data-image="/eprail/img/uk.png">English</option>
			<%}else{ %>
				<option value='SP' data-image="/eprail/img/es.png">Espa&ntilde;ol</option>
				<option value='EN' data-image="/eprail/img/uk.png" selected>English</option>
			<%} %>
		</select>
	</span>
<script type="text/javascript">
$(document).ready(function(e) {		
	$("select#lenguage").msDropdown({roundedBorder:false});
	
	$("#lenguage").change(function() {
		window.location.assign("/eprail/lenguage?ln="+$("#lenguage").val());
	});
});
</script>
</body>
</html>