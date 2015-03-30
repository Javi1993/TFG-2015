<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List,modeldata.tfg.eprailJPA.Project,modeldata.tfg.eprailJPA.Sharing"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="/eprail/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/eprail/script/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="/eprail/script/center.js"></script>
</head>
<script>
function alertDelete (id, name) {
	if (confirm("¿Est\u00e1 seguro de borrar el proyecto "+name+"? No podr\u00e1 deshacer esta acci\u00F3n.")) {
		window.location.assign("/eprail/controller/delete?id="+id);
	}
}
</script>
<body>
	<%-- Cogemos el JavaBean del usuario de la session --%>
	<jsp:useBean id="userBean" class="modeldata.tfg.eprailJPA.User"
		scope="session" />
	<jsp:include page="./top.jsp" flush="true" />
	<div class="center" style="height:500px; width:1000px;">
		<span class="title">Mis proyectos</span>&nbsp;&nbsp;<a class="subtitle" style="left:14%;" href="/eprail/jsp/upload.jsp" title="A&ntilde;adir proyecto"><img src="/eprail/img/add.png" alt="add" width="30" height="30"></a>
		<a id="reload" href="/eprail/controller/login" title="Recargar"><img src="/eprail/img/reload.png" alt="reload" width="20" height="20"></a>
					<table class="project" style="width: 100%">
					<%
						List<Project> list = (List<Project>) request.getSession().getAttribute("projectList");
						if (list != null) {
							for (Project project : list) {
								//tabla con documentos propios
					%>
					<tr class="row">
						<td><img src="/eprail/img/thunder.png" alt="project" width="30" height="30"></td>
						<td><%=project.getProjectName()%></td>
						<td><span style="border:2px solid #800000; padding: 10px;" title="<%=project.getStatuscategory().getStatusDescription()%>">
						<%=project.getStatuscategory().getStatusName()%>
						</span></td>
						<td style="color: #C0C0C0;">
						<%
						 SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");%>
						<%=sdf.format(project.getDateModified())%>
						</td>
						<td><a href="/eprail/controller/see?id=<%=project.getIdProject()%>" title="Ver"><img src="/eprail/img/eye.png" alt="see"></a></td>
						<td><a href="/eprail/controller/run?id=<%=project.getIdProject()%>" title="Simular"><img src="/eprail/img/gear.png" alt="run"></a></td>
						<td><a href="/eprail/controller/download?id=<%=project.getIdProject()%>" title="Descargar"><img src="/eprail/img/download.png" alt="download"></a></td>
						<td><a href="/eprail/controller/share?op=2&id=<%=project.getIdProject()%>&n=<%=project.getProjectName() %>" title="Compartir"><img src="/eprail/img/share.png" alt="share"></a></td>
						<td><a onclick="alertDelete(<%=project.getIdProject()%>, '<%=project.getProjectName()%>');" title="Borrar" style="cursor: pointer;"><img src="/eprail/img/delete.png" alt="delete"></a></td>
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
						List<Sharing> listSh = (List<Sharing>) request.getSession().getAttribute("projectListShared");
						if (listSh != null) {
							for (Sharing project : listSh) {
								//tabla con documentos propios
					%>
					<tr class="row">
						<td><img src="/eprail/img/thunder-share.png" alt="shared-project" width="30" height="30"></td>
						<td><%=project.getProject().getProjectName()%></td>
						<td style="color: #C0C0C0;">(<%=project.getUser2().getFirstName() %> <%=project.getUser2().getFamilyName() %>)</td>
						<td><span style="border:2px solid #800000; padding: 10px;" title="<%=project.getProject().getStatuscategory().getStatusDescription()%>">
						<%=project.getProject().getStatuscategory().getStatusName()%>
						</span></td>
						<td style="color: #C0C0C0;">
						<%
						 SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");%>
						<%=sdf.format(project.getProject().getDateModified()) %>
						</td>
						<td><a href="#" title="Ver"><img src="/eprail/img/eye.png" alt="see"></a></td>
						<td><%if(project.getAllowRecalculate()!=0){ %><a href="/eprail/controller/run?id=<%=project.getProject().getIdProject()%>" title="Simular"><img src="/eprail/img/gear.png" alt="run"></a><%}else{ %><img src="/eprail/img/gear-d.png" alt="run"><%} %></td>
						<td><%if(project.getAllowDownload()!=0){ %><a href="/eprail/controller/download?id=<%=project.getProject().getIdProject()%>" title="Descargar"><img src="/eprail/img/download.png" alt="download"></a><%}else{ %><img src="/eprail/img/download-d.png" alt="download"><%} %></td>
						<td><%if(project.getAllowShare()!=0){ %><a href="/eprail/controller/share?op=2&id=<%=project.getProject().getIdProject()%>&n=<%=project.getProject().getProjectName() %>" title="Compartir"><img src="/eprail/img/share.png" alt="share"></a><%}else{ %><img src="/eprail/img/share-d.png" alt="share"><%} %></td>
						<td><%if(project.getAllowDelete()!=0){ %><a href="/eprail/controller/delete?id=<%=project.getProject().getIdProject()%>" title="Borrar"><img src="/eprail/img/delete.png" alt="delete"></a><%}else{ %><img src="/eprail/img/delete-d.png" alt="share"><%} %></td>
					</tr>
					<%
							}
						}
					%>
					</table>
	</div>
</body>
</html>