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
  <c:when test="${asCardsAddEdit == 'N'}">
      <title><fmt:message key="sidebar.admin.asCardsFormAdd"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.asCardsFormAdd"/></content>
  </c:when>
  <c:when test="${asCardsAddEdit == 'Y'}">
      <title><fmt:message key="sidebar.admin.asCardsFormEdit"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.asCardsFormEdit"/></content>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>
<div class="body-content"></div>
<form:form commandName="asCards" name="checkform" method="post" action="form.htm"> 
	<div>
    	<form:input path="id" type="hidden" />
    </div>
    <table class="simple2">
    	<tr>
			<td colspan="4">
				<span style="font-size:12px;"><b><i>Thông tin nhập thiết bị </i></b></span>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="asCards.asTypesName"/>&nbsp;<font color="red">(*)</font></td>
			<td>
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
			<td><fmt:message key="asCards.warehourse"/>
			
			</td>
			<td>
				<select id="warehourse" name="warehourse" class="wid90">
	   				<c:forEach var="items" items="${warehourseList}">
		              	<c:choose>
		                <c:when test="${items.warehourse == warehourseCBB}">
		                    <option value="${items.warehourse}" selected="selected">${items.warehourse}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.warehourse}">${items.warehourse}</option>
		                </c:otherwise>
		              	</c:choose>
			    	</c:forEach>
				</select>
			</td>
		</tr>
    	<tr>
    		<td class="wid15 mwid110"><fmt:message key="asCards.productCode"/>&nbsp;<font color="red">(*)</font></td>
    		<td class="wid35">
    			<form:input path="productCode" maxlength="40" cssClass="wid90"/>&nbsp;<form:errors path="productCode" cssClass="errorMessages"/>
    		</td>
    		<td class="wid15 mwid110"><fmt:message key="global.list.STT"/>
    		</td>
    		<td>
    			<form:input path="stt" maxlength="40" cssClass="wid90"/>&nbsp;<form:errors path="stt" cssClass="errorMessages"/>
            	
    		</td>
    	</tr> 
      <tr>
   		<td><fmt:message key="asCards.productName"/>&nbsp;<font color="red">(*)</font></td>
        <td>
       		<form:input path="productName" maxlength="240" cssClass="wid90"/>&nbsp;<form:errors path="productName" cssClass="errorMessages"/>
        </td>
        <td><fmt:message key="asCards.serialNo"/>&nbsp;<font color="red">(*)</font></td>
        <td>
       		<form:input path="serialNo" maxlength="40" cssClass="wid90"/>&nbsp;<form:errors path="serialNo" cssClass="errorMessages"/>
        </td>
		</tr>
		<tr>
		   <td><fmt:message key="asCards.amount"/>&nbsp;<font color="red">(*)</font></td>
           <td>
	           	<form:input path="amount" maxlength="4" cssClass="wid30"/>&nbsp;<form:errors path="amount" cssClass="errorMessages"/>
           </td>
           <td><fmt:message key="asCards.unit"/></td>
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
         	<td><fmt:message key="asCards.importDate"/></td>
           	<td>
           		<input id="importDate" name="importDate" value="${importDate}" class="wid30" maxlength="20"/>
				<img alt="calendar" title="Click to choose the import date" id="chooseImportDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				&nbsp;<form:errors path="importDate" cssClass="errorMessages"/>
           	</td>
           	<td><fmt:message key="asCards.produceDate"/></td>
      		<td>
           		<input id="produceDate" name="produceDate" value="${produceDate}" class="wid30" maxlength="20"/>
				<img alt="calendar" title="Click to choose the import date" id="chooseProduceDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				&nbsp;<form:errors path="produceDate" cssClass="errorMessages"/>
       	 	</td>
      </tr>
      <tr>
        <td><fmt:message key="asCards.vendor"/></td>
      	<td>
           	<form:input path="vendor" maxlength="240" cssClass="wid90"/>
        </td>
        <td><fmt:message key="asCards.warrantyDate"/></td>
      	<td>
           	<input id="warrantyDate" name="warrantyDate" value="${warrantyDate}" class="wid30" maxlength="20"/>
			<img alt="calendar" title="Click to choose the warranty date" id="chooseWarrantyDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			&nbsp;<form:errors path="warrantyDate" cssClass="errorMessages"/>
        </td>
      </tr>
      <tr>
      	<td><fmt:message key="asCards.ject"/></td>
      	<td>
      		<form:input path="ject" id ="ject" cssClass="wid90" maxlength="500"/>
      	</td>
        <td><fmt:message key="asCards.description"/></td>
      	<td>
           	<form:input path="description" id ="description" cssClass="wid90" maxlength="900"/>
        </td>
      </tr>
      <tr>
			<td colspan="4">
				<span style="font-size:12px;"><b><i>Thông tin xuất và hoàn trả thiết bị </i></b></span>
			</td>
	   </tr>
	   <tr>
	   		<td><fmt:message key="asCards.votesNo"/></td>
	   		<td><form:input path="votesNo" id ="votesNo" cssClass="wid90" maxlength="90"/></td>
	   		<!-- <td><fmt:message key="asCards.departmentId"/></td>
	   		<td><form:input path="departmentId" id ="departmentId" cssClass="wid90" maxlength="90"/></td>-->
	   </tr>
	   <tr>
	   		<td><fmt:message key="asExportWarehouse.organization"/></td>
	   		<td><form:input path="organization" id ="organization" cssClass="wid90" maxlength="90"/></td>
	   		<td><fmt:message key="asExportWarehouse.departmentId"/></td>
	   		<td><form:input path="departmentId" id ="departmentId" cssClass="wid90" maxlength="90"/></td>
	   </tr>
	   <tr>
	   		<td><fmt:message key="asCards.nguonLayTb"/></td>
			<td><form:input type="text" path="nguonLayTb" class="wid90"/></td> 
							
			<td><fmt:message key="asCards.lyDoXuat"/></td>
			<td><form:input type="text" path="lyDoXuat" class="wid90"/></td> 
	   </tr>
	   <tr>
	   		<td><fmt:message key="asCards.exportDate"/></td>
	   		<td>
	   			<input id="exportDate" name="exportDate" value="${exportDate}" class="wid30" maxlength="20"/>
				<img alt="calendar" title="Click to choose the export date" id="chooseExportDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				&nbsp;<form:errors path="exportDate" cssClass="errorMessages"/>
	   		</td>
	   		<td><fmt:message key="asCards.dateReturn"/></td>
	   		<td>
	   			<input id="dateReturn" name="dateReturn" value="${dateReturn}" class="wid30" maxlength="20"/>
				<img alt="calendar" title="Click to choose the return date" id="chooseDateReturn" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				&nbsp;<form:errors path="dateReturn" cssClass="errorMessages"/>
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
<div style="padding-top:4px">
	<table>
		<tr> 
			<td>
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

Calendar.setup({
    inputField		:	"warrantyDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseWarrantyDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

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

function focusIt()
{
	
	var importDateError = '<c:out value="${importDateError}"/>';
	var exportDateError = '<c:out value="${exportDateError}"/>';
	var produceDateError = '<c:out value="${produceDateError}"/>';
	var dateReturnError = '<c:out value="${dateReturnError}"/>';
	var warrantyDateError = '<c:out value="${warrantyDateError}"/>';
	
	if(document.checkform.productCode.value==""){
		  var mytext = document.getElementById("productCode");
		  mytext.focus();
	}
	else if(document.checkform.productName.value == "")
	{
		var mytext = document.getElementById("productName");
	  	mytext.focus();
	}
	else if(document.checkform.serialNo.value == "")
	{
		var mytext = document.getElementById("serialNo");
	  	mytext.focus();
	}
	else if(document.checkform.amount.value == "")
	{
		var mytext = document.getElementById("amount");
	  	mytext.focus();
	}
	else if(importDateError !="")
	{
			var mytext = document.getElementById("importDate");
			  mytext.focus();
	}
	else if(exportDateError !="")
	{
			var mytext = document.getElementById("exportDate");
			  mytext.focus();
	}
	else if(produceDateError !="")
	{
			var mytext = document.getElementById("produceDate");
			  mytext.focus();
	}
	else if(dateReturnError !="")
	{
			var mytext = document.getElementById("dateReturn");
			  mytext.focus();
	}
	else if(warrantyDateError !="")
	{
			var mytext = document.getElementById("warrantyDate");
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
	$(".loading").css('display', 'block');
	$.getJSON("${pageContext.request.contextPath}/assets/quan-ly-nhap-kho-tai-san/loadAssetsManagementsByAsId.htm",{asTypesId: $('#asTypesId').val()}, function(j){
		var availableTags = [];
		for (var i = 0; i < j.length; i++) {
			availableTags[i] = j[i].productCode;
		}
		
		loadProductCode(availableTags);
		$(".loading").css('display', 'none');
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

			$("#unit option[value=" + j.unit + "]").attr('selected', 'selected');
		}
	});
}
</script>
