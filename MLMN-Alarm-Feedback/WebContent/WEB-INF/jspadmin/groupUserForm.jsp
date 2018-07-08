<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:choose>
  <c:when test="${nhomNguoiDungAddEdit == 'N'}">
      <title><fmt:message key="sidebar.admin.groupUserFormAdd"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.groupUserFormAdd"/></content>
  </c:when>
  <c:when test="${nhomNguoiDungAddEdit == 'Y'}">
      <title><fmt:message key="sidebar.admin.groupUserFormEdit"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.groupUserFormEdit"/></content>
  </c:when>
  <c:otherwise>
      
  </c:otherwise>
</c:choose>      

<form:form commandName="sysGroupUserForm" name="checkform" method="post" action="form.htm"> 
	<div>
		<form:hidden path="id"/>
	</div>
    <table class="simple2"> 	
     <tr>
     	<td class="wid15 mwid160">
     		<fmt:message key="qLNhomNguoiDung.tenNhom"/>&nbsp;<font color="red">(*)</font>
     	</td>
     	<td>
            <form:input path="groupName" maxlength="240" cssClass="wid30"/>&nbsp;<form:errors path="groupName" cssClass="error"/>
       </td>
       
     </tr>
      <tr>
           <td >
           		<fmt:message key="qLPhongBan.heThong"/>&nbsp;<font color="red">(*)</font>
          	</td>
           	<td >
           		<select name="system" class="wid30">
	   				<c:forEach var="items" items="${systemList}">
		              <c:choose>
		                <c:when test="${items.value == systemCBB}">
		                    <option value="${items.value}" selected="selected">${items.name}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.value}">${items.name}</option>
		                </c:otherwise>
		              </c:choose>
				    </c:forEach>
       			</select>
           	</td>       
      </tr>
      
      <tr>
      	<td><fmt:message key="qLNhomNguoiDung.sapXep"/></td>
      	<td><form:input path="ordering" maxlength="4" cssClass="wid10" />&nbsp;<form:errors path="ordering" cssClass="error"/></td>
      </tr>
      <tr>
      	<td><fmt:message key="qLNhomNguoiDung.dienGiai"/></td>
      	<td><form:input path="description" maxlength="220" cssClass="wid30" /></td>
      </tr>
       <tr>
           <td></td>
           <td>
               <input class="button" type="submit" class="button" name="save" value="<fmt:message key="global.form.luulai"/>" />
               <input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="danh-sach.htm"'>
           </td>
       </tr>
    </table>

</form:form>


<script type="text/javascript">

function focusIt()
{
	var orderingError = '<c:out value="${orderingError}"/>';
	if(document.checkform.groupName.value==""){
		  var mytext = document.getElementById("groupName");
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

