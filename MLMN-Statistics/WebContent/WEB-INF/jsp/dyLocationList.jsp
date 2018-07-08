<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>location list</title>
<content tag="heading"><fmt:message key="sidebar.location.qos"/></content>
<ul class="ui-tabs-nav">
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/location/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/location/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/location/mn/list.htm"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form" >
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm" name="frmSample" onSubmit="return ValidateForm()">
					
					Trung tâm 
			  		<select name="region" id="region" onchange="xl()">
			  			<option value="">Tất cả</option>
			              <c:forEach var="items" items="${regionList}">
				              <c:choose>
				                <c:when test="${items.region == region}">
				                    <option value="${items.region}" selected="selected">${items.region}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.region}">${items.region}</option>
				                </c:otherwise>
				              </c:choose>
						    </c:forEach>
			        </select>
					LOCATION 
			        <select name="location" onchange="xl()">
			        	<option value="">Tất cả</option>
				        <c:forEach var="prv" items="${locationList}">
			              <c:choose>
			                <c:when test="${prv.location == location}">
			                    <option value="${prv.location}" selected="selected">${prv.location}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${prv.location}">${prv.location}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
				    </select>
			        &nbsp;&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
	
			<div  style="overflow: auto;">
<display:table name="${vRpDyLocation}" id="vRpDyLocation" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
				<display:column property="region" titleKey="TT" sortable="true"/>
   <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true"/>
    <display:column property="location" titleKey="LOCATION" headerClass="hide" class="hide"/>
	<display:column titleKey="REGION" media="html">
		<a href="${pageContext.request.contextPath}/report/radio/location/hr/detail.htm?location=${vRpDyLocation.location}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyLocation.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyLocation.day}"/>">${vRpDyLocation.location}</a>
	</display:column>
    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SITES" sortable="true"/>
    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELLS" sortable="true"/>
    <display:column property="trxs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRXS" sortable="true" class="margin" headerClass="margin"/>
	<display:column property ="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF" sortable="true"/>
    <display:column property="haftratePercent" titleKey="HALFRATE" sortable="true"/>
    <display:column property="cssr" titleKey="CSSR" class="CSSR" sortable="true"/>
    <display:column property="tAsr" titleKey="T_ASR" sortable="true"/> 
    <display:column property="tDrpr" titleKey="T_DRPR" class="T_DRPR" sortable="true"/>
    <display:column property ="tBlkr" titleKey="T_BLKR" class="T_BLKR" sortable="true"/>
    <display:column property ="tHoblkr" titleKey="T_HOBLKR" sortable="true"/>
    <display:column property ="tEmpdr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_EMPDR" sortable="true"  class="margin" headerClass="margin"/>
    <display:column property="sSsr" titleKey="SSR (%)" sortable="true"/>
    <display:column property ="sDrps" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_DRPS" sortable="true"/>
    <display:column property ="sDrpr" titleKey="S_DRPR" class="S_DRPR" sortable="true"/>
    <display:column property="sBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_BLKS" sortable="true"/>
    <display:column property="sBlkr" titleKey="S_BLKR" class="S_BLKR margin" sortable="true" headerClass="margin"/>
    <display:column property ="inHoSucr" titleKey="IN_HO_SUCR" sortable="true"/>
    <display:column property="ogHoSucr" titleKey="OG_HO_SUCR" sortable="true" class="margin" headerClass="margin"/>
    <display:column property ="dataload" titleKey="DATALOAD" sortable="true"/>
</display:table>
</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript">
function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
}
	$(function() {
		$( "#startDate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
		$( "#endDate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});	
	});
</script>
