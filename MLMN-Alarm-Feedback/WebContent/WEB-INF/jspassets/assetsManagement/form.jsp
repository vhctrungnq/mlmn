<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
	.wid40 {width:40%;}
	.wid50 {width:50%;}
	.wid100 {width:100%;}
	td {padding: 1px 0;}
	.error {color:red;}
	.editbox {display:none}
	.editbox {
		background-color: #FAFAFA;
	    border: 1px solid #DDDDDD;
	    border-radius: 2px 2px 2px 2px;
	    box-shadow: 1px 0 1px rgba(0, 0, 0, 0.1) inset;
	    font-size: 14px;
	    padding: 4px;
	    width: 270px;
	}
	.edit_td, .delete_td {width: 16px;cursor:pointer;}
	.note{color:red; float:right;padding-right: 7px;}
</style>
<title>${titleF}</title>
<body class="section-5"/>
<content tag="heading">${titleF}</content>

<form:form commandName="assetsManagement" method="post" action="form.htm">
<div><form:input path="id" type="hidden" /></div>
    <table class="simple2">  
        <tr>
			<td><fmt:message key="assetsManagement.asTypesName" /><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td>
				<form:select path ="asTypesId" style="width:150px;" onchange="xl()"> 
	           		<c:forEach var="item" items="${asTypesNameList}">
						<c:choose>
			                <c:when test="${item.asTypesId == asTypesId}">
			                    <form:option value="${item.asTypesId}" selected="selected">${item.asTypesName}</form:option>
			                </c:when>
							<c:otherwise>
								<form:option value="${item.asTypesId}">${item.asTypesName}</form:option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select> 
			</td>
		</tr> 
		
		<tr>
			<td><fmt:message key="assetsManagement.productCode"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td>
				<form:input type ="text" path="productCode" class="wid50" maxlength="40"/>
				<br/><form:errors path="productCode" class="error"/>
			</td>
		</tr> 
		
		<tr>
			<td><fmt:message key="assetsManagement.assetsName"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td>
				<form:input type ="text" path="assetsName" class="wid50" maxlength="245"/>
				<br/><form:errors path="assetsName" class="error"/>
			</td>
		</tr> 
		
		<%-- <tr>
			<td><fmt:message key="assetsManagement.serialNo"/></td>
			<td>
				<form:input type ="text" path="serialNo" class="wid50" maxlength="40"/>
				<br/><form:errors path="serialNo" class="error"/>
			</td>
		</tr>   --%>
		
		<tr>
			<td><fmt:message key="assetsManagement.unit" /></td>
			<td>
				<form:select path ="unit" name = "unit" id="unit" style="width:100px;" onchange="xl()"> 
	           		<c:forEach var="item" items="${unitList}">
						<c:choose>
			                <c:when test="${item.name == unit}">
			                    <form:option value="${item.name}" selected="selected">${item.value}</form:option>
			                </c:when>
							<c:otherwise>
								<form:option value="${item.name}">${item.value}</form:option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select> 
			</td>
		</tr>  
		
		<tr>
			<td><fmt:message key="assetsManagement.ordering"/></td>
			<td>
				<form:input type ="text" path="ordering" class="wid50" maxlength="4"/>
				<br/><form:errors path="ordering" class="error"/>
			</td>
		</tr>  
		
		<tr>
			<td><fmt:message key="assetsManagement.description"/></td>
			<td>
				<form:textarea class="wid50" type ="text" path="description" maxlength="900"/>
				<br/><form:errors path="description" class="error"/>
			</td>
		</tr>  
		
        <tr>
           <td></td>
			<td>
				<input type="submit" class="button" id="save"  value="<fmt:message key="global.form.luulai"/>" />
               	<input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm"'>
			</td>
        </tr>
    </table>
    
</form:form>

<script type="text/javascript">
function xl(){
	var sub = document.getElementById("save");
	sub.focus();
}
</script>