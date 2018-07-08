<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>${title}</title>
<content tag="heading">${title}</content> 	

<div>
<form:form commandName="filter" method="post" action="list.htm?type=${type}">

	<table >
			<tr>
				<td style="width:80px;"><fmt:message key="define.groupName"/></td>
				<td >
					<input type="text" id="groupName" name="groupName" value="${groupName}" style="width: 100px;"/>
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
		<td align="right" class="ftsize12">
		<input class="button"  type="button" value="<fmt:message key="global.button.addNew"/>" id='addNew' onclick='window.location="form.htm?id=&type=${type}"' />
		<%-- <input class="button"  type="button" value="<fmt:message key="global.button.import"/>" id='import' onclick='window.location="upload.htm?type=${type}"' /> --%>
		</td>
		<td style="width: 20px"><div style="float: right;" id="listdefine"></div></td>
	</tr>
</table>
<div id="griddefine"></div>
<div id='Menu'>
            <ul>
            	<li><fmt:message key="global.button.editSelected"/></li>
	            <li><fmt:message key="global.button.deleteMultiSelected"/></li>
		   		<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
</div>
<div class="clear"></div>
<script type="text/javascript">
${griddefine}
$(document).ready(function(){
		// Khai bao sdate, edate
		var theme =  getTheme();
		$('#btFilter').jqxButton({ theme: theme });
		$('#addNew').jqxButton({ theme: theme });
		$('#import').jqxButton({ theme: theme });
		 var type =  "<c:out value='${type}'/>";
		 
		 
	$("#groupName").jqxInput({height: 20, width: 300, minLength: 0, maxLength: 900, theme: theme});
	  // handle context menu clicks.
    $("#Menu").on('itemclick', function (event) {
        var args = event.args;
        
        if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
       	  	var rowindex = $("#griddefine").jqxGrid('getselectedrowindex');
            var row = $("#griddefine").jqxGrid('getrowdata', rowindex);
        	window.location = 'form.htm?id='+row.id+"&type="+type;   
        }
        if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteMultiSelected"/>')  {
        	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
        	if (answer)
        	{
				var selectedrowindexes = $('#griddefine').jqxGrid('getselectedrowindexes'); 
        		var idList="";
        		var cond="";
        		//alert(selectedrowindexes);
        		//var rowIndexList = selectedrowindexes.split(",");
        		if (selectedrowindexes != null) {
        			for (var i=0; i<selectedrowindexes.length; i++) {
        				var row = $("#griddefine").jqxGrid('getrowdata', selectedrowindexes[i]);
        				idList+=cond+row.id;
        				cond=",";
        			}
        			// alert(idList);
        			window.location = 'delete.htm?idList='+idList+"&type="+type;
        		}
   			}
        }
        var exportFileName =  "<c:out value='${exportFileName}'/>";
    	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$("#griddefine").jqxGrid('exportdata', 'xls', exportFileName);
	    }
    });
      
  });
	  

</script>