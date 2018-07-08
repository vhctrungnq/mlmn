<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>messageCodes list</title>
<body class="section-4"/>
<content tag="heading">DANH SÁCH MÃ LỖI</content>

<table border="0" width="100%" cellspacing="0" cellpadding="0">
    <tr height="5"><td colspan="2"/></tr>
    <tr><td colspan="2">			
		<div  style="overflow: auto;">
<display:table name="${messageCodesList}" class="simple2" id="messageCodes" requestURI="" pagesize="100" export="true" sort="external" defaultsort="1">
		    <display:column property="msgCode" titleKey="MÃ LỖI"/>
		    <display:column property="message" titleKey="THÔNG ĐIỆP"/>
		    <display:column property="defineTime" format="{0,date,dd/MM/yyyy}" titleKey="THỜI GIAN ĐỊNH NGHĨA" media="html"/>
		    <display:column property="defineBy" titleKey="NGƯỜI ĐỊNH NGHĨA"/>
		    
		    <display:setProperty name="export.csv.include_header" value="true"/>
	    	<display:setProperty name="export.excel.include_header" value="true"/>
	    	<display:setProperty name="export.xml.include_header" value="true"/>
    		<display:setProperty name="export.xml.filename" value="MessageCodesList.xml"/>
    		<display:setProperty name="export.csv.filename" value="MessageCodesList.csv"/>
	    	<display:setProperty name="export.excel.filename" value="MessageCodesList.xls"/>
		</display:table>
</div>
	</td></tr>
</table>
