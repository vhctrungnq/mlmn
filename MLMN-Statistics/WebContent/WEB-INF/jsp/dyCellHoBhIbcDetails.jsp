<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>cell ibc handover daily report</title>
<content tag="heading">STATION IBC HANDOVER REPORT ${cellid}</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/cell-ho/hr/details.htm?bscid=${bscid}&cellid=${cellid}&day=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/cell-ho/dy/details.htm?bscid=${bscid}&cellid=${cellid}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/cell-ho/wk/details.htm?bscid=${bscid}&cellid=${cellid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/cell-ho/mn/details.htm?bscid=${bscid}&cellid=${cellid}"><span>Báo cáo tháng</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/ibc/cell-ho/dy/bhDetails.htm?bscid=${bscid}&cellid=${cellid}&endDate=${endDate}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/cell-ho/wk/bhDetails.htm?bscid=${bscid}&cellid=${cellid}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/cell-ho/mn/bhDetails.htm?bscid=${bscid}&cellid=${cellid}"><span>Báo cáo BH tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	  <form method="get" action="bhDetails.htm">
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
	            	Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	            	&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
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
					String[] cellHoNameArray = {"bhOgHoAtt","bhOgHoSucr","bhInHoAtt","bhInHoSucr"};
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
<display:table name="${vRpDyCellHoBhIbcDetails}" id="vRpDyCellHoBhIbcDetail" requestURI="" pagesize="100" class="simple2" export="true">
	    <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY"/>
	    <display:column titleKey="Busy Hour">
			${vRpDyCellHoBhIbcDetail.bh}:00
		</display:column>
		<display:column property ="bscid" titleKey="BSC" class="hide" headerClass="hide"/>	 
	    <display:column titleKey="BSC" media="html">
	    	<a href="${pageContext.request.contextPath}/report/radio/ibc/bsc-ho/dy/bhDetails.htm?bscid=${vRpDyCellHoBhIbcDetail.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCellHoBhIbcDetail.day}"/>">${vRpDyCellHoBhIbcDetail.bscid}</a>
	    </display:column>
	    <display:column property="cellid" titleKey="CELL" headerClass="hide" class="hide"/>
	    <display:column titleKey="CELL" media="html">
	    	<a href="${pageContext.request.contextPath}/report/radio/ibc/cell-ho/hr/details.htm?bscid=${vRpDyCellHoBhIbcDetail.bscid}&cellid=${vRpDyCellHoBhIbcDetail.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCellHoBhIbcDetail.day}"/>">${vRpDyCellHoBhIbcDetail.cellid}</a>
	    </display:column>
	    <display:column property ="bhOgHoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="OG_HO_ATT" />
	    <display:column property="bhOgHoSucr" titleKey="OG_HO_SUCR(%)" />
	    <display:column property="bhInHoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="IN_HO_ATT"/>
	    <display:column property ="bhInHoSucr" titleKey="IN_HO_SUCR(%)" />
	</display:table>
</div>
	
	<div id="container" style="width: 1000px; margin: 1em auto"></div>
</div>

${chart}

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
		$("#notaccordion").addClass("ui-accordion ui-widget ui-helper-reset ui-accordion-icons")
		.find("table")
			.addClass("ui-accordion-header ui-helper-reset ui-state-active ui-corner-top ui-state-focus")
			.prepend('<span class="ui-icon ui-icon-triangle-1-s"/>')
			.click(function() {
				$(this).toggleClass("ui-state-active ui-corner-top ui-state-focus ui-state-default ui-corner-all")
				.find("> .ui-icon").toggleClass("ui-icon-triangle-1-e ui-icon-triangle-1-s")
				.end().next().toggleClass("ui-icon-triangle-1-e ui-icon-triangle-1-s").toggle();
				return false;
			})
			.next().addClass("ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom").show();
		
		$( '.checkAll' ).live( 'change', function() {
			$( '.cb-element' ).attr( 'checked', $( this ).is( ':checked' ) ? 'checked' : '' );
			$( this ).next().text( $( this ).is( ':checked' ) ? 'Uncheck All' : 'Check All' );
			if($( this ).is( ':checked' )){
				for (var i=6;i<=9;i++)
				{
					$('#vRpDyCellHoBhIbcDetail td:nth-child('+i+'),#vRpDyCellHoBhIbcDetail th:nth-child('+i+')').show();
				}
			} else {
				for (var i=6;i<=9;i++)
				{
					$('#vRpDyCellHoBhIbcDetail td:nth-child('+i+'),#vRpDyCellHoBhIbcDetail th:nth-child('+i+')').hide();
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
	});
</script>
