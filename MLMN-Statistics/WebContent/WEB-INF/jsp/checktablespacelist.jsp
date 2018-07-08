<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>data file</title>
<content tag="heading">CHI TIẾT DATAFILE ${jobName}</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/log/checktablespace/detail.htm?user=${user}"><span>Kiểm tra tablespace</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/log/checktablespace/list.htm?user=${user}"><span>Chi tiết datafile</span></a></li>
</ul>
<div class="ui-tabs-panel">
	<form method="post" action="list.htm" name = "frmSample">
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

<div  id="doublescroll">
	<display:table name="${datafileloglist}" id="user" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
			<display:column property="fileName" titleKey="FILE_NAME"/>
			<display:column property="fileId" titleKey="FILE_ID"/>
		 	<display:column property="tablespaceName" titleKey="TABLESPACE_NAME"/>
			<display:column property="bytes" titleKey="BYTES"/>
			<display:column property="blocks" titleKey="BLOCKS"/>
			<display:column property="status" titleKey="STATUS"/>
			<display:column property="relativeFno" titleKey="RELATIVE_FNO"/>
			<display:column property="autoextensible" titleKey="AUTOEXTENSIBLE"/>
			<display:column property="maxbytes" titleKey="MAXBYTES" class="MAXBYTES" sortable="true" headerClass="master-header-2" sortName="MAXBYTES"/>
		 	<display:column property="maxblocks" titleKey="MAXBLOCKS"/>
			<display:column property="incrementBy" titleKey="INCREMENT_BY"/>
			<display:column property="userBytes" titleKey="USER_BYTES" class="USER_BYTES" sortable="true" headerClass="master-header-2" sortName="USER_BYTES"/>
			<display:column property="userBlocks" titleKey="USER_BLOCKS"/>
			<display:column property="onlineStatus" titleKey="ONLINE_STATUS"/>
			   
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