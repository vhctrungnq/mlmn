<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:choose>
  <c:when test="${SgsnAddEdit == 'N'}">
      <title><fmt:message key="title.hsgsn.formAdd"/></title>
	  <content tag="heading"><fmt:message key="title.hsgsn.formAdd"/></content>
  </c:when>
  <c:when test="${SgsnAddEdit == 'Y'}">
      <title><fmt:message key="title.hsgsn.formEdit"/></title>
	  <content tag="heading"><fmt:message key="title.hsgsn.formEdit"/></content>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>
<div class="body-content"></div>

<form:form name="checkform" commandName="sgsn" method="post" action="form.htm">
	<form:input path="id" type="hidden" />
    <table class="simple2">
        <tr>
            <td class="wid15 mwid110"><fmt:message key="hsgsn.sgsnid"/>&nbsp;<font color="red">(*)</font></td>
            <td class="wid35">            	
              <%-- <c:choose>
                <c:when test="${empty sgsn.sgsnid}">
                    <form:input path="sgsnid" maxlength="50" cssClass="wid50"/>
                </c:when>
                <c:otherwise>
                    <b><i>${sgsn.sgsnid}</i></b><form:hidden path="sgsnid" />
                </c:otherwise>
              </c:choose> --%>
              <form:input path="sgsnid" maxlength="50" cssClass="wid50"/>
             &nbsp;<form:errors path="sgsnid" cssClass="error"/> 
            </td>
            <td class="wid15 mwid110"><fmt:message key="hsgsn.sgsnName"/>&nbsp;<font color="red">(*)</font></td>
            <td>
            	<form:input path="sgsnName" maxlength="50" cssClass="wid50"/>&nbsp;<form:errors path="sgsnName" cssClass="error"/> 
			</td>
        </tr>
        <tr>
        	<td><fmt:message key="hsgsn.region"/></td>
        	<td>
		     	<form:select path="region" cssClass="wid30">
					<form:option value="TT2" label="TT2"/>
					<form:option value="TT6" label="TT6"/>
       			</form:select>
        	</td>
        	<td><fmt:message key="hsgsn.uiAttachCapacityLicense"/></td>
        	<td>
        		<form:input path="uiAttachCapacityLicense" maxlength="10" cssClass="wid30"/>
        		&nbsp;<form:errors path="uiAttachCapacityLicense" cssClass="error"/> 
        	</td>
        </tr>
        <tr>
            <td><fmt:message key="hsgsn.gbAttachCapacityLicense"/></td>
            <td>
            	<form:input path="gbAttachCapacityLicense" maxlength="4" cssClass="wid30"/>
            	&nbsp;<form:errors path="gbAttachCapacityLicense" cssClass="error"/>
            </td>
            <td><fmt:message key="hsgsn.totalAttachCapacityLicense"/></td>
            <td>
            	<form:input path="totalAttachCapacityLicense" maxlength="4" cssClass="wid30"/>
            	&nbsp;<form:errors path="totalAttachCapacityLicense" cssClass="error"/>
            </td>
        </tr>
        <tr>
        	<td><fmt:message key="hsgsn.thoughputCapacityLicense"/></td>
        	<td>
        		<form:input path="thoughputCapacityLicense" maxlength="4" cssClass="wid30"/>
        		&nbsp;<form:errors path="thoughputCapacityLicense" cssClass="error"/>
        	</td>
        	<td><fmt:message key="hsgsn.pdpContextCapacityLicense"/></td>
        	<td>
        		<form:input path="pdpContextCapacityLicense" maxlength="4" cssClass="wid30"/>
        		&nbsp;<form:errors path="pdpContextCapacityLicense" cssClass="error"/>
        	</td>
        </tr>
        <tr>
        	<td><fmt:message key="hMsc.dept"/></td>
        	<td>
        		<form:select path="dept" cssClass="wid50">  
	           		<form:option  value=""></form:option >
			        <c:forEach var="temp" items="${deptList}">
			              <c:choose>
			                <c:when test="${temp.deptCode == dept}">
			                    <form:option  value="${temp.deptCode}" selected="selected">${temp.deptCode}</form:option >
			                </c:when>
			                <c:otherwise>
			                    <form:option  value="${temp.deptCode}">${temp.deptCode}</form:option >
			                </c:otherwise>
			              </c:choose>
				    </c:forEach>
			 	</form:select>
        	</td>
        	<td><fmt:message key="hMsc.team"/></td>
        	<td>
        		<form:select path="team" cssClass="wid50">  
	           		<form:option value=""></form:option >
			        <c:forEach var="temp" items="${teamList}">
			              <c:choose>
			                <c:when test="${temp.deptCode == team}">
			                    <form:option  value="${temp.deptCode}" selected="selected">${temp.deptCode}</form:option >
			                </c:when>
			                <c:otherwise>
			                    <form:option  value="${temp.deptCode}">${temp.deptCode}</form:option >
			                </c:otherwise>
			              </c:choose>
				    </c:forEach>
			 	</form:select>
        	</td>
        </tr>
        <tr>
        	<td><fmt:message key="hMsc.subTeam"/></td>
        	<td>
        		<form:select path="subTeam" cssClass="wid50">  
	           		<form:option  value=""></form:option >
				        <c:forEach var="temp" items="${subteamList}">
				              <c:choose>
				                <c:when test="${temp.deptCode == subTeam}">
				                    <form:option  value="${temp.deptCode}" selected="selected">${temp.deptCode}</form:option >
				                </c:when>
				                <c:otherwise>
				                    <form:option  value="${temp.deptCode}">${temp.deptCode}</form:option >
				                </c:otherwise>
				              </c:choose>
				    </c:forEach>
			 	</form:select>
        	</td>
        	<td><fmt:message key="hMsc.neGroup"/></td>
            <td><form:input path="neGroup" maxlength="40" cssClass="wid50"/></td>
        </tr>
        <tr>
            <td></td>
            <td colspan="3">
                <input type="submit" class="button" name="save" value="<fmt:message key="global.form.luulai"/>"/>
                <input type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm"' class="button">
            </td>
        </tr>
    </table>
</form:form>

<script type="text/javascript">
function focusIt()
{
	var uiAttachCapacityLicenseError = '<c:out value="${uiAttachCapacityLicenseError}"/>';
	var gbAttachCapacityLicenseError = '<c:out value="${gbAttachCapacityLicenseError}"/>';
	var totalAttachCapacityLicenseError = '<c:out value="${totalAttachCapacityLicenseError}"/>';
	var thoughputCapacityLicenseError = '<c:out value="${thoughputCapacityLicenseError}"/>';
	var pdpContextCapacityLicenseError = '<c:out value="${pdpContextCapacityLicenseError}"/>';

	if(document.checkform.sgsnid.value==""){
		  var mytext = document.getElementById("sgsnid");
		  mytext.focus();
	}
	else if(document.checkform.sgsnName.value==""){
		  var mytext = document.getElementById("sgsnName");
		  mytext.focus();
	}
	else if(uiAttachCapacityLicenseError !="")
	{
			var mytext = document.getElementById("uiAttachCapacityLicense");
			  mytext.focus();
	}
	else if(gbAttachCapacityLicenseError !="")
	{
			var mytext = document.getElementById("gbAttachCapacityLicense");
			  mytext.focus();
	}
	else if(totalAttachCapacityLicenseError !="")
	{
			var mytext = document.getElementById("totalAttachCapacityLicense");
			  mytext.focus();
	}
	else if(thoughputCapacityLicenseError !="")
	{
			var mytext = document.getElementById("thoughputCapacityLicense");
			  mytext.focus();
	}
	else if(pdpContextCapacityLicenseError !="")
	{
			var mytext = document.getElementById("pdpContextCapacityLicense");
			  mytext.focus();
	}
}
onload = focusIt;
</script>
<script type="text/javascript"> 

	function loadToolbar(){
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
	};
	
	$("select#dept").change(function(){
		loadToolbar();
		$.getJSON("${pageContext.request.contextPath}/ajax/loadTeamByDept.htm",{dept: $(this).val()}, function(j){
			var options = '<option  value=""></option>';
			for (var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i].deptCode + '">' + j[i].deptName + '</option>';
			}
			$("#team").html(options);
			$('#team option:first').attr('selected', 'selected');
		})
	});
	
	/* Load subteam by dept, team */
	$("select#dept").change(function(){
		loadToolbar();
		$.getJSON("${pageContext.request.contextPath}/ajax/loadSubTeamByTeam.htm",{dept: $(dept).val(), team:$(team).val()}, function(j){
			var options = '<option  value=""></option>';
			for (var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i].deptCode + '">' + j[i].deptName + '</option>';
			}
			$("#subTeam").html(options);
			$('#subTeam option:first').attr('selected', 'selected');
		})
	});
	
	/* ------------------------------------------- */
	/* Load subteam by dept, team */
	$("select#team").change(function(){
		loadToolbar();
		$.getJSON("${pageContext.request.contextPath}/ajax/loadSubTeamByTeam.htm",{dept: $(dept).val(), team:$(team).val()}, function(j){
			var options = '<option  value=""></option>';
			for (var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i].deptCode + '">' + j[i].deptName + '</option>';
			}
			$("#subTeam").html(options);
			$('#subTeam option:first').attr('selected', 'selected');
		})
	});
	/* ------------------------------------------- */

</script>