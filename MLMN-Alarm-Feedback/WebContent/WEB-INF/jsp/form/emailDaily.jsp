<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Gửi Email báo cáo hàng ngày</title>
<style>
#contents {
	border:0;
}
</style>
<div>
<table class="wid100">
	<tr>
		<td>Người gửi</td>
		<td>
			<b>${user.email}</b>
		</td>
		
		<td>Mật khẩu</td>
		<td>
			<input type="password" id="e_password" style="height:20px;">
		</td>
	</tr>
	
	<tr>
		<td>
			Người nhận
		</td>
		<td colspan="3">
			<input type="text" id="e_email" style="width:100%;" value="${email}">
		</td>
	</tr>
	
	<tr>
		<td>
			Tệp đính kèm
		</td>
		<td colspan="3">
			<table>
				<c:forEach var="item" items="${fileAtt}" varStatus="rowCounter">
					<tr><td><input type="checkbox" id="fileAtt" checked="checked" name="user_group[]" value="${item}">${item}</td></tr>
				</c:forEach>
			</table>
		</td>
	</tr>
	
	<tr>
		<td>Nội dung</td>
		<td colspan="3">
			<textarea style="width:100%; height: 220px" name="e_content" id="e_content" maxlength="900" ></textarea>
			<script type="text/javascript">
				CKEDITOR.replace( 'e_content',
				{
					toolbar :
						[
							['NewPage','-','Undo','Redo'],
							['Find','Replace','-','SelectAll','RemoveFormat'],
							['Link', 'Unlink', 'Image'],
							['FontSize', 'Bold', 'Italic','Underline'],
							['NumberedList','BulletedList','-','Blockquote'],
							['TextColor', '-', 'Smiley','SpecialChar', '-', 'Maximize']
						]
				});
		  	</script>
		</td>
	</tr>
	
	<tr>
		<td></td>
		<td>
			<input type="button" id="btnSubmit" value="Gửi" class="button">
		</td>
		<td>
			<span style="display:none;" id="statusSumit"><img height="10px" src="${pageContext.request.contextPath}/images/loading.gif"></span>
		</td>
	</tr>
</table>
</div>