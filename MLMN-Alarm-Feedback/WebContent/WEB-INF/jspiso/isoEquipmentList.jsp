<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<c:choose>
  <c:when test="${inputStatus == 'DEVICE'}">    		  
		<title><fmt:message key="sidebar.admin.isoEquipment"/></title>
		<content tag="heading"><fmt:message key="sidebar.admin.isoEquipment"/></content>
  </c:when>
  <c:when test="${inputStatus == 'USAGE'}">
  		<title><fmt:message key="sidebar.admin.isoInventoryResourceR"/></title>
		<content tag="heading"><fmt:message key="sidebar.admin.isoInventoryResourceR"/></content>
  </c:when>
  <c:otherwise>
  		<title><fmt:message key="sidebar.admin.isoInventoryResourceT"/></title>
		<content tag="heading"><fmt:message key="sidebar.admin.isoInventoryResourceT"/></content>
  </c:otherwise>
</c:choose>  
<div>
	<input id="strWhere" name="strWhere" value="" type="hidden"/>
	<input id="sortfield" name="sortfield" value="" type="hidden"/>
	<input id="sortorder" name="sortorder" value="" type="hidden"/>
</div>
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left" colspan="2"><form:form commandName="filter" method="post" action="list.htm?inputStatus=${inputStatus}">
			<fieldset>
    			<legend>Filter</legend>
					<table border="0" cellspacing="1" cellpadding="0" width="100%">
						<tr> 
							<td class="wid7 mwid80"><fmt:message key="isoEquipment.deptCode"/></td>
							<td class="wid12">
				           		<div id='deptCode'></div>
							</td>
							<td class="wid7 mwid100"><fmt:message key="isoEquipment.team"/></td>
							<td class="wid12"><div id='team'></div></td>
							<td class="wid8 mwid90"><fmt:message key="isoEquipment.subTeam"/></td>
							<td class="wid12"><div id='subTeam'></div></td>
							<td class="wid6 mwid60"><fmt:message key="isoEquipment.province"/></td>
							<td class="wid12">
								<div id='province'></div>
							</td>
							<td class="wid6 mwid50"><fmt:message key="isoEquipment.district"/></td>
							<td class="wid12">
				           		<div id='district'></div>
							</td>
						</tr>
						<tr>
							<td><fmt:message key="isoEquipment.neType"/></td>
							<td><div id='neType'></div></td>
							<td><fmt:message key="isoEquipment.ne"/></td>
							<td><input type="text" id="ne" name="ne" value="${neName}" /></td> 
							<td><fmt:message key="isoEquipment.codeAssetType"/></td>
							<td><div id='neGroup'></div></td>
							<td><fmt:message key="isoEquipment.locationName"/></td>
							<td><input type="text" id="locationName" name="locationName" value="${locationName}" /></td>
							<td><fmt:message key="isoLicenseSoft.vendor"/></td>
							<td><div id='vendor'></div></td>
						</tr>
						<tr> 
							<td><fmt:message key="isoEquipment.productCode"/></td>
							<td><input type="text" id="productCode" name="productCode" value="${productCode}" /></td>
							<td><fmt:message key="isoEquipment.productName"/></td>
							<td><input type="text" id="productName" name="productName" value="${productName}" /></td>
							<td><fmt:message key="isoEquipment.seriNo"/></td>
							<td><input type="text" id="seriNo" name="seriNo" value="${seriNo}" /></td>
							<td><fmt:message key="isoEquipment.status"/></td>
							<td><div id='status'></div></td>
							<td>
								<input type="submit" value="<fmt:message key="global.form.timkiem"/>" id='jqxSubmitButton' />
							</td>
						</tr>				
					</table></fieldset>
				</form:form>
				</td>

		</tr>
		<tr>
			<td colspan="2">
				<c:choose>
	  				<c:when test="${inputStatus == 'DEVICE'}">
						<%@ include file="/WEB-INF/jspiso/jqwidgets/gridEquipment.jsp" %>
					</c:when>
				  	<c:when test="${inputStatus == 'USAGE'}">
				  		<%@ include file="/WEB-INF/jspiso/jqwidgets/gridResource.jsp" %>
				   	</c:when>
				  	<c:otherwise>
				  		<%@ include file="/WEB-INF/jspiso/jqwidgets/gridResource.jsp" %>
				    </c:otherwise>
				</c:choose>
			</td>
		</tr>
</table>

<script type="text/javascript">
	var theme = getTheme();
	$("#jqxSubmitButton").jqxButton({theme: theme});
	$("#ne").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
	$("#productCode").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
	$("#seriNo").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
	$("#productName").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
	$("#locationName").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
	
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
        
     	// Create a jqxComboBox dsDiaDiem
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
        var dataAdapterProvinceCode = new $.jqx.dataAdapter(sourceProvinceCode);
        // Create a jqxComboBox
        $("#province").jqxComboBox({ source: dataAdapterProvinceCode, displayMember: "province", valueMember: "province", width: '90%',height: 20,itemHeight: 30,theme: theme, autoOpen: true });
        var province =  "<c:out value='${province}'/>";
        if(province=="")
			$('#province').val('ALL');
		else
			$('#province').val(province);
        
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
        
     	// Create a jqxComboBox equipment list
       	var urlNeGroup = "${pageContext.request.contextPath}/iso/quan-ly-thiet-bi/neGroup.htm";
        // prepare the data
        var sourceNeGroup =
        {
            datatype: "json",
            datafields: [
                { name: 'code' },
                { name: 'code' }
            ],
            url: urlNeGroup,
            async: false
        };
        var dataAdapterNeGroup = new $.jqx.dataAdapter(sourceNeGroup);
        // Create a jqxComboBox
        $("#neGroup").jqxComboBox({ source: dataAdapterNeGroup, displayMember: "code", valueMember: "code", width: '90%',height: 20,itemHeight: 30,theme: theme, autoOpen: true });
        var neGroup =  "<c:out value='${neGroup}'/>";
        if(neGroup=="")
			$('#neGroup').val('ALL');
		else
			$('#neGroup').val(neGroup);
        
     	// Create a jqxComboBox status list
       	var urlStatus = "${pageContext.request.contextPath}/iso/quan-ly-thiet-bi/dsTrangThai.htm";
        // prepare the data
        var sourceStatus =
        {
            datatype: "json",
            datafields: [
                { name: 'value' },
                { name: 'name' }
            ],
            url: urlStatus,
            async: false
        };
        var dataAdapterStatus = new $.jqx.dataAdapter(sourceStatus);
        // Create a jqxComboBox
        $("#status").jqxComboBox({ source: dataAdapterStatus, displayMember: "name", valueMember: "value", width: '90%',height: 20,itemHeight: 30,theme: theme, autoOpen: true });
        var status =  "<c:out value='${status}'/>";
        if(status=="")
			$('#status').val('ALL');
		else
			$('#status').val(status);
        
     	// Create a jqxComboBox status list
       	var urlVendor = "${pageContext.request.contextPath}/iso/quan-ly-thiet-bi/vendorList.htm";
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
        $("#vendor").jqxComboBox({ source: dataAdapterVendor, displayMember: "name", valueMember: "value", width: '90%',height: 20,itemHeight: 30,theme: theme, autoOpen: true });
        var vendor =  "<c:out value='${vendor}'/>";
        if(vendor=="")
			$('#vendor').val('ALL');
		else
			$('#vendor').val(vendor);
        
     	// Create a jqxComboBox district list
       	var urlDistrict = "${pageContext.request.contextPath}/iso/quan-ly-thiet-bi/loadQuanHuyen.htm";
        // prepare the data
        var sourceDistrict =
        {
            datatype: "json",
            datafields: [
                { name: 'district' },
                { name: 'district' }
            ],
            url: urlDistrict,
            async: false
        };
        var dataAdapterDistrict = new $.jqx.dataAdapter(sourceDistrict);
        // Create a jqxComboBox
        $("#district").jqxComboBox({ source: dataAdapterDistrict, displayMember: "district", valueMember: "district", width: '90%',height: 20,itemHeight: 30,theme: theme, autoOpen: true });
        var district =  "<c:out value='${district}'/>";
        if(district=="")
			$('#district').val('ALL');
		else
			$('#district').val(district);
	});

	function focusIt()
	{
	  var mytext = document.getElementById("productCode");
	  mytext.focus();
	}

	onload = focusIt;
</script>
