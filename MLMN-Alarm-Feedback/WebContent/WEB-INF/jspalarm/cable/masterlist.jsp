<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>Định nghĩa thuộc tính cáp</title>
<content tag="heading">Định nghĩa thuộc tính cáp</content>

<style>
.wid100{
	width:100%;
}

.dpnone {
	display: none;
}

.pdl10 {
	padding-left:10px;
}
</style>

<div>
<form:form commandName="cableAttributesMaster" method="POST">
	<table>
		<tr>
			<td style="min-width:35px;"><fmt:message key="cableAttributesMaster.label"/></td>
			<td>
				<form:input path="label" class="wid100"/>
			</td>
			
			<td class="pdl10" style="min-width:35px;"><fmt:message key="cableAttributesMaster.cableTypeId"/></td>
			<td>
				<%-- <form:input path="cableTypeId" class="wid100"/> --%>
				<select name="cableTypeId" id="cableTypeId" style="width: 150px;height:20px; padding-top: 4px;">
	         	<option value=""><fmt:message key="global.All"/></option>
	          		<c:forEach var="item" items="${cableTypeIdList}">
					<c:choose>
		                <c:when test="${item.cableTypeId == cableTypeId}">
		                    <option value="${item.cableTypeId}" selected="selected">${item.cableTypeId}</option>
		                </c:when>
						<c:otherwise>
							<option value="${item.cableTypeId}">${item.cableTypeId}</option>
						</c:otherwise>
					</c:choose>
					</c:forEach>
				</select> 
			</td>
			
			<td class="pdl10" style="min-width:35px;"><fmt:message key="cableAttributesMaster.description"/></td>
			<td>
				<form:input path="description" class="wid100"/>
			</td>
			
			<td class="pdl10">
				<input class="button" type="submit" class="button" name="filter" value="<fmt:message key="button.search"/>" />
			</td>
		</tr>
	</table>
</form:form>
</div>
<c:if test="${isManager=='Y' }">
<div  class="ftsize12" align="right">
	<span><a href="form.htm" title="Thêm mới">Thêm mới</a></span>
</div>
</c:if>
<div id="result">
	<display:table name="${cableAttributesMasterList}" class="simple2" id="item" requestURI="" export="true" pagesize="15" sort="external" defaultsort="1" >
		<display:column title="STT">
			<c:out value="${item_rowNum}" />
		</display:column>
		<display:column property="cableTypeId" titleKey="cableAttributesMaster.cableTypeId" sortable="true" sortName="CABLE_TYPE_ID"/>
		<display:column titleKey="cableAttributesMaster.label" media="html" sortable="true" sortName="LABEL">
			<a href="form.htm?id=${item.id }" title="${item.label}">${item.label}</a>
		</display:column>
		<display:column property="label" titleKey="cableAttributesMaster.label" class="dpnone" headerClass="dpnone"/>
		<display:column property="description" titleKey="cableAttributesMaster.description" sortable="true" sortName="DESCRIPTION"/>
		<display:column property="ordering" titleKey="cableAttributesMaster.ordering" sortable="true" sortName="ORDERING"/>
		<c:if test="${isManager=='Y' }">
		<display:column titleKey="global.management" media="html" class="centerColumnMana">
			<a href="form.htm?id=${item.id}"><fmt:message key="global.form.sua"/></a>&nbsp;
 			<a href="#" onclick="javascript:DeleteAttMaster(${item.id});return false;"><fmt:message key="global.form.xoa"/></a>
 		</display:column>
 		</c:if>
 		<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
	</display:table>
</div>

<script type="text/javascript">
function DeleteAttMaster(id) {
	var r = confirm('<fmt:message key="messsage.confirm.delete"/>');
	if (r == true) {
		$.getJSON("${pageContext.request.contextPath}/alarm/cable-att-master/ajax/deleteAttMaster.htm", {id: id}, function(j){
			if (j==-1) {
				alert("Thuộc tính đã sử dụng. Bạn không thể xóa");
			} else if (j==1) {
				$("#cableAttributesMaster").submit();
			}
		});
	}
}
</script>