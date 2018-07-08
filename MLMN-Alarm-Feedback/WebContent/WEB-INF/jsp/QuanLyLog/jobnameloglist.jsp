<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%--<title>Log Jobs</title>
<content tag="heading">CHI TIẾT JOB ${jobName}</content> --%>
<title>${title}</title>
<content tag="heading">${title}</content> 
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/log/checkjob/detail.htm?user=${user}"><span>Kiểm tra Jobs</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/log/checkjob/list.htm?user=${user}"><span>Chi tiết log</span></a></li>
</ul>

<div class="ui-tabs-panel">
	<form method="post" action="list.htm" name = "frmSample">
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
	<display:table name="${jobnameloglist}" id="user" class="simple2" requestURI="" pagesize="100"  export="true" partialList="true" size="${totalSize}" sort="external" defaultsort="1">
			<display:column title="STT">
				<c:out value="${user_rowNum + startRecord}" />
			</display:column>
			<display:column property="logId" titleKey="LOG_ID"  sortable="true" sortName="LOG_ID"/>
			<display:column property="logDate" titleKey="LOG_DATE" sortable="true" sortName="LOG_DATE"/>
			<display:column property="owner" titleKey="OWNER" sortable="true" sortName="OWNER"/>
		 	<display:column property="jobName" titleKey="JOB_NAME" sortable="true" sortName="JOB_NAME"/>
			<display:column property="jobSubname" titleKey="JOB_SUBNAME" sortable="true" sortName="JOB_SUBNAME"/>
			<display:column property="jobClass" titleKey="JOB_CLASS" sortable="true" sortName="JOB_CLASS"/>
			<display:column property="operation" titleKey="OPERATION" sortable="true" sortName="OPERATION"/>
			<display:column property="status" titleKey="STATUS" sortable="true" sortName="STATUS"/>
			<display:column property="userName" titleKey="USER_NAME" sortable="true" sortName="USER_NAME"/>
			<display:column property="clientId" titleKey="CLIENT_ID" sortable="true" sortName="CLIENT_ID"/>
			<display:column property="globalUid" titleKey="GLOBAL_UID" sortable="true" sortName="GLOBAL_UID"/>
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
      window.location = '${pageContext.request.contextPath}/log/checkjob/list.htm?user=' + $("#user").val();
    });
</script>  