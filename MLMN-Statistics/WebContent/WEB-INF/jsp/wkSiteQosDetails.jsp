<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>site qos weekly report</title>
<content tag="heading">SITE QOS WEEKLY REPORT ${siteid}</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/hr/details.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/dy/details.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo ngày</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/site-qos/wk/details.htm?bscid=${bscid}&siteid=${siteid}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/mn/details.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/dy/bhDetails.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/wk/bhDetails.htm?bscid=${bscid}&siteid=${siteid}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/mn/bhDetails.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo BH tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	  <form method="get" action="details.htm">
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
	            	&nbsp;&nbsp;Từ tuần <input value="${startWeek}" name="startWeek" id="startWeek" size="2" maxlength="2">
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
<br/>
   	<div  style="overflow: auto;">
<display:table name="${vRpWkSiteQosDetails}" id="vRpWkSiteQosDetail" requestURI="" pagesize="100" class="simple2" export="true">
		<display:column property="region" titleKey="REGION"/>
	    <display:column property ="week" titleKey="WEEK" />
	    <display:column property ="year" titleKey="YEAR" />
	    <display:column property ="bscid" titleKey="BSCID"/> 
	    <display:column property ="siteid" titleKey="SITEID"/> 
			    <display:column property="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF"/>
			    <display:column property="tDrps" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_DRPS" />
			    <display:column property ="tDrpr" titleKey="T_DRPR"/>
			    <display:column property="tBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_BLKS"/>
			    <display:column property="tBlkr" titleKey="T_BLKR" class="T_BLKR"/>
			    <display:column property="tHoblks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_HOBLKS"/>
			    <display:column property="tHoblkr" titleKey="T_HOBLKR"/>
			    <display:column property="cssr" titleKey="CSSR"/>
			    <display:column property="sDrps" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_DRPS" />			    
			    <display:column property ="sDrpr" titleKey="S_DRPR" />
			    <display:column property="sBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_BLKS"/>			    
			    <display:column property ="sBlkr" titleKey="S_BLKR" />
			    <display:column property="dataload" titleKey="DATALOAD" />
	</display:table>
</div>
	
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
				for (var i=6;i<=16;i++)
				{
					$('#vRpMnSiteQosDetail td:nth-child('+i+'),#vRpMnSiteQosDetail th:nth-child('+i+')').show();
				}
			} else {
				for (var i=6;i<=16;i++)
				{
					$('#vRpMnSiteQosDetail td:nth-child('+i+'),#vRpMnSiteQosDetail th:nth-child('+i+')').hide();
				}
			}
		});

		$( '.cb-element' ).live( 'change', function() {
			$( '.cb-element' ).length == $( '.cb-element:checked' ).length ? $( '.checkAll' ).attr( 'checked', 'checked' ).next().text( 'Uncheck All' ) : $( '.checkAll' ).attr( 'checked', '' ).next().text( 'Check All' );

		});

		${checkColumns}

		${checkSeries}
		
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
