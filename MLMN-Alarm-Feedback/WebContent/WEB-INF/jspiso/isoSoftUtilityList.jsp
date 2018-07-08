<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title><fmt:message key="sidebar.admin.isoSoftUtility"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.isoSoftUtility"/></content>

<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left" colspan="2"><form method="post" action="list.htm">
					<table border="0" cellspacing="1" cellpadding="0" width="85%">
						<tr> 
							<td class="wid3 mwid50"><fmt:message key="isoLicenseSoft.vendor"/></td>
							<td><div id='vendor'></div></td>
							<td class="wid3 mwid70"><fmt:message key="isoLicenseSoft.neType"/></td>
							<td><div id='neType'></div></td>
							<td class="wid3 mwid70"><fmt:message key="isoLicenseSoft.ne"/></td>
							<td class="wid15">
								<input type="text" id="neName" name="neName" value="${neName}" class="wid90" />
							</td>	
							<td class="wid7 mwid90"><fmt:message key="isoLicenseSoft.featureCode"/></td>
							<td class="wid15">
								<input type="text" id="featureCode" name="featureCode" value="${featureCode}" class="wid90" />
							</td>
							
							<td class="wid7 mwid90"><fmt:message key="isoLicenseSoft.featureName"/></td>
							<td class="wid15">
								<input type="text" id="featureName" name="featureName" value="${featureName}" class="wid90" />
							</td>		 
							<td>
								<input type="submit" value="<fmt:message key="global.form.timkiem"/>" id='jqxSubmitButton' />
							</td>
							<td></td>
						</tr>				
					</table>
				</form>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<%@ include file="/WEB-INF/jspiso/jqwidgets/gridDefault.jsp" %>
			</td>
		</tr>
</table>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<script type="text/javascript">
$(document).ready(function () {
	var theme = getTheme();
	$("#jqxSubmitButton").jqxButton({theme: theme});
	$("#neName").jqxInput({  height: 20, minLength: 1, theme: theme });
	$("#featureCode").jqxInput({ height: 20, minLength: 1, theme: theme });
	$("#featureName").jqxInput({  height: 20, width:200, minLength: 1, theme: theme }); 

	// Create a jqxComboBox neTypeList
   	var urlNeType = "${pageContext.request.contextPath}/iso/quan-ly-ban-quyen-mem/neTypeList.htm";
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
    $("#neType").jqxComboBox({ source: dataAdapterNeType, displayMember: "value", valueMember: "value", width: 100,height: 20,itemHeight: 30,theme: theme, autoOpen: true });
    var neType =  "<c:out value='${neType}'/>";
    if(neType=="")
		$('#neType').val('ALL');
	else
		$('#neType').val(neType);
      
 	// Create a jqxComboBox vendor list
   	var urlVendor = "${pageContext.request.contextPath}/iso/quan-ly-ban-quyen-mem/vendorList.htm";
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
    $("#vendor").jqxComboBox({ source: dataAdapterVendor, displayMember: "name", valueMember: "value", width: 130,height: 20,itemHeight: 30,theme: theme, autoOpen: true });
    var vendor =  "<c:out value='${vendor}'/>";
    if(vendor=="")
		$('#vendor').val('ALL');
	else
		$('#vendor').val(vendor);
	
});

 

function focusIt()
{
  var mytext = document.getElementById("featureCode");
  mytext.focus();
}

onload = focusIt;

</script>
