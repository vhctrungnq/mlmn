<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>location gprs cs list</title>
<content tag="heading">LIST REGION GPRS-CS MONTH ${month}/${year}</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-cs/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/location-gprs-cs/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/location-gprs-cs/mn/list.htm"><span>Báo cáo tháng</span></a></li>
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
			        <!-- LOCATION <input value="${location}" name="location" id="location" size="15" maxlength="50"> -->
	                &nbsp;&nbsp;Tháng <input value="${month}" name="month" id="month" size="2" maxlength="2">
	                &nbsp;&nbsp;Năm <input value="${year}" name="year" id="year" size="4" maxlength="4">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
		<div  id="doublescroll">
<display:table name="${vRpMnLocationGprsCs}" id="vRpMnLocationGprsCs" requestURI="" pagesize="100" class="simple2" export="true">
			    <display:column property ="region" title="TT" />
			    <display:column property ="location" title="LOCATION" media="html">
			    	<a href="details.htm?location=${vRpMnLocationGprsCs.location}&endMonth=${month}&endYear=${year}">${vRpMnLocationGprsCs.location}</a>&nbsp;
			    </display:column> 
			    <display:column property ="csxLevel1" title="CSX_LEVEL1" />
			    <display:column property ="csxLevel2" title="CSX_LEVEL2" />
			    <display:column property ="csxLevel3" title="CSX_LEVEL3" />
			    <display:column property ="csxLevel4" title="CSX_LEVEL4"/>
			    <display:column property ="mcsxLevel1" title="MCSX_LEVEL1" />
			    <display:column property ="mcsxLevel2" title="MCSX_LEVEL2" />
			    <display:column property ="mcsxLevel3" title="MCSX_LEVEL3" />
			    <display:column property ="mcsxLevel4" title="MCSX_LEVEL4" />
			    <display:column property ="mcsxLevel5" title="MCSX_LEVEL5" />
			    <display:column property ="mcsxLevel6" title="MCSX_LEVEL6"/>
			    <display:column property ="mcsxLevel7" title="MCSX_LEVEL7" />
			    <display:column property ="mcsxLevel8" title="MCSX_LEVEL8" />
			    <display:column property ="mcsxLevel9" title="MCSX_LEVEL9" />
			    <display:column property="dataload" title="DATALOAD" />
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