<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<title><fmt:message key="title.alManageOnAir.alManageOnAirlist"/></title>
<content tag="heading"><fmt:message key="title.alManageOnAir.alManageOnAirlist"/></content>

<div>
	<input id="strWhere" name="strWhere" value="" type="hidden"/>
	<input id="sortfield" name="sortfield" value="" type="hidden"/>
	<input id="sortorder" name="sortorder" value="" type="hidden"/>
</div>
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left"><form:form commandName="filter" method="post" action="list.htm">
					<table width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td class="wid8 mwid90">
								<fmt:message key="alManageOnAir.projectCode"/>
							</td>
							<td class="wid15">
								<input type="text" id="projectCode" name="projectCode" value="${projectCode}" />
			           		</td>
							<td class="wid7 mwid70"><fmt:message key="alManageOnAir.network"/></td>
							<td class="wid15">
								<div id='network'></div>
							</td>
							
							<td class="wid6 mwid40"><fmt:message key="alManageOnAir.ne"/></td>
							<td class="wid15">
								<input type="text" id="ne" name="ne" value="${neName}" />
							</td>
							<td class="wid8 mwid70"><fmt:message key="alManageOnAir.siteid"/></td>
							<td class="wid15">
								<input type="text" id="siteid" name="siteid" value="${siteid}" />
							</td>	
							<td><input class="button" id="btSearch" type="submit" name="filter"
								value="<fmt:message key="global.form.timkiem"/>" /></td>
						</tr>			
					</table>
				</form:form>
				</td>
				<td class="wid6">
				</td> 
		</tr>
		<tr>
			<td colspan="2">
				<div id='jqxWidget' style="margin-top:5px;">
			    	<div style="float: right;margin-bottom:3px;width:200px;">
			        	<table border="0" cellspacing="1" cellpadding="0" width="100%">
							<tr>
								<td align="right"><input type="button" value="<fmt:message key="global.button.addNew"/>" id='addNew' />&nbsp;
			      					<input type="button" value="<fmt:message key="global.button.import"/>" id='importFile' /></td>
								<td><div style="float: right;" id="jqxlistbox"></div></td>
							</tr>
						</table>
			        </div><br>
			    	
			        <div id="jqxgrid"></div>
			        <div id='Menu'>
			            <ul>
							<li><fmt:message key="global.button.addNew"/></li>
				            <li><fmt:message key="global.button.editSelected"/></li>
					   		<li><fmt:message key="global.button.deleteSelected"/></li>
					   		<li><fmt:message key="global.button.exportExcel"/></li>
				        </ul>
			       </div>
			    </div>
			</td>
		</tr>
</table>
<script type="text/javascript">
var theme = getTheme();
${gridManage}
var projectCode = '<c:out value="${projectCode}"/>';

$("#projectCode").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
$("#ne").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
$("#siteid").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
$("#btSearch").jqxButton({theme: theme});
$('#addNew').jqxButton({ theme: theme });
$('#addNew').click(function () {
	if(projectCode != "")
		window.location = 'form.htm?projectCode='+projectCode;
	else
		window.location = 'form.htm';
});
$('#importFile').jqxButton({ theme: theme });
$('#importFile').click(function () {
	if(projectCode != "")
		window.location = 'upload.htm?projectCode='+projectCode;
	else
		window.location = 'upload.htm';
});
$('#Menu').on('itemclick', function (event) {
	var args = event.args;
	var rowindex = $('#jqxgrid').jqxGrid('getselectedrowindex');
	var row = $('#jqxgrid').jqxGrid('getrowdata', rowindex);
	// add new row
	if ($.trim($(args).text()) == '<fmt:message key="global.button.addNew"/>') {
		if(projectCode != "")
			window.location = 'form.htm?projectCode='+projectCode;
		else
			window.location = 'form.htm';
		}
	else if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
		if(projectCode != "")
			window.location = 'form.htm?id='+row.id +'&projectCode='+projectCode;
		else
			window.location = 'form.htm?id='+row.id;
	}
	else if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteSelected"/>') {
			var r=confirm('<fmt:message key="messsage.confirm.delete"/>');
			if (r==true)
			  {
				if(projectCode != "")
					window.location = 'delete.htm?id='+row.id +'&projectCode='+projectCode;
				else
					window.location = 'delete.htm?id='+row.id;
			  }
			return false;
        }
	// export data
	else{
		window.open('${pageContext.request.contextPath}/alarm/al-manage-on-air/exportData.htm?siteid='+"<c:out value='${siteid}'/>"+
	        	'&projectCode='+"<c:out value='${projectCode}'/>"+
	        	 '&network='+"<c:out value='${network}'/>"+
	        	 '&ne='+"<c:out value='${neName}'/>"+
	        	 '&strWhere='+$("#strWhere").val()+
	        	 '&sortfield='+$("#sortfield").val()+
	        	 '&sortorder='+$("#sortorder").val()
	        	 ,'_blank');
	}
});

//call view detail    
$("#jqxgrid").on('cellselect', function (event) 
{	
    var columnheader = $("#jqxgrid").jqxGrid('getcolumn', event.args.datafield).text; 
  	if (columnheader =='Site ID')
    {
    	var rowindex = event.args.rowindex;
    	var row = $("#jqxgrid").jqxGrid('getrowdata', rowindex);
    	var siteid = '';
    	var bscid = '';
    	var projectCode = '';
    	if(row.siteid!=null)
    	{
    		siteid=row.siteid;
    	}
    	if(row.ne!=null)
    	{
    		bscid=row.ne;
    	}
    	if(row.projectCode != null){
    		projectCode=row.projectCode;
    	}
    	window.open('${pageContext.request.contextPath}/alarm/al-manage-cr-cdd/list.htm?crCdd=CDD&site=' + siteid+'&bscid='+ bscid+'&projectCode='+projectCode,'_blank');
    }
    
});

//Create a jqxComboBox network
var urlNetwork = "${pageContext.request.contextPath}/alarm/al-manage-on-air/networkForOnAirList.htm";
// prepare the data
var sourceNetwork =
{
    datatype: "json",
    datafields: [
        { name: 'value' },
        { name: 'value' }
    ],
    url: urlNetwork,
    async: false
};
var dataAdapterNetwork = new $.jqx.dataAdapter(sourceNetwork);
// Create a jqxComboBox
$("#network").jqxComboBox({ source: dataAdapterNetwork, displayMember: "value", valueMember: "value", width: '90%',height: 20,itemHeight: 30,theme: theme, autoOpen: true });
var network =  "<c:out value='${network}'/>";
if(network=="")
	$('#network').val('ALL');
else
	$('#network').val(network);

function focusIt()
{
  var mytext = document.getElementById("projectCode");
  mytext.focus();
}
onload = focusIt;
</script>