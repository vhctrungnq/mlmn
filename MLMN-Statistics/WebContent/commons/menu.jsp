<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<div class="fl">
     <ul id="navmenu-h">
		   ${globalData.menuItem}
	</ul>
	</div>
    <c:if test="${not empty globalUser}">
	     <div class="fr">
		     <span class="username">
			      <b>${globalUser.username}
			      (${globalUser.maPhong})</b></span>
		     <span class="logout pl2">
		      	<a href="${pageContext.request.contextPath}/logout" title="user">Thoát</a>
		     </span>
	  	</div>
    </c:if>

<script type="text/javascript">
$(document).ready(function() {
  $('.links li code').hide();  
  $('.links li p').click(function() {
    $(this).next().slideToggle('fast');
  });
});
</script>
