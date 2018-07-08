<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Báo cáo QoS District ${district}/${province}</title>
<content tag="heading"><fmt:message key="sidebar.district.qos"/>${district}/${province}</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/district/hr/detail.htm?province=${province}&district=${district}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/district/dy/detail.htm?province=${province}&district=${district}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/district/wk/detail.htm?province=${province}&district=${district}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/district/mn/detail.htm?province=${province}&district=${district}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/district/dy/bh.htm?province=${province}&district=${district}&endDate=${endDate}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/district/wk/bh.htm?province=${province}&district=${district}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/district/mn/bh.htm?province=${province}&district=${district}"><span>Báo cáo BH tháng</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="detail.htm" class="form" name="frmSample" onSubmit="return ValidateForm()">
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
			        &nbsp;&nbsp;PROVINCE 
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
				    &nbsp;&nbsp;DISTRICT <input value="${district}" name="district" id="district" size="10" maxlength="50">
	            	&nbsp;&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	            	&nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
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
		<div style="overflow: auto;">
			<display:table style = "width: 150%" name="${vRpDyDistrictList}" id="vRpDyDistrict" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true"  headerClass="master-header-1"/>
			    <display:column property="region" titleKey="TT" sortable="true"  headerClass="master-header-1"/>
				<display:column property="province" titleKey="PROVINCE" headerClass="hide" class="hide"/>
				<display:column titleKey="PROVINCE" media="html" sortable="true" sortProperty="province" style = "width: 120px"  headerClass="master-header-1">
			    	<a href="${pageContext.request.contextPath}/report/radio/province/dy/detail.htm?province=${vRpDyDistrict.province}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyDistrict.day}"/>">${vRpDyDistrict.province}</a>
			    </display:column>
				<display:column property="district" titleKey="DISTRICT" headerClass="hide" class="hide"/>
			    <display:column titleKey="DISTRICT" media="html" sortable="true" sortProperty="district" style = "width: 120px"  headerClass="master-header-1">
			    	<a href="${pageContext.request.contextPath}/report/radio/district/hr/detail.htm?province=${vRpDyDistrict.province}&district=${vRpDyDistrict.district}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyDistrict.day}"/>">${vRpDyDistrict.district}</a>
			    </display:column>
			    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SITES" headerClass="hide" class="hide"/>
			    <display:column titleKey="SITES" media="html" sortable="true" sortProperty="sites"  headerClass="master-header-1">
			    	<a href="${pageContext.request.contextPath}/report/radio/site-qos/dy/list.htm?province=${vRpDyDistrict.province}&day=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyDistrict.day}"/>"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyDistrict.sites}"/></a>
			    </display:column>
			    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELLS" headerClass="hide" class="hide"/>
			    <display:column titleKey="CELLS" media="html" sortable="true" sortProperty="cells"  headerClass="master-header-1">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell/dy/list.htm?province=${vRpDyDistrict.province}&day=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyDistrict.day}"/>"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyDistrict.cells}"/></a>
			    </display:column>
			    <display:column property="trxs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRXS"  sortable="true" class="margin" headerClass="master-header-1 margin"/>
			    <display:column property ="tDef" titleKey="TCH Def" class="T_DEF" sortable="true"  headerClass="master-header-2"/>
				<display:column property ="tAvail" titleKey="TCH Avail" class="T_AVAIL" sortable="true"  headerClass="master-header-2"/>
				<display:column property ="tBlkr" titleKey="T_BLKR" class="T_BLKR" sortable="true"  headerClass="master-header-2"/>
				<display:column property ="tHoblkr" titleKey="T_HOBLKR" class="T_HOBLKR" sortable="true"  headerClass="master-header-2"/>
				<display:column property="cssr" titleKey="CSSR" class="CSSR margin" sortable="true"  headerClass="master-header-2"/>
				<display:column property="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Traffic (Erl)"  sortable="true"  headerClass="master-header-2"/>
			    <display:column property="tDrpr" titleKey="T_DRPR" class="T_DRPR" sortable="true"  headerClass="master-header-2"/>
			    
			    <display:column property="sSsr" titleKey="S_SSR" sortable="true"  headerClass="master-header-3"/>
			    <display:column property="sBlkr" titleKey="S_BLKR"  class="S_BLKR" sortable="true" headerClass="master-header-3"/>
			    <display:column property="sDrpr" titleKey="S_DRPR"  sortable="true" headerClass="master-header-3"/>
			    <display:column property="tAsr" titleKey="T_ASR"  sortable="true" headerClass="master-header-3"/>
			    <display:column property="haftratePercent" titleKey="HALFRATE"  sortable="true" class="margin" headerClass="margin master-header-3"/>
			    <%-- <display:column property="inHoSucr" titleKey="IN_HO_SUCR" sortable="true"/>
			    <display:column property="ogHoSucr" titleKey="OG_HO_SUCR"  sortable="true" class="margin" headerClass="margin"/> --%>
	    		<display:column property="dataload" titleKey="DATALOAD"  sortable="true"/>
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

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
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
		
		${highlight}
	});

 $(function() {
	$( '.checkAll' ).live( 'change', function() {
		$( '.cb-element' ).attr( 'checked', $( this ).is( ':checked' ) ? 'checked' : '' );
		$( this ).next().text( $( this ).is( ':checked' ) ? 'Uncheck All' : 'Check All' );
		if($( this ).is( ':checked' )){
			for (var i=9;i<20;i++)
			{
				$('#vRpDyDistrictList td:nth-child('+i+'),#vRpDyDistrictList th:nth-child('+i+')').show();
			}
		} else {
			for (var i=9;i<20;i++)
			{
				$('#vRpDyDistrictList td:nth-child('+i+'),#vRpDyDistrictList th:nth-child('+i+')').hide();
			}
		}
	});

	$( '.cb-element' ).live( 'change', function() {
		$( '.cb-element' ).length == $( '.cb-element:checked' ).length ? $( '.checkAll' ).attr( 'checked', 'checked' ).next().text( 'Uncheck All' ) : $( '.checkAll' ).attr( 'checked', '' ).next().text( 'Check All' );

	});

	${checkColumns}

	${checkSeries}
}); 
</script>
