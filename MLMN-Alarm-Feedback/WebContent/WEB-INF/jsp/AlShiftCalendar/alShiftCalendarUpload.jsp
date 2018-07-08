<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">   
 	#success { overflow: auto; overflow-y: hidden; }  
    #success p { margin: 0; padding: 1em; white-space: nowrap; }
    
    #failed { overflow: auto; overflow-y: hidden; }  
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>

<title>${titleU}</title>
<content tag="heading">${titleU}</content>
 <form:form  method="post" action="upload.htm" enctype="multipart/form-data">
	<table class="simple2">	
    	<tr style="height:20px;" >
    		<td width="150px"><b><fmt:message key="cautruc.filepath"/> <font color="red">(*)</font></b></td>
    		<td><input type="file" size="110" name="filePath" id="filePath" class="button" />
    			<input type="submit" name="upload" id="upload" class="button" value="<fmt:message key="global.button.import"/>" />
    		</td>
    	</tr>
    	<tr>
    		<td><b>
    			<fmt:message key="global.FileExample"/></b>
    		</td>
    		<td>
    		<ul>
    			<li><fmt:message key="global.formatFile"/></li>
    			<%-- <li>Cấu trúc file: 
    					<code>
    						&lt;<fmt:message key="alshiptcalendar.year"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="alshiptcalendar.week"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="alshiptcalendar.team"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="alshiptcalendar.area"/><span class="note">(*)</span>&gt;,
    						&lt;<fmt:message key="alshiptcalendar.session"/><span class="note">(*)</span>&gt;
    					</code></li> --%>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/Shift_calendar.xls" title="Danh sách lịch trực ca" style="color: blue; ">Shift_calendar.xls</a></li>
						<li><fmt:message key="global.file"/>&nbsp;<a href="${pageContext.request.contextPath}/upload/example/Shift_calendar_leader.xls" title="Danh sách lịch trực ca lãnh đạo" style="color: blue; ">Shift_calendar_leader.xls</a></li>
				<li><fmt:message key="global.chuY1"/></li>
    			</ul>
    			
    		</td>
    	</tr>
    	
    </table>
    
	<table>
		<c:if test="${failedNumLeader > 0}">
		<tr>
			<td><b>Dữ liệu lịch trực lãnh đạo không hợp lệ ${failedNumLeader}/${totalNum } </b></td>
		</tr>
		<tr>
			<td>
				<display:table name="${failedList}"  class="simple2" id="vAlShiftLeader" requestURI=""  >
				  	<display:column property="week" titleKey="Tuần"/>
					<display:column property="timeDistance" titleKey="Năm"/>
					<display:column property="team" titleKey="Tổ"/>
					<display:column property="name" titleKey="Tên"  />
					<display:column property="regency" titleKey="Chức vụ" />
				</display:table>
			</td>
		</tr>
		</c:if>
		<c:if test="${successNumLeader > 0}">
		<tr>
			<td><b>Dữ liệu lịch trực lãnh đạo hợp lệ ${successNumLeader}/${totalNum } </b></td>
		</tr>
		<tr>
			<td>
				<display:table name="${successList}" class="simple2" id="vAlShiftLeader" requestURI=""  >
				<display:column property="week" titleKey="Tuần"/>
				<display:column property="timeDistance" titleKey="Năm"/>
				<display:column property="team" titleKey="Tổ"/>
				<display:column property="name" titleKey="Tên"  />
				<display:column property="regency" titleKey="Chức vụ" />
				</display:table>
			</td>
		</tr>
		</c:if>
		<c:if test="${failedNum > 0 }">
		<tr>
			<td><b>Dữ liệu lịch trực ca không hợp lệ ${failedNum}/${totalNum } </b></td>
		</tr>
		<tr>
			<td>
				<display:table name="${failedList}" class="simple2" id="failedList" requestURI="" >
				<display:column property="week" titleKey="Tuần"/>
				<display:column property="year" titleKey="Năm"/>
			  	<display:column property="session" titleKey="alshiptcalendar.session"   class = "session" group="1" />
				<display:column property="thu2" titleKey="alshiptcalendar.thu2"  class="Thu2"/>
				<display:column property="thu3" titleKey="alshiptcalendar.thu3"    class="Thu3"/>
			  	<display:column property="thu4" titleKey="alshiptcalendar.thu4"   class="Thu4"/>
				<display:column property="thu5" titleKey="alshiptcalendar.thu5"   class="Thu5"/>
				<display:column property="thu6" titleKey="alshiptcalendar.thu6"    class="Thu6"/>
				<display:column property="thu7" titleKey="alshiptcalendar.thu7"  class="Thu7"/>
				<display:column property="cn" titleKey="alshiptcalendar.cn"    class="cn" />
				</display:table>
			</td>
		</tr>
		</c:if>
		<c:if test="${successNum > 0}">
		<tr>
			<td><b>Dữ liệu lịch trực ca hợp lệ ${successNum}/${totalNum }</b> </td>
		</tr>
		<tr>
			<td>
				<display:table name="${successList}" class="simple2" id="successList" requestURI="" >
				<display:column property="week" titleKey="Tuần"/>
				<display:column property="year" titleKey="Năm"/>
			  	<display:column property="session" titleKey="alshiptcalendar.session" sortName="SESSION"  class = "session" group="1" />
				<display:column property="thu2" titleKey="alshiptcalendar.thu2" class="Thu2"/>
				<display:column property="thu3" titleKey="alshiptcalendar.thu3"  class="Thu3"/>
			  	<display:column property="thu4" titleKey="alshiptcalendar.thu4" class="Thu4"/>
				<display:column property="thu5" titleKey="alshiptcalendar.thu5" class="Thu5"/>
				<display:column property="thu6" titleKey="alshiptcalendar.thu6"   class="Thu6"/>
				<display:column property="thu7" titleKey="alshiptcalendar.thu7"  class="Thu7"/>
				<display:column property="cn" titleKey="alshiptcalendar.cn"  class="cn" />
				</display:table>
			</td>
		</tr>
		</c:if>
		
		
		<tr>
			<td>
               	<input class="button" type="button" value="<fmt:message key="global.button.back"/>" onClick='window.location="list.htm"'>
			</td>
		</tr>
	</table>
</form:form>

<script type="text/javascript">  
    $(function() {
    	$('#item2>tbody>tr').each(
    	    	 function(){
   					  ${highlight}
   						});

    	$('#item1>tbody>tr').each(
   	    	 function(){
  					  ${highlight}
  					});
		}); 
</script>