<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<title><fmt:message key="${title}"/></title>
<content tag="heading"><fmt:message key="${title}"/></content>
  
<form:form commandName="sysParameterForm" name="checkform" method="post" action="form.htm">
	<form:hidden path="id"/>
	<input type="hidden" name="typeCode" id="typeCode" value="${typeCode}">
	<table class="simple2">
		
				<c:choose>
					<c:when test="${empty typeCode || typeCode==''}">
						<tr>
						<td ><b><fmt:message key="thamso.code"/>&nbsp;</b><font color = "red">(*)</font></td>
						 <c:choose>
			                <c:when test="${empty sysParameterForm.id}">
			                	<td>
				                    <form:input path="code" class="wid30" maxlength="200" />
				                    &nbsp;<form:errors path="code" cssClass="error"/>
								</td>
			                </c:when>
			         		<c:otherwise>
			                	<td colspan="2">
			                    	<b><i>${sysParameterForm.code}</i></b><form:hidden path="code" />
			                    </td>
			                </c:otherwise>
			            </c:choose>
			            </tr>
	                </c:when>
	                <c:otherwise>
	                	<form:hidden path="code" />
	                </c:otherwise>
		          </c:choose>  	
			
			
		<tr>
			<td class="wid15 mwid160"><b><fmt:message key="thamso.name"/></b>&nbsp;<font color = "red">(*)</font></td>
			<td>
				<form:input path="name" class="wid30" maxlength="240" />
				&nbsp;<form:errors path="name" cssClass="error"/>
			</td>
		</tr>
		<tr>
			<td><b><fmt:message key="thamso.value"/></b>&nbsp;<font color = "red">(*)</font></td>
			<td>
				<form:input path="value" class="wid30" maxlength="240"/>
				&nbsp;<form:errors path="value" cssClass="error"/>
			</td>
		</tr>	
		<tr>
			<td><b><fmt:message key="thamso.ordering"/></b></td>
			<td>
				<form:input path="ordering" class="wid10" maxlength="4"/>
				&nbsp;<form:errors path="ordering" cssClass="error"/>
			</td>
		</tr>
		<tr>
			<td><b><fmt:message key="thamso.dataType"/></b></td>
			<td>
				<form:input path="dataType" class="wid30" maxlength="40"/> 
				&nbsp;<form:errors path="dataType" cssClass="error"/>
			</td>
		</tr>	
		<tr>
			<td>
				<b><fmt:message key="thamso.remark"/></b>
			</td>
			<td>
				<form:textarea class="wid100" path="remark" id="remark" maxlength="500" />
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
       			 <input type="submit" class="button" name="save" value="<fmt:message key="global.form.luulai"/>">  
       			 <input type="button" class="button" onclick='window.location="danh-sach.htm?typeCode=${typeCode}"' value="<fmt:message key="global.form.huybo"/>" >
			</td>
		</tr>
	</table>
</form:form>
	
<script type="text/javascript">
function focusIt()
{
	var orderingError = '<c:out value="${orderingError}"/>';
	if(document.checkform.code.value==""){
		  var mytext = document.getElementById("code");
		  mytext.focus();
	}
	else if(document.checkform.name.value==""){
		  var mytext = document.getElementById("name");
		  mytext.focus();
		}
	else if(document.checkform.value.value==""){
		  var mytext = document.getElementById("value");
		  mytext.focus();
		}
	else if(orderingError != "")
		{
		var mytext = document.getElementById("ordering");
		  mytext.focus();
		}
}

onload = focusIt;
</script>