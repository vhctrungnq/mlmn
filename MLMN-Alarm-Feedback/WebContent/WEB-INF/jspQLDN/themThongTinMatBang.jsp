<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<title> CẬP NHẬT THÔNG TIN MẶT BẰNG</title>
<h3>Cập nhật thông tin mặt bằng</h3>
<form:form commandName="record" method="post" action="formTTMB.htm" id = "formTTMB" onsubmit="return validateForm();"> 
	<form:hidden path="id"/>
	<table class="simple2" >
				<tr> 
					<td class = "required"> Mã trạm</td> 
					<td><input id = "maTram" value = "${maTram}" maxlength="50" name = "maTram" 
						onblur = "clearMessage()"/> 
						<span id = "maTram-error" class = "error"> </span>
					</td> 
					<td class = "required"> Mã hợp đồng</td> 
					<td><input id = "idHopdong" value = "${idHopdong}" maxlength="50" name = "idHopdong" 
						onblur = "clearMessage()"/> 
						<span id = "idHopdong-error" class = "error"> </span>
					</td> 
				</tr>
				<tr>
					<td class = "required">Số hợp đồng</td>
					<td><input id = "sohd" value = "${sohd}" maxlength="50" name = "sohd" 
						onblur = "clearMessage()"/> 
						<span id = "sohd-error" class = "error"> </span>
					</td> 
					<td class = "required"> Tỉnh</td> 
					<td><input id = "tinh" value = "${tinh}" maxlength="50" name = "tinh" 
						onblur = "clearMessage()"/> 
						<span id = "tinh-error" class = "error"> </span>
					</td> 
				</tr>
				<tr>
					<td>Huyện</td>
					<td><input id = "huyen" value = "${huyen}" maxlength="50" name = "huyen" 
							onblur = "clearMessage()"/> 
						</td> 
						<td class = "required">Hình thức sở hữu</td>
					<td><input id = "htSohuu" value = "${htSohuu}" maxlength="50" name = "htSohuu" 
							onblur = "clearMessage()"/> 
							<span id = "htSohuu-error" class = "error"> </span>
						</td> 
					
				</tr>
				<tr>
					 <td>Tên đơn vị sở hữu</td>
					<td><input id = "tenDonViSoHuu" value = "${tenDonViSoHuu}" maxlength="50" name = "tenDonViSoHuu" 
							onblur = "clearMessage()"/> 
					</td>  
					<td class = "required">Tên chủ hợp đồng</td>
				<td><input id = "chuthehd" value = "${chuthehd}" maxlength="50" name = "chuthehd" 
						onblur = "clearMessage()"/> 
						<span id = "chuthehd-error" class = "error"> </span>
					</td>  
				</tr>
				<tr>
					<td class = "required">Ngày bắt đầu HĐ</td> 
					<td><input id = "Ngayhieuluc" value = "${Ngayhieuluc}" maxlength="50" name = "Ngayhieuluc" 
						onblur = "clearMessage()"/> 
						<img alt="calendar" title="Chon ngay bat dau" id="choosehdNgayHieuLuc" 
								style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
						<span id = "hdNgayhieuluc-error" class = "error"> </span>
					</td>
					<td class = "required">Ngày kết thúc HĐ</td> 
				<td><input id = "Ngayketthuc" value = "${Ngayketthuc}" maxlength="50" name = "Ngayketthuc" 
						onblur = "clearMessage()"/> 
						<img alt="calendar" title="Chon ngay bat dau" id="choosehdNgayKetThuc" 
								style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
						<span id = "hdNgayketthuc-error" class = "error"> </span>
				</td>
				</tr>
				<tr>
					 <td>Thời hạn hợp đồng</td>
					<td><input  id = "Ngayhethan" value = "${Ngayhethan}" maxlength="50" name = "Ngayhethan" 
							onblur = "clearMessage()"/> 
							<img alt="calendar" title="Chon ngay bat dau" id="choosehdNgayHetHan" 
								style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
						</td>  
					<td>Ghi chú</td>
					<td><input id = "ghiChu" value = "${ghiChu}" maxlength="50" name = "ghiChu" 
							onblur = "clearMessage()"/> 
					</td> 
				</tr>
			<tr>
				 
				<td>Ngày phát sóng</td>
				<td><input id = "ngayphatsong" value = "${ngayphatsong}" maxlength="50" name = "ngayphatsong" 
						onblur = "clearMessage()"/> 
						<img alt="calendar" title="Chon ngay bat dau" id="chooseNgayPS" 
								style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td> 
				<td>Nguồn CP mặt bằng BTS</td>
				<td><input id = "nguonBTS" value = "${nguonBTS}" maxlength="50" name = "nguonBTS" 
						onblur = "clearMessage()"/> 
				</td> 
			</tr>
			<tr>
				<td >Ngày thanh toán</td> 
				<td><input id = "ngaythanhtoan" value = "${ngaythanhtoan}" maxlength="50" name = "ngaythanhtoan" 
						onblur = "clearMessage()"/> 
						<img alt="calendar" title="Chon ngay bat dau" id="chooseNgayThanhToan" 
								style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				<td class = "required">Chu kỳ thanh toán (tháng)</td> 
				<td><input id = "chukytt" value = "${chukytt}" maxlength="50" name = "chukytt" 
						onblur = "clearMessage()"/>
						<span id = "chukytt-error" class = "error"> </span>
				</td> 
			</tr>
			<tr>
				<td class = "required">Đơn giá trong hợp đồng</td> 
				<td><input id = "dongiathangNovat" value = "${dongiathangNovat}" maxlength="50" name = "dongiathangNovat" 
						onblur = "clearMessage()"/>
						<span id = "dongiathangNovat-error" class = "error"> </span> 
				</td>
				<td>VAT</td> 
				<td><input id = "ttVat" value = "${ttVat}" maxlength="50" name = "ttVat" 
						onblur = "clearMessage()"/>
				</td> 
			</tr> 
			<tr>
				
				<td >Đơn giá sau VAT</td> 
				<td><input id = "hdDongiathangVat" value = "${hdDongiathangVat}" maxlength="50" name = "hdDongiathangVat" 
						onblur = "clearMessage()"/>
				</td> 
				<td >Tổng tiền / kỳ TT</td> 
				<td><input id = "ttSotiencktt" value = "${ttSotiencktt}" maxlength="50" name = "ttSotiencktt" 
						onblur = "clearMessage()"/> 
				</td> 
			</tr>
			<tr>
				 <td >Tổng tiền / năm</td> 
				<td><input id = "sotienggNam" value = "${sotienggNam}" maxlength="50" name = "sotienggNam" 
						onblur = "clearMessage()"/> 
				</td>
				 <td >Tổng tiền / HĐ</td> 
				<td><input id = "tongTienHD" value = "${tongTienHD}" maxlength="50" name = "tongTienHD" 
						onblur = "clearMessage()"/> 
				</td> 
			</tr>
			<tr>
				 <td class = "required" >Mã nhân viên</td> 
				<td><input id = "nguoiTao" value = "${nguoiTao}" maxlength="50" name = "nguoiTao" 
						onblur = "clearMessage()"/> 
						<span id = "nguoiTao-error" class = "error"> </span>
				</td> 
			</tr>
	</table>
	<br>
	<input type="submit" class="button" id = "submit" value="Save"> 
	<input type="button" class="button" id = "btCancel" value="Cancel" 
		onClick='window.location="thong-tin-mat-bang.htm"'>
</form:form>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.validate.min.js" ></script> --%>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script>
	function validateForm() {
		
		if (!$("#maTram").val().trim()) {
			$("#maTram-error").text("Mã trạm không được để trống");
			return false;
		}
		
		if (!$("#idHopdong").val().trim()) {
			$("#idHopdong-error").text("Mã hợp đồng không được để trống");
			return false;
		}
		
		if (!$("#sohd").val().trim()) {
			$("#sohd-error").text("Số hợp đồng không được để trống");
			return false;
		}	
		if (!$("#tinh").val().trim()) {
			$("#tinh-error").text("Tỉnh không được để trống");
			return false;
		}
		if (!$("#htSohuu").val().trim()) {
			$("#htSohuu-error").text("Hình thức sở hữu không được để trống");
			return false;
		}
		if (!$("#chuthehd").val().trim()) {
			$("#chuthehd-error").text("Chủ hợp đồng không được để trống");
			return false;
		}
		
		if (!$("#hdNgayhieuluc").val().trim()) {
			$("#hdNgayhieuluc-error").text("Ngày bắt đầu hợp đồng không được để trống");
			return false;
		}	
		if (!$("#hdNgayketthuc").val().trim()) {
			$("#hdNgayketthuc-error").text("Ngày kết thúc hợp đồng không được để trống");
			return false;
		}
		if (!$("#dongiathangNovat").val().trim()) {
			$("#dongiathangNovat-error").text("Đơn giá trong hợp đồng không được để trống");
			return false;
		}
		if (!$("#chukytt").val().trim()) {
			$("#chukytt-error").text("Chu kỳ thanh toán không được để trống");
			return false;
		}
		
		if (!$("#nguoiTao").val().trim()) {
			$("#nguoiTao-error").text("Mã nhân viên không được để trống");
			return false;
		}
		
		return true;	
	}
	
	function clearMessage() {
		 if ($("#maTram").val()) {
			$("#maTram-error").text("");
		}
		if ($("#idHopdong").val()) {
			$("#idHopdong-error").text("");
		}
		if ($("#sohd").val()) {
			$("#sohd-error").text("");
		}
		if ($("#dongiathangNovat").val()) {
			$("#dongiathangNovat-error").text("");
		} 
		 if ($("#tinh").val()) {
			$("#tinh-error").text("");
		}
		if ($("#htSohuu").val()) {
			$("#htSohuu-error").text("");
		}
		if ($("#chukytt").val()) {
			$("#chukytt-error").text("");
		}
		if ($("#hdNgayhieuluc").val()) {
			$("#hdNgayhieuluc-error").text("");
		} 
		if ($("#hdNgayketthuc").val()) {
			$("#hdNgayketthuc-error").text("");
		} 
		if ($("#chuthehd").val()) {
			$("#chuthehd-error").text("");
		} 
		if ($("#nguoiTao").val()) {
			$("#nguoiTao-error").text("");
		} 
	}
</script>
<script>


$(document).ready(function(){
	Calendar.setup({
	    inputField		:	"Ngayhieuluc",	// id of the input field
	    ifFormat		:	"%d/%m/%Y %H:%M:%S",   	// format of the input field
	    button			:   "choosehdNgayHieuLuc",  	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	});                                                                                                
	
	Calendar.setup({
	    inputField		:	"Ngayketthuc",	// id of the input field
	    ifFormat		:	"%d/%m/%Y %H:%M:%S",   	// format of the input field
	    button			:   "choosehdNgayKetThuc",   	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	}); 
	Calendar.setup({
	    inputField		:	"Ngayhethan",	// id of the input field
	    ifFormat		:	"%d/%m/%Y %H:%M:%S",   	// format of the input field
	    button			:   "choosehdNgayHetHan",  	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	}); 
	
	Calendar.setup({
	    inputField		:	"ngayphatsong",	// id of the input field
	    ifFormat		:	"%d/%m/%Y %H:%M:%S",   	// format of the input field
	    button			:   "chooseNgayPS",   	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	}); 
	Calendar.setup({
	    inputField		:	"ngaythanhtoan",	// id of the input field
	    ifFormat		:	"%d/%m/%Y %H:%M:%S",   	// format of the input field
	    button			:   "chooseNgayThanhToan",   	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	}); 

});
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