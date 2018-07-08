<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<style type="text/css">    

#doublescroll { overflow: auto; overflow-y: hidden; }  
 #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
 
 .wid35{
		width: 35%;
	}
	.wid15{
		width: 15%;
	}
	.wid3{
		width: 3%;
	}
	.wid5{
		width: 5%;
	}
	.wid10{
		width: 10%;
</style>

<title>Báo cáo BW link truyền dẫn tùy chọn</title>
<content tag="heading">Báo cáo BW link truyền dẫn tùy chọn</content>

<form method="get" action="option-link.htm">
	<table style="width:100%;" class="form">
		<tr>
			<td align="left">
               	Direction 
		  		<select name="direction" id="direction" class="wid15">
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
               &nbsp;&nbsp;Link 
		  		<select name="link" id="link" class="wid35">
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
		        &nbsp;&nbsp;Từ ngày <input value="${startTime}" name="startTime" id="startTime" class="wid10">
		        <img alt="calendar" titleKey="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
                &nbsp;&nbsp;đến ngày <input value="${endTime}" name="endTime" id="endTime"  class="wid10">
                <img alt="calendar" titleKey="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="Tìm kiếm"/>
            </td>
        </tr>		
	</table>
</form>
<div  id="doublescroll">
	<display:table name="${optionList}" id="optionList" requestURI="" pagesize="100" class="simple2" export="true">
	
			<display:column title="STT" class="textCenter"><c:out value="${optionList_rowNum}" /></display:column>
		    <display:column property="sdatetime" titleKey="starttime" headerClass="master-header-1"/>
		    <display:column property="edatetime" titleKey="endtime" class="margin" headerClass="master-header-1 margin"/>
		   
			<display:column property="direction" titleKey="direction" sortName="direction" sortable="true" headerClass="master-header-2"/>
			<display:column property="link" titleKey="link" sortName="link" sortable="true" headerClass="master-header-2"/>
    		<display:column property="bandWidth" titleKey="bandWidth" sortName="bandWidth" sortable="true" headerClass="master-header-2 margin"  class="margin"/>
    		<display:column property="inKbitSecond" titleKey="inKbitSecond" sortName="inKbitSecond" sortable="true" headerClass="master-header-3"/>
    		<display:column property="inUtilization" titleKey="inUtilization" sortName="inUtilization" sortable="true" headerClass="master-header-3 margin" class="margin OUT_UTILIZATION"/>
    		<display:column property="outKbitSecond" titleKey="outKbitSecond" sortName="outKbitSecond" sortable="true" headerClass="master-header-4"/>
    		<display:column property="outUtilization" titleKey="outUtilization" sortName="outUtilization" sortable="true" headerClass="master-header-4 margin" class="margin IN_UTILIZATION"/>
    		 
    		<display:column property="delayMax" titleKey="delayMax" sortName="delayMax" sortable="true" headerClass="master-header-4"/>
    		<display:column property="delayAvg" titleKey="delayAvg" sortName="delayAvg" sortable="true" headerClass="master-header-4"/>
    		<display:column property="jitterAvg" titleKey="jitterAvg" sortName="jitterAvg" sortable="true" headerClass="master-header-4"/>
    		<display:column property="mos" titleKey="mos" sortName="mos" sortable="true" headerClass="master-header-4"/>
    		<display:column property="packetLoss" titleKey="packetLoss" sortName="packetLoss" sortable="true" headerClass="master-header-4"/>
    		<display:column property="lossConnectionDur" titleKey="lossConnectionDur" sortName="lossConnectionDur" sortable="true" headerClass="master-header-4"/>
  
	</display:table>
</div>	

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
 
<script type="text/javascript">
${highlight}

Calendar.setup({
    inputField		:	"startTime",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
    button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"endTime",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
    button			:   "chooseEndDate",   	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
});

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