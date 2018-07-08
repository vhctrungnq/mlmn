<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxwindow.js"></script>

<title>${titleForm}</title>
<content tag="heading">${titleForm}</content> 	   
<form:form commandName="record" id="myform" method="post" action="compareForm.htm"> 
	<form:hidden path="id"/>
	<form:hidden path="idLog"/>
	<form:hidden path="alarmMappingId"/>
	<form:hidden path="alarmMappingName"/>
	<input type="hidden" name="type" id="type" value="${type}">
	<table class="simple2"> 
      <tr>
      		<td style="width: 120px;"><fmt:message key="comparePower.bscid"/></td>
			<td style="width: 200px;">
				<form:hidden path='bscid'/><span style="font-weight: bold;">${record.bscid}</span>
			</td>
			<td style="width: 120px;"><fmt:message key="comparePower.site"/></td>
			<td style="width: 200px;">
				<form:hidden path='cellid'/><span style="font-weight: bold;">${record.cellid}</span>
			</td>
			<td style="width: 100px;"><fmt:message key="comparePower.siteName"/></td>
			<td>
				<form:hidden path='siteName'/><span style="font-weight: bold;">${record.siteName}</span>
			</td>
      </tr> 
		
         <tr>
       		<td><fmt:message key="comparePower.province"/></td>
			<td>
				<form:hidden path='province'/><span style="font-weight: bold;">${record.province}</span>
			</td>
			<td><fmt:message key="comparePower.district"/></td>
			<td>
				<form:hidden path="district" /><span style="font-weight: bold;">${record.district}</span>
			</td>
			<td><fmt:message key="comparePower.dept"/></td>
			<td>
				<form:hidden path="dept"/><span style="font-weight: bold;">${record.dept}</span>
			</td>
		</tr>
		<tr>
      		<td><fmt:message key="comparePower.team"/></td>
			<td>
				<form:hidden path="team"/><span style="font-weight: bold;">${record.team}</span>
			</td>
			
			<td><fmt:message key="comparePower.groups"/></td>
			<td colspan="3">
				<form:hidden path="groups"/><span style="font-weight: bold;">${record.groups}</span>
			</td>
			
      </tr>  
      <%--  <tr>
       		<td><fmt:message key="comparePower.alarmMappingId"/></td>
			<td>
				<form:hidden path='alarmMappingId'/><span style="font-weight: bold;">${record.alarmMappingId}</span>
			</td>
			<td><fmt:message key="comparePower.alarmMappingName"/></td>
			<td colspan="3">
				<form:hidden path="alarmMappingName" /><span style="font-weight: bold;">${record.alarmMappingName}</span>
			</td>
			
		</tr> --%>
      <tr>
           <td><fmt:message key="comparePower.sdate"/></td>
			<td style="width:150px">
				<input type ="hidden" id='sdate' name='sdate' value='${sdate}'/><span style="font-weight: bold;">${sdate}</span>
			</td>
			<td><fmt:message key="comparePower.edate"/></td>
			<td style="width:150px" colspan="3">
				<input type ="hidden" id='edate' name='edate' value='${edate}'/><span style="font-weight: bold;">${edate}</span>
			</td>
			
         </tr>   
      <tr>
      		<td><fmt:message key="comparePower.shiftSdate"/></td>
			<td > 
				<div><input type ="text"  value="${shiftSdate}" name="shiftSdate" id="shiftSdate" size="17" maxlength="16" style="width:100px">
			   	<img alt="calendar" title="Click to choose the to date" id="chooseshiftSdate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</div>
				<font color="red">${errorshiftSdate}<form:errors path="shiftSdate"/></font>
				<!-- <div id='edate'></div> -->
			</td>
      		<td><fmt:message key="comparePower.shiftEdate"/></td>
			<td >
				<div><input type ="text"  value="${shiftEdate}" name="shiftEdate" id="shiftEdate" size="17" maxlength="16" style="width:100px">
			   	<img alt="calendar" title="Click to choose the ack time" id="chooseshiftEdate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</div>
				<font color="red">${errorshiftEdate}<form:errors path="shiftEdate"/></font><!-- <div id='ackTime'></div> -->
			</td>
			<td><fmt:message key="comparePower.assess"/></td>
           <td>
				<form:input type="text" path="assess"  id ="assess" />
				<font color="red">${errorassess}<form:errors path="assess"/></font>
		   </td>
       </tr>   
       
		<tr>
           <td><fmt:message key="comparePower.description"/></td>
           <td colspan="5">
				<form:input type="text" path="description"  id ="description" />
				<font color="red">${errordescription}<form:errors path="description"/></font>
		   </td>
       </tr>
     
      <tr>
           <td></td>
           <td colspan="5">
               	<input type="submit" class="button" id="submit" value="<fmt:message key="global.form.luulai"/>" />
           	  	<input type="button" class="button" id="btCancel" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="compareList.htm?type=${type}"'>
		 </td>
       </tr>
    </table>

</form:form>


<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script type="text/javascript">
Calendar.setup({
    inputField		:	"shiftSdate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
    button			:   "chooseshiftSdate",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 

Calendar.setup({
    inputField		:	"shiftEdate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
    button			:   "chooseshiftEdate",   	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 	
</script>
<script type="text/javascript">
var theme =  getTheme();
$('#btSave').jqxButton({ theme: theme });
$('#btCancel').jqxButton({ theme: theme });   
$('#submit').jqxButton({ theme: theme });   

$(document).ready(function(){
	//input description
    $("#description").jqxInput({height: 25, width: '100%', minLength: 0, maxLength: 350, theme: theme});
    $("#assess").jqxInput({ height: 25, width: '100%', minLength: 0, maxLength: 150, theme: theme});
    
});
</script>