<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<title>${title}</title>
<content tag="heading">${title}</content> 	


<form:form commandName="filter" method="post" action="list.htm?type=${type}&region=${region}">
	<table >
		<tr> 
		<c:if test="${region==''}">
			<td style="width:70px;"><fmt:message key="qLPhongBan.region"/></td>
			<td style="width:70px;">
		        <select id="regionTK" name = "regionTK">
		        		<option value=""></option>
	   					<c:forEach var="items" items="${regionList}">
			              <c:choose>
			                <c:when test="${items.name == regionTK}">
			                    <option value="${items.name}" selected="selected">${items.value}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${items.name}">${items.value}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
	           		</select>	
		        
	        </td>
	        </c:if>
			<c:if test="${type==''}">
				<td style="padding-left: 5px;width:70px;">
					Từ ngày
				</td>
				<td style="width:120px;">
					<input type="text" value="${ngayTK}" name="ngayTK" id="ngayTK" size="10" maxlength="10">
					<img alt="calendar" title="Click to choose the start date" id="choosengayTK" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
								
				</td>
				<td style="padding-left: 10px;width:40px;">
					Đến
				</td>
				<td style="width:120px;">
					<input type="text" value="${ngayTKTo}" name="ngayTKTo" id="ngayTKTo" size="10" maxlength="10">
					<img alt="calendar" title="Click to choose the start date" id="choosengayTKTo" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
								
				</td>
			</c:if>
			<c:if test="${type!=''}">
				<td>
					<input type="hidden" value="${ngayTK}" name="ngayTK" id="ngayTK" size="10" maxlength="10">
					<input type="hidden" value="${ngayTKTo}" name="ngayTKTo" id="ngayTKTo" size="10" maxlength="10">
				</td>
			</c:if>
				<td style="width:70px;">
					Ca trực
				</td>
				<td>
					<select name="caTK" id="caTK" style="width: 80px;height:20px; padding-top: 4px;">
						<option value=""><fmt:message key="global.shiftAll"/></option>
						<c:forEach var="item" items="${caList}">
							<c:choose>
				                <c:when test="${item.value == caTK}">
				                    <option value="${item.value}" selected="selected">${item.value}</option>
				                </c:when>
								<c:otherwise>
									<option value="${item.value}">${item.value}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select> 
				</td>
		
				<td	 style="padding-left: 10px;width:100px"><fmt:message key="workShift.userDelivered"/></td>
				<td>
					<!-- <input type="text" id="userDelivered"  name="userDelivered" style="width: 200px;" /> -->
					<select id="userDelivered" name="userDelivered" style="width: 250px;height:20px;" >
						<option value="">--Tất cả--</option>
		 				<c:forEach var="items" items="${fullNameList}">
		              	<c:choose>
		                <c:when test="${items.username == userDelivered}">
		                    <option value="${items.username}" selected="selected">${items.fullname} (${items.username})</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.username}">${items.fullname} (${items.username})</option>
		                </c:otherwise>
		              	</c:choose>
				    	</c:forEach>
			          </select>
				</td>
				<td style="padding-left: 10px;width:120px;"><fmt:message key="workShift.workName"/></td>
				<td>
					<input type="text" id="workName"  name="workName" style="width: 150px;" />
				</td>
				
				<td style="padding-left: 10px;">
					<input class="button" type="submit" id="submit" value="<fmt:message key="global.form.timkiem"/>" />
				</td>
				
			</tr>
		</table>
	</form:form>

<table style="width:100%">
	<tr>
		<c:if test="${checkCaTruc == true}">
			<td align="right" class="ftsize12">
			<input class="button"  type="button" value="<fmt:message key="global.button.addNew"/>" id='addNew' onclick='window.location="form.htm?type=${type}&region=${region}"' />
			</td>
		</c:if>
		<td style="width: 20px"><div style="float: right;" id="listColumn"></div></td>
	</tr>
</table>
<div id="gridReport"></div>
<div id='menuReport'>
            <ul>
            	<c:if test="${checkCaTruc == true}">
			   		<li><fmt:message key="global.button.editSelected"/></li>
		            <li><fmt:message key="global.button.deleteMultiSelected"/></li>
		            <c:if test="${type!='cvcd'}">
	            		<li><fmt:message key="global.button.updateWorkProgress"/></li>
	            		<li><fmt:message key="global.button.forwardWork"/></li>
	            	</c:if>
            	</c:if>
	            <li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script type="text/javascript">
Calendar.setup({
    inputField		:	"ngayTK",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "choosengayTK",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
});


Calendar.setup({
    inputField		:	"ngayTKTo",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "choosengayTKTo",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
});
</script>
<script type="text/javascript">
$(document).ready(function(){

		// Khai bao sdate, edate
		var theme =  getTheme();
		$('#submit').jqxButton({ theme: theme });
		$('#addNew').jqxButton({ theme: theme });
	
    // Create a input userDelivered
     var type =  "<c:out value='${type}'/>";
  /*    var userDeliveredList=[];
	  $.getJSON("${pageContext.request.contextPath}/working/getWorkFilterCondition.htm",{columnName:'USER_DELIVERED',type:type}, function(j){	  
		  userDeliveredList =j;
	   });
       $("#userDelivered").jqxInput({placeHolder: "Enter User Delivered",source: userDeliveredList, height: 20, width: 200, minLength: 0, maxLength: 200, theme: theme});
       var userDelivered =  "<c:out value='${userDelivered}'/>";
       if(userDelivered=="")
       	$('#userDelivered').val('');
       else
       	$('#userDelivered').val(userDelivered); 
	  */
    // Create a input workName
       var workNameList=[];
  	  $.getJSON("${pageContext.request.contextPath}/working/getWorkFilterCondition.htm",{columnName:'WORK_NAME',type:type}, function(j){	  
  			workNameList =j;
  	   });
         $("#workName").jqxInput({placeHolder: "Enter workName",source: workNameList, height: 20, width: 200, minLength: 0, maxLength: 200, theme: theme});
         var workName =  "<c:out value='${workName}'/>";
         if(workName=="")
         	$('#workName').val('');
         else
         	$('#workName').val(workName); 
  	 
        
});		 
</script>
<script type="text/javascript">
${gridReport}
// handle context menu clicks.
$("#menuReport").on('itemclick', function (event) {
    var args = event.args;
    var type =  "<c:out value='${type}'/>";
    var region =  "<c:out value='${region}'/>";
    if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
   	  	var rowindex = $("#gridReport").jqxGrid('getselectedrowindex');
        var row = $("#gridReport").jqxGrid('getrowdata', rowindex);
       // alert(row.id);
        if (type!='')
   	   { 
   			window.location = 'form.htm?id='+row.id+'&type='+type+'&region='+region;   
   	    }
       else
   	   {
    	   window.location = '${pageContext.request.contextPath}/w_working_managements/formContent.htm?id='+row.id+'&note=cvcl&region='+region;     
   	   } 
    }
    if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteMultiSelected"/>')  {
    	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
    	if (answer)
    	{
			var selectedrowindexes = $('#gridReport').jqxGrid('getselectedrowindexes'); 
    		var idList="";
    		var cond="";
    		//alert(selectedrowindexes);
    		//var rowIndexList = selectedrowindexes.split(",");
    		if (selectedrowindexes != null) {
    			for (var i=0; i<selectedrowindexes.length; i++) {
    				var row = $("#gridReport").jqxGrid('getrowdata', selectedrowindexes[i]);
    				idList+=cond+row.id;
    				cond=",";
    			}
    			// alert(idList);
    			window.location = 'delete.htm?idList='+idList+'&type='+type+'&region='+region;   
    		}
		}
    }
    
    
    if ($.trim($(args).text()) == '<fmt:message key="global.button.forwardWork"/>') {
   	  	var rowindex = $("#gridReport").jqxGrid('getselectedrowindex');
        var row = $("#gridReport").jqxGrid('getrowdata', rowindex);
       // alert(row.id);
      	window.location = 'forwardNextShift.htm?id='+row.id+'&type='+type+'&region='+region;       
       
    }
    var exportFileName =  "<c:out value='${exportFileName}'/>";
	if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
    	$("#gridReport").jqxGrid('exportdata', 'xls', exportFileName);
    }
	
	
	 if ($.trim($(args).text()) == '<fmt:message key="global.button.updateWorkProgress"/>')  {
		 	var rowindex = $("#gridReport").jqxGrid('getselectedrowindex');
	        var row = $("#gridReport").jqxGrid('getrowdata', rowindex);
	        window.location = '${pageContext.request.contextPath}/w_working_managements/formContentDetails.htm?id='+row.id+'&type='+type+'&note=cvcl&region='+region; 
	    }
});
  
</script>

