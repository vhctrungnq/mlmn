<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title>IPBB DAY REPORT</title>
<content tag="heading">IPBB ${title} REPORT</content>    
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
    
<ul class="ui-tabs-nav">
   <li class="${hr }"><a href="${pageContext.request.contextPath}/report/core/ip-backbone-sla/hr.htm"><span>Báo cáo giờ</span></a></li>
   <li class="${dy }"><a href="${pageContext.request.contextPath}/report/core/ip-backbone-sla/dy.htm"><span>Báo cáo ngày</span></a></li>
</ul>
<br>
 <form method="get" action="${level}.htm">
		<table style="width:100%;" class="form">
			<tr>
				<td align="left">
					Direction 
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
	             	Link
			  		<input name="link" id="link" value="${link}" size="10"  style="width:250px">
			        &nbsp;&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="16" maxlength="20">
			         <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	                &nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="16" maxlength="20">
	                 <img alt="calendar" title="Click to choose the start date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit"value="View Report"/>
	            </td>
	        </tr>		
		</table>
	</form>
<div  id="doublescroll">
		<display:table name="${ipbbSlaList}" id="ipbbSlaList" requestURI="" pagesize="100" class="simple2" export="true">
		    <c:choose> 
			    <c:when test="${level=='dy'}">
				   <display:column property="day" titleKey="Day" format="{0,date,dd/MM/yyyy}" sortable="true" headerClass="master-header-1"/>
				   <display:column property="direction" titleKey="Direction" headerClass="master-header-1" style = "width:150px" sortable="true"/>
				   <display:column property="link" titleKey="Link" headerClass="hide" class="hide" style = "width:200px"/>
				   <display:column titleKey="Link" media="html" sortable="true" headerClass="master-header-1" style = "width:200px">
				   	 	<a href="${pageContext.request.contextPath}/report/core/ip-backbone-sla/hr.htm?direction=${ipbbSlaList.direction}&link=${ipbbSlaList.link}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${ipbbSlaList.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${ipbbSlaList.day}"/>">${ipbbSlaList.link}</a>
				   </display:column>
			    </c:when>
			    <c:when test="${level=='hr'}">
			       <display:column property="day" titleKey="Datetime" format="{0,date,dd/MM/yyyy HH:mm:ss}" sortable="true" headerClass="master-header-1"/>
				   <display:column  property="direction" titleKey="Direction" sortable="true" headerClass="master-header-1" style = "width:150px"/>
				   <display:column  property="link" titleKey="Link" sortable="true" headerClass="master-header-1" style = "width:200px"/>
			    </c:when>
			</c:choose>
		    
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="delayMax"  titleKey="Delay Max(ms)"  sortable="true" headerClass="master-header-2" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="delayAvg"  titleKey="Delay Average(ms)"  sortable="true"  headerClass="master-header-2" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="jitterAvg"  titleKey="Jitter Average(ms)"  sortable="true"  headerClass="master-header-2" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="packageLoss"  titleKey="Packets Loss"  sortable="true"  headerClass="master-header-4" />
			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="downtime"  titleKey="Downtime(%)"  sortable="true"  headerClass="master-header-4"/>
 			<display:column class="rightColumnMana"   decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" property ="coverage"  titleKey="Coverage(%)"  sortable="true"  headerClass="master-header-4"/>
	</display:table>
	</div>
	 
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
 <c:choose> 
 	<c:when test="${level=='hr'}">
 	 
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
			    singleClick		:   false					//  double-click mode
			});
		</script>
    </c:when>
    <c:when test="${level=='dy'}">
       <script type="text/javascript">
			Calendar.setup({
			    inputField		:	"startDate",	// id of the input field
			    ifFormat		:	"%d/%m/%Y",   	// format of the input field
			    button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
			    showsTime		:	true,
			    singleClick		:   false					// double-click mode
			});
			
			Calendar.setup({
			    inputField		:	"endDate",	// id of the input field
			    ifFormat		:	"%d/%m/%Y",   	// format of the input field
			    button			:   "chooseEndDate",   	// trigger for the calendar (button ID)
			    showsTime		:	true,
			    singleClick		:   false					//  double-click mode
			});
		</script>
    </c:when>
</c:choose>
