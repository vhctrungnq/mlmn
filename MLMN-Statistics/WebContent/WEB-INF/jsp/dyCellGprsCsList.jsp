<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>CELL GPRS-CS DAILY REPORT</title>
<content tag="heading">CELL GPRS-CS DAILY REPORT</content>
<ul class="ui-tabs-nav">
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/mn/list.htm"><span>Báo cáo tháng</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/qr/list.htm"><span>Báo cáo quý</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/yr/list.htm"><span>Báo cáo năm</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm">
					Trung tâm 
			  			<select name="region" id="region">
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
			        <select name="province">
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
			        <select name="bscid">
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
	                &nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	
			<div  id="doublescroll">
<display:table style = "width: 150%" name="${vRpDyCellGprsCs}" id="vRpDyCellGprsCs" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
			    <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="Ngày" sortable="true" sortName="day" headerClass="master-header-1"/>
			    <display:column property="region" titleKey="TT" sortable="true" sortName="region" headerClass="master-header-1"/>
			    <display:column titleKey="PROVINCE" media="html" sortable="true" sortProperty="province" style = "width: 120px" headerClass="master-header-1">
			    	<a href="${pageContext.request.contextPath}/report/radio/province-gprs-cs/hr/details.htm?province=${vRpDyCellGprsCs.province}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCellGprsCs.day}"/>">${vRpDyCellGprsCs.province}</a>
			    </display:column>			    
			    <display:column property="province" titleKey="PROVINCE" headerClass="hide" class="hide"/>
			    <display:column property="bscid" titleKey="BSC" headerClass="hide" class="hide" />
			    <display:column titleKey="BSC" media="html" sortable="true" sortProperty="bscid" headerClass="master-header-1">
			    	<a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-cs/hr/detail.htm?bscid=${vRpDyCellGprsCs.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCellGprsCs.day}"/>">${vRpDyCellGprsCs.bscid}</a>
			    </display:column>
			    <display:column property="cellid" titleKey="CELL" headerClass="hide" class="hide"/>
			    <display:column titleKey="CELL" media="html" sortable="true" sortProperty="cellid" headerClass="master-header-1">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell-gprs-cs/hr/detail.htm?cellid=${vRpDyCellGprsCs.cellid}&bscid=${vRpDyCellGprsCs.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCellGprsCs.day}"/>">${vRpDyCellGprsCs.cellid}</a>
			    </display:column>
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
</div>

<script type="text/javascript">
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