<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Report QoS Bsc ${bscid}</title>
<content tag="heading">QOS BSC REPORT ${bscid}</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc/hr/detail.htm?bscid=${bscid}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/bsc/dy/detail.htm?bscid=${bscid}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc/wk/detail.htm?bscid=${bscid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc/mn/detail.htm?bscid=${bscid}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc/dy/bh.htm?bscid=${bscid}&endDate=${endDate}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc/wk/bh.htm?bscid=${bscid}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc/mn/bh.htm?bscid=${bscid}"><span>Báo cáo BH tháng</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="detail.htm" name="frmSample" onSubmit="return ValidateForm()">
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
	            	&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	            	&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	            	&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
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
	<div  style="overflow: auto;">
<display:table style="width:160%" name="${vRpDyBscList}" id="vRpDyBsc" requestURI="" pagesize="100" class="simple2" export="true">
		<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true"  headerClass="master-header-1"/>
		<display:column property="region" titleKey="TT" sortable="true"  headerClass="master-header-1"/>
		<display:column property="bscid" titleKey="BSCID"  headerClass="hide" class="hide" sortable="true" />
		<display:column titleKey="BSCID"  media="html" sortable="true"  headerClass="master-header-1">
	    	<a href="${pageContext.request.contextPath}/report/radio/bsc/hr/detail.htm?bscid=${vRpDyBsc.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyBsc.day}"/>">${vRpDyBsc.bscid}</a>
	    </display:column> 
	    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SITES" headerClass="hide" class="hide" sortable="true"/>
	    <display:column titleKey="SITES" media="html" sortable="true"  headerClass="master-header-1">
	    	<a href="${pageContext.request.contextPath}/report/radio/site-qos/dy/list.htm?bscid=${vRpDyBsc.bscid}&day=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyBsc.day}"/>"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyBsc.sites}"/></a>
	    </display:column>
	    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELLS" headerClass="hide" class="hide"/>
	    <display:column titleKey="CELLS" media="html" sortable="true"  headerClass="master-header-1">
	    	<a href="${pageContext.request.contextPath}/report/radio/cell/dy/list.htm?bscid=${vRpDyBsc.bscid}&day=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyBsc.day}"/>"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyBsc.cells}"/></a>
	    </display:column> 
	    <display:column property ="trxs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRXS"  class="margin" sortable="true"  headerClass="master-header-1"/>
	    <display:column property ="tDef" titleKey="TCH Def" class="T_DEF" sortable="true"  headerClass="master-header-2"/>
		<display:column property ="tAvail" titleKey="TCH Avail" class="T_AVAIL" sortable="true"  headerClass="master-header-2"/>
		<display:column property ="tBlkr" titleKey="T_BLKR" class="T_BLKR" sortable="true"  headerClass="master-header-2"/>
		<display:column property ="tHoblkr" titleKey="T_HOBLKR" class="T_HOBLKR" sortable="true"  headerClass="master-header-2"/>
		<display:column property="cssr" titleKey="CSSR" class="CSSR margin" sortable="true"  headerClass="master-header-2"/>
		
	    <display:column property ="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF" class="hide" headerClass="hide" sortable="true"/>	    
	   	<display:column titleKey="T_TRAF" media="html" sortable="true"  headerClass="master-header-2">
			    	<a href="${pageContext.request.contextPath}/report/radio/bsc/tTraffic/hr.htm?bscid=${vRpDyBsc.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyBsc.day}"/>"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyBsc.tTraf}"/></a>
		</display:column>
		<display:column property ="tDrpr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_DRPR" class="hide" headerClass="hide" sortable="true"/>
	    <display:column titleKey="T_DRPR" media="html" class="T_DRPR" sortable="true" headerClass="master-header-2">
			    	<a href="${pageContext.request.contextPath}/report/radio/bsc/tdrop/hr.htm?bscid=${vRpDyBsc.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyBsc.day}"/>">${vRpDyBsc.tDrpr}</a>
		</display:column>
	   <display:column property ="tAsr" titleKey="T_ASR" sortable="true"  headerClass="master-header-2"/>
	    <display:column property ="halfrate" titleKey="HALFRATE" class="margin" sortable="true"  headerClass="master-header-2"/>
		<display:column property="sSsr" titleKey="S_SSR" sortable="true" headerClass="master-header-3"/>
	    <display:column property ="sBlkr" titleKey="S_BLKR" class="S_BLKR" sortable="true"  headerClass="master-header-3"/>
	    <display:column property ="sDrpr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_DRPR" class="hide" headerClass="hide" sortable="true"/>
	    <display:column titleKey="S_DRPR" media="html" sortable="true"  headerClass="master-header-3">
			    	<a href="${pageContext.request.contextPath}/report/radio/bsc/sdrop/hr.htm?bscid=${vRpDyBsc.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyBsc.day}"/>">${vRpDyBsc.sDrpr}</a>
		</display:column>
	    <display:column property ="dataload" titleKey="DATALOAD"  sortable="true"/>
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
function CalcKeyCode(aChar) {
	  var character = aChar.substring(0,1);
	  var code = aChar.charCodeAt(0);
	  return code;
	}

	function checkNumber(val) {
	  var strPass = val.value;
	  var strLength = strPass.length;
	  for(var i =0;i<strLength+1;i++){
	  var lchar = val.value.charAt((strLength) - i);
	  var cCode = CalcKeyCode(lchar);
	  if (cCode < 48 || cCode > 57 ) {
	    var myNumber = val.value.substring(0, (strLength) - i);
	    val.value = myNumber;
	  }
	  }
	  var sub = document.getElementById("submit");
		sub.focus();
	  return false;
	}
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
