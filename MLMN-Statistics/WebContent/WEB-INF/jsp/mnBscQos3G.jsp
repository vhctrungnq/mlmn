<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style><title>Báo cáo QoS RNC 3G ${bscid}</title>
<content tag="heading">QOS RNC 3G REPORT ${bscid}</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc/hr/detail.htm?bscid=${bscid}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc/dy/detail.htm?bscid=${bscid}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc/wk/detail.htm?bscid=${bscid}"><span>Báo cáo tuần</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/rnc/mn/detail.htm?bscid=${bscid}&endMonth=${endMonth}&endYear=${endYear}"><span>Báo cáo tháng</span></a></li>
  <%-- <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc/dy/bh.htm?bscid=${bscid}"><span>Báo cáo BH ngày </span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc/wk/bh.htm?bscid=${bscid}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc/mn/bh.htm?bscid=${bscid}&endMonth=${endMonth}&endYear=${endYear}"><span>Báo cáo BH tháng</span></a></li> --%>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="detail.htm" onSubmit="return ValidateFormYear()">
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
			        RNC 
					<select name="bscid" id="bscid" style="width: 163px">
			        <option value="">--Select RNC--</option>
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
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.startYear);">
	            	&nbsp;Tới tháng <select name="endMonth" id="endMonth" onchange="xl()">
	            				<c:forEach var="month" items="${monthList}">
						              <c:choose>
						                <c:when test="${month == endMonth}">
						                    <option value="${month}" selected="selected">${month}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${month}">${month}</option>
						                </c:otherwise>
						              </c:choose>
						    </c:forEach>
			               	 </select>&nbsp;
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.endYear);">
	            	&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	  </form>
	<br/>
	<table class="form">
    	<tr>
    		<td colspan="12">
    			<b>Chọn chỉ số hiển thị: </b>
    		</td>
    	</tr>
	        <tr>
	        	<td>${checkColumns}</td>
			</tr>
	</table>
	<br/>
	<div  id="doublescroll">
<display:table name="${vRpMnBscList}" id="vRpMnBsc" requestURI="" pagesize="100" class="simple2" export="true">
		<display:column property ="month" titleKey="MONTH" sortable="true" headerClass="master-header-1"/>
		<display:column property ="year" titleKey="YEAR" sortable="true" headerClass="master-header-1"/> 
	    <display:column property="region" titleKey="REGION" headerClass="master-header-1" sortable="true"/>
		<display:column property="bscid" titleKey="RNC" sortable="true"  headerClass="master-header-1"/> 
	    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Sites" sortable="true"  headerClass="master-header-1"/>
	    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Cells" sortable="true"  headerClass="master-header-1 margin" class="margin"/>
	    <display:column property="speechTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SPEECH_TRAFF" class="SPEECH_TRAFF"  sortable="true" headerClass="master-header-3"/>
	    <display:column property="cs64Traff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CS64_TRAFF" sortable="true" headerClass="master-header-3"/>
	    <display:column property="psr99UlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_TRAFF"  sortable="true" headerClass="master-header-3"/>
	    <display:column property="psr99DlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_TRAFF"  sortable="true" headerClass="master-header-3"/>
	    <display:column property="hsupaUlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_TRAFF"  sortable="true" headerClass="master-header-3"/>
	    <display:column property ="hsdpaDlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_TRAFF"  sortable="true" headerClass="master-header-3 margin" class="margin"/>
	    <display:column property="speechCssr" titleKey="SPEECH_CSSR" class="SPEECH_CSSR" sortable="true" headerClass="master-header-4"/>
	    <display:column property ="cs64Cssr" titleKey="CS64_CSSR" class="CS64_CSSR" sortable="true" headerClass="master-header-4"/>
	    <display:column property="psr99Cssr" titleKey="PSR99_CSSR" class="PSR99_CSSR" sortable="true" headerClass="master-header-4"/>			    
	    <display:column property ="hsupaCssr" titleKey="HSUPA_CSSR" class="HSUPA_CSSR" sortable="true" headerClass="master-header-4"/>
	    <display:column property="hsdpaCssr" titleKey="HSDPA_CSSR" class="HSDPA_CSSR margin" sortable="true" headerClass="master-header-4 margin"/>
	    <%-- <display:column property ="speechDrop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SPEECH_DROP" sortable="true" headerClass="master-header-5"/> --%>
	    <display:column property ="speechDrpr" titleKey="SPEECH_DRPR" class="SPEECH_DRPR" sortable="true" headerClass="master-header-5"/>
	    <%-- <display:column property="cs64Drop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CS64_DROP" sortable="true" headerClass="master-header-5"/> --%>
	    <display:column property="cs64Drpr" titleKey="CS64_DRPR" class="CS64_DRPR" sortable="true" headerClass="master-header-5"/>
		<%-- <display:column property="psr99Drop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DROP" sortable="true" headerClass="master-header-5"/> --%>
   		<display:column property="psr99Drpr" titleKey="PSR99_DRPR" class="PSR99_DRPR" sortable="true" headerClass="master-header-5"/>
   		<%-- <display:column property="hsupaDrop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_DROP" sortable="true" headerClass="master-header-5"/> --%>
   		<display:column property ="hsupaDrpr" titleKey="HSUPA_DRPR" class="HSUPA_DRPR" sortable="true" headerClass="master-header-5"/>
   		<%-- <display:column property="hsdpaDrop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DROP" sortable="true" headerClass="master-header-5"/> --%>
   		<display:column property="hsdpaDrpr" titleKey="HSDPA_DRPR" class="HSDPA_DRPR margin" sortable="true" headerClass="master-header-5 margin"/>
	    <display:column property="psr99UlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_THROUGHTPUT" sortable="true" headerClass="master-header-3"/>
	    <display:column property="psr99DlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_THROUGHTPUT" sortable="true" headerClass="master-header-3"/>
	    <display:column property="hsupaUlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_THROUGHTPUT" sortable="true" headerClass="master-header-3"/>
	    <display:column property="hsdpaDlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_THROUGHTPUT" sortable="true" headerClass="master-header-3 margin" class="margin"/>
   		<%-- <display:column property="speechShoSrOut" titleKey="SPEECH_SHO_SR_OUT" sortable="true" headerClass="master-header-6"/>
   		<display:column property="shoSrOut" titleKey="SHO_SR_OUT" sortable="true" headerClass="master-header-6"/>
   		<display:column property="shoSrIn" titleKey="SHO_SR_IN" sortable="true" headerClass="master-header-6"/> --%>
   		<display:column property="csIratHoSr" titleKey="CS_IRAT_HO_SR" sortable="true" headerClass="master-header-6"/>
   		<display:column property ="psIratHoSr" titleKey="PS_IRAT_HO_SR" sortable="true" headerClass="master-header-6"/>
   		<display:column property ="softHsSr" titleKey="SOFT_HS_SR" sortable="true" headerClass="master-header-6 margin" class="margin"/>
   		<display:column property ="cellDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELL_DOWNTIME" sortable="true" headerClass="master-header-2"/>
	    <display:column property ="hsDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HS_DOWNTIME" sortable="true" headerClass="master-header-2"/>
	    <display:column property ="eulDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EUL_DOWNTIME" sortable="true" headerClass="master-header-2 margin" class="margin"/>
	    <display:column property ="dataload" titleKey="DATALOAD" sortable="true"/>
	</display:table>
</div>
	<br/>
	<div id="speechCssrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="cs64CssrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="psr99CssrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="hsupaCssrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="hsdpaCssrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="speechDrprChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="cs64DrprChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="psr99DrprChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="hsupaDrprChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="hsdpaDrprChart" style="width: 1000px; margin: 1em auto"></div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
${speechCssrChart}
${cs64CssrChart}
${psr99CssrChart}
${hsupaCssrChart}
${hsdpaCssrChart}
${speechDrprChart}
${cs64DrprChart}
${psr99DrprChart}
${hsupaDrprChart}
${hsdpaDrprChart}

<script type="text/javascript">
	$(function() {		
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

				lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getBsc3G.htm", request, function( data, status, xhr ) {
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

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
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
		${highlight}
	});
</script>
<script type="text/javascript">
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
