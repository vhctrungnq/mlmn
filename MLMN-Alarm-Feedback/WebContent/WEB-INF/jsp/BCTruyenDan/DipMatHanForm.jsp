<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<title>${titleF}</title>
<content tag="heading">${titleF}</content>
<style>
	.ui-multiselect {
		width:160px !important;
	}
	 #teamProcess {
     visibility: visible !important;
    }
    
    .ui-autocomplete {
	max-height: 200px; 
	overflow-y: auto;
	/* prevent horizontal scrollbar */
	overflow-x: hidden;
	}
	/* IE 6 doesn't support max-height
	* we use height instead, but this forces the menu to always be this tall
	*/
	* html .ui-autocomplete {
	height: 200px;
	}
</style>
<form:form commandName="alDyRpErrorDip" name="checkform" id="myform" method="post" action="formMH.htm"> 
	<form:hidden path="id"/>
	<table class="simple2"> 
      <tr>
           <td style="width: 120px;"><fmt:message key="alDyRpErrorDip.bscid"/><font color="red">(*)</font></td>
           <td style="width: 200px;">
           		<form:input type ="text" path="bscid" maxlength="18" style="width:160px;" rows="3"/>
           		<font color="red"><form:errors path="bscid"/></font>
           </td>
      
           <td style="width: 80px;"><fmt:message key="alDyRpErrorDip.dip"/><font color="red">(*)</font></td>
           <td style="width: 200px;">
           		<form:input type ="text" path="dip" maxlength="18" style="width:160px;" rows="3"/>
           		<font color="red"><form:errors path="dip"/></font>
           </td>
           <td style="width: 80px;"><fmt:message key="alDyRpErrorDip.faultType"/></td>
           <td style="width: 200px;">
           		 <form:input type ="text" path="faultType" maxlength="18" style="width:160px;" rows="3" id = "faultType" name="faultType"/>
           		<%--<select name="faultType" id="faultType" style="width: 160px;height:20px; padding-top: 4px;">
           		 <option value=""><fmt:message key="global.All"/></option>
	           		<c:forEach var="item" items="${faultTypeList}">
						<c:choose>
			                <c:when test="${item == faultType}">
			                    <option value="${item}" selected="selected">${item}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item}">${item}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> --%>
           </td>
           <td style="width: 100px;"><fmt:message key="alDyRpErrorDip.transType"/></td>
           <td >
           		<form:input type ="text" path="transType" maxlength="18" style="width:160px;" rows="3" id = "transType" name="transType"/>
           		<%--<select name="transType" id="transType" style="width: 160px;height:20px; padding-top: 4px;">
           		<option value=""><fmt:message key="global.All"/></option>
	           		<c:forEach var="item" items="${transTypeList}">
						<c:choose>
			                <c:when test="${item == transType}">
			                    <option value="${item}" selected="selected">${item}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item}">${item}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> --%>
           </td>
      </tr> 
      <tr>
           <td ><fmt:message key="alDyRpErrorDip.day"/><font color="red">(*)</font></td>
           <td >
           		<input type ="text"  value="${day}" name="day" id="day" size="20" maxlength="10" width="80px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseday" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red">${errorday}<form:errors path="day"/></font>
	    	</td>
	    	 <td ><fmt:message key="alDyRpErrorDip.sdate"/></td>
           <td>
           		<input type ="text"  value="${sdate}" name="sdate" id="sdate" size="20" maxlength="16" width="80px">
	   			 <img alt="calendar" title="Click to choose the start date" id="choosesdate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red">${errorsdate}<form:errors path="sdate"/></font>
	    	</td>
      
           <td ><fmt:message key="alDyRpErrorDip.edate"/></td>
           <td colspan="3">
           		<input type ="text"  value="${edate}" name="edate" id="edate" size="20" maxlength="16" width="80px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseedate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	   			<font color="red">${erroredate}<form:errors path="edate"/></font>
	    	 </td>
	   </tr>
	   <tr>
           <td><fmt:message key="alDyRpErrorDip.teamProcess"/><font color="red">(*)</font></td>
           <td>
           <select name="teamProcess" id="teamProcess" style="width: 160px;height:20px; padding-top: 4px;">
           		<c:forEach var="item" items="${teamList}">
					<c:choose>
		                <c:when test="${item.deptCode == team}">
		                    <option value="${item.deptCode}" selected="selected">${item.deptCode}</option>
		                </c:when>
						<c:otherwise>
							<option value="${item.deptCode}">${item.deptCode}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> 
			<font color="red"><form:errors path="teamProcess"/></font>
           </td>
           <td><fmt:message key="alDyRpErrorDip.userProcess"/></td>
           <td style="border:0;" colspan="5">
           		<select name="userProcess" id="userProcess" multiple="multiple" style="width:160px;"></select>
           </td>
       </tr>
       <tr>
           <td><fmt:message key="alDyRpErrorDip.causebyContent"/></td>
           <td colspan="7"> 
           	<form:input type ="text" path="causebyContent" maxlength="250" style="width:100%;" rows="3" name="causebyContent" id="causebyContent"/>
           </td>
       </tr>
        <tr>
           <td><fmt:message key="alDyRpErrorDip.resultContent"/></td>
           <td colspan="7"> 
           <textarea style="font-family:tahoma;width:100%; font-size:12px;height:30px;" rows="10"  name="resultContent" id="resultContent" maxlength="250" >${alDyRpErrorDip.resultContent}</textarea>	
          </td>
       </tr>
       <tr>
           <td></td>
           <td colspan="7">
               <input type="submit" class="button" value="<fmt:message key="button.save"/>" />
           	   <input type="button" class="button" value="<fmt:message key="button.cancel"/>" onClick='window.location="mat_han.htm"'>
					
           </td>
       </tr>
    </table>
</form:form>

<style>
    .error {
    	color: red;
    }
</style> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>

<link type="text/css" rel="Stylesheet" href="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.8.23.custom.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui.min-1.8.9.js"></script>



<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.filter.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/assets/style.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/assets/prettify.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.filter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/assets/prettify.js"></script>

<script type="text/javascript">
    Calendar.setup({
        inputField		:	"day",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "chooseday",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });

    Calendar.setup({
        inputField		:	"sdate",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "choosesdate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
    Calendar.setup({
        inputField		:	"edate",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseedate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
  
</script>
<script type="text/javascript">

	$('#item>tbody>tr').click(function() {  
		var value = $(this).find(".CAUSEBY").text();
	  $("#causebyContent").val(value);
	});	
</script>

<script type="text/javascript">

function focusIt()
{
	if(document.checkform.bscid.value==""){
	  var mytext = document.getElementById("bscid");
	  mytext.focus();
	}
}

onload = focusIt;

</script>
<script type="text/javascript">
	$(function(){
		var teamProcess = $("#teamProcess").val();
		var userProcess = '<c:out value="${alDyRpErrorDip.userProcess}"/>';
		var $widget;
		
		if (teamProcess != null && teamProcess != '') {

			// Tao multiselect jquery
			$widget = $("#userProcess").multiselect(), state = true;
			$widget.multiselect('enable');
			
			// Khoi tao userProcess
			$.getJSON("${pageContext.request.contextPath}/ajax/getUserByDeparment.htm",{dept: teamProcess,team:''}, function(j){
				var options = '';
				for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].username + '">' + j[i].username+ '</option>';
				}
				$("#userProcess").html(options);
		
				var userProcessVar = userProcess.split(",");
				if (userProcessVar != null) {
					for (var i=0; i<userProcessVar.length; i++) {
						$("#userProcess option[value='" + userProcessVar[i] + "']").attr('selected', 'selected');
					}
				}
				
				$("#userProcess").multiselect('refresh');
			});

			
		} else {
			$widget = $("#userProcess").multiselect(), state = true;
			$widget.multiselect('disable');
		}
		
		$("#teamProcess").change(function(){
			if ($("#teamProcess").val() == '' || $("#teamProcess").val() == null) {
				$widget.multiselect('disable');
			} else {
				/* $("#ngglIdError").text(''); */
				
				$widget.multiselect('enable');

				$.getJSON("${pageContext.request.contextPath}/ajax/getUserByDeparment.htm",{dept: $(this).val(),team:''}, function(j){
					var options = '';
					for (var i = 0; i < j.length; i++) {
						options += '<option value="' + j[i].username + '">' + j[i].username+ '</option>';
					}
					$("#userProcess").html(options);
					$("#userProcess").multiselect('refresh');
				});

			}
		});
	});
</script> 
<script type="text/javascript">
	$(function() {
		var availableTags = [];
		var i = 0;
		<c:forEach items="${faultTypeList}" var="listOfNames">
			availableTags[i] = "<c:out value='${listOfNames}' escapeXml='false' />";
			i = i + 1;
		</c:forEach>
		loadfaultType(availableTags);

		var availableTags2 = [];
		var i = 0;
		<c:forEach items="${transTypeList}" var="listOfNames1">
			availableTags2[i] = "<c:out value='${listOfNames1}' escapeXml='false' />";
			i = i + 1;
		</c:forEach>
		loadtransType(availableTags2);
	});
	function loadtransType(availableTags){
		function split( val ) {
		return val.split( /,\s*/ );
		}
		function extractLast( term ) {
		return split( term ).pop();
		}
		$("#transType")
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
	//	terms.pop();
		//check and add the selected item
		
		//	terms.push( ui.item.value );
			// add placeholder to get the comma-and-space at the end
		//	terms.push( "" );
		//	this.value = terms;
		this.value = ui.item.value;
		return false;
		}
		});
	}	

	function loadfaultType(availableTags){
		function split( val ) {
		return val.split( /,\s*/ );
		}
		function extractLast( term ) {
		return split( term ).pop();
		}
		$("#faultType")
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
		//terms.pop();
		//check and add the selected item
		
			//terms.push( ui.item.value );
			// add placeholder to get the comma-and-space at the end
		//	terms.push( "" );
		//	this.value = terms;
		this.value = ui.item.value;
		return false;
		}
		});
	}	
</script>
  