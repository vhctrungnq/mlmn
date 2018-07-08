<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<!--   <style type="text/css">
  
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
</style> -->
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
			<td><fmt:message key="cableET4Master.area" /></td>
			<td class="pdl10">
				<form:input type="text" value="${area}" path ="area" name="area" id="area" class="wid100"/>
			</td>
			
			<td class="pdl15"><fmt:message key="cableET4Master.exchange" /></td>
			<td class="pdl10">
				<form:input type="text" value="${exchange}" path ="exchange" name="exchange" id="exchange" class="wid100"/>
			</td>
			
			<td class="pdl15"><fmt:message key="cableET4Master.snt" /></td>
			<td class="pdl10">
				<form:input type="text" value="${snt}" path ="snt" name="snt" id="snt" class="wid100"/>
			</td> 
		</tr>
		<tr>
			<td><fmt:message key="cableET4Master.pos" /></td>
			<td class="pdl10">
				<form:input type="text" value="${pos}" path ="pos" name="pos" id="pos" class="wid100"/>
			</td> 
			
			<td class="pdl15"><fmt:message key="cableET4Master.ddf" /></td>
			<td class="pdl10">
				<form:input type="text" value="${ddf}" path ="ddf" name="ddf" id="ddf" class="wid100"/>
			</td> 
			
			<td class="pdl15"><fmt:message key="cableET4Master.info" /></td>
			<td class="pdl10">
				<form:input type="text" value="${info}" path ="info" name="info" id="info" class="wid100"/>
			</td> 
			
			<td class="pdl15">
				<input class="button" type="submit" id="submit" value="<fmt:message key="button.search"/>"/>
			</td> 
			<td></td>
			<c:if test="${isManager=='Y' }">
				<td align="right">
		            <a href="upload.htm"><fmt:message key="button.upload"/></a>&nbsp;-
		            <a href="form.htm"><fmt:message key="button.add"/></a>&nbsp;
		        </td>
			</c:if>
		    
		</tr> 
	</table>
</form:form>
</div>

<div style="overflow: auto;">
	<display:table name="${cableET4MasterList}" class="simple2" id="cableET4Master" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px !important;"> <c:out value="${cableET4Master_rowNum}" /> </display:column>
	  	<display:column property="area" titleKey="cableET4Master.area" sortable="true" sortName="AREA"/>
		<display:column property="exchange" titleKey="cableET4Master.exchange" sortable="true" sortName="EXCHANGE" />
	  	<display:column property="snt" titleKey="cableET4Master.snt" sortable="true" sortName="SNT" />
	  	<display:column property="sdip" titleKey="cableET4Master.sdip" sortable="true" sortName="SDIP" />
		<display:column property="pos" titleKey="cableET4Master.pos" sortable="true" sortName="POS" />
		<display:column property="posNew" titleKey="cableET4Master.posNew" sortable="true" sortName="POS_NEW" />
		<display:column property="ddf" titleKey="cableET4Master.ddf" sortable="true" sortName="DDF" />
		<display:column property="ddfNew" titleKey="cableET4Master.ddfNew" sortable="true" sortName="DDF_NEW" />
		<display:column property="node" titleKey="cableET4Master.node" sortable="true" sortName="NODE"/>
		<c:if test="${isManager=='Y' }">
		 <display:column titleKey="title.quanLy" media="html" class="centerColumnMana" >
			<a href="form.htm?id=${cableET4Master.id}"><fmt:message key="button.edit"/></a>&nbsp;
	 			<a href="delete.htm?id=${cableET4Master.id}"
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

function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
	}

	var cacheCell = {}, lastXhrCell;
	$( "#area" ).autocomplete({
	minLength: 1,
	source: function( request, response ) {
		var term = request.term;
		if ( term in cacheCell ) {
			response( cacheCell[ term ] );
			return;
		}

		lastXhrCell = $.getJSON( "${pageContext.request.contextPath}/ajax/getAllArea.htm", request, function( data, status, xhr ) {
			cacheCell[ term ] = data;
			if ( xhr === lastXhrCell ) {
				response( data );
			}
		});
	}
	});

	var cacheCell = {}, lastXhrCell;
	$( "#exchange" ).autocomplete({
	minLength: 1,
	source: function( request, response ) {
		var term = request.term;
		if ( term in cacheCell ) {
			response( cacheCell[ term ] );
			return;
		}

		lastXhrCell = $.getJSON( "${pageContext.request.contextPath}/ajax/getAllExchange.htm", request, function( data, status, xhr ) {
			cacheCell[ term ] = data;
			if ( xhr === lastXhrCell ) {
				response( data );
			}
		});
	}
	});
</script>