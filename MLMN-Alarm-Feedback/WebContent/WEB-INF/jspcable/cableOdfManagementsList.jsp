<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title><fmt:message key="cableOdfManagements.titleList"/></title>
<content tag="heading"><fmt:message key="cableOdfManagements.titleList"/></content>

<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left">
				<form method="post" action="list.htm<c:if test="${not empty schemaLink || not empty nameLink}">?</c:if><c:if test="${not empty schemaLink}">schemaLink=${schemaLink}</c:if><c:if test="${not empty nameLink && not empty schemaLink}">&</c:if><c:if test="${not empty nameLink}">nameLink=${nameLink}</c:if>">
					<table border="0" cellspacing="1" cellpadding="0" width="100%" >
						<tr>
							<td>
								<input type="hidden" id="delData" name="delData">
							</td>
						</tr>
						<tr> 
							<td class="wid8 mwid70"><fmt:message key="odfLienTang.tenSoDo"/></td>
							<td class="wid20">
								<select id="schemaName" name="schemaName" class="wid90">
									<option value="">--Tất cả--</option>
   									<c:forEach var="items" items="${cableOdfTypesList}">
						              <c:choose>
						                <c:when test="${items.id == schemaName}">
						                    <option value="${items.id}" selected="selected">${items.schemaName}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${items.id}">${items.schemaName}</option>
						                </c:otherwise>
						              </c:choose>
								    </c:forEach>
           						</select>
							</td>
							<td class="wid8 mwid50"><fmt:message key="cableOdfManagements.name"/></td>
							<td class="wid20"><input id="name" name="name" value="${name}" class="wid90"/></td>
							<td class="wid8 mwid50"><fmt:message key="cableOdfManagements.vendor"/></td>
							<td class="wid20"><input id="vendor" name="vendor" value="${vendor}" class="wid90"/></td>
							<td><input class="button" type="submit" class="button" name="filter"
								value="<fmt:message key="global.form.timkiem"/>" /></td>
						</tr>				
					</table>
				</form>
				</td>
				<td></td>
		</tr>
		<c:if test="${isManager=='Y' }">
			 <tr>
				<td></td>
				<td class="wid10 mwid150" align="right">
					<a href="form.htm<c:if test="${not empty schemaLink || not empty nameLink}">?</c:if><c:if test="${not empty schemaLink}">schemaLink=${schemaLink}</c:if><c:if test="${not empty nameLink && not empty schemaLink}">&</c:if><c:if test="${not empty nameLink}">nameLink=${nameLink}</c:if>"><fmt:message key="global.form.themmoi"/></a>&nbsp;
					<a href="upload.htm<c:if test="${not empty schemaLink || not empty nameLink}">?</c:if><c:if test="${not empty schemaLink}">schemaLink=${schemaLink}</c:if><c:if test="${not empty nameLink && not empty schemaLink}">&</c:if><c:if test="${not empty nameLink}">nameLink=${nameLink}</c:if>"><fmt:message key="global.button.import"/></a>&nbsp;
				</td> 
			</tr>
		</c:if>
		
		<tr>
			<td colspan="2">
				<div style="width:100%;overflow:auto; ">
					<display:table name="${cableOdfManList}" class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true" >
						<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item_rowNum}"/> </display:column>
						<display:column style="max-width:150px;word-wrap: break-word;" property="schemaName" titleKey="odfLienTang.tenSoDo" sortable="true" sortName="SCHEMA_NAME"/>
						<display:column class="rightColumnMana" style="max-width:80px;word-wrap: break-word;" property="port" titleKey="cableOdfManagements.viTri" sortable="true" sortName="PORT"/>
						<display:column style="max-width:150px;word-wrap: break-word;" property="name" titleKey="cableOdfManagements.name" sortable="true" sortName="NAME"/>
						<display:column property="vendor" titleKey="cableOdfManagements.vendor" sortable="true" sortName="VENDOR"/>
						<display:column style="max-width:80px;word-wrap: break-word;" property="nameTrangThai" titleKey="cableOdfManagements.isEnable" sortable="true" sortName="NAME_TRANG_THAI"/>
						<display:column class="rightColumnMana" property="ordering" titleKey="cableOdfManagements.ordering" sortable="true" sortName="ORDERING"/>
						<display:column style="max-width:260px;word-wrap: break-word;" property="description" titleKey="cableOdfManagements.description" sortable="true" sortName="DESCRIPTION"/>
						
		    			<c:if test="${isManager=='Y' }">
							<display:column titleKey="global.management" media="html" class="centerColumnMana">
								<a href="form.htm?id=${item.id}<c:if test="${not empty schemaLink}">&schemaLink=${schemaLink}</c:if><c:if test="${not empty nameLink}">&nameLink=${nameLink}</c:if>"><fmt:message key="global.form.sua"/></a>&nbsp;
								<a href="delete.htm?id=${item.id}<c:if test="${not empty schemaLink}">&schemaLink=${schemaLink}</c:if><c:if test="${not empty nameLink}">&nameLink=${nameLink}</c:if>"
										onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')"><fmt:message key="global.form.xoa"/></a>&nbsp;
			    			</display:column>	 
						</c:if>
						<display:setProperty name="export.csv.include_header" value="true" />
						<display:setProperty name="export.excel.include_header" value="true" />
						<display:setProperty name="export.xml.include_header" value="true" />
						<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
						<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
						<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
							
					</display:table>
				</div>
			</td>
		</tr>
</table>

<c:if test="${sizeList > 0}">
	<div class="fr" style="margin-top:-40px">
		<input type="button" id="btnExportAll" name="btnExportAll" value="Xóa tất cả" class="button">
	</div>
</c:if>

<div id="dialog-confirm" title="Thông báo" style="padding: 10px;display:none;">
	<p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
	Export dữ liệu sẽ xóa vào file excel?</p>
</div>

<script type="text/javascript">
function deleteAll() {
	$("#delData").val("1");
	$('form').submit();
}

function loop(myWindow){
	if (myWindow.closed == true) {
		deleteAll();
	} else {
		setTimeout(function(){loop(myWindow);},500);
	}
}

$("#btnExportAll").click(function() {
	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
	if (answer)
	{
		var url = $('span.excel').closest('a').attr('href');
		
		var loc = window.location;
	    var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('.htm') + 4);
		
	    var myWindow = window.open(pathName + url,'_blank','width=200,height=100');

	   // $(this).dialog("close");
	    
	    loop(myWindow); 
	}
});

	function focusIt()
	{
	  var mytext = document.getElementById("name");
	  mytext.focus();
	}

	onload = focusIt;
</script>