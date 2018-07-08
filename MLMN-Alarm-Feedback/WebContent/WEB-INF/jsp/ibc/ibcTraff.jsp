<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:if test = "${network == '2g' }" >    
	<title>TRAFFIC 2G KPI REPORT</title>
	<h1>TRAFFIC 2G KPI REPORT</h1>
	<ul class="ui-tabs-nav">
		<li class=ui-tabs-selected><a href="${pageContext.request.contextPath}/report/ibc-traffic/hrList.htm?network=2g"><span>Báo cáo 2g</span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/report/ibc-traffic/hrList.htm?network=3g"><span>Báo cáo 3g</span></a></li>
	</ul>
</c:if>
<c:if test = "${network == '3g' }" >    
	<title>TRAFFIC 3G KPI REPORT</title>
	<h1>TRAFFIC 2G KPI REPORT </h1>
	<ul class="ui-tabs-nav">
		<li class=><a href="${pageContext.request.contextPath}/report/ibc-traffic/hrList.htm?network=2g"><span>Báo cáo 2g</span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/ibc-traffic/hrList.htm?network=3g"><span>Báo cáo 3g</span></a></li>
	</ul>
</c:if>
<div class="ui-tabs-panel">
	
		<table style= "border-spacing: 0; border-width: 0; padding: 0; border-width: 0;" class="form">
		<form:form method="get" action="hrList.htm" >
		<input type="hidden" id="network" name="network" value = "${network }">
			<tr>
				<td>CELL</td>
				<td style = "max-width:150px"> <input type="text" id="cell_box" value="${cellid}" name = "cellid"/></td>			
				<td style = "padding-left:15px">Từ ngày</td>
            	<td style = "max-width:120px; padding-left:5px">
					<input type="text" id=startDate name="startDate" value="${startDate}" style = "max-width:90px"/>&nbsp;
         				<img alt="calendar" title="Click to choose the sdate " id="chooseSdate" style="cursor: pointer; " src="${pageContext.request.contextPath}/images/calendar.png"/>
    			</td>
    			<td style = "padding-left:25px">Tới ngày</td>
            	<td style = "max-width:120px; padding-left:5px">
					<input type="text" id=endDate name="endDate" value="${endDate}" style = "max-width:90px"/>&nbsp;
         				<img alt="calendar" title="Click to choose the edate " id="choosEdate" style="cursor: pointer; " src="${pageContext.request.contextPath}/images/calendar.png"/>
    			</td>	
	             <td style = "padding-left:25px"> <input type="submit" class="button" name="submit" value="View Report"/> </td>
	        </tr>
	        </form:form>		
		</table>
	<br/>
<c:if test = "${network == '2g' }" > 
	<div  style="overflow: auto; width:80%" class="tableStandar" >	
	<display:table name="${dataList}" id="tabledata" requestURI="" pagesize="100" class="simple2" export="true" sort="external" defaultsort="1">
		<display:column title="No." >  <c:out value="${tabledata_rowNum}"/> </display:column>
		<display:column property="day" format="{0,date,dd/MM/yyyy}" title="Date" headerClass="master-header-1" sortable="true" sortName = "day"/>
		<display:column property="siteid"  titleKey="Site" sortable="true" sortName = "siteid" />
		<display:column property="cellid"  titleKey="Cell" sortable="true" sortName = "cellid" />
		<display:column property="traffic" titleKey="TCH Traffic" sortable="true" sortName = "tch" class = "traffic2g"/>		
		<display:column property="lastWeekTraffic" titleKey="Last week tch traffic" sortable="true" sortName = "lastWeekTraffic" class = "lastWeekTraffic2g" />	
		<display:column property="isBadCell" titleKey="Is bad cell" sortable="true" sortName = "isBadCell" class = "isBadCell"/>	   		    
	</display:table>	
	</div>
</c:if>	
<c:if test = "${network == '3g' }" >
	<div  style="overflow: auto;" class="tableStandar" >
			<display:table name="${data3gList}" id="tabledata3g" requestURI="" pagesize="100" class="simple2" export="true" sort="external" defaultsort="1">
			<display:column title="No." >  <c:out value="${tabledata3g_rowNum}"/> </display:column>
			<display:column property="day" format="{0,date,dd/MM/yyyy}" title="Date" headerClass="master-header-1" sortable="true" sortName = "day"/>
			<display:column property="siteid"  titleKey="Site" sortable="true" sortName = "siteid" />
			<display:column property="cellid"  titleKey="Cell" sortable="true" sortName = "cellid" />
			<display:column property="traffic3g" titleKey="Speech Traffic (ERL)" sortable="true" sortName = "traffic3g" class = "traffic3g"/>		
			<display:column property="lastWeekTraffic3g" titleKey="Last week speech traffic" sortable="true" sortName = "lastWeekTraffic3g" class = "lastWeekTraffic3g"/>	
			<display:column property="data3g" titleKey="DATA 3g (GB)" sortable="true" sortName = "data3g" class = "data3g"/>		
			<display:column property="lastWeekData3g" titleKey="Last week data 3g (gb)" sortable="lastWeekData3g" sortName = "lastWeekData3g" class = "lastWeekData3g"/>	
			<display:column property="isBadCell" titleKey="Is bad cell" sortable="true" sortName = "isBadCell3g" class = "isBadCell3g"/>	   		    
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

var trafficThreshold = parseFloat('<c:out value="${trafficThreshold}"/>');
var data3gThreshold = parseFloat('<c:out value="${data3gThreshold}"/>');

$(document).ready(function() {
	
	 $('#tabledata>tbody>tr').each(function() {
		var traffic2g = parseFloat($(this).find(".traffic2g").text());
		var lastWeekTraffic = parseFloat($(this).find(".lastWeekTraffic2g").text());
		if (traffic2g < trafficThreshold * lastWeekTraffic) {
			$(this).find(".traffic2g").css({"background-color" : "red"});
		}
	}); 
	 
	 $('#tabledata3g>tbody>tr').each(function() {
		 var traffic3g = parseFloat($(this).find(".traffic3g").text());
		 var lastWeekTraffic3g = parseFloat($(this).find(".lastWeekTraffic3g").text());
		 var data3g = parseFloat($(this).find(".data3g").text());
		 var lastWeekData3g = parseFloat($(this).find(".lastWeekData3g").text());
		 
		 if (traffic3g < trafficThreshold * lastWeekTraffic3g) {
			$(this).find(".traffic3g").css({"background-color" : "red"});
		 }
		 
		 if (data3g < data3gThreshold * lastWeekData3g) {
				$(this).find(".data3g").css({"background-color" : "red"});
		}
	}); 
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
		
});

</script>

<style>
	#cell_box {max-height: max-content; }
</style>