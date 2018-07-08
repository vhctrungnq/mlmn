<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<title>${title}</title>
<content tag="heading">${title}</content> 	
<div>
	<div align="left">
		<ul class="ui-tabs-nav" >
		<c:choose>
			
			<c:when test="${type == 'SITE'}">
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=SITE"><span><fmt:message key="title.tabControls.SITE"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=NE"><span><fmt:message key="title.tabControls.NE"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=TEAM"><span><fmt:message key="title.tabControls.TEAM"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=DEPT"><span><fmt:message key="title.tabControls.DEPT"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=DISTRICT"><span><fmt:message key="title.tabControls.DISTRICT"/></span></a></li>
			</c:when>
			<c:when test="${type == 'NE'}">
				<li><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=SITE"><span><fmt:message key="title.tabControls.SITE"/></span></a></li>
				<li  class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=NE"><span><fmt:message key="title.tabControls.NE"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=TEAM"><span><fmt:message key="title.tabControls.TEAM"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=DEPT"><span><fmt:message key="title.tabControls.DEPT"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=DISTRICT"><span><fmt:message key="title.tabControls.DISTRICT"/></span></a></li>
			</c:when>
			
		 	<c:when test="${type == 'TEAM'}">
				<li><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=SITE"><span><fmt:message key="title.tabControls.SITE"/></span></a></li>
				<li ><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=NE"><span><fmt:message key="title.tabControls.NE"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=TEAM"><span><fmt:message key="title.tabControls.TEAM"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=DEPT"><span><fmt:message key="title.tabControls.DEPT"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=DISTRICT"><span><fmt:message key="title.tabControls.DISTRICT"/></span></a></li>
			</c:when>
			<c:when test="${type == 'DEPT'}">
				<li><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=SITE"><span><fmt:message key="title.tabControls.SITE"/></span></a></li>
				<li ><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=NE"><span><fmt:message key="title.tabControls.NE"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=TEAM"><span><fmt:message key="title.tabControls.TEAM"/></span></a></li>
				<li  class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=DEPT"><span><fmt:message key="title.tabControls.DEPT"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=DISTRICT"><span><fmt:message key="title.tabControls.DISTRICT"/></span></a></li>
			</c:when>
			<c:when test="${type == 'DISTRICT'}">
				<li><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=SITE"><span><fmt:message key="title.tabControls.SITE"/></span></a></li>
				<li ><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=NE"><span><fmt:message key="title.tabControls.NE"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=TEAM"><span><fmt:message key="title.tabControls.TEAM"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=DEPT"><span><fmt:message key="title.tabControls.DEPT"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=DISTRICT"><span><fmt:message key="title.tabControls.DISTRICT"/></span></a></li>
			</c:when>
		 	<c:otherwise>
		 		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=SITE"><span><fmt:message key="title.tabControls.SITE"/></span></a></li>
				<li ><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=NE"><span><fmt:message key="title.tabControls.NE"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=TEAM"><span><fmt:message key="title.tabControls.TEAM"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=DEPT"><span><fmt:message key="title.tabControls.DEPT"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/irsr/list.htm?type=DISTRICT"><span><fmt:message key="title.tabControls.DISTRICT"/></span></a></li>
			</c:otherwise>
		</c:choose>
		</ul>
		
	</div>
</div>
<div class="clear"></div>
<div class="ui-tabs-panel">

<form:form commandName="filter" method="post" action="list.htm?type=${type}">
<input type="hidden" name="teamTemp" id="teamTemp" value="${team}">
<input type="hidden" name="districtTemp" id="districtTemp" value="${district}">
<input type="hidden" name="siteTemp" id="siteTemp" value="${site}"> 
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
				<c:if test="${type == 'NE'||type == 'SITE'}">
					<td style="padding-left: 5px;width:60px"><fmt:message key="alarmLog.network"/></td>
					<td>
						<div id='network'></div>
					</td>
				</c:if>
				
				<c:if test="${type != 'TEAM'&&type != 'DEPT'}">
					<td style="padding-left: 5px;width:70px"><fmt:message key="alarmLog.district"/></td>
					<td>
						<div id='district'></div>
					</td>
				</c:if>
				<c:if test="${type == 'TEAM'||type == 'DEPT'}">
					<td style="padding-left: 5px;width:70px"><fmt:message key="alarmLog.dept"/></td>
					<td>
						 <div id='dept'></div>
					</td >
				</c:if>
				<c:if test="${type == 'TEAM'}">
					
					<td style="padding-left: 5px;width:70px"><fmt:message key="alarmLog.team"/></td>
					<td>
						 <div id='team'></div>
					</td >
				</c:if>
				<c:if test="${type == 'NE'||type == 'SITE'}">
					<td style="padding-left: 5px;width:50px"><fmt:message key="alarmLog.bscid"/></td>
					<td>
						 <div id='bscid'></div>
					</td>
				</c:if>
				<c:if test="${type == 'SITE'}">
					<td style="padding-left: 5px;width:50px"><fmt:message key="alarmLog.site"/></td>
					<td>
						 <div id='site'></div>
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
${highlight}
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
		//combobox team  
        ${deptList}
 	    $("#dept").jqxDropDownList({source: deptList, checkboxes: true, width: 250, height: 20, theme: theme });           
 	    var dept =  "<c:out value='${dept}'/>";
 	    if(dept=="")
 			$('#dept').val('ALL');
 		else
 		{
 			var deptVar = dept.split(",");
 			if (deptVar != null) {
 				for (var i=0; i<deptVar.length; i++) {
 					$("#dept").jqxDropDownList('checkItem', deptVar[i] ); 
 				}
 			}
 		}
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
	    
	 // combobox bsc
		${siteList}
	    $("#site").jqxDropDownList({source: siteList, checkboxes: true, width: 120, height: 20, theme: theme });           
	    var site =  "<c:out value='${site}'/>";
	    if(site=="")
			$('#site').val('ALL');
		else
		{
			var siteVar = site.split(",");
			if (siteVar != null) {
				for (var i=0; i<siteVar.length; i++) {
					$("#site").jqxDropDownList('checkItem', siteVar[i] ); 
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
       
		$("#network").change(function(){
			if ($("#network").val() != null && $("#network").val() != 'ALL') {
		 	   var username = "<c:out value='${username}'/>";
				var network = $('#network').val();
				var bscid=$('#bscid').val();
				if (network=='ALL')
				{
					network='';
				}
				var username = "<c:out value='${username}'/>";
	    	   $.getJSON("${pageContext.request.contextPath}/ajax/getBSCByNetwork.htm",{network:network,username:username,vendor:''}, function(j){
	    		   var BSCList=[];
	    		   BSCList =j;
	    		   $("#bscid").jqxDropDownList({source: BSCList, checkboxes: true, width: 120, height: 20, theme: theme });         
	    	   });
	    	   $.getJSON("${pageContext.request.contextPath}/ajax/getSiteByNetwork.htm",{network:network,bscid:bscid,district:'',username:username}, function(j){
	    		   var siteList=[];
	    		   siteList =j;
	    		   $("#site").jqxDropDownList({source: siteList, checkboxes: true, width: 120, height: 20, theme: theme });         
	    	   });
			}
		});	
	   //call view detail    
       $("#gridReport").on('cellselect', function (event) 
    	  		{
    	   			var type =  "<c:out value='${type}'/>";
    	   			
    	  		    var columnheader = $("#gridReport").jqxGrid('getcolumn', event.args.datafield).text; 
    	  		  	var rowindex = event.args.rowindex;
	  		    	var row = $("#gridReport").jqxGrid('getrowdata', rowindex);
	  		    	var day = new Date(row.day);
	  		    	var network = row.network;
    	  		  	if (columnheader=='NE')
    	  		    {
    	  		    	
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
    	  		    	var district='';
    	  		    	if (type=='NE')
   	  		    		{
    	  		    		
    	  		    		district= row.district;
   	  		    		}
    	  		    	 window.open('${pageContext.request.contextPath}/report/irsr/list.htm?sdate='+date+
										'&edate='+date+'&network='+network+
											'&bscid='+ne+
											'&team=&district='+district+
		    	  		    			'&site=&type=SITE','_blank');
    	  		    }
    	  		    
    	  		});
});
</script>

<script type="text/javascript">


$('#site').on('open', function (event) { 
	var username = "<c:out value='${username}'/>";
	var network= $('#network').val();
	var bscid=$('#bscid').val();
	var site=$('#site').val();
	var district=$('#district').val();
	/* alert(network);
	alert(district);
	alert(bscid);
	alert(site); */
//	alert(province);
	$.getJSON("${pageContext.request.contextPath}/ajax/getSiteByNetwork.htm",{network:network,bscid:bscid,district:district,username:username}, function(j){
		   var siteList=[];
		   siteList =j;
		   $("#site").jqxDropDownList({source: siteList}); 
		   var site = $('#siteTemp').val();
		    if(site=="")
				$('#site').val('ALL');
			else
			{
				var siteVar = site.split(",");
				if (siteVar != null) {
					for (var i=0; i<siteVar.length; i++) {
						$("#site").jqxDropDownList('checkItem', siteVar[i] ); 
					}
				}
			}
	   });
	//$("#district").jqxDropDownList({source: data});
}); 

$('#site').on('close', function (event) { 
	$('#siteTemp').val($("#site").val());
});
$('#district').on('close', function (event) { 
	$('#districtTemp').val($("#district").val());
});

</script>

