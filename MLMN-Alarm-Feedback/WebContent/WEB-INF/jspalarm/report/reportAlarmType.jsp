<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<title>${title}</title>
<content tag="heading">${title}</content> 	
<div>
	<div align="left">
		<ul class="ui-tabs-nav" >
		<c:choose>
			<c:when test="${type == 'ALARMTYPE'}">
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=ALARMTYPE"><span><fmt:message key="title.tabControls.alarmType"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=ALARMID"><span><fmt:message key="title.tabControls.alarmID"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=NE"><span><fmt:message key="title.tabControls.NE"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=TEAM"><span><fmt:message key="title.tabControls.TEAM"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=PROVINCE"><span><fmt:message key="title.tabControls.PROVINCE"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=REGION"><span><fmt:message key="title.tabControls.REGION"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=ALL"><span><fmt:message key="title.tabControls.ALL"/></span></a></li>
			</c:when>
			<c:when test="${type == 'ALARMID'}">
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=ALARMTYPE"><span><fmt:message key="title.tabControls.alarmType"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=ALARMID"><span><fmt:message key="title.tabControls.alarmID"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=NE"><span><fmt:message key="title.tabControls.NE"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=TEAM"><span><fmt:message key="title.tabControls.TEAM"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=PROVINCE"><span><fmt:message key="title.tabControls.PROVINCE"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=REGION"><span><fmt:message key="title.tabControls.REGION"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=ALL"><span><fmt:message key="title.tabControls.ALL"/></span></a></li>
			</c:when>
			<c:when test="${type == 'NE'}">
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=ALARMTYPE"><span><fmt:message key="title.tabControls.alarmType"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=ALARMID"><span><fmt:message key="title.tabControls.alarmID"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=NE"><span><fmt:message key="title.tabControls.NE"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=TEAM"><span><fmt:message key="title.tabControls.TEAM"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=PROVINCE"><span><fmt:message key="title.tabControls.PROVINCE"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=REGION"><span><fmt:message key="title.tabControls.REGION"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=ALL"><span><fmt:message key="title.tabControls.ALL"/></span></a></li>
			</c:when>
		 	<c:when test="${type == 'TEAM'||type == 'DISTRICT'}">
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=ALARMTYPE"><span><fmt:message key="title.tabControls.alarmType"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=ALARMID"><span><fmt:message key="title.tabControls.alarmID"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=NE"><span><fmt:message key="title.tabControls.NE"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=TEAM"><span><fmt:message key="title.tabControls.TEAM"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=PROVINCE"><span><fmt:message key="title.tabControls.PROVINCE"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=REGION"><span><fmt:message key="title.tabControls.REGION"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=ALL"><span><fmt:message key="title.tabControls.ALL"/></span></a></li>
			</c:when>
			<c:when test="${type == 'PROVINCE'}">
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=ALARMTYPE"><span><fmt:message key="title.tabControls.alarmType"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=ALARMID"><span><fmt:message key="title.tabControls.alarmID"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=NE"><span><fmt:message key="title.tabControls.NE"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=TEAM"><span><fmt:message key="title.tabControls.TEAM"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=PROVINCE"><span><fmt:message key="title.tabControls.PROVINCE"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=REGION"><span><fmt:message key="title.tabControls.REGION"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=ALL"><span><fmt:message key="title.tabControls.ALL"/></span></a></li>
			</c:when>
			<c:when test="${type == 'REGION'}">
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=ALARMTYPE"><span><fmt:message key="title.tabControls.alarmType"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=ALARMID"><span><fmt:message key="title.tabControls.alarmID"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=NE"><span><fmt:message key="title.tabControls.NE"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=TEAM"><span><fmt:message key="title.tabControls.TEAM"/></span></a></li>
				<li ><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=PROVINCE"><span><fmt:message key="title.tabControls.PROVINCE"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=REGION"><span><fmt:message key="title.tabControls.REGION"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=ALL"><span><fmt:message key="title.tabControls.ALL"/></span></a></li>
			</c:when>
			<c:when test="${type == 'ALL'}">
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=ALARMTYPE"><span><fmt:message key="title.tabControls.alarmType"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=ALARMID"><span><fmt:message key="title.tabControls.alarmID"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=NE"><span><fmt:message key="title.tabControls.NE"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=TEAM"><span><fmt:message key="title.tabControls.TEAM"/></span></a></li>
				<li ><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=PROVINCE"><span><fmt:message key="title.tabControls.PROVINCE"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=REGION"><span><fmt:message key="title.tabControls.REGION"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=ALL"><span><fmt:message key="title.tabControls.ALL"/></span></a></li>
			</c:when>
		 	<c:otherwise>
		 		<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=ALARMTYPE"><span><fmt:message key="title.tabControls.alarmType"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=ALARMID"><span><fmt:message key="title.tabControls.alarmID"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=NE"><span><fmt:message key="title.tabControls.NE"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=TEAM"><span><fmt:message key="title.tabControls.TEAM"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=PROVINCE"><span><fmt:message key="title.tabControls.PROVINCE"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=REGION"><span><fmt:message key="title.tabControls.REGION"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=ALL"><span><fmt:message key="title.tabControls.ALL"/></span></a></li>
			</c:otherwise>
		</c:choose>
		</ul>
		
	</div>
</div>
<div class="clear"></div>
<div class="ui-tabs-panel">

<form:form commandName="filter" method="post" action="${function}.htm?type=${type}">
<input type="hidden" name="teamTemp" id="teamTemp" value="${team}">
 <input type="hidden" name="districtTemp" id="districtTemp" value="${district}">
	<table >
		
			<tr> 
				<td style="width:70px"><fmt:message key="report.day"/></td>
				<td style="width:90px">
						<input type ="text"  value="${sdate}" name="sdate" id="sdate" size="10" maxlength="10" style="width:70px">
			   			 <img alt="calendar" title="Click to choose the from date" id="chooseSDate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				
				<td style="padding-left: 5px;width:30px"><fmt:message key="alarmLog.sdateT"/></td>
				<td style="width:90px">
					<input type ="text"  value="${edate}" name="edate" id="edate" size="10" maxlength="10" style="width:70px">
			   		<img alt="calendar" title="Click to choose the to date" id="chooseEDate" style="cursor: pointer; position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				<td style="padding-left: 5px;width:60px"><fmt:message key="alarmLog.network"/></td>
				<td>
					<div id='network'></div>
				</td>
				<td style="padding-left: 5px;width:60px"><fmt:message key="alarmLog.vendor"/></td>
				<td>
					<div id='vendor'></div>
				</td>
				<c:if test="${type == 'REGION'}">
					<td style="padding-left: 5px;width:50px"><fmt:message key="alarmLog.region"/></td>
					<td>
						<div id='region'></div>
					</td>
				</c:if>
				<c:choose>
					<c:when test="${type!= 'NE'&&type!= 'REGION'&&type!= 'ALL'}">
						<td style="padding-left: 5px;width:70px"><fmt:message key="alarmLog.province"/></td>
						<td>
							<div id='province'></div>
						</td>
					</c:when>
		 			<c:when test="${type== 'NE'||type== 'REGION'||type== 'ALL'}">
						<div id='province' hidden="true"></div>
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${type == 'TEAM'||type == 'DISTRICT'}">
						<td style="padding-left: 5px;width:70px"><fmt:message key="alarmLog.team"/></td>
						<td>
							 <div id='team'></div>
						</td >
						<td style="padding-left: 5px;width:70px"><fmt:message key="alarmLog.district"/></td>
						<td>
							<div id='district'></div>
						</td>
					</c:when>
		 			<c:when test="${type== 'NE'}">
						<td style="padding-left: 5px;width:50px"><fmt:message key="alarmLog.bscid"/></td>
						<td>
							 <div id='bscid'></div>
						</td>
					</c:when>
				</c:choose>
				<c:if test="${type=='ALARMTYPE'}">
					<td style="width:90px"><fmt:message key="alarmLog.alarmType"/></td>
					<td>
						 <div id='alarmType'></div>
					</td >
				</c:if>
				<c:if test="${type=='ALARMID'}">
				</tr>
					<tr>
						<td style="width:90px"><fmt:message key="alarmLog.alarmType"/></td>
						<td>
							 <div id='alarmType'></div>
						</td >
						<td style="padding-left: 5px;width:130px"><fmt:message key="alarmLog.alarmId"/></td>
						<td colspan="5">
							<div id='alarmMappingName'></div>
						</td>
				</c:if>
				<td style="padding-left: 5px;">
					<input class="button" type="submit" id="button" value="<fmt:message key="global.form.timkiem"/>" />
				</td>
			</tr>
		</table>
	</form:form>
</div>

<br/>
<div id="gridReport"></div>
<div id='menuReport'>
            <ul>
		   		<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
</div>
<div class="clear"></div>

<c:if test="${type != 'ALARMID' && type != 'ALARMTYPE'}">
<c:choose>
		 	<c:when test="${type == 'TEAM'||type == 'DISTRICT'}">
					<div id='jqxTabs'>
			            <ul>
			                <li style="margin-left: 30px;"><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=TEAM"><span><fmt:message key="title.tabControls.TEAM"/></span></a></li>
			                <li><a href="${pageContext.request.contextPath}/report/alarmtype/${function}.htm?type=DISTRICT"><span>District</span></a></li>
			            </ul>
			           <div id="content1">
			               <c:forEach  items="${alarmTypeList}"  var="alarmT">
								<fieldset>
									<legend>
										<input  id="bt${alarmT.alarmType}" type="button" value="ExportToImage" />
                					</legend>
									<div id="<c:out value='${alarmT.alarmType}'/>" style="width:100%; height:500px"></div>
									
								</fieldset>
							</c:forEach>
			            </div>
			            <div id="content2">
			            
				             <c:forEach items="${alarmTypeList}" var="alarmT">
								<fieldset>
									<legend>
										<input  id="bt<c:out value='${alarmT.alarmType}'/>D" type="button" value="ExportToImage" />
                					</legend>
									<div id="<c:out value='${alarmT.alarmType}'/>D" style="width:100%; height:500px"></div>
								</fieldset>
							</c:forEach>
			            </div>
			             
			        </div>
			</c:when>
		 	<c:otherwise>
		 		<c:forEach items="${alarmTypeList}"  var="alarmT">
					<fieldset>
						<legend>
							<input id="bt${alarmT.alarmType}" type="button" value="ExportToImage" />
	            		</legend>
						<div id="<c:out value='${alarmT.alarmType}'/>" style="width:100%; height:500px"></div>
					</fieldset>
				</c:forEach>
			</c:otherwise>
		</c:choose>
</c:if>

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
${gridReport}

//handle context menu 
$("#menuReport").on('itemclick', function (event) {
	    var args = event.args;
	    var exportFileName =  "<c:out value='${exportFileName}'/>";
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$("#gridReport").jqxGrid('exportdata', 'xls', exportFileName);
	    }
});

$(document).ready(function(){

		// Khai bao sdate, edate
		var theme =  getTheme();
		$('#button').jqxButton({ theme: theme });
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
        $("#vendor").jqxComboBox({ source: dataAdapterVendor, displayMember: "name", valueMember: "name", width: 140,height: 20,itemHeight: 30,theme: theme,autoDropDownHeight: true,autoOpen: true });
        var vendor =  "<c:out value='${vendor}'/>";
        if(vendor=="")
			$('#vendor').val('ALL');
        	//$('#vendor').val('ALCATEL');
		else
			$('#vendor').val(vendor);
      //combobox team  
        ${teamList}
 	    $("#team").jqxDropDownList({source: teamList, checkboxes: true, width: 120, height: 20, theme: theme });           
 	    var team =  "<c:out value='${team}'/>";
 	    if(team=="")
 			$('#team').val('ALL');
 		else
 		{
 			var teamVar = team.split(",");
 			if (teamVar != null) {
 				for (var i=0; i<teamVar.length; i++) {
 					$("#team").jqxDropDownList('checkItem', teamVar[i] ); 
 				}
 			}
 		}
       //combobox province  
       ${provinceList}
	    $("#province").jqxDropDownList({source: provinceList, checkboxes: true, width: 120, height: 20, theme: theme });           
	    var province =  "<c:out value='${province}'/>";
	    if(province=="")
			$('#province').val('ALL');
		else
		{
			var provinceVar = province.split(",");
			if (provinceVar != null) {
				for (var i=0; i<provinceVar.length; i++) {
					$("#province").jqxDropDownList('checkItem', provinceVar[i] ); 
				}
			}
		}
	    // combobox bsc
		${bscList}
	    $("#bscid").jqxDropDownList({source: bscList, checkboxes: true, width: 120, height: 20, theme: theme });           
	    var bscid =  "<c:out value='${bscid}'/>";
	    if(bscid=="")
			$('#bscid').val('ALL');
		else
		{
			var bscidVar = bscid.split(",");
			if (bscidVar != null) {
				for (var i=0; i<bscidVar.length; i++) {
					$("#bscid").jqxDropDownList('checkItem', bscidVar[i] ); 
				}
			}
		}
	    // combobox district
	 	${districtList}
	 	$("#district").jqxDropDownList({source: districtList, checkboxes: true, width: 120, height: 20, theme: theme });           
	    var district =  "<c:out value='${district}'/>";
	    if(district=="")
			$('#district').val('ALL');
		else
		{
			var districtVar = district.split(",");
			if (districtVar != null) {
				for (var i=0; i<districtVar.length; i++) {
					$("#district").jqxDropDownList('checkItem', districtVar[i] ); 
				}
			}
		}
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
       $("#network").jqxComboBox({ source: dataAdapternetwork, displayMember: "value", valueMember: "name",width: 100,height: 20,itemHeight: 30,theme: theme,autoDropDownHeight: true,autoOpen: true  });
        var network =  "<c:out value='${network}'/>";
	       if(network!="") {
			$('#network').val(network);
	       }
	       else
    	   {
    	   	$('#network').val('ALL');
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
        var networkT = $('#network').val();
		if (networkT=='ALL')
		{
			networkT='';
		}
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
		$("#alarmMappingName").jqxDropDownList({ source: dataAdapteralarmMappingName,checkboxes: true, displayMember: "alarmMappingName", valueMember: "alarmMappingName", width: 500, height: 20, theme: theme,autoOpen: true });
		var alarmMappingName =  "<c:out value='${alarmMappingName}'/>";
		//alert(alarmMappingName);
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
				$("#alarmMappingName").jqxDropDownList({ source: dataAdapteralarmMappingName,checkboxes: true, displayMember: "alarmMappingName", valueMember: "alarmMappingName", width: 300, height: 20, theme: theme,autoOpen: true });
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
	     		   $("#bscid").jqxDropDownList({source: BSCList, checkboxes: true, width: 120, height: 20, theme: theme });        
	     	   });
			 		
				var alarmTypeT = $('#alarmType').val();
				if (alarmTypeT=='ALL')
				{
					alarmTypeT='';
				}
				var network = $('#network').val();
				if (network=='ALL')
				{
					network='';
				}
				var urlalarmMappingName = "${pageContext.request.contextPath}/ajax/getAlarmMappingName.htm?vendor="+$("#vendor").val()+"&alarmType="+alarmTypeT+"&network="+network;
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
				$("#alarmMappingName").jqxDropDownList({ source: dataAdapteralarmMappingName,checkboxes: true, displayMember: "alarmMappingName", valueMember: "alarmMappingName", width: 300, height: 20, theme: theme,autoOpen: true });
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
		
		$("#network").change(function(){
			if ($("#network").val() != null && $("#network").val() != 'ALL') {
				var alarmTypeT = $('#alarmType').val();
				if (alarmTypeT=='ALL')
				{
					alarmTypeT='';
				}
				var vendorT = $('#vendor').val();
				if (vendorT=='ALL')
				{
					vendorT='';
				}
				var urlalarmMappingName = "${pageContext.request.contextPath}/ajax/getAlarmMappingName.htm?vendor="+vendorT+"&alarmType="+alarmTypeT+"&network="+$("#network").val();
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
				$("#alarmMappingName").jqxDropDownList({ source: dataAdapteralarmMappingName,checkboxes: true, displayMember: "alarmMappingName", valueMember: "alarmMappingName", width: 300, height: 20, theme: theme,autoOpen: true });
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
		 	   var username = "<c:out value='${username}'/>";
				var network = $('#network').val();
				if (network=='ALL')
				{
					network='';
				}
	    	   $.getJSON("${pageContext.request.contextPath}/ajax/getBSCByNetwork.htm",{network:network,username:username,vendor:vendorT}, function(j){
	    		   var BSCList=[];
	    		   BSCList =j;
	    		   $("#bscid").jqxDropDownList({source: BSCList, checkboxes: true, width: 120, height: 20, theme: theme });         
	    	   });
			}
		});	
	   //call view detail    
       $("#gridReport").on('cellselect', function (event) 
    	  		{
    	   			var type =  "<c:out value='${type}'/>";
    	   			var funtion =  "<c:out value='${function}'/>";
		    		//alert(type+'--'+funtion);
    	  		    var columnheader = $("#gridReport").jqxGrid('getcolumn', event.args.datafield).text; 
    	  		  	if (columnheader!='Day' && columnheader!='Network' && columnheader!='Vendor' && columnheader!='Province' && columnheader!='NE' && columnheader!='Team'&& columnheader!='District'&& columnheader!='Alarm mapping id'&& columnheader!='Alarm type'&& columnheader!='Alarm mapping name')
    	  		    {
    	  		    	var rowindex = event.args.rowindex;
    	  		    	var row = $("#gridReport").jqxGrid('getrowdata', rowindex);
    	  		    	var day = new Date(row.day);
    	  		    	var network = row.network;
    	  		    	var vendor= row.vendor;
    	  		    	var province= '';
    	  		    	var duarationTo='30';
    	  		    	if(row.province!=null)
   	  		    		{
   	  		    			province=row.province;
   	  		    		};
   	  		    		var alarmType ='';
   	  		    		if (columnheader=='Total')
	  		    		{
   	  		    		alarmType = 'ALL';
	  		    		}
   	  		    		else
   	  		    		{
   	  		    			if (funtion=='finishLately')
  	  		    			{
   	  		    				columnheader = $("#gridReport").jqxGrid('getcolumn', event.args.datafield).datafield; 
   	     	  		  			var str = columnheader.split('_'); 
   	  		    				alarmType = str[0];
  	  		    				duarationTo = str[1];
  	  		    				//alert(alarmType+'-'+duarationTo);
  	  		    			}
   	  		    			else
  	  		    			{
   	  		    				alarmType = columnheader;
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
    	  		    	//alert(date);
    	  		    	var ne='';
    	  		    	var team='';
    	  		    	var district='';
    	  		    	if (type=='NE')
   	  		    		{
    	  		    		ne= row.ne;
   	  		    		}
    	  		    	if (type=='TEAM')
   	  		    		{
    	  		    		team= row.groups;
    	  		    		district= row.district;
   	  		    		}
    	  		    	var alarmId='';
    	  		    	
    	  		    	if (type=='ALARMID')
   	  		    		{
    	  		    		alarmType= row.alarmType;
    	  		    		alarmId= row.alarmMappingName;
    	  		    		ne= row.ne;
    	  		    		team= row.groups;
   	  		    		}
    	  		    	
    	  		    	if (type=='ALARMTYPE')
   	  		    		{
    	  		    		alarmType= row.alarmType;
    	  		    		ne= row.ne;
    	  		    		team= row.groups;
   	  		    		}
    	  		    	
    	  		    	var sdateF = date;
    	  		    	var sdateT = date;
    	  		    	if (funtion!='nonfinish')
    	  		    	{
    	  		    		sdateF=sdateF+' 00:00';
    	  		    		sdateT=sdateT+' 23:59';
    	  		    	}
    	  		    	 window.open('${pageContext.request.contextPath}/report/detail/'+funtion+'.htm?sdate='+sdateF+
										'&edate='+sdateT+
											'&bscid='+ne+
											'&cellid=&vendor='+vendor+
											'&district='+district+
		    	  		    			'&alarmName=&province='+province+
			    	  		    		'&team='+team+
			    	  		    		'&alarmType='+alarmType+'&alarmMappingName='+alarmId+'+&reload=&reloadStr=&statusFinish='+
			    	  		    		'&severity=&netWork='+network+
			    	  		    		'&duarationTo='+duarationTo+'&bscPort=&btsPort=&objType=','_blank');
    	  		    }
    	  		    
    	  		});
});
</script>

<script type="text/javascript">

$('#district').on('open', function (event) { 
	var username = "<c:out value='${username}'/>";
	var team =  $('#team').val();
	//alert(team);
	var province =  $('#province').val();
	//alert(province);
	$.getJSON("${pageContext.request.contextPath}/ajax/getDistrictList.htm",{province:province,username:username,team:team}, function(j){
		   var districtList=[];
		   districtList =j;
		   $("#district").jqxDropDownList({source: districtList});   
		   var district =  $('#districtTemp').val();
		    if(district=="")
				$('#district').val('ALL');
			else
			{
				var districtVar = district.split(",");
				if (districtVar != null) {
					for (var i=0; i<districtVar.length; i++) {
						$("#district").jqxDropDownList('checkItem', districtVar[i] ); 
					}
				}
			}
	   });
	//$("#district").jqxDropDownList({source: data});
}); 

$('#team').on('open', function (event) { 
	var username = "<c:out value='${username}'/>";
	var province =  $('#province').val();
//	alert(province);
	$.getJSON("${pageContext.request.contextPath}/ajax/getSubTeamList.htm",{province:province,username:username}, function(j){
		   var teamList=[];
		   teamList =j;
		   $("#team").jqxDropDownList({source: teamList});  
		   var team =  $('#teamTemp').val();
		    if(team=="")
				$('#team').val('ALL');
			else
			{
				var teamVar = team.split(",");
				if (teamVar != null) {
					for (var i=0; i<teamVar.length; i++) {
						$("#team").jqxDropDownList('checkItem', teamVar[i] ); 
					}
				}
			}
	   });
	//$("#district").jqxDropDownList({source: data});
}); 

$('#team').on('close', function (event) { 
	$('#teamTemp').val($("#team").val());
});
$('#district').on('close', function (event) { 
	$('#districtTemp').val($("#district").val());
});

</script>
<c:if test="${type != 'ALARMID'}">
<%-- ${chart} --%>
<script type="text/javascript">	
	$(document).ready(function () {
		var minDay = "<c:out value='${minDay}'/>";
		var maxDay = "<c:out value='${maxDay}'/>";
		var type = "<c:out value='${type}'/>";
		var network = "<c:out value='${network}'/>";
		var province = "<c:out value='${province}'/>";
		var vendor = "<c:out value='${vendor}'/>";
		var bscid = "<c:out value='${bscid}'/>";
		var team = "<c:out value='${team}'/>";
		var district = "<c:out value='${district}'/>";
		var func = "<c:out value='${function}'/>";
		var description="<c:out value='${description}'/>";
		
		var months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
		 var categoryAxis =  {
	                text: 'Category Axis',
	                 textRotationAngle: 0,
	                dataField: 'Day',
	                formatFunction: function (value) {
	                    return  value.getDate();
	               },
	                toolTipFormatFunction: function (value) {
	                    return value.getDate() + '-' + months[value.getMonth()] + '-' + value.getFullYear();
	                },
	                showTickMarks: true,
	                type: 'date',
	                baseUnit: 'day',
	                tickMarksInterval: 1,
	                tickMarksColor: '#888888',
	                unitInterval: 1,
	                showGridLines: true,
	                gridLinesInterval: 1,
	                gridLinesColor: '#888888',
	                gridLinesDashStyle: '2,2',
	                minValue: minDay,
	                maxValue: maxDay,
	                valuesOnTicks: false
	            };
		// bIEU DO BINH THUONG
			${sierie}
			var seriesGroups = [
			 	                {
				                	type: 'spline',
				                    symbolType: 'circle',
				                    valueAxis:
				                    {
				                      	minValue: 0,
				                        displayValueAxis: true,
				                        formatFunction: function(value){
				                		return value.toFixed(0);
				                			},
				                		formatSettings:
				                		{
				                		    decimalPlaces: 0
				                		},
				                        description: 'Quantity',
				                         axisSize: 'auto',
				                		gridLinesDashStyle: '2,2',
				                        tickMarksColor: '#888888'
				                    },
				                    series: sierie
				                }
				            ];
	   <c:forEach  items="${alarmTypeList}"  var="alarmT">
	   		//var id = "<c:out value='${alarmT.alarmType}'/>";
			//var title = "ALARM TYPE: "+ "<c:out value='${alarmT.alarmType}'/>";
			// prepare the data
			$.getJSON("${pageContext.request.contextPath}/report/alarmtype/dataAlarmTypeChar.htm",{type:type,sdate:minDay,edate:maxDay,
				network:network,province:province,alarmtype:"<c:out value='${alarmT.alarmType}'/>",
				vendor:vendor,bscid:bscid,team:team,district:district,func: func}, function(j){
					//j.alarmType;
				var jdata = j.alarmType.replace(/\\/g,'');
				var sameData = jQuery.parseJSON(jdata);
				var settings = {
		        title: 'ALARM TYPE: '+ '<c:out value='${alarmT.alarmType}'/>',
		        description: description,
		        enableAnimations: true,
		        showLegend: true,
		        padding: { left: 5, top: 5, right: 11, bottom: 5 },
		        titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
		        source: sameData,
		        categoryAxis:categoryAxis,
		        colorScheme: 'scheme01',
		        seriesGroups:seriesGroups
			    };
		           // setup the chart
			$('#'+'<c:out value='${alarmT.alarmType}'/>').jqxChart(settings);
			$('#bt'+'<c:out value='${alarmT.alarmType}'/>').click(function () {
	            // call the export server to create a PNG image
	            $('#'+'<c:out value='${alarmT.alarmType}'/>').jqxChart('saveAsPNG', '<c:out value='${alarmT.alarmType}'/>'+'Chart'+description+'.png');
	        });
		});	 
	</c:forEach>	
	
// BIEU DO CHO DISTRICT
	<c:if test="${type == 'DISTRICT'}">
		
			$('#jqxTabs').jqxTabs({ selectedItem: 1 }); 
			// ${sierieD}
		 	 var seriesGroupsD = [
			 	                {
				                	type: 'spline',
				                    symbolType: 'circle',
				                    valueAxis:
				                    {
				                      	minValue: 0,
				                        displayValueAxis: true,
				                        formatFunction: function(value){
				                		return value.toFixed(0);
				                			},
				                		formatSettings:
				                		{
				                		    decimalPlaces: 0
				                		},
				                        description: 'Quantity',
				                         axisSize: 'auto',
				                		gridLinesDashStyle: '2,2',
				                        tickMarksColor: '#888888'
				                    },
				                    series: sierie
				                }
				            ];
		var settingsD = {
				   //     title: 'ALARM TYPE: '+ '<c:out value='${alarmT.alarmType}'/>',
				        description: description,
				        enableAnimations: true,
				        showLegend: true,
				        padding: { left: 5, top: 5, right: 11, bottom: 5 },
				        titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
				        //source: sameData,
				        categoryAxis:categoryAxis,
				        colorScheme: 'scheme01',
				        seriesGroups:seriesGroupsD
				        };
	<c:forEach  items="${alarmTypeList}"  var="alarmT">		
			 
			// setup the chart
			 $('#'+'<c:out value='${alarmT.alarmType}'/>D').jqxChart(settingsD);
			 $('#'+'<c:out value='${alarmT.alarmType}'/>D').jqxChart({title:'ALARM TYPE: '+ '<c:out value='${alarmT.alarmType}'/>'}); 
			 $('#'+'<c:out value='${alarmT.alarmType}'/>D').jqxChart('refresh');
			 		// prepare the data
			
			$.getJSON("${pageContext.request.contextPath}/report/alarmtype/dataAlarmTypeChar.htm",{type:'DISTRICT',sdate:minDay,edate:maxDay,
				network:network,province:province,alarmtype:"<c:out value='${alarmT.alarmType}'/>",
				vendor:vendor,bscid:bscid,team:team,district:district,func: func}, function(j){
					var jdata = j.alarmType.replace(/\\/g,'');
					var sameData = jQuery.parseJSON(jdata);
					var dataAdapter = new $.jqx.dataAdapter(sameData);
				 $('#'+'<c:out value='${alarmT.alarmType}'/>D').jqxChart({source:sameData});
				 $('#'+'<c:out value='${alarmT.alarmType}'/>D').jqxChart('refresh');
 					
			});
			
			
			 $('#bt'+'<c:out value='${alarmT.alarmType}'/>D').click(function () {
		            // call the export server to create a PNG image
		            $('#'+'<c:out value='${alarmT.alarmType}'/>D').jqxChart('saveAsPNG', '<c:out value='${alarmT.alarmType}'/>D'+'Chart'+description+'.png');
		        });
		</c:forEach> 
	</c:if>
	
		
});	
</script>			
</c:if>
<script type="text/javascript">
$(document).ready(function () {
    var theme = getTheme();
    $('#jqxTabs').jqxTabs({ width: '100%', position: 'top', theme: theme, reorder: true });
    var cbregion =  "<c:out value='${region}'/>"; 
    var type =  "<c:out value='${type}'/>";
    if (type=='REGION'){
		  //combobox region
			var urlRegion = "${pageContext.request.contextPath}/ajax/getRegionList.htm";
		    var sourceRegion =
		    {
		       datatype: "json",
		       url: urlRegion,
		       datafields: [
		                     { name: 'name'},
		                     { name: 'value'}
		                 ],
		        async: false
		   };
		    var dataAdapterregion = new $.jqx.dataAdapter(sourceRegion);
		    $("#region").jqxDropDownList({source: dataAdapterregion, displayMember: "value", valueMember: "name",checkboxes: true, width: 120, autoDropDownHeight: true, theme: theme,enableHover: true });           
			if(cbregion=="")
				$('#region').val('Choose');
			else
			{
				var regionVar = cbregion.split(",");
				if (regionVar != null) {
					for (var i=0; i<regionVar.length; i++) {
						$("#region").jqxDropDownList('checkItem', regionVar[i] ); 
					}
				}
			}    
    };
});
</script>
