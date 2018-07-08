<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<title>MSC DAY REPORT</title>
<content tag="heading">CENTER QOS DAILY REPORT</content>
 
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/center/hr.htm?region=${temp.region}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo Giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/center/dy.htm?region=${temp.region}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo Ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/center/mn.htm?region=${temp.region}"><span>Báo cáo Tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/center/qr.htm?region=${temp.region}"><span>Báo cáo Quý</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/center/yr.htm?region=${temp.region}"><span>Báo cáo Năm</span></a></li>
</ul>
<div class="ui-tabs-panel">
	<form  method="get" action="dy.htm" name = "frmSample" onSubmit = "return ValidateForm()">
		<table style="width: 100%;" class="form">
			<tr>
			    <td align="left">
					Trung Tâm
					<select name="region" id="region" onchange="xl()">
					<option value="">--Tất cả--</option>
					        <c:forEach var="temp" items="${regionlist}">
					              <c:choose>
					                <c:when test="${temp.region == region}">
					                    <option value="${temp.region}" selected="selected">${temp.region}</option>
					                </c:when>
					                <c:otherwise>
					                    <option value="${temp.region}">${temp.region}</option>
					                </c:otherwise>
					              </c:choose>
					    </c:forEach>
					</select>
	               Từ Ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10"/>
	                Đến Ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit"value="View Report"/>
	            </td>
	        </tr>		
		</table>
	</form>
	<br/>
</div>
<div  style="overflow: auto;">
		<display:table name="${dyregion}" id="dyregion" requestURI="" pagesize="100" class="simple3" export="true">
		 	<display:column property ="region"  titleKey="REGION" />
		    <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" />
		    <display:column property="tDrpr" titleKey="T_DRPR"/>
		    <display:column property="tEmpdr" titleKey="T_EMPDR"/>
		    <display:column property="tBlkr" titleKey="T_BLKR"/>
		  	<display:column property="tNblkr" titleKey="T_NBLKR"/>
		    <display:column property="tHoblkr" titleKey="T_HOBLKR"/>
		    <display:column property="tAsr" titleKey="T_ASR"/>
		    <display:column property="tAvailhf" titleKey="T_AVAILHF"/>
		    <display:column property="tCap" titleKey="T_CAP"/>
		    <display:column property="tOtraf" titleKey="T_OTRAF"/>
		  	<display:column property="tGos" titleKey="T_GOS"/>
		    <display:column property="sBlkr" titleKey="S_BLKR"/>
		    <display:column property="sDrpr" titleKey="S_DRPR"/>
		    <display:column property="sSsr" titleKey="S_SSR"/>
		  	<display:column property="cssr" titleKey="CSSR"/>
		    <display:column property="tUtil" titleKey="T_UTIL"/>
		    <display:column property="ldPagesLoad" titleKey="LD_PAGES_LOAD"/>
		    <display:column property="haftratePercent" titleKey="HAFTRATE_PERCENT"/>
		  	<display:column property="trxVar" titleKey="TRX_VAR"/>
		    <display:column property="tchVar" titleKey="TCH_VAR"/>
		    <display:column property="tchreq" titleKey="TCHREQ"/>
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
		
		$( "#endDate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		}); 
	});
</script>
