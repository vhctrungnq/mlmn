<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>download log list</title>
<body class="section-6"/>
<content tag="heading">GIÁM SÁT QUÁ TRÌNH DOWNLOAD FILE</content>

<form:form commandName="filter" method="post" action="download.htm">
	<table width="100%" class="form">
		<tr>
		    <td align="left">
            	Từ ngày <form:input path="startDate" id="startDate" size="16" maxlength="16"/>
            	<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
            	&nbsp;Đến ngày <form:input path="endDate" id="endDate" size="16" maxlength="16"/>
            	<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
            	&nbsp;&nbsp;<input type="submit" class="button" name="filter" value="Tìm kiếm"/>
            </td>
        </tr>		
	</table>
</form:form>
<br/>

<div  style="overflow: auto;">
<display:table name="${downloadLogList}" id="downloadLog" class="simple2" export="true" requestURI="" sort="external" defaultsort="1" pagesize="100">
    <display:column property="downloadTime" format="{0,date,dd/MM/yyyy HH:mm}" title="THỜI ĐIỂM TẢI FILE"/>        
    <display:column property="serverName" title="TÊN SERVER"/>
    <display:column property="directory" title="THƯ MỤC"/>        
    <display:column property="fileName" title="TÊN FILE"/>
    <display:column property="fileSize" title="KÍCH THƯỚC FILE (BYTES)"/>
    	<display:setProperty name="export.csv.filename" value="DownloadLog.csv"/>
	<display:setProperty name="export.excel.filename" value="DownloadLog.xls"/>
	<display:setProperty name="export.xml.filename" value="DownloadLog.xml"/>
</display:table>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
    Calendar.setup({
        inputField		:	"startDate",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });

    Calendar.setup({
        inputField		:	"endDate",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseEndDate",   	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
</script>
