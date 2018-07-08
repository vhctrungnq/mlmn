<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Cập nhật thông tin LAC</title>
<content tag="heading">Cập nhật thông tin LAC</content>

<form:form commandName="bsc" method="post" action="form.htm">
	<div>
		<form:hidden path="region" />
		<form:hidden path="launchDate" />
	</div>
	<table class="simple2">
	 	<tr> 
	        <td width="200"><b>Vendor</b></td>
	        <td class = "mwid110">
	           	<form:select path="vendor" class="wid90" onchange="xl()"> 
						<form:option  value="">Tất cả</form:option >
						<c:forEach var="item" items="${vendorList}">
							<c:choose>
								<c:when test="${item.name == vendor}">
									<form:option  value="${item.name}" selected="selected">${item.value}</form:option >
								</c:when>
								<c:otherwise>
									<form:option  value="${item.name}">${item.value}</form:option >
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select>
			</td>
	      </tr>
	      <tr> 
	        <td width="200"><b>Chi nhánh</b></td>
	        <td class = "mwid110">
	           	<form:select path="cn" class="wid90" onchange="xl()"> 
						<form:option  value="">Tất cả</form:option >
						<c:forEach var="item" items="${branchList}">
							<c:choose>
								<c:when test="${item.name == cn}">
									<form:option  value="${item.name}" selected="selected">${item.value}</form:option >
								</c:when>
								<c:otherwise>
									<form:option  value="${item.name}">${item.value}</form:option >
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select>
			</td>
	      </tr>
	      <tr>  
	          <td><b>BSC<font color = "red">(*)</font></b></td>
	          <td class = "mwid110">            	
	            <c:choose>
	              <c:when test="${empty bsc.bscid}">
	                  <form:input path="bscid" maxlength="50"/>
	              </c:when>
	              <c:otherwise>
	                  <b><i>${bsc.bscid}</i></b><form:hidden path="bscid" />
	              </c:otherwise>
	            </c:choose>
	               <font color="red"><form:errors path="bscid"/></font>  
	          </td>
	      </tr> 
	      <tr>  
	          <td><b>Lac<font color = "red">(*)</font></b></td>
	          <td class = "mwid110">            	
	            <c:choose>
	              <c:when test="${empty bsc.lac}">
	                  <form:input path="lac" maxlength="50"/>
	              </c:when>
	              <c:otherwise>
	                  <b><i>${bsc.lac}</i></b><form:hidden path="lac" />
	              </c:otherwise>
	            </c:choose>
	               <font color="red"><form:errors path="lac"/></font>  
	          </td>
	      </tr> 
	       <tr>
	           <td class = "mwid110"><b>Rac</b></td>
	           <td><form:input path="rac" style="width: 190px;" maxlength="40"/></td>
	      </tr>
	       <tr>
            <td></td>
            <td>
                <input type="submit" class="button" id ="submit" name="submit" value="Save"/>
                <input type="button" value="Cancel" onClick='window.location="list.htm"' class = "button">
            </td>
        </tr>
    </table> 
</form:form> 

<script type="text/javascript"> 
function xl(){
	/* Focus to button submit */
	var sub = document.getElementById("submit");
	sub.focus();
}
</script>
