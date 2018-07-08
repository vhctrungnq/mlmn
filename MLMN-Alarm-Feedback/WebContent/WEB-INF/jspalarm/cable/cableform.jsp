<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>Cáp truyền dẫn</title>
<content tag="heading">Cáp truyền dẫn</content>

<style>
	.wid40 {
		width:40%;
	}
	.wid50 {
		width:50%;
	}
	.wid100 {
		width:100%;
	}
	td {
	    padding: 1px 0;
	}
	.error {
		color:red;
	}
	.editbox {
		display:none
	}
	.editbox {
		background-color: #FAFAFA;
	    border: 1px solid #DDDDDD;
	    border-radius: 2px 2px 2px 2px;
	    box-shadow: 1px 0 1px rgba(0, 0, 0, 0.1) inset;
	    font-size: 14px;
	    padding: 4px;
	    width: 270px;
	}
	/*.edit_tr:hover {
		background:url(${pageContext.request.contextPath}/images/edit.png) right no-repeat #80C8E5 !important;
		cursor:pointer;
	}*/
	.edit_td, .delete_td {
	    width: 16px;
	    cursor:pointer;
	}
</style>

<div class="fl wid50" style="border-right:1px solid #ddd">
<form:form method="POST" commandName="cableLine" action="form.htm">
	<table class="wid100" style="padding-right: 15px;">
		<tr>
			<td style="width:20%; min-width:170px;"><form:hidden path="id"/></td>
			<td></td>
		</tr>
		<tr>
			<td><fmt:message key="cableLine.cableLineId"/><font color="red">(*)</font></td>
			<td style="max-width: 400px; word-wrap: break-word;">
				<c:choose>
					<c:when test="${cableLine.id == null }">
						<form:input type ="text" path="cableLineId" class="wid100" maxlength="250"/>
						<br/>
						<span class="error" id="cableLineIdExistsError">
							<form:errors path="cableLineId" class="error"/>
						</span>
					</c:when>
					<c:otherwise>
						<b>${cableLine.cableLineId}</b>
						<form:hidden path="cableLineId"/>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		
		<tr>
			<td id="sBscTitle"><fmt:message key="cableLine.sBsc"/><font color="red">(*)</font></td>
			<td>
				<form:input type ="text"  path="sBsc" class="wid50 detail" maxlength="250"/>
				<br/><form:errors path="sBsc" class="error"/>
			</td>
		</tr>
		
		<tr id="transmission1Title">
			<td><fmt:message key="cableLine.transmission1"/></td>
			<td>
				<form:input type ="text" path="transmission1" class="wid100 detail" maxlength="250"/>
				<br/><form:errors path="transmission1" class="error"/>
			</td>
		</tr>
		
		<tr id="location1Title">
			<td><fmt:message key="cableLine.location1"/></td>
			<td><form:input type ="text" path="location1" class="wid100 detail" maxlength="250"/></td>
		</tr>
		
		<tr id="transmission2Title">
			<td><fmt:message key="cableLine.transmission2"/></td>
			<td><form:input type ="text" path="transmission2" class="wid100 detail" maxlength="250"/></td>
		</tr>
		
		<tr id="location2Title">
			<td><fmt:message key="cableLine.location2"/></td>
			<td><form:input type ="text" path="location2" class="wid100 detail" maxlength="250"/></td>
		</tr>
		
		<tr id="transmission3Title">
			<td><fmt:message key="cableLine.transmission3"/></td>
			<td><form:input type ="text" path="transmission3" class="wid100 detail" maxlength="250"/></td>
		</tr>
		
		<tr id="location3Title">
			<td><fmt:message key="cableLine.location3"/></td>
			<td><form:input type ="text" path="location3" class="wid100 detail" maxlength="250"/></td>
		</tr>
		
		<tr id="transmission4Title">
			<td><fmt:message key="cableLine.transmission4"/></td>
			<td><form:input type ="text" path="transmission4" class="wid100 detail" maxlength="250"/></td>
		</tr>
		
		<tr id="location4Title">
			<td><fmt:message key="cableLine.location4"/></td>
			<td><form:input type ="text" path="location4" class="wid100 detail" maxlength="250"/></td>
		</tr>
		
		<tr id="transmission5Title">
			<td><fmt:message key="cableLine.transmission5"/></td>
			<td><form:input type ="text" path="transmission5" class="wid100 detail" maxlength="250"/></td>
		</tr>
		
		<tr id="location5Title">
			<td><fmt:message key="cableLine.location5"/></td>
			<td><form:input type ="text" path="location5" class="wid100 detail" maxlength="250"/></td>
		</tr>
		
		<tr id="transmission6Title">
			<td><fmt:message key="cableLine.transmission6"/></td>
			<td><form:input type ="text" path="transmission6" class="wid100 detail" maxlength="250"/></td>
		</tr>
		
		<tr id="location6Title">
			<td><fmt:message key="cableLine.location6"/></td>
			<td><form:input type ="text" path="location6" class="wid100 detail" maxlength="250"/></td>
		</tr>
		
		<tr id="eBscTitle">
			<td><fmt:message key="cableLine.eBsc"/><font color="red">(*)</font></td>
			<td>
				<form:input type ="text" path="eBsc" class="wid50 detail" maxlength="250"/>
				<br/><form:errors path="eBsc" class="error"/>	
			</td>
		</tr>
		
		<tr>
			<td></td>
			<td>
				<input class="button" type="submit" class="button" id="save" name="save" value="<fmt:message key="global.form.luulai"/>" />
               	<input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="<%=request.getContextPath() %>/alarm/cable/list.htm"'>
			</td>
		</tr>
	</table>
</form:form>
</div>
<div class="wid50">
	<table class="wid100" style="padding-left: 15px;">
		<tr>
			<td colspan="4">
				<input type="hidden" id="fieldColumnKey">
			</td>
		</tr>
		<tr>
			<td colspan="4"  style="position: relative; width: -moz-fit-content;">
			<div style="float:left">Thông tin chi tiết:&nbsp;</div><div id="cCableLineId" style="word-wrap: break-word; max-width: 600px;float:left;font-weight:bold;"></div>
			</td>
		</tr>
		<tr>
			<td style="width:20%; min-width:80px"><span id="transmissionTitle"></span></td>
			<td style="max-width: 200px; word-wrap: break-word;"><span id="cTransmission"></span></td>
			<td style="width:45px; text-align:center;">Loại</td>
			<td style="width:200px;">
				<select id="cableTypeId" name="cableTypeId" class="wid100">
					<option value="">Chọn</option>
					<c:forEach var="item" items="${cableTypeList}">
						<option value="${item.cableTypeId}">${item.cableTypeId}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<table id="resultAtt" class="simple2" style="margin-top:15px;">
				</table>
			</td>
		</tr>
	</table>
</div>

<script>
$('#cableLineId').change(function() {
	var cableLineId = encodeURI($("#cableLineId").val());
	
	$.getJSON("${pageContext.request.contextPath}/alarm/cable/ajax/getCableLineByCablelineId.htm", {cableLineId: cableLineId}, function(j){
		if (j.id!=null) {
			$("#cableLineIdExistsError").text("Tuyến truyền dẫn đã tồn tại");
			$("#cCableLineId").text('');
		} else {
			$("#cableLineIdExistsError").text("");
			$("#cCableLineId").text($("#cableLineId").val());
		}
	});
});

$('#sBsc').change(function() {
	$("#cSBsc").text($("#sBsc").val());
});

$('#cableTypeId').change(function() {
	getCableLineAtt();
});

$("#cableLine").submit(function() {
	var cableLineIdExistsError = $("#cableLineIdExistsError").text();
	
	if (cableLineIdExistsError == '') {
		return true;
	}

	return false;
});

$('.detail').focus(function(){
    var id = $(this).attr('id');
    $('#transmissionTitle').text($('#'+id+'Title').text());
    $('#cTransmission').text($(this).val());

    $('#fieldColumnKey').val(id);

	getCableLineAtt();
});

function getCableLineAtt() {

	if ($('#cTransmission').text() == '') {
		$('#resultAtt').html('');
		return;
	}
	
	var cableLineId = encodeURI($("#cableLineId").val());
	var cableTypeId = encodeURI($("#cableTypeId").val());
	var fieldColumnKey = $('#fieldColumnKey').val();

	$.getJSON("${pageContext.request.contextPath}/alarm/cable/ajax/getAttByBscAndCabletype.htm",
		{cableTypeId: cableTypeId, cableLineId: cableLineId, fieldColumnKey: fieldColumnKey}, 
		function(data){
			var html = '<tr><td colspan="4" style="background: #f1f1f1"><b>Thuộc tính cáp</b></td></tr>';
			for (var i=0; i<data.length; i++) {
				var value = (data[i].value == null)?'':data[i].value;
				html += '<tr id="'+i+'" class="edit_tr">';
				html += '<td class=dpnone><input id="att_'+i+'" type="hidden" value="'+data[i].id+'"></td>';
				html += '<td class="wid40"><span id=first_'+i +' class="text">'+data[i].label+'</span>';
				html += '<input type="text" value="'+data[i].label+'" class="editbox" id="first_input_'+i+'"></td>';
				html += '<td><span id=last_'+i +' class="text">'+value+'</span>';
				html += '<input type="text" value="'+value+'" class="editbox" id="last_input_'+i+'"></td>';
				html += '<td class="edit_td"><img src="${pageContext.request.contextPath}/images/edit.png"></td>';
				html += '<td class="delete_td"><img src="${pageContext.request.contextPath}/images/delete.png"></td>';
				html += '</tr>';
			}
			
			$('#resultAtt').html(html);
		}
	);
}

$(document).ready(function(){
	$("#cCableLineId").text($("#cableLineId").val());
	$("#cSBsc").text($("#sBsc").val());
	
	$('#transmissionTitle').text($('#sBscTitle').text());
    $('#cTransmission').text($('#sBsc').val());
	
    $('#fieldColumnKey').val('sBsc');

    $('.delete_td').live('click', function() {
    	var r = confirm('<fmt:message key="messsage.confirm.delete"/>');
    	if (r == true) {
	    	var v_tr = $(this).closest("tr");
	    	var ID = v_tr.attr('id');
	
	    	var attId = $('#att_'+ID).val();
			
			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath}/alarm/cable/ajax/deleteCableLineAtt.htm",
				data: {id: attId},
				cache: false,
				success: function(html) {
	
					getCableLineAtt();
				}
			});
    	}
    });

    $(".edit_td").live('click', function() {

        var v_tr = $(this).closest("tr");
        
		var ID = v_tr.attr('id');
		
		//$("#first_"+ID).hide();
		$("#last_"+ID).hide();
		//$("#first_input_"+ID).show();
		$("#last_input_"+ID).show();
		$("#last_input_"+ID).focus();
		
	});

    $(".edit_tr").live('change', function() {
		var v_tr = $(this).closest("tr");
		
		var ID = v_tr.attr('id');
		var first=$("#first_input_"+ID).val();
		var last=$("#last_input_"+ID).val();

		var attId = $('#att_'+ID).val();
		var cableLineId = encodeURI($("#cableLineId").val());
		var cableTypeId = encodeURI($("#cableTypeId").val());
		var fieldColumnKey = $('#fieldColumnKey').val();

		var label = encodeURI($("#first_input_"+ID).val());
		var value = encodeURI($("#last_input_"+ID).val());
		//$("#first_"+ID).html('<img src="load.gif" />');
	
	
		if(first.length && last.length>0) {
			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath}/alarm/cable/ajax/saveCableLineAtt.htm",
				data: {id: attId, cableLineId: cableLineId, cableTypeId: cableTypeId, fieldColumnKey: fieldColumnKey, label: label, value: value },
				cache: false,
				success: function(html) {

					if (html != 0) {
						$('#att_'+ID).val(html);
					}
					
					$("#last_"+ID).html(last);
					
					$(".editbox").hide();
					$(".text").show();
				}
			});
		}
		else {
			alert('Enter something.');
		}
	
	});
	
	$(".editbox").mouseup(function() {
		$(this).show();
		return false;
	});

	$(document).mouseup(function() {
		$(".editbox").hide();
		$(".text").show();
	});
});

</script>