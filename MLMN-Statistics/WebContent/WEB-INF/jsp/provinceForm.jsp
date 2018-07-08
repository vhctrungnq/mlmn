<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:choose>
  <c:when test="${HProvincesCodeAddEdit == 'N'}">
      <title><fmt:message key="title.hProvincesCode.formAdd"/></title>
	  <content tag="heading"><fmt:message key="title.hProvincesCode.formAdd"/></content>
  </c:when>
  <c:when test="${HProvincesCodeAddEdit == 'Y'}">
      <title><fmt:message key="title.hProvincesCode.formEdit"/></title>
	  <content tag="heading"><fmt:message key="title.hProvincesCode.formEdit"/></content>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>

<form:form name="checkform" commandName="province" method="post" action="form.htm">
	<form:input id="id" path="id" type="hidden"/>
    <table class="simple2">
     <tr>
         <td class="wid15 mwid110"><fmt:message key="hProvincesCode.code"/>&nbsp;<font color = "red">(*)</font></td>
         <td>
         	<%-- <c:choose>
                <c:when test="${HProvincesCodeAddEdit == 'N'}">
                    <form:input path="code" maxlength="15" cssClass="wid30"/>
                </c:when>
                <c:when test="${HProvincesCodeAddEdit == 'Y'}">
                    <b><i>${province.code}</i></b><form:hidden path="code" />
                </c:when>
                <c:otherwise></c:otherwise>
            </c:choose> --%>
            <form:input path="code" maxlength="15" cssClass="wid30"/>
            &nbsp;<form:errors path="code" cssClass="error"/>
         </td>
     </tr>
     <tr>
         <td><fmt:message key="hProvincesCode.region"/></td>
         <td>            	
           	<form:select path="region" cssClass="wid20">
                  <form:option value="TT6"/>
            </form:select>&nbsp;<form:errors path="region" cssClass="error"/>
         </td>
     </tr>
      <tr>
          <td><fmt:message key="hProvincesCode.branch"/></td>
          <td><form:input path="branch" maxlength="40" cssClass="wid30"/></td>
      </tr>
      <tr>
          <td><fmt:message key="hProvincesCode.location"/></td>
          <td><form:input path="location" maxlength="40" cssClass="wid30"/></td>
      </tr>
      <tr>
          <td><fmt:message key="hProvincesCode.province"/></td>
          <td><form:input path="province" maxlength="40" cssClass="wid30"/></td>
      </tr>
      <tr>
          <td><fmt:message key="hProvincesCode.district"/></td>
          <td><form:input path="district" maxlength="40" cssClass="wid30"/></td>
      </tr>
      <tr>
      	<td><fmt:message key="hProvincesCode.dept"/></td>
			<td>
				<%-- <select name="dept" class="wid20" id="dept">
       				<c:forEach var="items" items="${maPhongList}">
		              <c:choose>
		                <c:when test="${items.deptCode == deptCBB}">
		                    <option value="${items.deptCode}" selected="selected">${items.deptCode}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.deptCode}">${items.deptCode}</option>
		                </c:otherwise>
		              </c:choose>
				    </c:forEach>
       			</select> --%>
       			<form:select path="dept" cssClass="wid20">  
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
      </tr>
      <tr>
      	<td><fmt:message key="hProvincesCode.team"/></td>
      	<td>
       		<form:select path="team" cssClass="wid20">  
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
        		<form:select path="groups" cssClass="wid20">  
	           		<form:option  value=""></form:option >
				        <c:forEach var="temp" items="${subteamList}">
				              <c:choose>
				                <c:when test="${temp.deptCode == groups}">
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
          <td>
              <input class="button" type="submit" name="save" value="<fmt:message key="global.form.luulai"/>" />
              <input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm"'>
          </td>
      </tr>
</table>
</form:form>
<script type="text/javascript">
function focusIt()
{
	if(document.checkform.code.value==""){
		  var mytext = document.getElementById("code");
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
			$("#groups").html(options);
			$('#groups option:first').attr('selected', 'selected');
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
			$("#groups").html(options);
			$('#groups option:first').attr('selected', 'selected');
		})
	});
	/* ------------------------------------------- */

</script>