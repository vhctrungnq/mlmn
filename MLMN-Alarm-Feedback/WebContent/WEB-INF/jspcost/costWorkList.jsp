
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title>${title}</title>
<content tag="heading">${title}</content> 	
<style>

.dpnone {
	display: none;
}

</style>
<%
        // you can do this as a scriptlet on the page, but i put it into a taglib...
        org.displaytag.decorator.MultilevelTotalTableDecorator subtotals = new org.displaytag.decorator.MultilevelTotalTableDecorator();
        subtotals.setGrandTotalDescription("&nbsp;");    // optional, defaults to Grand Total
        subtotals.setSubtotalLabel("&nbsp;", null);
        pageContext.getRequest().setAttribute("subtotaler", subtotals);
%>
<div>
<form:form commandName="costWork" method="POST" action="list.htm">
	<input type="hidden" name="highlight" id="highlight" value="${highlight}">
	
	<div>
		<form:hidden path="id"/>
		<form:hidden path="rootNo"/>
	</div>
	<table>
		<tr>
			<td style="width: 100px;"><fmt:message key="costWork.expensesCode"/></td>
			<td>
				<form:select path="expensesCode" style="width: 500px;height:20px; padding-top: 4px;">
					<option value=""><fmt:message key="global.All"/></option>
	           		<c:forEach var="item" items="${expensesCodeList}">
						<c:choose>
			                <c:when test="${item.expensesCode == costWork.expensesCode}">
			                    <option value="${item.expensesCode}" selected="selected">${item.expensesName}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.expensesCode}">${item.expensesName}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select>
			</td>
			<td style="padding-left: 5px;width: 100px;"><fmt:message key="costWork.workName"/></td>
			<td>
				<form:input path="workName" width="80px" size="25"/>
			</td>
			<td  style="padding-left: 5px;width: 70px;"><fmt:message key="costWork.isEnable"/></td>
			<td>
				<select name="isEnable" id="isEnable" style="width: 170px;height:20px; padding-top: 4px;">
				<option value=""><fmt:message key="global.All"/></option>
           		<c:forEach var="item" items="${isEnableList}">
					<c:choose>
		                <c:when test="${item.name == isEnable}">
		                    <option value="${item.name}" selected="selected">${item.value}</option>
		                </c:when>
						<c:otherwise>
							<option value="${item.name}">${item.value}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> 
			<input class="button" type="submit" class="button" 
								value="<fmt:message key="button.search"/>" />
			</td>
			</tr>
	</table>
</form:form>
</div>
<div class="ftsize12" align="right"> 
	<span><a href="form.htm" title="Thêm mới">Thêm mới</a></span> - 
	<span><a href="upload.htm" title="Import">Import file</a></span> 
</div>
  <div id="doublescroll" >
  
	<display:table name="${workList}"  class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:20px;"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="rootNo"  class="dpnone ROOT_NO" headerClass="dpnone" media="html"/>
		<display:column property="expensesCode" titleKey="costWork.expensesCode"  class="dpnone" headerClass="dpnone" media="html"/>
		<display:column property="workSuper" titleKey="costWork.workSuper"  class="dpnone WORK_SUPER" headerClass="dpnone"/>
	  	<display:column property="expensesName" titleKey="costWork.expensesName" sortable="true" sortName="EXPENSES_NAME"  group="1" />
	  	<display:column property="workName" titleKey="costWork.workName" sortable="true" sortName="WORK_NAME" style="max-width:100px;" />
	  	<display:column property="workContent" titleKey="costWork.workContent" sortable="true" sortName="WORK_CONTENT" style="min-width:400px;"/>
		<display:column property="isEnable" titleKey="costWork.isEnable" sortable="true" sortName="IS_ENABLE" style="max-width:60px;"/>
		<display:column property="ordering" titleKey="costWork.ordering" sortable="true" sortName="ORDERING" style="max-width:60px;"/>
		<display:column titleKey="global.management" media="html" class="centerColumnMana" style="min-width: 70px;">
			<a href="form.htm?id=${item.id }" title="${item.id}"><fmt:message key="global.form.sua"/></a>&nbsp;
			
 			<a href="#" onclick="javascript:DeletecostWork('${item.workSuper}','${item.expensesCode}','${item.id}');return false;"><fmt:message key="global.form.xoa"/></a>
 		</display:column>
	   
	   	<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />

	</display:table>
</div>

<script type="text/javascript">
	function focusIt()
	{
	  var mytext = document.getElementById("expensesCode");
	  mytext.focus();
	}
	onload = focusIt;
	
	

</script>
<script type="text/javascript">
$(function() {
	${highlight}				
  	});   
  	</script>
  
  <script type="text/javascript">
function DeletecostWork(workSuper,expensesCode,id) {

	var r = false;
	$.getJSON("${pageContext.request.contextPath}/cost/costWork/ajax/checkBeforeDelete.htm", {id: id}, function(j){
		if (j==1)
			{
				r = confirm('Khoản mục có khoản mục con hoặc đang dùng bên  kế hoạch chi phí chi tiết');
				
			}
		else
			{
				r = confirm('<fmt:message key="messsage.confirm.delete"/>');
				if (r == true) {
					$.getJSON("${pageContext.request.contextPath}/cost/costWork/ajax/deletecostWork.htm", {id: id,workSuper: workSuper}, function(i){
						$("#costWork").submit();
					});
				}
			}
		
	});
}



</script>