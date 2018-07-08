<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>${titleSystem}</title>
<content tag="heading">${titleSystem}</content>

<form:form name="checkform" method="post" action="danh-sach.htm">
    <table class="simple2"> 
    	
      <tr>
           <td class="wid12 mwid120">
           		<b><fmt:message key="qLNguoiDung.taiKhoan"/></b>
           	</td>
           <td>
                <b><i>${username}</i></b>
           </td>
           
      </tr>
      <tr>
      		<td>
           		<b><fmt:message key="qLNguoiDung.matKhauCu"/></b>&nbsp;<font color="red">(*)</font>
          	</td>
           	<td >
           		<input type="password" id="matKhauCu" name="matKhauCu" value="${matKhauCu}" maxlength="50" class="wid30"/>
           	</td>
      </tr>
      <tr>
        <td>
        	<b><fmt:message key="qLNguoiDung.matKhauMoi"/></b>
        </td> 
      	<td>
      		<input type="password" id="matKhauMoi" name="matKhauMoi" value="${matKhauMoi}" maxlength="50" class="wid30"/>
      	</td>
      </tr>
      <tr>
        <td>
        	<b><fmt:message key="qLNguoiDung.nhapLaiMK"/></b>
        </td> 
      	<td>
      		<input type="password" id="nhapLaiMK" name="nhapLaiMK" value="${nhapLaiMK}" maxlength="50" class="wid30"/>
      	</td>
      </tr>
       <tr>
           <td></td>
           <td>
               <input class="button" type="submit" class="button" name="save" value="<fmt:message key="global.form.luulai"/>" />
               <input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="${pageContext.request.contextPath}/welcome.htm"'>
           </td>
       </tr>
    </table>

</form:form>


<script type="text/javascript">

	function focusIt()
	{
		var passError = '<c:out value="${passError}"/>';
		if(passError != "")
			{
				var mytext = document.getElementById("nhapLaiMK");
			  	mytext.focus();
		  	}
		else
			{
				var mytext = document.getElementById("matKhauCu");
			  	mytext.focus();
		  	}
	  		
	}
	onload = focusIt;

</script>