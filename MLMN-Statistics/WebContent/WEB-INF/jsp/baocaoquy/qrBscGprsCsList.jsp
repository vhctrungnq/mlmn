<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>BSC GPRS-CS QUARTERLY REPORT</title>
<content tag="heading">BSC GPRS-CS QUARTERLY REPORT</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-cs/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-cs/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-cs/mn/list.htm"><span>Báo cáo tháng</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-cs/qr/list.htm"><span>Báo cáo quý</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-cs/yr/list.htm"><span>Báo cáo năm</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm">
					<fmt:message key="option.cellgprscs.region"/>
		  			<select name="region">
			        	<option value="">Tất cả</option>
				        <c:forEach var="item" items="${regionList}">
			              <c:choose>
			                <c:when test="${item.region == region}">
			                    <option value="${item.region}" selected="selected">${item.region}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${item.region}">${item.region}</option>
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
			       &nbsp;Từ quý <input value="${startQuarter}"
						name="startQuarter" id="startQuarter" size="1" maxlength="1">
					&nbsp;Năm <input value="${startYear}" name="startYear"
						id="startYear" size="4" maxlength="4"> &nbsp;Tới quý <input
						value="${endQuarter}" name="endQuarter" id="endQuarter" size="1"
						maxlength="1"> &nbsp;Năm <input value="${endYear}"
						name="endYear" id="endYear" size="4" maxlength="4"> &nbsp;<input
						type="submit" class="button" name="submit" value="View Report" />
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
<div  style="overflow: auto;" class="tableStandar" id = "doublescroll">
	<display:table style = "width: 150%"  name="${vRpQrBscGprsCs}" id="item" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
	    <display:column property="quarter" titleKey="QUARTER" sortable="true" sortName="quarter" headerClass="master-header-1"/>
	    <display:column property="year" titleKey="YEAR" sortable="true" sortName="year" headerClass="master-header-1"/>
	    <display:column property="region" titleKey="TT" sortable="true" sortName="region" headerClass="master-header-1"/>
	    <display:column property="bscid" titleKey="BSC" sortable="true" sortName="bscid" headerClass="master-header-1"/> 
	    <display:column property ="mcsxLevel1" titleKey="MCSX_LEVEL1" headerClass="master-header-3" sortable="true" sortName="MCSX_LEVEL1"/>
	    <display:column property="mcsxLevel2" titleKey="MCSX_LEVEL2" headerClass="master-header-3" sortable="true" sortName="MCSX_LEVEL2"/>
	    <display:column property ="mcsxLevel3" titleKey="MCSX_LEVEL3" headerClass="master-header-3" sortable="true" sortName="MCSX_LEVEL3"/>
	    <display:column property="mcsxLevel4" titleKey="MCSX_LEVEL4" headerClass="master-header-3" sortable="true" sortName="MCSX_LEVEL4"/>
	    <display:column property ="mcsxLevel5" titleKey="MCSX_LEVEL5" headerClass="master-header-3" sortable="true" sortName="MCSX_LEVEL5"/>
	    <display:column property="mcsxLevel6" titleKey="MCSX_LEVEL6" headerClass="master-header-3" sortable="true" sortName="MCSX_LEVEL6"/>
	    <display:column property ="mcsxLevel7" titleKey="MCSX_LEVEL7" headerClass="master-header-3" sortable="true" sortName="MCSX_LEVEL7"/>
	    <display:column property="mcsxLevel8" titleKey="MCSX_LEVEL8" headerClass="master-header-3" sortable="true" sortName="MCSX_LEVEL8"/>
	    <display:column property="mcsxLevel9" titleKey="MCSX_LEVEL9" headerClass="master-header-3" sortable="true" sortName="MCSX_LEVEL9"/>
	    <display:column property="csxLevel1" titleKey="CSX_LEVEL1" headerClass="master-header-2" sortable="true" sortName="CSX_LEVEL1"/>
	    <display:column property ="csxLevel2" titleKey="CSX_LEVEL2" headerClass="master-header-2" sortable="true" sortName="CSX_LEVEL2"/>
	    <display:column property="csxLevel3" titleKey="CSX_LEVEL3" headerClass="master-header-2" sortable="true" sortName="CSX_LEVEL3"/>
	    <display:column property="csxLevel4" titleKey="CSX_LEVEL4" headerClass="master-header-2" sortable="true" sortName="CSX_LEVEL4"/>
	    <display:column property ="dataload" titleKey="DATALOAD" sortable="true" sortName="DATALOAD"/>
	    
	    <display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />
	</display:table>
</div>

<script type="text/javascript">
	$(function() {
		var cacheCell = {}, lastXhrCell;
		$("#cellid")
				.autocomplete(
						{
							minLength : 3,
							source : function(request, response) {
								var term = request.term;
								if (term in cacheCell) {
									response(cacheCell[term]);
									return;
								}

								lastXhrCell = $
										.getJSON(
												"${pageContext.request.contextPath}/ajax/getCell.htm",
												request, function(data, status,
														xhr) {
													cacheCell[term] = data;
													if (xhr === lastXhrCell) {
														response(data);
													}
												});
							}
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
