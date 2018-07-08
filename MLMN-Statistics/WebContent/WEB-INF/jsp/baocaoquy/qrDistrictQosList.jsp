<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Báo cáo District QoS</title>
<content tag="heading">DISTRICT QOS QUATERLY REPORT</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/district/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/district/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/district/mn/list.htm"><span>Báo cáo tháng</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/district/qr/list.htm"><span>Báo cáo quý</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/district/yr/list.htm"><span>Báo cáo năm</span></a></li>
</ul>
<div class="ui-tabs-panel">
	<table width="100%" class="form">
		<tr>
			<td align="left">
				<form method="get" action="list.htm">
					<fmt:message key="option.cellgprscs.region"/>
		  			<select name="region">
			        	<option value="">Tất cả</option>
				        <c:forEach var="item" items="${regionList}">
			              <c:choose>
			                <c:when test="${item.region == region}">
			                    <option value="${item.region}" selected="selected">${item.region}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${item.region}">${item.region}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
			    	</select>
					</select> &nbsp;PROVINCE <select name="province">
						<option value="">Tất cả</option>
						<c:forEach var="prv" items="${provinceList}">
							<c:choose>
								<c:when test="${prv.province == province}">
									<option value="${prv.province}" selected="selected">${prv.province}</option>
								</c:when>
								<c:otherwise>
									<option value="${prv.province}">${prv.province}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select> &nbsp;DISTRICT <input value="${district}" name="district" id="district" size="10" maxlength="50" onchange="xl()">
					&nbsp;Từ quý <input value="${startQuarter}" name="startQuarter" id="startQuarter" size="1" maxlength="1">
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4">
	            	&nbsp;Tới quý <input value="${endQuarter}" name="endQuarter" id="endQuarter" size="1" maxlength="1">
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4">
					&nbsp;<input type="submit" class="button" name="submit" value="View Report" />
				</form>
			</td>
		</tr>
	</table>
	<br />
<div  style="overflow: auto;" class="tableStandar">
	<display:table name="${vRpQrDistrictQos}" id="item" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
			    <display:column property="quarter" titleKey="QUARTER" sortable="true"/>
			    <display:column property="year" titleKey="YEAR" sortable="true"/>
			    <display:column property="region" titleKey="TT" sortable="true"/>
			    <display:column property="province" titleKey="PROVINCE" sortable="true" />
			    <display:column property="district" titleKey="DISTRICT" sortable="true" />
			    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SITES" sortable = "true" sortName = "SITES"/>
			    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELLS" sortable = "true" sortName = "CELLS"/>
			    <display:column property="trxs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRXS" sortable="true" class="margin" headerClass="margin"/>
			    <display:column property="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF" sortable="true"/>
			    <display:column property="tDrpr" titleKey="T_DRPR" class="T_DRPR" sortable="true"/>
			    <display:column property="tBlkr" titleKey="T_BLKR" class="T_BLKR" sortable="true"/>
			    <display:column property="tHoblkr" titleKey="T_HOBLKR" class="T_HOBLKR" sortable="true"/>
			    <display:column property="cssr" titleKey="CSSR" class="CSSR margin" sortable="true" headerClass="margin"/>
			    <display:column property="sSsr" titleKey="S_SSR" sortable="true"/>
			    <display:column property="sBlkr" titleKey="S_BLKR" class="S_BLKR" sortable="true"/>
			    <display:column property="sDrpr" titleKey="S_DRPR" class="S_DRPR" sortable="true"/>
			    <display:column property="tAsr" titleKey="T_ASR" class="T_ASR" sortable="true"/>
			    <display:column property="haftratePercent" titleKey="HALFRATE" sortable="true" class="margin" headerClass="margin"/>
			    <display:column property="downtime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DOWNTIME"  sortable="true"  class="margin" headerClass="margin"/>
			    <display:column property="dataload" titleKey="DATALOAD" sortable="true" />
			    
				<display:setProperty name="export.csv.include_header" value="true" />
				<display:setProperty name="export.excel.include_header" value="true" />
				<display:setProperty name="export.xml.include_header" value="true" />
				<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
				<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
				<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />			    
	</display:table>
</div>
</div>	

<script type="text/javascript">
	function xl(){
		var sub = document.getElementById("submit");
		sub.focus();
	} 
</script>