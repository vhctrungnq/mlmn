<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <!-- Xuat va hoan tra tai san -->
<c:choose>
  <c:when test="${asExportWarehouseAddEdit == 'N'}">
      <title><fmt:message key="sidebar.admin.asExportWarehouseAdd"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.asExportWarehouseAdd"/></content>
  </c:when>
  <c:when test="${asExportWarehouseAddEdit == 'Y'}">
      <title><fmt:message key="sidebar.admin.asExportWarehouseEdit"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.asExportWarehouseEdit"/></content>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>
<body class="section-5"/>
<form:form commandName="asExportWarehouse" method="post" action="form.htm">
	<div><form:input path="id" type="hidden" /></div>
    <table class="simple2">     
    		<tr>
    			<td colspan="4"><span style="font-size:12px;"><b><i><fmt:message key="asExportWarehouse.export"/></i></b></span></td>
    		</tr> 
    		
    		<tr>
				<td><fmt:message key="asExportWarehouse.productCode"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>	
				<td>
					<form:input path="productCode" class="wid30" maxlength="40"/>
					<br/><form:errors path="productCode" cssClass="errorMessages"/>
				</td>
				
				<td><fmt:message key="asExportWarehouse.assetsName"/></td>
				<td> 
	                <form:input path="productName" class="wid90" maxlength="40"/>
					<br/><form:errors path="productName" cssClass="errorMessages"/> 
				</td> 
				
			</tr> 
			
			<tr> 
				<td><fmt:message key="asExportWarehouse.amountEx"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
				<td> 
	               	<form:input path="amountEx" class="wid90" maxlength="4"/>
	               	<br/><span style="color: red;">${slConlai} ${slxuat}</span>
					<br/><form:errors path="amountEx" cssClass="errorMessages"/> 
				</td> 
				<td><fmt:message key="asExportWarehouse.serialNo"/></td>
				<td> 
					<form:select path ="serialNo" class="wid30" onchange="xl()"> 
					<%-- <form:option value="">Chọn số serial</form:option> --%>
		           		<c:forEach var="item" items="${serialNoList}">
							<c:choose>
				                <c:when test="${item.serialNo == serialNo}">
				                    <form:option value="${item.serialNo}" selected="selected">${item.serialNo}</form:option>
				                </c:when>
								<c:otherwise>
									<form:option value="${item.serialNo}">${item.serialNo}</form:option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select> 
					<br/><form:errors path="serialNo" cssClass="errorMessages"/>  
				</td> 
				
			</tr> 
			
			<tr>
				<td><fmt:message key="asExportWarehouse.exportDate"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
				<td>  
	                <input id="exportDate" name="exportDate" value="${exportDate}" class="wid50" maxlength="10"/>
					<img alt="calendar" title="Chọn ngày xuất" id="chooseExportDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
					<br/><form:errors path="exportDate" cssClass="errorMessages"/>
				</td>
				
				<td><fmt:message key="asExportWarehouse.vendor"/></td>
				<td> 
	                <%-- <form:input path="vendor" class="wid90" maxlength="40"/> 
					<br/><form:errors path="vendor" cssClass="errorMessages"/>  --%>
					<form:select path ="vendor" class="wid30" onchange="xl()">  
		           		<c:forEach var="item" items="${vendorList}">
							<c:choose>
				                <c:when test="${item.vendor == vendor}">
				                    <form:option value="${item.vendor}" selected="selected">${item.vendor}</form:option>
				                </c:when>
								<c:otherwise>
									<form:option value="${item.vendor}">${item.vendor}</form:option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select> 
					<br/><form:errors path="vendor" cssClass="errorMessages"/>  
				</td> 
			</tr> 
			
			<tr>
				<td><fmt:message key="asExportWarehouse.votesNo"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
				<td> 
	                <form:input path="votesNo" class="wid90" maxlength="80"/> 
					<br/><form:errors path="votesNo" cssClass="errorMessages"/>
				</td>
				<td><fmt:message key="asExportWarehouse.unit"/></td> 
				<td>
					<form:select path ="unit" class="wid30" onchange="xl()"> 
					<%-- <form:option value=""><fmt:message key="global.chonDv"/></form:option> --%>
		           		<c:forEach var="item" items="${unitList}">
							<c:choose>
				                <c:when test="${item.name == unit}">
				                    <form:option value="${item.name}" selected="selected">${item.value}</form:option>
				                </c:when>
								<c:otherwise>
									<form:option value="${item.name}">${item.value}</form:option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select> 
					<br/><form:errors path="unit" cssClass="errorMessages"/> 
				</td>
				
			</tr>   
			
			<tr>
				<td><fmt:message key="asExportWarehouse.usersEx"/></td>
				<td>
	                <form:input path="usersEx" class="wid90" maxlength="80"/>
					<br/><form:errors path="usersEx" cssClass="errorMessages"/>
				</td> 
				
				<td><fmt:message key="asExportWarehouse.organization"/></td>
				<td>
	                <form:input path="organization" class="wid90" maxlength="80"/>
					<br/><form:errors path="organization" cssClass="errorMessages"/>
				</td>  
			</tr>  
			
			<tr>
				<td><fmt:message key="asExportWarehouse.departmentId"/></td>
				<td>
					<form:input path="departmentId" class="wid90" maxlength="80"/>
					<br/><form:errors path="departmentId" cssClass="errorMessages"/>
				</td>
				
				<td><fmt:message key="asExportWarehouse.users"/></td>
				<td>
	                <form:input path="users" class="wid90" maxlength="80"/>
					<br/><form:errors path="users" cssClass="errorMessages"/>
				</td>   
			</tr>
			<tr>
				<td><fmt:message key="asExportWarehouse.nguonlaytb"/></td>
				<td>
					<form:input path="nguonLayTB" class="wid90" maxlength="80"/>
					<br/><form:errors path="nguonLayTB" cssClass="errorMessages"/>
				</td> 
				
				<td><fmt:message key="asExportWarehouse.lydoxuat"/></td>
				<td>
					<form:textarea class="wid90" type ="text" path="lyDoXuat" maxlength="90"/> 
					<br/><form:errors path="lyDoXuat" cssClass="errorMessages"/>
				</td> 
			</tr>
			<tr>
				<td><fmt:message key="asExportWarehouse.description"/></td>
				<td>
					<form:textarea class="wid90" type ="text" path="description" maxlength="900"/>
					<br/><form:errors path="description" cssClass="errorMessages"/>
				</td>
			</tr>
			<tr>
    			<td colspan="4"><span style="font-size:12px;"><b><i><fmt:message key="asExportWarehouse.hoantra"/></i></b></span></td>
    		</tr>
			<tr> 
				<td><fmt:message key="asExportWarehouse.amountReturn"/></td>
				<td>
					<form:input path="amountReturn" class="wid90" maxlength="4"/> 
					<br/><span style="color: red;">${sltralai}</span>
					<br/><form:errors path="amountReturn" cssClass="errorMessages"/>
				</td> 
				
				<td><fmt:message key="asExportWarehouse.dateReturn"/></td>
				<td>
					<form:input type ="text"  path = "dateReturn" value="${dateReturn}" name ="dateReturn" id="dateReturn" class="wid30" maxlength="10"/> 
					<img alt="calendar" title="Chọn ngày trả" id="chooseDateReturn" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
					<br/><span style="color: red;">${ktNgaytra}</span>
					<br/><form:errors path="dateReturn" cssClass="errorMessages"/>
				</td>   
			</tr>
       <tr>
           <td></td>
			<td>
				<input type="submit" class="button" id="save"  value="<fmt:message key="global.form.luulai"/>" />
               	<input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm"'>
			</td>
        </tr>
    </table>
    
</form:form>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<link type="text/css" rel="Stylesheet" href="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.8.23.custom.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/assets/style.css" />

<script type="text/javascript">
function xl(){
	var sub = document.getElementById("save");
	sub.focus();
} 

Calendar.setup({
    inputField		:	"exportDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseExportDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"dateReturn",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseDateReturn",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
}); 

</script> 

<script type="text/javascript">

$('#productCode').focusout(function() {
	checkExists();
	});

function checkExists() {
	var productCode = encodeURI($("#productCode").val());
	$.getJSON("${pageContext.request.contextPath}/assets/xuat-tai-san/ajax/getImportWarehouseByProCode.htm", {productCode: productCode}, function(j){
		if (j.unit != null) {
			document.getElementById("unit").value = j.unit;
		}
		
		if (j.productName != null) {  
			document.getElementById("productName").value = j.productName;
		}
	});
}
</script>
<script type="text/javascript">
$('#productCode').focusout(function() {
	comboxList();
	});

function comboxList() {
	var productCode = encodeURI($("#productCode").val());
	$.getJSON("${pageContext.request.contextPath}/assets/xuat-tai-san/ajax/getSeriNoByProCode.htm", {productCode: productCode}, function(j){
			var options = '';
		    /* options += '<option value="">Chọn số serial</option>'; */  
		    for (var i = 0; i < j.length; i++) { 
		    	if(j[i].serialNo != null){
		    		options += '<option value="' + j[i].serialNo + '">' + j[i].serialNo + '</option>';
		    	} 
		   }
		  $("#serialNo").html(options);
		  $('#serialNo option:first').attr('selected', 'selected');
	});
}
</script>

<script type="text/javascript">
$('#serialNo').focusout(function() {
	comboxList1();
	});

function comboxList1() {
	var productCode = encodeURI($("#productCode").val());
	var serialNo = encodeURI($("#serialNo").val());
	$.getJSON("${pageContext.request.contextPath}/assets/xuat-tai-san/ajax/getNXSBySerial.htm", {productCode: productCode,serialNo: serialNo}, function(j){
			var options = '';  
		    for (var i = 0; i < j.length; i++) { 
		    	if(j[i].vendor != null){
		    		options += '<option value="' + j[i].vendor + '">' + j[i].vendor + '</option>';
		    	} 
		   }
		  $("#vendor").html(options);
		  $('#vendor option:first').attr('selected', 'selected');
	});
}
</script>

<script type="text/javascript">
	$(function() { 
		var cache = {},
		lastXhr;
		$( "#productCode" ).autocomplete({
			minLength: 1,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cache ) {
					response( cache[ term ] );
					return;
				}

				lastXhr = $.getJSON( "${pageContext.request.contextPath}/assets/xuat-tai-san/ajax/getMaTaiSan.htm", request, function( data, status, xhr ) {
					cache[ term ] = data;
					if ( xhr === lastXhr ) {
						response( data );
					}
				});
			}
		});
	});
</script>

