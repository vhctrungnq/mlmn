<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style><title>Báo cáo RNC QoS 3G</title>
<content tag="heading">RNC QOS 3G WEEKLY REPORT</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/rnc/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc/mn/list.htm"><span>Báo cáo tháng</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc/qr/list.htm"><span>Báo cáo quý</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc/yr/list.htm"><span>Báo cáo năm</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm" name="frmSample" onSubmit="return ValidateFormWeek()">
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
			        &nbsp;RNC 
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
	            	&nbsp;Từ tuần <input value="${startWeek}" name="startWeek" id="startWeek" size="2" maxlength="2" onchange ="javascript:checkNumber(document.frmSample.startWeek);">
					<img alt="calendar" title="Click to choose the start week number" id="chooseStartWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.startYear);">
	            	&nbsp;Tới tuần <input value="${endWeek}" name="endWeek" id="endWeek" size="2" maxlength="2" onchange="javascript:checkNumber(document.frmSample.endWeek);">
					<img alt="calendar" title="Click to choose the end week number" id="chooseEndWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.endYear);">
	            	&nbsp;<input type="submit" class="button" name="submit" id="submit"value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
	
			<div  id="doublescroll">
<display:table name="${vRpWkBsc}" id="vRpWkBsc" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
	    <display:column property="week" titleKey="WEEK" sortable="true" headerClass="master-header-1"/>
	    <display:column property="year" titleKey="YEAR" sortable="true" headerClass="master-header-1"/>
	    <display:column property="region" titleKey="REGION" sortable="true" headerClass="master-header-1"/>
	    <display:column property="bscid" titleKey="RNC" sortable="true" headerClass="hide" class="hide"/>
	    <display:column title="RNC" media="html" sortable="true" headerClass="master-header-1" sortProperty="bscid">
	    	<a href="${pageContext.request.contextPath}/report/radio3g/rnc/wk/detail.htm?bscid=${vRpWkBsc.bscid}&endWeek=${vRpWkBsc.week}&endYear=${vRpWkBsc.year}&startWeek=${vRpWkBsc.week}&startYear=${vRpWkBsc.year}">${vRpWkBsc.bscid}</a>
	    </display:column>
	    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" title="Sites"  headerClass="master-header-1" sortable="true"/>
	    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Cells" sortable="true" headerClass="hide" class="hide"/>
	    <display:column title="Cells" media="html" sortable="true" headerClass="master-header-1 margin" class="margin" sortProperty="cells">
	    	<a href="${pageContext.request.contextPath}/report/radio3g/cell/wk/list.htm?bscid=${vRpMnBsc.bscid}&week=${vRpWkBsc.week}&year=${vRpWkBsc.year}"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpWkBsc.cells}"/></a>
	    </display:column>
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
	    <display:column property ="speechDrop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SPEECH_DROP" sortable="true" headerClass="master-header-5"/>
	    <display:column property ="speechDrpr" titleKey="SPEECH_DRPR" class="SPEECH_DRPR" sortable="true" headerClass="master-header-5"/>
	    <display:column property="cs64Drop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CS64_DROP" sortable="true" headerClass="master-header-5"/>
	    <display:column property="cs64Drpr" titleKey="CS64_DRPR" class="CS64_DRPR" sortable="true" headerClass="master-header-5"/>
		<display:column property="psr99Drop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DROP" sortable="true" headerClass="master-header-5"/>
   		<display:column property="psr99Drpr" titleKey="PSR99_DRPR" class="PSR99_DRPR" sortable="true" headerClass="master-header-5"/>
   		<display:column property="hsupaDrop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_DROP" sortable="true" headerClass="master-header-5"/>
   		<display:column property ="hsupaDrpr" titleKey="HSUPA_DRPR" class="HSUPA_DRPR" sortable="true" headerClass="master-header-5"/>
   		<display:column property="hsdpaDrop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DROP" sortable="true" headerClass="master-header-5"/>
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
   		<%-- <display:column property ="cellDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELL_DOWNTIME" sortable="true" headerClass="master-header-2"/>
	    <display:column property ="hsDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HS_DOWNTIME" sortable="true" headerClass="master-header-2"/>
	    <display:column property ="eulDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EUL_DOWNTIME" sortable="true" headerClass="master-header-2 margin" class="margin"/>
	    --%> <display:column property ="dataload" titleKey="DATALOAD" sortable="true"/>
		</display:table>
</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />


<script type="text/javascript">
function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
} 
function CalcKeyCode(aChar) {
	  var character = aChar.substring(0,1);
	  var code = aChar.charCodeAt(0);
	  return code;
	}

	function checkNumber(val) {
	  var strPass = val.value;
	  var strLength = strPass.length;
	  for(var i=0;i<strLength+1;i++){
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
	Calendar.setup({
	    inputField		:	"startWeek",	// id of the input field
	    ifFormat		:	"%W",   	// format of the input field
	    button			:   "chooseStartWeek",  	// trigger for the calendar (button ID)
	    singleClick		:   false					// double-click mode
	});
	Calendar.setup({
	    inputField		:	"endWeek",	// id of the input field
	    ifFormat		:	"%W",   	// format of the input field
	    button			:   "chooseEndWeek",  	// trigger for the calendar (button ID)
	    singleClick		:   false					// double-click mode
	});
	
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