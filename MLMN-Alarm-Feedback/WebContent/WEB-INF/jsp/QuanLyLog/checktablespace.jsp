<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%--<title>Check table</title>
<content tag="heading">KIỂM TRA TABLE SPACE</content> --%>
<title>${title}</title>
<content tag="heading">${title}</content> 	
<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/log/checktablespace/detail.htm?user=${user}"><span>Kiểm tra tablespace</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/log/checktablespace/list.htm?user=${user}"><span>Chi tiết datafile</span></a></li>
</ul>
<div class="ui-tabs-panel">
	<form method="post" action="detail.htm" name = "frmSample">
		<table style="width:100%;" class="form">
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
	<display:table name="${freespacelist1}" id="user" class="simple2" requestURI="" pagesize="100"  export="true" partialList="true" size="${totalSize}" sort="external" defaultsort="1">
			<display:column title="STT">
				<c:out value="${user_rowNum + startRecord}" />
			</display:column>
			<display:column property="tablespaceName" titleKey="TABLESPACE_NAME" headerClass="hide" class="hide"/>
			<display:column titleKey="TABLESPACE_NAME" media="html"  sortable="true" sortName="TABLESPACE_NAME">
			    	<a class = "link" href="${pageContext.request.contextPath}/log/checktablespace/list.htm?user=${user.tablespaceName}">${user.tablespaceName}</a>&nbsp;
			</display:column>
			<display:column property="fileId" titleKey="FILE_ID"  sortable="true" sortName="FILE_ID"/>
		 	<display:column property="blockId" titleKey="BLOCK_ID" sortable="true" sortName="BLOCK_ID"/>
			<display:column property="bytes" titleKey="BYTES" sortable="true" sortName="BYTES"/>
			<display:column property="blocks" titleKey="BLOCKS" sortable="true" sortName="BLOCKS"/>
			<display:column property="relativeFno" titleKey="RELATIVE_FNO" sortable="true" sortName="RELATIVE_FNO"/>
			<display:setProperty name="export.csv.include_header" value="true" />
			<display:setProperty name="export.excel.include_header" value="true" />
			<display:setProperty name="export.xml.include_header" value="true"/>
			<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
			<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
			<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />
	</display:table>
</div>			
<script>
    $("#user").change(function () {
      window.location = '${pageContext.request.contextPath}/log/checktablespace/detail.htm?user=' + $("#user").val();
    });
</script>  