<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Site hourly report</title>
<content tag="heading">REPORT HANDOVER SITE ${siteid}</content>
<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/site-ho/hr/details.htm?bscid=${bscid}&siteid=${siteid}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-ho/dy/details.htm?bscid=${bscid}&siteid=${siteid}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-ho/wk/details.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-ho/mn/details.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-ho/dy/bhDetails.htm?bscid=${bscid}&siteid=${siteid}&endDate=${endDate}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-ho/wk/bhDetails.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-ho/mn/bhDetails.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo BH tháng</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="details.htm" name = "frmSample" onSubmit = "return ValidateForm()">
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
					<input type="hidden" name="bscid" value="${bscid}">
			        SITE <input name="siteid" id="siteid" value="${siteid}" size="10">
	            	&nbsp;Từ <select name="startHour" id="startHour" onchange="xl()">
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
			<td align="left">
				<input type="checkbox" class="checkAll" checked="checked" /><b>Check all</b>
				<% 
					String[] siteHoArray = {"OG_HO_ATT","OG_HO_SUC","OG_HO_SUCR","IN_HO_ATT","IN_HO_SUC","IN_HO_SUCR"};
					String[] siteHoNameArray = {"ogHoAtt","ogHoSuc","ogHoSucr","inHoAtt","inHoSuc","inHoSucr"};
					int i;
					for (i = 0; i < siteHoArray.length; i++) {
				%>
		        <input type="checkbox" class="cb-element" name="<%= siteHoNameArray[i].toString()%>" id="<%= siteHoNameArray[i].toString()%>" checked="checked"/> <%= siteHoArray[i].toString()%>
		        <% } %>
            </td>
        </tr>		
	</table>
	<br/>
	<div  style="overflow: auto;">
<display:table name="${vRpHrSiteHoDetails}" id="vRpHrSiteHoDetail" requestURI="" pagesize="100" class="simple2" export="true">
				<display:column property="region" titleKey="TT"/>
	    <display:column titleKey="HOUR" >
	    	${vRpHrSiteHoDetail.hour}:00
	    </display:column>
	    <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY"/>
	    <display:column property="bscid" titleKey="BSCID" headerClass="hide" class="hide"/>
	    <display:column titleKey="BSC" media="html">
	    	<a href="${pageContext.request.contextPath}/report/radio/bsc-ho/hr/details.htm?bscid=${vRpHrSiteHoDetail.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrSiteHoDetail.day}"/>">${vRpHrSiteHoDetail.bscid}</a>
	    </display:column>  
	    <display:column property="siteid" titleKey="SITEID"/> 
	    <display:column property ="ogHoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="OG_HO_ATT" />
	    <display:column property="ogHoSuc" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="OG_HO_SUC" />
	    <display:column property="ogHoSucr" titleKey="OG_HO_SUCR(%)" />
	    <display:column property="inHoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="IN_HO_ATT"/>
	    <display:column property ="inHoSuc" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="IN_HO_SUC" />
	    <display:column property ="inHoSucr" titleKey="IN_HO_SUCR(%)" />
	</display:table>
</div>
	
	<div id="container" style="width: 1000px; margin: 1em auto;"></div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
${chart}

<script type="text/javascript">
function xl(){
	var sub= document.getElementById("submit");
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
		
		$( '.checkAll' ).live( 'change', function() {
			$( '.cb-element' ).attr( 'checked', $( this ).is( ':checked' ) ? 'checked' : '' );
			$( this ).next().text( $( this ).is( ':checked' ) ? 'Uncheck All' : 'Check All' );
			if($( this ).is( ':checked' )){
				for (var i=7;i<=12;i++)
				{
					$('#vRpHrSiteHoDetail td:nth-child('+i+'),#vRpHrSiteHoDetail th:nth-child('+i+')').show();
				}
			} else {
				for (var i=7;i<=12;i++)
				{
					$('#vRpHrSiteHoDetail td:nth-child('+i+'),#vRpHrSiteHoDetail th:nth-child('+i+')').hide();
				}
			}
		});

		$( '.cb-element' ).live( 'change', function() {
			$( '.cb-element' ).length == $( '.cb-element:checked' ).length ? $( '.checkAll' ).attr( 'checked', 'checked' ).next().text( 'Uncheck All' ) : $( '.checkAll' ).attr( 'checked', '' ).next().text( 'Check All' );

		});

		${checkColumns}

		${checkSeries}
		
		var cacheSite = {},
		lastXhrSite;
		$( "#siteid" ).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cacheSite ) {
					response( cacheSite[ term ] );
					return;
				}

				lastXhrSite = $.getJSON( "${pageContext.request.contextPath}/ajax/getSite.htm", request, function( data, status, xhr ) {
					cacheSite[ term ] = data;
					if ( xhr === lastXhrSite ) {
						response( data );
					}
				});
			}
		});
	});
</script>
