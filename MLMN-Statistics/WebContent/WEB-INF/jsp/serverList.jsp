<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>server list</title>
<content tag="heading">CẤU HÌNH FTP SERVER</content>

<table style="width:100%; cellspacing:0; cellpadding:0;">
    <tr>
        <td align="right">
            <a href="form.htm">Thêm mới</a>&nbsp;
        </td>
    </tr>
</table>

<div  style="overflow: auto;">
	<display:table name="${serverList}" id="server" class="simple2" export="true" requestURI="" pagesize="100" sort="external" defaultsort="1">
	    <display:column property="serverName" titleKey="TÊN SERVER"/>
	    <display:column property="ipAddress" titleKey="ĐỊA CHỈ IP"/>
	    <display:column property="port" titleKey="CỔNG"/>        
	    <display:column property="ftpUser" titleKey="TÊN ĐĂNG NHẬP BẰNG FTP"/>
	    <display:column property="loginTime" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="LẦN CUỐI CÙNG ĐĂNG NHẬP"/>
	    <display:column titleKey="QUẢN LÝ" media="html">
	    	<a href="form.htm?serverId=${server.serverId}">Sửa</a>&nbsp;
	    	<a href="delete.htm?serverId=${server.serverId}"
	    	   onclick="return confirm('Bạn có thực sự muốn xóa không?')">Xóa</a>
	    </display:column>
	    <display:setProperty name="export.xml.filename" value="ServerList.xml"/>
	    		<display:setProperty name="export.csv.filename" value="ServerList.csv"/>
		    	<display:setProperty name="export.excel.filename" value="ServerList.xls"/>
	</display:table>
</div>
