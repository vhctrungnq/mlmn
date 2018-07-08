<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<c:choose>
  <c:when test="${maPAAddEdit == 'N'}">
      <title><fmt:message key="sidebar.admin.loaiPhanAnhFormAdd"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.loaiPhanAnhFormAdd"/></content>
  </c:when>
  <c:when test="${maPAAddEdit == 'Y'}">
      <title><fmt:message key="sidebar.admin.loaiPhanAnhFormEdit"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.loaiPhanAnhFormEdit"/></content>
  </c:when>
  <c:otherwise>
      
  </c:otherwise>
</c:choose>
   
<form:form commandName="FbType" name="checkform" method="post" action="form.htm"> 
	<div>
    		<form:input path="id" type="hidden" />
    </div>
    <table class="simple2"> 
      <tr>
           <td class="wid15 mwid90">
           		<b><fmt:message key="qLLoaiPhanAnh.maPhanAnh"/></b>&nbsp;<font color="red">(*)</font>
           	</td>
           <td>
           		<c:choose>
                <c:when test="${maPAAddEdit == 'N'}">
                    <form:input path="code" maxlength="40" cssClass="wid20"/>
                </c:when>
                <c:when test="${maPAAddEdit == 'Y'}">
                    <b><i>${FbType.code}</i></b><form:hidden path="code" />
                </c:when>
                <c:otherwise>
                    
                </c:otherwise>
            </c:choose>&nbsp;<form:errors path="code" cssClass="error"/>
           </td>
      	</tr>
      	<tr>
      		<td>
           		<b><fmt:message key="qLLoaiPhanAnh.tenPhanAnh"/></b>&nbsp;<font color="red">(*)</font>
          	</td>
           	<td>
           		<form:input path="name" maxlength="240" cssClass="wid20"/>&nbsp;<form:errors path="name" cssClass="error"/>
           	</td>
      </tr>
      <tr>
      	<td>
      		<b><fmt:message key="qLLoaiPhanAnh.sapXep"/></b>
      	</td>
      	<td>
           		<form:input path="ordering" maxlength="4" cssClass="wid10"/>&nbsp;<form:errors path="ordering" cssClass="error"/>
        </td>
        
      </tr>
      <tr>
      	<td>
        	<b><fmt:message key="qLLoaiPhanAnh.trangThai"/></b>
        </td>
        <td>
        	
           	<form:select path="isEnable" cssClass="wid20">
   					<c:forEach var="items" items="${sysParameterByCodeList}">
						              <c:choose>
						                <c:when test="${items.value == trangThaiCBB}">
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
       <%-- <tr>
      
      	<td><b><fmt:message key="qLLoaiPhanAnhPhongBan.thoiHanXuLy"/></b></td>
      	<td>
      		<form:input path="timeProcess" id ="timeProcess" cssClass="wid20" maxlength="5"/><font color="#0560A6"> (HH:MM) </font>&nbsp;<form:errors path="timeProcess" cssClass="error"/>
      	</td>
      	</tr> --%>
      <tr>
      	<td><b><fmt:message key="qLLoaiPhanAnh.dienGiai"/></b></td>
      	<td>
      		<form:input path="description" maxlength="245" cssClass="wid50"/>
      	</td>
      </tr>
       <tr>
           <td></td>
           <td>
               <input class="button" type="submit" class="button" name="save" value="<fmt:message key="global.form.luulai"/>" />
               <input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm"'>
           </td>
       </tr>
    </table>

</form:form>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/timePicker/jquery.timePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/timePicker/jquery.timePicker.min.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/timePicker.css" />

<script type="text/javascript">
jQuery(function() {
    // Default.
    $("#timeProcess").timePicker();
});
</script>
<script type="text/javascript">

function focusIt()
{
	var sapXepError = '<c:out value="${sapXepError}"/>';
	var thoiHanXuLyError = '<c:out value="${thoiHanXuLyError}"/>';
	if(document.checkform.code.value==""){
		  var mytext = document.getElementById("code");
		  mytext.focus();
		}
		else if(document.checkform.name.value == "")
			{
			var mytext = document.getElementById("name");
			  mytext.focus();
			}
		else if(sapXepError != "")
			{
				var mytext = document.getElementById("ordering");
			  	mytext.focus();
			}
		else if(thoiHanXuLyError != "")
		{
			var mytext = document.getElementById("timeProcess");
		  	mytext.focus();
		}
}

onload = focusIt;

</script>

