<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">   
 	#success { overflow: auto; overflow-y: hidden; }  
    #success p { margin: 0; padding: 1em; white-space: nowrap; }
    
    #failed { overflow: auto; overflow-y: hidden; }  
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>

<title>${titleU}</title>
<content tag="heading">${titleU}</content>
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
    			<li>Cấu trúc file: 
    					<code>
    						&lt;<fmt:message key="contactNumber.area"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="contactNumber.typeError"/>&gt;,
    						&lt;<fmt:message key="contactNumber.name"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="contactNumber.phoneNumber"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="contactNumber.email"/>&gt;,
    						&lt;<fmt:message key="contactNumber.regency"/>&gt;,
    						&lt;<fmt:message key="contactNumber.ghiChu"/>&gt;
    					</code></li>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/ContactNumber_Ex.xls" title="Danh sách thông tin đầu mối liên lạc" style="color: blue; ">ContactNumber_Ex.xls</a></li>
				<li><fmt:message key="global.chuY1"/></li>
    			</ul>
    			
    		</td>
    	</tr>
    	
    </table>
    
    <c:if test="${status != null}" >
    	<div class="error">${status} ${statusExists}</div>
    </c:if>
    <c:if test="${fn:length(failedList) gt 0}">
    	<div id="failed">
    		<div><b>Dữ liệu thông tin đầu mối liên lạc không hợp lệ ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
		    	<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="700" >
					<display:column title="STT">
						<c:out value="${item1_rowNum}" />
					</display:column>
					<display:column property="area" titleKey="contactNumber.area" class="NOT_NULL"/>
					<display:column property="typeError" titleKey="contactNumber.typeError"/>
					<display:column property="name" titleKey="contactNumber.name" class="NOT_NULL"/>
					<display:column property="phoneNumber" titleKey="contactNumber.phoneNumber" class="NOT_NULL"/>
					<display:column property="email" titleKey="contactNumber.email"/>
					<display:column property="regency" titleKey="contactNumber.regency"/>
					<display:column property="ghiChu" titleKey="contactNumber.ghiChu"/>
					
					<display:setProperty name="export.csv.include_header" value="true" />
					<display:setProperty name="export.excel.include_header" value="true" />
					<display:setProperty name="export.xml.include_header" value="true" />
					<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
					<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
					<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu thông tin đầu mối liên lạc hợp lệ ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
		    	<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700" >
					<display:column title="STT">
						<c:out value="${item2_rowNum}" />
					</display:column>
					<display:column property="area" titleKey="contactNumber.area" class="NOT_NULL"/>
					<display:column property="typeError" titleKey="contactNumber.typeError"/>
					<display:column property="name" titleKey="contactNumber.name" class="NOT_NULL"/>
					<display:column property="phoneNumber" titleKey="contactNumber.phoneNumber" class="NOT_NULL"/>
					<display:column property="email" titleKey="contactNumber.email"/>
					<display:column property="regency" titleKey="contactNumber.regency"/>
					<display:column property="ghiChu" titleKey="contactNumber.ghiChu"/>
				</display:table>
			</div>
		</div>
	</c:if>
    
	<table>
		<tr>
			<td>
               	<input class="button" type="button" value="<fmt:message key="global.button.back"/>" onClick='window.location="list.htm"'>
			</td>
		</tr>
	</table>
</form:form>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css">

<script type="text/javascript">  
    $(function() {
    	$('#item2>tbody>tr').each(
    	    	 function(){
   					  ${highlight}
   						});

    	$('#item1>tbody>tr').each(
   	    	 function(){
  					  ${highlight}
  					});
		}); 
</script>