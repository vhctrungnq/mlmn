<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>${title}</title>
<content tag="heading">${title}</content> 	
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxwindow.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxdropdownbutton.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxtree.js"></script>
<style>
.ui-tabs-nav .ui_link a:hover{
color: black;
}
</style>

<style>
 .shiftId
{
color:blue;
}
 </style>  
<div>
	<div align="left">
		<ul class="ui-tabs-nav">	
		<c:choose>
			<c:when test="${function == 'MLL_SITE'}">
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/bao-cao-van-hanh-kt.htm?function=MLL_SITE"><span><fmt:message key="title.tabControls.MLL_SITE"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/bao-cao-van-hanh-kt.htm?function=MLL_CELL"><span><fmt:message key="title.tabControls.MLL_CELL"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/bao-cao-van-hanh-kt.htm?function=SU_CO_LON"><span><fmt:message key="title.tabControls.SU_CO_LON"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/bao-cao-van-hanh-kt.htm?function=HIEU_CHINH_MO_RONG"><span><fmt:message key="title.tabControls.HIEU_CHINH_MO_RONG"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/bao-cao-van-hanh-kt.htm?function=TRAM_CAP_3"><span><fmt:message key="title.tabControls.TRAM_CAP_3"/></span></a></li>
			</c:when>
		 	<c:when test="${function == 'MLL_CELL'}">
				<li class=""><a href="${pageContext.request.contextPath}/report/bao-cao-van-hanh-kt.htm?function=MLL_SITE"><span><fmt:message key="title.tabControls.MLL_SITE"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/bao-cao-van-hanh-kt.htm?function=MLL_CELL"><span><fmt:message key="title.tabControls.MLL_CELL"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/bao-cao-van-hanh-kt.htm?function=SU_CO_LON"><span><fmt:message key="title.tabControls.SU_CO_LON"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/bao-cao-van-hanh-kt.htm?function=HIEU_CHINH_MO_RONG"><span><fmt:message key="title.tabControls.HIEU_CHINH_MO_RONG"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/bao-cao-van-hanh-kt.htm?function=TRAM_CAP_3"><span><fmt:message key="title.tabControls.TRAM_CAP_3"/></span></a></li>
			</c:when>
			<c:when test="${function == 'SU_CO_LON'}">
				<li class=""><a href="${pageContext.request.contextPath}/report/bao-cao-van-hanh-kt.htm?function=MLL_SITE"><span><fmt:message key="title.tabControls.MLL_SITE"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/bao-cao-van-hanh-kt.htm?function=MLL_CELL"><span><fmt:message key="title.tabControls.MLL_CELL"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/bao-cao-van-hanh-kt.htm?function=SU_CO_LON"><span><fmt:message key="title.tabControls.SU_CO_LON"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/bao-cao-van-hanh-kt.htm?function=HIEU_CHINH_MO_RONG"><span><fmt:message key="title.tabControls.HIEU_CHINH_MO_RONG"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/bao-cao-van-hanh-kt.htm?function=TRAM_CAP_3"><span><fmt:message key="title.tabControls.TRAM_CAP_3"/></span></a></li>
			</c:when>
			<c:when test="${function == 'HIEU_CHINH_MO_RONG'}">
				<li class=""><a href="${pageContext.request.contextPath}/report/bao-cao-van-hanh-kt.htm?function=MLL_SITE"><span><fmt:message key="title.tabControls.MLL_SITE"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/bao-cao-van-hanh-kt.htm?function=MLL_CELL"><span><fmt:message key="title.tabControls.MLL_CELL"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/bao-cao-van-hanh-kt.htm?function=SU_CO_LON"><span><fmt:message key="title.tabControls.SU_CO_LON"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/bao-cao-van-hanh-kt.htm?function=HIEU_CHINH_MO_RONG"><span><fmt:message key="title.tabControls.HIEU_CHINH_MO_RONG"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/bao-cao-van-hanh-kt.htm?function=TRAM_CAP_3"><span><fmt:message key="title.tabControls.TRAM_CAP_3"/></span></a></li>
			</c:when>
			<c:when test="${function == 'TRAM_CAP_3'}">
				<li class=""><a href="${pageContext.request.contextPath}/report/bao-cao-van-hanh-kt.htm?function=MLL_SITE"><span><fmt:message key="title.tabControls.MLL_SITE"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/bao-cao-van-hanh-kt.htm?function=MLL_CELL"><span><fmt:message key="title.tabControls.MLL_CELL"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/bao-cao-van-hanh-kt.htm?function=SU_CO_LON"><span><fmt:message key="title.tabControls.SU_CO_LON"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/report/bao-cao-van-hanh-kt.htm?function=HIEU_CHINH_MO_RONG"><span><fmt:message key="title.tabControls.HIEU_CHINH_MO_RONG"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/bao-cao-van-hanh-kt.htm?function=TRAM_CAP_3"><span><fmt:message key="title.tabControls.TRAM_CAP_3"/></span></a></li>
			</c:when>
		</c:choose>
		</ul>
	</div>
</div>
<div class="clear"></div>
<div class="ui-tabs-panel">
<form:form commandName="filter" method="post" action="bao-cao-van-hanh-kt.htm?function=${function}">
	<table >
		
			<tr> 
				<td style="width:90px"><fmt:message key="catruc.ngayTKF"/></td>
				<td style="width:150px">
						<input type ="text"  value="${dateF}" name="dateF" id="dateF" size="17" maxlength="16" style="width:100px">
			   			 <img alt="calendar" title="Click to choose the from date" id="chooseSDateF" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				<td  style="padding-left: 5px;width:60px"><fmt:message key="catruc.ngayTKTO"/></td>
				<td style="width:150px">
					<input type ="text"  value="${dateT}" name="dateT" id="dateT" size="17" maxlength="16" style="width:100px">
			   		<img alt="calendar" title="Click to choose the to date" id="choosesdateT" style="cursor: pointer; position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
					<!-- <div id='edate'></div> -->
				</td>
				
				
				<td style="width:30px;padding-left: 5px;">
			<fmt:message key="catruc.caTK"/>
		</td>
		<td>
			<select name="caTK" id="caTK" style="width: 80px;height:20px; padding-top: 4px;">
				<option value=""><fmt:message key="global.shiftAll"/></option>
				<c:forEach var="item" items="${caList}">
					<c:choose>
		                <c:when test="${item.value == caTK}">
		                    <option value="${item.value}" selected="selected">${item.value}</option>
		                </c:when>
						<c:otherwise>
							<option value="${item.value}">${item.value}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> 
		</td>	
		
		<td style="padding-left: 5px;"><fmt:message key="alarmLog.alarmName"/></td>
				<td >
					<input type="text" id="alarmName" name="alarmName" style="width: 100px;"/>
				</td>	
		</tr>
			<tr>
				<td ><fmt:message key="alarmLog.team"/></td>
		<td  >
			 <input type="text" id="team" name="team" style="width: 100px;"/>
		</td>
				<td style="padding-left: 5px;"><fmt:message key="alarmLog.groups"/></td>
				<td  >
					 <input type="text" id="groups" name="groups" style="width: 100px;"/>
				</td>
				<td style="padding-left: 5px;"><fmt:message key="alarmLog.bscid"/></td>
				<td >
					 <input type="text" id="bscid" name="bscid" style="width: 200px;"/>
				</td>
				<td style="padding-left: 5px;"><fmt:message key="alarmLog.cellTK"/></td>
				<td >
					<input type="text" id="cellid"  name="cellid" style="width: 200px;" />
					<input class="button" type="submit" id="btFilter" name="btFilter" value="<fmt:message key="global.form.timkiem"/>" />
				</td>
			</tr>	
		</table>
		<div style="float: right;" id="jqxlistbox"></div>
		<div id="grid"></div>
		 <div id='menuReport'>
		            <ul>
				   		<li><fmt:message key="global.button.exportExcel"/></li>
			        </ul>
		 </div>
		
	</form:form>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script type="text/javascript">
${grid}

$("#menuReport").on('itemclick', function (event) {
    var args = event.args;
    var exportFileName =  "<c:out value='${exportFileName}'/>";
    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
    	$("#grid").jqxGrid('exportdata', 'xls', exportFileName);
    }
});


Calendar.setup({
    inputField		:	"dateF",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseSDateF",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 

Calendar.setup({
    inputField		:	"dateT",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "choosesdateT",   	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 	
$(document).ready(function(){
	var theme =  getTheme();
	$('#btFilter').jqxButton({ theme: theme });
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
	        // terms.push("");
	         value = terms.join(",");
	    	}
   		return value;
	};
	
	$("#alarmName").jqxInput({ height: 20, width: 200, theme: theme});
	//Input BSC
    ${bscList}
    $("#bscid").jqxInput({ height: 20, width: '100%', theme: theme,
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
 	$("#cellid").jqxInput({ height: 20, width: 200, theme: theme,
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
   
	//Input team
    ${teamList}
    $("#team").jqxInput({ height: 20, width: '100%', theme: theme,
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
    var groupList=[];
	$.getJSON("${pageContext.request.contextPath}/ajax/getGroupListAlarm.htm",{dept:"",team:team}, function(j){
		groupList =j;
	   });
	
   $("#groups").jqxInput({ height: 20, width: '100%', theme: theme,
       source: function (query, response) {
           var item = query.split(/,\s*/).pop();
           // update the search query.
           $("#groups").jqxInput({ query: item });
           response(groupList);
       },
       renderer: renderer
   });
   
   $("#team").change(function(){
	   var teamProcess = $("#team").val();
  	  if (teamProcess==null||teamProcess=='null')
  		  {
  			teamProcess='';
  		  }
  	 $.getJSON("${pageContext.request.contextPath}/ajax/getGroupListAlarm.htm",{dept:'',team:teamProcess}, function(j){
	   		groupList =j;
		   $("#groups").jqxInput({source: function (query, response) {
	           var item = query.split(/,\s*/).pop();
	           // update the search query.
	           $("#groups").jqxInput({ query: item });
	           response(groupList);
	       },
	       renderer: renderer
		    });
	   });	
});	
});

</script>