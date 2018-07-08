<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>bsc monthly report</title>
<content tag="heading">GPRS-QOS BSC ${bscid} REPORT</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-qos/hr/detail.htm?bscid=${bscid}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-qos/dy/detail.htm?bscid=${bscid}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-qos/wk/detail.htm?bscid=${bscid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-qos/mn/detail.htm?bscid=${bscid}&endMonth=${endMonth}&endYear=${endYear}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-qos/dy/bh.htm?bscid=${bscid}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-qos/wk/bh.htm?bscid=${bscid}"><span>Báo cáo BH tuần</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-qos/mn/bh.htm?bscid=${bscid}&endMonth=${endMonth}&endYear=${endYear}"><span>Báo cáo BH tháng</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="bh.htm" name = "frmSample" onSubmit = "return ValidateYear()">
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
String[] cellHoArray = {"BH_DL_TBF_REQ","BH_DL_TBF_SUCC","BH_DL_TBF_SUCR","BH_UL_TBF_REQ","BH_UL_TBF_SUCC","BH_UL_TBF_SUCR","BH_GDL_TRAF","BH_GUL_TRAF","BH_EDL_TRAF","BH_EUL_TRAF"};
String[] cellHoNameArray = {"bhDlTbfReq","bhDlTbfSucc","bhDlTbfSucr","bhUlTbfReq","bhUlTbfSucc","bhUlTbfSucr","bhGdlTraf","bhGulTraf","bhEdlTraf","bhEulTraf"};
int i;
for (i = 0; i < cellHoArray.length; i++) {
%>
        <input type="checkbox" class="cb-element" name="<%= cellHoNameArray[i].toString()%>" id="<%= cellHoNameArray[i].toString()%>" checked="checked"/> <%= cellHoArray[i].toString()%>
        <% } %>
            </td>
        </tr> 
</table>
	<div  id="doublescroll">
<display:table name="${vRpMnBscGprsQosBh}" id="vRpMnBscGprsQosBh" requestURI="" pagesize="100" class="simple2" export="true">
		<display:column property="region" titleKey="TT"/>
		<display:column property ="year" titleKey="YEAR" />
	    <display:column property ="month" titleKey="MONTH" />
	    <display:column property="bscid" titleKey="BSC" headerClass="hide" class="hide"/>
	    <display:column titleKey="BSC" media="html">
	    	<a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-qos/mn/detail.htm?bscid=${vRpMnBscGprsQosBh.bscid}&endMonth=${vRpMnBscGprsQosBh.month}&endYear=${vRpMnBscGprsQosBh.year}">${vRpMnBscGprsQosBh.bscid}</a>&nbsp;
	    </display:column>
	    <display:column property="bhDlTbfReq" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="BH_DL_TBF_REQ"/>
	    <display:column property ="bhDlTbfSucc" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="BH_DL_TBF_SUCC" />
	    <display:column property ="bhDlTbfSucr" titleKey="BH_DL_TBF_SUCR" />
	    <display:column property="bhUlTbfReq" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="BH_UL_TBF_REQ" />
	    <display:column property ="bhUlTbfSucc" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="BH_UL_TBF_SUCC" />
	    <display:column property ="bhUlTbfSucr" titleKey="BH_UL_TBF_SUCR" />
	    <display:column property="bhGdlTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="BH_GDL_TRAF"/> 
	    <display:column property="bhGulTraf"  decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="BH_GUL_TRAF"/>
	    <display:column property="bhEdlTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="BH_EDL_TRAF"/>
	    <display:column property ="bhEulTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="BH_EUL_TRAF" />
	</display:table>
</div>
	<br/>
	<div id="dlTbfSucrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="ulTbfSucrChart" style="width: 1000px; margin: 1em auto"></div>
	</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
${dlTbfSucrChart}
${ulTbfSucrChart}

<script type="text/javascript">
function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
	} 
function CalcKeyCode(aChar) {
	   var character = aChar.substring(0,1);
	   var code = aChar.charCodeAt(0);
	   return code;
	 }

function checkNumber(val) {
	var strPass = val.value;
	var strLength = strPass.length;
	for(var i=1; i<= strLength;i++){
	  var lchar = val.value.charAt((strLength) - i);
	  var cCode = CalcKeyCode(lchar);

	  if (cCode < 48 || cCode > 57 ) {
	    var myNumber = val.value.substring(0, (strLength)-i);
	    val.value = myNumber;
	  }
	}
	  var sub = document.getElementById("submit");
		sub.focus();
	  return false;
	}
	$(function() {
		$( '.checkAll' ).live( 'change', function() {
			$( '.cb-element' ).attr( 'checked', $( this ).is( ':checked' ) ? 'checked' : '' );
			$( this ).next().text( $( this ).is( ':checked' ) ? 'Uncheck All' : 'Check All' );
			if($( this ).is( ':checked' )){
				for (var i=5;i<=14;i++)
				{
					$('#vRpMnBscGprsQosBh td:nth-child('+i+'),#vRpMnBscGprsQosBh th:nth-child('+i+')').show();
				}
			} else {
				for (var i=5;i<=14;i++)
				{
					$('#vRpMnBscGprsQosBh td:nth-child('+i+'),#vRpMnBscGprsQosBh th:nth-child('+i+')').hide();
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