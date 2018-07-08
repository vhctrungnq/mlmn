<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>route list</title>
 <body class="section-4"/>
<content tag="heading">DANH SÁCH ROUTE </content>

<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td align="left">
			<form:form commandName="filter" method="post" action="list.htm">
				<table width="100%" border="0" cellspacing="1" cellpadding="0">
    				<tr>
	    			<td>Route Id</td><td><form:input id="routeid" name="routeid" path="routeid" maxlength="50" size="10"/></td>
	    			<td>Tên Route</td><td><form:input id="routename" name="routename" path="routename" maxlength="50" size="10"/></td>
	    			<td>Điểm đầu</td><td><form:input id="fromMscid" name="fromMscid" path="fromMscid" maxlength="50" size="10"/></td>
	        		<td>Điểm cuối</td><td><form:input id="toMscid" name="toMscid" path="toMscid" maxlength="30" size="10"/></td>
	        		<td><input type="submit" class="button" id="submit" name="filter" value="Tìm kiếm"/></td>
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
 	
<div style="overflow:auto;">
<display:table name="${routeList}" id="route" requestURI="" pagesize="100" class="simple2" export="true" sort="external" defaultsort="1">
		<display:column property="routeid" title="ROUTEID"/>
		<display:column property ="routename" title="TÊN ROUTE" />
		<display:column property="fromMscid" title="ĐIỂM ĐẦU"/>
	    <display:column property="toMscid" title="ĐIỂM CUỐI"/>
	    <display:column property="dev" title="TÊN THIẾT BỊ" />
	     <c:if test="${checkRoleManager==true}">	
	    <display:column title="QUẢN LÝ" media="html">
	    	<a href="form.htm?routeid=${route.routeid}&fromMscid=${route.fromMscid}&toMscid=${route.toMscid}">Sửa</a>&nbsp;
	    	<a href="delete.htm?routeid=${route.routeid}&fromMscid=${route.fromMscid}&toMscid=${route.toMscid}"
	    	   onclick="return confirm('Bạn có chắc muốn xóa?')" >Xóa</a>&nbsp;
	    </display:column>
	    </c:if>
	    <display:setProperty name="export.csv.include_header" value="true"/>
    	<display:setProperty name="export.excel.include_header" value="true"/>
    	<display:setProperty name="export.xml.include_header" value="true"/>
   		<display:setProperty name="export.xml.filename" value="RouteList.xml"/>
   		<display:setProperty name="export.csv.filename" value="RouteList.csv"/>
    	<display:setProperty name="export.excel.filename" value="RouteList.xls"/>
	</display:table>
</div>
</td>
</tr>
</table>
