<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Danh sách BSC</title>
<content tag="heading">Danh sách BSC</content>

<form:form commandName="filter" method="post" action="list.htm">
	<table style="width:90%; border:0; cellspacing:3; cellpadding:0;">
		    <tr>
				<td class="mwid50">Dept</td>
				<td class="wid15">
					<form:select path="dept" class="wid90" onchange="xl()"> 
						<form:option  value="">Tất cả</form:option >
						<c:forEach var="item" items="${deptList}">
							<c:choose>
								<c:when test="${item.deptCode == dept}">
									<form:option  value="${item.deptCode}" selected="selected">${item.deptName}</form:option >
								</c:when>
								<c:otherwise>
									<form:option  value="${item.deptCode}">${item.deptName}</form:option >
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select>
				</td>
				<td class="mwid50">Team</td>
				<td class="wid15">
					<form:select path="team" class="wid90" onchange="xl()"> 
						<form:option  value="">Tất cả</form:option >
						<c:forEach var="item" items="${teamList}">
							<c:choose>
								<c:when test="${item.deptCode == team}">
									<form:option  value="${item.deptCode}" selected="selected">${item.deptName}</form:option >
								</c:when>
								<c:otherwise>
									<form:option  value="${item.deptCode}">${item.deptName}</form:option >
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select> 
				</td>
				<td class="mwid50">Subteam</td>
				<td class="wid15">
					<form:select path="subTeam" class="wid90" onchange="xl()">  
						<form:option  value="">Tất cả</form:option >
						<c:forEach var="item" items="${subteamList}">
							<c:choose>
								<c:when test="${item.deptCode == subTeam}">
									<form:option  value="${item.deptCode}" selected="selected">${item.deptName}</form:option >
								</c:when>
								<c:otherwise>
									<form:option  value="${item.deptCode}">${item.deptName}</form:option >
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select>
				 </td>
				 <td class="mwid50">Vendor</td>
				<td class="wid15">
					<form:select path="vendor" class="wid90" onchange="xl()">
						<form:option value="" label="Tất cả"/>
						<form:option value="ALCATEL"/>
						<form:option value="HUAWEI"/>
						<form:option value="ERICSSON"/>
						<form:option value="NOKIA SIEMENS"/>
					</form:select>
				</td>
		    </tr>
		    
		    <tr>
				
				
				<td class="mwid50">MSC</td>
				<td class="wid15">
					<form:input type="text" path="mscid" class="wid90"/>
				</td>
				
				<td class="mwid50">BSC</td>
				<td class="wid15">
					<form:input type="text" path="bscid" class="wid90"/>
				</td> 
				<td class="mwid50">Location Name</td>
				<td class="wid15">
					<form:input type="text" path="locationName" class="wid90"/>
				</td> 
				<td>
				</td>
				<td>
					<input type="submit" class="button" id="submit" name="submit" value="Search"/>
				</td> 	
				
		    </tr>  
	</table> 
	<div align="right" style="font-family: verdana, arial, helvetica, tahoma;
		font-size: 11px;" >
		<c:if test="${checkRoleManager==true}">	
			  <a href="upload.htm">Import</a>&nbsp;
		            <a href="form.htm">Add New</a>&nbsp;
		 </c:if>
	</div>
	<div class="tableStandar">
		<display:table name="${bscList}" id="bsc" requestURI="" pagesize="100"   export="false" sort="external" defaultsort="1">				   
			<display:column class="rightColumnMana" title="STT" > <c:out value="${bsc_rowNum}"/> </display:column>
			<display:column property="dept" titleKey="Dept"/>   
			<display:column property="team" titleKey="Team"/>	
			<display:column property="subTeam" titleKey="Subteam"/>	
			<display:column property="vendor" titleKey="Vendor"/>   
		    <display:column property="bscid" title="BSC"/>   
		    <display:column property="mscid" titleKey="MSC"/>
		    <display:column property="locationName" titleKey="Location name" /> 
		    <display:column class="rightColumnMana" property="smsCssr" titleKey="SMS CSSR" />
		    <display:column class="rightColumnMana" property="smsDrc" titleKey="SMS DRC" />
		    <display:column property="nsei" titleKey="NSEI" />
		    <display:column property="neGroup" titleKey="NE group" />
		    <%-- <display:column property="launchDate" format="{0,date,dd/MM/yyyy}" titleKey="Launch date" /> --%>
		   <c:if test="${checkRoleManager==true}">	
		    <display:column titleKey="Quản lý" media="html">
		    	<a href="form.htm?bscid=${bsc.bscid}">Edit</a>&nbsp;
		    	<a href="delete.htm?bscid=${bsc.bscid}"
		    	   onclick="return confirm('Bạn có chắc muốn xóa?')" >Delete</a>&nbsp;
		    </display:column>
		    </c:if>
		</display:table>
		<div class="exportlinks">Export options:
			<a href="${pageContext.request.contextPath}/network/bsc/exportExcel.htm?deptid=${filter.dept}&teamid=${filter.team}&subTeam=${filter.subTeam}&vendor=${filter.vendor}&mscid=${filter.mscid}&bscid=${filter.bscid}"><span class="export excel">Excel </span></a>
		</div>
	</div>
</form:form> 
<script type="text/javascript">
function xl(){
	var sub = document.getElementById("filter");
	sub.focus();
}

$(function() {
	var cache = {},
	lastXhr;
	$( "#bscid" ).autocomplete({
		minLength: 2,
		source: function( request, response ) {
			var term = request.term;
			if ( term in cache ) {
				response( cache[ term ] );
				return;
			}

			lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getBsc.htm", request, function( data, status, xhr ) {
				cache[ term ] = data;
				if ( xhr === lastXhr ) {
					response( data );
				}
			});
		}
	});
});	
</script>

<script type="text/javascript">
$(function() {
	var cache = {},
	lastXhr;
	$( "#mscid" ).autocomplete({
		minLength: 2,
		source: function( request, response ) {
			var term = request.term;
			if ( term in cache ) {
				response( cache[ term ] );
				return;
			}

			lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getMsc.htm", request, function( data, status, xhr ) {
				cache[ term ] = data;
				if ( xhr === lastXhr ) {
					response( data );
				}
			});
		}
	});
});	 
</script>
<script type="text/javascript"> 
$(function() {  
	$("select#dept").change(function(){
		$.getJSON("${pageContext.request.contextPath}/ajax/loadTeamByDept.htm",{dept: $(this).val()}, function(j){
			var options = '<option  value="">Tất cả</option>';
			for (var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i].deptCode + '">' + j[i].deptName + '</option>';
			}
			$("#team").html(options);
			$('#team option:first').attr('selected', 'selected');
		})
	}); 
}); 
</script>
<script type="text/javascript"> 
$(function() {  
	/* Load subteam by dept, team */
	$("select#dept").change(function(){
		$.getJSON("${pageContext.request.contextPath}/ajax/loadSubTeamByTeam.htm",{dept: $(dept).val(), team:$(team).val()}, function(j){
			var options = '<option  value="">Tất cả</option>';
			for (var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i].deptCode + '">' + j[i].deptName + '</option>';
			}
			$("#subTeam").html(options);
			$('#subTeam option:first').attr('selected', 'selected');
		})
	});
	/* ------------------------------------------- */
	/* Load subteam by dept, team */
	$("select#team").change(function(){
		$.getJSON("${pageContext.request.contextPath}/ajax/loadSubTeamByTeam.htm",{dept: $(dept).val(), team:$(team).val()}, function(j){
			var options = '<option  value="">Tất cả</option>';
			for (var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i].deptCode + '">' + j[i].deptName + '</option>';
			}
			$("#subTeam").html(options);
			$('#subTeam option:first').attr('selected', 'selected');
		})
	});
	/* ------------------------------------------- */ 
});  
</script>
