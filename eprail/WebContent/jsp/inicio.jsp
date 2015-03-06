<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*,javabeans.tfg.eprail.Project"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="/eprail/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/eprail/script/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="/eprail/script/center.js"></script>
</head>
<body>
	<%-- Cogemos el JavaBean del usuario de la session --%>
	<jsp:useBean id="userBean" class="javabeans.tfg.eprail.User"
		scope="session" />
	<jsp:include page="./top.jsp" flush="true" />
	<div class="center" style="height:500px; width:1000px;">
		<span class="title">Mis proyectos</span>&nbsp;&nbsp;<a class="subtitle" style="left:14%;" href="/eprail/jsp/upload.jsp" title="A&ntilde;adir proyecto"><img src="/eprail/img/add.png" alt="add" width="30" height="30"></a>
					<table class="project" style="width: 100%">
					<%
						List<Project> list = (List<Project>) request.getSession().getAttribute("projectList");
						if (list != null) {
							for (Project project : list) {
								//tabla con documentos propios
					%>
					<tr class="row">
						<td><img src="/eprail/img/thunder.png" alt="project" width="30" height="30"></td>
						<td><%=project.getProjectName().replaceAll(".ongf", "") %></td>
						<td><span style="border:2px solid #800000; padding: 10px;" title="<%=project.getIdProjectStatus().getStatusDescription()%>">
						<%=project.getIdProjectStatus().getStatusName() %>
						</span></td>
						<td>
						<%
						 SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");%>
						<%=sdf.format(project.getDateModifed()) %>
						</td>
						<td><img src="/eprail/img/eye.png" alt="see"></td>
						<td><img src="/eprail/img/gear.png" alt="run"></td>
						<td><a href="/eprail/controller/download?id=<%=project.getIdProject()%>" title="Descargar"><img src="/eprail/img/download.png" alt="download"></a></td>
						<td><a href="/eprail/controller/share?op=2&id=<%=project.getIdProject()%>&n=<%=project.getProjectName().replaceAll(".ongf", "") %>" title="Compartir"><img src="/eprail/img/share.png" alt="share"></a></td>
						<td><a href="/eprail/controller/delete?id=<%=project.getIdProject()%>" title="Borrar"><img src="/eprail/img/delete.png" alt="delete"></a></td>
					</tr>
					<%
							}
						}else{
					%>
					<p>No hay ning&uacute;n proyecto propio.</p>
					<%} %>
					</table>
					<table class="project" style="width: 100%">
					<%
						List<Project> listSh = (List<Project>) request.getSession().getAttribute("projectListShared");
						if (listSh != null) {
							for (Project project : listSh) {
								//tabla con documentos propios
					%>
					<tr class="row">
						<td><img src="/eprail/img/thunder-share.png" alt="shared-project" width="30" height="30"></td>
						<td><%=project.getProjectName().replaceAll(".ongf", "") %></td>
						<td><span style="border:2px solid #800000; padding: 10px;" title="<%=project.getIdProjectStatus().getStatusDescription()%>">
						<%=project.getIdProjectStatus().getStatusName() %>
						</span></td>
						<td>
						<%
						 SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");%>
						<%=sdf.format(project.getDateModifed()) %>
						</td>
						<td><img src="/eprail/img/eye.png" alt="see"></td>
						<td><img src="/eprail/img/gear.png" alt="run"></td>
						<td><a href="/eprail/controller/download?id=<%=project.getIdProject()%>" title="Descargar"><img src="/eprail/img/download.png" alt="download"></a></td>
						<td><a href="/eprail/controller/jsp/shared.jsp" title="Compartir"><img src="/eprail/img/share.png" alt="share"></a></td>
						<td><a href="/eprail/controller/delete?id=<%=project.getIdProject()%>" title="Borrar"><img src="/eprail/img/delete.png" alt="delete"></a></td>
					</tr>
					<%
							}
						}
					%>
					</table>
	</div>
</body>
</html>