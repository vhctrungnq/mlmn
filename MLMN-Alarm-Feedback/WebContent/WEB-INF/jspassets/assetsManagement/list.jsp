<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
  <!-- Danh sach Danh muc tai san -->
  <style type="text/css">  
    
	#doublescroll { overflow: auto; overflow-y: hidden; }   
	#doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
    
     #dvtTeamProcess {
     visibility: visible !important;
    }
   
    #acLow {
     visibility: visible !important;
    }
    
     #bscid_chzn {

		width: 220px !important;
	}
    .ui-autocomplete {
		max-height: 200px;
		overflow-y: auto;
		/* prevent horizontal scrollbar */
		overflow-x: hidden;
	}
	/* IE 6 doesn't support max-height
	* we use height instead, but this forces the menu to always be this tall
	*/
	* html .ui-autocomplete {
		height: 200px;
	}  
</style>
<title>${title}</title>
<content tag="heading">${title}</content><div>
<form:form commandName="filter" method="post" action="list.htm"> 
	<table style = "width:100%">
		<tr>		
			<td><fmt:message key="assetsManagement.asTypesName" /></td>
			<td>
				<form:select path ="asTypesId" class="wid70" onchange="xl()"> 
						<form:option value=""><fmt:message key="global.All"/></form:option>
		   				<c:forEach var="items" items="${asTypesIDList}">
			              	<c:choose>
			                <c:when test="${items.asTypesId == asTypesId}">
			                    <form:option value="${items.asTypesId}" selected="selected">${items.asTypesName}</form:option>
			                </c:when>
			                <c:otherwise>
			                    <form:option value="${items.asTypesId}">${items.asTypesName}</form:option>
			                </c:otherwise>
			              	</c:choose>
				    	</c:forEach>
				</form:select> 
			</td>
			
			<td ><fmt:message key="assetsManagement.productCode" /></td>
			<td >
				<form:input type="text" value="${productCode}" path ="productCode" class="wid100"/>
			</td>
			
			<td class="pdl15"><fmt:message key="assetsManagement.assetsName" /></td>
			<td >
				<form:input type="text" value="${assetsName}" path ="assetsName" class="wid100"/>
			</td> 
			
			<td class="pdl15">
				<input class="button" type="submit" id="submit" value="<fmt:message key="button.search"/>"/>
			</td>
		</tr> 
		
		<tr>
			<td align="right" colspan="9">
				<a href="form.htm"><fmt:message key="button.add"/></a>&nbsp;-
	            <a href="upload.htm"><fmt:message key="button.upload"/></a>&nbsp; 
	        </td>
		</tr>
	</table>
</form:form>
</div>

<div style="overflow: auto;">
	<display:table name="${assetsManagement}" class="simple2" id="assetsManagement" requestURI="" pagesize="100" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px !important;"> <c:out value="${assetsManagement_rowNum}" /> </display:column>	
		<display:column property="asTypesName" titleKey="assetsManagement.asTypesName" sortable="true" sortName="AS_TYPES_NAME"/>
		<display:column property="productCode" titleKey="assetsManagement.productCode" sortable="true" sortName="PRODUCT_CODE"/>
		<display:column property="assetsName" titleKey="assetsManagement.assetsName" sortable="true" sortName="ASSETS_NAME"/>
		<display:column property="unit" titleKey="assetsManagement.unit" sortable="true" sortName="UNIT"/> 
		<display:column property="ordering" titleKey="assetsManagement.ordering" sortable="true" sortName="ORDERING"/>
		<display:column property="description" titleKey="assetsManagement.description" sortable="true" sortName="DESCRIPTION"/>
		<display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style="width:90px !important;">
			<a href="form.htm?id=${assetsManagement.id}"><fmt:message key="button.edit"/></a>&nbsp;
	 			<a href="delete.htm?id=${assetsManagement.id}"
					onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')">
			<fmt:message key="button.delete"/></a>&nbsp; 
	 		</display:column>
		
	   	<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />

	</display:table>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css">

<script type="text/javascript">
function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
}
</script>