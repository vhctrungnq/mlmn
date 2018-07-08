<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<title> THÊM, SỬA THÔNG TIN MÁY NỔ </title>
<form:form commandName="record" method="post" action="form.htm" > 
	<form:hidden path="id"/>
	
	<table style = "width:80%;max-width:800px;">
		<tr>
			<td class = "label">Mã trạm</td> 
			<td><form:input path = "idTram" value = "${record.idTram}" maxlength="50" /></td>
			
			<td class = "label">Số hợp đồng</td>
			<td><form:input path = "soHopDong" value = "${record.soHopDong}" maxlength="200" /></td> 
		</tr>	
		<tr>
			<td class = "label">ID ĐTXD</td>
			<td><form:input path = "idDtxd" value = "${record.idDtxd }" maxlength="200" /></td>
			<td rowspan = "3" class = "label">Tên chủ nhà</td>
			<td rowspan="3"><form:textarea path = "tenChuNha" rows = "4"cols = "30"  /></td>
		</tr>
		<tr>
			<td class = "label">Tỉnh</td>
			<td><form:input path = "tinh" value = "${record.tinh}" /></td>
		</tr>
		<tr>	
			<td class = "label">Tổ</td>
			<td><form:input path = "to" value = "${record.to }" /></td>
		</tr>
		<tr>
			<td class = "label">Nhóm</td>
			<td><form:input path = "nhom" value = "${record.nhom }" /></td>
			<td class = "label">ATS</td>
			<td>
				<form:select path = "ats" >
					<form:option value = "" label = "--- Chọn ATS ---" />
					<form:options items = "${atsList }" />
				</form:select>	
			</td>
		</tr>
		<tr>
			<td class = "label">Hiệu máy nổ</td>
			<td><form:input path = "hieuMayNo" value = "${record.hieuMayNo }" /></td>
			<td class = "label">Công suất (KVA)</td>
			<td><form:input path="congSuat" value = "${record.congSuat }" class = "number-only" maxlength="10"/></td>
		</tr>
		<tr>
			<td class = "label">Loại nhiên liệu</td>
			<td>
				<form:select path = "loaiNhienLieu" >
					<form:option value = "" label = "--- Chọn loại nhiên liệu ---" />
					<form:options items = "${fuelList }" itemLabel="value" itemValue="name"/>
				</form:select>	
			</td>
			<td class = "label">Loại thanh toán</td>
			<td class = "label">
				<form:select path = "loaiThanhToan" >
					<form:option value = "" label = "--- Chọn loại thanh toán ---" />
					<form:options items = "${payList }" itemLabel="value" itemValue="name"/>
				</form:select>	
			</td>
		</tr>
		<tr>
			<td class = "label" >Định mức</td>
			<td><form:input path = "dinhMuc" value = "${record.dinhMuc }" class = "number-only" maxlength="10"/></td>
			
		</tr>
	</table>
	<br>
	<input type="submit" class="button" id = "submit" value="Save"/> 
	<input type="button" class="button" id = "btCancel" value="Cancel">
</form:form>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/focus-input-number.js" ></script>

<script>
	$(document).ready(function(){
		focusInputNumber();
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

	td > input, textarea {
		width:100%;
		height:100%;
	}
	
	.label {
		font-weight: bold;
	}

table {
	border-collapse: collapse;
}

table td {
	color: #000;
	padding: 4px;
	font-size: 12px;
	text-align: left;
	border: 1px solid #ccc;
}
</style> 