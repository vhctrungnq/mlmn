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
<form:form method="POST" commandName="cableTransmission" action="list.htm?typeC=${typeC}">
<%-- <input type="hidden" name="typeC" id="typeC" value="${typeC}"> --%>
	<table>
		<tr>
			<td>
				<form:hidden path="id"/>
				<input type="hidden" id="delData" name="delData">
			</td>
		</tr>
		<tr>
			<td><fmt:message key="cableTransmission.directionId"/></td>
			<td class="pdl10">
				<c:choose>
					<c:when test="${directionIdCheck != null}">
						<input type="checkbox" name="directionIdCheck" id="directionIdCheck" checked="checked" class="odf">
						<form:input path="directionId" class="directionIdCheck" />
					</c:when>
					<c:otherwise>
						<input type="checkbox" name="directionIdCheck" id="directionIdCheck" class="odf">
						<form:input path="directionId" class="directionIdCheck hidden" />
					</c:otherwise>
				</c:choose>
			</td>
			
			<td class="pdl15"><fmt:message key="cableTransmission.cableId"/></td>
			<td><form:input path="cableId" class="wid100"/></td>
			
			<td class="pdl15"><fmt:message key="cableTransmission.vendor"/></td>
			<td class="pdl10"><form:input path="vendor" class="wid100"/></td>
			
			<td class="pdl15"><fmt:message key="cableTransmission.enSourceTK"/></td>
			<td class="pdl10"><form:input path="enSource" class="wid100"/></td>
		</tr>
		<tr>
			<td><fmt:message key="cableTransmission.CardPort"/></td>
			<td class="pdl10"><form:input path="cardPortSource" class="wid100"/></td>
			
			<td class="pdl15"><fmt:message key="cableTransmission.ODFTK"/></td>
			<td><form:input path="odfSource" class="wid100"/></td>
			
			<td class="pdl15"><fmt:message key="cableTransmission.noiDungKhac"/></td>
			<td class="pdl10"><form:input path="description" class="wid100"/></td>
			
			<td class="pdl15">
				<input class="button" type="submit" class="button" name="filter" value="<fmt:message key="button.search"/>" />
			</td>
			<td></td>
		</tr>
		<c:choose>
			<c:when test="${typeC == 'BSC' || typeC == 'BSC_MGW'||typeC == 'IPBB_LTT'||typeC == 'NodeTDQH'}">
			<tr>
				<td colspan="8" id="checkboxs">
					<span>
						<c:choose>
							<c:when test="${cableTransmission.odf1 != null}">
								<input type="checkbox" name="odf1" id="odf1" checked="checked" class="odf">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="odf1" id="odf1" class="odf">
							</c:otherwise>
						</c:choose>
						<c:choose>
				            <c:when test="${typeC == 'IPBB_LTT'}">
				               <fmt:message key="cableTransmission.odfOsn1"/>
				            </c:when>
							<c:otherwise>
								<fmt:message key="cableTransmission.ODF1TK"/>
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<c:choose>
							<c:when test="${cableTransmission.odf2 != null}">
								<input type="checkbox" name="odf2" id="odf2" checked="checked" class="odf">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="odf2" id="odf2" class="odf">
							</c:otherwise>
						</c:choose>
						<c:choose>
				            <c:when test="${typeC == 'IPBB_LTT'}">
				               <fmt:message key="cableTransmission.odfOsn2"/>
				            </c:when>
							<c:otherwise>
								<fmt:message key="cableTransmission.ODF2TK"/>
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<c:choose>
							<c:when test="${cableTransmission.odf3 != null}">
								<input type="checkbox" name="odf3" id="odf3" checked="checked" class="odf">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="odf3" id="odf3" class="odf">
							</c:otherwise>
						</c:choose>
						<c:choose>
				            <c:when test="${typeC == 'IPBB_LTT'}">
				               <fmt:message key="cableTransmission.odfOsnLtt3"/>
				            </c:when>
							<c:otherwise>
								<fmt:message key="cableTransmission.ODFDT1TK"/>
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<c:choose>
							<c:when test="${cableTransmission.odf4 != null}">
								<input type="checkbox" name="odf4" id="odf4" checked="checked" class="odf">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="odf4" id="odf4" class="odf">
							</c:otherwise>
						</c:choose>
						<c:choose>
				            <c:when test="${typeC == 'IPBB_LTT'}">
				               <fmt:message key="cableTransmission.odfOsnLtt4"/>
				            </c:when>
							<c:otherwise>
								<fmt:message key="cableTransmission.ODFDT2TK"/>
							</c:otherwise>
						</c:choose>
					</span>
				</td>
			</tr>
		</c:when>
		<c:otherwise>
			<tr>
			<td colspan="8" id="checkboxs">
					<span class="pdl15">
						<c:choose>
							<c:when test="${cableTransmission.odf3 != null}">
								<input type="checkbox" name="odf3" id="odf3" checked="checked" class="odf">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="odf3" id="odf3" class="odf">
							</c:otherwise>
						</c:choose>
						<c:choose>
				            <c:when test="${typeC == 'IPBB_LTT'}">
				               <fmt:message key="cableTransmission.odfOsnLtt3"/>
				            </c:when>
				            <c:when test="${typeC == 'GMSC'}">
				               <fmt:message key="cableTransmission.odfDdfVendor1"/>
				            </c:when>
							<c:otherwise>
								<fmt:message key="cableTransmission.ODFDT1TK"/>
							</c:otherwise>
						</c:choose>
					</span>
					
					<span class="pdl15">
						<c:choose>
							<c:when test="${cableTransmission.odf4 != null}">
								<input type="checkbox" name="odf4" id="odf4" checked="checked" class="odf">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="odf4" id="odf4" class="odf">
							</c:otherwise>
						</c:choose>
						<c:choose>
				            <c:when test="${typeC == 'IPBB_LTT'}">
				               <fmt:message key="cableTransmission.odfOsnLtt4"/>
				            </c:when>
				            <c:when test="${typeC == 'GMSC'}">
				               <fmt:message key="cableTransmission.odfDdfVendor2"/>
				            </c:when>
							<c:otherwise>
								<fmt:message key="cableTransmission.ODFDT2TK"/>
							</c:otherwise>
						</c:choose>
					</span>
				</td>
				</tr>
		</c:otherwise>
	</c:choose>
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
	<display:table name="${cableTransmissionList}" class="simple2" id="item" requestURI="" export="true" 
							pagesize="50" sort="external" defaultsort="1" >
	
		<display:column title="STT">
			<c:out value="${item_rowNum}" />
		</display:column>
		<display:column property="directionId" titleKey="cableTransmission.directionId" sortable="true" sortName="DIRECTION_ID" headerClass="directionIdCheck hidden" class="directionIdCheck hidden" style="max-width: 150px;"/>
		<display:column property="cableId" titleKey="cableTransmission.cableId" sortable="true" sortName="CABLE_ID"/>
		<display:column property="flowConnection" titleKey="cableTransmission.flowConnection" sortable="true" sortName="FLOW_CONNECTION"  style="min-width:60px;"/>
		<display:column property="enSource" titleKey="cableTransmission.enSource" sortable="true" sortName="EN_SOURCE"/>
		<display:column property="cardPortSource" titleKey="cableTransmission.cardPortSource" sortable="true" sortName="CARD_PORT_SOURCE"/>
		
		<c:choose>
            <c:when test="${typeC == 'GMSC'}">
               <display:column property="odfSource" titleKey="cableTransmission.odfDdfSource" sortable="true" sortName="ODF_SOURCE"/>
			</c:when>
			<c:otherwise>
				<display:column property="odfSource" titleKey="cableTransmission.odfSource" sortable="true" sortName="ODF_SOURCE"/>
			</c:otherwise>
		</c:choose>
		
		<c:if test="${typeC == 'BSC' || typeC == 'BSC_MGW'||typeC == 'NodeTDQH'}">
			<display:column property="positionEt" titleKey="cableTransmission.positionEt" sortable="true" sortName="POSITION_ET"/>
			<display:column property="cardPort1" titleKey="cableTransmission.cardPort1" sortable="true" sortName="CARD_PORT_1" headerClass="odf1 hidden" class="odf1 hidden "/>
			<display:column property="odf1" titleKey="cableTransmission.odf1" sortable="true" sortName="ODF_1" headerClass="odf1 hidden" class="odf1 hidden" style="max-width: 60px;"/>
			<display:column property="cardPort2" titleKey="cableTransmission.cardPort2" sortable="true" sortName="CARD_PORT_2" headerClass="odf2 hidden" class="odf2 hidden"/>
			<display:column property="odf2" titleKey="cableTransmission.odf2" sortable="true" sortName="ODF_2" headerClass="odf2 hidden" class="odf2 hidden"/>
	   	</c:if>
		<c:if test="${typeC == 'IPBB_LTT'}">
			<display:column property="odf1" titleKey="cableTransmission.odfOsn1" sortable="true" sortName="ODF_1" headerClass="odf1 hidden" class="odf1 hidden" style="max-width: 60px;"/>
			<display:column property="odf2" titleKey="cableTransmission.odfOsn2" sortable="true" sortName="ODF_2" headerClass="odf2 hidden" class="odf2 hidden"/>
		</c:if>
		<c:choose>
            <c:when test="${typeC == 'GMSC'}">
               <display:column property="odfVendor1" titleKey="cableTransmission.odfDdfVendor1" sortable="true" sortName="ODF_VENDOR_1" headerClass="odf3 hidden" class="odf3 hidden"/>
				<display:column property="transmission" titleKey="cableTransmission.transmission" sortable="true" sortName="TRANSMISSION"/>
				<display:column property="odfVendor2" titleKey="cableTransmission.odfDdfVendor2" sortable="true" sortName="ODF_VENDOR_2" headerClass="odf4 hidden" class="odf4 hidden"/>
			</c:when>
			<c:otherwise>
				<display:column property="odfVendor1" titleKey="cableTransmission.odfVendor1" sortable="true" sortName="ODF_VENDOR_1" headerClass="odf3 hidden" class="odf3 hidden"/>
				<display:column property="transmission" titleKey="cableTransmission.transmission" sortable="true" sortName="TRANSMISSION"/>
				<display:column property="odfVendor2" titleKey="cableTransmission.odfVendor2" sortable="true" sortName="ODF_VENDOR_2" headerClass="odf4 hidden" class="odf4 hidden"/>
			</c:otherwise>
		</c:choose>
		<c:if test="${typeC == 'BSC_MGW'}">
			<display:column property="odfReplace" titleKey="cableTransmission.odfReplace" sortable="true" sortName="ODF_REPLACE"/>
	   	</c:if>
	   	<c:if test="${typeC == 'IPBB_LTT'}">
			<display:column property="odfOsnLtt3" titleKey="cableTransmission.odfOsnLtt3" sortable="true" sortName="ODF_OSN_LTT3" headerClass="odf3 hidden" class="odf3 hidden"/>
			<display:column property="odfOsnLtt4" titleKey="cableTransmission.odfOsnLtt4" sortable="true" sortName="ODF_OSN_LTT4" headerClass="odf4 hidden" class="odf4 hidden"/>
	   	</c:if>
		<display:column property="odfDestination" titleKey="cableTransmission.odfDestination" sortable="true" sortName="ODF_DESTINATION"/>
		<display:column property="cardPortDestination" titleKey="cableTransmission.cardPortDestination" sortable="true" sortName="CARD_PORT_DESTINATION"/>
		
		<display:column property="enDestination" titleKey="cableTransmission.enDestination" sortable="true" sortName="EN_DESTINATION"/>
		<display:column property="vendor" titleKey="cableTransmission.vendor" sortable="true" sortName="VENDOR"/>
		<display:column property="bandwith" titleKey="cableTransmission.bandwith" sortable="true" sortName="BANDWITH"/>
		<display:column property="description" titleKey="cableTransmission.description" sortable="true" sortName="DESCRIPTION"/>
		<c:if test="${isManager=='Y' }">
			<display:column titleKey="global.management" media="html" class="centerColumnMana" style="min-width: 70px;">
				<a href="form.htm?id=${item.id }&typeC=${typeC}" title="${item.cableId}"><fmt:message key="global.form.sua"/></a>&nbsp;
	 			<a href="#" onclick="javascript:DeleteCableTms('${item.cableId}', ${item.id});return false;"><fmt:message key="global.form.xoa"/></a>
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

<script type="text/javascript">
function deleteAll() {
	$("#delData").val("1");
	$('#cableTransmission').submit();
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
	
	/* $("#dialog-confirm").dialog({
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
	}); */
});

function DeleteCableTms(cableId, id) {
	var cableId = encodeURI(cableId);
	var r = false;
	$.getJSON("${pageContext.request.contextPath}/alarm/cable/ajax/checkDeleteCableTms.htm", {cableId: cableId}, function(j){
		if (j==0) {
			r = confirm('<fmt:message key="messsage.confirm.delete"/>');
		} else {
			r = confirm('Cáp truyền dẫn đã định nghĩa thuộc tính. Tiếp tục xóa?');
		}

		if (r == true) {
			$.getJSON("${pageContext.request.contextPath}/alarm/cable/ajax/deleteCableTms.htm", {cableId: cableId, id: id}, function(j){
				$("#cableTransmission").submit();
			});
		}
	});
}

$(document).ready(function() {
	$('#checkboxs input:checked').each(function() {
		var ID = $(this).attr('id');
	
		$("."+ID).removeClass("hidden");
	});
});

$("#directionId").autocomplete({
	source: '${pageContext.request.contextPath}/alarm/cable/ajax/get-CableTransmission-inf-search.htm?focus=directionId&typeC=${typeC}',
});

$("#cableId").autocomplete({
	source: '${pageContext.request.contextPath}/alarm/cable/ajax/get-CableTransmission-inf-search.htm?focus=cableId&typeC=${typeC}',
});

$("#vendor").autocomplete({
	source: '${pageContext.request.contextPath}/alarm/cable/ajax/get-CableTransmission-inf-search.htm?focus=vendor&typeC=${typeC}',
});

$("#enSource").autocomplete({
	source: '${pageContext.request.contextPath}/alarm/cable/ajax/get-CableTransmission-inf-search.htm?focus=enSource&typeC=${typeC}',
});

$('.odf').change(function() {
	var ID = $(this).attr('id');
	alert("ID = "+ID);
	if ($(this).is(':checked')) {
		$("."+ID).removeClass("hidden");
	} else {
		$("."+ID).addClass("hidden");
		
	}
});

$('.directionIdCheck').change(function() {
	var ID = $(this).attr('id');
	if ($(this).is(':checked')) {
		$("."+ID).removeClass("hidden");
	} else {
		$("."+ID).addClass("hidden");
	}
});
</script>