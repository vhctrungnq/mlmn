<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title><fmt:message key="sidebar.site.qos"/></title>
<content tag="heading"><fmt:message key="sidebar.site.qos"/> ${siteid}</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/hr/details.htm?bscid=${bscid}&siteid=${siteid}&day=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/dy/details.htm?bscid=${bscid}&siteid=${siteid}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/wk/details.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/mn/details.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo tháng</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/site-qos/dy/bhDetails.htm?bscid=${bscid}&siteid=${siteid}&endDate=${endDate}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/wk/bhDetails.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/mn/bhDetails.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo BH tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	  <form method="get" action="bhDetails.htm" name="frmSample" onSubmit="return ValidateForm()">
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
			        BSC 
			        <select name="bscid" id="bscid" onchange="xl()">
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
				    &nbsp;&nbsp;SITE 
			        <select name="siteid" id="siteid" onchange="xl()">
						<option  value="">--Select Site--</option>
				        <c:forEach var="site" items="${siteList}">
			              <c:choose>
			                <c:when test="${site == siteid}">
			                    <option value="${site}" selected="selected">${site}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${site}">${site}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
					</select>
	            	Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	            	&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	  </form>
	<br/>
	<table class="form">
	    	<tr>
	    		<td>
	    			<b>Chọn chỉ số hiển thị: </b>
	    		</td>
	    	</tr>
	        <tr>
	        	<td>${checkColumns}</td>
			</tr>
		</table>
	<br/>

	<div  style="overflow: auto;">
	<display:table name="${vRpDySiteQosBhDetails}" id="vRpDySiteQosBhDetail" requestURI="" pagesize="100" class="simple2" export="true">
				<display:column property="region" titleKey="TT"/>
		<display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY"/>
		<display:column titleKey="BH">
			${vRpDySiteQosBhDetail.bh}:00
		</display:column>
		<display:column property="bscid" titleKey="BSC" headerClass="hide" class="hide"/>
	    <display:column titleKey="BSC" media="html">
	    	<a href="${pageContext.request.contextPath}/report/radio/bsc/dy/bh.htm?bscid=${vRpDySiteQosBhDetail.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDySiteQosBhDetail.day}"/>">${vRpDySiteQosBhDetail.bscid}</a>
	    </display:column> 
	    <display:column property="siteid" titleKey="SITEID" headerClass="hide" class="hide"/>
	    <display:column titleKey="SITEID" media="html">
	    	<a href="${pageContext.request.contextPath}/report/radio/site-qos/hr/details.htm?bscid=${vRpDySiteQosBhDetail.bscid}&siteid=${vRpDySiteQosBhDetail.siteid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDySiteQosBhDetail.day}"/>">${vRpDySiteQosBhDetail.siteid}</a>
	    </display:column>
	    <display:column property ="bhTDef" titleKey="T_DEF" />
	    <display:column property="bhTAvail" titleKey="T_AVAIL" />
	    <display:column property ="bhTAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_ATTS" />
	    <display:column property ="bhTTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF" />
	    <display:column property ="bhTTrafh" titleKey="H_TRAFF" />
	    <display:column property ="bhTDrps" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_DRPS" />
	    <display:column property="bhTDrpr" titleKey="T_DRPR" />
	    <display:column property="bhTBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_BLKS"/>
	    <display:column property="bhTBlkr" titleKey="T_BLKR"/>
	    <display:column property="bhTHoblkr" titleKey="T_HOBLKR"/>
	    <display:column property="bhCssr" titleKey="CSSR"/> 
	    <display:column property="bhTGos" titleKey="GoS (%)" />
	    <display:column property ="bhSDef" titleKey="S_DEF" />
	    <display:column property="bhSAvail" titleKey="S_AVAIL" />
	    <display:column property ="bhSAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_ATTS" />
	    <display:column property ="bhSDrps" titleKey="S_DRPS" />
	    <display:column property ="bhSDrpr" titleKey="S_DRPR" />
	    <display:column property ="bhSBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_BLKS"/>
	    <display:column property="bhSBlkr" titleKey="S_BLKR" />
	</display:table>
	</div>
	<br/>
	<div id="container" style="width: 1000px; margin: 1em auto;"></div>
</div>

${chart}

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
		
		$( '.checkAll' ).live( 'change', function() {
			$( '.cb-element' ).attr( 'checked', $( this ).is( ':checked' ) ? 'checked' : '' );
			$( this ).next().text( $( this ).is( ':checked' ) ? 'Uncheck All' : 'Check All' );
			if($( this ).is( ':checked' )){
				for (var i=7;i<=24;i++)
				{
					$('#vRpDySiteQosBhDetail td:nth-child('+i+'),#vRpDySiteQosBhDetail th:nth-child('+i+')').show();
				}
			} else {
				for (var i=7;i<=24;i++)
				{
					$('#vRpDySiteQosBhDetail td:nth-child('+i+'),#vRpDySiteQosBhDetail th:nth-child('+i+')').hide();
				}
			}
		});

		$( '.cb-element' ).live( 'change', function() {
			$( '.cb-element' ).length == $( '.cb-element:checked' ).length ? $( '.checkAll' ).attr( 'checked', 'checked' ).next().text( 'Uncheck All' ) : $( '.checkAll' ).attr( 'checked', '' ).next().text( 'Check All' );

		});
		
		$("select#bscid").change(function(){
			$.getJSON("${pageContext.request.contextPath}/ajax/getSiteOfBsc.htm",{bscid: $(this).val()}, function(j){
				var options = '<option  value="">--Select Site--</option>';
				for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i] + '">' + j[i] + '</option>';
				}
				$("#siteid").html(options);
				$('#siteid option:first').attr('selected', 'selected');
			})
		})
	});
</script>
