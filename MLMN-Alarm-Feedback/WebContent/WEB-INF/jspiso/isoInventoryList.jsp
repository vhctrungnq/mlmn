<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<title><fmt:message key="sidebar.admin.isoInventoryResourceR"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.isoInventoryResourceR"/></content>

<div>
	<input id="strWhere" name="strWhere" value="" type="hidden"/>
	<input id="sortfield" name="sortfield" value="" type="hidden"/>
	<input id="sortorder" name="sortorder" value="" type="hidden"/>
</div>
<div class="body-content"></div>
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left" colspan="2"><form:form commandName="filter" method="post" action="list.htm">
					<table border="0" cellspacing="3" cellpadding="0" width="100%">
						<tr> 
							<td class="wid7 mwid80"><fmt:message key="isoEquipment.deptCode"/></td>
							<td class="wid12">
				           		<div id='deptCode'></div>
							</td>
							<td class="wid7 mwid100"><fmt:message key="isoEquipment.team"/></td>
							<td class="wid12"><div id='team'></div></td>
							<td class="wid8 mwid90"><fmt:message key="isoEquipment.subTeam"/></td>
							<td class="wid12"><div id='subTeam'></div></td>
							<td class="wid6 mwid80"><fmt:message key="isoEquipment.locationName"/></td>
							<td colspan = "2" class="wid12"><input type="text" id="locationName" name="locationName" value="${locationName}" /></td>
						</tr>
						<tr>
							<td><fmt:message key="isoEquipment.province"/></td>
							<td><div id='province'></div></td>
							<td><fmt:message key="isoEquipment.district"/></td>
							<td><div id='district'></div></td>
							<td><fmt:message key="isoLicenseSoft.vendor"/></td>
							<td><div id='vendor'></div></td>
							<td><fmt:message key="isoEquipment.inputStatus"/></td>
							<td colspan = "2"><div id='inputStatus'></div></td>	
						</tr>
						<tr>
							<td><fmt:message key="isoEquipment.neType"/></td>
							<td><div id='neType'></div></td>
							<td><fmt:message key="isoEquipment.ne"/></td>
							<td><input type="text" id="ne" name="ne" value="${neName}" /></td> 
							<td><fmt:message key="isoEquipment.codeAssetType"/></td>
							<td><div id='neGroup'></div></td>
							<td><fmt:message key="isoEquipment.status"/></td>
							<td colspan = "2"><div id='status'></div></td>
						</tr>
						<tr> 
							<td><fmt:message key="isoEquipment.productCode"/></td>
							<td><input type="text" id="productCode" name="productCode" value="${productCode}" /></td>
							<td><fmt:message key="isoEquipment.productName"/></td>
							<td><input type="text" id="productName" name="productName" value="${productName}" /></td>
							<td><fmt:message key="isoEquipment.seriNo"/></td>
							<td><input type="text" id="seriNo" name="seriNo" value="${seriNo}" /></td>
							<td><fmt:message key="isoEquipment.location"/></td>
							<td style="width:100px"><div  id='location'></div>
								</td>
								<td><input type="submit" value="<fmt:message key="global.form.timkiem"/>" id='jqxSubmitButton' />
							</td>
						</tr>				
					</table>
				</form:form>
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
					   		<li><fmt:message key="global.button.deleteMultiSelected"/></li>
					   		<li><fmt:message key="global.button.exportExcel"/></li>
					   		<li><fmt:message key="global.link.cardNew"/></li>
					   		<li><fmt:message key="global.link.cardMove"/></li>
					   		<li><fmt:message key="global.link.cardRemove"/></li>
				        </ul>
			       </div>
			    </div>  	
			</td>
		</tr>
</table>

<script type="text/javascript">
	var theme = getTheme();
	${gridManage}
	$("#jqxSubmitButton").jqxButton({theme: theme});
	$("#ne").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
	$("#productCode").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
	$("#seriNo").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
	$("#productName").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
	$("#locationName").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
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
    	var productCode = row.productCode;
    	if(productCode == null)
    		productCode = "";
    	var seriNo = row.seriNo;
    	if(seriNo == null)
    		seriNo = "";
    	// add new row
    	if ($.trim($(args).text()) == '<fmt:message key="global.button.addNew"/>') {
    		window.location = 'form.htm';
    		}
    	else if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
    		window.location = 'form.htm?id='+row.id;
    	}
    	else if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteMultiSelected"/>') {
    			var r=confirm('<fmt:message key="messsage.confirm.delete"/>');
    			if (r==true)
    			  {
    				var selectedrowindexes = $('#jqxgrid').jqxGrid('getselectedrowindexes'); 
            		var idList="";
            		var cond="";
            		if (selectedrowindexes != null) {
            			for (var i=0; i<selectedrowindexes.length; i++) {
            				var row = $("#jqxgrid").jqxGrid('getrowdata', selectedrowindexes[i]);
            				idList+=cond+row.id;
            				cond=",";
            			}
            			window.location = 'delete.htm?idList='+idList;
            		}
    			  }
    			return false;
            }
    	// export data
    	else if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>'){

    		window.open('${pageContext.request.contextPath}/iso/iso-inventory/exportResource.htm?deptCode='+"<c:out value='${deptCode}'/>"+
    				'&team='+"<c:out value='${team}'/>"+
    				'&subTeam='+"<c:out value='${subTeam}'/>"+
    	        	'&province='+"<c:out value='${province}'/>"+
    	        	'&district='+"<c:out value='${district}'/>"+
    	        	 '&neType='+"<c:out value='${neType}'/>"+
    	        	 '&ne='+"<c:out value='${neName}'/>"+
    	        	 '&neGroup='+"<c:out value='${neGroup}'/>"+
    	        	 '&locationName='+"<c:out value='${locationName}'/>"+
    	        	 '&vendor='+"<c:out value='${vendor}'/>"+
    	        	 '&productCode='+"<c:out value='${productCode}'/>"+
    	        	 '&productName='+"<c:out value='${productName}'/>"+
    	        	 '&seriNo='+"<c:out value='${seriNo}'/>"+
    	        	 '&inputStatus='+"<c:out value='${inputStatus}'/>"+
    	        	 '&location='+"<c:out value='${location}'/>"+
    	        	 '&status='+"<c:out value='${status}'/>"+
    	        	 '&strWhere='+$("#strWhere").val()+
    	        	 '&sortfield='+$("#sortfield").val()+
    	        	 '&sortorder='+$("#sortorder").val()
    	        	 ,'_blank');
    	}
    	else if ($.trim($(args).text()) == '<fmt:message key="global.link.cardNew"/>'){
    		window.location = '${pageContext.request.contextPath}/iso/theo-doi-tai-nguyen-mang-luoi/list.htm?status=N&productCode=' + productCode + '&seriNo=' + seriNo;
    	}
    	else if ($.trim($(args).text()) == '<fmt:message key="global.link.cardMove"/>'){
    		window.location = '${pageContext.request.contextPath}/iso/theo-doi-tai-nguyen-mang-luoi/list.htm?status=H&productCode=' + productCode + '&seriNo=' + seriNo;
    	}
    	else if ($.trim($(args).text()) == '<fmt:message key="global.link.cardRemove"/>'){
    		window.location = '${pageContext.request.contextPath}/iso/theo-doi-tai-nguyen-mang-luoi/list.htm?status=R&productCode=' + productCode + '&seriNo=' + seriNo;
    	}
    	});
    
	$(document).ready(function(){
		
		// Create a jqxComboBox dsDonViQuanLy
       	var urlDeptCode = "${pageContext.request.contextPath}/iso/iso-inventory/dsDonViQuanLy.htm";
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
        
     	// Create a jqxComboBox team list
       	var urlTeam = "${pageContext.request.contextPath}/iso/iso-inventory/teamList.htm";
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
       	var urlSubTeam = "${pageContext.request.contextPath}/iso/iso-inventory/subTeamList.htm";
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
		
		// Create a jqxComboBox dsLoaiThietBi
       	var urlNeType = "${pageContext.request.contextPath}/iso/iso-inventory/dsLoaiThietBi.htm";
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
        
     	// Create a jqxComboBox equipment list
       	var urlNeGroup = "${pageContext.request.contextPath}/iso/iso-inventory/neGroup.htm";
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
       	var urlStatus = "${pageContext.request.contextPath}/iso/iso-inventory/dsTrangThai.htm";
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
     // Create a jqxComboBox location list
       	var urlLocation = "${pageContext.request.contextPath}/iso/iso-inventory/location-list.htm";
        // prepare the data
        var sourceLocation =
        {
            datatype: "json",
            datafields: [
                { name: 'location' },
                { name: 'location' }
            ],
            url: urlLocation,
            async: false
        };
        var dataAdapterLocation = new $.jqx.dataAdapter(sourceLocation);
        // Create a jqxComboBox
        $("#location").jqxComboBox({ source: dataAdapterLocation, displayMember: "location", valueMember: "location", width: '90%',height: 20,itemHeight: 30,theme: theme, autoOpen: true });
        var location =  "<c:out value='${location}'/>";
        if(location=="")
			$('#location').val('ALL');
		else
			$('#location').val(location);
     	// Create a jqxComboBox status list
       	var urlVendor = "${pageContext.request.contextPath}/iso/iso-inventory/vendorList.htm";
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
        
     	// Create a jqxComboBox input status list
       	var urlInputStatus = "${pageContext.request.contextPath}/iso/iso-inventory/inputStatusList.htm";
        // prepare the data
        var sourceInputStatus =
        {
            datatype: "json",
            datafields: [
                { name: 'value' },
                { name: 'name' }
            ],
            url: urlInputStatus,
            async: false
        };
        var dataAdapterInputStatus = new $.jqx.dataAdapter(sourceInputStatus);
        // Create a jqxComboBox
        $("#inputStatus").jqxComboBox({ source: dataAdapterInputStatus, displayMember: "name", valueMember: "value", width: '90%',height: 20,itemHeight: 30,theme: theme, autoOpen: true });
        var inputStatus =  "<c:out value='${inputStatus}'/>";
        if(inputStatus=="")
			$('#inputStatus').val('ALL');
		else
			$('#inputStatus').val(inputStatus);
	});

	// Create a jqxComboBox dsDiaDiem
   	var urlProvinceCode = "${pageContext.request.contextPath}/iso/iso-inventory/dsDiaDiem.htm";
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
    
 	// Create a jqxComboBox district list
   	var urlDistrict = "${pageContext.request.contextPath}/iso/iso-inventory/loadQuanHuyen.htm?province="+"<c:out value='${province}'/>";
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
    
	function focusIt()
	{
	  var mytext = document.getElementById("productCode");
	  mytext.focus();
	}

	onload = focusIt;
</script>

<script type="text/javascript">
function loadToolBar(){
	$('#load').remove();
	$('.body-content').append('<span id="load">LOADING...</span>');
	$('#load').fadeIn('normal', loadContent);

	function loadContent() {
		$('#result').load('', '', showNewContent());
	}
	
	function showNewContent() {
		$('#result').show('normal', hideLoader());
	}
	
	function hideLoader() {
		$('#load').fadeOut('normal');
	}
}

$("#province").change(function(){
	  var province = $("#province").val();
  	  if (province==null||province=='null')
	  {
	  				province='';
	  }
  	  loadToolBar();
  	  $.getJSON("${pageContext.request.contextPath}/iso/iso-inventory/loadQuanHuyen.htm",{province:province}, function(k){
	  		 var provinceList=[];
	  			 provinceList =k;
	  			$("#district").jqxComboBox({source: provinceList});
	  		});	
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