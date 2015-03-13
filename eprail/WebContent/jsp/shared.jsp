<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.*, modeldata.tfg.eprail.Sharing, modeldata.tfg.eprail.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%List<User> listUser = (List<User>) request.getSession().getAttribute("userList"); %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="/eprail/css/style.css" rel="stylesheet" type="text/css">
<link href="/eprail/css/jquery-ui.min.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/eprail/script/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="/eprail/script/center.js"></script>
<script type="text/javascript" src="/eprail/script/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="/eprail/script/jquery-ui.min.js"></script>
<script>
$(function() {
    var projects = [
	<%if(listUser!=null && listUser.size()!=0){ 
		for(User us : listUser){ %>
      {
        value: "<%= us.getFirstName().toUpperCase()%> <%=us.getFamilyName().toUpperCase()%>",
        desc: "<%=us.getEmail()%>"
      },
      <%}}%>
    ];
 
    $( "#tags" ).autocomplete({
      minLength: 0,
      source: projects,
      focus: function( event, ui ) {
        $( "#tags" ).val( ui.item.value );
        $( "#tags" ).val( ui.item.desc );
        return false;
      },
      select: function( event, ui ) {
        $( "#tags" ).val( ui.item.desc );
 
        return false;
      }
    })
    .autocomplete( "instance" )._renderItem = function( ul, item ) {
      return $( "<li>" )
        .append( "<a>" + item.value + "<br>" + item.desc + "</a>" )
        .appendTo( ul );
    };
  });
</script>
</head>
<body>
	<%-- Cogemos el JavaBean del usuario de la session --%>
	<jsp:useBean id="userBean" class="modeldata.tfg.eprail.User"
		scope="session" />
	<jsp:include page="./top.jsp" flush="true" />
	<div class="center" style="height:500px; width:1000px;">
		<span class="title">Compartir proyecto <%=request.getParameter("n").replaceAll(".ongf", "")%></span>&nbsp;&nbsp;<a class="subtitle" style="left:28%;" href="/eprail/controller/login" title="Volver"><img src="/eprail/img/back.png" alt="back" width="30"
			height="30"></a>
		<p>Introduce el e-mail del usuario registrado con el que quieres compartir el proyecto</p>
		<form action="/eprail/controller/share?op=1&id=<%=request.getParameter("id") %>" method="post" name="add-sh">
			<input id="tags" type="email" name="email" maxlength="60" size="50"/>&nbsp;<input type="image" src="/eprail/img/add.png" width="20" height="20" alt="add" title="A&ntilde;adir">
		</form>
		<br><br><br><br>
		<form action="/eprail/controller/share?op=2&id=<%=request.getParameter("id") %>" method="post" name="recuperar">
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
					List<Sharing> list = (List<Sharing>) request.getSession().getAttribute("userSharedList");
					if (list != null) {
						int cnt=0;	
						for (Sharing sh : list) {
						//tabla con usuarios con acceso a ese documento
					%>
					<tr <%if(cnt%2==0){ %>class="alt"<%} %>>
						<td><%=sh.getUser1().getFirstName() %></td>
						<td><%=sh.getUser1().getEmail() %></td>
						<td><input type="checkbox" checked="checked" disabled="disabled"></td>
						<td><input type="checkbox" value="<%="R"+sh.getIdSharing() %>" name="perm" <%if(sh.getAllowRecalculate()!=0){ %>checked="checked"<%} %>></td>
						<td><input type="checkbox" value="<%="D"+sh.getIdSharing() %>" name="perm" <%if(sh.getAllowDownload()!=0){ %>checked="checked"<%} %>></td>
						<td><input type="checkbox" value="<%="S"+sh.getIdSharing() %>" name="perm" <%if(sh.getAllowShare()!=0){ %>checked="checked"<%} %>></td>
						<td><input type="checkbox" value="<%="X"+sh.getIdSharing() %>" name="perm" <%if(sh.getAllowDelete()!=0){ %>checked="checked"<%} %>></td>
						<td class="delete"><a href="/eprail/controller/share?op=1&i=<%=sh.getIdSharing()%>&id=<%=sh.getProject().getIdProject()%>" title="Quitar"><img src="/eprail/img/delete-u.png" width="15" height="15" alt="Eliminar"></a></td>
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
				<input type="submit" value="Guardar" id="edit-s" />
			</fieldset>
		</form>
	</div>
</body>
</html>