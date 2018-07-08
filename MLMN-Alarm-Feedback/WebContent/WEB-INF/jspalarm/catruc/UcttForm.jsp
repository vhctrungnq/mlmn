<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxtreegrid.js"></script>   --%>
<title>${title}</title>
<content tag="heading">${title}</content> 	
<div class="ui-tabs-panel">
	<form:form commandName="filter" method="post" action="${function}.htm">
		<table >
			<tr> 
					<td style="width:50px">Ngày</td>
					<td style="width:90px">
						<input type ="text"  value="${day}" name="day" id="day" size="10" maxlength="10" style="width:70px">
			   			 <img alt="calendar" title="Click to choose the from date" id="chooseSDate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
					</td>
					<td>Khu vực</td>
					<td>
				        <div id='region'></div>
			        </td>
				
					<td style="padding-left: 5px;">
						<input class="button" type="submit" id="button" value="<fmt:message key="global.form.timkiem"/>" />
					</td>
			</tr>
		</table>
	</form:form>
</div>

<div id="grid"></div>
<div id="menuJQ">
      <ul>
  		<li><fmt:message key="global.button.exportExcel"/></li>
      </ul>
</div>


<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>

<script type="text/javascript">
	Calendar.setup({
	    inputField		:	"day",	// id of the input field
	    ifFormat		:	"%d/%m/%Y",   	// format of the input field
	    button			:   "chooseSDate",  	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	}); 
	
</script>
<script type="text/javascript">
$(document).ready(function(){
	var theme =  getTheme();
	${grid}
	
	$('#menuJQ').on('itemclick', function (event) {
	    var args = event.args;
	    var exportFileName = '<c:out value='${exportFileName}'/>';
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$('#grid').jqxGrid('exportdata', 'xls', exportFileName);
	    }
	});
	//combobox region
	var urlRegion = "${pageContext.request.contextPath}/ajax/getRegionList.htm";
	var sourceRegion =
	{
	   datatype: "json",
	   url: urlRegion,
	   datafields: [
	                 { name: 'name'},
	                 { name: 'value'}
	             ],
	    async: false
	};
	var dataAdapterregion = new $.jqx.dataAdapter(sourceRegion);
	$("#region").jqxDropDownList({source: dataAdapterregion, displayMember: "value", valueMember: "name",checkboxes: true, width: 120, autoDropDownHeight: true, theme: theme,enableHover: true });           
	
	var cbregion = '<c:out value="${region}"/>';
	// alert(dept);
	if(cbregion=="")
		$('#region').val('Choose');
	else
	{
		var regionVar = cbregion.split(",");
		if (regionVar != null) {
			for (var i=0; i<regionVar.length; i++) {
				$("#region").jqxDropDownList('checkItem', regionVar[i] ); 
			}
		}
	} 
});
</script>
