<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Báo cáo Province Ibc ${province}</title>
<content tag="heading">PROVINCE IBC REPORT ${province}</content>
<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/ibc/province/hr/detail.htm?province=${province}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/province/dy/detail.htm?province=${province}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/province/wk/detail.htm?province=${province}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/province/mn/detail.htm?province=${province}"><span>Báo cáo tháng</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="detail.htm" name = "frmSample" onSubmit = "return ValidateForm()">
		<table width="100%" class="form">
			<tr>
				<td align="left">
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
			        &nbsp;PROVINCE 
			        <select name="province" onchange="xl()">
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
				    </select>
	            	&nbsp;Từ <select name="startHour" id="startHour" onchange="xl()">
	            				<c:forEach var="hour" items="${hourList}">
						              <c:choose>
						                <c:when test="${hour == startHour}">
						                    <option value="${hour}" selected="selected">${hour}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${hour}">${hour}</option>
						                </c:otherwise>
						              </c:choose>
						    </c:forEach>
			               	 </select>&nbsp; giờ
	                &nbsp;<input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10"/>
	                &nbsp;Đến <select name="endHour" id="endHour" onchange="xl()">
	            				<c:forEach var="hour" items="${hourList}">
						              <c:choose>
						                <c:when test="${hour == endHour}">
						                    <option value="${hour}" selected="selected">${hour}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${hour}">${hour}</option>
						                </c:otherwise>
						              </c:choose>
						    </c:forEach>
			               	 </select>&nbsp;  giờ
	                &nbsp;<input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	  </form>
	<br/>
			<table class="form">
		    	<tr>
		    		<td>
		    			<b>Chọn chỉ số hiển thị: </b>
		    		</td>
		    	</tr>
		        <tr>
		        	<td>${checkColumns}</td>
				</tr>
		</table>
		<br/>
			<div  style="overflow: auto;">
<display:table name="${vRpHrProvinceIbcList}" id="vRpHrProvince" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true"/>
			    <display:column titleKey="HOUR"  sortable="true" sortProperty="hour">
			    	${vRpHrProvince.hour}:00
			    </display:column> 
			    <display:column property="region" titleKey="TT" sortable="true"/>
			    <display:column property="province" titleKey="PROVINCE" headerClass="hide" class="hide" sortable="true"/> 
			    <display:column titleKey="PROVINCE" media="html"  sortable="true" class="margin" headerClass="margin" sortProperty="province">
			    	<a href="${pageContext.request.contextPath}/report/radio/ibc/cell/dy/list.htm?province=${vRpHrProvince.province}&day=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrProvince.day}"/>">${vRpHrProvince.province}</a>
			    </display:column>
			    <display:column property="tTraf" titleKey="Traffic (Erl)" headerClass="hide" class="hide" sortable="true"/>
			    <display:column titleKey="Traffic (Erl)" media="html"  sortable="true" sortProperty="tTraf">
			    	<a href="${pageContext.request.contextPath}/report/radio/province/tTraffic/hr.htm?province=${vRpHrProvince.province}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrProvince.day}"/>"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpHrProvince.tTraf}"/></a>
				</display:column>
				<display:column property="tDrpr" titleKey="T_DRPR" headerClass="hide" class="hide" sortable="true"/>
			    <display:column titleKey="T_DRPR" media="html" class="T_DRPR" sortable="true" sortProperty="tDrpr">
			    	<a href="${pageContext.request.contextPath}/report/radio/province/tdrop/hr.htm?province=${vRpHrProvince.province}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrProvince.day}"/>">${vRpHrProvince.tDrpr}</a>
				</display:column>
			    <display:column property="tBlkr" titleKey="T_BLKR" sortable="true" class="T_BLKR"/>
			    <display:column property="tHoblkr" titleKey="T_HOBLKR"  sortable="true"/>
			    <display:column property="cssr" titleKey="CSSR" class="CSSR margin" sortable="true" headerClass="margin"/>
			    <display:column property="sSsr" titleKey="SSR (%)" sortable="true"/>
			    <display:column property="sBlkr" titleKey="S_BLKR"  class="S_BLKR" sortable="true"/>
			    <display:column property="sDrpr" titleKey="S_DRPR" headerClass="hide" class="hide" sortable="true"/>
			    <display:column titleKey="S_DRPR" media="html" sortable="true" sortProperty="sDrpr">
			    	<a href="${pageContext.request.contextPath}/report/radio/province/sdrop/hr.htm?province=${vRpHrProvince.province}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrProvince.day}"/>">${vRpHrProvince.sDrpr}</a>
				</display:column>
			    <display:column property="tAsr" titleKey="T_ASR"  sortable="true"/>
			    <display:column property="haftratePercent" titleKey="HALFRATE" class="margin" headerClass="margin" sortable="true"/>
			    <display:column property="inHoSucr" titleKey="IN_HO_SUCR" sortable="true"/>
			    <display:column property="ogHoSucr" titleKey="OG_HO_SUCR"  sortable="true"/>
			</display:table>
</div>
	<br/>
	<div id="tDrprChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="sDrprChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="cssrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="tBlkrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="sBlkrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="tTrafChart" style="width: 1000px; margin: 1em auto"></div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
${availChart}
${trafChart}
${tDrprChart}
${sDrprChart}
${cssrChart}
${tBlkrChart}
${sBlkrChart}
${tTrafChart}

<script type="text/javascript">
function xl(){
	var sub= document.getElementById("submit");
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
		
		${highlight};
	});
</script>