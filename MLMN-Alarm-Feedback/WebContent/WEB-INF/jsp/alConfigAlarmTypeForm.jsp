<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>${titleF}</title>
<content tag="heading">${titleF}</content>

<form:form commandName="alConfigAlarmType" name="checkform" id="myform" method="post" action="form.htm"> 
	<form:hidden path="id"/>
	<table class="simple2"> 
      <tr>       
          <td style="width: 110px;"><b><fmt:message key="alConfigAlarmType.filter.alarmType"/></b></td>
		<td>
			<select name = "alarmType" id="alarmType" style="width: 165px;height:20px; padding-top: 0px;" onchange = "xl()">
           		<c:forEach var="item" items="${alarmTypeList}">
					<c:choose>
		                <c:when test="${item.name == alarmTypeList}">
		                    <option value="${item.name}" selected="selected">${item.value}</option>
		                </c:when>
						<c:otherwise>
							<option value="${item.name}">${item.value}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> 
			<font color="red"><form:errors path="alarmType" cssClass="error"/></font>
		</td>
      </tr> 
      
      <tr>
           <td style="width: 110px;"><b><fmt:message key="alConfigAlarmType.filter.rawTable"/></b></td>
           <td style="width: 200px;">
           		<form:input type ="text" path="rawTable" maxlength="255" style="width:500px;" rows="3"/>
           		<font color="red"><form:errors path="rawTable" cssClass="error"/></font>
           </td>
      </tr>
      
      <tr>
           <td style="width: 110px;"><b><fmt:message key="alConfigAlarmType.filter.alarmInfo"/></b></td>
           <td style="width: 200px;">
           		<form:input type ="text" path="alarmInfoValue" maxlength="255" style="width:500px;" rows="3"/>
           		<font color="red"><form:errors path="alarmInfoValue" cssClass="error"/></font>
           </td>
      </tr>
      
      <tr>
           <td style="width: 110px;"><b><fmt:message key="alConfigAlarmType.filter.colunmName"/></b></td>
           <td style="width: 200px;">
           		<form:input type ="text" path="blockColumnName" maxlength="30" style="width:500px;" rows="3"/>
           		<font color="red"><form:errors path="blockColumnName" cssClass="error"/></font>
           </td>
      </tr>
      
      <tr>
           <td style="width: 110px;"><b><fmt:message key="alConfigAlarmType.filter.alarmBlockValue"/></b></td>
           <td style="width: 200px;">
           		<form:input type ="text" path="blockValue" maxlength="255" style="width:500px;" rows="3"/>
           		<font color="red"><form:errors path="blockValue" cssClass="error"/></font>
           </td>
      </tr>
      
       <tr>
           <td style="width: 110px;"><b><fmt:message key="alConfigAlarmType.filter.colunmInfo"/></b></td>
           <td style="width: 200px;">
           		<form:input type ="text" path="alarmInfoColumnName" maxlength="30" style="width:500px;" rows="3"/>
           		<font color="red"><form:errors path="alarmInfoColumnName" cssClass="error"/></font>
           </td>
      </tr>
      
       <tr>
           <td style="width: 110px;"><b><fmt:message key="alConfigAlarmType.filter.description"/></b></td>
           <td style="width: 200px;">
           		<form:textarea type ="text" path="description" maxlength="255" cols="20" rows="3" style="width:500px;"/>
           		<font color="red"><form:errors path="description" cssClass="error"/></font>
           </td>
      </tr>
      <tr>
   		<td style = "width:70px;"><b><fmt:message key="alConfigAlarmType.filter.search"/></b></td>
       	<td>
	      	<select name="search" id="search" style="width: 165px;height:20px; padding-top: 0px;">
	          		<c:forEach var="item" items="${searchList}">
					<c:choose>
		                <c:when test="${item.name == search}">
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
           <td style="width: 110px;"><b><fmt:message key="alConfigAlarmType.filter.node"/></b></td>
           <td>
				<select name="node" id="node" style="width: 165px;height:20px; padding-top: 0px;">
	           		<c:forEach var="item" items="${nodeList}">
						<c:choose>
			                <c:when test="${item.name == node}">
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
           <td style="width: 110px;"><b><fmt:message key="alConfigAlarmType.filter.vendor"/></b></td>
           <td>
				<select name="vendor" id="vendor" style="width: 165px;height:20px; padding-top: 0px;">
	           		<c:forEach var="item" items="${vendorList}">
						<c:choose>
			                <c:when test="${item.name == vendor}">
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
           <td></td>
           <td colspan="5">
               <input type="submit" class="button" name="save" value="<fmt:message key="button.save"/>" />
           	   <input type="button" class="button" value="<fmt:message key="button.cancel"/>" onClick='window.location="list.htm"'>
					
           </td>
       </tr>
    </table>
</form:form>

<style>
    .error {
    	color: red;
    }
</style> 

<!-- <script type="text/javascript">

function focusIt()
{
	if(document.checkform.mscid.value==""){
	  var mytext = document.getElementById("bscid");
	  mytext.focus();
	}
}

onload = focusIt;

</script> -->