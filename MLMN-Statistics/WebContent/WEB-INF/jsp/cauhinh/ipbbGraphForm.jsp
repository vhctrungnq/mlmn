<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Cập nhật thông tin IPBB</title>
<content tag="heading">Cập nhật thông tin IPBB</content>

<form:form commandName="ipbbGraph" method="post" action="form.htm"> 
	<form:hidden path="id" /> 
	
    <table class="simple2">
    		<tr>  
	          	<td width="200"><b>Local Graph Id</b></td>
				<td >
					<form:input type="text" path="localGraphId" maxlength ="6" style="width:250px;"/>
					<font color="red"><form:errors path="localGraphId"/></font>
				</td>
	      	</tr> 
	      	
	    	<tr>  
	          	<td><b>Title<font color="#ff0000">(*)</font></b></td>
				<td>
					<form:input type="text" path="title" maxlength = "200" style="width:250px;"/>
					<font color="red"><form:errors path="title"/></font>
				</td>
	      	</tr> 
	      	
	      	<tr>
	      		<td><b>Link</b></td>
				<td>
					<form:select path = "hostId" onchange="xl()"> 
						<form:option  value=""></form:option >
						<c:forEach var="item" items="${linkList}">
							<c:choose>
								<c:when test="${item.id == hostId}">
									<form:option  value="${item.id}" selected="selected">${item.link}</form:option >
								</c:when>
								<c:otherwise>
									<form:option  value="${item.id}">${item.link}</form:option >
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select> 
				</td>
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