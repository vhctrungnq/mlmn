
<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>IPBB LINK MONTH REPORT</title>
<content tag="heading">IPBB LINK MONTHLY REPORT</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/core/ip-backbone/hr_delay_internet.htm?direction=&startDate=&endDate="><span>Báo cáo giờ</span></a></li>
 	<li class=""><a href="${pageContext.request.contextPath}/report/core/ip-backbone/dy_delay_internet.htm?direction=${direction}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
 	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/ip-backbone/mn_ipbblink.htm?&startMonth=${startMonth}&startYear=${startYear}&endMonth=${endMonth}&endYear=${endYear}"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="mn_ipbblink.htm" name = "frmSample" onSubmit = "return ValidateFormYear()">			  			
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
</div>	
	
<div  style="overflow: auto;">
		<h1><center>IPBB LINK1 MONTHLY REPORT</center></h1>
		<display:table name="${MnIpbblink1}" id="MnIpbblink1" requestURI="" pagesize="50" class="simple2" export="true" style="width: 200%;">
			<display:column property ="month"  titleKey="MONTH" />
		    <display:column property="year" titleKey="YEAR"/>
	    	<display:column property="direction" titleKey="DIRECTION"/>
		    <display:column property="link" titleKey="LINK"/>
		    <display:column property="refValue" titleKey="Unit(ms)"/>
		     <display:column property="ngay01" titleKey="01" class = "PER01_1"/>
		    <display:column property="ngay02" titleKey="02" class = "PER02_1"/>
		    <display:column property="ngay03" titleKey="03" class = "PER03_1"/>
		    <display:column property="ngay04" titleKey="04" class = "PER04_1"/>
		    <display:column property="ngay05" titleKey="05" class = "PER05_1"/>
		    <display:column property="ngay06" titleKey="06" class = "PER06_1"/>
		    <display:column property="ngay07" titleKey="07" class = "PER07_1"/>
		    <display:column property="ngay08" titleKey="08" class = "PER08_1"/>
		    <display:column property="ngay09" titleKey="09" class = "PER09_1"/>
		    <display:column property="ngay10" titleKey="10" class = "PER10_1"/>
		    <display:column property="ngay11" titleKey="11" class = "PER11_1"/>
		    <display:column property="ngay12" titleKey="12" class = "PER12_1"/>
		    <display:column property="ngay13" titleKey="13" class = "PER13_1"/>
		    <display:column property="ngay14" titleKey="14" class = "PER14_1"/>
		    <display:column property="ngay15" titleKey="15" class = "PER15_1"/>
		    <display:column property="ngay16" titleKey="16" class = "PER16_1"/>
		    <display:column property="ngay17" titleKey="17" class = "PER17_1"/>
		    <display:column property="ngay18" titleKey="18" class = "PER18_1"/>
		    <display:column property="ngay19" titleKey="19" class = "PER19_1"/>
		    <display:column property="ngay20" titleKey="20" class = "PER20_1"/>
		    <display:column property="ngay21" titleKey="21" class = "PER21_1"/>
		    <display:column property="ngay22" titleKey="22" class = "PER22_1"/>
		    <display:column property="ngay23" titleKey="23" class = "PER23_1"/>
		    <display:column property="ngay24" titleKey="24" class = "PER24_1"/>
		    <display:column property="ngay25" titleKey="25" class = "PER25_1"/>
		    <display:column property="ngay26" titleKey="26" class = "PER26_1"/>
		    <display:column property="ngay27" titleKey="27" class = "PER27_1"/>
		    <display:column property="ngay28" titleKey="28" class = "PER28_1"/>
		    <display:column property="ngay29" titleKey="29" class = "PER29_1"/>
		    <display:column property="ngay30" titleKey="30" class = "PER30_1"/>
		    <display:column property="ngay31" titleKey="31" class = "PER31_1"/>
		    <display:column property="tongDat" titleKey="Tổng đạt" class = "TONG_DAT"/>
		    <display:column property="tongKhongDat" titleKey="Tổng không đạt" class = "TONG_KHONG_DAT"/>
		    
	    	 <display:setProperty name="export.csv.filename" value="IPBB_Link1List.csv"/>
		     <display:setProperty name="export.excel.filename" value="IPBB_Link1List.xls"/>
		     <display:setProperty name="export.xml.filename" value="IPBB_Link1List.xml"/>
		</display:table> 
	</div>

	<div style="overflow: auto;">
		<h1><center>IPBB LINK2 MONTHLY REPORT</center></h1>
		<display:table name="${MnIpbblink2}" id="MnIpbblink2" requestURI="" pagesize="50" class="simple2" export="true" style="width: 200%;">
			<display:column property ="month"  titleKey="MONTH" />
		    <display:column property="year" titleKey="YEAR"/>
	    	<display:column property="direction" titleKey="DIRECTION"/>
		    <display:column property="link" titleKey="LINK"/>
		    <display:column property="refValue" titleKey="Unit(ms)"/>
		    <display:column property="ngay01" titleKey="01" class = "PER01_1"/>
		    <display:column property="ngay02" titleKey="02" class = "PER02_1"/>
		    <display:column property="ngay03" titleKey="03" class = "PER03_1"/>
		    <display:column property="ngay04" titleKey="04" class = "PER04_1"/>
		    <display:column property="ngay05" titleKey="05" class = "PER05_1"/>
		    <display:column property="ngay06" titleKey="06" class = "PER06_1"/>
		    <display:column property="ngay07" titleKey="07" class = "PER07_1"/>
		    <display:column property="ngay08" titleKey="08" class = "PER08_1"/>
		    <display:column property="ngay09" titleKey="09" class = "PER09_1"/>
		    <display:column property="ngay10" titleKey="10" class = "PER10_1"/>
		    <display:column property="ngay11" titleKey="11" class = "PER11_1"/>
		    <display:column property="ngay12" titleKey="12" class = "PER12_1"/>
		    <display:column property="ngay13" titleKey="13" class = "PER13_1"/>
		    <display:column property="ngay14" titleKey="14" class = "PER14_1"/>
		    <display:column property="ngay15" titleKey="15" class = "PER15_1"/>
		    <display:column property="ngay16" titleKey="16" class = "PER16_1"/>
		    <display:column property="ngay17" titleKey="17" class = "PER17_1"/>
		    <display:column property="ngay18" titleKey="18" class = "PER18_1"/>
		    <display:column property="ngay19" titleKey="19" class = "PER19_1"/>
		    <display:column property="ngay20" titleKey="20" class = "PER20_1"/>
		    <display:column property="ngay21" titleKey="21" class = "PER21_1"/>
		    <display:column property="ngay22" titleKey="22" class = "PER22_1"/>
		    <display:column property="ngay23" titleKey="23" class = "PER23_1"/>
		    <display:column property="ngay24" titleKey="24" class = "PER24_1"/>
		    <display:column property="ngay25" titleKey="25" class = "PER25_1"/>
		    <display:column property="ngay26" titleKey="26" class = "PER26_1"/>
		    <display:column property="ngay27" titleKey="27" class = "PER27_1"/>
		    <display:column property="ngay28" titleKey="28" class = "PER28_1"/>
		    <display:column property="ngay29" titleKey="29" class = "PER29_1"/>
		    <display:column property="ngay30" titleKey="30" class = "PER30_1"/>
		    <display:column property="ngay31" titleKey="31" class = "PER31_1"/>
		    <display:column property="tongDat" titleKey="Tổng đạt" class = "TONG_DAT"/>
		    <display:column property="tongKhongDat" titleKey="Tổng không đạt" class = "TONG_KHONG_DAT"/>
		    
		     <display:setProperty name="export.csv.filename" value="IPBB_Link2List.csv"/>
			 <display:setProperty name="export.excel.filename" value="IPBB_Link2List.xls"/>
			 <display:setProperty name="export.xml.filename" value="IPBB_Link2List.xml"/>
		</display:table>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script>
	 ${highlight};
	 ${highlight1};
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
		
var cache = {},
lastXhr;
$( "#mscid" ).autocomplete({
	minLength: 2,
	source: function( request, response ) {
		var term = request.term;
		if ( term in cache ) {
			response( cache[ term ] );
			return;
		}
		lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getMsc.htm", request, function( data, status, xhr ) {
			cache[ term ] = data;
			if ( xhr === lastXhr ) {
				response( data );
			}
		});
	}
}); 
</script>
