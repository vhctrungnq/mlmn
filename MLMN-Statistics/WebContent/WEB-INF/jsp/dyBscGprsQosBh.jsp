<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>bsc daily report</title>
<content tag="heading">GPRS BSC QOS ${bscid} REPORT</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-qos/hr/detail.htm?bscid=${bscid}&day=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-qos/dy/detail.htm?bscid=${bscid}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-qos/wk/detail.htm?bscid=${bscid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-qos/mn/detail.htm?bscid=${bscid}"><span>Báo cáo tháng</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-qos/dy/bh.htm?bscid=${bscid}&endDate=${endDate}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-qos/wk/bh.htm?bscid=${bscid}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-qos/mn/bh.htm?bscid=${bscid}"><span>Báo cáo BH tháng</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="bh.htm" name="frmSample" onSubmit="return ValidateForm()">
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
<display:table name="${vRpDyBscGprsQosBh}" id = "vRpDyBscGprsQosBh" requestURI="" pagesize="100" class="simple2" export="true">
		<display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY"/>
		<display:column titleKey="Busy Hour">
		   	${vRpDyBscGprsQosBh.bh}:00
		</display:column> 
		<display:column property="region" titleKey="TT"/>
	    <display:column property="bscid" titleKey="BSC" class="hide" headerClass="hide"/>
	    <display:column titleKey="BSC" media="html">
    	<a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-qos/hr/detail.htm?bscid=${vRpDyBscGprsQosBh.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyBscGprsQosBh.day}"/>">${vRpDyBscGprsQosBh.bscid}</a>&nbsp;
    	</display:column>   
	    <display:column property="bhDlTbfReq" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="BH_DL_TBF_REQ"/>
	    <display:column property ="bhDlTbfSucc" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="BH_DL_TBF_SUCC" />
	    <display:column property ="bhDlTbfSucr" titleKey="BH_DL_TBF_SUCR" />
	    <display:column property="bhUlTbfReq" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="BH_UL_TBF_REQ" />
	    <display:column property ="bhUlTbfSucc" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="BH_UL_TBF_SUCC" />
	    <display:column property ="bhUlTbfSucr" titleKey="BH_UL_TBF_SUCR" />
	    <display:column property="bhGdlTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="BH_GDL_TRAF"/> 
	    <display:column property="bhGulTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="BH_GUL_TRAF"/>
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
				for (var i=6;i<=15;i++)
				{
					$('#vRpDyBscGprsQosBh td:nth-child('+i+'),#vRpDyBscGprsQosBh th:nth-child('+i+')').show();
				}
			} else {
				for (var i=6;i<=15;i++)
				{
					$('#vRpDyBscGprsQosBh td:nth-child('+i+'),#vRpDyBscGprsQosBh th:nth-child('+i+')').hide();
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