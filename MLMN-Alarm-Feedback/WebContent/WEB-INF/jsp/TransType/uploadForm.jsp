<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">   
 	#success { overflow: auto; overflow-y: hidden; }  
    #success p { margin: 0; padding: 1em; white-space: nowrap; }
    
    #failed { overflow: auto; overflow-y: hidden; }  
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>

<title>${title}</title>
<content tag="heading">${title}</content>
 <form:form  method="post" action="upload.htm" enctype="multipart/form-data">
	<table class="simple2">	
    	<tr style="height:20px;" >
    		<td width="150px"><b><fmt:message key="cautruc.filepath"/> <font color="red">(*)</font></b></td>
    		<td><input type="file" size="110" name="filePath" id="filePath" class="button" />
    			<input type="submit" name="upload" id="upload" class="button" value="<fmt:message key="global.button.import"/>" />
    		</td>
    	</tr>
    	<tr>
    		<td><b>
    			<fmt:message key="global.FileExample"/></b>
    		</td>
    		<td>
    		<ul>
    			<li><fmt:message key="global.formatFile"/></li>
    			<li><fmt:message key="Truyendan.xls"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/Truyendan.xls" title="Danh sách kiểu truyền dẫn" style="color: blue; ">Truyendan.xls</a></li>
				<li><fmt:message key="global.chuY1"/></li>
    			</ul>
    			
    		</td>
    	</tr>
    	
    </table>
    
	<table>
		<c:if test="${failedNum > 0 }">
		<tr>
			<td><b>Dữ liệu truyền dẫn không hợp lệ ${failedNum}/${totalNum } </b></td>
		</tr>
		<tr>
			<td>
				<display:table name="${failedList}" class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
				  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px;"> <c:out value="${item_rowNum}"/> </display:column>
					<display:column property="siteid" titleKey="Mã trạm" sortable="true" sortName="SITEID"  style="max-width:100px;"/>
					<display:column property="siteType2g"  titleKey="Loại trạm 2G" sortable="true" sortName="SITE_TYPE_2G" style="max-width:60px;"/>
					<display:column property="fullname"  titleKey="Loại truyền dẫn 2G" sortable="true" sortName="FULLNAME"  style="max-width:100px;"/>
					<display:column property="dipPartner2g"  titleKey="Đối tác 2G" sortable="true" sortName="DIP_PARTNER_2G" style="max-width:150px;"/>
					<display:column property="nodeb"  titleKey="NodeB" sortable="true" sortName="NODEB" style="max-width:50px;" />
					<display:column property="dipPartner3g"  titleKey="Đối tác 3G" sortable="true" sortName="DIP_PARTNER_3G" />
					<display:column property="dipType3g"  titleKey="Loại truyền dẫn 3G" sortable="true" sortName="DIP_TYPE_3G" />
					
					<display:setProperty name="export.csv.include_header" value="true" />
					<display:setProperty name="export.excel.include_header" value="true" />
					<display:setProperty name="export.xml.include_header" value="true" />
					<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
					<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
					<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />
			
				</display:table>
			</td>
		</tr>
		</c:if>
		<c:if test="${successNum > 0}">
		<tr>
			<td><b>Dữ liệu truyền dẫn hợp lệ ${successNum}/${totalNum }</b> </td>
		</tr>
		<tr>
			<td>
				<display:table name="${successList}" class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
				  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px;"> <c:out value="${item_rowNum}"/> </display:column>
					<display:column property="siteid" titleKey="Mã trạm" sortable="true" sortName="SITEID"  style="max-width:100px;"/>
					<display:column property="siteType2g"  titleKey="Loại trạm 2G" sortable="true" sortName="SITE_TYPE_2G" style="max-width:60px;"/>
					<display:column property="fullname"  titleKey="Loại truyền dẫn 2G" sortable="true" sortName="FULLNAME"  style="max-width:100px;"/>
					<display:column property="dipPartner2g"  titleKey="Đối tác 2G" sortable="true" sortName="DIP_PARTNER_2G" style="max-width:150px;"/>
					<display:column property="nodeb"  titleKey="NodeB" sortable="true" sortName="NODEB" style="max-width:50px;" />
					<display:column property="dipPartner3g"  titleKey="Đối tác 3G" sortable="true" sortName="DIP_PARTNER_3G" />
					<display:column property="dipType3g"  titleKey="Loại truyền dẫn 3G" sortable="true" sortName="DIP_TYPE_3G" />
					
					<display:setProperty name="export.csv.include_header" value="true" />
					<display:setProperty name="export.excel.include_header" value="true" />
					<display:setProperty name="export.xml.include_header" value="true" />
					<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
					<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
					<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />
			
				</display:table>
			</td>
		</tr>
		</c:if>
		
		
		<tr>
			<td>
               	<input class="button" type="button" value="<fmt:message key="global.button.back"/>" onClick='window.location="list.htm"'>
			</td>
		</tr>
	</table>
</form:form>