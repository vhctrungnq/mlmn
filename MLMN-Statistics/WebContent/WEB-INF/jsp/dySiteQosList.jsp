<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title><fmt:message key="sidebar.site.qos"/></title>
<content tag="heading">LIST SITE QOS DAY ${day}</content>
<ul class="ui-tabs-nav">
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/site-qos/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/mn/list.htm"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm" name="frmSample" onSubmit="return ValidateForm()">
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
			  		PROVINCE 
			        <select name="province" onchange="xl()">
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
	                &nbsp;&nbsp;Ngày <input value="${day}" name="day" id="day" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
		<div  style="overflow: auto;">
<display:table name="${vRpDySiteQos}" id="vRpDySiteQos" requestURI="" pagesize="100" class="simple2" export="true">
				<display:column property="region" titleKey="TT"/>
			    <display:column property ="province" titleKey="PROVINCE" />
			    <display:column property="bscid" titleKey="BSCID" headerClass="hide" class="hide"/>
			    <display:column titleKey="BSC" media="html">
			    	<a href="${pageContext.request.contextPath}/report/radio/bsc/hr/detail.htm?bscid=${vRpDySiteQos.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDySiteQos.day}"/>">${vRpDySiteQos.bscid}</a>
			    </display:column> 
			    <display:column property="siteid" titleKey="SITEID" headerClass="hide" class="hide"/>
			    <display:column titleKey="SITEID" media="html">
			    	<a href="${pageContext.request.contextPath}/report/radio/site-qos/hr/details.htm?bscid=${vRpDySiteQos.bscid}&siteid=${vRpDySiteQos.siteid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDySiteQos.day}"/>">${vRpDySiteQos.siteid}</a>
			    </display:column>
			    <display:column property ="tDef" titleKey="T_DEF" />
			    <display:column property ="tAvail" titleKey="T_AVAIL" />
			    <display:column property="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF" />
			    <display:column property="haftratePercent" titleKey="HAFT RATE (%)" />
			    <display:column property="tAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_ATTS" />
			    <display:column property="tDrpr" titleKey="T_DRPR" class="T_DRPR"/>
			    <display:column property="tBlkr" titleKey="T_BLKR" class="T_BLKR"/>
			    <display:column property="tHoblkr" titleKey="T_HOBLKR"/>
			    <display:column property="cssr" titleKey="CSSR"/> 
			    <display:column property ="sDef" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_DEF" />
			    <display:column property="sAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_ATTS" />
			    <display:column property="sDrpr" titleKey="S_DRPR" class="S_DRPR"/>
			    <display:column property ="sBlkr" titleKey="S_BLKR" />
			    <display:column property ="dataload" titleKey="DB LOAD(%)" />
		</display:table>
</div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript">
function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
}
	$(function() {
		$( "#day" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
		
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
