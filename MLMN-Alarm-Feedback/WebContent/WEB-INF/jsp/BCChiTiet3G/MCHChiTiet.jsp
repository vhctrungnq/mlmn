
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">   
 	#doublescroll { overflow: auto; overflow-y: hidden; }  
    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
</style>
<title>${titleD}</title>
<content tag="heading">${titleD}</content> 	
 <input type="hidden" name="alarmType" id="alarmType" value="${alarmType}">
<fmt:message key="warning.operator"/>: ${rncid}&nbsp;-&nbsp;  <fmt:message key="rAlarmLog.system"/>: ${alarmLevel} &nbsp; - &nbsp;<fmt:message key="vAlRbLossPower.Start"/>: ${startTime}
 <div align="left"> <input type="button" class="button" value="<fmt:message key="button.cancel"/>" onClick='window.location="dsach_ch.htm?alarmType=${alarmType}"'>
</div>
  <div id="doublescroll" >
	<display:table name="${detailList}" class="simple2" id="item" requestURI="" pagesize="50">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px;"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="rncid" titleKey="warning.operator"  style="min-width:60px;max-width:60px;"/>
	  	<display:column property="alarmLevel" titleKey="rAlarmLog.system"  style="min-width:60px;max-width:60px;"/>
		<display:column property="alarmName" titleKey="vAlRbLossPower.alarm" style="min-width:180px;max-width:180px;"/>
		<display:column property="sdate"  titleKey="rAlarmLog.sdate" format="{0,date,dd/MM/yyyy HH:mm:ss}" style="min-width:130px;max-width:130px;" />
		<display:column property="edate"  titleKey="rAlarmLog.edate" format="{0,date,dd/MM/yyyy HH:mm:ss}" style="min-width:130px;max-width:130px;"/>
		<display:column property="totalTime" titleKey="vAlRbLossPower.tgMD" style="min-width:60px;max-width:60px;"/>
	   	<display:column property="alarmType" titleKey="vAlRbLossPower.alarmType" style="min-width:80px;max-width:80px;"/>
		<display:column property="alarmClass" titleKey="vAlRbLossPower.alarmClass" style="min-width:30px;max-width:30px;"/>
		<display:column property="alarmInfo" titleKey="vAlRbLossPower.alarmInfo"/>
		<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />

	</display:table>
</div>
