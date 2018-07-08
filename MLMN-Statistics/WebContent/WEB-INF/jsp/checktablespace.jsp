<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Check table</title>
<content tag="heading">KIỂM TRA TABLE SPACE</content>
<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/log/checktablespace/detail.htm?user=${user}"><span>Kiểm tra tablespace</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/log/checktablespace/list.htm?user=${user}"><span>Chi tiết datafile</span></a></li>
</ul>
<div class="ui-tabs-panel">
	<form method="post" action="detail.htm" name = "frmSample">
		<table width ="100%" class="form">
			<tr>
			    <td align="left">
			    	TABLESPACE NAME
					<select name="user" id="user" onchange="xl()">
						<option value="">--Select TABLESPACE--</option>
				        <c:forEach var="tablespaceName" items="${freespacelist}">
			              <c:choose>
			                <c:when test="${tablespaceName.tablespaceName == user}">
			                    <option value="${tablespaceName.tablespaceName}" selected="selected">${tablespaceName.tablespaceName}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${tablespaceName.tablespaceName}">${tablespaceName.tablespaceName}</option>
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
	<display:table name="${freespacelist1}" id="user" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
			<display:column property="tablespaceName" titleKey="TABLESPACE_NAME" headerClass="hide" class="hide"/>
			<display:column titleKey="TABLESPACE_NAME" media="html">
			    	<a class = "link" href="${pageContext.request.contextPath}/log/checktablespace/list.htm?user=${user.tablespaceName}">${user.tablespaceName}</a>&nbsp;
			</display:column>
			<display:column property="fileId" titleKey="FILE_ID"/>
		 	<display:column property="blockId" titleKey="BLOCK_ID"/>
			<display:column property="bytes" titleKey="BYTES"/>
			<display:column property="blocks" titleKey="BLOCKS"/>
			<display:column property="relativeFno" titleKey="RELATIVE_FNO"/>
	</display:table>
</div>			
<script>
    $("#user").change(function () {
      window.location = '${pageContext.request.contextPath}/log/checktablespace/detail.htm?user=' + $("#user").val();
    });
</script>  