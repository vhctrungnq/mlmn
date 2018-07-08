<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>province tch drop qos</title>
<content tag="heading">PROVINCE TCH DROP QOS REPORT</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/province/tdrop/hr.htm?province=${province}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/province/tdrop/dy.htm?province=${province}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  	  <form method="get" action="dy.htm" name = "frmSample" onSubmit = "return ValidateForm()">
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
	                &nbsp;&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	          	  </form>
	            </td>
	        </tr>		
		</table>
		<br/>
	
		<div style="overflow: auto;">
			<display:table name="${dyProvinceDcrtQos}" id="dyProvinceDcrtQos" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
				<display:column property="region" titleKey="TT" sortable="true"/>
			    <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true"/>	
			    <display:column property="province" titleKey="PROVINCE" sortable="true" class="hide" headerClass="hide"/>    	    
			    <display:column titleKey="PROVINCE" media="html" sortable="true" sortProperty="province">
			    	<a href="${pageContext.request.contextPath}/report/radio/province/tdrop/hr.htm?province=${dyProvinceDcrtQos.province}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyProvinceDcrtQos.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyProvinceDcrtQos.day}"/>">${dyProvinceDcrtQos.province}</a>
			    </display:column>
			    <display:column property ="f1" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Total No. of Dropperd TCH Connections" sortable="true"/>
			    <display:column property ="f17" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL FR Drop" sortable="true"/>
			    <display:column property ="f18" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL HR Drop" sortable="true"/>
			    <display:column property ="f19" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="OL FR Drop" sortable="true"/>
			    <display:column property ="f20" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="OL HR Drop" sortable="true"/>
			    <display:column property ="f2" titleKey="TCH DROP (%)" sortable="true"/>
			    <display:column property="f3" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TCH Erlang Minutes per Drop" sortable="true"/>
			    <display:column property="f4" titleKey="Drop Reason, Low SS DL (%)" sortable="true"/>
			    <display:column property ="f5" titleKey="Drop Reason, Low SS UL (%)" sortable="true"/>
			    <display:column property ="f6" titleKey="Drop Reason, Low SS UL/DL (%)" sortable="true"/>
			    <display:column property="f7" titleKey="Drop Reason, bad Quality DL (%)" sortable="true"/> 
			    <display:column property="f8" titleKey="Drop Reason, Bad Quality UL (%)" sortable="true"/>
			    <display:column property ="f9" titleKey="Drop Reason, Bad Quality UL/DL (%)" sortable="true"/>
			    <display:column property ="f10" titleKey="Drop Reason, Suddenly Lost Connections (%)" sortable="true"/>
			    <display:column property ="f11" titleKey="Drop Reason, Excessive TA (%)" sortable="true"/>
			    <display:column property="f12" titleKey="Drop Reason, FER DL (%)" sortable="true"/>
			    <display:column property="f13" titleKey="Drop Reason, FER UL (%)" sortable="true"/>
			    <display:column property="f14" titleKey="Drop Reason, FER UL/DL (%)" sortable="true"/>
			    <display:column property ="f15" titleKey="Drop Reason, Other (%)" sortable="true"/>
			</display:table>
		</div>

		
		<br>
		
	<div id="f1Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f2Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f3Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f4Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f5Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f6Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f7Chart" style="width: 1000px; margin: 1em auto"></div>
	<br>
	<div id="f8Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f9Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f10Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f11Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f12Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f13Chart" style="width: 1000px; margin: 1em auto"></div>
	<br>
	<div id="f14Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="f15Chart" style="width: 1000px; margin: 1em auto"></div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
${f1Chart}
${f2Chart}
${f3Chart}
${f4Chart}
${f5Chart}
${f6Chart}
${f7Chart}
${f8Chart}
${f9Chart}
${f10Chart}
${f11Chart}
${f12Chart}
${f13Chart}
${f14Chart}
${f15Chart}
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
		var cache = {},
		lastXhr;
		$( "#province" ).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cache ) {
					response( cache[ term ] );
					return;
				}

				lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getProvince.htm", request, function( data, status, xhr ) {
					cache[ term ] = data;
					if ( xhr === lastXhr ) {
						response( data );
					}
				});
			}
		});
	});
</script>
