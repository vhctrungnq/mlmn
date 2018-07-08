<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<title><fmt:message key="title.cableSwitchboard.cableSwitchboardList"/></title>
<content tag="heading"><fmt:message key="title.cableSwitchboard.cableSwitchboardList"/></content>

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
							<td class="wid6 mwid60">
								<fmt:message key="cableSwitchboard.vendor"/>
							</td>
							<td class="wid20">
								<input type="text" id="vendor" name="vendor" value="${vendor}" />
			           		</td>
							<td class="wid8 mwid80"><fmt:message key="cableSwitchboard.name"/></td>
							<td class="wid20">
								<input type="text" id="name" name="name" value="${name}" />
								
							</td>
							<td class="wid8 mwid100"><fmt:message key="cableSwitchboard.site"/></td>
							<td class="wid20">
								<input type="text" id="site" name="site" value="${site}" />
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
				<c:if test="${isManager=='Y' }">
			    	<div style="float: right;margin-bottom:3px;width:200px;">
			        	<table border="0" cellspacing="1" cellpadding="0" width="100%">
							<tr>
								<td align="right">
									<input type="button" value="<fmt:message key="global.button.addNew"/>" id='addNew' />
			      				</td>
								<td style="width:33px"><div style="float: right;" id="jqxlistbox"></div></td>
							</tr>
						</table>
			        </div><br>
			    	</c:if>
			        <div id="jqxgrid"></div>
			        <div id='Menu'>
			            <ul>
			            	<li><fmt:message key="global.button.addNew"/></li>
			             	<li><fmt:message key="global.button.editSelected"/></li>
							<c:if test="${isManager=='Y' }">
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
$("#vendor").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
$("#name").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });
$("#site").jqxInput({ width: '90%', height: 20, minLength: 1, theme: theme });

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
		window.open('${pageContext.request.contextPath}/cable/switchboard/exportData.htm?vendor='+"<c:out value='${vendor}'/>"+
	        	'&site='+"<c:out value='${site}'/>"+
	        	'&name='+"<c:out value='${name}'/>"+
	        	 '&strWhere='+$("#strWhere").val()+
	        	 '&sortfield='+$("#sortfield").val()+
	        	 '&sortorder='+$("#sortorder").val()
	        	 ,'_blank');
	}
});



function focusIt()
{
  var mytext = document.getElementById("vendor");
  mytext.focus();
}
onload = focusIt;
</script>