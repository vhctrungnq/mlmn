<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>bsc tch drop qos</title>
<content tag="heading">BSC TCH DROP QOS REPORT</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bsc/tdrop/hr.htm?bscid=${bscid}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/bsc/tdrop/dy.htm?bscid=${bscid}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  	  <form method="get" action="dy.htm" name = "frmSample" onSubmit = "return ValidateForm()">
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
			        &nbsp;&nbsp;BSC 
			       <select name="bscid" id="bscid" style="width: 163px">
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
	                &nbsp;&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	          	  </form>
	            </td>
	        </tr>		
		</table>
		<br/>
	
		<div style="overflow: auto;">
			<display:table name="${dyBscDcrtQos}" id="dyBscDcrtQos" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
				<display:column property="region" titleKey="TT" sortable="true"/>
			    <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true"/>	   		    
			    <display:column property="bscid" titleKey="BSC" sortable="true" class="hide" headerClass="hide"/>
			    <display:column titleKey="BSC" media="html" sortable="true" sortProperty="bscid">
			    	<a href="${pageContext.request.contextPath}/report/radio/bsc/tdrop/hr.htm?bscid=${dyBscDcrtQos.bscid}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyBscDcrtQos.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyBscDcrtQos.day}"/>">${dyBscDcrtQos.bscid}</a>
			    </display:column>	    
			    <display:column property ="f1" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" title="Total No. of Dropperd TCH Connections" sortable="true"/>
			    <display:column property ="f17" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" title="UL FR Drop" sortable="true"/>
			    <display:column property ="f18" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" title="UL HR Drop" sortable="true"/>
			    <display:column property ="f19" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" title="OL FR Drop" sortable="true"/>
			    <display:column property ="f20" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" title="OL HR Drop" sortable="true"/>
			    <display:column property ="f2" titleKey="TCH DROP (%)" sortable="true"/>
			    <display:column property="f3" titleKey="TCH Erlang Minutes per Drop" sortable="true"/>
			    <display:column property="f4" titleKey="Drop Reason, Low SS DL (%)" sortable="true"/>
			    <display:column property ="f5" titleKey="Drop Reason, Low SS UL (%)" sortable="true"/>
			    <display:column property ="f6" titleKey="Drop Reason, Low SS UL/DL (%)" sortable="true"/>
			    <display:column property="f7" titleKey="Drop Reason, bad Quality DL (%)" sortable="true"/> 
			    <display:column property="f8" titleKey="Drop Reason, Bad Quality UL (%)" sortable="true"/>
			    <display:column property ="f9" titleKey="Drop Reason, Bad Quality UL/DL (%)" sortable="true"/>
			    <display:column property ="f10" titleKey="Drop Reason, Suddenly Lost Connections (%)" sortable="true"/>
			    <display:column property ="f11" titleKey="Drop Reason, Excessive TA (%)" sortable="true"/>
			    <display:column property="f12" titleKey="Drop Reason, FER DL (%)" sortable="true"/>
			    <display:column property="f13" titleKey="Drop Reason, FER UL (%)" sortable="true"/>
			    <display:column property="f14" titleKey="Drop Reason, FER UL/DL (%)" sortable="true"/>
			    <display:column property ="f15" titleKey="Drop Reason, Other (%)" sortable="true"/>
			</display:table>
		</div>
		
		<br>
		
	<div id="chart" style="width: 1000px; margin: 1em auto"></div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
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
	});
</script>
