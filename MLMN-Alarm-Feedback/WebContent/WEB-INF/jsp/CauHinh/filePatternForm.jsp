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
<form:form commandName="filePattern" method="post" action="form.htm">
	<form:hidden path="patternId" />
    <table class="simple2">
        <tr>
            <td style="width:120px;"><fmt:message key="filePattern.filePattern"/><font color = "red">(*)</font></td>
            <td  style="max-width:400px;width:400px;"><form:input path="filePattern" size="50" maxlength="200"/> 
           		<font color="red"><form:errors path="filePattern"/></font>
            </td>
            <td  style="width:120px;"><fmt:message key="filePattern.nodeType"/></td>
            <td><form:input path="nodeType" maxlength="30"/>
        </tr>
        <tr>
            <td><fmt:message key="filePattern.convertClass"/><font color = "red">(*)</font></td>
            <td><form:input path="convertClass" size="50" maxlength="100"/>
           		<font color="red"><form:errors path="convertClass"/></font>
            </td>
            <td><fmt:message key="filePattern.rawTable"/></td>
            <td><form:input path="rawTable" maxlength="30"/>  
        </tr>
        <tr>
            <td><fmt:message key="filePattern.subDir"/></td>
            <td><form:input path="subDir" size="50" maxlength="50"/></td>
            <td><fmt:message key="filePattern.nodePatternGroup"/></td>
            <td><form:input path="nodePatternGroup" maxlength="2"/>
            <font color="red"><form:errors path="nodePatternGroup"/></font>   
             </td>
        </tr>
        <tr>
            <td><fmt:message key="filePattern.timePatternGroup"/></td>
            <td><form:input path="timePatternGroup" maxlength="2"/>
            <font color="red"><form:errors path="timePatternGroup"/></font>  
            </td>
            <td><fmt:message key="filePattern.separator"/></td>
            <td><form:input path="separator" maxlength="5"/></td>
        </tr>
        <tr>
            <td><fmt:message key="filePattern.commentChar"/></td>
            <td><form:input path="commentChar" maxlength="5"/></td>
            <td><fmt:message key="filePattern.timePattern"/></td>
            <td><form:input path="timePattern" maxlength="30"/></td>
        </tr>
        <tr>
            <td><fmt:message key="filePattern.importRule"/></td>
            <td>
            	<form:select path="importRule" style="width:140px;">
                    <form:option value="F" label="File"/>
                    <form:option value="M" label="Import Mapping"/>
                </form:select>
			</td>
           <td><fmt:message key="filePattern.status"/></td><td>  
            	<form:select path="status" onchange="xl()"  style="width:140px;">
                    <form:option value="Y" label="Đang sử dụng"/>
                    <form:option value="N" label="Không sử dụng"/>
                </form:select>
			</td>
        </tr>
        <tr>
            <td></td>
            <td colspan="3">
                 <input type="submit" class="button" value="<fmt:message key="button.save"/>" />
           	   	<input type="button" class="button" value="<fmt:message key="button.cancel"/>" onClick='window.location="list.htm"'>
			 </td>

        </tr>
    </table>
    
</form:form>
