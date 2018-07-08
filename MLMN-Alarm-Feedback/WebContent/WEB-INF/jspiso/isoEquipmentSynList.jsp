<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title><fmt:message key="sidebar.admin.isoEquipmentSyn"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.isoEquipmentSyn"/></content>

<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left" colspan="2"><form:form commandName="filter" method="post" action="${function}.htm">
					<table border="0" cellspacing="1" cellpadding="0" width="100%">
						<tr> 
							<td class="wid8 mwid90"><fmt:message key="isoEquipment.deptCode"/></td>
							<td class="wid15">
								<div id='deptCode'></div>
							</td>	
							<td class="wid7 mwid100"><fmt:message key="isoEquipment.team"/></td>
							<td class="wid12"><div id='team'></div></td>
							<td class="wid8 mwid90"><fmt:message key="isoEquipment.subTeam"/></td>
							<td class="wid12"><div id='subTeam'></div></td> 
							<td class="wid8 mwid70"><fmt:message key="isoEquipment.neType"/></td>
							<td class="wid15">
								<div id='neType'></div>
							</td>	
							<td>
								<input type="submit" value="<fmt:message key="global.form.timkiem"/>" id='jqxSubmitButton' /></td>
						</tr>				
					</table>
				</form:form>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<%-- <%@ include file="/WEB-INF/jspiso/jqwidgets/gridReport.jsp" %> --%>
				
				<div id='jqxWidget' style="margin-top:5px">
			    	<div style="float: right;margin-bottom:3px;width:180px;">
			        	<table border="0" cellspacing="1" cellpadding="0" width="100%">
							<tr>
								<td><div style="float: right;" id="jqxlistbox"></div></td>
							</tr>
						</table>
			        </div><br>
			        <div id="jqxgrid"></div>
			        <div id='Menu'>
			            <ul>
					   		<li><fmt:message key="global.button.exportExcel"/></li>
				        </ul>
			       </div>
			    </div>
			</td>
		</tr>
</table>
<script type="text/javascript">
	var theme = getTheme();
	$("#jqxSubmitButton").jqxButton({theme: theme});
	${gridReport}
	$(document).ready(function(){
		// Create a jqxComboBox dsLoaiThietBi
       	var urlNeType = "${pageContext.request.contextPath}/iso/quan-ly-thiet-bi/dsLoaiThietBi.htm";
        // prepare the data
        var sourceNeType =
        {
            datatype: "json",
            datafields: [
                { name: 'value' },
                { name: 'value' }
            ],
            url: urlNeType,
            async: false
        };
        var dataAdapterNeType = new $.jqx.dataAdapter(sourceNeType);
        // Create a jqxComboBox
        $("#neType").jqxComboBox({ source: dataAdapterNeType, displayMember: "value", valueMember: "value", width: '90%',height: 20,itemHeight: 30,theme: theme, autoOpen: true });
        var neType =  "<c:out value='${neType}'/>";
        if(neType=="")
			$('#neType').val('ALL');
		else
			$('#neType').val(neType);
        
		// Create a jqxComboBox dsDonViQuanLy
       	var urlDeptCode = "${pageContext.request.contextPath}/iso/quan-ly-thiet-bi/dsDonViQuanLy.htm";
        // prepare the data
        var sourceDeptCode =
        {
            datatype: "json",
            datafields: [
                { name: 'deptCode' },
                { name: 'deptCode' }
            ],
            url: urlDeptCode,
            async: false
        };
        var dataAdapterDeptCode = new $.jqx.dataAdapter(sourceDeptCode);
        // Create a jqxComboBox
        $("#deptCode").jqxComboBox({ source: dataAdapterDeptCode, displayMember: "deptCode", valueMember: "deptCode", width: '90%',height: 20,itemHeight: 30,theme: theme, autoOpen: true });
        var deptCode =  "<c:out value='${deptCode}'/>";
        if(deptCode=="")
			$('#deptCode').val('ALL');
		else
			$('#deptCode').val(deptCode);
        
     	/* // Create a jqxComboBox dsDiaDiem
       	var urlProvinceCode = "${pageContext.request.contextPath}/iso/quan-ly-thiet-bi/dsDiaDiem.htm";
        // prepare the data
        var sourceProvinceCode =
        {
            datatype: "json",
            datafields: [
                { name: 'province' },
                { name: 'province' }
            ],
            url: urlProvinceCode,
            async: false
        };
        var dataAdapterProvinceCode = new $.jqx.dataAdapter(sourceProvinceCode); */
        
     	// Create a jqxComboBox team list
       	var urlTeam = "${pageContext.request.contextPath}/iso/quan-ly-thiet-bi/teamList.htm";
        // prepare the data
        var sourceTeam =
        {
            datatype: "json",
            datafields: [
                { name: 'team' },
                { name: 'team' }
            ],
            url: urlTeam,
            async: false
        };
        var dataAdapterTeam = new $.jqx.dataAdapter(sourceTeam);
        // Create a jqxComboBox
        $("#team").jqxComboBox({ source: dataAdapterTeam, displayMember: "team", valueMember: "team", width: '90%',height: 20,itemHeight: 30,theme: theme, autoOpen: true });
        var team =  "<c:out value='${team}'/>";
        if(team=="")
			$('#team').val('ALL');
		else
			$('#team').val(team);
        
     	// Create a jqxComboBox sub team list
       	var urlSubTeam = "${pageContext.request.contextPath}/iso/quan-ly-thiet-bi/subTeamList.htm";
        // prepare the data
        var sourceSubTeam =
        {
            datatype: "json",
            datafields: [
                { name: 'subTeam' },
                { name: 'subTeam' }
            ],
            url: urlSubTeam,
            async: false
        };
        var dataAdapterSubTeam = new $.jqx.dataAdapter(sourceSubTeam);
        // Create a jqxComboBox
        $("#subTeam").jqxComboBox({ source: dataAdapterSubTeam, displayMember: "subTeam", valueMember: "subTeam", width: '90%',height: 20,itemHeight: 30,theme: theme, autoOpen: true });
        var subTeam =  "<c:out value='${subTeam}'/>";
        if(subTeam=="")
			$('#subTeam').val('ALL');
		else
			$('#subTeam').val(subTeam);
        
      	//call view detail    
        $("#jqxgrid").on('cellselect', function (event) 
  		{
  		    var columnheader = $("#jqxgrid").jqxGrid('getcolumn', event.args.datafield).text; 
  		  	if (columnheader=='Total')
  		    {
  		    	var rowindex = event.args.rowindex;
  		    	var row = $("#jqxgrid").jqxGrid('getrowdata', rowindex);
  		    	var team= '';
  		    	var subTeam= '';
  		    	var neType= '';
  		    	var neGroup='';
  		    	var locationName='';
  		    	if(row.team!=null)
 		    	{
  		    		team=row.team;
 		    	}
  		    	if(row.neGroup!=null)
 		    	{
  		    		neGroup=row.neGroup;
 		    	}
  		    	if(row.locationName!=null)
 		    	{
  		    		locationName=row.locationName;
 		    	}
  		    	
  		    	if(row.subTeam!=null)
 		    	{
  		    		subTeam=row.subTeam;
 		    	}
 		    	if(row.neType!=null)
 		    	{
 		    			neType=row.neType;
 		    	}
 		    	
  		    	window.open('${pageContext.request.contextPath}/iso/iso-inventory/list.htm?team='+team+ '&subTeam='+subTeam+'&neType='+neType+'&neGroup='+neGroup+'&locationName='+locationName,'_blank');
  		    }
  		    
  		});
	});
	
	$('#Menu').on('itemclick', function (event) {
		var args = event.args;
		// export data
		if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>') {
				window.open('${pageContext.request.contextPath}/iso/tong-hop-thiet-bi/exportData.htm?deptCode='+"<c:out value='${deptCode}'/>"+
			        	 '&team='+"<c:out value='${team}'/>"+
			        	 '&subTeam='+"<c:out value='${subTeam}'/>"+
			        	 '&neType='+"<c:out value='${neType}'/>"
			        	 ,'_blank');
				}
	});
	
</script>
<script type="text/javascript">
var rolesManagerDeptCode = '<c:out value="${rolesManagerDeptCode}"/>';
var rolesManagerTeam = '<c:out value="${rolesManagerTeam}"/>';
var rolesManagerSubTeam = '<c:out value="${rolesManagerSubTeam}"/>';

if(rolesManagerDeptCode == 'true'){
	$("#deptCode").jqxComboBox({ disabled: true }); 
}
if(rolesManagerTeam == 'true'){
	$("#team").jqxComboBox({ disabled: true }); 
}
if(rolesManagerSubTeam == 'true'){
	$("#subTeam").jqxComboBox({ disabled: true }); 
}
</script>