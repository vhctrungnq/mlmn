<%@ include file="/commons/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<title>bcs core list</title>
<content tag="heading">BSC REPORT</content>
<!-- 
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/core/bsc/hr/list.htm"><span>Báo cáo giờ</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/bsc/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/bsc/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/bsc/mn/list.htm"><span>Báo cáo tháng</span></a></li>
</ul>
 -->
<div class="ui-tabs-panel">

<form method="get" action="list.htm" name = "frmSample" onSubmit = "return ValidateForm()">
<table width="100%" class="form">
	<tr>
		<td align="left">
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
		&nbsp;&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10" />
		&nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10" />
		&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report" />
		</td>
	</tr>
</table>
</form>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr height="5">
		<td colspan="2" />
	</tr>
	<tr>
		<td colspan="2">
		  <div style="overflow: auto;">
			<display:table name="${dyBscCore}" id="bsc" requestURI="" pagesize="100" class="simple2" export="true">
				<display:column property="bscid" titleKey="BSC" />
				<display:column property="cssr" titleKey="CSSR" />
				<display:column property="psr" titleKey="PSR" />
				<display:column property="dcrs" titleKey="DCRS" />
				<display:column property="dcrt" titleKey="DCRT" />
				<display:column property="traucr" titleKey="TRAUCR" />
				<display:column property="hsr" titleKey="HSR" />
				<display:column title="Báo cáo" media="html">
					<a href="main.htm?bscid=${bsc.bscid}&day=<fmt:formatDate pattern="dd/MM/yyyy" value="${bsc.day}"/>"> Báo cáo</a>&nbsp;
				</display:column>
			</display:table>
		</div></td>
	</tr>
</table>
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
