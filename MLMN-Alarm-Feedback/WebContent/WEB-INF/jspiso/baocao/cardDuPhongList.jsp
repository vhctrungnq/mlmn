<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title><fmt:message key="sidebar.admin.isoInventoryReportCardDuPhong"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.isoInventoryReportCardDuPhong"/></content>
<div class="body-content"></div>
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left" colspan="2"><form method="post" action="${function}.htm">
					<table border="0" cellspacing="3" cellpadding="0" width="100%">
						<tr>
							<td class="wid6 mwid70"><fmt:message key="isoEquipment.deptCode"/></td>
							<td class="wid15"><div id='deptCode'></div></td>
							<td class="wid6 mwid90"><fmt:message key="isoEquipment.team"/></td>
							<td class="wid15"><div id='team'></div></td>
							<td class="wid8 mwid90"><fmt:message key="isoEquipment.subTeam"/></td>
							<td class="wid15"><div id='subTeam'></div></td>
							<td></td>
						</tr>
						<tr>
							<td class="wid8 mwid70"><fmt:message key="report.general.diadiem"/></td>
							<td class="wid15">	
						       	<div id='province'></div>
							</td>
							<td><fmt:message key="isoEquipment.district"/></td>
							<td >
				           		<div id='district'></div>
							</td>
							<td ><fmt:message key="cardDuPhong.productName"/></td>
							<td >
								<input type="text" id="productName" name="productName" value="${productName}" class="wid90" />
							</td>
							<td></td>
						</tr>
						<tr>
							<td ><fmt:message key="cardDuPhong.productCode"/></td>
							<td >
								<input type="text" id="productCode" name="productCode" value="${productCode}" class="wid90" />
							</td>	
							<td><fmt:message key="isoEquipment.neType"/></td>
							<td >
				           		<div id='neType'></div>
				           		<div>
									<input id="strWhere" name="strWhere" value="" type="hidden"/>
									<input id="sortfield" name="sortfield" value="" type="hidden"/>
									<input id="sortorder" name="sortorder" value="" type="hidden"/>
								</div>
							</td>
							<td>
								<input type="submit" value="<fmt:message key="global.form.timkiem"/>" id='jqxSubmitButton' />
							</td>
						</tr>				
					</table>
				</form>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<%-- <%@ include file="/WEB-INF/jspiso/jqwidgets/gridDefault.jsp" %> --%>
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
					   		<li><fmt:message key="global.button.exportOptima"/></li>
				        </ul>
			       </div>
			    </div>
			</td>
		</tr>
</table>

<script type="text/javascript">
	var theme = getTheme();
	$("#jqxSubmitButton").jqxButton({theme: theme});
	$("#productName").jqxInput({ height: 20, minLength: 1, theme: theme });
	$("#productCode").jqxInput({ height: 20, minLength: 1, theme: theme });
	${gridReport}
	$(document).ready(function () {	
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
	});
    
	// Create a jqxComboBox dsDiaDiem
   	var urlProvinceCode = "${pageContext.request.contextPath}/iso/report-detail/dsDiaDiem.htm";
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
   	var urlDistrict = "${pageContext.request.contextPath}/iso/quan-ly-thiet-bi/loadQuanHuyen.htm?province="+"<c:out value='${province}'/>";
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
    
 	/* // Create a jqxComboBox dsLoaiThietBi
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
		$('#neType').val(neType); */
		
	var urlNeType = "${pageContext.request.contextPath}/iso/iso-inventory/dsLoaiThietBi.htm";
	var sourceNeType =
	{
	    datatype: "json",
	    url: urlNeType,
	    datafields: [
	                 { name: 'value'},
	                 { name: 'value'}
	             ],
	    async: false
	};
	var dataAdapterNeType = new $.jqx.dataAdapter(sourceNeType);
	$("#neType").jqxDropDownList({source: dataAdapterNeType, displayMember: "value", valueMember: "value",checkboxes: true, width: "90%", height: 20,itemHeight: 30, theme: theme,autoOpen: true,enableHover: true });           
	var neType = '<c:out value="${neType}"/>';

	if(neType=="")
		$('#neType').val('Choose');
	else
	{
		var neTypeVar = neType.split(",");
		if (neTypeVar != null) {
			for (var i=0; i<neTypeVar.length; i++) {
				$("#neType").jqxDropDownList('checkItem', neTypeVar[i] ); 
			}
		}
	}
	$("#neType").on('checkChange', function (event) {
	    if (event.args) {
	        var item = event.args.item;
	        if (item) {
	            var items = $("#neType").jqxDropDownList('getCheckedItems');
	            var checkedItems = "";
	            var con="";
	            $.each(items, function (index) {
	                checkedItems += con+this.value ;
	                con= ",";                          
	            });
	            $("#neType").val(checkedItems);
	        }
	    }
	});
    
	function focusIt()
	{
	  var mytext = document.getElementById("productName");
	  mytext.focus();
	}
	onload = focusIt;
	
	$('#Menu').on('itemclick', function (event) {
		var args = event.args;
		// export data
		if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>') {
				window.open('${pageContext.request.contextPath}/iso/bao-cao-card-du-phong/exportData.htm?deptCode='+"<c:out value='${deptCode}'/>"+
			        	 '&team='+"<c:out value='${team}'/>"+
			        	 '&subTeam='+"<c:out value='${subTeam}'/>"+
			        	 '&province='+"<c:out value='${province}'/>"+
			        	 '&district='+"<c:out value='${district}'/>"+
			        	 '&productName='+"<c:out value='${productName}'/>"+
			        	 '&productCode='+"<c:out value='${productCode}'/>"+
			        	 '&neType='+"<c:out value='${neType}'/>"+
			        	 '&strWhere='+$("#strWhere").val()+
			        	 '&sortfield='+$("#sortfield").val()+
			        	 '&sortorder='+$("#sortorder").val()
			        	 ,'_blank');
				}
		else if ($.trim($(args).text()) == '<fmt:message key="global.button.exportOptima"/>') {
			window.open('${pageContext.request.contextPath}/iso/bao-cao-card-du-phong/exportDataOptima.htm?productName='+"<c:out value='${productName}'/>"+
		        	 '&productCode='+"<c:out value='${productCode}'/>"+
		        	 '&neType='+"<c:out value='${neType}'/>"+
		        	 '&vendor='+"<c:out value='${vendor}'/>"+
		        	 '&strWhere='+$("#strWhere").val()+
		        	 '&sortfield='+$("#sortfield").val()+
		        	 '&sortorder='+$("#sortorder").val()
		        	 ,'_blank');
		}
	});
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