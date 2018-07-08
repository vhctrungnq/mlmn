<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:choose>
  <c:when test="${crCdd == 'CR'}">
      <title><fmt:message key="title.alManageCrCdd.crlist"/></title>
	  <content tag="heading"><fmt:message key="title.alManageCrCdd.crlist"/></content>
  </c:when>
  <c:when test="${crCdd == 'CDD'}">
  	<title><fmt:message key="title.alManageCrCdd.cddlist"/></title>
	<content tag="heading"><fmt:message key="title.alManageCrCdd.cddlist"/></content>
  </c:when>
</c:choose>

<div>
	<input id="strWhere" name="strWhere" value="" type="hidden"/>
	<input id="sortfield" name="sortfield" value="" type="hidden"/>
	<input id="sortorder" name="sortorder" value="" type="hidden"/>
</div>
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left"><form:form commandName="filter" method="post" action="list.htm?crCdd=${crCdd}">
					<c:choose>
		  				<c:when test="${crCdd == 'CR'}">
							<table width="100%" border="0" cellspacing="3" cellpadding="0">
								<tr>
									<td class="wid8 mwid80"><fmt:message key="alManageBacklog.team"/></td>
									<td class="wid20">
										<div id='maPhong'></div>
									</td>
									<td class="wid8 mwid70"><fmt:message key="alManageCrCdd.projectId"/></td>
									<td class="wid20">
										<div id='projectCode'></div>
									</td>
									<td class="wid8 mwid70">
										<fmt:message key="alManageCrCdd.crName"/>	
									</td>
									<td class="wid20">
										<input type="text" id="crName" name="crName" value="${crName}" />
					           		</td>
									<td class="wid8 mwid70"><fmt:message key="alManageCrCdd.vendor"/></td>
									<td class="wid20">
										<input type="text" id="vendor" name="vendor" value="${vendor}" />
									</td>	
									<td><input class="button" id="btSearch" type="submit" name="filter"
										value="<fmt:message key="global.form.timkiem"/>" /></td>
								</tr>			
							</table>
						</c:when>
						<c:when test="${crCdd == 'CDD'}">
							<table width="100%" border="0" cellspacing="3" cellpadding="0">
								<tr>
									<td class="wid8 mwid70"><fmt:message key="alManageCrCdd.projectId"/></td>
									<td class="wid20">
										<div id='projectCode'></div>
									</td>
									<td class="wid8 mwid70"><fmt:message key="alManageCrCdd.bscid"/></td>
									<td class="wid20"><input type="text" id="bscid" name="bscid" value="${bscid}" /></td>
									<td class="wid6 mwid50"><fmt:message key="alManageCrCdd.site"/></td>
									<td class="wid20"><input type="text" id="site" name="site" value="${site}" /></td>	
									<td></td>
								</tr>
								<tr>
									<td class="wid8 mwid80"><fmt:message key="alManageBacklog.team"/></td>
									<td class="wid20">
										<div id='maPhong'></div>
									</td>
									<td >
										<fmt:message key="alManageCrCdd.cddName"/>	
									</td>
									<td >
										<input type="text" id="crName" name="crName" value="${crName}" />
					           		</td>
									<td> <fmt:message key="alManageCrCdd.vendor"/></td>
									<td >
										<input type="text" id="vendor" name="vendor" value="${vendor}" />
									</td>
									<td></td>
									<td><input class="button" id="btSearch" type="submit" name="filter"
										value="<fmt:message key="global.form.timkiem"/>" /></td>
								</tr>			
							</table>
						</c:when>
					</c:choose>	
				</form:form>
				</td>
				<td class="wid6">
				</td> 
		</tr>
		<tr>
			<td colspan="2">
				<div id='jqxWidget' style="margin-top:5px;">
			    	<div style="float: right;margin-bottom:3px;width:180px;">
			        	<table border="0" cellspacing="1" cellpadding="0" width="100%">
							<tr>
							<c:if test="${checkRegion==true}">
								<td align="right"><input type="button" value="<fmt:message key="global.button.addNew"/>" id='addNew' />
			      				</td>
			      			</c:if>
								<td style="width:33px"><div style="float: right;" id="jqxlistbox"></div></td>
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
var crCdd =  "<c:out value='${crCdd}'/>";
$("#crName").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
$("#vendor").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });

$("#bscid").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
$("#site").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
$("#btSearch").jqxButton({theme: theme});
$('#addNew').jqxButton({ theme: theme });
$('#addNew').click(function () {
	window.location = 'form.htm?crCdd=' + crCdd;
});
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
$("#maPhong").jqxComboBox({ source: dataAdapterTeam, displayMember: "deptValue", valueMember: "deptValue", width: '90%',height: 20,itemHeight: 30,theme: theme, autoOpen: true });
var maPhong =  "<c:out value='${maPhong}'/>";
if(maPhong=="")
	$('#maPhong').val('ALL');
else
	$('#maPhong').val(maPhong);
$('#Menu').on('itemclick', function (event) {
	var args = event.args;
	var rowindex = $('#jqxgrid').jqxGrid('getselectedrowindex');
	var row = $('#jqxgrid').jqxGrid('getrowdata', rowindex);
	// add new row
	if ($.trim($(args).text()) == '<fmt:message key="global.button.addNew"/>') {
		window.location = 'form.htm?crCdd=' + crCdd;
		}
	else if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
		window.location = 'form.htm?crCdd=' + crCdd + '&id='+row.id;
	}
	else if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteSelected"/>') {
			var r=confirm('<fmt:message key="messsage.confirm.delete"/>');
			if (r==true)
			  {
				window.location = 'delete.htm?crCdd=' + crCdd + '&id='+row.id;
			  }
			return false;
        }
	// export data
	else{
		window.open('${pageContext.request.contextPath}/alarm/al-manage-cr-cdd/exportData.htm?crName='+$("#crName").val()+
	        	'&vendor='+$("#vendor").val()+
	        	 '&projectCode='+$("#projectCode").val()+
	        	 '&crCdd='+"<c:out value='${crCdd}'/>"+
	        	 '&maPhong='+$("#maPhong").val()+
	        	 '&bscid='+$("#bscid").val()+
	        	 '&site='+$("#site").val()+
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
  	if (columnheader =='Tên file')
    {
    	var rowindex = event.args.rowindex;
    	var row = $("#jqxgrid").jqxGrid('getrowdata', rowindex);
    	var id= '';
    	if(row.id!=null)
    	{
    		id=row.id;
    		if(row.fileName != null)
    			window.open('${pageContext.request.contextPath}/alarm/al-manage-cr-cdd/download.htm?id=' + id,'_blank');
    	}
    }
    
});

//Create a jqxComboBox project code
var urlProjectCode = "${pageContext.request.contextPath}/alarm/al-manage-cr-cdd/alManageProjectList.htm";
// prepare the data
var sourceProjectCode =
{
    datatype: "json",
    datafields: [
        { name: 'projectCode' },
        { name: 'projectCode' }
    ],
    url: urlProjectCode,
    async: false
};
var dataAdapterProjectCode = new $.jqx.dataAdapter(sourceProjectCode);
// Create a jqxComboBox
$("#projectCode").jqxComboBox({ source: dataAdapterProjectCode, displayMember: "projectCode", valueMember: "projectCode", width: '90%',height: 20,itemHeight: 30,theme: theme, autoOpen: true });
var projectCode =  "<c:out value='${projectCode}'/>";
if(projectCode=="")
	$('#projectCode').val('ALL');
else
	$('#projectCode').val(projectCode);

function focusIt()
{
  var mytext = document.getElementById("crName");
  mytext.focus();
}
onload = focusIt;
</script>