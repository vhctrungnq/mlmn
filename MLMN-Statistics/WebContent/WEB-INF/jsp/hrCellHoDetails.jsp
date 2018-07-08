<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>cell ho hourly report</title>
<content tag="heading">Báo cáo Handover Cell ${cellid}</content>
<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell-ho/hr/details.htm?bscid=${bscid}&cellid=${cellid}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-ho/dy/details.htm?bscid=${bscid}&cellid=${cellid}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-ho/wk/details.htm?bscid=${bscid}&cellid=${cellid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-ho/mn/details.htm?bscid=${bscid}&cellid=${cellid}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-ho/dy/bhDetails.htm?bscid=${bscid}&cellid=${cellid}&endDate=${endDate}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-ho/wk/bhDetails.htm?bscid=${bscid}&cellid=${cellid}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-ho/mn/bhDetails.htm?bscid=${bscid}&cellid=${cellid}"><span>Báo cáo BH tháng</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="details.htm">
		<table width="100%" class="form">
			<tr>
				<td align="left">
					BSC 
			        <select name="bscid" id="bscid">
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
				    &nbsp;CELL <input value="${cellid}" name="cellid" id="cellid" size="10">
	            	&nbsp;Từ giờ <input value="${startHour}" name="startHour" id="startHour" size="1" maxlength="2"> Ngày
	            	&nbsp;<input value="${startDate}"" name="startDate" id="startDate" size="10" maxlength="10">
	            	&nbsp;&nbsp;Tới ngày <input value="${endHour}" name="endHour" id="endHour" size="1" maxlength="2"> Ngày
	            	&nbsp;<input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	  </form>
		<br/>
		<table class="form">
	        <tr>
				<td align="left">
					<input type="checkbox" class="checkAll" checked="checked" /><b>Check all</b>
					<% 
						String[] cellHoArray = {"HO_ATT","HO_SUCR","OG_HO_ATT","OG_HO_SUCR","IN_HO_ATT","IN_HO_SUCR"};
						String[] cellHoNameArray = {"hoAtt","hoSucr","ogHoAtt","ogHoSucr","inHoAtt","inHoSucr"};
						int i;
						for (i = 0; i < cellHoArray.length; i++) {
					%>
			        <input type="checkbox" class="cb-element" name="<%= cellHoNameArray[i].toString()%>" id="<%= cellHoNameArray[i].toString()%>" checked="checked"/> <%= cellHoArray[i].toString()%>
			        <% } %>
	            </td>
	        </tr>		
		</table>
	<br/>
	<div  style="overflow: auto;">
<display:table name="${vRpHrCellHoDetails}" id="vRpHrCellHoDetail" requestURI="" pagesize="100" class="simple2" export="true">
	    <display:column property="day" format="{0,date,dd/MM/yyyy}" title="Ngày"/>
	    <display:column title="Giờ">
	    	${vRpHrCellHoDetail.hour}:00
	    </display:column>
	    <display:column property="bscid" title="BSC" headerClass="hide" class="hide"/>
		<display:column title="BSC" media="html">
	    	<a href="${pageContext.request.contextPath}/report/radio/bsc-ho/hr/details.htm?bscid=${vRpHrCellHoDetail.bscid}&endDate=${endDate}">${vRpHrCellHoDetail.bscid}</a>
	    </display:column>
	    <display:column property="cellid" title="CELL"/>
	    <display:column property ="hoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" title="HO_ATT" />
	    <display:column property="hoSucr" titleKey="HO_SUCR (%)" class="hide" headerClass="hide"/>
	    <display:column title="HO_SUCR (%)" media="html" class="HO_SUCR">
			<a href="${pageContext.request.contextPath}/report/radio/cell/ho/hr.htm?bscid=${vRpHrCellHoDetail.bscid}&cellid=${vRpHrCellHoDetail.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrCellHoDetail.day}"/>">${vRpHrCellHoDetail.hoSucr}</a>
	    </display:column>
	    <display:column property ="intraHoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="INTRA_HO_ATT"/>
	    <display:column property="intraHoSucr" titleKey="INTRA_HO_SUCR(%)"/>
	    <display:column property ="ogHoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" title="OG_HO_ATT"/>
	    <display:column property="ogHoSucr" titleKey="OG_HO_SUCR(%)"/>
	    <display:column property="inHoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" title="IN_HO_ATT"/>
	    <display:column property ="inHoSucr" titleKey="IN_HO_SUCR(%)"/>
	    <display:column property="hoRev" title="HO REVERSION(%)"/>
	    <display:column property ="hoLost" title="HO LOST(%)"/>
	</display:table>
</div>
	<br/>
	<div id="inHoSucrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="ogHoSucrChart" style="width: 1000px; margin: 1em auto"></div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
${inHoSucrChart}
${ogHoSucrChart}

<script type="text/javascript">
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
		
		$( '.checkAll' ).live( 'change', function() {
			$( '.cb-element' ).attr( 'checked', $( this ).is( ':checked' ) ? 'checked' : '' );
			$( this ).next().text( $( this ).is( ':checked' ) ? 'Uncheck All' : 'Check All' );
			if($( this ).is( ':checked' )){
				for (var i=6;i<=13;i++)
				{
					$('#vRpHrCellHoDetail td:nth-child('+i+'),#vRpHrCellHoDetail th:nth-child('+i+')').show();
				}
			} else {
				for (var i=6;i<=13;i++)
				{
					$('#vRpHrCellHoDetail td:nth-child('+i+'),#vRpHrCellHoDetail th:nth-child('+i+')').hide();
				}
			}
		});

		$( '.cb-element' ).live( 'change', function() {
			$( '.cb-element' ).length == $( '.cb-element:checked' ).length ? $( '.checkAll' ).attr( 'checked', 'checked' ).next().text( 'Uncheck All' ) : $( '.checkAll' ).attr( 'checked', '' ).next().text( 'Check All' );

		});

		${checkColumns}
		
		var cacheCell = {}, lastXhrCell;
		$( "#cellid" ).autocomplete({
			minLength: 3,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cacheCell ) {
					response( cacheCell[ term ] );
					return;
				}

				lastXhrCell = $.getJSON( "${pageContext.request.contextPath}/ajax/getCell.htm", request, function( data, status, xhr ) {
					cacheCell[ term ] = data;
					if ( xhr === lastXhrCell ) {
						response( data );
					}
				});
			}
		});
		
		${highlight}
	});
</script>
