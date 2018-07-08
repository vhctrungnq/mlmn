
<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>${titleF}</title>
<content tag="heading">${titleF}</content>

<form:form commandName="monitorSite" method="post" action="siteform.htm"> 
	<form:hidden path="id"/>
	<table class="simple2"> 
      <tr>
           
			<td style="width: 150px;"><fmt:message key="monitorSite.network"/><font color="red">(*)</font></td>
			<td style="width: 250px;">
				<div id='network' ></div><font color="red">${errorNetwork}<form:errors path="network"/></font>
			</td>
			<td style="width: 100px;"><fmt:message key="monitorSite.site"/><font color="red">(*)</font></td>
			<td>
				<div id='site'></div>
				<font color="red">${errorSite}<form:errors path="site"/></font>
			</td>
      </tr> 
      <tr>
   		 	<td><fmt:message key="monitorSite.sdate"/></td>
				<td>
						<input type ="text"  value="${sdate}" name="sdate" id="sdate" size="17" maxlength="19" style="width:150px">
			   			 <img alt="calendar" title="Click to choose the from date" id="chooseSDate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
						<font color="red">${errorsdate}<form:errors path="sdate"/></font>
				</td>
				
				<td ><fmt:message key="monitorSite.edate"/></td>
				<td>
					<input type ="text"  value="${edate}" name="edate" id="edate" size="17" maxlength="19" style="width:150px">
			   		<img alt="calendar" title="Click to choose the to date" id="chooseEDate" style="cursor: pointer; position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
					<font color="red">${erroredate}<form:errors path="edate"/></font>
				</td>
      </tr> 
       <tr>
           <td><fmt:message key="monitorSite.content"/></td>
           <td colspan="3"> 
           	<form:input path="content"  class = "long" rows="10" maxlength="400"/><font color="red">${errorContenty}</font>
          </td>
       </tr>
	    <tr>
           <td><fmt:message key="monitorSite.causeby"/></td>
           <td  colspan="3"> 
           		<form:input type ="text" path="causeby" maxlength="400"  class = "long" rows="3" /><font color="red">${errorcauseby}</font>
          </td>
       </tr>
       
     <tr>
           <td ><fmt:message key="monitorSite.processUser"/></td>
			<td>
				
				<div id='cbprocessUser'></div><form:hidden path="processUser"  id ="processUser"/><font color="red">${errorProcessUser}</font>
			</td>
			<td ><fmt:message key="monitorSite.requierUser"/></td>
			<td>
				<div id='cbrequierUser'></div><form:hidden path="requierUser"  id ="requierUser"/><font color="red">${errorRequierUser}</font>
			</td>
       </tr>
       <tr>
           <td><fmt:message key="monitorSite.description"/></td>
           <td colspan="3"> 
           	<form:input path="description"  class = "long" rows="10" maxlength="400"/><font color="red">${errordescription}</font>
          </td>
       </tr>
       <tr>
           <td></td>
           <td colspan="3">
           		<input type="submit" class="button" id = "submit" value="<fmt:message key="global.form.luulai"/>" />
           	  	<input type="button" class="button" id = "btCancel" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="sitelist.htm"'>	
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
        inputField		:	"sdate",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseSDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
    Calendar.setup({
        inputField		:	"edate",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseEDate",  	// trigger for the calendar (button ID)
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
    $("#causeby").jqxInput({placeHolder: "Enter causeby", height: 25, width: '100%', minLength: 0, maxLength: 400, theme: theme});
  //input alarmInfo
    $("#content").jqxInput({placeHolder: "Enter content", height: 25, width: '100%', minLength: 0, maxLength: 400, theme: theme});
  //input reportProcess
    $("#description").jqxInput({placeHolder: "Enter description", height: 25, width: '100%', minLength: 0, maxLength: 400, theme: theme});
  //ComboBox operator
	${siteList}
	$("#site").jqxDropDownList({ source: siteList, width: 160,height: 20,itemHeight: 30,theme: theme,autoOpen: true,enableHover: true }); 
	var site =  "<c:out value='${monitorSite.site}'/>";
	$("#site").jqxDropDownList('addItem', { label: '', value: ''}, 0 );
	if(site!=null&&site!='')
		{
		$('#site').val(site);
	
		} 
  //network
    var urlnetwork = "${pageContext.request.contextPath}/ajax/getNetworkList.htm";
    // prepare the data
    var sourcenetwork =
    {
        datatype: "json",
        datafields: [
            { name: 'value' },
            { name: 'name' }
        ],
        url: urlnetwork,
        async: false
    };
    var dataAdapternetwork = new $.jqx.dataAdapter(sourcenetwork);
    $("#network").jqxComboBox({ source: dataAdapternetwork, displayMember: "value", valueMember: "name",width: 160,height: 20,itemHeight: 30,theme: theme,autoDropDownHeight: true,autoOpen: true  });
     var network =  "<c:out value='${monitorSite.network}'/>";
	       if(network!="") {
			$('#network').val(network);
	       }
	       else
 	   {
 	   	$('#network').val('ALL');
 	   }
	});
	$("#network").change(function(){
	   var username = "<c:out value='${username}'/>";
	   var network = $('#network').val();
		if (network=='ALL')
		{
			network='';
		}
		
	   $.getJSON("${pageContext.request.contextPath}/ajax/getSiteByNetwork.htm",{network:network,bscid: "", district:"",username:""}, function(j){
		   var siteList=[];
		   siteList =j;
		   $("#site").jqxDropDownList({source: siteList});        
	   });
	});		
	
	// ComboBox user
    var urlUser = "";
	urlUser = "${pageContext.request.contextPath}/ajax/getUserByDeparment.htm?dept=&team=";
 	// prepare the data
    var sourceUser =
    {
        datatype: "json",
        url: urlUser,
        datafields: [
                     { name: 'username'},
                     { name: 'fullname'}
                 ],
        async: false
    };
    var dataAdapterUser = new $.jqx.dataAdapter(sourceUser);
    var dataAdapterUser1 = new $.jqx.dataAdapter(sourceUser);
    $("#cbprocessUser").jqxDropDownList({source: dataAdapterUser1, displayMember: "username", valueMember: "username",checkboxes: true, width: 160, height: 20, theme: theme,autoOpen: true,enableHover: true });           
    var processUser = '<c:out value="${monitorSite.processUser}"/>';
    
    if(processUser=="")
    	{
		$('#cbprocessUser').val('Choose');
    	}
	else
	{

		var processUserVar = processUser.split(",");
		if (processUserVar != null) {
			for (var i=0; i<processUserVar.length; i++) {
				$("#cbprocessUser").jqxDropDownList('checkItem', processUserVar[i] ); 
			}
		}
	}
    $("#cbprocessUser").on('checkChange', function (event) {
        if (event.args) {
            var item = event.args.item;
            if (item) {
                var items = $("#cbprocessUser").jqxDropDownList('getCheckedItems');
                var checkedItems = "";
                $.each(items, function (index) {
                    checkedItems += this.value + ", ";                          
                });
                $("#processUser").val(checkedItems);
            }
        }
    });
    //combobox require user
    $("#cbrequierUser").jqxDropDownList({source: dataAdapterUser, displayMember: "username", valueMember: "username",checkboxes: true, width: 160, height: 20, theme: theme,autoOpen: true,enableHover: true });           
    var requierUser = '<c:out value="${monitorSite.requierUser}"/>';
    
    if(requierUser=="")
		$('#cbrequierUser').val('Choose');
	else
	{
		var requierUserVar = requierUser.split(",");
		if (requierUserVar != null) {
			for (var i=0; i<requierUserVar.length; i++) {
				$("#cbrequierUser").jqxDropDownList('checkItem', requierUserVar[i] ); 
			}
		}
	}
    $("#cbrequierUser").on('checkChange', function (event) {
        if (event.args) {
            var item = event.args.item;
            if (item) {
                var items = $("#cbrequierUser").jqxDropDownList('getCheckedItems');
                var checkedItems = "";
                $.each(items, function (index) {
                    checkedItems += this.value + ", ";                          
                });
                $("#requierUser").val(checkedItems);
            }
        }
    });
</script>
</body>
</html>