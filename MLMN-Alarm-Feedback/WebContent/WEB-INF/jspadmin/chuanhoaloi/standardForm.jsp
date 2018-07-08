<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<title><fmt:message key="${titleSystem}"/></title>
<content tag="heading"><fmt:message key="${titleSystem}"/></content>
  
<form:form commandName="standardForm" name="checkform" method="post" action="form.htm">
	<form:hidden path="id"/>
	<table class="simple2">
		<tr>
			<td class="wid10 "><b><fmt:message key="chuanhoaloi.vendor"/></b>&nbsp;<font color = "red">(*)</font></td>
			<td>
				<div id='vendor'></div>
				&nbsp;<font color = "red">${evendor}</font>
			</td>
		</tr>
		<tr>
			<td><b><fmt:message key="chuanhoaloi.network"/></b>&nbsp;<font color = "red">(*)</font></td>
			<td>
				<div id='network' ></div>
				&nbsp;<font color = "red">${enetwork}</font>
			</td>
		</tr>	
		
		<tr>
			<td><b><fmt:message key="chuanhoaloi.alarmName"/></b></td>
			<td>
				<div id='alarmName' style = "width: 100%"></div>
				&nbsp;<form:errors path="alarmName" cssClass="error"/>
			</td>
		</tr>	
		<tr>
			<td><b><fmt:message key="chuanhoaloi.alarmNameMapping"/></b>&nbsp;<font color = "red">(*)</font></td>
			<td>
				<form:input path="alarmNameMapping" style = "width: 450px;"/> 
				&nbsp;<font color = "red">${ealarmNameMapping}</font>
			</td>
		</tr>	
		
		<tr>
			<td>
				<b><fmt:message key="chuanhoaloi.description"/></b>
			</td>
			<td>
				<form:input path="description" style = "width: 450px;"/> 
			</td>
		</tr>
		<tr>
			<td><b><fmt:message key="chuanhoaloi.ordering"/></b></td>
			<td>
				<form:input path="ordering" class="wid10" maxlength="4"/>
				&nbsp;<form:errors path="ordering" cssClass="error"/>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
       			 <input type="submit" class="button" name="save" value="<fmt:message key="global.form.luulai"/>">  
       			 <input type="button" class="button" onclick='window.location="danh-sach.htm?typeCode=${typeCode}"' value="<fmt:message key="global.form.huybo"/>" >
			</td>
		</tr>
	</table>
</form:form>
	
<script type="text/javascript">
var theme =  getTheme();
//Create a jqxComboBox vendor
	var urlVendor = "${pageContext.request.contextPath}/ajax/getVendor.htm";
// prepare the data
var sourceVendor =
{
    datatype: "json",
    datafields: [
        { name: 'value' },
        { name: 'name' }
    ],
    url: urlVendor,
    async: false
};
var dataAdapterVendor = new $.jqx.dataAdapter(sourceVendor);
// Create a jqxComboBox
$("#vendor").jqxComboBox({ source: dataAdapterVendor, displayMember: "value", valueMember: "name", width: 160,height: '20px',itemHeight: 30,theme: theme,autoDropDownHeight: true,autoOpen: true });
var vendor =  "<c:out value='${standardForm.vendor}'/>";
if(vendor=="")
	$('#vendor').val('');
else
	$('#vendor').val(vendor);
	
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
 var network =  "<c:out value='${standardForm.network}'/>";
       if(network!="") {
		$('#network').val(network);
       }
   var vendor = $('#vendor').val();
   var network = $('#network').val();
$("#network").change(function(){
	network = $('#network').val();
	var urlAlarmName = "${pageContext.request.contextPath}/ajax/getAlarmName.htm?vendor="+vendor+"&alarmType=&network="+network;
    // prepare the data
    var sourceAlarmName =
    {
        datatype: "json",
        datafields: [
            { name: 'blockValue'}
        ],
        url: urlAlarmName,
        async: false
    };
    var dataAdapterAlarmName = new $.jqx.dataAdapter(sourceAlarmName);
    // Create a jqxComboBox
    $("#alarmName").jqxComboBox({ source: dataAdapterAlarmName, displayMember: "blockValue", valueMember: "blockValue", width: 450, height: 20, theme: theme,autoOpen: true });
    var alarmName =  "<c:out value='${standardForm.alarmName}'/>";
    if(alarmName=="")
		$('#alarmName').val('');
	else
		$('#alarmName').val(alarmName);
});	
//alarm name
var urlAlarmName = "${pageContext.request.contextPath}/ajax/getAlarmName.htm?vendor="+vendor+"&alarmType=&network="+network;
// prepare the data
var sourceAlarmName =
{
    datatype: "json",
    datafields: [
        { name: 'blockValue'}
    ],
    url: urlAlarmName,
    async: false
};
var dataAdapterAlarmName = new $.jqx.dataAdapter(sourceAlarmName);
// Create a jqxComboBox
$("#alarmName").jqxComboBox({ source: dataAdapterAlarmName, displayMember: "blockValue", valueMember: "blockValue", width: 450, height: 20, theme: theme,autoOpen: true  });
var alarmName =  "<c:out value='${standardForm.alarmName}'/>";
if(alarmName=="")
	$('#alarmName').val('');
else
	$('#alarmName').val(alarmName);
	
	

</script>