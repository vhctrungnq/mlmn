<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Báo cáo Cell GPRS-QoS</title>
<content tag="heading">CELL GPRS-QOS REPORT</content>
<ul class="ui-tabs-nav">

	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-qos/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-qos/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-qos/mn/list.htm"><span>Báo cáo tháng</span></a></li>
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
			  		&nbsp;PROVINCE 
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
			        &nbsp;BSC 
			        <select name="bscid" onchange="xl()">
			        	<option value="">Tất cả</option>
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
			        &nbsp;CELL <input value="${cellid}" name="cellid" id="cellid" size="10" onchange="xl()">
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
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
	
			<div  style="overflow: auto;">
<display:table name="${vRpMnCellGprsQos}" id="vRpMnCellGprsQos" requestURI="" pagesize="100" class="simple2" export="true">
			    <display:column property="month" titleKey="MONTH" sortable="true"/>
			    <display:column property="year" titleKey="YEAR" sortable="true"/>
			    <display:column property="region" titleKey="TT" sortable="true"/>
			    <display:column property="province" titleKey="PROVINCE" sortable="true"/> 
			    <display:column property="bscid" titleKey="BSC" sortable="true"/> 
			    <display:column property="cellid" titleKey="CELL" headerClass="hide" class="hide"/>
			    <display:column titleKey="CELL" media="html" sortable="true">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell-gprs-qos/mn/detail.htm?cellid=${vRpMnCellGprsQos.cellid}&bscid=${vRpMnCellGprsQos.bscid}&endMonth=${month}&endYear=${year}">${vRpMnCellGprsQos.cellid}</a>&nbsp;
			    </display:column> 
			    <display:column property="dlTbfReq" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DL_TBF_REQ" sortable="true"/>
			    <display:column property ="dlTbfSucr" titleKey="DL_TBF_SUCR" sortable="true"/>
			    <display:column property="ulTbfReq" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL_TBF_REQ" sortable="true"/>
			    <display:column property="ulTbfSucr" titleKey="UL_TBF_SUCR" sortable="true"/>
			    <display:column property ="gdlTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GDL_TRAF" sortable="true"/>
			    <display:column property="gulTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GUL_TRAF" sortable="true"/>
			    <display:column property ="edlTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EDL_TRAF" sortable="true"/>
			    <display:column property="eulTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EUL_TRAF" sortable="true"/>
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
<script type="text/javascript">
	$(function() {
		var cacheCell = {}, lastXhrCell;
		$( "#cellid" ).autocomplete({
			minLength: 3,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cacheCell ) {
					response( cacheCell[ term ] );
					return;
				}
		
				lastXhrCell = $.getJSON( "${pageContext.request.contextPath}/ajax/getCell.htm", request, function( data, status, xhr ) {
					cacheCell[ term ] = data;
					if ( xhr === lastXhrCell ) {
						response( data );
					}
				});
			}
		});
	});
</script>
