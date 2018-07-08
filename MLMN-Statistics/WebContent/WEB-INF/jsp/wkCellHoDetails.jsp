<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>cell handover weekly report</title>
<content tag="heading">Báo cáo Handover Cell ${cellid}</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="#"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-ho/dy/details.htm?bscid=${bscid}&cellid=${cellid}"><span>Báo cáo ngày</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell-ho/wk/details.htm?bscid=${bscid}&cellid=${cellid}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-ho/mn/details.htm?bscid=${bscid}&cellid=${cellid}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-ho/dy/bhDetails.htm?bscid=${bscid}&cellid=${cellid}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-ho/wk/bhDetails.htm?bscid=${bscid}&cellid=${cellid}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo BH tuần</span></a></li>
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
				    &nbsp;&nbsp;CELL 
			        <select name="cellid" id="cellid">
						<option  value="">--Select Cell--</option>
				        <c:forEach var="cell" items="${cellList}">
			              <c:choose>
			                <c:when test="${cell.cellid == cellid}">
			                    <option value="${cell.cellid}" selected="selected">${cell.cellid}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${cell.cellid}">${cell.cellid}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
					</select>
	            	&nbsp;Từ tuần <input value="${startWeek}" name="startWeek" id="startWeek" size="2">
					<img alt="calendar" title="Click to choose the start week number" id="chooseStartWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4">
	            	&nbsp;Tới tuần <input value="${endWeek}" name="endWeek" id="endWeek" size="2">
					<img alt="calendar" title="Click to choose the end week number" id="chooseEndWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4">
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
					String[] cellHoArray = {"OG_HO_ATT","OG_HO_SUCR","IN_HO_ATT","IN_HO_SUCR"};
					String[] cellHoNameArray = {"ogHoAtt","ogHoSucr","inHoAtt","inHoSucr"};
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
<display:table name="${vRpWkCellHoDetails}" id="vRpWkCellHoDetail" requestURI="" pagesize="100" class="simple2" export="true">
	    <display:column property ="week" title="Tuần" />
	    <display:column property ="year" title="Năm" />
	    <display:column property="bscid" title="BSCID"/> 
	    <display:column property="cellid" title="CELLID"/>
	    <display:column property ="hoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" title="HO_ATT" />
	    <display:column property ="hoSucr" title="HO_SUCR(%)" class="HO_SUCR"/>
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
	
	<div id="container" style="width: 1000px; margin: 1em auto;"></div>
</div>

${chart}

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
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
		$( '.checkAll' ).live( 'change', function() {
			$( '.cb-element' ).attr( 'checked', $( this ).is( ':checked' ) ? 'checked' : '' );
			$( this ).next().text( $( this ).is( ':checked' ) ? 'Uncheck All' : 'Check All' );
			if($( this ).is( ':checked' )){
				for (var i=5;i<=8;i++)
				{
					$('#vRpWkCellHoDetail td:nth-child('+i+'),#vRpWkCellHoDetail th:nth-child('+i+')').show();
				}
			} else {
				for (var i=5;i<=8;i++)
				{
					$('#vRpWkCellHoDetail td:nth-child('+i+'),#vRpWkCellHoDetail th:nth-child('+i+')').hide();
				}
			}
		});

		$( '.cb-element' ).live( 'change', function() {
			$( '.cb-element' ).length == $( '.cb-element:checked' ).length ? $( '.checkAll' ).attr( 'checked', 'checked' ).next().text( 'Uncheck All' ) : $( '.checkAll' ).attr( 'checked', '' ).next().text( 'Check All' );

		});

		${checkColumns}

		${checkSeries}
		
		$("select#bscid").change(function(){
			$.getJSON("${pageContext.request.contextPath}/ajax/getCellOfBsc.htm",{bscid: $(this).val()}, function(j){
				var options = '<option  value="">--Select Cell--</option>';
				for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].cellid + '">' + j[i].cellid + '</option>';
				}
				$("#cellid").html(options);
				$('#cellid option:first').attr('selected', 'selected');
			});
		});
		
		${highlight}
	});
</script>
