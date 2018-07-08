<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>${titleForm}</title>
<content tag="heading">${titleForm}</content>
<style>
	.ui-multiselect {
		width:400 !important;
	}
	 #teamProcess {
     visibility: visible !important;
    }
</style>
<form:form commandName="alarmExtend" name="checkform" id="myform" method="post" action="form.htm"> 
	<form:hidden path="id"/>
	<input type="hidden" name="note" id="note" value="${note}">
	<input type="hidden" name="region" id="region" value="${region}">
	<table class="simple2"> 
      <tr>
           <td style="width:150px;"><fmt:message key="vAlAlarmLog.content"/><font color="red">(*)</font></td>
           <td colspan="5">
           		<form:input type ="text" path="alarm" maxlength="500" style="width:95%;" rows="3"/>
           		<font color="red"><form:errors path="alarm"/></font>
           </td>
      </tr> 
		<tr>
			<td><fmt:message key="vAlAlarmLog.dept"/><font color="red">(*)</font></td>
           <td style="width:200px;">
           		<div id='cbdept'></div><form:hidden path="dept"  id ="dept"/>
				<font color="red"><form:errors path="dept"/></font>
           </td>
           <td><fmt:message key="vAlAlarmLog.teamProcess"/><font color="red">(*)</font></td>
           <td style="width:200px;">
          <!--   <div id='teamProcess'></div> -->
          <form:input type ="text" path="teamProcess" maxlength="500" style="width:95%;" rows="3"/>
			<font color="red"><form:errors path="teamProcess"/></font>
           </td>
       
        <td style="width:120px;"><fmt:message key="vAlAlarmLog.userExcute"/></td>
           <td style="border:0;">
           		<%-- <div id='cbUserProcess'></div><form:hidden path="userProcess"  id ="userProcess"/> --%>
        		<!-- <select name="userProcess" id="userProcess" multiple="multiple" style="width:160px;"></select> -->
				<form:input type ="text" path="userProcess" maxlength="500" style="width:95%;" rows="3"/>
            
           </td>
       </tr>
      <tr>
      	<td style="width:120px;"><fmt:message key="vAlAlarmLog.area"/></td>
           <td style="width:200px;">
         	</div><form:input type ="text" path="area" maxlength="500" style="width:95%;" rows="3"/>
            
               <%--       <select name="area" id="area" style="width: 160px;height:20px; padding-top: 4px;">
						<c:forEach var="item" items="${areaList}">
							<c:choose>
				                <c:when test="${item.placesCode == alarmExtend.area}">
				                    <option value="${item.placesCode}" selected="selected">${item.placesCode}</option>
				                </c:when>
								<c:otherwise>
									<option value="${item.placesCode}">${item.placesCode}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select> --%>
           </td> 
          <td align="left"><fmt:message key="vAlAlarmLog.sdate"/><font color="red">(*)</font></td>
	    	<td>
	    		<input type ="text"  value="${startTime}" name="startTime" id="startTime" size="20" maxlength="19" width="80px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red">${errorStartTime}<form:errors path="stime"/></font>
	    	</td>
           <td align="left"><fmt:message key="vAlAlarmLog.edate"/></td>
	    	<td colspan="3">
	    		<input type ="text"  value="${endTime}" name="endTime" id="endTime" size="20" maxlength="19" width="80px">
	    		<img alt="calendar" title="Click to choose the stop date" id="chooseStopDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				<font color="red">${errorendTime}<form:errors path="etime"/></font>
			</td>
      </tr>     
      <tr>
           <td><fmt:message key="vAlAlarmLog.resultsProcess"/></td>
           <td colspan="5">
          		<%--  <textarea style="font-family:tahoma;width:100%; font-size:12px;height:30px;" rows="3"  name="result" id="result" maxlength="900" >${alarmExtend.resultsProcess}</textarea> --%>
           			<form:textarea path="resultsProcess" style="font-family:tahoma;width:100%; font-size:12px;height:30px;" rows="3" maxlength="900"/>
           	 </td>
       </tr>   
       
        <tr>
           <td><fmt:message key="vAlAlarmLog.description"/></td>
           <td colspan="5"> 
           <textarea style="font-family:tahoma;width:100%; font-size:12px;height:30px;" rows="5"  name="description" id="description" maxlength="900" >${alarmExtend.description}</textarea>	
          </td>
       </tr>
     
       <tr>
           <td></td>
           <td colspan="5">
               <input type="submit" class="button" id = "btsubmit" value="<fmt:message key="global.form.luulai"/>" />
           			<c:choose>
           			<c:when test="${note == 'caTruc'}">
		                   <input type="button" class="button" id = "btCancel" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="${pageContext.request.contextPath}/caTruc/list.htm"'>
						
		                </c:when>
		                 <c:when test="${note == 'filter'}">
			                   <input type="button" class="button" id="btCancel" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="${pageContext.request.contextPath}/caTruc/listFilter.htm"'>
							
			             </c:when>
						<c:otherwise>
							 <input type="button" class="button" id = "btCancel" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm"'>
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
        inputField		:	"startTime",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });

    Calendar.setup({
        inputField		:	"endTime",	// id of the input field
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

$(document).ready(function(){



	var theme =  getTheme(); 
	$('#btsubmit').jqxButton({ theme: theme });
	$('#btCancel').jqxButton({ theme: theme });
	
	//input alarm

	$("#alarm").jqxInput({placeHolder: "Enter alarm", height: 25, width: '100%', minLength: 0, maxLength: 170, theme: theme});
	$("#area").jqxInput({placeHolder: "Enter area", height: 25, width: '90%', minLength: 0, maxLength: 50, theme: theme});
	//combobox dept
	var urldept = "${pageContext.request.contextPath}/ajax/getDeptListAlarm.htm";

    // prepare the data

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
    $("#cbdept").jqxDropDownList({source: dataAdapterdept, displayMember: "deptName", valueMember: "deptName",checkboxes: true, width: 160, height: 25, theme: theme,autoOpen: true,enableHover: true });           

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
	         terms.push("");
	         value = terms.join(",");
	    	}
       
        return value;
    };
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
       		var actionProcess = 'Báo đài ';
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

 // prepare the data
/* 
   var sourceArea =

    {

        datatype: "json",

        url: urlArea,

        datafields: [

                     { name: 'placesCode'}

                 ],

        async: false

    };

    var dataAdapterArea = new $.jqx.dataAdapter(sourceArea);

   $("#area").jqxDropDownList({ source: dataAdapterArea,displayMember: "placesCode", valueMember: "placesCode", width: 160,height: 25,itemHeight: 30,theme: theme,autoOpen: true,enableHover: true }); 

 	var area =  "<c:out value='${alarmExtend.area}'/>";

 	$("#area").jqxDropDownList('insertAt', { label: '', value: ''}, 0 );

 	if(area!=null&&area!='')

 		{

			$('#area').val(systemChosse);

 		}
 */
  

    
	

});	

</script>

