<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title> RNC HOURLY REPORT</title>
<content tag="heading">RNC ACCESSIBILITY HOURLY REPORT</content>

<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/rnc-access/hr.htm?bscid=${bsc.bscid}&mscid=${msc.mscid}&region=${region}&startHour=${startHour}&startDate=${startDate}&endHour=${endHour}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc-access/dy.htm?bscid=${bsc.bscid}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc-access/wk.htm?bscid=${bsc.bscid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/rnc-access/mn.htm?bscid=${bsc.bscid}"><span>Báo cáo tháng</span></a></li>
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
		    <display:column property ="totRrcConreqPssucc"  titleKey="TOT_RRC_CONREQ_PSSUCC" />
		    <display:column property ="normNassignRelsCs"  titleKey="NORM_NASSIGN_RELS_CS" />
		    <display:column property ="rabEstlshSuccSpch"  titleKey="RAB_ESTLSH_SUCC_SPCH" />
		    <display:column property ="totRrcConnReqcs"  titleKey="TOT_RRC_CONN_REQCS" />
		    <display:column property ="sysNasSignRelscs"  titleKey="SYS_NAS_SIGN_RELSCS" />
		    <display:column property ="rabEstlsAttSpch"  titleKey="RAB_ESTLS_ATT_SPCH" />
		    <display:column property ="rabEstlsSuccCs64"  titleKey="RAB_ESTLS_SUCC_CS64" />
		    <display:column property ="rabEstlsAttCs64"  titleKey="RAB_ESTLS_ATT_CS64" />
		    <display:column property ="cs64AccSuccr"  titleKey="CS64_ACC_SUCCR" />
		    <display:column property ="rabEstlsSuccCs57"  titleKey="RAB_ESTLS_SUCC_CS57" />
		    <display:column property ="rabEstlsAttCs57"  titleKey="RAB_ESTLS_ATT_CS57" />
		    <display:column property ="cs57AccSuccr"  titleKey="CS57_ACC_SUCCR" />
		    <display:column property ="rabEstlsSuccPkstr"  titleKey="RAB_ESTLS_SUCC_PKSTR" />		    
		    <display:column property ="rabEstlsAttPkstr"  titleKey="RAB_ESTLS_ATT_PKSTR" />
		    <display:column property ="csSpchAccsuccr"  titleKey="CS_SPCH_ACCSUCCR" />
		    <display:column property ="psStr64AccSucc"  titleKey="PS_STR64_ACC_SUCC" />
		    <display:column property ="rabEstlsSuccPsstrhs"  titleKey="RAB_ESTLS_SUCC_PSSTRHS" />
		    <display:column property ="rabEstlsAttPsstrhs"  titleKey="RAB_ESTLS_ATT_PSSTRHS" />
		    <display:column property ="psstrHsAccSucc"  titleKey="PSSTR_HS_ACC_SUCC" />
		    <display:column property ="norNasgReleaps"  titleKey="NOR_NASG_RELEAPS" />
		    <display:column property ="rabEstlsSuccPkintevi"  titleKey="RAB_ESTLS_SUCC_PKINTEVI" />
		    <display:column property ="totNorRcconnReqps"  titleKey="TOT_NOR_RCCONN_REQPS" />
		    <display:column property ="sysNasgReleaps"  titleKey="SYS_NASG_RELEAPS" />
		    <display:column property ="rabEstlsAttPkintevi"  titleKey="RAB_ESTLS_ATT_PKINTEVI" />
		    <display:column property ="psDataInteviAccsucc"  titleKey="PS_DATA_INTEVI_ACCSUCC" />
		    <display:column property ="psdataintevidchFachaccucc"  titleKey="PSDATAINTEVIDCH_FACHACCUCC" />
		    <display:column property ="psDataInteviHsAccsucc"  titleKey="PS_DATA_INTEVI_HS_ACCSUCC" />
		    <display:column property ="psDataInteviEulAccsucc"  titleKey="PS_DATA_INTEVI_EUL_ACCSUCC" />
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

