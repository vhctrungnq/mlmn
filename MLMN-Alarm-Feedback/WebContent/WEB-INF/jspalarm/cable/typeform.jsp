<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>Loại cáp</title>
<content tag="heading">Loại cáp</content>

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

<form:form method="POST" commandName="cableType" action="form.htm">
	<form:hidden path="id" />
	<table style="width:500px">	
		<tr>
			<td><fmt:message key="cableType.cableTypeId"/><font color="red">(*)</font></td>
			<td>
				<c:choose>
					<c:when test="${cableType.id == null }">
						<form:input path="cableTypeId" class="wid100" maxlength="50"/>
						<form:errors path="cableTypeId" class="error"/>
						<br/><span class="error" id="cableTypeIdExistsError"></span>
					</c:when>
					<c:otherwise>
						<b>${cableType.cableTypeId}</b>
						<form:hidden path="cableTypeId"/>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cableType.cableTypeName"/><font color="red">(*)</font></td>
			<td>
				<form:input path="cableTypeName" class="wid100" maxlength="250"/>
				<form:errors path="cableTypeName" class="error"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cableType.description"/></td>
			<td>
				<form:textarea path="description" class="wid100" maxlength="250"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cableType.ordering"/></td>
			<td>
				<form:input path="ordering" style="width:130px; text-align:center" maxlength="10"/>
				<form:errors path="ordering" class="error"/>
			</td>
		</tr>
		
		<tr>
			<td></td>
			<td>
				<input class="button" type="submit" class="button" id="save" name="save" value="<fmt:message key="global.form.luulai"/>" />
               	<input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="<%=request.getContextPath() %>/alarm/cable-type/list.htm"'>
			</td>
		</tr>
	</table>
</form:form>

<script>
function checkCableTypeId() {
	var cableTypeId = encodeURI($("#cableTypeId").val());
	
	$.getJSON("${pageContext.request.contextPath}/alarm/cable-type/ajax/getCableTypeByCableTypeId.htm", {cableTypeId: cableTypeId}, function(j){
		if (j.id!=null) {
			$("#cableTypeIdExistsError").text("Mã đã được sử dụng");
		} else {
			$("#cableTypeIdExistsError").text("");
		}
	});
}
$('#cableTypeId').change(function() {
	checkCableTypeId();
});

$("#cableType").submit(function() {
	checkCableTypeId();
	var cableTypeIdExistsError = $("#cableTypeIdExistsError").text();
	
	if (cableTypeIdExistsError == '') {
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