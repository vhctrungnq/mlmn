
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">   
 	#doublescroll { overflow: auto; overflow-y: hidden; }  
    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
</style>
<title><fmt:message key="rAlarmLog.title"/></title>
<content tag="heading"><fmt:message key="rAlarmLog.title"/></content> 	
<div>
<form:form commandName="filter" method="get" action="rAlarmLog.htm">
	<table>
	<tr><td></td></tr>
	</table>
</form:form>
</div>
  <div  id="doublescroll" >
	<display:table name="${CB2GList}" class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" > <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="alarmName" titleKey="rAlarmLog.alarmName" sortable="true" sortName="ALARM_NAME"/>
	  	<display:column property="system" titleKey="rAlarmLog.system" sortable="true" sortName="SYSTEM"/>
		<display:column property="bscid"  titleKey="rAlarmLog.bscid" sortable="true" sortName="BSCID"/>
		<display:column property="alarmType"  titleKey="rAlarmLog.alarmType" sortable="true" sortName="ALARM_TYPE"/>
		 <display:column property="sdate"  titleKey="rAlarmLog.sdate" format="{0,date,dd/MM/yyyy HH:mm}" sortable="true" sortName="SDATE" /> 
		<display:column property="edate"  titleKey="rAlarmLog.edate" format="{0,date,dd/MM/yyyy HH:mm}" sortable="true" sortName="EDATE" />
		<display:column property="totalTime" titleKey="rAlarmLog.totalTime" sortable="true" sortName="TOTAL_TIME"/>
		<display:column property="causeby" titleKey="rAlarmLog.causeby" sortable="true" sortName="CAUSEBY"/>
		<display:column property="actionProcess" titleKey="rAlarmLog.actionProcess" sortable="true" sortName="ACTION_PROCESS"/>
		
	   	<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />

	</display:table>
</div>
<script type="text/javascript">
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
 	   