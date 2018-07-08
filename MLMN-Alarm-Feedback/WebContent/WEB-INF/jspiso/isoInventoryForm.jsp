<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<c:choose>
 		<c:when test="${isoEquipmentAddEdit == 'N'}">
      	<title><fmt:message key="sidebar.admin.isoResourceFormAdd"/></title>
  		<content tag="heading"><fmt:message key="sidebar.admin.isoResourceFormAdd"/></content>
  	</c:when>
  	<c:when test="${isoEquipmentAddEdit == 'Y'}">
      	<title><fmt:message key="sidebar.admin.isoResourceFormEdit"/></title>
  		<content tag="heading"><fmt:message key="sidebar.admin.isoResourceFormEdit"/></content>
  	</c:when>
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
<div class="body-content"></div>
<form:form commandName="isoInventory" name="checkform" method="post" action="form.htm" > 
	<div>
    	<form:input path="id" type="hidden" />
    </div>
    <table class="simple2">
      <tr>
           <td class="wid15 mwid110"><fmt:message key="isoEquipment.deptCode"/></td>
           <td class="wid35">
                    <form:select path="deptCode" cssClass="wid60">
	   					<c:forEach var="items" items="${mDepartmentList}">
			              <c:choose>
			                <c:when test="${items.deptCode == deptCodeCBB}">
			                    <option value="${items.deptCode}" selected="selected">${items.deptCode}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${items.deptCode}">${items.deptCode}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
	           		</form:select>
           </td>
           <td class="wid15 mwid110"><fmt:message key="isoEquipment.team"/></td>
      	   <td ><form:input path="team" maxlength="35" cssClass="wid90"/></td>       
      </tr>
      <tr>
      	<td><fmt:message key="isoEquipment.subTeam"/></td>
      	<td><form:input path="subTeam" maxlength="35" cssClass="wid90"/></td>
        <td><fmt:message key="isoEquipment.location"/></td>
        <td><form:input path="location" maxlength="35" cssClass="wid90"/></td>
      </tr>
      <tr>
      	<td ><fmt:message key="isoEquipment.province"/></td>
       	<td>
       		<form:select path="province" cssClass="wid60">
				<c:forEach var="items" items="${hProvinceCodeList}">
		            <c:choose>
		              <c:when test="${items.province == provinceCBB}">
		                  <option value="${items.province}" selected="selected">${items.province}</option>
		              </c:when>
		              <c:otherwise>
		                  <option value="${items.province}">${items.province}</option>
		              </c:otherwise>
		            </c:choose>
    			</c:forEach>
       		</form:select>
       	</td>
        <td><fmt:message key="isoEquipment.district"/></td>
        <td>
        	<form:select path="district" id="district" cssClass="wid60">
        		<c:forEach var="items" items="${quanhuyenList}">
	              	<c:choose>
	                <c:when test="${items.district == quanHuyenCBB}">
	                    <option value="${items.district}" selected="selected">${items.district}</option>
	                </c:when>
	                <c:otherwise>
	                    <option value="${items.district}">${items.district}</option>
	                </c:otherwise>
	              	</c:choose>
		    	</c:forEach>
			</form:select>
        </td>   	
      </tr> 
      <tr>
           <td><fmt:message key="isoEquipment.neType"/></td>
           <td>
           		<form:select path="neType" cssClass="wid60">
   					<c:forEach var="items" items="${neTypeList}">
		              <c:choose>
		                <c:when test="${items.value == neTypeCBB}">
		                    <option value="${items.value}" selected="selected">${items.value}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.value}">${items.value}</option>
		                </c:otherwise>
		              </c:choose>
				    </c:forEach>
           		</form:select>
           </td>
    	   <td><fmt:message key="isoEquipment.ne"/>&nbsp;</td>
           <td><form:input path="ne" maxlength="15" cssClass="wid90"/></td>
      </tr>
      <tr>
      	<td><fmt:message key="isoEquipment.neParent"/></td>
        <td><form:input path="neParent" maxlength="15" cssClass="wid90"/></td>
        <td><fmt:message key="isoEquipment.locationName"/></td>
      	<td><form:input path="locationName" maxlength="15" cssClass="wid90"/></td>
      </tr>
      <tr>
      	<td><fmt:message key="isoEquipment.productCode"/>&nbsp;<font color="red">(*)</font></td>
      	<td><form:input path="productCode" maxlength="25" cssClass="wid90"/>&nbsp;<form:errors path="productCode" cssClass="error"/></td>
        <td><fmt:message key="isoEquipment.seriNo"/>&nbsp;<font color="red">(*)</font></td>
        <td><form:input path="seriNo" maxlength="25" cssClass="wid90"/>&nbsp;<form:errors path="seriNo" cssClass="error"/></td>
      </tr>
      <tr>
      	<td><fmt:message key="isoEquipment.productDate"/></td>
      	<td>
      		<input id="productDate" name="productDate" value="${productDate}" class="wid30" maxlength="20"/>
			<img alt="calendar" title="Click to choose the date" id="chooseProductDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
           	&nbsp;<form:errors path="productDate" cssClass="error"/>
      	</td>
      	<td><fmt:message key="isoEquipment.productName"/></td>
        <td><form:input path="productName" maxlength="17" cssClass="wid90"/></td>
      </tr>
      <tr>
      	<td><fmt:message key="isoEquipment.codeAssetType"/></td>
      	<td>
			<form:select path="neGroup" cssClass="wid60">
				<c:forEach var="items" items="${codeAssetTypeList}">
					<c:choose>
					<c:when test="${items.code == neGroupCBB}">
					   <option value="${items.code}" selected="selected">${items.code}</option>
					</c:when>
					<c:otherwise>
					   <option value="${items.code}">${items.code}</option>
					</c:otherwise>
					</c:choose>
				</c:forEach>
			</form:select>
      	</td>
      	<td><fmt:message key="isoEquipment.status"/></td>
      	<td>
			<form:select path="status" cssClass="wid60">
				<c:forEach var="items" items="${statusList}">
					<c:choose>
					<c:when test="${items.value == statusCBB}">
					   <option value="${items.value}" selected="selected">${items.name}</option>
					</c:when>
					<c:otherwise>
					   <option value="${items.value}">${items.name}</option>
					</c:otherwise>
					</c:choose>
				</c:forEach>
			</form:select>
      	</td>
      </tr>
      <tr>
      	<td><fmt:message key="isoLicenseSoft.vendor"/></td>
      	<td>
			<form:select path="vendor" cssClass="wid30">
				<c:forEach var="items" items="${vendorForResourceList}">
					<c:choose>
					<c:when test="${items.value == vendorCBB}">
					   <option value="${items.value}" selected="selected">${items.value}</option>
					</c:when>
					<c:otherwise>
					   <option value="${items.value}">${items.value}</option>
					</c:otherwise>
					</c:choose>
				</c:forEach>
			</form:select>
      	</td>
      	<td><fmt:message key="isoEquipment.rack"/></td>
      	<td>
			<form:input path="rack" maxlength="10" cssClass="wid30"/>
      	</td>
      </tr>
      <tr>
      	<td><fmt:message key="isoEquipment.subrack"/></td>
      	<td><form:input path="subrack" maxlength="10" cssClass="wid30"/></td>
      	<td><fmt:message key="isoEquipment.slot"/></td>
      	<td><form:input path="slot" maxlength="10" cssClass="wid30"/></td>
      </tr>
      <tr>
      	<td><fmt:message key="isoEquipment.swVersion"/></td>
      	<td>
			<form:input path="swVersion" maxlength="17" cssClass="wid90"/>
      	</td>
      	<td><fmt:message key="isoEquipment.initializeDate"/></td>
      	<td>
			<input id="initializeDate" name="initializeDate" value="${initializeDate}" class="wid30" maxlength="20"/>
			<img alt="calendar" title="Click to choose the date" id="chooseInitializeDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
           	&nbsp;<form:errors path="initializeDate" cssClass="error"/>
      	</td>
      </tr>
      <tr>
      	<td><fmt:message key="isoEquipment.neOld"/></td>
      	<td>
			<form:input path="neOld" maxlength="15" cssClass="wid90"/>
      	</td>
      	<td><fmt:message key="isoEquipment.updated"/></td>
      	<td>
			<input id="updated" name="updated" value="${updated}" class="wid30" maxlength="20"/>
			<img alt="calendar" title="Click to choose the date" id="chooseUpdated" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
           	&nbsp;<form:errors path="updated" cssClass="error"/>
      	</td>
      </tr>
      <tr>
		<td colspan="4">
			<span style="font-size:12px;"><b><i>Thông tin thiết bị </i></b></span>
		</td>
	  </tr>
      <tr>
      	<td><fmt:message key="isoEquipment.omIp"/></td>
      	<td><form:input path="omIp" maxlength="15" cssClass="wid90"/></td>
      	<td><fmt:message key="isoEquipment.nsei"/></td>
      	<td><form:input path="nsei" maxlength="15" cssClass="wid90"/></td>
      </tr>
      <tr>
      	<td><fmt:message key="isoEquipment.spc"/></td>
      	<td><form:input path="spc" maxlength="15" cssClass="wid90"/></td>
      	<td><fmt:message key="isoEquipment.bscidRncid"/></td>
      	<td><form:input path="bscidRncid" maxlength="15" cssClass="wid90"/></td>
      </tr>
      <tr>
      	<td><fmt:message key="isoEquipment.power"/></td>
      	<td><form:input path="power" maxlength="4" cssClass="wid30"/>&nbsp;<form:errors path="power" cssClass="error"/></td>
      	<td><fmt:message key="isoEquipment.defaultCurrent"/></td>
      	<td><form:input path="defaultCurrent" maxlength="4" cssClass="wid30"/>&nbsp;<form:errors path="defaultCurrent" cssClass="error"/></td>
      </tr>
      <tr>
      	<td><fmt:message key="isoEquipment.defaultVoltage"/></td>
      	<td><form:input path="defaultVoltage" maxlength="4" cssClass="wid30"/>&nbsp;<form:errors path="defaultVoltage" cssClass="error"/></td>
      	<td><fmt:message key="isoEquipment.realCurrent"/></td>
      	<td><form:input path="realCurrent" maxlength="4" cssClass="wid30"/>&nbsp;<form:errors path="realCurrent" cssClass="error"/></td>
      </tr>
      <tr>
      	<td><fmt:message key="isoEquipment.loadFollow"/></td>
      	<td><form:input path="loadFollow" maxlength="4" cssClass="wid30"/>&nbsp;<form:errors path="loadFollow" cssClass="error"/></td>
      	<td><fmt:message key="isoEquipment.battery"/></td>
      	<td><form:input path="battery" maxlength="4" cssClass="wid30"/>&nbsp;<form:errors path="battery" cssClass="error"/></td>
      </tr>
      <tr>
      	<td><fmt:message key="isoEquipment.batteryDuration"/></td>
      	<td><form:input path="batteryDuration" maxlength="4" cssClass="wid30"/>&nbsp;<form:errors path="batteryDuration" cssClass="error"/></td>
      	<td><fmt:message key="isoEquipment.maintenanceInterval"/></td>
      	<td><form:input path="maintenanceInterval" maxlength="4" cssClass="wid30"/>&nbsp;<form:errors path="maintenanceInterval" cssClass="error"/></td>
      </tr>
      <tr>
      	<td><fmt:message key="isoEquipment.maintenanceDate"/></td>
      	<td>
      		<input id="maintenanceDate" name="maintenanceDate" value="${maintenanceDate}" class="wid30" maxlength="20"/>
			<img alt="calendar" title="Click to choose the date" id="chooseMaintenanceDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
           	&nbsp;<form:errors path="maintenanceDate" cssClass="error"/>
      	</td>
      	<td><fmt:message key="isoEquipment.maintenanceResult"/></td>
      	<td><form:input path="maintenanceResult" maxlength="210" cssClass="wid90"/></td>
      </tr>
      <tr>
      	<td><fmt:message key="isoEquipment.maintenanceSupervisor"/></td>
      	<td><form:input path="maintenanceSupervisor" maxlength="25" cssClass="wid90"/></td>
      	<td><fmt:message key="isoEquipment.maintenanceComment"/></td>
      	<td><form:input path="maintenanceComment" maxlength="250" cssClass="wid90"/></td>
      </tr>
      <tr>
      	<td><fmt:message key="isoEquipment.contractNumber"/></td>
      	<td><form:input path="contractNumber" maxlength="15" cssClass="wid90"/></td>
      	<td><fmt:message key="isoEquipment.contractDate"/></td>
      	<td>
      		<input id="contractDate" name="contractDate" value="${contractDate}" class="wid30" maxlength="20"/>
			<img alt="calendar" title="Click to choose the date" id="chooseContractDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
           	&nbsp;<form:errors path="contractDate" cssClass="error"/>
      	</td>
      </tr>
      <tr>
      	
      	<td><fmt:message key="isoEquipment.description"/></td>
      	<td colspan="3"><form:input path="description" id ="description" cssClass="wid96" maxlength="250"/></td>
      </tr>
      <tr>
      	<td style="vertical-align:middle;"><fmt:message key="isoEquipment.fileAttach"/></td>
      	<td colspan="3">
      			<form:hidden path="fileId" readonly="true"/>
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
        <td></td>
        <td colspan="3">
            <input class="button" type="submit" name="save" value="<fmt:message key="global.form.luulai"/>" />
            <input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm"'>
        </td>
      </tr>
    </table>
</form:form>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<script type="text/javascript">
Calendar.setup({
    inputField		:	"initializeDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseInitializeDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"productDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseProductDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"maintenanceDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseMaintenanceDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"contractDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseContractDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"updated",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseUpdated",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

function focusIt()
{
	var powerError = '<c:out value="${powerError}"/>';
	var defaultCurrentError = '<c:out value="${defaultCurrentError}"/>';
	var defaultVoltageError = '<c:out value="${defaultVoltageError}"/>';
	var realCurrentError = '<c:out value="${realCurrentError}"/>';
	var loadFollowError = '<c:out value="${loadFollowError}"/>';
	var batteryError = '<c:out value="${batteryError}"/>';
	var batteryDurationError = '<c:out value="${batteryDurationError}"/>';
	var maintenanceIntervalError = '<c:out value="${maintenanceIntervalError}"/>';
	var productDateError = '<c:out value="${productDateError}"/>';
	var initializeDateError = '<c:out value="${initializeDateError}"/>';
	var updatedError = '<c:out value="${updatedError}"/>';
	var maintenanceDateError = '<c:out value="${maintenanceDateError}"/>';
	var contractDateError = '<c:out value="${contractDateError}"/>';
	if(document.checkform.ne.value==""){
		  var mytext = document.getElementById("ne");
		  mytext.focus();
		}
	else if(document.checkform.productCode.value==""){
	  var mytext = document.getElementById("productCode");
	  mytext.focus();
	}
	else if(document.checkform.seriNo.value == "")
	{
		var mytext = document.getElementById("seriNo");
	  	mytext.focus();
	}
	else if(productDateError != "")
	{
		var mytext = document.getElementById("productDate");
	  	mytext.focus();
	}
	else if(initializeDateError != "")
	{
		var mytext = document.getElementById("initializeDate");
	  	mytext.focus();
	}
	else if(updatedError != "")
	{
		var mytext = document.getElementById("updated");
	  	mytext.focus();
	}
	else if(powerError != "")
	{
		var mytext = document.getElementById("power");
	  	mytext.focus();
	}
	else if(defaultCurrentError != "")
	{
		var mytext = document.getElementById("defaultCurrent");
	  	mytext.focus();
	}
	else if(defaultVoltageError != "")
	{
		var mytext = document.getElementById("defaultVoltage");
	  	mytext.focus();
	}
	else if(realCurrentError != "")
	{
		var mytext = document.getElementById("realCurrent");
	  	mytext.focus();
	}
	else if(loadFollowError != "")
	{
		var mytext = document.getElementById("loadFollow");
	  	mytext.focus();
	}
	else if(batteryError != "")
	{
		var mytext = document.getElementById("battery");
	  	mytext.focus();
	}
	else if(batteryDurationError != "")
	{
		var mytext = document.getElementById("batteryDuration");
	  	mytext.focus();
	}
	else if(maintenanceIntervalError != "")
	{
		var mytext = document.getElementById("maintenanceInterval");
	  	mytext.focus();
	}
	else if(maintenanceDateError != "")
	{
		var mytext = document.getElementById("maintenanceDate");
	  	mytext.focus();
	}
	else if(contractDateError != "")
	{
		var mytext = document.getElementById("contractDate");
	  	mytext.focus();
	}
}
onload = focusIt;

</script>
<script type="text/javascript">
$('#province').change(function(){

	$.ajax({
	  url: "${pageContext.request.contextPath}/iso/iso-inventory/loadQuanHuyen.htm",
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
	  data:{province: $('#province').val()}}).done(function(j) {
		  var options = '';
		  for (var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i].district + '">' + j[i].district + '</option>';
			}
		$("#district").html(options);
		$('#district option:first').attr('selected', 'selected');
	    
	  });
	  
});
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