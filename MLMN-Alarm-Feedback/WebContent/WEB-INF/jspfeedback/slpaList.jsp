<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title><fmt:message key="feedback.baoCaoSoLuongPhanAnh"/></title>
<content tag="heading"><fmt:message key="feedback.baoCaoSoLuongPhanAnh"/></content>
    
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr>
			<td colspan="2" align="center">
				<div id="slpaChart" style="width: 90%; margin: 1em auto"></div>
			</td>
		</tr>
		<tr> 
			<td align="left"><form method="post" action="list.htm">
					<table width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td class="wid10 mwid110">
								<fmt:message key="feedback.thoiGianGuiPhanAnh"/>	
							</td>
							<td class="wid20">
								<input type="text" id="startDate" name="startDate" value="${startDate}" class="wid50" maxlength="20"/>
          						<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
							</td>
							<td class="wid1 mwid40"><fmt:message key="qLThongTinPhanAnh.to"/></td>
							<td class="wid20">
								<input type="text" id="endDate" name="endDate" value="${endDate}" class="wid50" maxlength="20"/>
          						<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
							</td>
							<td><input class="button" type="submit" class="button" name="filter"
								value="<fmt:message key="button.baoCaoFeedback.taoBaoCao"/>" /></td>
						</tr>				
					</table>
				</form>
			</td>
			<td></td> 
		</tr>
		<tr>
			<td colspan="2">
				
				<div style="width:100%;overflow:auto; ">
					<display:table name="${slpaList}" class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
						<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item_rowNum}"/> </display:column>
						<display:column property="fbName" style="max-width:150px;word-wrap: break-word;" class="link" href="/VMSC2-Alarm-Feedback/feedback/quan-ly-thong-tin-lpa/checkedList.htm" paramId="checkedList" paramProperty="id" titleKey="qLThongTinPhanAnh.loaiPhanAnh" sortable="true" sortName="FB_NAME"/>
						<display:column class="centerColumnMana" property="subscribers" titleKey="qLThongTinPhanAnh.soThueBao" sortable="true" sortName="SUBSCRIBERS"/>
						<display:column class="centerColumnMana" property="nameSubscriberType" titleKey="qLThongTinPhanAnh.loaiThueBao" sortable="true" sortName="SUBSCRIBER_TYPE"/>
						<display:column class="centerColumnMana" property="networkType" titleKey="qLThongTinPhanAnh.loaiMang" sortable="true" sortName="NETWORK_TYPE"/>
						<display:column property="causedby" titleKey="timKinhNghiemXL.hienTuongLoi" sortable="true" sortName="RESPONSE_CONTENT"/>
						<display:column property="districtName" titleKey="qLThongTinPhanAnh.quan" sortable="true" sortName="DISTRICT"/>
						<display:column property="villageName" titleKey="qLThongTinPhanAnh.phuong" sortable="true" sortName="WARDS"/>
						<display:column style="max-width:410px;word-wrap: break-word;" property="fbContent" titleKey="qLThongTinPhanAnh.noiDungPhanAnh" sortable="true" sortName="FB_CONTENT"/>
						
						<display:column class="centerColumnMana" titleKey="qLThongTinPhanAnh.thoiGianConLai" media="html">		
							<div id="timeLeftFirst_${item.id}" class="timeLeft"></div>
						</display:column>
						
						<display:setProperty name="export.csv.include_header" value="true" />
						<display:setProperty name="export.excel.include_header" value="true" />
						<display:setProperty name="export.xml.include_header" value="true" />
						<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
						<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
						<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
							
					</display:table>
				</div>
				
			</td>
			<td></td>
		</tr>
		
</table>





<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/themes/grid.js"></script>

${slpaChart}

<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.7.2.js"></script> --%>
<link type="text/css" rel="Stylesheet" href="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.8.23.custom.css" />
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui.min-1.8.9.js"></script> --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery.jcountdown1.3.js"></script>
<script type="text/javascript">
$(function() {
	var x = [];
	var i = 0;
	<c:forEach items="${slpaList}" var="listOfNames">
		x[i] = "<c:out value='${listOfNames.id}' escapeXml='false' />";
		
		var trangThai = "<c:out value='${listOfNames.status}' escapeXml='false' />";
		var tgConLai = "<c:out value='${listOfNames.tgConLai}' escapeXml='false' />";
		if(trangThai == '3' && tgConLai == 'HOAN_THANH'){
			var tgXuLy = "<c:out value='${listOfNames.tgXuLy}' escapeXml='false' />";
			$("#timeLeftFirst_" +  x[i]).html(tgXuLy);
			
			}
		else{
			
			$("#timeLeftFirst_" +  x[i]).countdown({
				
				date: '<fmt:formatDate value="${listOfNames.deadline}" pattern="MM dd, yyyy HH:mm"/>', //Counting TO a date
				htmlTemplate: "%{d} <span class=\"cd-time\">ngày</span> %{h} <span class=\"cd-time\">:</span> %{m} <span class=\"cd-time\">:</span> %{s} <span class=\"cd-time\"></span>", 
				onChange: function( event, timer ){
					
				},
				onComplete: function( event ){
					var status = "<c:out value='${listOfNames.tgConLai}' escapeXml='false' />";
					
					if (status == 'HOAN_THANH') {
						$(this).html("Hoàn thành");
					} else {
						$(this).html("Quá hạn");
					}
				},
				leadingZero: true,
				direction: "down"
			});
		}
		
		i = i + 1;
	</c:forEach>
	
});
</script>

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