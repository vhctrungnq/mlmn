
<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>${titleF}</title>
<content tag="heading">${titleF}</content>

<form:form commandName="monitorSite" method="post" action="temperatureForm.htm"> 
	<form:hidden path="id"/>
	<input type="hidden" name="note" id="note" value="${note}">
	<input type="hidden" name="region" id="region" value="${region}">
	<table class="simple2"> 
      <tr>
   		 	<td style="width: 150px;"><fmt:message key="monitorSite.checkDate"/><font color="red">(*)</font></td>
			<td style="width: 250px;">
					<input type ="text"  value="${checkDate}" name="checkDate" id="checkDate" size="17" maxlength="19" style="width:130px">
		   			 <img alt="calendar" title="Click to choose the from date" id="chooseSDate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
					<font color="red">${errorsdate}<form:errors path="checkDate"/></font>
			</td>
			<td style="width: 150px;"><fmt:message key="monitorSite.site"/><font color="red">(*)</font></td>
			<td style="width: 300px;">
				<form:input path="site" maxlength="40"/>
				<font color="red">${errorSite}<form:errors path="site"/></font>
			</td>
			 <td style="width: 150px;"><fmt:message key="monitorSite.phoneNumber"/></td>
           <td> 
           	<form:input path="phoneNumber"  class = "long" rows="10" maxlength="20"/><font color="red">${errorphoneNumber}</font>
          </td>	
      </tr> 
       <tr>
          
          <td><fmt:message key="monitorSite.temperature"/></td>
           <td > 
           	<form:input path="temperature" maxlength="20"/><sup>o</sup>C
           	<font color="red"><br/><form:errors path="temperature"/></font>
          </td>
          <td ><fmt:message key="monitorSite.humidity"/></td>
           <td > 
           	<form:input path="humidity" maxlength="20"/>%<font color="red"><br/><form:errors path="humidity"/></font>
          </td>
           <td><fmt:message key="monitorSite.statusFridge"/></td>
           <td> 
           	<form:input path="statusFridge" maxlength="150"/><font color="red">${errorstatusFridge}</font>
          </td>
       </tr>
       <tr>
           <td><fmt:message key="monitorSite.statusElectricity"/></td>
           <td> 
           	<form:input path="statusElectricity"   maxlength="150"/><font color="red">${errorstatusElectricity}</font>
          </td>
           <td><fmt:message key="monitorSite.statusCamera"/></td>
           <td> 
           	<form:input path="statusCamera"   maxlength="150"/>
          </td>
          <td ><fmt:message key="monitorSite.contactUser"/></td>
			<td>
				<form:input path="contactUser"  maxlength="150"/>
				<%-- <div id='cbcontactUser'></div><form:hidden path="contactUser"  id ="contactUser"/> --%>
				<font color="red">${errorcontactUser}</font>
			</td>
       </tr>
	   
     <tr>
			<td><fmt:message key="monitorSite.description"/></td>
           <td  colspan="5"> 
           	<form:input path="description"  class = "long" rows="10" maxlength="400"/><font color="red">${errordescription}</font>
          </td>
       </tr>
       <tr>
           <td></td>
           <td colspan="5">
           		<input type="submit" class="button" id="submit" value="<fmt:message key="global.form.luulai"/>" />
           	  	<c:choose>
           			<c:when test="${note == 'caTruc'}">
		                   <input type="button" class="button" id = "btCancel" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="${pageContext.request.contextPath}/caTruc/list.htm"'>
						
		                </c:when>
		                <c:when test="${note == 'filter'}">
			                   <input type="button" class="button" id="btCancel" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="${pageContext.request.contextPath}/caTruc/listFilter.htm"'>
							
			             </c:when>
						<c:otherwise>
							 <input type="button" class="button" id = "btCancel" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="temperaturelist.htm"'>
						</c:otherwise>
					</c:choose>	
           </td>
       </tr>
    </table>
</form:form>
<style>
    .error {
    	color: red;
    }
</style> 

 <script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>

 
<script type="text/javascript">
    Calendar.setup({
        inputField		:	"checkDate",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseSDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
</script>
<script type="text/javascript">
var theme =  getTheme(); 
$('#submit').jqxButton({ theme: theme });
$('#btCancel').jqxButton({ theme: theme });

$(document).ready(function(){
	
	 //input causeby
    $("#phoneNumber").jqxInput({ height: 20, width: '97%', minLength: 0, maxLength: 20, theme: theme});
  //input alarmInfo
    $("#temperature").jqxInput({ height: 20, width: 100, minLength: 0, maxLength: 20, theme: theme});
  //input reportProcess
    $("#humidity").jqxInput({ height: 20, width: 100, minLength: 0, maxLength: 20, theme: theme});
 //input statusFridge
  $("#statusFridge").jqxInput({height: 20, width: '97%', minLength: 0, maxLength: 150, theme: theme});
//input statusElectricity
  $("#statusElectricity").jqxInput({ height: 20, width: '97%', minLength: 0, maxLength: 150, theme: theme});
 //input description
 $("#description").jqxInput({ height: 20, width: '98%', minLength: 0, maxLength: 200, theme: theme});
 $("#statusCamera").jqxInput({ height: 20, width: '97%', minLength: 0, maxLength: 150, theme: theme});
  //ComboBox operator
	${siteList}
	$("#site").jqxInput({ source: siteList, width: '94%',height: 20,maxLength:40, minLength:1,theme: theme}); 
	var site =  "<c:out value='${monitorSite.site}'/>";
	if(site!=null&&site!='')
		{
		$('#site').val(site);
	
		} 
	//input description
	 $("#contactUser").jqxInput({ height: 20, width: '98%', minLength: 0, maxLength: 200, theme: theme});
	 
	/* // ComboBox user
    var urlUser = "";
	urlUser = "${pageContext.request.contextPath}/ajax/getContactUser.htm";
 	// prepare the data
    var sourceUser =
    {
        datatype: "json",
        url: urlUser,
        datafields: [
                     { name: 'fullname'}
                 ],
        async: false
    };
    var dataAdapterUser = new $.jqx.dataAdapter(sourceUser);
    $("#cbcontactUser").jqxDropDownList({source: dataAdapterUser, displayMember: "fullname", valueMember: "fullname",checkboxes: true, width: 250, height: 20, theme: theme,autoOpen: true,enableHover: true });           
   var contactUser = '<c:out value="${monitorSite.contactUser}"/>';
    
    if(contactUser=="")
		$('#cbcontactUser').val('Choose');
	else
	{
		var contactUserVar = contactUser.split(",");
		if (contactUserVar != null) {
			for (var i=0; i<contactUserVar.length; i++) {
				$("#cbcontactUser").jqxDropDownList('checkItem', contactUserVar[i] ); 
			}
		}
	}
    $("#cbcontactUser").on('checkChange', function (event) {
        if (event.args) {
            var item = event.args.item;
            if (item) {
                var items = $("#cbcontactUser").jqxDropDownList('getCheckedItems');
                var checkedItems = "";
                $.each(items, function (index) {
                    checkedItems += this.value + ", ";                          
                });
                $("#contactUser").val(checkedItems);
            }
        }
    }); */
});
</script>
</body>
</html>