<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>Thuộc tính cáp</title>
<content tag="heading">Thuộc tính cáp</content>

<style>
.wid50 {
	width:50%;
}
.wid100 {
	width:100%;
}
td {
    padding: 1px 0;
}
</style>

<form:form method="POST" commandName="cableAttributesMaster" action="form.htm">
	<form:hidden path="id" />
	<table style="width:500px">
		
		<tr>
			<td style="width:70px"><fmt:message key="cableAttributesMaster.label"/><font color="red">(*)</font></td>
			<td>
				<c:choose>
					<c:when test="${cableAttributesMaster.id == null }">
						<form:input type ="text" path="label" class="wid100" maxlength="250"/>
						<form:errors path="label" class="error"/>
						<br/><span class="error" id="labelExistsError"></span>
					</c:when>
					<c:otherwise>
						<b>${cableAttributesMaster.label}</b>
						<form:hidden path="label"/>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cableAttributesMaster.cableTypeId"/><font color="red">(*)</font></td>
			<td style="width:200px;">
				<select id="cableTypeId" name="cableTypeId" class="wid100">
					<option value="">Chọn</option>
					<c:forEach var="item" items="${cableTypeList}">
						<c:choose>
							<c:when test="${cableAttributesMaster.cableTypeId == item.cableTypeId }">
								<option value="${item.cableTypeId}" selected="selected">${item.cableTypeId}</option>
							</c:when>
							<c:otherwise>
								<option value="${item.cableTypeId}">${item.cableTypeId}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
				<br/><form:errors path="cableTypeId" class="error"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cableType.description"/></td>
			<td>
				<form:textarea path="description" class="wid100" maxlength="250"/>
				<form:errors path="description" class="error"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cableType.ordering"/></td>
			<td>
				<form:input type ="text" path="ordering" style="width:130px; text-align:center" maxlength="9"/>
				<form:errors path="ordering" class="error"/>
			</td>
		</tr>
		
		<tr>
			<td></td>
			<td>
				<input class="button" type="submit" class="button" id="save" name="save" value="<fmt:message key="global.form.luulai"/>" />
               	<input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="<%=request.getContextPath() %>/alarm/cable-att-master/list.htm"'>
			</td>
		</tr>
	</table>
</form:form>

<script>
function checkExistLabel() {
	var label = encodeURI($("#label").val());
	var cableTypeId = encodeURI($("#cableTypeId").val());
	
	$.getJSON("${pageContext.request.contextPath}/alarm/cable-att-master/ajax/getAttMasByLabelCabTypeId.htm", {label: label, cableTypeId: cableTypeId}, function(j){
		if (j.id!=null) {
			$("#labelExistsError").text("Mã đã được sử dụng");
		} else {
			$("#labelExistsError").text("");
		}
	});
}
$('#label').change(function() {
	checkExistLabel();
});

$('#cableTypeId').change(function() {
	checkExistLabel();
});

$("#cableAttributesMaster").submit(function() {
	checkExistLabel();
	var labelExistsError = $("#labelExistsError").text();
	
	if (labelExistsError == '') {
		return true;
	}

	return false;
});
</script>

<script type="text/javascript">
$("#ordering").keydown(function(event) {
	if(event.shiftKey)
		event.preventDefault();

	if (event.keyCode == 46 || event.keyCode == 8) {
	}
	else {
		if (event.keyCode < 95) {
			if ((event.keyCode < 48) || (event.keyCode > 57)) {
				event.preventDefault();
			}
		}			
		else {
			if ((event.keyCode < 96) || (event.keyCode > 105)) {
				event.preventDefault();
			}
		}
	}
});
</script>