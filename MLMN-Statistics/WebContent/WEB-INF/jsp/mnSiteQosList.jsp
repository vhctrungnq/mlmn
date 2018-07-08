<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>site qos list</title>
<content tag="heading">LIST SITE QOS MONTH ${month}/${year}</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/site-qos/mn/list.htm"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm" onSubmit="return ValidateFormYear()">
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
			        &nbsp;&nbsp;BSC <input value="${bscid}" name="bscid" id="bscid" size="10" maxlength="50" onchange="xl()">
			        &nbsp;&nbsp;SITE <input value="${siteid}" name="siteid" id="siteid" size="10" maxlength="50" onchange="xl()">
	                &nbsp;&nbsp;Tháng <input value="${month}" name="month" id="month" size="2" maxlength="2">
	                &nbsp;&nbsp;Năm <input value="${year}" name="year" id="year" size="4" maxlength="4">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
		<div  style="overflow: auto;">
<display:table name="${vRpMnSiteQos}" id="vRpMnSiteQos" requestURI="" pagesize="100" class="simple2" export="true">
		<display:column property="region" titleKey="REGION"/>
			    <display:column property ="province" titleKey="PROVINCE" />
			    <display:column property ="bscid" titleKey="BSCID"/> 
			    <display:column property="siteid" titleKey="SITEID" headerClass="hide" class="hide"/> 
			    <display:column titleKey="SITEID" media="html">			    
			    	<a href="details.htm?bscid=${vRpMnSiteQos.bscid}&siteid=${vRpMnSiteQos.siteid}&endMonth=${month}&endYear=${year}">${vRpMnSiteQos.siteid}</a>&nbsp;
			    </display:column>
			    <display:column property ="tDef" titleKey="T_DEF" />
			    <display:column property ="tAvail" titleKey="T_AVAIL" />
			    <display:column property="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF" />
			    <display:column property="tAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_ATTS" />
			    <display:column property="tDrpr" titleKey="T_DRPR" class="T_DRPR"/>
			    <display:column property="tBlkr" titleKey="T_BLKR" class="T_BLKR"/>
			    <display:column property="tHoblkr" titleKey="T_HOBLKR"/>
			    <display:column property="cssr" titleKey="CSSR"/> 
			    <display:column property ="sDef" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_DEF" />
			    <display:column property="sAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_ATTS" />
			    <display:column property="sDrpr" titleKey="S_DRPR" class="S_DRPR"/>
			    <display:column property ="sBlkr" titleKey="S_BLKR" />
			    <display:column property ="dataload" titleKey="DB LOAD (%)" />
		</display:table>
</div>
</div>


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
