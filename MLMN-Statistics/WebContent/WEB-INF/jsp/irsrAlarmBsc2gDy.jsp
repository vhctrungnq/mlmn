<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>irsr 2g list</title>
<content tag="heading">BSC IRSR 2G DAILY LIST</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/irsr/by-site/dy/list.htm?bscid=${bscid}&startDate=${startDate}&endDate=${endDate}"><span>ALARM by SITE</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/irsr/by-bsc/dy/list.htm?bscid=${bscid}&startDate=${startDate}&endDate=${endDate}"><span>IRSR by BSC</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/irsr/by-district/dy/list.htm?startDate=${startDate}&endDate=${endDate}"><span>IRSR by District</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/irsr/by-region/dy/list.htm?startDate=${startDate}&endDate=${endDate}"><span>IRSR by Center</span></a></li>
</ul>
<div class="ui-tabs-panel">
	<span><b>Theo ngày</b></span>&nbsp;
	<a href="${pageContext.request.contextPath}/report/radio/irsr/by-bsc/mn/list.htm?bscid=${bscid}"><span><b>Theo tháng</b></span></a>&nbsp;
	<a href="${pageContext.request.contextPath}/report/radio/irsr/by-bsc/qr/list.htm?bscid=${bscid}"><span><b>Theo quý</b></span></a>&nbsp;
	<a href="${pageContext.request.contextPath}/report/radio/irsr/by-bsc/yr/list.htm?bscid=${bscid}"><span><b>Theo năm</b></span></a>
	<br/>
	
		<table width="100%" class="form">
			<tr>
			  <td align="left">
			  <form method="get" action="list.htm">
					BSC <input name="bscid" id="bscid" value="${bscid}" size="10">
	                &nbsp;&nbsp;Từ Ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;&nbsp;Tới Ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	          </form>
	          </td>
	        </tr>		
		</table>
	<br/>
	<div  style="overflow: auto;">
		<display:table name="${dyIrsrAlarmBsc2gList}" id="dyIrsrAlarmBsc2g" requestURI="" pagesize="100" class="simple3" export="true">		
		    <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" />
		    <display:column property ="bscid" titleKey="BSC" />
		    <display:column property="irsr" titleKey="% IRSR"/>
		</display:table>
	</div>
</div>
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
</script>
