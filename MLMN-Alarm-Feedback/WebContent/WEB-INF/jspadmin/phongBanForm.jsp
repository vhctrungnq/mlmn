<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<c:choose>
  <c:when test="${maPhongAddEdit == 'N'}">
      <title><fmt:message key="sidebar.admin.departmentManaFormAdd"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.departmentManaFormAdd"/></content>
  </c:when>
  <c:when test="${maPhongAddEdit == 'Y'}">
      <title><fmt:message key="sidebar.admin.departmentManaFormEdit"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.departmentManaFormEdit"/></content>
  </c:when>
  <c:otherwise>
      
  </c:otherwise>
</c:choose>


<form:form commandName="phongBanform" name="checkform" method="post" action="form.htm">
	<div>
    		<form:input path="id" type="hidden" />
    </div>
    <table class="simple2"> 
    	
      <tr>
           <td class="wid10 mwid80">
           		<fmt:message key="qLPhongBan.maPhong"/>&nbsp;<font color="red">(*)</font>
           	</td>
           <td class="wid20">
           		<c:choose>
                <c:when test="${maPhongAddEdit == 'N'}">
                    <form:input cssClass="wid90" path="deptCode" maxlength="200"/>
                </c:when>
                <c:when test="${maPhongAddEdit == 'Y'}">
                    <b><i>${phongBanform.deptCode}</i></b><form:hidden path="deptCode" />
                </c:when>
                <c:otherwise>
                    
                </c:otherwise>
            	</c:choose>&nbsp;<form:errors path="deptCode" cssClass="error"/>
           </td>
           <td class="wid10 mwid80">
           		<fmt:message key="qLPhongBan.tenVietTat"/>&nbsp;<font color="red">(*)</font>
           </td>
           <td class="wid20">
           		<form:input path="abbreviated" maxlength="200" cssClass="wid90"/>&nbsp;<form:errors path="abbreviated" cssClass="error"/>
           </td>
           <td class="wid10 mwid80">
           		<fmt:message key="qLPhongBan.tenPhong"/>&nbsp;<font color="red">(*)</font>
           </td>
           <td class="wid20">
           		<form:input path="deptName" maxlength="230" cssClass="wid90"/>&nbsp;<form:errors path="deptName" cssClass="error"/>
           </td>
      </tr>
      <tr>
      		<td>
        	<fmt:message key="qLPhongBan.region"/>
	        </td>
	        <td>
		        <form:select path="region" cssClass="wid90">
	   					<c:forEach var="items" items="${regionList}">
			              <c:choose>
			                <c:when test="${items.name == region}">
			                    <option value="${items.name}" selected="selected">${items.value}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${items.name}">${items.value}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
	           		</form:select>	
		        
	        </td>
      		<td>
           		<fmt:message key="qLPhongBan.dienThoai"/>
          	</td>
           	<td >
           		<form:input path="phone" maxlength="30" cssClass="wid90"/>&nbsp;<form:errors path="phone" cssClass="error"/>
           	</td>
           	<td>
           		<fmt:message key="qLPhongBan.fax"/>
          	</td>
           	<td >
           		<form:input path="fax" maxlength="40" cssClass="wid90"/>
           	</td>
           	
           	
      </tr>
      <tr>
        <td>
        	<fmt:message key="qLPhongBan.trangThai"/>
        </td>
        <td>
	        <form:select path="isEnable" cssClass="wid90">
   					<c:forEach var="items" items="${trangThaiList}">
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
        <%-- <td>
        	<b><fmt:message key="qLPhongBan.heThong"/></b>
      	</td>
      	<td>
      		 <form:select path="system" cssClass="wid90">
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
           		</form:select>	
      	</td> --%>
      	<td><fmt:message key="qLPhongBan.parentId"/></td>
      	<td>
      		<c:choose>
			  <c:when test="${maPhongAddEdit == 'N'}">
			      <input type="checkbox" id="phongBanCha" style="float:left" name="phongBanCha" value="Y" checked = "checked" />
			      &nbsp;<span id="selectParentIdChanged" style="display:none">
			           	<select name="parent_id" id="selectParentId" class="wid50">
			          				<c:forEach var="items" items="${phongBanChaList}">
					              <c:choose>
					                <c:when test="${items.id == idPhongBanCBB}">
					                    <option value="${items.id}" selected="selected">${items.deptCode}</option>
					                </c:when>
					                <c:otherwise>
					                    <option value="${items.id}">${items.deptCode}</option>
					                </c:otherwise>
					              </c:choose>
							    </c:forEach>
						</select>
					</span>
			  </c:when>
			  <c:when test="${maPhongAddEdit == 'Y'}">
			      <input type="checkbox" id="phongBanCha" style="float:left" name="phongBanCha" value="Y" />
			      &nbsp;<span id="selectParentIdChanged" style="display:none">
			           	<select name="parent_id" id="selectParentId" class="wid50">
			          				<c:forEach var="items" items="${phongBanChaList}">
					              <c:choose>
					                <c:when test="${items.id == idPhongBanCBB}">
					                    <option value="${items.id}" selected="selected">${items.deptCode}</option>
					                </c:when>
					                <c:otherwise>
					                    <option value="${items.id}">${items.deptCode}</option>
					                </c:otherwise>
					              </c:choose>
							    </c:forEach>
						</select>
					</span>
			  </c:when>
			  <c:otherwise>
			      
			  </c:otherwise>
			</c:choose>
      	</td>
      	<td><fmt:message key="qLPhongBan.diaChi"/></td>
      	<td><form:input path="address" maxlength="240" cssClass="wid90"/></td>
      </tr>
      <tr>
      		<td>
      			<fmt:message key="qLPhongBan.sapXep"/>
      		</td>
      		<td>
           		<form:input path="ordering" maxlength="4" cssClass="wid50"/>&nbsp;<form:errors path="ordering" cssClass="error"/>
	        </td>
	        <td colspan="4"></td>
      </tr>
       <tr>
           <td></td>
           <td colspan="5">
               <input class="button" type="submit" class="button" name="save" value="<fmt:message key="button.save"/>" />
               <input class="button" type="button" value="<fmt:message key="button.cancel"/>" onClick='window.location="danh-sach.htm"'>
           </td>
       </tr>
    </table>

</form:form>


<script type="text/javascript">
$(function() {
	var parent = '<c:out value="${phongBanform.parentId}"/>';
	
	if(parent == "")
		$('#phongBanCha').attr('checked', true);
	else
		$('#phongBanCha').attr('checked', false);
	
	if (!$('#phongBanCha').is(':checked')) {
    	$("#selectParentIdChanged").show();
    }
    else{
    	$("#selectParentIdChanged").hide();
    }
});

$('#phongBanCha').click(function() {
    if (!$(this).is(':checked')) {
    	$("#selectParentIdChanged").show();
    }
    else{
    	$("#selectParentIdChanged").hide();
    }
});

function focusIt()
{
	var temp = '<c:out value="${maPhongAddEdit}"/>';
	if(temp == 'N')
	{
		if(document.checkform.deptCode.value == ""){
			  var mytext = document.getElementById("deptCode");
			  mytext.focus();
		}
		else if(document.checkform.abbreviated.value=="")
		{
				var mytext = document.getElementById("abbreviated");
				mytext.focus();
		}
		else if(document.checkform.deptName.value=="")
		{
			var mytext = document.getElementById("deptName");
			mytext.focus();
		}
	}
	else
		{
			if(document.checkform.abbreviated.value=="")
			{
					var mytext = document.getElementById("abbreviated");
					mytext.focus();
			}
			else if(document.checkform.deptName.value=="")
			{
				var mytext = document.getElementById("deptName");
				mytext.focus();
			}
			else
				{
				var mytext = document.getElementById("phone");
				mytext.focus();
				}
		}
}

onload = focusIt;

</script>

