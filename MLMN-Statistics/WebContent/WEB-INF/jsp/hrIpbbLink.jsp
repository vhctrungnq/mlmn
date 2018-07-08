<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>


	<title>IP BACK BONE</title>
	<content tag="heading">IP BACKBONE DATA ${link} HOURLY REPORT</content>
	<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/ip-backbone/hr-link.htm?link=${link}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/core/ip-backbone/dy-link.htm?direction=${direction}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
</ul>
<br/>
	<form method="get" action="hr-link.htm">
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
		<display:table name="${vhrlink}" id="user" requestURI="" pagesize="100" class="simple2" export="true">
	    		<display:column property="time" format="{0,date,dd/MM/yyyy hh:mm:ss}" titleKey="Stime"/>
	    		<display:column property="direction" titleKey="DIRECTION"/>
	    		<display:column property="link" titleKey="LINK"/>
	    		<display:column property="bandWidth" titleKey="BandWidth"/>
	    		<display:column property="inKbitSecond" titleKey="Kbit/second (Peak)"/>
	    		<display:column property="inUtilization" titleKey="Utilization (%)" class="IN_UTILIZATION"/>
	    		<display:column property="outKbitSecond" titleKey="Kbit/second (Peak)"/>
	    		<display:column property="outUtilization" titleKey="Utilization (%)" class="OUT_UTILIZATION"/>
	    		<display:column property="delay" titleKey="Delay (ms)"/>
	    		<display:column property="jitterSdp" titleKey="jitter sdp (ms)"/>
	    		<display:column property="jitterDsp" titleKey="jitter dsp (ms)"/>
	    		<display:column property="jitterSdn" titleKey="jitter sdn (ms)"/>
	    		<display:column property="jitterDsn" titleKey="jitter dsn (ms)"/>
	    		<display:column property="lossSd" titleKey="loss SD"/>
	    		<display:column property="lossDs" titleKey="loss DS"/>
	    		<display:column property="mos" titleKey="MOS"/>
		</display:table>
</div>	

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<script>
    $("#user").change(function () {
      window.location = '${pageContext.request.contextPath}/log/checkjob/detail.htm?user=' + $("#user").val();
      
      });  
    ${highlight}
</script>  
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

<script type="text/javascript">
	$(document).ready(function(){
		var trs='<tr>';
	    trs=trs +'<th colspan="4" style="color:blue;"></th>';
	    trs=trs +'<th colspan="2" style="color:blue;">TRAFFIC IN</th>';
	    trs=trs +'<th colspan="2" style="color:blue;">TRAFFIC OUT</th>';
	    trs=trs +'<th colspan="8" style="color:blue;">SLA PARAMETERS</th>';

	    trs=trs +'<tr><th style="color:blue;width:130px">Stime</th>';
	    trs=trs +'<th style="color:blue;width:130px" >Direction</th>';
	    trs=trs +'<th style="color:blue;width:160px">Link</th>';
	    trs=trs +'<th style="color:blue;">BandWidth</th>';
	    trs=trs +'<th style="color:blue;">kbit/second (Peak)</th>';
	    trs=trs +'<th style="color:blue;" >Utilization (%)</th>';
	    trs=trs +'<th style="color:blue;">kbit/second (Peak)</th>';
	    trs=trs +'<th style="color:blue;">Utilization (%)</th>';
	    trs=trs +'<th style="color:blue;">Delay (ms)</th>';
	    trs=trs +'<th style="color:blue;">Jitter sdp (ms)</th>';
	    trs=trs +'<th style="color:blue;">Jitter dsp (ms)</th>';
	    trs=trs +'<th style="color:blue;">Jitter sdn (ms)</th>';
	    trs=trs +'<th style="color:blue;">Jitter dsn (ms)</th>';
	    trs=trs +'<th style="color:blue;">Loss SD</th>';
	    trs=trs +'<th style="color:blue;">Loss DS</th>';
	    trs=trs +'<th style="color:blue;">MOS</th>';
	    
	$('#user thead').html(trs);
	});
</script>