<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<c:choose>
  <c:when test="${alManageOnAirAddEdit == 'N'}">
      <title><fmt:message key="title.alManageOnAirFormAdd"/></title>
	  <content tag="heading"><fmt:message key="title.alManageOnAirFormAdd"/></content>
  </c:when>
  <c:when test="${alManageOnAirAddEdit == 'Y'}">
      <title><fmt:message key="title.alManageOnAirFormEdit"/></title>
	  <content tag="heading"><fmt:message key="title.alManageOnAirFormEdit"/></content>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>

<form:form commandName="alManageOnAir" name="checkform" method="post" action="form.htm"> 
	<div class="body-content"></div>
	<div>
    	<form:input path="id" type="hidden" />
    	<input id="projectCode" name="projectCode" value="${projectCode}" type="hidden" />
    </div>
    <table class="simple2">
      <tr>
           <td class="wid15 mwid110"><fmt:message key="alManageOnAir.siteid"/>&nbsp;<font color="red">(*)</font></td>
           <td class="wid35">
		       	<form:input path="siteid" maxlength="20" />
		       	&nbsp;<form:errors path="siteid" cssClass="error"/>
           </td>
           <td class="wid15 mwid110"><fmt:message key="alManageOnAir.siteName"/></td>
           <td>
           		<form:input path="siteName" maxlength="20" />
           </td>       
      </tr>
      <tr>
           <td><fmt:message key="alManageOnAir.ne"/>&nbsp;</td>
           <td>
				<form:input path="ne" maxlength="100" />
           </td>
           <td><fmt:message key="alManageOnAir.network"/>&nbsp;<font color="red">(*)</font></td>
           <td>
           		<form:select path="network" cssClass="wid30">
					<c:forEach var="items" items="${networkList}">
						<c:choose>
						<c:when test="${items.value == networkCBB}">
						   <option value="${items.value}" selected="selected">${items.value}</option>
						</c:when>
						<c:otherwise>
						   <option value="${items.value}">${items.value}</option>
						</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select>
				&nbsp;<form:errors path="network" cssClass="error"/>
           </td>      
      </tr>
      <tr>
      	   <td><fmt:message key="alManageOnAir.clusters"/></td>
           <td>
		       	<form:input path="clusters" maxlength="8" />
		       	&nbsp;<form:errors path="clusters" cssClass="error"/>
           </td> 
           <td><fmt:message key="alManageOnAir.projectId"/></td>
           <td>
           		<form:select path="projectId" cssClass="wid60">
					<c:forEach var="items" items="${projectIdList}">
						<c:choose>
						<c:when test="${items.id == projectIdCBB}">
						   <option value="${items.id}" selected="selected">${items.projectCode}</option>
						</c:when>
						<c:otherwise>
						   <option value="${items.id}">${items.projectCode}</option>
						</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select>
				&nbsp;<form:errors path="projectId" cssClass="error"/>
           </td>  
                
      </tr>  
      <tr>
      		<td><fmt:message key="alManageOnAir.projectSupervisor"/></td>
           <td>
				<form:input path="projectSupervisor" maxlength="100" />
           </td>
           <td><fmt:message key="alManageOnAir.intNe"/></td>
           <td>
           		<input id="intNe" name="intNe" value="${intNe}" class="wid30" maxlength="20"/>
	 			<img alt="calendar" title="Click to choose the int date" id="chooseIntNe" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	 			&nbsp;<form:errors path="intNe" cssClass="error"/>
           </td>
	   	 	
      </tr>
      <tr>
      		<td><fmt:message key="alManageOnAir.onAir"/></td>
        	<td>
        		<input id="onAir" name="onAir" value="${onAir}" class="wid30" maxlength="20"/>
 				<img alt="calendar" title="Click to choose the onAir2g date" id="chooseOnAir" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
 				&nbsp;<form:errors path="onAir" cssClass="error"/>
        	</td>
           <td><fmt:message key="alManageOnAir.noCells"/></td>
           <td>
		       	<form:input path="noCells" maxlength="9" />
		       	&nbsp;<form:errors path="noCells" cssClass="error"/>
           </td>        
      </tr>
      <tr>
      		<td><fmt:message key="alManageOnAir.noTrxs"/></td>
           <td>
           		<form:input path="noTrxs" maxlength="9" />
           		&nbsp;<form:errors path="noTrxs" cssClass="error"/>
           </td>
           <td><fmt:message key="alManageOnAir.license"/></td>
           <td>
           		<form:input path="license" maxlength="230" />
           </td>        
      </tr>
      <tr>
      		<td><fmt:message key="alManageOnAir.note"/></td>
           <td>
           		<form:input path="note" maxlength="400" />
           </td>
           <td><fmt:message key="alManageOnAir.alarmTest"/></td>
           <td>
		       	<form:input path="alarmTest" maxlength="220" />
           </td>
                 
      </tr>
      <tr>
      		<td><fmt:message key="alManageOnAir.callTest"/></td>
           <td>
           		<form:input path="callTest" maxlength="220" />
           </td> 
           <td><fmt:message key="alManageOnAir.extalm"/></td>
           <td>
		       	<form:input path="extalm" maxlength="90" />
           </td>
                   
      </tr>
      <tr>
      		<td><fmt:message key="alManageOnAir.status"/></td>
           <td>
		       	<form:input path="status" maxlength="20" />
           </td> 
           <td><fmt:message key="alManageCrCdd.cddName"/></td>
           <td><div id='cddName'></div></td>    
      </tr>
      <tr>
      		<td><fmt:message key="alManageOnAir.description"/></td>
         	<td colspan="3">
       			<form:input path="description" maxlength="220" />
         	</td>
      </tr>
      <tr>
        <td></td>
        <td colspan="3">
            <input class="button" type="submit" name="save" value="<fmt:message key="global.form.luulai"/>" />
            <input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm<c:if test="${not empty projectCode}">?projectCode=${projectCode}</c:if>"'>
        </td>
      </tr>
    </table>
</form:form>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<script type="text/javascript">

Calendar.setup({
    inputField		:	"intNe",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseIntNe",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"onAir",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseOnAir",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});


function focusIt()
{
	var clustersError = '<c:out value="${clustersError}"/>';
	var intNeError = '<c:out value="${intNeError}"/>';
	var onAirError = '<c:out value="${onAirError}"/>';
	var noCellsError = '<c:out value="${noCellsError}"/>';
	var noTrxsError = '<c:out value="${noTrxsError}"/>';
	
	if(document.checkform.siteid.value==""){
		  var mytext = document.getElementById("siteid");
		  mytext.focus();
		}
	else if(clustersError !=""){
		  var mytext = document.getElementById("clusters");
		  mytext.focus();
		}
	else if(intNeError !=""){
		  var mytext = document.getElementById("intNe");
		  mytext.focus();
		}
	else if(onAirError != "")
	{
		var mytext = document.getElementById("onAir");
	  	mytext.focus();
	}
	else if(noCellsError != "")
	{
		var mytext = document.getElementById("noCells");
	  	mytext.focus();
	}
	else if(noTrxsError != "")
	{
		var mytext = document.getElementById("noTrxs");
	  	mytext.focus();
	}
}

onload = focusIt;

</script>
<script type="text/javascript">
var theme = getTheme();
$("#siteid").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#siteName").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#ne").jqxInput({ width: '30%', height: 20, minLength: 1, theme: theme });
$("#clusters").jqxInput({ width: '30%', height: 20, minLength: 1, theme: theme });
$("#projectSupervisor").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#noCells").jqxInput({ width: '30%', height: 20, minLength: 1, theme: theme });
$("#noTrxs").jqxInput({ width: '30%', height: 20, minLength: 1, theme: theme });
$("#note").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#alarmTest").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#callTest").jqxInput({ width: '30%', height: 20, minLength: 1, theme: theme });
$("#extalm").jqxInput({ width: '30%', height: 20, minLength: 1, theme: theme });
$("#license").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#status").jqxInput({ width: '30%', height: 20, minLength: 1, theme: theme });
$("#description").jqxInput({ width: '85%', height: 20, minLength: 1, theme: theme });

//Create a jqxMultile Input
var renderer = function (itemValue, inputValue) {
    var terms = inputValue.split(/,\s*/);
    // remove the current input
    terms.pop();
    // add the selected item
    terms.push(itemValue);
    // add placeholder to get the comma-and-space at the end
    terms.push("");
    var value = terms.join("");
    return value;
};

var networkCBB = '<c:out value="${networkCBB}"/>';
if(networkCBB == '3G'){
	loadRnc();
}else{
	loadBsc();
}

$('#network').change(function(){
	$('#load').remove();
	$('.body-content').append('<span id="load">LOADING...</span>');
	$('#load').fadeIn('normal', loadContent);
	function loadContent() {
		$('#result').load('', '', showNewContent());
	}
	function showNewContent() {
		$('#result').show('normal', hideLoader());
	}
	function hideLoader() {
		$('#load').fadeOut('normal');
	}
	var network = $('#network').val();
	if(network == '2G'){
		loadBsc();
	}
	else{
		loadRnc();
	}
});

function loadBsc(){
	//Input bscid
	${bscList}
	$("#ne").jqxInput({ placeHolder: "", height: 20, width: '60%', theme: theme,
	    source: function (query, response) {
	        var item = query.split(/,\s*/).pop();
	        // update the search query.
	        $("#ne").jqxInput({ query: item });
	        response(bscList);
	    },
	    renderer: renderer
	});
};

function loadRnc(){
	//Input rncid
	${rncList}
	$("#ne").jqxInput({ placeHolder: "", height: 20, width: '60%', theme: theme,
	    source: function (query, response) {
	        var item = query.split(/,\s*/).pop();
	        // update the search query.
	        $("#ne").jqxInput({ query: item });
	        response(rncList);
	    },
	    renderer: renderer
	});
};
</script>


<script type="text/javascript">

$.fn.delayKeyup = function(callback, ms){
    var timer = 0;
    var el = $(this);
    $(this).keyup(function(){                   
        clearTimeout (timer);
        timer = setTimeout(function(){
            callback(el)
                }, ms);
    });
    return $(this);
};
$(document).ready(function(){
	loadCddName();
});

function loadCddName(){
	var siteid = $('#siteid').val();
	var ne = $('#ne').val();
	var str = "?siteid=" + siteid + "&ne=" + ne;
	// Create a jqxComboBox
	var urlCddName = "${pageContext.request.contextPath}/alarm/al-manage-on-air/loadCddBySiteidAndNe.htm" + str;
	// prepare the data
	var sourceCddName =
	{
	     datatype: "json",
	     datafields: [
	         { name: 'crName' },
	         { name: 'crName' }
	     ],
	     url: urlCddName,
	     async: false
	};
	 var dataAdapterCddName = new $.jqx.dataAdapter(sourceCddName);
	 // Create a jqxComboBox
	 $("#cddName").jqxComboBox({ source: dataAdapterCddName, displayMember: "crName", valueMember: "crName", width: '60%',height: 20,itemHeight: 30,theme: theme, autoOpen: true });
	 var cddName =  "<c:out value='${cddName}'/>";
	 if(cddName!=""){
		 $("#cddName").find('input').val(cddName);
	 }
};

$("#siteid").delayKeyup(function() {
	$('#load').remove();
	$('.body-content').append('<span id="load">LOADING...</span>');
	$('#load').fadeIn('normal', loadContent);
	function loadContent() {
		$('#result').load('', '', showNewContent());
	}
	function showNewContent() {
		$('#result').show('normal', hideLoader());
	}
	function hideLoader() {
		$('#load').fadeOut('normal');
	}
	
	loadCddName();
},1000);

$("#ne").delayKeyup(function() {
	$('#load').remove();
	$('.body-content').append('<span id="load">LOADING...</span>');
	$('#load').fadeIn('normal', loadContent);
	function loadContent() {
		$('#result').load('', '', showNewContent());
	}
	function showNewContent() {
		$('#result').show('normal', hideLoader());
	}
	function hideLoader() {
		$('#load').fadeOut('normal');
	}
	
	loadCddName();
},1000);
</script>