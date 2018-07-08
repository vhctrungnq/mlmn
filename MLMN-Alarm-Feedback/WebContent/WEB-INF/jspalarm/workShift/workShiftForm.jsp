
<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title>${titleF}</title>
<content tag="heading">${titleF}</content>

<form:form commandName="workShift" method="post" action="form.htm?type=${type}&region=${region}"> 
	<form:hidden path="id"/>
	<form:hidden path="workType"/>
	<input type="hidden" name="note" id="note" value="${note}">
	<table class="simple2"> 
      
	   	<tr>
           <td style="width:150px;"><fmt:message key="workShift.workName"/><font color="red">(*)</font></td>
           <td colspan="5"> 
              <script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/ckeditor.js"></script>
			  <script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/sample.js"></script>
			
           	   		<form:textarea cols="80" rows="3"  path="workName" maxlength="1900" ></form:textarea>
           	   		
           	   		<font color="red">${errorWorkName}<form:errors path="workName"/></font>
					 <script type="text/javascript">
						CKEDITOR.replace( 'workName',
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
       		<c:if test="${region==''}">
	       		<td>
	       			<fmt:message key="qLPhongBan.region"/>&nbsp;<font color="red">(*)</font>
		        </td>
		        <td>
					<div id='regionCB'></div><form:errors path="region" cssClass="error"/>
					   <font color="red"><span id='errorRegion'>${errorRegion}</span></font>
		      	</td>
		    </c:if>
		   
       		<td style="padding-left: 5px;width:150px"><fmt:message key="workShift.userDelivered"/><font color="red">(*)</font></td>
			<td colspan="3">
					<form:select path="userDelivered" style="width: 350px;height:20px;" >
						
		 				<c:forEach var="items" items="${fullNameList}">
		              	<c:choose>
		                <c:when test="${items.username == workShift.userDelivered}">
		                    <option value="${items.username}" selected="selected">${items.fullname} (${items.username})</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.username}">${items.fullname} (${items.username})</option>
		                </c:otherwise>
		              	</c:choose>
				    	</c:forEach>
			          </form:select>
			           <font color="red"><span id='erroruserDelivered'>${erroruserDelivered}</span></font>
			  
				</td>   
       </tr>
     <tr>
     	<c:if test="${type==''}">
				<td style="width:100px;">
					Ngày
				</td>
				<td style="width:150px;">
					<input type="text" value="${day}" name="day" id="day" size="10" maxlength="10">
					<img alt="calendar" title="Click to choose the start date" id="choosengayTKTo" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
								
				</td>
			</c:if>
			<c:if test="${type!=''}">
				<input type="hidden" value="${day}" name="day" id="day" size="10" maxlength="10">
			</c:if>
          <td style="width:150px;">
					<fmt:message key="workShift.shift"/><font color="red">(*)</font>
				</td>
				<td style="width:150px;padding-left: 10px;">
					<select name="shift" id="shift" style="width: 80px;height:20px; padding-top: 4px;">
						<c:forEach var="item" items="${caList}">
							<c:choose>
				                <c:when test="${item.value == shift}">
				                    <option value="${item.value}" selected="selected">${item.value}</option>
				                </c:when>
								<c:otherwise>
									<option value="${item.value}">${item.value}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select> 
				</td>
			 <td style="width:150px;"><fmt:message key="workShift.deadline"/><font color="red">(*)</font></td>
        	 <td> 
     		    <div id='cbdeadline'></div><form:hidden path="deadline"  id ="deadline"/>
     		    <font color="red"><span id='errordeadline'>${errordeadline}</span></font>

			 </td>
      </tr> 
       <c:if test="${type!='cvcd'}">
       <tr >
      	<td><b><fmt:message key="isoEquipment.fileAttach"/></b></td>
           <td colspan="5"><input id="file_upload" name="file_upload" type="file" multiple="true"></td>
			<!-- <td><input class="button" type="file" size="50%" name="file" id="file"/></td> -->
      </tr>
	  <tr>
	      	<td style="vertical-align:middle;"><b><spring:message code="title.qLDanhSachCongViec.tepCongVan"/></b></td>
	      	<td colspan="5">
	      			<input type="hidden" id="fileId" name="fileId" value="${file_attachment}"/>
					<div style="margin-bottom: 7px">
						<div class="queue-item">
							<div id="queue-upload"></div>
							<div id="queue"></div>
						</div>
						
						
					</div>
	      	</td>
	      </tr>
	     </c:if>
    
       <tr>
           <td></td>
           <td colspan="5">
           		<input type="submit" class="button" id = "submit" value="<fmt:message key="global.form.luulai"/>" />
           	  	<input type="button" class="button" id = "btCancel" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm?type=${type}&region=${region}"'>	
           </td>
       </tr>
    </table>
</form:form>
<style>
    .error {
    	color: red;
    }
</style> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
 
<script type="text/javascript">
     Calendar.setup({
        inputField		:	"day",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "choosengayTKTo",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    }); 
     
</script> 
<script type="text/javascript">


var theme =  getTheme(); 
$('#submit').jqxButton({ theme: theme });
$('#btCancel').jqxButton({ theme: theme });
$(document).ready(function () {         
	var hourList = ["00:00:00", "00:30:00", "01:00:00", "01:30:00", "02:00:00", "02:30:00", "03:00:00", "03:30:00", "04:00:00", "04:30:00", "05:00:00", "05:30:00", "06:00:00", "06:30:00"
							, "07:00:00", "07:30:00", "08:00:00", "08:30:00", "09:00:00", "09:30:00", "10:00:00", "10:30:00", "11:00:00", "11:30:00", "12:00:00", "12:30:00", "13:00:00", "13:30:00"
							, "14:00:00", "14:30:00", "15:00:00", "15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00", "18:00:00", "18:30:00", "19:00:00", "19:30:00", "20:00:00", "20:30:00"
							, "21:00:00", "21:30:00", "22:00:00", "22:30:00", "23:00:00", "23:30:00", "24:00:00", "24:30:00"];
			
	$("#cbdeadline").jqxComboBox({ source: hourList , width: '120', height: '20px',itemHeight: 30,theme: theme,autoDropDownHeight: false,dropDownHeight: 120 });
 	var deadline =  "<c:out value='${deadline}'/>";
 	//alert(cellid);
    if(deadline!="")
   	{
		$('#cbdeadline').val(deadline);
		$('#deadline').val(deadline);
   	}
    $("#cbdeadline").change(function(){
 	   var deadlinecb = $("#cbdeadline").val();
 	  	$('#deadline').val(deadlinecb);
    });	
    $("#shift").change(function(){
  	   var shift = $("#shift").val();
  	   if (shift.indexOf("S") == 0)
	   {
  		 $('#cbdeadline').val("12:30:00");
  		 $('#deadline').val("12:30:00");
	   }
  	 	if (shift.indexOf("C") == 0)
	   {
		 $('#cbdeadline').val("18:30:00");
		 $('#deadline').val("18:30:00");
	   }
  		if (shift.indexOf("T") == 0)
	   {
		 $('#cbdeadline').val("06:30:00");
		 $('#deadline').val("06:30:00");
	   }
  	  	
     });	
 	
});
/* 
$(document).ready(function(){
	
  //network
    var urlstatus = "${pageContext.request.contextPath}/ajax/getStatusWork.htm";
    // prepare the data
    var sourcestatus =
    {
        datatype: "json",
        datafields: [
            { name: 'value' },
            { name: 'name' }
        ],
        url: urlstatus,
        async: false
    };
    var dataAdapterstatus = new $.jqx.dataAdapter(sourcestatus);
    $("#employeeAssessment").jqxComboBox({ source: dataAdapterstatus, displayMember: "name", valueMember: "name",width: 160,height: 20,itemHeight: 30,theme: theme,autoDropDownHeight: true,autoOpen: true  });
     var employeeAssessment =  "<c:out value='${employeeAssessment}'/>";
	       if(employeeAssessment!="") {
			$('#employeeAssessment').val(employeeAssessment);
	       }
	      
	
	$("#superiorAssessment").jqxComboBox({ source: dataAdapterstatus, displayMember: "name", valueMember: "name",width: 160,height: 20,itemHeight: 30,theme: theme,autoDropDownHeight: true,autoOpen: true  });
	var superiorAssessment =  "<c:out value='${superiorAssessment}'/>";
	      if(superiorAssessment!="") {
			$('#superiorAssessment').val(superiorAssessment);
	      }
	     
}); */

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
		'uploadScript' 		: '${pageContext.request.contextPath}/w_working_managements/upload/docpa.htm',
		'fileObjName' 		: 'filedata',
		'onInit'		: function() {
			var fileId = $('#fileId').val();
		//	alert(fileId);
			if (fileId != '') {
				var fileList = fileId.split(",");
				
					for (var i=0; i<fileList.length; i++) {
						var fileA =fileList[i];
						
						$.getJSON("${pageContext.request.contextPath}/w_working_managements/getFile.htm",{fileId: fileA}, function(j){
							if (j==null) {
								return;
							}
							$('#queue-upload').append(
								'<div class="uploadifive-queue-item" id="uploadifive-file_upload-file--' + j.id + '">' +
								'	<a href="javascript:removeAttachFile(' + j.id + ')" class="close">X</a>' +
								'	<div>'+
								'		<span class="filename"><a href="${pageContext.request.contextPath}/w_working_managements/download.htm?id='+ j.id +'">'+ j.fileName +'</a></span>'+
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
		},
		'onSelect'         	: function() {
			$('#file_upload').uploadifive('upload');
		},
		'onUploadComplete' : function(fileA, data) {
			
			addFileId(data);
		}
	});
});

function removeAttachFile(v_sysFileId) {
	
	//$.getJSON("${pageContext.request.contextPath}/file/delete.htm",{id: v_sysFileId}, function(j){});
	$.getJSON("${pageContext.request.contextPath}/w_working_managements/deleteFile.htm",{id: v_sysFileId, idWorkMana: ""}, function(j){});
		
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
	return '<a href="${pageContext.request.contextPath}/w_working_managements/download.htm?id=' + v_sysFileId + '">' + v_fileName + '</a>';
}

function addFileId(data) {
	var fileId = $("#fileId").val();
	$("#fileId").val(fileId + ',' + data );
}

function xoa_tep(id){
	var r = confirm('Bạn có chắc chắn muốn xóa không?');
	if (r==true)
	{
		$.getJSON("${pageContext.request.contextPath}/w_working_managements/deleteFile.htm",{id: id, idWorkMana: $("#id").val()}, function(j){
		
		var tabX = '';
		for (var i = 0; i < j.length; i++) {
			
			tabX += '<div id="uploadifive-file_upload-file-' + j[i].id +'" class="uploadifive-queue-item complete">';
			tabX += '<a class="close" title="Xóa tệp" onClick="xoa_tep(' + j[i].id + ')">X</a>';
			tabX += '<div>';
			tabX += '<span class="filename">' + j[i].fileName + '</span>';
			tabX += '</div></div>';
		}
		$("#idDeleteFile").html(tabX);
		
		});
	}
};
//create dropbox region

var urlregion = "${pageContext.request.contextPath}/ajax/getRegionList.htm";
//prepare the data
var sourceregion =
{
  datatype: "json",
  datafields: [
      { name: 'name' },
      { name: 'name' }
  ],
  url: urlregion,
  async: false
};
var dataAdapterregion = new $.jqx.dataAdapter(sourceregion);
// Create a jqxComboBox
$("#regionCB").jqxComboBox({ source: dataAdapterregion, displayMember: "name", valueMember: "name", width: '80%',height: 15,itemHeight: 15,theme: theme, autoOpen: true });
var regionCBB =  "<c:out value='${regionCB}'/>";
if(regionCBB!=""){
	 $("#regionCB").val(regionCBB);
}
</script>
</body>
</html>