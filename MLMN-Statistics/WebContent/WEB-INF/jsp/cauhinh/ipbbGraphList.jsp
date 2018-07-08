<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Danh sách Ipbb Graph Template Graph</title>
<content tag="heading">Danh sách Ipbb Graph Template Graph</content>

<form method="post" action="list.htm">
	<table style="width:100%; border:0; cellspacing:3; cellpadding:0;">
		    <tr>
				<td style = "width:70px;">Title</td>
				<td class="wid20">
					<input type="text" name="title" id="title" value = "${title}" class="wid90"/>
				</td>
				
				<td style = "width:70px;">Link</td>
				<td class="wid20">
					<select id="link" name = "link" class="wid90" onchange="xl()"> 
						<option  value="">Tất cả</option >
						<c:forEach var="item" items="${linkList}">
							<c:choose>
								<c:when test="${item.id == link}">
									<option  value="${item.id}" selected="selected">${item.link}</option >
								</c:when>
								<c:otherwise>
									<option  value="${item.id}">${item.link}</option >
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select> 
				</td>
				
				<td>
					<input type="submit" class="button" id="submit" name="submit" value="Search"/>
				</td> 	
				<td align="right" class="wid10 mwid110">
		            <a href="upload.htm">Import</a>&nbsp;
		            <a href="form.htm">Add New</a>&nbsp;
		        </td>
		    </tr>  
	</table> 
	<div  style="overflow: auto;">
		<display:table name="${ipbbList}" id="ipbbList" requestURI="" pagesize="100" class="simple2" export="false" sort="external" defaultsort="1">				   
			<display:column class="rightColumnMana" title="STT" > <c:out value="${ipbbList_rowNum}"/> </display:column>
			<display:column class="rightColumnMana" property="localGraphId" titleKey="Local Graph Id"/>   
			<display:column property="title" titleKey="Title"/>	
			<display:column property="link" titleKey="Link"/>	 
		    <display:column titleKey="Quản lý" media="html">
		    	<a href="form.htm?id=${ipbbList.id}">Edit</a>&nbsp;
		    	<a href="delete.htm?id=${ipbbList.id}"
		    	   onclick="return confirm('Bạn có chắc muốn xóa?')" >Delete</a>&nbsp;
		    </display:column>
		</display:table>
		<div class="exportlinks">Export options:
			<a href="${pageContext.request.contextPath}/cauhinh/ipbbGraph/exportExcel.htm?title=${title}&link=${link}"><span class="export excel">Excel </span></a>
		</div>
	</div>
</form> 
<script type="text/javascript">
function xl(){
	var sub = document.getElementById("filter");
	sub.focus();
}
</script>
