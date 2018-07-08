<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>RNC HOURLY REPORT</title>
<content tag="heading">RNC DROPPED RATE HOURLY REPORT</content>

<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/rnc-drop-rate/hr.htm?bscid=${bsc.bscid}&mscid=${msc.mscid}&region=${region}&startHour=${startHour}&startDate=${startDate}&endHour=${endHour}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc-drop-rate/dy.htm?"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc-drop-rate/wk.htm?"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc-drop-rate/mn.htm?"><span>Báo cáo tháng</span></a></li>
</ul>
  
<div class="ui-tabs-panel">
	<form:form method="get" commandName="filter" action="hr.htm" name="frmSample" onSubmit="return ValidateForm()">
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
	                Từ <select name="startHour" id="startHour" onchange="xl()">
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
	                Ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10"/>
	                Đến <select name="endHour" id="endHour" onchange="xl()">
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
	                Ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10"/>
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
		   	<display:column property ="hour" format="{0,number}:00" titleKey="HOUR" />
		   	<display:column property ="region"  titleKey="REGION" />
		   	<display:column property ="mscid"  titleKey="MSC" />
		    <display:column property ="bscid"  titleKey="RNC" />
		    <display:column property ="sites"  titleKey="SITES" />
		    <display:column property ="cells"  titleKey="CELLS" />
		    <display:column property ="norRabRelSpeeh"  titleKey="NOR_RAB_REL_SPEEH" />
		    <display:column property ="speechDrop"  titleKey="SPEECH_DROP" />
		    <display:column property ="speechDrpr"  titleKey="SPEECH_DRPR" />
		    <display:column property ="norRabRelCs64"  titleKey="NOR_RAB_REL_CS64" />
		    <display:column property ="cs64Drop"  titleKey="CS64_DROP" />
		    <display:column property ="cs64Drpr"  titleKey="CS64_DRPR" />
		    <display:column property ="rabNorRecs57Strm"  titleKey="RAB_NOR_RECS57_STRM" />
		    <display:column property ="rabSysRecs57Strm"  titleKey="RAB_SYS_RECS57_STRM" />
		    <display:column property ="cs57StrmDropr"  titleKey="CS57_STRM_DROPR" />
		    <display:column property ="rabNorRepkStrm"  titleKey="RAB_NOR_REPK_STRM" />
		    <display:column property ="rabSysRepkStrm"  titleKey="RAB_SYS_REPK_STRM" />
		    <display:column property ="ps64StrmDropr"  titleKey="PS64_STRM_DROPR" />
		    <display:column property ="rabNorRepkStrm128"  titleKey="RAB_NOR_REPK_STRM128" />		    
		    <display:column property ="rabSysRepkStrm128"  titleKey="RAB_SYS_REPK_STRM128" />
		    <display:column property ="psStrm128Dropr"  titleKey="PS_STRM128_DROPR" />
		    <display:column property ="rabNorReStrmhs"  titleKey="RAB_NOR_RE_STRMHS" />
		    <display:column property ="rabSysReStrmhs"  titleKey="RAB_SYS_RE_STRMHS" />
		    <display:column property ="psStrmHsDropr"  titleKey="PS_STRM_HS_DROPR" />
		    <display:column property ="rabSysRepk"  titleKey="RAB_SYS_REPK" />
		    <display:column property ="rabSysRepkUra"  titleKey="RAB_SYS_REPK_URA" />
		    <display:column property ="rabNorRepk"  titleKey="RAB_NOR_REPK" />
		    <display:column property ="rabNorRepkUra"  titleKey="RAB_NOR_REPK_URA" />
		    <display:column property ="psInterativeDropr"  titleKey="PS_INTERATIVE_DROPR" />
		    <display:column property ="rbSysReHs"  titleKey="RB_SYS_RE_HS" />
		    <display:column property ="rbNorReHs"  titleKey="RB_NOR_RE_HS" />
		    <display:column property ="psHsInterativeDropr"  titleKey="PS_HS_INTERATIVE_DROPR" />
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
