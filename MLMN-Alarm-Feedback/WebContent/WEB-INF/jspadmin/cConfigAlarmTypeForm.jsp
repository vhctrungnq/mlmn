<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<c:choose>
  <c:when test="${configAlarmTypeAddEdit == 'N'}">
      <title><fmt:message key="title.cConfigAlarmType.formAdd"/></title>
	  <content tag="heading"><fmt:message key="title.cConfigAlarmType.formAdd"/></content>
  </c:when>
  <c:when test="${configAlarmTypeAddEdit == 'Y'}">
      <title><fmt:message key="title.cConfigAlarmType.formEdit"/></title>
	  <content tag="heading"><fmt:message key="title.cConfigAlarmType.formEdit"/></content>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>

<form:form modelAttribute="configAlarmTypeForm" commandName="configAlarmTypeForm" name="checkform" method="post" action="form.htm"> 
	<div><form:input id="id" path="id" type="hidden"/></div>
    <table class="simple2"> 
      <tr>
      		<td class="wid15 mwid110"><fmt:message key="cConfigAlarmType.vendor"/></td>
           	<td class="wid35">
        		<select name="vendor" class="wid60" id="vendor">
       				<c:forEach var="items" items="${vendorList}">
		              <c:choose>
		                <c:when test="${items.value == vendorCBB}">
		                    <option value="${items.value}" selected="selected">${items.value}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.value}">${items.value}</option>
		                </c:otherwise>
		              </c:choose>
				    </c:forEach>&nbsp;<form:errors path="vendor" cssClass="errorMessages"/>
       			</select>
           	</td>
           	<td class="wid15 mwid110"><fmt:message key="cConfigAlarmType.node"/></td>
           	<td>
           		<select name="node" class="wid60" id="node">
       				<c:forEach var="items" items="${nodeList}">
		              <c:choose>
		                <c:when test="${items.name == nodeCBB}">
		                    <option value="${items.name}" selected="selected">${items.name}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.name}">${items.name}</option>
		                </c:otherwise>
		              </c:choose>
				    </c:forEach>
       			</select>&nbsp;<form:errors path="node" cssClass="errorMessages"/>
           	</td>
      </tr>
      <tr>
      	<td><fmt:message key="cConfigAlarmType.neType"/></td>
        <td>
           <select name="neType" class="wid60" id="neType">
   				<c:forEach var="items" items="${neTypeList}">
	              <c:choose>
	                <c:when test="${items.value == neTypeCBB}">
	                    <option value="${items.value}" selected="selected">${items.value}</option>
	                </c:when>
	                <c:otherwise>
	                    <option value="${items.value}">${items.value}</option>
	                </c:otherwise>
	              </c:choose>
			    </c:forEach>
   			</select>&nbsp;<form:errors path="neType" cssClass="errorMessages"/>		
        </td> 
        <td><fmt:message key="cConfigAlarmType.rawTable"/></td>
        <td>
        	<select name="rawTable" class="wid60" id="rawTable">
   				<c:forEach var="items" items="${rawTableList}">
	              <c:choose>
	                <c:when test="${items.value == rawTableCBB}">
	                    <option value="${items.value}" selected="selected">${items.value}</option>
	                </c:when>
	                <c:otherwise>
	                    <option value="${items.value}">${items.value}</option>
	                </c:otherwise>
	              </c:choose>
			    </c:forEach>
   			</select>&nbsp;<form:errors path="rawTable" cssClass="errorMessages"/>
        </td>
      </tr>
      <tr>
      	<td><fmt:message key="cConfigAlarmType.blockColumnName"/></td>
        <td>
           	<form:select path="blockColumnName" class="wid60" id="blockColumnName">
       				<c:forEach var="items" items="${blockColumnNameList}">
		              <c:choose>
		                <c:when test="${items.value == blockColumnNameCBB}">
		                    <option value="${items.value}" selected="selected">${items.name}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.value}">${items.name}</option>
		                </c:otherwise>
		              </c:choose>
				    </c:forEach>
		    </form:select>&nbsp;<form:errors path="blockColumnName" cssClass="errorMessages"/>			
       </td>
       <td><fmt:message key="cConfigAlarmType.blockValue"/>&nbsp;<font color="red">(*)</font></td>
       <td><input type="text" id="blockValue" name="blockValue" value="${blockValue}" maxlength="220"/>&nbsp;<form:errors path="blockValue" cssClass="errorMessages"/></td> 
      </tr>
      <tr>
        <td><fmt:message key="cConfigAlarmType.alarmMappingName"/>&nbsp;<font color="red">(*)</font></td>
        <td>
        	<input type="text" id="alarmMappingName" name="alarmMappingName" maxlength="220"/>&nbsp;<form:errors path="alarmMappingName" cssClass="errorMessages"/>
        </td>
        <td><fmt:message key="cConfigAlarmType.alarmMappingId"/>&nbsp;<font color="red">(*)</font></td>
       	<td><input type="text" id="alarmMappingId" name="alarmMappingId" maxlength="30"/>&nbsp;<form:errors path="alarmMappingId" cssClass="errorMessages"/></td>
      </tr>
      <tr>
       	<td><fmt:message key="cConfigAlarmType.alarmType"/>&nbsp;<font color="red">(*)</font></td>
        <td><input type="text" id="alarmType" name="alarmType" maxlength="220"/>&nbsp;<form:errors path="alarmType" cssClass="errorMessages"/></td>
        <td><fmt:message key="cConfigAlarmType.description"/></td>
        <td><input type="text" id="description" name="description" value="${description}" maxlength="220"/></td>
      </tr>
      <tr>
      	<td><fmt:message key="cConfigAlarmType.isMonitor"/></td>
      	<td>
          <form:select path="isMonitor" class="wid30">
   				<c:forEach var="items" items="${yesNoList}">
	              <c:choose>
	                <c:when test="${items.value == isMonitorCBB}">
	                    <option value="${items.value}" selected="selected">${items.name}</option>
	                </c:when>
	                <c:otherwise>
	                    <option value="${items.value}">${items.name}</option>
	                </c:otherwise>
	              </c:choose>
			    </c:forEach>
			</form:select>
        </td>
        <td><fmt:message key="cConfigAlarmType.isSendSms"/></td>
        <td>  	
        	<form:select path="isSendSms" class="wid30">
   				<c:forEach var="items" items="${yesNoList}">
	              <c:choose>
	                <c:when test="${items.value == isSendSmsCBB}">
	                    <option value="${items.value}" selected="selected">${items.name}</option>
	                </c:when>
	                <c:otherwise>
	                    <option value="${items.value}">${items.name}</option>
	                </c:otherwise>
	              </c:choose>
			    </c:forEach>
			</form:select>
        </td>
      </tr>
      <tr>
      	<td><fmt:message key="cConfigAlarmType.ordering"/></td>
	    <td><input type="text" id="ordering" name="ordering" value="${ordering}" maxlength="12"/>&nbsp;<form:errors path="ordering" cssClass="error"/></td>
	    <td><fmt:message key="cConfigAlarmType.isEnable"/></td>
	    <td>
	    	<form:select path="isEnable" class="wid30">
   				<c:forEach var="items" items="${yesNoList}">
	              <c:choose>
	                <c:when test="${items.value == isEnableCBB}">
	                    <option value="${items.value}" selected="selected">${items.name}</option>
	                </c:when>
	                <c:otherwise>
	                    <option value="${items.value}">${items.name}</option>
	                </c:otherwise>
	              </c:choose>
			    </c:forEach>
			</form:select>
	    </td>
      </tr>
      <tr>
      	<td><fmt:message key="cConfigAlarmType.summaryType"/></td>
      	<td><input type="text" id="summaryType" name="summaryType" value="${summaryType}"  maxlength="9"/></td>
      	<td><fmt:message key="cConfigAlarmType.search"/></td>
      	<td>
      		<form:select path="search" class="wid30">
   				<c:forEach var="items" items="${searchList}">
	              <c:choose>
	                <c:when test="${items.value == searchCBB}">
	                    <option value="${items.value}" selected="selected">${items.value}</option>
	                </c:when>
	                <c:otherwise>
	                    <option value="${items.value}">${items.value}</option>
	                </c:otherwise>
	              </c:choose>
			    </c:forEach>
			</form:select>
      	</td>
      </tr>
      <tr>
      	<td><fmt:message key="cConfigAlarmType.alarmInfoColumnName"/></td>
      	<td><input type="text" id="alarmInfoColumnName" name="alarmInfoColumnName" value="${alarmInfoColumnName}" maxlength="20"/></td>
      	<td><fmt:message key="cConfigAlarmType.alarmInfoValue"/></td>
      	<td><input type="text" id="alarmInfoValue" name="alarmInfoValue" value="${alarmInfoValue}" maxlength="220"/></td>
      </tr>
      <tr>
      	<td><fmt:message key="cConfigAlarmType.brandname"/></td>
        <td>
           		<form:input path="brandname" maxlength="13" />
        </td>
        <td><fmt:message key="cConfigAlarmType.isSoundAlarm"/></td>
        <td>
           	<form:select path="isSoundAlarm" class="wid30">
   				<c:forEach var="items" items="${yesNoList}">
	              <c:choose>
	                <c:when test="${items.value == isSoundAlarm}">
	                    <option value="${items.value}" selected="selected">${items.name}</option>
	                </c:when>
	                <c:otherwise>
	                    <option value="${items.value}">${items.name}</option>
	                </c:otherwise>
	              </c:choose>
			    </c:forEach>
			</form:select>
        </td>
      </tr>
       <tr>
           <td></td>
           <td colspan="3">
               <input class="button" type="submit" name="save" value="<fmt:message key="global.form.luulai"/>" />
               <input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm"'>
           </td>
       </tr>
    </table>
</form:form>
<script type="text/javascript">
function focusIt()
{
	var orderingError = '<c:out value="${orderingError}"/>';

	if(document.checkform.blockValue.value==""){
		  var mytext = document.getElementById("blockValue");
		  mytext.focus();
	}
	else if(document.checkform.alarmMappingName.value==""){
		  var mytext = document.getElementById("alarmMappingName");
		  mytext.focus();
	}
	else if(document.checkform.alarmMappingId.value==""){
		  var mytext = document.getElementById("alarmMappingId");
		  mytext.focus();
	}
	else if(document.checkform.alarmType.value==""){
		  var mytext = document.getElementById("alarmType");
		  mytext.focus();
	}
	else if(orderingError !="")
	{
			var mytext = document.getElementById("ordering");
			  mytext.focus();
	}
}
onload = focusIt;
</script>
<script type="text/javascript">
var theme = getTheme();

$("#blockValue").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#description").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#ordering").jqxInput({ width: '30%', height: 20, minLength: 1, theme: theme });
$("#summaryType").jqxInput({ width: '30%', height: 20, minLength: 1, theme: theme });
$("#alarmInfoColumnName").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#alarmInfoValue").jqxInput({ width: '60%', height: 20, minLength: 1, theme: theme });
$("#brandname").jqxInput({ width: '24%', height: 20, minLength: 1, theme: theme });
//Create a jqxMultile Input
var renderer = function (itemValue, inputValue) {
    var terms = inputValue.split(/,\s*/);
    // remove the current input
    terms.pop();
    // add the selected item
    terms.push(itemValue);
    // add placeholder to get the comma-and-space at the end
    terms.push("");
    var value = terms.join("");
    return value;
};

//Input alarmMappingName
${alarmMappingNameList}
$("#alarmMappingName").jqxInput({ placeHolder: "", height: 20, width: '60%', theme: theme,
    source: function (query, response) {
        var item = query.split(/,\s*/).pop();
        // update the search query.
        $("#alarmMappingName").jqxInput({ query: item });
        response(alarmMappingNameList);
    },
    renderer: renderer
});
var alarmMappingNameCBB =  "<c:out value='${alarmMappingNameCBB}'/>";
if(alarmMappingNameCBB!="")
	$('#alarmMappingName').val(alarmMappingNameCBB);
	
//Input alarmMappingId
${alarmMappingIdList}
$("#alarmMappingId").jqxInput({ placeHolder: "", height: 20, width: '60%', theme: theme,
    source: function (query, response) {
        var item = query.split(/,\s*/).pop();
        // update the search query.
        $("#alarmMappingName").jqxInput({ query: item });
        response(alarmMappingIdList);
    },
    renderer: renderer
});
var alarmMappingIdCBB =  "<c:out value='${alarmMappingIdCBB}'/>";
if(alarmMappingIdCBB!="")
	$('#alarmMappingId').val(alarmMappingIdCBB);
	
//Input alarmType
${alarmTypeList}
$("#alarmType").jqxInput({ placeHolder: "", height: 20, width: '60%', theme: theme,
    source: function (query, response) {
        var item = query.split(/,\s*/).pop();
        // update the search query.
        $("#alarmType").jqxInput({ query: item });
        response(alarmTypeList);
    },
    renderer: renderer
});
var alarmTypeCBB =  "<c:out value='${alarmTypeCBB}'/>";
if(alarmTypeCBB!="")
	$('#alarmType').val(alarmTypeCBB);
</script>