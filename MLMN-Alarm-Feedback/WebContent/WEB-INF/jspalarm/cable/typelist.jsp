<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>Loại cáp</title>
<content tag="heading">Loại cáp</content>

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
<form:form commandName="cableType" method="POST">
	<table>
		<tr>
			<td style="min-width:35px;">Mã</td>
			<td>
				<form:input path="cableTypeId" class="wid100"/>
			</td>
			
			<td class="pdl10" style="min-width:35px;">Tên</td>
			<td>
				<form:input path="cableTypeName" class="wid100"/>
			</td>
			
			<td class="pdl10" style="min-width:35px;">Mô tả</td>
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
	<display:table name="${cableTypeList}" class="simple2" id="item" requestURI="" export="true" pagesize="15" sort="external" defaultsort="1" >
		<display:column title="STT">
			<c:out value="${item_rowNum}" />
		</display:column>
		<display:column titleKey="cableType.cableTypeId" media="html" sortable="true" sortName="CABLE_TYPE_ID">
			<a href="form.htm?id=${item.id }" title="${item.cableTypeId}">${item.cableTypeId}</a>
		</display:column>
		<display:column property="cableTypeId" titleKey="cableType.cableTypeId" class="dpnone" headerClass="dpnone"/>
		<display:column property="cableTypeName" titleKey="cableType.cableTypeName" sortable="true" sortName="CABLE_TYPE_NAME"/>
		<display:column property="description" titleKey="cableType.description" sortable="true" sortName="DESCRIPTION"/>
		<display:column property="ordering" titleKey="cableType.ordering" sortable="true" sortName="ORDERING"/>
		<c:if test="${isManager=='Y' }">
		<display:column titleKey="global.management" media="html" class="centerColumnMana">
			<a href="form.htm?id=${item.id}"><fmt:message key="global.form.sua"/></a>&nbsp;
 			<a href="#" onclick="javascript:DeleteAttMaster('${item.cableTypeId}');return false;"><fmt:message key="global.form.xoa"/></a>
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
function DeleteAttMaster(cableTypeId) {
	var cableTypeId = encodeURI(cableTypeId);
	var r = confirm('<fmt:message key="messsage.confirm.delete"/>');
	if (r == true) {
		$.getJSON("${pageContext.request.contextPath}/alarm/cable-type/ajax/deleteCableType.htm", {cableTypeId: cableTypeId}, function(j){
			if (j==-1) {
				alert("Loại Cable đã được sử dụng. Bạn không thể xóa");
			} else if (j==1) {
				$("#cableType").submit();
			}
		});
	}
}
</script>