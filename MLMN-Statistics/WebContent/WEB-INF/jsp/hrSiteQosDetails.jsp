<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Site qos hourly report</title>
<content tag="heading">REPORT QOS SITE ${siteid}</content>
<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/site-qos/hr/details.htm?bscid=${bscid}&siteid=${siteid}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/dy/details.htm?bscid=${bscid}&siteid=${siteid}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/wk/details.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/mn/details.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/dy/bhDetails.htm?bscid=${bscid}&siteid=${siteid}&endDate=${endDate}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/wk/bhDetails.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/mn/bhDetails.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo BH tháng</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="details.htm" name = "frmSample" onSubmit = "return ValidateForm()">
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
	            	&nbsp;Từ <select name="startHour" id="startHour" onchange="xl()">
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
<display:table name="${vRpHrSiteQosDetails}" id="vRpHrSiteQosDetail" requestURI="" pagesize="100" class="simple2" export="true">
				<display:column property="region" titleKey="TT"/>
	    <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY"/>
	    <display:column titleKey="HOUR" >
	    	${vRpHrSiteQosDetail.hour}:00
	    </display:column>
	    <display:column property="bscid" titleKey="BSCID" headerClass="hide" class="hide"/> 
	    <display:column titleKey="BSC" media="html">
	    	<a href="${pageContext.request.contextPath}/report/radio/bsc/hr/detail.htm?bscid=${vRpHrSiteQosDetail.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrSiteQosDetail.day}"/>">${vRpHrSiteQosDetail.bscid}</a>
	    </display:column> 
	    <display:column property="siteid" titleKey="SITEID"/>
	    <display:column property ="tDef" titleKey="T_DEF" />
	    <display:column property="tAvail" titleKey="T_AVAIL" />
	    <display:column property="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF"/>
	    <display:column property ="haftratePercent" titleKey="HAFT RATE (%)" />
	    <display:column property="tAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_ATTS" />
	    <display:column property ="tSeizs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_SEIZS"/>
	    <display:column property="tDrps" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_DRPS" />
	    <display:column property ="tDrpr" titleKey="T_DRPR"/>
	    <display:column property="tBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_BLKS"/>
	    <display:column property="tBlkr" titleKey="T_BLKR" class="T_BLKR"/>
	    <display:column property="tHoblks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_HOBLKS"/>
	    <display:column property="tHoblkr" titleKey="T_HOBLKR"/>
	    <display:column property="cssr" titleKey="CSSR"/>
	    <display:column property="sDef" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_DEF" />
	    <display:column property ="sAvail" titleKey="S_AVAIL" />
	    <display:column property="sAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_ATTS"/>
	    <display:column property ="sSeizs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_SEIZS"/>
	    <display:column property="sDrps" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_DRPS" />			    
	    <display:column property ="sDrpr" titleKey="S_DRPR"/>
	    <display:column property="sBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_BLKS"/>			    
	    <display:column property ="sBlkr" titleKey="S_BLKR"/>
	</display:table>
</div>
	
	<div id="container" style="width: 1000px; margin: 1em auto;"></div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
${chart}

<script type="text/javascript">
function xl(){
	var sub= document.getElementById("submit");
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
				for (var i=7;i<=25;i++)
				{
					$('#vRpHrSiteQosDetail td:nth-child('+i+'),#vRpHrSiteQosDetail th:nth-child('+i+')').show();
				}
			} else {
				for (var i=7;i<=25;i++)
				{
					$('#vRpHrSiteQosDetail td:nth-child('+i+'),#vRpHrSiteQosDetail th:nth-child('+i+')').hide();
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
