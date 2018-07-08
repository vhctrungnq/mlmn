<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<title>${title}</title>
<content tag="heading">${title}</content> 	
<form:form commandName="filter" method="post" action="${function}.htm?type=">
	<table >
			<tr> 
				<td style="width:70px"><fmt:message key="alarmLog.sdateF"/></td>
				<td style="width:90px">
						<input type ="text"  value="${sdate}" name="sdate" id="sdate" size="10" maxlength="10" style="width:70px">
			   			 <img alt="calendar" title="Click to choose the from date" id="chooseSDate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				
				<td style="padding-left: 5px;width:30px"><fmt:message key="alarmLog.sdateT"/></td>
				<td style="width:90px">
					<input type ="text"  value="${edate}" name="edate" id="edate" size="10" maxlength="10" style="width:70px">
			   		<img alt="calendar" title="Click to choose the to date" id="chooseEDate" style="cursor: pointer; position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				
				<td style="padding-left: 5px;width:70px"><fmt:message key="alarmLog.province"/></td>
				<td>
					<input type="text" id="province" name="province" style="width: 100px;"/>
				</td>
				<td style="padding-left: 5px;width:50px"><fmt:message key="alarmLog.bscid"/></td>
				<td>
					 <input type="text" id="bscid" name="bscid" style="width: 200px;"/>
				</td>
				<%-- <c:if test="${function!= 'board'}"> --%>
					<td style="padding-left: 5px;"><fmt:message key="alarmLog.site"/></td>
					<td >
						<input type="text" id="site"  name="site" style="width: 200px;" />
					</td>
				<%-- </c:if> --%>
			</tr>
			<tr>
			<td style="width:60px"><fmt:message key="alarmLog.vendor"/></td>
				<td>
					<div id='vendor'></div>
				</td>
			<%-- <c:if test="${function!= 'board' && function!= 'ater'&& function!= 'a'}">	 --%>
			
				<td style="padding-left: 5px;width:60px"><fmt:message key="alarmLog.network"/></td>
				<td>
					<div id='network'></div>
				</td>
				<td style="padding-left: 5px;width:70px"><fmt:message key="alarmLog.team"/></td>
				<td>
					 <input type="text" id="team" name="team" style="width: 100px;"/>
				</td >
				<td style="padding-left: 5px;width:70px"><fmt:message key="alarmLog.district"/></td>
				<td>
					<input type="text" id="district" name="district" style="width: 100px;"/>
				</td>	
		<%-- </c:if>	 --%>
				<td style="padding-left: 5px;width:50px;"><fmt:message key="alarmLog.qualityLimit"/></td>
				<td style="width:100px;">
					 >= <input type="text" id="qualityLimit" name="qualityLimit" style="width: 100px;"/>
				</td>
			<c:if test="${function=='list'}">
			</tr>
			<tr>
				<td style="width:90px"><fmt:message key="alarmLog.severity"/></td>
				<td>
					 <div id='severity'></div>
				</td >
				<td style="padding-left: 5px;width:90px"><fmt:message key="alarmLog.alarmType"/></td>
				<td>
					 <div id='alarmType'></div>
				</td >
				<td style="padding-left: 5px;width:130px"><fmt:message key="alarmLog.alarmId"/></td>
				<td colspan="3">
					<div id='alarmMappingName'></div>
				</td>	
			</c:if>	
				<td>
				<input class="button" type="submit" id="button" value="<fmt:message key="global.form.timkiem"/>" />
				</td>
			</tr>
			
		</table>
	</form:form>
<br/>
<%@ include file="/includeJQ/gridReport.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script type="text/javascript">
Calendar.setup({
    inputField		:	"sdate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseSDate",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 

Calendar.setup({
    inputField		:	"edate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseEDate",   	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 	
</script>
<script type="text/javascript">
$(document).ready(function(){

		// Khai bao sdate, edate
		var theme =  getTheme();
		$('#button').jqxButton({ theme: theme });
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
		$("#qualityLimit").jqxInput({  height: 20, width: 60, theme: theme});
	     var qualityLimit =  "<c:out value='${qualityLimit}'/>";
		 	//alert(cellid);
	     if(qualityLimit!="")
				$('#qualityLimit').val(qualityLimit);
	     
	     $("#site").jqxInput({  height: 20, width:200, theme: theme});
	     var site =  "<c:out value='${site}'/>";
		 	//alert(cellid);
	     if(site!="")
				$('#site').val(site);
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
      //Input team
	    ${teamList}
	    $("#team").jqxInput({ placeHolder: "Enter a team", height: 25, width: 200, theme: theme,
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
        $("#vendor").jqxComboBox({ source: dataAdapterVendor, displayMember: "name", valueMember: "name", width: 100,height: 20,itemHeight: 30,theme: theme,autoDropDownHeight: true,autoOpen: true });
        var vendor =  "<c:out value='${vendor}'/>";
        if(vendor=="")
			$('#vendor').val('ALL');
        	//$('#vendor').val('ALCATEL');
		else
			$('#vendor').val(vendor);
	    //network
       var urlnetwork = "${pageContext.request.contextPath}/ajax/getNetworkList.htm";
       // prepare the data
       var sourcenetwork =
       {
           datatype: "json",
           datafields: [
               { name: 'value' },
               { name: 'name' }
           ],
           url: urlnetwork,
           async: false
       };
       var dataAdapternetwork = new $.jqx.dataAdapter(sourcenetwork);
       $("#network").jqxComboBox({ source: dataAdapternetwork, displayMember: "value", valueMember: "name",width: 120,height: 20,itemHeight: 30,theme: theme,autoDropDownHeight: true,autoOpen: true  });
        var network =  "<c:out value='${network}'/>";
	       if(network!="") {
			$('#network').val(network);
	       }
	       else
    	   {
    	   	$('#network').val('ALL');
    	   }
       // Create a jqxComboBox AlarmType
		var urlseverity = "${pageContext.request.contextPath}/ajax/getSeverity.htm";
        // prepare the data
        var sourceseverity =
        {
            datatype: "json",
            datafields: [
                { name: 'name'},
                { name: 'value'}
            ],
            url: urlseverity,
            async: false
        };
        var dataAdapterseverity = new $.jqx.dataAdapter(sourceseverity);
        // Create a jqxComboBox
        $("#severity").jqxComboBox({ source: dataAdapterseverity, displayMember: "value", valueMember: "value", width: 100, height: 20, theme: theme,autoDropDownHeight: true,autoOpen: true  });
        var severity =  "<c:out value='${severity}'/>";
        if(severity=="")
			$('#severity').val('ALL');
		else
			$('#severity').val(severity);
        
       // var alarmType = 'TRANSMISSION';
        var networkT = $('#network').val();
		if (networkT=='ALL')
		{
			networkT='';
		}
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
        $("#alarmType").jqxComboBox({ source: dataAdapteralarmType, displayMember: "alarmType", valueMember: "alarmType", width: 120, height: 20, theme: theme,autoDropDownHeight: true,autoOpen: true  });
        var alarmType =  "<c:out value='${alarmType}'/>";
        if(alarmType=="")
			$('#alarmType').val('ALL');
		else
			$('#alarmType').val(alarmType);
        
		//Create a jqxComboBox alarmName	
		var urlalarmMappingName = "${pageContext.request.contextPath}/ajax/getAlarmMappingName.htm?vendor="+vendor+"&alarmType="+alarmType+"&network="+networkT;
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
		$("#alarmMappingName").jqxComboBox({ source: dataAdapteralarmMappingName, displayMember: "alarmMappingName", valueMember: "alarmMappingName", width: "100%", height: 20, theme: theme,autoOpen: true });
		var alarmMappingName =  "<c:out value='${alarmMappingName}'/>";
		if(alarmMappingName=="")
			$('#alarmMappingName').val('ALL');
		else
			$('#alarmMappingName').val(alarmMappingName);
		
		$("#alarmType").change(function(){
			if ($("#alarmType").val() != null && $("#alarmType").val() != 'ALL') {
				var vendorT = $('#vendor').val();
				if (vendorT=='ALL')
				{
					vendorT='';
				}
				var network = $('#network').val();
				if (network=='ALL')
				{
					network='';
				}
				var urlalarmMappingName = "${pageContext.request.contextPath}/ajax/getAlarmMappingName.htm?vendor="+vendorT+"&alarmType="+$("#alarmType").val()+"&network="+network;
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
				$("#alarmMappingName").jqxDropDownList({ source: dataAdapteralarmMappingName,checkboxes: true, displayMember: "alarmMappingName", valueMember: "alarmMappingName", width: "100%", height: 20, theme: theme,autoOpen: true });
				var alarmMappingName =  "<c:out value='${alarmMappingName}'/>";
		 	    if(alarmMappingName=="")
		 			$('#alarmMappingName').val('');
		 		else
		 		{
		 			var teamVar = alarmMappingName.split(",");
		 			if (teamVar != null) {
		 				for (var i=0; i<teamVar.length; i++) {
		 					$("#alarmMappingName").jqxDropDownList('checkItem', teamVar[i] ); 
		 				}
		 			}
		 		}

			}
			
		});	
		$("#vendor").change(function(){
			if ($("#vendor").val() != null && $("#vendor").val() != 'ALL') {
				
	 		   var username = "<c:out value='${username}'/>";
	 			var network = $('#network').val();
				if (network=='ALL')
				{
					network='';
				}
	     	   $.getJSON("${pageContext.request.contextPath}/ajax/getBSCByNetwork.htm",{network:network,username:username,vendor:$("#vendor").val()}, function(j){
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
			 	
				var network = $('#network').val();
				if (network=='ALL')
				{
					network='';
				}
				var urlalarmMappingName = "${pageContext.request.contextPath}/ajax/getAlarmMappingName.htm?vendor="+$("#vendor").val()+"&alarmType="+alarmType+"&network="+network;
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
				$("#alarmMappingName").jqxComboBox({ source: dataAdapteralarmMappingName, displayMember: "alarmMappingName", valueMember: "alarmMappingName", width: "100%", height: 20, theme: theme,autoOpen: true });
				var alarmMappingName =  "<c:out value='${alarmMappingName}'/>";
				if(alarmMappingName=="")
					$('#alarmMappingName').val('ALL');
				else
					$('#alarmMappingName').val(alarmMappingName);
				
			}
		});	
		
		$("#network").change(function(){
			if ($("#network").val() != null && $("#network").val() != 'ALL') {
				var vendorT = $('#vendor').val();
				if (vendorT=='ALL')
				{
					vendorT='';
				}
				var urlalarmMappingName = "${pageContext.request.contextPath}/ajax/getAlarmMappingName.htm?vendor="+vendorT+"&alarmType="+alarmType+"&network="+$("#network").val();
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
				$("#alarmMappingName").jqxComboBox({ source: dataAdapteralarmMappingName, displayMember: "alarmMappingName", valueMember: "alarmMappingName", width: "100%", height: 20, theme: theme,autoOpen: true });
				var alarmMappingName =  "<c:out value='${alarmMappingName}'/>";
				if(alarmMappingName=="")
					$('#alarmMappingName').val('ALL');
				else
					$('#alarmMappingName').val(alarmMappingName);
				
		 	   var username = "<c:out value='${username}'/>";
				var network = $('#network').val();
				if (network=='ALL')
				{
					network='';
				}
	    	   $.getJSON("${pageContext.request.contextPath}/ajax/getBSCByNetwork.htm",{network:network,username:username,vendor:vendorT}, function(j){
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
			}
		});	
		
		
	   //call view detail    
       $("#jqxgrid").on('cellselect', function (event) 
    	  		{
    	   			var type =  "<c:out value='${type}'/>";
    	   			var funtion = 'jitter';
    	   			var objType = "<c:out value='${function}'/>";
    	   			var quality = $('#qualityLimit').val();
		    		//alert(type+'--'+funtion);
    	  		    var columnheader = $("#jqxgrid").jqxGrid('getcolumn', event.args.datafield).text; 
    	  		  	if (columnheader!='Day' && 
    	  		  			columnheader!='Network' &&
    	  		  			columnheader!='Vendor' && 
    	  		  			columnheader!='Province' && 
    	  		  			columnheader!='NE' && 
    	  		  			columnheader!='Sub team'&& 
    	  		  			columnheader!='District'&& 
    	  		  			columnheader!='Site'&& 
    	  		  			columnheader!='Cell'&& 
  		  					columnheader!='Alarm mapping id'&& 
    	  		  			columnheader!='Severity'&& 
    	  		  			columnheader!='Alarm mapping name'&& 
    	  		  			columnheader!='NE Port'&& 
    	  		  			columnheader!='BTS Port')
    	  		    {
    	  		    	var rowindex = event.args.rowindex;
    	  		    	var row = $("#jqxgrid").jqxGrid('getrowdata', rowindex);
    	  		    	var day = new Date(row.day);
    	  		    	var network = '';
    	  		    	var vendor= row.vendor;
    	  		    	var ne = row.ne;
    	  		    	var province='';
    	  		    	if(row.province!=null)
   	  		    		{
   	  		    			province=row.province;
   	  		    		}
   	  		    		var alarmType ='';
	   	  		    	var team='';
	   	  		    	var district='';
	   	  		   		var alarmId='';
  	  		    		//var site = 'null';
  	  		    		var site = '';
  	  		    		var severity='';
  	  		    		var bscPort='';
  	  		    		var btsPort='';
   	  		    		if (objType!='list')
		    			{
	  		    			alarmType = 'TRANSMISSION';
  	    	  		    	
   	    	  		    	network = row.network;
   	    	  		    	if (objType=='abis' && network=='3G')
   	    	  		    		{
   	    	  		    			objType=='';
   	    	  		    		}
		    	  		    	if(row.bscport!=null)
		   	  		    		{
			  	  		    		bscPort=row.bscport;
		   	  		    		}
			   	  		    	if(row.btsport!=null)
		   	  		    		{
			   	  		    		btsPort=row.btsport;
		   	  		    		}
			   	  		    	if(row.groups!=null)
			  		    		{
			   	  		    		team=row.groups;
			  		    		}
			  		    		if(row.district!=null)
			  		    		{
			  		    			district=row.district;
			  		    		}
			  		    		if(row.site!=null)
			  		    		{
			  		    			site=row.site;
			  		    		}
   	   
	  		    		}
   	  		    		else
   	  		    		{
   	  		    			objType='';
	   	  		    		severity=  row.severity;
	    	  		    	network = row.network;
		   	  		    	if(row.groups!=null)
		  		    		{
		   	  		    		team=row.groups;
		  		    		}
		  		    		if(row.district!=null)
		  		    		{
		  		    			district=row.district;
		  		    		}
		  		    		if(row.site!=null)
		  		    		{
		  		    			site=row.site;
		  		    		}
		  		    		if(row.alarmMappingName!=null)
		  		    		{
		  		    			alarmId=row.alarmMappingName;
		  		    		}
   	  		    			
   	  		    		}
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
    	  		    	
    	  		    	 window.open('${pageContext.request.contextPath}/report/detail/'+funtion+'.htm?sdate='+date+' 00:00'+
										'&edate='+date+' 23:59'+
											'&bscid='+ne+
											'&cellid='+site+
											'&vendor='+vendor+
											'&district='+district+
		    	  		    			'&alarmName=&province='+province+
			    	  		    		'&team='+team+
			    	  		    		'&alarmType='+alarmType+'&alarmMappingName='+alarmId+'+&reload=&reloadStr=&statusFinish='+
			    	  		    		'&severity='+severity+
			    	  		    		'&netWork='+network+
			    	  		    		'&duarationTo=&bscPort='+bscPort+
			    	  		    		'&btsPort='+btsPort+
			    	  		    		'&objType='+objType+'&quality='+quality,'_blank');
    	  		    }
    	  		    
    	  		});
});
</script>
