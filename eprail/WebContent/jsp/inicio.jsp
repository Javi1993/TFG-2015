<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.Locale, java.text.SimpleDateFormat, funciones.tfg.aplicacion.Funciones, java.util.List,modeldata.tfg.aplicacionJPA.Project,modeldata.tfg.aplicacionJPA.Sharing"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Aplicaci&oacute;n web</title>
<link href="/aplicacion/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/aplicacion/script/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="/aplicacion/script/center.js"></script>
</head>
<script>
function alertDelete (id, name, sh, idiom) {
	text = "";
	if(idiom == "SP") {
		text = "¿Est\u00e1 seguro de borrar el proyecto "+name+"? No podr\u00e1 deshacer esta acci\u00F3n.";
	}else{
		text = "Are you sure to delete "+name+" project? You can't undo this action.";
	}
	if (confirm(text)) {
		window.location.assign("/aplicacion/controller/delete?id="+id+"&sh="+sh);
	}
}
</script>
<body>
<%
	String serv = (String) request.getSession().getAttribute("server");
	if(serv == null)
	{
		serv = "off";
	}
	String leng = (String) request.getSession().getAttribute("lenguage");
	if(leng == null)
	{
		Funciones.setLenaguage(request.getSession());
		leng = (String) request.getSession().getAttribute("lenguage");
	}
%>
	<%-- Cogemos el JavaBean del usuario de la session --%>
	<jsp:useBean id="userBean" class="modeldata.tfg.aplicacionJPA.User"
		scope="session" />
	<jsp:include page="./top.jsp" flush="true" />
	<div class="center" style="height:500px; width:1000px;">
		<span class="title"><%if(leng.equals("SP")){ %>Mis proyectos</span>&nbsp;&nbsp;<a class="subtitle" style="left:14%;" href=<%if(serv.equals("on")){ %>"/aplicacion/jsp/upload.jsp" title="A&ntilde;adir proyecto"<%}else{%>"javascript: void(0)"<%}%>><%}else{ %>My projects</span>&nbsp;&nbsp;<a class="subtitle" style="left:13%;" href="<%if(serv.equals("on")){ %>/aplicacion/jsp/upload.jsp<%}else{%>javascript: void(0)<%}%>" title="Add project"><%} %><img src="<%if(serv.equals("on")){ %>/aplicacion/img/add.png<%}else{%>/aplicacion/img/add-d.png<%} %>" alt="add" width="30" height="30"></a>
		<a id="reload" href="/aplicacion/controller/login" title="<%if(leng.equals("SP")){ %>Recargar<%}else{%>Reload<%}%>"><img src="/aplicacion/img/reload.png" alt="reload" width="20" height="20"></a>
					<div style="height: 220px;">
					<table class="project" style="width: 100%;">
					<%//tabla con documentos propios
						List<Project> list = (List<Project>) request.getSession().getAttribute("projectList");
						int back = 0;
						int next = 0;
						int max = 0;
						if (list != null && list.size()>0) {
							back = (int)request.getSession().getAttribute("offsetBack");
							next = (int)request.getSession().getAttribute("offsetNext");
							max = (int)request.getSession().getAttribute("max");
							for (Project project : list) {
								boolean botones = false;
								String style = "";
								String status = "";
								String desc = "";
								switch(project.getStatuscategory().getIdProjectStatus())
								{
								case 0:
									style = "border:2px solid #5C85FF; padding: 10px; color:#FFF; background-color:#506CC1;";
									botones = false;
									status = "Pending";
									desc = "The case was uploaded but not yet started the simulation";
									break;
								case 1:
									style = "border:2px solid #5C85FF; padding: 10px; color: #5C85FF; background-color: #D6EBFF;";
									botones = false;
									status = "Calculating...";
									desc = "Simulation has been launched but not yet concluded";
									break;
								case 2:
									style = "border:2px solid #5C85FF; padding: 10px;";
									botones = true;
									status = "Simulated";
									desc = "Simulation was successful";
									break;
								default:
									style = "border:2px solid #FFF; padding: 10px; color: #FFF; background-color: #5C85FF;";
									status = "Errors";
									desc = "Error during simulation";
									botones = false;
								}		
								if(leng.equals("SP")){
									status = project.getStatuscategory().getStatusName();
									desc = project.getStatuscategory().getStatusDescription();
								}
					%>
					<tr class="row">
						<td><img src="/aplicacion/img/thunder.png" alt="project" width="30" height="30"></td>
						<td><%=project.getProjectName()%></td>
						<td><span style="<%=style %>" title="<%=desc%>">
						<%=status%>
						</span></td>
						<td style="color: #C0C0C0;">
						<%
						 SimpleDateFormat sdf = null;
						 if(leng.equals("SP")){
							 sdf = new SimpleDateFormat("dd-MMM-yyyy");
						 }else{
							 sdf = new SimpleDateFormat("MMM-dd-yyyy", Locale.UK);
						 }%>
						<%=sdf.format(project.getDateModified())%>
						</td>
						<%if(botones){ %>
						<td><a href="/aplicacion/controller/see?id=<%=project.getIdProject()%>" title="<%if(leng.equals("SP")){ %>Ver<%}else{%>See<%}%>"><img src="/aplicacion/img/eye.png" alt="see"></a></td>
						<td><a href="<%if(serv.equals("on")){ %>/aplicacion/controller/run?id=<%=project.getIdProject()%>" title="<%if(leng.equals("SP")){ %>Simular"<%}else{%>Simulate"<%}}else{%>javascript: void(0)"<%} %>><img src="<%if(serv.equals("on")){%>/aplicacion/img/gear.png<%}else{%>/aplicacion/img/gear-d.png<%}%>" alt="run"></a></td>
						<td><a href="/aplicacion/controller/download?id=<%=project.getIdProject()%>&sh=0" title="<%if(leng.equals("SP")){ %>Descargar<%}else{%>Download<%}%>"><img src="/aplicacion/img/download.png" alt="download"></a></td>
						<td><a href="/aplicacion/controller/share?op=2&id=<%=project.getIdProject()%>&n=<%=project.getProjectName() %>" title="<%if(leng.equals("SP")){ %>Compartir<%}else{%>Share<%}%>"><img src="/aplicacion/img/share.png" alt="share"></a></td>
						<%}else{ %>
						<td><img src="/aplicacion/img/eye-d.png" alt="see"></td>
						<td><img src="/aplicacion/img/gear-d.png" alt="run"></td>
						<td><img src="/aplicacion/img/download-d.png" alt="download"></td>
						<td><img src="/aplicacion/img/share-d.png" alt="share"></td>
						<%}if(project.getStatuscategory().getIdProjectStatus()!=0&&project.getStatuscategory().getIdProjectStatus()!=1){ %>
						<td><a onclick="alertDelete(<%=project.getIdProject()%>, '<%=project.getProjectName()%>', '0', '<%=leng %>');" title="<%if(leng.equals("SP")){ %>Borrar<%}else{%>Delete<%}%>" style="cursor: pointer;"><img src="/aplicacion/img/delete.png" alt="delete"></a></td>
						<%}else{ %>
						<td><img src="/aplicacion/img/delete-d.png" alt="delete"></td>
						<%} %>
					</tr>
					<%
							}
						}else{
					%>
					<p><%if(leng.equals("SP")){ %>No hay ning&uacute;n proyecto propio.<%}else{ %>No own project.<%} %></p>
					<%} %>
					</table>
					</div>
					<div style="height: 240px;">
					<p style="text-decoration: underline;">Proyectos compartidos:</p>
					<table class="project" style="width: 100%;">
					<%
						List<Sharing> listSh = (List<Sharing>) request.getSession().getAttribute("projectListShared");
						if (listSh != null && listSh.size()>0) {
							back = (int)request.getSession().getAttribute("offsetBack");
							next = (int)request.getSession().getAttribute("offsetNext");
							max = (int)request.getSession().getAttribute("max");
							for (Sharing project : listSh) {
								//tabla con documentos compartidos
								boolean botones = false;
								String style = "";
								String status = "";
								String desc = "";
								switch(project.getProject().getStatuscategory().getIdProjectStatus())
								{
								case 0:
									style = "border:2px solid #5C85FF; padding: 10px; color:#FFF; background-color:#506CC1;";
									botones = false;
									status = "Pending";
									desc = "The case was uploaded but not yet started the simulation";
									break;
								case 1:
									style = "border:2px solid #5C85FF; padding: 10px; color: #5C85FF; background-color: #D6EBFF;";
									botones = false;
									status = "Calculating...";
									desc = "Simulation has been launched but not yet concluded";
									break;
								case 2:
									style = "border:2px solid #5C85FF; padding: 10px;";
									botones = true;
									status = "Simulated";
									desc = "Simulation was successful";
									break;
								default:
									status = "Errors";
									desc = "Error during simulation";
									botones = false;
								}
								if(leng.equals("SP")){
									status = project.getProject().getStatuscategory().getStatusName();
									desc = project.getProject().getStatuscategory().getStatusDescription();
								}
					%>
					<tr class="row">
						<td><img src="/aplicacion/img/thunder-share.png" alt="shared-project" width="30" height="30"></td>
						<td style="width: 150px;">&nbsp;&nbsp;&nbsp;<%=project.getProject().getProjectName()%></td>
						<td style="color: #C0C0C0; width: 200px;">(<%=project.getUser2().getFirstName() %> <%=project.getUser2().getFamilyName() %>)</td>
						<td><span style="<%=style %>" title="<%=project.getProject().getStatuscategory().getStatusDescription()%>">
						<%=project.getProject().getStatuscategory().getStatusName()%>
						</span></td>
						<td style="color: #C0C0C0;">
						<%
						 SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");%>
						<%=sdf.format(project.getProject().getDateModified()) %>
						</td>
						<%if(botones){ %>
						<td><a href="/aplicacion/controller/see?id=<%=project.getProject().getIdProject()%>" title="<%if(leng.equals("SP")){ %>Ver<%}else{%>See<%}%>"><img src="/aplicacion/img/eye.png" alt="see"></a></td>
						<td><%if(project.getAllowRecalculate()!=0 && serv.equals("on")){ %><a href="/aplicacion/controller/run?id=<%=project.getProject().getIdProject()%>" title="<%if(leng.equals("SP")){ %>Simular<%}else{%>Simulate<%}%>"><img src="/aplicacion/img/gear.png" alt="run"></a><%}else{ %><img src="/aplicacion/img/gear-d.png" alt="run"><%} %></td>
						<td><%if(project.getAllowDownload()!=0){ %><a href="/aplicacion/controller/download?id=<%=project.getProject().getIdProject()%>&sh=1" title="<%if(leng.equals("SP")){ %>Descargar<%}else{%>Download<%}%>"><img src="/aplicacion/img/download.png" alt="download"></a><%}else{ %><img src="/aplicacion/img/download-d.png" alt="download"><%} %></td>
						<td><%if(project.getAllowShare()!=0){ %><a href="/aplicacion/controller/share?op=2&id=<%=project.getProject().getIdProject()%>&n=<%=project.getProject().getProjectName() %>" title="<%if(leng.equals("SP")){ %>Compartir<%}else{%>Share<%}%>"><img src="/aplicacion/img/share.png" alt="share"></a><%}else{ %><img src="/aplicacion/img/share-d.png" alt="share"><%} %></td>	
						<%}else{ %>
						<td><img src="/aplicacion/img/eye-d.png" alt="see"></td>
						<td><img src="/aplicacion/img/gear-d.png" alt="run"></td>
						<td><img src="/aplicacion/img/download-d.png" alt="download"></td>
						<td><img src="/aplicacion/img/share-d.png" alt="share"></td>
						<%}if(project.getProject().getStatuscategory().getIdProjectStatus()!=0&&project.getProject().getStatuscategory().getIdProjectStatus()!=1){ %>
							<td><%if(project.getAllowDelete()!=0){ %><a onclick="alertDelete(<%=project.getProject().getIdProject()%>, '<%=project.getProject().getProjectName()%>', '1', '<%=leng %>');" style="cursor: pointer;" title="<%if(leng.equals("SP")){ %>Borrar<%}else{%>Delete<%}%>"><img src="/aplicacion/img/delete.png" alt="delete"></a><%}else{ %><img src="/aplicacion/img/delete-d.png" alt="share"><%} %></td>
						<%}else{ %>
						<td><img src="/aplicacion/img/delete-d.png" alt="delete"></td>
							<%} %>
					</tr>
					<%
							}
						}else{
							%>
							<p><%if(leng.equals("SP")){ %>No hay ning&uacute;n proyecto compartido.<%}else{ %>No shared project.<%} %></p>
					<%} %>
					</table>
					</div>
					<div style="bottom: 0; position: absolute; text-align: right;">
					<%if(back>=0 && (next-4)>0){ %>
						<a href="/aplicacion/controller/page?op=0" title="<%if(leng.equals("SP")){ %>Atr&aacute;s<%}else{%>Back<%}%>"><img src="/aplicacion/img/backp.png" alt="back-page"></a>
						&nbsp;<%}
					if(next<max){
					%><a href="/aplicacion/controller/page?op=1" title="<%if(leng.equals("SP")){ %>Siguiente<%}else{%>Next<%}%>"><img src="/aplicacion/img/next.png" alt="next-page"></a>
					<%} %>
					</div>
	</div>
<div class="modal"></div>
<script>
$('#reload').click(function() {
    $('.modal').show();
});
</script>
</body>
</html>