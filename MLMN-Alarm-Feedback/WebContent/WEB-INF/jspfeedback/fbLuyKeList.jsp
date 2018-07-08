<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxtreegrid.js"></script>   --%>
<title>${title}</title>
<content tag="heading">${title}</content> 	
<div class="ui-tabs-panel">
	<form:form commandName="filter" method="post" action="${function}.htm">
		<table >
			<tr>
                <td style="width:50px">Từ ngày</td>
				<td style="width:90px">
						<input type ="text"  value="${sdate}" name="sdate" id="sdate" size="10" maxlength="10" style="width:70px">
			   			 <img alt="calendar" title="Click to choose the from date" id="chooseSDate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				<td style="width:70px">Đến ngày</td>
				<td style="width:90px">
						<input type ="text"  value="${edate}" name="edate" id="edate" size="10" maxlength="10" style="width:70px">
			   			 <img alt="calendar" title="Click to choose the from date" id="chooseEDate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
				</td>
				<td>Khu vực</td>
				<td>
			        <div id='region'></div>
		        </td>
				<td style="padding-left: 5px;width:70px"><fmt:message key="alarmLog.province"/></td>
				<td>
					 <div id='province'></div>
				</td>
				<td><fmt:message key="baoCaoFeedback.baoCaoTheoNgay.loaiBaoCao"/></td>
				<td>
			        <div id='fbType'></div>
		        </td>
		        <td><spring:message code="qLThongTinPhanAnh.trangThai"/></td>
				<td>
			        <div id='status'></div>
		        </td>
				<td style="padding-left: 5px;">
					<input class="button" type="submit" id="button" value="<fmt:message key="global.form.timkiem"/>" />
				</td>
			</tr>
			
		</table>
	</form:form>
</div>
<br>
<div id="gridReport"></div>
<div id='menuReport'>
     <ul>
          <li><fmt:message key="global.button.exportExcel"/></li>
     </ul>
</div>




<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script type="text/javascript">

	Calendar.setup({
	    inputField		:	"sdate",	// id of the input field
	    ifFormat		:	"%d/%m/%Y",   	// format of the input field
	    button			:   "chooseSDate",  	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	}); 
	Calendar.setup({
	    inputField		:	"edate",	// id of the input field
	    ifFormat		:	"%d/%m/%Y",   	// format of the input field
	    button			:   "chooseEDate",  	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	});

</script>
<script type="text/javascript">
$(document).ready(function(){
	var theme =  getTheme();
	${gridReport}
	// handle context menu clicks.
	$("#menuReport").on('itemclick', function (event) {
		    var args = event.args;
			if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
				window.open('${pageContext.request.contextPath}/feedback/diemden/export.htm?loaiPA='+"<c:out value='${loaiPA}'/>"+
	    	 			'&thoiGianPAFrom='+"<c:out value='${thoiGianPAFrom}'/>"+
	    	 			'&thoiGianPATo='+"<c:out value='${thoiGianPATo}'/>"+
	    	 			'&thoiGianXLFrom='+"<c:out value='${thoiGianXLFrom}'/>"+
	    	 			'&thoiGianXLTo='+"<c:out value='${thoiGianXLTo}'/>"+
	    	 			'&deptProcess='+"<c:out value='${deptProcess}'/>"+
	    	 			'&team='+"<c:out value='${team}'/>"+
	    	 			'&status='+"<c:out value='${status}'/>"
	    			 ,'_blank');
	        }
		   
		});	
		//combobox region
		var urlRegion = "${pageContext.request.contextPath}/ajax/getRegionList.htm";
		var sourceRegion =
		{
		   datatype: "json",
		   url: urlRegion,
		   datafields: [
		                 { name: 'name'},
		                 { name: 'value'}
		             ],
		    async: false
		};
		var dataAdapterregion = new $.jqx.dataAdapter(sourceRegion);
		$("#region").jqxDropDownList({source: dataAdapterregion, displayMember: "value", valueMember: "name",checkboxes: true, width: 120, autoDropDownHeight: true, theme: theme,enableHover: true });           
		
		var cbregion = '<c:out value="${region}"/>';
		// alert(dept);
		if(cbregion=="")
			$('#region').val('Choose');
		else
		{
			var regionVar = cbregion.split(",");
			if (regionVar != null) {
				for (var i=0; i<regionVar.length; i++) {
					$("#region").jqxDropDownList('checkItem', regionVar[i] ); 
				}
			}
		}  
		var region = $("#region").val();
		//combobox province
		var urlprovince = "${pageContext.request.contextPath}/ajax/getProvinceList.htm?region="+region;
		var sourceprovince =
		{
		   datatype: "json",
		   url: urlprovince,
		   datafields: [
		                 { name: 'province'}
		             ],
		    async: false
		};
		var dataAdapterprovince = new $.jqx.dataAdapter(sourceprovince);
		$("#province").jqxDropDownList({source: dataAdapterprovince, displayMember: "province", valueMember: "province",checkboxes: true, width: 120, autoDropDownHeight: true, theme: theme,enableHover: true });           
		
		var cbprovince = '<c:out value="${province}"/>';
		// alert(dept);
		if(cbprovince=="")
			$('#province').val('Choose');
		else
		{
			var provinceVar = cbprovince.split(",");
			if (provinceVar != null) {
				for (var i=0; i<provinceVar.length; i++) {
					$("#province").jqxDropDownList('checkItem', provinceVar[i] ); 
				}
			}
		}  
		
		//combobox fbType
		var urlfbType = "${pageContext.request.contextPath}/ajax/getFBTypeList.htm";
		var sourceFbType =
		{
		   datatype: "json",
		   url: urlfbType,
		   datafields: [
		                 { name: 'code'},
		                 { name: 'name'}
		             ],
		    async: false
		};
		var dataAdapterFbType = new $.jqx.dataAdapter(sourceFbType);
		$("#fbType").jqxDropDownList({source: dataAdapterFbType, displayMember: "name", valueMember: "code",checkboxes: true, width: 170, autoDropDownHeight: true,height: '25', theme: theme,enableHover: true });           
		
		var cbfbType = '<c:out value="${fbType}"/>';
		// alert(dept);
		if(cbfbType=="")
			$('#fbType').val('Choose');
		else
		{
			var fbTypeVar = cbfbType.split(",");
			if (fbTypeVar != null) {
				for (var i=0; i<fbTypeVar.length; i++) {
					$("#fbType").jqxDropDownList('checkItem', fbTypeVar[i] ); 
				}
			}
		}  
		
		
		//combobox fb status
		var urlFbStatus = "${pageContext.request.contextPath}/ajax/getStatusFBList.htm";
		var sourceFbStatus =
		{
		   datatype: "json",
		   url: urlFbStatus,
		   datafields: [
		                 { name: 'code'},
		                 { name: 'name'}
		             ],
		    async: false
		};
		var dataAdapterFbStatus = new $.jqx.dataAdapter(sourceFbStatus);
		$("#status").jqxDropDownList({source: dataAdapterFbStatus, displayMember: "name", valueMember: "code", width: 120, autoDropDownHeight: true, theme: theme,enableHover: true });           
		
		var cbstatus = '<c:out value="${status}"/>';
		// alert(dept);
		if(cbstatus=="")
			$('#status').val('Choose');
		else
		{
			$('#status').val(cbstatus);
		}  
		
		
});
</script>
