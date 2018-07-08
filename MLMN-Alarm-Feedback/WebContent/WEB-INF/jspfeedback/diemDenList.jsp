<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<title>Danh sách điểm đen</title>
<content tag="heading">Danh sách điểm đen</content> 	

<form:form commandName="filter" method="post" action="list.htm">
	<table >
		
			<tr> 
				<td><spring:message code="qLThongTinPhanAnh.loaiPhanAnh"/></td>
				<td colspan="5">
					<div class="psr ovh select">
						<select id="loaiPA" name="loaiPA" class="select" >
							<option value="">--Tất cả--</option>
			 				<c:forEach var="items" items="${loaiPAList}">
			              	<c:choose>
			                <c:when test="${items.code == loaiPA}">
			                    <option value="${items.code}" selected="selected">${items.name}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${items.code}">${items.name}</option>
			                </c:otherwise>
			              	</c:choose>
					    	</c:forEach>
				          </select>
				    </div>
				</td>
				<td class="pl10"><spring:message code="qLThongTinPhanAnh.trangThai"/></td>
				<td>
					<div class="wid110 psr ovh select">
						<select id="status" name="status" class="select">
							<option value="">--Tất cả--</option>
	          				<c:forEach var="items" items="${QLTTFBList}">
				              	<c:choose>
				                <c:when test="${items.value == status}">
				                    <option value="${items.value}" selected="selected">${items.name}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.value}">${items.name}</option>
				                </c:otherwise>
				              	</c:choose>
					    	</c:forEach>
       					</select>
       				</div>
				</td>
			</tr>
			<tr>
				<td><spring:message code="qLThongTinPhanAnh.thoiGianPA"/></td>
				<td style="width:130px">
						<input type ="text"  value="${thoiGianPAFrom}" name="thoiGianPAFrom" id="thoiGianPAFrom" size="17" maxlength="19" style="width:110px">
			   			 <img alt="calendar" title="Click to choose the from date" id="chooseThoiGianPAFrom" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				<td class="pl10">
					<spring:message code="qLThongTinPhanAnh.to"/>
				</td>
				<td style="width:130px">
						<input type ="text"  value="${thoiGianPATo}" name="thoiGianPATo" id="thoiGianPATo" size="17" maxlength="19" style="width:110px">
			   			<img alt="calendar" title="Click to choose the from date" id="chooseThoiGianPATo" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				<td class="pl10"><spring:message code="qLThongTinPhanAnh.thoiGianXL"/></td>
				<td style="width:130px">
						<input type ="text"  value="${thoiGianXLFrom}" name="thoiGianXLFrom" id="thoiGianXLFrom" size="17" maxlength="19" style="width:110px">
			   			<img alt="calendar" title="Click to choose the from date" id="chooseThoiGianXLFrom" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				<td class="pl10">
					<spring:message code="qLThongTinPhanAnh.to"/>
				</td>
				<td style="width:130px">
						<input type ="text"  value="${thoiGianXLTo}" name="thoiGianXLTo" id="thoiGianXLTo" size="17" maxlength="19" style="width:110px">
			   			<img alt="calendar" title="Click to choose the from date" id="chooseThoiGianXLTo" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
			</tr>
			<tr>
				<td><spring:message code="qLThongTinPhanAnh.phongDai"/></td>
				<td>
					<div class="psr ovh select">
						<select id="deptProcess" name="deptProcess" class="select">
							<option value="">--Tất cả--</option>
	          				<c:forEach var="items" items="${deptProcessList}">
				              	<c:choose>
				                <c:when test="${items.deptCode == deptProcess}">
				                    <option value="${items.deptCode}" selected="selected">${items.deptCode}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.deptCode}">${items.deptCode}</option>
				                </c:otherwise>
				              	</c:choose>
					    	</c:forEach>
       					</select>
       				</div>
				</td>
				<td class="pl10"><spring:message code="qLThongTinPhanAnh.toXuLy"/></td>
				<td>
					<div class="wid110 psr ovh select">
						<select id="team" name="team" class="select">
							<option value="">--Tất cả--</option>
	          				<c:forEach var="items" items="${teamList}">
				              	<c:choose>
				                <c:when test="${items.team == team}">
				                    <option value="${items.team}" selected="selected">${items.team}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.team}">${items.team}</option>
				                </c:otherwise>
				              	</c:choose>
					    	</c:forEach>
       					</select>
       				</div>
				</td>
						
				<td style="padding-left: 5px;" colspan="2">
					<input class="button" type="submit" id="submit" value="<fmt:message key="global.form.timkiem"/>" />
				</td>
			</tr>
		</table>
	</form:form>
<br>
<div id="gridReport"></div>
<div id='menuReport'>
     <ul>
  		<li><fmt:message key="global.button.detailFB"/></li>
         	<li><fmt:message key="global.button.KhongLaDiemDen"/></li>
         	<li><fmt:message key="global.button.deleteFB"/></li>
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
    inputField		:	"thoiGianPAFrom",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseThoiGianPAFrom",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 

Calendar.setup({
    inputField		:	"thoiGianPATo",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseThoiGianPATo",   	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 	
Calendar.setup({
    inputField		:	"thoiGianXLFrom",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseThoiGianXLFrom",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 

Calendar.setup({
    inputField		:	"thoiGianXLTo",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseThoiGianXLTo",   	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 	
</script>
<script type="text/javascript">
$(document).ready(function(){

		// Khai bao sdate, edate
var theme =  getTheme();
$('#submit').jqxButton({ theme: theme });
//renderer for grid cells. 
var numberrenderer = function (row, column, value) {
     return '<div style="text-align: center; margin-top: 5px;">' + (1 + value) + '</div>';
 };
 var cellsrenderer = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {
		return '<div id="timeLeftFirst_'+rowdata.id +'" class="timeLeft" style="width:100%;padding-top: 10px;text-align: center;">'+rowdata.tgConLai+'</div>';
 	//return '<div id="timeLeftFirst_'+rowdata.id +'">'+convertDateToString(rowdata.deadline)+'</div>';
 	//return '<div id="timeLeftFirst_'+rowdata.id +'"></div>';
 };
	var cellsrenderer48H = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {
		return '<div id="timeTelecom48h_'+rowdata.id +'" class="timeLeft" style="width:100%;padding-top: 10px;text-align: center;">'+rowdata.fbDVT48hour+'</div>';
	};
	var cellsrenderer5day = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {
		return '<div id="timeTelecom5day_'+rowdata.id +'" class="timeLeft" style="width:100%;padding-top: 10px;text-align: center;">'+rowdata.fbDVT5day+'</div>';
	};
	var cellsrendererSTB = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {
			if (rowdata.vipCode!= '0'){
				return '<div style="width:100%;padding-top: 10px;"><a href="${pageContext.request.contextPath}/feedback/general-feedback/checkedList.htm?checkedList='+rowdata.id+'&note=DD'+'" class="timeLeft" >'+rowdata.subscribers+'</a></div>';
			}
			else
			{
				return '<div style="width:100%;padding-top: 10px;"><a href="${pageContext.request.contextPath}/feedback/general-feedback/checkedList.htm?checkedList='+rowdata.id+'&note=DD'+'"  >'+rowdata.subscribers+'</a></div>';
			}
	};
	var cellsrendererFeebacked = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {
		if (rowdata.isFeedbacked == 'Y'){
			return '<div style="width:100%;text-align: center;padding-top: 10px;"><input type="checkbox" name="is_feedbacked" value="'+rowdata.isFeedbacked+'" checked="checked"/></div>';
		}
		else
		{
			return '<div style="width:100%;text-align: center;padding-top: 10px;"><input type="checkbox" name="is_feedbacked" value="'+rowdata.isFeedbacked+'"/></div>';
			
		}
	};
	var cellsrendererSendTelecom = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {
		
		if (rowdata.fbSendTelecom == 'Y'){
			return '<div style="width:100%;text-align: center;padding-top: 10px;"><input  type="checkbox" name="fb_send_telecom" value="'+rowdata.fbSendTelecom+'" checked="checked"/></div>';
		}
		else
		{
			return '<div style="width:100%;text-align: center;padding-top: 10px;"><input type="checkbox" name="fb_send_telecom" value="'+rowdata.fbSendTelecom+'"/></div>';
			
		}
	};
${gridReport}
// handle context menu clicks.
$("#menuReport").on('itemclick', function (event) {
	    var args = event.args;
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.detailFB"/>') {
       	  	var rowindex = $("#gridReport").jqxGrid('getselectedrowindex');
            var row = $("#gridReport").jqxGrid('getrowdata', rowindex);
        	window.location = '${pageContext.request.contextPath}/feedback/general-feedback/checkedList.htm?checkedList='+row.id+'&note=DD';   
        }
        if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteFB"/>')  {
        	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
        	if (answer)
        	{
				var selectedrowindexes = $('#gridReport').jqxGrid('getselectedrowindexes'); 
        		var idList="";
        		var cond="";
        		//alert(selectedrowindexes);
        		//var rowIndexList = selectedrowindexes.split(",");
        		if (selectedrowindexes != null) {
        			for (var i=0; i<selectedrowindexes.length; i++) {
        				var row = $("#gridReport").jqxGrid('getrowdata', selectedrowindexes[i]);
        				idList+=cond+row.id;
        				cond=",";
        			}
        			// alert(idList);
        			window.location = '${pageContext.request.contextPath}/feedback/general-feedback/delete.htm?idList='+idList+'&note=DD';
        		}
        		
        	}
          
        }
        if ($.trim($(args).text()) == '<fmt:message key="global.button.KhongLaDiemDen"/>')  {
        	var answer = confirm ('<fmt:message key="messsage.confirm.KhongLaDiemDen"/>');
        	if (answer)
        	{
				var selectedrowindexes = $('#gridReport').jqxGrid('getselectedrowindexes'); 
        		var idList="";
        		var cond="";
        		//alert(selectedrowindexes);
        		//var rowIndexList = selectedrowindexes.split(",");
        		if (selectedrowindexes != null) {
        			for (var i=0; i<selectedrowindexes.length; i++) {
        				var row = $("#gridReport").jqxGrid('getrowdata', selectedrowindexes[i]);
        				idList+=cond+row.id;
        				cond=",";
        			}
        			// alert(idList);
        			window.location = '${pageContext.request.contextPath}/feedback/general-feedback/checkDiemDen.htm?idList='+idList+'&status=N&note=DD';
        		}	
        	}
        }
		if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
			window.open('${pageContext.request.contextPath}/feedback/diemden/export.htm?loaiPA='+"<c:out value='${loaiPA}'/>"+
    	 			'&thoiGianPAFrom='+"<c:out value='${thoiGianPAFrom}'/>"+
    	 			'&thoiGianPATo='+"<c:out value='${thoiGianPATo}'/>"+
    	 			'&thoiGianXLFrom='+"<c:out value='${thoiGianXLFrom}'/>"+
    	 			'&thoiGianXLTo='+"<c:out value='${thoiGianXLTo}'/>"+
    	 			'&deptProcess='+"<c:out value='${deptProcess}'/>"+
    	 			'&team='+"<c:out value='${team}'/>"+
    	 			'&status='+"<c:out value='${status}'/>"
    			 ,'_blank');
        }
	   
	});
});
  
</script>

