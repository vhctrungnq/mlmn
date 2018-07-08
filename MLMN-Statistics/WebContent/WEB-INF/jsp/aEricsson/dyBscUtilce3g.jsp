<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>RNC UTILITY CE USAGE DAILY REPORT</title>
<content tag="heading">RNC UTILITY CE USAGE DAILY REPORT</content>

<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc/ericsson/util-ce/hr.htm"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/rnc/ericsson/util-ce/dy.htm"><span>Báo cáo ngày</span></a></li>
</ul>

<div class="ui-tabs-panel">
<form method="post" action="dy.htm">
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
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
				<fmt:message key="ericsson.tuNgay"/>
				<input value="${sdate}" name="sdate" id="sdate" size="10" maxlength="10">
	            &nbsp;&nbsp;<fmt:message key="ericsson.toiNgay"/>
	            <input value="${edate}" name="edate" id="edate" size="10" maxlength="10">
				&nbsp;&nbsp;<input class="button" type="submit" name="filter" value="<fmt:message key="global.form.viewReport"/>" />
			</td>	
		</tr>
		
		<tr>
			<td style="padding-top: 10px;">
				<div style="width:100%;overflow:auto; ">
					<display:table name="${dyBscUtilce3g}" class="simple2" id="item" requestURI="" pagesize="100" sort="external" defaultsort="1" export="true" >
						<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="bscUtilce.day" sortable="true" sortName="DAY"/>
						<display:column property="bscid" titleKey="bscUtilce.bscid" headerClass="hide" class="hide"/>		   
					    <display:column titleKey="bscUtilce.bscid" media="html" sortable="true" sortName="BSCID">
					   	 	<a href="${pageContext.request.contextPath}/report/radio3g/rnc/ericsson/util-ce/hr.htm?bscid=${item.bscid}&sdate=<fmt:formatDate pattern="dd/MM/yyyy" value="${item.day}"/>&edate=<fmt:formatDate pattern="dd/MM/yyyy" value="${item.day}"/>">${item.bscid}</a>
					    </display:column>	
						<display:column property="ulUsage" class="rightColumnMana" titleKey="bscUtilce.ulUsage" sortable="true" sortName="UL_USAGE"/>
						<display:column property="dlUsage" class="rightColumnMana" titleKey="bscUtilce.dlUsage" sortable="true" sortName="DL_USAGE"/>
	 	
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
</table>
</form>
</div>
 
<script type="text/javascript" src="/VMSC2-Alarm-Feedback/scripts/exporting.js"></script>
<script type="text/javascript" src="/VMSC2-Alarm-Feedback/scripts/themes/grid.js"></script> 

<script type="text/javascript">

$(function() {
	$( "#sdate" ).datepicker({
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

function focusIt()
{
  var mytext = document.getElementById("startDate");
  mytext.focus();
}

onload = focusIt;

</script>