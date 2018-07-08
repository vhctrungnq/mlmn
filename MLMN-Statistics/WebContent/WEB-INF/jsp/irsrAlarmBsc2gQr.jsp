<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>irsr 2g list</title>
<content tag="heading">BSC IRSR 2G QUARTERLY LIST</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/irsr/by-site/dy/list.htm"><span>ALARM by SITE</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/irsr/by-bsc/qr/list.htm"><span>IRSR by BSC</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/irsr/by-district/qr/list.htm"><span>IRSR by District</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/irsr/by-region/qr/list.htm"><span>IRSR by Center</span></a></li>
</ul>
<div class="ui-tabs-panel">
	<a href="${pageContext.request.contextPath}/report/radio/irsr/by-bsc/dy/list.htm"><span><b>Theo ngày</b></span></a>&nbsp;
	<a href="${pageContext.request.contextPath}/report/radio/irsr/by-bsc/mn/list.htm"><span><b>Theo tháng</b></span></a>&nbsp;
	<span><b>Theo qúy</b></span>&nbsp;
	<a href="${pageContext.request.contextPath}/report/radio/irsr/by-bsc/yr/list.htm"><span><b>Theo năm</b></span></a>
	<br/>
	
		<table width="100%" class="form">
			<tr>
			  <td align="left">
			  <form method="get" action="list.htm">
					BSC <input name="bscid" id="bscid" value="${bscid}" size="10">
	                Từ tháng <input value="${startQuarter}" name="startQuarter" id="startQuarter" size="2" maxlength="2">
	            	&nbsp;&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4">
	            	&nbsp;Tới tháng <input value="${endQuarter}" name="endQuarter" id="endQuarter" size="2" maxlength="2">
	            	&nbsp;&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4">
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	          </form>
	          </td>
	        </tr>		
		</table>
	<br/>
	<div  style="overflow: auto;">
		<display:table name="${qrIrsrAlarmBsc2gList}" id="qrIrsrAlarmBsc2g" requestURI="" pagesize="100" class="simple3" export="true">		
		    <display:column property ="quarter" titleKey="QUARTER" />
		    <display:column property ="year" titleKey="YEAR" />
		    <display:column property ="bscid" titleKey="BSC" />
		    <display:column property="irsr" titleKey="% IRSR"/>
		</display:table>
	</div>
</div>