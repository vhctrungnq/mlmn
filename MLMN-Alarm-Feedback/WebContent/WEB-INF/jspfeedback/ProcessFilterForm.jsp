<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<title>${titleF}</title>
<content tag="heading">${titleF}</content>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/ckeditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/sample.js"></script>
<form:form commandName="fbProcess" name="checkform" id="myform" method="post" action="form.htm"> 
	<form:hidden path="id"/>
	<table class="simple2"> 
      <tr>
          <td style="width: 120px;"><fmt:message key="fbRpProcess.fbType"/><font color="red">(*)</font></td>
          <td style="max-width:600px;">
	    	
    		<select name="fbType" id="fbType" style="width: 700px;height:20px; padding-top: 4px;">
	         		<c:forEach var="item" items="${fbTypeList}">
					<c:choose>
		                <c:when test="${item.code == fbType}">
		                    <option value="${item.code}" selected="selected">${item.name}</option>
		                </c:when>
						<c:otherwise>
							<option value="${item.code}">${item.name}</option>
						</c:otherwise>
					</c:choose>
					</c:forEach>
				</select> 
    	</td>
    	<td style="width:100px; padding-left:5px;">
    		<fmt:message key="fbRpProcess.networkType"/><font color="red">(*)</font>
    	</td>
    	<td>
    		<select name="networkType" id="networkType" style="width: 160px;height:20px; padding-top: 4px;">
	         		<c:forEach var="item" items="${networkTypeList}">
					<c:choose>
		                <c:when test="${item.name == networkType}">
		                    <option value="${item.name}" selected="selected">${item.value}</option>
		                </c:when>
						<c:otherwise>
							<option value="${item.name}">${item.value}</option>
						</c:otherwise>
					</c:choose>
					</c:forEach>
				</select> 
    	</td>
    	</tr>
    	<tr>
          <td><fmt:message key="fbRpProcess.fbContent"/></td>
           	<td colspan="3">
           		<form:textarea path="fbContent" style="font-family:tahoma;width:100%; font-size:12px;height:30px;" name="fbContent" id="fbContent" rows="10" maxlength="3000"/>
           		<script type="text/javascript">
					CKEDITOR.replace( 'fbContent',
					{
						toolbar :
							[
								['NewPage','-','Undo','Redo'],
								['Find','Replace','-','SelectAll','RemoveFormat'],
								['Link', 'Unlink', 'Image'],
								['FontSize', 'Bold', 'Italic','Underline'],
								['NumberedList','BulletedList','-','Blockquote'],
								['TextColor', '-', 'Smiley','SpecialChar', '-', 'Maximize']
							]
					});
			  	</script>
           	</td>
         </tr>
         <tr>
          <td ><fmt:message key="fbRpProcess.causedby"/></td>
           	<td colspan="3">
           		<form:textarea path="causedby" style="font-family:tahoma;width:100%; font-size:12px;height:30px;" name="causedby" id="causedby" rows="10" maxlength="900"/>
           		<script type="text/javascript">
					CKEDITOR.replace( 'causedby',
					{
						toolbar :
							[
								['NewPage','-','Undo','Redo'],
								['Find','Replace','-','SelectAll','RemoveFormat'],
								['Link', 'Unlink', 'Image'],
								['FontSize', 'Bold', 'Italic','Underline'],
								['NumberedList','BulletedList','-','Blockquote'],
								['TextColor', '-', 'Smiley','SpecialChar', '-', 'Maximize']
							]
					});
			  	</script>
           	</td>
         </tr>
         <tr>
          <td><fmt:message key="fbRpProcess.responseContent"/></td>
           	<td colspan="3">
           		<form:textarea path="responseContent" style="font-family:tahoma;width:100%; font-size:12px;height:30px;" name="responseContent" id="responseContent" rows="3000" maxlength="200"/>
           		<script type="text/javascript">
					CKEDITOR.replace( 'responseContent',
					{
						toolbar :
							[
								['NewPage','-','Undo','Redo'],
								['Find','Replace','-','SelectAll','RemoveFormat'],
								['Link', 'Unlink', 'Image'],
								['FontSize', 'Bold', 'Italic','Underline'],
								['NumberedList','BulletedList','-','Blockquote'],
								['TextColor', '-', 'Smiley','SpecialChar', '-', 'Maximize']
							]
					});
			  	</script>
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

<style>
    .error {
    	color: red;
    }
</style> 


