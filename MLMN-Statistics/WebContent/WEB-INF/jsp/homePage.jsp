<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<style type="text/css">
    #doublescroll { overflow: auto; overflow-y: hidden; }
    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
</style>

<title>${title}</title>
<content tag="heading"><center>${content}</center></content>
<br/>
	<form method="get" action="welcome.htm">
		<table style="width:100%;" class="form">
			<tr>
				<td align="left">
					Khu vực
			        <select name="region">
			        	<option value="">Tất cả</option>
				        <c:forEach var="item" items="${regionList}">
			              <c:choose>
			                <c:when test="${item.value == region}">
			                    <option value="${item.value}" selected="selected">${item.name}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${item.value}">${item.name}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
			    	</select>&nbsp;
	                
			        Ngày <input value="${day}" name="day" id="day" size="10" maxlength="10">
			        
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	</form>
	
<%-- 	<center><h2>BÁO CÁO CS CORE GSM</h2></center>
	<br/>
	<div style="overflow: auto;">
	<div class = "tableStandar">
		<display:table name="${vRpDyMscSummary}" id="vRpDyMscSummary" requestURI="" pagesize="100"   export="true">
			<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true"/>
		    <display:column property="mscid" titleKey="SYSTEM" sortable="true"/>
		    <display:column property ="succrChassigGsm"  titleKey="Channel Assignment Success, GSM (%)" href="${pageContext.request.contextPath}/report/core-era/channel-assig/msc/dy.htm" sortable="true" />

			<display:column property ="callSetUp"  titleKey="Call Set-up Success (%)"  href="${pageContext.request.contextPath}/report/core-era/call-setup-success/dy/list.htm"  sortable="true" />
			<display:column property ="luRegSucc"  titleKey="Location Update Success (%)" href="${pageContext.request.contextPath}/report/core-era/call-setup-success/dy/list.htm" sortable="true" />
			<display:column property ="piSuccAuth"  titleKey="Authentication (%)" href="${pageContext.request.contextPath}/report/core-era/authen-cation/dy.htm" sortable="true" />
			<display:column property ="endUserGsmSucc"  titleKey="GSM End-User Perceived Paging Success (%)" href="${pageContext.request.contextPath}/report/core-era/msc-pag-glo/dy.htm" sortable="true" />
			<display:column property ="endUserWcdmaSucc"  titleKey="WCDMA End-User Perceived Paging Success (%)" href="${pageContext.request.contextPath}/report/core-era/msc-pag-glo/dy.htm" sortable="true" />
			<display:column property ="succrCiphGsm"  titleKey="Ciphering Success, GSM (%)" href="${pageContext.request.contextPath}/report/core-era/call-setup-success/dy/list.htm"  sortable="true" />
			<display:column property ="succrCiphWcdma"  titleKey="Ciphering Success, WCDMA (%)" href="${pageContext.request.contextPath}/report/core-era/call-setup-success/dy/list.htm" sortable="true" />
			<display:column property ="succrInterMsc"  titleKey="Inter-MSC Handover Success, GSM (%)" href="${pageContext.request.contextPath}/report/core-era/retainability/dy.htm" sortable="true" />
			<display:column property ="succrU2gInterMscHo"  titleKey="Inter-MSC Handover Success, U2G (%)" href="${pageContext.request.contextPath}/report/core-era/retainability/dy.htm" sortable="true" />
			<display:column property ="succrIntraMscHoGsm"  titleKey="Intra-MSC Handover Success, GSM (%)" href="${pageContext.request.contextPath}/report/core-era/retainability/dy.htm" sortable="true" />
			<display:column property ="succrIntraMscHoU2g"  titleKey="Intra-MSC Handover Success, U2G (%)" href="${pageContext.request.contextPath}/report/core-era/retainability/dy.htm" sortable="true" />
			<display:column property ="termSmsSucc"  titleKey="Terminating SMS Success (%)" href="${pageContext.request.contextPath}/report/core-era/msc-sms/dy.htm" sortable="true" />
			<display:column property ="termMscSmsSucc"  titleKey="Originating SMS Success (%)" href="${pageContext.request.contextPath}/report/core-era/msc-sms/dy.htm" sortable="true" />
			<display:column property ="maxCpLoad"  titleKey="CP Load" href="${pageContext.request.contextPath}/report/core-era/msc-cpload/dy.htm" sortable="true" />

		</display:table>
		</div>
	</div>
	<br/>
	<hr color="d7833b" style="margin: 0px auto;" width="100%" size="4">
	<br/>
	<center><h2>BÁO CÁO CS CORE MGW</h2></center>
	<div style="overflow: auto;">
	<div class = "tableStandar">
		<display:table name="${vRpDyMscMgwSummary}" id="vRpDyMscMgwSummary" requestURI="" pagesize="100"  export="true">
			<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true"/>
		    <display:column property="mscid" titleKey="SYSTEM" sortable="true"/>
		    <display:column property ="numMax"  titleKey="max LicMedia Stream Channels"  sortable="true" />
			<display:column property ="numBusy"  titleKey="Media Stream Channels Busy"  sortable="true" />
			<display:column property ="utilization"  titleKey="Media stream channel util (%)"  sortable="true" />
			<display:column property ="processorload"  titleKey="Max process load (%)"  sortable="true" />
			<display:column property ="curentTraff"  titleKey="Current traffic"  sortable="true" />
			<display:column property ="mgwAcc"  titleKey="MGw access (%)"  sortable="true" />
			<display:column property ="retainability"  titleKey="MGw service retain (%)"  sortable="true" />
			<display:column property ="receidLink1"  titleKey="Recei BW link1 (Mbps)"  sortable="true" />
			<display:column property ="receidLink2"  titleKey="Recei BW link2 (Mbps)"  sortable="true" />
			<display:column property ="transmitedLink1"  titleKey="Trans BW link1 (Mbps)"  sortable="true" />
			<display:column property ="transmitedLink2"  titleKey="Trans BW link2 (Mbps)"  sortable="true" />
			<display:column property ="signal"  titleKey="IP signa quality (%)"  sortable="true" />
			<display:column property ="poolProxySucc"  titleKey="MSC POOL PROXY Succ rate (%)"  sortable="true" />
			<display:column property ="errorRec"  titleKey="SCCP Protocol Data Unit Error messages received per second"  sortable="true" />
			<display:column property ="errorSent"  titleKey="SCCP ERRor messages sent per second"  sortable="true" />
			<display:column property ="gcpUptimeSecond"  titleKey="GCP link uptime in seconds  AAL2" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" sortable="true" />
			<display:column property ="aal2TermSeizure"  titleKey="AAL2 terminate seizure success rate (%)"  sortable="true" />
			<display:column property ="ipTermSeizure"  titleKey="IP terminate seizure success rate (%)"  sortable="true" />
			<display:column property ="orgInitSucc"  titleKey="Org Nb connect initial success rate (%)"  sortable="true" />
			<display:column property ="aal2Bearer"  titleKey="AAL2 bearer establish success rate (%)"  sortable="true" />
			<display:column property ="ipbcpBearer"  titleKey="IPBCP bearer establish success rate (%)"  sortable="true" />	   
		</display:table>
		</div>
	</div>
	<br/>
	<hr color="d7833b" style="margin: 0px auto;" width="100%" size="4">
	<br/> --%>
	<center><h2>BÁO CÁO 2G</h2></center>
	<div style="overflow: auto;">
	<div class = "tableStandar">
		<display:table name="${vRpDyBsc2GList}" id="vRpDyBsc2G" requestURI="" pagesize="100"   export="true">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY"  sortable="true"/>
	    		<display:column property="region" titleKey="REGION"  sortable="true"/>
	    		<display:column property="bscid" titleKey="BSC"  sortable="true"/>
	    		<display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Sites"  sortable="true"/>
	    		<display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Cells"  sortable="true"/>
			    <display:column property ="tDef"  titleKey="TCH Define"  sortable="true" />
				<display:column property ="tAvail"  titleKey="TCH Avail"  sortable="true" />
				<display:column property ="tTraf"  titleKey="TCH Traffic"  sortable="true" />
				<display:column property ="halfrate"  titleKey="HalfRate"  sortable="true" />
<%-- 				<display:column property ="inHoSucr"  titleKey="Inc HO Succ(%)"  sortable="true" />
				<display:column property ="ogHoSucr"  titleKey="Oug HO Succ(%)"  sortable="true" /> --%>
				<display:column property ="tDrpr"  titleKey="T_DRPR"  sortable="true" />
				<display:column property ="tBlkr"  titleKey="TCH block(%)"  sortable="true" />
				<display:column property ="tNblkr"  titleKey="TCH Nblock(%)  "  sortable="true" />
				<display:column property ="sDrpr"  titleKey="SDCCH Drop(%)"  sortable="true" />
				<display:column property ="sBlkr"  titleKey="SDCCH Block(%)"  sortable="true" />
				<display:column property ="cssr"  titleKey="CSSR"  sortable="true" />
				<display:column property ="dataload"  titleKey="Data Load (%)"  sortable="true" />

		</display:table>
		</div>
	</div>
	<hr color="d7833b" style="margin: 0px auto;" width="100%" size="4">
	<br/>
	<center><h2>BÁO CÁO 3G</h2></center>
	<br/>
	<div style="overflow: auto;">
	<div class = "tableStandar">
		<display:table name="${vRpDyBscWelcome3GList}" id="vRpDyBscWelcome3G" requestURI="" pagesize="100"  export="true">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY"  sortable="true"/>
	    		<display:column property="region" titleKey="REGION"  sortable="true"/>
	    		<display:column property="bscid" titleKey="BSC"  sortable="true"/>
	    		<display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Sites"  sortable="true"/>
	    		<display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Cells"  sortable="true"/>
			     <display:column property="speechTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SPEECH_TRAFF" class="SPEECH_TRAFF"  sortable="true" headerClass="master-header-3"/>
			    <display:column property="cs64Traff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CS64_TRAFF" sortable="true" headerClass="master-header-3"/>
			    <display:column property="psr99UlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_TRAFF"  sortable="true" headerClass="master-header-3"/>
			    <display:column property="psr99DlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_TRAFF"  sortable="true" headerClass="master-header-3"/>
			    <display:column property="hsupaUlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_TRAFF"  sortable="true" headerClass="master-header-3"/>
			    <display:column property ="hsdpaDlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_TRAFF"  sortable="true" headerClass="master-header-3 margin" class="margin"/>
			    <display:column property="speechCssr" titleKey="SPEECH_CSSR" sortable="true" headerClass="master-header-4"/>
			 
			    <display:column property ="cs64Cssr" titleKey="CS64_CSSR" class="CS64_CSSR" sortable="true" headerClass="master-header-4"/>
			    <display:column property="psr99Cssr" titleKey="PSR99_CSSR" class="PSR99_CSSR" sortable="true" headerClass="master-header-4"/>			    
			    <display:column property ="hsupaCssr" titleKey="HSUPA_CSSR" class="HSUPA_CSSR" sortable="true" headerClass="master-header-4"/>
			    <display:column property="hsdpaCssr" titleKey="HSDPA_CSSR" class="HSDPA_CSSR margin" sortable="true" headerClass="master-header-4 margin"/>
			    <display:column property="speechDrpr" titleKey="SPEECH_DRPR" sortable="true" headerClass="master-header-5"/>
			 
			    <display:column property="cs64Drpr" titleKey="CS64_DRPR" class="CS64_DRPR" sortable="true" headerClass="master-header-5"/>
				<display:column property="psr99Drpr" titleKey="PSR99_DRPR" class="PSR99_DRPR" sortable="true" headerClass="master-header-5"/>
				<display:column property ="hsupaDrpr" titleKey="HSUPA_DRPR" class="HSUPA_DRPR" sortable="true" headerClass="master-header-5"/>
				<display:column property="hsdpaDrpr" titleKey="HSDPA_DRPR" class="HSDPA_DRPR margin" sortable="true" headerClass="master-header-5 margin"/>
			    <display:column property="psr99UlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_THROUGHTPUT" sortable="true" headerClass="master-header-3"/>
			    <display:column property="psr99DlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_THROUGHTPUT" sortable="true" headerClass="master-header-3"/>
			    <display:column property="hsupaUlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_THROUGHTPUT" sortable="true" headerClass="master-header-3"/>
			    <display:column property="hsdpaDlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_THROUGHTPUT" sortable="true" headerClass="master-header-3 margin" class="margin"/>
				<display:column property="csIratHoSr" titleKey="CS_IRAT_HO_SR" sortable="true" headerClass="master-header-6"/>
				<display:column property ="psIratHoSr" titleKey="PS_IRAT_HO_SR" sortable="true" headerClass="master-header-6"/>
				<display:column property ="softHsSr" titleKey="SOFT_HS_SR" sortable="true" headerClass="master-header-6 margin" class="margin"/>
			    <display:column property ="cellDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELL_DOWNTIME" sortable="true" headerClass="master-header-2"/>
			    <display:column property ="hsDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HS_DOWNTIME" sortable="true" headerClass="master-header-2"/>
			    <display:column property ="eulDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EUL_DOWNTIME" sortable="true" headerClass="master-header-2 margin" class="margin"/>
			    <display:column property ="totBadCell" titleKey="BAD_CELL" sortable="true"/>
			    <display:column property ="badCellR" titleKey="BAD_CELL_R" sortable="true" headerClass="margin" class="margin"/>
			    <display:column property ="dataload" titleKey="DATALOAD" sortable="true"/>
</display:table>
</div>
	</div>
<br/>
<hr color="92cdd8" align=left width="100%" size="6">
<br/>
<center><h2>IP BACK BONE</h2></center>
<br/>
<div  style=" overflow-x: auto;overflow-y: hidden;">
<div class = "tableStandar">
		<display:table name="${vipbbdata}" id="user"  requestURI="" pagesize="100"  export="true">
	    		<display:column property="direction" titleKey="DIRECTION" headerClass="hide" class="hide"/>
	    		<display:column title="DIRECTION" media="html"  sortable="true">
		   	 	<a href="${pageContext.request.contextPath}/report/core/ip-backbone/DyLink.htm?direction=${user.direction}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${user.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${user.day}"/>">${user.direction}</a>
		    	</display:column>
	    		<display:column property="bandWidth" titleKey="BAND_WIDTH"  sortable="true"/>
	    		<display:column property="inKbitSecond" titleKey="IN_KBIT_SECOND"  sortable="true"/>
	    		<display:column property="inMaxUtilization" titleKey="IN_MAX_UTILIZATION" class="IN_MAX_UTILIZATION"  sortable="true"/>
	    		<display:column property="outKbitSecond" titleKey="OUT_KBIT_SECOND"/>
	    		<display:column property="outMaxUtilization" titleKey="OUT_MAX_UTILIZATION" class="OUT_MAX_UTILIZATION"  sortable="true"/>
	    		<display:column property="delayMax" titleKey="DELAY_MAX"  sortable="true"/>
	    		<display:column property="delayAvg" titleKey="DELAY_AVG"  sortable="true"/>
	    		<display:column property="jitterAvg" titleKey="JITTER_AVG"  sortable="true"/>
	    		<display:column property="maxOverThreshold50" titleKey="MAX_OVER_THRESHOLD_50"  sortable="true"/>
	    		<display:column property="maxOverThreshold10" titleKey="MAX_OVER_THRESHOLD_10"  sortable="true"/>
	    		<display:column property="packageLoss" titleKey="PACKAGE_LOSS"  sortable="true"/>
		</display:table>
</div>	
</div>
<br/>

<center><h2>IP BACK BONE DELAYTIME INTERNET</h2></center>
<br/>
<div style=" overflow-x: auto;overflow-y: hidden;">
<div class = "tableStandar">
			<display:table name="${vipbbInternet}"  id = "vipbbInternet" requestURI="" pagesize="100" >
	    		<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DATE" sortable="true"/>
	    		<display:column property="direction" titleKey="DIRECTION" sortable="true"/>
	    		<display:column property="link" titleKey="LINK"  sortable="true" />
	    		<display:column property ="refValue"  titleKey="Unit(ms)"  sortable="true" />
				<display:column property ="delayMs1Max"  titleKey="Max DelayTime"  sortable="true" headerClass="master-header-3"/>
				<display:column property ="delayMs1Avg"  titleKey="Avg DelayTime"  sortable="true" headerClass="master-header-3" class = "AVG1"/>
				<display:column property ="perfomance1"  titleKey="Performance"  sortable="true" class = "PER1" headerClass="master-header-3"/>
				<display:column property ="delayMs2Max"  titleKey="Max DelayTime"  sortable="true" headerClass="master-header-4"/>
				<display:column property ="delayMs2Avg"  titleKey="Avg DelayTime"  sortable="true" headerClass="master-header-4" class = "AVG2"/>
				<display:column property ="perfomance2"  titleKey="Performance"  sortable="true" class = "PER2" headerClass="master-header-4"/>
			</display:table>
		</div>
</div>
<hr color="92cdd8" align=left width="100%" size="6">
<br/>

<br/>
<center><h2>THEO DÕI PHÁT TRIỂN SITE</h2></center>
	<br/> 
	<div id="siteGrowthChart" style="width: 1000px; margin: 1em auto"></div>
<br/>
<hr color="d7833b" style="margin: 0px auto;" width="100%" size="4">
<br/>
<center><h3>CÁC TRẠM MỚI PHÁT SÓNG HOẶC ĐỔI TÊN</h3></center>
<br/>
<center><b>TRẠM MỚI PHÁT SÓNG 2G</b></center>
	<br/>
		<div style="overflow: auto;">
		<div class = "tableStandar">
			<display:table name="${vRpDyNodeChange2GList}" id="vRpDyNodeChange2G" requestURI="" pagesize="100"  export="true">
					<display:column property="region" titleKey="REGION"/>
					<display:column property="district" titleKey="DISTRICT"/>
				    <display:column property ="parentNode" titleKey="BSC"/>	
				    <display:column property="equipmentName" titleKey="CELL"/>
					<display:column property="cgi" titleKey="CGI"/>
					<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY BROADCASTING"/>
					<display:column titleKey="HOUR BROADCASTING">
						${vRpDyNodeChange2G.hour}:00
					</display:column>
			</display:table>
		</div>
		</div>
	<br/>
	<hr color="d7833b" style="margin: 0px auto;" width="100%" size="2">
	<br/>
<center><b>TRẠM MỚI PHÁT SÓNG 3G</b></center>
	<br/>
		<div style="overflow: auto;">
		<div class = "tableStandar">
			<display:table name="${vRpDyNodeChange3GList}" id="vRpDyNodeChange3G" requestURI="" pagesize="100"  export="true">
					<display:column property="region" titleKey="REGION"/>
					<display:column property="district" titleKey="DISTRICT"/>
				    <display:column property ="parentNode" titleKey="BSC"/>	
				    <display:column property="equipmentName" titleKey="CELL"/>
					<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY BROADCASTING"/>
					<display:column titleKey="HOUR BROADCASTING">
						${vRpDyNodeChange3G.hour}:00
					</display:column>
			</display:table>
			</div>
		</div>
	<br/>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script> 
${siteGrowthChart}
<script>
    $("#user").change(function () {
      window.location = '${pageContext.request.contextPath}/log/checkjob/detail.htm?user=' + $("#user").val();
      
      });  
    ${highlight}
</script>  
<script type="text/javascript">
	$(function() {
		$( "#day" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
		${highlight1}
		${highlight2}
		${highlight3}
		${highlight4}
		${highlight5}
		${highlight6}
		${highlight7}
		${highlight8}
		${highlight9}
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

<script type="text/javascript">
	$(document).ready(function(){
		var trs='<tr >';
	    trs=trs +'<th style="color:#0000FF" colspan="2"></th>';
	    trs=trs +'<th style="color:#0000FF" colspan="2">TRAFFIC IN</th>';
	    trs=trs +'<th style="color:#0000FF" colspan="2">TRAFFIC OUT</th>';
	    trs=trs +'<th style="color:#0000FF" colspan="6">SLA PARAMETERS</th>';

	   	trs=trs +'<tr style="color:#0000FF;width:135px"><th >Direction</th>';
	    trs=trs +'<th style="color:#0000FF">BandWidth</th>';
	    trs=trs +'<th style="color:#0000FF">kbit/second (Peak)</th>';
	    trs=trs +'<th style="color:#0000FF">MAX Utilization (%)</th>';
	    trs=trs +'<th style="color:#0000FF">kbit/second (Peak)</th>';
	    trs=trs +'<th style="color:#0000FF">MAX Utilization (%)</th>';
	    trs=trs +'<th style="color:#0000FF">Delay MAX (ms)</th>';
	    trs=trs +'<th style="color:#0000FF">Delay Average (ms)</th>';
	    trs=trs +'<th style="color:#0000FF" >Jitter Average (ms)</th>';
	    trs=trs +'<th style="color:#0000FF" >MaxOver Threshold (10ms) Duration (Min)</th>';
	    trs=trs +'<th style="color:#0000FF">MaxOver Threshold (50ms) Duration (Min)</th>';
	    trs=trs +'<th style="color:#0000FF">Package loss</th>';
	    
	$('#user thead').html(trs);
	});
</script>