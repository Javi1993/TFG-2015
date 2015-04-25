<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="funciones.tfg.eprail.Funciones, java.util.*,modeldata.tfg.eprailJPA.Sharing,modeldata.tfg.eprailJPA.User"%>
<!DOCTYPE html>
<html>
<head>
<%
	List<User> listUser = (List<User>) request.getAttribute("userList");
	String leng = (String) request.getSession().getAttribute("lenguage");
	if(leng == null)
	{
		Funciones.setLenaguage(request.getSession());
		leng = (String) request.getSession().getAttribute("lenguage");
	}
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Eprail: repositorio de casos</title>
<link href="/eprail/css/style.css" rel="stylesheet" type="text/css">
<link href="/eprail/css/jquery-ui.min.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/eprail/script/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="/eprail/script/center.js"></script>
<script type="text/javascript" src="/eprail/script/jquery-ui.min.js"></script>
<script>
$(function(){
    var projects = [
	<%if(listUser!=null && listUser.size()!=0){ 
		for(User us : listUser){%>
      {
        value: "<%=us.getFirstName().toUpperCase()%> <%=us.getFamilyName().toUpperCase()%>",
        desc: "<%=us.getEmail()%>"
      },
      <%}}%>
    ];
 
    $( "#tags" ).autocomplete({
      minLength: 0,
      source: projects,
      focus: function( event, ui ) {
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
	<jsp:useBean id="userBean" class="modeldata.tfg.eprailJPA.User"
		scope="session" />
	<jsp:include page="./top.jsp" flush="true" />
	<div class="center" style="height:500px; width:1000px;">
		<span class="title" style="text-align:center; width:200px; overflow:hidden; white-space:nowrap; text-overflow:ellipsis;"><%if(leng.equals("SP")){%>Compartir proyecto <%=request.getParameter("n")%><%}else{ %>Share <%=request.getParameter("n")%> project<%} %></span>&nbsp;&nbsp;<a class="subtitle" style="left:270px;" href="/eprail/controller/login" title="<%if(leng.equals("SP")){%>Volver<%}else{%>Back<%}%>"><img src="/eprail/img/back.png" alt="back" width="30"
			height="30"></a>
		<p><%if(leng.equals("SP")){%>Introduce el e-mail del usuario registrado con el que quieres compartir el proyecto<%}else{%>Enter the email address of the registered user with whom you want to share the project<%}%></p>
		<form id="form" action="/eprail/controller/share?op=1&id=<%=request.getParameter("id") %>" method="post" name="add-sh">
			<input id="tags" type="email" name="email" maxlength="60" size="50" required/>&nbsp;<input type="image" src="/eprail/img/add.png" width="20" height="20" alt="add" title="<%if(leng.equals("SP")){%>A&ntilde;adir<%}else{%>Add<%}%>">
		</form>
		<%if(request.getAttribute("message")==null){ %><sub style="font-size: 10px; color:#C0C0C0"><%if(leng.equals("SP")){%>Si no recuerdas el email puedes buscar por el nombre<%}else{ %>If you do not remember the email, you can search by username<%}%></sub><%}else{ %>
		<sub style="font-size: 10px; color:#5C85FF"><%=request.getAttribute("message") %></sub><%} %>
		<br><br><br><br>
		<form action="/eprail/controller/share?op=2&id=<%=request.getParameter("id") %>" method="post" name="recuperar">
			<fieldset>
			<table class="project" style="width: 100%">
					<tr>
						<th><%if(leng.equals("SP")){%>Usuario<%}else{ %>User<%}%></th>
						<th>Email</th>
						<th><img src="/eprail/img/eye.png" alt="see"><br><%if(leng.equals("SP")){%>Ver<%}else{ %>See<%}%></th>
						<th><img src="/eprail/img/gear.png" alt="run"><br><%if(leng.equals("SP")){%>Recalcular<%}else{ %>Recalculate<%}%></th>
						<th><img src="/eprail/img/download.png" alt="download"><br><%if(leng.equals("SP")){%>Descargar<%}else{ %>Download<%}%></th>
						<th><img src="/eprail/img/share.png" alt="share"><br><%if(leng.equals("SP")){%>Compartir<%}else{ %>Share<%}%></th>
						<th><img src="/eprail/img/delete.png" alt="delete"><br><%if(leng.equals("SP")){%>Borrar<%}else{ %>Delete<%}%></th>
						<th class="delete">&nbsp;&nbsp;</th>
					</tr>

					<%
					List<Sharing> list = (List<Sharing>) request.getAttribute("userSharedList");
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
						<td class="delete"><a href="/eprail/controller/share?op=1&i=<%=sh.getIdSharing()%>&id=<%=sh.getProject().getIdProject()%>" title="<%if(leng.equals("SP")){%>Quitar<%}else{ %>Remove<%}%>"><img src="/eprail/img/delete-u.png" width="15" height="15" alt="Eliminar"></a></td>
					</tr>
					<%
						cnt++;
							}
					%>
					<caption><%if(leng.equals("SP")){%>ACCIONES AUTORIZADAS<%}else{ %>AUTHORIZED SHARES<%}%></caption>
					<%
						}else{
					%>
					<caption><%if(leng.equals("SP")){%>ARCHIVO NO COMPARTIDO<%}else{ %>FILE NOT SHARED<%}%></caption>
					<%} %>
					</table>
				<input type="submit" value="<%if(leng.equals("SP")){%>Guardar<%}else{ %>Save<%}%>" id="edit-s" />
			</fieldset>
		</form>
	</div>
</body>
</html>