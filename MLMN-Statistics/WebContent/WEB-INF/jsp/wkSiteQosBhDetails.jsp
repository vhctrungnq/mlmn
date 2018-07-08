<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>site qos weekly report</title>
<content tag="heading">QOS SITE WEEKLY REPORT ${siteid}</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="#"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/dy/details.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/wk/details.htm?bscid=${bscid}&siteid=${siteid}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/mn/details.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/dy/bhDetails.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo BH ngày</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/site-qos/wk/bDdetails.htm?bscid=${bscid}&siteid=${siteid}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/mn/bhDetails.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo BH tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	  <form method="get" action="bhDetails.htm">
		<table width="100%" class="form">
			<tr>
				<td align="left">
					Trung tâm 
			  			<select name="region" id="region">
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
			            </select>
			        BSC 
			        <select name="bscid" id="bscid">
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
			        <select name="siteid" id="siteid">
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
	            	Từ tuần <input value="${startWeek}" name="startWeek" id="startWeek" size="2">
					<img alt="calendar" title="Click to choose the week number" id="chooseStartWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4">
	            	&nbsp;Tới tuần <input value="${endWeek}" name="endWeek" id="endWeek" size="2">
					<img alt="calendar" title="Click to choose the week number" id="chooseEndWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4">
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
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
	<display:table name="${vRpWkSiteQosBhDetails}" id="vRpWkSiteQosBhDetails" requestURI="" pagesize="100" class="simple2" export="true">
		<display:column property="region" titleKey="REGION"/>
		<display:column property ="week" titleKey="WEEK"/>
		<display:column property ="year" titleKey="YEAR"/>
	    <display:column property="bscid" titleKey="BSC"/> 
	    <display:column property="siteid" titleKey="SITE"/>
	    <display:column property ="bhTDef" titleKey="T_DEF" />
	    <display:column property="bhTAvail" titleKey="T_AVAIL" />
	    <display:column property ="bhTAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_ATTS" />
	    <display:column property ="bhTTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="BH_T_TRAF" />
	    <display:column property ="bhTTrafh" titleKey="T_TRAFH" />
	    <display:column property ="bhTDrps" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_DRPS" />
	    <display:column property="bhTDrpr" titleKey="T_DRPR" />
	    <display:column property="bhTBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_BLKS"/>
	    <display:column property="bhTBlkr" titleKey="T_BLKR"/>
	    <display:column property="bhTHoblks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_HOBLKS"/>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
    Calendar.setup({
        inputField		:	"startWeek",	// id of the input field
        ifFormat		:	"%W",   	// format of the input field
        button			:   "chooseStartWeek",  	// trigger for the calendar (button ID)
        singleClick		:   false					// double-click mode
    });
    Calendar.setup({
        inputField		:	"endWeek",	// id of the input field
        ifFormat		:	"%W",   	// format of the input field
        button			:   "chooseEndWeek",  	// trigger for the calendar (button ID)
        singleClick		:   false					// double-click mode
    });
	$(function() {
		$( '.checkAll' ).live( 'change', function() {
			$( '.cb-element' ).attr( 'checked', $( this ).is( ':checked' ) ? 'checked' : '' );
			$( this ).next().text( $( this ).is( ':checked' ) ? 'Uncheck All' : 'Check All' );
			if($( this ).is( ':checked' )){
				for (var i=6;i<=23;i++)
				{
					$('#vRpMnSiteQosBhDetail td:nth-child('+i+'),#vRpMnSiteQosBhDetail th:nth-child('+i+')').show();
				}
			} else {
				for (var i=6;i<=23;i++)
				{
					$('#vRpMnSiteQosBhDetail td:nth-child('+i+'),#vRpMnSiteQosBhDetail th:nth-child('+i+')').hide();
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
