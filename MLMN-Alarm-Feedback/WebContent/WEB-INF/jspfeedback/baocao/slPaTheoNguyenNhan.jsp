<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title><fmt:message key="baoCaoFeedback.baoCaoSoLuongPaTheoNN"/></title>
<content tag="heading"><fmt:message key="baoCaoFeedback.baoCaoSoLuongPaTheoNN"/></content>

<form:form id="filterController" method="post" action="list.htm">
	<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr>
			<td>
					<fmt:message key="baoCaoSoLuongPA.tuNgay"/>&nbsp;
					<input type="text" id="startDate" name="startDate" value="${startDate}" style="width: 8%" maxlength="20"/>
   					<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
					<fmt:message key="baoCaoSoLuongPA.denNgay"/>&nbsp;
					<input type="text" id="endDate" name="endDate" value="${endDate}" style="width: 8%" maxlength="20"/>
   					<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
   					<c:if test="${Center=='TT6' }">
   					<spring:message code="qLThongTinPhanAnh.fbSendTelecom"/>&nbsp; 
   					<select id="fbSendTelecom" name="fbSendTelecom" id="fbSendTelecom">
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
        			</select> &nbsp;
        			</c:if>
   					<input class="button" type="submit" name="filter" value="<fmt:message key="global.form.timkiem"/>" />
			</td>
		</tr>
		<tr>
			<td>
				<div style="width:100%;overflow:auto; ">
					<display:table name="${soLuongFBList}" class="simple2" id="item" requestURI="" pagesize="50" sort="external" export="true">
						<display:column class="centerColumnMana" titleKey="global.list.No"> <c:out value="${item_rowNum}"/> </display:column>
						<display:column property="fbDate" format="{0,date,dd/MM/yyyy}" titleKey="baoCaoSoLuongPA.ngay"/>	
						<display:column property="causedby" titleKey="title.xuLyPhanAnh.nguyenNhan" />				
						<display:column class="rightColumnMana" property="haiG" titleKey="baoCaoFeedback.baoCaoTheoNgay.2g"/>
						<display:column class="rightColumnMana" property="baG" titleKey="baoCaoFeedback.baoCaoTheoNgay.3g"/>
						<display:column class="rightColumnMana" property="other" titleKey="baoCaoFeedback.baoCaoTheoNgay.other"/>
						<display:column class="rightColumnMana" property="total" titleKey="baoCaoSoLuongPA.tongSo"/>
		    			
						<display:setProperty name="export.csv.include_header" value="true" />
						<display:setProperty name="export.excel.include_header" value="true" />
						<display:setProperty name="export.xml.include_header" value="true" />
						<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
						<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
						<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
							
					</display:table>
				</div>
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
  var mytext = document.getElementById("startDate");
  mytext.focus();
}

onload = focusIt;

</script>