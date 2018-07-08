<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title><fmt:message key="sidebar.admin.deNghiXuat"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.deNghiXuat"/></content>
<form style="padding-top:10px">
	<table style="width:100%">
		<tr>
			<td>
				<div style="overflow:auto;">
					<display:table name="${proposedWarehouse}" class="simple2" id="proposedWarehouse" requestURI="" pagesize="100" sort="external" defaultsort="1" export="true">
					  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px !important;"> <c:out value="${proposedWarehouse_rowNum}" /> </display:column>
						<display:column property="asTypesId" titleKey="proposedWarehouse.asTypesId" sortable="true" sortName="AS_TYPES_ID"/>
						<display:column property="productName" titleKey="proposedWarehouse.productName" sortable="true" sortName="PRODUCT_NAME"/>				
						<display:column property="productCode" titleKey="proposedWarehouse.productCode" sortable="true" sortName="PRODUCT_CODE"/>
						<display:column property="serialNo" titleKey="proposedWarehouse.serialNo" sortable="true" sortName="SERIAL_NO"/>
						<display:column property="soLuong" titleKey="proposedWarehouse.soLuong" headerClass="hide" class="hide" sortable="true" sortName="SO_LUONG"/>
						
						<display:column titleKey="proposedWarehouse.soLuong" media="html">
							<input style="width:30px; font-size:12px;" type = "text"  value="${proposedWarehouse.soLuong}" 
							id = "soluong_${proposedWarehouse_rowNum}" onkeypress="blockString()">
						</display:column>
											
						<display:column property="donVi" titleKey="proposedWarehouse.donVi" sortable="true" sortName="DON_VI"/>  
						<display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style="width:90px !important;"> 
					 			<a href="delete.htm?id=${proposedWarehouse.id}"
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
			</td>
		</tr>
	</table> 
</form>

<div>
	<form > 
		<table style = "width:100%">
			<tr>
				<td style="width:80px"><fmt:message key="proposedWarehouse.boPhanSd"/></td>
				<td><input id="boPhanSd" name="boPhanSd" value="${boPhanSd}" class="wid90" maxlength="90"/></td>
				
				<td style="width:80px"><fmt:message key="proposedWarehouse.donViSd"/></td>
				<td><input id="donViSd" name="donViSd" value="${donViSd}" class="wid90" maxlength="90"/></td>
				
				<td style="width:80px"><fmt:message key="proposedWarehouse.nguoiSd"/></td>
				<td><input id="nguoiSd" name="nguoiSd" value="${nguoiSd}" class="wid90" maxlength="90"/></td> 
				
				<td style="width:80px"><fmt:message key="proposedWarehouse.ngayXuat"/></td>
				<td><input type = "text" id="ngayXuat" name="ngayXuat" value="${ngayXuat}" class="wid80" maxlength="10"/></td>
				<td></td>
			</tr>
			
			<tr> 
				<td style="width:80px"><fmt:message key="proposedWarehouse.lyDoXuat"/></td>
				<td colspan="3"><input id="lyDoXuat" name="lyDoXuat" value="${lyDoXuat}" class="wid95" maxlength="90"/></td>
				
				<td style="width:80px"><fmt:message key="proposedWarehouse.description"/></td>
				<td colspan="3"><input id="description" name="description" value="${description}" class="wid95" maxlength="90"/></td>
				
				<td align="right"><input id = "btnSave" type="button" class="button" name="save" value="<fmt:message key="button.dieuchinh"/>" /></td>   
			</tr>
		</table>
	</form>
</div>

<div style="padding-top:4px">
	<table>
		<tr> 
			<td>
				<input type = "text" value = "${row_num}" style="display: none;" id = "txtRowNum"/>
				<input type = "text" value = "${rowId}" style="display: none;" id = "rowId"/>
				<div style="margin-left:600px;display: none;" class="loading fl">
						<img src="${pageContext.request.contextPath}/images/icon/barIndicator.gif">
					</div>
			</td>
		</tr> 
	</table> 
</div> 

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script type="text/javascript"> $(".chzn-select").chosen(); $(".chzn-select-deselect").chosen({allow_single_deselect:true}); </script>

 <SCRIPT type="text/javascript">
 function blockString() {
	 if (window.event.keyCode < 48 || 57 < window.event.keyCode)
		 alert(window.event.keyCode);
	 //nếu phím không phải là số thì bỏ đi
	 window.event.keyCode = 0;
 }
 </SCRIPT>
 
<script type="text/javascript">
$(function() {
	$( "#ngayXuat" ).datepicker({
		dateFormat: "dd/mm/yy",
		showOn: "button",
		buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
		buttonImageOnly: true
	});
});
</script>  

<script type="text/javascript"> 
$("#btnSave").live("click", function(event){
	$(".loading").css('display', 'block');
	var rowId = $("#rowId").val();
	var row_num = $("#txtRowNum").val(); 
	var boPhanSd = "",donviSd = "", nguoiSd = "",ngayXuat = "",lyDoXuat = "", description = "";
		
		boPhanSd = $("#boPhanSd").val();
		donviSd = $("#donViSd").val();
		nguoiSd = $("#nguoiSd").val();
		ngayXuat = $("#ngayXuat").val();
		lyDoXuat = $("#lyDoXuat").val();
		description = $("#description").val();
		
	var soLuong = "";
	for(var i = 1; i <= row_num; i++)
	{ 
		if($("#soluong_"+i).val() != "" || $("#soluong_"+i).val() != null){
			soLuong = soLuong + $("#soluong_"+i).val() + ","; 
		}else{
			soLuong = soLuong + "0,";
		} 
	} 
	
	$.ajax({
		url: "${pageContext.request.contextPath}/assets/de-nghi-xuat-kho/save-de-nghi.htm",
		type:"POST",
		data:{ key_soLuong: soLuong, key_id: rowId, 
			key_boPhanSd: boPhanSd, key_donviSd: donviSd, 
			key_nguoiSd: nguoiSd, key_ngayXuat: ngayXuat, 
			key_lyDoXuat: lyDoXuat, key_description: description},
		error: function(){},
		beforeSend: function(){},
		complete: function(){},
		success: function(data){
		if(data.status == 'success')
		{
			$(".loading").css('display', 'none');
			alert('Gửi đề nghị thành công.Chờ xét duyệt!');
		}else if(data.status == 'false'){
			$(".loading").css('display', 'none');
			alert('Gửi đề nghị thất bại. Không có thiết bị!');
		}else{
			$(".loading").css('display', 'none');
			alert('Gửi đề nghị thất bại. Sai định dạng ngày xuất!');
		}
		window.open('${pageContext.request.contextPath}/assets/de-nghi-xuat-kho/list.htm','_self');
		}
	});
});
</script>