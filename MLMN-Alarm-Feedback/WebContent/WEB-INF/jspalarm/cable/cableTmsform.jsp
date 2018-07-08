<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- <title>Cáp truyền dẫn</title>
<content tag="heading">Cáp truyền dẫn</content>--%>
<title>${titleF}</title>
<content tag="heading">${titleF}</content> 
<style>
.wid40 {width:40%;}
.wid50 {width:50%;}
.wid100 {width:100%;}
td {padding: 1px 0;}
.error {color:red;}
.editbox {display:none}
.editbox {
	background-color: #FAFAFA;
    border: 1px solid #DDDDDD;
    border-radius: 2px 2px 2px 2px;
    box-shadow: 1px 0 1px rgba(0, 0, 0, 0.1) inset;
    font-size: 14px;
    padding: 4px;
    width: 270px;
}
.edit_td, .delete_td {width: 16px;cursor:pointer;}
.note{color:red; float:right;padding-right: 7px;}
</style>

<div class="fl wid40" style="border-right:1px solid #ddd">
<form:form method="post" commandName="cableTransmission" action="form.htm?typeC=${typeC}">
	<%-- <input type="hidden" name="typeC" id="typeC" value="${typeC}"> --%>
	<table class="wid100" style="padding-right: 15px;">
		<tr>
			<td style="width:20%; min-width:170px;"><form:hidden path="id"/></td>
			<td></td>
		</tr>
		
		<tr id="directionIdTitle">
			<td><fmt:message key="cableTransmission.directionId"/><span class="note">(*)</span></td>
			<td>
				<form:input type ="text" path="directionId" class="wid100" maxlength="250"/>
				<br/><form:errors path="directionId" class="error"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cableTransmission.cableId"/><span class="note">(*)</span></td>
			<td style="max-width: 400px; word-wrap: break-word;">
				<c:choose>
					<c:when test="${cableTransmission.id == null }">
						<form:input type ="text" path="cableId" class="wid100" maxlength="250"/>
						<br/>
						<span class="error" id="cableIdExistsError">
							<form:errors path="cableId" class="error"/>
						</span>
					</c:when>
					<c:otherwise>
						<b>${cableTransmission.cableId}</b>
						<form:hidden path="cableId"/>
						<br/>
						<span class="error" id="cableIdExistsError"></span>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		
		<tr id="flowConnectionTitle">
			<td><fmt:message key="cableTransmission.flowConnection"/><span class="note">(*)</span></td>
			<td>
				<form:input type ="text" path="flowConnection" class="wid100 detail" maxlength="250"/>
				<br/><form:errors path="flowConnection" class="error"/>
			</td>
		</tr>
		
		<tr id="enSourceTitle">
			<td><fmt:message key="cableTransmission.enSource"/><span class="note">(*)</span></td>
			<td>
				<form:input type ="text" path="enSource" class="wid100 detail" maxlength="50"/>
				<br/><form:errors path="enSource" class="error"/>
			</td>
		</tr>
		
		<tr id="cardPortSourceTitle">
			<td><fmt:message key="cableTransmission.cardPortSource"/></td>
			<td>
				<form:input type ="text" path="cardPortSource" class="wid100 detail" maxlength="50"/>
				
			</td>
		</tr>
		
		<tr id="odfSourceTitle">
			<td>
				<c:choose>
		            <c:when test="${typeC == 'GMSC'}">
		            	<fmt:message key="cableTransmission.odfDdfSource"/>
		            </c:when>
					<c:otherwise>
						<fmt:message key="cableTransmission.odfSource"/>
					</c:otherwise>
				</c:choose>
			</td>
			<td>
				<form:input type ="text" path="odfSource" class="wid100 detail" maxlength="250"/>
				
			</td>
		</tr>
		<c:if test="${typeC == 'BSC' || typeC == 'BSC_MGW'|| typeC == 'NodeTDQH'}">
			<tr id="positionEtTitle">
				<td><fmt:message key="cableTransmission.positionEt"/></td>
				<td>
					<form:input type ="text" path="positionEt" class="wid100 detail" maxlength="50"/>
					
				</td>
			</tr>
			<tr id="cardPort1Title">
				<td><fmt:message key="cableTransmission.cardPort1"/></td>
				<td>
					<form:input type ="text" path="cardPort1" class="wid100 detail" maxlength="50"/>
					
				</td>
			</tr>
			<tr id="odf1Title">
				<td><fmt:message key="cableTransmission.odf1"/></td>
				<td>
					<form:input type ="text" path="odf1" class="wid100 detail" maxlength="250"/>
					
				</td>
			</tr>
			<tr id="cardPort2Title">
				<td><fmt:message key="cableTransmission.cardPort2"/></td>
				<td>
					<form:input type ="text" path="cardPort2" class="wid100 detail" maxlength="50"/>
					
				</td>
			</tr>
			<tr id="odf2Title">
				<td><fmt:message key="cableTransmission.odf2"/></td>
				<td>
					<form:input type ="text" path="odf2" class="wid100 detail" maxlength="250"/>
					
				</td>
			</tr>
		</c:if>
		<c:if test="${typeC == 'IBPP_LTT'}">
			<tr id="odf1Title">
				<td><fmt:message key="cableTransmission.odfOsn1"/></td>
				<td>
					<form:input type ="text" path="odf1" class="wid100 detail" maxlength="250"/>
					
				</td>
			</tr>
			<tr id="odf2Title">
				<td><fmt:message key="cableTransmission.odfOsn2"/></td>
				<td>
					<form:input type ="text" path="odf2" class="wid100 detail" maxlength="250"/>
					
				</td>
			</tr>
		</c:if>
		<tr id="odfVendor1Title">
			<td>
				<c:choose>
		            <c:when test="${typeC == 'GMSC'}">
		               <fmt:message key="cableTransmission.odfDdfVendor1"/>
					</c:when>
					<c:otherwise>
						<fmt:message key="cableTransmission.odfVendor1"/>
					</c:otherwise>
				</c:choose>
			</td>
			<td>
				<form:input type ="text" path="odfVendor1" class="wid100 detail" maxlength="250"/>
				
			</td>
		</tr>
		<tr id="transmissionTitle">
			<td>
				<fmt:message key="cableTransmission.transmission"/>
			</td>
			<td>
				<form:input type ="text" path="transmission" class="wid100 detail" maxlength="250"/>
				
			</td>
		</tr>
		<tr id="odfVendor2Title">
			<td>
				<c:choose>
		            <c:when test="${typeC == 'GMSC'}">
		               <fmt:message key="cableTransmission.odfDdfVendor2"/>
					</c:when>
					<c:otherwise>
						<fmt:message key="cableTransmission.odfVendor2"/>
					</c:otherwise>
				</c:choose>
			</td>
			<td>
				<form:input type ="text" path="odfVendor2" class="wid100 detail" maxlength="250"/>
				
			</td>
		</tr>
		<c:if test="${typeC == 'BSC_MGW'}">
			<tr id="odfReplaceTitle">
				<td>
					<fmt:message key="cableTransmission.odfReplace"/>
				</td>
				<td>
					<form:input type ="text" path="odfReplace" class="wid100 detail" maxlength="250"/>
					
				</td>
			</tr>
	   	</c:if>
	   	<c:if test="${typeC == 'IBPP_LTT'}">
	   		<tr id="odfOsnLtt3Title">
				<td>
					<fmt:message key="cableTransmission.odfOsnLtt3"/>
				</td>
				<td>
					<form:input type ="text" path="odfOsnLtt3" class="wid100 detail" maxlength="250"/>
					
				</td>
			</tr>
			<tr id="odfOsnLtt4Title">
				<td>
					<fmt:message key="cableTransmission.odfOsnLtt4"/>
				</td>
				<td>
					<form:input type ="text" path="odfOsnLtt4" class="wid100 detail" maxlength="250"/>
					
				</td>
			</tr>
		</c:if>
		<tr id="odfDestinationTitle">
			<td><fmt:message key="cableTransmission.odfDestination"/></td>
			<td>
				<form:input type ="text" path="odfDestination" class="wid100 detail" maxlength="250"/>
				
			</td>
		</tr>
		<tr id="cardPortDestinationTitle">
			<td><fmt:message key="cableTransmission.cardPortDestination"/></td>
			<td>
				<form:input type ="text" path="cardPortDestination" class="wid100 detail" maxlength="50"/>
				
			</td>
		</tr>
		
		<tr id="enDestinationTitle">
			<td><fmt:message key="cableTransmission.enDestination"/></td>
			<td>
				<form:input type ="text" path="enDestination" class="wid100 detail" maxlength="50"/>
				
			</td>
		</tr>
		
		<tr id="vendorTitle">
			<td><fmt:message key="cableTransmission.vendor"/></td>
			<td>
				<form:input type ="text" path="vendor" class="wid100 detail" maxlength="50"/>
				
			</td>
		</tr>
		
		<tr id="bandwith">
			<td><fmt:message key="cableTransmission.bandwith"/></td>
			<td>
				<form:input type ="text" path="bandwith" class="wid100 detail" maxlength="250"/>
				
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cableTransmission.description"/></td>
			<td>
				<form:textarea path="description" style="height: 50px" class="wid100" maxlength="900"/>
			</td>
		</tr>
		
		<tr>
			<td></td>
			<td>
				<input type="submit" class="button" id="save"  value="<fmt:message key="global.form.luulai"/>" />
               	<input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="<%=request.getContextPath() %>/alarm/cable/list.htm?typeC=${typeC}"'>
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
			<div style="float:left">Thông tin chi tiết:&nbsp;</div><div id="cCableId" style="word-wrap: break-word; max-width: 600px;float:left;font-weight:bold;"></div>
			</td>
		</tr>
		<tr>
			<td style="width:20%; min-width:80px"><span id="transmissionTit"></span></td>
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

<script type="text/javascript">

$('#cableId').change(function() {

	checkExistsCable();
	
	$("#cCableId").text($("#cableId").val());
});

$('#directionId').change(function() {
	checkExistsCable();
});

$('#flowConnection').change(function() {
	checkExistsCable();
});

$('#enSource').change(function() {
	checkExistsCable();
});

$('#enDestination').change(function() {
	checkExistsCable();
});

$('#cableTypeId').change(function() {
	getCableLineAtt();
});

/*$("#cableTransmission").submit(function() {
	var cableIdExistsError = $("#cableIdExistsError").text();
	
	if (cableIdExistsError == '') {
		return true;
	}

	return false;
});*/

$("#directionId").autocomplete({
	source: '${pageContext.request.contextPath}/alarm/cable/ajax/get-CableTransmission-inf-search.htm?focus=directionId&typeC=${typeC}',
});

$("#cableId").autocomplete({
	source: '${pageContext.request.contextPath}/alarm/cable/ajax/get-CableTransmission-inf-search.htm?focus=cableId&typeC=${typeC}',
});

$("#vendor").autocomplete({
	source: '${pageContext.request.contextPath}/alarm/cable/ajax/get-CableTransmission-inf-search.htm?focus=vendor&typeC=${typeC}',
});

$("#enSource").autocomplete({
	source: '${pageContext.request.contextPath}/alarm/cable/ajax/get-CableTransmission-inf-search.htm?focus=enSource&typeC=${typeC}',
});

$('.detail').focus(function(){
    var id = $(this).attr('id');
    $('#transmissionTit').text($('#'+id+'Title').text());
    $('#cTransmission').text($(this).val());

    $('#fieldColumnKey').val(id);

	getCableLineAtt();
});

function checkExistsCable() {
	var directionId 	= encodeURI($("#directionId").val());
	var cableId 		= encodeURI($("#cableId").val());
	var flowConnection 	= encodeURI($("#flowConnection").val());
	var enSource 		= encodeURI($("#enSource").val());
	var enDestination 	= encodeURI($("#enDestination").val());

	$.getJSON("${pageContext.request.contextPath}/alarm/cable/ajax/checkExistsCableTms.htm", 
			{directionId: directionId, cableId: cableId, flowConnection: flowConnection, enSource: enSource, enDestination: enDestination}, function(j){
				
		if (j.id!=null && j.id != $('#id').val()) {
			$("#cableIdExistsError").text("Tuyến truyền dẫn đã tồn tại");
			$("#cCableId").text('');
		} else {
			$("#cableIdExistsError").text("");
			$("#cCableId").text($("#cableId").val());
		}
	});
}

function getCableLineAtt() {

	if ($('#cTransmission').text() == '') {
		$('#resultAtt').html('');
		return;
	}
	
	var cableId = encodeURI($("#cableId").val());
	var cableTypeId = encodeURI($("#cableTypeId").val());
	var fieldColumnKey = $('#fieldColumnKey').val();

	$.getJSON("${pageContext.request.contextPath}/alarm/cable/ajax/getAttByBscAndCabletype.htm",
		{cableTypeId: cableTypeId, cableId: cableId, fieldColumnKey: fieldColumnKey}, 
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
	$("#cCableId").text($("#cableId").val());


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
		
		$("#last_"+ID).hide();
		
		$("#last_input_"+ID).show();
		$("#last_input_"+ID).focus();
		
	});

	$(".edit_tr").live('change', function() {
		var v_tr = $(this).closest("tr");
		
		var ID = v_tr.attr('id');
		var first=$("#first_input_"+ID).val();
		var last=$("#last_input_"+ID).val();

		var attId = $('#att_'+ID).val();
		var cableId = encodeURI($("#cableId").val());
		var cableTypeId = encodeURI($("#cableTypeId").val());
		var fieldColumnKey = $('#fieldColumnKey').val();

		var label = encodeURI($("#first_input_"+ID).val());
		var value = encodeURI($("#last_input_"+ID).val());
	
	
		if(first.length && last.length>0) {
			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath}/alarm/cable/ajax/saveCableLineAtt.htm",
				data: {id: attId, cableLineId: cableId, cableTypeId: cableTypeId, fieldColumnKey: fieldColumnKey, label: label, value: value },
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