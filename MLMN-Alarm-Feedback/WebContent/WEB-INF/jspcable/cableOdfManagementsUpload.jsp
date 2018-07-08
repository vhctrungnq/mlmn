<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">   
    #success p { margin: 0; padding: 1em; white-space: nowrap; } 
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>

<title><fmt:message key="sidebar.admin.cableOdfManagementsUpload"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.cableOdfManagementsUpload"/></content>
 	
<form:form method="post" action="upload.htm" enctype="multipart/form-data" >
	<div><input id="schemaLink" name="schemaLink" value="${schemaLink}" type="hidden" />
		<input id="nameLink" name="nameLink" value="${nameLink}" type="hidden" />
	</div>
	<table class="simple2">
		<tr>
			<td><b><fmt:message key="odfLienTang.tenSoDo"/></b>&nbsp;<font color="red">(*)</font></td>
			<td>
				<select id="idOdfTypes" name="idOdfTypes" class="wid30">
					<c:forEach var="items" items="${cableOdfTypesList}">
		              <c:choose>
		                <c:when test="${items.id == idOdfTypesCBB}">
		                    <option value="${items.id}" selected="selected">${items.schemaName}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.id}">${items.schemaName}</option>
		                </c:otherwise>
		              </c:choose>
				    </c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td class="wid10 mwid140"><b><fmt:message key="qLNguoiDung.file"/></b></td>
			<td ><input class="button" type="file" name="file" size="90"/>&nbsp;<input class="button" type="submit" class="button" name="save" value="<fmt:message key="global.button.import"/>"/></td>
		</tr>
		<tr>
				<td>
				<b><fmt:message key="qLNguoiDung.dinhDangFile"/></b>
				</td>
				<td>
					<ul style="list-style-type: none;">
						<li>File import là file (.xls)</li>
						<li>Dữ liệu trong file có dạng: 
							<code>
								&lt;<fmt:message key="cableOdfManagements.viTri"/>&gt;<font color="red">(*)</font>, 
								&lt;<fmt:message key="cableOdfManagements.name"/>&gt;,
								&lt;<fmt:message key="cableOdfManagements.vendor"/>&gt;,  
								&lt;<fmt:message key="cableOdfManagements.isEnable"/>&gt;, 
								&lt;<fmt:message key="cableOdfManagements.ordering"/>&gt;, 
								&lt;<fmt:message key="cableOdfManagements.description"/>&gt;.
							</code>
						</li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="UsersExample" href="${pageContext.request.contextPath}/upload/example/CableOdfManagements.xls">CableOdfManagements.xls</a></li>
						<li>Chú ý:- Những thông tin được đánh dấu <font color="red">(*)</font> là thông tin nhập liệu bắt buộc. Chọn tên sơ đồ cho dữ liệu import</li>
					</ul>
			</td>
		</tr>
	</table>
	
	<c:if test="${status != null}">
    	<div class="error">${status} ${statusExists}</div>
    </c:if>
    <c:if test="${fn:length(failedList) gt 0}">
    	<div id="failed">
    		<div><b>Dữ liệu cáp không hợp lệ  ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="700">
						<display:column class="centerColumnMana" style="width:40px;word-wrap: break-word;" title="STT" > <c:out value="${item1_rowNum}"/> </display:column>
						<display:column property="schemaName" titleKey="odfLienTang.tenSoDo" class="NOT_NULL"/>
						<display:column property="portStr" titleKey="cableOdfManagements.viTri" class="rightColumnMana NOT_NULL"/>
						<display:column property="name" titleKey="cableOdfManagements.name" />
						<display:column property="vendor" titleKey="cableOdfManagements.vendor" />
						<display:column property="isEnableStr" titleKey="cableOdfManagements.isEnable" />
						<display:column class="rightColumnMana" property="orderingStr" titleKey="cableOdfManagements.ordering" />
						<display:column property="description" titleKey="cableOdfManagements.description" />	
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu cáp hợp lệ  ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700">
						<display:column class="centerColumnMana" style="width:40px;word-wrap: break-word;" title="STT" > <c:out value="${item2_rowNum}"/> </display:column>
						<display:column property="schemaName" titleKey="odfLienTang.tenSoDo" class="NOT_NULL"/>
						<display:column property="port" titleKey="cableOdfManagements.viTri" class="rightColumnMana NOT_NULL"/>
						<display:column property="name" titleKey="cableOdfManagements.name" />
						<display:column property="vendor" titleKey="cableOdfManagements.vendor" />
						<display:column property="isEnableStr" titleKey="cableOdfManagements.isEnable" />
						<display:column class="rightColumnMana" property="ordering" titleKey="cableOdfManagements.ordering" />
						<display:column property="description" titleKey="cableOdfManagements.description" />	
				</display:table>
			</div>
		</div>
	</c:if>
	<table>
		<tr>
			<td>
               	<input class="button" type="button" value="<fmt:message key="global.button.back"/>" onClick='window.location="list.htm<c:if test="${not empty schemaLink || not empty nameLink}">?</c:if><c:if test="${not empty schemaLink}">schemaLink=${schemaLink}</c:if><c:if test="${not empty nameLink && not empty schemaLink}">&</c:if><c:if test="${not empty nameLink}">nameLink=${nameLink}</c:if>"'>
			</td>
		</tr>
	</table>
</form:form>
<script type="text/javascript">  
    $(function() {
    	$('#item2>tbody>tr').each(
    	    	 function(){
   					  ${highlight}
   						});

    	$('#item1>tbody>tr').each(
   	    	 function(){
  					  ${highlight}
  					});
		}); 
</script>