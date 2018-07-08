<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- <title>Check Jobs</title>
<content tag="heading">KIỂM TRA JOBS</content>--%>
<title>${title}</title>
<content tag="heading">${title}</content> 	

<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/log/checkjob/detail.htm?user=${user}"><span>Kiểm tra Jobs</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/log/checkjob/list.htm?user=${user}"><span>Chi tiết log</span></a></li>
</ul>

<div class="ui-tabs-panel">
	<form name="jobnamelist1" method="post" action="detail.htm">
		<table style="width:100%;" class="form">
			<tr>
			    <td align="left">
			    	JOB NAME
					<select name="user" id="user" onchange="xl()">
						<option value="">--Select Job--</option>
				        <c:forEach var="jobName" items="${jobnamelist}">
			              <c:choose>
			                <c:when test="${jobName.jobName == user}">
			                    <option value="${jobName.jobName}" selected="selected">${jobName.jobName}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${jobName.jobName}">${jobName.jobName}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
						</select>
				</td>
	        </tr>		
		</table>
	</form>
	<br/>
</div>

<div  style="overflow: auto;">
	<display:table name="${jobnamelist1}" id="user" class="simple2" requestURI="" pagesize="100"  export="true" partialList="true" size="${totalSize}" sort="external" defaultsort="1">
			<display:column title="STT">
				<c:out value="${user_rowNum + startRecord}" />
			</display:column>
			<display:column property="owner" titleKey="OWNER" sortable="true" sortName="OWNER"/>
			<display:column property="jobName" titleKey="JOB_NAME" headerClass="hide" class="hide"/>
			<display:column titleKey="JOB_NAME" media="html"  sortable="true" sortName="JOB_NAME">
			    	<a class = "link" href="${pageContext.request.contextPath}/log/checkjob/list.htm?user=${user.jobName}">${user.jobName}</a>&nbsp;
			</display:column>
			<display:column property="comments" titleKey="COMMENTS"  sortable="true" sortName="COMMENTS"/>
		 	<display:column property="enabled" titleKey="ENABLED" sortable="true" sortName="ENABLED"/>
			<display:column property="repeatInterval" titleKey="REPEAT_INTERVAL" sortable="true" sortName="REPEAT_INTERVAL"/>
			<display:column property="startDate" titleKey="START_DATE" sortable="true" sortName="START_DATE"/>
			<display:column property="endDate" titleKey="END_DATE" sortable="true" sortName="END_DATE"/>
			<display:column property="jobAction" titleKey="JOB_ACTION" sortable="true" sortName="JOB_ACTION"/>   
			<display:setProperty name="export.csv.include_header" value="true" />
			<display:setProperty name="export.excel.include_header" value="true" />
			<display:setProperty name="export.xml.include_header" value="true" />
			<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
			<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
			<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />
	</display:table>
</div>			
<script>
    $("#user").change(function () {
      window.location = '${pageContext.request.contextPath}/log/checkjob/detail.htm?user=' + $("#user").val();
    });
</script>  