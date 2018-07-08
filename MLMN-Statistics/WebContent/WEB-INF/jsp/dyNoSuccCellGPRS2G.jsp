<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<title>NO SUCCESS CELL GPRS DAY REPORT</title>
<content tag="heading">NO SUCCESS CELL GPRS DAY REPORT</content>

<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-no-succ-gprs/hr.htm"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cell-no-succ-gprs/dy.htm?bscid=${bsc.bscid}&cellid=${cellid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-no-succ-gprs/wk.htm"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/cell-no-succ-gprs/mn.htm"><span>Báo cáo tháng</span></a></li>
</ul>

<div class="ui-tabs-panel">
	<form:form  method="get" commandName="filter" action="dy.htm" name = "frmSample" onSubmit = "return ValidateForm()">
		<table width ="100%" class="form">
			<tr>
			    <td align="left">
					 BSC
					<select name="bscid" id="bscid" onchange="xl()" >
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
					CELL <input value="${cellid}" name="cellid" id="cellid" size="10">
	                Từ Ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10"/>
	                Đến Ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id= "submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	</form:form>
	<br/>
</div>
<div  style="overflow: auto;">
		<display:table name="${vDyCellData2G}" id="vDyCellData2G" requestURI="" pagesize="100" class="simple3" export="true">
		    <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="Day" />
		    <display:column property="bscid"  titleKey="BSCID" />
		    <display:column property="cellid" titleKey="CELLID"/>
		    <display:column property="ulTbfReq" titleKey="UL_TBF_REQ"/>
		    <display:column property="ulTbfFail" titleKey="UL_TBF_FAIL"/>
		    <display:column property="ulTbfSucr" titleKey="UL_TBF_SUCR"/>
		    <display:column property="dlTbfReq" titleKey="DL_TBF_REQ"/>
		    <display:column property="dlTbfFail" titleKey="DL_TBF_FAIL"/>
		    <display:column property="dlTbfSucr" titleKey="DL_TBF_SUCR"/>
		    <display:column property="gprsUlData" titleKey="GPRS_UL_DATA"/>
		    <display:column property="gprsDlData" titleKey="GPRS_DL_DATA"/>
		    <display:column property="edgeUlData" titleKey="EDGE_UL_DATA"/>
		    <display:column property="edgeDlData" titleKey="EDGE_DL_DATA"/>		    
		    <display:column property="gprsUlDataThroughput" titleKey="GPRS_UL_DATA_THROUGHPUT"/>
		    <display:column property="gprsDlDataThroughput" titleKey="GPRS_DL_DATA_THROUGHPUT"/>
		    <display:column property="edgeUlDataThroughput" titleKey="EDGE_UL_DATA_THROUGHPUT"/>
		    <display:column property="edgeDlDataThroughput" titleKey="EDGE_DL_DATA_THROUGHPUT"/>
		</display:table>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>	
<script type="text/javascript">
	function xl(){
		var sub = document.getElementById("submit");
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
			minLength: 2,
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
