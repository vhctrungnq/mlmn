<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>route form</title>
<body class="section-4"/>
<content tag="heading">ROUTE</content>

<form:form commandName="route" method="post" action="form.htm">
	<form:hidden path="region" />
	
    <table class="simple2">
        <tr>
            <td width="200"><b>Route<font color="#ff0000">(*)</font></b></td>
            <td>            	
              <c:choose>
                <c:when test="${empty route.region}">
                    <form:input path="routeid" maxlength="50"/>
                </c:when>
                <c:otherwise>
                    <b><i>${route.routeid}</i></b><form:hidden path="routeid" />
                </c:otherwise>
              </c:choose>
              <br/><font color="red"><form:errors path="routeid"/></font>  
            </td>
        </tr>
        <tr>
            <td><b>Điểm đầu<font color="#ff0000">(*)</font></b></td>
            <td>
            	<form:input path="fromMscid" maxlength="20"/>
            	<br/><font color="red"><form:errors path="fromMscid"/></font>  
			</td>
        </tr>
        <tr>
            <td><b>Điểm cuối<font color="#ff0000">(*)</font></b></td>
            <td><form:input path="toMscid" maxlength="30"/>
            	<br/><font color="red"><form:errors path="toMscid"/></font> 
             </td>
        </tr>
        <tr>
            <td><b>Tên Route</b></td>
            <td><form:input path="routename" maxlength="50"/>
            	<br/><font color="red"><form:errors path="routename"/></font> 
            </td>
        </tr>
        <tr>
            <td><b>Dev</b></td>
            <td><form:input path="dev" maxlength="20"/>
            	<br/><font color="red"><form:errors path="dev"/></font> 
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
