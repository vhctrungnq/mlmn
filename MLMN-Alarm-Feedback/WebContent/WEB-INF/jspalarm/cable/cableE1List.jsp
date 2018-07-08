<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>${title}</title>
<content tag="heading">${title}</content> 	

<style>
.wid100 { width:100%; }
.dpnone { display: none; }
.pdl10 { padding-left:10px; }
.pdl15 { padding-left:15px; }
#doublescroll { overflow: auto; overflow-y: hidden; }  
#doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
.hidden {display: none;}
</style>

<div>
<form:form method="POST" commandName="cableE1" action="list.htm?typeC=${typeC}">
<%-- <input type="hidden" name="typeC" id="typeC" value="${typeC}"> --%>
	<table>
		<tr>
			<td>
				<form:hidden path="id"/>
				<input type="hidden" id="delData" name="delData">
			</td>
		</tr>
		<tr>
			<td><fmt:message key="cableE1.directionId"/></td>
			<td class="pdl10"><form:input path="directionId" class="wid100"/></td>
			
			<td class="pdl15"><fmt:message key="cableE1.dev"/></td>
			<td class="pdl10"><form:input path="dev" class="wid100"/></td>
			
			<td class="pdl15"><fmt:message key="cableE1.snt"/></td>
			<td class="pdl10"><form:input path="snt" class="wid100"/></td>
			
			<td class="pdl15"><fmt:message key="cableE1.dip"/></td>
			<td ><form:input path="dip" class="wid100"/></td>
		</tr>
		<tr>	
			<td ><fmt:message key="cableE1.ddfOut"/></td>
			<td class="pdl10"><form:input path="ddfOut" class="wid100"/></td>
			
			<td class="pdl15"><fmt:message key="cableE1.state"/></td>
			<td class="pdl10"><form:input path="state" class="wid100"/></td>
			
			<td class="pdl15"><fmt:message key="cableE1.planeNext"/></td>
			<td class="pdl10"><form:input path="planeNext" class="wid100"/></td>
			
			<td class="pdl15"><fmt:message key="cableE1.noiDungKhac"/></td>
			<td class="pdl10"><form:input path="description" class="wid100"/></td>
			
			<td class="pdl15">
				<input class="button" type="submit" class="button" name="filter" value="<fmt:message key="button.search"/>" />
			</td>
			<td></td>
		</tr>
	</table>
</form:form>
</div>
<c:if test="${isManager=='Y' }">
	<div class="ftsize12" align="right" style="margin-top:5px"> 
		<span><a href="form.htm?typeC=${typeC}" title="Thêm mới">Thêm mới</a></span> - 
		<span><a href="upload.htm?typeC=${typeC}" title="Import">Import file</a></span> 
	</div>		 
</c:if>

<div>
	<display:table name="${cableE1List}" class="simple2" id="item" requestURI="" export="true" 
							pagesize="50" sort="external" defaultsort="1" >
	
		<display:column title="STT">
			<c:out value="${item_rowNum}" />
		</display:column>
		<display:column property="directionId" titleKey="cableE1.directionId" sortable="true" sortName="DIRECTION_ID"/>
		<display:column property="rp" titleKey="cableE1.rp" sortable="true" sortName="RP"/>
		<display:column property="em" titleKey="cableE1.em" sortable="true" sortName="EM"/>
		<display:column property="dev" titleKey="cableE1.dev" sortable="true" sortName="DEV"/>
		<display:column property="snt" titleKey="cableE1.snt" sortable="true" sortName="SNT"/>
		
		<display:column property="dip" titleKey="cableE1.dip" sortable="true" sortName="DIP"/>
		<display:column property="sntinl" titleKey="cableE1.sntinl" sortable="true" sortName="SNTINL"/>
		<display:column property="ddfOut" titleKey="cableE1.ddfOut" sortable="true" sortName="DDF_OUT"/>
		<display:column property="state" titleKey="cableE1.state" sortable="true" sortName="STATE"/>
		<display:column property="node" titleKey="cableE1.node" sortable="true" sortName="NODE"/>
		<display:column property="planeNext" titleKey="cableE1.planeNext" sortable="true" sortName="PLANE_NEXT"/>
		<c:if test="${typeC == 'E1_BSG'}">
			<display:column property="directionTransmission" titleKey="cableE1.directionTransmission" sortable="true" sortName="DIRECTION_TRANSMISSION"/>
		</c:if>
		<display:column property="description" titleKey="cableE1.description" sortable="true" sortName="DESCRIPTION"/>
		<c:if test="${isManager=='Y' }">
			<display:column titleKey="global.management" media="html" class="centerColumnMana" style="min-width: 70px;">
					<a href="form.htm?id=${item.id}&typeC=${typeC}"><fmt:message key="global.form.sua"/></a>&nbsp;
		 			<a href="#" onclick="javascript:DeleteCableE1(${item.id});return false;"><fmt:message key="global.form.xoa"/></a>
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
	<div class="fr" style="margin-top:-50px">
		<input type="button" id="btnExportAll" name="btnExportAll" value="Xóa tất cả" class="button">
	</div>
</c:if>

<div id="dialog-confirm" title="Thông báo" style="padding: 10px;display:none;">
	<p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
	Export dữ liệu sẽ xóa vào file excel?</p>
</div>
	 <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxwindow.js"></script>
     <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxpanel.js"></script>
<script type="text/javascript">

function deleteAll() {
	$("#delData").val("1");
	$('#cableE1').submit();
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
	/*$("#dialog-confirm").dialog({
		
		resizable: false,
		height: 120,
		modal: true,
		buttons: {
			"Đồng ý": function() {
				var url = $('span.excel').closest('a').attr('href');
				
				var loc = window.location;
			    var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('.htm') + 4);
				
			    var myWindow = window.open(pathName + url,'_blank','width=200,height=100');

			    $(this).dialog("close");
			    
			    loop(myWindow);
			},
			"Không": function() {
				deleteAll();
			},
			"Hủy": function() {
				$(this).dialog("close");
			}
		}
	});*/
});

function DeleteCableE1(id) {
	var r = false;
	r = confirm('<fmt:message key="messsage.confirm.delete"/>');
		if (r == true) {
			$.getJSON("${pageContext.request.contextPath}/alarm/cableE1/ajax/deleteCableE1.htm", {id: id}, function(j){
				$("#cableE1").submit();
			});
		}
}
$("#directionId").autocomplete({
	source: '${pageContext.request.contextPath}/alarm/cableE1/ajax/get-cableE1-inf-search.htm?focus=directionId&typeC=${typeC}',
});

$("#dev").autocomplete({
	source: '${pageContext.request.contextPath}/alarm/cableE1/ajax/get-cableE1-inf-search.htm?focus=dev&typeC=${typeC}',
});

$("#snt").autocomplete({
	source: '${pageContext.request.contextPath}/alarm/cableE1/ajax/get-cableE1-inf-search.htm?focus=snt&typeC=${typeC}',
});

$("#dip").autocomplete({
	source: '${pageContext.request.contextPath}/alarm/cableE1/ajax/get-cableE1-inf-search.htm?focus=dip&typeC=${typeC}',
});

$("#ddfOut").autocomplete({
	source: '${pageContext.request.contextPath}/alarm/cableE1/ajax/get-cableE1-inf-search.htm?focus=ddfOut&typeC=${typeC}',
});

$("#state").autocomplete({
	source: '${pageContext.request.contextPath}/alarm/cableE1/ajax/get-cableE1-inf-search.htm?focus=state&typeC=${typeC}',
});

$("#planeNext").autocomplete({
	source: '${pageContext.request.contextPath}/alarm/cableE1/ajax/get-cableE1-inf-search.htm?focus=planeNext&typeC=${typeC}',
});

</script>