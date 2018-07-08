<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title><spring:message code="header.title.dealerForm"/></title>
<content tag="heading"><spring:message code="header.title.dealerForm"/></content>

<form:form commandName="dealer" method="post" action="form.htm"> 
    <table class="content_detail">
    <tr>
    <td colspan="2"><h2><spring:message code="header.title.dealerForm"/></h2> 	</td>
    </tr>
       <tr>
            <td><b>id</b></td>
            <td><form:input path="id"/></td>
        </tr>
        <tr>
            <td><b>code</b></td>
            <td><form:input path="code"/></td>
        </tr>
        <tr>
            <td><b>abbreviation</b></td>
            <td><form:input path="abbreviation"/></td>
        </tr>
          <tr>
            <td><b>vn_name</b></td>
            <td><form:input path="vn_name"  size= "150px"/></td>
        </tr>     <tr>
            <td><b>en_name</b></td>
            <td><form:input path="en_name"/></td>
        </tr>   
        
        <tr>
            <td></td>
            <td>
                <input type="submit" class="button" name="save" value="Save"/>
                <input type="button" value="Cancel" onClick="history.back()">
            </td>
        </tr>
    </table>
    
</form:form>
