
<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>${titleF}</title>
<content tag="heading">${titleF}</content>

<form:form commandName="handing" method="post" action="form.htm?"> 
	<form:hidden path="id"/>
	<form:hidden path="alarmId"/>
	<form:hidden path="alarmName" />
	<form:hidden path="alarmMappingId"/>
	<form:hidden path="alarmMappingName"/>
	<table class="simple2"> 
      <tr>
           	<td style="width: 100px;"><fmt:message key="alarmLog.vendor"/></td>
			<td>
				<div id='vendor'></div>
			</td>
			<td style="width: 150px;"><fmt:message key="alarmLog.alarmType"/></td>
			<td>
				<div id='alarmType' style = "width: 100%"></div>
			</td>
      </tr> 
   		 <tr>
           	<td><fmt:message key="alarmLog.alarmName"/></td>
			<td>
				<div id='alarmNameDr' style = "width: 100%"></div>
			</td>
	    	<td><fmt:message key="alarmLog.alarmMappingName"/></td>
			<td>
				<div id='alarmMappingNameDr' style = "width: 100%"></div>
			</td>
      </tr> 
       <tr>
           <td><fmt:message key="vAlAlarmLog.alarmInfo"/></td>
           <td colspan="3"> 
           	<form:input path="alarmInfo"  class = "long" rows="10" maxlength="900"/>
          </td>
       </tr>
	    <tr>
           <td><fmt:message key="vAlAlarmLog.causeby"/></td>
           <td  colspan="3"> 
           		<form:input type ="text" path="causeby" maxlength="900"  class = "long" rows="3" />
          </td>
       </tr>
       
     <tr>
           <td><fmt:message key="vAlAlarmLog.actionProcess"/></td>
           <td colspan="3"> 
           	<form:input path="actionProcess"  class = "long" rows="10" maxlength="900"/>
          </td>
       </tr>
       <tr>
           <td><fmt:message key="vAlAlarmLog.description"/></td>
           <td colspan="3"> 
           	<form:input path="description"  class = "long" rows="10" maxlength="900"/>
          </td>
       </tr>
       <tr>
           <td></td>
           <td colspan="3">
           		<input type="submit" id='btsubmit' class="button" value="<fmt:message key="global.form.luulai"/>" />
           	  	<input type="button" id='btCancel' class="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm"'>	
           </td>
       </tr>
    </table>
</form:form>
<style>
    .error {
    	color: red;
    }
</style> 

<script type="text/javascript">
var theme =  getTheme(); 
$('#btsubmit').jqxButton({ theme: theme });
$('#btCancel').jqxButton({ theme: theme });

$(document).ready(function(){
	
	 //input causeby
    $("#causeby").jqxInput({placeHolder: "Enter causeby", height: 25, width: '100%', minLength: 0, maxLength: 900, theme: theme});
  //input alarmInfo
    $("#alarmInfo").jqxInput({placeHolder: "Enter alarmInfo", height: 25, width: '100%', minLength: 0, maxLength: 900, theme: theme});
  //input actionProcess
    $("#actionProcess").jqxInput({placeHolder: "Enter actionProcess", height: 25, width: '100%', minLength: 0, maxLength: 900, theme: theme});
  //input reportProcess
    $("#description").jqxInput({placeHolder: "Enter description", height: 25, width: '100%', minLength: 0, maxLength: 900, theme: theme});
 	// Create a jqxDropDownList AlarmType
	var urlalarmType = "${pageContext.request.contextPath}/ajax/getAlarmTypeAtShift.htm?network=";
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
    // Create a jqxDropDownList
    $("#alarmType").jqxDropDownList({ source: dataAdapteralarmType, displayMember: "alarmType", valueMember: "alarmType", width: 150, height: 20, theme: theme,autoDropDownHeight: true,autoOpen: true  });
    $("#alarmType").jqxDropDownList('insertAt', { label: '', value: ''}, 0 );
    var alarmType =  "<c:out value='${handing.alarmType}'/>";
    if(alarmType=='')
		$('#alarmType').val('ALL');
	else
		$('#alarmType').val(alarmType);
 // Create a jqxDropDownList
    $("#vendor").jqxDropDownList({ source: dataAdapterVendor, displayMember: "value", valueMember: "name", width: 150,height: '20px',itemHeight: 30,theme: theme,autoDropDownHeight: true,autoOpen: true });
   	var vendor =  "<c:out value='${handing.vendor}'/>";
    if(vendor!=''&&vendor!=null)
    	$('#vendor').val(vendor);
	else
		 $('#vendor').val('ALCATEL');
  
 // Create a jqxDropDownList alarmName
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
				{ name: 'blockColumnName'},
				{ name: 'blockValue'}
       ],
        url: urlAlarmName,
        async: false
    };
    var dataAdapterAlarmName = new $.jqx.dataAdapter(sourceAlarmName);
    // Create a jqxDropDownList
    $("#alarmNameDr").jqxComboBox({ source: dataAdapterAlarmName, displayMember: "blockValue", valueMember: "blockColumnName", width: "100%", height: 20, theme: theme,autoOpen: true });
    var alarmName =  $('#alarmName').val();
    var alarmid =  $('#alarmId').val();
    if(alarmName!=""&&alarmName!=null){
    	$('#alarmNameDr').val(alarmName);}
	else {
		$('#alarmNameDr').val(alarmid);}
	//Create a jqxDropDownList alarmName
	
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
	// Create a jqxDropDownList
	$('#alarmMappingNameDr').jqxComboBox({ source: dataAdapteralarmMappingName, displayMember: "alarmMappingName", valueMember: "alarmMappingId", width: "100%", height: 20, theme: theme,autoOpen: true });
	var alarmMappingName =  "<c:out value='${handing.alarmMappingName}'/>";
    if(alarmMappingName==""){
		$('#alarmMappingNameDr').val('');}
	else{
		$('#alarmMappingNameDr').val(alarmMappingName); }

});
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
	            { name: 'blockColumnName'},
				{ name: 'blockValue'}
	        ],
	        url: urlAlarmName,
	        async: false
	    };
	    var dataAdapterAlarmName = new $.jqx.dataAdapter(sourceAlarmName);
	    // Create a jqxDropDownList
	    $("#alarmNameDr").jqxComboBox({ source: dataAdapterAlarmName, displayMember: "blockValue", valueMember: "blockColumnName", width: "100%", height: 20, theme: theme,autoOpen: true });
	    var alarmName =  $('#alarmName').val();
	    var alarmid =  $('#alarmId').val();
	    if(alarmName!=""&&alarmName!=null){
	    	$('#alarmNameDr').val(alarmName);}
		else {
			$('#alarmNameDr').val(alarmid);}
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
		// Create a jqxDropDownList
		$('#alarmMappingNameDr').jqxComboBox({ source: dataAdapteralarmMappingName, displayMember: "alarmMappingName", valueMember: "alarmMappingId", width: "100%", height: 20, theme: theme,autoOpen: true });
		 var alarmMappingName =  "<c:out value='${handing.alarmMappingName}'/>";
			if(alarmMappingName==""){
				$('#alarmMappingNameDr').val('');}
			else{
				$('#alarmMappingNameDr').val(alarmMappingName); }
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
                	{ name: 'blockColumnName'},
   					{ name: 'blockValue'}
	        ],
	        url: urlAlarmName,
	        async: false
	    };
	    var dataAdapterAlarmName = new $.jqx.dataAdapter(sourceAlarmName);
	    // Create a jqxDropDownList
	    $("#alarmNameDr").jqxComboBox({ source: dataAdapterAlarmName, displayMember: "blockValue", valueMember: "blockColumnName", width: "100%", height: 20, theme: theme,autoOpen: true });
	    var alarmName =  $('#alarmName').val();
	    var alarmid =  $('#alarmId').val();
	    if(alarmName!=""&&alarmName!=null){
	    	$('#alarmNameDr').val(alarmName);}
		else {
			$('#alarmNameDr').val(alarmid);}
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
		// Create a jqxDropDownList
		$('#alarmMappingNameDr').jqxComboBox({ source: dataAdapteralarmMappingName, displayMember: "alarmMappingName", valueMember: "alarmMappingId", width: "100%", height: 20, theme: theme,autoOpen: true });
		 var alarmMappingName =  "<c:out value='${handing.alarmMappingName}'/>";
		 if(alarmMappingName==""){
				$('#alarmMappingNameDr').val('');}
			else{
				$('#alarmMappingNameDr').val(alarmMappingName); }
	}
   });	
	$('#alarmMappingNameDr').on('change', function (event)
		{
		    var args = event.args;
		    if (args) {
				    var item = args.item;
				    // get item's label and value.
				    var label = item.label;
				    var value = item.value;
				    //alert(value);
				    $("#alarmMappingId").val(value);
				    $('#alarmMappingName').val(label);
				}
		    else
		    	{
		    			/* alert($('#alarmMappingNameDr').val()); */
		    			$("#alarmMappingId").val('');
		    	 		$('#alarmMappingName').val($('#alarmMappingNameDr').val());
		    	}
		    
		    
		});
	$('#alarmNameDr').on('change', function (event)
			{
			    var args = event.args;
			    if (args) {
					    var item = args.item;
					    // get item's label and value.
					    var label = item.label;
					    var value = item.value;
					//    alert(value);
					    if (value=='ALARM_NAME')
				    	{
				    		$("#alarmName").val(label);
				    	}
					    else
				    	{
					    	$("#alarmId").val(label);
					    	//$("#alarmName").val(label);
				    	}
					    
					}
			    else
			    	{
			    			
			    		$("#alarmName").val($('#alarmNameDr').val());
			    	}
			});
</script>
</body>
</html>