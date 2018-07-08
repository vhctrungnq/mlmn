<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:choose>
  <c:when test="${sysMenuAddEdit == 'N'}">
      <title><fmt:message key="sidebar.admin.sysMenuAdd"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.sysMenuAdd"/></content>
  </c:when>
  <c:when test="${sysMenuAddEdit == 'Y'}">
      <title><fmt:message key="sidebar.admin.sysMenuEdit"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.sysMenuEdit"/></content>
  </c:when>
  <c:otherwise>
      
  </c:otherwise>
</c:choose>      
<div class="body-content"></div>
<form:form commandName="sysMenuForm" name="checkform" method="post" action="form.htm"> 
	<div>
		<form:hidden path="id"/>
	</div>
    <table class="simple2"> 
      <tr>
           <td class="wid15 mwid110">
           		<fmt:message key="qLyMenu.menuCon"/>&nbsp;<font color="red">(*)</font>
           	</td>
           <td class="wid35">
                    <form:input path="menuName" maxlength="240" cssClass="wid90"/>&nbsp;<form:errors path="menuName" cssClass="error"/>
           </td>
           <td class="wid15 mwid110">
           		<fmt:message key="qLPhongBan.heThong"/>&nbsp;<font color="red">(*)</font>
          	</td>
           	<td>
          			<select name="system" id="selectSystem" class="wid50">
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
      	<td>
      		<fmt:message key="qLyMenu.isParent"/>
      	</td>
      	<td>
		      <form:checkbox id="isParentChanged" style="float:left" path="isParent" value="Y" />
		      &nbsp;<span id="selectIdHasChanged" style="display:none">
		           	<select name="idHas" id="selectSysMenu" class="wid50">
		          			<c:forEach var="items" items="${sysMenuList}">
				              <c:choose>
				                <c:when test="${items.id == idSysMenuCBB}">
				                    <option value="${items.id}" selected="selected">${items.menuName}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.id}">${items.menuName}</option>
				                </c:otherwise>
				              </c:choose>
						    </c:forEach>
					</select>
				</span>  
        </td>
        <td>
        	<fmt:message key="qLyMenu.ordering"/>
        </td>
        <td>
        	<form:input path="ordering" maxlength="4" cssClass="wid20"/>&nbsp;<form:errors path="ordering" cssClass="error"/>
        </td> 
      </tr>
      <tr>
        <td>
        	<fmt:message key="qLyMenu.appCode"/>
        </td>
        <td colspan="3">
        	<form:input path="appCode" maxlength="240" cssClass="wid80"/>&nbsp;<span style="color: #0560A6;">(định dạng: .../...)</span>
        </td> 
      </tr>
       <tr>
           <td></td>
           <td colspan="3">
               <input class="button" type="submit" class="button" name="save" value="<fmt:message key="global.form.luulai"/>" />
               <input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="danh-sach.htm"'>
           </td>
       </tr>
    </table>

</form:form>

<script type="text/javascript">

$(function() {
	if (!$('#isParentChanged').is(':checked')) {
    	$("#selectIdHasChanged").show();
    }
    else{
    	$("#selectIdHasChanged").hide();
    }
});

$('#isParentChanged').click(function() {
    if (!$(this).is(':checked')) {
    	$("#selectIdHasChanged").show();
    }
    else{
    	$("#selectIdHasChanged").hide();
    }
});

$('#selectSystem').change(function(){

	$.ajax({
	  url: "${pageContext.request.contextPath}/admin/menu-management/ajax/loadsysMenuBySystem.htm",
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
	  data:{system: $('#selectSystem').val()}}).done(function( j ) {
		  var options = '';
		  for (var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i].id + '">' + j[i].menuName+ '</option>';
			}
		$("#selectSysMenu").html(options);
		$('#selectSysMenu option:first').attr('selected', 'selected');
	    
	  });
	  
	
});

function focusIt()
{
	var orderingError = '<c:out value="${orderingError}"/>';
	if(document.checkform.menuName.value==""){
		  var mytext = document.getElementById("menuName");
		  mytext.focus();
		}
	else if (orderingError != "")
		{
		var mytext = document.getElementById("ordering");
		  mytext.focus();
		}
		else
			{
			var mytext = document.getElementById("appCode");
			  mytext.focus();
			}
}

onload = focusIt;

</script>

