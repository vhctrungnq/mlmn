<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<ul class="ui-tabs-nav">
<c:choose>
	<c:when test="${function == 'ericsson-load-2g'}">
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/alarm/giam-sat/ericsson-load-2g.htm"><span><fmt:message key="Tải 2G Ericsson" /></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/alarm/giam-sat-tai/ericsson-load-3g.htm"><span><fmt:message key="Tải 3G Ericsson" /></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/alarm/giam-sat-tai/nokia-load.htm"><span><fmt:message key="Tải Nokiasiemens" /></span></a></li>
		
	</c:when>
 	<c:when test="${function == 'ericsson-load-3g'}">
		<li class=""><a href="${pageContext.request.contextPath}/alarm/giam-sat-tai/ericsson-load-2g.htm"><span><fmt:message key="Tải 2G Ericsson" /></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/alarm/giam-sat-tai/ericsson-load-3g.htm"><span><fmt:message key="Tải 3G Ericsson" /></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/alarm/giam-sat-tai/nokia-load.htm"><span><fmt:message key="Tải Nokiasiemens" /></span></a></li>
		
	</c:when>
	<c:when test="${function == 'nokia-load'}">
		<li class=""><a href="${pageContext.request.contextPath}/alarm/giam-sat-tai/ericsson-load-2g.htm"><span><fmt:message key="Tải 2G Ericsson" /></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/alarm/giam-sat-tai/ericsson-load-3g.htm"><span><fmt:message key="Tải 3G Ericsson" /></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/alarm/giam-sat-tai/nokia-load.htm"><span><fmt:message key="Tải Nokiasiemens" /></span></a></li>
		
	</c:when>
 	<c:otherwise></c:otherwise>
</c:choose>
</ul>
<title>GIÁM SÁT TẢI TRỰC TUYẾN</title>
<content tag="heading">GIÁM SÁT TẢI TRỰC TUYẾN </content>
</br>
<form method="post" action="${function }.htm">
<table border="0"  cellspacing="0" cellpadding="0" class="form" style="width:70%">
		<tr> 
			 <td>NE</td>
			 <td><select name="neid" id="neid" >
	         <option value="">--Tất cả--</option>
        				<c:forEach var="items" items="${bscList}">
			              <c:choose>
			                <c:when test="${items.bscid == neid}">
			                    <option value="${items.bscid}" selected="selected">${items.bscid}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${items.bscid}">${items.bscid}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
        			</select>
	        </td> 
	        <td>${objectname}</td>
	        <td style="width:230px;"><input type="text" id="cardType" name="cardType" value="${cardType}" class="wid80" maxlength="20" /></td>
	        <td class="wid1 mwid50"><fmt:message key="baoCaoTongHop.tuNgay" /></td>
	        <td class="wid20">
				<input type="text" id="startDate" name="startDate" value="${startDate}" class="wid70" maxlength="20" />
      						<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png" />
			</td>
			<td class="wid1 mwid40"><fmt:message key="baoCaoTongHop.denNgay" /></td>
			<td class="wid20">
				<input type="text" id="endDate" name="endDate" value="${endDate}" class="wid70" maxlength="20" />
      			 <img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png" />
			</td>
			<td><input class="button" type="submit" name="filter" value="<fmt:message key="global.form.timkiem"/>" /> </td>
		</tr>
</table>
</form> 
 <br />
<div  id="doublescroll">
<c:choose>
	<c:when test="${function == 'ericsson-load-2g'}">
		<display:table name="${vRpHrEBscLoadList}" id="vRpHrEBscLoadList" requestURI="" pagesize="100" class="simple2" export="true">
	    		<display:column property="datetime" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="DateTime" sortable="true" />
	    		<display:column property="vendor" titleKey="Vendor" sortable="true" />
	    		<display:column property="network" titleKey="Network" sortable="true" />
	    		<display:column property="ne" titleKey="Ne" sortable="true" />
	    		<display:column property="int2g" titleKey="Int" sortable="true"  class="rightColumnMana"  />
	    		<display:column property="pload" titleKey="PLoad (%)" class="PLOAD rightColumnMana" sortable="true" /> 
		</display:table>
	</c:when>
 	<c:when test="${function == 'ericsson-load-3g'}">
		<display:table name="${vRpHrEBscLoad3gList}" id="vRpHrEBscLoad3gList" requestURI="" pagesize="100" class="simple2" export="true">
	    		<display:column property="datetime" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="DateTime" sortable="true" />
	    		<display:column property="vendor" titleKey="Vendor" sortable="true" />
	    		<display:column property="network" titleKey="Network" sortable="true" />
	    		<display:column property="ne" titleKey="Ne" sortable="true" />
	    		<display:column property="object" titleKey="Object" sortable="true" />
	    		<display:column property="counter" titleKey="Counter" sortable="true" />
	    		<display:column property="value" titleKey="Load (%)" class="LOAD rightColumnMana" sortable="true" /> 
		</display:table>
	</c:when>
	<c:when test="${function == 'nokia-load'}">
		<display:table name="${vRpHrNloadList}" id="vRpHrNloadList" requestURI="" pagesize="100" class="simple2" export="true">
	    		<display:column property="datetime" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="DateTime" sortable="true" />
	    		<display:column property="vendor" titleKey="Vendor" sortable="true" />
	    		<display:column property="network" titleKey="Network" sortable="true" />
	    		<display:column property="ne" titleKey="Ne" sortable="true" />
	    		<display:column property="cardType" titleKey="CardType" sortable="true" />
	    		<display:column property="timeUsageAllowed" titleKey="Time Usage Allowed" sortable="true" />
	    		<display:column property="loadAllowed" titleKey="Load Allowed" sortable="true" />
	    		<display:column property="loadPercent" titleKey="Load (%)" class="LOAD rightColumnMana" sortable="true"   /> 
		</display:table>
	</c:when>
 	<c:otherwise></c:otherwise>
</c:choose>
		
</div>	
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<script type="text/javascript">
Calendar.setup({
    inputField		:	"startDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
    button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"endDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
    button			:   "chooseEndDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});
var autoRefresh = $('#autoRefresh').val();	
	setTimeout(function(){
		$('#filterController').submit();
	}, 
	autoRefresh * 1000);
</script>