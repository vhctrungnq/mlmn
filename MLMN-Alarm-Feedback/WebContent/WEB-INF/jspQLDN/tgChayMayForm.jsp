<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<title> THÊM THỜI GIAN CHẠY MÁY NỔ (${loaiThanhToan})</title>
<form:form commandName="record" method="post" action="form.htm" id = "form" onsubmit="return validateForm();"> 
<input type="hidden" id="type" name ="type" value="${type}"/>
	<form:hidden path="id"/>
	<form:hidden path = "loaiThanhToan" value = "${type}" />
	<table class="simple2" >
		<c:choose>
			<c:when test="${loaiThanhToan != 'TTL' }">
				<tr> 
					<td class = "required"> Trạm </td> 
					<td><form:input path = "idTram" value = "${record.idTram}" maxlength="50" name = "tram" 
						onblur = "clearMessage()"/> 
						<span id = "idTram-error" class = "error"> </span>
					</td> 
					
					<td class = "required"> Ngày chạy máy</td>
					<td> <input id = "ngayChay" value = "${ngayChay}" name = "ngayChay" onblur = "clearMessage()"/> 
						<img alt="calendar" title="Chon ngay chay" id="chooseRunDay" 
							style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				    	<span id = "ngayChay-error" class = "error"> </span>
					</td>
				</tr>
				<tr> 
					<td class = "required">TG bắt đầu </td>
					<td> <input id = "tgBatDau" value = "${tgBatDau}" name = "tgBatDau" onblur = "clearMessage()"/>
						<img alt="calendar" title="Chon thoi gian bat dau" id="chooseStartTime" style="cursor: pointer;" 
						src="${pageContext.request.contextPath}/images/calendar.png"/>
			    		<span id = "tgBatDau-error" class = "error"> </span>
					</td>
					
					<td class = "required"> TG kết thúc </td>
					<td> <input id = "tgKetThuc" value = "${tgKetThuc}" name = "tgKetThuc" onblur = "clearMessage()"/>
						<img alt="calendar" title="Chon thoi gian ketThuc" id="chooseEndTime" style="cursor: pointer;" 
						src="${pageContext.request.contextPath}/images/calendar.png"/>
			    		<span id = "tgKetThuc-error" class = "error"> </span>
					</td>
				</tr>
				<tr>
					<td>TG hoạt động</td>
					<td> <form:input path = "tgHoatDong" value = "${record.tgHoatDong}" name = "tgHoatDong"/> </td>
					<td>Giờ quy đổi </td>
					<td> <form:input path = "soHQuyDoi" value = "${record.soHQuyDoi}" name = "soHQuyDoi" class = "number-only"/> </td>
				</tr>
				<tr>
					<td> Định mức </td>
					<td> <form:input path = "dinhMuc" value = "${dinhMuc}" name = "dinhMuc" class = "number-only"/> </td>
					<td> Tiêu hao NL</td>
					<td> <form:input path = "tongLitDm" value = "${record.tongLitDm}" name = "tongLitDm"
						class = "number-only"/> </td>
				</tr>
				<tr>
					<td class = "required"> Đơn giá </td>
					<td> <form:input path = "donGia" value = "${record.donGia}" name = "donGia" onblur = "clearMessage()"
						class = "number-only"/> 
						<span id = "donGia-error" class = "error"> </span>
					</td>
					<td> Thành tiền </td>
					<td> <form:input path = "thanhTien" value = "${record.thanhTien}" class = "number-only"/> </td>
				</tr>
				<tr>
					<c:choose>
						<c:when test ="${loaiThanhToan=='XHH'}">
							<td> Ghi chú </td>
						<td colspan="3"> <form:input path = "ghiChu" value = "${record.ghiChu}" maxlength="2000" name = "ghiChu"/> </td>
						</c:when>
						<c:otherwise>
							<td> Chỉ số H</td>
							<td> <form:input path = "chiSoH" value = "${record.chiSoH}" maxlength="20" name = "chiSoH"/> </td>
							<td> Ghi chú </td>
							<td> <form:input path = "ghiChu" value = "${record.ghiChu}" maxlength="2000" name = "ghiChu"/> </td>
						</c:otherwise>
					</c:choose>		
				</tr>
		</c:when>
		<c:otherwise>
			<tr>
				<td class = "required"> Trạm </td> 
				<td><form:input path = "idTram" value = "${record.idTram}" maxlength="50" name = "tram" 
					onblur = "clearMessage()"/> 
					<span id = "idTram-error" class = "error"> </span>
				</td> 
				<td class = "required">TG bắt đầu </td>
				<td> <input id = "tgBatDau" value = "${tgBatDau}" name = "tgBatDau" onblur = "clearMessage()"/>
					<img alt="calendar" title="Chon thoi gian bat dau" id="chooseStartTime" style="cursor: pointer;" 
					src="${pageContext.request.contextPath}/images/calendar.png"/>
					<span id = "tgBatDau-error" class = "error"> </span>
				</td>	
			</tr>
			<tr>
				<td class = "required"> TG kết thúc </td>
				<td> <input id = "tgKetThuc" value = "${tgKetThuc}" name = "tgKetThuc" onblur = "clearMessage()"/>
					<img alt="calendar" title="Chon thoi gian ketThuc" id="chooseEndTime" style="cursor: pointer;" 
						src="${pageContext.request.contextPath}/images/calendar.png"/>
					<span id = "tgKetThuc-error" class = "error"> </span>
				</td>
				<td class = "required"> Số hóa đơn </td> 
				<td><form:input path = "soHoaDon" value = "${record.soHoaDon}" maxlength="20" name = "soHoaDon"
					onblur = "clearMessage()"/> 
					<span id = "soHoaDon-error" class = "error"> </span>
				</td> 
			</tr>
			<tr>
				<td class = "required"> Số lít </td>
				<td> <form:input path = "tongLitDm" value = "${record.tongLitDm}" name = "tongLitDm" onblur = "clearMessage()"
					class = "number-only"/> 
					<span id = "tongLitDm-error" class = "error"> </span>
				</td>
				<td class = "required"> Ngày thanh toán</td>
				<td> <input id = "ngayThanhToan" value = "${ngayThanhToan}" name = "ngayThanhToan" onblur = "clearMessage()"/> 
					<img alt="calendar" title="Chon ngay thanh toan" id="choosePayDay" 
					style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
		    		<span id = "ngayThanhToan-error" class = "error"> </span>		
				</td>	
			</tr>
			<tr>
				<td> Số tiền </td>
				<td> <form:input path = "thanhTien" value = "${record.thanhTien}" name = "thanhTien" class = "number-only"/> 
					<!-- <span id = "thanhTien-error" class = "error"> </span> -->
				</td>
				<td> Ghi chú </td>
				<td> <form:input path = "ghiChu" value = "${record.ghiChu}" maxlength="2000" name = "ghiChu"/> </td>
			</tr>
		</c:otherwise>	
	</c:choose>	
	</table>
	<br>
	<input type="submit" class="button" id = "submit" value="Save" 
		onClick='window.location="list.htm?loaiThanhToan=${loaiThanhToan}"'/> 
	<input type="button" class="button" id = "btCancel" value="Cancel" 
		onClick='window.location="list.htm?loaiThanhToan=${loaiThanhToan}"'>
</form:form>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/focus-input-number.js" ></script>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.validate.min.js" ></script> --%>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script>

	var loaiThanhToan = "<c:out value = '${loaiThanhToan}'/>";
	
	$(document).ready(function(){
		Calendar.setup({
		    inputField		:	"tgBatDau",	// id of the input field
		    ifFormat		:	"%d/%m/%Y %H:%M:%S",   	// format of the input field
		    button			:   "chooseStartTime",  	// trigger for the calendar (button ID)
		    showsTime		:	true,
		    singleClick		:   false					// double-click mode
		}); 
		
		Calendar.setup({
		    inputField		:	"tgKetThuc",	// id of the input field
		    ifFormat		:	"%d/%m/%Y %H:%M:%S",   	// format of the input field
		    button			:   "chooseEndTime",   	// trigger for the calendar (button ID)
		    showsTime		:	true,
		    singleClick		:   false					// double-click mode
		}); 
		if (loaiThanhToan != 'TTL') {
			Calendar.setup({
			    inputField		:	"ngayChay",	// id of the input field
			    ifFormat		:	"%d/%m/%Y",   	// format of the input field
			    button			:   "chooseRunDay",   	// trigger for the calendar (button ID)
			    showsTime		:	true,
			    singleClick		:   false					// double-click mode
			});
		} else {
			Calendar.setup({
			    inputField		:	"ngayThanhToan",	// id of the input field
			    ifFormat		:	"%d/%m/%Y",   	// format of the input field
			    button			:   "choosePayDay",   	// trigger for the calendar (button ID)
			    showsTime		:	true,
			    singleClick		:   false					// double-click mode
			});
		}
		
		/* $("#thanhTien").val("1000");
		console.log($("#tgBatDau").val()) */
		/* $("#form").validate({
			rules: {
				tram: "required",
				ngayChay: "required",
				tgBatDau: "required",
				tgKetThuc: "required",
				donGia: "required",
				soHoaDon:"required",
			},
			messages: {
				tram: "ID trạm không được để trống",
				ngayChay: "Ngày chạy máy không được để trống",
				tgBatDau: "Thời gian bắt đầu chạy máy không được để trống",
				tgKetThuc: "Thời gian kết thúc chạy máy không được để trống",
				donGia: "Đơn giá không được để trống",
				soHoaDon: "Số hóa đơn không được để trống"
			}
		});*/
		
		
		/* $("input.number-only").keydown(function(e){
			var keyCode = e.which;
			// Chi cho phep nhap 0-9, dau "."" va dung backspace + tab de chinh du lieu
			var isValidCharacter = (keyCode >= 49 && keyCode <= 58)
				|| (keyCode >=96 && keyCode <= 105) // ban phim phu
				|| keyCode == 110 
				|| keyCode == 190 // dau "." o ban phim phu
				|| keyCode == 8   // backspace
				|| keyCode == 9 // tab
			var val = $(this).val();
			if (!isValidCharacter) {
				return false;
			} 
			// loai bo truong hop dau "." o dau hoac co nhieu dau "."
			else if ((!val || val.indexOf(".") > -1) && (keyCode == 110 || keyCode == 190)) { 
				return false;
			}

			return true;
		});
		 */
		 
		focusInputNumber();
		var errorField = "<c:out value = '${errorField}'/>"; 
		var reason = "<c:out value = '${reason}'/>";
		if (errorField == 'tgBatDau') {
			if (reason == 'wrong format!') {
				$("#tgBatDau-error").text("Nhập thời gian bắt đầu chạy máy đúng định dạng dd/mm/yyyy hh:mi:ss");
			} else if (reason == 'invalid value!') {
				$("#tgBatDau-error").text("Thời gian bắt đầu chạy máy không thể lớn hơn thời gian hiện tại");
			}
			
		}
		if (errorField == 'ngayThanhToan') {
			
			if (reason == 'wrong format!') {
				$("#ngayThanhToan-error").text("Nhập ngày thanh toán đúng định dạng dd/mm/yyyy");
			} else if (reason == 'invalid value!') {
				$("#ngayThanhToan-error").text("Ngày thanh toán không thể lớn hơn ngày hiện tại");
			}
		}
		if (errorField == 'ngayChay') {
			if (reason == 'wrong format!') {
				$("#ngayChay-error").text("Nhập ngày chạy máy đúng định dạng dd/mm/yyyy");
			} else if (reason == 'invalid value!') {
				$("#ngayChay-error").text("Ngày chạy máy không thể lớn hơn ngày hiện tại");
			}
		}
		
		if (errorField == 'tgKetThuc') {
			
			if (reason == 'wrong format!') {
				$("#tgKetThuc-error").text("Nhập thời gian kết thúc chạy máy đúng định dạng dd/mm/yyyy hh:mi:ss");
			} else if (reason == 'invalid value!') {
				$("#ngayThanhToan-error").text("Thời gian kết thúc chạy máy không thể lớn hơn ngày hiện tại");
			}
		}
	}); 
	
	function validateForm() {
		
		if (!$("#idTram").val()) {
			$("#idTram-error").text("ID trạm không được để trống");
			return false;
		}
		
		if (!$("#tgBatDau").val()) {
			$("#tgBatDau-error").text("Thời gian bắt đầu chạy máy không được để trống");
			return false;
		}
		
		if (!$("#tgKetThuc").val()) {
			$("#tgKetThuc-error").text("Thời gian kết thúc chạy máy không được để trống");
			return false;
		}
		
		
		if (loaiThanhToan == 'TTL') {			
			if (!$("#tongLitDm").val()) {
				$("#tongLitDm-error").text("Số lít không được để trống");
				return false;
			}
			if (!$("#soHoaDon").val()) {
				$("#soHoaDon-error").text("Số hóa đơn không được để trống");
				return false;
			}		
			if (!$("#ngayThanhToan").val()) {
				$("#ngayThanhToan-error").text("Ngày thanh toán không được để trống");
				return false;
			}
		} else {
			if (!$("#ngayChay").val()) {
				$("#ngayChay-error").text("Ngày chạy không được để trống");
				return false;
			}
			
			if (!$("#donGia").val()) {
				$("#donGia-error").text("Đơn giá không được để trống");
				return false;
			}
		}
		return true;	
	}
	
	function clearMessage() {
		if ($("#idTram").val()) {
			$("#idTram-error").text("");
		}
		if ($("#ngayChay").val()) {
			$("#ngayChay-error").text("");
		}
		if ($("#tgBatDau").val()) {
			$("#tgBatDau-error").text("");
		}
		if ($("#donGia").val()) {
			$("#donGia-error").text("");
		}

		if ($("#soHoaDon").val()) {
			$("#soHoaDon-error").text("");
		}
		if ($("#tongLitDm").val()) {
			$("#tongLitDm-error").text("");
		}
		if ($("#ngayThanhToan").val()) {
			$("#ngayThanhToan-error").text("");
		}
	}
</script>

<style>
	#submit {
		margin-right: 10px;
	}
    .required, .error {
    	color: red;
    }
    div#contents {
    	border: none;
    }
    
</style> 