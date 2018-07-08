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
select {
    height: 23px;
}
td {
    padding: 1px 0;
}

</style>

<form:form method="POST" commandName="cableLineAttributes" action="form.htm">
	<form:hidden path="id"/>
	<table style="width:500px">
		<tr>
			<td style="width:150px"><fmt:message key="cableLineAttributes.cableLineId"/><font color="red">(*)</font></td>
			<td>
				<select id="cableLineId" name="cableLineId" class="wid100">
					<option value="">Chọn</option>
					<c:forEach var="item" items="${cableList}">
						<c:choose>
							<c:when test="${cableLineAttributes.cableLineId == item.cableId }">
								<option value="${item.cableId}" selected="selected">${item.cableId}</option>
							</c:when>
							<c:otherwise>
								<option value="${item.cableId}">${item.cableId}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
				<br/><form:errors path="cableLineId" class="error"/>	
			</td>
		</tr>
		
		<tr>
			<td class="pdl10" style="min-width:35px;"><fmt:message key="cableLineAttributes.fieldColumnKey"/><font color="red">(*)</font></td>
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
				<br/><form:errors path="fieldColumnKey" class="error"/>	
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cableLineAttributes.cableTypeId"/><font color="red">(*)</font></td>
			<td>
				<select id="cableTypeId" name="cableTypeId" class="wid100">
					<option value="">Chọn</option>
					<c:forEach var="item" items="${cableTypeList}">
						<c:choose>
							<c:when test="${cableLineAttributes.cableTypeId == item.cableTypeId }">
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
			<td><fmt:message key="cableLineAttributes.label"/><font color="red">(*)</font></td>
			<td>
				<select id="label" name="label" class="wid100">
					<option value="">Chọn</option>
					<c:forEach var="item" items="${labelList}">
						<c:choose>
							<c:when test="${cableLineAttributes.label == item.label }">
								<option value="${item.label}" selected="selected">${item.label}</option>
							</c:when>
							<c:otherwise>
								<option value="${item.label}">${item.label}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
				<br/><form:errors path="label" class="error"/>	
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cableLineAttributes.value"/></td>
			<td>
				<form:input type ="text" path="value" maxlength="250" class="wid100"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cableLineAttributes.ordering"/></td>
			<td>
				<form:input type ="text" path="ordering" style="width:160px; text-align:center" maxlength="9"/>
				<form:errors path="ordering" class="error"/>
			</td>
		</tr>
		
		<tr>
			<td></td>
			<td >
				<input class="button" type="submit" class="button" id="save" name="save" value="<fmt:message key="global.form.luulai"/>" />
               	<input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="<%=request.getContextPath() %>/alarm/cable-att/list.htm"'>
			</td>
		</tr>
		
	</table>
</form:form>

<script type="text/javascript">
$('#cableLineId').change(function() {
	getData();
});

$('#cableTypeId').change(function() {
	getData();
});

$('#fieldColumnKey').change(function() {
	getData();
});

$('#label').change(function() {
	var cableLineId = encodeURI($("#cableLineId").val());
	var cableTypeId = encodeURI($("#cableTypeId").val());
	var fieldColumnKey = $('#fieldColumnKey').val();

	$.getJSON("${pageContext.request.contextPath}/alarm/cable/ajax/getAttByBscAndCabletype.htm",
		{cableTypeId: cableTypeId, cableId: cableLineId, fieldColumnKey: fieldColumnKey}, 
		function(data){
			var label = $("#label").val();
			
			for (var i=0; i<data.length; i++) {
				if (data[i].label = label) {
					$('#value').val((data[i].value == null)?'':data[i].value);
					break;
				}
			}
		}
	);
});

$(document).ready(function(){
	var label = '<c:out value="${cableLineAttributes.label}"/>';
	if (label != '') {
		$('#label').html('<option value="' + label + '" selected="selected">' + label + '</option>');
	} else {
		$('#label').html('<option value="">Chọn</option>');
	}
	getData();	
});
</script>

<script type="text/javascript">
	function getData() {
		var cableLineId = encodeURI($("#cableLineId").val());
		var cableTypeId = encodeURI($("#cableTypeId").val());
		var fieldColumnKey = $('#fieldColumnKey').val();

		$.getJSON("${pageContext.request.contextPath}/alarm/cable/ajax/getAttByBscAndCabletype.htm",
			{cableTypeId: cableTypeId, cableId: cableLineId, fieldColumnKey: fieldColumnKey}, 
			function(data){
				var status = 0;
				var options = '<option value="">Chọn</option>';
				for (var i=0; i<data.length; i++) {
					if (data[i].label == '<c:out value="${cableLineAttributes.label}"/>' && $("#label").val() == data[i].label) {
						options += '<option value="' + data[i].label + '" selected="selected">' + data[i].label+ '</option>';
						$('#value').val((data[i].value == null)?'':data[i].value);
						status = 1;
					} else {
						options += '<option value="' + data[i].label + '">' + data[i].label+ '</option>';
					}
				}
				if (status == 0) {
					$('#value').val('');
				}
				$('#label').html(options);
			}
		);
	}
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