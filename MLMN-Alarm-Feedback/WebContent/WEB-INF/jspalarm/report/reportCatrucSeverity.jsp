<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<title>${title}</title>
<content tag="heading">${title}</content> 	
<div>
	<div align="left">
		<ul class="ui-tabs-nav" >
		<c:choose>
			<c:when test="${type == 'NE'}">
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/catruc.htm?type=NE"><span><fmt:message key="title.tabControls.NE"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/catruc.htm?type=PROVINCE"><span><fmt:message key="title.tabControls.PROVINCE"/></span></a></li>
			</c:when>
			<c:when test="${type == 'PROVINCE'}">
				<li class=""><a href="${pageContext.request.contextPath}/report/catruc.htm?type=NE"><span><fmt:message key="title.tabControls.NE"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/catruc.htm?type=PROVINCE"><span><fmt:message key="title.tabControls.PROVINCE"/></span></a></li>
			</c:when>
		 	<c:otherwise>
		 		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/catruc.htm?type=NE"><span><fmt:message key="title.tabControls.NE"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/catruc.htm?type=PROVINCE"><span><fmt:message key="title.tabControls.PROVINCE"/></span></a></li>
			</c:otherwise>
		</c:choose>
		</ul>
		
	</div>
</div>
<div class="clear"></div>
<div class="ui-tabs-panel">

<form:form commandName="filter" method="post" action="catruc.htm?type=${type}">
 <table >
		
			<tr> 
				<td style="width:70px"><fmt:message key="catruc.day"/></td>
				<td style="width:90px">
						<input type ="text"  value="${day}" name="day" id="day" size="10" maxlength="10" style="width:70px">
			   			 <img alt="calendar" title="Click to choose the from date" id="chooseSDate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				<td style="width:30px;padding-left: 10px;">
					<fmt:message key="catruc.ca"/>
				</td>
				<td>
					<div id='shift'></div>
				</td>
				<td style="padding-left: 5px;width:60px"><fmt:message key="alarmLog.network"/></td>
				<td>
					<div id='network'></div>
				</td>
				
				<td style="padding-left: 5px;width:50px"><fmt:message key="alarmLog.vendor"/></td>
				<td>
					<div id='vendor'></div>
				</td>
				<c:if test="${type!= 'NE'}">
					<td style="padding-left: 5px;width:60px"><fmt:message key="alarmLog.province"/></td>
					<td>
						<div id='province'></div>
					</td>
				</c:if>
				<c:if test="${type== 'NE'}">
					<td style="padding-left: 5px;width:50px"><fmt:message key="alarmLog.bscid"/></td>
					<td>
						 <div id='bscid'></div>
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
<div id="catrucReport"></div>
<div id='menuReport'>
            <ul>
		   		<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script type="text/javascript">
Calendar.setup({
    inputField		:	"day",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseSDate",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 
</script>
<script type="text/javascript">
${catrucReport}
//handle context menu 
$("#menuReport").on('itemclick', function (event) {
	    var args = event.args;
	    var exportFileName =  "<c:out value='${exportFileName}'/>";
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$("#catrucReport").jqxGrid('exportdata', 'xls', exportFileName);
	    }
});

$(document).ready(function(){

		// Khai bao sdate, edate
		var theme =  getTheme();
		$('#button').jqxButton({ theme: theme });
		// Create a jqxComboBox shift
       	var urlshift = "${pageContext.request.contextPath}/ajax/getShift.htm";
        // prepare the data
        var sourceshift =
        {
            datatype: "json",
            datafields: [
                { name: 'value' },
                { name: 'name' }
            ],
            url: urlshift,
            async: false
        };
        var dataAdaptershift = new $.jqx.dataAdapter(sourceshift);
        // Create a jqxComboBox
        $("#shift").jqxComboBox({ source: dataAdaptershift, displayMember: "value", valueMember: "name", width: 100,height: 20,itemHeight: 30,theme: theme,autoDropDownHeight: true,autoOpen: true });
        var shift =  "<c:out value='${shift}'/>";
        if(shift=="")
			$('#shift').val('');
		else
			$('#shift').val(shift);
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
        $("#vendor").change(function(){
 		   var username = "<c:out value='${username}'/>";
 		  var network = $('#network').val();
			if (network=='ALL')
			{
				network='';
			}
			 var vendor = $('#vendor').val();
				if (vendor=='ALL')
				{
					vendor='';
				}
     	   $.getJSON("${pageContext.request.contextPath}/ajax/getBSCByNetwork.htm",{network:network,username:username,vendor:vendor}, function(j){
     		   var BSCList=[];
     		   BSCList =j;
     		   $("#bscid").jqxDropDownList({source: BSCList, checkboxes: true, width: 120, height: 20, theme: theme });        
     	   });
 		});	
     
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
       $("#network").jqxComboBox({ source: dataAdapternetwork, displayMember: "value", valueMember: "name",width: 80,height: 20,itemHeight: 30,theme: theme,autoDropDownHeight: true,autoOpen: true  });
        var network =  "<c:out value='${network}'/>";
	       if(network!="") {
			$('#network').val(network);
	       }
	       else
    	   {
    	   	$('#network').val('ALL');
    	   }
       $("#network").change(function(){
		   var username = "<c:out value='${username}'/>";
		   var network = $('#network').val();
			if (network=='ALL')
			{
				network='';
			}
			 var vendor = $('#vendor').val();
				if (vendor=='ALL')
				{
					vendor='';
				}
    	   $.getJSON("${pageContext.request.contextPath}/ajax/getBSCByNetwork.htm",{network:network,username:username,vendor:vendor}, function(j){
    		   var BSCList=[];
    		   BSCList =j;
    		   $("#bscid").jqxDropDownList({source: BSCList, checkboxes: true, width: 120, height: 20, theme: theme });        
    	   });
		});	
	   
});


</script>


