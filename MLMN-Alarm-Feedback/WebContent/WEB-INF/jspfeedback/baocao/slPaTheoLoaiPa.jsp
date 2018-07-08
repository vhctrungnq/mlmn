<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title><fmt:message key="baoCaoFeedback.baoCaoSoLuongPaTheoLoaiPa"/></title>
<content tag="heading"><fmt:message key="baoCaoFeedback.baoCaoSoLuongPaTheoLoaiPa"/></content>

<ul class="ui-tabs-nav">
<c:choose>
	<c:when test="${function == 'tong-hop'}">
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/feedback/so-luong-pa-theo-loai-pa/tong-hop.htm"><span><fmt:message key="title.tabControls.baoCaoTongHop"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/feedback/so-luong-pa-theo-loai-pa/chi-tiet.htm"><span><fmt:message key="title.tabControls.baoCaoChiTiet"/></span></a></li>
	</c:when>
 	<c:when test="${function == 'chi-tiet'}">
		<li class=""><a href="${pageContext.request.contextPath}/feedback/so-luong-pa-theo-loai-pa/tong-hop.htm"><span><fmt:message key="title.tabControls.baoCaoTongHop"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/feedback/so-luong-pa-theo-loai-pa/chi-tiet.htm"><span><fmt:message key="title.tabControls.baoCaoChiTiet"/></span></a></li>
	</c:when>
 	<c:otherwise></c:otherwise>
</c:choose>
</ul>
<br>
<form:form id="filterController" method="post" action="${function}.htm">
	<table border="0" width="80%" cellspacing="0" cellpadding="0" class="form">
		<tr>
			<td class="wid40">
			
				    <fmt:message key="baoCaoSoLuongPA.tuNgay"/>&nbsp;
					<input type="text" id="startDate" name="startDate" value="${startDate}" style="width: 120px" maxlength="20"/>
   					<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
					<fmt:message key="baoCaoSoLuongPA.denNgay"/>&nbsp;
					<input type="text" id="endDate" name="endDate" value="${endDate}" style="width: 120px" maxlength="20"/>
   					<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
			</td>
			<td>
				<c:choose>
				<c:when test="${function == 'chi-tiet'}">
				
					<fmt:message key="qLThongTinPhanAnh.loaiPhanAnh"/>&nbsp;
					<select id="fbType" name="fbType" class="wid60">
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
			          </select>	    
			      
				</c:when>
				<c:otherwise></c:otherwise>
				</c:choose>
				 </td>
				 
				<c:if test="${Center=='TT6' }">
				<td  class="wid10">
				 <spring:message code="qLThongTinPhanAnh.fbSendTelecom"/>
				 </td>
				 <td class="wid10">
				<div class="wid70 psr ovh select">
					<select id="fbSendTelecom" name="fbSendTelecom" class="select" id="fbSendTelecom">
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
        			</select>
        			<input type="hidden" id="fbDistrict" name="fbDistrict" value="${fbDistrict}"/>
        			
		         </div>
		         </td>
		       </c:if>
			
			<c:if test="${function == 'chi-tiet'}">
			<td style="width:10%">
				<input class="button" type="submit" name="filter" value="<fmt:message key="global.form.timkiem"/>" />
			</td>
			</c:if>
			<c:if test="${function == 'tong-hop'}">
			<td style="width:50%">
				<input class="button" type="submit" name="filter" value="<fmt:message key="global.form.timkiem"/>" />
			</td>
			</c:if> 
		</tr>
		</table>

				<div style="width:100%;overflow:auto;"  class="tableStandar">
					<display:table name="${soLuongFBList}"   id="item" requestURI="" pagesize="50" sort="external" export="true">
						<display:column class="centerColumnMana" titleKey="global.list.No"> <c:out value="${item_rowNum}"/> </display:column>
						<c:choose>
							<c:when test="${function == 'chi-tiet'}">
								<display:column property="fbDate" format="{0,date,dd/MM/yyyy}" titleKey="baoCaoSoLuongPA.ngay"/>
								<display:column property="fbName" titleKey="qLThongTinPhanAnh.loaiPhanAnh" />
							</c:when>
							<c:when test="${function == 'tong-hop'}">
								<display:column property="name" titleKey="qLThongTinPhanAnh.loaiPhanAnh" sortable="true" headerClass="hide" class="hide"/>
								<display:column  property="name" class="link" href="chi-tiet.htm" paramId="fbType"   paramProperty="name" media="html" titleKey="qLThongTinPhanAnh.loaiPhanAnh" />				
							</c:when>
						</c:choose>
						<display:column class="rightColumnMana" property="haiG" titleKey="baoCaoFeedback.baoCaoTheoNgay.2g"/>
						<display:column class="rightColumnMana" property="baG" titleKey="baoCaoFeedback.baoCaoTheoNgay.3g"/>
						<display:column class="rightColumnMana" property="other" titleKey="baoCaoFeedback.baoCaoTheoNgay.other"/>
						<display:column class="rightColumnMana" property="total" titleKey="baoCaoSoLuongPA.tongSo"/>
						<display:column class="rightColumnMana" property="trongHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.daXuLyTrongHan"/>
						<display:column class="rightColumnMana" property="tlTrongHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.tlDaXuLyTrongHan"/>
						<display:column class="rightColumnMana" property="quaHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.daXuLyQuaHan"/>
						<display:column class="rightColumnMana" property="tlQuaHan" titleKey="baoCaoFeedback.baoCaoTheoNgay.tlDaXuLyQuaHan"/>
		    			<display:column class="rightColumnMana" property="chuaXuly" titleKey="baoCaoFeedback.baoCaoTheoNgay.chuaXuly"/>
		    			<display:column class="rightColumnMana" property="tlChuaXuly" titleKey="baoCaoFeedback.baoCaoTheoNgay.tlChuaXuly"/>
		    			
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
$(document).ready(function(){
	$('#item>tbody>tr').each(
	    	 function(){
	    		 var value = $(this).find(".link").text();
	    		 if(value.indexOf('Total') != -1)
	    			 $(this).find("td").css({ 'color' : 'red', 'font-weight': 'bold'});
	});
});		
function focusIt()
{
  var mytext = document.getElementById("startDate");
  mytext.focus();
}

onload = focusIt;

</script>