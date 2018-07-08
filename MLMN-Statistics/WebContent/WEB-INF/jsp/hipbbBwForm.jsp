<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Direction Form</title>
<body class="section-5"/>
<content tag="heading">DIRECTION/LINK IPBB</content>

<form:form commandName="hipbbBw" method="post" action="form.htm">
	<form:hidden path="id" />
    <table class="simple2">
	    <tr>
		    <td><b>Vendor</b></td>
			<td>
				<form:select path="vendor"> 
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
			
			<td width="200"><b>Direction<font color = "red">(*)</font></b></td>
            <td><form:input path="direction" size="50" maxlength="150"/>
              <br/><font color="red"><form:errors path="direction"/></font>    
            </td>
	    </tr>  
	     
        <tr>
            <td><b>Link</b></td>
            <td><form:input path="link" size="50"/>
            <br/><font color="red"><form:errors path="link"/></font></td>  
            
            <td><b>Interface Name</b></td>
        	<td><form:input path="interfaceName" size="50" maxlength="250"/></td>  
        </tr>
        
        <tr>
         	<td><b>Interface Service</b></td>
        	<td><form:input path="interfaceService" size="50" maxlength="250"/></td>
        	
        	<td><b>BandWidth </b></td>
            <td><form:input path="bw" maxlength="30"/>
            <br/><font color="red"><form:errors path="bw"/></font></td>   
        </tr>
        
        <tr>
            <td><b>IP</b></td>
            <td><form:input path="ip" size="50" maxlength="20"/>
            
            </td>
            
            <td><b>Local Id</b></td>
            <td><form:input path="localId" size="50" maxlength="20"/>
          
            </td>
        </tr>

        <tr>
            <td><b>Dept</b></td>
            <td>	
            	<form:select path="dept"  >  
	           		<form:option  value=""></form:option >
				        <c:forEach var="temp" items="${deptList}">
				              <c:choose>
				                <c:when test="${temp.deptCode == hipbbBw.dept}">
				                    <form:option  value="${temp.deptCode}" selected="selected">${temp.deptName}</form:option >
				                </c:when>
				                <c:otherwise>
				                    <form:option  value="${temp.deptCode}">${temp.deptName}</form:option >
				                </c:otherwise>
				              </c:choose>
				    </c:forEach>
			 	</form:select>
            <br/><font color="red"><form:errors path="dept"/></font>
            </td>
            
            <td><b>Team</b></td>
            <td><form:select path="team" style="width: 190px;">  
	           		<form:option  value=""></form:option >
				        <c:forEach var="temp" items="${teamList}">
				              <c:choose>
				                <c:when test="${temp.deptCode == hipbbBw.team}">
				                    <form:option  value="${temp.deptCode}" selected="selected">${temp.deptName}</form:option >
				                </c:when>
				                <c:otherwise>
				                    <form:option  value="${temp.deptCode}">${temp.deptName}</form:option >
				                </c:otherwise>
				              </c:choose>
				    </c:forEach>
			 </form:select> 
            <br/><font color="red"><form:errors path="team"/></font>
            </td>
        </tr> 
        
         <tr>
            <td><b>Sub team</b></td>
            <td><form:select path="subTeam" style="width: 190px;">  
	           		<form:option  value=""></form:option >
				        <c:forEach var="temp" items="${subteamList}">
				              <c:choose>
				                <c:when test="${temp.deptCode == hipbbBw.subTeam}">
				                    <form:option  value="${temp.deptCode}" selected="selected">${temp.deptName}</form:option >
				                </c:when>
				                <c:otherwise>
				                    <form:option  value="${temp.deptCode}">${temp.deptName}</form:option >
				                </c:otherwise>
				              </c:choose>
				    </c:forEach>
			 </form:select> 
            <br/><font color="red"><form:errors path="subTeam"/></font>
            </td>
            
            <td><b>NE Group</b></td>
            <td><form:input path="neGroup" size="50" />
            <br/><font color="red"><form:errors path="neGroup"/></font>
            </td>
        </tr> 
        
         <tr>
            <td><b>Location Name</b></td>
            <td><form:input path="locationName" size="50" />
            <br/><font color="red"><form:errors path="locationName"/></font>
            </td>
            
            <td><b>Pha</b></td>
            <td><form:input path="pha" size="20" maxlength="20"/>
            <br/><font color="red"><form:errors path="pha"/></font>
            </td>
        </tr>  
        
        <tr>
        	<td><b><fmt:message key="ipbbbw.list.diemdau"/></b></td>
            <td><form:input path="diemDau" size="50" maxlength="180"/></td>
            <td><b><fmt:message key="ipbbbw.list.diemcuoi"/></b></td>
            <td><form:input path="diemCuoi" size="50" maxlength="180"/></td>
        </tr>
        
        <tr>
        	<td><b><fmt:message key="ipbbbw.list.donviquanly"/></b></td>
            <td><form:input path="donViQuanLy" size="50" maxlength="180"/></td>
            <td><b><fmt:message key="ipbbbw.list.doitactruyendan"/></b></td>
            <td><form:input path="doiTacTruyenDan" size="50" maxlength="180"/></td>
        </tr>
        
        <tr>
        	<td><b><fmt:message key="ipbbbw.list.congvantangbw"/></b></td>
            <td><form:input path="congVanTangBw" size="50" maxlength="180"/></td>
            <td></td><td></td>
        </tr>
        
        <tr>
            <td></td><td></td><td></td>
            <td>
                <input type="submit" class="button" name="save" id ="save" value="Lưu lại"/>
                <input type="button" value="Hủy bỏ" onClick='window.location="list.htm"' class = "button">
            </td>
        </tr>
    </table>
    
</form:form>


<script type="text/javascript"> 
$(function() {  
	$("select#dept").change(function(){
		$.getJSON("${pageContext.request.contextPath}/ajax/loadTeamByDept.htm",{dept: $(this).val()}, function(j){
			var options = '<option  value=""></option>';
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
			var options = '<option  value=""></option>';
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
			var options = '<option  value=""></option>';
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
