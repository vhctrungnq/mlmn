<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>${title}</title>
<content tag="heading">${title}</content> 	

<div>
<form:form commandName="filter" method="post" action="list.htm">
<input type="hidden" name="reloadStr" id="reloadStr" value="${reloadStr}">
	<table >
			<tr>
				
				<td ><fmt:message key="alarmLog.vendor"/></td>
				<td>
					<div id='vendor'></div>
				</td>
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
				<td>
					<input class="button" type="submit" id="btFilter" name="btFilter" value="<fmt:message key="global.form.timkiem"/>" />
				</td>
			</tr>
		</table>
	</form:form>
</div>
<div class="clear"></div>	
<table style="width:100%">
	<tr>
		<td align="right" class="ftsize12">
		<input class="button"  type="button" value="<fmt:message key="global.button.addNew"/>" id='addNew' onclick='window.location="form.htm?id="' />
		</td>
		<td style="width: 20px"><div style="float: right;" id="listHading"></div></td>
	</tr>
</table>
<div id="gridHading"></div>
<div id='Menu'>
            <ul>
            	<li><fmt:message key="global.button.editSelected"/></li>
	            <li><fmt:message key="global.button.deleteMultiSelected"/></li>
		   		<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
</div>
<div class="clear"></div>
<script type="text/javascript">

${gridHading}
$(document).ready(function(){
		// Khai bao sdate, edate
		var theme =  getTheme();
		$('#btFilter').jqxButton({ theme: theme });
		$('#addNew').jqxButton({ theme: theme });
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
        $("#vendor").jqxComboBox({ source: dataAdapterVendor, displayMember: "value", valueMember: "name", width: 120,height: '20px',itemHeight: 30,theme: theme,autoDropDownHeight: true,autoOpen: true });
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
        $("#alarmType").jqxComboBox({ source: dataAdapteralarmType, displayMember: "alarmType", valueMember: "alarmType", width: 120, height: 20, theme: theme,autoDropDownHeight: true,autoOpen: true  });
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
		var urlAlarmName = "${pageContext.request.contextPath}/ajax/getAlarmName.htm?vendor="+vendorT+"&alarmType="+alarmTypeT+"&network=";
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
	    $("#alarmName").jqxComboBox({ source: dataAdapterAlarmName, displayMember: "blockValue", valueMember: "blockValue", width: 200, height: 20, theme: theme,autoOpen: true  });
	    var alarmName =  "<c:out value='${alarmName}'/>";
	    if(alarmName=="")
			$('#alarmName').val('ALL');
		else
			$('#alarmName').val(alarmName);
		//Create a jqxComboBox alarmName
		
		var urlalarmMappingName = "${pageContext.request.contextPath}/ajax/getAlarmMappingName.htm?vendor="+vendorT+"&alarmType="+alarmTypeT+"&network=";
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
		$("#alarmMappingName").jqxComboBox({ source: dataAdapteralarmMappingName, displayMember: "alarmMappingName", valueMember: "alarmMappingName", width: 400, height: 20, theme: theme,autoOpen: true });
		var alarmMappingName =  "<c:out value='${alarmMappingName}'/>";
		if(alarmMappingName=="")
			$('#alarmMappingName').val('ALL');
		else
			$('#alarmMappingName').val(alarmMappingName);
		$("#vendor").change(function(){
		if ($("#vendor").val() != null && $("#vendor").val() != 'ALL') {
			var alarmTypeT = $('#alarmType').val();
			if (alarmTypeT=='ALL')
			{
				alarmTypeT='';
			}
			
			//alarmname change
			var urlAlarmName = "${pageContext.request.contextPath}/ajax/getAlarmName.htm?vendor="+$("#vendor").val()+"&alarmType="+alarmTypeT+"&network=";
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
		    $("#alarmName").jqxComboBox({ source: dataAdapterAlarmName, displayMember: "blockValue", valueMember: "blockValue", width: 200, height: 20, theme: theme,autoOpen: true });
		    var alarmName =  "<c:out value='${alarmName}'/>";
		    if(alarmName=="")
				$('#alarmName').val('ALL');
			else
				$('#alarmName').val(alarmName);
		  //alarmname change
			var urlalarmMappingName = "${pageContext.request.contextPath}/ajax/getAlarmMappingName.htm?vendor="+$("#vendor").val()+"&alarmType="+alarmTypeT+"&network=";
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
			$("#alarmMappingName").jqxComboBox({ source: dataAdapteralarmMappingName, displayMember: "alarmMappingName", valueMember: "alarmMappingName", width: 400, height: 20, theme: theme,autoOpen: true });
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
			var urlAlarmName = "${pageContext.request.contextPath}/ajax/getAlarmName.htm?vendor="+vendorT+"&alarmType="+$("#alarmType").val()+"&network=";
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
		    $("#alarmName").jqxComboBox({ source: dataAdapterAlarmName, displayMember: "blockValue", valueMember: "blockValue", width: 200, height: 20, theme: theme,autoOpen: true });
		    var alarmName =  "<c:out value='${alarmName}'/>";
		    if(alarmName=="")
				$('#alarmName').val('ALL');
			else
				$('#alarmName').val(alarmName);
			
			var urlalarmMappingName = "${pageContext.request.contextPath}/ajax/getAlarmMappingName.htm?vendor="+vendorT+"&alarmType="+$("#alarmType").val()+"&network=";
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
			$("#alarmMappingName").jqxComboBox({ source: dataAdapteralarmMappingName, displayMember: "alarmMappingName", valueMember: "alarmMappingName", width: 400, height: 20, theme: theme,autoOpen: true });
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
       	  	var rowindex = $("#gridHading").jqxGrid('getselectedrowindex');
            var row = $("#gridHading").jqxGrid('getrowdata', rowindex);
        	window.location = 'form.htm?id='+row.id;   
        }
        if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteMultiSelected"/>')  {
        	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
        	if (answer)
        	{
				var selectedrowindexes = $('#gridHading').jqxGrid('getselectedrowindexes'); 
        		var idList="";
        		var cond="";
        		//alert(selectedrowindexes);
        		//var rowIndexList = selectedrowindexes.split(",");
        		if (selectedrowindexes != null) {
        			for (var i=0; i<selectedrowindexes.length; i++) {
        				var row = $("#gridHading").jqxGrid('getrowdata', selectedrowindexes[i]);
        				idList+=cond+row.id;
        				cond=",";
        			}
        			// alert(idList);
        			window.location = 'delete.htm?idList='+idList;
        		}
   			}
        }
        var exportFileName =  "<c:out value='${exportFileName}'/>";
    	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$("#gridHading").jqxGrid('exportdata', 'xls', exportFileName);
	    }
    });
      
  });
	  

</script>