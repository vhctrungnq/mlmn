<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>Báo cáo BSC GPRS-CS</title>
<content tag="heading">BSC GPRS-CS WEEKLY REPORT</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-cs/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-cs/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-cs/mn/list.htm"><span>Báo cáo tháng</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-cs/qr/list.htm"><span>Báo cáo quý</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-cs/yr/list.htm"><span>Báo cáo năm</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm" name="frmSample" onSubmit="return ValidateFormWeek()">
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
	                &nbsp;Từ tuần <input value="${startWeek}" name="startWeek" id="startWeek" size="2" maxlength="2" onchange ="javascript:checkNumber(document.frmSample.startWeek);">
					<img alt="calendar" title="Click to choose the start week number" id="chooseStartWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.startYear);">
	            	&nbsp;Tới tuần <input value="${endWeek}" name="endWeek" id="endWeek" size="2" maxlength="2" onchange="javascript:checkNumber(document.frmSample.endWeek);">
					<img alt="calendar" title="Click to choose the end week number" id="chooseEndWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.endYear);">
	            	&nbsp;<input type="submit" class="button" name="submit" id="submit"value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
	
			<div  id="doublescroll">
<display:table name="${vRpWkBscGprsCs}" id="vRpWkBscGprsCs" requestURI="" pagesize="100" class="simple2" export="true">
		<display:column property ="week" titleKey="WEEK" sortable="true" />
	    <display:column property ="year" titleKey="YEAR" sortable="true" />
	    <display:column property ="region" titleKey="TT" sortable="true" />
		<display:column property="bscid" titleKey="BSC" headerClass="hide" class="hide"/>
	    <display:column titleKey="BSC" media="html" sortable="true">
	    	<a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-cs/wk/detail.htm?bscid=${vRpWkBscGprsCs.bscid}&endWeek=${week}&endYear=${year}">${vRpWkBscGprsCs.bscid}</a>&nbsp;
	    </display:column>
	    <display:column property="csxLevel1" titleKey="CSX_LEVEL1" sortable="true" headerClass="master-header-1"/>
	    <display:column property ="csxLevel2" titleKey="CSX_LEVEL2" sortable="true" headerClass="master-header-1" />
	    <display:column property="csxLevel3" titleKey="CSX_LEVEL3" sortable="true" headerClass="master-header-1" />
	    <display:column property="csxLevel4" titleKey="CSX_LEVEL4" sortable="true" headerClass="master-header-1"/>
	    <display:column property ="mcsxLevel1" titleKey="MCSX_LEVEL1" sortable="true" headerClass="master-header-2" />
	    <display:column property="mcsxLevel2" titleKey="MCSX_LEVEL2" sortable="true" headerClass="master-header-2"/>
	    <display:column property ="mcsxLevel3" titleKey="MCSX_LEVEL3" sortable="true" headerClass="master-header-2" />
	    <display:column property="mcsxLevel4" titleKey="MCSX_LEVEL4" sortable="true" headerClass="master-header-2" />
	    <display:column property ="mcsxLevel5" titleKey="MCSX_LEVEL5" sortable="true" headerClass="master-header-2" />
	    <display:column property="mcsxLevel6" titleKey="MCSX_LEVEL6" sortable="true" headerClass="master-header-2"/>
	    <display:column property ="mcsxLevel7" titleKey="MCSX_LEVEL7" sortable="true" headerClass="master-header-2" />
	    <display:column property="mcsxLevel8" titleKey="MCSX_LEVEL8" sortable="true" headerClass="master-header-2" />
	    <display:column property="mcsxLevel9" titleKey="MCSX_LEVEL9" sortable="true" headerClass="master-header-2" />
	    <display:column property ="dataload" titleKey="DATALOAD" sortable="true" headerClass="master-header-2" />
	</display:table>
</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />



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
	  for(var i=0;i<strLength+1;i++){
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
Calendar.setup({
    inputField		:	"startWeek",	// id of the input field
    ifFormat		:	"%W",   	// format of the input field
    button			:   "chooseStartWeek",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});
Calendar.setup({
    inputField		:	"endWeek",	// id of the input field
    ifFormat		:	"%W",   	// format of the input field
    button			:   "chooseEndWeek",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
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