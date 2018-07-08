<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<ul class="ui-tabs-nav">
<c:choose>
	<c:when test="${function == 'hr'}">
		<title><fmt:message key="sidebar.bsc2g.signalUtil.hr"/></title>
		<content tag="heading"><fmt:message key="sidebar.bsc2g.signalUtil.hr"/></content>

		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/bsc/ericsson/signal-util/hr.htm"><span><fmt:message key="title.tabControls.mucGio"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc/ericsson/signal-util/dy.htm"><span><fmt:message key="title.tabControls.mucNgay"/></span></a></li>
	</c:when>
	<c:when test="${function == 'dy'}">
		<title><fmt:message key="sidebar.bsc2g.signalUtil.dy"/></title>
		<content tag="heading"><fmt:message key="sidebar.bsc2g.signalUtil.dy"/></content>

		<li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc/ericsson/signal-util/hr.htm"><span><fmt:message key="title.tabControls.mucGio"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/bsc/ericsson/signal-util/dy.htm"><span><fmt:message key="title.tabControls.mucNgay"/></span></a></li>
	</c:when>
 	<c:otherwise></c:otherwise>
</c:choose>
</ul>
<div class="ui-tabs-panel">
<form:form id="filterController" method="post" action="${function}.htm">
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		
		<c:choose>
		<c:when test="${function == 'hr'}">
			<tr>
				<td align="left">
					<fmt:message key="ericsson.bsc"/>&nbsp;
					<select id="bsc" name="bsc" style="width: 8%">
						<option value="">--Select BSC--</option>
		 				<c:forEach var="items" items="${bscList}">
		              	<c:choose>
		                <c:when test="${items.bscid == bscCBB}">
		                    <option value="${items.bscid}" selected="selected">${items.bscid}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.bscid}">${items.bscid}</option>
		                </c:otherwise>
		              	</c:choose>
				    	</c:forEach>
		          	</select>
					<fmt:message key="ericsson.tu"/>
					<select id="shour" name="shour" style="width: 6%">
		 				<c:forEach var="items" items="${hourList}">
		              	<c:choose>
		                <c:when test="${items == shourCBB}">
		                    <option value="${items}" selected="selected">${items}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items}">${items}</option>
		                </c:otherwise>
		              	</c:choose>
				    	</c:forEach>
		          	</select>
		          	<fmt:message key="ericsson.gio"/>&nbsp;
					<input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">&nbsp;&nbsp;
		            <fmt:message key="ericsson.toi"/>
		            <select id="ehour" name="ehour" style="width: 6%">
		 				<c:forEach var="items" items="${hourList}">
		              	<c:choose>
		                <c:when test="${items == ehourCBB}">
		                    <option value="${items}" selected="selected">${items}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items}">${items}</option>
		                </c:otherwise>
		              	</c:choose>
				    	</c:forEach>
		          	</select>
		          	<fmt:message key="ericsson.gio"/>&nbsp;
		            <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">&nbsp;&nbsp;
					<input class="button" type="submit" name="filter" value="<fmt:message key="global.form.viewReport"/>" />
				</td>	
			</tr>
			<tr>
				<td style="padding-top: 10px;">
					<div style="width:100%;overflow:auto; ">
						<display:table name="${hrBscSignalUtilList}" class="simple2" id="item" requestURI="" pagesize="100" sort="external" defaultsort="1" export="true" >
							<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="dyBscCpload.ngay" sortable="true" sortName="DAY"/>
							<display:column property="hour" class="rightColumnMana" titleKey="dyBscCpload.hour" sortable="true" sortName="HOUR"/>
							<display:column property="bscid" titleKey="dyBscCpload.bscid" sortable="true" sortName="BSCID"/>	
							
							<display:column property="utilRxHsl" class="rightColumnMana BSC_SIGNAL_UTIL" titleKey="dyBscSignalUtil.utilRxHsl" sortable="true" sortName="UTIL_RX_HSL"/>
							<display:column property="utilRxNb" class="rightColumnMana BSC_SIGNAL_UTIL" titleKey="dyBscSignalUtil.utilRxNb" sortable="true" sortName="UTIL_RX_NB"/>
							<display:column property="utilTxHsl" class="rightColumnMana BSC_SIGNAL_UTIL" titleKey="dyBscSignalUtil.utilTxHsl" sortable="true" sortName="UTIL_TX_HSL"/>
							<display:column property="utilTxNb" class="rightColumnMana BSC_SIGNAL_UTIL" titleKey="dyBscSignalUtil.utilTxNb" sortable="true" sortName="UTIL_TX_NB"/>
			    			
							<display:setProperty name="export.csv.include_header" value="true" />
							<display:setProperty name="export.excel.include_header" value="true" />
							<display:setProperty name="export.xml.include_header" value="true" />
							<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
							<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
							<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
								
						</display:table>
					</div>
				</td>
			</tr>
		</c:when>
		<c:when test="${function == 'dy'}">
			<tr>
				<td align="left">
					<fmt:message key="ericsson.bsc"/>&nbsp;
					<select id="bsc" name="bsc" style="width: 8%">
						<option value="">--Select BSC--</option>
		 				<c:forEach var="items" items="${bscList}">
		              	<c:choose>
		                <c:when test="${items.bscid == bscCBB}">
		                    <option value="${items.bscid}" selected="selected">${items.bscid}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.bscid}">${items.bscid}</option>
		                </c:otherwise>
		              	</c:choose>
				    	</c:forEach>
		          	</select>
					<fmt:message key="ericsson.tuNgay"/>
					<input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">&nbsp;&nbsp;
		            <fmt:message key="ericsson.toiNgay"/>
		            <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">&nbsp;&nbsp;
					<input class="button" type="submit" name="filter" value="<fmt:message key="global.form.viewReport"/>" />
				</td>	
			</tr>
			<tr>
				<td style="padding-top: 10px;">
					<div style="width:100%;overflow:auto; ">
						<display:table name="${dyBscSignalUtilList}" class="simple2" id="item" requestURI="" pagesize="100" sort="external" defaultsort="1" export="true" >
							<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="dyBscCpload.ngay" sortable="true" sortName="DAY"/>
							
							<display:column class="link" titleKey="dyBscCpload.bscid" media="html" sortable="true" sortName="BSCID">
						   	 	<a href="${pageContext.request.contextPath}/report/radio/bsc/ericsson/signal-util/hr.htm?bsc=${item.bscid}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${item.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${item.day}"/>">${item.bscid}</a>
						    </display:column>
							<display:column property="bscid" titleKey="dyBscCpload.bscid" headerClass="hide" class="hide" />	
							<display:column property="utilRxHsl" class="rightColumnMana BSC_SIGNAL_UTIL" titleKey="dyBscSignalUtil.utilRxHsl" sortable="true" sortName="UTIL_RX_HSL"/>
							<display:column property="utilRxNb" class="rightColumnMana BSC_SIGNAL_UTIL" titleKey="dyBscSignalUtil.utilRxNb" sortable="true" sortName="UTIL_RX_NB"/>
							<display:column property="utilTxHsl" class="rightColumnMana BSC_SIGNAL_UTIL" titleKey="dyBscSignalUtil.utilTxHsl" sortable="true" sortName="UTIL_TX_HSL"/>
							<display:column property="utilTxNb" class="rightColumnMana BSC_SIGNAL_UTIL" titleKey="dyBscSignalUtil.utilTxNb" sortable="true" sortName="UTIL_TX_NB"/>
			    			
							<display:setProperty name="export.csv.include_header" value="true" />
							<display:setProperty name="export.excel.include_header" value="true" />
							<display:setProperty name="export.xml.include_header" value="true" />
							<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
							<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
							<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
								
						</display:table>
					</div>
				</td>
			</tr>
			${html}
		</c:when>
		<c:otherwise></c:otherwise>
		</c:choose>
</table>
</form:form>
</div>

<script type="text/javascript">
${highlight}

$(function() {
	$( "#startDate" ).datepicker({
		dateFormat: "dd/mm/yy",
		showOn: "button",
		buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
		buttonImageOnly: true
	});
	$( "#endDate" ).datepicker({
		dateFormat: "dd/mm/yy",
		showOn: "button",
		buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
		buttonImageOnly: true
	});
	
});

function focusIt()
{
  var mytext = document.getElementById("startDate");
  mytext.focus();
}

onload = focusIt;

</script>