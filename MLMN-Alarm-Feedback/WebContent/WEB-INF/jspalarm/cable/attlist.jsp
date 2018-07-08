<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>Danh sách thuộc tính cáp</title>
<content tag="heading">Danh sách thuộc tính cáp</content>

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
<form:form commandName="cableLineAttributes" method="POST">
	<table>
		<tr>
			<td style="min-width:35px;"><fmt:message key="cableLineAttributes.cableLineId"/></td>
			<td>
				<form:input path="cableLineId" class="wid100"/>
			</td>
			
			<td class="pdl10" style="min-width:35px;"><fmt:message key="cableLineAttributes.cableTypeId"/></td>
			<td>
				<form:input path="cableTypeId" class="wid100"/>
			</td>
			
			<td class="pdl10" style="min-width:35px;"><fmt:message key="cableLineAttributes.label"/></td>
			<td>
				<form:input path="label" class="wid100"/>
			</td>
		</tr>
		<tr>
			<td style="min-width:35px;"><fmt:message key="cableLineAttributes.value"/></td>
			<td>
				<form:input path="value" class="wid100"/>
			</td>
			
			<td class="pdl10" style="min-width:35px;"><fmt:message key="cableLineAttributes.fieldColumnKey"/></td>
			<td>
				<select id="fieldColumnKey" name="fieldColumnKey" class="wid100">
					<option value="">Chọn</option>
					<c:forEach var="item" items="${fieldKeyList}">
						<c:choose>
							<c:when test="${cableLineAttributes.fieldColumnKey == item.value }">
								<option value="${item.value}" selected="selected">${item.name}</option>
							</c:when>
							<c:otherwise>
								<option value="${item.value}">${item.name}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
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
	<display:table name="${cableLineAttributesList}" class="simple2" id="item" requestURI="" export="true" pagesize="15" sort="external" defaultsort="1" >
		<display:column title="STT">
			<c:out value="${item_rowNum}" />
		</display:column>
		
		<display:column property="cableTypeId" titleKey="cableLineAttributes.cableTypeId" sortable="true" sortName="CABLE_TYPE_ID"/>
		<display:column property="cableLineId" titleKey="cableLineAttributes.cableLineId" sortable="true" sortName="CABLE_LINE_ID"/>
		<display:column property="fieldColumnKey" titleKey="cableLineAttributes.fieldColumnKey" sortable="true" sortName="FIELD_COLUMN_KEY"/>
		<display:column property="label" titleKey="cableLineAttributes.label" sortable="true" sortName="LABEL"/>
		<display:column property="value" titleKey="cableLineAttributes.value" sortable="true" sortName="VALUE"/>
		<display:column property="ordering" titleKey="cableLineAttributes.ordering" sortable="true" sortName="ORDERING"/>
		<c:if test="${isManager=='Y' }">
		 	<display:column titleKey="global.management" media="html" class="centerColumnMana">
			<a href="form.htm?id=${item.id}"><fmt:message key="global.form.sua"/></a>&nbsp;
 			<a href="#" onclick="javascript:DeleteCableAtt(${item.id});return false;"><fmt:message key="global.form.xoa"/></a>
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
function DeleteCableAtt(id) {
	var r = confirm('<fmt:message key="messsage.confirm.delete"/>');
	if (r == true) {
		$.getJSON("${pageContext.request.contextPath}/alarm/cable-att/ajax/deleteCableAtt.htm", {id: id}, function(j){
			if (j==-1) {
				alert("Thực hiện xóa bị lỗi");
			} else if (j==1) {
				$("#cableLineAttributes").submit();
			}
		});
	}
}
</script>