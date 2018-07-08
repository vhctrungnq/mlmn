<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>highlightConfig form</title>
<content tag="heading">HighlightConfig</content>

<form:form commandName="highlightConfig" method="post" action="form.htm">
	<input name="tick" value="${tick}" type="hidden">
    <table class="simple2">
    <tr>
            <td><b>ID</b></td>
            <td>            	
              <c:choose>
                <c:when test="${tick==0}">
                    <form:input path="id" maxlength="4"/>
                </c:when>
                <c:otherwise>
                    <b><i>${highlightConfig.id}</i></b><form:hidden path="id" />
                </c:otherwise>
              </c:choose>
               <br/><font color="red"><form:errors path="id"/></font>  
            </td>
        </tr>
        <tr>
            <td width="200"><b>KPI</b></td>
            <td>            	
            	<form:input path="kpi" maxlength="20"/>
            </td>
        </tr>
        <tr>
            <td><b>KEY</b></td>
            <td>            	
            	<form:input path="key" maxlength="20"/>
            </td>
        </tr>
        <tr>
            <td><b>Công thức</b></td>
            <td>            	
            	<form:input path="formula" maxlength="20"/>
            </td>
        </tr>
        <tr>
            <td><b>Giá trị</b></td>
            <td>            	
            	<form:input path="value" maxlength="10"/>
            </td>
        </tr>
        <tr>
            <td><b>Kiểu giá trị</b></td>
            <td>            	
            	<form:input path="style" maxlength="100"/>
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
