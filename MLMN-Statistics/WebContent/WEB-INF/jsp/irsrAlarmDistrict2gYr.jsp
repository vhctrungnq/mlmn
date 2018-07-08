<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>irsr 2g list</title>
<content tag="heading">DISTRICT IRSR 2G YEAR LIST</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/irsr/by-site/dy/list.htm"><span>ALARM by SITE</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/irsr/by-bsc/yr/list.htm"><span>IRSR by BSC</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/irsr/by-district/yr/list.htm"><span>IRSR by District</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/irsr/by-region/yr/list.htm"><span>IRSR by Center</span></a></li>
</ul>
<div class="ui-tabs-panel">
	<a href="${pageContext.request.contextPath}/report/radio/irsr/by-district/dy/list.htm"><span><b>Theo ngày</b></span></a>&nbsp;
	<a href="${pageContext.request.contextPath}/report/radio/irsr/by-district/mn/list.htm"><span><b>Theo tháng</b></span></a>&nbsp;
	<a href="${pageContext.request.contextPath}/report/radio/irsr/by-district/qr/list.htm"><span><b>Theo quý</b></span></a>
	<span><b>Theo năm</b></span>&nbsp;
	<br/>
	
		<table width="100%" class="form">
			<tr>
			  <td align="left">
			  <form method="get" action="list.htm">
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
			        &nbsp;PROVINCE
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
				    &nbsp;DISTRICT <input value="${district}" name="district" id="district" size="10" maxlength="50">
	            	Từ năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4">
	            	&nbsp;&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4">
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	          </form>
	          </td>
	        </tr>		
		</table>
	<br/>
	<div  style="overflow: auto;">
		<display:table name="${yrIrsrAlarmDistrict2gList}" id="yrIrsrAlarmDistrict2g" requestURI="" pagesize="100" class="simple3" export="true">		
		    <display:column property ="year" titleKey="YEAR" />
		    <display:column property ="region" titleKey="REGION" />
		    <display:column property="irsr" titleKey="% IRSR"/>
		</display:table>
	</div>
</div>