<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title><fmt:message key="Import alarm đồng bộ"/></title>
<content tag="heading"><fmt:message key="Import alarm đồng bộ"/></content>
      
  
<style>
	.uploadifive-button {
		margin-right: 10px;
	}
	
	.queue-item {
		overflow: auto;
		margin-bottom: 10px;
		padding: 0 3px 3px;
	}
	
	.uploadifive-queue-item {
	    margin-top: 3px;
    	padding: 1px 10px;
	}
	
	.uploadifive-queue-item .close {
    background: url("${pageContext.request.contextPath}/js/uploadifive/uploadifive-cancel.png") no-repeat scroll 0 0 transparent;
    display: block;
    float: left;
    height: 16px;
    text-indent: -9999px;
    width: 16px;
	}
	
	.filename{
	color: #0560A6;
	}
</style>
<div class="body-content"></div>
<form  method="post" action="importAlarm.htm" id = "importForm"> 
	<div>
    	<input id="id" type="hidden" />
    	<input type="hidden" id="action" name="action">
    </div>
    <table class="simple2">  
 	<tr style="height:60px;">
 		<td><b>
 			<fmt:message key="global.FileExample"/></b>
 		</td>
 		<td colspan="2">
 			<ul>
 				<li><fmt:message key="global.formatFile1"/></li>
 				<li><fmt:message key="global.formatFile2"/></li>
 				<li><fmt:message key="global.file"/>
			&nbsp;<a href="${pageContext.request.contextPath}/upload/rawfile/ALU_xxxx.xls" style="color: blue; ">ALU_xxx.xls</a>&nbsp;,
			&nbsp;<a href="${pageContext.request.contextPath}/upload/rawfile/Ericsson_xxxx.txt" style="color: blue; ">Ericsson_xxx.txt</a>&nbsp;,
			&nbsp;<a href="${pageContext.request.contextPath}/upload/rawfile/NSN_xxxx.txt" style="color: blue; ">NSN_xxx.txt</a>&nbsp;,
			&nbsp;<a href="${pageContext.request.contextPath}/upload/rawfile/Huawei_xxxx.csv" style="color: blue; ">Huawei_xxx.csv</a>&nbsp;
		</li>
 			</ul>
 		</td> 
 	</tr>
      <tr>
      	<td style="vertical-align:middle;"><b><fmt:message key="isoEquipment.fileAttach"/></b></td>
      	<td colspan="3">
      			<input type = "hidden" id="fileId" name="fileId" readonly="true"/>
				<div style="margin-bottom: 7px">
					<div class="queue-item">
						<div id="queue-upload"></div>
						<div id="queue"></div>
					</div>
					
					<input id="file_upload" name="file_upload" type="file" multiple="true">
					
				</div>
      	</td>
      </tr>
      <tr>
      <td><b>Network</b></td>
      <td>
      <select id="network" name = "network" class="wid90" onchange="xl()"> 
				<option  value="">Tất cả</option >
				<c:forEach var="item" items="${networkList}">
					<c:choose>
						<c:when test="${item.value == network}">
							<option  value="${item.value}" selected="selected">${item.value}</option >
						</c:when>
						<c:otherwise>
							<option  value="${item.value}">${item.value}</option >
						</c:otherwise>
					</c:choose>
				</c:forEach>
		</select>
      </td>
      	<td   > 
      	<b><fmt:message key="alarmLog.edateT"/></b>&nbsp;&nbsp;
		<input type ="text"  value="${etime}" name="etime" id="etime" size="17" maxlength="19" style="width:150px">
	 	<img alt="calendar" title="Click to choose the from date" id="chooseEDateT" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
		&nbsp;&nbsp;&nbsp;&nbsp;	
      	</td>
      </tr>
      <tr>
        <td></td>
        <td colspan="3">
            <input class="button" type="button" name="synHis" id = "synHis"  value="<fmt:message key="Sync History Alarms"/>" />
            <input class="button" type="button" name="synAct" id = "synAct"  value="<fmt:message key="Sync Active Alarms"/>" />
        </td>
      </tr>
    </table>
</form>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
 <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
 
<script src="<%=request.getContextPath() %>/js/uploadifive/jquery.uploadifive.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/uploadifive/uploadifive.css">
<link href="<%=request.getContextPath() %>/js/ajaxupload/fileuploader.css" rel="stylesheet" type="text/css">	
<script src="<%=request.getContextPath() %>/js/ajaxupload/fileuploader.js" type="text/javascript"></script>
 
<script type="text/javascript">
Calendar.setup({
    inputField		:	"etime",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M:%S",   	// format of the input field
    button			:   "chooseEDateT",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					
}); 
</script>

<script type="text/javascript">
$(function() {
	$('#file_upload').uploadifive({
		
		'method'			: 'post',
		'auto'         		: true,
		'multi' 			: true,
		'formData'			: {'path' : 'documents'},
		'queueID'      		: 'queue',
		'uploadScript' 		: '${pageContext.request.contextPath}/file/upload-alarm-syn/docpa.htm',
		'fileObjName' 		: 'filedata',
		'onInit'		: function() {
			var fileId = $('#fileId').val(); 
			if (fileId != '') {
				var fileList = fileId.split(",");
	
				if (fileList != null) {
					for (var i=0; i<fileList.length; i++) {
						$.getJSON("${pageContext.request.contextPath}/file/get.htm",{fileId: fileList[i]}, function(j){
							if (j==null) {
								return;
							}
							$('#queue-upload').append(
								'<div class="uploadifive-queue-item" id="uploadifive-file_upload-file--' + j.id + '">' +
								'	<a href="javascript:removeAttachFile(' + j.id + ')" class="close">X</a>' +
								'	<div>'+
								'		<span class="filename"><a href="${pageContext.request.contextPath}/file/download-alarm-syn.htm?id='+ j.id +'">'+ j.fileName +'</a></span>'+
								'		<span class="fileinfo"></span>'+
								'	</div>' +
								'	<div class="progress" style="display: none;">' +
								'		<div class="progress-bar"></div>' +
								'	</div>' +
								'</div>'
							);
						});
					}
				}
			}
		},
		'onSelect'         	: function() {
			$('#file_upload').uploadifive('upload');
		},
		'onUploadComplete' : function(file, data) {
			addFileId(data); 
		}
	});
});

function removeAttachFile(v_sysFileId) {
	$.getJSON("${pageContext.request.contextPath}/file/delete-alarm-syn.htm",{id: v_sysFileId}, function(j){});

	var item = $('#uploadifive-file_upload-file--'+v_sysFileId);
	item.hide("fast");
	item.html('');
	var file_upload = '';
	var fileId = $('#fileId').val();
	if (fileId != '') {
		var fileList = fileId.split(",");

		if (fileList != null) {
			for (var i=0; i<fileList.length; i++) {
				if (fileList[i] != v_sysFileId) {
					file_upload += fileList[i] + ',';
				}
			}
		}
	}
	
	$("#fileId").val(file_upload);
}

function getUrlFileName(v_sysFileId, v_fileName) {
	return '<a href="${pageContext.request.contextPath}/file/download-alarm-syn.htm?id=' + v_sysFileId + '">' + v_fileName + '</a>';
}

function addFileId(data) {
	var fileId = $("#fileId").val();
	$("#fileId").val(fileId + data + ',');
}
$( "#synAct" ).click(function() {
	var action = document.getElementById("action");
	action.value = "synAct";  
	var etime = document.getElementById("etime");
	var network = document.getElementById("network");
	if(network.value == ''){
		alert("Bạn chưa chọn loại mạng"); 
		network.focus();
	}else if(etime.value == ''){
		alert("Bạn chưa nhập thời gian kết thúc"); 
		etime.focus();
	}
	 else{
		$( "#importForm" ).submit();
	}
});
$( "#synHis" ).click(function() {
	var action = document.getElementById("action"); 
	action.value = "synHis";  
	$( "#importForm" ).submit();
	});
</script>