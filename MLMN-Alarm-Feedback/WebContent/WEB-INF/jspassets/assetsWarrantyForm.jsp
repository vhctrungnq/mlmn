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
  <c:when test="${assetsWarrantyAddEdit == 'N'}">
      <title><fmt:message key="sidebar.admin.assetsWarrantyFormAdd"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.assetsWarrantyFormAdd"/></content>
  </c:when>
  <c:when test="${assetsWarrantyAddEdit == 'Y'}">
      <title><fmt:message key="sidebar.admin.assetsWarrantyFormEdit"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.assetsWarrantyFormEdit"/></content>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>
<div class="body-content"></div>
<form:form commandName="assetsWarranty" name="checkform" method="post" action="form.htm"> 
	<div>
    	<form:input path="id" type="hidden" />
    </div>
    <table class="simple2">
    	<tr>
    		<td class="wid15 mwid110"><fmt:message key="assetsWarranty.csr"/>&nbsp;<font color="red">(*)</font></td>
    		<td class="wid35">
    			<form:input path="csr" maxlength="40" cssClass="wid90"/>&nbsp;<form:errors path="csr" cssClass="errorMessages"/>
    		</td>
    		<td class="wid15 mwid110"><fmt:message key="assetsWarranty.function"/></td>
    		<td>
    			<form:input path="function" maxlength="40" cssClass="wid90"/>
    		</td>
    	</tr> 
      	<tr>
      		<td><fmt:message key="assetsWarranty.productCode"/>&nbsp;<font color="red">(*)</font></td>
    		<td>
    			<form:input path="productCode" maxlength="40" cssClass="wid90"/>&nbsp;<form:errors path="productCode" cssClass="errorMessages"/>
    		</td>
          	<td><fmt:message key="assetsWarranty.rState"/></td>
          	<td>
          		<form:input path="rState" maxlength="40" cssClass="wid90"/>
          	</td>
		</tr>
		<tr>
          	<td><fmt:message key="assetsWarranty.vendor"/></td>
          	<td colspan="3">
          		<form:input path="vendor" maxlength="40" style="width:36%"/>
          	</td>
		</tr>
		<tr>
			<td colspan="4">
				<span style="font-size:12px;"><b><i>CARD SENT</i></b></span>
			</td>
		</tr>
      	<tr>
      		<td><fmt:message key="assetsWarranty.serialNo"/>&nbsp;<font color="red">(*)</font></td>
          	<td>
          		<form:input path="serialNo" maxlength="40" cssClass="wid90"/>&nbsp;<form:errors path="serialNo" cssClass="errorMessages"/>
          	</td>
      		<td><fmt:message key="assetsWarranty.serialNoScan"/></td>
           	<td>
           		<form:input path="serialNoScan" maxlength="40" cssClass="wid90"/>
           	</td>
      </tr>
      <tr>
      	<td><fmt:message key="assetsWarranty.sentDate"/></td>
      	<td>
           <input id="sentDate" name="sentDate" value="${sentDate}" class="wid30" maxlength="20"/>
			<img alt="calendar" title="Click to choose the sent date" id="chooseSentDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			&nbsp;<form:errors path="sentDate" cssClass="errorMessages"/> <span class="errorMessages">${errorSentDate}</span>		
       	</td>
      	<td><fmt:message key="assetsWarranty.sameUnit"/></td>
      	<td>
       		<select id="sameUnit" name="sameUnit" class="wid40">
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
       	<td><fmt:message key="assetsWarranty.ject"/></td>
      	<td colspan="3">
           	<select id="ject" name="ject" class="wid15">
   				<c:forEach var="items" items="${rejectList}">
	              	<c:choose>
	                <c:when test="${items.value == rejectCBB}">
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
       	<td colspan="4">
       		<span style="font-size:12px;"><b><i>CARD RECEIVED</i></b></span>
       	</td>
       </tr>
       <tr> 
        <td><fmt:message key="assetsWarranty.serialNoRep"/></td>
      	<td>
           	<form:input path="serialNoRep" maxlength="40" cssClass="wid90"/>
        </td>
        <td><fmt:message key="assetsWarranty.receivedDate"/></td>
      	<td>
           <input id="receivedDate" name="receivedDate" value="${receivedDate}" class="wid30" maxlength="20"/>
			<img alt="calendar" title="Click to choose the received date" id="chooseReceivedDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>	
        </td>
      </tr>
      <tr>
      	<td><fmt:message key="assetsWarranty.deliveryNo"/></td>
      	<td>
           	<form:input path="deliveryNo" maxlength="40" cssClass="wid90"/>
        </td>
        <td><fmt:message key="assetsWarranty.qty"/></td>
      	<td>
           	<form:input path="qty" maxlength="4" cssClass="wid30"/>&nbsp;<form:errors path="qty" cssClass="errorMessages"/>
        </td>
      </tr>
      <tr>
      	<td colspan="4">
      		<span style="font-size:12px;"><b><i>DEPARTMENT</i></b></span>
      	</td>
      </tr>
      <tr>
        <td><fmt:message key="assetsWarranty.departmentId"/></td>
      	<td>
           	<select id="departmentId" name="departmentId" class="wid40">
   				<c:forEach var="items" items="${toXuLyList}">
	              	<c:choose>
	                <c:when test="${items.value == toXuLyCBB}">
	                    <option value="${items.value}" selected="selected">${items.name}</option>
	                </c:when>
	                <c:otherwise>
	                    <option value="${items.value}">${items.name}</option>
	                </c:otherwise>
	              	</c:choose>
		    	</c:forEach>
			</select>
        </td>
        <td><fmt:message key="assetsWarranty.returnDate"/></td>
        <td><input id="returnDate" name="returnDate" value="${returnDate}" class="wid30" maxlength="20"/>
			<img alt="calendar" title="Click to choose the return date" id="chooseReturnDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			&nbsp;<form:errors path="returnDate" cssClass="errorMessages"/>
		</td>
      </tr>
      <tr>
      	<td><fmt:message key="assetsWarranty.description"/></td>
      	<td colspan="3">
      		<form:input path="description" id ="description" cssClass="wid96" maxlength="900"/>
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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<link type="text/css" rel="Stylesheet" href="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.8.23.custom.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/assets/style.css" />
<script type="text/javascript">

Calendar.setup({
    inputField		:	"sentDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseSentDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"receivedDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseReceivedDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"returnDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseReturnDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

function focusIt()
{
	var errorSentDate = '<c:out value="${errorSentDate}"/>';

	if(document.checkform.csr.value==""){
		  var mytext = document.getElementById("csr");
		  mytext.focus();
	}
	else if(document.checkform.productCode.value==""){
		  var mytext = document.getElementById("productCode");
		  mytext.focus();
	}
	else if(document.checkform.serialNo.value == "")
	{
		var mytext = document.getElementById("serialNo");
	  	mytext.focus();
	}
	else if(errorSentDate != "")
		{
			var mytext = document.getElementById("sentDate");
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

	loadData(availableTags, "productCode");
});

$('#productCode').focusout(function() {
	checkExists();
});

function checkExists() {
	var productCode = encodeURI($("#productCode").val());
	$.getJSON("${pageContext.request.contextPath}/assets/bao-hanh-tai-san/ajax/getListSeriByProCode.htm", {productCode: productCode}, function(j){
		var temp = [];
		for (var i = 0; i < j.length; i++) {
			temp[i] = j[i].serialNo;
		}
		
		loadData(temp, "serialNo");
	});
}
	
function loadData(availableTags, id){
	function split( val ) {
	return val.split( /,\s*/ );
	}
	function extractLast( term ) {
	return split( term ).pop();
	}
	$( "#" + id)
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

