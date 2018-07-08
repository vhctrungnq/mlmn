<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<title> CẬP NHẬT THÔNG TIN ĐƠN VỊ THỤ HƯỞNG </title>
<h3>Cập nhật thông tin đơn vị thụ hưởng</h3>
<form:form commandName="record" method="post" action="formDVTH.htm" id = "formDVTH" onsubmit="return validateForm();"> 
	<form:hidden path="id"/>
	<table class="simple2" >
				<tr> 
					<td class = "required"> Tên đơn vị thụ hưởng</td> 
					<td><form:input path = "tenDv" value = "${record.tenDv}" maxlength="50" name = "tenDv" 
						onblur = "clearMessage()"/> 
						<span id = "tenDv-error" class = "error"> </span>
					</td> 
					
					<td class = "required"> Mã số thuế</td> 
					<td><form:input path = "maSoThue" value = "${record.maSoThue}" maxlength="50" name = "maSoThue" 
						onblur = "clearMessage()"/> 
						<span id = "maSoThue-error" class = "error"> </span>
					</td>
				</tr>
				<tr> 
					<td class = "required">Số tài khoản</td> 
					<td><form:input path = "soTaiKhoan" value = "${record.soTaiKhoan}" maxlength="50" name = "soTaiKhoan" 
						onblur = "clearMessage()"/> 
						<span id = "soTaiKhoan-error" class = "error"> </span>
					</td>
					
					<td class = "required">Tên ngân hàng</td> 
					<td><form:input path = "tenNganHang" value = "${record.tenNganHang}" maxlength="50" name = "tenNganHang" 
						onblur = "clearMessage()"/> 
						<span id = "tenNganHang-error" class = "error"> </span>
					</td>
				</tr>
				
				<tr>
					<td> Địa chỉ ngân hàng </td>
					<td><form:input path = "diaChiNh" value = "${record.diaChiNh}" maxlength="50" name = "diaChiNh" 
						onblur = "clearMessage()"/> 
						
					</td>
					<td> Liên hệ </td> 
					<td><form:input path = "lienHe" value = "${record.lienHe}" maxlength="50" name = "lienHe" 
						onblur = "clearMessage()"/> 
						
					</td> 
				</tr>
			<tr>
				<td>Ghi chú</td>
				<td><form:input path = "ghiChu" value = "${record.ghiChu}" maxlength="50" name = "ghiChu" 
						onblur = "clearMessage()"/> 
					</td> 
				
			</tr>
			
	</table>
	<br>
	<input type="submit" class="button" id = "submit" value="Save"> 
	<input type="button" class="button" id = "btCancel" value="Cancel" 
		onClick='window.location="list.htm"'>
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
		
		if (!$("#tenDv").val().trim()) {
			$("#tenDv-error").text("Tên đơn vị không được để trống");
			return false;
		}
		
		if (!$("#maSoThue").val().trim()) {
			$("#maSoThue-error").text("Mã số thuế không được để trống");
			return false;
		}
		
		if (!$("#soTaiKhoan").val().trim()) {
			$("#soTaiKhoan-error").text("Số tài khoản không được để trống");
			return false;
		}	
		if (!$("#tenNganHang").val().trim()) {
				$("#tenNganHang-error").text("Tên ngân hàng không được để trống");
				return false;
		}
		return true;	
	}
	
	function clearMessage() {
		 if ($("#tenDv").val()) {
			$("#tenDv-error").text("");
		}
		if ($("#maSoThue").val()) {
			$("#maSoThue-error").text("");
		}
		if ($("#soTaiKhoan").val()) {
			$("#soTaiKhoan-error").text("");
		}
		if ($("#tenNganHang").val()) {
			$("#tenNganHang-error").text("");
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