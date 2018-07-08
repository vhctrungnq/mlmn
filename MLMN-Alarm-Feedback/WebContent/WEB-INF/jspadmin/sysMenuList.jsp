<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>${titleSystem}</title>
<content tag="heading">${titleSystem}</content>

<div class="body-content"></div>
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left"><form:form commandName="filter" method="post" action="danh-sach.htm">
					<table border="0" cellspacing="1" cellpadding="0" width="100%">
						<tr>
				        	<td class="wid8 mwid60"><fmt:message key="qLyMenu.heThong"/></td>
							<td class="wid20">
			           			<select id="selectSystem" name="system" class="wid90">
			           				<option value="">--Tất cả--</option>
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
							<td class="wid10 mwid80"><fmt:message key="qLyMenu.menuCon"/></td>
							<td class="wid20"><form:input path="menuName" cssClass="wid90" /></td>
							
							<td><input class="button" type="submit" name="filter"
								value="<fmt:message key="global.form.timkiem"/>" /></td>
						</tr>				
					</table>
				</form:form>
				</td>
				<td>
				</td> 
		</tr>
		<tr>
			<td></td>
			<td class="wid10 mwid70" align="right">
				<a href="form.htm"><fmt:message key="global.form.themmoi"/></a>&nbsp;
				</td> 
		</tr>
		<tr>
			<td colspan="2">
				
				<div style="width:100%;overflow:auto; ">
					<display:table name="${sysMenuList}" class="simple2" id="item" requestURI="" pagesize="50" export="true">
						<display:column property="stt" titleKey="qLNguoiDung.level" class="LEVEL_MENU centerColumnMana"/>
						<display:column property="menuCon" titleKey="qLyMenu.menuCon" />
						<display:column class="centerColumnMana" property="ordering" titleKey="qLyMenu.ordering" />
						<display:column property="appCode" titleKey="qLyMenu.appCode" />
						<display:column property="name" titleKey="qLyMenu.heThong" />
						
						<display:column titleKey="global.management" media="html" class="centerColumnMana">
							<a href="form.htm?id=${item.id}"><fmt:message key="global.form.sua"/></a>&nbsp;
		    				<a href="delete.htm?id=${item.id}"
									onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')"><fmt:message key="global.form.xoa"/></a>&nbsp;
		    			</display:column>
		    			
						<display:setProperty name="export.csv.include_header" value="true" />
						<display:setProperty name="export.excel.include_header" value="true" />
						<display:setProperty name="export.xml.include_header" value="true" />
						<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
						<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
						<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
							
					</display:table>
				</div>
				
			</td>
			<td></td>
		</tr>
</table>


<table border="0" cellspacing="1" cellpadding="0" width="100%">
 <tr>
	<td class="wid15 mwid150"><fmt:message key="qLyMenu.copyMenuFromSystem"/></td>
	<td class="wid15">
			<select name="systemCopyFrom" id="systemCopyFrom" class="wid90">
     				<c:forEach var="items" items="${sysMenuSystemDistinct}">
			            <c:choose>
			              <c:when test="${items.system == systemCopyFromCBB}">
			                  <option value="${items.system}" selected="selected">${items.name}</option>
			              </c:when>
			              <c:otherwise>
			                  <option value="${items.system}">${items.name}</option>
			              </c:otherwise>
			            </c:choose>
    				</c:forEach>
 			</select>
 	</td>
	<td class="wid10 mwid90"><fmt:message key="qLyMenu.copyMenuToSystem"/></td>	
	<td class="wid15">
		<select name="systemCopyTo" id="systemCopyTo" class="wid90">
	     	<c:forEach var="items" items="${systemNotInSysMenu}">
	            <c:choose>
	              <c:when test="${items.value == systemCopyFromCBB}">
	                  <option value="${items.value}" selected="selected">${items.name}</option>
	              </c:when>
	              <c:otherwise>
	                  <option value="${items.value}">${items.name}</option>
	              </c:otherwise>
	            </c:choose>
    		</c:forEach>
		</select>
	</td>
	<td>
		<input class="button" type="button" id="copy" name="copy" value="<fmt:message key="global.form.copy"/>" />
	</td>
</tr>
</table>
		
<!-- <script type="text/javascript">
	$('#selectSystem ').change(function(){
	
		$.ajax({
		  url: "${pageContext.request.contextPath}/admin/menu-management/ajax/loadMenuCha.htm",
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
		  data:{system: $('#selectSystem ').val()}}).done(function( j ) {
			  var options = '';
			  options += '<option value="">--Tất cả--</option>';
			  for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].idMenuCha + '">' + j[i].menuCha+ '</option>';
				}
			$("#menuCha").html(options);
			$('#menuCha option:first').attr('selected', 'selected');
		    
		  });
		  
		
	});

</script> -->
<script type="text/javascript">
$(function() {
	${highlight}				
  	});   
  	
	$('#copy').click(function(){

		$.getJSON("${pageContext.request.contextPath}/admin/menu-management/ajax/copySystem.htm", {systemFrom : $('#systemCopyFrom').val(), systemTo: $('#systemCopyTo').val()}, function(j){

			window.location = '${pageContext.request.contextPath}/admin/menu-management/danh-sach.htm';
			
		});	
	});
	
	function focusIt()
	{
	  var mytext = document.getElementById("menuName");
	  mytext.focus();
	}
	onload = focusIt;
</script>
