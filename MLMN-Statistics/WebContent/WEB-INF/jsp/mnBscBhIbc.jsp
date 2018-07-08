<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Báo cáo Bsc Ibc ${bscid}</title>
<content tag="heading">BSC IBC ${bscid} REPORT</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/bsc/hr/detail.htm?bscid=${bscid}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/bsc/dy/detail.htm?bscid=${bscid}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/bsc/wk/detail.htm?bscid=${bscid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/bsc/mn/detail.htm?bscid=${bscid}&endMonth=${endMonth}&endYear=${endYear}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/bsc/dy/bh.htm?bscid=${bscid}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/ibc/bsc/wk/bh.htm?bscid=${bscid}"><span>Báo cáo BH tuần</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/ibc/bsc/mn/bh.htm?bscid=${bscid}&endMonth=${endMonth}&endYear=${endYear}"><span>Báo cáo BH tháng</span></a></li>
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
	<display:table name="${vRpMnBscBhIbcList}" id="vRpMnBscBhIbc" requestURI="" pagesize="100" class="simple2" export="true">
		<display:column property ="month" titleKey="MONTH" sortable="true"/>
		<display:column property ="year" titleKey="YEAR" sortable="true"/>  
	    <display:column property="region" titleKey="TT" sortable="true"/>
		<display:column property="bscid" titleKey="BSC" headerClass="margin" class="margin" sortable="true"/>
	    <display:column property ="bhTDef" titleKey="T_DEF"  sortable="true"/>
	    <display:column property="bhTAvail" titleKey="T_AVAIL"  sortable="true"/>
	    <display:column property ="bhTAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_ATTS"  sortable="true"/>
	    <display:column property="bhTBlkr" titleKey="T_BLKR" sortable="true"/>
	    <display:column property="bhTBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_BLKS" sortable="true"/>
		<display:column property="bhTHoblkr" titleKey="T_HOBLKR" sortable="true"/>
	    <display:column property="bhTHoblks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_HOBLKS" sortable="true"/>
	    <display:column property="bhCssr" titleKey="CSSR" sortable="true"/> 
	    <display:column property="bhTDrpr" titleKey="T_DRPR"  sortable="true"/>
	    <display:column property ="bhTDrps" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_DRPS"  sortable="true"/>
	    <display:column property ="bhTTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF"  sortable="true"/>
	    <display:column property ="bhTTrafh" titleKey="T_TRAFH"  sortable="true"/>
	    <display:column property="bhTGos" titleKey="BH_T_GoS" headerClass="margin" class="margin" sortable="true"/>
	    <display:column property ="bhSDef" titleKey="S_DEF"  sortable="true"/>
	    <display:column property="bhSAvail" titleKey="S_AVAIL"  sortable="true"/>
	    <display:column property ="bhSAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_ATTS"  sortable="true"/>
	    <display:column property="bhSBlkr" titleKey="S_BLKR"  sortable="true"/>
	    <display:column property ="bhSBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_BLKS" sortable="true"/>
	    <display:column property ="bhSDrpr" titleKey="S_DRPR"  sortable="true"/>
	    <display:column property ="bhSDrps" titleKey="S_DRPS"  sortable="true"/>
	</display:table>
	</div>
	<br/>
	<div id="container" style="width: 1000px; margin: 1em auto"></div>
</div>

${chart}
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.filter.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/assets/style.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/assets/prettify.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/jquery-ui.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.filter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/assets/prettify.js"></script>	
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>

<script type="text/javascript">
$("#bscid").multiselect().multiselectfilter();
</script>
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
