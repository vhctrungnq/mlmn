<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:choose>
  <c:when test="${ContentWorksAddEdit == 'Y'}">
      <title><spring:message code="header.title.contentFormAdd"/></title>
	  <content tag="heading"><spring:message code="header.title.contentFormAdd"/></content>
  </c:when>
  <c:when test="${ContentWorksAddEdit == 'N'}">
      <title><spring:message code="header.title.contentFormEdit"/></title>
	  <content tag="heading"><spring:message code="header.title.contentFormEdit"/></content>
  </c:when>
  <c:otherwise>
      
  </c:otherwise>
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

<form:form name="checkform" commandName="w_working_managements" method="post" action="formContent.htm?idWorkTypes=${w_working_managements.idWorkTypes}&deptCode=${deptCode}&wwmFkId=${wwmFkId}" enctype="multipart/form-data"> 
	<div>
		<form:hidden path="id"/>
		<form:hidden path="wwmFkId"/>
		<form:hidden path="dayShift"/>
		<form:hidden path="shift"/>
		
		<input value="${received}" id=received name="received" type="hidden"/>
		<input type="hidden" value="${note}" name="note" id="note" size="10" maxlength="10">
		<input type="hidden" value="${maCVCha}" name="maCVCha" id="maCVCha" size="10" maxlength="10">
		<input type="hidden" value="${statusUpdate}" name="statusUpdate" id="statusUpdate" size="10" maxlength="10">
		<input type="hidden" value="${status}" name="status" id="status" size="10" maxlength="10">
		<input type="hidden" value="${region}" name="region" id="region" size="10" maxlength="10">
		
		<%--<form:hidden path="idWorkTypes"/> --%>
	</div>
    <table class="simple2">
    	<tr>
    		<td><b><spring:message code="header.title.danhMucCongViec"/></b>&nbsp;<font color="red">(*)</font></td>
           	<td >
           		<select id="idWorkTypes" name="idWorkTypes" class="wid90">
	        			<c:forEach var="items" items="${getDanhMucCongViecList}">
			              	<c:choose>
			                <c:when test="${items.id == danhMucCVCBB}">
			                    <option value="${items.id}" selected="selected">${items.name}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${items.id}">${items.name}</option>
			                </c:otherwise>
			              	</c:choose>
			    		</c:forEach>
				</select>
				
           	</td>
           	<td style="width: 150px;"> Công việc tại trạm: &nbsp;&nbsp; 
           		<c:choose>
					<c:when test="${isSite=='Y'}">
						<input id="isSite" name="isSite" type="checkbox" checked="checked">
					</c:when>
					<c:otherwise>
						<input id="isSite" name="isSite" type="checkbox" >
					</c:otherwise>
				</c:choose>
           	</td>
           	<td>
           		<div id="siteDis" style="display: none;">
           		Tên trạm:&nbsp;&nbsp; 
           		<form:input path="site" maxlength="250" cssClass="wid40"/>&nbsp;<form:errors path="site" cssClass="error"/>
	       		</div>
	        </td>
           	
    	</tr>
   		<tr>
          <td><b><spring:message code="title.qLDanhSachCongViec.tenCongViec"/></b>&nbsp;<font color="red">(*)</font></td>
          <c:choose>
			<c:when test="${ContentWorksAddEdit == 'Y'}">
	          <td colspan="3" >
	          	<form:input path="tenCongViec" maxlength="250" cssClass="wid60"/>&nbsp;<form:errors path="tenCongViec" cssClass="error"/>
	          	<c:if test="${maCVCha!=null&&maCVCha!=''}">
		          	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<b>Mã công việc cha : ${maCVCha}</b>
				</c:if>
	          </td>
         	</c:when>
	  	 <c:when test="${ContentWorksAddEdit == 'N'}">
	           <td colspan="3">
	           	<form:input path="tenCongViec" maxlength="250" cssClass="wid40"/>&nbsp;<form:errors path="tenCongViec" cssClass="error"/>
	           	&nbsp;<b>(<spring:message code="title.qLDanhSachCongViec.maCongViec"/>: <i>${w_working_managements.maCongViec}</i> 
	           	<c:if test="${maCVCha!=null}">
		          	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<b>Mã công việc cha : ${maCVCha}</b>
				</c:if>
				)</b>
	           	<form:hidden path="maCongViec"/>
	           
	           </td>
          
		 </c:when>
		 <c:otherwise>
	      
		 </c:otherwise>
		</c:choose>
      </tr>
      <tr>
           <td><b><spring:message code="title.qLDanhSachCongViec.tomTatNoiDung"/></b>&nbsp;<font color="red">(*)</font></td>
           <td colspan="3"> 
            <script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/ckeditor.js"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/sample.js"></script>
			
	           		<form:textarea cols="80" rows="3"  path="tomTatNoiDung" maxlength="900" ></form:textarea>
					 <script type="text/javascript">
						CKEDITOR.replace( 'tomTatNoiDung',
						{
							toolbar :
								[
									['NewPage','-','Undo','Redo'],
									['Find','Replace','-','SelectAll','RemoveFormat'],
									['Link', 'Unlink', 'Image'],
									['FontSize', 'Bold', 'Italic','Underline'],
									['NumberedList','BulletedList','-','Blockquote'],
									['TextColor', '-', 'Smiley','SpecialChar', '-', 'Maximize']
								],
							height: '100px'
						});
				  	</script>
	       </td>
           <%-- <td colspan="3">
           
           		<form:input path="tomTatNoiDung" maxlength="500" cssClass="wid40"/>&nbsp;<form:errors path="tomTatNoiDung" cssClass="error"/>
           </td> --%>
      </tr>
      <tr>
           <td><b><spring:message code="title.qLDanhSachCongViec.noiDungCongViec"/></b></td>
           <%-- <td colspan="3"><form:textarea path="noiDung" cssClass="textareaCV96" maxlength="3900"/></td> --%>
           <td colspan="3"> 
            <script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/ckeditor.js"></script>
		 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/sample.js"></script>
			
	           		<form:textarea cols="80" rows="8"  path="noiDung" maxlength="900" ></form:textarea>
					 <script type="text/javascript">
						CKEDITOR.replace( 'noiDung',
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
      	<td class="wid15 mwid120"><b><spring:message code="title.qLDanhSachCongViec.ngayGiaoViec"/></b></td>
        <td class="wid20 mwid150">
        	<input type="text" id="assignDate" name="assignDate" value="${assignDate}" class="wid70" maxlength="30"/>
        	<img alt="calendar" title="Click to choose the assign date" id="chooseAssignDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;<form:errors path="assignDate" cssClass="error"/>
        </td>
        <td class="wid15 mwid160"><b><spring:message code="title.qLDanhSachCongViec.ngayGiaoHoanThanh"/></b></td>
	    <c:choose>
      	<c:when test="${statusUpdate=='N'}">
      			<td><input id="estimateDate" value="${estimateDate}" style="width:100%" DISABLED /></td>
      	</c:when>
      	<c:otherwise>
      		<td>
	        	<input type="text" id="estimateDate" name="estimateDate" value="${estimateDate}" class="wid30" maxlength="30"/>
	        	<img alt="calendar" title="Click to choose the estimate date" id="chooseEstimateDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;<form:errors path="estimateDate" cssClass="error"/>
	        </td>
      	</c:otherwise>
      	</c:choose>
        
      </tr> 
      <tr>
           <td><b><spring:message code="title.qLDanhSachCongViec.nguoiGiaoViec"/></b>&nbsp;<font color="red">(*)</font></td>
           <td>
	          <!-- <div id='cbnguoiGiaoViec'></div> -->
	          <b>${nguoiGiaoViecCBB}</b>
           	<input type="hidden" name="nguoiGiaoViec" id="nguoiGiaoViec" value="${nguoiGiaoViecCBB}">
	          &nbsp;<form:errors path="nguoiGiaoViec" cssClass="error"/>
           </td>
           
           <td><b><spring:message code="title.qLDanhSachCongViec.nguoiChuTri"/></b>&nbsp;<font color="red">(*)</font></td>
           <td>
	        <div id='cbnguoiChuTri'></div>
           	<input type="hidden" name="nguoiChuTri" id="nguoiChuTri" value="${nguoiChuTriCBB}">
	          &nbsp;<form:errors path="nguoiChuTri" cssClass="error"/>
           </td>
      </tr>
      <tr>
      	<td><b><spring:message code="title.qLDanhSachCongViec.nguoiNhanViec"/></b>&nbsp;<font color="red">(*)</font></td>
           <td  colspan="3">
           <div id='cbnguoiNhanViec'></div>
           <input type="hidden" name="nguoiNhanViec" id="nguoiNhanViec" value="${nguoiNhanViecCBB}">
             &nbsp;<form:errors path="nguoiNhanViec" cssClass="error"/>
           </td>
      </tr>
      <tr>
           
           <td><b><spring:message code="title.qLDanhSachCongViec.ccEmail"/></b></td>
           <td colspan="3"><form:input type="text" path="ccEmail"  id ="ccEmail"/>
           <!-- <div id='ccEmail'></div> -->
           </td>
           
           
      </tr>
      <tr >
      	<td><b><fmt:message key="isoEquipment.fileAttach"/></b></td>
           <td colspan="3"><input id="file_upload" name="file_upload" type="file" multiple="true"></td>
			<!-- <td><input class="button" type="file" size="50%" name="file" id="file"/></td> -->
      </tr>
	  <tr>
	      	<td style="vertical-align:middle;"><b><spring:message code="title.qLDanhSachCongViec.tepCongVan"/></b></td>
	      	<td colspan="3">
	      			<input type="hidden" id="fileId" name="fileId" value="${file_attachment}"/>
					<div style="margin-bottom: 7px">
						<div class="queue-item">
							<div id="queue-upload"></div>
							<div id="queue"></div>
						</div>
						
						
					</div>
	      	</td>
	      </tr>
       <tr>
           <td><input type="hidden" id="action" name="action">
           <input type="hidden" id="deptCode" name="deptCode" value="${deptCode}"></td>
           <td colspan="3">
           		<c:choose>
			      	<c:when test="${statusUpdate=='N'&& status == 'QUA_HAN'}">
			      			<%-- <input class="button" type="submit" name="save" value="<spring:message code="button.save"/>" disabled="disabled"/>
			      			<input class="button" type="submit" name="saveAndSend" onclick="setAction('sendMail')" value="<spring:message code="button.saveAndSend"/>" disabled="disabled"/> --%>
			      	</c:when>
			      	<c:otherwise>
			      		<input class="button" type="submit" name="save" value="<spring:message code="button.save"/>" />
               			<input class="button" type="submit" name="saveAndSend" onclick="setAction('sendMail')" value="<spring:message code="button.saveAndSend"/>" />
               		</c:otherwise>
      			</c:choose>
               <c:choose>
               	<c:when test="${note == 'form'}">
                    <input class="button" type="button" value="<spring:message code="button.cancel"/>" onclick='window.location="formContentDetails.htm?id=${wwmFkId}&type="'>
                </c:when>
                <c:when test="${note == 'cvcl'}">
                    <input class="button" type="button" value="<spring:message code="button.cancel"/>" onclick='window.location="${pageContext.request.contextPath}/working/list.htm?type=&region=${region}"'>
                </c:when>
                <c:when test="${note == 'shif'}">
                    <input class="button" type="button" value="<spring:message code="button.cancel"/>" onclick='window.location="${pageContext.request.contextPath}/caTruc/list.htm?region=${region}"'>
                </c:when>
                <c:otherwise>
                    <input class="button" type="button" value="<spring:message code="button.cancel"/>" onclick='window.location="list.htm?received=${received}"'>
                </c:otherwise>
               </c:choose>
              </td>
       </tr>
    </table>

</form:form>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
Calendar.setup({
    inputField		:	"assignDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M:%S",   	// format of the input field
    button			:   "chooseAssignDate",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"estimateDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M:%S",   	// format of the input field
    button			:   "chooseEstimateDate",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
});
function setAction(value) {
	var action = document.getElementById("action");
	action.value = value;

	return true;
}
function focusIt()
{
	var assignDateError = '<c:out value="${assignDateError}"/>';
	var estimateDateError = '<c:out value="${estimateDateError}"/>';
	
	if(document.checkform.tenCongViec.value==""){
	  var mytext = document.getElementById("tenCongViec");
	  mytext.focus();
	}
	else if(document.checkform.tomTatNoiDung.value==""){
		var mytext = document.getElementById("tomTatNoiDung");
		mytext.focus();
	}
	
	else if(assignDateError != "")
	{
		var mytext = document.getElementById("assignDate");
		  mytext.focus();
		}
	else if(estimateDateError != "")
	{
		var mytext = document.getElementById("estimateDate");
		  mytext.focus();
		}
	else
		{
		var mytext = document.getElementById("noiDung");
		mytext.focus();
		}
}

onload = focusIt;

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
$(document).ready(function(){
	
	
	
var theme =  getTheme(); 
//prepare the data
var depCode='<c:out value="${deptCode}"/>';

var urlUserGiao = "${pageContext.request.contextPath}/ajax/getUserForWork.htm?deptCode=&diliver=";
var sourceUserGiao =
{
    datatype: "json",
    url: urlUserGiao,
    datafields: [
                 { name: 'username'},
                 { name: 'fullname'},
                 { name: 'maPhong'},
                 { name: 'position'}
             ],
    async: false
};
var dataAdapterUserGiao = new $.jqx.dataAdapter(sourceUserGiao);
//Nguoi giao viec

$('#cbnguoiGiaoViec').jqxComboBox({source: dataAdapterUserGiao, displayMember: "username", valueMember: "username", width: "97%", height: 25, theme: theme,autoOpen: true,autoComplete: true,
	 renderer: function (index, label, value) {
        var item = dataAdapterUserGiao.records[index];
        if (item != null) {
       	 var label = item.username + "(" + item.fullname+ " - " + item.maPhong  +" - " + item.position  + ")";
            return label;
        }
        return "";
    }
               
 });
var nguoiGiaoViec = '<c:out value="${nguoiGiaoViecCBB}"/>';
if(nguoiGiaoViec=="")
	$('#cbnguoiGiaoViec').val('');
else
{
	$("#cbnguoiGiaoViec").val(nguoiGiaoViec );
}
//trigger selection changes.
$("#cbnguoiGiaoViec").on('change', function (event) {
	 var args = event.args;
	    if (args) {
   	 var item = args.item;
	   	 var value = item.value;
  		 $("#nguoiGiaoViec").val(value);
	    }
});

//nhan viec, chu tri
var urlUser = "${pageContext.request.contextPath}/ajax/getUserForWork.htm?deptCode=&diliver="+nguoiGiaoViec;
var sourceUser =
{
    datatype: "json",
    url: urlUser,
    datafields: [
                 { name: 'username'},
                 { name: 'fullname'},
                 { name: 'maPhong'},
                 { name: 'position'}
             ],
    async: false
};
var dataAdapterUser = new $.jqx.dataAdapter(sourceUser);
$("#cbnguoiNhanViec").jqxComboBox({source: dataAdapterUser, displayMember: "username", valueMember: "username",multiSelect: true, width: "98%", height: 25, theme: theme,autoOpen: true,autoComplete: true
	,renderer: function (index, label, value) {
        var item = dataAdapterUser.records[index];
        if (item != null) {
        	var label = item.username + "(" + item.fullname+ " - " + item.maPhong  +" - " + item.position  + ")";
            return label;
        }
        return "";
    }
               
});           
var nguoiNhanViec = '<c:out value="${nguoiNhanViecCBB}"/>';
if(nguoiNhanViec=="")
	$('#cbnguoiNhanViec').val('');
else
{
	//$("#cbnguoiNhanViec").text(nguoiNhanViec );
	var nguoiNhanViecVar = nguoiNhanViec.split(",");
	if (nguoiNhanViecVar != null) {
		for (var i=0; i<nguoiNhanViecVar.length; i++) {
			$("#cbnguoiNhanViec").jqxComboBox('selectItem', nguoiNhanViecVar[i] );  
		}
	}
}
//trigger selection changes.
$("#cbnguoiNhanViec").on('change', function (event) {
	var items = $("#cbnguoiNhanViec").jqxComboBox('getSelectedItems');
	var selectedItems = "";
    $.each(items, function (index) {
    	selectedItems += this.label;
        if (items.length - 1 != index) {
            selectedItems += ", ";
        }
    });
    $("#nguoiNhanViec").val(selectedItems);
});
//nguoi chu tri

 $('#cbnguoiChuTri').jqxComboBox({source: dataAdapterUser, displayMember: "username", valueMember: "username", width: "97%", height: 25, theme: theme,autoOpen: true,autoComplete: true,dropDownHeight: 120,
	 renderer: function (index, label, value) {
         var item = dataAdapterUser.records[index];
         if (item != null) {
        	 var label = item.username + "(" + item.fullname+ " - " + item.maPhong  +" - " + item.position  + ")";
             return label;
         }
         return "";
     }
                
  });
           
var nguoiChuTri = '<c:out value="${nguoiChuTriCBB}"/>';
if(nguoiChuTri=="")
	$('#cbnguoiChuTri').val('');
else
{
	$("#cbnguoiChuTri").val(nguoiChuTri );
}
//trigger selection changes.
$("#cbnguoiChuTri").on('change', function (event) {
	 var args = event.args;
	    if (args) {
    	 var item = args.item;
	   	 var value = item.value;
   		 $("#nguoiChuTri").val(value);
	    }
});

var ccEmail = '<c:out value="${ccEmail}"/>';

 var renderer = function (itemValue, inputValue) {
     var terms = inputValue.split(/,\s*/);
     var value = inputValue;
  
      if (inputValue.indexOf(itemValue) < 0)
	    	{
   
     	 // remove the current input
          terms.pop();
          // add the selected item
	    	 terms.push(itemValue);
	         // add placeholder to get the comma-and-space at the end
	        // terms.push("");
	         value = terms.join(",");
	    	}
    
     return value;
 };
${ccEmailList}
$("#ccEmail").jqxInput({ height: 20, width: '100%', theme: theme,
    source: function (query, response) {
        var item = query.split(/,\s*/).pop();
        // update the search query.
        $("#ccEmail").jqxInput({ query: item });
        response(ccEmailList);
    },
    renderer: renderer
});
// alert(bscid);
	if(ccEmail!="")
		$('#ccEmail').val(ccEmail);
	// cong viec tai tram
	var site =  "<c:out value='${w_working_managements.site}'/>";
	var isSite ='<c:out value="${isSite}"/>';
	if (isSite=='Y')
	 {
		 $("#siteDis").css( "display", "block" );
		 ${cellList}
	 	$("#site").jqxInput({ height: 20, width: 200, theme: theme,
	        source: cellList
	    });
		//alert(cellid);
        if(site!="")
			$('#site').val(site);
	 }
	 else
	 {
	 	$("#siteDis").css( "display", "none" );
	 }
	
	$("#isSite").change( function() {
		if ($(this).is(':checked')) {
			 $("#siteDis").css( "display", "block" );
			 ${cellList}
		 	$("#site").jqxInput({ height: 20, width: 200, theme: theme,
		        source: cellList
		    });
		 	//alert(cellid);
	        if(site!="")
				$('#site').val(site);
		} else {
			$("#siteDis").css( "display", "none" );
			$('#site').val("");
		}
	});
	
	if ($('#isSite').is(':checked')) {
		$("#siteDis").css( "display", "block" );
		
	} else {
		$("#siteDis").css( "display", "none" );
		$('#site').val("");
	}
		
	
	
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
</script>