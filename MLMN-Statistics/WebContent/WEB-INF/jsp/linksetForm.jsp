<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>linkset form</title>
<body class="section-4"/>
<content tag="heading">LINKSET</content>

<form:form commandName="linkset" method="post" action="form.htm">
	<form:hidden path="region" />
	
    <table class="simple2">
        <tr>
            <td width="200"><b>Linkset Id<font color = "red">(*)</font></b></td>
            <td>            	
            <c:choose>
                <c:when test="${empty linkset.region}">  	
            		<form:input path="linksetid" maxlength="30"/>
            		<br/><font color="red"><form:errors path="linksetid"/></font>  
                </c:when>
                <c:otherwise>
                    <b><i>${linkset.linksetid}</i></b><form:hidden path="linksetid" />
                </c:otherwise>
              </c:choose>     
            </td>
        </tr>
        <tr>
            <td><b>Node Mạng<font color = "red">(*)</font></b></td>
            <td>
            <c:choose>
                <c:when test="${empty linkset.region}">  	
            		<form:input path="nodeid" maxlength="50"/>
            		<br/><font color="red"><form:errors path="nodeid"/></font>
                </c:when>
                <c:otherwise>
                    <b><i>${linkset.nodeid}</i></b><form:hidden path="nodeid" />
                </c:otherwise>
              </c:choose> 
            </td>  
        </tr>
        <tr>
            <td><b>Tên Linkset</b></td>
            <td><form:input path="linksetName" maxlength="100"/></td>
        </tr>
        <tr>
            <td><b>Số Link</b></td>
            <td><form:input path="linkDevice" maxlength="9"/> 
            <br/><font color="red"><form:errors path="linkDevice"/></font>  </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" class="button" name="save" value="Lưu lại"/>
                <input type="button" value="Hủy bỏ" onClick='window.location="list.htm"' class="button">
            </td>
        </tr>
    </table>
    
</form:form>
