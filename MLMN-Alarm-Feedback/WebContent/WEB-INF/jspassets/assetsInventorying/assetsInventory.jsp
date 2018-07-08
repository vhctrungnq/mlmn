<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title><fmt:message key="sidebar.admin.assetsInventorying"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.assetsInventorying"/></content>
<div>
<form  method="post" action="kiem-ke.htm"> 
	<table style = "width:50%">
		<tr>   
			<td style="width:80px"><fmt:message key="assetsManagement.asTypesName" /></td>
			<td>
				<select name ="asTypesId"  id="asTypesId" style="width:150px" onchange="xl()"> 
						<option value=""><fmt:message key="global.All"/></option>
		   				<c:forEach var="items" items="${asTypesIDList}">
			              	<c:choose>
			                <c:when test="${items.asTypesId == asTypesId}">
			                    <option value="${items.asTypesId}" selected="selected">${items.asTypesName}</option>		                </c:when>
			                <c:otherwise>
			                    <option value="${items.asTypesId}">${items.asTypesName}</option>
			                </c:otherwise>
			              	</c:choose>
				    	</c:forEach>
				</select> 
			</td>
			
			<td style="width:80px;padding-left:40px"><fmt:message key="asExportWarehouse.productCode" /></td>
			<td >
				<input type="text" value="${productCode}" id ="productCode" name="productCode" class="wid90"/>
			</td> 
			
			<td align="right"><input class="button" type="submit" class="button" name="filter"
								value="<fmt:message key="global.form.timkiem"/>" /></td> 
		</tr>
	</table>
</form>
</div>

<form style="padding-top:10px">
	<fieldset>
		<legend>Nhập thông tin kiểm kê</legend>
			<table style="width:50%">
				<tr>
					<td style="width:80px"><fmt:message key="assetsInventorying.dotKiemKe"/></td>
					<td>  
					     <input id="dotKiemKe" name="dotKiemKe" value="${dotKiemKe}" style="width:150px" maxlength="90"/>
					</td>
					
					<td style="width:80px"><fmt:message key="assetsInventorying.ngayKiemKe"/></td>
					<td>  
					    <input id="ngayKiemKe" name="ngayKiemKe" value="${ngayKiemKe}" class="wid50" maxlength="10"/>
						<img alt="calendar" title="Chọn ngày kiểm kê" id="chooseNgayKiemKe" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
					</td>
				</tr>
			</table>
			
			<table style="width:100%">
				<tr>
				<td>
					<div style="overflow:auto;">
						<display:table name="${assetsInventorying}" class="simple2" id="item" requestURI="" pagesize="10" sort="external" defaultsort="1" export="true" >
							<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item_rowNum}"/> </display:column>
							<display:column property="asTypesName" titleKey="assetsManagement.asTypesName" sortable="true" sortName="AS_TYPES_NAME"/>
							<display:column property="productCode" titleKey="assetsInventorying.productCode" sortable="true" sortName="PRODUCT_CODE"/>
							<display:column property="productName" titleKey="assetsInventorying.productName" sortable="true" sortName="PRODUCT_NAME"/> 	
							<display:column property="serialNo" titleKey="assetsInventorying.serialNo" sortable="true" sortName="SERIAL_NO"/>
							<display:column property="dangSd" titleKey="assetsInventorying.dangSD" sortable="true" sortName="DANG_SD"/>
							<display:column property="koSd" titleKey="assetsInventorying.khongSD" sortable="true" sortName="KO_SD"/>
							<display:column property="baoHanh" titleKey="assetsInventorying.baoHanh" sortable="true" sortName="BAO_HANH"/>
							<display:column property="amount" titleKey="assetsInventorying.ss" sortable="true" sortName="AMOUNT"/>
							
							<display:column titleKey="assetsInventorying.amount">
								<input style="width:30px; font-size:12px;" type = "text"  value="${item.kiemke}" id = "kiemke_${item_rowNum}" >
							</display:column>
							
							<%-- <display:column property="lech" titleKey="assetsInventorying.lech" sortable="true" sortName="UNIT"/> --%>
							<display:column property="unit" titleKey="assetsInventorying.unit" sortable="true" sortName="UNIT"/>
							<display:column property="importDate" titleKey="assetsInventorying.importDate" format="{0,date,dd/MM/yyyy}" sortable="true" sortName="IMPORT_DATE"/>
							<display:column property="organization" titleKey="asExportWarehouse.organization" sortable="true" sortName="ORGANIZATION"/>
							<display:column property="departmentId" titleKey="asExportWarehouse.departmentId" sortable="true" sortName="DEPARTMENT_ID"/>
							<display:column property="users" titleKey="assetsInventorying.users" sortable="true" sortName="USERS"/>
							<display:column property="description" titleKey="assetsInventorying.description" sortable="true" sortName="DESCRIPTION"/>
							
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
	</fieldset>
</form>


<div style="padding-top:4px">
	<table>
		<tr>
			<c:choose>
				<c:when test="${edit==true}">
					<input id = "btnSave" type="button" class="button" name="save" value="<fmt:message key="button.save"/>" />&nbsp;
					<input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm"'>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
		</tr>
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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<link type="text/css" rel="Stylesheet" href="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.8.23.custom.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/assets/style.css" />

<script type="text/javascript">
Calendar.setup({
    inputField		:	"ngayKiemKe",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseNgayKiemKe",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
}); 
</script>  
<script type="text/javascript"> 
$("#btnSave").live("click", function(event){
	$(".loading").css('display', 'block');
	var rowId = $("#rowId").val();
	var row_num = $("#txtRowNum").val(); 
	var dotKiemKe = "";
		dotKiemKe = $("#dotKiemKe").val(); 
	var ngayKiemKe = "";
		ngayKiemKe = $("#ngayKiemKe").val(); 
		
	var kiemke = "";
	for(var i = 1; i <= row_num; i++)
	{ 
		if($("#kiemke_"+i).val() != "" || $("#kiemke_"+i).val() != null){
			kiemke = kiemke + $("#kiemke_"+i).val() + ","; 
		}else{
			kiemke = kiemke + "0,";
		} 
	} 
	
	$.ajax({
		url: "${pageContext.request.contextPath}/assets/kiem-ke-tai-san/form/save-kiemke.htm",
		type:"POST",
		data:{ key_kiemke: kiemke, key_id: rowId, 
			key_dotKiemKe: dotKiemKe, key_ngayKiemKe: ngayKiemKe},
		error: function(){},
		beforeSend: function(){},
		complete: function(){},
		success: function(data){
		if(data.status == 'success')
		{
			$(".loading").css('display', 'none');
			alert('Cập nhật thành công');
		}
		/* window.open('${pageContext.request.contextPath}/assets/kiem-ke-tai-san/kiem-ke.htm?insert=0','_self'); */
		}
	});
});
</script>
<script type="text/javascript">
$(function() {
	var availableTags = [];
	var i = 0;
	<c:forEach items="${productCodeList}" var="listOfNames">
		availableTags[i] = "<c:out value='${listOfNames}' escapeXml='false' />";
		i = i + 1;
	</c:forEach>
	loadBsc(availableTags);
}); 

function loadBsc(availableTags){
	function split( val ) {
	return val.split( /,\s*/ );
	}
	function extractLast( term ) {
	return split( term ).pop();
	}
	$( "#productCode" )
	// don't navigate away from the field on tab when selecting an item
	.bind( "keydown", function( event ) {
	if ( event.keyCode === $.ui.keyCode.TAB &&
	$( this ).data( "autocomplete" ).menu.active ) {
	event.preventDefault();
	}
	})
	.autocomplete({
	minLength: 0,
	source: function( request, response ) {
	// delegate back to autocomplete, but extract the last term
	response( $.ui.autocomplete.filter(
	availableTags, extractLast( request.term ) ) );
	},
	focus: function() {
	// prevent value inserted on focus
	return false;
	},
	select: function( event, ui ) {
	var terms = split( this.value );
	// remove the current input
	terms.pop();
	//check and add the selected item
	var temp = ui.item.value;
	var bscTp = $("#productCode").val();
	var bscL = bscTp.split(",");
	var kt = false;
	for (var i=0; i<bscL.length; i++) {
		if (temp==bscL[i])
			kt=true;
	}
	if (kt==false)
	{
		terms.push( ui.item.value );
		// add placeholder to get the comma-and-space at the end
		terms.push( "" );
		this.value = terms.join( "," );
	}
	return false;
	}
	});
}	 
</script>
