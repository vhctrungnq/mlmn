<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>msc Hour report</title>
<content tag="heading">MSC MULTI MEDIA CS DATA</content>

<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core-era/msc-mm/hr.htm?mscid=${msc.mscid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/core-era/msc-mm/dy.htm?mscid=${msc.mscid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
</ul>
  
<div class="ui-tabs-panel">
	<form method="get" action="hr.htm" name = "frmSample" onSubmit = "return ValidateForm()">
		<table style="width:100%;" class="form">
			<tr>
			    <td align="left">
					MSC
					<select name="mscid" id="mscid" onchange="xl()">
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
	                Từ<select name="startHour" id="startHour">
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
	                Ðến <select name="endHour" id="endHour">
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
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit"value="View Report"/>
	            </td>
	        </tr>		
		</table>
	</form>
	<br/>
</div>
<div  style="overflow: auto;">
		<display:table name="${hrMSCMM}" id="hrMSCMM" requestURI="" pagesize="100" class="simple3" export="true">
			<display:column property="mscid" titleKey="SYSTEM" headerClass="hide" class="hide"/>
		    <display:column title="SYSTEM" media="html">
		   	 	<a target="_bank" href="${pageContext.request.contextPath}/report/core-era/msc-mm/hr-bc.htm?mscid=${hrMSCMM.mscid}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${hrMSCMM.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${hrMSCMM.day}"/>">${hrMSCMM.mscid}</a>
		    </display:column>
		    <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="Day" />
		    <display:column property ="hour" format="{0,number}:00" titleKey="Hour" />
		    <display:column property="mulmedSerReq" titleKey="Number of service request for multimedia over 3.1kHz audio"/>
		    <display:column property="mulMedSerReq" titleKey="Number od accepted request for multimedia over 3.1kHz audio"/>
		    <display:column property="mmGsmSucc" titleKey="Succ MM GSM (%)"/>
		    <display:column property="mulmedSerReqDi" titleKey="Number od service request for multimedia over UDI or RDI"/>
		    <display:column property="mulMedSerReqDi" titleKey="Number od accepted request for multimedia over UDI or RDI"/>
		    <display:column property="mmUmtsSucc" titleKey="Succ MM UMTS (%)"/>
		</display:table>
	</div>

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