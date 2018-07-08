<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<title>${title}</title>
<content tag="heading">${title}</content> 	
<div class="ui-tabs-panel">
<!-- type=NE,TEAM,PROVINCE,REGION,ALL -->
<form:form commandName="filter" method="post" action="${function}.htm">
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
				<td style="padding-left: 5px;width:50px"><fmt:message key="alarmLog.region"/></td>
				<td>
					<div id='region'></div>
				</td>
				<c:choose>
					<c:when test="${function== 'province'||function== 'district'}">
						<td style="padding-left: 5px;width:60px"><fmt:message key="alarmLog.province"/></td>
						<td>
							<div id='province'></div>
						</td>
					</c:when>
					<c:otherwise><div id='province' hidden="true"></div></c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${function== 'district'}">
						<td style="padding-left: 5px;width:50px"><fmt:message key="alarmLog.district"/></td>
						<td>
							<div id='district'></div>
						</td>
					</c:when>
					<c:otherwise><div id='district' hidden="true"></div></c:otherwise>
				</c:choose>
				
				<c:choose>
					<c:when test="${function== 'team'||function== 'dept'}">
						<td style="padding-left: 5px;width:60px"><fmt:message key="alarmLog.dept"/></td>
						<td>
							<div id='dept'></div>
						</td>
					</c:when>
					<c:otherwise><div id='dept' hidden="true"></div></c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${function== 'team'}">
						<td style="padding-left: 5px;width:70px"><fmt:message key="alarmLog.team"/></td>
						<td>
							 <div id='team'></div>
						</td >
						
					</c:when>
		 			<c:otherwise><div id='team' hidden="true"></div></c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${function== 'fb_type'}">
						<td style="padding-left: 5px;width:100px">Loại phản ánh</td>
						<td>
							 <div id='fb_type'></div>
						</td >
						
					</c:when>
		 			<c:otherwise><div id='fb_type' hidden="true"></div></c:otherwise>
				</c:choose>
				
				<td style="padding-left: 5px;">
					<input class="button" type="submit" id="button" value="<fmt:message key="global.form.timkiem"/>" />
				</td>
			</tr>
		</table>
	</form:form>
</div>
<div class="clear"></div>
	<fieldset>
	    <legend>
			<input  id="btexport" type="button" value="ExportToImage" />
	    </legend>
		<div id='FEEDBACK' style="width:100%; height:500px"></div>
	</fieldset>
	
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
 	    
 	 //combobox dept  
        ${deptList}
 	    $("#dept").jqxDropDownList({source: deptList, checkboxes: true, width: 120, height: 20, theme: theme });           
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
 	    
 	 //combobox fb_type  
        ${fbTypeList}
 	    $("#fb_type").jqxDropDownList({source: fbTypeList, checkboxes: true, width: 350, height: 20, theme: theme });           
 	    var fb_type =  "<c:out value='${fb_type}'/>";
 	    if(fb_type=="")
 			$('#fb_type').val('ALL');
 		else
 		{
 			var fb_typeVar = fb_type.split(",");
 			if (fb_typeVar != null) {
 				for (var i=0; i<fb_typeVar.length; i++) {
 					$("#fb_type").jqxDropDownList('checkItem', fb_typeVar[i] ); 
 				}
 			}
 		}
 	    
});

$('#district').on('open', function (event) { 
	
	var region =  $('#region').val();
	var province =  $('#province').val();
	//alert(province);
	$.getJSON("${pageContext.request.contextPath}/ajax/getDistrictFBList.htm",{region:region,province:province}, function(j){
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
}); 

$('#team').on('open', function (event) { 
	var dept =  $('#dept').val();
//	alert(province);
	$.getJSON("${pageContext.request.contextPath}/ajax/getTeamFbList.htm",{dept:dept}, function(j){
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
}); 

$('#team').on('close', function (event) { 
	$('#teamTemp').val($("#team").val());
});
$('#district').on('close', function (event) { 
	$('#districtTemp').val($("#district").val());
});


</script>

<script type="text/javascript">
${chart}

$('#btexport').click(function () {
    // call the export server to create a PNG image
    $('#FEEDBACK').jqxChart('saveAsPNG', 'ChartFB.png');
});
</script>


