<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>bsc handover monthly report</title>
<content tag="heading">REPORT HANDOVER BSC ${bscid}</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-ho/hr/details.htm?bscid=${bscid}&day=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-ho/dy/details.htm?bscid=${bscid}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-ho/wk/details.htm?bscid=${bscid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-ho/mn/details.htm?bscid=${bscid}&endMonth=${endMonth}&endYear=${endYear}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-ho/dy/bhDetails.htm?bscid=${bscid}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-ho/wk/bhDetails.htm?bscid=${bscid}"><span>Báo cáo BH tuần</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/bsc-ho/mn/bhDetails.htm?bscid=${bscid}&endMonth=${endMonth}&endYear=${endYear}"><span>Báo cáo BH tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	  <form method="get" action="bhDetails.htm">
		<table width="100%" class="form">
			<tr>
				<td align="left">
					Trung tâm 
			  			<select name="region" id="region">
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
					BSC 
					<select name="bscid" id="bscid" style="width: 163px">
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
	            	&nbsp;&nbsp;Từ tháng <input value="${startMonth}" name="startMonth" id="startMonth" size="2" maxlength="2">
	            	&nbsp;&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4">
	            	&nbsp;&nbsp;Tới tháng <input value="${endMonth}" name="endMonth" id="endMonth" size="2" maxlength="2">
	            	&nbsp;&nbsp;Năm<input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4">
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
					String[] bscHoArray = {"OG_HO_ATT","OG_HO_SUC","OG_HO_SUCR","IN_HO_ATT","IN_HO_SUC","IN_HO_SUCR"};
					String[] bscHoNameArray = {"bhOgHoAtt","bhOgHoSuc","bhOgHoSucr","bhInHoAtt","bhInHoSuc","bhInHoSucr"};
					int i;
					for (i = 0; i < bscHoArray.length; i++) {
				%>
		        <input type="checkbox" class="cb-element" name="<%= bscHoNameArray[i].toString()%>" id="<%= bscHoNameArray[i].toString()%>" checked="checked"/> <%= bscHoArray[i].toString()%>
		        <% } %>
            </td>
        </tr>		
	</table>
	<br/>
	<div  style="overflow: auto;">
<display:table name="${vRpMnBscHoBhDetails}" id="vRpMnBscHoBhDetail" requestURI="" pagesize="100" class="simple2" export="true">
		<display:column property="region" titleKey="CENTER"/>
	    <display:column property ="month" titleKey="MONTH" />
	    <display:column property ="year" titleKey="YEAR" />
	    <display:column property="bscid" titleKey="BSCID"/> 
	    <display:column property ="bhOgHoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="OG_HO_ATT" />
	    <display:column property="bhOgHoSuc" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="OG_HO_SUC" />
	    <display:column property="bhOgHoSucr" titleKey="OG_HO_SUCR(%)" />
	    <display:column property="bhInHoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="IN_HO_ATT"/>
	    <display:column property ="bhInHoSuc" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="IN_HO_SUC" />
	    <display:column property ="bhInHoSucr" titleKey="IN_HO_SUCR(%)" />
	</display:table>
</div>
	
	<div id="container" style="width: 1000px; margin: 1em auto"></div>
</div>

${chart}

<script type="text/javascript">
	$(function() {
		$( '.checkAll' ).live( 'change', function() {
			$( '.cb-element' ).attr( 'checked', $( this ).is( ':checked' ) ? 'checked' : '' );
			$( this ).next().text( $( this ).is( ':checked' ) ? 'Uncheck All' : 'Check All' );
			if($( this ).is( ':checked' )){
				for (var i=5;i<=10;i++)
				{
					$('#vRpMnBscHoBhDetail td:nth-child('+i+'),#vRpMnBscHoBhDetail th:nth-child('+i+')').show();
				}
			} else {
				for (var i=5;i<=10;i++)
				{
					$('#vRpMnBscHoBhDetail td:nth-child('+i+'),#vRpMnBscHoBhDetail th:nth-child('+i+')').hide();
				}
			}
		});

		$( '.cb-element' ).live( 'change', function() {
			$( '.cb-element' ).length == $( '.cb-element:checked' ).length ? $( '.checkAll' ).attr( 'checked', 'checked' ).next().text( 'Uncheck All' ) : $( '.checkAll' ).attr( 'checked', '' ).next().text( 'Check All' );

		});

		${checkColumns}

		${checkSeries}
		
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

				lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getBsc.htm", request, function( data, status, xhr ) {
					cache[ term ] = data;
					if ( xhr === lastXhr ) {
						response( data );
					}
				});
			}
		});
	});
</script>
