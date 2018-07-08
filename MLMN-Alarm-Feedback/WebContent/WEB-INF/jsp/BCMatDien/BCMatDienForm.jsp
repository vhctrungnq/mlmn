<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>${titleF}</title>
<content tag="heading">${titleF}</content>
<style>
	.ui-multiselect {
		width:160px !important;
	}
	 #dvtTeamProcess {
     visibility: visible !important;
    }
    
     #ungCuuMpd {
     visibility: visible !important;
    }
    #nodeTruyenDan {
     visibility: visible !important;
    }
    #acLow {
     visibility: visible !important;
    }
    
    
</style>
<form:form commandName="vAlRbLossPower" name="checkform" id="myform" method="post" action="form.htm"> 
	<form:hidden path="id"/>
	<table class="simple2"> 
      <tr>
      		<td style="width:100px;"><fmt:message key="qLPhongBan.region"/></td>
			<td style="width:100px;">
		        <form:select path="region" style="width:150px;">
		        		<option value=""></option>
	   					<c:forEach var="items" items="${regionList}">
			              <c:choose>
			                <c:when test="${items.name == region}">
			                    <option value="${items.name}" selected="selected">${items.value}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${items.name}">${items.value}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
	           	</form:select>	
		    </td>
           <td style="width: 120px;"><fmt:message key="vAlRbLossPower.bscid"/><font color="red">(*)</font></td>
           <td style="width: 200px;">
           		<form:input type ="text" path="bscid" maxlength="18" style="width:160px;" rows="3"/>
           		<font color="red"><form:errors path="bscid"/></font>
           </td>
           <td style="width: 120px;"><fmt:message key="vAlRbLossPower.cellid"/><font color="red">(*)</font></td>
           <td colspan="3">
           		<form:input type ="text" path="cellid" maxlength="18" style="width:160px;" rows="3"/>
           		<font color="red"><form:errors path="cellid"/></font>
           </td>
      </tr> 
      <tr>
           <td ><fmt:message key="vAlRbLossPower.sdate"/><font color="red">(*)</font></td>
           <td>
           		<input type ="text"  value="${sdate}" name="sdate" id="sdate" size="20" maxlength="16" width="80px">
	   			 <img alt="calendar" title="Click to choose the start date" id="choosesdate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red">${errorsdate}<form:errors path="sdate"/></font>
	    	</td>
      
           <td ><fmt:message key="vAlRbLossPower.edate"/></td>
           <td style="width: 200px;">
           		<input type ="text"  value="${edate}" name="edate" id="edate" size="20" maxlength="16" width="80px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseedate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	   			<font color="red">${erroredate}<form:errors path="edate"/></font>
	    	 </td>
           <td style="width: 120px;"><fmt:message key="vAlRbLossPower.ddhBaoMd"/></td>
           <td>
           		<input type ="text"  value="${ddhBaoMd}" name="ddhBaoMd" id="ddhBaoMd" size="20" maxlength="16" width="80px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseddhBaoMd" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    	<font color="red">${errorddhBaoMd}<form:errors path="ddhBaoMd"/></font>
	    	
	    	</td>
      </tr> 
      <tr>
           <td><fmt:message key="vAlRbLossPower.mllSdate"/></td>
           <td>
           		<input type ="text"  value="${mllSdate}" name="mllSdate" id="mllSdate" size="20" maxlength="16" width="80px">
	   			 <img alt="calendar" title="Click to choose the start date" id="choosemllSdate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    	<font color="red">${errormllSdate}<form:errors path="mllSdate"/></font>
	    	
	    	</td>
        <td><fmt:message key="vAlRbLossPower.mllEdate"/></td>
           <td>
           		<input type ="text"  value="${mllEdate}" name="mllEdate" id="mllEdate" size="20" maxlength="16" width="80px">
	   			 <img alt="calendar" title="Click to choose the start date" id="choosemllEdate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red">${errormllEdate}<form:errors path="mllEdate"/></font>
	    	</td>
     
           <td><fmt:message key="vAlRbLossPower.ddhBaoMll"/></td>
           <td>
           		<input type ="text"  value="${ddhBaoMll}" name="ddhBaoMll" id="ddhBaoMll" size="20" maxlength="16" width="80px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseddhBaoMll" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red">${errorddhBaoMll}<form:errors path="ddhBaoMll"/></font>
	    	</td>
      </tr> 
	
       <tr>
			<td><fmt:message key="vAlRbLossPower.ungCuuMpd"/></td>
			<td>
				<select name="ungCuuMpd" id="ungCuuMpd" style="width: 160px;height:20px; padding-top: 4px;">
				<option value=""><fmt:message key="global.Chosse"/></option>
           		<c:forEach var="item" items="${ungCuuMpdList}">
					<c:choose>
		                <c:when test="${item.name == ungCuuMpd}">
		                    <option value="${item.name}" selected="selected">${item.value}</option>
		                </c:when>
						<c:otherwise>
							<option value="${item.name}">${item.value}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> 
			</td>
			<td><fmt:message key="vAlRbLossPower.nodeTruyenDan"/></td>
			<td>
				<select name="nodeTruyenDan" id="nodeTruyenDan" style="width: 160px;height:20px; padding-top: 4px;">
				<option value=""><fmt:message key="global.Chosse"/></option>
           		<c:forEach var="item" items="${paraList}">
					<c:choose>
		                <c:when test="${item.name == nodeTruyenDan}">
		                    <option value="${item.name}" selected="selected">${item.value}</option>
		                </c:when>
						<c:otherwise>
							<option value="${item.name}">${item.value}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> 
			</td>
		
			<td><fmt:message key="vAlRbLossPower.acLow"/></td>
			<td>
				<select name="acLow" id="acLow" style="width: 160px;height:20px; padding-top: 4px;">
				<option value=""><fmt:message key="global.Chosse"/></option>
           		<c:forEach var="item" items="${paraList}">
					<c:choose>
		                <c:when test="${item.name == acLow}">
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
           <td><fmt:message key="vAlRbLossPower.dvtTeamProcess"/></td>
           <td>
           <select name="dvtTeamProcess" id="dvtTeamProcess" style="width: 160px;height:20px; padding-top: 4px;">
           		<c:forEach var="item" items="${teamList}">
					<c:choose>
		                <c:when test="${item.deptCode == team}">
		                    <option value="${item.deptCode}" selected="selected">${item.deptCode}</option>
		                </c:when>
						<c:otherwise>
							<option value="${item.deptCode}">${item.deptCode}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> 
	  </td>
      
           <td><fmt:message key="vAlRbLossPower.dvtUserProcess"/></td>
           <td style="border:0;" >
           		<select name="dvtUserProcess" id="dvtUserProcess" multiple="multiple" style="width:160px;"></select>
           </td>
           	<td><fmt:message key="vAlRbLossPower.mch1800"/></td>
			<td>
				<select name="mch1800" id="mch1800" style="width: 160px;height:20px; padding-top: 4px;">
				<option value=""><fmt:message key="global.All"/></option>
           		<c:forEach var="item" items="${paraList}">
					<c:choose>
		                <c:when test="${item.name == mch1800}">
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
           <td><fmt:message key="vAlRbLossPower.description"/></td>
           <td colspan="5"> 
           <textarea style="font-family:tahoma;width:100%; font-size:12px;height:30px;" rows="10"  name="description" id="description" maxlength="250" >${vAlRbLossPower.description}</textarea>	
          </td>
       </tr>
     
       <tr>
           <td></td>
           <td colspan="5">
               <input type="submit" class="button" value="<fmt:message key="button.save"/>" />
           	   <input type="button" class="button" value="<fmt:message key="button.cancel"/>" onClick='window.location="list.htm"'>
					
           </td>
       </tr>
    </table>
</form:form>

<style>
    .error {
    	color: red;
    }
</style> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>

<link type="text/css" rel="Stylesheet" href="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.8.23.custom.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui.min-1.8.9.js"></script>



<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.filter.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/assets/style.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/assets/prettify.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.filter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/assets/prettify.js"></script>
<script type="text/javascript">
    Calendar.setup({
        inputField		:	"sdate",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "choosesdate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
    Calendar.setup({
        inputField		:	"edate",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseedate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
    Calendar.setup({
        inputField		:	"ddhBaoMd",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseddhBaoMd",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
    Calendar.setup({
        inputField		:	"mllSdate",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "choosemllSdate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
    Calendar.setup({
        inputField		:	"mllEdate",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "choosemllEdate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });

    Calendar.setup({
        inputField		:	"ddhBaoMll",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseddhBaoMll",   	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
	
</script>
<script type="text/javascript">

function focusIt()
{
	if(document.checkform.bscid.value==""){
	  var mytext = document.getElementById("bscid");
	  mytext.focus();
	}
}

onload = focusIt;

</script>
<script type="text/javascript">
	$(function(){
		var dvtTeamProcess = $("#dvtTeamProcess").val();
		var dvtUserProcess = '<c:out value="${vAlRbLossPower.dvtUserProcess}"/>';
		var $widget;
		
		if (dvtTeamProcess != null && dvtTeamProcess != '') {

			// Tao multiselect jquery
			$widget = $("#dvtUserProcess").multiselect(), state = true;
			$widget.multiselect('enable');
			
			// Khoi tao dvtUserProcess
			$.getJSON("${pageContext.request.contextPath}/ajax/getUserByDeparment.htm",{dept: dvtTeamProcess,team:''}, function(j){
				var options = '';
				for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].username + '">' + j[i].username+ '</option>';
				}
				$("#dvtUserProcess").html(options);
		
				var dvtUserProcessVar = dvtUserProcess.split(",");
				if (dvtUserProcessVar != null) {
					for (var i=0; i<dvtUserProcessVar.length; i++) {
						$("#dvtUserProcess option[value='" + dvtUserProcessVar[i] + "']").attr('selected', 'selected');
					}
				}
				
				$("#dvtUserProcess").multiselect('refresh');
			});

			
		} else {
			$widget = $("#dvtUserProcess").multiselect(), state = true;
			$widget.multiselect('disable');
		}
		
		$("#dvtTeamProcess").change(function(){
			if ($("#dvtTeamProcess").val() == '' || $("#dvtTeamProcess").val() == null) {
				$widget.multiselect('disable');
			} else {

				/* $("#ngglIdError").text(''); */
				
				$widget.multiselect('enable');

				$.getJSON("${pageContext.request.contextPath}/ajax/getUserByDeparment.htm",{dept: $(this).val(),team:''}, function(j){
					var options = '';
					for (var i = 0; i < j.length; i++) {
						options += '<option value="' + j[i].username + '">' + j[i].username+ '</option>';
					}
					$("#dvtUserProcess").html(options);
					$("#dvtUserProcess").multiselect('refresh');
				});

			}
		});
	});
</script> 