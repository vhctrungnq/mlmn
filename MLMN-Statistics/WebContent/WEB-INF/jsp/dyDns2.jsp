<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>DNS ${title} REPORT</title>
<content tag="heading">DNS ${title} REPORT</content>

<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/csi-cmd-licence/dy.htm"><span>Báo cáo ngày</span></a></li>
</ul>
  
<div class="ui-tabs-panel">
	<form:form method="get" commandName="filter" action="dy.htm" name="frmSample"  >
		<table width="100%" class="form">
			<tr>
			    <td align="left"> 
			        &nbsp;&nbsp;NodeId  <input name="nodeid" id="nodeid" value="${nodeid}" size="10"  style="width:150px"> 
	                &nbsp;&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id = "submit"value="View Report"/>
	            </td>
	        </tr>		
		</table>
	</form:form>
	</div>
	<br/>
<div  class="tableStandar">
		<display:table name="${dyCsiList}" id="dyCsiList" requestURI="" pagesize="100"  export="true">		    
		    <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" />
		    <display:column property ="nodeid" titleKey="Nodeid" />
		    <display:column property ="cpuUsage" titleKey="CPU Usage(%)" />
		    <display:column property ="memUsage"  titleKey="Memory Usage(%)" />
		    <display:column property ="diskUsage"  titleKey="Disk Usage(%)" />
		    <display:column property ="latencyTime"  titleKey="Latency Time(usecond)" /> 
		    <display:column property ="numberOfQueries"  titleKey="Number of queries" /> 
		</display:table>
</div>
<table style="width:99%">
		<tr>
			<td><div id="chartCmdLicence" style="width: 100%; margin: 1em auto"></div></td>
		</tr> 
	</table>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script> 

<script type="text/javascript"> 

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
	 