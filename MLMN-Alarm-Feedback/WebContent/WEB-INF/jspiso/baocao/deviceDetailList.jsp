<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title><fmt:message key="title.iso.report.detail"/></title>
<content tag="heading"><fmt:message key="title.iso.report.detail"/></content>
<form:form name="checkform" method="post" action="list.htm">
<div>
	<input id="strWhere" name="strWhere" value="" type="hidden"/>
	<input id="sortfield" name="sortfield" value="" type="hidden"/>
	<input id="sortorder" name="sortorder" value="" type="hidden"/>
</div>
<div class="body-content"></div>
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left" colspan="2">
					<table border="0" cellspacing="3" cellpadding="0" width="100%">
						<tr>
							<td class="wid6 mwid70"><fmt:message key="isoEquipment.deptCode"/></td>
							<td class="wid15"><div id='deptCode'></div></td>
							<td class="wid6 mwid70"><fmt:message key="isoEquipment.team"/></td>
							<td class="wid15"><div id='team'></div></td>
							<td class="wid8 mwid70"><fmt:message key="isoEquipment.subTeam"/></td>
							<td class="wid15"><div id='subTeam'></div></td>
							<td class="wid6 mwid90"><fmt:message key="report.general.locationName"/></td>
							<td class="wid15">	
						       	<input type="text" id="locationName" name="locationName" />
							</td>
							<td></td>
						</tr>
						<tr> 
							<td ><fmt:message key="report.general.diadiem"/></td>
							<td >	
						       	<div id='province'></div>
							</td>	
							<td><fmt:message key="isoEquipment.district"/></td>
							<td >
				           		<div id='district'></div>
							</td>
							<td ><fmt:message key="report.detail.neType"/></td>
							<td >	
						       	<div id='neType'></div>
							</td>
							<td><fmt:message key="isoEquipment.location"/></td>
							<td style="width:100px"><div  id='location'></div>
								</td>
								<td><input type="submit" value="<fmt:message key="global.form.timkiem"/>" id='jqxSubmitButton' />
							</td>
						</tr>				
					</table>
				</td>
		</tr>
		<tr>
			<td colspan="2">
				<%-- <%@ include file="/WEB-INF/jspiso/jqwidgets/gridReportScollbarAndPage.jsp" %> --%>
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
<div style="padding-top: 10px;">
	<table border="0" cellspacing="1" cellpadding="0" width="100%">
		<tr>
			<td class="wid9 mwid110"><fmt:message key="report.detail.tenDonViSuDung"/></td>
			<td class="wid20">
				<input id="tenDonViSuDung" name="tenDonViSuDung" value="${tenDonViSuDung}" class="wid90"/>
			</td>
			<td class="wid9 mwid100"><fmt:message key="report.detail.donViSuDung"/></td>
			<td class="wid20"><input id="donViSuDung" name="donViSuDung" value="${donViSuDung}" class="wid90"/></td>
			<td></td>
		</tr>
		<tr>
			<td><fmt:message key="report.detail.nguoiLapBieu"/></td>
			<td>
				<input id="nguoiLapBieu" name="nguoiLapBieu" value="${nguoiLapBieu}" class="wid90"/>
			</td>
			<td><fmt:message key="report.detail.keToanTruong"/></td>
			<td>
				<input id="keToanTruong" name="keToanTruong" value="${keToanTruong}" class="wid90"/>
			</td>
			<td>
				<input class="button" type="button" name="btnReport" id="btnReport" value="<fmt:message key="global.button.runReport"/>" />
			</td>
		</tr>
	</table>
</div>
</form:form>
<script type="text/javascript">
var theme = getTheme();

$(document).ready(function () {
	${gridReport}
	$("#jqxSubmitButton").jqxButton({theme: theme});
	
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
    
 	// Create a jqxComboBox dsLoaiThietBi
   	var urlNeType = "${pageContext.request.contextPath}/iso/report-detail/dsLoaiThietBi.htm";
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
    
  	//Create a jqxMultile Input
	var renderer = function (itemValue, inputValue) {
	    var terms = inputValue.split(/,\s*/);
	    // remove the current input
	    terms.pop();
	    // add the selected item
	    terms.push(itemValue);
	    // add placeholder to get the comma-and-space at the end
	    terms.push("");
	    var value = terms.join(",");
	    return value;
	};
	
  	//Input alarmMappingName
    ${locationNameList}
    $("#locationName").jqxInput({ placeHolder: "", height: 20, width: '90%', theme: theme,
        source: function (query, response) {
            var item = query.split(/,\s*/).pop();
            // update the search query.
            $("#locationName").jqxInput({ query: item });
            response(locationNameList);
        },
        renderer: renderer
    });
    var locationNameCBB =  "<c:out value='${locationNameCBB}'/>";
    if(locationNameCBB!="")
    	$('#locationName').val(locationNameCBB);
});

//Create a jqxComboBox dsDiaDiem
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

//Create a jqxComboBox district list
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
</script>


<script type="text/javascript">
$("#btnReport").click(function(){
	/* var deptCode = encodeURIComponent($("#deptCode").val());
	var team = encodeURIComponent($("#team").val());
	var subTeam = encodeURIComponent($("#subTeam").val());
	var province = encodeURIComponent($("#province").val());
	var district = encodeURIComponent($("#district").val());
	var neType = encodeURIComponent($("#neType").val());
	var locationName = encodeURIComponent($("#locationName").val()); */
	var tenDonViSuDung = encodeURIComponent($("#tenDonViSuDung").val());
	var nguoiLapBieu = encodeURIComponent($("#nguoiLapBieu").val());
	var donViSuDung = encodeURIComponent($("#donViSuDung").val());
	var keToanTruong = encodeURIComponent($("#keToanTruong").val());

	window.open('${pageContext.request.contextPath}/iso/report-detail/reportDetailDocx.htm?province='+"<c:out value='${province}'/>"+
			'&deptCode='+"<c:out value='${deptCode}'/>"+
			'&team='+"<c:out value='${team}'/>"+
			'&subTeam='+"<c:out value='${subTeam}'/>"+
			'&district='+"<c:out value='${district}'/>"+
			'&location='+"<c:out value='${location}'/>"+
			'&neType='+"<c:out value='${neType}'/>"+
			'&locationName='+"<c:out value='${locationName}'/>"+
			'&tenDonViSuDung=' + tenDonViSuDung +
			'&nguoiLapBieu=' + nguoiLapBieu +
			'&donViSuDung=' + donViSuDung + 
			'&keToanTruong=' + keToanTruong+
			'&strWhere='+$("#strWhere").val()+
       	 	'&sortfield='+$("#sortfield").val()+
       	 	'&sortorder='+$("#sortorder").val(),'_blank','width=300,height=200,location=0,menubar=0,scrollbars=0,status=0,toolbar=0,resizable=0','yes|no|1|0',true);
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

//handle context menu clicks.
$('#Menu').on('itemclick', function (event) {
var args = event.args;
var rowindex = $('#jqxgrid').jqxGrid('getselectedrowindex');
var row = $('#jqxgrid').jqxGrid('getrowdata', rowindex);
// export data
if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>') {
	//$("#jqxgrid").jqxGrid('exportdata', 'xls', exportFileName);
	
	window.open('${pageContext.request.contextPath}/iso/report-detail/exportDetail.htm?province='+"<c:out value='${province}'/>"+
			'&deptCode='+"<c:out value='${deptCode}'/>"+
			'&team='+"<c:out value='${team}'/>"+
			'&subTeam='+"<c:out value='${subTeam}'/>"+
			'&district='+"<c:out value='${district}'/>"+
        	 '&neType='+"<c:out value='${neType}'/>"+
        	 '&location='+"<c:out value='${location}'/>"+
        	 '&locationName='+"<c:out value='${locationName}'/>"+
        	 '&strWhere='+$("#strWhere").val()+
        	 '&sortfield='+$("#sortfield").val()+
        	 '&sortorder='+$("#sortorder").val(),'_blank');
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