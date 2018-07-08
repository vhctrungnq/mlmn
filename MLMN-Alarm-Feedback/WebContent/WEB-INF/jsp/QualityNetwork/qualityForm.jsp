<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<title>${titleF}</title>
<content tag="heading">${titleF}</content>
<style>
	.ui-multiselect {
		width:160px !important;
	}
	 #dvtTeamProcess {
     visibility: visible !important;
    }
      #ungCuuMpd {
     visibility: visible !important;
    }
  
</style>
<form:form commandName="hQualityNetwork" name="checkform" id="myform" method="post" action="form.htm"> 
	<form:hidden path="id"/>
	<table class="simple2"> 
      <tr>
           <td style="width: 120px;"><fmt:message key="hQualityNetwork.groupName"/><font color="red">(*)</font></td>
           <td colspan="5">
	           <select name="groupName" id="groupName" style="width: 160px;height:20px; padding-top: 4px;">
	           		<c:forEach var="item" items="${groupNameList}">
						<c:choose>
			                <c:when test="${item.value == team}">
			                    <option value="${item.value}" selected="selected">${item.value}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.value}">${item.value}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> 
           		<font color="red"><form:errors path="groupName"/></font>
           </td>
          
      </tr> 
     <tr>
     		<td style="width: 120px;"><fmt:message key="hQualityNetwork.qualityCode"/><font color="red">(*)</font></td>
           	<td style="width: 200px;">
           		<form:input type ="text" path="qualityCode" maxlength="50" style="width:160px;" rows="3"/>
          		<font color="red"><form:errors path="qualityCode"/></font>
           	</td>
           <td style="width: 120px;"><fmt:message key="hQualityNetwork.qualityName"/><font color="red">(*)</font></td>
           <td style="width: 200px;">
           		<form:input type ="text" path="qualityName" maxlength="200" style="width:160px;" rows="3"/>
           		<font color="red"><form:errors path="qualityName"/></font>
	    	</td>
      
           <td style="width: 120px;"><fmt:message key="hQualityNetwork.qualityCondition"/></td>
           <td>
           		<select name="qualityCondition" id="qualityCondition" style="width: 160px;height:20px; padding-top: 4px;">
	           		 <option value=""></option>
	           		<c:forEach var="item" items="${conditionList}">
						<c:choose>
			                <c:when test="${item.value == qualityCondition}">
			                    <option value="${fn:escapeXml(item.value)}" selected="selected"><c:out value="${item.value}"></c:out></option>
			                </c:when>
							<c:otherwise>
								<option value="${fn:escapeXml(item.value)}"><c:out value="${item.value}"></c:out></option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> 	
	    	</td>
      	</tr>
        <tr>
           <td><fmt:message key="hQualityNetwork.qualityValue"/><font color="red">(*)</font></td>
           <td>
           		<form:input type ="text" path="qualityValue" maxlength="18" style="width:160px;" rows="3"/>
           		<font color="red"><form:errors path="qualityValue"/></font>
	    	</td>
     
           <td><fmt:message key="hQualityNetwork.qualityUnit"/></td>
           <td>
	          	<select name="qualityUnit" id="qualityUnit" style="width: 160px;height:20px; padding-top: 4px;">
	          	 <option value=""></option>
	           		<c:forEach var="item" items="${unitList}">
						<c:choose>
			                <c:when test="${item.value == qualityUnit}">
			                    <option value="${item.value}" selected="selected">${item.value}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.value}">${item.value}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> 
    		</td>
           <td><fmt:message key="hQualityNetwork.ordering"/></td>
           <td>
           		<form:input type ="text" path="ordering" maxlength="18" style="width:160px;" rows="3"/>
           		<font color="red"><form:errors path="ordering"/></font>
	     	</td>
         </tr> 
      	<tr>  
           <td></td>
           <td colspan="5">
               <input type="submit" class="button" value="<fmt:message key="button.save"/>" />
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


<script type="text/javascript">

function focusIt()
{
	if(document.checkform.groupName.value==""){
	  var mytext = document.getElementById("groupName");
	  mytext.focus();
	}
}

onload = focusIt;
</script>
