<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>Báo cáo District GPRS-CS</title>
<content tag="heading">GPRS-CS DISTRICT MONTHLY REPORT</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/district-gprs-cs/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/district-gprs-cs/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/district-gprs-cs/mn/list.htm"><span>Báo cáo tháng</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/district-gprs-cs/qr/list.htm"><span>Báo cáo quý</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/district-gprs-cs/yr/list.htm"><span>Báo cáo năm</span></a></li>
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
			        &nbsp;&nbsp;DISTRICT <input value="${district}" name="district" id="district" size="10" maxlength="50" onchange="xl()">
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
		<div  id="doublescroll">
<display:table name="${vRpMnDistrictGprsCs}" id="vRpMnDistrictGprsCs" requestURI="" pagesize="100" class="simple2" export="true">
			    <display:column property ="year" titleKey="YEAR" />
			    <display:column property ="month" titleKey="MONTH" />
			    <display:column property ="region" titleKey="TT" /> 
			    <display:column property ="province" titleKey="PROVINCE" headerClass="hide" class="hide"/>
			    <display:column titleKey="PROVINCE" media="html">
			    	<a href="/VMSC2-Statistics/report/radio/province-gprs-cs/mn/details.htm?province=${vRpMnDistrictGprsCs.province}&endMonth=${vRpMnDistrictGprsCs.month}&endYear=${vRpMnDistrictGprsCs.year}">${vRpMnDistrictGprsCs.province}</a>&nbsp;
			    </display:column>
			    <display:column property ="district" titleKey="DISTRICT" headerClass="hide" class="hide"/> 
			    <display:column titleKey="DISTRICT" media="html">
			    	<a href="/VMSC2-Statistics/report/radio/district-gprs-cs/mn/details.htm?province=${vRpMnDistrictGprsCs.province}&district=${vRpMnDistrictGprsCs.district}&endMonth=${vRpMnDistrictGprsCs.month}&endYear=${vRpMnDistrictGprsCs.year}">${vRpMnDistrictGprsCs.district}</a>&nbsp;
			    </display:column>
			    <display:column property ="csxLevel1" titleKey="CSX_LEVEL1" />
			    <display:column property ="csxLevel2" titleKey="CSX_LEVEL2" />
			    <display:column property ="csxLevel3" titleKey="CSX_LEVEL3" />
			    <display:column property ="csxLevel4" titleKey="CSX_LEVEL4"/>
			    <display:column property ="mcsxLevel1" titleKey="MCSX_LEVEL1" />
			    <display:column property ="mcsxLevel2" titleKey="MCSX_LEVEL2" />
			    <display:column property ="mcsxLevel3" titleKey="MCSX_LEVEL3" />
			    <display:column property ="mcsxLevel4" titleKey="MCSX_LEVEL4" />
			    <display:column property ="mcsxLevel5" titleKey="MCSX_LEVEL5" />
			    <display:column property ="mcsxLevel6" titleKey="MCSX_LEVEL6"/>
			    <display:column property ="mcsxLevel7" titleKey="MCSX_LEVEL7" />
			    <display:column property ="mcsxLevel8" titleKey="MCSX_LEVEL8" />
			    <display:column property ="mcsxLevel9" titleKey="MCSX_LEVEL9" />
			    <display:column property="dataload" titleKey="DATALOAD" />
		</display:table>
</div>
</div>

<script type="text/javascript" src="/VMSC2-Statistics/scripts/text_date.js"></script>
<script type="text/javascript" src="/VMSC2-Statistics/scripts/highcharts.js"></script>
<script type="text/javascript" src="/VMSC2-Statistics/scripts/exporting.js"></script>
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