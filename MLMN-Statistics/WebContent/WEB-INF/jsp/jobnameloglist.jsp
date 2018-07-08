<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Log Jobs</title>
<content tag="heading">CHI TIẾT JOB ${jobName}</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/log/checkjob/detail.htm?user=${user}"><span>Kiểm tra Jobs</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/log/checkjob/list.htm?user=${user}"><span>Chi tiết log</span></a></li>
</ul>

<div class="ui-tabs-panel">
	<form method="post" action="list.htm" name = "frmSample">
		<table width ="100%" class="form">
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
	<display:table name="${jobnameloglist}" id="user" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
			<display:column property="logId" titleKey="LOG_ID"/>
			<display:column property="logDate" titleKey="LOG_DATE"/>
			<display:column property="owner" titleKey="OWNER"/>
		 	<display:column property="jobName" titleKey="JOB_NAME"/>
			<display:column property="jobSubname" titleKey="JOB_SUBNAME"/>
			<display:column property="jobClass" titleKey="JOB_CLASS"/>
			<display:column property="operation" titleKey="OPERATION"/>
			<display:column property="status" titleKey="STATUS"/>
			<display:column property="userName" titleKey="USER_NAME"/>
			<display:column property="clientId" titleKey="CLIENT_ID"/>
			<display:column property="globalUid" titleKey="GLOBAL_UID"/>
			   
	</display:table>
</div>			
<script>
    $("#user").change(function () {
      window.location = '${pageContext.request.contextPath}/log/checkjob/list.htm?user=' + $("#user").val();
    });
</script>  