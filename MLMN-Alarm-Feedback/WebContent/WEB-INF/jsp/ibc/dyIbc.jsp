<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:if test = "${network == '2g' }" >    
	<title>BÁO CÁO KPI IBC 2G</title>
	<h1>BÁO CÁO KPI IBC 2G</h1>
	<ul class="ui-tabs-nav">
		<li class=ui-tabs-selected><a href="${pageContext.request.contextPath}/report/ibc/hrList.htm?network=2g"><span>Báo cáo 2g</span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/report/ibc/hrList.htm?network=3g"><span>Báo cáo 3g</span></a></li>
	</ul>
</c:if>
<c:if test = "${network == '3g' }" >    
	<title>BÁO CÁO KPI IBC 3G</title>
	<h1>BÁO CÁO KPI IBC 3G </h1>
	<ul class="ui-tabs-nav">
		<li class=><a href="${pageContext.request.contextPath}/report/ibc/hrList.htm?network=2g"><span>Báo cáo 2g</span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/ibc/hrList.htm?network=3g"><span>Báo cáo 3g</span></a></li>
	</ul>
</c:if>
<div class="ui-tabs-panel">
	
		<table style= "border-spacing: 0; border-width: 0; padding: 0; border-width: 0;" class="form">
		<form:form method="get" action="hrList.htm" >
			<tr>
			  		<td><input type="hidden" id="network" name="network" value = "${network }"></td>
			  		<td style = "max-width:60px">CELL </td>
					<td style = "max-width:150px; padding: 5px;"> <input type="text" id="cell_box" value = "${cellid }" name = "cellid"/> </td>
					<td style = "padding-left:15px">Từ </td>
	            	<td style = "padding-left:5px"><input type = "number" value="${startHour}" name="startHour" id="startHour" size="5" maxlength="5" min = "0" max = "23"></td>
	            	<td style = "padding-left:5px"> giờ </td>
	            	<td style = "padding-left:15px">ngày </td>
	            	<td style = "max-width:120px; padding-left:5px">
						<input type="text" id=startDate name="startDate" value="${startDate}" style = "max-width:90px"/>&nbsp;
          				<img alt="calendar" title="Click to choose the sdate " id="chooseSdate" style="cursor: pointer; " src="${pageContext.request.contextPath}/images/calendar.png"/>
	    			</td>
	    			<td style = "padding-left:15px">Tới</td>
	            	<td style = "padding-left:5px"><input type = "number" value="${endHour}" name="endHour" id="endHour" size="5" maxlength="5" min = "0" max = "23"></td>
	            	<td style = "padding-left:5px"> giờ </td>
	            	<td style = "padding-left:15px">ngày </td>
	            	<td style = "max-width:120px; padding-left:5px">
						<input type="text" id=endDate name="endDate" value="${endDate}" style = "max-width:90px"/>&nbsp;
          				<img alt="calendar" title="Click to choose the edate " id="choosEdate" style="cursor: pointer; " src="${pageContext.request.contextPath}/images/calendar.png"/>
	    			</td>
	    			
	            	
	             <td style = "padding-left:15px"> <input type="submit" class="button" name="submit" value="View Report"/> </td>
	        </tr>
	        </form:form>		
		</table>
	<br/>
<c:if test = "${network == '2g' }" > 
	<div  style="overflow: auto;" class="tableStandar" >	
	<display:table name="${dataList}" id="tabledata" requestURI="" pagesize="100" class="simple2" export="true" sort="external" defaultsort="1">
		<display:column title="No." >  <c:out value="${tabledata_rowNum}"/> </display:column>
		<display:column property="day" format="{0,date,dd/MM/yyyy}" title="Date" headerClass="master-header-1" sortable="true" sortName = "day"/>
		<display:column property="hour" title="Hour" headerClass="master-header-1" sortable="true" sortName = "hour"/>
		<display:column property="siteid"  titleKey="Site" sortable="true" sortName = "siteid" />
		<display:column property="cellid"  titleKey="Cell" sortable="true" sortName = "cellid" />
		<display:column property="cssr" titleKey="CSSR_2G" sortable="true" sortName = "cssr" class = "cssr"/>		
		<display:column property="cdr" titleKey="CDR_2G" sortable="true" sortName = "cdr" class = "cdr"/>	
		<display:column property="numSdcchDropBlock" titleKey="NUM_SDCCH_DROP_BLOCK" sortable="true" sortName = "numSdcchDropBlock" class = "numSdcchDropBlock"/>	   		    
		<display:column property="numDrop2g" titleKey="NUM_DROP2G" sortable="true" sortName = "numSdcchDropBlock" class = "numDrop2g"/>	
		<display:column property="times" titleKey="TIMES" sortable="true" sortName = "times"/>	   		    
	</display:table>	
	</div>
</c:if>	
<c:if test = "${network == '3g' }" >
	<div  style="overflow: auto;" class="tableStandar" >
			<display:table name="${data3gList}" id="tabledata3g" requestURI="" pagesize="100" class="simple2" export="true" sort="external" defaultsort="1">
			<display:column title="No." >  <c:out value="${tabledata3g_rowNum}"/> </display:column>
			<display:column property="day" format="{0,date,dd/MM/yyyy}" title="Date" headerClass="master-header-1" sortable="true" sortName = "day"/>
			<display:column property="hour" title="Hour" headerClass="master-header-1" sortable="true" sortName = "hour"/>
			<display:column property="siteid"  titleKey="Site" sortable="true" sortName = "siteid" />
			<display:column property="cellid"  titleKey="Cell" sortable="true" sortName = "cellid" />
			<display:column property="rtpw" titleKey="RTPW" sortable="true" sortName = "rtpw" class = "rtpw"/>		
			<display:column property="cssr3g" titleKey="CSSR3G" sortable="true" sortName = "cssr3g" class = "cssr3g"/>		
			<display:column property="cdr3g" titleKey="CDR3G" sortable="true" sortName = "cdr3g" class = "cdr3g"/>	
			<display:column property="pasr3g" titleKey="PASR3G" sortable="true" sortName = "pasr3g" class = "pasr3g"/>	   		    
			<display:column property="padr3g" titleKey="PADR3G" sortable="true" sortName = "padr3g" class = "padr3g"/>	
			<display:column property="numCsfail" titleKey="NUM_CSFAIL" sortable="true" sortName = "numCsfail" class = "numCsfail"/>
			<display:column property="numCsdrop3g" titleKey="NUM_CSDROP3G" sortable="true" sortName = "numCsdrop3g" class = "numCsdrop3g"/>
			<display:column property="numPsfail" titleKey="NUM_PSFAIL" sortable="true" sortName = "numPsfail" class = "numPsfail"/>
			<display:column property="numPsdrop3g" titleKey="NUM_PSDROP3G" sortable="true" sortName = "numPsdrop3g" class = "numPsdrop3g"/>
			<display:column property="times" titleKey="TIMES" sortable="true" sortName = "times"/>	   		    
			</display:table>
	</div>	
</c:if>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
	Calendar.setup({
	    inputField		:	"startDate",	// id of the input field
	    ifFormat		:	"%d/%m/%Y",   	// format of the input field
	    button			:   "chooseSdate",  	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	}); 
</script>	
<script type="text/javascript">
	Calendar.setup({
	    inputField		:	"endDate",	// id of the input field
	    ifFormat		:	"%d/%m/%Y",   	// format of the input field
	    button			:   "chooseEdate",  	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	}); 
</script>	
<script type="text/javascript">


var cssrThreshold = parseFloat('<c:out value="${cssrThreshold}"/>');
var cdrThreshold = parseFloat('<c:out value="${cdrThreshold}"/>');
var numSdcchDropBlockThreshold = parseInt("<c:out value = '${numSdcchDropBlockThreshold}'/>");
var numDropThreshold = parseInt("<c:out value = '${numDropThreshold}'/>");
var pasr3gThreshold = parseFloat("<c:out value = '${pasr3gThreshold}'/>");
var padr3gThreshold = parseFloat("<c:out value = '${padr3gThreshold}'/>");
var cssr3gThreshold = parseFloat("<c:out value = '${cssr3gThreshold}'/>");
var cdr3gThreshold = parseFloat("<c:out value = '${cdr3gThreshold}'/>");
var psDrop3gThreShold = parseInt("<c:out value = '${psDrop3gThreShold}'/>");
var psFailThreshold = parseInt("<c:out value = '${psFailThreshold}'/>");
var csDrop3gThreshold = parseInt("<c:out value = '${csDrop3gThreshold}'/>");
var csFailThreshold = parseInt("<c:out value = '${csFailThreshold}'/>");
var rtpwThreshold = parseFloat("<c:out value = '${rtpwThreshold}'/>");

/* console.log (cssrThreshold + " " + cdrThreshold + " " + numSdcchDropBlockThreshold + " "+numDropThreshold); */

$(document).ready(function() {
	var network = "<c:out value = '${network}'/>";
	$("#cell_box").jqxInput({placeHolder: "Nhập cell", height: 17, width: "100%", minLength: 1, 	
		source: function (query, response) {
			
			if (query.length > 1) {
				
				var dataAdapter = new $.jqx.dataAdapter
				(
					{
						datatype: "json",
						url:"${pageContext.request.contextPath}/ajax/getCellIbc.htm?network=" + network,
					},
					{
						autoBind: true,
						formatData: function (data) {
							
							data.name_container = query;
							return data;
							
						},
						loadComplete: function (data) {
							
							if (data.length > 0) {
								response($.map(data, function (item) {
									return item
								}));
							}
					
						}
					}
				);
				
			}
			
		}
	
	});
	

	
	 $('#tabledata>tbody>tr').each(function() {
		 var cssr = parseFloat($(this).find(".cssr").text());
		 var cdr = parseFloat($(this).find(".cdr").text());
		 var numDrop = parseInt($(this).find(".numDrop2g").text());
		 var numSdcchDropBlock = parseInt($(this).find(".numSdcchDropBlock").text());
		 if (cssr < cssrThreshold && numSdcchDropBlock > numSdcchDropBlockThreshold) {
			$(this).find(".cssr").css({"background-color" : "red"});
		 } 
		 
		 if (cdr > cdrThreshold && numDrop > numDropThreshold) {
			 $(this).find(".cdr").css({"background-color" : "red"});
		 }

	}); 
	 
	 $('#tabledata3g>tbody>tr').each(function() {
		 var cssr3g = parseFloat($(this).find(".cssr3g").text());
		 var cdr3g = parseFloat($(this).find(".cdr3g").text());
		 var pasr3g = parseFloat($(this).find(".pasr3g").text());
		 var padr3g = parseFloat($(this).find(".padr3g").text());
		 var rtpw = parseFloat($(this).find(".rtpw").text());
		 var numCsfail = parseInt($(this).find(".numCsfail").text());
		 var numCsdrop3g = parseInt($(this).find(".numCsdrop3g").text());
		 var numPsfail = parseInt($(this).find(".numPsfail").text());
		 var numPsdrop3g = parseInt($(this).find(".numPsdrop3g").text());
		 
		 if (cssr3g < cssr3gThreshold && numCsfail > csFailThreshold) {
			 $(this).find(".cssr3g").css({"background-color" : "red"}); 
		 }
		
		 if (cdr3g > cdr3gThreshold && numCsdrop3g > csDrop3gThreshold) {
			 $(this).find(".cdr3g").css({"background-color" : "red"}); 
		 }
		 
		 if (pasr3g < pasr3gThreshold && numPsfail > psFailThreshold) {
			 $(this).find(".pasr3g").css({"background-color" : "red"}); 
		 }
		 
		 if (padr3g > padr3gThreshold && numPsdrop3g > psDrop3gThreShold) {
			 $(this).find(".padr3g").css({"background-color" : "red"}); 
		 }
		 
		 if (rtpw > rtpwThreshold) {
			 $(this).find(".rtpw").css({"background-color" : "red"});
		 }
	}); 
	
});
	
</script>

<style>

	#cell_box {max-height: max-content; }

</style>

