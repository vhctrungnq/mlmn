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
		<table width="100%" class="form">
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
	<center><h2>BÁO CÁO 2G</h2></center>
	<div style="overflow: auto;">
	 <div class="tableStandar">
	 	<display:table style = "width:150%" name="${vRpDyRegionList}" id="vRpDyRegion" requestURI="" pagesize="100" export="true">
			<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="Ngày" sortable="true" headerClass="master-header-1"/>
		    <display:column property="region" titleKey="Center" sortable="true" headerClass="master-header-1"/>
		    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SITES" sortable="true" headerClass="master-header-1"/>
		    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELLS" sortable="true" headerClass="master-header-1"/>
		    <display:column property="trxs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRXS" sortable="true" class="margin" headerClass="master-header-1"/>
		    <display:column property ="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF" headerClass="master-header-2" class="T_TRAF" sortable="true"/>
		    <display:column property ="tDrpr" titleKey="T_DRPR" class="T_DRPR" sortable="true" headerClass="master-header-2"/>
		    <display:column property ="tBlkr" titleKey="T_BLKR" class="T_BLKR" sortable="true" headerClass="master-header-2"/>
		    <display:column property ="tHoblkr" titleKey="T_HOBLKR" class="T_HOBLKR" sortable="true" headerClass="master-header-2"/>
		    <display:column property ="tEmpdr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_EMPDR" sortable="true" headerClass="master-header-2"/>
		    <display:column property="tAsr" titleKey="T_ASR" sortable="true" headerClass="master-header-2"/> 
		    <display:column property="cssr" titleKey="CSSR" class="CSSR margin" sortable="true"  headerClass="master-header-2" />
		    <display:column property ="sDef" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_DEF" sortable="true"  headerClass="master-header-3"/>
		    <display:column property="sSsr" titleKey="S_SSR" sortable="true" headerClass="master-header-3"/>
		    <display:column property="sBlkr" titleKey="S_BLKR" class="S_BLKR" sortable="true" headerClass="master-header-3"/>
		    <display:column property ="sDrpr" titleKey="S_DRPR" sortable="true" class="S_DRPR" headerClass="master-header-3"/>

		    <display:column property="halfrate" titleKey="HALFRATE" sortable="true" class="margin"  headerClass="master-header-3"/> 
		    <display:column property ="ulData2g" titleKey="UL DATA (GB)" sortable="true"  headerClass="master-header-4"/>
		    <display:column property="dlData2g" titleKey="DL DATA (GB)" sortable="true"  headerClass="master-header-4"/>
		    <display:column property ="badCell" titleKey="BAD_CELL" sortable="true"  />
		    <display:column property ="badCellR" titleKey="BAD_CELL_R" sortable="true"  />
		    <display:column property ="dataload" titleKey="DATALOAD" sortable="true" class="margin" headerClass="margin" />
		</display:table>
	 </div>
		
	</div> 
	<center><h2>BÁO CÁO 3G</h2></center>
	<br/>
	<div style="overflow: auto;">
	 <div class="tableStandar">
		<display:table style = "width:200%" name="${vRpDyRegion3GList}" id="vRpDyRegion3G" requestURI="" pagesize="100"  export="true">
			<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="Date" sortable="true" headerClass="master-header-1"/>
		    <display:column property="region" titleKey="Center" sortable="true" headerClass="master-header-1"/>
		    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Sites" sortable="true"  headerClass="master-header-1"/>
		    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Cells" sortable="true" headerClass="master-header-1 margin" class="margin"/>
		    <display:column property="speechTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SPEECH_TRAFF" class="SPEECH_TRAFF" sortable="true" headerClass="master-header-3"/>
		    <display:column property="cs64Traff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CS64_TRAFF" sortable="true" headerClass="master-header-3"/>
		    <display:column property="psr99UlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_TRAFF"  sortable="true" headerClass="master-header-3"/>
		    <display:column property="psr99DlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_TRAFF"  sortable="true" headerClass="master-header-3"/>
		    <display:column property="hsupaUlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_TRAFF"  sortable="true" headerClass="master-header-3"/>
		    <display:column property ="hsdpaDlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_TRAFF"  sortable="true" headerClass="master-header-3 margin" class="margin"/>
		    <display:column property ="speechCssr" titleKey="SPEECH_CSSR" class="SPEECH_CSSR" sortable="true" headerClass="master-header-4"/>
		    <display:column property ="cs64Cssr" titleKey="CS64_CSSR" class="CS64_CSSR" sortable="true" headerClass="master-header-4"/>
		    <display:column property="psr99Cssr" titleKey="PSR99_CSSR" class="PSR99_CSSR" sortable="true" headerClass="master-header-4"/>			    
		    <display:column property ="hsupaCssr" titleKey="HSUPA_CSSR" class="HSUPA_CSSR" sortable="true" headerClass="master-header-4"/>
		    <display:column property="hsdpaCssr" titleKey="HSDPA_CSSR" sortable="true" headerClass="master-header-4 margin" class="HSDPA_CSSR margin"/>
		    <display:column property="speechDrpr" titleKey="SPEECH_DRPR" class="SPEECH_DRPR" sortable="true" headerClass="master-header-5"/>
		    <display:column property="cs64Drpr" titleKey="CS64_DRPR" class="CS64_DRPR" sortable="true" headerClass="master-header-5"/>
    		<display:column property="psr99Drpr" titleKey="PSR99_DRPR" class="PSR99_DRPR" sortable="true" headerClass="master-header-5"/>
    		<display:column property ="hsupaDrpr" titleKey="HSUPA_DRPR" class="HSUPA_DRPR" sortable="true" headerClass="master-header-5"/>
    		<display:column property="hsdpaDrpr" titleKey="HSDPA_DRPR" sortable="true" headerClass="master-header-5 margin" class="HSDPA_DRPR margin"/>
		    <display:column property="psr99UlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_THROUGHTPUT" sortable="true" headerClass="master-header-3"/>
		    <display:column property="psr99DlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_THROUGHTPUT" sortable="true" headerClass="master-header-3"/>
		    <display:column property="hsupaUlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_THROUGHTPUT" sortable="true" headerClass="master-header-3"/>
		    <display:column property="hsdpaDlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_THROUGHTPUT" sortable="true" headerClass="master-header-3 margin" class="margin"/>
    		<display:column property ="cellDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELL_DOWNTIME" sortable="true" headerClass="master-header-2"/>
		    <display:column property ="hsDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HS_DOWNTIME" sortable="true" headerClass="master-header-2"/>
		    <display:column property ="eulDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EUL_DOWNTIME" sortable="true" headerClass="master-header-2"/>
		    <display:column property ="totBadCell" titleKey="BAD_CELL" sortable="true"/>
		    <display:column property ="badCellR" titleKey="BAD_CELL_R" sortable="true"/>
		</display:table>
		</div>
	</div>
<br/>
<hr color="92cdd8" align=left width="100%" size="6">
	<br/>
<center><h2>CHẤT LƯỢNG MẠNG LƯỚI 30 NGÀY GẦN ĐÂY</h2></center>
<br/>
	<div id="trafChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="dlDataChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="ulDataChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="cssrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="dcrChart" style="width: 1000px; margin: 1em auto"></div>
<br/>
<hr color="92cdd8" align=left width="100%" size="6">
	<br/>
<center><h2>BÁO CÁO THEO CHI NHÁNH</h2></center> 
	<center><h3>BÁO CÁO 2G</h3></center> 
	<div class="tableStandar">
		<display:table name="${vRpDyBranch2G3GList}" id="vRpDyBranch2G3G" requestURI="" pagesize="100"  export="true">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="Ngày"/>
	    		<display:column property="region" titleKey="Center"/>
	    		<display:column property="branch" titleKey="Branch"/>
			    <display:column property="traf2g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRAFFIC (Erl)" class="T_TRAF"/>
			    <display:column property="cssr2g" titleKey="CSSR (%)" class="CSSR"/>
			    <display:column property ="dcr2g" titleKey="DCR (%)" class="T_DRPR"/>
			    <display:column property ="ulData2g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL DATA (GB)"/>	
			    <display:column property="dlData2g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DL DATA (GB)" />
		</display:table>
		</div>  
	<center><h3>BÁO CÁO 3G</h3></center>  
	<div class="tableStandar">
		<display:table name="${vRpDyBranch2G3GList}" id="vRpDyBranch3G" requestURI="" pagesize="100"   export="true">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="Ngày"/>
	    		<display:column property="region" titleKey="Center"/>
	    		<display:column property="branch" titleKey="Branch"/>
			    <display:column property="traf3g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SPEECH_TRAFF" class="SPEECH_TRAFF"/>
			    <display:column property="cssr3g" titleKey="SPEECH_CSSR" class="SPEECH_CSSR"/>
			    <display:column property ="ulData3g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL DATA (GB)"/>	
			    <display:column property="dlData3g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DL DATA (GB)" />
			    <display:column property ="dcr3g" titleKey="SPEECH_DRPR"/>
		</display:table>
		</div> 
	<br/>
<hr color="92cdd8" align=left width="100%" size="6">
	<br/>
<center><h2>BÁO CÁO THEO QUẬN HUYỆN</h2></center>
<br/>
	<center><h3>BÁO CÁO 2G</h3></center> 
	<div class="tableStandar">
		<display:table name="${vRpDyDistrict2G3GList}" id="vRpDyDistrict2G3G" requestURI="" pagesize="100" export="true">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="Ngày"/>
	    		<display:column property="region" titleKey="Center"/>
	    		<display:column property="province" titleKey="Province"/>
	    		<display:column property="district" titleKey="District"/>
			    <display:column property="traf2g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRAFFIC (Erl)" class="T_TRAF"/>
			    <display:column property="cssr2g" titleKey="CSSR (%)" class="CSSR"/>
			    <display:column property ="dcr2g" titleKey="DCR (%)" class="T_DRPR"/>
			    <display:column property ="ulData2g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL DATA (GB)"/>	
			    <display:column property="dlData2g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DL DATA (GB)" />
			    <display:column property ="availability" titleKey="AVAILABILITY (%)"/>
			    <display:column property ="dataload" titleKey="DATALOAD"/>
		</display:table>
		</div>  
	<center><h3>BÁO CÁO 3G</h3></center>  
	<div class="tableStandar">
		<display:table name="${vRpDyDistrict2G3GList}" id="vRpDyDistrict3G" requestURI="" pagesize="100"  export="true">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="Ngày"/>
	    		<display:column property="region" titleKey="Center"/>
	    		<display:column property="province" titleKey="Province"/>
	    		<display:column property="district" titleKey="District"/>
			    <display:column property="traf3g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SPEECH_TRAFF" class="SPEECH_TRAFF"/>
			    <display:column property="cssr3g" titleKey="SPEECH_CSSR" class="SPEECH_CSSR"/>
			    <display:column property ="dcr3g" titleKey="SPEECH_DRPR" class="SPEECH_DRPR"/>
			    <display:column property ="ulData3g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL DATA (GB)"/>	
			    <display:column property="dlData3g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DL DATA (GB)" />
			    <display:column property ="dataload3g" titleKey="DATALOAD"/>
		</display:table>
	</div>
<br/>
<hr color="92cdd8" align=left width="100%" size="6">
<br/>
<center><h2>BÁO CÁO THEO BSC/RNC</h2></center> 
	<center><h3>BÁO CÁO 2G</h3></center>
	<div class="tableStandar">
		<display:table name="${vRpDyBsc2GList}" id="vRpDyBsc2G" requestURI="" pagesize="100"  export="true">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="Ngày"/>
	    		<display:column property="region" titleKey="Center"/>
	    		<display:column property="bscid" titleKey="Bsc"/>
	    		<display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Sites"/>
	    		<display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Cells"/>
			    <display:column property="traf2g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRAFFIC (Erl)" class="T_TRAF"/>
			    <display:column property="cssr2g" titleKey="CSSR (%)" class="CSSR"/>
			    <display:column property ="dcr2g" titleKey="DCR (%)" class="T_DRPR"/>
			    <display:column property ="ulData2g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL DATA (GB)"/>	
			    <display:column property="dlData2g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DL DATA (GB)" />
			    <display:column property ="badCell2g" titleKey="Bad Cells"/>
			    <display:column property ="rateBadCell2g" titleKey="% Bad Cell"/>
			    <display:column property ="availability" titleKey="AVAILABILITY (%)"/>
			    <display:column property ="dataload" titleKey="DATALOAD"/>
		</display:table>
	</div> 
	<center><h3>BÁO CÁO 3G</h3></center> 
	<div class="tableStandar">
		<display:table name="${vRpDyBscWelcome3GList}" id="vRpDyBscWelcome3G" requestURI="" pagesize="100"   export="true">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="Ngày"/>
	    		<display:column property="region" titleKey="Center"/>
	    		<display:column property="bscid" titleKey="Bsc"/>
	    		<display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Sites"/>
	    		<display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Cells"/>
			    <display:column property="speechTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SPEECH_TRAFF" class="SPEECH_TRAFF"/>
			    <display:column property="speechCssr" titleKey="SPEECH_CSSR" class="SPEECH_CSSR"/>
			    <display:column titleKey="UL DATA (GB)">
			    	<fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyBscWelcome3G.ulData}"/>
			    </display:column>	
			    <display:column titleKey="DL DATA (GB)">
			    	<fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyBscWelcome3G.dlData}"/>
			    </display:column>
			    <display:column property ="speechDrpr" titleKey="SPEECH_DRPR" class="SPEECH_DRPR"/>
			    <display:column property ="totBadCell" titleKey="Bad Cells"/>
			    <display:column property ="badCellR" titleKey="% Bad Cell"/>
		</display:table>
	</div> 
<hr color="92cdd8" align=left width="100%" size="6">
<br/>
<center><h2>THEO DÕI PHÁT TRIỂN SITE</h2></center>
	<br/>
	<div id="siteGrowthChart" style="width: 1000px; margin: 1em auto"></div> 
<hr color="92cdd8" align=left width="100%" size="6"> 
<center><h3>CÁC TRẠM MỚI PHÁT SÓNG HOẶC ĐỔI TÊN</h3></center>
<br/>
<center><b>TRẠM MỚI PHÁT SÓNG 2G</b></center>
	<br/>
		<div class="tableStandar">
			<display:table name="${vRpDyNodeChange2GList}" id="vRpDyNodeChange2G" requestURI="" pagesize="100"  export="true">
					<display:column property="region" titleKey="Center"/>
					<display:column property="district" titleKey="District"/>
				    <display:column property ="parentNode" titleKey="BSC"/>	
				    <display:column property="equipmentName" titleKey="CELL"/>
					<display:column property="cgi" titleKey="CGI"/>
					<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="Ngày phát sóng"/>
					<display:column titleKey="Giờ phát sóng">
						${vRpDyNodeChange2G.hour}:00
					</display:column>
			</display:table>
		</div>
	<br/>
	<hr color="d7833b" style="margin: 0px auto;" width="100%" size="2">
	<br/>
<center><b>TRẠM MỚI PHÁT SÓNG 3G</b></center>
	<br/>
		<div class="tableStandar">
			<display:table name="${vRpDyNodeChange3GList}" id="vRpDyNodeChange3G" requestURI="" pagesize="100"  export="true">
					<display:column property="region" titleKey="Center"/>
					<display:column property="district" titleKey="District"/>
				    <display:column property ="parentNode" titleKey="BSC"/>	
				    <display:column property="equipmentName" titleKey="CELL"/>
					<display:column property="equipmentType" titleKey="CGI"/>
					<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="Ngày phát sóng"/>
					<display:column titleKey="Giờ phát sóng">
						${vRpDyNodeChange3G.hour}:00
					</display:column>
			</display:table>
		</div>
	<br/>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
${trafChart}
${dlDataChart}
${ulDataChart}
${cssrChart}
${dcrChart}
${traffBranch2GChart}
${traffBranch3GChart}
${traffBranch2G3GChart}
${ulDataTraffBrach2GChart}
${ulDataTraffBrach3GChart}
${ulDataTraffBrach2G3GChart}
${dlDataTraffBrach2GChart}
${dlDataTraffBrach3GChart}
${dlDataTraffBrach2G3GChart}
${siteGrowthChart}
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