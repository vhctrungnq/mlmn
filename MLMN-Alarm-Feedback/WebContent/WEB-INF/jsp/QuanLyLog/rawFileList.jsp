<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>${title}</title>
<content tag="heading">${title}</content> 	

<form:form commandName="filter" method="post" action="rawFile.htm">
	<table style="width:100%;" class="form">
		<tr>
		   <td style="width:70px;">
            	<fmt:message key="rawFile.startDate"/>
            </td>
            <td class="wid15">
	            <input value="${startDate}" name="startDate" id="startDate" size="16" maxlength="10">
	            <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/></td>
	        <td  style="width:70px; padding-left:5px;"><fmt:message key="rawFile.endDate"/></td>
	        <td  class="wid15">
	        	<input value="${endDate}" name="endDate" id="endDate" size="16" maxlength="10">
	        	<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
           </td>
            <td  style="width:150px; padding-left:5px;"><fmt:message key="rawFile.statusFileConvert"/></td>
            <td  style="width:150px;">
         		<select name="convertFlag" id="convertFlag" style="width: 140px;height:20px; padding-top: 4px;">
         			<option value=""><fmt:message key="global.All"/></option>
	           		<c:forEach var="item" items="${convertFlagList}">
						<c:choose>
			                <c:when test="${item.name == convertFlag}">
			                    <option value="${item.name}" selected="selected">${item.value}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.name}">${item.value}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> 
	        </td> 
                
        </tr>
        <tr>
         	<td  style="width:130px;"><fmt:message key="rawFile.rawTable"/></td>
            <td><input value="${rawTable}" name="rawTable" id="rawTable" size="20" /></td>    
        	<td ><fmt:message key="rawFile.fileName"/></td>
        	<td><input value="${fileName}" name="fileName" id="fileName" size="16" /></td>
			<td style=" padding-left:5px;"><fmt:message key="rawFile.statusFileImport"/></td>
			<td>
				<select name="importFlag" id="importFlag" style="width: 140px;height:20px; padding-top: 4px;">
					<option value=""><fmt:message key="global.All"/></option>
	           		<c:forEach var="item" items="${importFlagList}">
						<c:choose>
			                <c:when test="${item.name == importFlag}">
			                    <option value="${item.name}" selected="selected">${item.value}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.name}">${item.value}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> 
			</td> 
          <td colspan="4" style=" padding-left:5px;">	
          	<input class="button" type="submit" value="<fmt:message key="button.search"/>" />
		</td>
        </tr>		
	</table>
</form:form>
<br/>
<div class="tableStandar"  style="overflow: auto;">
<display:table name="${rawFileList}" id="rawFile" requestURI="" pagesize="100"  export="true" partialList="true" size="${totalSize}" sort="external" defaultsort="1">
    <display:column title="STT">
				<c:out value="${rawFile_rowNum + startRecord}" />
	</display:column>
	<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="rawFile.day"  sortable="true" sortName="DAY"/>        
    <display:column property="hour" titleKey="rawFile.hour"  sortable="true" sortName="HOUR"/>
    <display:column property="fileId" titleKey="rawFile.fileId"  sortable="true" sortName="FILE_ID"/>
    <display:column property="fileName" titleKey="rawFile.fileName"  sortable="true" sortName="FILE_NAME"/>        
    <display:column property="convertFlagStr" titleKey="rawFile.statusFileConvert"  sortable="true" sortName="CONVERT_FLAG_STR"/>
    <display:column property="importFlagStr" titleKey="rawFile.statusFileImport"  sortable="true" sortName="IMPORT_FLAG_STR"/>
    <display:column property="nodeName" titleKey="rawFile.nodeName"  sortable="true" sortName="NODE_NAME"/>
    <display:column property="rawTable" titleKey="rawFile.rawTable"  sortable="true" sortName="RAW_TABLE"/>
    <display:column property="originalName" titleKey="rawFile.originalName"  sortable="true" sortName="ORIGINAL_NAME"/>
  	<display:setProperty name="export.csv.include_header" value="true" />
	<display:setProperty name="export.excel.include_header" value="true" />
	<display:setProperty name="export.xml.include_header" value="true" />
	<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
	<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
	<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />
</display:table>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
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

<script type = "text/javascript">
function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
} 

function focusIt()
{
  var mytext = document.getElementById("startDate");
  mytext.focus();
}

onload = focusIt;
</script>