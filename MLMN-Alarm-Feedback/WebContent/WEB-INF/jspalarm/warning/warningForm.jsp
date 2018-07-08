<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>${titleWarning}</title>
<content tag="heading">${titleWarning}</content>
<style>
	.ui-multiselect {
		width:160px !important;
	}
	 #teamProcess {
     visibility: visible !important;
    }
</style>
<form:form commandName="warning" name="checkform" id="myform" method="post" action="form.htm"> 
	<form:hidden path="id"/>
	<input type="hidden" name="warningTp" id="warningTp" value="${warningTp}">
	<input type="hidden" name="note" id="note" value="${note}">
	<input type="hidden" name="region" id="region" value="${region}">
	
    <table class="simple2"> 
      <tr>
           <td style="width: 150px;"><fmt:message key="vAlAlarmLog.alarmName"/><font color="red">(*)</font></td>
           <td colspan="5">
           		<form:input type ="text"  path="alarm" maxlength="255" style="width:95%;" rows="3"/>
           	
           		<font color="red"><form:errors path="alarm"/></font>
           </td>
      </tr>
      <tr>
           <td><fmt:message key="vAlAlarmLog.system"/><font color="red">(*)</font></td>
          <c:choose>
			<c:when test="${warningTp=='PS_CORE'}">
		    	<!-- <td>
           		<div id='systemChosse' align="left"></div>
				</td> -->
				<td colspan="5">
					<form:input type ="text" path="system" maxlength="45" style="width:500px;;height:20px;" rows="3"/>
		            <font color="red">${errorsystem}<form:errors path="system"/></font>
         		</td>			
			</c:when>
		 	<c:otherwise>
		 		<td colspan="5">
					<input type="hidden" id="systemChosse"/>
					<form:input type ="text" path="system" maxlength="45" style="width:500px;;height:20px;" rows="3"/>
		            <font color="red"><form:errors path="system"/></font>
          		</td>
          </c:otherwise>
         </c:choose>			
      </tr>
      <tr>
          <td align="left"><fmt:message key="vAlAlarmLog.sdate"/><font color="red">(*)</font></td>
	    	<td style="width: 200px;">
	    		<input type ="text"  value="${startTime}" name="stime" id="stime" size="20" maxlength="16" width="80px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red">${errorStartTime}<form:errors path="stime"/></font>
	    	</td>
           <td align="left" style="width: 120px;"><fmt:message key="vAlAlarmLog.edate"/></td>
	    	<td colspan="3">
	    		<input  type ="text"  value="${endTime}" name="etime" id="etime" size="20" maxlength="16" width="80px">
	    		<img alt="calendar" title="Click to choose the stop date" id="chooseStopDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				<font color="red">${errorendTime}<form:errors path="etime"/></font>
			</td>
      </tr>  
       <tr>
          <%--  <td><fmt:message key="vAlAlarmLog.teamProcess"/></td>
           <td>
            <div id='teamProcess'></div>
           </td>
          <td><fmt:message key="vAlAlarmLog.userExcute"/></td>
           <td style="border:0;">
           		<div id='cbUserProcess'></div><form:hidden path="userProcess"  id ="userProcess"/>
           </td> --%>
           <td><fmt:message key="vAlAlarmLog.dept"/></td>
           <td style="width:200px;">
           		<div id='cbdept'></div><form:hidden path="dept"  id ="dept"/>
				<font color="red"><form:errors path="dept"/></font>
           </td>
           <td><fmt:message key="vAlAlarmLog.teamProcess"/></td>
           <td style="width:200px;">
      
          		<form:input type ="text" path="teamProcess" maxlength="500" style="width:95%;" rows="3"/>
				<font color="red"><form:errors path="teamProcess"/></font>
           </td>
       
        <td style="width:120px;"><fmt:message key="vAlAlarmLog.userExcute"/></td>
           <td style="border:0;">
           		<form:input type ="text" path="userProcess" maxlength="500" style="width:95%;" rows="3"/>
            
           </td>
       </tr>   
      <tr>
           <td><fmt:message key="vAlAlarmLog.alarmInfo"/></td>
           <td colspan="5">
           		<form:input type ="text" path="alarmInfo" maxlength="250" style="width:100%;" rows="3" name="alarmInfo" id="alarmInfo"/>
           </td>
      </tr>
      <tr>
           <td><fmt:message key="vAlAlarmLog.causeby"/></td>
           <td colspan="5">
          			<form:input type ="text" path="causeby" maxlength="250" style="width:100%;" rows="3" name="causeby" id="causeby"/>
           </td>
       </tr>   
       
        <tr>
           <td><fmt:message key="vAlAlarmLog.actionProcess"/></td>
           <td colspan="5"> 
           		<form:input type ="text" path="actionProcess" maxlength="250" style="width:100%;" rows="3" name="actionProcess" id="actionProcess"/>
           </td>
       </tr>
      
        <tr>
           <td><fmt:message key="vAlAlarmLog.resultsProcess"/></td>
           <td colspan="5"> 
           		<form:input type ="text" path="resultsProcess" maxlength="250" style="width:100%;" rows="3" name="resultsProcess" id="resultsProcess"/>
          </td>
       </tr>
        <tr>
           <td><fmt:message key="vAlAlarmLog.reportTreatment"/></td>
           <td colspan="5"> 
           <form:input type ="text" path="reportProcess" maxlength="250" style="width:100%;" rows="3" name="reportProcess" id="reportProcess"/>
          	</td>
       </tr>
      
       <tr>
           <td></td>
           <td colspan="5">
               <input type="submit" class="button" id="btsubmit" value="<fmt:message key="global.form.luulai"/>" />
               <c:choose>
	           			<c:when test="${note == 'caTruc'}">
			                   <input type="button" class="button" id="btCancel" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=${warningTp}&region=${region}"'>
							
			             </c:when>
			             <c:when test="${note == 'filter'}">
			                   <input type="button" class="button" id="btCancel" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=${warningTp}&region=${region}"'>
							
			             </c:when>
							<c:otherwise>
								<input type="button" class="button" id="btCancel" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm?warningTp=${warningTp}"'>
							</c:otherwise>
					</c:choose>	
               
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

<!-- <link type="text/css" rel="Stylesheet" href="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.8.23.custom.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui.min-1.8.9.js"></script>



<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.filter.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/assets/style.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/assets/prettify.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.filter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/assets/prettify.js"></script> -->
<script type="text/javascript">
    Calendar.setup({
        inputField		:	"stime",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });

    Calendar.setup({
        inputField		:	"etime",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseStopDate",   	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
	
</script>
<script type="text/javascript">

function focusIt()
{
	if(document.checkform.alarm.value==""){
	  var mytext = document.getElementById("alarm");
	  mytext.focus();
	}
}

onload = focusIt;

</script>
<script type="text/javascript">
$(document).ready(function(){
	var theme =  getTheme(); 
	 // Create a jqxMultile Input
	var renderer = function (itemValue, inputValue) {
        var terms = inputValue.split(/,\s*/);
        var value = inputValue;
     
         if (inputValue.indexOf(itemValue) < 0)
	    	{
      
        	 // remove the current input
             terms.pop();
             // add the selected item
	    	 terms.push(itemValue);
	         // add placeholder to get the comma-and-space at the end
	        // terms.push("");
	         value = terms.join(",");
	    	}
       
        return value;
    };
    
	$('#btsubmit').jqxButton({ theme: theme });
	$('#btCancel').jqxButton({ theme: theme });

	//input alarm
	//$("#alarm").jqxInput({placeHolder: "Enter alarm", height: 20, width: '100%', minLength: 0, maxLength: 170, theme: theme});
	 ${alarmList}
	 $("#alarm").jqxInput({ placeHolder: "Enter alarm",  height: 20, width: '98%', minLength: 0, maxLength: 170, theme: theme,
        source: alarmList
    });
	//ComboBox operator
 	${systemList}
 	$("#system").jqxInput({ height: 20, width: 500, theme: theme,
        source: function (query, response) {
            var item = query.split(/,\s*/).pop();
            // update the search query.
            $("#system").jqxInput({ query: item });
            response(systemList);
        },
        renderer: renderer
    });
 	var system =  "<c:out value='${system}'/>";
 	//alert(cellid);
    if(system!="")
		$('#system').val(system);
   
  //input system
  //  $("#system").jqxInput({ height: 20, width: "100%", minLength: 1, maxLength: 48, theme: theme});
   //input causeby
    $("#causeby").jqxInput({placeHolder: "Enter causeby", height: 20, width: '100%', minLength: 0, maxLength: 250, theme: theme}); 
    //input causeby
    $("#resultsProcess").jqxInput({placeHolder: "Enter results process", height: 20, width: '100%', minLength: 0, maxLength: 200, theme: theme});
  //input alarmInfo
    $("#alarmInfo").jqxInput({placeHolder: "Enter alarmInfo", height: 20, width: '100%', minLength: 0, maxLength: 900, theme: theme});
  //input actionProcess
    $("#actionProcess").jqxInput({placeHolder: "Enter actionProcess", height: 20, width: '100%', minLength: 0, maxLength: 900, theme: theme});
  //input reportProcess
    $("#reportProcess").jqxInput({placeHolder: "Enter report", height: 20, width: '100%', minLength: 0, maxLength: 900, theme: theme});
    
	 // prepare the data
	//combobox dept
	var urldept = "${pageContext.request.contextPath}/ajax/getDeptListAlarm.htm";
    var sourcedept =

    {

       datatype: "json",

       url: urldept,

        datafields: [
                     { name: 'deptCode'},
                     { name: 'deptName'}
                 ],

        async: false

   };

    var dataAdapterdept = new $.jqx.dataAdapter(sourcedept);
    $("#cbdept").jqxDropDownList({source: dataAdapterdept, displayMember: "deptName", valueMember: "deptName",checkboxes: true, width: 160, height: 20, theme: theme,autoOpen: true,enableHover: true });           

    var dept = '<c:out value="${dept}"/>';
   // alert(dept);
	if(dept=="")
		$('#cbdept').val('Choose');
	else
	{
		var deptVar = dept.split(",");
		if (deptVar != null) {
			for (var i=0; i<deptVar.length; i++) {
				$("#cbdept").jqxDropDownList('checkItem', deptVar[i] ); 
			}
		}
	}
   
  
   //Input team
	 var teamList=[];
	$.getJSON("${pageContext.request.contextPath}/ajax/getTeamListAlarm.htm",{dept:dept}, function(j){
		   teamList =j;
	   });
    $("#teamProcess").jqxInput({ height: 20, width: 200, theme: theme,
        source: function (query, response) {
            var item = query.split(/,\s*/).pop();
            // update the search query.
            $("#teamProcess").jqxInput({ query: item });
            response(teamList);
        },
        renderer: renderer
    });
    var teamProcess =  "<c:out value='${teamProcess}'/>";
   // alert(bscid);
    if(teamProcess!="")
		$('#teamProcess').val(teamProcess);
    
  //Input user
	 var userList=[];
	$.getJSON("${pageContext.request.contextPath}/ajax/getUserByDeparmentTeam.htm",{dept:dept,team:teamProcess}, function(k){
		userList =k;
	   });
   $("#userProcess").jqxInput({ height: 20, width: "100%", theme: theme,
       source: function (query, response) {
           var item = query.split(/,\s*/).pop();
           // update the search query.
           $("#userProcess").jqxInput({ query: item });
           response(userList);
       },
       renderer: renderer
   });
   var userProcess =  "<c:out value='${userProcess}'/>";
  // alert(bscid);
   if(userProcess!="")
		$('#userProcess').val(userProcess);
   
   $("#cbdept").on('checkChange', function (event) {
       if (event.args) {
           var item = event.args.item;
           if (item) {
               var items = $("#cbdept").jqxDropDownList('getCheckedItems');
               var checkedItems = "";
               var con="";
               $.each(items, function (index) {
                   checkedItems += con+this.value ;
                   con= ",";                          
               });
               $("#dept").val(checkedItems);
            var teamProcess = $("#teamProcess").val();
       	   	var dept = $("#dept").val();
	       	 if (teamProcess==null||teamProcess=='null')
	 		  {
	 			teamProcess='';
	 		  }
	 	 	if (dept==null||dept=='null')
			  {
	 			dept='';
			  }
	 	 	var actionProcess = 'Báo đài ' ;
       		if (teamProcess!='')
   			{
   				actionProcess = actionProcess+ teamProcess ;
   			}
   			else
   			{
   				actionProcess = actionProcess+ dept ;
   			}
       		
       		$("#actionProcess").val(actionProcess);	
       		
       		$.getJSON("${pageContext.request.contextPath}/ajax/getTeamListAlarm.htm",{dept:dept}, function(j){
       			   teamList =j;
       			   $("#teamProcess").jqxInput({source: function (query, response) {
       		           var item = query.split(/,\s*/).pop();
       		           // update the search query.
       		           $("#teamProcess").jqxInput({ query: item });
       		           response(teamList);
       		       },
       		       renderer: renderer
       			    });
       		   });	
       		
       		$.getJSON("${pageContext.request.contextPath}/ajax/getUserByDeparmentTeam.htm",{dept:dept,team:teamProcess}, function(k){
       			userList =k;
       			$("#userProcess").jqxInput({source: function (query, response) {
       		           var item = query.split(/,\s*/).pop();
       		           // update the search query.
       		           $("#userProcess").jqxInput({ query: item });
       		           response(userList);
       		       },
       		       renderer: renderer
       		    
       		   });
       		});	
           }
       }
   });
   
   $("#teamProcess").change(function(){
	   var teamProcess = $("#teamProcess").val();
	 
  	   	var dept = $("#dept").val();
  	  if (teamProcess==null||teamProcess=='null')
  		  {
  			teamProcess='';
  		  }
  	 	if (dept==null||dept=='null')
		  {
  			dept='';
		  }
  	 	var actionProcess = 'Báo đài ' ;
   		if (teamProcess!='')
			{
				actionProcess = actionProcess+ teamProcess ;
			}
			else
			{
				actionProcess = actionProcess+ dept ;
			}
   		
   		$("#actionProcess").val(actionProcess);	
	   $.getJSON("${pageContext.request.contextPath}/ajax/getUserByDeparmentTeam.htm",{dept:dept,team:teamProcess}, function(k){
  			userList =k;
  			$("#userProcess").jqxInput({source: function (query, response) {
  		           var item = query.split(/,\s*/).pop();
  		           // update the search query.
  		           $("#userProcess").jqxInput({ query: item });
  		           response(userList);
  		       },
  		       renderer: renderer
  		    
  		   });
  		});	
   });	
	 /* $("#systemChosse").change(function(){

	 		var systemChose = $("#systemChosse").val();

	 		if (systemChose!=null && systemChose!='')

	 			{

	 				var system = $("#system").val();

	 				var systemList = system.split(",");

	 				var kt = false;

	 				if (system != null && system != '') {

	 					for (var i=0; i<systemList.length; i++) {

	 						if (systemChose ==systemList[i] )

	 							kt= true;

	 					}

	 					if (kt == false)

	 					{

	 						system+=','+systemChose;					

	 					}

	 				}

	 				else

	 					{

	 						system+=systemChose;

	 					}

	 				$("#system").val(system);	

	 			}		

	 	}); */ 
});	


</script> 