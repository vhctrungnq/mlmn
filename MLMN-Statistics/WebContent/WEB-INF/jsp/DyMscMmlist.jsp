<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<title>MSC DAY REPORT</title>
<content tag="heading">MSC MULTI MEDIA CS DATA</content>
 
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/core-era/msc-mm/hr.htm?mscid=${msc.mscid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core-era/msc-mm/dy.htm?mscid=${msc.mscid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
</ul>
<div class="ui-tabs-panel">
	<form  method="get" action="dy.htm" name = "frmSample" onSubmit = "return ValidateForm()">
		<table style="width: 100%;" class="form">
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
		<display:table name="${dyMscMm}" id="dyMscMm" requestURI="" pagesize="100" class="simple3" export="true">
		<display:column property="mscid" titleKey="SYSTEM" headerClass="hide" class="hide"/>
		   <display:column title="SYSTEM" media="html">
		   	 	<a href="${pageContext.request.contextPath}/report/core-era/msc-mm/hr.htm?mscid=${dyMscMm.mscid}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyMscMm.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyMscMm.day}"/>">${dyMscMm.mscid}</a>
		    </display:column>
		    <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" />	    
		    <display:column property="mulmedSerReq" titleKey="Number of service request for multimedia over 3.1kHz audio"/>
		    <display:column property="mulMedSerReq" titleKey="Number od accepted request for multimedia over 3.1kHz audio"/>
		    <display:column property="mmGsmSucc" titleKey="Succ MM GSM (%)"/>
		    <display:column property="mulmedSerReqDi" titleKey="Number od service request for multimedia over UDI or RDI"/>
		    <display:column property="mulMedSerReqDi" titleKey="Number od accepted request for multimedia over UDI or RDI"/>
		    <display:column property="mmUmtsSucc" titleKey="Succ MM UMTS (%)"/>
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
