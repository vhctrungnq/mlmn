<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
	.wid150{width:150px;}
	.wid40 {width:40%;}
	.wid50 {width:50%;}
	.wid100 {width:100%;}
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

<form:form commandName="alShiftVendorSupport" name="checkform" method="post" action="form.htm">
	<div><form:hidden path="id"/></div>
    <table class="simple2">
        <tr>
        	<td class="wid15 mwid110"><fmt:message key="vendorSupport.filter.stime" /><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td class="wid35">
				<input type ="text" id="sdate" name = "sdate" value="${sdate}" class="wid150"/>
	   			<img alt="calendar" title="Click chọn thời gian bắt đầu" id="choosestime" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	   			<br/><span class = "error">${SDateError}</span>
			</td>
			
			<td class="wid15 mwid110"><fmt:message key="vendorSupport.filter.etime" /> </td>
			<td>
				<input type ="text" id="edate" name = "edate" value="${edate}" class="wid150"/>
	   			<img alt="calendar" title="Click chọn thời gian kết thúc" id="chooseetime" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	   			<br/><span class = "error">${EDateError}</span>
			</td>
        </tr>
        
		<tr>
			<td><fmt:message key="vendorSupport.vendor" /><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td>
				
				<form:input type ="text" path="vendor" class="wid70" maxlength="20"/>
				<br/><form:errors path="vendor" class="error"/>
			</td>
			
			<td><fmt:message key="vendorSupport.system"/> </td>
			<td>
				<form:input type ="text" path="system" class="wid70" maxlength="20"/>
				<br/><form:errors path="system" class="error"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="vendorSupport.fullName"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td>
				<form:input type ="text" path="fullname" class="wid70" maxlength="100"/>
				<br/><form:errors path="fullname" class="error"/>
			</td>
			
			<td><fmt:message key="vendorSupport.idNumber"/></td>
			<td>
				<form:input type ="text" path="idNumber" class="wid70" maxlength="20"/>
				<br/><form:errors path="idNumber" class="error"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="vendorSupport.phone"/></td>
			<td>
				<form:input type ="text" path="phone" class="wid70" maxlength="100"/>
				<br/><form:errors path="phone" class="error"/>
			</td>
			
			<td><fmt:message key="vendorSupport.email"/></td>
			<td>
				<form:input type ="text" path="email" class="wid70" maxlength="100"/>
				<br/><form:errors path="email" class="error"/>
			</td>
		</tr> 
		
		<tr>
			<td><fmt:message key="vendorSupport.region"/></td>
			<td>
				<c:choose>
					<c:when test="${alShiftVendorSupport.region != null}">
						<form:hidden path="region"/>${alShiftVendorSupport.region}
					</c:when>
					<c:otherwise>
						<form:input type ="text" path="region" class="wid70" maxlength="100"/>
						<br/><form:errors path="region" class="error"/>
					</c:otherwise>
				</c:choose>
				
			</td>
			
			<td><fmt:message key="vendorSupport.jobTitle"/></td>
			<td>
				<form:input type ="text" path="jobTitle" class="wid70" maxlength="100"/>
				<br/><form:errors path="jobTitle" class="error"/>
			</td>
		</tr>  
		
		<tr>
			<td><fmt:message key="vendorSupport.supportType"/></td>
			<td>
				<form:input type ="text" path="supportType" class="wid70" maxlength="20"/>
				<br/><form:errors path="supportType" class="error"/>
			</td>
			
			<td><fmt:message key="vendorSupport.leader"/></td>
			<td>
				<form:input type ="text" path="leader" class="wid70" maxlength="20"/>
				<br/><form:errors path="leader" class="error"/>
			</td>  
		</tr>
		
		<tr>
			<td><fmt:message key="vendorSupport.device"/></td>
			<td>
				<form:input type ="text" path="device" class="wid70" maxlength="100"/>
				<br/><form:errors path="device" class="error"/>
			</td>
			
			<td><fmt:message key="vendorSupport.deviceNumber"/></td>
			<td>
				<form:input type ="text" path="deviceNumber" class="wid70" maxlength="100"/>
				<br/><form:errors path="deviceNumber" class="error"/>
			</td>
		</tr>  
		
		<tr>
			<td><fmt:message key="vendorSupport.description"/></td>
			<td colspan="3">
				<form:textarea type ="text" path="description" style="width:88%;" maxlength="1000"/>
				<br/><form:errors path="description" class="error"/>
			</td> 
		</tr>
		
        <tr>
           <td></td>
			<td colspan="3">
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

function focusIt()
{
	
	if(document.checkform.sdate.value==""){
		  var mytext = document.getElementById("sdate");
		  mytext.focus();
	}
	else if(document.checkform.edate.value=="")
	{
			var mytext = document.getElementById("edate");
			  mytext.focus();
	}
	else if(document.checkform.vendor.value=="")
	{
			var mytext = document.getElementById("vendor");
			  mytext.focus();
	}
	else if(document.checkform.system.value=="")
	{
			var mytext = document.getElementById("system");
			  mytext.focus();
	}
	else if(document.checkform.fullname.value=="")
	{
			var mytext = document.getElementById("fullname");
			  mytext.focus();
	}
}
onload = focusIt;
</script>

<script type="text/javascript">
var theme = getTheme();
$("#vendor").jqxInput({ width: '70%', height: 20, minLength: 1, theme: theme });
$("#system").jqxInput({ width: '70%', height: 20, minLength: 1, theme: theme });
$("#fullname").jqxInput({ width: '70%', height: 20, minLength: 1, theme: theme });
$("#idNumber").jqxInput({ width: '70%', height: 20, minLength: 1, theme: theme });
$("#phone").jqxInput({ width: '70%', height: 20, minLength: 1, theme: theme });
$("#email").jqxInput({ width: '70%', height: 20, minLength: 1, theme: theme });
$("#region").jqxInput({ width: '70%', height: 20, minLength: 1, theme: theme });
$("#supportType").jqxInput({ width: '70%', height: 20, minLength: 1, theme: theme });
$("#leader").jqxInput({ width: '70%', height: 20, minLength: 1, theme: theme });
$("#device").jqxInput({ width: '70%', height: 20, minLength: 1, theme: theme });
$("#deviceNumber").jqxInput({ width: '70%', height: 20, minLength: 1, theme: theme });


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
${jobTitleList}
$("#jobTitle").jqxInput({ placeHolder: "", height: 20, width: '70%', theme: theme,
    source: function (query, response) {
        var item = query.split(/,\s*/).pop();
        // update the search query.
        $("#jobTitle").jqxInput({ query: item });
        response(jobTitleList);
    },
    renderer: renderer
});
var jobTitle =  "<c:out value='${jobTitle}'/>";
if(jobTitle!="")
	$('#jobTitle').val(jobTitle);

</script>