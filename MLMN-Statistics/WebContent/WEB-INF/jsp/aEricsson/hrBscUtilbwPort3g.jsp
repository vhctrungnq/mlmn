<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>RNC UTILITY BW PORT HOURLY REPORT</title>
<content tag="heading">RNC UTILITY BW PORT HOURLY REPORT</content>

<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/rnc/ericsson/util-bw-port/hr.htm"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc/ericsson/util-bw-port/dy.htm"><span>Báo cáo ngày</span></a></li>
</ul>
  
<div class="ui-tabs-panel">
	<form method="post" action="hr.htm" name = "frmSample">
		<table style="width:100%" class="form">
			<tr>
			    <td align="left">
				 <fmt:message key="ericsson.bsc"/>&nbsp;
					<select id="bscid" name="bscid" style="width: 8%">
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
	                <select name="shour" id="shour">
	            				<c:forEach var="hour" items="${hourList}">
						              <c:choose>
						                <c:when test="${hour == shourCBB}">
						                    <option value="${hour}" selected="selected">${hour}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${hour}">${hour}</option>
						                </c:otherwise>
						              </c:choose>
						    </c:forEach>
			               	 </select>&nbsp; giờ
	                <input value="${sdate}" name="sdate" id="sdate" size="10" maxlength="10"/>
	                
	                &nbsp;&nbsp;<fmt:message key="ericsson.toi"/> 
	                <select name="ehour" id="ehour">
	            				<c:forEach var="hour" items="${hourList}">
						              <c:choose>
						                <c:when test="${hour == ehourCBB}">
						                    <option value="${hour}" selected="selected">${hour}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${hour}">${hour}</option>
						                </c:otherwise>
						              </c:choose>
						    </c:forEach>
			               	 </select>&nbsp; giờ
	                <input value="${edate}" name="edate" id="edate" size="10" maxlength="10"/>
	                
	                &nbsp;&nbsp;<input class="button" type="submit" name="filter" value="<fmt:message key="global.form.viewReport"/>" />
	            </td>
	        </tr>		
		</table>
	</form>
	<br/>
</div>
	
	
<div style="width:100%;overflow:auto; ">
	<display:table name="${hrBscUtilbwPort3g}" class="simple2" id="item" requestURI="" pagesize="100" sort="external" defaultsort="1" export="true" >
		<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="bscUtilbwPort.day" sortable="true" sortName="DAY"/>
		<display:column property="hour" class="rightColumnMana" titleKey="bscUtilbwPort.hour" sortable="true" sortName="HOUR"/>
		<display:column property="bscid" titleKey="bscUtilbwPort.bscid" sortable="true" sortName="BSCID"/>	
		<display:column property="inoctetshi" class="rightColumnMana" titleKey="bscUtilbwPort.inoctetshi" sortable="true" sortName="INOCTETSHI"/>
		<display:column property="inoctetslo" class="rightColumnMana" titleKey="bscUtilbwPort.inoctetslo" sortable="true" sortName="INOCTETSLO"/>
		<display:column property="outoctetshi" class="rightColumnMana" titleKey="bscUtilbwPort.outoctetshi" sortable="true" sortName="OUTOCTETSHI"/>
		<display:column property="outoctetslo" class="rightColumnMana" titleKey="bscUtilbwPort.outoctetslo" sortable="true" sortName="OUTOCTETSLO"/>
		<display:column property="inOctets" class="rightColumnMana" titleKey="bscUtilbwPort.inOctets" sortable="true" sortName="IN_OCTETS"/>
		<display:column property="outOctets" class="rightColumnMana" titleKey="bscUtilbwPort.outOctets" sortable="true" sortName="OUT_OCTETS"/>
		<display:column property="totalOctets" class="rightColumnMana" titleKey="bscUtilbwPort.totalOctets" sortable="true" sortName="TOTAL_OCTETS"/>
	 		 		
		<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
			
	</display:table>
</div>

<script type="text/javascript" src="/VMSC2-Alarm-Feedback/scripts/exporting.js"></script>
<script type = "text/javascript">
function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
}
</script>

<script type="text/javascript">
	$(function() {
		$( "#edate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
		
		$( "#edate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
	});
</script>
