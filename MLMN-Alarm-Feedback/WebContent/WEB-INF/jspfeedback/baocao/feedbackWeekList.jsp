<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title><fmt:message key="baoCaoFeedback.baoCaoFeedbackTuan"/></title>
<content tag="heading"><fmt:message key="baoCaoFeedback.baoCaoFeedbackTuan"/></content>

<form:form id="filterController" method="post" action="${function}.htm">
	<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr>
			<td>
				<fmt:message key="baoCaoSoLuongPA.tuNgay"/>&nbsp;
				<input type="text" id="startDate" name="startDate" value="${startDate}" style="width: 8%" maxlength="20"/>
   				<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
				<fmt:message key="baoCaoSoLuongPA.denNgay"/>&nbsp;
				<input type="text" id="endDate" name="endDate" value="${endDate}" style="width: 8%" maxlength="20"/>
   				<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
   				<fmt:message key="qLThongTinPhanAnh.loaiPhanAnh"/>&nbsp;
				<select id="fbType" name="fbType" style="width: 15%">
					<option value="">--Tất cả--</option>
	 				<c:forEach var="items" items="${loaiPAList}">
		              	<c:choose>
		                <c:when test="${items.code == loaiPACBB}">
		                    <option value="${items.code}" selected="selected">${items.name}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.code}">${items.name}</option>
		                </c:otherwise>
		              	</c:choose>
			    	</c:forEach>
	          	</select>&nbsp;
	          	<c:if test="${Center=='TT6' }">
	          	<spring:message code="qLThongTinPhanAnh.fbSendTelecom"/>&nbsp;
	          	
	          	<select id="fbSendTelecom" name="fbSendTelecom" >
					<option value="">--Tất cả--</option>
          				<c:forEach var="status" items="${statusList}">
			              	<c:choose>
			                <c:when test="${status.name == fbSendTelecom}">
			                    <option value="${status.name}" selected="selected">${status.value}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${status.name}">${status.value}</option>
			                </c:otherwise>
			              	</c:choose>
				    	</c:forEach>
	          	</select>&nbsp;
	          	</c:if>
				<input class="button" type="submit" name="filter" value="<fmt:message key="global.form.timkiem"/>" />
			</td>
		</tr>
		<tr>
			<td style="padding-top:10px;"><b><i>Số lượng Feedback trong tuần: ${totalFb} &nbsp;FB</i></b></td>
		</tr>
		<tr>
			<td>
				<div id="chartFeedbackWeek" style="width: 98%; margin: 1em auto"></div>
			</td>
		</tr>	
	</table>
	
	<div style="width:100%;overflow:auto; " class="tableStandar">
					<display:table name="${gridWeekList}"  id="item" requestURI="" pagesize="50" sort="external" export="true">
						<display:column class="centerColumnMana" titleKey="global.list.No"> <c:out value="${item_rowNum}"/> </display:column>
						<display:column property="networkType" titleKey="qLThongTinPhanAnh.loaiMang"/>
						<display:column property="fbName" titleKey="qLThongTinPhanAnh.loaiPhanAnh" />
						<display:column property="fbContent" titleKey="qLThongTinPhanAnh.noiDungPhanAnh"/>
						<display:column class="rightColumnMana" property="qty" titleKey="qLThongTinPhanAnh.soLuong"/>
						<display:column property="processStatus" titleKey="qLThongTinPhanAnh.processStatus"/>
						<display:column property="processHandleMethod" titleKey="qLThongTinPhanAnh.processHandleMethod"/>
						<display:column property="processMotional" titleKey="qLThongTinPhanAnh.processMotional"/>
						    			
						<display:setProperty name="export.csv.include_header" value="true" />
						<display:setProperty name="export.excel.include_header" value="true" />
						<display:setProperty name="export.xml.include_header" value="true" />
						<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
						<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
						<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
							
		</display:table>
	</div>
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
  var mytext = document.getElementById("fbType");
  mytext.focus();
}

onload = focusIt;

</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/highchart2.3.3/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/highchart2.3.3/modules/exporting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/themes/grid.js"></script>
${chartdivScript}