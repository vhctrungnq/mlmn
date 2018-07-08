<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>RNC HOURLY REPORT</title>
<content tag="heading">RNC CALL COMPLETION HOURLY REPORT</content>

<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/rnc-call-completion/hr.htm?bscid=${bsc.bscid}&mscid=${msc.mscid}&region=${region}&startHour=${startHour}&startDate=${startDate}&endHour=${endHour}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc-call-completion/dy.htm?bscid=${bsc.bscid}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc-call-completion/wk.htm?bscid=${bsc.bscid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc-call-completion/mn.htm?bscid=${bsc.bscid}"><span>Báo cáo tháng</span></a></li>
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
		    <display:column property ="totNorConReqCssucc"  titleKey="TOT_NOR_CON_REQ_CSSUCC" />
		    <display:column property ="normNassignRelsCs"  titleKey="NORM_NASSIGN_RELS_CS" />
		    <display:column property ="rabEstlsSuccCs64"  titleKey="RAB_ESTLS_SUCC_CS64" />
		    <display:column property ="sysNasSignRelscs"  titleKey="SYS_NAS_SIGN_RELSCS" />
		    <display:column property ="rabEstlsAttCs64"  titleKey="RAB_ESTLS_ATT_CS64" />
		    <display:column property ="norRabRelCs64"  titleKey="NOR_RAB_REL_CS64" />
		    <display:column property ="cs64AccSuccr"  titleKey="CS64_ACC_SUCCR" />
		    <display:column property ="cs64Drpr"  titleKey="CS64_DRPR" />
		    <display:column property ="cs64Cssr"  titleKey="CS64_CSSR" />
		    <display:column property ="rabEstlsSuccCs57"  titleKey="RAB_ESTLS_SUCC_CS57" />
		    <display:column property ="rabEstlsAttCs57"  titleKey="RAB_ESTLS_ATT_CS57" />
		    <display:column property ="totRrcConnReqcs"  titleKey="TOT_RRC_CONN_REQCS" />
		    <display:column property ="rabSysRecs57Strm"  titleKey="RAB_SYS_RECS57_STRM" />		    
		    <display:column property ="cs57AccSuccr"  titleKey="CS57_ACC_SUCCR" />
		    <display:column property ="cs57Drpr"  titleKey="CS57_DRPR" />
		    <display:column property ="cs57Cssr"  titleKey="CS57_CSSR" />
		    <display:column property ="rabEstlsSuccPkstr"  titleKey="RAB_ESTLS_SUCC_PKSTR" />
		    <display:column property ="rabEstlsAttPkstr"  titleKey="RAB_ESTLS_ATT_PKSTR" />
		    <display:column property ="rabNorRepkStrm"  titleKey="RAB_NOR_REPK_STRM" />
		    <display:column property ="rabSysRepkStrm"  titleKey="RAB_SYS_REPK_STRM" />
		    <display:column property ="ps64StrmDropr"  titleKey="PS64_STRM_DROPR" />
		    <display:column property ="psStr64AccSucc"  titleKey="PS_STR64_ACC_SUCC" />
		    <display:column property ="psStr64Cssr"  titleKey="PS_STR64_CSSR" />
		    <display:column property ="rabEstlsAttpktstrm128"  titleKey="RAB_ESTLS_ATTPKTSTRM128" />
		    <display:column property ="norRabEstlsSuccpktstrm128"  titleKey="NOR_RAB_ESTLS_SUCCPKTSTRM128" />
		    <display:column property ="sysRabRelsPktstrm128"  titleKey="SYS_RAB_RELS_PKTSTRM128" />
		    <display:column property ="norRabRelsPktstrm128"  titleKey="NOR_RAB_RELS_PKTSTRM128" />
		    <display:column property ="psStr128AccSucc"  titleKey="PS_STR128_ACC_SUCC" />
		    <display:column property ="psStr128Droprate"  titleKey="PS_STR128_DROPRATE" />
		    <display:column property ="psStr128Cssr"  titleKey="PS_STR128_CSSR" />
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
