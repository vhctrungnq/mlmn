<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>${title}</title>
<content tag="heading">${title}</content> 	

<div>
<form:form commandName="filter" method="post" action="list.htm">
<input type="hidden" name="reloadStr" id="reloadStr" value="${reloadStr}">
	<table >
			<tr>
				
				<td style="width:70px;"><fmt:message key="emailMaster.mailLevel"/></td>
				<td>
					<div id='mailLevel'></div>
				</td>
				<td style="padding-left: 5px;">
					<input class="button" type="submit" id="btFilter" name="btFilter" value="<fmt:message key="global.form.timkiem"/>" />
				</td>
				
			</tr>
		</table>
	</form:form>
</div>
<div class="clear"></div>	
<table style="width:100%">
	<tr>
	<%-- 	<td align="right" class="ftsize12">
		<input class="button"  type="button" value="<fmt:message key="global.button.addNew"/>" id='addNew' onclick='window.location="form.htm?id="' />
		</td> --%>
		<td align="right"><div style="float: right;" id="listMailParameterMaster"></div></td>
	</tr>
</table>
<div id="gridMailParameterMaster"></div>
<div id='Menu'>
            <ul>
            	<li><fmt:message key="global.button.editSelected"/></li>
	           <%--  <li><fmt:message key="global.button.deleteMultiSelected"/></li> --%>
		   		<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
</div>
<div class="clear"></div>
<script type="text/javascript">

${gridMailParameterMaster}
$(document).ready(function(){
		// Khai bao sdate, edate
		var theme =  getTheme();
	// Create a jqxComboBox Type Email Hour
       	var urlTypeMail = "${pageContext.request.contextPath}/ajax/getTypeEmailHourList.htm";
        // prepare the data
        var sourceTypeMail =
        {
            datatype: "json",
            datafields: [
                { name: 'value' },
                { name: 'name' }
            ],
            url: urlTypeMail,
            async: false
        };
        var dataAdapterTypeMail = new $.jqx.dataAdapter(sourceTypeMail);
        // Create a jqxComboBox
        $("#mailLevel").jqxComboBox({ source: dataAdapterTypeMail, displayMember: "value", valueMember: "name", width: 120,height: '20px',itemHeight: 30,theme: theme,autoDropDownHeight: true,autoOpen: true });
        var typeMail =  "<c:out value='${mailLevel}'/>";
        if(typeMail=="")
			$('#mailLevel').val('ALL');
		else
			$('#mailLevel').val(typeMail);
        
	  // handle context menu clicks.
    $("#Menu").on('itemclick', function (event) {
        var args = event.args;
        
        if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
       	  	var rowindex = $("#gridMailParameterMaster").jqxGrid('getselectedrowindex');
            var row = $("#gridMailParameterMaster").jqxGrid('getrowdata', rowindex);
        	window.location = 'form.htm?id='+row.id;   
        }
       /*  if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteMultiSelected"/>')  {
        	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
        	if (answer)
        	{
				var selectedrowindexes = $('#gridMailParameterMaster').jqxGrid('getselectedrowindexes'); 
        		var idList="";
        		var cond="";
        		//alert(selectedrowindexes);
        		//var rowIndexList = selectedrowindexes.split(",");
        		if (selectedrowindexes != null) {
        			for (var i=0; i<selectedrowindexes.length; i++) {
        				var row = $("#gridMailParameterMaster").jqxGrid('getrowdata', selectedrowindexes[i]);
        				idList+=cond+row.id;
        				cond=",";
        			}
        			// alert(idList);
        			window.location = 'delete.htm?idList='+idList;
        		}
   			}
        } */
        var exportFileName =  "<c:out value='${exportFileName}'/>";
    	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$("#gridMailParameterMaster").jqxGrid('exportdata', 'xls', exportFileName);
	    }
    }); 
});
</script>