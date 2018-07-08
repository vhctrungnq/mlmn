<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>REPORT QOS BSC ${bscid}</title>
<content tag="heading">QOS BSC ${bscid} REPORT</content>
<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/bsc/hr/detail.htm?bscid=${bscid}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc/dy/detail.htm?bscid=${bscid}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc/wk/detail.htm?bscid=${bscid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc/mn/detail.htm?bscid=${bscid}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc/dy/bh.htm?bscid=${bscid}&endDate=${endDate}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc/wk/bh.htm?bscid=${bscid}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc/mn/bh.htm?bscid=${bscid}"><span>Báo cáo BH tháng</span></a></li>
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
			        &nbsp;BSC
					<select name="bscid" id="bscid" style="width: 163px">
			        <option value="">--Select BSC--</option>
				        <c:forEach var="bsc" items="${bscList}">
			              <c:choose>
			                <c:when test="${bsc.bscid == bscid}">
			                    <option value="${bsc.bscid}" selected="selected">${bsc.bscid}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${bsc.bscid}">${bsc.bscid}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
					</select>
	            	&nbsp;Từ <select name="startHour" id="startHour"onchange="xl()">
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
	<div class = "tableStandar">
<display:table name="${vRpHrBscList}" id="vRpHrBsc" requestURI="" pagesize="100"  export="true">
		<display:column  sortable="true"  headerClass="master-header-1"   property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY"/>
	    <display:column  sortable="true"  headerClass="master-header-1"   titleKey="HOUR" >
	    	${vRpHrBsc.hour} 
	    </display:column>
	    <display:column  sortable="true"  headerClass="master-header-1"   property="region" titleKey="TT"/>
	    <display:column  sortable="true"  property="bscid" titleKey="BSC" headerClass="hide" class="hide"/>
	    <display:column  sortable="true"  headerClass="master-header-1"   titleKey="BSC" media="html">
	    	<a href="${pageContext.request.contextPath}/report/radio/cell/hr/detail.htm?bscid=${vRpHrBsc.bscid}&cellid=&startHour=${vRpHrBsc.hour }:00&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrBsc.day}"/>&endHour=${vRpHrBsc.hour }:00&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrBsc.day}"/>">${vRpHrBsc.bscid}</a>
	    </display:column> 
	    <display:column  sortable="true"  headerClass="master-header-1"   property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SITES"/>
	    <display:column  sortable="true"  headerClass="master-header-1"   property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELLS"/>
	    <display:column  sortable="true"  headerClass="master-header-1"   property ="trxs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRXS" class="margin"/>
	    <display:column property ="tDef" titleKey="TCH Def" class="T_DEF" sortable="true"  headerClass="master-header-2"/>
		<display:column property ="tAvail" titleKey="TCH Avail" class="T_AVAIL" sortable="true"  headerClass="master-header-2"/>
		<display:column property ="tBlkr" titleKey="T_BLKR" class="T_BLKR" sortable="true"  headerClass="master-header-2"/>
		<display:column property ="tHoblkr" titleKey="T_HOBLKR" class="T_HOBLKR" sortable="true"  headerClass="master-header-2"/>
		<display:column property="cssr" titleKey="CSSR" class="CSSR margin" sortable="true"  headerClass="master-header-2"/>
		
	    <display:column  sortable="true"  property="tTraf" titleKey="T_TRAF" headerClass="hide" class="hide"/>
	    <display:column  sortable="true"  headerClass="master-header-2"   titleKey="T_TRAF" media="html">
			    	<a href="${pageContext.request.contextPath}/report/radio/bsc/tTraffic/hr.htm?bscid=${vRpHrBsc.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrBsc.day}"/>"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpHrBsc.tTraf}"/></a>
		</display:column>
		<display:column  sortable="true"  property="tDrpr" titleKey="T_DRPR" headerClass="hide" class="hide"/>
	    <display:column  sortable="true"  headerClass="master-header-2"   titleKey="T_DRPR" media="html" class="T_DRPR">
			    	<a href="${pageContext.request.contextPath}/report/radio/bsc/tdrop/hr.htm?bscid=${vRpHrBsc.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrBsc.day}"/>">${vRpHrBsc.tDrpr}</a>
		</display:column>
	    <display:column  sortable="true"  headerClass="master-header-3"   property="sSsr" titleKey="S_SSR"/>
	    <display:column  sortable="true"  headerClass="master-header-3"   property ="sBlkr" titleKey="S_BLKR" class="S_BLKR"/>
	    <display:column  sortable="true"  property="sDrpr" titleKey="S_DRPR" headerClass="hide" class="hide"/>
	    <display:column  sortable="true"  headerClass="master-header-3"   titleKey="S_DRPR" media="html">
			    	<a href="${pageContext.request.contextPath}/report/radio/bsc/sdrop/hr.htm?bscid=${vRpHrBsc.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrBsc.day}"/>">${vRpHrBsc.sDrpr}</a>
		</display:column>
	    
	    <display:column  sortable="true"  headerClass="master-header-2"   property ="halfrate" titleKey="HALFRATE" />
	</display:table>
	</div>
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
		
		var cache = {},
		lastXhr;
		$( "#bscid" ).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cache ) {
					response( cache[ term ] );
					return;
				}

				lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getBsc.htm", request, function( data, status, xhr ) {
					cache[ term ] = data;
					if ( xhr === lastXhr ) {
						response( data );
					}
				});
			}
		});
		
		${highlight}
	});
</script>
