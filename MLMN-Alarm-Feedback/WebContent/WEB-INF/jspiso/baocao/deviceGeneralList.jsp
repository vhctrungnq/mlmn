<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title><fmt:message key="title.iso.report.general"/></title>
<content tag="heading"><fmt:message key="title.iso.report.general"/></content>
<form method="post" action="list.htm">
<div>
	<input id="strWhere" name="strWhere" value="" type="hidden"/>
	<input id="sortfield" name="sortfield" value="" type="hidden"/>
	<input id="sortorder" name="sortorder" value="" type="hidden"/>
</div>
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left" colspan="2">
					<table border="0" cellspacing="1" cellpadding="0" width="100%">
						<tr> 
							<td class="wid8 mwid100"><fmt:message key="report.general.locationName"/></td>
							<td class="wid20">	
						       	<input type="text" id="locationName" name="locationName" />
							</td>		 
							<td>
								<input type="submit" value="<fmt:message key="global.form.timkiem"/>" id='jqxSubmitButton' />
							</td>
						</tr>				
					</table>
				</td>
		</tr>
		<tr>
			<td colspan="2">
				<%@ include file="/WEB-INF/jspiso/jqwidgets/gridReportScollbarAndPage.jsp" %>
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
			<td class="wid9 mwid100"><fmt:message key="report.general.phieuKKSo"/></td>
			<td class="wid20"><input id="phieuKKSo" name="phieuKKSo" value="${phieuKKSo}" class="wid90"/></td>
			<td><input class="button" type="button" name="btnReport" id="btnReport" value="<fmt:message key="global.button.runReport"/>" /></td>
		</tr>
	</table>
</div>
</form>
<script type="text/javascript">
$(document).ready(function () {
	var theme = getTheme();
	$("#jqxSubmitButton").jqxButton({theme: theme});
	
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
    $("#locationName").jqxInput({ placeHolder: "", height: 20, width: '60%', theme: theme,
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

</script>

<script type="text/javascript">
$("#btnReport").click(function(){
	var locationName = encodeURIComponent($("#locationName").val());
	var tenDonViSuDung = encodeURIComponent($("#tenDonViSuDung").val());
	var phieuKKSo = encodeURIComponent($("#phieuKKSo").val());
	window.open('${pageContext.request.contextPath}/iso/report-general/reportGeneralDocx.htm?locationName=' + locationName+'&tenDonViSuDung=' + tenDonViSuDung
			+'&phieuKKSo=' + phieuKKSo+
			'&strWhere='+$("#strWhere").val()+
       	 	'&sortfield='+$("#sortfield").val()+
       	 	'&sortorder='+$("#sortorder").val(),'_blank','width=300,height=200,location=0,menubar=0,scrollbars=0,status=0,toolbar=0,resizable=0','yes|no|1|0',true);
});
</script>