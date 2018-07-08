<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>cell handover list</title>
<content tag="heading">CELL HANDOVER DAILY REPORT</content>
<ul class="ui-tabs-nav">
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell-ho/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-ho/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-ho/mn/list.htm"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	<%-- <%@ include file="/WEB-INF/jsp/includes/filterform.jsp" %> --%>
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
			        &nbsp;CELL <input value="${cellid}" name="cellid" id="cellid" size="10" >
	                &nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<div  style="overflow: auto;">
			<display:table name="${vRpDyCellHo}" id="vRpDyCellHo" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
			    <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY"/>
			    <display:column property ="province" titleKey="PROVINCE" sortable="true" sortName="province"/>
			    <display:column property="bscid" titleKey="BSC" headerClass="hide" class="hide"/>
			    <display:column titleKey="BSC" media="html" sortable="true" sortProperty="bscid">
			    	<a href="${pageContext.request.contextPath}/report/radio/bsc-ho/hr/details.htm?bscid=${vRpDyCellHo.bscid}&endDate=${day}">${vRpDyCellHo.bscid}</a>
			    </display:column>
			    <display:column property="cellid" titleKey="CELL" headerClass="hide" class="hide"/>
			    <display:column titleKey="CELL" media="html" sortable="true" sortProperty="cellid">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell-ho/hr/details.htm?bscid=${vRpDyCellHo.bscid}&cellid=${vRpDyCellHo.cellid}&endDate=${day}">${vRpDyCellHo.cellid}</a>
			    </display:column>
			    <display:column property ="hoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HO_ATT" sortable="true" sortName="HO_ATT"/>
			    <display:column property="hoSucr" titleKey="HO_SUCR (%)" headerClass="hide" class="hide"/>
			    <display:column titleKey="HO_SUCR (%)" media="html" sortable="true" sortProperty="hoSucr" class="HO_SUCR">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell/ho/dy.htm?bscid=${vRpDyCellHo.bscid}&cellid=${vRpDyCellHo.cellid}&endDate=${day}">${vRpDyCellHo.hoSucr}</a>
			    </display:column>
			    <display:column property ="intraHoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="INTRA_HO_ATT" sortable="true" sortName="INTRA_HO_ATT"/>
			    <display:column property="intraHoSucr" titleKey="INTRA_HO_SUCR" sortable="true" sortName="INTRA_HO_SUCR(%)"/>
			    <display:column property ="ogHoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="OG_HO_ATT" sortable="true" sortName="OG_HO_ATT"/>
			    <display:column property="ogHoSucr" titleKey="OG_HO_SUCR" sortable="true" sortName="OG_HO_SUCR(%)"/>
			    <display:column property="inHoAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="IN_HO_ATT" sortable="true" sortName="IN_HO_ATT"/>
			    <display:column property ="inHoSucr" titleKey="IN_HO_SUCR" sortable="true" sortName="IN_HO_SUCR(%)"/>
			    <display:column property="hoRev" titleKey="HO REVERSION(%)" sortable="true" sortName="ho_Rev"/>
			    <display:column property ="hoLost" titleKey="HO LOST(%)" sortable="true" sortName="ho_Lost"/>
		</display:table>
</div>
</div>


<%-- <%@ include file="/WEB-INF/jsp/includes/calendar.jsp" %> --%>
<script type="text/javascript">
	$(function() {
		
		
		var cacheCell = {}, lastXhrCell;
		$( "#cellid" ).autocomplete({
			minLength: 3,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cacheCell ) {
					response( cacheCell[ term ] );
					return;
				}

				lastXhrCell = $.getJSON( "${pageContext.request.contextPath}/ajax/getCell.htm", request, function( data, status, xhr ) {
					cacheCell[ term ] = data;
					if ( xhr === lastXhrCell ) {
						response( data );
					}
				});
			}
		});
		
		${highlight}
	});
</script>
