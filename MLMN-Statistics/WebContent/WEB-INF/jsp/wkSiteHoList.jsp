<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>site handover list</title>
<content tag="heading">LIST SITE HANDOVER WEEK ${week}/${year}</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/site-ho/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/site-ho/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/site-ho/mn/list.htm"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm">
					Trung tâm 
			  			<select name="region" id="region">
			              <c:choose>
			                <c:when test="${region == 'TT2'}">
								<option value=""> Tất cả </option>
								<option value="TT2" selected="selected"> TT2 </option>
			                    <option value="TT6"> TT6 </option>
			                </c:when>
			                <c:when test="${region == 'TT6'}">
								<option value=""> Tất cả </option>
								<option value="TT2"> TT2 </option>
			                    <option value="TT6" selected="selected"> TT6 </option>
			                </c:when>
			                <c:otherwise>
								<option value="" selected="selected"> Tất cả </option>
								<option value="TT2"> TT2 </option>
			                    <option value="TT6"> TT6 </option>
			                </c:otherwise>
			              </c:choose>
			            </select>
			        PROVINCE 
			        <select name="province">
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
			        &nbsp;&nbsp;BSC <input value="${bscid}" name="bscid" id="bscid" size="10" maxlength="50">
			        &nbsp;&nbsp;SITE <input value="${siteid}" name="siteid" id="siteid" size="10" maxlength="50">
	                Tuần <input value="${week}" name="week" id="week" size="2" maxlength="2"/>
					<img alt="calendar" title="Click to choose the week number" id="chooseWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	                Năm <input value="${year}" name="year" id="year" size="4" maxlength="4"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
		<div  style="overflow: auto;">
<display:table name="${vRpWkSiteHo}" id="vRpWkSiteHo" requestURI="" pagesize="100" class="simple2" export="true">
			    <display:column property ="region" titleKey="REGION"/>
			    <display:column property ="province" titleKey="PROVINCE" />
			    <display:column property="bscid" titleKey="BSCID" headerClass="hide" class="hide"/>
			    <display:column titleKey="BSC">
			    	<a href="${pageContext.request.contextPath}/report/radio/bsc-ho/wk/details.htm?bscid=${vRpWkSiteHo.bscid}&endWeek=${vRpWkSiteHo.week}&endYear=${vRpWkSiteHo.year}">${vRpWkSiteHo.siteid}</a>
			    </display:column>   
			    <display:column titleKey="SITEID">
			    	<a href="details.htm?bscid=${vRpWkSiteHo.bscid}&siteid=${vRpWkSiteHo.siteid}&endWeek=${vRpWkSiteHo.week}&endYear=${vRpWkSiteHo.year}">${vRpWkSiteHo.siteid}</a>
			    </display:column>  
			    <display:column property="sitename" titleKey="SITE NAME" headerClass="hide" class="hide"/>      
			    <display:column property ="ogHoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="OG_HO_ATT" />
			    <display:column property="ogHoSuc" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="OG_HO_SUC" />
			    <display:column property="ogHoSucr" titleKey="OG_HO_SUCR(%)" />
			    <display:column property="inHoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="IN_HO_ATT"/>
			    <display:column property ="inHoSuc" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="IN_HO_SUC" />
			    <display:column property ="inHoSucr" titleKey="IN_HO_SUCR(%)" />
		</display:table>
</div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
    Calendar.setup({
        inputField		:	"week",	// id of the input field
        ifFormat		:	"%W",   	// format of the input field
        button			:   "chooseWeek",  	// trigger for the calendar (button ID)
        singleClick		:   false					// double-click mode
    });
    
	$(function() {
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
