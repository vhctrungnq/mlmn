<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>

<%-- <title>data file</title>
<content tag="heading">CHI TIẾT DATAFILE ${jobName}</content>--%>
<title>${title}</title>
<content tag="heading">${title}</content> 	
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/log/checktablespace/detail.htm?user=${user}"><span>Kiểm tra tablespace</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/log/checktablespace/list.htm?user=${user}"><span>Chi tiết datafile</span></a></li>
</ul>
<div class="ui-tabs-panel">
	<form method="post" action="list.htm" name = "frmSample">
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

<div>
	<display:table name="${datafileloglist}" id="user" class="simple2" requestURI="" pagesize="100"  export="true" partialList="true" size="${totalSize}" sort="external" defaultsort="1">
			<display:column title="STT">
				<c:out value="${user_rowNum + startRecord}" />
			</display:column>
			<display:column property="fileName" titleKey="FILE NAME" sortable="true" sortName="FILE_NAME"/>
			<display:column property="fileId" titleKey="FILE ID" sortable="true" sortName="FILE_ID" style="min-width:40px;max-width:40px;"/>
		 	<display:column property="tablespaceName" titleKey="TABLESPACE NAME" sortable="true" sortName="TABLESPACE_NAME"/>
			<display:column property="bytes" titleKey="BYTES" sortable="true" sortName="BYTES"/>
			<display:column property="blocks" titleKey="BLOCKS" sortable="true" sortName="BLOCKS"/>
			<display:column property="status" titleKey="STATUS" sortable="true" sortName="STATUS"/>
			<display:column property="relativeFno" titleKey="RELATIVE_FNO" sortable="true" sortName="RELATIVE_FNO" style="min-width:40px;max-width:40px;"/>
			<display:column property="autoextensible" titleKey="AUTOEXTENSIBLE" sortable="true" sortName="AUTOEXTENSIBLE" style="min-width:40px;max-width:40px;"/>
			<display:column property="maxbytes" titleKey="MAXBYTES" class="MAXBYTES" sortable="true" headerClass="master-header-2" sortName="MAXBYTES"/>
		 	<display:column property="maxblocks" titleKey="MAXBLOCKS" sortable="true" sortName="MAXBLOCKS"/>
			<display:column property="incrementBy" titleKey="INCREMENT_BY" sortable="true" sortName="INCREMENT_BY" style="min-width:40px;max-width:40px;"/>
			<display:column property="userBytes" titleKey="USER BYTES" class="USER_BYTES" sortable="true" headerClass="master-header-2" sortName="USER_BYTES"/>
			<display:column property="userBlocks" titleKey="USER BLOCKS" sortable="true" sortName="USER_BLOCKS"/>
			<display:column property="onlineStatus" titleKey="ONLINE STATUS" sortable="true" sortName="ONLINE_STATUS"/>
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
      window.location = '${pageContext.request.contextPath}/log/checktablespace/list.htm?user=' + $("#user").val();
      
      });  
    ${highlight}
</script>  
<script type="text/javascript">
    function DoubleScroll(element) {
        var scrollbar= document.createElement('div');
        scrollbar.appendChild(document.createElement('div'));
        scrollbar.style.overflow= 'auto';
        scrollbar.style.overflowY= 'hidden';
        scrollbar.firstChild.style.width= element.scrollWidth+'px';
        scrollbar.firstChild.style.paddingTop= '1px';
        scrollbar.firstChild.appendChild(document.createTextNode('\xA0'));
        scrollbar.onscroll= function() {
            element.scrollLeft= scrollbar.scrollLeft;
        };
        element.onscroll= function() {
            scrollbar.scrollLeft= element.scrollLeft;
        };
        element.parentNode.insertBefore(scrollbar, element);
    }

    DoubleScroll(document.getElementById('doublescroll'));
</script>