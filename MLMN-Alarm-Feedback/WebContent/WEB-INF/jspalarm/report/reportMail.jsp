<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<title>${title}</title>
<content tag="heading">${title}</content> 	
<div class="ui-tabs-panel">
	<form:form commandName="filter" method="post" action="${function}.htm?mailid=${mailid}%blockid=${blockid}">
		<table >
		
			<tr> 
				<td style="width:50px">Day</td>
				<td style="width:90px">
						<input type ="text"  value="${sdate}" name="sdate" id="sdate" size="10" maxlength="10" style="width:70px">
			   			 <img alt="calendar" title="Click to choose the from date" id="chooseSDate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				<c:if test="${function=='hr'}">
				<td style="width:50px">Hour</td>
				<td style="width:50px">
						<input value="${shour}" name="shour" id="shour" size="5" maxlength="5">
				</td>
				</c:if>
				<td style="padding-left: 5px;">
					<input class="button" type="submit" id="button" value="<fmt:message key="global.form.timkiem"/>" />
				</td>
			</tr>
		</table>
	</form:form>
</div>
<c:forEach  items="${blockList}"  var="block">
	<br/>
	<h3>${block.blockName}</h3>
	<div id="grid${block.blockId}"></div>
	<div id='menu${block.blockId}'>
       <ul>
   		<li><fmt:message key="global.button.exportExcel"/></li>
       </ul>
	</div>
	<div class="clear"></div>
</c:forEach>



<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script type="text/javascript">
Calendar.setup({
    inputField		:	"sdate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseSDate",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 
</script>
<script type="text/javascript">
<c:forEach  items="${gridList}"  var="grid">
	${grid}
</c:forEach>	
//handle context menu 
<c:forEach  items="${blockList}"  var="block">
	$('#menu'+ '<c:out value='${block.blockId}'/>').on('itemclick', function (event) {
		
	    var args = event.args;
	    var exportFileName =  '<c:out value='${block.blockId}'/>'+'<c:out value='${shour}'/>';
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$('#grid'+ '<c:out value='${block.blockId}'/>').jqxGrid('exportdata', 'xls', exportFileName);
	    }
	});
</c:forEach>	

</script>
