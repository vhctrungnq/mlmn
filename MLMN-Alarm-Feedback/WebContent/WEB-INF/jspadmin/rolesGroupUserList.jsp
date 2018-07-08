<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>${titleSystem}</title>
<content tag="heading">${titleSystem}</content>

<div class="body-content"></div>
<div class="loadCBBPhongBan">
		<fmt:message key="pQuyenNhomNguoiDung.chonHeThong"/>&nbsp;
	          			<select name="system" class="widthCBB" id="selectSystem">
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
</div>
<div id="result" style="padding-top:5px;">
		<div id="phanQuyenNguoiDungLeft" class="phanQuyenNguoiDungLeft">
			<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
				<tr>
					<td id="tableLeft">
						<div style="overflow-y: auto; overflow-x: hidden; max-height: 500px;">
							<display:table name="${sysGroupUserList}" class="simple2" id="item" requestURI="">
								<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item_rowNum}"/> </display:column>
								<display:column media="html" titleKey="qLNhomNguoiDung.tenNhom">
									<a id="${item.id}" class="link" href="danh-sach.htm?groupID=${item.id}&system=${systemCBB}">${item.groupName}</a>
								</display:column>	
							</display:table>
						</div>
					</td>
				</tr>
			</table>
		</div>
	
	<div class="phanQuyenNguoiDungRight">
		<form:form name="displ" method="post" action="danh-sach.htm">
			<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
				<tr>
					<td>
						<div style="overflow-y: auto; overflow-x: hidden; max-height: 500px;">
							<display:table name="${sysResponsibilitiesList}" class="simple2" id="item1" requestURI="" >
								<%-- <display:column property="menuCha" titleKey="pQuyenNhomNguoiDung.nhomChucNang" media="html" total="true" group="1" style="border:0;border-left:1px solid #ddd;"/> --%>					
								<display:column property="stt" titleKey="qLNguoiDung.level" class="LEVEL_MENU centerColumnMana"/>
								<display:column property="menuCon" titleKey="pQuyenNhomNguoiDung.tenChucNang"/>
								
								<display:column class="centerColumnMana" style="width:80px;" title="<input type='checkbox' name='selectAllCheck' onClick='javascript:funcSelectAll()' value='Select All' />" media="html">
									<c:choose>
										<c:when test="${item1.checked == 1}">
											<input class="selectall" type="checkbox" name="lang" value="${item1.id}" checked="checked"/>
										</c:when>
										<c:otherwise>
											<input class="selectall" type="checkbox" name="lang" value="${item1.id}"/>
										</c:otherwise>
									</c:choose>
	          					</display:column>
							</display:table>
						</div>
					</td>
				</tr>
			</table>
			<c:choose>
			<c:when test="${not empty groupID}">
				<div class="paddingTop10" align="right">
					<input class="button" type="button" id="capQuyen" name="capQuyen" value="<fmt:message key="global.form.capQuyenTruyCap"/>" />
				</div>
			</c:when>
				<c:otherwise></c:otherwise>
			</c:choose>
	</form:form>
	</div>
</div>
<style type="text/css">
.link{
	text-decoration: none;
}
.link:hover {
	text-decoration: underline;
}
</style>
<script type="text/javascript">

	$(document).ready(function(){
		$('#' + '<c:out value="${groupID}"/>').addClass("selected");
	});
	
	$('#capQuyen').click(function(){
		var val = [];
		var checkedList = "";
		var uncheckedList = "";
		
		
		$(':checkbox').each(function(i){
			if($(this).is(':checked'))
			{
				if($(this).val() != "Select All" && $(this).val() != "on"){
				checkedList += $(this).val() + "-";}
			}
			else
			{
				if($(this).val() != "Select All" && $(this).val() != "on"){
				uncheckedList += $(this).val() + "-";
				}
			}
			
		});

		$.getJSON("${pageContext.request.contextPath}/admin/phan-quyen-nguoi-dung/ajax/checkedList.htm", {checkedList : checkedList, uncheckedList: uncheckedList}, function(j){

			var groupID = '<c:out value = "${param.groupID}" />';
			var system = $('#selectSystem ').val();
			if(groupID != "")
				window.location = '${pageContext.request.contextPath}/admin/phan-quyen-nguoi-dung/danh-sach.htm?groupID=' + groupID + '&system=' + system;
			else
				window.location = '${pageContext.request.contextPath}/admin/phan-quyen-nguoi-dung/danh-sach.htm';
			
		});
	});

	function funcSelectAll()
	{
	   if(document.forms[0].selectAllCheck.checked==true)
	   {
	            for (var a=0; a < document.forms[0].lang.length; a++){
	                 document.displ.lang[a].checked = true;            
	           }
	     }
	     else
	     {
	           for (var a=0; a < document.forms[0].lang.length; a++){
	                  document.displ.lang[a].checked = false;          
	           }
	     }          
	
	}

	$('#selectSystem').change(function(){

		var toLoad = 'danh-sach.htm?system=' + $('#selectSystem').val() + ' #result';
		$('#load').remove();
		$('.body-content').append('<span id="load">LOADING...</span>');

		$('#load').fadeIn('normal', loadContent);

		function loadContent() {
			$('#result').load(toLoad, '', showNewContent());
		}
		
		function showNewContent() {
			$('#result').show('normal', hideLoader());
		}
		
		function hideLoader() {
			$('#load').fadeOut('normal');
		}
		
		return false;
		
		
	});

	$(function() {
		${highlight}
	});
</script>