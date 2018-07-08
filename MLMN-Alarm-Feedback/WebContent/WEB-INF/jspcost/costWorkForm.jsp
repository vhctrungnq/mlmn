<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>${titleF}</title>
<content tag="heading">${titleF}</content> 
<style>
.note{color:red; float:right;padding-right: 7px;}
</style>

<form:form method="post" commandName="costWork" action="form.htm">
	<input type="hidden" name="oldSuper" id="oldSuper" value="${oldSuper}">
	<div>
		<form:hidden path="id"/>
		<form:hidden path="rootNo"/>
	</div>
	<table class="simple2" >
		
		<tr>
			<td style="width:120px; "><fmt:message key="costWork.expensesCode"/><span class="note">(*)</span></td>
			<td  style="width:38%; ">		
				<select name="expensesCode" id="expensesCode" style="width: 100%;height:20px; padding-top: 4px;">
					
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
				</select> 
				<form:errors path="expensesCode" class="error"/>
			</td>
			
			<td style="width:120px;padding-left: 10px; "><fmt:message key="costWork.workSuper"/></td>
			<td >		
				<select name="workSuper" id="workSuper" style="width: 100%;height:20px; padding-top: 4px;">
					<option value="0"><fmt:message key="costWork.super"/></option>
	           		<c:forEach var="item" items="${workSuperList}">
						<c:choose>
			                <c:when test="${item.id == costWork.workSuper}">
			                    <option value="${item.id}" selected="selected">${item.workName}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.id}">${item.workName}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> 
				<form:errors path="workSuper" class="error"/>
			</td>
			</tr>
			<tr>
			<td style="width:120px;"><fmt:message key="costWork.workName"/><span class="note">(*)</span></td>
			<td colspan="3">
			 	<form:input type ="text" path="workName" style="width:99%;" maxlength="300"/>
				<form:errors path="workName" class="error"/>
     		</td>
			
		</tr>
		
		<tr>
			<td>
				   	<fmt:message key="costWork.isEnable"/>
		    </td>
			<td>
				<select name="isEnable" id="isEnable" >
	           		<c:forEach var="item" items="${isEnableList}">
						<c:choose>
			                <c:when test="${item.name == costWork.isEnable}">
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
			     	<fmt:message key="costWork.ordering"/>		
			</td>
			<td>
				<form:input type ="text" path="ordering"  maxlength="10"/>
				<form:errors path="ordering" class="error"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="costWork.workContent"/></td>
			<td colspan="3">
				<form:textarea path="workContent" style="height: 50px" class="wid100" maxlength="300"/>
			</td>
		</tr>
		<tr>
			<td></td>
			<td colspan="5">
				<input type="submit" class="button" id="save"  value="<fmt:message key="global.form.luulai"/>" />
               	<input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="<%=request.getContextPath() %>/cost/costWork/list.htm"'>
			</td>
		</tr>
	</table>
</form:form>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript">
$(function() {
     $("#expensesCode").change(function(){
		if ($("#expensesSupper").val() != '' && $("#expensesCode").val() != null) {

			$.getJSON("${pageContext.request.contextPath}/cost/costWork/ajax/getWorkSuperList.htm",{expensesCode: $(this).val()}, function(j){
				var options = '<option value="0"><fmt:message key="costWork.super"/></option>';
				for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].id + '">' + j[i].workName+ '</option>';
				}
				$("#workSuper").html(options);
			});

		}
 	});
 }); 	
</script>