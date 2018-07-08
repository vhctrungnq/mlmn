<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<c:choose>
  <c:when test="${cableSwitchboardAddEdit == 'N'}">
      <title><fmt:message key="title.cableSwitchboardFormAdd"/></title>
	  <content tag="heading"><fmt:message key="title.cableSwitchboardFormAdd"/></content>
  </c:when>
  <c:when test="${cableSwitchboardAddEdit == 'Y'}">
      <title><fmt:message key="title.cableSwitchboardFormEdit"/></title>
	  <content tag="heading"><fmt:message key="title.cableSwitchboardFormEdit"/></content>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>

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

<form:form commandName="cableSwitchboard" name="checkform" method="post" action="form.htm"> 
	<div>
    	<form:input path="id" type="hidden" />
    </div>
    <table class="simple2">
      <tr>
           <td class="wid15 mwid110"><fmt:message key="cableSwitchboard.vendor"/>&nbsp;<font color="red">(*)</font></td>
           <td class="wid35">
		       	<form:input path="vendor" maxlength="100" />
		       	&nbsp;<form:errors path="vendor" cssClass="error"/>
           </td>
           <td class="wid15 mwid110"><fmt:message key="cableSwitchboard.neType"/>&nbsp;<font color="red">(*)</font></td>
           <td>
           		<form:input path="neType" maxlength="100" />
           		&nbsp;<form:errors path="neType" cssClass="error"/>
           </td>       
      </tr>
      <tr>
           <td><fmt:message key="cableSwitchboard.name"/></td>
           <td>
				<form:input path="name" maxlength="220" />
           </td>
           <td><fmt:message key="cableSwitchboard.site"/></td>
           <td>
				<form:input path="site" maxlength="100" />
           </td>       
      </tr>
      <tr>
           <td><fmt:message key="cableSwitchboard.description"/></td>
           <td>
		       	<form:input path="description" maxlength="220" />
           </td>
           <td></td>
           <td></td>      
      </tr> 
      <tr>
           <td style="vertical-align:middle;"><fmt:message key="cableSwitchboard.file"/></td>
           <td colspan="3">
           		<form:hidden path="fileId" readonly="true"/>
				<div style="margin-bottom: 5px">
					<div class="queue-item">
						<div id="queue-upload"></div>
						<div id="queue"></div>
					</div>
					
					<input id="file_upload" name="file_upload" type="file" multiple="true">
				</div>
           </td>       
      </tr>  
      <tr>
        <td></td>
        <td colspan="3">
         <c:if test="${isManager=='Y' }">
            <input class="button" type="submit" name="save" value="<fmt:message key="global.form.luulai"/>" />
		 </c:if>
            <input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm"'>
        </td>
      </tr>
    </table>
</form:form>

<script type="text/javascript">

function focusIt()
{
	
	if(document.checkform.vendor.value==""){
		  var mytext = document.getElementById("vendor");
		  mytext.focus();
		}
	else if(document.checkform.neType.value==""){
		  var mytext = document.getElementById("neType");
		  mytext.focus();
		}
}

onload = focusIt;

</script>
<script type="text/javascript">
var theme = getTheme();
$("#vendor").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#neType").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#name").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#site").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#description").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
</script>
<script src="<%=request.getContextPath() %>/js/uploadifive/jquery.uploadifive.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/uploadifive/uploadifive.css">
<link href="<%=request.getContextPath() %>/js/ajaxupload/fileuploader.css" rel="stylesheet" type="text/css">	
<script src="<%=request.getContextPath() %>/js/ajaxupload/fileuploader.js" type="text/javascript"></script>

<script type="text/javascript">
$(function() {
	$('#file_upload').uploadifive({
		
		'method'			: 'post',
		'auto'         		: true,
		'multi' 			: true,
		'formData'			: {'path' : 'documents'},
		'queueID'      		: 'queue',
		'uploadScript' 		: '${pageContext.request.contextPath}/file/upload/docpa.htm',
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
								'		<span class="filename"><a href="${pageContext.request.contextPath}/file/download.htm?id='+ j.id +'">'+ j.fileName +'</a></span>'+
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
	$.getJSON("${pageContext.request.contextPath}/file/delete.htm",{id: v_sysFileId}, function(j){});

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
	return '<a href="${pageContext.request.contextPath}/file/download.htm?id=' + v_sysFileId + '">' + v_fileName + '</a>';
}

function addFileId(data) {
	var fileId = $("#fileId").val();
	$("#fileId").val(fileId + data + ',');
}
</script>