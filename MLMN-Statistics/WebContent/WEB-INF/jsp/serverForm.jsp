<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>server form</title>
<body class="section-5"/>
<content tag="heading">CẤU HÌNH FTP SERVER</content>

<form:form commandName="server" method="post" action="form.htm">
	<form:hidden path="serverId" />
	
    <table class="simple2">
        <tr>
            <td width="300"><b>Tên Server<font color="#ff0000">(*)</font></b></td>
            <td>
            	<form:input path="serverName" maxlength="30"/>
            	<br/><font color="red"><form:errors path="serverName"/></font>    
            </td>
        </tr>
        <tr>
            <td><b>Địa chỉ IP<font color="#ff0000">(*)</font></b></td>
            <td>
            	<form:input path="ipAddress" maxlength="15"/>
            	<br/><font color="red"><form:errors path="ipAddress"/></font>    
            </td>
        </tr>
        <tr>
            <td><b>Cổng<font color="#ff0000">(*)</font></b></td>
            <td><form:input path="port"  maxlength="5"/>
            <br/><font color="red"><form:errors path="port"/></font>    
            </td>
        </tr>
        <tr>
            <td><b>Tên đăng nhập</b></td>
            <td><form:input path="ftpUser"  maxlength="15"/>
         	<br/><font color="red"><form:errors path="ftpUser"/></font>
            </td>
        </tr>
        <tr>
            <td><b>Mật khẩu</b></td>
            <td><form:password path="ftpPassword" maxlength="30"/>
            <br/><font color="red"><form:errors path="ftpPassword"/></font> 
            </td> 
        </tr>
        <tr>
            <td><b>Ghi chú</b></td>
            <td><form:textarea path="description"  maxlength="1000"/></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" class="button" name="save" value="Lưu lại"/>
                <input type="button" value="Hủy bỏ" onClick='window.location="list.htm"' class = "button">
            </td>
        </tr>
    </table>
    
</form:form>

<br/>
<c:if test="${not empty server.serverId}">
	<table style="width:100%; cellspacing:0; cellpadding:0;">
        <tr class="gray">
            <td><b>Server Detail</b></td>
	        <td align="right">
	            <a href="${pageContext.request.contextPath}/system/ftpServerDetail/form.htm?serverId=${server.serverId}">Thêm mới</a>&nbsp;
	        </td>
	    </tr>
        <tr>
            <td colspan="2">
			    <div  style="overflow: auto;">
				<display:table name="serverDetailList" id="serverDetail" class="simple2" requestURI="" pagesize="100" sort="external" defaultsort="1" export="true">
			        
			        <display:column value="${server.serverName}" titleKey="Tên Server"/>
			        <display:column property="baseDir" titleKey="Thư mục lưu file dữ liệu thô"/>
			        <display:column property="description" titleKey="Ghi chú"/>
				    <display:column titleKey="Quản lý" media="html">
				    	<a href="${pageContext.request.contextPath}/system/ftpServerDetail/form.htm?serverId=${server.serverId}&baseDir=${serverDetail.baseDir}">Sửa</a>&nbsp;
				    	<a href="${pageContext.request.contextPath}/system/ftpServerDetail/delete.htm?serverId=${server.serverId}&baseDir=${serverDetail.baseDir}"
				    	   onclick="return confirm('Bạn có thực sự muốn xóa không?')" >Xóa</a>
				    </display:column>
				    
				    <display:setProperty name="export.csv.include_header" value="true" />
						<display:setProperty name="export.excel.include_header" value="true" />
						<display:setProperty name="export.xml.include_header" value="true" />
						<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
						<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
						<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
			    </display:table>
				</div>
			</td>
		</tr>
	</table>
</c:if>