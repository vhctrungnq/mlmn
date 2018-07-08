<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>


	<title>IP BACK BONE</title>
	<content tag="heading"> ${title} HOURLY REPORT</content>
	<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/ip-backbone/hr_delay_internet.htm?link=${link}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/core/ip-backbone/dy_delay_internet.htm?direction=${direction}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
</ul>
<br/>
	<form method="get" action="hr_delay_internet.htm">
		<table style="width:100%;" class="form">
			<tr>
				<td align="left">
	               	DIRECTION 
			  		<select name="direction" id="direction" onchange="xl()">
			  			<option value="">Tất cả</option>
			              <c:forEach var="items" items="${directionList}">
				              <c:choose>
				                <c:when test="${items.direction == direction}">
				                    <option value="${items.direction}" selected="selected">${items.direction}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.direction}">${items.direction}</option>
				                </c:otherwise>
				              </c:choose>
						    </c:forEach>
			        </select>
	                &nbsp;&nbsp;LINK
			  		<select name="link" id="link" onchange="xl()">
			  			<option value="">Tất cả</option>
			              <c:forEach var="items" items="${linkList}">
				              <c:choose>
				                <c:when test="${items.link == link}">
				                    <option value="${items.link}" selected="selected">${items.link}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.link}">${items.link}</option>
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
		<display:table name="${vRpHrIpbbInternet}" id="vRpHrIpbbInternet" requestURI="" pagesize="100" class="simple2" export="true">
	    		<display:column property="time" format="{0,date,dd/MM/yyyy HH:mm:ss}" titleKey="Stime"/>
	    		<display:column property="direction" titleKey="DIRECTION"/>
	    		<display:column property="link" titleKey="LINK"/>
	    		<display:column property="refValue" titleKey="refValue"/>
	    		<display:column property="delayLink1Ms" titleKey="Delay Link1 Ms"/>
	    		<display:column property="delayLink2Ms" titleKey="Delay Link2 Ms"/>
	    		
	    		 <display:setProperty name="export.csv.filename" value="IPBBInternetList.csv"/>
			     <display:setProperty name="export.excel.filename" value="IPBBInternetList.xls"/>
			     <display:setProperty name="export.xml.filename" value="IPBBInternetList.xml"/>
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