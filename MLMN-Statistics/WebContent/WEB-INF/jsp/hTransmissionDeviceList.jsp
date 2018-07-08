<%@ include file="/commons/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<title><fmt:message key="hTransmissionDevice.title" /></title>
<content tag="heading">
<fmt:message key="hTransmissionDevice.title" /></content>

<ul class="ui-tabs-nav">
	<li class = "" id="all"><a href="${pageContext.request.contextPath}/transmission-device/list.htm"><span>Tất cả</span></a></li>				
	<li class = "" id="MT"><a href="${pageContext.request.contextPath}/transmission-device/list.htm?region=MT"><span>Miền Tây</span></a></li>				
	<li class = "" id="MD"><a href="${pageContext.request.contextPath}/transmission-device/list.htm?region=MD"><span>Miền Đông</span></a></li>				
	<li class = "" id="HCM"><a href="${pageContext.request.contextPath}/transmission-device/list.htm?region=HCM"><span>TP Hồ Chí Minh</span></a></li>				
</ul>
<div class="ui-tabs-panel">

	<form:form commandName="filter" method="post" action="list.htm"
		id="filterform">
		<form:hidden path="region" id="region" />
		<table style="width: 100%">
			<tr>
				<td><fmt:message key="hTransmissionDevice.province" /></td>
				<td><form:select path="province" id="province"
						style="width: 150px;height:20px; padding-top: 0px;"
						onchange="xl()">
						<option value=""><fmt:message key="global.All" /></option>
						<c:forEach var="item" items="${provinceList}">
							<c:choose>
								<c:when test="${filter.province == item.province}">
									<option value="${item.province}" selected="selected">${item.province}</option>
								</c:when>
								<c:otherwise>
									<option value="${item.province}">${item.province}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select></td>

				<td><fmt:message key="hTransmissionDevice.ne" /></td>
				<td><form:input type="text" id="ne" path="ne" width="90px" /></td>

				<td><fmt:message key="hTransmissionDevice.neType" /></td>
				<td><form:select path="neType" id="neType"
						style="width: 150px;height:20px; padding-top: 0px;"
						onchange="xl()">
						<option value=""><fmt:message key="global.All" /></option>
						<c:forEach var="item" items="${neTypeList}">
							<c:choose>
								<c:when test="${item == filter.neType}">
									<option value="${item}" selected="selected">${item}</option>
								</c:when>
								<c:otherwise>
									<option value="${item}">${item}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select></td>

				<td><fmt:message key="hTransmissionDevice.oam" /></td>
				<td><form:input type="text" id="oam" path="oam" width="90px" /></td>

				<td style="padding-left: 0px"><input class="button"
					type="submit" id="submit" class="button"
					value="<fmt:message key="button.search"/>" /></td>

				<td align="right"><a href="form.htm"><fmt:message
							key="button.add" /></a>&nbsp;</td>
			</tr>
		</table>
	</form:form>
</div>

<div style="overflow: auto;">
	<display:table name="${hTransmissionDeviceList}" class="simple2"
		id="item" requestURI="" pagesize="50" sort="external" defaultsort="1"
		export="true">
		<display:column class="centerColumnMana" titleKey="global.list.STT"
			style="width:20px;">
			<c:out value="${item_rowNum}" />
		</display:column>
		<display:column property="region"
			titleKey="hTransmissionDevice.region" sortable="true"
			sortName="REGION" />
		<display:column property="province"
			titleKey="hTransmissionDevice.province" sortable="true"
			sortName="PROVINCE" />

		<display:column property="site" titleKey="hTransmissionDevice.site"
			sortable="true" sortName="SITE" />
		<display:column property="ne" titleKey="hTransmissionDevice.ne"
			sortable="true" sortName="NE" />
		<display:column property="neType"
			titleKey="hTransmissionDevice.neType" sortable="true"
			sortName="NE_TYPE" />
		<display:column property="oam" titleKey="hTransmissionDevice.oam"
			sortable="true" sortName="OAM" />
		<display:column property="chucNang"
			titleKey="hTransmissionDevice.chucNang" sortable="true"
			sortName="CHUC_NANG" />
		<display:column property="ghiChu"
			titleKey="hTransmissionDevice.ghiChu" sortable="true"
			sortName="GHI_CHU" />
		<display:column property="createDate"
			titleKey="hTransmissionDevice.createDate"
			format="{0,date,dd/MM/yyyy HH:mm:ss}" sortable="true"
			sortName="CREATE_DATE" />
		<display:column property="updateDate"
			titleKey="hTransmissionDevice.updateDate"
			format="{0,date,dd/MM/yyyy HH:mm:ss}" sortable="true"
			sortName="UPDATE_DATE" />

		<display:column titleKey="title.quanLy" media="html"
			class="centerColumnMana">
			<a href="form.htm?id=${item.id}"><fmt:message key="button.edit" /></a>&nbsp;
	 		<a href="delete.htm?id=${item.id}"
				onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')"><fmt:message
					key="button.delete" /></a>&nbsp;
		</display:column>


		<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename"
			value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename"
			value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename"
			value="${exportFileName}.xls" />

	</display:table>
</div>


<script type="text/javascript">
	function xl() {
		var sub = document.getElementById("submit");
		sub.focus();
	}

//	var idSelector = "<c:out value = '${region}' />";
	var idSelector = $("#region").val();
	$(document).ready(function() {
		if (!idSelector) {
			$('#all').addClass("ui-tabs-selected");	
		} else {
			$('#' + idSelector).addClass("ui-tabs-selected");
		}
	});
</script>
