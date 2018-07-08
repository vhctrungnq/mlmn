<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
  <style type="text/css"> 
  .styleArea{
  	width:150px !important;
  }
  .stylestylePhone{
  	min-width:150px !important;
  	max-width:200px !important;
  }
	.time{
		width:110px !important;
	}
	.name{
		width:110px !important;
	}
	.phone{
		width:90px !important;
	}
	.regency{
		width:80px !important;
	}
</style>
<title>${title}</title>
<content tag="heading">${title}</content><div>
<form:form commandName="filter" method="post" action="list.htm"> 
	<table style = "width:100%">
		<tr>	
			<td style="width:70px;"><fmt:message key="shiftFes.filter.stime" /></td>
			<td class="pdl10">
				<input type ="text"  value="${sTime}" id = "sTime" name = "sTime" class = "wid70"/>
	   			<img alt="calendar" title="Click chọn thời gian bắt đầu" id="choosestime" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
			
			<td class="pdl15" style="width:70px;"><fmt:message key="shiftFes.filter.etime" /></td>
			<td class="pdl10">
				<input type ="text"  value="${eTime}" id = "eTime" name = "eTime" class = "wid70"/>
	   			<img alt="calendar" title="Click chọn thời gian kết thúc" id="chooseetime" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
				
			<td class="pdl15"><fmt:message key="shiftFes.dept" /></td>
			<td class="pdl10">
				<%-- <form:input type="text" value="${dept}" path ="dept" class="wid70"/> --%>
				<select id="regionTk" name = "regionTk">
	        		<option value=""></option>
   					<c:forEach var="items" items="${regionList}">
		              <c:choose>
		                <c:when test="${items.name == regionTk}">
		                    <option value="${items.name}" selected="selected">${items.value}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.name}">${items.value}</option>
		                </c:otherwise>
		              </c:choose>
				    </c:forEach>
           		</select>	
			</td>
			
			<td class="pdl15" style="width:80px;"><fmt:message key="shiftFes.leaderName" /></td>
			<td class="pdl10">
				<form:input type="text" value="${leaderName}" path ="leaderName" class="wid70"/>
			</td>
			<td></td>
		</tr>
		<tr>	
			<td style="width:70px;"><fmt:message key="shiftFes.leaderPhone" /></td>
			<td class="pdl10">
				<form:input type="text" value="${leaderPhone}" path ="leaderPhone" class="wid70"/>
			</td> 
								
			<td class="pdl15" style="width:70px;"><fmt:message key="shiftFes.regency" /></td>
			<td class="pdl10">
				<form:input type="text" path ="regency" class="wid70"/>
			</td>
			
			<td class="pdl15" style="width:70px;"><fmt:message key="shiftFes.decription" /></td>
			<td class="pdl10">
				<form:input type="text" value="${decription}" path ="decription" class="wid70"/>
			</td>
			
			<td class="pdl15">
				<input class="button" type="submit" id="submit" value="<fmt:message key="button.search"/>"/>
			</td>  
			<td></td> 
		</tr>
		<c:if test="${checkRegion==true}">
		<tr>
			<td align="right" colspan="9">
	            <a href="upload.htm"><fmt:message key="button.upload"/></a>&nbsp;-
	            <a href="form.htm"><fmt:message key="button.add"/></a>&nbsp;
	        </td>
		</tr>
		</c:if>
	</table>
</form:form>
</div>

<div style="overflow: auto;" class="tableStandar">
	<display:table name="${shiftFesList}"  id="shiftFes" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px !important;"> <c:out value="${shiftFes_rowNum}" /> </display:column>
	  	<display:column property="stime" titleKey="shiftFes.stime" format="{0,date,dd/MM/yyyy HH:mm}"/>
	  	<display:column property="etime" titleKey="shiftFes.etime" format="{0,date,dd/MM/yyyy HH:mm}"/>		
	  	<display:column property="dept" titleKey="shiftFes.dept"/>
		<display:column property="leaderName" titleKey="shiftFes.leaderName"/>
		<display:column property="leaderPhone" titleKey="shiftFes.leaderPhone"/>
		<display:column property="regency" titleKey="shiftFes.regency"/>
		<display:column property="staffName" titleKey="shiftFes.staffName"/>
		<display:column property="staffPhone" titleKey="shiftFes.staffPhone"/> 
		<display:column property="decription" titleKey="shiftFes.decription"/>
		<c:if test="${checkRegion==true}">
			<display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style="width:90px !important;">
			<a href="form.htm?id=${shiftFes.shiftId}"><fmt:message key="button.edit"/></a>&nbsp;
	 			<a href="delete.htm?id=${shiftFes.shiftId}"
					onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')">
			<fmt:message key="button.delete"/></a>&nbsp; 
	 		</display:column>
		</c:if>
	   	<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />

	</display:table>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script type="text/javascript">
	
    Calendar.setup({
        inputField		:	"sTime",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "choosestime",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });

    Calendar.setup({
        inputField		:	"eTime",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseetime",   	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    }); 
</script>

<script type="text/javascript">
	$(document).ready(function(){
		var trs='<tr>';
	    trs=trs +'<th rowspan="2" class=""><fmt:message key="global.list.STT"/></th>';
	    trs=trs +'<th colspan="2" class=""><fmt:message key="shiftFes.group.time"/></th>';
	    trs=trs +'<th rowspan="2" class=""><fmt:message key="shiftFes.dept"/></th>'; 
	    trs=trs +'<th colspan="3" class=""><fmt:message key="shiftFes.leader"/></th>'; 
	    trs=trs +'<th colspan="2" class=""><fmt:message key="shiftFes.staff"/></th>'; 
	    trs=trs +'<th rowspan="2" class=""><fmt:message key="shiftFes.decription"/></th>'; 
	    trs=trs +'<th rowspan="2" class=""><fmt:message key="title.quanLy"/></th></tr>'; 
	    
	   	trs=trs +'<tr><th class="time"><fmt:message key="shiftFes.stime"/></th>';
	    trs=trs +'<th class="time"><fmt:message key="shiftFes.etime"/></th>';
	    trs=trs +'<th class="name"><fmt:message key="shiftFes.leaderName"/></th>';
	    trs=trs +'<th class="phone"><fmt:message key="shiftFes.leaderPhone"/></th>';
	    trs=trs +'<th class="regency"><fmt:message key="shiftFes.regency"/></th>';
	    trs=trs +'<th class="name"><fmt:message key="shiftFes.staffName"/></th>';
	    trs=trs +'<th class="phone"><fmt:message key="shiftFes.staffPhone"/></th></tr>'; 
	    
	$('#shiftFes thead').html(trs);
	});
</script>

<script type="text/javascript">
function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
}
</script>
<script type="text/javascript"> 
var theme = getTheme();
$("#dept").jqxInput({ width: '80%', height: 20, minLength: 1, theme: theme });
$("#leaderName").jqxInput({ width: '80%', height: 20, minLength: 1, theme: theme });
$("#leaderPhone").jqxInput({ width: '80%', height: 20, minLength: 1, theme: theme });
$("#regency").jqxInput({ width: '80%', height: 20, minLength: 1, theme: theme });
$("#decription").jqxInput({ width: '80%', height: 20, minLength: 1, theme: theme });
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
${leaderList}
$("#leaderName").jqxInput({ placeHolder: "", height: 20, width: '80%', theme: theme,
    source: function (query, response) {
        var item = query.split(/,\s*/).pop();
        // update the search query.
        $("#leaderName").jqxInput({ query: item });
        response(leaderList);
    },
    renderer: renderer
});
var leaderName =  "<c:out value='${leaderName}'/>";
if(leaderName!="")
	$('#leaderName').val(leaderName);

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