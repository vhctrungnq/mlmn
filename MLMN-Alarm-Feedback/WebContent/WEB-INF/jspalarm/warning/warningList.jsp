
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- <style type="text/css">
    #doublescroll { overflow: auto; overflow-y: hidden; }
    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; } 
</style>--%>
<title>${titleWarningList}</title>
<content tag="heading">${titleWarningList}</content> 	


<table class="form" style="width:100%;">
		<tr> 
			<td align="left"><form:form commandName="filter" method="post"	action="list.htm">
			<input type="hidden" name="warningTp" id="warningTp" value="${warningTp}">
			<input type="hidden" name="region" id="region" value="${region}">
					<table >
						<tr> 
							<td  align="left"><fmt:message key="timer.startTime"/></td>
					    	<td>
					    		<input type ="text"  value="${startTime}" name="stime" id="stime" size="15" maxlength="16" width="60px">
					   			 <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
					    	</td>
					    	<td style="padding-left:5px;" align="left"><fmt:message key="timer.endTime"/> </td>
					    	<td>
					    		<input type ="text"  value="${endTime}" name="etime" id="etime" size="15" maxlength="16" width="60px">
					    		<img alt="calendar" title="Click to choose the stop date" id="chooseStopDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
								
							</td>	
							<td style="padding-left:5px;"><fmt:message key="warning.warningName"/></td>
							<td>
								<input type ="text"  name="alarm" id="alarm" style="width: 150px" value="${alarm}">
							</td>
							<td style="width:70px; padding-left:5px;"><fmt:message key="warning.methodTreatment"/></td>
							<td>
								<input type ="text" name="actionProcess" id="actionProcess" style="width: 150px" value="${actionProcess}">
							</td>
							<td style="width:50px; padding-left:5px;"><fmt:message key="warning.teamProcess"/></td>
							<td>
								 <select name="teamProcess" id="teamProcess" style="width: 100px;height:20px; padding-top: 4px;">
								 <option value=""><fmt:message key="global.All"/></option>
									<c:forEach var="item" items="${userList}">
										<c:choose>
							                <c:when test="${item.deptCode == teamProcess}">
							                    <option value="${item.deptCode}" selected="selected">${item.deptCode}</option>
							                </c:when>
											<c:otherwise>
												<option value="${item.deptCode}">${item.deptCode}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select> 
							</td>
							<td style="width:50px; padding-left:5px;"><fmt:message key="warning.mscid"/></td>
							<td ><input type ="text"  name="system" id="system" value="${system}" style="width: 120px">
								<input class="button" type="submit" id="button"
								value="<fmt:message key="button.search"/>" />
							</td>
						
						</tr>
					
					</table>
				</form:form>
				</td> 
		</tr> 
		<c:if test="${checkCaTruc==true}">
			<tr>
				<td class="wid10 mwid60 ftsize12" align="right" colspan="6">
		            <a href="form.htm?warningTp=${warningTp}&note=&region=${region}"><fmt:message key="button.add"/></a>
		            <a href="upload.htm?warningTp=${warningTp}&region=${region}"><fmt:message key="button.upload"/></a>
		        </td>
		    </tr>
		</c:if>
		
	    <tr>
	    	<td>
	    		<div id="doublescroll" >
					<display:table name="${warningList}" class="simple2" id="item" requestURI="" pagesize="15" sort="external" defaultsort="1" export="true">
					  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px;"> <c:out value="${item_rowNum}"/> </display:column>
						<display:column property="alarm" titleKey="warning.warningName" sortable="true" sortName="ALARM" style="min-width:120px;max-width:120px;"/>
					  	<display:column property="system" titleKey="warning.mscid" sortable="true" sortName="SYSTEM" style="min-width:125px;max-width:125px;"/>
						<display:column property="stime" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="warning.startTime" sortable="true" sortName="STIME" style="min-width:115px;max-width:115px;"/>
						<display:column property="etime"  format="{0,date,dd/MM/yyyy HH:mm}" titleKey="warning.endTime" sortable="true" sortName="ETIME" style="min-width:115px;max-width:115px;"/>
						<display:column property="thoiGian"  titleKey="warning.thoiGian" sortable="true" sortName="THOI_GIAN" style="min-width:40px;max-width:40px;"/>
						<display:column property="causeby"  titleKey="warning.causes" sortable="true" sortName="CAUSEBY" style="min-width:70px;max-width:70px;"/>
						<display:column property="actionProcess"  titleKey="warning.methodTreatment" sortable="true" sortName="ACTION_PROCESS" style="min-width:70px;max-width:70px;"/>
						<display:column property="resultsProcess"  titleKey="warning.resultsProcess" sortable="true" sortName="RESULTS_PROCESS" style="min-width:60px;max-width:60px;"/>
						<display:column property="reportProcess"  titleKey="warning.reportTreatment" sortable="true" sortName="REPORT_PROCESS" style="min-width:60px;max-width:60px;"/>
						<display:column property="teamProcess"  titleKey="warning.teamProcess" sortable="true" sortName="TEAM_PROCESS" style="min-width:60px;max-width:60px;"/>
						<display:column property="userProcess"  titleKey="warning.userExcute" sortable="true" sortName="USER_PROCESS" style="min-width:70px;max-width:70px;"/>
						<c:if test="${checkCaTruc==true}">
							<display:column titleKey="title.quanLy" media="html" class="centerColumnMana">
								<a href="form.htm?id=${item.id}&warningTp=${warningTp}&note=&region=${region}"><fmt:message key="button.edit"/></a>&nbsp;
			    				<a href="delete.htm?id=${item.id}&warningTp=${warningTp}&note=&region=${region}"
										onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')"><fmt:message key="button.delete"/></a>&nbsp;
			    			</display:column>
						</c:if>
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

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>

<script type="text/javascript"> $(".chzn-select").chosen(); $(".chzn-select-deselect").chosen({allow_single_deselect:true}); </script>
<script type="text/javascript">
    Calendar.setup({
        inputField		:	"stime",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });

    Calendar.setup({
        inputField		:	"etime",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseStopDate",   	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
	
</script>
<script type="text/javascript">
var theme =  getTheme(); 
$('#button').jqxButton({ theme: theme });
	function focusIt()
	{
	  var mytext = document.getElementById("stime");
	  mytext.focus();
	}
	onload = focusIt;
</script>
