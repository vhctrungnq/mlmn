
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title>${title}</title>
<content tag="heading">${title}</content> 	
<style>

.dpnone {
	display: none;
}

</style>

<div>
<form:form commandName="costExpenses" method="POST" action="list.htm">
	<input type="hidden" name="highlight" id="highlight" value="${highlight}">
	<input type="hidden" name="highlight1" id="highlight1" value="${highlight1}">
	<table>
		<tr>
			<td style="width: 100px;"><fmt:message key="costExpenses.expensesSuper"/></td>
			
			<td>
				<form:select path="expensesCode" style="width: 370px;height:20px; padding-top: 4px;">
					<option value=""><fmt:message key="global.Chosse"/></option>
	           		<c:forEach var="item" items="${expensesSuperList}">
						<c:choose>
			                <c:when test="${item.expensesCode == costExpenses.expensesCode}">
			                    <option value="${item.expensesCode}" selected="selected">${item.expensesName}</option>
			                </c:when>
							<c:otherwise>	
								<option value="${item.expensesCode}">${item.expensesName}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select> 
			</td>
			
			<td style="padding-left: 5px;width: 100px;"><fmt:message key="costExpenses.expensesName"/></td>
			<td>
				<form:input path="expensesName" width="80px" size="25"/>
			</td>
			<td  style="padding-left: 5px;width: 70px;"><fmt:message key="costExpenses.isEnable"/></td>
			<td>
				<form:select path="isEnable"  style="width: 170px;height:20px; padding-top: 4px;">
				<option value=""><fmt:message key="global.All"/></option>
           		<c:forEach var="item" items="${isEnableList}">
					<c:choose>
		                <c:when test="${item.name == costExpenses.isEnable}">
		                    <option value="${item.name}" selected="selected">${item.value}</option>
		                </c:when>
						<c:otherwise>
							<option value="${item.name}">${item.value}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</form:select> 
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
	<display:table name="${expensesList}"  class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:20px;"> <c:out value="${item_rowNum}"/> </display:column>
		<display:column property="rootNo"  class="dpnone ROOT_NO" headerClass="dpnone" media="html"/>
		<display:column property="expensesSuper" titleKey="costExpenses.expensesSuper"  class="dpnone EXPENSES_SUPER" headerClass="dpnone"/>
	  	<display:column property="expensesCode" titleKey="costExpenses.expensesCode" sortable="true" sortName="EXPENSES_CODE" style="max-width:100px;" />
	  	<%--<display:column property="workId"  titleKey="costExpenses.workId" sortable="true" sortName="WORK_ID" style="max-width:100px;"/> --%>
		<display:column property="expensesName" titleKey="costExpenses.expensesName" sortable="true" sortName="EXPENSES_NAME" style="min-width:400px;"/>
		<display:column property="description" titleKey="costExpenses.description" sortable="true" sortName="DESCRIPTION" style="min-width:400px;"/>
		<display:column property="isEnable" titleKey="costExpenses.isEnable" sortable="true" sortName="IS_ENABLE" style="max-width:60px;"/>
		<display:column property="ordering" titleKey="costExpenses.ordering" sortable="true" sortName="ORDERING" style="max-width:60px;"/>
		<display:column titleKey="global.management" media="html" class="centerColumnMana" style="min-width: 70px;">
			<a href="form.htm?id=${item.id }" title="${item.id}"><fmt:message key="global.form.sua"/></a>&nbsp;
			
 			<a href="#" onclick="javascript:DeleteCostExpenses('${item.expensesSuper}','${item.expensesCode}','${item.id}');return false;"><fmt:message key="global.form.xoa"/></a>
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
function DeleteCostExpenses(expensesSuper,expensesCode,id) {

	var r = false;
	$.getJSON("${pageContext.request.contextPath}/cost/costExpenses/ajax/checkBeforeDalete.htm", {expensesCode: expensesCode}, function(j){
		if (j==1)
			{
				r = confirm('Khoản mục đang có ràng buộc nên không thể xóa');
				
			}
		else
			{
				r = confirm('<fmt:message key="messsage.confirm.delete"/>');
				if (r == true) {
					$.getJSON("${pageContext.request.contextPath}/cost/costExpenses/ajax/deleteCostExpenses.htm", {id: id,expensesSuper:expensesSuper}, function(i){
						$("#costExpenses").submit();
					});
				}
			}
		
	});
}
$("#expensesCode").autocomplete({
	source: '${pageContext.request.contextPath}/cost/costExpenses/ajax/get-costExpenses-inf-search.htm?focus=expensesCode',
});

$("#expensesName").autocomplete({
	source: '${pageContext.request.contextPath}/cost/costExpenses/ajax/get-costExpenses-inf-search.htm?focus=expensesName',
});


</script>