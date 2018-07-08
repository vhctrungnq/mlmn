<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>${titleDetail}</title>
<content tag="heading">${titleDetail}</content> 	
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
			<c:when test="${netWork == '2G'}">
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork="><span><fmt:message key="title.tabControls.All"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=2G"><span><fmt:message key="title.tabControls.2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=3G"><span><fmt:message key="title.tabControls.3G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=CS_CORE"><span><fmt:message key="title.tabControls.CS_CORE"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=PS_CORE"><span><fmt:message key="title.tabControls.PS_CORE"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=IPBB"><span><fmt:message key="title.tabControls.IPBB"/></span></a></li>
			</c:when>
		 	<c:when test="${netWork == '3G'}">
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork="><span><fmt:message key="title.tabControls.All"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=2G"><span><fmt:message key="title.tabControls.2G"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=3G"><span><fmt:message key="title.tabControls.3G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=CS_CORE"><span><fmt:message key="title.tabControls.CS_CORE"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=PS_CORE"><span><fmt:message key="title.tabControls.PS_CORE"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=IPBB"><span><fmt:message key="title.tabControls.IPBB"/></span></a></li>
			</c:when>
			<c:when test="${netWork == 'PS_CORE'}">
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork="><span><fmt:message key="title.tabControls.All"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=2G"><span><fmt:message key="title.tabControls.2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=3G"><span><fmt:message key="title.tabControls.3G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=CS_CORE"><span><fmt:message key="title.tabControls.CS_CORE"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=PS_CORE"><span><fmt:message key="title.tabControls.PS_CORE"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=IPBB"><span><fmt:message key="title.tabControls.IPBB"/></span></a></li>
			</c:when>
			<c:when test="${netWork == 'CS_CORE'}">
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork="><span><fmt:message key="title.tabControls.All"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=2G"><span><fmt:message key="title.tabControls.2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=3G"><span><fmt:message key="title.tabControls.3G"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=CS_CORE"><span><fmt:message key="title.tabControls.CS_CORE"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=PS_CORE"><span><fmt:message key="title.tabControls.PS_CORE"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=IPBB"><span><fmt:message key="title.tabControls.IPBB"/></span></a></li>
			</c:when>
			<c:when test="${netWork == 'IPBB'}">
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork="><span><fmt:message key="title.tabControls.All"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=2G"><span><fmt:message key="title.tabControls.2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=3G"><span><fmt:message key="title.tabControls.3G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=CS_CORE"><span><fmt:message key="title.tabControls.CS_CORE"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=PS_CORE"><span><fmt:message key="title.tabControls.PS_CORE"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=IPBB"><span><fmt:message key="title.tabControls.IPBB"/></span></a></li>
			</c:when>
		 	<c:otherwise>
		 		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork="><span><fmt:message key="title.tabControls.All"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=2G"><span><fmt:message key="title.tabControls.2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=3G"><span><fmt:message key="title.tabControls.3G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=CS_CORE"><span><fmt:message key="title.tabControls.CS_CORE"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=PS_CORE"><span><fmt:message key="title.tabControls.PS_CORE"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/detail/${function}.htm?netWork=IPBB"><span><fmt:message key="title.tabControls.IPBB"/></span></a></li>
			</c:otherwise>
		</c:choose>
		</div>
			<div align="right" class="ui_link">
				<table>
					<tr>
						<td style="width: 30px;height:20px; border: 1px; background-color: red;"><a style="background: none;border: 0px;" href="${pageContext.request.contextPath}/report/detail/${function}.htm?sdate=${sdate}&edate=${edate}&bscid=${bscid}&cellid=${cellid}&vendor=${vendor}&district=${district}&alarmName=${alarmName}&province=${province}&team=${team}&alarmType=${alarmType}&alarmMappingName=${alarmMappingName}&reload=${reload}&reloadStr=${reloadStr}&statusFinish=&severity=A1&netWork=${netWork}" title="Số lượng cảnh báo A1">${countA1}</a></td>
						<td style="width: 30px;height:20px; border: 1px; background-color: #EFA110;"><a style="background: none;border: 0px;"  href="${pageContext.request.contextPath}/report/detail/${function}.htm?sdate=${sdate}&edate=${edate}&bscid=${bscid}&cellid=${cellid}&vendor=${vendor}&district=${district}&alarmName=${alarmName}&province=${province}&team=${team}&alarmType=${alarmType}&alarmMappingName=${alarmMappingName}&reload=${reload}&reloadStr=${reloadStr}&statusFinish=&severity=A2&netWork=${netWork}" title="Số lượng cảnh báo A2">${countA2}</a></td>
						<td style="width: 30px;height:20px; border: 1px; background-color: #EFEF4F;"><a style="background: none;border: 0px;" href="${pageContext.request.contextPath}/report/detail/${function}.htm?sdate=${sdate}&edate=${edate}&bscid=${bscid}&cellid=${cellid}&vendor=${vendor}&district=${district}&alarmName=${alarmName}&province=${province}&team=${team}&alarmType=${alarmType}&alarmMappingName=${alarmMappingName}&reload=${reload}&reloadStr=${reloadStr}&statusFinish=&severity=A3&netWork=${netWork}" title="Số lượng cảnh báo A3">${countA3}</a></td>
						<td style="width: 30px;height:20px; border: 1px; background-color: #455FC6;"><a style="background: none;border: 0px;" href="${pageContext.request.contextPath}/report/detail/${function}.htm?sdate=${sdate}&edate=${edate}&bscid=${bscid}&cellid=${cellid}&vendor=${vendor}&district=${district}&alarmName=${alarmName}&province=${province}&team=${team}&alarmType=${alarmType}&alarmMappingName=${alarmMappingName}&reload=${reload}&reloadStr=${reloadStr}&statusFinish=&severity=A4&netWork=${netWork}" title="Số lượng cảnh báo A4">${countA4}</a></td>
						<td style="width: 30px;height:20px; border: 1px; background-color: #32CD32;"><a style="background: none;border: 0px;" href="${pageContext.request.contextPath}/report/detail/${function}.htm?sdate=${sdate}&edate=${edate}&bscid=${bscid}&cellid=${cellid}&vendor=${vendor}&district=${district}&alarmName=${alarmName}&province=${province}&team=${team}&alarmType=${alarmType}&alarmMappingName=${alarmMappingName}&reload=${reload}&reloadStr=${reloadStr}&statusFinish=Y&severity=&netWork=${netWork}"  title="Số lượng cảnh báo đã kết thúc">${totalFinish}</a></td>
						<td style="width: 30px;height:20px; border: 1px;"><a style="background: none;border: 0px;" href="${pageContext.request.contextPath}/report/detail/${function}.htm?sdate=${sdate}&edate=${edate}&bscid=${bscid}&cellid=${cellid}&vendor=${vendor}&district=${district}&alarmName=${alarmName}&province=${province}&team=${team}&alarmType=${alarmType}&alarmMappingName=${alarmMappingName}&reload=${reload}&reloadStr=${reloadStr}&statusFinish=&severity=&netWork=${netWork}"  title="Tổng số lượng cảnh báo">${total}</a></td>
					</tr>
				</table>
			</div>
		</ul>
		
	</div>
</div>
<div class="clear"></div>
<div class="ui-tabs-panel">
<form:form commandName="filter" method="post" action="${function}.htm?netWork=${netWork}">
<input type="hidden" name="alarmName" id="alarmName" value="${alarmName}">

	<table >
		
			<tr> 
				<td style="width:90px"><fmt:message key="alarmLog.sdateF"/></td>
				<td style="width:150px">
						<input type ="text"  value="${sdate}" name="sdate" id="sdate" size="17" maxlength="16" style="width:100px">
			   			 <img alt="calendar" title="Click to choose the from date" id="chooseSDate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
					<!-- <div id='sdate'></div> -->
				</td>
				
				<td style="padding-left: 5px;width:70px"><fmt:message key="alarmLog.sdateT"/></td>
				<td style="width:150px">
					<input type ="text"  value="${edate}" name="edate" id="edate" size="17" maxlength="16" style="width:100px">
			   		<img alt="calendar" title="Click to choose the to date" id="chooseEDate" style="cursor: pointer; position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
					<!-- <div id='edate'></div> -->
				</td>
				<td style="padding-left: 5px;width: 140px;"><fmt:message key="alarmLog.province"/></td>
				<td colspan="3">
					 <input type="text" id="province" name="province" style="width: 100px;"/>
				</td>
				<td style="padding-left: 5px;width: 90px;"><fmt:message key="alarmLog.team"/></td>
				<td  colspan="3">
					 <input type="text" id="team" name="team" style="width: 100px;"/>
				</td>
			</tr>
			<tr>
				<td ><fmt:message key="alarmLog.vendor"/></td>
				<td>
					<div id='vendor'></div>
				</td>
				<td style="padding-left: 5px;" ><fmt:message key="alarmLog.district"/></td>
				<td>
					 <input type="text" id="district" name="district" style="width: 100px;"/>
				</td>
				<td style="padding-left: 5px;"><fmt:message key="alarmLog.bscid"/></td>
				<td colspan="3">
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
				<td  style="padding-left: 5px;"><fmt:message key="alarmLog.region"/></td>
				<td >
					<div id='region' style = "width: 100%"></div>
				</td>
				<td style="padding-left: 5px;"><fmt:message key="alarmLog.alarmMappingName"/></td>
				<td colspan="3">
					<div id='alarmMappingName' style = "width: 100%"></div>
				</td>
				<c:choose>
					<c:when test="${function == 'finishLately'}">
						<td style="padding-left: 5px;"><fmt:message key="alarmLog.Duaration"/></td>
						<td style="width: 100px;">
							 > <input type="text" id="duarationTo" name="duarationTo" style="width: 100px;"/>
						</td>
					</c:when>
					<c:when test="${function == 'jitter'}">
						<td style="padding-left: 5px;"><fmt:message key="alarmLog.objType"/></td>
						<td >
							<input type="text" id="objType" name="objType" style="width: 100px;"/>
						</td>
						<td style="padding-left: 5px;"><fmt:message key="alarmLog.bscPort"/></td>
						<td >
							<input type="text" id="bscPort" name="bscPort" style="width: 100px;"/>
						</td>
						<td style="padding-left: 5px;"><fmt:message key="alarmLog.btsPort"/></td>
						<td >
							<input type="text" id="btsPort" name="btsPort" style="width: 100px;"/>
						</td>
					</c:when>
					<c:otherwise>
						<input type="hidden" id="duarationTo" name="duarationTo" style="width: 100px;"/>
						<input type="hidden" id="btsPort" name="btsPort" style="width: 100px;"/>
						<input type="hidden" id="bscPort" name="bscPort" style="width: 100px;"/>
						<input type="hidden" id="objType" name="objType" style="width: 100px;"/>
						<input type="hidden" id="quality" name="quality" style="width: 100px;"/>
					</c:otherwise>
				</c:choose>
					<td>
						<input class="button" type="submit" id="btFilter" name="btFilter" value="<fmt:message key="global.form.timkiem"/>" />
					</td>
			</tr>
		</table>
	</form:form>
</div>
<div class="clear"></div>
<%@ include file="/includeJQ/gridAlarmLog.jsp" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<c:choose>
<c:when test="${function == 'nonfinish'}">
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
</c:when>
<c:otherwise>
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
</c:otherwise>
</c:choose>
<script type="text/javascript">
	//refresh 
	$(document).ready(function(){
		
		// Khai bao sdate, edate
		var theme =  getTheme();
		$('#btFilter').jqxButton({ theme: theme });
		$("#quality").jqxInput({  height: 20, width: 50, theme: theme});
	     var quality =  "<c:out value='${quality}'/>";
		 	//alert(cellid);
	     if(quality!="")
				$('#quality').val(quality);
		//Input duaration
	     $("#duarationTo").jqxInput({  height: 20, width: 80, theme: theme});
	     var duarationTo =  "<c:out value='${duarationTo}'/>";
		 	//alert(cellid);
	     if(duarationTo!="")
				$('#duarationTo').val(duarationTo);
	     
	   //Input objType
	     $("#objType").jqxInput({  height: 20, width: 100, theme: theme});
	     var objType =  "<c:out value='${objType}'/>";
		 	//alert(cellid);
	     if(objType!="")
				$('#objType').val(objType);
	     
	   //Input bscPort
	     $("#bscPort").jqxInput({  height: 20, width: 50, theme: theme});
	     var bscPort =  "<c:out value='${bscPort}'/>";
		 	//alert(cellid);
	     if(bscPort!="")
				$('#bscPort').val(bscPort);
	     
	   //Input btsPort
	     $("#btsPort").jqxInput({  height: 20, width: 50, theme: theme});
	     var btsPort =  "<c:out value='${btsPort}'/>";
		 	//alert(cellid);
	     if(btsPort!="")
				$('#btsPort').val(btsPort);
	     
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
		$("#district").jqxInput({ placeHolder: "Enter a district", height: 25, width: 150, theme: theme,
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
		/* var urlAlarmName = "${pageContext.request.contextPath}/ajax/getAlarmName.htm?vendor="+vendorT+"&alarmType="+alarmTypeT+"&network="+networkT;
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
			$('#alarmName').val(alarmName); */
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
			/* var urlAlarmName = "${pageContext.request.contextPath}/ajax/getAlarmName.htm?vendor="+$("#vendor").val()+"&alarmType="+alarmTypeT+"&network="+networkT;
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
				$('#alarmName').val(alarmName); */
			
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
			/* var urlAlarmName = "${pageContext.request.contextPath}/ajax/getAlarmName.htm?vendor="+vendorT+"&alarmType="+$("#alarmType").val()+"&network="+networkT;
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
				$('#alarmName').val(alarmName); */
			
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
        	window.location = '${pageContext.request.contextPath}/report/form.htm?id='+row.id+'&netWork='+"<c:out value='${netWork}'/>"+'&function='+"<c:out value='${function}'/>";   
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
        		
        	}
          
        }
        if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
        	
        	var funtion = "<c:out value='${function}'/>";
        	
        		 window.open('${pageContext.request.contextPath}/exportExcel/reportAlarmLog.htm?sdate='+"<c:out value='${sdate}'/>"+
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
      		        	'&duarationTo='+"<c:out value='${duarationTo}'/>"+
      		        	'&bscPort='+"<c:out value='${bscPort}'/>"+
      		        	'&btsPort='+"<c:out value='${btsPort}'/>"+
      		        	'&objType='+"<c:out value='${objType}'/>"+
      		        	'&region='+"<c:out value='${region}'/>"+
      		        	'&function='+funtion,'_blank');
            
        }
      
    });
    var cbregion =  "<c:out value='${region}'/>"; 
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
	// alert(dept);
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
</script>
