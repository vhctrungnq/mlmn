<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>route form</title>
<body class="section-4"/>
<content tag="heading">ROUTE</content>

<form:form commandName="route" method="post" action="form.htm">
	<form:hidden path="id" />
	
    <table class="simple2">
        <tr>
            <td><b>ROUTER ID<font color="#ff0000">(*)</font></b></td>
            <td>
            	<form:input path="routerId" maxlength="20"/>
            	<font color="red"><form:errors path="routerId" cssClass="error"/></font>  
			</td>
			<td></td>
        </tr>
        
        <tr>
            <td><b>ROUTER NAME<font color="#ff0000">(*)</font></b></td>
            <td>
            	<form:input path="routerName" maxlength="20"/>
            	<font color="red"><form:errors path="routerName" cssClass="error"/></font>  
			</td>
			<td></td>
        </tr>
        <tr>
            <td><b>DEVICE TYPE</b></td>
            <td><form:input path="deviceType" maxlength="50"/>
            	<font color="red"><form:errors path="deviceType"/></font> 
             </td>
        </tr>
        <tr>
            <td><b>FUNCTION</b></td>
            <td><form:input path="function" maxlength="50"/>
            	<font color="red"><form:errors path="function"/></font> 
            </td>
        </tr>
        <tr>
            <td><b>OAM</b></td>
            <td><form:input path="oam" maxlength="20"/>
            	<font color="red"><form:errors path="oam"/></font> 
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" class="button" name="save" value="Lưu lại"/>
                <input type="button" value="Hủy bỏ" onClick='window.location="list.htm"' class = "button">
            </td>
        </tr>
    </table>
    
</form:form>
