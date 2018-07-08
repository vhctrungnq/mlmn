<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<c:choose>
  <c:when test="${MscAddEdit == 'N'}">
      <title><fmt:message key="title.hMsc.formAdd"/></title>
	  <content tag="heading"><fmt:message key="title.hMsc.formAdd"/></content>
  </c:when>
  <c:when test="${MscAddEdit == 'Y'}">
      <title><fmt:message key="title.hMsc.formEdit"/></title>
	  <content tag="heading"><fmt:message key="title.hMsc.formEdit"/></content>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>
<div class="body-content"></div>
<form:form name="checkform" commandName="msc" method="post" action="form.htm">
	<form:hidden path="region" />
	<form:input path="id" type="hidden" />
    <table class="simple2">
        <tr>
            <td class="wid15 mwid110"><fmt:message key="hMsc.mscid"/>&nbsp;<font color="red">(*)</font></td>
            <td class="wid35">            	
              <%-- <c:choose>
                <c:when test="${empty msc.mscid}">
                    <form:input path="mscid" maxlength="80" cssClass="wid50"/>
                </c:when>
                <c:otherwise>
                    <b><i>${msc.mscid}</i></b><form:hidden path="mscid" />
                </c:otherwise>
              </c:choose> --%>
              <form:input path="mscid" maxlength="80" cssClass="wid50"/>
             &nbsp;<form:errors path="mscid" cssClass="error"/> 
            </td>
            <td class="wid15 mwid110"><fmt:message key="hMsc.vendor"/></td>
            <td>
            	<form:select path="vendor" class="wid50" >
       				<c:forEach var="items" items="${vendorList}">
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
        </tr>
        <tr>
        	<td><fmt:message key="hMsc.location"/></td>
        	<td><form:input path="location" maxlength="20" cssClass="wid50"/></td>
        	<td><fmt:message key="hMsc.locationName"/></td>
        	<td><form:input path="locationName" maxlength="50" cssClass="wid50"/></td>
        </tr>
        <tr>
            <td><fmt:message key="hMsc.msuCapacity"/></td>
            <td><form:input path="msuCapacity" maxlength="4" cssClass="wid30"/>&nbsp;<form:errors path="msuCapacity" cssClass="error"/></td>
            <td><fmt:message key="hMsc.neGroup"/></td>
            <td><form:input path="neGroup" maxlength="40" cssClass="wid50"/></td>
        </tr>
        <tr>
        	<td><fmt:message key="hMsc.longitude"/></td>
        	<td><form:input path="longitude" maxlength="20" cssClass="wid50"/></td>
        	<td><fmt:message key="hMsc.latitude"/></td>
        	<td><form:input path="latitude" maxlength="20" cssClass="wid50"/></td>
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
        	<td colspan="3">
        		<form:select path="subTeam" cssClass="wid30">  
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
	var msuCapacityError = '<c:out value="${msuCapacityError}"/>';

	if(document.checkform.mscid.value==""){
		  var mytext = document.getElementById("mscid");
		  mytext.focus();
	}
	else if(msuCapacityError !="")
	{
			var mytext = document.getElementById("msuCapacity");
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