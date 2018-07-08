<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:choose>
  <c:when test="${alWorkCalendarAddEdit == 'N'}">
      <title><fmt:message key="title.alWorkCalendar.alWorkCalendarFormAdd"/></title>
	  <content tag="heading"><fmt:message key="title.alWorkCalendar.alWorkCalendarFormAdd"/></content>
  </c:when>
  <c:when test="${alWorkCalendarAddEdit == 'Y'}">
      <title><fmt:message key="title.alWorkCalendar.alWorkCalendarFormEdit"/></title>
	  <content tag="heading"><fmt:message key="title.alWorkCalendar.alWorkCalendarFormEdit"/></content>
  </c:when>
  <c:otherwise>
      
  </c:otherwise>
</c:choose>      

<form:form commandName="alWorkCalendar" name="checkform" method="post" action="form.htm?region=${region}"> 
	<div>
		<form:hidden path="id"/>
		<input id="startDate" name="startDate" value="${startDate}" type="hidden" />
		<input id="endDate" name="endDate" value="${endDate}" type="hidden" />
	</div>
    <table class="simple2"> 
    <tr>
    	<td>
       		<fmt:message key="qLPhongBan.region"/>&nbsp;<font color="red">(*)</font>
        </td>
       
        <td>
			 <c:choose>
	        	<c:when test="${region!=''}">
	        		<p style="font-weight: bold;">${region}</p>
	        	</c:when>
	        	<c:otherwise><div id='region'></div></c:otherwise>
	        </c:choose>
	      
      	</tr>	
     <tr>
     	<td class="wid15 mwid160"><fmt:message key="alWorkCalendar.email"/>&nbsp;<font color="red">(*)</font></td>
     	<td>
            <form:input path="email" maxlength="60" cssClass="wid30"/>&nbsp;<form:errors path="email" cssClass="error"/>
       </td>
     </tr>
     
      <tr>
           <td><fmt:message key="alWorkCalendar.day"/>&nbsp;<font color="red">(*)</font></td>
           	<td>
           		<input id="day" name="day" value="${day}" class="wid10" maxlength="20"/>
	 			<img alt="calendar" title="Click to choose the date" id="chooseDay" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	 			&nbsp;<form:errors path="day" cssClass="error"/><span class="error">${errorDay}</span>
           	</td>       
      </tr>
      <tr>
      	<td><fmt:message key="alWorkCalendar.shift"/>&nbsp;<font color="red">(*)</font></td>
      	<td>
			<div id="shiftCBB" ></div>&nbsp;<form:errors path="shift" cssClass="error"/>
			<form:hidden path="shift"/>
      	</td>
      </tr>
      <tr>
      	<td><fmt:message key="alWorkCalendar.function"/>&nbsp;<font color="red">(*)</font></td>
      	<td>
			<div id="functionCBB"></div></div><form:errors path="function" cssClass="error"/><form:hidden path="function"/>
      	</td>
      </tr>
      <tr>
     	<td><fmt:message key="alWorkCalendar.emailBefore"/></td>
     	<td>
            <form:input path="emailBefore" maxlength="60" cssClass="wid30"/>
       </td>
     </tr>
     <tr>
     	<td><fmt:message key="alWorkCalendar.description"/></td>
     	<td>
            <form:input path="description" maxlength="200" cssClass="wid30"/>
       </td>
     </tr>
       <tr>
           <td></td>
           <td>
               <input class="button" type="submit" class="button" name="save" value="<fmt:message key="global.form.luulai"/>" />
               <input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm?startDate=${startDate}&endDate=${endDate}&region=${region}"'>
           </td>
       </tr>
    </table>
</form:form>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<script type="text/javascript">
Calendar.setup({
    inputField		:	"day",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseDay",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

function focusIt()
{
	var dayError = '<c:out value="${dayError}"/>';
	var errorDay = '<c:out value="${errorDay}"/>';
	if(document.checkform.email.value==""){
		  var mytext = document.getElementById("email");
		  mytext.focus();
		}
		else if(dayError != "")
			{
			var mytext = document.getElementById("day");
			  mytext.focus();
			}
		else if(errorDay != "")
		{
		var mytext = document.getElementById("day");
		  mytext.focus();
		}
}

onload = focusIt;

</script>
<script type="text/javascript">
var theme = getTheme();
$("#email").jqxInput({ width: '30%', height: 20, minLength: 1, theme: theme });
$("#emailBefore").jqxInput({ width: '30%', height: 20, minLength: 1, theme: theme });
$("#description").jqxInput({ width: '30%', height: 20, minLength: 1, theme: theme });

//Create a jqxMultile Input
var renderer = function (itemValue, inputValue) {
        var terms = inputValue.split(/,\s*/);
        var value = inputValue;
     
         if (inputValue.indexOf(itemValue) < 0)
	    	{
      
        	 // remove the current input
             terms.pop();
             // add the selected item
	    	 terms.push(itemValue);
	         // add placeholder to get the comma-and-space at the end
	         terms.push("");
	         value = terms.join("");
	    	}
       
        return value;
    };
//Input email
${emailUsersList}
$("#email").jqxInput({ placeHolder: "", height: 20, width: '30%', theme: theme,
    source: function (query, response) {
        var item = query.split(/,\s*/).pop();
        // update the search query.
        $("#email").jqxInput({ query: item });
        response(emailUsersList);
    },
    renderer: renderer
});
var email =  "<c:out value='${email}'/>";
if(email!="")
	$('#email').val(email);

//Input emailBefore
$("#emailBefore").jqxInput({ placeHolder: "", height: 20, width: '30%', theme: theme,
    source: function (query, response) {
        var item = query.split(/,\s*/).pop();
        // update the search query.
        $("#emailBefore").jqxInput({ query: item });
        response(emailUsersList);
    },
    renderer: renderer
});
var emailBefore =  "<c:out value='${emailBefore}'/>";
if(emailBefore!="")
	$('#emailBefore').val(emailBefore);




//Create a jqxDropDownList
urlShift = "${pageContext.request.contextPath}/alarm/al-work-calendar/shiftList.htm";
// prepare the data
var sourceShift =
{
    datatype: "json",
    url: urlShift,
    datafields: [
                 { name: 'value'},
                 { name: 'value'}
             ],
    async: false
};
var dataAdapterShift = new $.jqx.dataAdapter(sourceShift);
$("#shiftCBB").jqxDropDownList({source: dataAdapterShift, selectedIndex: 0, displayMember: "value", valueMember: "value", width:'15%', height: 20, theme: theme, enableHover: true, autoOpen: true  });           
var shiftCBB = '<c:out value="${shiftCBB}"/>';
if(shiftCBB!="")
	{
	 $('#shift').val(shiftCBB);
	 $('#shiftCBB').val(shiftCBB);
	}
	
$('#shiftCBB').on('select', function (event) {
    var args = event.args;
    if (args != undefined) {
        var item = event.args.item;
        $('#shift').val(item.value);
    }
});	
/* //Create a jqxComboBox chuc nang
var urlFunction = "${pageContext.request.contextPath}/alarm/al-work-calendar/functionList.htm";
// prepare the data
var sourceFunction =
{
    datatype: "json",
    datafields: [
        { name: 'value' },
        { name: 'value' }
    ],
    url: urlFunction,
    async: false
};
var dataAdapterFunction = new $.jqx.dataAdapter(sourceFunction);
// Create a jqxComboBox
$("#function").jqxDropDownList({ source: dataAdapterFunction, selectedIndex: 0, displayMember: "value", valueMember: "value", width: '15%',height: 20,itemHeight: 30,theme: theme, enableHover: true , autoOpen: true });
var functionCBB =  "<c:out value='${functionCBB}'/>";
if(functionCBB!="")
	$('#function').val(functionCBB); */
	
// Create a jqxComboBox
var urlFunction = "${pageContext.request.contextPath}/alarm/al-work-calendar/functionList.htm";
// prepare the data
var sourceFunction =
{
     datatype: "json",
     datafields: [
         { name: 'value' },
         { name: 'value' }
     ],
     url: urlFunction,
     async: false
};
 var dataAdapterFunction = new $.jqx.dataAdapter(sourceFunction);
 // Create a jqxComboBox
 $("#functionCBB").jqxComboBox({ source: dataAdapterFunction, displayMember: "value", valueMember: "value", width: '15%',height: 20,itemHeight: 30,theme: theme, autoOpen: true });
 var functionCBB =  "<c:out value='${functionCBB}'/>";
 if(functionCBB!=""){
	 $('#function').val(functionCBB);
	 $("#functionCBB").val(functionCBB);
 }
 $('#functionCBB').on('select', function (event) {
	    var args = event.args;
	    if (args != undefined) {
	        var item = event.args.item;
	        $('#function').val(item.value);
	    }
	});	
//create dropbox region
var urlregion = "${pageContext.request.contextPath}/ajax/getRegionList.htm";
//prepare the data
var sourceregion =
{
  datatype: "json",
  datafields: [
      { name: 'name' },
      { name: 'value' }
  ],
  url: urlregion,
  async: false
};
var dataAdapterregion = new $.jqx.dataAdapter(sourceregion);
// Create a jqxComboBox
$("#region").jqxComboBox({ source: dataAdapterregion, displayMember: "value", valueMember: "name", width: '15%',height: 20,itemHeight: 30,theme: theme, autoOpen: true });
var regionCBB =  "<c:out value='${region}'/>";
//alert(regionCBB);
if(regionCBB!=""){
	 $("#region").val(regionCBB);
}
</script>
