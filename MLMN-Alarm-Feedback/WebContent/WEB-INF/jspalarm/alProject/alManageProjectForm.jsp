<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:choose>
	  <c:when test="${type == 'ON_AIR'}">    
		<c:choose>
		  <c:when test="${alManageProjectAddEdit == 'N'}">
		      <title><fmt:message key="alManageProjectOnAirFormAdd"/></title>
			  <content tag="heading"><fmt:message key="alManageProjectOnAirFormAdd"/></content>
		  </c:when>
		  <c:when test="${alManageProjectAddEdit == 'Y'}">
		      <title><fmt:message key="alManageProjectOnAirFormEdit"/></title>
			  <content tag="heading"><fmt:message key="alManageProjectOnAirFormEdit"/></content>
		  </c:when>
		  <c:otherwise></c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="${type == 'FOLLOW'}">
		<c:choose>
		  <c:when test="${alManageProjectAddEdit == 'N'}">
		      <title><fmt:message key="alManageProjectFollowFormAdd"/></title>
			  <content tag="heading"><fmt:message key="alManageProjectFollowFormAdd"/></content>
		  </c:when>
		  <c:when test="${alManageProjectAddEdit == 'Y'}">
		      <title><fmt:message key="alManageProjectFollowFormEdit"/></title>
			  <content tag="heading"><fmt:message key="alManageProjectFollowFormEdit"/></content>
		  </c:when>
		  <c:otherwise></c:otherwise>
		</c:choose>
	</c:when>
</c:choose>
<form:form commandName="alManageProject" name="checkform" method="post" action="form.htm"> 
	<div>
    	<form:input path="id" type="hidden" />
    	<input id="type" name="type" value="${type}" type="hidden" />
    </div>
    <table class="simple2">
      <tr>
           <td class="wid15 mwid110"><fmt:message key="alManageProject.projectCode"/>&nbsp;<font color="red">(*)</font></td>
           <td class="wid35">
		       	<form:input path="projectCode" maxlength="200" cssClass="wid60"/>
		       	&nbsp;<form:errors path="projectCode" cssClass="error"/>
           </td>
           <td class="wid15 mwid110"><fmt:message key="alManageProject.projectName"/>&nbsp;<font color="red">(*)</font></td>
           <td>
           		<form:input path="projectName" maxlength="200" cssClass="wid60"/>
           		&nbsp;<form:errors path="projectName" cssClass="error"/>
           </td>       
      </tr> 
      <tr>
           <td><fmt:message key="alManageProject.startDate"/></td>
           <td>
           		<input id="startDate" name="startDate" value="${startDate}" class="wid30" maxlength="20"/>
	 			<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	 			&nbsp;<form:errors path="startDate" cssClass="error"/>
           </td>
	   	 	<td><fmt:message key="alManageProject.endDate"/></td>
        	<td>
        		<input id="endDate" name="endDate" value="${endDate}" class="wid30" maxlength="20"/>
 				<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
 				&nbsp;<form:errors path="endDate" cssClass="error"/>
        	</td>
      </tr>
      
      <c:choose>
      		<c:when test="${type == 'ON_AIR'}">
      			<tr>
			      	<td><fmt:message key="alManageProject.projectType"/></td>
			      	<td><form:input path="projectType" maxlength="70" cssClass="wid60"/></td>
			        <td><fmt:message key="alManageProject.installationPlanRateOnAir"/></td>
			        <td>
			        	<form:input path="installationPlanRate" maxlength="3" cssClass="wid30"/>
			        	&nbsp;<form:errors path="installationPlanRate" cssClass="error"/>
			        </td>
			      </tr>
			      <tr>
			      	<td><fmt:message key="alManageProject.vendor"/></td>
			      	<td><form:input path="vendor" maxlength="40" cssClass="wid60"/></td>
			        <td><fmt:message key="alManageProject.team"/></td>
			        <td>
			        	<form:input path="team" maxlength="50" cssClass="wid60"/>
			        </td>
			      </tr>
			      <tr>
			      	<td><fmt:message key="alManageProject.supervisorOnAir"/></td>
			      	<td><form:input path="supervisor" maxlength="200" cssClass="wid60"/></td>
			      	<td><fmt:message key="alManageProject.description"/></td>
			        <td>
			        	<form:input path="description" maxlength="200" cssClass="wid60"/>
			        </td>
			      </tr>
      		</c:when>
	  		<c:when test="${type == 'FOLLOW'}">
	  			<tr>
			      	<td><fmt:message key="alManageProject.vendor"/></td>
			      	<td><form:input path="vendor" maxlength="40" cssClass="wid60"/></td>
			        <td><fmt:message key="alManageProject.team"/></td>
			        <td>
			        	<form:input path="team" maxlength="50" cssClass="wid60"/>
			        </td>
			    </tr>
			    <tr>
			      	<td><fmt:message key="alManageProject.supervisor"/></td>
			      	<td><form:input path="supervisor" maxlength="200" cssClass="wid60"/></td>
			        <td><fmt:message key="alManageProject.installationPlanRateOnAir"/></td>
			        <td>
			        	<form:input path="installationPlanRate" maxlength="3" cssClass="wid30"/>
			        	&nbsp;<form:errors path="installationPlanRate" cssClass="error"/>
			        </td>
			      </tr> 
			      <tr>
			     <td><fmt:message key="alManageProject.projectType"/></td>
			      	<td>
				      	<form:select path="projectType" cssClass="wid60">
							<c:forEach var="items" items="${projectTypeList}">
								<c:choose>
								<c:when test="${items.name == projectType}">
								   <option value="${items.name}" selected="selected">${items.value}</option>
								</c:when>
								<c:otherwise>
								   <option value="${items.name}">${items.value}</option>
								</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select>
			      	</td>
			        
			      	<td><fmt:message key="alManageProject.description"/></td>
			        <td colspan="3">
			        	<form:input path="description" maxlength="200" cssClass="wid60"/>
			        </td>
			      </tr> 
			</c:when>
	  </c:choose>
      <tr>
        <td></td>
        <td colspan="3">
            <input class="button" type="submit" name="save" value="<fmt:message key="global.form.luulai"/>" />
            <input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm?type=${type}"'>
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
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"endDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseEndDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

function focusIt()
{
	var startDateError = '<c:out value="${startDateError}"/>';
	var endDateError = '<c:out value="${endDateError}"/>';
	
	if(document.checkform.projectCode.value==""){
		  var mytext = document.getElementById("projectCode");
		  mytext.focus();
		}
	else if(document.checkform.projectName.value==""){
		  var mytext = document.getElementById("projectName");
		  mytext.focus();
		}
	else if(startDateError != "")
	{
		var mytext = document.getElementById("startDate");
	  	mytext.focus();
	}
	else if(endDateError != "")
	{
		var mytext = document.getElementById("endDate");
	  	mytext.focus();
	}
}

onload = focusIt;

</script>
<script type="text/javascript">
var theme = getTheme();
$("#projectCode").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#projectName").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
var type =  "<c:out value='${type}'/>";
if(type == 'FOLLOW')
	$("#description").jqxInput({ width: '84%', height: 20, minLength: 1, theme: theme });
else
	$("#description").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });

$("#vendor").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#supervisor").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#installationPlanRate").jqxInput({ width: '30%', height: 20, minLength: 1, theme: theme });
$("#team").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
//Create a jqxMultile Input
var renderer = function (itemValue, inputValue) {
    var terms = inputValue.split(/,\s*/);
    // remove the current input
    terms.pop();
    // add the selected item
    terms.push(itemValue);
    // add placeholder to get the comma-and-space at the end
    terms.push("");
    var value = terms.join("");
    return value;
};

//Input projectType
${projectTypeList}
$("#projectType").jqxInput({ placeHolder: "", height: 20, width: '60%', theme: theme,
    source: function (query, response) {
        var item = query.split(/,\s*/).pop();
        // update the search query.
        $("#projectType").jqxInput({ query: item });
        response(projectTypeList);
    },
    renderer: renderer
});
var projectType =  "<c:out value='${projectType}'/>";
if(projectType!="")
	$('#projectType').val(projectType);
	
//Input vendor
${vendorList}
$("#vendor").jqxInput({ placeHolder: "", height: 20, width: '60%', theme: theme,
    source: function (query, response) {
        var item = query.split(/,\s*/).pop();
        // update the search query.
        $("#vendor").jqxInput({ query: item });
        response(vendorList);
    },
    renderer: renderer
});
var vendor =  "<c:out value='${vendor}'/>";
if(vendor!="")
	$('#vendor').val(vendor);
	
/* //Create a jqxComboBox
var urlTeam = "${pageContext.request.contextPath}/alarm/al-manage-project/teamList.htm";
// prepare the data
var sourceTeam =
{
     datatype: "json",
     datafields: [
         { name: 'deptValue' },
         { name: 'deptValue' }
     ],
     url: urlTeam,
     async: false
};
 var dataAdapterTeam = new $.jqx.dataAdapter(sourceTeam);
 // Create a jqxComboBox
 $("#team").jqxComboBox({ source: dataAdapterTeam, displayMember: "deptValue", valueMember: "deptValue", width: '60%',height: 20, itemHeight: 30,theme: theme, autoOpen: true });
 var team =  "<c:out value='${team}'/>";
 if(team!=""){
	 $("#team").find('input').val(team);
 } */
 
//Input team
 ${teamList}
 $("#team").jqxInput({ placeHolder: "", height: 20, width: '60%', theme: theme,
     source: function (query, response) {
         var item = query.split(/,\s*/).pop();
         // update the search query.
         $("#team").jqxInput({ query: item });
         response(teamList);
     },
     renderer: renderer
 });
 var team =  "<c:out value='${team}'/>";
 if(team!="")
 	$('#team').val(team);

</script>
