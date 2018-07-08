<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
			<td><fmt:message key="cableDropEt4direction.circuit" /></td>
			<td class="pdl10">
				<form:input type="text" value="${circuit}" path ="circuit" name="circuit" id="circuit" class="wid100"/>
			</td>
			
			<td class="pdl15"><fmt:message key="cableDropEt4direction.dipName" /></td>
			<td class="pdl10">
				<form:input type="text" value="${dipName}"  path ="dipName" name="dipName" id="dipName" class="wid100"/>
			</td> 
			
			<td class="pdl15"><fmt:message key="cableDropEt4direction.dipFinish" /></td>
			<td class="pdl10">
				<form:input type="text" value="${dipFinish}" path ="dipFinish" name="dipFinish" id="dipFinish" class="wid100"/>
			</td> 
			
			<td class="pdl15"><fmt:message key="cableDropEt4direction.direction" /></td>
			<td class="pdl10">
				<form:input type="text" value="${direction}" path ="direction" name="direction" id="direction" class="wid100"/>
			</td> 
		</tr>
		<tr>
			<td><fmt:message key="cableDropEt4direction.dipp" /></td>
			<td class="pdl10">
				<form:input type="text" value="${dipp}" path ="dipp" name="dipp" id="dipp" class="wid100"/>
			</td> 
			
			<td class="pdl15"><fmt:message key="cableDropEt4direction.ddfHead" /></td>
			<td class="pdl10">
				<form:input type="text" value="${ddfHead}" path ="ddfHead" name="ddfHead" id="ddfHead" class="wid100"/>
			</td> 
			
			<td class="pdl15"><fmt:message key="cableDropEt4direction.ddfFinish" /></td>
			<td class="pdl10">
				<form:input type="text" value="${ddfFinish}" path ="ddfFinish" name="ddfFinish" id="ddfFinish" class="wid100"/>
			</td> 
			
			<td class="pdl15"><fmt:message key="cableDropEt4direction.status" /></td>
			<td class="pdl10">
				<form:input type="text" value="${status}" path ="status" name="status" id="status" class="wid100"/>
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
	<display:table name="${cableDropEt4directionList}" class="simple2" id="cableDropeT4Driection" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px !important;"> <c:out value="${cableDropeT4Driection_rowNum}" /> </display:column>
	  	<display:column property="circuit" titleKey="cableDropEt4direction.circuit" sortable="true" sortName="CIRCUIT" />
		<display:column property="dipName" titleKey="cableDropEt4direction.dipName" sortable="true" sortName="DIP_NAME" />
	  	<display:column property="direction" titleKey="cableDropEt4direction.direction" sortable="true" sortName="DIRECTION" />
	  	<display:column property="dipp" titleKey="cableDropEt4direction.dipp" sortable="true" sortName="DIPP"  />
		<display:column property="ddfHead" titleKey="cableDropEt4direction.ddfHead" sortable="true" sortName="DDF_HEAD" />
		<display:column property="dipFinish" titleKey="cableDropEt4direction.dipFinish" sortable="true" sortName="DIP_FINISH" />
		<display:column property="ddfFinish" titleKey="cableDropEt4direction.ddfFinish" sortable="true" sortName="DDF_FINISH" />
		<display:column property="description" titleKey="cableDropEt4direction.description" sortable="true" sortName="DESCRIPTION" />
		<display:column property="status" titleKey="cableDropEt4direction.status" sortable="true" sortName="STATUS" />
		<c:if test="${isManager=='Y' }">
		 <display:column titleKey="title.quanLy" media="html" class="centerColumnMana">
			<a href="form.htm?id=${cableDropeT4Driection.id}"><fmt:message key="button.edit"/></a>&nbsp;
	 			<a href="delete.htm?id=${cableDropeT4Driection.id}"
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
	$( "#circuit" ).autocomplete({
	minLength: 1,
	source: function( request, response ) {
		var term = request.term;
		if ( term in cacheCell ) {
			response( cacheCell[ term ] );
			return;
		}
	
		lastXhrCell = $.getJSON( "${pageContext.request.contextPath}/ajax/getAllCiruit.htm", request, function( data, status, xhr ) {
			cacheCell[ term ] = data;
			if ( xhr === lastXhrCell ) {
				response( data );
			}
		});
	}
	});
</script>
<!-- <script type="text/javascript">
function keypress(e){
var keypressed = null;
if (window.event)
{
keypressed = window.event.keyCode; //IE
}
else {

keypressed = e.which; //NON-IE, Standard
}
if (keypressed < 48 || keypressed > 57)
{ 
if (keypressed == 8 || keypressed == 127)
{
return;
}
return false;
}
}
</script> -->
