<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Cập nhật thông tin BSC</title>
<content tag="heading">Cập nhật thông tin BSC</content>

<form:form commandName="bsc" method="post" action="form.htm">
	<div>
		<form:hidden path="region" />
		<form:hidden path="launchDate" />
	</div>
	
    <table class="simple2">
	    	<tr>  
	          <td width="200"><b>BSC<font color = "red">(*)</font></b></td>
	          <td class = "mwid110">            	
	            <c:choose>
	              <c:when test="${empty bsc.region}">
	                  <form:input path="bscid" maxlength="50"/>
	              </c:when>
	              <c:otherwise>
	                  <b><i>${bsc.bscid}</i></b><form:hidden path="bscid" />
	              </c:otherwise>
	            </c:choose>
	               <font color="red"><form:errors path="bscid"/></font>  
	          </td>
	      	</tr> 
	      	
	       <tr> 
	           <td><b>Vendor</b></td>
	           <td class = "mwid110">
	           	<form:select path="vendor" name="vendor" id="vendor" style="width: 190px;">
					<form:option value="ALCATEL"/>
					<form:option value="HUAWEI"/>
					<form:option value="ERICSSON"/>
					<form:option value="NOKIA SIEMENS"/>
	               </form:select>
			</td>
	      </tr>
	      <tr>
	       	
	      <td><b>Dept</b></td>  
	         <td class = "mwid110">
	           	<form:select path="dept" onchange="xl()" >  
	           		<form:option  value=""></form:option >
				        <c:forEach var="temp" items="${deptList}">
				              <c:choose>
				                <c:when test="${temp.deptCode == dept}">
				                    <form:option  value="${temp.deptCode}" selected="selected">${temp.deptName}</form:option >
				                </c:when>
				                <c:otherwise>
				                    <form:option  value="${temp.deptCode}">${temp.deptName}</form:option >
				                </c:otherwise>
				              </c:choose>
				    </c:forEach>
			 	</form:select>
			</td>
	      </tr>
	      
	      <tr> 
	         <td><b>Team</b></td>
	         <td class = "mwid110">
	           	<form:select path="team" style="width: 190px;" onchange="xl()">  
	           		<form:option  value=""></form:option >
				        <c:forEach var="temp" items="${teamList}">
				              <c:choose>
				                <c:when test="${temp.deptCode == team}">
				                    <form:option  value="${temp.deptCode}" selected="selected">${temp.deptName}</form:option >
				                </c:when>
				                <c:otherwise>
				                    <form:option  value="${temp.deptCode}">${temp.deptName}</form:option >
				                </c:otherwise>
				              </c:choose>
				    </c:forEach>
			 </form:select> 
			</td>
	      </tr> 
	
	      <tr> 
	         <td><b>Sub Team</b></td>
	         <td class = "mwid110">
	           	<form:select path="subTeam" style="width: 190px;" onchange="xl()">  
	           		<form:option  value=""></form:option >
				        <c:forEach var="temp" items="${subteamList}">
				              <c:choose>
				                <c:when test="${temp.deptCode == subTeam}">
				                    <form:option  value="${temp.deptCode}" selected="selected">${temp.deptName}</form:option >
				                </c:when>
				                <c:otherwise>
				                    <form:option  value="${temp.deptCode}">${temp.deptName}</form:option >
				                </c:otherwise>
				              </c:choose>
				    </c:forEach>
			 </form:select> 
			</td>
	      </tr>    
      
	      <tr>
	           <td class = "mwid110"><b>NE Group</b></td>
	           <td><form:input path="neGroup" style="width: 190px;" maxlength="40"/></td>
	      </tr>
	      
	      <tr>
	           <td class = "mwid110"><b>Location Name</b></td>
	           <td><form:input path="locationName" style="width: 190px;" maxlength="40"/></td>
	      </tr>
	      
	      <tr>
            <td class = "mwid110"><b>MSC</b></td>
            <td class = "mwid110">
             	<form:input path="mscid" style="width: 190px;" maxlength="40"/>
        
            </td>
        </tr>
      
		<tr>
		     <td class = "mwid110"><b>SMS CSSR</b></td>
		     <td><form:input path="smsCssr" size="5" maxlength="6"/>
		     <font color="red"><form:errors path="smsCssr"/></font>
		</tr>
		
		<tr>
		     <td class = "mwid110"><b>SMS DRC</b></td>
		     <td><form:input path="smsDrc" size="5" maxlength="6"/>
		     <font color="red"><form:errors path="smsDrc"/></font>
		</tr>  
        <tr>
            <td class = "mwid110"><b>NSEI</b></td>
            <td><form:input path="nsei" style="width: 190px;" maxlength="40"/>
            <font color="red"><form:errors path="nsei"/></font>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" class="button" id ="submit" name="submit" value="Save"/>
                <input type="button" value="Cancel" onClick='window.location="list.htm"' class = "button">
            </td>
        </tr>
    </table> 
</form:form> 

<script type="text/javascript"> 
function xl(){
	/* Focus to button submit */
	var sub = document.getElementById("submit");
	sub.focus();
}
</script>

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
	/*Load Mscid*/
	var cacheMscid = {}, lastXhrMscid;
	$("#mscid" ).autocomplete({
		minLength: 1,
		source: function( request, response ) {
			var term = request.term;
			if ( term in cacheMscid ) {
				response( cacheMscid[ term ] );
				return;
			}

			lastXhrMscid = $.getJSON( "${pageContext.request.contextPath}/ajax/getMsc.htm", request, function( data, status, xhr ) {
				cacheMscid[ term ] = data;
				if ( xhr === lastXhrMscid ) {
					response( data );
				}
			});
		}
	});
});  
</script>
