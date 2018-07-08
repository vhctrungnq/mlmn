<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:choose>
  <c:when test="${toXuLyAddEdit == 'N'}">
      <title><fmt:message key="title.toXuLy.themMoiTXL"/></title>
	  <content tag="heading"><fmt:message key="title.toXuLy.themMoiTXL"/></content>
  </c:when>
  <c:when test="${toXuLyAddEdit == 'Y'}">
      <title><fmt:message key="title.toXuLy.suaTXL"/></title>
	  <content tag="heading"><fmt:message key="title.toXuLy.suaTXL"/></content>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>

<form:form commandName="FbDePlace" method="post" action="form.htm">
	<form:hidden path="id" />
	
    <table  class="simple2">
        <tr>
            <td class="wid15 mwid150"><b><fmt:message key="title.toXuLy.phongBanToXuLy"/></b>&nbsp;<font color="red">(*)</font></td>
            <td>
				<form:select path="deptCode" cssClass="wid30">
					<c:forEach var="items" items="${DepartmentList}">
		              <c:choose>
		                <c:when test="${items.deptCode == departmentCBB}">
		                    <option value="${items.deptCode}" selected="selected">${items.abbreviated}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.deptCode}">${items.abbreviated}</option>
		                </c:otherwise>
		              </c:choose>
				    </c:forEach>
				</form:select>&nbsp;<font color = "red"><form:errors path = "deptCode"/></font>
            </td>
        </tr>
        <tr>
          <td class="wid10 mwid110"><b><fmt:message key="qLThongTinPhanAnh.quan"/></b>&nbsp;<font color="red">(*)</font></td>
          <td> 
	          	<form:select path="placesCode" cssClass="wid30">
					<c:forEach var="items" items="${quanHuyenList}">
		              	<c:choose>
		                <c:when test="${items.code == tinhThanhCBB && items.district == quanHuyenCBB}">
		                    <option value="${items.code}//${items.district}" selected="selected">${items.districtName}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.code}//${items.district}">${items.districtName}</option>
		                </c:otherwise>
		              	</c:choose>
			    	</c:forEach>
				</form:select>&nbsp;<font color = "red"><form:errors path = "placesCode"/></font>
            </td>
        </tr>
        <tr>
            <td><b><fmt:message key="title.toXuLy.sapXep"/></b></td>
            <td><form:input path="ordering" cssClass="wid10"></form:input>
            	<font color = "red"><form:errors path = "ordering"/></font>
            </td>
        </tr>
 
        <tr>
            <td></td>
            <td colspan="5">
               <input class="button" type="submit" class="button" name="save" value="<fmt:message key="global.form.luulai"/>" />
               <input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm"'>
           </td>
        </tr>
    </table>
    
</form:form>

<script type="text/javascript">

function focusIt()
{	
  var mytext = document.getElementById("ordering");
  mytext.focus();
}

onload = focusIt;

</script>