<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<c:choose>
  <c:when test="${alManageBacklogAddEdit == 'N'}">
      <title><fmt:message key="title.alManageBacklogFormAdd"/></title>
	  <content tag="heading"><fmt:message key="title.alManageBacklogFormAdd"/></content>
  </c:when>
  <c:when test="${alManageBacklogAddEdit == 'Y'}">
      <title><fmt:message key="title.alManageBacklogFormEdit"/></title>
	  <content tag="heading"><fmt:message key="title.alManageBacklogFormEdit"/></content>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>

<form:form commandName="alManageBacklog" name="checkform" method="post" action="form.htm"> 
	<div>
    	<form:input path="id" type="hidden" />
    </div>
    <table class="simple2">
      <tr>
           <td class="wid15 mwid110"><fmt:message key="alManageBacklog.work"/>&nbsp;<font color="red">(*)</font></td>
           <td class="wid35">
		       	<form:input path="work" maxlength="800" />
		       	&nbsp;<form:errors path="work" cssClass="error"/>
           </td>
           <td class="wid15 mwid110"><fmt:message key="alManageBacklog.process"/></td>
           <td>
           		<form:input path="process" maxlength="220" />
           </td>       
      </tr>
      <tr>
           <td><fmt:message key="alManageBacklog.startDate"/></td>
           <td>
				<input id="startDate" name="startDate" value="${startDate}" class="wid30" maxlength="20"/>
	 			<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	 			&nbsp;<form:errors path="startDate" cssClass="error"/>
           </td>
           <td><fmt:message key="alManageBacklog.endDate"/></td>
           <td>
				<input id="endDate" name="endDate" value="${endDate}" class="wid30" maxlength="20"/>
	 			<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	 			&nbsp;<form:errors path="endDate" cssClass="error"/>
           </td>       
      </tr>
      <tr>
           <td><fmt:message key="alManageBacklog.team"/></td>
           <td>
           		<c:choose>
					<c:when test="${alManageBacklog.team != null}">
						${alManageBacklog.team}
					</c:when>
					<c:otherwise>
						<div id='team'></div>
					</c:otherwise>
				</c:choose>
		       	
           </td>
           <td><fmt:message key="alManageBacklog.ne"/></td>
           <td>
		      <form:input path="ne" maxlength="150" />
           </td>
                
      </tr>  
       <tr>
           <td><fmt:message key="alManageBacklog.previousConfig"/></td>
           <td>
		       <form:input path="previousConfig" maxlength="150" />
           </td>
           <td><fmt:message key="alManageBacklog.laterConfig"/></td>
           <td>
           		<form:input path="laterConfig" maxlength="150" />
           </td>       
      </tr> 
      <tr>
          
           <td><fmt:message key="alManageBacklog.causeby"/></td>
           <td>
           		<form:input path="causeby" maxlength="150" />
           </td>    
           <td><fmt:message key="alManageBacklog.description"/></td>
           <td>
           		<form:input path="description" maxlength="220" />
           </td>     
      </tr> 
     
      <tr>
        <td></td>
        <td colspan="3">
            <input class="button" type="submit" name="save" value="<fmt:message key="global.form.luulai"/>" />
            <input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm"'>
        </td>
      </tr>
    </table>
</form:form>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
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
    button			:   "chooseEndDate",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
});

function focusIt()
{
	var startDateError = '<c:out value="${startDateError}"/>';
	var endDateError = '<c:out value="${endDateError}"/>';
	
	if(document.checkform.work.value==""){
		  var mytext = document.getElementById("work");
		  mytext.focus();
		}
	else if(startDateError !=""){
		  var mytext = document.getElementById("startDate");
		  mytext.focus();
		}
	else if(endDateError !=""){
		  var mytext = document.getElementById("endDate");
		  mytext.focus();
		}
}

onload = focusIt;

</script>
<script type="text/javascript">
var theme = getTheme();
$("#work").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#process").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#description").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#ne").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#previousConfig").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#laterConfig").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#causeby").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
//Create a jqxDropDownList
urlTeam = "${pageContext.request.contextPath}/alarm/al-manage-backlog/teamList.htm";
// prepare the data
var sourceTeam =
{
    datatype: "json",
    url: urlTeam,
    datafields: [
                 { name: 'deptValue'},
                 { name: 'deptValue'}
             ],
    async: false
};
var dataAdapterTeam = new $.jqx.dataAdapter(sourceTeam);
$("#team").jqxDropDownList({source: dataAdapterTeam, selectedIndex: 0, displayMember: "deptValue", valueMember: "deptValue", width:'60%', height: 20, theme: theme, enableHover: true, autoOpen: true });           

</script>
