<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>site qos monthly report</title>
<content tag="heading">SITE QOS REPORT ${siteid}</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/hr/details.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/dy/details.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/wk/details.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo tuần</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/site-qos/mn/details.htm?bscid=${bscid}&siteid=${siteid}&endMonth=${endMonth}&endYear=${endYear}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/dy/bhDetails.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/wk/bhDetails.htm?bscid=${bscid}&siteid=${siteid}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/site-qos/mn/bhDetails.htm?bscid=${bscid}&siteid=${siteid}&endMonth=${endMonth}&endYear=${endYear}"><span>Báo cáo BH tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	  <form method="get" action="details.htm" onSubmit="return ValidateFormYear()">
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
	            		&nbsp;Từ tháng <select name="startMonth" id="startMonth" onchange="xl()">
	            				<c:forEach var="month" items="${monthList}">
						              <c:choose>
						                <c:when test="${month == startMonth}">
						                    <option value="${month}" selected="selected">${month}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${month}">${month}</option>
						                </c:otherwise>
						              </c:choose>
						    </c:forEach>
			               	 </select>&nbsp;
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.startYear);">
	            	&nbsp;Tới tháng <select name="endMonth" id="endMonth" onchange="xl()">
	            				<c:forEach var="month" items="${monthList}">
						              <c:choose>
						                <c:when test="${month == endMonth}">
						                    <option value="${month}" selected="selected">${month}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${month}">${month}</option>
						                </c:otherwise>
						              </c:choose>
						    </c:forEach>
			               	 </select>&nbsp;
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.endYear);">
	            	&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
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
<display:table name="${vRpMnSiteQosDetails}" id="vRpMnSiteQosDetail" requestURI="" pagesize="100" class="simple2" export="true">
		<display:column property="region" titleKey="REGION"/>
	    <display:column property ="month" titleKey="MONTH" />
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


<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
<script type="text/javascript">
	function CalcKeyCode(aChar) {
	  var character = aChar.substring(0,1);
	  var code = aChar.charCodeAt(0);
	  return code;
	}

	function checkNumber(val) {
	  var strPass = val.value;
	  var strLength = strPass.length;
	  for(var i =0;i<strLength+1;i++){
	  var lchar = val.value.charAt((strLength) - i);
	  var cCode = CalcKeyCode(lchar);
	  if (cCode < 48 || cCode > 57 ) {
	    var myNumber = val.value.substring(0, (strLength) - i);
	    val.value = myNumber;
	  }
	  }
	  var sub = document.getElementById("submit");
		sub.focus();
	  return false;
	}
	function xl(){
		var sub = document.getElementById("submit");
		sub.focus();
	} 
	$(function() {
		${highlight}
	});
</script>

<script type="text/javascript">
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
