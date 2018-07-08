<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>${titleF}</title>
<content tag="heading">${titleF}</content> 
<style>
.note{color:red; float:right;padding-right: 7px;}
</style>

<form:form method="post" commandName="costExpenses" action="form.htm">
	
	<div>
		<form:hidden path="id"/>
		<form:hidden path="rootNo"/>
		<input type="hidden" id="expensesSuperName" name="expensesSuperName" value="${expensesSuperName}"/>
	</div>
	<table class="simple2" >
		
		<tr>
			<td style="width:120px; "><fmt:message key="costExpenses.expensesSuper"/></td>
			<td >	
				<c:choose>
	                <c:when test="${empty costExpenses.id}">
	                	<select name="expensesSuper" id="expensesSuper" style="width: 95%;height:20px; padding-top: 4px;">
							<option value="0"><fmt:message key="global.supper"/></option>
			           		<c:forEach var="item" items="${expensesSuperList}">
								<c:choose>
					                <c:when test="${item.expensesCode == expensesSuper}">
					                    <option value="${item.expensesCode}" selected="selected">${item.expensesName}</option>
					                </c:when>
									<c:otherwise>
										<option value="${item.expensesCode}">${item.expensesName}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select> 
	                </c:when>
	                <c:otherwise>
	                    <b><i>${expensesSuperName}</i></b><form:hidden path="expensesSuper" />
	                </c:otherwise>
	            </c:choose>  	
				
			</td>
			<td style="width:120px;padding-left: 10px;"><fmt:message key="costExpenses.expensesCode"/><span class="note">(*)</span></td>
			<td colspan="3">
				<c:choose>
	                <c:when test="${empty costExpenses.id}">
	                	<form:input type ="text" path="expensesCode" style="width:60%;" maxlength="20"/>
						<form:errors path="expensesCode" class="error"/>
	                </c:when>
	                <c:otherwise>
	                    <b><i>${costExpenses.expensesCode}</i></b><form:hidden path="expensesCode" />
	                </c:otherwise>
	            </c:choose>  
				
			</td>
			
		</tr>
		
		<tr>
			<%-- <td><fmt:message key="costExpenses.workId"/></td>
			<td>
				<form:input type ="text" path="workId" class="wid100 detail" maxlength="50"/>
			</td>
			--%>
			<td style="width:120px; "><fmt:message key="costExpenses.expensesName"/> <span class="note">(*)</span> </td>
			<td>
				<form:input type ="text" path="expensesName" maxlength="200" style="width:95%;"/>
				<form:errors path="expensesName" class="error"/>
			</td>
			<td>
				   	<fmt:message key="costExpenses.isEnable"/>
		    </td>
			<td>
				<select name="isEnable" id="isEnable" >
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
			</td>
			<td style="width:70px; padding-left: 10px;">
			     	<fmt:message key="costExpenses.ordering"/>		
			</td>
			<td>
				<form:input type ="text" path="ordering"  maxlength="10"/>
				<form:errors path="ordering" class="error"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="costExpenses.description"/></td>
			<td colspan="5">
				<form:textarea path="description" style="height: 50px" class="wid100" maxlength="400"/>
			</td>
		</tr>
		<tr>
			<td></td>
			<td colspan="5">
				<input type="submit" class="button" id="save"  value="<fmt:message key="global.form.luulai"/>" />
               	<input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="<%=request.getContextPath() %>/cost/costExpenses/list.htm"'>
			</td>
		</tr>
	</table>
</form:form>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.7.2.js"></script>
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


$("#expensesCode").autocomplete({
	source: '${pageContext.request.contextPath}/cost/costExpenses/ajax/get-costExpenses-inf-search.htm?focus=expensesCode',
});

$("#expensesName").autocomplete({
	source: '${pageContext.request.contextPath}/cost/costExpenses/ajax/get-costExpenses-inf-search.htm?focus=expensesName',
});

</script>