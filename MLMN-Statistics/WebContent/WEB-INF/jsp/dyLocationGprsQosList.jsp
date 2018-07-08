<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title><fmt:message key="sidebar.location.gprs-qos"/></title>
<content tag="heading"><fmt:message key="sidebar.location.gprs-qos"/></content>
<ul class="ui-tabs-nav">
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/location-gprs-qos/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-qos/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-qos/mn/list.htm"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form" name="frmSample" onSubmit="return ValidateForm()">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm">
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
			        <!-- LOCATION <input value="${location}" name="location" id="location" size="10" maxlength="50"> -->
	                &nbsp;&nbsp;Ngày <input value="${day}" name="day" id="day" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
		<div  style="overflow: auto;">
<display:table name="${vRpDyLocationGprsQos}" id="vRpDyLocationGprsQos" requestURI="" pagesize="100" class="simple2" export="true">
			    <display:column property ="region" titleKey="TT" />
			    <display:column property ="location" titleKey="LOCATION"/> 
			    <display:column property ="dlTbfReq" titleKey="DL_TBF_REQ" />
			    <display:column property ="dlTbfSucr" titleKey="DL_TBF_SUCR" />
			    <display:column property ="ulTbfReq" titleKey="UL_TBF_REQ" />
			    <display:column property ="ulTbfSucr" titleKey="UL_TBF_SUCR"/>
			    <display:column property ="gdlTraf" titleKey="GDL_TRAF" />
			    <display:column property ="gulTraf" titleKey="GUL_TRAF" />
			    <display:column property ="edlTraf" titleKey="EDL_TRAF" />
			    <display:column property ="eulTraf" titleKey="EUL_TRAF" />
			    <display:column property="dataload" titleKey="DATALOAD" />
			    <display:column titleKey="Báo cáo" media="html">
			    	<a href="${pageContext.request.contextPath}/report/radio/location-gprs-qos/hr/details.htm?location=${vRpDyLocationGprsQos.location}&endDate=${day}">Báo cáo</a>&nbsp;
			    </display:column>
		</display:table>
</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript">
function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
}
<script type="text/javascript">
	$(function() {
		$( "#day" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
	});
</script>
