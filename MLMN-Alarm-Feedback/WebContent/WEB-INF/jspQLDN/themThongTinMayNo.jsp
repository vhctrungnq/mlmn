<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<title> CẬP NHẬT THÔNG TIN MÁY NỔ</title>
<h3>Cập nhật thông tin máy nổ</h3>
<form:form commandName="record" method="post" action="formTTMN.htm" id = "formTTMN" onsubmit="return validateForm();"> 
	<form:hidden path="id"/>
	<table class="simple2" >
				<tr> 
					<td class = "required"> Mã trạm</td> 
					<td><form:input path = "idTram"  maxlength="50" name = "idTram" 
						onblur = "clearMessage()"/> 
						<span id = "idTram-error" class = "error"> </span>
					</td> 
					<td class = "required"> ID ĐTXD</td> 
					<td><form:input path = "idDtxd"  maxlength="50" name = "idDtxd" 
						onblur = "clearMessage()"/> 
						<span id = "idDtxd-error" class = "error"> </span>
					</td> 
				</tr>
				<tr>
					<td class = "required">Số hợp đồng</td>
					<td><form:input path = "soHopDong"  maxlength="50" name = "soHopDong" 
						onblur = "clearMessage()"/> 
						<span id = "soHopDong-error" class = "error"> </span>
					</td> 
					<td class = "required"> Loại thanh toán</td> 
					<td><div id = "loaiThanhToan"  name = "loaiThanhToan"></div>
						<span id = "loaiThanhToan-error" class = "error"> </span>
					</td> 
				</tr>
			<tr>
				<td>Tên XHH/VTT</td>
				<td><form:input path = "tenXhhVtt" maxlength="50" name = "tenXhhVtt" 
						onblur = "clearMessage()"/> 
					</td> 
					<td>Hiệu máy nổ</td>
				<td><form:input path = "hieuMayNo"  maxlength="50" name = "hieuMayNo" 
						onblur = "clearMessage()"/> 
					</td> 
				
			</tr>
			<tr>
				<td>Công suất</td>
				<td><form:input path = "congSuat"  maxlength="50" name = "congSuat" 
						onblur = "clearMessage()"/> 
					</td> 
					<td>Định mức</td>
				<td><form:input path = "dinhMuc" maxlength="50" name = "dinhMuc" 
						onblur = "clearMessage()"/> 
					</td> 
				
			</tr>
			<tr>
				<td>Loại nhiên liệu</td>
				<td><div id = "loaiNhienLieu"  name = "loaiNhienLieu"></div>
				</td> 
				<td>AST</td>
				<td> <div id = "ATS" name = "ATS"></div>
				</td> 
				
			</tr>
			<tr>
				<td >Tổ</td> 
				<td><form:input path = "to" maxlength="50" name = "to" 
						onblur = "clearMessage()"/> 
					<span id = "tenNganHang-error" class = "error"> </span>
				</td>
				<td> Tên chủ nhà</td> 
				<td><form:input path = "tenChuNha"  maxlength="50" name = "tenChuNha" 
						onblur = "clearMessage()"/>
				</td> 
			</tr>
			<tr>
				<td >Ghi chú</td> 
				<td><form:input path = "ghiChu"  maxlength="50" name = "ghiChu" 
						onblur = "clearMessage()"/> 
				</td>
				<td> Tình trạng</td> 
				<td><form:input path = "tinhTrang"  maxlength="50" name = "tinhTrang" 
						onblur = "clearMessage()"/>
				</td> 
			</tr>
			<tr>
				<td >Mức tải</td> 
				<td><form:input path = "mucTai"  maxlength="50" name = "mucTai" 
						onblur = "clearMessage()"/> 
				</td>
				<td> Định mức điện</td> 
				<td><form:input path = "dmDien"  maxlength="50" name = "dmDien" 
						onblur = "clearMessage()"/>
				</td> 
			</tr>
	</table>
	<br>
	<input type="submit" class="button" id = "submit" value="Save"> 
	<input type="button" class="button" id = "btCancel" value="Cancel" 
		onClick='window.location="thong-tin-may-no.htm"'>
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
		
		if (!$("#idTram").val().trim()) {
			$("#idTram-error").text("Mã trạm không được để trống");
			return false;
		}
		
		if (!$("#idDtxd").val().trim()) {
			$("#idDtxd-error").text("Mã DTXD không được để trống");
			return false;
		}
		
		if (!$("#loaiThanhToan").val().trim()) {
			$("#loaiThanhToan-error").text("Loại thanh toán không được để trống");
			return false;
		}	
		if (!$("#soHopDong").val().trim()) {
				$("#soHopDong-error").text("Số hợp đồng không được để trống");
				return false;
		}
		return true;	
	}
	
	function clearMessage() {
		 if ($("#idTram").val()) {
			$("#idTram-error").text("");
		}
		if ($("#idDtxd").val()) {
			$("#idDtxd-error").text("");
		}
		if ($("#loaiThanhToan").val()) {
			$("#loaiThanhToan-error").text("");
		}
		if ($("#soHopDong").val()) {
			$("#soHopDong-error").text("");
		} 
		
	}
	
	var theme= getTheme();
	//Create a jqxComboBox vendor
	var urlloaithanhtoan = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=LOAI_THANH_TOAN";
	// prepare the data
	var sourceloaithanhtoan =
	{
	    datatype: "json",
	    datafields: [
	        { name: 'value' },
	        { name: 'name' }
	    ],
	    url: urlloaithanhtoan,
	    async: false
	};
	var dataAdapterloaithanhtoan = new $.jqx.dataAdapter(sourceloaithanhtoan);
	// Create a jqxComboBox
	$("#loaiThanhToan").jqxComboBox({ source: dataAdapterloaithanhtoan, displayMember: "value", valueMember: "name", width: 172,height: '20px',itemHeight: 30,theme: theme,autoOpen: true });
	var loaithanhtoan =  "<c:out value='${loaiThanhToan}'/>";
	if(loaithanhtoan=="")
	$('#loaiThanhToan').val('');
	else
	$('#loaiThanhToan').val(loaithanhtoan);

	var urlloainhienlieu = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=LOAI_NHIEN_LIEU";
	//prepare the data
	var sourceloainhienlieu =
	{
	 datatype: "json",
	 datafields: [
	     { name: 'value' },
	     { name: 'name' }
	 ],
	 url: urlloainhienlieu,
	 async: false
	};
	var dataAdapterloainhienlieu = new $.jqx.dataAdapter(sourceloainhienlieu);
	//Create a jqxComboBox
	$("#loaiNhienLieu").jqxComboBox({ source: dataAdapterloainhienlieu, displayMember: "value", valueMember: "name",  width: 172,height: '20px',itemHeight: 30,theme: theme,autoOpen: true });
	var loainhienlieu =  "<c:out value='${loaiNhienLieu}'/>";
	if(loainhienlieu=="")
	$('#loaiNhienLieu').val('');
	else
	$('#loaiNhienLieu').val(loainhienlieu);

	var urlATS = "${pageContext.request.contextPath}/ajax/getParameterByCode.htm?code=ATS";
	//prepare the data
	var sourceATS =
	{
	datatype: "json",
	datafields: [
	   { name: 'value' },
	   { name: 'name' }
	],
	url: urlATS,
	async: false
	};
	var dataAdapterATS = new $.jqx.dataAdapter(sourceATS);
	//Create a jqxComboBox
	$("#ATS").jqxComboBox({ source: dataAdapterATS, displayMember: "value", valueMember: "name", width: 150,height: '20px',itemHeight: 30,theme: theme,autoOpen: true });
	var ATS =  "<c:out value='${loaiNhienLieu}'/>";
	if(ATS=="")
	$('#ATS').val('');
	else
	$('#ATS').val(ATS);
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