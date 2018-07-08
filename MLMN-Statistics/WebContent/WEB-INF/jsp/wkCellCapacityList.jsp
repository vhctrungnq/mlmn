<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>Báo cáo Cell GPRS-CS</title>
<content tag="heading">REPORT CELL GPRS-CS</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-capacity/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell-capacity/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-capacity/mn/list.htm"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table style="width:100%;" class="form">
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
			        CELL <input value="${cellid}" name="cellid" id="cellid" size="10">
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
<display:table name="${vRpWkCellCapacity}" id="vRpWkCellCapacity" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
			    <display:column property="week" titleKey="WEEK" sortable="true" sortName="week"/>
			    <display:column property="year" titleKey="YEAR" sortable="true" sortName="year"/>
			    <display:column property="region" titleKey="TT" sortable="true" sortName="region"/>
			    <display:column property="province" titleKey="PROVINCE" sortable="true" sortName="province"/> 
			    <display:column property="bscid" titleKey="BSC" sortable="true" sortName="bscid"/> 
			    <display:column property="cellid" titleKey="CELL" sortable="true" />
			    <display:column property="cgi" titleKey="CGI" sortable="true"/>
			    <display:column property ="tAvail" titleKey="T_AVAIL" class="T_AVAIL" sortable="true" sortName="T_AVAIL"/>
			    <display:column property="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF" sortable="true"/>
			    <display:column property="tTrafh" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAFH"  sortable="true"  sortName="T_TRAFH"/>			    
			    <display:column property="tGos" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_GOS"  sortable="true"  sortName="T_GOS"/>			    
			    <display:column property="tCap" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_CAP"  sortable="true"  sortName="T_CAP"/>			    
			    <display:column property="tOtraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_OTRAF"  sortable="true"  sortName="T_OTRAF"/>			    
			    <display:column property="tUtil" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_UTIL"  sortable="true"  sortName="T_UTIL"/>			    
			    <display:column property="tBlkr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_BLKR"  sortable="true"  sortName="T_BLKR"/>			    
			    <display:column property="tchVar" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TCH_VAR"  sortable="true"  sortName="T_CAP"/>			    
			    <display:column property="trxVar" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRX_VAR"  sortable="true"  sortName="TRX_VAR"/>			    
			    <display:column property="tchreq" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TCHREQ"  sortable="true"  sortName="TCHREQ"/>			    
		</display:table>
</div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="




/styles/calendar-blue.css" />

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