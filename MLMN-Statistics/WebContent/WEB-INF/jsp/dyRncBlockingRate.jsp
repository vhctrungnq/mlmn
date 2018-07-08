<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>RNC DAILY REPORT</title>
<content tag="heading">RNC BLOCKING RATE DAILY REPORT</content>

<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc-blocking-rate/hr.htm?bscid=${bsc.bscid}"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/rnc-blocking-rate/dy.htm?bscid=${bsc.bscid}&mscid=${msc.mscid}&region=${region}&startHour=${startHour}&startDate=${startDate}&endHour=${endHour}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc-blocking-rate/wk.htm?bscid=${bsc.bscid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc-blocking-rate/mn.htm?bscid=${bsc.bscid}"><span>Báo cáo tháng</span></a></li>
</ul>
  
<div class="ui-tabs-panel">
	<form:form method="get" commandName="filter" action="dy.htm" name="frmSample" onSubmit="return ValidateForm()">
		<table width="100%" class="form">
			<tr>
			    <td align="left">
					Trung tâm 
			  			<select name="region" id="region" onchange="xl()">
			              <c:choose>
			                <c:when test="${region == 'TT2'}">
								<option value=""> Tất cả </option>
								<option value="TT2" selected="selected"> TT2 </option>
			                    <option value="TT6"> TT6 </option>
			                </c:when>
			                <c:when test="${region == 'TT6'}">
								<option value=""> Tất cả </option>
								<option value="TT2"> TT2 </option>
			                    <option value="TT6" selected="selected"> TT6 </option>
			                </c:when>
			                <c:otherwise>
								<option value="" selected="selected"> Tất cả </option>
								<option value="TT2"> TT2 </option>
			                    <option value="TT6"> TT6 </option>
			                </c:otherwise>
			              </c:choose>
			            </select>&nbsp;
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
					</select>&nbsp;
			        &nbsp;RNC 
					 <select name="bscid" id="bscid" multiple="multiple" style="width: 163px">
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
	                &nbsp;&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id = "submit"value="View Report"/>
	            </td>
	        </tr>		
		</table>
	</form:form>
	</div>
	<br/>
<div  style="overflow: auto;">
		<display:table name="${vRpDyBsc3gQos}" id="vRpDyBsc3gQos" requestURI="" pagesize="100" class="simple3" export="true">		    
		    <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" />
		    <display:column property ="region" titleKey="REGION" />
		    <display:column property ="mscid" titleKey="MSC" />
		    <display:column property ="bscid"  titleKey="RNC" />
		    <display:column property ="sites"  titleKey="SITES" />
		    <display:column property ="cells"  titleKey="CELLS" />
		    <display:column property ="failRabEstattlackDlhwbest"  titleKey="FAIL_RAB_ESTATTLACK_DLHWBEST" />
		    <display:column property ="rabEstlsAttcs64"  titleKey="RAB_ESTLS_ATTCS64" />
		    <display:column property ="rabEstlsAttcs57"  titleKey="RAB_ESTLS_ATTCS57" />
		    <display:column property ="rabEstlsAttpktintact"  titleKey="RAB_ESTLS_ATTPKTINTACT" />
		    <display:column property ="rabEstlsAttpktstrm"  titleKey="RAB_ESTLS_ATTPKTSTRM" />
		    <display:column property ="rabEstlsAttpktstrm128"  titleKey="RAB_ESTLS_ATTPKTSTRM128" />
		    <display:column property ="rabEstlsAttpsstrmhs"  titleKey="RAB_ESTLS_ATTPSSTRMHS" />
		    <display:column property ="failRrcconnReqhw"  titleKey="FAIL_RRCCONN_REQHW" />
		    <display:column property ="totNorrcconnReq"  titleKey="TOT_NORRCCONN_REQ" />
		    <display:column property ="rabDowlbl"  titleKey="RAB_DOWLBL" />
		    <display:column property ="rabUplbl"  titleKey="RAB_UPLBL" />
		    <display:column property ="rrcbl"  titleKey="RRCBL" />
		</display:table>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript">
		function xl(){
			var sub = document.getElementById("submit");
			sub.focus();
		} 
	$(function() {
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.filter.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/assets/style.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/assets/prettify.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/jquery-ui.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.js"></script>
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.filter.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/assets/prettify.js"></script>	
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>

		<script type="text/javascript">
		$("#bscid").multiselect().multiselectfilter();
		</script>
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
