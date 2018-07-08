<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>


	<title>IP BACK BONE</title>
	<content tag="heading"> ${title} HOURLY REPORT</content>
<ul class="ui-tabs-nav">
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/ip-backbone-fw/hr.htm"><span>Báo cáo giờ</span></a></li>
 	<li class=""><a href="${pageContext.request.contextPath}/report/core/ip-backbone-fw/dy.htm"><span>Báo cáo ngày</span></a></li>
</ul>
<br/>
	<form method="get" action="hr.htm">
		<table style="width:100%;" class="form">
			<tr>
				<td align="left">
	               	ROUTE 
			  		<select name="routeid" id="routeid" onchange="xl()">
			  			<option value="">Tất cả</option>
			              <c:forEach var="items" items="${routeList}">
				              <c:choose>
				                <c:when test="${items.routeid == routeid}">
				                    <option value="${items.routeid}" selected="selected">${items.routeid}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.routeid}">${items.routeid}</option>
				                </c:otherwise>
				              </c:choose>
						    </c:forEach>
			        </select>
	               &nbsp;&nbsp;SCP 
			  		<select name="scp" id="scp" onchange="xl()">
			  			<option value="">Tất cả</option>
			              <c:forEach var="items" items="${scpList}">
				              <c:choose>
				                <c:when test="${items.dev == scp}">
				                    <option value="${items.dev}" selected="selected">${items.dev}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.dev}">${items.dev}</option>
				                </c:otherwise>
				              </c:choose>
						    </c:forEach>
			        </select>
			        &nbsp;&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="16" maxlength="20">
			        <img alt="calendar" titleKey="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	                &nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="16" maxlength="20">
	                <img alt="calendar" titleKey="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit"value="View Report"/>
	            </td>
	        </tr>		
		</table>
	</form>
	<br/>
<div  id="doublescroll">
		<display:table name="${hrIpbbFw}"  id = "hrIpbbFw" requestURI="" pagesize="100" class="simple2">
	    		<display:column property="distanceTime" titleKey="Time" sortable="true" class = "TIME"/>
				<display:column property ="route"  titleKey="route"  sortable="true" class = "ROUTE"/>
				<display:column property ="scp"  titleKey="scp"  sortable="true" class = "SCP"/>
				<display:column property ="cpuUtil"  titleKey="CPU Utilization (%)"  sortable="true" class = "CPU_UTIL"/>
				<display:column property ="memmoryUtil"  titleKey="Memory Utilization (%)"  sortable="true" class = "MEMORY_UTIL"/>
				<display:column property ="totalSession"  titleKey="Session Number"  sortable="true" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" class = "TOTAL_SESSION"/>
		</display:table>
</div>	

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
Calendar.setup({
    inputField		:	"startDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
    button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"endDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
    button			:   "chooseEndDate",   	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
});
</script>

<script language = "Javascript">
function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
} 

function focusIt()
{
  var mytext = document.getElementById("startDate");
  mytext.focus();
}

onload = focusIt;
</script>	

	<script type="text/javascript">
    function DoubleScroll(element) {
        var scrollbar= document.createElement('div');
        scrollbar.appendChild(document.createElement('div'));
        scrollbar.style.overflow= 'auto';
        scrollbar.style.overflowY= 'hidden';
        scrollbar.firstChild.style.width= element.scrollWidth+'px';
        scrollbar.firstChild.style.paddingTop= '1px';
        scrollbar.firstChild.appendChild(document.createTextNode('\xA0'));
        scrollbar.onscroll= function() {
            element.scrollLeft= scrollbar.scrollLeft;
        };
        element.onscroll= function() {
            scrollbar.scrollLeft= element.scrollLeft;
        };
        element.parentNode.insertBefore(scrollbar, element);
    }

    DoubleScroll(document.getElementById('doublescroll'));
</script>