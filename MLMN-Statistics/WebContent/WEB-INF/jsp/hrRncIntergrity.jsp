<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title> RNC HOURLY REPORT</title>
<content tag="heading">RNC INTERGRITY HOURLY REPORT</content>

<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/rnc-integrity/hr.htm?bscid=${bsc.bscid}&mscid=${msc.mscid}&region=${region}&startHour=${startHour}&startDate=${startDate}&endHour=${endHour}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc-integrity/dy.htm?bscid=${bsc.bscid}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc-integrity/wk.htm?bscid=${bsc.bscid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc-integrity/mn.htm?bscid=${bsc.bscid}"><span>Báo cáo tháng</span></a></li>
</ul>
  
<div class="ui-tabs-panel">
	<form:form method="get" commandName="filter" action="hr.htm" name = "frmSample" onSubmit = "return ValidateForm()">
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			    Trung tâm 
			  	<select name="region" id="region" onchange="xl()">
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
			        MSC
					<select name="mscid" id="mscid"  onchange="xl()">
						<option value="">--Select MSC--</option>
				        <c:forEach var="msc" items="${mscList}">
			              <c:choose>
			                <c:when test="${msc.mscid == mscid}">
			                    <option value="${msc.mscid}" selected="selected">${msc.mscid}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${msc.mscid}">${msc.mscid}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
					</select>
			        RNC <form:input path="bscid" size="10"/>&nbsp;&nbsp;
	                 &nbsp;Từ <select name="startHour" id="startHour"onchange="xl()">
	            				<c:forEach var="hour" items="${hourList}">
						              <c:choose>
						                <c:when test="${hour == startHour}">
						                    <option value="${hour}" selected="selected">${hour}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${hour}">${hour}</option>
						                </c:otherwise>
						              </c:choose>
						    </c:forEach>
			               	 </select>&nbsp; giờ
	                &nbsp;<input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10"/>
	                &nbsp;Đến <select name="endHour" id="endHour" onchange="xl()">
	            				<c:forEach var="hour" items="${hourList}">
						              <c:choose>
						                <c:when test="${hour == endHour}">
						                    <option value="${hour}" selected="selected">${hour}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${hour}">${hour}</option>
						                </c:otherwise>
						              </c:choose>
						    </c:forEach>
			               	 </select>&nbsp;  giờ
	                &nbsp;<input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	</form:form>
	</div>
	<br/>
	
	
<div  style="overflow: auto;">
		<display:table name="${vRpHrBsc3gQos}" id="vRpHrBsc3gQos" requestURI="" pagesize="100" class="simple3" export="true">
		    <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" />
		    <display:column property ="hour" titleKey="HOUR" />
		    <display:column property ="region" titleKey="REGION" />
		    <display:column property ="mscid" titleKey="MSC" />
		    <display:column property ="bscid"  titleKey="RNC" />
		    <display:column property ="sites"  titleKey="SITES" />
		    <display:column property ="cells"  titleKey="CELLS" />
		    <display:column property ="sumDchDlrlcUserPktthp"  titleKey="SUM_DCH_DLRLC_USER_PKTTHP" />
		    <display:column property ="samDchDlrlcUserpktthp"  titleKey="SAM_DCH_DLRLC_USERPKTTHP" />
		    <display:column property ="psdchFachdownlinkthp"  titleKey="PSDCH_FACHDOWNLINKTHP" />
		    <display:column property ="sumDchUlrlcUserpktthp"  titleKey="SUM_DCH_ULRLC_USERPKTTHP" />
		    <display:column property ="samDchUlrlcUserpktthp"  titleKey="SAM_DCH_ULRLC_USERPKTTHP" />
		    <display:column property ="psdchFachuplthp"  titleKey="PSDCH_FACHUPLTHP" />
		    <display:column property ="sumDlrlcUserthpPsstrm64"  titleKey="SUM_DLRLC_USERTHP_PSSTRM64" />
		    <display:column property ="samDlrlcUserthppsstrm64"  titleKey="SAM_DLRLC_USERTHPPSSTRM64" />
		    <display:column property ="psStr64Dowlthp"  titleKey="PS_STR64_DOWLTHP" />
		    <display:column property ="sumDlrlcUserThppsstrm128"  titleKey="SUM_DLRLC_USER_THPPSSTRM128" />
		    <display:column property ="samDlrlcUserthppsstrm128"  titleKey="SAM_DLRLC_USERTHPPSSTRM128" />
		    <display:column property ="psStr128Dowlthp"  titleKey="PS_STR128_DOWLTHP" />
		    <display:column property ="sumUlrlcUserThppsstrm16"  titleKey="SUM_ULRLC_USER_THPPSSTRM16" />		    
		    <display:column property ="samUlrlcUserThppsstrm16"  titleKey="SAM_ULRLC_USER_THPPSSTRM16" />
		    <display:column property ="psUplStr16thp"  titleKey="PS_UPL_STR16THP" />
		    <display:column property ="sumUlrlcUserThppsstrm32"  titleKey="SUM_ULRLC_USER_THPPSSTRM32" />
		    <display:column property ="samUlrlcUserThppsstrm32"  titleKey="SAM_ULRLC_USER_THPPSSTRM32" />
		    <display:column property ="psUplstr128thp"  titleKey="PS_UPLSTR128THP" />
		</display:table>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>		
<script type = "text/javascript">
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
		$("#endDate").datepicker({
			dateFormat: "dd/mm/yy",
			showOn:"button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
		var cachebsc = {},
		lastXhrBsc;
		$( "#bscid" ).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cachebsc ) {
					response( cachebsc[ term ] );
					return;
				}
				
				lastXhrBsc = $.getJSON( "${pageContext.request.contextPath}/ajax/getBsc3G.htm", request, function( data, status, xhr ) {
					cachebsc[ term ] = data;
					if ( xhr === lastXhrBsc ) {
						response( data );
					}
				});
			}
		});
		var cache = {},
		lastXhr;
		$( "#mscid" ).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cache ) {
					response( cache[ term ] );
					return;
				}
				lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getMsc.htm", request, function( data, status, xhr ) {
					cache[ term ] = data;
					if ( xhr === lastXhr ) {
						response( data );
					}
				});
			}
		});
	});
</script>

