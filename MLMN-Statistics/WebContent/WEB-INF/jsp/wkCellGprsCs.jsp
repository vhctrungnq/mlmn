<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>GPRS-CS CELL ${cellid} REPORT</title>
<content tag="heading">GPRS-CS CELL ${cellid} REPORT</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/hr/detail.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo giờ</span></a></li>
 	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/dy/detail.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo ngày</span></a></li>
 	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/wk/detail.htm?cellid=${cellid}&bscid=${bscid}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo tuần</span></a></li>
  	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/mn/detail.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo tháng</span></a></li>
  	  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/qr/detail.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo quý</span></a></li>
 	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/dy/bh.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo BH ngày</span></a></li>
 	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/wk/bh.htm?cellid=${cellid}&bscid=${bscid}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo BH tuần</span></a></li>
  	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/mn/bh.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo BH tháng</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="detail.htm" name="frmSample" onSubmit="return ValidateFormWeek()">
		<table style="width:100%;" class="form">
			<tr>
				<td align="left">
			        BSC 
			        <select name="bscid" id="bscid" onchange="xl()">
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
				    &nbsp;&nbsp;CELL 
			        <select name="cellid" id="cellid" onchange="xl()">
						<option  value="">--Select Cell--</option>
				        <c:forEach var="cell" items="${cellList}">
			              <c:choose>
			                <c:when test="${cell.cellid == cellid}">
			                    <option value="${cell.cellid}" selected="selected">${cell.cellid}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${cell.cellid}">${cell.cellid}</option>
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
	            </td>
	        </tr>		
		</table>
	  </form>
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
			<div  id="doublescroll">
<display:table style = "width: 150%"  name="${vRpWkCellGprsCsList}" id="vRpWkCellGprsCs" requestURI="" pagesize="100" class="simple2" export="true">
			    <display:column property="week" titleKey="WEEK" sortable="true"  headerClass="master-header-1"/> 
			   <display:column property="year" titleKey="YEAR" sortable="true"  headerClass="master-header-1"/> 
			   <display:column property="province" titleKey="PROVINCE" sortable="true"  headerClass="master-header-1" style = "width: 120px" /> 
			    <display:column property="bscid" titleKey="BSC" sortable="true"  headerClass="master-header-1"/> 
			    <display:column property="cellid" titleKey="CELL" sortable="true"  headerClass="master-header-1"/> 
			    <display:column property="csxLevel1" titleKey="CSX_LEVEL1" headerClass="master-header-2" sortable="true" sortName="CSX_LEVEL1"/>
			    <display:column property ="csxLevel2" titleKey="CSX_LEVEL2" headerClass="master-header-2" sortable="true" sortName="CSX_LEVEL2"/>
			    <display:column property="csxLevel3" titleKey="CSX_LEVEL3" headerClass="master-header-2" sortable="true" sortName="CSX_LEVEL3"/>
			    <display:column property="csxLevel4" titleKey="CSX_LEVEL4" headerClass="master-header-2" sortable="true" sortName="CSX_LEVEL4"/>
			    <display:column property ="mcsxLevel1" titleKey="MCSX_LEVEL1" headerClass="master-header-3" sortable="true" sortName="MCSX_LEVEL1"/>
			    <display:column property="mcsxLevel2" titleKey="MCSX_LEVEL2" headerClass="master-header-3" sortable="true" sortName="MCSX_LEVEL2"/>
			    <display:column property ="mcsxLevel3" titleKey="MCSX_LEVEL3" headerClass="master-header-3" sortable="true" sortName="MCSX_LEVEL3"/>
			    <display:column property="mcsxLevel4" titleKey="MCSX_LEVEL4" headerClass="master-header-3" sortable="true" sortName="MCSX_LEVEL4"/>
			    <display:column property ="mcsxLevel5" titleKey="MCSX_LEVEL5" headerClass="master-header-3" sortable="true" sortName="MCSX_LEVEL5"/>
			    <display:column property="mcsxLevel6" titleKey="MCSX_LEVEL6" headerClass="master-header-3" sortable="true" sortName="MCSX_LEVEL6"/>
			    <display:column property ="mcsxLevel7" titleKey="MCSX_LEVEL7" headerClass="master-header-3" sortable="true" sortName="MCSX_LEVEL7"/>
			    <display:column property="mcsxLevel8" titleKey="MCSX_LEVEL8" headerClass="master-header-3" sortable="true" sortName="MCSX_LEVEL8"/>
			    <display:column property="mcsxLevel9" titleKey="MCSX_LEVEL9" headerClass="master-header-3" sortable="true" sortName="MCSX_LEVEL9"/>
			    <display:column property ="dataload" titleKey="DATALOAD" sortable="true" sortName="DATALOAD"/>
			</display:table>
</div>
	<br/>
	<div id="csxLevel1Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="csxLevel2Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="csxLevel3Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="csxLevel4Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="mcsxLevel1Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="mcsxLevel2Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="mcsxLevel3Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="mcsxLevel4Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="mcsxLevel5Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="mcsxLevel6Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="mcsxLevel7Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="mcsxLevel8Chart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="mcsxLevel9Chart" style="width: 1000px; margin: 1em auto"></div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
${csxLevel1Chart}
${csxLevel2Chart}
${csxLevel3Chart}
${csxLevel4Chart}
${mcsxLevel1Chart}
${mcsxLevel2Chart}
${mcsxLevel3Chart}
${mcsxLevel4Chart}
${mcsxLevel5Chart}
${mcsxLevel6Chart}
${mcsxLevel7Chart}
${mcsxLevel8Chart}
${mcsxLevel9Chart}

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
	
	$(function() {		
		$("select#bscid").change(function(){
			$.getJSON("${pageContext.request.contextPath}/ajax/getCellOfBsc.htm",{bscid: $(this).val()}, function(j){
				var options = '<option  value="">--Select Cell--</option>';
				for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].cellid + '">' + j[i].cellid + '</option>';
				}
				$("#cellid").html(options);
				$('#cellid option:first').attr('selected', 'selected');
			});
		});
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