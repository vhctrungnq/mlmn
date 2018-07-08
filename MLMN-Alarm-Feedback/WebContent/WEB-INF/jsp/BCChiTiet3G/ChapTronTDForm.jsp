<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>${titleF}</title>
<content tag="heading">${titleF}</content>
<style>
	.ui-multiselect {
		width:160px !important;
	}
	 #dvtTeamProcess {
     visibility: visible !important;
    }
</style>
<form:form commandName="alDyRpDip3G" name="checkform" id="myform" method="post" action="chapchon_form.htm"> 
	<form:hidden path="id"/>
	<table class="simple2"> 
      <tr>
           <td style="width: 120px;"><fmt:message key="alDyRpDip3G.rnc"/><font color="red">(*)</font></td>
           <td style="width: 200px;">
           		
				<form:input type ="text" path="rnc" maxlength="18" style="width:160px;" rows="3"/>
		   		<font color="red"><form:errors path="rnc"/></font>
           </td>
      
           <td style="width: 100px;"><fmt:message key="alDyRpDip3G.nodeb"/><font color="red">(*)</font></td>
           <td>
           		<form:input type ="text" path="nodeb" maxlength="18" style="width:160px;" rows="3"/>
         		<font color="red"><form:errors path="nodeb"/></font>
           </td>
      </tr> 
      <tr>
           <td><fmt:message key="alDyRpDip3G.day"/><font color="red">(*)</font></td>
           <td>
           		<input type ="text"  value="${day}" name="day" id="day" size="20" maxlength="10" width="80px">
	   			 <img alt="calendar" title="Click to choose the start date" id="choosesdate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red">${errorsdate}<form:errors path="day"/></font>
	    	</td>
	    	<td ><fmt:message key="alDyRpErrorDip.transType"/></td>
           <td >
           		<form:input type ="text" path="transType" maxlength="18" style="width:160px;" rows="3" id = "transType" name="transType"/>
           	</td>
      </tr> 
		<tr>
           <td><fmt:message key="alDyRpDip3G.dvtTeamProcess"/></td>
           <td>
           <select name="dvtTeamProcess" id="dvtTeamProcess" style="width: 160px;height:20px; padding-top: 4px;">
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
           </td>
      
           <td><fmt:message key="alDyRpDip3G.dvtUserProcess"/></td>
           <td style="border:0;">
           		<select name="dvtUserProcess" id="dvtUserProcess" multiple="multiple" style="width:160px;"></select>
           </td>
       </tr>
		<tr>
           <td><fmt:message key="alDyRpDip3G.causebyContent"/></td>
           <td colspan="3"> 
           		<form:input type ="text" path="causebyContent" maxlength="250" style="width:100%;" rows="3" name="causeby" id="causeby"/>
          	
           	</td>
       </tr>
       <tr>
           <td><fmt:message key="alDyRpDip3G.resultContent"/></td>
	        <td colspan="3"> 
	           	<form:textarea path="resultContent" style="font-family:tahoma;width:100%; font-size:12px;height:30px;" rows="10" maxlength="900"/>
	           	
	        </td>
       </tr>
       <tr>
           <td></td>
           <td colspan="3">
               <input type="submit" class="button" value="<fmt:message key="button.save"/>" />
           	   <input type="button" class="button" value="<fmt:message key="button.cancel"/>" onClick='window.location="chapchon.htm"'>
					
           </td>
       </tr>
    </table>
<%-- <div><span style="color: blue; padding-top: 10px;"><fmt:message key="vAlAlarmLog.listSame"/></span></div>
<div id="doublescroll" style=" overflow-x: auto;overflow-y: hidden;">
		<display:table name="${alarmSameList}" class="simple2" id="item" requestURI="" pagesize="15" sort="external" defaultsort="1">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="alarmName" titleKey="rAlarmLog.alarmName" sortable="true" sortName="ALARM_NAME"/>
	  	<display:column property="bscid"  titleKey="alDyRpDip3G.rnc" sortable="true" sortName="BSCID"/>
		<display:column property="device" titleKey="alDyRpDip3G.nodeb" sortable="true" sortName="DEVICE"/>
		<display:column property="alarmType"  titleKey="rAlarmLog.alarmType" sortable="true" sortName="ALARM_TYPE" style="max-width: 150px;word-wrap: break-word;"/>
		 <display:column property="day"  titleKey="WarningDipSame.day" format="{0,date,dd/MM/yyyy}" sortable="true" sortName="DAY" /> 
		<display:column property="causeby" titleKey="WarningDipSame.causeby" sortable="true" sortName="CAUSEBY" class="CAUSEBY"/>
		<display:column property="actionProcess" titleKey="WarningDipSame.actionProcess" sortable="true" sortName="ACTION_PROCESS"/>
	</display:table>
</div> --%>
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
<!-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/jquery-ui.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script> -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.filter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/assets/prettify.js"></script>
<script type="text/javascript">
    Calendar.setup({
        inputField		:	"day",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "choosesdate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
    
</script>
<script type="text/javascript">

function focusIt()
{
	if(document.checkform.mscid.value==""){
	  var mytext = document.getElementById("rncid");
	  mytext.focus();
	}
}

onload = focusIt;

</script>
<script type="text/javascript">

	$('#item>tbody>tr').click(function() {  
		var value = $(this).find(".CAUSEBY").text();
	  $("#causeby").val(value);
	});	
</script>
<script type="text/javascript">
	$(function(){
		var dvtTeamProcess = $("#dvtTeamProcess").val();
		var dvtUserProcess = '<c:out value="${alDyRpDip3G.dvtUserProcess}"/>';
		var $widget;
		
		if (dvtTeamProcess != null && dvtTeamProcess != '') {

			// Tao multiselect jquery
			$widget = $("#dvtUserProcess").multiselect(), state = true;
			$widget.multiselect('enable');
			
			// Khoi tao dvtUserProcess
			$.getJSON("${pageContext.request.contextPath}/ajax/getUserByDeparment.htm",{dept: dvtTeamProcess,team:''}, function(j){
				var options = '';
				for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].username + '">' + j[i].username+ '</option>';
				}
				$("#dvtUserProcess").html(options);
		
				var dvtUserProcessVar = dvtUserProcess.split(",");
				if (dvtUserProcessVar != null) {
					for (var i=0; i<dvtUserProcessVar.length; i++)  {
						$("#dvtUserProcess option[value='" + dvtUserProcessVar[i] + "']").attr('selected', 'selected');
					}
				}
				
				$("#dvtUserProcess").multiselect('refresh');
			});

			
		} else {
			$widget = $("#dvtUserProcess").multiselect(), state = true;
			$widget.multiselect('disable');
		}
		
		$("#dvtTeamProcess").change(function(){
			if ($("#dvtTeamProcess").val() == '' || $("#dvtTeamProcess").val() == null) {
				$widget.multiselect('disable');
			} else {

				$widget.multiselect('enable');

				$.getJSON("${pageContext.request.contextPath}/ajax/getUserByDeparment.htm",{dept: $(this).val(),team:''}, function(j){
					var options = '';
					for (var i = 0; i < j.length; i++) {
						options += '<option value="' + j[i].username + '">' + j[i].username+ '</option>';
					}
					$("#dvtUserProcess").html(options);
					$("#dvtUserProcess").multiselect('refresh');
				});

			}
		});
	});
</script> 
<script type="text/javascript">
	$(function() {
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

	
</script>