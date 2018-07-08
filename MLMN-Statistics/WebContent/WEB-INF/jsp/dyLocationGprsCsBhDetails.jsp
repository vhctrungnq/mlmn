<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title><fmt:message key="sidebar.location.gprs-cs"/></title>
<content tag="heading"><fmt:message key="sidebar.location.gprs-cs"/> ${location}</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-cs/hr/details.htm?location=${location}&day=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-cs/dy/details.htm?location=${location}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-cs/wk/details.htm?location=${location}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-cs/mn/details.htm?location=${location}"><span>Báo cáo tháng</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/location-gprs-cs/dy/bhDetails.htm?location=${location}&endDate=${endDate}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-cs/wk/bhDetails.htm?location=${location}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-cs/mn/bhDetails.htm?location=${location}"><span>Báo cáo BH tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	  <form method="get" action="bhDetails.htm" name="frmSample" onSubmit="return ValidateForm()">
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
					LOCATION 
			        <select name="location" onchange="xl()">
			        	<option value="">Tất cả</option>
				        <c:forEach var="prv" items="${locationList}">
			              <c:choose>
			                <c:when test="${prv.location == location}">
			                    <option value="${prv.location}" selected="selected">${prv.location}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${prv.location}">${prv.location}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
				    </select>
					<!-- location <input value="${location}" name="location" id="location" size="10" maxlength="50"> -->
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
			<td align="left">
				<input type="checkbox" class="checkAll" checked="checked" /><b>Check all</b>
				<% 
					String[] locationGprsCsArray = {"CSX_LEVEL1","CSX_LEVEL2","CSX_LEVEL3","CSX_LEVEL4","MCSX_LEVEL1","MCSX_LEVEL2","MCSX_LEVEL3","MCSX_LEVEL4","MCSX_LEVEL5","MCSX_LEVEL6","MCSX_LEVEL7","MCSX_LEVEL8","MCSX_LEVEL9"};
					String[]  locationGprsCsNameArray = {"bhCsxLevel1","bhCsxLevel2","bhCsxLevel3","bhCsxLevel4","bhMcsxLevel1","bhMcsxLevel2","bhMcsxLevel3","bhMcsxLevel4","bhMcsxLevel5","bhMcsxLevel6","bhMcsxLevel7","bhMcsxLevel8","bhMcsxLevel9"};
					int i;
					for (i = 0; i < locationGprsCsArray.length; i++) {
				%>
		        <input type="checkbox" class="cb-element" name="<%= locationGprsCsNameArray[i].toString()%>" id="<%= locationGprsCsNameArray[i].toString()%>" checked="checked"/> <%= locationGprsCsArray[i].toString()%>
		        <% } %>
            </td>
        </tr>		
	</table>
	<br/>
	<div  id="doublescroll">
<display:table name="${vRpDyLocationGprsCsBhDetails}" id="vRpDyLocationGprsCsBhDetail" requestURI="" pagesize="100" class="simple2" export="true">
				<display:column property="region" titleKey="TT"/>
	    <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY"/>
	    <display:column property ="bh" titleKey="BH"/> 
	    <display:column property ="location" titleKey="LOCATION"/> 
	    <display:column property ="bhCsxLevel1" titleKey="CSX_LEVEL1" />
	    <display:column property ="bhCsxLevel2" titleKey="CSX_LEVEL2" />
	    <display:column property ="bhCsxLevel3" titleKey="CSX_LEVEL3" />
	    <display:column property ="bhCsxLevel4" titleKey="CSX_LEVEL4"/>
	    <display:column property ="bhMcsxLevel1" titleKey="MCSX_LEVEL1" />
	    <display:column property ="bhMcsxLevel2" titleKey="MCSX_LEVEL2" />
	    <display:column property ="bhMcsxLevel3" titleKey="MCSX_LEVEL3" />
	    <display:column property ="bhMcsxLevel4" titleKey="MCSX_LEVEL4" />
	    <display:column property ="bhMcsxLevel5" titleKey="MCSX_LEVEL5" />
	    <display:column property ="bhMcsxLevel6" titleKey="MCSX_LEVEL6"/>
	    <display:column property ="bhMcsxLevel7" titleKey="MCSX_LEVEL7" />
	    <display:column property ="bhMcsxLevel8" titleKey="MCSX_LEVEL8" />
	    <display:column property ="bhMcsxLevel9" titleKey="MCSX_LEVEL9" />
	</display:table>
</div>
	
	<div id="container" style="width: 1000px; margin: 1em auto;"></div>
</div>

${chart}

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
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
				for (var i=5;i<=17;i++)
				{
					$('#vRpDyLocationGprsCsBhDetail td:nth-child('+i+'),#vRpDyLocationGprsCsBhDetail th:nth-child('+i+')').show();
				}
			} else {
				for (var i=5;i<=17;i++)
				{
					$('#vRpDyLocationGprsCsBhDetail td:nth-child('+i+'),#vRpDyLocationGprsCsBhDetail th:nth-child('+i+')').hide();
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