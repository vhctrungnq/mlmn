<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>raw file list</title>
<content tag="heading">	DANH SÁCH DỮ LIỆU THÔ</content>

<form method="get" action="rawFile.htm" name="frmSample" onSubmit="return ValidateForm()">
	<table style="width:100%;" class="form">
		<tr>
		    <td>Từ ngày</td><td><input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10"></td>
            <td>Đến ngày</td><td><input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10"></td>
            <td>Trạng thái File Convert</td>
            <td><select name="convertFlag" id="convertFlag" style="width: 192px;">
	            				<c:choose>
								<c:when test="${convertFlag == 0}">
									<option value="2">Tất cả</option>
									<option value="-1">Convert bị lỗi</option>
									<option value="0" selected="selected">Chưa convert</option>
									<option value="1" >Đã convert</option>			
								</c:when>
								<c:when test="${convertFlag == -1}">
									<option value="-1" selected="selected">Convert bị lỗi</option>
									<option value="0">Chưa convert</option>
									<option value="1" >Đã convert</option>
									<option value="2">Tất cả</option>
								</c:when>
								<c:when test="${convertFlag == 1}">
									<option value="2">Tất cả</option>
									<option value="-1">Convert bị lỗi</option>
									<option value="0" >Chưa convert</option>
									<option value="1" selected="selected">Đã convert</option>					
								</c:when>
								<c:otherwise>
									<option value="2" selected="selected">Tất cả</option>
									<option value="-1">Convert bị lỗi</option>
									<option value="0" >Chưa convert</option>
									<option value="1" >Đã convert</option>									
								</c:otherwise>
								</c:choose>
							</select></td> 
            <td>Tên bảng thô</td><td><input value="${rawTable}" name="rawTable" id="rawTable" size="16" maxlength="30"/></td>         
        </tr>
        <tr>
        	<td>Tên file</td><td><input value="${fileName}" name="fileName" id="fileName" size="16" maxlength="300"/></td>
        	<td>Tên node</td><td><input value="${nodeName}" name="nodeName" id="nodeName" size="16" maxlength="200"/></td>
			<td>Trạng thái File Import</td>
			<td><select name="importFlag" id="importFlag" style="width: 192px;">
			            				<c:choose>
										<c:when test="${importFlag == -1}">
											<option value="2">Tất cả</option>
											<option value="0">Chưa import</option>
											<option value="1" >Đã import</option>
											<option value="-1" selected="selected" >Import lỗi</option>
										</c:when>
										<c:when test="${importFlag == 0}">
											<option value="2">Tất cả</option>
											<option value="0" selected="selected">Chưa import</option>
											<option value="1" >Đã import</option>
											<option value="-1">Import lỗi</option>
										</c:when>
										<c:when test="${importFlag ==1}">
											<option value="2">Tất cả</option>
											<option value="0" >Chưa import</option>
											<option value="1" selected="selected">Đã import</option>
											<option value="-1" >Import lỗi</option>											
										</c:when>
										<c:otherwise>
											<option value="2" selected="selected">Tất cả</option>
											<option value="0" >Chưa import</option>
											<option value="1" >Đã import</option>
											<option value="-1" >Import lỗi</option>											
										</c:otherwise>
										</c:choose>
									</select></td> 
          <td></td><td><input type="submit" class="button" name="filter" id="submit" value="Tìm kiếm"/></td>
        </tr>		
	</table>
</form>
<br/>
<div  style="overflow: auto;">
<display:table name="${rawFileList}" id="rawFile" class="simple3" export="true" requestURI="" pagesize="100" sort="external" defaultsort="1" size="${totalSize}">
    <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="NGÀY SINH DỮ LIỆU"/>        
    <display:column property="hour" titleKey="GIỜ SINH DỮ LIỆU"/>
    <display:column property="fileId" titleKey="FILE ID"/>
    <display:column property="fileName" titleKey="TÊN FILE"/>        
    <display:column property="statusFileConvert" titleKey="TRẠNG THÁI CONVERT FILE"/>
    <display:column property="statusFileImport" titleKey="TRẠNG THÁI IMPORT FILE"/>
    <display:column property="nodeName" titleKey="TÊN NODE"/>
    <display:column property="rawTable" titleKey="TÊN BẢNG DỮ LIỆU THÔ"/>
    <display:column property="originalName" titleKey="TÊN GỐC CỦA FILE"/>
    <display:setProperty name="export.csv.filename" value="Rawfile.csv"/>
	<display:setProperty name="export.excel.filename" value="Rawfile.xls"/>
	<display:setProperty name="export.xml.filename" value="Rawfile.xml"/>
</display:table>
</div>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script> --%>
<script type="text/javascript">  
function CalcKeyCode(aChar) {
	  var character = aChar.substring(0,1);
	  var code = aChar.charCodeAt(0);
	  return code;
	}

	function checkNumber(val) {
	  var strPass = val.value;
	  var strLength = strPass.length;
	  for(var i =0;i<strLength+1;i++){
	  var lchar = val.value.charAt((strLength) - i);
	  var cCode = CalcKeyCode(lchar);
	  if (cCode < 48 || cCode > 57 ) {
	    var myNumber = val.value.substring(0, (strLength) - i);
	    val.value = myNumber;
	  }
	  }
	  var sub = document.getElementById("submit");
		sub.focus();
	  return false;
	}
	function xl(){
		var sub = document.getElementById("submit");
		sub.focus();
	} 
	$(function() {
		$( "#startDate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
		$( "#endDate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
		
	});
</script>
