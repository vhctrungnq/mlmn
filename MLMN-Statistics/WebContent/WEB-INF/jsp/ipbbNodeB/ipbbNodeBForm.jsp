<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>${title}</title> 
<content tag="heading">${title}</content>

<form:form commandName="hIpbbNodeB" method="post" action="form.htm">
	<form:hidden path="id" />
	<input type="hidden" id="type" name="type" value="${type}"/>
    <table class="simple2">
        <tr>
            <td style="width: 120px;"><b>NodeB ID<font color = "red">(*)</font></b></td>
            <td>
            	<form:input path="nodebId" style="width:50%" maxlength="255"/>
            	<br/><font color="red"><form:errors path="nodebId"/></font>
            </td>
        </tr>
        <tr>
            <td><b>Tên trạm<font color = "red">(*)</font></b></td>
            <td>
            	<form:input path="tenTram" style="width:50%" maxlength="255"/>
            	 <br/><font color="red"><form:errors path="tenTram"/></font>
            </td>
        </tr>
        <tr>
            <td><b>Tên đối tác</b></td>
            <td>
            	<form:input path="tenDoiTac" style="width:50%" maxlength="255"/>
            	 <br/><font color="red"><form:errors path="tenDoiTac"/></font>
            </td>
        </tr>
        <tr>
            <td><b>Mã trạm</b></td>
            <td>
            	<form:input path="maTram" style="width:50%" maxlength="255"/>
            	 <br/><font color="red"><form:errors path="maTram"/></font>
            </td>
        </tr>
        <tr>
            <td><b>BandWith</b></td>
            <td><form:input path="bw" style="width:50%" />
            <br/><font color="red"><form:errors path="bw"/></font>
            </td>
        </tr>
        <tr>
            <td><b>Ghi chú</b></td>
            <td><form:textarea path="description" style="width:50%" maxlength="255"/></td>
        </tr>
        <tr> 
            <td colspan="2">
                <input type="submit" class="button" name="save" value="Lưu lại"/> 
                <input type="button" value="Hủy bỏ" onClick='window.location="list.htm?type=${type}"' class = "button">
            </td>
        </tr>
    </table>
    
</form:form>
