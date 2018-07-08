<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxwindow.js"></script>

<title>${titleForm}</title>
<content tag="heading">${titleForm}</content> 	   
<form:form commandName="alarmLog" id="myform" method="post" action="form.htm?netWork=${netWork}&function=${function}"> 
	<form:hidden path="id"/>
	<table class="simple2"> 
      <tr>
           <td style="width: 100px;"><fmt:message key="alarmLog.bscid"/></td>
			<td style="width: 300px;">
				 <form:hidden path='ne'/><span style="font-weight: bold;">${bscid}</span>
			</td>
			<td style="width: 120px;"><fmt:message key="alarmLog.site"/></td>
			<td style="width: 250px;">
				<form:hidden path='site'/><span style="font-weight: bold;">${alarmLog.site}</span>
			</td>
			<td style="width: 150px;"><fmt:message key="alarmLog.cellid"/></td>
			<td>
				<form:hidden path='cellid'/><span style="font-weight: bold;">${alarmLog.cellid}</span>
			</td>
      </tr> 
		<tr>
           <td><fmt:message key="alarmLog.sdate"/></td>
			<td style="width:150px">
				<form:hidden path='sdate'/><span style="font-weight: bold;">${sdate}</span>
			</td>
			<td><fmt:message key="alarmLog.vendor"/></td>
			<td>
				<form:hidden path='vendor'/><span style="font-weight: bold;">${alarmLog.vendor}</span>
			</td>
			<td><fmt:message key="alarmLog.severity"/></td>
			<td>
				<form:hidden path='severity'/><span style="font-weight: bold;">${alarmLog.severity}</span>
			</td>
			
			
         </tr>
         <tr>
       		<td><fmt:message key="alarmLog.alarmType"/></td>
			<td>
				<form:hidden path='alarmType'/><span style="font-weight: bold;">${alarmLog.alarmType}</span>
			</td>
			<td><fmt:message key="alarmLog.alarmMappingId"/></td>
			<td>
				<form:hidden path="alarmMappingId" /><span style="font-weight: bold;">${alarmLog.alarmMappingId}</span>
			</td>
			<td><fmt:message key="alarmLog.alarmMappingName"/></td>
			<td>
				<form:hidden path="alarmMappingName"/><span style="font-weight: bold;">${alarmLog.alarmMappingName}</span>
			</td>
		</tr>	
      <tr>
      		<td><fmt:message key="alarmLog.alarmName"/></td>
			<td>
				<form:hidden path="alarmName"/><span style="font-weight: bold;">${alarmLog.alarmName}</span>
			</td>
      		<td><fmt:message key="alarmLog.team"/></td>
			<td>
				<form:hidden path="team"/><span style="font-weight: bold;">${alarmLog.team}</span>
			</td>
      		<td><fmt:message key="alarmLog.district"/></td>
			<td>
				<form:hidden path="district"/><span style="font-weight: bold;">${alarmLog.district}</span>
			</td>
			
      </tr>     
      <tr>
      		<td><fmt:message key="alarmLog.edate"/></td>
			<td > 
				<div><input type ="text"  value="${edate}" name="edate" id="edate" size="17" maxlength="16" style="width:100px">
			   	<img alt="calendar" title="Click to choose the to date" id="chooseEDate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</div>
				<font color="red"><form:errors path="edate"/></font>
				<!-- <div id='edate'></div> -->
			</td>
			<td  style="padding-left: 10px;width:120px;"><fmt:message key="vAlAlarmLog.mdSdate"/></td>
           <td >
           		<input type ="text"  value="${mdSdate}" name="mdSdate" id="mdSdate" size="20" maxlength="16" class = "edate">
	   			 <img alt="calendar" title="Click to choose the start date" id="choosemdSdate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red">${errormdSdate}<form:errors path="mdSdate"/></font>
	    	</td>
      		<td><fmt:message key="alarmLog.ackTime"/></td>
			<td>
				<div><input type ="text"  value="${ackTime}" name="ackTime" id="ackTime" size="17" maxlength="16" style="width:100px">
			   	<img alt="calendar" title="Click to choose the ack time" id="chooseAckTime" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</div>
				<font color="red"><form:errors path="ackTime"/></font><!-- <div id='ackTime'></div> -->
			</td>
			
			
			
       </tr>   
       
        <tr>
       		 <td><fmt:message key="alarmLog.ackUser"/></td>
			<td>
				<div id='cbackUser'></div><form:hidden path="ackUser"  id ="ackUser"/><font color="red">${errorackUser}</font>
			</td>
        	<td><fmt:message key="alarmLog.causebySys"/></td>
			<td>
				<div id='causebySys' style = "width: 100%"></div>
				
			</td>
        	<td><fmt:message key="alarmLog.ackStatus"/></td>
			<td>
				<div id='ackStatus'></div>
			</td>
			
		</tr>
		<tr>
			<td><fmt:message key="alarmLog.statusView"/></td>
			<td>
				<div id='statusView'></div>
			</td>
			<td><fmt:message key="alarmLog.causeby"/></td>
			<td colspan="3">
				<form:input type="text" path="causeby"  id ="causeby"/>
				<input type="button" class="button" value="?" id="btSearch" name="btSearch" title="Handling library" style="width: 10px;height: 15px;"/>
       			<font color="red">${errorcauseby}<form:errors path="causeby"/></font>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="alarmLog.actionProcess"/></td>
			<td colspan="5">
				<form:input type="text" path="actionProcess" id ="actionProcess" />
				<font color="red">${erroractionProcess}<form:errors path="actionProcess"/></font>
			</td>
		</tr>
		<tr>
           <td><fmt:message key="alarmLog.description"/></td>
           <td colspan="5">
				<form:input type="text" path="description"  id ="description" />
				<font color="red">${errordescription}<form:errors path="description"/></font>
		   </td>
       </tr>
     
      <tr>
           <td></td>
           <td colspan="5">
               	<input type="submit" class="button" id="submit" value="<fmt:message key="global.form.luulai"/>" />
           	  	<input type="button" class="button"  id="btSaveAction" value="<fmt:message key="global.form.saveAction"/>">
				<input type="button" class="button" id="btCancelTK" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="${function}.htm?netWork=${netWork}"'>
		 </td>
       </tr>
    </table>

</form:form>
<div id="jqxwindow">
		<div><fmt:message key="handingLibrary.titleAdd"/></div>
		<div>
       	<table class="simple2">
       		<tr>
       			<td style="width:70px;"><fmt:message key="alarmLog.alarmType"/></td>
				<td>
					<span style="font-weight: bold;">${alarmLog.alarmType}</span>
				</td>
				<td style="width:120px;"><fmt:message key="alarmLog.alarmMappingId"/></td>
				<td>
					<span style="font-weight: bold;">${alarmLog.alarmMappingId}</span>
				</td>
				<td style="width:150px;"><fmt:message key="alarmLog.alarmMappingName"/></td>
				<td>
					<span style="font-weight: bold;">${alarmLog.alarmMappingName}</span>
				</td>
       		</tr>
       		<tr>
       			<td style="width:100px;"><fmt:message key="handingLibrary.causeby"/></td>
       			<td colspan="5"><input type ="text"  name="causebyH" id="causebyH" size="20" maxlength="80">
       			</td>
       		</tr>
       		<tr>
       			<td><fmt:message key="handingLibrary.actionProcess"/></td>
       			<td colspan="5"><input type ="text"  name="actionProcessH" id="actionProcessH" size="20" maxlength="80" width="80px"></td>
       		</tr>
       		<tr>
       			<td><fmt:message key="handingLibrary.description"/></td>
       			<td colspan="5"><input type ="text"  name="descriptionH" id="descriptionH" size="20" maxlength="80" width="80px"></td>
       		</tr>
       		<tr>
       			<td></td>
       			<td colspan="5">
       				<input type="button" class="button" value="Save" id="btSave" />
                    <input type="button" class="button" value="Cancel" id="btCancel" />
                </td>
       		</tr>
       </table>
     </div>
</div>
      
<div id="jqxwindowGrid">
<div><fmt:message key="handingLibrary.titleList"/></div>
<div>
<%@ include file="/includeJQ/gridSimple.jsp" %> 
<div style="float: right;padding-top: 10px;"><input type="button" class="button" value="Cancel" id="btCancelTK" /></div>
</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script type="text/javascript">
Calendar.setup({
    inputField		:	"ackTime",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
    button			:   "chooseAckTime",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 

Calendar.setup({
    inputField		:	"edate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
    button			:   "chooseEDate",   	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
}); 	
Calendar.setup({
    inputField		:	"mdSdate",	// id of the input field
    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
    button			:   "choosemdSdate",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
});
</script>
<script type="text/javascript">
var theme =  getTheme();
$('#btSearch').jqxButton({ theme: theme });
$('#btSaveAction').jqxButton({ theme: theme });
$('#submit').jqxButton({ theme: theme });
$('#btCancelTK').jqxButton({ theme: theme });
$('#btSave').jqxButton({ theme: theme });
$('#btCancel').jqxButton({ theme: theme });   
$('#btCancelTK').jqxButton({ theme: theme });   
$(document).ready(function(){
	// Khai bao sdate, edate
	
	/* var edate =  "<c:out value='${edate}'/>";
	var ackTime =  "<c:out value='${ackTime}'/>";
	// alert(sdate+"-"+edate);
	$("#edate").jqxDateTimeInput({ width: '150px', height: '25px',  formatString: 'dd/MM/yyyy HH:mm' });
	$("#ackTime").jqxDateTimeInput({ width: '150px', height: '25px',  formatString: 'dd/MM/yyyy HH:mm' });

	$('#inputedate').val(edate);
	$('#inputackTime').val(ackTime);
	 */
	//ComboBox district
 	/* ${districtList}
 	$("#district").jqxDropDownList({ source: districtList, width: 180,height: 25,itemHeight: 30,theme: theme ,autoOpen: true,enableHover: true}); 
 	$("#district").jqxDropDownList('insertAt', { label: '', value: ''}, 0 );
 	var district =  "<c:out value='${alarmLog.district}'/>";
 	if(district!=null&&district!="")
		$('#district').val(district); */
    // combobox statusView
    var urlStatusView = "${pageContext.request.contextPath}/ajax/getStatusViewList.htm";
     var sourceStatusView =
    {
        datatype: "json",
        datafields: [
            { name: 'value'},
            { name: 'name'}
        ],
        url: urlStatusView,
        async: false
    };
    var dataAdapterStatusView = new $.jqx.dataAdapter(sourceStatusView);
    $("#statusView").jqxDropDownList({ source: dataAdapterStatusView, displayMember: "value", valueMember: "name", width: 180,height: 25,theme: theme,autoDropDownHeight: true,autoOpen: true  });
    $("#statusView").jqxDropDownList('insertAt', { label: '', value: ''}, 0 );
    var statusView =  "<c:out value='${alarmLog.statusView}'/>";
    if(statusView!=null&&statusView!="")
		$('#statusView').val(statusView);
    
    // combobox ackStatus
    var urlAckStatus = "${pageContext.request.contextPath}/ajax/getAckStatusList.htm";
     var sourceAckStatus =
    {
        datatype: "json",
        datafields: [
            { name: 'value'},
            { name: 'name'}
        ],
        url: urlAckStatus,
        async: false
    };
    var dataAdapterAckStatus = new $.jqx.dataAdapter(sourceAckStatus);
    $("#ackStatus").jqxDropDownList({ source: dataAdapterAckStatus, displayMember: "value", valueMember: "name", width: 180,height: 25,theme: theme ,autoDropDownHeight: true,autoOpen: true });
    $("#ackStatus").jqxDropDownList('insertAt', { label: '', value: ''}, 0 );
    var ackStatus =  "<c:out value='${alarmLog.ackStatus}'/>";
    if(ackStatus!=null&&ackStatus!="")
		$('#ackStatus').val(ackStatus);
    //Create a jqxDropDownList causeby sys
   	var urlCausebySys = "${pageContext.request.contextPath}/ajax/getCausebySys.htm";
    // prepare the data
    var sourceCausebySys =
    {
        datatype: "json",
        datafields: [
            { name: 'value' },
            { name: 'name' }
        ],
        url: urlCausebySys,
        async: false
    };
    var dataAdapterCausebySys = new $.jqx.dataAdapter(sourceCausebySys);
    $("#causebySys").jqxDropDownList({ source: dataAdapterCausebySys, displayMember: "value", valueMember: "name", width: 250,height: 25,theme: theme,autoOpen: true });
    $("#causebySys").jqxDropDownList('insertAt', { label: '', value: ''}, 0 );
    var causebySys =  "<c:out value='${alarmLog.causebySys}'/>";
    if(causebySys!=null&&causebySys!="")
		$('#causebySys').val(causebySys);
 	/* // ComboBox Team
    ${teamList}
 	$("#team").jqxDropDownList({ source: teamList, width: 180,height: 25,itemHeight: 30,theme: theme ,autoOpen: true,enableHover: true}); 
 	$("#team").jqxDropDownList({ source: teamList, width: 180,height: 25,itemHeight: 30,theme: theme ,autoDropDownHeight: true,autoOpen: true,enableHover: true}); 
  	var team =  "<c:out value='${alarmLog.team}'/>";
  	if(team!=null&&team!="")
		$('#team').val(team); */
 	
 	// ComboBox user
    //var urlUser = "${pageContext.request.contextPath}/ajax/getUserByDeparment.htm?dept=&team="+$('#team').val();
		 var urlUser = "${pageContext.request.contextPath}/ajax/getUserByDeparment.htm?dept=&team=";
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
    $("#cbackUser").jqxDropDownList({source: dataAdapterUser, displayMember: "username", valueMember: "username",checkboxes: true, width: 180, height: 25, theme: theme ,autoOpen: true});           
    var ackUser =  "<c:out value='${alarmLog.ackUser}'/>";
    if(ackUser=="")
		$('#cbackUser').val('Choose');
	else
	{
		var userProcessVar = ackUser.split(",");
		if (userProcessVar != null) {
			for (var i=0; i<userProcessVar.length; i++) {
				$("#cbackUser").jqxDropDownList('checkItem', userProcessVar[i] ); 
			}
		}
	}
    $("#cbackUser").on('checkChange', function (event) {
        if (event.args) {
            var item = event.args.item;
            if (item) {
                var items = $("#cbackUser").jqxDropDownList('getCheckedItems');
                var checkedItems = "";
                $.each(items, function (index) {
                    checkedItems += this.value + ", ";                          
                });
                $("#ackUser").val(checkedItems);
            }
        }
    });
  	//input causeby
    $("#causeby").jqxInput({placeHolder: "Enter causeby", height: 25, width: '95%', minLength: 0, maxLength: 200, theme: theme});
  	//input actionProcess
    $("#actionProcess").jqxInput({placeHolder: "Enter action process", height: 25, width: '100%', minLength: 0, maxLength: 350, theme: theme});
  	//input description
    $("#description").jqxInput({placeHolder: "Enter description", height: 25, width: '100%', minLength: 0, maxLength: 350, theme: theme});
    // create jqxWindow.
    $("#jqxwindow").jqxWindow({ resizable: true, theme: theme, autoOpen: false, width: 800, minHeight: 250,isModal: true});
    //input causeby
    $("#causebyH").jqxInput({placeHolder: "Enter causeby", height: 30, width: '100%', minLength: 0, maxLength: 80, theme: theme});
  	//input actionProcess
    $("#actionProcessH").jqxInput({placeHolder: "Enter action process", height: 30, width: '100%', minLength: 0, maxLength: 150, theme: theme});
  	//input description
    $("#descriptionH").jqxInput({placeHolder: "Enter description", height: 30, width: '100%', minLength: 0, maxLength: 150, theme: theme});
 	// create jqxWindow.
    $("#jqxwindowGrid").jqxWindow({ resizable: true, theme: theme, autoOpen: false,minWidth: 1000, maxWidth: 1800,minHeight: 300,isModal: true});
    
    $('#jqxgrid').on('rowclick', function (event) 
    		{
    		    var rowindex = args.rowindex;
    		    //alert(rowindex);
    		    var row = $("#jqxgrid").jqxGrid('getrowdata', rowindex);
    		    $('#causeby').val(row.causeby);
    		    $('#actionProcess').val(row.actionProcess);
    		    $('#description').val(row.description);
    		});
});

$("#btSaveAction").click(function (event) {
   	$("#jqxwindow").jqxWindow('open');
    $("#causebyH").val($("#causeby").val());
    $("#actionProcessH").val($("#actionProcess").val());
    $("#descriptionH").val($("#description").val());
});

$("#btCancel").click(function (event) {
    $("#jqxwindow").jqxWindow('close');
});


$("#btSave").click(function (event) {
	var handling = {};
	handling.vendor    = $("#vendor").val();
	var alarmId = "<c:out value='${alarmLog.alarmId}'/>";
	if (alarmId!='')
		{
			handling.alarmId = parseInt(alarmId);
		}
	
	handling.alarmMappingId    = "<c:out value='${alarmLog.alarmMappingId}'/>";
	handling.alarmMappingName    = "<c:out value='${alarmLog.alarmMappingName}'/>";
	handling.alarmName = "<c:out value='${alarmLog.alarmName}'/>";
	handling.alarmInfo = "<c:out value='${alarmLog.alarmInfo}'/>";
	handling.causeby = $("#causebyH").val();
	handling.actionProcess = $("#actionProcessH").val();
	handling.description = $("#descriptionH").val();
	handling.alarmType ="<c:out value='${alarmLog.alarmType}'/>";
	 	
	$.ajax({
	    type: "POST",
	    url: "${pageContext.request.contextPath}/alarmLog/insertHanding.htm",
	    data: JSON.stringify(handling),
	    dataType: 'json',
	    contentType: 'application/json',
	    mimeType: 'application/json',
	    beforeSend: function(){
	    	$("#btSave").prop('disabled', true);
	    },
	    complete: function(){
	    	$("#btSave").prop('disabled', false);
	    },
	    success: function(data){
	    	$("#jqxwindow").jqxWindow('close');
             var row = {};
             row["vendor"] = handling.vendor ;
             row["alarmId"] = handling.alarmId ;
             row["alarmMappingId"] = handling.alarmMappingId ;
             row["alarmMappingName"] = handling.alarmMappingName ;
             row["alarmInfo"] = handling.alarmInfo ;
             row["causeby"] =handling.causeby ;
             row["actionProcess"] = handling.actionProcess ;
             row["description"] = handling.description ;
             row["alarmType"] = handling.alarmType ;
             row["alarmName"] = handling.alarmName ;
             var commit = $("#jqxgrid").jqxGrid('addrow', null, row);
	    }
	});
   
});

$("#btSearch").click(function (event) {
    $('#jqxgrid').jqxGrid('refreshdata');
    $("#jqxwindowGrid").jqxWindow('open');
});
$("#btCancelTK").click(function (event) {
    $("#jqxwindowGrid").jqxWindow('close');
});

</script>