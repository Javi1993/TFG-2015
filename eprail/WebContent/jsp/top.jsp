<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/eprail/script/jquery.qtip.min.js"></script>
<script type="text/javascript" src="/eprail/script/messages.js"></script>
<link href="/eprail/css/jquery.qtip.min.css" rel="stylesheet" type="text/css">
</head>
<body>
<%
	String olga = (String) request.getSession().getAttribute("olga");
	if(olga == null)
	{
		olga = "off";
	}
%>
	<jsp:include page="./lenguage.jsp" flush="true" />
	<%-- Cogemos el JavaBean del usuario de la session --%>
	<jsp:useBean id="userBean" class="modeldata.tfg.eprailJPA.User"
		scope="session" />
	<span id="top">
		<%if(olga.equals("on")){ %>
		<img class="top" src="/eprail/img/on.png" alt="on" width="20"
			height="20" title="<%if(request.getSession().getAttribute("lenguage").equals("SP")){ %>El servidor de simulaciones est&aacute; operativo<%}else{%>Simulations server is operative<%}%>">
		<%}else{ %><img class="top" src="/eprail/img/off.png" alt="off" width="20"
			height="20" title="<%if(request.getSession().getAttribute("lenguage").equals("SP")){ %>El servidor de simulaciones no est&aacute; operativo<%}else{%>Simulations server is inoperative<%}%>"><%} %><span class="top"><jsp:getProperty
				name="userBean" property="firstName" /></span><span class="top"><jsp:getProperty
				name="userBean" property="familyName" /></span> <span class="top"><a
			href="/eprail/controller/logout" id="logout">Logout</a></span><span class="top"><a
			href="/eprail/controller/jsp/account.jsp"><%if(request.getSession().getAttribute("lenguage").equals("SP")){%>Cuenta<%}else{ %>Account<%} %></a></span>
	</span>
<div class="modal"></div>
<script>
$('#logout').click(function() {
    $('.modal').show();
});
</script>
</body>
</html>