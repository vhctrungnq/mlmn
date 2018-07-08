<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>${titleF}</title>
<content tag="heading">${titleF}</content>

<div class="body-content"></div>
<form:form commandName="provincesCode" name="checkform" method="post" action="form.htm">
	<input name="tick" value="${tick}" type="hidden">
	<form:hidden path="id"/>
    <table class="simple2">
     <tr>
         <td class="wid15 mwid110"><fmt:message key="provincesCode.region"/>&nbsp;<font color="red">(*)</font></td>
         <td class="wid35">            	
           	<form:select path="region" class="wid30">
	           		<c:forEach var="item" items="${regionList}">
						<c:choose>
			                <c:when test="${item.name == region}">
			                    <option value="${item.name}" selected="selected">${item.value}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.name}">${item.value}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select>   
            <font color="red"><form:errors path="region"/></font>
         </td>
         <td class="wid15 mwid110"><fmt:message key="provincesCode.branch"/></td>
         <td><form:input path="branch" maxlength="50" class="wid60"/></td>
        </tr>
        <tr>
	        <td><fmt:message key="provincesCode.location"/></td>
	        <td><form:input path="location" maxlength="50" class="wid60"/></td>
	        <td><fmt:message key="hWards.ordering"/></td>
            <td>
            	<form:input path="ordering" maxlength="4" class="wid30"/>&nbsp;
        		<font color="red"><form:errors path="ordering"/></font>
        	</td>
        </tr>
        <tr>
         	<td><fmt:message key="provincesCode.code"/>&nbsp;<font color="red">(*)</font></td>
            <td>
            	<c:choose>
	                <c:when test="${empty provincesCode.id}">
	                	<form:input path="code" maxlength="15" class="wid60"/>&nbsp; 
	                    <font color="red"><form:errors path="code"/></font>
	                </c:when>
	                <c:otherwise>
	                    <b><i>${provincesCode.code}</i></b><form:hidden path="code" />
	                </c:otherwise>
	            </c:choose>  
            </td>
            <td><fmt:message key="provincesCode.province"/>&nbsp;<font color="red">(*)</font></td>
            <td>
            	<form:input path="province" maxlength="50" class="wid60"/>&nbsp;
            	<font color="red"><form:errors path="province"/></font>
            </td> 
        </tr>
        <tr>
        	<td><fmt:message key="provincesCode.district"/>&nbsp;<font color="red">(*)</font></td>
            <td>
            	<c:choose>
	                <c:when test="${empty provincesCode.id}">
	                	<form:input path="district" maxlength="50" class="wid60"/>&nbsp;
            			<font color="red"><form:errors path="district"/></font>
	                </c:when>
	                <c:otherwise>
	                    <b><i>${provincesCode.district}</i></b><form:hidden path="district" />
	                </c:otherwise>
	            </c:choose>  
            </td>
            <td><fmt:message key="provincesCode.districtName"/>&nbsp;<font color="red">(*)</font></td>
            <td>
            	<form:input path="districtName" maxlength="50" class="wid60"/>&nbsp;
            	<font color="red"><form:errors path="districtName"/></font>
            </td>
        </tr>
        <tr>
        	<td><fmt:message key="qLThongTinPhanAnh.phongDai"/></td>
			 <td>
				<form:select path="deptCode" class="wid60">
						<option value=""></option>
          				<c:forEach var="items" items="${deptProcessList}">
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
            <td><fmt:message key="qLThongTinPhanAnh.toXuLy"/></td>
            <td>
            	<form:select path="team" class="wid60">
            			<option value=""></option>
          				<c:forEach var="items" items="${teamList}">
			              	<c:choose>
			                <c:when test="${items.team == teamCBB}">
			                    <option value="${items.team}" selected="selected">${items.team}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${items.team}">${items.team}</option>
			                </c:otherwise>
			              	</c:choose>
				    	</c:forEach>
   					</form:select>&nbsp;
            </td>
        </tr>
        <tr>
        	<td><fmt:message key="provincesCode.marks"/></td>
        	<td colspan="3">
           		<form:textarea path="marks" style="font-family:tahoma;width:86%; font-size:12px;height:30px;" rows="10" maxlength="200"/>
	    	</td> 
        </tr>
        <tr>
            <td></td>
            <td colspan="5">
                <input type="submit" class="button" value="<fmt:message key="button.save"/>" />
           	   	<input type="button" class="button" value="<fmt:message key="button.cancel"/>" onClick='window.location="list.htm"'>
            </td>
        </tr>
    </table>
    
</form:form>
<script type="text/javascript">
$('#deptCode').change(function(){

	$.ajax({
	  url: "${pageContext.request.contextPath}/feedback/so-luong-pa-theo-to-vien-thong/loadTeam.htm",
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
	  data:{deptProcess: $('#deptCode').val()}}).done(function(j) {
		  var options = '';
		  options += '<option value=""></option>';
		  for (var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i].team + '">' + j[i].team + '</option>';
			}
		$("#team").html(options);
		$('#team option:first').attr('selected', 'selected');
	    
	  });
	  
});

function focusIt()
{
	var orderingError = '<c:out value="${orderingError}"/>';

	if(document.checkform.code.value==""){
		  var mytext = document.getElementById("code");
		  mytext.focus();
		}
	else if(document.checkform.province.value==""){
	  var mytext = document.getElementById("province");
	  mytext.focus();
	}
	else if(document.checkform.district.value==""){
		  var mytext = document.getElementById("district");
		  mytext.focus();
		}
	else if(document.checkform.districtName.value==""){
		  var mytext = document.getElementById("districtName");
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