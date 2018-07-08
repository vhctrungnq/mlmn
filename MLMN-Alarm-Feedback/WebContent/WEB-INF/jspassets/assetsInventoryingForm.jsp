<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
   .ui-autocomplete {
		max-height: 200px;
		overflow-y: auto;
		/* prevent horizontal scrollbar */
		overflow-x: hidden;
	}
	/* IE 6 doesn't support max-height
	* we use height instead, but this forces the menu to always be this tall
	*/
	html .ui-autocomplete {
		height: 200px;
	}
</style>    
<c:choose>
  <c:when test="${assetsInventoryingFormAddEdit == 'N'}">
      <title><fmt:message key="sidebar.admin.assetsInventoryingFormAdd"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.assetsInventoryingFormAdd"/></content>
  </c:when>
  <c:when test="${assetsInventoryingFormAddEdit == 'Y'}">
      <title><fmt:message key="sidebar.admin.assetsInventoryingFormEdit"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.assetsInventoryingFormEdit"/></content>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>
<div class="body-content"></div>
<form:form commandName="assetsInventorying" name="checkform" method="post" action="form.htm"> 
	<div>
    	<form:input path="id" type="hidden" />
    </div>
    <table class="simple2">
    	<tr>
    		<td class="wid15 mwid110"><b><fmt:message key="assetsInventorying.dotKiemKe"/></b>&nbsp;<font color="red">(*)</font></td>
    		<td>
            	<form:input path="dotKiemKe" maxlength="90" cssClass="wid90"/>
            	&nbsp;<form:errors path="dotKiemKe" cssClass="errorMessages"/>
    		</td>
    		
    		<td class="wid15 mwid110"><b><fmt:message key="assetsTypes.asTypesName"/></b>&nbsp;<font color="red">(*)</font></td>
    		<td class="wid35">
    			<select id="asTypesId" name="asTypesId" class="wid90">
	   				<c:forEach var="items" items="${asTypesIDList}">
		              	<c:choose>
		                <c:when test="${items.asTypesId == asTypesIDCBB}">
		                    <option value="${items.asTypesId}" selected="selected">${items.asTypesName}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.asTypesId}">${items.asTypesName}</option>
		                </c:otherwise>
		              	</c:choose>
			    	</c:forEach>
				</select>
    		</td>
    	</tr> 
      <tr>
	   		<td class="wid15 mwid110"><b><fmt:message key="assetsInventorying.productCode"/></b>&nbsp;<font color="red">(*)</font></td>
	   		<td>
	           	<form:input path="productCode" maxlength="40" cssClass="wid90"/>
	           	&nbsp;<form:errors path="productCode" cssClass="errorMessages"/>
	   		</td>
	    		
	  		<td><b><fmt:message key="assetsInventorying.productName"/></b>&nbsp;<font color="red">(*)</font></td>
	        <td>
	        	<form:input path="productName" maxlength="240" cssClass="wid90"/>&nbsp;<form:errors path="productName" cssClass="errorMessages"/>
	        </td>
		</tr>
		<tr>
			<td><b><fmt:message key="assetsInventorying.serialNo"/></b></td> 
			<td> 
				<form:select path ="serialNo" class="wid30" onchange="xl()"> 
				<form:option value="">Chọn số serial</form:option>
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
			
		   <td><b><fmt:message key="assetsInventorying.amount"/></b>&nbsp;<font color="red">(*)</font></td>
           <td><form:input path="amount" maxlength="4" cssClass="wid30"/>&nbsp;<form:errors path="amount" cssClass="errorMessages"/></td>         
		</tr>
      	<tr>
         	<td><b><fmt:message key="assetsInventorying.importDate"/></b>&nbsp;<font color="red">(*)</font></td>
           	<td>
           		<input id="importDate" name="importDate" value="${importDate}" class="wid30" maxlength="20"/>
				<img alt="calendar" title="Click to choose the import date" id="chooseImportDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				&nbsp;<form:errors path="importDate" cssClass="errorMessages"/> <span class="errorMessages">${errorImportDate}</span>
           	</td>
           	
           	<td><b><fmt:message key="assetsInventorying.unit"/></b></td>
           	<td>
           		<select id="unit" name="unit" class="wid30">
	   				<c:forEach var="items" items="${unitList}">
		              	<c:choose>
		                <c:when test="${items.value == unitCBB}">
		                    <option value="${items.value}" selected="selected">${items.name}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.value}">${items.name}</option>
		                </c:otherwise>
		              	</c:choose>
			    	</c:forEach>
				</select>
           	</td>
      </tr>
      <tr>
		<td><b><fmt:message key="assetsInventorying.produceDate"/></b></td>
		<td>
		   	<input id="produceDate" name="produceDate" value="${produceDate}" class="wid30" maxlength="20"/>
			<img alt="calendar" title="Click to choose the import date" id="chooseProduceDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			&nbsp;<form:errors path="produceDate" cssClass="errorMessages"/> <span class="errorMessages">${errorProduceDate}</span>
		</td>
		
        <td><b><fmt:message key="assetsInventorying.vendor"/></b></td>
      	<td>
           	<form:input path="vendor" maxlength="240" cssClass="wid90"/>
        </td> 
      </tr>
      <tr>
      	 <td><b><fmt:message key="assetsInventorying.origin"/></b></td>
      	<td>
           	<form:input path="origin" maxlength="500" cssClass="wid90"/>
        </td>
        
        <td><b><fmt:message key="assetsInventorying.status"/></b></td>
      	<td>
           	<form:input path="status" maxlength="1" cssClass="wid90"/>
        </td> 
      </tr>
      <tr>
      	<td><b><fmt:message key="assetsInventorying.users"/></b></td>
      	<td>
           	<form:input path="users" maxlength="40" cssClass="wid90"/>
        </td>
        
        <td><b><fmt:message key="assetsInventorying.departmentUse"/></b></td>
      	<td>
           	<form:input path="departmentUse" maxlength="40" cssClass="wid90"/>
        </td>
      </tr>
      <tr>
      	<td><b><fmt:message key="assetsInventorying.departmentId"/></b></td>
      	<td>
           	<select id="departmentId" name="departmentId" class="wid40">
   				<c:forEach var="items" items="${toXuLyList}">
	              	<c:choose>
	                <c:when test="${items.deptCode == toXuLyCBB}">
	                    <option value="${items.deptCode}" selected="selected">${items.deptCode}</option>
	                </c:when>
	                <c:otherwise>
	                    <option value="${items.deptCode}">${items.deptCode}</option>
	                </c:otherwise>
	              	</c:choose>
		    	</c:forEach>
			</select>
        </td>
        
        <td><b><fmt:message key="assetsInventorying.description"/></b></td>
		<td>
			<form:textarea class="wid90" type ="text" path="description" maxlength="900"/>
			<br/><form:errors path="description" cssClass="errorMessages"/>
		</td> 
      </tr>
       <tr>
           <td></td>
           <td colspan="3">
               <input class="button" type="submit" class="button" name="save" value="<fmt:message key="global.form.luulai"/>" />
               <input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm"'>
           </td>
       </tr>
    </table>

</form:form>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script type="text/javascript"> $(".chzn-select").chosen(); $(".chzn-select-deselect").chosen({allow_single_deselect:true}); </script>

<script type="text/javascript">

Calendar.setup({
    inputField		:	"importDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseImportDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"produceDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseProduceDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

function focusIt()
{
	var errorImportDate = '<c:out value="${errorImportDate}"/>';
	var importDateError = '<c:out value="${importDateError}"/>';
	
	if(document.checkform.productCode.value==""){
		  var mytext = document.getElementById("productCode");
		  mytext.focus();
	}
	else if(document.checkform.productName.value == "")
	{
		var mytext = document.getElementById("productName");
	  	mytext.focus();
	}
	else if(errorImportDate != "")
	{
		var mytext = document.getElementById("importDate");
	  	mytext.focus();
	}
	else if(importDateError !="")
	{
			var mytext = document.getElementById("importDate");
			  mytext.focus();
	}
}

onload = focusIt;

</script>
<script type="text/javascript">
$(function() {
	var availableTags = [];
	var i = 0;
	<c:forEach items="${assetsManagementList}" var="listOfNames">
		availableTags[i] = "<c:out value='${listOfNames.productCode}' escapeXml='false' />";
		i = i + 1;
	</c:forEach>

	loadProductCode(availableTags);
});

$("#asTypesId").change(function(){
	$.getJSON("${pageContext.request.contextPath}/assets/bao-hanh-tai-san/loadAssetsManagementsByAsId.htm",{asTypesId: $('#asTypesId').val()}, function(j){
		
		var availableTags = [];
		for (var i = 0; i < j.length; i++) {
			availableTags[i] = j[i].productCode;
		}
		
		loadProductCode(availableTags);
	});

});

function loadProductCode(availableTags){
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
	// add the selected item
	terms.push( ui.item.value );
	// add placeholder to get the comma-and-space at the end
	terms.push( "" );
	this.value = terms.join( "" );
	return false;
	}
	});
}
</script>
<script type="text/javascript">

$('#productCode').focusout(function() {
	checkExists();
	});

function checkExists() {
	var productCode = encodeURI($("#productCode").val());
	$.getJSON("${pageContext.request.contextPath}/assets/quan-ly-nhap-kho-tai-san/ajax/getAsManagementByProCode.htm", {productCode: productCode}, function(j){
		//alert(j.assetsName);		
		if (j.assetsName != null) {
			document.getElementById("productName").value = j.assetsName;
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
		    options += '<option value="">Chọn số serial</option>';
		    for (var i = 0; i < j.length; i++) { 
		    options += '<option value="' + j[i].serialNo + '">' + j[i].serialNo + '</option>';
		   }
		  $("#serialNo").html(options);
		  $('#serialNo option:first').attr('selected', 'selected');
	});
}
</script>
