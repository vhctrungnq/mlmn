<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
	.wid40 {width:40%;}
	.wid50 {width:50%;}
	.wid100 {width:100%;}
	.wid200{width:200px}
	td {padding: 1px 0;}
	.error {color:red;}
	.editbox {display:none}
	.editbox {
		background-color: #FAFAFA;
	    border: 1px solid #DDDDDD;
	    border-radius: 2px 2px 2px 2px;
	    box-shadow: 1px 0 1px rgba(0, 0, 0, 0.1) inset;
	    font-size: 14px;
	    padding: 4px;
	    width: 270px;
	}
	.edit_td, .delete_td {width: 16px;cursor:pointer;}
	.note{color:red; float:right;padding-right: 7px;}
</style>
<title>${titleF}</title>
<body class="section-5"/>
<content tag="heading">${titleF}</content>

<form:form commandName="alShiftFes" method="post" action="form.htm">
    <table class="simple2">
        <tr>
			<td style="width:20%; min-width:170px;"><form:hidden path="shiftId"/></td>
			<td></td>
		</tr>
        
        <tr>  
        	<td style="width:70px;"><fmt:message key="shiftFes.filter.stime" /><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td class="pdl10">
				<input type ="text"  value="${sdate}" id="sdate" name="sdate" class = "wid200"/>
	   			<img alt="calendar" title="Click chọn thời gian bắt đầu" id="choosestime" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	   			<br/><span class="error">${SDateError}</span>
			</td>
		</tr>
		<tr>
			<td class="pdl15" style="width:70px;"><fmt:message key="shiftFes.filter.etime" /><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td class="pdl10">
				<input type ="text"  value="${edate}" id="edate" name="edate" class = "wid200"/>
	   			<img alt="calendar" title="Click chọn thời gian kết thúc" id="chooseetime" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	   			<br/><span class="error">${EDateError}</span>
			</td>
        </tr> 
		
		<tr>
			<td><fmt:message key="shiftFes.dept"/></td>
			<td>
				 <c:choose>
					<c:when test="${alShiftFes.dept != null}">
						<form:hidden path="dept"/>${alShiftFes.dept}
					</c:when>
					<c:otherwise>
						<form:input type ="text" path="dept" class="wid200" maxlength="200"/>
						<br/><form:errors path="dept" class="error"/>
					</c:otherwise>
				</c:choose>
				
			</td>
		</tr>
			
		<tr> 
			<td><fmt:message key="shiftFes.leaderName"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td>
				<form:input type ="text" path="leaderName" class="wid200" maxlength="100"/>
				<br/><form:errors path="leaderName" class="error"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="shiftFes.leaderPhone"/></td>
			<td>
				<form:input type ="leaderPhone" path="leaderPhone" class="wid200" maxlength="100"/>
				<br/><form:errors path="leaderPhone" class="error"/>
			</td> 
		</tr><tr>
			<td class="pdl15" style="width:70px;"><fmt:message key="shiftFes.regency" /></td>
			<td class="pdl10">
				<form:input type="text" path ="regency" class="wid70"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="shiftFes.staffName"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td>
				<form:input type ="text" path="staffName" class="wid200" maxlength="100"/>
				<br/><form:errors path="staffName" class="error"/>
			</td>
		</tr>
		<tr>		
			<td><fmt:message key="shiftFes.staffPhone"/></td>
			<td>
				<form:input type ="staffPhone" path="staffPhone" class="wid200" maxlength="100"/>
				<br/><form:errors path="staffPhone" class="error"/>
			</td>
		</tr>  
		
		<tr>
			<td><fmt:message key="shiftFes.decription"/></td>
			<td>
				<form:textarea type ="text" path="decription" maxlength="500"/>
				<br/><form:errors path="decription" class="error"/>
			</td>
		</tr>
		
        <tr>
           <td></td>
			<td>
				<input type="submit" class="button" id="save"  value="<fmt:message key="global.form.luulai"/>" />
               	<input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm"'>
			</td>
        </tr>
    </table>
    
</form:form>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script type="text/javascript">
	
    Calendar.setup({
        inputField		:	"sdate",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "choosestime",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });

    Calendar.setup({
        inputField		:	"edate",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseetime",   	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    }); 
</script>
<script type="text/javascript">
function xl(){
	var sub = document.getElementById("save");
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