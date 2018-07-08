<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Báo cáo khác</title>
<content tag="heading"><h1>BÁO CÁO KHÁC</h1></content>

<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/other/mn.htm"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/other/qr.htm"><span>Báo cáo quý</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/other/yr.htm"><span>Báo cáo năm</span></a></li>
</ul>

<div class="ui-tabs-panel">
	<center><h2>BÁO CÁO NĂM</h2></center>
	<br/>
	<form method="get" action="yr.htm">
		<table width="100%" class="form">
			<tr>
				<td align="left">
			        Chọn năm báo cáo :  
			         <input value="${year}" name="year" id="year" size="4" maxlength="4">
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	<br/>
	<center><h3>BÁO CÁO TRAFFIC - KPI NGÀY TRONG THÁNG ${month}/${year}</h3></center>
	<br/>
	<div style="overflow: auto;">
		<display:table name="${vRpMnRegion2G3GList}" id="vRpMnRegion" requestURI="" pagesize="100" class="simple2" export="true">
			<display:column property="year" titleKey="YEAR"/>
    		<display:column property="region" titleKey="CENTER"/>
		    <display:column property="traf2g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRAFFIC 2G (Erl)"/>
		    <display:column property="traf3g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRAFFIC 3G (Erl)"/>
		    <display:column property ="cssr2g" titleKey="CSSR 2G (%)"/>	
		    <display:column property="dcr2g" titleKey="DCR 2G (%)" />
		    <display:column property ="badCell2g" titleKey="% BAD CELL 2G"/>	
		    <display:column property="badCell3g" titleKey="% BAD CELL 3G" />
		</display:table>
	</div>
	<br/>
	<div id="trafChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="dataChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<hr color="92cdd8" align=left width="100%" size="6">
	<br/>
	<center><h3>BÁO CÁO TỶ LỆ TRAFFIC/DATA THEO CHI NHÁNH</h3></center>
	<br/>
	<table>
	<tr>
		<td><div id="traffBranch2GChart" style="width: 400px; height: 400px; margin: 0 auto"></div></td>
		<td style="width:10px"></td>
		<td><div id="traffBranch3GChart" style="width: 400px; height: 400px; margin: 0 auto"></div></td>
		<td style="width:10px"></td>
		<td><div id="traffBranch2G3GChart" style="width: 400px; height: 400px; margin: 0 auto"></div></td>
	</tr>
	<tr height="15"></tr>
	<tr>
		<td><div id="dataTraffBranch2GChart" style="width: 400px; height: 400px; margin: 0 auto"></div></td>
		<td style="width:10px"></td>
		<td><div id="dataTraffBranch3GChart" style="width: 400px; height: 400px; margin: 0 auto"></div></td>
		<td style="width:10px"></td>
		<td><div id="dataTraffBranch2G3GChart" style="width: 400px; height: 400px; margin: 0 auto"></div></td>
	</tr>
	</table>
	<br/>
	<hr color="92cdd8" align=left width="100%" size="6">
	<br/>
	<center><h2>BÁO CÁO THEO NĂM</h2></center>
	<br/>
	<br/>
		<table width="100%" class="form">
			<tr>
				<td align="left">
			        Traffic - KPI theo năm 
			         <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4">
			          Đến <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4">
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	<br/>
	</form>
	<center><h3>BÁO CÁO TRAFFIC THEO TRUNG TÂM</h3></center>
	<br/>
	<div style="overflow: auto;">
		<display:table name="${vRpYrRegion2G3GList}" id="vRpYrRegion2G3G" requestURI="" pagesize="100" class="simple2" export="true">
				<display:column property="year" titleKey="YEAR"/>
	    		<display:column property="region" titleKey="CENTER"/>
			    <display:column property="traf2g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRAFFIC 2G (Erl)"/>
			    <display:column property="traf3g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRAFFIC 3G (Erl)"/>
			    <display:column property ="cssr2g" titleKey="CSSR 2G (%)"/>
			    <display:column property ="cssr3g" titleKey="CSSR 3G (%)"/>	
			    <display:column property="dcr2g" titleKey="DCR 2G (%)" />	
			    <display:column property="dcr3g" titleKey="DCR 3G (%)" />
			    <display:column property ="ulData2g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL DATA 2G (GB)"/>	
			    <display:column property="dlData2g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DL DATA 2G (GB)" />
			    <display:column property ="ulData3g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL DATA 3G (GB)"/>	
			    <display:column property="dlData3g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DL DATA 3G (GB)" />
			    <display:column property ="badCell2g" titleKey="% BAD CELL 2G"/>	
			    <display:column property="badCell3g" titleKey="% BAD CELL 3G" />
		</display:table>
	</div>
	<br/>
	<div id="traffYearChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="traffByYearChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="traffRegionChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<hr color="92cdd8" align=left width="100%" size="6">
	<br/>
	<center><h3>BÁO CÁO TRAFFIC THEO CHI NHÁNH</h3></center>
	<br/>
	<div style="overflow: auto;">
		<display:table name="${vRpYrBranchTraffic2G3GList}" id="vRpYrBranchTraffic2G3G" requestURI="" pagesize="100" class="simple2" export="true">
				<display:column property="year" titleKey="YEAR"/>
	    		<display:column property="region" titleKey="CENTER"/>
	    		<display:column property="branch" titleKey="BRANCH"/>
			    <display:column property="traf2g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRAFFIC 2G (Erl)"/>
			    <display:column property="traf3g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRAFFIC 3G (Erl)"/>
			    <display:column property ="ulData2g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL DATA 2G (GB)"/>	
			    <display:column property="dlData2g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DL DATA 2G (GB)" />
			    <display:column property ="ulData3g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL DATA 3G (GB)"/>	
			    <display:column property="dlData3g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DL DATA 3G (GB)" />
		</display:table>
	</div>
	<br/>
	<div id="traff2GBranchChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="traff3GBranchChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="dataBranchChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
${trafChart}
${dataChart}
${traff2GBranchChart}
${traff3GBranchChart}
${dataBranchChart}
${traffYearChart}
${traffByYearChart}
${traffRegionChart}
${traffBranch2GChart}
${traffBranch3GChart}
${traffBranch2G3GChart}
${dataTraffBranch2GChart}
${dataTraffBranch3GChart}
${dataTraffBranch2G3GChart}

<script type="text/javascript">
	$(function() {
		${highlight1}
		${highlight2}
	});
</script>