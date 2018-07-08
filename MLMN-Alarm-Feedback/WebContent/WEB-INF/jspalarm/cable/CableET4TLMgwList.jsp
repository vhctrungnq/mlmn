<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
  <style type="text/css">
  
  .styleArea{
  	width:150px !important;
  }
  .stylestylePhone{
  	min-width:150px !important;
  	max-width:200px !important;
  }
    
	#doublescroll { overflow: auto; overflow-y: hidden; }   
	#doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
    
     #dvtTeamProcess {
     visibility: visible !important;
    }
   
    #acLow {
     visibility: visible !important;
    }
    
     #bscid_chzn {

		width: 220px !important;
	}
    .ui-autocomplete {
		max-height: 200px;
		overflow-y: auto;
		/* prevent horizontal scrollbar */
		overflow-x: hidden;
	}
	/* IE 6 doesn't support max-height
	* we use height instead, but this forces the menu to always be this tall
	*/
	* html .ui-autocomplete {
		height: 200px;
	}
</style>
<title>${title}</title>
<content tag="heading">${title}</content><div>
<form:form commandName="filter" method="post" action="list.htm"> 
	<table style = "width:100%">
		<tr>
			<td>
				<input type="hidden" id="delData" name="delData">
			</td>
		</tr>
		<tr>	
			<td class="wid5 mwid70"><fmt:message key="cabledropet4tlmgw.system" /></td>
			<td class="wid20">
				<form:input type="text" value="${system}" path ="system" name="system" id="system" class="wid90"/>
			</td>
			
			<td class="wid7 mwid70"><fmt:message key="cabledropet4tlmgw.frSlotPort" /></td>
			<td class="wid20">
				<form:input type="text" value="${frSlotPort}" path ="frSlotPort" name="frSlotPort" id="frSlotPort" class="wid90"/>
			</td>
			
			<td class="wid7 mwid70"><fmt:message key="cabledropet4tlmgw.ddfEt4" /></td>
			<td class="wid20">
				<form:input type="text" value="${ddfEt4}" path ="ddfEt4" name="ddfEt4" id="ddfEt4" class="wid90"/>
			</td> 
			
			<td>
				<input class="button" type="submit" id="submit" value="<fmt:message key="button.search"/>"/>
			</td> 
		</tr>
		<c:if test="${isManager=='Y' }">
		 <tr>
			<td colspan="7" align="right">
	            <a href="upload.htm"><fmt:message key="button.upload"/></a>&nbsp;-
	            <a href="form.htm"><fmt:message key="button.add"/></a>&nbsp;
	        </td>
		</tr> 
		</c:if>
		
	</table>
</form:form>
</div>

<div style="overflow: auto;">
	<display:table name="${Cabledropet4tlmgwList}" class="simple2" id="cableDropeT4TlMgw" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px !important;"> <c:out value="${cableDropeT4TlMgw_rowNum}" /> </display:column>
	  	<display:column property="system" titleKey="cabledropet4tlmgw.system" sortable="true" sortName="SYSTEM" class="styleArea" />
		<display:column property="frSlotPort" titleKey="cabledropet4tlmgw.frSlotPort" sortable="true" sortName="FR_SLOT_PORT" class="styleArea" />
	  	<display:column property="ddfEt4" titleKey="cabledropet4tlmgw.ddfEt4" sortable="true" sortName="DDF_ET4" class="stylePhone" />
	  	<display:column property="description" titleKey="cabledropet4tlmgw.description" sortable="true" sortName="DESCRIPTION" class="styleTime" />
		 
		
		<c:if test="${isManager=='Y' }">
		 <display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style="width:50px !important;">
			<a href="form.htm?id=${cableDropeT4TlMgw.id}"><fmt:message key="button.edit"/></a>&nbsp;
	 			<a href="delete.htm?id=${cableDropeT4TlMgw.id}"
					onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')">
			<fmt:message key="button.delete"/></a>&nbsp; 
	 	</display:column>
		</c:if>
	   	<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />

	</display:table>
</div>

<c:if test="${sizeList > 0}">
	<div class="fr" style="margin-top:-40px">
		<input type="button" id="btnExportAll" name="btnExportAll" value="Xóa tất cả" class="button">
	</div>
</c:if>

<div id="dialog-confirm" title="Thông báo" style="padding: 10px;display:none;">
	<p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
	Export dữ liệu sẽ xóa vào file excel?</p>
</div>

<script type="text/javascript">
function deleteAll() {
	$("#delData").val("1");
	$('#submit').click();
}

function loop(myWindow){
	if (myWindow.closed == true) {
		deleteAll();
	} else {
		setTimeout(function(){loop(myWindow);},10);
	}
}

$("#btnExportAll").click(function() {
	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
	if (answer)
	{
		var url = $('span.excel').closest('a').attr('href');
		
		var loc = window.location;
	    var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('.htm') + 4);
		
	    var myWindow = window.open(pathName + url,'_blank','width=200,height=100');

	   // $(this).dialog("close");
	    
	    loop(myWindow); 
	}
});

function focusIt()
{
  var mytext = document.getElementById("system");
  mytext.focus();
}

onload = focusIt;


var cacheCell = {}, lastXhrCell;
$( "#system" ).autocomplete({
minLength: 1,
source: function( request, response ) {
	var term = request.term;
	if ( term in cacheCell ) {
		response( cacheCell[ term ] );
		return;
	}

	lastXhrCell = $.getJSON( "${pageContext.request.contextPath}/ajax/getAllSystem.htm", request, function( data, status, xhr ) {
		cacheCell[ term ] = data;
		if ( xhr === lastXhrCell ) {
			response( data );
		}
	});
}
});
</script>