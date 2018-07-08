<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style>
	#doublescroll { overflow: auto; overflow-y: hidden; }
	#doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
</style>

<title>import mapping list</title>
<body class="section-5"/>
<content tag="heading">DANH SÁCH DIRECTION/LINK IPBB</content>

<form:form commandName="filter" method="post" action="list.htm">
<table style="border:0; width:100%;">
	<tr>
		<td>Vendor</td>
		<td>
			<form:select path="vendor" class="wid90" onchange="xl()"> 
				<form:option  value="">Tất cả</form:option >
				<c:forEach var="item" items="${vendorForResourceList}">
					<c:choose>
						<c:when test="${item.value == vendor}">
							<form:option  value="${item.value}" selected="selected">${item.value}</form:option >
						</c:when>
						<c:otherwise>
							<form:option  value="${item.value}">${item.value}</form:option >
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</form:select>
		</td>
		
		<td>Direction</td>
        <td><form:input path="direction" id="direction" name="direction" size="16" maxlength="250"/></td>
        <td>Link</td>
        <td><form:input path="link" name="link" id="link" size="16" maxlength="250"/></td>  
        <td>IP</td>
        <td><form:input path="ip" size="16" maxlength="100"/></td> 
		<td>Location name</td>
        <td><form:input path="locationName" size="16" maxlength="250" /></td> 
		<td>
		<input type="submit" class="button" name="filter" id="submit" value="Tìm kiếm"/></td>  
	 </tr>
	  <c:if test="${checkRoleManager==true}">	
	 <tr>
	 	 
	    <td colspan = "11" align="right" style="padding-top:5px;">
            <a href="upload.htm">Upload file</a>&nbsp;
            <a href="form.htm">Thêm mới</a>&nbsp;
        </td>
     </tr>
     </c:if>
    </table>
</form:form> 
<div id = "doublescroll" class="tableStandar"  style="overflow: auto">
<display:table name="${IpbbBwList}" id="hipbbBw" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
		    <display:column class="centerColumnMana" titleKey="STT"> <c:out value="${ipbbBw_rowNum}"/> </display:column>
		    <display:column property="vendor" titleKey="Vendor"/>
		    <display:column property="direction" titleKey="Direction"/>
		    <display:column property="link" titleKey="Link"/>
		    <display:column property="interfaceName" titleKey="Interface Name"/>
		    <display:column property="interfaceService" titleKey="InterfaceService"/>
		    <display:column property="bw" titleKey="BandWidth" />   
		    <display:column property="ip" titleKey="IP"/>
		    <display:column property="localId" titleKey="LocalId"/>
		    <display:column property="dept" titleKey="Dept"/>
		    <display:column property="team" titleKey="Team"/>
		    <display:column property="subTeam" titleKey="SubTeam"/> 
		    <display:column property="locationName" titleKey="LocationName"/>
		    <display:column property="pha" titleKey="Pha"/>  
		    <display:column property="diemDau" titleKey="ipbbbw.list.diemdau"/>
		    <display:column property="diemCuoi" titleKey="ipbbbw.list.diemcuoi"/>
		    <display:column property="donViQuanLy" titleKey="ipbbbw.list.donviquanly"/>
		    <display:column property="doiTacTruyenDan" titleKey="ipbbbw.list.doitactruyendan"/>
		    <display:column property="description" titleKey="ipbb.description"/> 
		     <c:if test="${checkRoleManager==true}">	
		    <display:column titleKey="QUẢN LÝ" media="html">
		    	<a href="form.htm?id=${hipbbBw.id}">Sửa</a>&nbsp;
		    	<a href="delete.htm?id=${hipbbBw.id}"
		    	  onclick="return confirm('Bạn có thực sự muốn xóa không?')">Xóa</a>
		    </display:column>
		    </c:if>
		    <display:setProperty name="export.csv.include_header" value="true"/>
		    <display:setProperty name="export.excel.include_header" value="true"/>
	    	<display:setProperty name="export.csv.filename" value="IpbbBwList.csv"/>
		    <display:setProperty name="export.excel.filename" value="IpbbBwList.xls"/>
		</display:table>
</div>
<script type="text/javascript">
function xl(){
	var sub= document.getElementById("submit");
	sub.focus();
}
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

