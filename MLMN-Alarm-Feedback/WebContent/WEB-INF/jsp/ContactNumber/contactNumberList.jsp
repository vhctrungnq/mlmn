<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
  <style type="text/css">
  .wid70{
  	width:70%;
  }
  .styleArea{
  	width:80px !important;
  }
  .stylestylePhone{
  	min-width:150px !important;
  	max-width:200px !important;
  }
    
	#doublescroll { overflow: auto; overflow-y: hidden; }   
	#doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
    
     #dvtTeamProcess {
     visibility: visible !important;
    }
   
    #acLow {
     visibility: visible !important;
    }
    
     #bscid_chzn {

		width: 220px !important;
	}
    .ui-autocomplete {
		max-height: 200px;
		overflow-y: auto;
		/* prevent horizontal scrollbar */
		overflow-x: hidden;
	}
	/* IE 6 doesn't support max-height
	* we use height instead, but this forces the menu to always be this tall
	*/
	* html .ui-autocomplete {
		height: 200px;
	}
</style>
<title>${title}</title>
<content tag="heading">${title}</content> 
<div>
<form:form commandName="filter" method="post" action="list.htm"> 
	<table style = "width:95%">
		<tr>	
			<td style="width:70px"><fmt:message key="contactNumber.area" /></td>
			<td><form:input type="text" path ="area" class="wid70"/></td>
			
			<td style="width:70px"><fmt:message key="contactNumber.name" /></td>
			<td><form:input type="text" path ="name" class="wid70"/></td> 
				
			<td style="width:90px"><fmt:message key="contactNumber.phoneNumber" /></td>
			<td><form:input type="text" path ="phoneNumber" class="wid70"/></td>
			<td></td>
		</tr>
		<tr>	
			<td><fmt:message key="contactNumber.typeError" /></td>
			<td>
			<form:input type="text" path ="typeError" class="wid70"/>
				<%-- <form:select path ="typeError" class="wid70" onchange="xl()">
					<form:option value=""><fmt:message key="global.All"/></form:option>
	           		<c:forEach var="item" items="${getTypeErrorList}">
						<c:choose>
			                <c:when test="${item.name == typeError}">
			                    <form:option value="${item.name}" selected="selected">${item.value}</form:option>
			                </c:when>
							<c:otherwise>
								<form:option value="${item.name}">${item.value}</form:option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select>  --%>
			</td>
							
			<td><fmt:message key="contactNumber.regency" /></td>
			<td>
			<form:input type="text" path ="regency" class="wid70"/>
				<%-- <form:select path ="regency" class="wid80" onchange="xl()">
					<form:option value=""><fmt:message key="global.All"/></form:option>
	           		<c:forEach var="item" items="${getRegencyList}">
						<c:choose>
			                <c:when test="${item.name == regency}">
			                    <form:option value="${item.name}" selected="selected">${item.value}</form:option>
			                </c:when>
							<c:otherwise>
								<form:option value="${item.name}">${item.value}</form:option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select>  --%>
			</td>
			
			<td >
				<input class="button" type="submit" id="submit" value="<fmt:message key="button.search"/>"/>
			</td> 
			<td></td>  
		</tr>
		
		<tr>
			<td align="right" colspan="7">
	            <a href="upload.htm"><fmt:message key="button.upload"/></a>&nbsp;-
	            <a href="form.htm"><fmt:message key="button.add"/></a>&nbsp;
	        </td>
		</tr>
	</table>
</form:form>
</div>

<div style="overflow: auto;" class="tableStandar">
	<display:table name="${contactNumberList}"  id="contactNumber" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px !important;"> <c:out value="${contactNumber_rowNum}" /> </display:column>
	  	<display:column property="area" titleKey="contactNumber.area" sortable="true" sortName="AREA" style="width:150px !important;"/>
	  	<display:column property="typeError" titleKey="contactNumber.typeError" sortable="true" sortName="TYPE_ERROR" class="styleTime" />
		<display:column property="name" titleKey="contactNumber.name" sortable="true" sortName="NAME" style="width:150px !important;" />
	  	<display:column property="phoneNumber" titleKey="contactNumber.phoneNumber" sortable="true" sortName="PHONE_NUMBER" class="stylePhone" />
	  	<display:column property="email" titleKey="contactNumber.email" sortable="true" sortName="EMAIL" class="styleTime" />
		<display:column property="regency" titleKey="contactNumber.regency" sortable="true" sortName="REGENCY" class="styleArea" />
	  	<display:column property="ghiChu" titleKey="contactNumber.ghiChu" sortable="true" sortName="GHI_CHU" class="styleTime" />
		<display:column property="updateDate" titleKey="contactNumber.updateDate" format="{0,date,dd/MM/yyyy HH:mm}" sortable="true" sortName="UPDATE_DATE" class="styleTime" />
		<display:column property="updateBy" titleKey="contactNumber.updateBy" sortable="true" sortName="UPDATE_BY" class="styleArea" />
		<display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style="width:90px !important;">
			<a href="form.htm?id=${contactNumber.id}"><fmt:message key="button.edit"/></a>&nbsp;
	 			<a href="delete.htm?id=${contactNumber.id}"
					onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')">
			<fmt:message key="button.delete"/></a>&nbsp; 
	 		</display:column>
		
	   	<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />

	</display:table>
</div>
 
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" /> 

<script type="text/javascript">
function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
}
</script>
<script type="text/javascript"> 
var theme = getTheme();
$("#typeError").jqxInput({ width: '80%', height: 20, minLength: 1, theme: theme });
$("#regency").jqxInput({ width: '80%', height: 20, minLength: 1, theme: theme });
$("#phoneNumber").jqxInput({ width: '80%', height: 20, minLength: 1, theme: theme });
$("#name").jqxInput({ width: '80%', height: 20, minLength: 1, theme: theme });
$("#area").jqxInput({ width: '80%', height: 20, minLength: 1, theme: theme });
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

//Input projectType
${teamList}
$("#typeError").jqxInput({ placeHolder: "", height: 20, width: '80%', theme: theme,
    source: function (query, response) {
        var item = query.split(/,\s*/).pop();
        // update the search query.
        $("#typeError").jqxInput({ query: item });
        response(teamList);
    },
    renderer: renderer
});
var projectType =  "<c:out value='${typeError}'/>";
if(projectType!="")
	$('#typeError').val(projectType);

// Input Chuc vu

${regencyList}
$("#regency").jqxInput({ placeHolder: "", height: 20, width: '80%', theme: theme,
    source: function (query, response) {
        var item = query.split(/,\s*/).pop();
        // update the search query.
        $("#regency").jqxInput({ query: item });
        response(regencyList);
    },
    renderer: renderer
});
var regency =  "<c:out value='${regency}'/>";
if(regency!="")
	$('#regency').val(regency);

</script>