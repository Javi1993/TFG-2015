<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	String leng = (String) request.getSession().getAttribute("lenguage");
	if(leng.equals("SP")){
%>
No se ha encontrado el MANIFEST.XML en el proyecto o este no ha podido ser validado por su esquema XSD.
<%}else{%>
The MANIFEST.XML file is not found in project or project can not be validated by the XSD schema.
<%}%>