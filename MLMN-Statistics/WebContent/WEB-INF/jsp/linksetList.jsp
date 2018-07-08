<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>linkset list</title>
<body class="section-4"/>
<content tag="heading">DANH SÁCH LINKSET</content>

<table border="0" width="100%" cellspacing="0" cellpadding="0">
    <tr>
    	<td align="left">
			<form:form commandName="filter" method="post" action="list.htm">
				<table width="100%" border="0" cellspacing="1" cellpadding="0">
					<tr>
					    <td>
					    	Node mạng&nbsp;
					    	<form:input path="nodeid" size="10"/>
							&nbsp;Linkset Id&nbsp;
							<form:input path="linksetid" size="10"/>
			                &nbsp;Tên Linkset&nbsp;
							<form:input path="linksetName" size="10"/>
			                &nbsp;<input type="submit" class="button" name="filter" value="Tìm kiếm"/>
			            </td>
			        </tr>		
				</table>
			</form:form>
        </td>
        <c:if test="${checkRoleManager==true}">	
        <td align="right">
            <a href="upload.htm">Upload file</a>&nbsp;
            <a href="form.htm">Thêm mới</a>&nbsp;
        </td>
        </c:if>
    </tr>
    <tr height="5"><td colspan="2"/></tr>
    <tr><td colspan="2">			
<div  style="overflow: auto;">
<display:table name="${linksetList}" id="linkset" requestURI="" pagesize="100" class="simple2" export="true" sort="external" defaultsort="1">
			    <display:column property="region" titleKey="TRUNG TÂM" media="html"/>        
			    <display:column property="nodeid" titleKey="NODE MẠNG"/>
			    <display:column property="linksetid" titleKey="LINKSET ID"/>
			    <display:column property="linksetName" titleKey="TÊN LINKSET" />
			    <display:column property="launchDate" format="{0,date,dd/MM/yyyy}" titleKey="NGÀY CẬP NHẬT" media ="html"/>
			    <display:column property="linkDevice" titleKey="SỐ LINK"/>
			    <c:if test="${checkRoleManager==true}">	
			    <display:column titleKey="QUẢN LÝ" media="html">
			    	<a href="form.htm?linksetid=${linkset.linksetid}&nodeid=${linkset.nodeid}">Sửa</a>&nbsp;
			    	<a href="delete.htm?linksetid=${linkset.linksetid}&nodeid=${linkset.nodeid}"
			    	   onclick="return confirm('Bạn có thực sự muốn xóa không?')" >Xóa</a>&nbsp;
			    </display:column>
			    </c:if>
			    <display:setProperty name="export.csv.include_header" value="true"/>
			    <display:setProperty name="export.excel.include_header" value="true"/>
		    	<display:setProperty name="export.csv.filename" value="LinksetList.csv"/>
			    <display:setProperty name="export.excel.filename" value="LinksetList.xls"/>
			    
			</display:table>
</div>
	</td></tr>
</table>
