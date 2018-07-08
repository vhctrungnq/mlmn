<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<title><fmt:message key="title.alManageBacklog.alManageBackloglist"/></title>
<content tag="heading"><fmt:message key="title.alManageBacklog.alManageBackloglist"/></content>

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
							<td class="wid8 mwid80">
								<fmt:message key="alManageBacklog.work"/>
							</td>
							<td class="wid20">
								<input type="text" id="work" name="work" value="${work}" />
			           		</td>
							<td class="wid8 mwid80"><fmt:message key="alManageBacklog.team"/></td>
							<td class="wid20">
								<div id='team'></div>
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
								<c:if test="${checkRegion==true}">
										<td align="right"><input type="button" value="<fmt:message key="global.button.addNew"/>" id='addNew' />&nbsp;
				      					<input type="button" value="<fmt:message key="global.button.import"/>" id='importFile' /></td>
								</c:if>
								<td><div style="float: right;" id="jqxlistbox"></div></td>
							</tr>
						</table>
			        </div><br>
			    	
			        <div id="jqxgrid"></div>
			        <div id='Menu'>
			            <ul>
				            <c:if test="${checkRegion==true}">
								<li><fmt:message key="global.button.addNew"/></li>
					            <li><fmt:message key="global.button.editSelected"/></li>
						   		<li><fmt:message key="global.button.deleteSelected"/></li>
						   	</c:if>
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
$("#work").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });

//Create a jqxComboBox teamList
var urlTeam = "${pageContext.request.contextPath}/alarm/al-manage-backlog/teamList.htm";
// prepare the data
var sourceTeam =
{
    datatype: "json",
    datafields: [
        { name: 'deptValue' },
        { name: 'deptValue' }
    ],
    url: urlTeam,
    async: false
};
var dataAdapterTeam = new $.jqx.dataAdapter(sourceTeam);
// Create a jqxComboBox
$("#team").jqxComboBox({ source: dataAdapterTeam, displayMember: "deptValue", valueMember: "deptValue", width: '90%',height: 20,itemHeight: 30,theme: theme, autoOpen: true });
var team =  "<c:out value='${team}'/>";
if(team=="")
	$('#team').val('ALL');
else
	$('#team').val(team);

$("#btSearch").jqxButton({theme: theme});
$('#addNew').jqxButton({ theme: theme });
$('#addNew').click(function () {
	window.location = 'form.htm';
});
$('#importFile').jqxButton({ theme: theme });
$('#importFile').click(function () {
	window.location = 'upload.htm';
});
$('#Menu').on('itemclick', function (event) {
	var args = event.args;
	var rowindex = $('#jqxgrid').jqxGrid('getselectedrowindex');
	var row = $('#jqxgrid').jqxGrid('getrowdata', rowindex);
	// add new row
	if ($.trim($(args).text()) == '<fmt:message key="global.button.addNew"/>') {
		window.location = 'form.htm';
		}
	else if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
		window.location = 'form.htm?id='+row.id;
	}
	else if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteSelected"/>') {
			var r=confirm('<fmt:message key="messsage.confirm.delete"/>');
			if (r==true)
			  {
				window.location = 'delete.htm?id='+row.id;
			  }
			return false;
        }
	// export data
	else{
		window.open('${pageContext.request.contextPath}/alarm/al-manage-backlog/exportData.htm?work='+"<c:out value='${work}'/>"+
	        	'&team='+"<c:out value='${team}'/>"+
	        	'&teamOfUser='+"<c:out value='${teamOfUser}'/>"+
	        	 '&strWhere='+$("#strWhere").val()+
	        	 '&sortfield='+$("#sortfield").val()+
	        	 '&sortorder='+$("#sortorder").val()
	        	 ,'_blank');
	}
});



function focusIt()
{
  var mytext = document.getElementById("work");
  mytext.focus();
}
onload = focusIt;
</script>