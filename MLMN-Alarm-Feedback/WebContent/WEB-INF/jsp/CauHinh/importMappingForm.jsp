<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <style>   
     #status {
     visibility: visible !important;
    }
</style>        
<title>${titleF}</title>
<content tag="heading">${titleF}</content> 	

<form:form commandName="importMapping" method="post" action="form.htm">
	<form:hidden path="createBy" />
	
    <table class="simple2">
        <tr>
            <td width="120px;"><fmt:message key="importMapping.rawTable"/><font color = "red">(*)</font></td>
            <td>
            	<c:choose>
	                <c:when test="${empty importMapping.createBy}">
	                    <form:input type ="text" path="rawTable" maxlength="30" size="39"/>
	                    <font color="red"><form:errors path="rawTable"/></font>
	                </c:when>
	                <c:otherwise>
	                    <b><i>${importMapping.rawTable}</i></b><form:hidden path="rawTable" />
	                </c:otherwise>
	            </c:choose>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="importMapping.tableColumn"/><font color = "red">(*)</font></td>
            <td>
            	<c:choose>
	                <c:when test="${empty importMapping.createBy}">
	                    <form:input type ="text" path="tableColumn" maxlength="30" size="39"/>
	                     <font color="red"><form:errors path="tableColumn"/></font>
	                </c:when>
	                <c:otherwise>
	                    <b><i>${importMapping.tableColumn}</i></b><form:hidden path="tableColumn" />
	                </c:otherwise>
	            </c:choose>
            </td>
        </tr>
         <tr>
            <td><fmt:message key="importMapping.filePattern"/><font color = "red">(*)</font></td>
            <td>
            	<c:choose>
	                <c:when test="${empty importMapping.createBy}">
	                    <form:select path="patternId" items="${filePatternList}" itemValue="patternId" itemLabel="filePattern" style="width:250px;"/>
	                </c:when>
	                <c:otherwise>
	                    <b><i>${importMapping.filePattern}</i></b><form:hidden path="patternId" />
	                </c:otherwise>
	            </c:choose>
	             <font color="red"><form:errors path="patternId"/></font>
			</td>
        </tr>
        <tr>
            <td><fmt:message key="importMapping.dataType"/></td>
            <td><form:input type ="text" path="dataType" maxlength="30" size="39"/></td>
        </tr>
        <tr>
            <td><fmt:message key="importMapping.dataFormat"/></td>
            <td><form:input type ="text" path="dataFormat" maxlength="30" size="39"/></td>
        </tr>
        <tr>
            <td><fmt:message key="importMapping.fileColumnHeader"/></td>
            <td><form:input type ="text" path="fileColumnHeader" maxlength="100" size="39"/></td>
        </tr>
        <tr>
            <td><fmt:message key="importMapping.fileColumn"/></td>
            <td><form:input type ="text" path="fileColumn" maxlength="4" size="39"/>
            <font color="red"><form:errors path="fileColumn"/></font>
            </td>
        </tr>
       
        <tr>
            <td><fmt:message key="importMapping.status"/></td>
            <td>
            	<form:select path="status" style="width:250px;" id="status" name="status">
                    <form:option value="Y" label="Đang sử dụng"/>
                    <form:option value="N" label="Không sử dụng"/>
                </form:select>
			</td>
        </tr>
        <tr>
            <td></td>
            <td>
                 <input type="submit" class="button" value="<fmt:message key="button.save"/>" />
           	   	<input type="button" class="button" value="<fmt:message key="button.cancel"/>" onClick='window.location="list.htm"'>
			 </td>
        </tr>
    </table>
    
</form:form>
