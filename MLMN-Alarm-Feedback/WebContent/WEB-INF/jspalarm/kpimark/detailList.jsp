<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title><fmt:message key="kpismark.title"/></title>
<content tag="heading"><fmt:message key="kpismark.title"/></content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/alarm/kpi-mark-dept/list.htm"><span><fmt:message key="kpismark.title.dept"/></span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/alarm/kpi-mark-team/list.htm"><span><fmt:message key="kpismark.title.team"/></span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/alarm/kpi-mark-group/list.htm"><span><fmt:message key="kpismark.title.group"/></span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/alarm/kpi-mark-group-causeby/list.htm"><span><fmt:message key="kpismark.title.groupcauseby"/></span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/alarm/kpi-mark-detail/list.htm"><span><fmt:message key="kpismark.title.detail"/></span></a></li>
</ul>
<br>
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left" colspan="2"><form:form commandName="filter" method="post" action="${function}.htm">
					<table border="0" cellspacing="1" cellpadding="0" width="100%">
						<tr> 
							<td class="wid8 mwid90"><fmt:message key="kpismark.filter.startdate"/></td>
							<td class="wid15">	
							       	<input id="startDate" name="startDate" value="${startDate}" class="wid50" maxlength="20"/>
							 		<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
							</td>	
							<td class="wid7 mwid100"><fmt:message key="kpismark.filter.enddate"/></td>
							<td class="wid15">
							       	<input id="endDate" name="endDate" value="${endDate}" class="wid50" maxlength="20"/>
							 		<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
							</td> 
							<td>
								<fmt:message key="kpismark.title.dept"/>
							</td>
							<td><input type="text" id="dept" name="dept" style="width: 100px;"/></td>
							<td>
								<fmt:message key="kpismark.title.team"/>
							</td>
							<td><input type="text" id="team" name="team" style="width: 100px;"/></td>
							<td>
								<fmt:message key="kpismark.title.group"/>
							</td>
							<td><input type="text" id="group" name="group" style="width: 100px;"/></td>
							<td>
								<input type="submit" value="<fmt:message key="global.form.timkiem"/>" id='jqxSubmitButton' /></td>
						</tr>				
					</table>
				</form:form>
			</td>
		</tr>
		<tr>
			<td colspan="2"> 
				<div id='jqxWidget' style="margin-top:5px">
			    	<div id="jqxgrid"></div>
			        <div id='Menu'>
			            <ul>
					   		<li><fmt:message key="global.button.exportExcel"/></li>
				        </ul>
			       </div>
			    </div>
			</td>
		</tr>
</table>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<script type="text/javascript">
	var theme = getTheme();
	$("#jqxSubmitButton").jqxButton({theme: theme});
	${gridReport}
	
	$('#Menu').on('itemclick', function (event) {
		var args = event.args;
		// export data
		if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>') {
				window.open('${pageContext.request.contextPath}/alarm/kpi-mark-detail/exportData.htm?startDate='+"<c:out value='${startDate}'/>"+
			        	 '&endDate='+"<c:out value='${endDate}'/>" +
			        	 '&dept='+"<c:out value='${dept}'/>"+
			        	 '&team='+"<c:out value='${team}'/>"+
			        	 '&group='+"<c:out value='${group}'/>"+
			        	 '&shiftTime='+"<c:out value='${shiftTime}'/>"+
			        	 '&causeby='+"<c:out value='${causeby}'/>"+
			        	 '&siteLevel='+"<c:out value='${siteLevel}'/>"
			        	 ,'_blank');
				}
	});
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
	//Input dept
	$("#dept").jqxInput({ height: 20, width: '100%', theme: theme });
	//Input team
    ${teamList}
    $("#team").jqxInput({ height: 20, width: '100%', theme: theme,
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
    var groupList=[];
	$.getJSON("${pageContext.request.contextPath}/ajax/getGroupListAlarm.htm",{dept:"",team:team}, function(j){
		groupList =j;
	   });
	var group =  "<c:out value='${group}'/>";
	   // alert(bscid);
	    if(group!="")
			$('#group').val(group);
   $("#group").jqxInput({ height: 20, width: '100%', theme: theme,
       source: function (query, response) {
           var item = query.split(/,\s*/).pop();
           // update the search query.
           $("#group").jqxInput({ query: item });
           response(groupList);
       },
       renderer: renderer
   });
   
	   $("#group").change(function(){
		   var teamProcess = $("#team").val();
	  	  if (teamProcess==null||teamProcess=='null')
	  		  {
	  			teamProcess='';
	  		  }
	  	 $.getJSON("${pageContext.request.contextPath}/ajax/getGroupListAlarm.htm",{dept:'',team:teamProcess}, function(j){
		   		groupList =j;
			   $("#group").jqxInput({source: function (query, response) {
		           var item = query.split(/,\s*/).pop();
		           // update the search query.
		           $("#group").jqxInput({ query: item });
		           response(groupList);
		       },
		       renderer: renderer
			    });
		   });	
	});	
	Calendar.setup({
	    inputField		:	"startDate",	// id of the input field
	    ifFormat		:	"%d/%m/%Y",   	// format of the input field
	    button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
	    singleClick		:   false					// double-click mode
	});

	Calendar.setup({
	    inputField		:	"endDate",	// id of the input field
	    ifFormat		:	"%d/%m/%Y",   	// format of the input field
	    button			:   "chooseEndDate",  	// trigger for the calendar (button ID)
	    singleClick		:   false					// double-click mode
	});
</script>
 