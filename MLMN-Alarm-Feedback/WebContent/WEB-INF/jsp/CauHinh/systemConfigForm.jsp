<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>${titleF}</title>
<content tag="heading">${titleF}</content>
<!-- <content tag="heading">THAM SỐ HỆ THỐNG</content> -->

<form:form commandName="systemConfig" method="post" action="form.htm">
	<input type="hidden" name="oldParamName" id="oldParamName" value="${oldParamName}">
    <table class="simple2">
        <tr>
            <td width="120px;"><fmt:message key="systemConfig.paramName"/><font color = "red">(*)</font></td>
            <td>
            	<c:choose>
	                <c:when test="${empty oldParamName}">
	                    <form:input type ="text" path="paramName" maxlength="30" size="40"/>
	                    <font color="red"><form:errors path="paramName"/></font>
	                </c:when>	
	                <c:otherwise>
	                    <b><i>${systemConfig.paramName}</i></b><form:hidden path="paramName" />
	                </c:otherwise>
	            </c:choose>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="systemConfig.paramValue"/><font color = "red">(*)</font></td>
            <td><form:input path="paramValue" maxlength="50"  size="40"/>
            <font color="red"><form:errors path="paramValue"/></font>
            </td>
        </tr>
         <tr>
            <td><fmt:message key="systemConfig.configType"/><font color = "red">(*)</font></td>
	            <td>
            	  <select name="configType" id="configType" style="width: 260px;height:20px; padding-top: 4px;">
		           		<c:forEach var="item" items="${configTypeList}">
							<c:choose>
				                <c:when test="${item.name == configType}">
				                    <option value="${item.name}" selected="selected">${item.value}</option>
				                </c:when>
								<c:otherwise>
									<option value="${item.name}">${item.value}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select> 
            	
			</td>
		</tr>
        <tr>
            <td><fmt:message key="systemConfig.description"/></td>
            <td><form:textarea path="description" maxlength="200" style="width:100%"/></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" class="button" name="Lưu lại" id="submit" value="Lưu lại"/>
                <input type="button" value="Hủy bỏ" onClick='window.location="list.htm"' class = "button">
            </td>
        </tr>
    </table>
    
</form:form>
