<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>${title}</title>
<content tag="heading">${title}</content> 	

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxwindow.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxdropdownbutton.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxtree.js"></script>

<style>
.ui-tabs-nav .ui_link a:hover{
color: black;
}
</style>

<style>
 .shiftId
{
color:blue;
}
 </style> 
 <div>
	<div align="left">
	<ul class="ui-tabs-nav">		
		<c:choose>
			<c:when test="${function == 'month'}">
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/assess/cham-diem-ca-truc.htm?function=month"><span><fmt:message key="title.tabControls.month"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/assess/cham-diem-ca-truc.htm?function=week"><span><fmt:message key="title.tabControls.week"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/assess/cham-diem-ca-truc.htm?function=day"><span><fmt:message key="title.tabControls.day"/></span></a></li>
				<%-- <li class=""><a href="${pageContext.request.contextPath}/assess/cham-diem-ca-truc.htm?function=detail"><span><fmt:message key="title.tabControls.detail"/></span></a></li> --%>
			</c:when>
		 	<c:when test="${function == 'week'}">
				<li class=""><a href="${pageContext.request.contextPath}/assess/cham-diem-ca-truc.htm?function=month"><span><fmt:message key="title.tabControls.month"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/assess/cham-diem-ca-truc.htm?function=week"><span><fmt:message key="title.tabControls.week"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/assess/cham-diem-ca-truc.htm?function=day"><span><fmt:message key="title.tabControls.day"/></span></a></li>
				<%-- <li class=""><a href="${pageContext.request.contextPath}/assess/cham-diem-ca-truc.htm?function=detail"><span><fmt:message key="title.tabControls.detail"/></span></a></li> --%>
			</c:when>
			<c:when test="${function == 'day'}">
				<li class=""><a href="${pageContext.request.contextPath}/assess/cham-diem-ca-truc.htm?function=month"><span><fmt:message key="title.tabControls.month"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/assess/cham-diem-ca-truc.htm?function=week"><span><fmt:message key="title.tabControls.week"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/assess/cham-diem-ca-truc.htm?function=day"><span><fmt:message key="title.tabControls.day"/></span></a></li>
				<%-- <li class=""><a href="${pageContext.request.contextPath}/assess/cham-diem-ca-truc.htm?function=detail"><span><fmt:message key="title.tabControls.detail"/></span></a></li> --%>
			</c:when>
			<%-- <c:when test="${function == 'detail'}">
				<li class=""><a href="${pageContext.request.contextPath}/assess/cham-diem-ca-truc.htm?function=month"><span><fmt:message key="title.tabControls.month"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/assess/cham-diem-ca-truc.htm?function=week"><span><fmt:message key="title.tabControls.week"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/assess/cham-diem-ca-truc.htm?function=day"><span><fmt:message key="title.tabControls.day"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/assess/cham-diem-ca-truc.htm?function=detail"><span><fmt:message key="title.tabControls.detail"/></span></a></li>
			</c:when> --%>
		</c:choose>
		</ul>
	</div>
</div>
<div class="clear"></div>
<div class="ui-tabs-panel"> 
<form:form commandName="filter" method="post" action="cham-diem-ca-truc.htm?function=${function}">
	<table >
		
			<tr> 
				<c:choose>
					<c:when test="${function=='week'}">
						<td>Từ tuần</td>
						<td><input value="${startWeek}" name="startWeek" id="startWeek" size="2">
							<img alt="calendar" titleKey="Click to choose the start week number" id="chooseStartWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			           		&nbsp; &nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4">
	            	
			            </td>
			            <td style="padding-left: 10px;">Tới tuần</td>
						<td><input value="${endWeek}" name="endWeek" id="endWeek" size="2">
							<img alt="calendar" titleKey="Click to choose the end week number" id="chooseEndWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
		            		&nbsp; &nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4">
						</td>
					</c:when>
					<c:when test="${function=='month'}">
						<td>Từ tháng</td>
						<td><input value="${startMonth}" name="startMonth" id="startMonth" size="2" maxlength="2">&nbsp;
			               &nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.startYear);">
	            		</td>
			            <td style="padding-left: 10px;">Tới tháng</td>
						<td><input value="${endMonth}" name="endMonth" id="endMonth" size="2" maxlength="2">&nbsp;
	            			&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.endYear);">
	            	</td>
					</c:when>
					<c:otherwise>
						<td style="width:90px"><fmt:message key="catruc.ngayTKF"/></td>
						<td style="width:100px">
								<input type ="text"  value="${dateF}" name="dateF" id="dateF" size="17" maxlength="16" style="width:80px">
					   			 <img alt="calendar" title="Click to choose the from date" id="chooseSDateF" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
						</td>
						<td style="width:30px;padding-left: 5px;">To</td>
						<td style="width:100px">
							 <input type ="text"  value="${dateT}" name="dateT" id="dateT" size="17" maxlength="16" style="width:80px">
					   		<img alt="calendar" title="Click to choose the to date" id="choosesdateT" style="cursor: pointer; position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
							<!-- <div id='edate'></div> -->
						</td>
						<td style="width:70px;padding-left: 5px;">
							<fmt:message key="catruc.caTK"/>
						</td>
						<td>
							<select name="shift" id="shift" style="width: 80px;height:20px; padding-top: 4px;">
								<option value=""><fmt:message key="global.shiftAll"/></option>
								<c:forEach var="item" items="${caList}">
									<c:choose>
						                <c:when test="${item.value == shift}">
						                    <option value="${item.value}" selected="selected">${item.value}</option>
						                </c:when>
										<c:otherwise>
											<option value="${item.value}">${item.value}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select> 
						</td>	
					</c:otherwise>
				</c:choose>
				<td style="padding-left: 10px;"><fmt:message key="alarmLog.network"/></td>
				<td>
					 <input type="text" id="network" name="network" value="${network}" style="width: 50px;"/>
				</td>
				<td style="padding-left: 10px;"><fmt:message key="alarmLog.neType"/></td>
				<td>
					 <input type="text" id="neType" name="neType" value="${neType}"  style="width: 50px;"/>
				</td>
				<td style="width:60px;padding-left: 10px;"><fmt:message key="alarmLog.dept"/></td>
				<td >
					<div id='dept'></div>
				</td>	
				
		</tr>
		<tr>
				<td><fmt:message key="alarmLog.team"/></td>
				<td colspan="3">
					 <input type="text" id="team" name="team" style="width: 100px;"/>
				</td>
				<td style="padding-left: 10px;" ><fmt:message key="alarmLog.groups"/></td>
				<td   colspan="3" >
					 <input type="text" id="groups" name="groups" style="width: 100px;"/>
				</td>
				<td style="padding-left: 10px;"><fmt:message key="alarmLog.users"/></td>
				<td colspan="3">
					 <input type="text" id="users" name="users" style="width: 200px;"/>
					</td>
				<td style="padding-left: 10px;"><input class="button" type="submit" id="btFilter" name="btFilter" value="<fmt:message key="global.form.timkiem"/>" />
				</td>
			</tr>	
		</table>
		<div style="float: right;" id="jqxlistbox"></div>
		<div id="grid"></div>
		 <div id='menuReport'>
		            <ul>
				   		<li><fmt:message key="global.button.exportExcel"/></li>
			        </ul>
		 </div>
	</form:form>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>

<script type="text/javascript">
${grid}

$("#menuReport").on('itemclick', function (event) {
    var args = event.args;
    var exportFileName =  "<c:out value='${exportFileName}'/>";
    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
    	$("#grid").jqxGrid('exportdata', 'xls', exportFileName);
    }
});

var func = '<c:out value="${function}"/>';
if (func=='day')
	{
		Calendar.setup({
		    inputField		:	"dateF",	// id of the input field
		    ifFormat		:	"%d/%m/%Y",   	// format of the input field
		    button			:   "chooseSDateF",  	// trigger for the calendar (button ID)
		    showsTime		:	true,
		    singleClick		:   false					// double-click mode
		}); 
		
		Calendar.setup({
		    inputField		:	"dateT",	// id of the input field
		    ifFormat		:	"%d/%m/%Y",   	// format of the input field
		    button			:   "choosesdateT",   	// trigger for the calendar (button ID)
		    showsTime		:	true,
		    singleClick		:   false					// double-click mode
		}); 	
	}
if (func=='week')
	{
		Calendar.setup({
		    inputField		:	"startWeek",	// id of the input field
		    ifFormat		:	"%W",   	// format of the input field
		    button			:   "chooseStartWeek",  	// trigger for the calendar (button ID)
		    singleClick		:   false					// double-click mode
		});
		Calendar.setup({
		    inputField		:	"endWeek",	// id of the input field
		    ifFormat		:	"%W",   	// format of the input field
		    button			:   "chooseEndWeek",  	// trigger for the calendar (button ID)
		    singleClick		:   false					// double-click mode
		});
	}
$(document).ready(function(){
	var theme =  getTheme();
	$('#btFilter').jqxButton({ theme: theme });
	//Input team
   var urldept = "${pageContext.request.contextPath}/ajax/getDeptListAlarm.htm";
    var sourcedept =

    {

       datatype: "json",

       url: urldept,

        datafields: [
                     { name: 'deptCode'},
                     { name: 'deptName'},
                     { name: 'abbreviated'}, 
                 ],

        async: false

   };

    var dataAdapterdept = new $.jqx.dataAdapter(sourcedept);
    $("#dept").jqxDropDownList({source: dataAdapterdept, displayMember: "deptName", valueMember: "deptName",checkboxes: true, width: 160, height: 20, theme: theme,autoOpen: true,enableHover: true });           

    var dept = '<c:out value="${dept}"/>';
   // alert(dept);
	if(dept=="")
		$('#dept').val('Choose');
	else
	{
		$('#dept').val(dept);
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
	        // terms.push("");
	         value = terms.join(",");
	    	}
       
        return value;
    };
   //Input team
	 var teamList=[];
	$.getJSON("${pageContext.request.contextPath}/ajax/getTeamListAlarm.htm",{dept:dept}, function(j){
		   teamList =j;
	   });
    $("#team").jqxInput({ height: 20, width: 250, theme: theme,
        source: function (query, response) {
            var item = query.split(/,\s*/).pop();
            // update the search query.
            $("#team").jqxInput({ query: item });
            response(teamList);
        },
        renderer: renderer
    });
    var team =  "<c:out value='${team}'/>";
   // alert(bscid);
    if(team!="")
		$('#team').val(team);
    
  //Input team
	 var groupList=[];
	$.getJSON("${pageContext.request.contextPath}/ajax/getGroupListAlarm.htm",{dept:dept,team:team}, function(j){
		groupList =j;
	   });
   $("#groups").jqxInput({ height: 20, width: '100%', theme: theme,
       source: function (query, response) {
           var item = query.split(/,\s*/).pop();
           // update the search query.
           $("#groups").jqxInput({ query: item });
           response(groupList);
       },
       renderer: renderer
   });
   var groups =  "<c:out value='${groups}'/>";
  // alert(bscid);
   if(groups!="")
		$('#groups').val(groups);
   
  //Input user
	 var userList=[];
	$.getJSON("${pageContext.request.contextPath}/ajax/getUserByDeparmentTeam.htm",{dept:dept,team:team}, function(k){
		userList =k;
	   });
   $("#users").jqxInput({ height: 20, width: "100%", theme: theme,
       source: function (query, response) {
           var item = query.split(/,\s*/).pop();
           // update the search query.
           $("#users").jqxInput({ query: item });
           response(userList);
       },
       renderer: renderer
   });
   var users =  "<c:out value='${users}'/>";
  // alert(bscid);
   if(users!="")
		$('#users').val(users);
   
   $("#team").change(function(){
	   var team = $("#team").val();
	   var dept= $("#dept").val();
	  	  if (team==null||team=='null')
  		  {
  				team='';
  		  }
	  	if (dept==null||dept=='null')
		  {
	  		dept='';
		  }
  	 $.getJSON("${pageContext.request.contextPath}/ajax/getGroupListAlarm.htm",{dept:dept,team:team}, function(j){
	   		groupList =j;
		   $("#groups").jqxInput({source: function (query, response) {
	           var item = query.split(/,\s*/).pop();
	           // update the search query.
	           $("#groups").jqxInput({ query: item });
	           response(groupList);
	       },
	       renderer: renderer
		    });
	   });	
  	$.getJSON("${pageContext.request.contextPath}/ajax/getUserByDeparmentTeam.htm",{dept:dept,team:team}, function(k){
		userList =k;
		$("#users").jqxInput({ height: 20, width: "100%", theme: theme,
		       source: function (query, response) {
		           var item = query.split(/,\s*/).pop();
		           // update the search query.
		           $("#users").jqxInput({ query: item });
		           response(userList);
		       },
		       renderer: renderer
		   });
	   });
   
	});	
   $("#dept").on('change', function (event) {
	   var team = $("#team").val();
	   var dept= $("#dept").val();
	  	  if (team==null||team=='null')
  		  {
  				team='';
  		  }
	  	if (dept==null||dept=='null')
		  {
	  		dept='';
		  }
	  	 $.getJSON("${pageContext.request.contextPath}/ajax/getGroupListAlarm.htm",{dept:dept,team:team}, function(j){
		   		groupList =j;
			   $("#groups").jqxInput({source: function (query, response) {
		           var item = query.split(/,\s*/).pop();
		           // update the search query.
		           $("#groups").jqxInput({ query: item });
		           response(groupList);
		       },
		       renderer: renderer
			    });
		   });	
	  	$.getJSON("${pageContext.request.contextPath}/ajax/getUserByDeparmentTeam.htm",{dept:dept,team:team}, function(k){
			userList =k;
			$("#users").jqxInput({ height: 20, width: "100%", theme: theme,
			       source: function (query, response) {
			           var item = query.split(/,\s*/).pop();
			           // update the search query.
			           $("#users").jqxInput({ query: item });
			           response(userList);
			       },
			       renderer: renderer
			   });
		   });
   });
  
	   //call view detail    
	   $("#grid").on('cellselect', function (event) 
	  		{
	   		
	   			var funtion =  "<c:out value='${function}'/>";   
	    		var columnheader = $("#grid").jqxGrid('getcolumn', event.args.datafield).datafield; 
	    		var rowindex = event.args.rowindex;
  		    	var row = $("#grid").jqxGrid('getrowdata', rowindex);
  		    	var status='';
  		    	if (columnheader.indexOf("pro") !=-1)
  		    		{
  		    			status='Y';
  		    		}
  		    	var network = row.network;
    			var neType= row.neType;
    			var severity='';
    			if (columnheader.indexOf("A1") !=-1)
				{
					severity='A1';
				}
    			else if (columnheader.indexOf("A2") !=-1)
				{
					severity='A2';
				}
  		    	//alert(columnheader+'-'+status);
  		    	if (funtion == 'month')
	    		{
	    			var month = row.monthAssess;
	    			var year = row.yearAssess;
	    			var user = row.username;
	    			
	    			var type = 'MONTH_DETAIL';
	    
					if (columnheader=='rateWorkSucc') {
						window.open('${pageContext.request.contextPath}/assess/detail.htm?function=month&startMonth='+month+
    							'&endMonth='+month+
    							'&startYear='+year+
    							'&endYear='+year+
    							'&users='+user+
    							'&status='+status+
    							'&network='+network+
								'&neType='+neType+
								'&severity='+severity+
								'&columnheader='+columnheader+
								'&type=work','_blank');
					}
					else
					{
						window.open('${pageContext.request.contextPath}/assess/cham-diem-ca-truc.htm?function=day&startMonth='+month+
    							'&startYear='+year+
    							'&endMonth='+month+
    							'&endYear='+year+
								'&users='+user+
								'&network='+network+
								'&neType='+neType+
								'&severity='+severity+
								'&columnheader='+columnheader+
							'&type='+type,'_blank');
					}
	    		}
  		    	
  		    	if (funtion == 'week')
	    		{
	    			
	    			var year = row.yearAssess;
	    			var week = row.weekAssess;
	    			var user = row.username;
	    			var type = 'WEEK_DETAIL';
	    			/* if (columnheader=='proAlarm'||columnheader=='alarmQuantity'
	    					||columnheader=='workshiftQuantity'||columnheader=='proWorkshift'
	    					||columnheader=='rateAlarmSucc'||columnheader=='rateWorkShiftSucc')
    				{
	    				window.open('${pageContext.request.contextPath}/assess/cham-diem-ca-truc.htm?function=day&startWeek='+week+
	    							'&endWeek='+week+
	    							'&startYear='+year+
	    							'&endYear='+year+
									'&users='+user+
									'&network='+network+
									'&neType='+neType+
									'&severity='+severity+
									'&columnheader='+columnheader+
									'&type='+type,'_blank');
    				}
	    			else */
	    			if (columnheader=='rateWorkSucc') {
	    				window.open('${pageContext.request.contextPath}/assess/detail.htm?function=week&startWeek='+week+
    							'&endWeek='+week+
    							'&startYear='+year+
    							'&endYear='+year+
								'&users='+user+
								'&status='+status+
								'&network='+network+
								'&neType='+neType+
								'&severity='+severity+
								'&columnheader='+columnheader+
								'&type=work','_blank');
					}
	    			else
    				{
	    				window.open('${pageContext.request.contextPath}/assess/cham-diem-ca-truc.htm?function=day&startWeek='+week+
    							'&endWeek='+week+
    							'&startYear='+year+
    							'&endYear='+year+
								'&users='+user+
								'&network='+network+
								'&neType='+neType+
								'&severity='+severity+
								'&columnheader='+columnheader+
								'&type='+type,'_blank');
    				
    				}
	    		}
  		    	if (funtion == 'day')
	    		{
	    			var day = new Date(row.dayShift);
	    			var catruc=row.catruc;
	    			var date ='';
	  		    	if (day.getDate()<=9)
	  		    		{
	  		    		 date +='0';
	  		    		}
	  		    	date+=day.getDate() + '/';
	  		    	if (day.getMonth()+1<=9)
	  		    		{
	  		    		 date +='0';
	  		    		}
	  		    	date+=(day.getMonth()+1) + '/' +day.getFullYear();
	    			
	    			if (columnheader.indexOf("Alarm") !=-1||columnheader.indexOf("alarm") !=-1)
    				{
	    				window.open('${pageContext.request.contextPath}/assess/detail.htm?function=day&dateF='+date+
	    						'&dateT='+date+
    							'&catruc='+catruc+
    							'&status='+status+
    							'&network='+network+
								'&neType='+neType+
								'&severity='+severity+
								'&columnheader='+columnheader+
									'&type=alarm','_blank');
    				}
	    			if (columnheader=='workshiftQuantity'||columnheader=='proWorkshift'
    					||columnheader=='rateWorkShiftSucc')
					{
	    				window.open('${pageContext.request.contextPath}/assess/detail.htm?function=day&dateF='+date+
	    							'&dateT='+date+
	    							'&catruc='+catruc+
	    							'&status='+status+
	    							'&network='+network+
									'&neType='+neType+
									'&severity='+severity+
									'&columnheader='+columnheader+
									'&type=workShift','_blank');
					}
					else if (columnheader=='rateWorkSucc') {
						window.open('${pageContext.request.contextPath}/assess/detail.htm?function=day'+
									'&dateF='+date+
		    						'&dateT='+date+
		    						'&users='+users+
		    						'&status='+status+
		    						'&network='+network+
									'&neType='+neType+
									'&severity='+severity+
									'&columnheader='+columnheader+
								'&type=work','_blank');
					}
	    		}
	  		    
	  		});
   
});
</script>