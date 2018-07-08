<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style><title>Báo cáo QoS RNC 3G ${bscid}</title>
<content tag="heading">RNC QOS 3G ${bscid} REPORT</content>
<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/rnc/hr/detail.htm?bscid=${bscid}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc/dy/detail.htm?bscid=${bscid}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc/wk/detail.htm?bscid=${bscid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc/mn/detail.htm?bscid=${bscid}"><span>Báo cáo tháng</span></a></li>
  <%-- <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc/dy/bh.htm?bscid=${bscid}&endDate=${endDate}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc/wk/bh.htm?bscid=${bscid}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc/mn/bh.htm?bscid=${bscid}"><span>Báo cáo BH tháng</span></a></li> --%>
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
			        &nbsp;RNC
					<select name="bscid" id="bscid" style="width: 163px">
			        <option value="">--Select RNC--</option>
				        <c:forEach var="bsc" items="${bscList}">
			              <c:choose>
			                <c:when test="${bsc == bscid}">
			                    <option value="${bsc}" selected="selected">${bsc}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${bsc}">${bsc}</option>
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
	<div  id="doublescroll">
	<div class = "tableStandar">
<display:table name="${vRpHrBscList}" id="vRpHrBsc" requestURI="" pagesize="100"  export="true" sort="list">
		<display:column property="day" format="{0,date,dd/MM/yyyy}" title="Date" headerClass="master-header-1" sortable="true"/>
	    <display:column title="HOUR" sortable="true"  headerClass="master-header-1" sortProperty="hour">
	    	${vRpHrBsc.hour} 
	    </display:column>
	    <display:column property="region" title="Center" sortable="true"  headerClass="master-header-1"/>
	    <display:column property="bscid" titleKey="RNC" sortable="true" headerClass="hide" class="hide"/>
	    <display:column title="RNC" media="html" sortable="true"  headerClass="master-header-1" sortProperty="bscid">
	    	<a href="${pageContext.request.contextPath}/report/radio3g/cell/hr/detail.htm?bscid=${vRpHrBsc.bscid}&cellid=&startHour=${vRpHrBsc.hour}:00&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrBsc.day}"/>&endHour=${vRpHrBsc.hour}:00&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrBsc.day}"/>">${vRpHrBsc.bscid}</a>
	    </display:column> 
	    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" title="SITES" sortable="true"  headerClass="master-header-1"/>
	    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" title="CELLS" sortable="true"  headerClass="master-header-1 margin" class="margin"/>
	    <display:column property="speechTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SPEECH_TRAFF" class="SPEECH_TRAFF"  sortable="true" headerClass="master-header-3"/>
	    <display:column property="cs64Traff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CS64_TRAFF" sortable="true" headerClass="master-header-3"/>
	    <display:column property="psr99UlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_TRAFF"  sortable="true" headerClass="master-header-3"/>
	    <display:column property="psr99DlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_TRAFF"  sortable="true" headerClass="master-header-3"/>
	    <display:column property="hsupaUlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_TRAFF"  sortable="true" headerClass="master-header-3"/>
	    <display:column property ="hsdpaDlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_TRAFF"  sortable="true" headerClass="master-header-3 margin" class="margin"/>
	    <display:column property="speechCssr" titleKey="SPEECH_CSSR" sortable="true" headerClass="hide" class="hide"/>
	    <display:column titleKey="SPEECH_CSSR" class="SPEECH_CSSR" sortable="true" headerClass="master-header-4" media="html" sortProperty="speechCssr">
	    <a href="${pageContext.request.contextPath}/report/radio3g/rnc/cssr/hr.htm?bscid=${vRpHrBsc.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrBsc.day}"/>">${vRpHrBsc.speechCssr}</a>
	    </display:column>
	    <display:column property="speechDrpr" titleKey="SPEECH_DRPR" sortable="true" headerClass="hide" class="hide"/>
        <display:column titleKey="SPEECH_DRPR" class="SPEECH_DRPR" sortable="true" headerClass="master-header-5" media="html" sortProperty="speechDrpr">
        <a href="${pageContext.request.contextPath}/report/radio3g/rnc/drop/hr.htm?bscid=${vRpHrBsc.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrBsc.day}"/>">${vRpHrBsc.speechDrpr}</a>
        </display:column>
        <display:column property ="pasr3g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" 
                            titleKey="PASR3G" sortable="true" headerClass="master-header-4"/>
        <display:column property ="padr3g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"
                            titleKey="PADR3G" sortable="true" headerClass="master-header-5"/>         
	    <display:column property ="cs64Cssr" titleKey="CS64_CSSR" class="CS64_CSSR" sortable="true" headerClass="master-header-4"/>
	    <display:column property="psr99Cssr" titleKey="PSR99_CSSR" class="PSR99_CSSR" sortable="true" headerClass="master-header-4"/>			    
	    <display:column property ="hsupaCssr" titleKey="HSUPA_CSSR" class="HSUPA_CSSR" sortable="true" headerClass="master-header-4"/>
	    <display:column property="hsdpaCssr" titleKey="HSDPA_CSSR" class="HSDPA_CSSR margin" sortable="true" headerClass="master-header-4 margin"/>
	    <%-- <display:column property ="speechDrop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SPEECH_DROP" sortable="true" headerClass="master-header-5"/> --%>
	    
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
   		<%-- <display:column property ="cellDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELL_DOWNTIME" sortable="true" headerClass="master-header-2"/>
	    <display:column property ="hsDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HS_DOWNTIME" sortable="true" headerClass="master-header-2"/>
	    <display:column property ="eulDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EUL_DOWNTIME" sortable="true" headerClass="master-header-2"/> --%>
	</display:table>
	</div>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
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