<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Báo cáo GPRS QoS Bsc ${bscid}</title>
<content tag="heading">GPRS QOS BSC ${bscid} REPORT</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-qos/hr/detail.htm?bscid=${bscid}&day=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-qos/dy/detail.htm?bscid=${bscid}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-qos/wk/detail.htm?bscid=${bscid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-qos/mn/detail.htm?bscid=${bscid}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-qos/dy/bh.htm?bscid=${bscid}&endDate=${endDate}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-qos/wk/bh.htm?bscid=${bscid}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-qos/mn/bh.htm?bscid=${bscid}"><span>Báo cáo BH tháng</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="detail.htm" name="frmSample" onSubmit="return ValidateForm()">
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
					&nbsp;BSC 
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
	            	&nbsp;&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="15">
	            	&nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="15">
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	  </form>
	<br/>
	<table class="form">
	    	<tr>
	    		<td>
	    			<b>Chọn chỉ số hiển thị: </b>
	    		</td>
	    	</tr>
	        <tr>
	        	<td>${checkColumns}</td>
			</tr>
</table>
<br/>
			<div  style="overflow: auto;">
<display:table name="${vRpDyBscGprsQosList}" id="vRpDyBscGprsQos" requestURI="" pagesize="100" class="simple2" export="true">
	    <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY"/>
		<display:column property="region" titleKey="TT"/>
	    <display:column titleKey="BSC" class="hide" headerClass="hide"/>
	    <display:column titleKey="BSC" media="html">
    	<a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-qos/hr/detail.htm?bscid=${vRpDyBscGprsQos.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyBscGprsQos.day}"/>">${vRpDyBscGprsQos.bscid}</a>&nbsp;
    </display:column>  
	    <display:column property="dlTbfReq"  decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DL_TBF_REQ"/>
	    <display:column property ="dlTbfSucr" titleKey="DL_TBF_SUCR" />
	    <display:column property="ulTbfReq" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL_TBF_REQ" />
	    <display:column property="ulTbfSucr" titleKey="UL_TBF_SUCR"/>
	    <display:column property ="gdlTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GDL_TRAF" />
	    <display:column property="gulTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GUL_TRAF"/>
	    <display:column property ="edlTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EDL_TRAF" />
	    <display:column property="eulTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EUL_TRAF" />
	    <display:column property="dataload" titleKey="DB LOAD(%)" />
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
	});
</script>
