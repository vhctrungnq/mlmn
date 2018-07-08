<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>${title}</title>
<content tag="heading">${title}</content> 	
<style>
.ui-tabs-nav .ui_link a:hover{
color: black;
}
</style>
<div>
	<div align="left">
		<ul class="ui-tabs-nav" style="padding-bottom:0px;">
		<div align="left" >
		<c:choose>
			<c:when test="${function == 'VENDOR'}">
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/mainPage/${function}.htm"><span><fmt:message key="title.tabControls.vendor"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/mainPage/${function}.htm"><span><fmt:message key="title.tabControls.neType"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/mainPage/${function}.htm"><span><fmt:message key="title.tabControls.lossConfig"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/mainPage/${function}.htm"><span><fmt:message key="title.tabControls.lossPower"/></span></a></li>
			</c:when>
		 	<c:when test="${function == 'NE_TYPE'}">
				<li class=""><a href="${pageContext.request.contextPath}/mainPage/${function}.htm"><span><fmt:message key="title.tabControls.vendor"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/mainPage/${function}.htm"><span><fmt:message key="title.tabControls.neType"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/mainPage/${function}.htm"><span><fmt:message key="title.tabControls.lossConfig"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/mainPage/${function}.htm"><span><fmt:message key="title.tabControls.lossPower"/></span></a></li>
			</c:when>
			<c:when test="${function == 'LOSS_CONFIG'}">
				<li class=""><a href="${pageContext.request.contextPath}/mainPage/${function}.htm"><span><fmt:message key="title.tabControls.vendor"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/mainPage/${function}.htm"><span><fmt:message key="title.tabControls.neType"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/mainPage/${function}.htm"><span><fmt:message key="title.tabControls.lossConfig"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/mainPage/${function}.htm"><span><fmt:message key="title.tabControls.lossPower"/></span></a></li>
			</c:when>
			<c:when test="${function == 'LOSS_POWER'}">
				<li class=""><a href="${pageContext.request.contextPath}/mainPage/${function}.htm"><span><fmt:message key="title.tabControls.vendor"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/mainPage/${function}.htm"><span><fmt:message key="title.tabControls.neType"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/mainPage/${function}.htm"><span><fmt:message key="title.tabControls.lossConfig"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/mainPage/${function}.htm"><span><fmt:message key="title.tabControls.lossPower"/></span></a></li>
			</c:when>
		
		 	<c:otherwise>
		 		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/mainPage/${function}.htm"><span><fmt:message key="title.tabControls.vendor"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/mainPage/${function}.htm"><span><fmt:message key="title.tabControls.neType"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/mainPage/${function}.htm"><span><fmt:message key="title.tabControls.lossConfig"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/mainPage/${function}.htm"><span><fmt:message key="title.tabControls.lossPower"/></span></a></li>
			</c:otherwise>
		</c:choose>
		</div>
		</ul>
		
	</div>
</div>
<div class="clear"></div>
<div class="ui-tabs-panel">
<form:form commandName="filter" method="post" action="${function}.htm">
<input type="hidden" name="reloadStr" id="reloadStr" value="${reloadStr}">
	<table >
		
			<tr> 
				<td style="width:90px"><fmt:message key="alarmLog.sdateFS"/></td>
				<td style="width:150px">
						<input type ="text"  value="${sdate}" name="sdate" id="sdate" size="17" maxlength="16" style="width:100px">
			   			 <img alt="calendar" title="Click to choose the from date" id="chooseSDate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				
				<td style="padding-left: 5px;width:100px"><fmt:message key="alarmLog.sdateT"/></td>
				<td style="width:150px">
					<input type ="text"  value="${edate}" name="edate" id="edate" size="17" maxlength="16" style="width:100px">
			   		<img alt="calendar" title="Click to choose the to date" id="chooseEDate" style="cursor: pointer; position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
					<!-- <div id='edate'></div> -->
				</td>
				<td style="padding-left: 5px;width: 170px;"><fmt:message key="alarmLog.province"/></td>
				<td>
					 <input type="text" id="province" name="province" style="width: 100px;"/>
				</td>
				<td><fmt:message key="alarmLog.team"/></td>
				<td>
					 <input type="text" id="team" name="team" style="width: 100px;"/>
				</td>
				<td style="padding-left: 5px;width: 90px;"><fmt:message key="alarmLog.district"/></td>
				<td>
					 <input type="text" id="district" name="district" style="width: 100px;"/>
				</td>
						
			</tr>
			<tr>
				
				<td style="padding-left: 5px;"><fmt:message key="alarmLog.vendor"/></td>
				<td>
					<div id='vendor'></div>
				</td>
				<td style="padding-left: 5px;"><fmt:message key="alarmLog.bscid"/></td>
				<td>
					 <input type="text" id="bscid" name="bscid" style="width: 200px;"/>
				</td>
				<td style="padding-left: 5px;"><fmt:message key="alarmLog.cellTK"/></td>
				<td colspan="3">
					<input type="text" id="cellid"  name="cellid" style="width: 200px;" />
				</td>
					
				
			</tr>
			<tr>
				<td><fmt:message key="alarmLog.alarmType"/></td>
				<td >
					<div id='alarmType' style = "width: 100%"></div>
				</td>
				<td style="padding-left: 5px;"><fmt:message key="alarmLog.alarmName"/></td>
				<td >
					<div id='alarmName' style = "width: 100%"></div>
				</td>
				<td style="padding-left: 5px;"><fmt:message key="alarmLog.alarmMappingName"/></td>
				<td >
					<div id='alarmMappingName' style = "width: 100%"></div>
				</td>
				<td style="padding-left: 5px;"><fmt:message key="alarmLog.statusView"/></td>
				<td >
					<div id='statusViewC' style = "width: 100%"></div>
				</td>
				<td style="padding-left: 5px;" >
					<div style="width:90px;float:left;">
						Refresh <c:choose>
								<c:when test="${reload != null}">
									<input id="reload" name="reload" type="checkbox" checked="checked">
								</c:when>
								<c:otherwise>
									<input id="reload" name="reload" type="checkbox" >
								</c:otherwise>
							</c:choose>
					</div>
					<%-- <div  id="time" style="padding-left: 8px">
					Sau <input id="timeLoad" name="timeLoad" value="${timeLoad}"  maxlength="6"  type="text" style="width:35px;text-align:center"> giây
					</div>	 --%>
				</td>
				<td>
					<input class="button" type="submit" id="btFilter" name="btFilter" value="<fmt:message key="global.form.timkiem"/>" />
				</td>
			</tr>
		</table>
	</form:form>
</div>
<div class="clear"></div>

<%-- <div style="float: right;">
	<input class="button"  type="button" value="<fmt:message key="global.button.import"/>" id='import' onclick='window.location="${pageContext.request.contextPath}/importAlarm.htm?function=${function}&netWork=${netWork}"' />
</div>		 --%>		
<%@ include file="/includeJQ/gridAlarmLog.jsp" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script type="text/javascript">
Calendar.setup({
    inputField		:	"sdate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
    button			:   "chooseSDate",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 

Calendar.setup({
    inputField		:	"edate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
    button			:   "chooseEDate",   	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 	
</script>
<script type="text/javascript">
$("#reload").change( function() {
	if ($(this).is(':checked')) {
		$('#time').css("display","block");
		$('#reloadStr').val('Y');
	} else {
		$('#time').css("display","none");
		$('#reloadStr').val('N');
	}
});
//reload
	if ($('#reload').is(':checked')) {
		//$('#time').css("display","block");
		$('#reloadStr').val('Y');
		var timeLoad = 180;
		
		setTimeout(function(){
			$('#filter').submit();
		}, 
		timeLoad * 1000);
	} else {
		//$('#time').css("display","none");
		$('#reloadStr').val('N');
	}
	//refresh 
	$(document).ready(function(){
		var reload = $("#reload").val();
		if (reload=='Y') {
			setTimeout(function(){
				$('#filter').submit();
			}, 
			10000);
			$('#reloadStr').val('Y');
		}
		
		// Khai bao sdate, edate
		var theme =  getTheme();
		// Create a jqxMultile Input
		var renderer = function (itemValue, inputValue) {
	        var terms = inputValue.split(/,\s*/);
	        // remove the current input
	        terms.pop();
	        // add the selected item
	        terms.push(itemValue);
	        // add placeholder to get the comma-and-space at the end
	        terms.push("");
	        var value = terms.join(",");
	        return value;
	    };
	    //Input BSC
	    ${bscList}
	    $("#bscid").jqxInput({ placeHolder: "Enter a BSC", height: 25, width: 200, theme: theme,
	        source: function (query, response) {
	            var item = query.split(/,\s*/).pop();
	            // update the search query.
	            $("#bscid").jqxInput({ query: item });
	            response(bscList);
	        },
	        renderer: renderer
	    });
	    var bscid =  "<c:out value='${bscid}'/>";
	   // alert(bscid);
        if(bscid!="")
			$('#bscid').val(bscid);
	 	// Input CELL
	 	${cellList}
	 	$("#cellid").jqxInput({ placeHolder: "Enter a Cell", height: 25, width: 200, theme: theme,
	        source: function (query, response) {
	            var item = query.split(/,\s*/).pop();
	            // update the search query.
	            $("#cellid").jqxInput({ query: item });
	            response(cellList);
	        },
	        renderer: renderer
	    });
	 	var cellid =  "<c:out value='${cellid}'/>";
	 	//alert(cellid);
        if(cellid!="")
			$('#cellid').val(cellid);
	 // Input district
	 	${districtList}
		$("#district").jqxInput({ placeHolder: "Enter a district", height: 25, width: 200, theme: theme,
	        source: function (query, response) {
	            var item = query.split(/,\s*/).pop();
	            // update the search query.
	            $("#district").jqxInput({ query: item });
	            response(districtList);
	        },
	        renderer: renderer
	    });
	 	var district =  "<c:out value='${district}'/>";
	 
        if(district!="") {
			$('#district').val(district);
			//alert(district);
        }
     //create status view
     
  	var urlStatusView = "${pageContext.request.contextPath}/ajax/getStatusViewList.htm";
     // prepare the data
     var sourceStatusView =
     {
         datatype: "json",
         datafields: [
             { name: 'value' },
             { name: 'name' }
         ],
         url: urlStatusView,
         async: false
     };
     var dataAdapterStatusView = new $.jqx.dataAdapter(sourceStatusView);
     // Create a jqxComboBox
     $("#statusViewC").jqxComboBox({ source: dataAdapterStatusView, displayMember: "value", valueMember: "name", width: 130,height: '20px',itemHeight: 30,theme: theme,autoDropDownHeight: true,autoOpen: true });
     var statusView =  "<c:out value='${statusView}'/>";
     if(statusView=="")
			$('#statusViewC').val('ALL');
		else
			$('#statusViewC').val(statusView);
     
	// Create a jqxComboBox vendor
       	var urlVendor = "${pageContext.request.contextPath}/ajax/getVendor.htm";
        // prepare the data
        var sourceVendor =
        {
            datatype: "json",
            datafields: [
                { name: 'value' },
                { name: 'name' }
            ],
            url: urlVendor,
            async: false
        };
        var dataAdapterVendor = new $.jqx.dataAdapter(sourceVendor);
        // Create a jqxComboBox
        $("#vendor").jqxComboBox({ source: dataAdapterVendor, displayMember: "value", valueMember: "name", width: 150,height: '20px',itemHeight: 30,theme: theme,autoDropDownHeight: true,autoOpen: true });
        var vendor =  "<c:out value='${vendor}'/>";
        if(vendor=="")
			$('#vendor').val('ALL');
		else
			$('#vendor').val(vendor);
      
	 // Create a jqxComboBox AlarmType
		var urlalarmType = "${pageContext.request.contextPath}/ajax/getAlarmType.htm";
        // prepare the data
        var sourcealarmType =
        {
            datatype: "json",
            datafields: [
                { name: 'alarmType'}
            ],
            url: urlalarmType,
            async: false
        };
        var dataAdapteralarmType = new $.jqx.dataAdapter(sourcealarmType);
        // Create a jqxComboBox
        $("#alarmType").jqxComboBox({ source: dataAdapteralarmType, displayMember: "alarmType", valueMember: "alarmType", width: 150, height: 20, theme: theme,autoDropDownHeight: true,autoOpen: true  });
        var alarmType =  "<c:out value='${alarmType}'/>";
        if(alarmType=="")
			$('#alarmType').val('ALL');
		else
			$('#alarmType').val(alarmType);
	
		// Create a jqxComboBox alarmName
		var vendorT = $('#vendor').val();
		if (vendorT=='ALL')
		{
			vendorT='';
		}
		var alarmTypeT = $('#alarmType').val();
		if (alarmTypeT=='ALL')
		{
			alarmTypeT='';
		}
		var networkT = "<c:out value='${netWork}'/>";
		var urlAlarmName = "${pageContext.request.contextPath}/ajax/getAlarmName.htm?vendor="+vendorT+"&alarmType="+alarmTypeT+"&network="+networkT;
	    // prepare the data
	    var sourceAlarmName =
	    {
	        datatype: "json",
	        datafields: [
	            { name: 'blockValue'}
	        ],
	        url: urlAlarmName,
	        async: false
	    };
	    var dataAdapterAlarmName = new $.jqx.dataAdapter(sourceAlarmName);
	    // Create a jqxComboBox
	    $("#alarmName").jqxComboBox({ source: dataAdapterAlarmName, displayMember: "blockValue", valueMember: "blockValue", width: 150, height: 20, theme: theme,autoOpen: true  });
	    var alarmName =  "<c:out value='${alarmName}'/>";
	    if(alarmName=="")
			$('#alarmName').val('ALL');
		else
			$('#alarmName').val(alarmName);
		//Create a jqxComboBox alarmName
		
		var urlalarmMappingName = "${pageContext.request.contextPath}/ajax/getAlarmMappingName.htm?vendor="+vendorT+"&alarmType="+alarmTypeT+"&network="+networkT;
		// prepare the data
		var sourcealarmMappingName =
		{
		    datatype: "json",
		    datafields: [
		        { name: 'alarmMappingId'},
		        { name: 'alarmMappingName'}
		    ],
		    url: urlalarmMappingName,
		    async: false
		};
		var dataAdapteralarmMappingName = new $.jqx.dataAdapter(sourcealarmMappingName);
		// Create a jqxComboBox
		$("#alarmMappingName").jqxComboBox({ source: dataAdapteralarmMappingName, displayMember: "alarmMappingName", valueMember: "alarmMappingName", width: 200, height: 20, theme: theme,autoOpen: true });
		var alarmMappingName =  "<c:out value='${alarmMappingName}'/>";
		if(alarmMappingName=="")
			$('#alarmMappingName').val('ALL');
		else
			$('#alarmMappingName').val(alarmMappingName);
		//Input team
	    ${teamList}
	    $("#team").jqxInput({ placeHolder: "Enter a team", height: 25, width: 150, theme: theme,
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
        
      //Input province
	    ${provinceList}
	    $("#province").jqxInput({ placeHolder: "Enter a province", height: 25, width: 200, theme: theme,
	        source: function (query, response) {
	            var item = query.split(/,\s*/).pop();
	            // update the search query.
	            $("#province").jqxInput({ query: item });
	            response(provinceList);
	        },
	        renderer: renderer
	    });
	    var province =  "<c:out value='${province}'/>";
	   // alert(bscid);
        if(province!="")
			$('#province').val(province);
	});
	var theme =  getTheme();
	$("#vendor").change(function(){
		if ($("#vendor").val() != null && $("#vendor").val() != 'ALL') {
			var alarmTypeT = $('#alarmType').val();
			if (alarmTypeT=='ALL')
			{
				alarmTypeT='';
			}
			var networkT = "<c:out value='${netWork}'/>";
			var username = "<c:out value='${username}'/>";
			//bsc change
     	   $.getJSON("${pageContext.request.contextPath}/ajax/getBSCByNetwork.htm",{network:networkT,username:username,vendor:$("#vendor").val()}, function(j){
     		   var BSCList=[];
     		   BSCList =j;
     		  $("#bscid").jqxInput({ placeHolder: "Enter a BSC", height: 25, width: 200, theme: theme,
     		        source: function (query, response) {
     		            var item = query.split(/,\s*/).pop();
     		            // update the search query.
     		            $("#bscid").jqxInput({ query: item });
     		            response(BSCList);
     		        },
     		        renderer: renderer
     		    });
     	   });
			//alarmname change
			var urlAlarmName = "${pageContext.request.contextPath}/ajax/getAlarmName.htm?vendor="+$("#vendor").val()+"&alarmType="+alarmTypeT+"&network="+networkT;
		    // prepare the data
		    var sourceAlarmName =
		    {
		        datatype: "json",
		        datafields: [
		            { name: 'blockValue'}
		        ],
		        url: urlAlarmName,
		        async: false
		    };
		    var dataAdapterAlarmName = new $.jqx.dataAdapter(sourceAlarmName);
		    // Create a jqxComboBox
		    $("#alarmName").jqxComboBox({ source: dataAdapterAlarmName, displayMember: "blockValue", valueMember: "blockValue", width: 150, height: 20, theme: theme,autoOpen: true });
		    var alarmName =  "<c:out value='${alarmName}'/>";
		    if(alarmName=="")
				$('#alarmName').val('ALL');
			else
				$('#alarmName').val(alarmName);
		  //alarmname change
			var urlalarmMappingName = "${pageContext.request.contextPath}/ajax/getAlarmMappingName.htm?vendor="+$("#vendor").val()+"&alarmType="+alarmTypeT+"&network="+networkT;
			// prepare the data
			var sourcealarmMappingName =
			{
			    datatype: "json",
			    datafields: [
			        { name: 'alarmMappingId'},
			        { name: 'alarmMappingName'}
			    ],
			    url: urlalarmMappingName,
			    async: false
			};
			var dataAdapteralarmMappingName = new $.jqx.dataAdapter(sourcealarmMappingName);
			// Create a jqxComboBox
			$("#alarmMappingName").jqxComboBox({ source: dataAdapteralarmMappingName, displayMember: "alarmMappingName", valueMember: "alarmMappingName", width: 200, height: 20, theme: theme,autoOpen: true });
			var alarmMappingName =  "<c:out value='${alarmMappingName}'/>";
			if(alarmMappingName=="")
				$('#alarmMappingName').val('ALL');
			else
				$('#alarmMappingName').val(alarmMappingName);
			
		}
	});	
	
	$("#alarmType").change(function(){
		if ($("#alarmType").val() != null && $("#alarmType").val() != 'ALL') {
			var vendorT = $('#vendor').val();
			if (vendorT=='ALL')
			{
				vendorT='';
			}
			var networkT = "<c:out value='${netWork}'/>";
			var urlAlarmName = "${pageContext.request.contextPath}/ajax/getAlarmName.htm?vendor="+vendorT+"&alarmType="+$("#alarmType").val()+"&network="+networkT;
		    // prepare the data
		    var sourceAlarmName =
		    {
		        datatype: "json",
		        datafields: [
		            { name: 'blockValue'}
		        ],
		        url: urlAlarmName,
		        async: false
		    };
		    var dataAdapterAlarmName = new $.jqx.dataAdapter(sourceAlarmName);
		    // Create a jqxComboBox
		    $("#alarmName").jqxComboBox({ source: dataAdapterAlarmName, displayMember: "blockValue", valueMember: "blockValue", width: 150, height: 20, theme: theme,autoOpen: true });
		    var alarmName =  "<c:out value='${alarmName}'/>";
		    if(alarmName=="")
				$('#alarmName').val('ALL');
			else
				$('#alarmName').val(alarmName);
			
			var urlalarmMappingName = "${pageContext.request.contextPath}/ajax/getAlarmMappingName.htm?vendor="+vendorT+"&alarmType="+$("#alarmType").val()+"&network="+networkT;
			// prepare the data
			var sourcealarmMappingName =
			{
			    datatype: "json",
			    datafields: [
			        { name: 'alarmMappingId'},
			        { name: 'alarmMappingName'}
			    ],
			    url: urlalarmMappingName,
			    async: false
			};
			var dataAdapteralarmMappingName = new $.jqx.dataAdapter(sourcealarmMappingName);
			// Create a jqxComboBox
			$("#alarmMappingName").jqxComboBox({ source: dataAdapteralarmMappingName, displayMember: "alarmMappingName", valueMember: "alarmMappingName", width: 200, height: 20, theme: theme,autoOpen: true });
			var alarmMappingName =  "<c:out value='${alarmMappingName}'/>";
			if(alarmMappingName=="")
				$('#alarmMappingName').val('ALL');
			else
				$('#alarmMappingName').val(alarmMappingName);
			
		}
		
	   
	});	
	  // handle context menu clicks.
    $("#Menu").on('itemclick', function (event) {
        var args = event.args;
        
        if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
       	  	var rowindex = $("#jqxgrid").jqxGrid('getselectedrowindex');
            var row = $("#jqxgrid").jqxGrid('getrowdata', rowindex);
        	window.location = 'form.htm?id='+row.id+'&netWork='+"<c:out value='${netWork}'/>"+'&function='+"<c:out value='${function}'/>";   
        }
        if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteMultiSelected"/>')  {
        	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
        	if (answer)
        	{
				var selectedrowindexes = $('#jqxgrid').jqxGrid('getselectedrowindexes'); 
        		var idList="";
        		var cond="";
        		//alert(selectedrowindexes);
        		//var rowIndexList = selectedrowindexes.split(",");
        		if (selectedrowindexes != null) {
        			for (var i=0; i<selectedrowindexes.length; i++) {
        				var row = $("#jqxgrid").jqxGrid('getrowdata', selectedrowindexes[i]);
        				idList+=cond+row.id;
        				cond=",";
        			}
        			// alert(idList);
        			window.location = 'delete.htm?idList='+idList+'&netWork='+"<c:out value='${netWork}'/>"+'&function='+"<c:out value='${function}'/>";
        		}
        		//alert(idList);
        		/* var rowid = $("#jqxgrid").jqxGrid('getrowid', rowindex);
                 $("#jqxgrid").jqxGrid('deleterow', rowid); */
              
        	}
          
        }
        if ($.trim($(args).text()) == '<fmt:message key="global.button.ackRow"/>')  {
        	var answer = confirm ('<fmt:message key="messsage.confirm.ackRow"/>');
        	if (answer)
        	{
				var selectedrowindexes = $('#jqxgrid').jqxGrid('getselectedrowindexes'); 
        		var idList="";
        		var cond="";
        		var idEndList="";
        		var condE="";
        		if (selectedrowindexes != null) {
        			for (var i=0; i<selectedrowindexes.length; i++) {
        				var row = $("#jqxgrid").jqxGrid('getrowdata', selectedrowindexes[i]);
        				//alert(row.edate);
        				if (row.edate==null||row.edate=='')
        					{
        						//alert(row.sdate);
		        				idList+=cond+row.id;
		        				cond=",";
        					}
        				else
        					{
        						idEndList+=condE+row.id;
	        					condE=",";
        					}
        			}
        			
        				window.location = 'ack.htm?idList='+idList+'&idEndList='+idEndList+'&netWork='+"<c:out value='${netWork}'/>"+'&function='+"<c:out value='${function}'/>"+'&inshift=N';
	
				}
    		} 
        }
        if ($.trim($(args).text()) == '<fmt:message key="global.button.unackRow"/>')  {
        	var answer = confirm ('<fmt:message key="messsage.confirm.unackRow"/>');
        	if (answer)
        	{
				var selectedrowindexes = $('#jqxgrid').jqxGrid('getselectedrowindexes'); 
        		var idList="";
        		var cond="";
        		var idEndList="";
        		var condE="";
        		if (selectedrowindexes != null) {
        			for (var i=0; i<selectedrowindexes.length; i++) {
        				var row = $("#jqxgrid").jqxGrid('getrowdata', selectedrowindexes[i]);
        				
       					//alert(row.edate);
       					if (row.ackTime!=null&&row.ackTime!='')
       					{
       						
       						if (row.ackTime.getTime()== row.edate.getTime())
       							{
    		        				idList+=cond+row.id;
    		        				cond=",";
       							}
       						else
       							{
        							idEndList+=condE+row.id;
    		       					condE=",";
       							}
       						
       					}
        			}
        			window.location = 'unack.htm?idList='+idList+'&idEndList='+idEndList+'&netWork='+"<c:out value='${netWork}'/>"+'&function='+"<c:out value='${function}'/>";
	
				}
    		} 
        }
        if ($.trim($(args).text()) == '<fmt:message key="global.button.ackRowShift"/>')  {
        	var answer = confirm ('<fmt:message key="messsage.confirm.ackRowShift"/>');
        	if (answer)
        	{
				var selectedrowindexes = $('#jqxgrid').jqxGrid('getselectedrowindexes'); 
        		var idList="";
        		var cond="";
        		var idEndList="";
        		var condE="";
        		
        		if (selectedrowindexes != null) {
        			for (var i=0; i<selectedrowindexes.length; i++) {
        				var row = $("#jqxgrid").jqxGrid('getrowdata', selectedrowindexes[i]);
        				if (row.edate==null||row.edate=='')
        					{
        						//alert(row.edate);
		        				idList+=cond+row.id;
		        				cond=",";
        					}
        				else
    					{
    						idEndList+=condE+row.id;
        					condE=",";
    					}
        			}
        			window.location = 'ack.htm?idList='+idList+'&idEndList='+idEndList+'&netWork='+"<c:out value='${netWork}'/>"+'&function='+"<c:out value='${function}'/>"+'&inshift=Y';
        		}
    		} 
        }
      /*   if ($.trim($(args).text()) == "Remove filter")  {
        	$("#jqxgrid").jqxGrid('clearfilters');
        } */
        if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
        	
        	var funtion = "<c:out value='${function}'/>";
        	 window.open('${pageContext.request.contextPath}/exportExcel/alarmLog.htm?sdate='+"<c:out value='${sdate}'/>"+
	        	'&edate='+"<c:out value='${edate}'/>"+
	        	 '&bscid='+"<c:out value='${bscid}'/>"+
	        	 '&cellid='+"<c:out value='${cellid}'/>"+
	        	 '&vendor='+"<c:out value='${vendor}'/>"+
	        	 '&district='+"<c:out value='${district}'/>"+
	        	 '&alarmName='+"<c:out value='${alarmName}'/>"+
	        	 '&province='+"<c:out value='${province}'/>"+
	        	 '&team='+"<c:out value='${team}'/>"+
	        	 '&alarmType='+"<c:out value='${alarmType}'/>"+
	        	 '&alarmMappingName='+"<c:out value='${alarmMappingName}'/>"+
	        	 '&statusFinish='+"<c:out value='${statusFinish}'/>"+
	        	 '&severity='+"<c:out value='${severity}'/>"+
	        	 '&netWork='+"<c:out value='${netWork}'/>"+
	        	 '&function='+funtion+
	        	 '&statusView='+"<c:out value='${statusView}'/>"+
	        	 '&typeExport=xls','_blank');
        	
        }
      
    });
    $('#statusViewC').on('change', function (event)
	{
	    var args = event.args;
	    if (args) {
			    var item = args.item;
			    // get item's label and value.
			    var label = item.label;
			    var value = item.value;
			    //alert(value);
			   	$("#statusView").val(value);
		    	
			 }
	    else
	    	{
	    		$("#statusView").val('');
	    	}
	}); 
  
    //call view detail 
    $("#jqxgrid").on("cellclick", function (event) 
	{
	    var column = event.args.column.text;
	    //alert(column);
	    if (column=='Site' )
		    {
			    var rowindex = event.args.rowindex;
			    var row = $("#jqxgrid").jqxGrid('getrowdata', rowindex);
		    	var site = row.site;
		    	 window.open('/mlmn_sts/map/list.htm?siteid='+site,'_blank');
		    }
	});
</script>