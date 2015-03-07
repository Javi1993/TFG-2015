<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.*,javabeans.tfg.eprail.Sharings"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="/eprail/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/eprail/script/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="/eprail/script/center.js"></script>
<script type="text/javascript" src="/eprail/script/allowed.js"></script>
</head>
<body>
	<%-- Cogemos el JavaBean del usuario de la session --%>
	<jsp:useBean id="userBean" class="javabeans.tfg.eprail.User"
		scope="session" />
	<jsp:include page="./top.jsp" flush="true" />
	<div class="center" style="height:500px; width:1000px;">
		<span class="title">Compartir proyecto <%=request.getParameter("n") %></span>&nbsp;&nbsp;<a class="subtitle" style="left:28%;" href="/eprail/controller/login" title="Volver"><img src="/eprail/img/back.png" alt="back" width="30"
			height="30"></a>
		<p>Introduce el e-mail del usuario registrado con el que quieres compartir el proyecto</p>
		<form action="/eprail/controller/share?op=1&id=<%=request.getParameter("id") %>&n=<%=request.getParameter("n") %>" method="post" name="add-sh">
			<input type="email" name="email" maxlength="60" size="50"/>&nbsp;<input type="image" src="/eprail/img/add.png" width="20" height="20" alt="add" title="A&ntilde;adir">
		</form>
		<br><br><br><br>
		<form action="/eprail/controller/share?op=2" method="post" name="recuperar">
			<fieldset>
			<table class="project" style="width: 100%">
					<tr>
						<th>Usuario</th>
						<th>Email</th>
						<th><img src="/eprail/img/eye.png" alt="see"><br>Ver</th>
						<th><img src="/eprail/img/gear.png" alt="run"><br>Recalcular</th>
						<th><img src="/eprail/img/download.png" alt="download"><br>Descargar</th>
						<th><img src="/eprail/img/share.png" alt="share"><br>Compartir</th>
						<th><img src="/eprail/img/delete.png" alt="delete"><br>Borrar</th>
						<th class="delete">&nbsp;&nbsp;</th>
					</tr>

					<%
					List<Sharings> list = (List<Sharings>) request.getSession().getAttribute("userSharedList");
					int cnt=0;	
					if (list != null) {
						for (Sharings sh : list) {
						//tabla con usuarios con acceso a ese documento
					%>
					<tr <%if(cnt%2==0){ %>class="alt"<%} %>>
						<td><%=sh.getUid().getFirstName() %></td>
						<td><%=sh.getUid().getEmail() %></td>
						<td><input type="checkbox" checked="checked" disabled="disabled"></td>
						<td><input type="checkbox" name="<%=sh.getUid()+"R"%>" <%if(sh.isAllowRecalculated()!=0){ %>checked="checked"<%} %>></td>
						<td><input type="checkbox" name="<%="dow"+sh.getUid()%>" <%if(sh.isAllowDownload()!=0){ %>checked="checked"<%} %>></td>
						<td><input type="checkbox" name="<%="sha"+sh.getUid()%>" <%if(sh.isAllowShare()!=0){ %>checked="checked"<%} %>></td>
						<td><input type="checkbox" name="<%="del"+sh.getUid()%>" <%if(sh.isAllowDelete()!=0){ %>checked="checked"<%} %>></td>
						<td class="delete"><a href="/eprail/controller/share?op=1&u=<%=sh.getIdSharing()%>&p=<%=sh.getIdProject()%>"></a></td>
					</tr>
					<%
						cnt++;
							}
					%>
					<caption>ACCIONES AUTORIZADAS</caption>
					<%
						}else{
					%>
					<caption>ARCHIVO NO COMPARTIDO</caption>
					<%} %>
					</table>
				<input type="submit" value="Guardar" id="edit-s" disabled="disabled"/>
			</fieldset>
		</form>
	</div>
</body>
</html>