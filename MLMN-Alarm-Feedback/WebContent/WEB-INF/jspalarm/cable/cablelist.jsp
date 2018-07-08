<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>Danh sách cáp truyền dẫn</title>
<content tag="heading">Danh sách cáp truyền dẫn</content>

<style>
.wid100{
	width:100%;
}

.dpnone {
	display: none;
}

.pdl10 {
	padding-left:10px;
}

#doublescroll { overflow: auto; overflow-y: hidden; }  
#doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
</style>

<div>
<form:form method="POST" commandName="cableLine">
	<table>
		<tr>
			<td>Tổng đài</td>
			<td>
				<form:input path="sBsc" class="wid100"/>
			</td>
			
			<td class="pdl10">Truyền dẫn</td>
			<td>
				<form:input path="transmission1" class="wid100"/>
			</td>
			
			<td class="pdl10">Vị trí</td>
			<td>
				<form:input path="location1" class="wid100"/>
			</td>
			
			<!-- <td class="pdl10">Thuộc tính</td>
			<td>
				<input type="text" name="thuocTinh" id="thuocTinh" class="wid100" value="${thuocTinh}">
			</td>
			 -->
			<td class="pdl10">
				<input class="button" type="submit" class="button" name="filter" value="<fmt:message key="button.search"/>" />
			</td>
		</tr>
	</table>
</form:form>
</div>
<c:if test="${isManager=='Y' }">
	<div class="ftsize12" align="right"> 
		<span><a href="form.htm" title="Thêm mới">Thêm mới</a></span> - 
		<span><a href="upload.htm" title="Import">Import file</a></span> 
	</div>	 
</c:if>


<div id="doublescroll">
	<display:table name="${cableList}" class="simple2" id="item" requestURI="" export="true" pagesize="15" sort="external" defaultsort="1" >
		<display:column title="STT">
			<c:out value="${item_rowNum}" />
		</display:column>
		<display:column titleKey="cableLine.cableLineId" media="html" sortable="true" sortName="CABLE_LINE_ID">
			<a href="form.htm?id=${item.id }" title="${item.cableLineId}">${item.cableLineId}</a>
		</display:column>
		<display:column property="cableLineId" titleKey="cableLine.cableLineId" class="dpnone" headerClass="dpnone"/>
		<display:column property="sBsc" titleKey="cableLine.sBsc" sortable="true" sortName="S_BSC"/>
		
		<display:column property="transmission1" titleKey="cableLine.transmission1" sortable="true" sortName="TRANSMISSION_1"/>
		<display:column property="location1" titleKey="cableLine.location1" sortable="true" sortName="LOCATION_1"/>
		
		<display:column property="transmission2" titleKey="cableLine.transmission2" sortable="true" sortName="TRANSMISSION_2"/>
		<display:column property="location2" titleKey="cableLine.location2" sortable="true" sortName="LOCATION_2"/>
		
		<display:column property="transmission3" titleKey="cableLine.transmission3" sortable="true" sortName="TRANSMISSION_3"/>
		<display:column property="location3" titleKey="cableLine.location3" sortable="true" sortName="LOCATION_3"/>
		
		<display:column property="transmission4" titleKey="cableLine.transmission4" sortable="true" sortName="TRANSMISSION_4"/>
		<display:column property="location4" titleKey="cableLine.location4" sortable="true" sortName="LOCATION_4"/>
		
		<display:column property="transmission5" titleKey="cableLine.transmission5" sortable="true" sortName="TRANSMISSION_5"/>
		<display:column property="location5" titleKey="cableLine.location5" sortable="true" sortName="LOCATION_5"/>
		
		<display:column property="transmission6" titleKey="cableLine.transmission6" sortable="true" sortName="TRANSMISSION_6"/>
		<display:column property="location6" titleKey="cableLine.location6" sortable="true" sortName="LOCATION_6"/>
		
		<display:column property="eBsc" titleKey="cableLine.eBsc" sortable="true" sortName="E_BSC"/>
		
		<c:if test="${isManager=='Y' }">
		<display:column titleKey="global.management" media="html" class="centerColumnMana" style="min-width: 70px;">
			<a href="form.htm?id=${item.id }" title="${item.cableLineId}"><fmt:message key="global.form.sua"/></a>&nbsp;
 			<a href="#" onclick="javascript:DeleteCableLine('${item.cableLineId}');return false;"><fmt:message key="global.form.xoa"/></a>
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

<script type="text/javascript">
function DeleteCableLine(cableId) {
	var cableLineId = encodeURI(cableId);
	var r = false;
	$.getJSON("${pageContext.request.contextPath}/alarm/cable/ajax/checkDeleteCableLine.htm", {cableLineId: cableLineId}, function(j){
		if (j==0) {
			r = confirm('<fmt:message key="messsage.confirm.delete"/>');
		} else {
			r = confirm('Cáp truyền dẫn đã định nghĩa thuộc tính. Tiếp tục xóa?');
		}

		if (r == true) {
			$.getJSON("${pageContext.request.contextPath}/alarm/cable/ajax/deleteCableLine.htm", {cableLineId: cableLineId}, function(j){
				$("#cableLine").submit();
			});
		}
	});
}

function DoubleScroll(element) {
    var scrollbar= document.createElement('div');
    scrollbar.appendChild(document.createElement('div'));
    scrollbar.style.overflow= 'auto';
    scrollbar.style.overflowY= 'hidden';
    scrollbar.firstChild.style.width= element.scrollWidth+'px';
    scrollbar.firstChild.style.paddingTop= '1px';
    scrollbar.firstChild.appendChild(document.createTextNode('\xA0'));
    scrollbar.onscroll= function() {
        element.scrollLeft= scrollbar.scrollLeft;
    };
    element.onscroll= function() {
        scrollbar.scrollLeft= element.scrollLeft;
    };
    element.parentNode.insertBefore(scrollbar, element);
}

DoubleScroll(document.getElementById('doublescroll'));
</script>