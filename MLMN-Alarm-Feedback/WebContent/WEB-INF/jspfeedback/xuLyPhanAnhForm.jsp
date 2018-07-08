<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/ckeditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/sample.js"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/js/editortext/sample.css" />

<title><fmt:message key="title.baoCaoXuLyPA.EditForm"/></title>
<content tag="heading"><fmt:message key="title.baoCaoXuLyPA.EditForm"/></content>

<form:form commandName="xuLyPhanAnhForm" name="checkform" method="post" action="form.htm"> 
	<div>
		<form:hidden path="id"/>
	</div>
    <table class="simple2"> 	
     <tr>
     	<td class="wid15 mwid160">
     		<b><fmt:message key="title.xuLyPhanAnh.nguyenNhan"/></b>&nbsp;<font color="red">(*)</font>
     	</td>
     	<td>
          <form:textarea path="causedby" cssClass="wid100" maxlength="900"/>&nbsp;<form:errors path="causedby" cssClass="error"/>
       	</td>
     </tr>
     <tr>
     	<td>
     		<b><fmt:message key="title.xuLyPhanAnh.cachXuLy"/></b>&nbsp;<font color="red">(*)</font>
     	</td>
     	<td>
          	<%-- <form:textarea path="responseContent" cssClass="textareaCV50" maxlength="1000"/> --%>
          	<textarea rows="10" class="wid98" id="responseContent" name="responseContent" maxlength="900">
          	${xuLyPhanAnhForm.responseContent}
          	</textarea>
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
			</script>&nbsp;<form:errors path="responseContent" cssClass="error"/>
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
	if(document.checkform.causedby.value==""){
		  var mytext = document.getElementById("causedby");
		  mytext.focus();
	}
	else if(document.checkform.responseContent.value=="")
	{
		var mytext = document.getElementById("responseContent");
	  	mytext.focus();
	}
}

onload = focusIt;

</script>