<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat, java.util.List,modeldata.tfg.eprailJPA.Project,modeldata.tfg.eprailJPA.Sharing"%>
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
function alertDelete (id, name, sh) {
	if (confirm("¿Est\u00e1 seguro de borrar el proyecto "+name+"? No podr\u00e1 deshacer esta acci\u00F3n.")) {
		window.location.assign("/eprail/controller/delete?id="+id+"&sh="+sh);
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
					<%//tabla con documentos propios
						List<Project> list = (List<Project>) request.getSession().getAttribute("projectList");
						if (list != null) {
							for (Project project : list) {
								boolean botones = false;
								String style = "";
								switch(project.getStatuscategory().getIdProjectStatus())
								{
								case 0:
									style = "border:2px solid #800000; padding: 10px; color:#800000; background-color:#DB9595;";
									botones = false;
									break;
								case 1:
									style = "border:2px solid #800000; padding: 10px; color: #800000; background-color: #EEDBDD;";
									botones = false;
									break;
								case 2:
									style = "border:2px solid #800000; padding: 10px;";
									botones = true;
									break;
								default:
									style = "border:2px solid #FFF; padding: 10px; color: #FFF; background-color: #800000;";
									botones = false;
								}			
					%>
					<tr class="row">
						<td><img src="/eprail/img/thunder.png" alt="project" width="30" height="30"></td>
						<td><%=project.getProjectName()%></td>
						<td><span style="<%=style %>" title="<%=project.getStatuscategory().getStatusDescription()%>">
						<%=project.getStatuscategory().getStatusName()%>
						</span></td>
						<td style="color: #C0C0C0;">
						<%
						 SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");%>
						<%=sdf.format(project.getDateModified())%>
						</td>
						<%if(botones){ %>
						<td><a href="/eprail/controller/see?id=<%=project.getIdProject()%>" title="Ver"><img src="/eprail/img/eye.png" alt="see"></a></td>
						<td><a href="/eprail/controller/run?id=<%=project.getIdProject()%>" title="Simular"><img src="/eprail/img/gear.png" alt="run"></a></td>
						<td><a href="/eprail/controller/download?id=<%=project.getIdProject()%>&sh=0" title="Descargar"><img src="/eprail/img/download.png" alt="download"></a></td>
						<td><a href="/eprail/controller/share?op=2&id=<%=project.getIdProject()%>&n=<%=project.getProjectName() %>" title="Compartir"><img src="/eprail/img/share.png" alt="share"></a></td>
						<%}else{ %>
						<td><img src="/eprail/img/eye-d.png" alt="see"></td>
						<td><img src="/eprail/img/gear-d.png" alt="run"></td>
						<td><img src="/eprail/img/download-d.png" alt="download"></td>
						<td><img src="/eprail/img/share-d.png" alt="share"></td>
						<%}if(project.getStatuscategory().getIdProjectStatus()!=0&&project.getStatuscategory().getIdProjectStatus()!=1){ %>
						<td><a onclick="alertDelete(<%=project.getIdProject()%>, '<%=project.getProjectName()%>', '0');" title="Borrar" style="cursor: pointer;"><img src="/eprail/img/delete.png" alt="delete"></a></td>
						<%}else{ %>
						<td><img src="/eprail/img/delete-d.png" alt="delete"></td>
						<%} %>
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
								//tabla con documentos compartidos
								boolean botones = false;
								String style = "";
								switch(project.getProject().getStatuscategory().getIdProjectStatus())
								{
								case 0:
									style = "border:2px solid #800000; padding: 10px; color:#800000; background-color:#DB9595;";
									botones = false;
									break;
								case 1:
									style = "border:2px solid #800000; padding: 10px; color: #800000; background-color: #EEDBDD;";
									botones = false;
									break;
								case 2:
									style = "border:2px solid #800000; padding: 10px;";
									botones = true;
									break;
								default:
									style = "border:2px solid #FFF; padding: 10px; color: #FFF; background-color: #800000;";
									botones = false;
								}			
					%>
					<tr class="row">
						<td><img src="/eprail/img/thunder-share.png" alt="shared-project" width="30" height="30"></td>
						<td><%=project.getProject().getProjectName()%></td>
						<td style="color: #C0C0C0;">(<%=project.getUser2().getFirstName() %> <%=project.getUser2().getFamilyName() %>)</td>
						<td><span style="<%=style %>" title="<%=project.getProject().getStatuscategory().getStatusDescription()%>">
						<%=project.getProject().getStatuscategory().getStatusName()%>
						</span></td>
						<td style="color: #C0C0C0;">
						<%
						 SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");%>
						<%=sdf.format(project.getProject().getDateModified()) %>
						</td>
						<%if(botones){ %>
						<td><a href="/eprail/controller/see?id=<%=project.getProject().getIdProject()%>" title="Ver"><img src="/eprail/img/eye.png" alt="see"></a></td>
						<td><%if(project.getAllowRecalculate()!=0){ %><a href="/eprail/controller/run?id=<%=project.getProject().getIdProject()%>" title="Simular"><img src="/eprail/img/gear.png" alt="run"></a><%}else{ %><img src="/eprail/img/gear-d.png" alt="run"><%} %></td>
						<td><%if(project.getAllowDownload()!=0){ %><a href="/eprail/controller/download?id=<%=project.getProject().getIdProject()%>&sh=1" title="Descargar"><img src="/eprail/img/download.png" alt="download"></a><%}else{ %><img src="/eprail/img/download-d.png" alt="download"><%} %></td>
						<td><%if(project.getAllowShare()!=0){ %><a href="/eprail/controller/share?op=2&id=<%=project.getProject().getIdProject()%>&n=<%=project.getProject().getProjectName() %>" title="Compartir"><img src="/eprail/img/share.png" alt="share"></a><%}else{ %><img src="/eprail/img/share-d.png" alt="share"><%} %></td>	
						<%}else{ %>
						<td><img src="/eprail/img/eye-d.png" alt="see"></td>
						<td><img src="/eprail/img/gear-d.png" alt="run"></td>
						<td><img src="/eprail/img/download-d.png" alt="download"></td>
						<td><img src="/eprail/img/share-d.png" alt="share"></td>
						<%}if(project.getProject().getStatuscategory().getIdProjectStatus()!=0&&project.getProject().getStatuscategory().getIdProjectStatus()!=1){ %>
							<td><%if(project.getAllowDelete()!=0){ %><a onclick="alertDelete(<%=project.getProject().getIdProject()%>, '<%=project.getProject().getProjectName()%>', '1');" style="cursor: pointer;" title="Borrar"><img src="/eprail/img/delete.png" alt="delete"></a><%}else{ %><img src="/eprail/img/delete-d.png" alt="share"><%} %></td>
						<%}else{ %>
						<td><img src="/eprail/img/delete-d.png" alt="delete"></td>
							<%} %>
					</tr>
					<%
							}
						}
					%>
					</table>
	</div>
</body>
</html>