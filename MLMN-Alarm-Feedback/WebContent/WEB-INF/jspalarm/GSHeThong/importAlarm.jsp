<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
 .rcUpdate
{
color:red;
}
 </style>  
<title>${title}</title>
<content tag="heading">${title}</content>

 <form:form  method="post" action="importAlarm.htm?function=${function}" enctype="multipart/form-data">
 <input type="hidden" name="netWork" id="netWork" value="${netWork}">
  <input type="hidden" name="vendor" id="vendor" value="${vendor}">
 
 <br/>
	<table class="simple2">	
	    	<tr style="height:20px;" >
	    		<td width="150px"><b><fmt:message key="cautruc.filepath"/> <font color="red">(*)</font></b></td>
	    		<td><input type="file" size="110" name="filePath" id="filePath" class="button" />
	    			<input type="submit" id="upload" class="button" value="<fmt:message key="global.button.import"/>" />
	    		</td>
	    	</tr>
	    	<tr style="height:60px;">
	    		<td><b>
	    			<fmt:message key="global.FileExample"/></b>
	    		</td>
	    		<td>
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
	    	</table>
</form:form>
<div><b><fmt:message key="import.insetSuccess"/></b></div>
<table style="width: 100%;">
			
			<tr>
				<td style="width:300px;"><b>${fromDate}</b></td>
				<td style="width:250px;"><b>${toDate}</b></td>
				<td style="width:400px;"><b>${countSuccess}</b></td>
				<td align="right">
					<input class="button" type="button" id="btupdateAll" name="btupdateAll" value="<fmt:message key="global.button.update"/>" onClick='window.location="updateEdateAll.htm?vendor=${vendor}&function=${function}"' title = "Cập nhật các alarm trong file">
				</td>
			</tr>	
</table>

<div>
	<div><span style="color: red;">${messageInsert}</span></div>
	<div style="float: right;" id="listSuccess"></div>
</div>
<div id="gridSuccess"></div>	
<%-- <div id='menuSuccess'>
            <ul>
		   		<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
 </div> --%>
 <br/>
 <div>
 	<div><b>Danh sách cảnh báo treo</b></div>
 	<div align="right">
 		<fmt:message key="alarmLog.causebyFinished"/>&nbsp;&nbsp;
		<input type ="text"  id="causebyFinished"  name ="causebyFinished" style="width:250px" value="${causebyFinished}"/>		
	 	<fmt:message key="alarmLog.edateT"/>&nbsp;&nbsp;
		<input type ="text"  value="${etime}" name="etime" id="etime" size="17" maxlength="19" style="width:150px">
	 	<img alt="calendar" title="Click to choose the from date" id="chooseEDateT" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
		&nbsp;&nbsp;&nbsp;&nbsp;	
 	<c:choose>
			<c:when test="${vendor == 'NOKIA SIEMENS'}">
				<input class="button" type="button" id="btupdate2G" name="btupdate2G" value="<fmt:message key="global.button.updateEdate2G"/>"  title = "Đồng bộ cảnh báo active 2G trong file">
				<input class="button" type="button" id="btupdate3G" name="btupdate3G" value="<fmt:message key="global.button.updateEdate3G"/>"  title = "Đồng bộ cảnh báo active 3G trong file">
			</c:when>
		 	<c:when test="${vendor == 'ERICSSON'}">
				<input class="button" type="button" id="btupdate3G" name="btupdate3G" value="<fmt:message key="global.button.updateEdate3G"/>" title = "Đồng bộ cảnh báo active 3G trong file">
			</c:when>
			<c:when test="${vendor == 'HUAWEI'}">
				<input class="button" type="button" id="btupdate2G" name="btupdate2G" value="<fmt:message key="global.button.updateEdate2G"/>" title = "Đồng bộ cảnh báo active 2G trong file">
				<input class="button" type="button" id="btupdateCore" name="btupdateCore" value="<fmt:message key="global.button.updateEdateCore"/>"  title = "Đồng bộ cảnh báo active Core trong file">
			</c:when>
			<c:when test="${vendor == 'ALCATEL'}">
				<input class="button" type="button" id="btupdate2G" name="btupdate2G" value="<fmt:message key="global.button.updateEdate2G"/>"  title = "Đồng bộ cảnh báo active 2G trong file">
			</c:when>
		 	<c:otherwise>
		 		<input class="button" type="button" id="btupdate" name="btupdate" value="<fmt:message key="global.button.updateEdate"/>"  title = "Đồng bộ cảnh báo active trong file">
			</c:otherwise>
		</c:choose>
 		<div style="float: right;" id="listSuccesD"></div>
	</div>
</div>
<div><span style="color: red;">${messageUpdate}</span></div>
<div id="gridDExit"></div>	


<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
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
${gridSuccess}

${gridDExit}
var theme =  getTheme();
$('#btupdateAll').jqxButton({ theme: theme });
$('#upload').jqxButton({ theme: theme });
$('#btupdate2G').jqxButton({ theme: theme });
$('#btupdate3G').jqxButton({ theme: theme });
$('#btupdateCore').jqxButton({ theme: theme });
$('#btupdate').jqxButton({ theme: theme });

//handle context menu success
/* $("#menuSuccess").on('itemclick', function (event) {
	    var args = event.args;
	    var exportFileName =  "<c:out value='${exportFileNameS}'/>";
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$("#gridSuccess").jqxGrid('exportdata', 'xls', exportFileName);
	    }
});*/
 
$('#btupdate2G').on('click', function () { 
	window.location="updateEdate.htm?vendor=${vendor}&functionA=${function}&network=2G&etime="+$("#etime").val()+"&causebyFinished="+$("#causebyFinished").val();
	/* $.getJSON("${pageContext.request.contextPath}/updateEdate.htm",{vendor:"<c:out value='${vendor}'/>",functionA:"<c:out value='${function}'/>",network:"2G",etime:$("#edateT").val()}, function(j){
		 if (j==1)
			 {
			 	alert("Cập nhật thời gian kết thúc thành công");
			 }
		 else
			 {
			 	alert("Cập nhật không thành công.Thời gian kết thúc bé hơn thời gian bắt đầu");
			 }
	   }); */
	
}); 
$('#btupdate3G').on('click', function () { 
	window.location="updateEdate.htm?vendor=${vendor}&functionA=${function}&network=3G&etime="+$("#etime").val()+"&causebyFinished="+$("#causebyFinished").val();
	/* $.getJSON("${pageContext.request.contextPath}/updateEdate.htm",{vendor:"<c:out value='${vendor}'/>",functionA:"<c:out value='${function}'/>",network:"3G",etime:$("#edateT").val()}, function(j){
		 if (j==1)
			 {
			 	alert("Cập nhật thời gian kết thúc thành công");
			 }
		 else
			 {
			 	alert("Cập nhật không thành công.Thời gian kết thúc bé hơn thời gian bắt đầu");
			 }
	   }); */
	
}); 
$('#btupdateCore').on('click', function () { 
	window.location="updateEdate.htm?vendor=${vendor}&functionA=${function}&network=CORE&etime="+$("#etime").val()+"&causebyFinished="+$("#causebyFinished").val();
	/* $.getJSON("${pageContext.request.contextPath}/updateEdate.htm",{vendor:"<c:out value='${vendor}'/>",functionA:"<c:out value='${function}'/>",network:"CORE",etime:$("#edateT").val()}, function(j){
		 if (j==1)
			 {
			 	alert("Cập nhật thời gian kết thúc thành công");
			 }
		 else
			 {
			 	alert("Cập nhật không thành công.Thời gian kết thúc bé hơn thời gian bắt đầu");
			 }
	   });
	 */
}); 
$('#btupdate').on('click', function () { 
	window.location="updateEdate.htm?vendor=${vendor}&functionA=${function}&network=&etime="+$("#etime").val()+"&causebyFinished="+$("#causebyFinished").val();
	/* $.getJSON("${pageContext.request.contextPath}/updateEdate.htm",{vendor:"<c:out value='${vendor}'/>",functionA:"<c:out value='${function}'/>",network:"",etime:$("#edateT").val()}, function(j){
		 if (j==1)
			 {
			 	alert("Cập nhật thời gian kết thúc thành công");
			 }
		 else
			 {
			 	alert("Cập nhật không thành công.Thời gian kết thúc bé hơn thời gian bắt đầu");
			 }
	   }); */
	
}); 

</script>

