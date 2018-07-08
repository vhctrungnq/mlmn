<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>${titleF}</title>
<content tag="heading">${titleF}</content>
<div class="body-content"></div>
<form:form commandName="wards" name="checkform" method="post" action="form.htm">
	<form:hidden path="id" />
    <table class="simple2">
     	<tr>
            <td class="wid15 mwid110"><fmt:message key="hWards.village"/>&nbsp;<font color="red">(*)</font></td>
            <td class="wid35">     
                <form:input path="village" maxlength="50" class="wid60"/>&nbsp;
                <font color="red"><form:errors path="village"/></font>
            </td >
            <td class="wid15 mwid110"><fmt:message key="hWards.villageName"/>&nbsp;<font color="red">(*)</font></td>
            <td>
            	<form:input path="villageName" maxlength="255" class="wid60"/>&nbsp;
            	<font color="red"><form:errors path="villageName"/></font>
            </td>
        </tr>
        <tr>
        	<td><fmt:message key="hWards.provinceCode"/>&nbsp;<font color="red">(*)</font></td>
			 <td>
            	<select name="provinceCode" id="provinceCode" class="wid60">
					<c:forEach var="item" items="${provinceCodeList}">
						<c:choose>
			                <c:when test="${item.code == provinceCode}">
			                    <option value="${item.code}" selected="selected">${item.province}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.code}">${item.province}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>&nbsp;
				<font color="red"><form:errors path="provinceCode"/></font>
            </td>
            <td><fmt:message key="hWards.districtCode"/>&nbsp;<font color="red">(*)</font></td>
			 <td>
            	<select name="districtCode" id="districtCode" class="wid60">
					<c:forEach var="item" items="${districtCodeList}">
						<c:choose>
			                <c:when test="${item.district == districtCode}">
			                    <option value="${item.district}" selected="selected">${item.districtName}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.district}">${item.districtName}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>&nbsp; 
				<font color="red"><form:errors path="districtCode"/></font>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="hWards.ordering"/></td>
            <td>
            	<form:input path="ordering" maxlength="4" class="wid30"/>&nbsp;
        		<font color="red"><form:errors path="ordering"/></font>
        	</td>
        	<td><fmt:message key="hWards.description"/></td>
        	<td>
        		<form:input path="description" maxlength="200" class="wid60"/>
	    	</td> 
        </tr>
        <tr>
            <td></td>
            <td colspan="3">
                <input type="submit" class="button" value="<fmt:message key="button.save"/>" />
           	   	<input type="button" class="button" value="<fmt:message key="button.cancel"/>" onClick='window.location="list.htm"'>
            </td>
        </tr>
    </table>
    
</form:form>
<script type="text/javascript">
	$("#provinceCode").change(function(){
		loadToolbar();
		$.getJSON("${pageContext.request.contextPath}/ajax/districtCodeList.htm",{provinceCode: $(this).val()}, function(j){
			
			var options = '';
			for (var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i].district + '">' + j[i].districtName+ '</option>';
			}
			$("#districtCode").html(options);
		});
	
	});
	
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
	
	function focusIt()
	{
		var orderingError = '<c:out value="${orderingError}"/>';

		if(document.checkform.village.value==""){
			  var mytext = document.getElementById("village");
			  mytext.focus();
			}
		else if(document.checkform.villageName.value==""){
		  var mytext = document.getElementById("villageName");
		  mytext.focus();
		}
		else if(orderingError != "")
		{
			var mytext = document.getElementById("ordering");
		  	mytext.focus();
		}
	}
	onload = focusIt;
</script>
	