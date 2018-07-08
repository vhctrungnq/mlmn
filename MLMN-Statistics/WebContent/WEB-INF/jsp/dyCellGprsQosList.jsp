<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Báo cáo Cell GPRS-QoS</title>
<content tag="heading">CELL GPRS-QOS REPORT</content>
<ul class="ui-tabs-nav">
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-qos/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-qos/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-gprs-qos/mn/list.htm"><span>Báo cáo tháng</span></a></li>
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
			        &nbsp;CELL <input value="${cellid}" name="cellid" id="cellid" size="10" >
	                &nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
			<div  style="overflow: auto;">
<display:table name="${vRpDyCellGprsQos}" id="vRpDyCellGprsQos" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
			    <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true" sortName="DAY"/>
			    <display:column property="region" titleKey="TT" sortable="true" sortName="region"/>
			    <display:column titleKey="PROVINCE" media="html" sortable="true" sortProperty="PROVINCE">
			    	<a href="${pageContext.request.contextPath}/report/radio/province-gprs-qos/hr/details.htm?province=${vRpDyCellGprsQos.province}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCellGprsQos.day}"/>">${vRpDyCellGprsQos.province}</a>
			    </display:column>			    
			    <display:column property="province" titleKey="PROVINCE" headerClass="hide" class="hide"/>
			    <display:column property="bscid" titleKey="BSC" headerClass="hide" class="hide"/>
			    <display:column titleKey="BSC" media="html" sortable="true" sortProperty="bscid">
			    	<a href="${pageContext.request.contextPath}/report/radio/bsc-gprs-qos/hr/detail.htm?bscid=${vRpDyCellGprsQos.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCellGprsQos.day}"/>">${vRpDyCellGprsQos.bscid}</a>
			    </display:column>
			    <display:column property="cellid" titleKey="CELL" headerClass="hide" class="hide"/>
			    <display:column titleKey="CELL" media="html" sortable="true" sortProperty="cellid">
			    	<a href="${pageContext.request.contextPath}/report/radio/cell-gprs-qos/hr/detail.htm?cellid=${vRpDyCellGprsQos.cellid}&bscid=${vRpDyCellGprsQos.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCellGprsQos.day}"/>">${vRpDyCellGprsQos.cellid}</a>
			    </display:column>
			    <display:column property="dlTbfReq" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DL_TBF_REQ" sortable="true" sortName="DL_TBF_REQ"/>
			    <display:column property ="dlTbfSucr" titleKey="DL_TBF_SUCR" sortable="true" sortName="DL_TBF_SUCR"/>
			    <display:column property="ulTbfReq" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL_TBF_REQ" sortable="true" sortName="UL_TBF_REQ"/>
			    <display:column property="ulTbfSucr" titleKey="UL_TBF_SUCR" sortable="true" sortName="UL_TBF_SUCR"/>
			    <display:column property ="gdlTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GDL_TRAF" sortable="true" sortName="GDL_TRAF"/>
			    <display:column property="gulTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GUL_TRAF" sortable="true" sortName="GUL_TRAF"/>
			    <display:column property ="edlTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EDL_TRAF" sortable="true" sortName="EDL_TRAF"/>
			    <display:column property="eulTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EUL_TRAF" sortable="true" sortName="EUL_TRAF"/>
			</display:table>
</div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
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
	});
</script>
