<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>GPRS-CS CELL ${cellid} REPORT</title>
<content tag="heading">GPRS-CS CELL ${cellid} REPORT </content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/hr/detail.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/dy/detail.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/wk/detail.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/mn/detail.htm?cellid=${cellid}&bscid=${bscid}&endMonth=${endMonth}&endYear=${endYear}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/dy/bh.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/wk/bh.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo BH tuần</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/mn/bh.htm?cellid=${cellid}&bscid=${bscid}&endMonth=${endMonth}&endYear=${endYear}"><span>Báo cáo BH tháng</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="bh.htm">
		<table width="100%" class="form">
			<tr>
			<td align="left">
			        BSC 
			        <select name="bscid" id="bscid">
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
			        <select name="cellid" id="cellid">
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
	            	Từ tháng <input value="${startMonth}" name="startMonth" id="startMonth" size="2" maxlength="2">
	            	&nbsp;&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4">
	            	&nbsp;Tới tháng <input value="${endMonth}" name="endMonth" id="endMonth" size="2" maxlength="2">
	            	&nbsp;&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4">
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
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
<display:table name="${vRpMnCellGprsCsBh}"  id="vRpMnCellGprsCsBh" requestURI="" pagesize="100" class="simple2" export="true">
		<display:column property="month" titleKey="MONTH" headerClass="master-header-1"/>
	    <display:column property ="year" title="YEAR" headerClass="master-header-1"/>
	    <display:column  sortable="true"  headerClass="master-header-1"  property="province" titleKey="PROVINCE" style = "width: 120px"/>
	    <display:column  sortable="true"  headerClass="master-header-1"  property="bscid" titleKey="BSC"/> 
	    <display:column  sortable="true"  headerClass="master-header-1"  property="cellid" titleKey="CELL"/>
	    <display:column  sortable="true"  headerClass="master-header-2"  property="bhCsxLevel1" titleKey="CSX_LEVEL1"/>
	    <display:column  sortable="true"  headerClass="master-header-2"  property ="bhCsxLevel2" titleKey="CSX_LEVEL2" />
	    <display:column  sortable="true"  headerClass="master-header-2"  property="bhCsxLevel3" titleKey="CSX_LEVEL3" />
	    <display:column  sortable="true"  headerClass="master-header-2"  property="bhCsxLevel4" titleKey="CSX_LEVEL4"/>
	    <display:column  sortable="true"  headerClass="master-header-3"  property ="bhMcsxLevel1" titleKey="MCSX_LEVEL1" />
	    <display:column  sortable="true"  headerClass="master-header-3"  property="bhMcsxLevel2" titleKey="MCSX_LEVEL2"/>
	    <display:column  sortable="true"  headerClass="master-header-3"  property ="bhMcsxLevel3" titleKey="MCSX_LEVEL3" />
	    <display:column  sortable="true"  headerClass="master-header-3"  property="bhMcsxLevel4" titleKey="MCSX_LEVEL4" />
	    <display:column  sortable="true"  headerClass="master-header-3"  property ="bhMcsxLevel5" titleKey="MCSX_LEVEL5" />
	    <display:column  sortable="true"  headerClass="master-header-3"  property="bhMcsxLevel6" titleKey="MCSX_LEVEL6"/>
	    <display:column  sortable="true"  headerClass="master-header-3"  property ="bhMcsxLevel7" titleKey="MCSX_LEVEL7" />
	    <display:column  sortable="true"  headerClass="master-header-3"  property="bhMcsxLevel8" titleKey="MCSX_LEVEL8" />
	    <display:column  sortable="true"  headerClass="master-header-3"  property="bhMcsxLevel9" titleKey="MCSX_LEVEL9" />
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

<script type="text/javascript">
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