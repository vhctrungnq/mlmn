<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<title>${title}</title>
<content tag="heading">${title}</content> 	
<form:form commandName="filter" method="post" action="detail.htm?type=${type}">
	<table >
			<tr> 
				<td style="width:70px"><fmt:message key="alarmLog.sdateF"/></td>
				<td style="width:150px">
						<input type ="text"  value="${dateF}" name="dateF" id="dateF" size="10" maxlength="10" style="width:130px">
			   			 <img alt="calendar" title="Click to choose the from date" id="chooseSDate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				
				<td style="padding-left: 5px;width:30px"><fmt:message key="alarmLog.sdateT"/></td>
				<td style="width:150px">
					<input type ="text"  value="${dateT}" name="dateT" id="dateT" size="10" maxlength="10" style="width:130px">
			   		<img alt="calendar" title="Click to choose the to date" id="chooseEDate" style="cursor: pointer; position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				<c:if test="${type=='alarm'}">
				<td style="padding-left: 5px;width: 50px;"><fmt:message key="alarmLog.network"/></td>
				<td>
					 <input type="text" id="network" name="network" value="${network}" style="width: 50px;"/>
				</td>
				<td style="padding-left: 5px;width: 50px;"><fmt:message key="alarmLog.neType"/></td>
				<td>
					 <input type="text" id="neType" name="neType" value="${neType}"  style="width: 50px;"/>
				</td>
				<td style="padding-left: 5px;width: 50px;"><fmt:message key="alarmLog.serverity"/></td>
				<td>
					 <input type="text" id="severity" name="severity" value="${severity}" style="width: 50px;"/>
				</td>
				
					<td class="pl10"><spring:message code="alarmLog.tinhTrang"/></td>
					<td style="width:150px">
						<div class="psr ovh select">
							<select id="status" name ="status" class="wid100 select" >
								<option value="">--Tất cả--</option>
		          				<c:forEach var="items" items="${statusList}">
					              	<c:choose>
					                <c:when test="${items.name == status}">
					                    <option value="${items.name}" selected="selected">${items.value}</option>
					                </c:when>
					                <c:otherwise>
					                    <option value="${items.name}">${items.value}</option>
					                </c:otherwise>
					              	</c:choose>
						    	</c:forEach>
          					</select>
          				</div>
					</td>
				</c:if>
				<c:if test="${type!='alarm'}">
				
					<td style="width:70px;padding-left: 5px;">
						<fmt:message key="catruc.caTK"/>
					</td>
					<td>
						<select name="catruc" id="catruc" style="width: 80px;height:20px; padding-top: 4px;">
							<option value=""><fmt:message key="global.shiftAll"/></option>
							<c:forEach var="item" items="${caList}">
								<c:choose>
					                <c:when test="${item.value == catruc}">
					                    <option value="${item.value}" selected="selected">${item.value}</option>
					                </c:when>
									<c:otherwise>
										<option value="${item.value}">${item.value}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select> 
					</td>	
					<td style="padding-left: 5px;width:70px"><fmt:message key="alarmLog.users"/></td>
					<td>
						<input type="text" id="users" name="users" style="width: 100px;"/>
					</td>
					<td class="pl10"><spring:message code="alarmLog.tinhTrang"/></td>
					<td style="width:150px">
						<div class="psr ovh select">
							<select id="status" name ="status" class="wid100 select" >
								<option value="">--Tất cả--</option>
		          				<c:forEach var="items" items="${statusList}">
					              	<c:choose>
					                <c:when test="${items.value == status}">
					                    <option value="${items.value}" selected="selected">${items.name}</option>
					                </c:when>
					                <c:otherwise>
					                    <option value="${items.value}">${items.name}</option>
					                </c:otherwise>
					              	</c:choose>
						    	</c:forEach>
          					</select>
          				</div>
					</td>
				</c:if>
					
				<td>
				<input class="button" type="submit" id="button" value="<fmt:message key="global.form.timkiem"/>" />
				</td>
			</tr>
		</table>
<c:choose>
    <c:when test="${type=='alarm'}">
        <%@ include file="/includeJQ/gridAlarmLog.jsp" %>
    </c:when>
    <c:otherwise>
       <div style="float: right;" id="jqxlistbox"></div>
		<div id="jqxgrid"></div>
		 <div id='Menu'>
		            <ul>
				   		<li><fmt:message key="global.button.exportExcel"/></li>
			        </ul>
		 </div>
    </c:otherwise>
 </c:choose>
		
	</form:form>
<br/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script type="text/javascript">
var type = '<c:out value="${type}"/>';
if (type!='alarm')
	{
		Calendar.setup({
		    inputField		:	"dateF",	// id of the input field
		    ifFormat		:	"%d/%m/%Y",   	// format of the input field
		    button			:   "chooseSDate",  	// trigger for the calendar (button ID)
		    showsTime		:	true,
		    singleClick		:   false					// double-click mode
		}); 
		
		Calendar.setup({
		    inputField		:	"dateT",	// id of the input field
		    ifFormat		:	"%d/%m/%Y",   	// format of the input field
		    button			:   "chooseEDate",   	// trigger for the calendar (button ID)
		    showsTime		:	true,
		    singleClick		:   false					// double-click mode
		}); 
	}
else
	{
		Calendar.setup({
		    inputField		:	"dateF",	// id of the input field
		    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
		    button			:   "chooseSDate",  	// trigger for the calendar (button ID)
		    showsTime		:	true,
		    singleClick		:   false					// double-click mode
		}); 
		
		Calendar.setup({
		    inputField		:	"dateT",	// id of the input field
		    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
		    button			:   "chooseEDate",   	// trigger for the calendar (button ID)
		    showsTime		:	true,
		    singleClick		:   false					// double-click mode
		}); 
	}
	
${gridDetail}

$("#Menu").on('itemclick', function (event) {
    var args = event.args;
    var exportFileName =  "<c:out value='${exportFileName}'/>";
    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
    /* 	$("#jqxgrid").jqxGrid('exportdata', 'xls', exportFileName); */
    	 window.open('${pageContext.request.contextPath}/assess/export.htm?function='+"<c:out value='${function}'/>"+
   				 '&dateF='+"<c:out value='${dateF}'/>"+
   				 '&dateT='+"<c:out value='${dateT}'/>"+
				'&catruc='+"<c:out value='${catruc}'/>"+
				'&users='+"<c:out value='${users}'/>"+
				'&status='+"<c:out value='${status}'/>"+
				'&network='+"<c:out value='${network}'/>"+
				'&neType='+"<c:out value='${neType}'/>"+
				'&severity='+"<c:out value='${severity}'/>"+
				'&columnheader='+"<c:out value='${columnheader}'/>"+
				'&type='+"<c:out value='${type}'/>" ,'_blank');
    }
});

</script>
<script type="text/javascript">
$(document).ready(function(){

		// Khai bao sdate, edate
		var theme =  getTheme();
		$('#button').jqxButton({ theme: theme });
});
</script>
