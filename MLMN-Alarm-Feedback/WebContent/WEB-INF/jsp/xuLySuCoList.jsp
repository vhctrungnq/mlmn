<%@ include file="/includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript"src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxwindow.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxdropdownbutton.js"></script>	
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxtree.js"></script>	
<div>
	<input id="strWhere" name="strWhere" value="" type="hidden" /> 
	<input id="sortfield" name="sortfield" value="" type="hidden" /> 
	<input id="sortorder" name="sortorder" value="" type="hidden" />
</div>
<content tag="heading">${title } </content>	
<br>
<div >
	<form:form commandName="filter" method="post"
				action="list.htm?sumLevel=${sumLevel }">
				<table >
				<tr>				
			 <td style = "width:130px; padding-right:15px;">	 
				<fmt:message key = "general.region" />	 				
				<select name="region" id = "region" >
		        	<option value="">Tất cả</option>
			        <c:forEach var="item" items="${regionList}">
		              <c:choose>
		                <c:when test="${item == region}">
		                    <option value="${item}" selected="selected">${item}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${item}">${item}</option>
		                </c:otherwise>
		              </c:choose>
				    </c:forEach>
		    	</select>	
			   </td>    
			   
			   <td style ="width:150px; padding-right:10px;"><fmt:message key="general.department" /> </td>
			   <td style ="width:300px;">
			  
				<select name="dept" id = "dept" >
		        	<option value="">Tất cả</option>
			        <c:forEach var="item" items="${deptList}">
		              <c:choose>
		                <c:when test="${item == dept}">
		                    <option value="${item}" selected="selected">${item}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${item}">${item}</option>
		                </c:otherwise>
		              </c:choose>
				    </c:forEach>
		    	</select>	
			   </td>   
   				<c:choose>
					
					<c:when test="${sumLevel== 'month'}">
	                   	<td style="width:50px">Từ tháng</td>
						<td style="width:130px">
							<select name="start" id="start" onchange="xl()">
		            				<c:forEach var="month" items="${monthList}">
							              <c:choose>
							                <c:when test="${month == start}">
							                    <option value="${month}" selected="selected">${month}</option>
							                </c:when>
							                <c:otherwise>
							                    <option value="${month}">${month}</option>
							                </c:otherwise>
							              </c:choose>
							    </c:forEach>
				              </select>
				             &nbsp;Năm <input value="${syear}" name="syear" id="syear" size="4" maxlength="4">
				         </td>
						<td style="width:70px">Đến tháng</td>
						<td style="width:130px">
							<select name="end" id="end" onchange="xl()">
	            				<c:forEach var="month" items="${monthList}">
						              <c:choose>
						                <c:when test="${month == end}">
						                    <option value="${month}" selected="selected">${month}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${month}">${month}</option>
						                </c:otherwise>
						              </c:choose>
							    </c:forEach>
				              </select>
				             &nbsp;Năm <input value="${eyear}" name="eyear" id="eyear" size="4" maxlength="4">
						</td>
	                </c:when>
	                <c:when test = "${sumLevel == 'week' }">
						<td style="width:50px">Từ tuần</td>
						<td style="width:130px">
								<input value="${start}" name="start" id="start" size="2">
								<img alt="calendar" titleKey="Click to choose the start week number" id="chooseStartWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
		            			&nbsp;Năm <input value="${syear}" name="syear" id="syear" size="4" maxlength="4">
		            	</td>
						<td style="width:70px">Đến tuần</td>
						<td style="width:130px">
								<input value="${end}" name="end" id="end" size="2">
								<img alt="calendar" titleKey="Click to choose the start week number" id="chooseEndWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
		            			&nbsp;<input value="${eyear}" name="eyear" id="eyear" size="4" maxlength="4">
						</td>
					</c:when>
					<c:when test = "${sumLevel == 'year' }">
						<td style="width:50px">Từ năm</td>
						<td style="width:130px">
								 <input value="${syear}" name="syear" id="syear" size="4" maxlength="4">
		            	</td>
						<td style="width:70px">Đến năm</td>
						<td style="width:130px">
								Năm <input value="${eyear}" name="eyear" id="eyear" size="4" maxlength="4">
						</td>
					</c:when>
					<c:otherwise>
						<td style ="width:60px;" ><fmt:message key="general.startTime"/> </td>
						<td >
							<input type="text" id="start" name="start" value="${start}" size = "15" style = "width:100px;"/>
			           		<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer; " src="${pageContext.request.contextPath}/images/calendar.png"/>
			           	</td>
						<td style ="width:60px; padding-left:20px;"><fmt:message key="general.endTime"/> </td>
						<td >
							<input type="text" id="end" name="end" value="${end}" size = "15" style = "width:100px;"/>
  								<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
						</td>
					</c:otherwise>
					</c:choose>
					<td style="padding-left: 10px;">
							<input class="button" type="submit" id="submit" value="<fmt:message key="global.form.timkiem"/>" />
					</td>
					</tr>
				</table>
			</form:form>
</div>			
<br>
<div id="jqxgrid"></div>
<div id='Menu'>
 <ul>
    
     <li><fmt:message key="global.button.exportExcel"/></li>
 </ul>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" /> 
<script type="text/javascript">
var func = '<c:out value='${sumLevel}'/>'
if (func=='day'){
	Calendar.setup({
	    inputField		:	"start",	// id of the input field
	    ifFormat		:	"%d/%m/%Y",   	// format of the input field
	    button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
	    singleClick		:   false					// double-click mode
	});

	Calendar.setup({
	    inputField		:	"end",	// id of the input field
	    ifFormat		:	"%d/%m/%Y",   	// format of the input field
	    button			:   "chooseEndDate",  	// trigger for the calendar (button ID)
	    singleClick		:   false					// double-click mode
	});
}
else if (func=='week')
{
	Calendar.setup({
	    inputField		:	"start",	// id of the input field
	    ifFormat		:	"%W",   	// format of the input field
	    button			:   "chooseStartWeek",  	// trigger for the calendar (button ID)
	    singleClick		:   false					// double-click mode
	});
	Calendar.setup({
	    inputField		:	"end",	// id of the input field
	    ifFormat		:	"%W",   	// format of the input field
	    button			:   "chooseEndWeek",  	// trigger for the calendar (button ID)
	    singleClick		:   false					// double-click mode
	});
}
</script> 

<script type="text/javascript">
	var theme = getTheme();
	${gridManage}
	$('#submit').jqxButton({ theme: theme });
//    var exportFileName =  "<c:out value='${exportFileName}'/>";
$	("#Menu").on('itemclick', function (event) {
		if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$("#jqxgrid").jqxGrid('exportdata', 'xls', 'Chỉ tiêu cảnh báo');
	    }	 
	});
</script>