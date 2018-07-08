<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<c:choose>
  <c:when test="${alManageTrackAddEdit == 'N'}">
      
	  <c:choose>
		<c:when test="${type == 'FOLLOW'}">
			<title><fmt:message key="title.alManageTrackFormAdd"/></title>
		  <content tag="heading"><fmt:message key="title.alManageTrackFormAdd"/></content>
		</c:when>
		<c:when test="${type == 'UPGRADE'}">
			<title><fmt:message key="Thêm mới nâng cấp phần mềm"/></title>
			<content tag="heading"><fmt:message key="Dự án nâng cấp phần mềm"/></content>
		</c:when>
	</c:choose>
  </c:when>
  <c:when test="${alManageTrackAddEdit == 'Y'}">
      
	  <c:choose>
		<c:when test="${type == 'FOLLOW'}">
			<title><fmt:message key="title.alManageTrackFormEdit"/></title>
	  		<content tag="heading"><fmt:message key="title.alManageTrackFormEdit"/></content>
		</c:when>
		<c:when test="${type == 'UPGRADE'}">
			<title><fmt:message key="Sửa nâng cấp phần mềm"/></title>
			<content tag="heading"><fmt:message key="Sửa dự án nâng cấp phần mềm"/></content>
		</c:when>
	</c:choose>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>

<form:form commandName="alManageTrack" name="checkform" method="post" action="form.htm"> 
	<div>
    	<form:input path="id" type="hidden" />
    	<input id="type" name="type" value="${type}" type="hidden" />
    </div>
    <table class="simple2">
    	<tr>
    	
    
    		<td><fmt:message key="alManageOnAir.projectName"/></td>
    		<td>
    			<form:select path="projectId" cssClass="wid60">
					<c:forEach var="items" items="${projectIdList}">
						<c:choose>
						<c:when test="${items.id == projectIdCBB}">
						   <option value="${items.id}" selected="selected">${items.projectName}</option>
						</c:when>
						<c:otherwise>
						   <option value="${items.id}">${items.projectName}</option>
						</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select>
    		</td>
    	</tr>
      <tr>
           <td class="wid15 mwid110"><fmt:message key="alManageProject.siteName"/>&nbsp;<font color="red">(*)</font></td>
           <td class="wid35">
		       	<form:input path="siteName" maxlength="450" />
		       	&nbsp;<form:errors path="siteName" cssClass="error"/>
           </td>
           <td class="wid15 mwid110"><fmt:message key="alManageProject.node"/>&nbsp;<font color="red">(*)</font></td>
           <td>
           		<form:input path="node" maxlength="450" />
           		&nbsp;<form:errors path="node" cssClass="error"/>
           </td>       
      </tr>
      <tr>
      	<td><fmt:message key="alManageProject.team"/></td>
      	<td>
		      <form:input path="team" maxlength="50" />
          </td>
          <td><fmt:message key="alManageProject.finishRate"/></td>
          <td>
          		<form:input path="finishRate" maxlength="3" />
          		&nbsp;<form:errors path="finishRate" cssClass="error"/>
          </td> 
      </tr>
      
      <c:choose>
		<c:when test="${type == 'FOLLOW'}">
		<tr>
           <td><fmt:message key="alManageProject.deliveryPlanDate"/></td>
           <td>
				<input id="deliveryPlanDateStart" name="deliveryPlanDateStart" value="${deliveryPlanDateStart}" class="wid20" maxlength="20"/>
	 			<img alt="calendar" title="Click to choose the delivery plan start date" id="chooseDeliveryPlanDateStart" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	 			&nbsp;<form:errors path="deliveryPlanDateStart" cssClass="error"/>&nbsp;
	 			<fmt:message key="alManageProject.to"/>&nbsp;
	 			<input id="deliveryPlanDateEnd" name="deliveryPlanDateEnd" value="${deliveryPlanDateEnd}" class="wid20" maxlength="20"/>
	 			<img alt="calendar" title="Click to choose the delivery plan end date" id="chooseDeliveryPlanDateEnd" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	 			&nbsp;<form:errors path="deliveryPlanDateEnd" cssClass="error"/>
           </td>
           <td><fmt:message key="alManageProject.deliveryPlanRate"/></td>
           <td>
				<form:input path="deliveryPlanRate" maxlength="3" />
				&nbsp;<form:errors path="deliveryPlanRate" cssClass="error"/>
           </td>       
      </tr>
      <tr>
           <td><fmt:message key="alManageProject.deliveryPlanStatus"/></td>
           <td>
		       	<form:input path="deliveryPlanStatus" maxlength="200" />
           </td>
           <td><fmt:message key="alManageProject.deliveryPlanVendorStaff"/></td>
           <td>
           		<form:input path="deliveryPlanVendorStaff" maxlength="200" />
           </td>       
      </tr>  
      <tr>
           <td><fmt:message key="alManageProject.deliveryPlanVmsStaff"/></td>
           <td>
           		<form:input path="deliveryPlanVmsStaff" maxlength="200" />
           </td>
	   	 	<td><fmt:message key="alManageProject.deliveryPlanDescription"/></td>
        	<td>
        		<form:input path="deliveryPlanDescription" maxlength="200" />
        	</td>
      </tr>
			 <tr>
	           <td><fmt:message key="alManageProject.installationPlanDate"/></td>
	           <td>
			       	<input id="installationPlanDateStart" name="installationPlanDateStart" value="${installationPlanDateStart}" class="wid20" maxlength="20"/>
		 			<img alt="calendar" title="Click to choose the installation plan start date" id="chooseInstallationPlanDateStart" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
		 			&nbsp;<form:errors path="installationPlanDateStart" cssClass="error"/>&nbsp;
		 			<fmt:message key="alManageProject.to"/>&nbsp;
		 			<input id="installationPlanDateEnd" name="installationPlanDateEnd" value="${installationPlanDateEnd}" class="wid20" maxlength="20"/>
		 			<img alt="calendar" title="Click to choose the installation plan end date" id="chooseInstallationPlanDateEnd" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
		 			&nbsp;<form:errors path="installationPlanDateEnd" cssClass="error"/>
	           </td>
	           <td><fmt:message key="alManageProject.installationPlanRate"/></td>
	           <td>
	           		<form:input path="installationPlanRate" maxlength="3" />
	           		&nbsp;<form:errors path="installationPlanRate" cssClass="error"/>
	           </td>       
	      </tr>
	      <tr>
	           <td><fmt:message key="alManageProject.installationPlanVendorStaff"/></td>
	           <td>
			       	<form:input path="installationPlanVendorStaff" maxlength="200" />
	           </td>
	           <td><fmt:message key="alManageProject.installationPlanVmsStaff"/></td>
	           <td>
	           		<form:input path="installationPlanVmsStaff" maxlength="220" />
	           </td>       
	      </tr>
	      <tr>
	           <td><fmt:message key="alManageProject.installationPlanStatus"/></td>
	           <td>
			       	<form:input path="installationPlanStatus" maxlength="200" />
	           </td>
	           <td><fmt:message key="alManageProject.installationPlanDescription"/></td>
	           <td>
	           		<form:input path="installationPlanDescription" maxlength="200" />
	           </td>       
	      </tr>
	      <tr>
	           <td><fmt:message key="alManageProject.commissioningPlan"/></td>
	           <td>
	           		<input id="commissioningPlanStart" name="commissioningPlanStart" value="${commissioningPlanStart}" class="wid20" maxlength="20"/>
		 			<img alt="calendar" title="Click to choose the commissioning plan start date" id="chooseCommissioningPlanStart" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
		 			&nbsp;<form:errors path="commissioningPlanStart" cssClass="error"/>&nbsp;
		 			<fmt:message key="alManageProject.to"/>&nbsp;
		 			<input id="commissioningPlanEnd" name="commissioningPlanEnd" value="${commissioningPlanEnd}" class="wid20" maxlength="20"/>
		 			<img alt="calendar" title="Click to choose the commissioning plan end date" id="chooseCommissioningPlanEnd" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
		 			&nbsp;<form:errors path="commissioningPlanEnd" cssClass="error"/>
	           </td>
		   	 	<td><fmt:message key="alManageProject.integrationPlan"/></td>
	        	<td>
	        		<input id="integrationPlanStart" name="integrationPlanStart" value="${integrationPlanStart}" class="wid20" maxlength="20"/>
	 				<img alt="calendar" title="Click to choose the integration plan start date" id="chooseIntegrationPlanStart" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	 				&nbsp;<form:errors path="integrationPlanStart" cssClass="error"/>&nbsp;
		 			<fmt:message key="alManageProject.to"/>&nbsp;
		 			<input id="integrationPlanEnd" name="integrationPlanEnd" value="${integrationPlanEnd}" class="wid20" maxlength="20"/>
	 				<img alt="calendar" title="Click to choose the integration plan end date" id="chooseIntegrationPlanEnd" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	 				&nbsp;<form:errors path="integrationPlanEnd" cssClass="error"/>
	        	</td>
	      </tr>
	      <tr>
	           <td><fmt:message key="alManageProject.acceptancePlan"/></td>
	           <td>
			       	<input id="acceptancePlanStart" name="acceptancePlanStart" value="${acceptancePlanStart}" class="wid20" maxlength="20"/>
	 				<img alt="calendar" title="Click to choose the acceptance plan start date" id="chooseAcceptancePlanStart" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	 				&nbsp;<form:errors path="acceptancePlanStart" cssClass="error"/>&nbsp;
		 			<fmt:message key="alManageProject.to"/>&nbsp;
		 			<input id="acceptancePlanEnd" name="acceptancePlanEnd" value="${acceptancePlanEnd}" class="wid20" maxlength="20"/>
	 				<img alt="calendar" title="Click to choose the acceptance plan end date" id="chooseAcceptancePlanEnd" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	 				&nbsp;<form:errors path="acceptancePlanEnd" cssClass="error"/>
	           </td>
	           <td><fmt:message key="alManageProject.installationSupervisor"/></td>
	           <td>
	           		<form:input path="supervisor" maxlength="80" />
	           </td>       
	      </tr>
	      <tr>
	           <td><fmt:message key="alManageProject.commissioningResponsible"/></td>
	           <td>
			       	<form:input path="commissioningResponsible" maxlength="800" />
	           </td>
	           <td><fmt:message key="alManageProject.atnd"/></td>
	           <td>
	           		<form:input path="atnd" maxlength="480" />
	           </td>       
	      </tr>
	      <tr>
	           <td><fmt:message key="alManageProject.manager"/></td>
	           <td>
			       	<form:input path="manager" maxlength="200" />
	           </td>
	           <td><fmt:message key="alManageProject.supporter"/></td>
	           <td>
	           		<form:input path="supporter" maxlength="200" />
	           </td>       
	      </tr>
		</c:when>
		<c:otherwise>
				<tr>
		           <td><fmt:message key="Date"/></td>
		           <td>
						<input id="deliveryPlanDateStart" name="deliveryPlanDateStart" value="${deliveryPlanDateStart}" class="wid20" maxlength="20"/>
			 			<img alt="calendar" title="Click to choose the delivery plan start date" id="chooseDeliveryPlanDateStart" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			 			&nbsp;<form:errors path="deliveryPlanDateStart" cssClass="error"/>&nbsp;
			 			<fmt:message key="alManageProject.to"/>&nbsp;
			 			<input id="deliveryPlanDateEnd" name="deliveryPlanDateEnd" value="${deliveryPlanDateEnd}" class="wid20" maxlength="20"/>
			 			<img alt="calendar" title="Click to choose the delivery plan end date" id="chooseDeliveryPlanDateEnd" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			 			&nbsp;<form:errors path="deliveryPlanDateEnd" cssClass="error"/>
		           </td>
		           <td><fmt:message key="Plan Rate"/></td>
		           <td>
						<form:input path="deliveryPlanRate" maxlength="3" />
						&nbsp;<form:errors path="deliveryPlanRate" cssClass="error"/>
		           </td>       
		      </tr>
		      <tr>
		           <td><fmt:message key="Status"/></td>
		           <td>
				       	<form:input path="deliveryPlanStatus" maxlength="200" />
		           </td>
		           <td><fmt:message key="Vendor"/></td>
		           <td>
		           		<form:input path="deliveryPlanVendorStaff" maxlength="200" />
		           </td>       
		      </tr>  
		      <tr>
		           <td><fmt:message key="VMS"/></td>
		           <td>
		           		<form:input path="deliveryPlanVmsStaff" maxlength="200" />
		           </td>
			   	 	<td><fmt:message key="Software Version"/></td>
		        	<td>
		        		<form:input path="deliveryPlanDescription" maxlength="200" />
		        	</td>
		      </tr>
		</c:otherwise>
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
<<script type="text/javascript">
<!--
$('#projectId').change(function(){

	$.ajax({
	  url: "${pageContext.request.contextPath}/alarm/al-manage-track/ajax/loadprojectname.htm",
	  beforeSend: function( ) {
		  $('#load').remove();
			$('.body-content').append('<span id="load">LOADING...</span>');
			$('#load').fadeIn('normal', loadContent);

			function loadContent() {
				$('#result').load('', '', showNewContent());
			}
			
			function showNewContent() {
				$('#result').show('normal', hideLoader());
			}
			
			function hideLoader() {
				$('#load').fadeOut('normal');
			}
	  },
	  data:{system: $('#projectId').val()}}).done(function( j ) {
		  var options = '';
		  for (var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i].id + '">' + j[i].projectName+ '</option>';
			}
		$("#projectName").html(options);
		$('#projectName option:first').attr('selected', 'selected');
	    
	  });
	  
	
});
//-->
</script>
<c:choose>
		<c:when test="${type == 'FOLLOW'}">
		<script type="text/javascript">
		Calendar.setup({
		    inputField		:	"installationPlanDateStart",	// id of the input field
		    ifFormat		:	"%d/%m/%Y",   	// format of the input field
		    button			:   "chooseInstallationPlanDateStart",  	// trigger for the calendar (button ID)
		    singleClick		:   false					// double-click mode
		});

		Calendar.setup({
		    inputField		:	"installationPlanDateEnd",	// id of the input field
		    ifFormat		:	"%d/%m/%Y",   	// format of the input field
		    button			:   "chooseInstallationPlanDateEnd",  	// trigger for the calendar (button ID)
		    singleClick		:   false					// double-click mode
		});

		Calendar.setup({
		    inputField		:	"commissioningPlanStart",	// id of the input field
		    ifFormat		:	"%d/%m/%Y",   	// format of the input field
		    button			:   "chooseCommissioningPlanStart",  	// trigger for the calendar (button ID)
		    singleClick		:   false					// double-click mode
		});

		Calendar.setup({
		    inputField		:	"commissioningPlanEnd",	// id of the input field
		    ifFormat		:	"%d/%m/%Y",   	// format of the input field
		    button			:   "chooseCommissioningPlanEnd",  	// trigger for the calendar (button ID)
		    singleClick		:   false					// double-click mode
		});

		Calendar.setup({
		    inputField		:	"integrationPlanStart",	// id of the input field
		    ifFormat		:	"%d/%m/%Y",   	// format of the input field
		    button			:   "chooseIntegrationPlanStart",  	// trigger for the calendar (button ID)
		    singleClick		:   false					// double-click mode
		});

		Calendar.setup({
		    inputField		:	"integrationPlanEnd",	// id of the input field
		    ifFormat		:	"%d/%m/%Y",   	// format of the input field
		    button			:   "chooseIntegrationPlanEnd",  	// trigger for the calendar (button ID)
		    singleClick		:   false					// double-click mode
		});

		Calendar.setup({
		    inputField		:	"acceptancePlanStart",	// id of the input field
		    ifFormat		:	"%d/%m/%Y",   	// format of the input field
		    button			:   "chooseAcceptancePlanStart",  	// trigger for the calendar (button ID)
		    singleClick		:   false					// double-click mode
		});

		Calendar.setup({
		    inputField		:	"acceptancePlanEnd",	// id of the input field
		    ifFormat		:	"%d/%m/%Y",   	// format of the input field
		    button			:   "chooseAcceptancePlanEnd",  	// trigger for the calendar (button ID)
		    singleClick		:   false					// double-click mode
		});
		</script>
		</c:when>
</c:choose>
<script type="text/javascript">

Calendar.setup({
    inputField		:	"deliveryPlanDateStart",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseDeliveryPlanDateStart",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});
 
Calendar.setup({
    inputField		:	"deliveryPlanDateEnd",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseDeliveryPlanDateEnd",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});
 


function focusIt()
{
	var finishRateError = '<c:out value="${finishRateError}"/>';
	var deliveryPlanDateError = '<c:out value="${deliveryPlanDateError}"/>';
	var installationPlanDateError = '<c:out value="${installationPlanDateError}"/>';
	var commissioningPlanError = '<c:out value="${commissioningPlanError}"/>';
	var integrationPlanError = '<c:out value="${integrationPlanError}"/>';
	var acceptancePlanError = '<c:out value="${acceptancePlanError}"/>';
	
	if(document.checkform.siteName.value==""){
		  var mytext = document.getElementById("siteName");
		  mytext.focus();
		}
	else if(document.checkform.node.value==""){
		var mytext = document.getElementById("node");
		  mytext.focus();
	}
	else if(finishRateError !=""){
		  var mytext = document.getElementById("finishRate");
		  mytext.focus();
		}
	else if(deliveryPlanDateError !=""){
		  var mytext = document.getElementById("deliveryPlanDate");
		  mytext.focus();
		}
	else if(installationPlanDateError !=""){
		  var mytext = document.getElementById("installationPlanDate");
		  mytext.focus();
		}
	else if(commissioningPlanError != "")
	{
		var mytext = document.getElementById("commissioningPlan");
	  	mytext.focus();
	}
	else if(integrationPlanError != "")
	{
		var mytext = document.getElementById("integrationPlan");
	  	mytext.focus();
	}
	else if(acceptancePlanError != "")
	{
		var mytext = document.getElementById("acceptancePlan");
	  	mytext.focus();
	}
}

onload = focusIt;

</script>
<script type="text/javascript">
var theme = getTheme();
$("#siteName").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#node").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#team").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#finishRate").jqxInput({ width: '30%', height: 20, minLength: 1, theme: theme });
$("#deliveryPlanRate").jqxInput({ width: '30%', height: 20, minLength: 1, theme: theme });
$("#deliveryPlanStatus").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#deliveryPlanVendorStaff").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#deliveryPlanVmsStaff").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#deliveryPlanDescription").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#installationPlanRate").jqxInput({ width: '30%', height: 20, minLength: 1, theme: theme });
$("#installationPlanVendorStaff").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#installationPlanVmsStaff").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#installationPlanStatus").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#installationPlanDescription").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#supervisor").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#commissioningResponsible").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#atnd").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#manager").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#supporter").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
</script>
