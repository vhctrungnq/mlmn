<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title><fmt:message key="baoCaoFeedback.baoCaoSoLuongPaTheoQuanHuyen"/></title>
<content tag="heading"><fmt:message key="baoCaoFeedback.baoCaoSoLuongPaTheoQuanHuyen"/></content>

<ul class="ui-tabs-nav">
<c:choose>
	<c:when test="${function == 'tong-hop'}">
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/feedback/so-luong-pa-theo-quan-huyen/tong-hop.htm"><span><fmt:message key="title.tabControls.baoCaoTongHop"/></span></a></li>
		<li class=""><a href="${pageContext.request.contextPath}/feedback/so-luong-pa-theo-quan-huyen/chi-tiet.htm"><span><fmt:message key="title.tabControls.baoCaoChiTiet"/></span></a></li>
	</c:when>
 	<c:when test="${function == 'chi-tiet'}">
		<li class=""><a href="${pageContext.request.contextPath}/feedback/so-luong-pa-theo-quan-huyen/tong-hop.htm"><span><fmt:message key="title.tabControls.baoCaoTongHop"/></span></a></li>
		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/feedback/so-luong-pa-theo-quan-huyen/chi-tiet.htm"><span><fmt:message key="title.tabControls.baoCaoChiTiet"/></span></a></li>
	</c:when>
 	<c:otherwise></c:otherwise>
</c:choose>
</ul>
<br>
<form:form id="filterController" method="post" action="${function}.htm">
	<div class="body-content"></div>
	<table border="0" width="90%" cellspacing="0" cellpadding="0" class="form">
		<tr>
			<td>
				<fmt:message key="baoCaoSoLuongPA.tuNgay"/>&nbsp;
				<input type="text" id="startDate" name="startDate" value="${startDate}" style="width:120px" maxlength="20"/>
				<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
				<fmt:message key="baoCaoSoLuongPA.denNgay"/>&nbsp;
				<input type="text" id="endDate" name="endDate" value="${endDate}" style="width:120px" maxlength="20"/>
				<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;
				<spring:message code="baoCaoFeedback.baoCaoTheoNgay.tinhThanh"/>&nbsp;
			</td>
			<td>
				<select id="province" name="province">
					<option value="">--Tất cả--</option>
       				<c:forEach var="items" items="${tinhThanhList}">
		              	<c:choose>
			                <c:when test="${items.code == provinceCBB}">
			                    <option value="${items.code}" selected="selected">${items.province}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${items.code}">${items.province}</option>
			                </c:otherwise>
		              	</c:choose>
			    	</c:forEach>
				</select>
			</td>
			<td>
				<spring:message code="qLThongTinPhanAnh.quan"/>&nbsp;
				<select id="district" name="district" >
					<option value="">--Tất cả--</option>
       				<c:forEach var="items" items="${quanHuyenList}">
		              	<c:choose>
			                <c:when test="${items.district == districtCBB}">
			                    <option value="${items.district}" selected="selected">${items.districtName}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${items.district}">${items.districtName}</option>
			                </c:otherwise>
	              		</c:choose>
			    	</c:forEach>
				</select>  
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
		         </div>
		         </td>
		       </c:if>
			<td>
				<input class="button" type="submit" name="filter" value="<fmt:message key="global.form.timkiem"/>" />
			</td>
		</tr>
		</table>
		 
				<div style="width:100%;overflow:auto; " class="tableStandar">
					<display:table name="${soLuongFBList}"  id="item" requestURI="" pagesize="50" sort="external" export="true">
						<display:column class="centerColumnMana" titleKey="global.list.No"> <c:out value="${item_rowNum}"/> </display:column>
						<c:choose>
							<c:when test="${function == 'chi-tiet'}">
								<display:column property="fbDate" format="{0,date,dd/MM/yyyy}" titleKey="baoCaoSoLuongPA.ngay"/>
								<display:column property="provinceName" titleKey="baoCaoFeedback.baoCaoTheoNgay.tinhThanh"/>
								<display:column property="districtName" titleKey="baoCaoFeedback.baoCaoTheoNgay.quanHuyen" />
							</c:when>
							<c:when test="${function == 'tong-hop'}">
								<display:column property="provinceName" titleKey="baoCaoFeedback.baoCaoTheoNgay.tinhThanh"/>
								<display:column property="name" class="link" href="chi-tiet.htm" paramId="district" paramProperty="district" media="html" titleKey="baoCaoFeedback.baoCaoTheoNgay.quanHuyen" />				
								<display:column property="name" titleKey="baoCaoFeedback.baoCaoTheoNgay.quanHuyen" sortable="true" headerClass="hide" class="hide"/>
							
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
<script type="text/javascript">
$('#province').change(function(){

	$.ajax({
	  url: "${pageContext.request.contextPath}/feedback/so-luong-pa-theo-quan-huyen/loadQuanHuyen.htm",
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
	  data:{province: $('#province').val()}}).done(function(j) {
		  var options = '';
		  options += '<option value="">--Tất cả--</option>';
		  for (var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i].district + '">' + j[i].districtName + '</option>';
			}
		$("#district").html(options);
		$('#district option:first').attr('selected', 'selected');
	    
	  });
	  
});
</script>