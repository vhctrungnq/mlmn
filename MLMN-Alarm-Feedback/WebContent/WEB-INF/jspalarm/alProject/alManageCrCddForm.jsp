<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<c:choose>
	  <c:when test="${crCdd == 'CR'}">    
		<c:choose>
		  <c:when test="${alManageCrCddAddEdit == 'N'}">
		      <title><fmt:message key="alManageCrFormAdd"/></title>
			  <content tag="heading"><fmt:message key="alManageCrFormAdd"/></content>
		  </c:when>
		  <c:when test="${alManageCrCddAddEdit == 'Y'}">
		      <title><fmt:message key="alManageCrFormEdit"/></title>
			  <content tag="heading"><fmt:message key="alManageCrFormEdit"/></content>
		  </c:when>
		  <c:otherwise></c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="${crCdd == 'CDD'}">
		<c:choose>
		  <c:when test="${alManageCrCddAddEdit == 'N'}">
		      <title><fmt:message key="alManageCddFormAdd"/></title>
			  <content tag="heading"><fmt:message key="alManageCddFormAdd"/></content>
		  </c:when>
		  <c:when test="${alManageCrCddAddEdit == 'Y'}">
		      <title><fmt:message key="alManageCddFormEdit"/></title>
			  <content tag="heading"><fmt:message key="alManageCddFormEdit"/></content>
		  </c:when>
		  <c:otherwise></c:otherwise>
		</c:choose>
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
<form:form commandName="alManageCrCdd" name="checkform" method="post" action="form.htm" enctype="multipart/form-data"> 
	<div>
    	<form:input path="id" type="hidden" />
    	<input id="crCdd" name="crCdd" value="${crCdd}" type="hidden" />
    </div>
    <table class="simple2">
      
      <tr>
           <td class="wid15 mwid110">
           <c:choose>
				<c:when test="${crCdd == 'CR'}">
					<fmt:message key="alManageCrCdd.crName"/>
				</c:when>
				<c:when test="${crCdd == 'CDD'}">
					<fmt:message key="alManageCrCdd.cddName"/>
				</c:when>
			</c:choose>
           &nbsp;<font color="red">(*)</font></td>
           <td class="wid35">
		       	<form:input path="crName" maxlength="300" cssClass="wid60"/>
		       	&nbsp;<form:errors path="crName" cssClass="error"/>
           </td>
           <td class="wid15 mwid110"><fmt:message key="alManageCrCdd.crDate"/></td>
           <td>
           		<input id="crDate" name="crDate" value="${crDate}" class="wid30" maxlength="20"/>
	 			<img alt="calendar" title="Click to choose the date" id="chooseCrDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	 			&nbsp;<form:errors path="crDate" cssClass="error"/>
           </td>       
      </tr>
      <c:choose>
		  <c:when test="${crCdd == 'CDD'}">
		  	<tr>
		  		<td><fmt:message key="alManageCrCdd.bscid"/></td>
		  		<td>
		  			<form:input path="bscid" maxlength="20" cssClass="wid60"/>
		  		</td>
		  		<td><fmt:message key="alManageCrCdd.site"/></td>
		  		<td>
		  			<form:input path="site" maxlength="20" cssClass="wid60"/>
		  		</td>
		  	</tr>
		  </c:when>
	 </c:choose> 
      <tr>
           <td><fmt:message key="alManageCrCdd.vendor"/></td>
           <td>
           		<form:input path="vendor" maxlength="30" cssClass="wid60"/>
           </td>
    	 	<td><fmt:message key="alManageCrCdd.supervisor"/></td>
         	<td><form:input path="supervisor" maxlength="40" cssClass="wid60"/></td>
      </tr>
      <tr>
      	<td><fmt:message key="alManageCrCdd.result"/></td>
      	<td><form:input path="result" maxlength="700" cssClass="wid60"/></td>
        <td><fmt:message key="alManageCrCdd.projectId"/></td>
        <td>
        	<form:select path="projectId" cssClass="wid60">
				<c:forEach var="items" items="${projectIdList}">
					<c:choose>
					<c:when test="${items.fieldName1 == projectIdCBB}">
					   <option value="${items.fieldName1}" selected="selected">${items.projectCode}</option>
					</c:when>
					<c:otherwise>
					   <option value="${items.fieldName1}">${items.projectCode}</option>
					</c:otherwise>
					</c:choose>
				</c:forEach>
			</form:select>
        </td>
      </tr>
      <tr>
      	<td><fmt:message key="alManageCrCdd.resultRate"/></td>
      	<td>
			<form:input path="resultRate" maxlength="3" cssClass="wid30"/>
			&nbsp;<form:errors path="resultRate" cssClass="error"/>
      	</td>
      	<td><fmt:message key="alManageCrCdd.description"/></td>
      	<td>
			<form:input path="description" maxlength="400" cssClass="wid60"/>
      	</td>
      </tr>
      <%-- <tr>
      	<td><fmt:message key="alManageCrCdd.fieldName1"/></td>
      	<td>
			<form:input path="fieldName1" maxlength="220" cssClass="wid90"/>
      	</td>
      	<td><fmt:message key="alManageCrCdd.fieldName2"/></td>
      	<td>
			<form:input path="fieldName2" maxlength="220" cssClass="wid90"/>
      	</td>
      </tr>
      <tr>
      	<td><fmt:message key="alManageCrCdd.fieldName3"/></td>
      	<td>
			<form:input path="fieldName3" maxlength="220" cssClass="wid90"/>
      	</td>
      	<td><fmt:message key="alManageCrCdd.fieldName4"/></td>
      	<td>
			<form:input path="fieldName4" maxlength="220" cssClass="wid90"/>
      	</td>
      </tr> --%>
      <c:choose>
		  <c:when test="${alManageCrCddAddEdit == 'N'}">
		  	<tr>
		      	<td class="vam"><fmt:message key="alManageCrCdd.file"/></td>
		      	<td colspan="3">
				    <input class="button" type="file" size="50%" name="file" id="file"/>
				</td>
		      </tr>
		  </c:when>
		  <c:when test="${alManageCrCddAddEdit == 'Y'}">
		  	<c:choose>
		      <c:when test="${roleUpdateFile == 'Y'}">
		      	<tr>
			      	<td class="vam"><fmt:message key="alManageCrCdd.file"/></td>
			      	<td colspan="3">
					    <input class="button" type="file" size="50%" name="file" id="file"/>&nbsp;
					    <c:if test="${not empty alManageCrCdd.fileName}">
						    <div id="idDeleteFile">
				        		<div id="uploadifive-file_upload-file-${alManageCrCdd.id}" class="uploadifive-queue-item complete">
					        		<a class="close" title="Xóa file" onClick="xoa_tep(${alManageCrCdd.id})">X</a>
					        		<div>
			       						<span class="filename"><a href="${pageContext.request.contextPath}/alarm/al-manage-cr-cdd/download.htm?id=${alManageCrCdd.id}">${alManageCrCdd.fileName}</a></span>
			       					</div>
		       					</div>	
		       				</div>
	       				</c:if>
					</td>
		      	</tr>
		      </c:when>
			  <c:otherwise><input style="display: none;" class="button" type="file" name="file" id="file"/></c:otherwise>
			 </c:choose>
		  </c:when>
		  <c:otherwise></c:otherwise>
	</c:choose>
      <tr>
        <td></td>
        <td colspan="3">
            <input class="button" type="submit" name="save" value="<fmt:message key="global.form.luulai"/>" />
            <input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm?crCdd=${crCdd}"'>
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
    inputField		:	"crDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseCrDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

function focusIt()
{
	var crDateError = '<c:out value="${crDateError}"/>';
	var resultRateError = '<c:out value="${resultRateError}"/>';
	
	if(document.checkform.crName.value==""){
		  var mytext = document.getElementById("crName");
		  mytext.focus();
		}
	else if(crDateError != "")
	{
		var mytext = document.getElementById("crDate");
	  	mytext.focus();
	}
	else if(resultRateError != "")
	{
		var mytext = document.getElementById("resultRate");
	  	mytext.focus();
	}
}

onload = focusIt;

</script>
<script type="text/javascript">
function xoa_tep(id){
	var r = confirm('Bạn có chắc chắn muốn xóa không?');
	if (r==true)
	{
		$.getJSON("${pageContext.request.contextPath}/alarm/al-manage-cr-cdd/deleteFile.htm",{id: $("#id").val()}, function(j){
		var tabX = '';
		for (var i = 0; i < j.length; i++) {
			
			tabX += '<div id="uploadifive-file_upload-file-' + j[i].id +'" class="uploadifive-queue-item complete">';
			tabX += '<a class="close" title="Xóa file" onClick="xoa_tep(' + j[i].id + ')">X</a>';
			tabX += '<div>';
			tabX +='<span class="filename"><a href="${pageContext.request.contextPath}/alarm/al-manage-cr-cdd/download.htm?id='+ j[i].id +'">'+ j[i].fileName +'</a></span>';
			tabX += '</div></div>';
		}
		$("#idDeleteFile").html(tabX);
		});
	}
};
</script>
<script type="text/javascript">
var theme = getTheme();
$("#crName").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#bscid").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#site").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#vendor").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#supervisor").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#result").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#resultRate").jqxInput({ width: '30%', height: 20, minLength: 1, theme: theme });
$("#description").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
</script>
