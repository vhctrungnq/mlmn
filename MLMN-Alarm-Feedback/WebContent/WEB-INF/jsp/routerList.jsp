<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>route list</title>
<!-- <body class="section-4"/> -->
<content tag="heading">DANH SÁCH ROUTER</content>

<table style="border:0; width:100%; cellspacing:0; cellpadding:0;" class="form">
	<tr>
		<td align="left">
			<form:form commandName="filter" method="post" action="list.htm">
				<table style="width:80%; border:0; cellspacing:1; cellpadding:0;">
    				<tr>
    				<td style="width: 90px">Router Name</td><td><form:input id="routerName" name="routerName" path="routerName" maxlength="50" size="16"/></td>
	    			<td style="width: 90px">Route Id</td><td><form:input id="routerId" name="routerId" path="routerId" maxlength="50" size="16"/></td>
	        		<td><input type="submit" class="button" id="submit" value="Tìm kiếm"/></td>
        			</tr>
        		</table>
        	</form:form>
        </td>
        <td align="right">
            <a href="upload.htm">Upload file</a>&nbsp;
            <a href="form.htm">Thêm mới</a>&nbsp;
        </td>
  </tr>	
	<tr height="5"><td colspan="2"/></tr>
    <tr><td colspan="2">	
 	
<div style="overflow:auto;">
<display:table name="${routeList}" id="route" requestURI="" pagesize="50" class="simple2" export="true" sort="external" defaultsort="1">
		<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:20px;"> <c:out value="${route_rowNum}"/> </display:column>
		<display:column property ="routerName" title="Router name" sortable="true" sortName="ROUTER_NAME"/>
		<display:column property="deviceType" title="Device type" sortable="true" sortName="DEVICE_TYPE"/>
	    <display:column property="function" title="Function" sortable="true" sortName="FUNCTION"/>
	    <display:column property="routerId" title="Router id" sortable="true" sortName="ROUTER_ID"/>
	    <display:column property="oam" title="OAM" sortable="true" sortName="OAM"/>
	    <display:column title="QUẢN LÝ" media="html">
	    	<a href="form.htm?id=${route.id}">Sửa</a>&nbsp;
	    	<a href="delete.htm?id=${route.id}"
	    	   onclick="return confirm('Bạn có chắc muốn xóa?')" >Xóa</a>&nbsp;
	    </display:column>
	    <display:setProperty name="export.csv.include_header" value="true"/>
    	<display:setProperty name="export.excel.include_header" value="true"/>
    	<display:setProperty name="export.xml.include_header" value="true"/>
   		<display:setProperty name="export.xml.filename" value="RouterList.xml"/>
   		<display:setProperty name="export.csv.filename" value="RouterList.csv"/>
    	<display:setProperty name="export.excel.filename" value="RouterList.xls"/>
	</display:table>
</div>
</td>
</tr>
</table>
