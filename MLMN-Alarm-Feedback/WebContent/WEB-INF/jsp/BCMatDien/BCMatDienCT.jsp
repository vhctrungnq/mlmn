
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">   
 	#doublescroll { overflow: auto; overflow-y: hidden; }  
    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
</style>
<title>${titleD}</title>
<content tag="heading">${titleD}</content> 	
 <input type="hidden" name="alarmType" id="alarmType" value="${alarmType}">
<fmt:message key="warning.operator"/>: ${bsc}&nbsp;-&nbsp;  <fmt:message key="rAlarmLog.system"/>: ${cellid} &nbsp; - &nbsp;<fmt:message key="vAlRbLossPower.Start"/>: ${start}
 <div align="left"> <input type="button" class="button" value="<fmt:message key="button.cancel"/>" onClick='window.location="list.htm"'>
</div>
  <div id="doublescroll" class="tableStandar">
	<display:table name="${detailList}"  id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px;"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="bscid" titleKey="warning.operator" sortable="true" sortName="BSCID" style="min-width:60px;max-width:60px;"/>
	  	<display:column property="site" titleKey="rAlarmLog.system" sortable="true" sortName="SITE" style="min-width:60px;max-width:60px;"/>
		<display:column property="alarm" titleKey="vAlRbLossPower.alarm" sortable="true" sortName="ALARM" style="min-width:180px;max-width:180px;"/>
		<display:column property="sdate"  titleKey="rAlarmLog.sdate" format="{0,date,dd/MM/yyyy HH:mm:ss}" sortable="true" sortName="SDATE" style="min-width:130px;max-width:130px;" />
		<display:column property="edate"  titleKey="rAlarmLog.edate" format="{0,date,dd/MM/yyyy HH:mm:ss}" sortable="true" sortName="EDATE" style="min-width:130px;max-width:130px;"/>
		<display:column property="tgMD" titleKey="vAlRbLossPower.tgMD" sortable="true" sortName="TG_MD" style="min-width:60px;max-width:60px;"/>
	   	<display:column property="alarmType" titleKey="vAlRbLossPower.alarmType" sortable="true" sortName="ALARM_TYPE" style="min-width:80px;max-width:80px;"/>
		<display:column property="alarmClass" titleKey="vAlRbLossPower.alarmClass" sortable="true" sortName="ALARM_CLASS" style="min-width:40px;max-width:40px;"/>
		<display:column property="alarmInfo" titleKey="vAlRbLossPower.alarmInfo" sortable="true" sortName="ALARM_INFO" />
		<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />

	</display:table>
</div>
<%-- <script type="text/javascript">
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
    
  
</script> --%>