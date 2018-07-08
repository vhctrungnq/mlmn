<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<title>${title}</title>
<content tag="heading">${title}</content> 	

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
				
				<td style="padding-left: 5px;width:50px"><fmt:message key="alarmLog.vendor"/></td>
				<td>
					<div id='vendor'></div>
				</td>
				<c:if test="${type == 'REGION'}">
					<td style="padding-left: 5px;width:50px"><fmt:message key="alarmLog.region"/></td>
					<td>
						<div id='region'></div>
					</td>
				</c:if>
				<c:if test="${type!= 'NE'&&type!= 'REGION'&&type!= 'ALL'}">
					<td style="padding-left: 5px;width:60px"><fmt:message key="alarmLog.province"/></td>
					<td>
						<div id='province'></div>
					</td>
				</c:if>
				<c:choose>
					<c:when test="${type== 'TEAM'}">
					</tr><tr>
						<td style="width:70px"><fmt:message key="alarmLog.team"/></td>
						<td>
							 <div id='team'></div>
						</td >
						<td style="padding-left: 5px;width:50px"><fmt:message key="alarmLog.district"/></td>
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
				<td style="padding-left: 5px;"><fmt:message key="alarmLog.qualityLimit"/></td>
				<td >
					 >= <input type="text" id="qualityLimit" name="qualityLimit" style="width: 100px;"/>
				</td>
				<td style="padding-left: 5px;">
					<input class="button" type="submit" id="button" value="<fmt:message key="global.form.timkiem"/>" />
				</td>
			</tr>
		</table>
	</form:form>
</div>
<br/>
<%@ include file="/includeJQ/gridReport.jsp" %>
<div class="clear"></div>
<c:choose>
		 	<c:when test="${type == 'TEAM'}">
					<div id='jqxTabs'>
			            <ul>
			                <li style="margin-left: 30px;">Sub team</li>
			                <li>District</li>
			            </ul>
			            <div>
			               <fieldset>
			               		<legend>
									<input style='float: left;' id="btjitterChar" type="button" value="ExportToImage" />
               					</legend>
								<div id='jitterChar' style="width:100%; height:500px"></div>
							</fieldset>
							
			            </div>
			            <div>
			                <fieldset>
			                	<legend>
									<input style='float: left;' id="btjitterCharDis" type="button" value="ExportToImage" />
               					</legend>
								<div id='jitterCharDis' style="width:100%; height:500px"></div>
							</fieldset>
			            </div>
			        </div>
			</c:when>
		 	<c:otherwise>
		 		<fieldset>
               		<legend>
						<input style='float: left;' id="btjitterChar" type="button" value="ExportToImage" />
            		</legend>
					<div id='jitterChar' style="width:100%; height:500px"></div>
				</fieldset>
			</c:otherwise>
		</c:choose>

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
		$("#qualityLimit").jqxInput({  height: 20, width: 50, theme: theme});
	     var qualityLimit =  "<c:out value='${qualityLimit}'/>";
		 	//alert(cellid);
	     if(qualityLimit!="")
				$('#qualityLimit').val(qualityLimit);
	     
	     
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

<script type="text/javascript">
${jitterChar}
</script>
<script type="text/javascript">
${jitterCharDis}
</script>
<script type="text/javascript">
$(document).ready(function () {
    var theme = getDemoTheme();
    $('#jqxTabs').jqxTabs({ width: '100%', position: 'top', theme: theme, collapsible: true });
});

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
