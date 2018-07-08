<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>GPRS-CS CELL ${cellid} REPORT</title>
<content tag="heading">GPRS-CS CELL ${cellid} REPORT</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/hr/detail.htm?cellid=${cellid}&bscid=${bscid}&day=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/dy/detail.htm?cellid=${cellid}&bscid=${bscid}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/wk/detail.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/mn/detail.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/qr/detail.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo quý</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/dy/bh.htm?cellid=${cellid}&bscid=${bscid}&endDate=${endDate}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/wk/bh.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/mn/bh.htm?cellid=${cellid}&bscid=${bscid}"><span>Báo cáo BH tháng</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="detail.htm" name="frmSample" onSubmit="return ValidateForm()">
		<table width="100%" class="form">
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
	            	Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	            	&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
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
<display:table style = "width: 150%"  name="${vRpDyCellGprsCsList}" id="vRpDyCellGprsCs" requestURI="" pagesize="100" class="simple2" export="true">
			    <display:column  sortable="true" property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" headerClass="master-header-1"/> 
			   <display:column  sortable="true" property="province" titleKey="PROVINCE" style = "width: 120px" headerClass="master-header-1"/> 
			    <display:column  sortable="true" property="bscid" titleKey="BSC" headerClass="master-header-1"/> 
			    <display:column  sortable="true" property="cellid" titleKey="CELL" headerClass="hide" class="hide"/>
			    <display:column  sortable="true" titleKey="CELL" media="html" headerClass="master-header-1">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/hr/detail.htm?bscid=${vRpDyCellGprsCs.bscid}&cellid=${vRpDyCellGprsCs.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCellGprsCs.day}"/>">${vRpDyCellGprsCs.cellid}</a>
			    </display:column>
			    <display:column  sortable="true" property="csxLevel1" titleKey="CSX_LEVEL1" headerClass="master-header-2"/>
			    <display:column  sortable="true" property ="csxLevel2" titleKey="CSX_LEVEL2" headerClass="master-header-2" />
			    <display:column  sortable="true" property="csxLevel3" titleKey="CSX_LEVEL3" headerClass="master-header-2" />
			    <display:column  sortable="true" property="csxLevel4" titleKey="CSX_LEVEL4" headerClass="master-header-2"/>
			    <display:column  sortable="true" property ="mcsxLevel1" titleKey="MCSX_LEVEL1" headerClass="master-header-3" />
			    <display:column  sortable="true" property="mcsxLevel2" titleKey="MCSX_LEVEL2" headerClass="master-header-3"/>
			    <display:column  sortable="true" property ="mcsxLevel3" titleKey="MCSX_LEVEL3" headerClass="master-header-3" />
			    <display:column  sortable="true" property="mcsxLevel4" titleKey="MCSX_LEVEL4" headerClass="master-header-3" />
			    <display:column  sortable="true" property ="mcsxLevel5" titleKey="MCSX_LEVEL5" headerClass="master-header-3" />
			    <display:column  sortable="true" property="mcsxLevel6" titleKey="MCSX_LEVEL6" headerClass="master-header-3"/>
			    <display:column  sortable="true" property ="mcsxLevel7" titleKey="MCSX_LEVEL7" headerClass="master-header-3" />
			    <display:column  sortable="true" property="mcsxLevel8" titleKey="MCSX_LEVEL8" headerClass="master-header-3" />
			    <display:column  sortable="true" property="mcsxLevel9" titleKey="MCSX_LEVEL9" headerClass="master-header-3" />
			    <display:column  sortable="true" property ="dataload" titleKey="DATALOAD" />
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

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
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
function xl(){
	var sub=document.getElementById("submit");
	sub.focus();
}
	$(function() {
		$( "#startDate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
		
		$( "#endDate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
		
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