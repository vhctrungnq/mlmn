<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>location gprs cs monthly report</title>
<content tag="heading">REPORT GPRS-CS REGION ${location}</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="#"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-cs/dy/details.htm?location=${location}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-cs/wk/details.htm?location=${location}"><span>Báo cáo tuần</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/location-gprs-cs/mn/details.htm?location=${location}&endMonth=${endMonth}&endYear=${endYear}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-cs/dy/bhDetails.htm?location=${location}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-cs/wk/bhDetails.htm?location=${location}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-cs/mn/bhDetails.htm?location=${location}&endMonth=${endMonth}&endYear=${endYear}"><span>Báo cáo BH tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	  <form method="get" action="details.htm" onSubmit="return ValidateFormYear()">
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
			        <select name="location">
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
	            	&nbsp;Từ tháng <select name="startMonth" id="startMonth" onchange="xl()">
	            				<c:forEach var="month" items="${monthList}">
						              <c:choose>
						                <c:when test="${month == startMonth}">
						                    <option value="${month}" selected="selected">${month}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${month}">${month}</option>
						                </c:otherwise>
						              </c:choose>
						    </c:forEach>
			               	 </select>&nbsp;
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.startYear);">
	            	&nbsp;Tới tháng <select name="endMonth" id="endMonth" onchange="xl()">
	            				<c:forEach var="month" items="${monthList}">
						              <c:choose>
						                <c:when test="${month == endMonth}">
						                    <option value="${month}" selected="selected">${month}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${month}">${month}</option>
						                </c:otherwise>
						              </c:choose>
						    </c:forEach>
			               	 </select>&nbsp;
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.endYear);">
	            	&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
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
					String[] locationGprsCsArray = {"CSX_LEVEL1","CSX_LEVEL2","CSX_LEVEL3","CSX_LEVEL4","MCSX_LEVEL1","MCSX_LEVEL2","MCSX_LEVEL3","MCSX_LEVEL4","MCSX_LEVEL5","MCSX_LEVEL6","MCSX_LEVEL7","MCSX_LEVEL8","MCSX_LEVEL9","DATALOAD"};
					String[]  locationGprsCsNameArray = {"csxLevel1","csxLevel2","csxLevel3","csxLevel4","mcsxLevel1","mcsxLevel2","mcsxLevel3","mcsxLevel4","mcsxLevel5","mcsxLevel6","mcsxLevel7","mcsxLevel8","mcsxLevel9","dataload"};
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
<display:table name="${vRpMnLocationGprsCsDetails}" id="vRpMnLocationGprsCsDetail" requestURI="" pagesize="100" class="simple2" export="true">
		<display:column property="region" titleKey="TT"/>
	    <display:column property ="month" titleKey="MONTH" />
	    <display:column property ="year" titleKey="YEAR" />
	    <display:column property ="location" titleKey="LOCATION"/> 
	    <display:column property ="csxLevel1" titleKey="CSX_LEVEL1" />
	    <display:column property ="csxLevel2" titleKey="CSX_LEVEL2" />
	    <display:column property ="csxLevel3" titleKey="CSX_LEVEL3" />
	    <display:column property ="csxLevel4" titleKey="CSX_LEVEL4"/>
	    <display:column property ="mcsxLevel1" titleKey="MCSX_LEVEL1" />
	    <display:column property ="mcsxLevel2" titleKey="MCSX_LEVEL2" />
	    <display:column property ="mcsxLevel3" titleKey="MCSX_LEVEL3" />
	    <display:column property ="mcsxLevel4" titleKey="MCSX_LEVEL4" />
	    <display:column property ="mcsxLevel5" titleKey="MCSX_LEVEL5" />
	    <display:column property ="mcsxLevel6" titleKey="MCSX_LEVEL6"/>
	    <display:column property ="mcsxLevel7" titleKey="MCSX_LEVEL7" />
	    <display:column property ="mcsxLevel8" titleKey="MCSX_LEVEL8" />
	    <display:column property ="mcsxLevel9" titleKey="MCSX_LEVEL9" />
	    <display:column property="dataload" titleKey="DATALOAD" />
	</display:table>
</div>
	
	<div id="container" style="width: 1000px; margin: 1em auto;"></div>
</div>

${chart}

<script type="text/javascript">
	$(function() {		
		$( '.checkAll' ).live( 'change', function() {
			$( '.cb-element' ).attr( 'checked', $( this ).is( ':checked' ) ? 'checked' : '' );
			$( this ).next().text( $( this ).is( ':checked' ) ? 'Uncheck All' : 'Check All' );
			if($( this ).is( ':checked' )){
				for (var i=5;i<=18;i++)
				{
					$('#vRpMnLocationGprsCsDetail td:nth-child('+i+'),#vRpMnLocationGprsCsDetail th:nth-child('+i+')').show();
				}
			} else {
				for (var i=5;i<=18;i++)
				{
					$('#vRpMnLocationGprsCsDetail td:nth-child('+i+'),#vRpMnLocationGprsCsDetail th:nth-child('+i+')').hide();
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

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
<script type="text/javascript">
	function CalcKeyCode(aChar) {
	  var character = aChar.substring(0,1);
	  var code = aChar.charCodeAt(0);
	  return code;
	}

	function checkNumber(val) {
	  var strPass = val.value;
	  var strLength = strPass.length;
	  for(var i =0;i<strLength+1;i++){
	  var lchar = val.value.charAt((strLength) - i);
	  var cCode = CalcKeyCode(lchar);
	  if (cCode < 48 || cCode > 57 ) {
	    var myNumber = val.value.substring(0, (strLength) - i);
	    val.value = myNumber;
	  }
	  }
	  var sub = document.getElementById("submit");
		sub.focus();
	  return false;
	}
	function xl(){
		var sub = document.getElementById("submit");
		sub.focus();
	} 
	$(function() {
		${highlight}
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