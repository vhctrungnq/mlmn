<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>BSC DAY HANDOVER</title>
<content tag="heading">BSC LOW SUCCESS HANDOVER DAILY REPORT </content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/2g/bsc-low-succ/hr.htm"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/2g/bsc-low-succ/dy.htm?bscid=${bscid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/2g/bsc-low-succ/wk.htm"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/2g/bsc-low-succ/mn.htm"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
				  <form:form method="get" commandName="filter" action="dy.htm" name="frmSample" onSubmit="return ValidateForm()">
				        BSC
					<select name="bscid" id="bscid" style="width: 163px">
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
		            	Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
		            	&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
		                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
		          </form:form>
	            </td>
	        </tr>		
		</table>
	<br/>
		<div  style="overflow: auto;">
			<display:table name="${vRpDyBscLSHo}" id="vRpDyBscLSHo" requestURI="" pagesize="100" class="simple2" export="true">
		    <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY"/>
		    <display:column property="bscid" titleKey="BSC"/>
		    <display:column property ="ogHoAtt" titleKey="OG_HO_ATT" />
		    <display:column property="ogHoSuc" titleKey="OG_HO_SUC"/> 
		    <display:column property ="ogHoSucr" titleKey="OG_HO_SUCR" />
		    <display:column property="inHoAtt" titleKey="IN_HO_ATT" />
		    <display:column property="inHoSuc"  titleKey="IN_HO_SUC"/>
		    <display:column property ="inHoSucr" titleKey="IN_HO_SUCR" />
		</display:table>
	</div>	
</div>



<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type = "text/javascript">
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
		
		var cache = {},
		lastXhr;
		$( "#bscid" ).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cache ) {
					response( cache[ term ] );
					return;
				}
				lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getBsc.htm", request, function( data, status, xhr ) {
					cache[ term ] = data;
					if ( xhr === lastXhr ) {
						response( data );
					}
				});
			}
		});
	});
</script>
