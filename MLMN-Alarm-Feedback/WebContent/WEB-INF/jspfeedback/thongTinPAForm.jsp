<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title><fmt:message key="title.thongTinPhanAnhFormAdd"/></title>
<content tag="heading"><fmt:message key="title.thongTinPhanAnhFormAdd"/></content>
<div class="body-content"></div>
<form:form commandName="thongTinPAForm" name="checkform" method="post" action="form.htm"> 
	<table class="simple2">
		<tr>
			<td class="wid15 mwid110"><spring:message code="qLThongTinPhanAnh.loaiPhanAnh"/>&nbsp;<font color="red">(*)</font></td>
			<td class="wid35">
				<select name="fbType" id="fbType" class="wid90">
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
			</td>
			<td class="wid15 mwid110"><spring:message code="qLThongTinPhanAnh.vip"/></td>
			<td>
				<select id="vipCode" name="vipCode" class="wid60">
       				<c:forEach var="items" items="${fbVipList}">
		              	<c:choose>
		                <c:when test="${items.vipCode == vipCBB}">
		                    <option value="${items.vipCode}" selected="selected">${items.vipName}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.vipCode}">${items.vipName}</option>
		                </c:otherwise>
		              	</c:choose>
			    	</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td><spring:message code="qLThongTinPhanAnh.nguoiPhanAnh"/></td>
			<td>
			    <form:input id="fbUser" path="fbUser" maxlength="20" class="wid60"/>
			</td>
			<td><spring:message code="qLThongTinPhanAnh.thoiGianPA1"/>&nbsp;<font color="red">(*)</font></td>
	        <td>
	        	<input id="fbDate" name="fbDate" value="${fbDate}" class="wid30" maxlength="20"/>
				<img alt="calendar" title="Click to choose the feedback date" id="chooseFeedbackDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				&nbsp;<form:errors path="fbDate" cssClass="error"/> <span class="error">${errorFbDate}</span>
	        </td>		
		</tr>
		<tr>
			<td><spring:message code="qLThongTinPhanAnh.soThueBao"/>&nbsp;<font color="red">(*)</font></td>
	        <td>
	        	<form:input id="subscribers" path="subscribers" maxlength="20" class="wid60"/>&nbsp;<form:errors path="subscribers" cssClass="error"/>
	        </td>
	        <td><spring:message code="qLThongTinPhanAnh.loaiThueBao"/></td>
	        <td>
				<select name="subscriberType" id="subscriberType" class="wid30">
         				<c:forEach var="items" items="${loaiTBList}">
		              	<c:choose>
		                <c:when test="${items.value == loaiThueBaoCBB}">
		                    <option value="${items.value}" selected="selected">${items.name}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.value}">${items.name}</option>
		                </c:otherwise>
		              	</c:choose>
			    	</c:forEach>
       			</select>
	        </td>
		</tr>
		<tr>
	        <td><spring:message code="qLThongTinPhanAnh.loaiMang"/></td>
			<td>
					<select name="networkType" id="networkType" class="wid30">
         				<c:forEach var="items" items="${loaiMangList}">
		              	<c:choose>
		                <c:when test="${items.value == loaiMangCBB}">
		                    <option value="${items.value}" selected="selected">${items.name}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.value}">${items.name}</option>
		                </c:otherwise>
		              	</c:choose>
			    		</c:forEach>
   					</select>
			</td>
			<td><spring:message code="qLThongTinPhanAnh.bsc_rnc"/></td>
	        <td>
	        	<select name="bscRnc" id="bscRnc" class="wid30">
         				<c:forEach var="items" items="${bscList}">
		              	<c:choose>
		                <c:when test="${items.bscid == bscRncCBB}">
		                    <option value="${items.bscid}" selected="selected">${items.bscid}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.bscid}">${items.bscid}</option>
		                </c:otherwise>
		              	</c:choose>
			    		</c:forEach>
				</select>
	        </td>
		</tr>
		<tr>
			<td><spring:message code="qLThongTinPhanAnh.quan"/></td>
			<td>
			    <select id="district" name="district" class="wid60">
			    	<c:forEach var="items" items="${quanHuyenList}">
		              	<c:choose>
		                <c:when test="${items.code == provinceCBB && items.district == districtCBB}">
		                    <option value="${items.code}//${items.district}" selected="selected">${items.districtName}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.code}//${items.district}">${items.districtName}</option>
		                </c:otherwise>
		              	</c:choose>
			    	</c:forEach>
				</select>
			</td>
			<td><spring:message code="qLThongTinPhanAnh.phuong"/></td>
	        <td>
	        	<select id="wards" name="wards" class="wid60">
       				<c:forEach var="items" items="${phuongXaList}">
		              	<c:choose>
		                <c:when test="${items.village == wardsCBB}">
		                    <option value="${items.village}" selected="selected">${items.villageName}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.village}">${items.villageName}</option>
		                </c:otherwise>
		              	</c:choose>
		    		</c:forEach>
				</select>
	        </td>
		</tr>
		<tr>
			<td><spring:message code="qLThongTinPhanAnh.phongDai"/></td>
			<td>
				<select id="deptProcess" name="deptProcess" class="wid60">
          				<c:forEach var="items" items="${deptProcessList}">
			              	<c:choose>
			                <c:when test="${items.deptCode == deptProcessCBB}">
			                    <option value="${items.deptCode}" selected="selected">${items.deptCode}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${items.deptCode}">${items.deptCode}</option>
			                </c:otherwise>
			              	</c:choose>
				    	</c:forEach>
				</select>
			</td>
			<td><spring:message code="qLThongTinPhanAnh.toXuLy"/></td>
	        <td>
				<select id="team" name="team" class="wid60">
						<option value=""></option>
         				<c:forEach var="items" items="${teamList}">
		              	<c:choose>
		                <c:when test="${items.team == teamCBB}">
		                    <option value="${items.team}" selected="selected">${items.team}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.team}">${items.team}</option>
		                </c:otherwise>
		              	</c:choose>
			    	</c:forEach>
				</select>
	        </td>
		</tr>
		<tr>
			<td><spring:message code="qLThongTinPhanAnh.fbIbc"/></td>
			<td>
				<select id="fbIbc" name="fbIbc" class="wid60">
       				<c:forEach var="items" items="${fbIbcList}">
		              	<c:choose>
		                <c:when test="${items.value == fbIbcCBB}">
		                    <option value="${items.value}" selected="selected">${items.name}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.value}">${items.name}</option>
		                </c:otherwise>
		              	</c:choose>
			    	</c:forEach>
				</select>
			</td>
           <td><spring:message code="qLThongTinPhanAnh.noiDungPhanAnh"/>&nbsp;<font color="red">(*)</font></td>
           <td>
           	 	<textarea class="wid90" id="fbContent" name="fbContent" maxlength="3000">${fbContent}</textarea>&nbsp;<form:errors path="fbContent" cssClass="error"/>	
           </td>
   		</tr>
		<tr>
           <td></td>
           <td colspan="3">
               <input class="button" type="submit" class="button" name="save" value="<fmt:message key="global.button.luuLai"/>" />
               <input class="button" type="button" value="<fmt:message key="global.button.huyBo"/>" onClick='window.location="list.htm"'>
           </td>
       </tr>
	</table>
</form:form>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<!-- <script type="text/javascript" src="/VMSC2-Alarm-Feedback/js/jquery/jquery-1.7.2.js"></script> -->
<script type="text/javascript">
Calendar.setup({
    inputField		:	"fbDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M:%S",   	// format of the input field
    button			:   "chooseFeedbackDate",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
});

function focusIt()
{
	var fbDateError = '<c:out value="${fbDateError}"/>';
	var errorFbDate = '<c:out value="${errorFbDate}"/>';
  	if(document.checkform.subscribers.value==""){
	  var mytext = document.getElementById("subscribers");
	  mytext.focus();
	}
	else if(document.checkform.fbContent.value=="")
	{
			var mytext = document.getElementById("fbContent");
			  mytext.focus();
	}
	
	else if(fbDateError !="")
	{
			var mytext = document.getElementById("fbDate");
			  mytext.focus();
	}
	else if(errorFbDate !="")
	{
			var mytext = document.getElementById("fbDate");
			  mytext.focus();
	}
	else if(document.checkform.fbUser.value=="")
	{
		var mytext = document.getElementById("fbUser");
		  mytext.focus();
		}
}

onload = focusIt;
</script>
<script type="text/javascript">
function loadBscProvince(){
	$.ajax({
		  url: "${pageContext.request.contextPath}/feedback/quan-ly-thong-tin-lpa/ajax/loadBscProvince.htm",
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
		  data:{loaiMang: $('#networkType').val()}}).done(function( j ) {
			  var options = '';
				
			  for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].bscid + '">' + j[i].bscid+ '</option>';
				}
			$("#bscRnc").html(options);
			$('#bscRnc option:first').attr('selected', 'selected');
		    
		  });
		  
}

$('#networkType').change(function(){

	loadBscProvince();
});

function loadDeptProcess(){
	$.ajax({
		  url: "${pageContext.request.contextPath}/feedback/quan-ly-thong-tin-lpa/ajax/loadDeptProcess.htm",
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
		  data:{provinceDistrict: encodeURI($('#district').val())}}).done(function( j ) {
			  var options = '';
				
			  for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].deptCode + '">' + j[i].deptCode + '</option>';
				}
			$("#deptProcess").html(options);
			$('#deptProcess option:first').attr('selected', 'selected');
		    
			if(j.length > 0){
				loadTeam(j[0].deptCode);
			}
		  });
}

function loadTeam(deptProcess){
	$.ajax({
		  url: "${pageContext.request.contextPath}/feedback/so-luong-pa-theo-to-vien-thong/loadTeam.htm",
		  beforeSend: function( ) {
		  },
		  data:{deptProcess: deptProcess}}).done(function(j) {
			  var options = '';
			  options += '<option value=""></option>';
			  for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].team + '">' + j[i].team + '</option>';
				}
			$("#team").html(options);
			$('#team option:first').attr('selected', 'selected');
		    
		  });
};
</script>
<script type="text/javascript">
$('#district').change(function(){

	$.ajax({
	  url: "${pageContext.request.contextPath}/feedback/quan-ly-thong-tin-lpa/ajax/loadPhuongXa.htm",
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
	  data:{provinceDistrict: encodeURI($('#district').val())}}).done(function(j) {
		  var options = '';
		  for (var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i].village + '">' + j[i].villageName + '</option>';
			}
		$("#wards").html(options);
		$('#wards option:first').attr('selected', 'selected');
	    
	  });
	  loadDeptProcess();
});

</script>