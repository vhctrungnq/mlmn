<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <style>   
 	#doublescroll { overflow: auto; overflow-y: hidden; }  
    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
    
     #causebySystem {
     visibility: visible !important;
    }
</style>     
<title>${titleF}</title>
<content tag="heading">${titleF}</content>
<style>
	.ui-multiselect {
		width:160px !important;
	}
	 #teamProcess {
     visibility: visible !important;
    }
  
   .ui-autocomplete {
	max-height: 200px;
	overflow-y: auto;
	/* prevent horizontal scrollbar */
	overflow-x: hidden;
	}
	/* IE 6 doesn't support max-height
	* we use height instead, but this forces the menu to always be this tall
	*/
	* html .ui-autocomplete {
	height: 200px;
	}
  
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<form:form commandName="alDyNonFinishDetail" name="checkform" id="myform" method="post" action="form.htm"> 
	<form:hidden path="id"/>
	<input type="hidden" name="alarmType" id="alarmType" value="${alarmType}">
	<input type="hidden" name="netWork" id="netWork" value="${netWork}">
	<table class="simple2"> 
      <tr>
           <td style="width: 150px;">
           <c:choose>
	            <c:when test="${netWork == '3G'}">
	               <fmt:message key="AlDyNonFinishDetail.rncid"/>
	            </c:when>
				<c:otherwise>
					<fmt:message key="AlDyNonFinishDetail.bscid"/>
				</c:otherwise>
			</c:choose>
           <font color="red">(*)</font></td>
           <td style="width: 200px;">
           		<form:input type ="text" path="bscid" maxlength="18" style="width:160px;" rows="3"/>
           		<font color="red"><form:errors path="bscid"/></font>
           </td>
           <td style="width: 120px;"><fmt:message key="AlDyNonFinishDetail.site"/></td>
           <td  style="width: 200px;">
           		<form:input type ="text" path="site" maxlength="18" style="width:200px;" rows="3"/>
           </td>
           <c:choose>
            <c:when test="${alarmType == 'DOWN_CELL'}">
               <td style="width: 70px;"><fmt:message key="AlDyNonFinishDetail.cellid"/></td>
	           <td>
	           		<form:input type ="text" path="system" maxlength="18" style="width:150px;" rows="3"/>
	           </td>
            </c:when>
			<c:otherwise>
				<td colspan="2"></td>
			</c:otherwise>
			</c:choose> 
      </tr> 
      <tr>
		
    	 <c:choose>
    	<c:when test="${alarmType == 'DOWN_1800'}">
    		<td><fmt:message key="AlDyNonFinishDetail.cffautid"/></td>
	           <td>
	           		<form:input type ="text" path="system" maxlength="18" style="width:160px;" rows="3"/>
	           </td>
		    <td><fmt:message key="AlDyNonFinishDetail.checkDay"/></td>
	          	<td >
	          		<input type ="text"  value="${day}" name="day" id="day" size="20" maxlength="16" width="80px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseddhBaoMll" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	   				<font color="red">${errorday}<form:errors path="day"/></font>
	    	</td>
	    	<td colspan="2"></td>
	    	<script type="text/javascript">
			    Calendar.setup({
			        inputField		:	"day",	// id of the input field
			        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
			        button			:   "chooseddhBaoMll",   	// trigger for the calendar (button ID)
			        showsTime		:	true,
			        singleClick		:   false					// double-click mode
			    });
				
			</script>
	    	
	    </c:when>
			<c:otherwise>
				<td ><fmt:message key="AlDyNonFinishDetail.sdate"/><font color="red">(*)</font></td>
          <td>
          		<input type ="text"  value="${sdate}" name="sdate" id="sdate" size="20" maxlength="16" width="80px">
   			 <img alt="calendar" title="Click to choose the start date" id="choosesdate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
    		<font color="red">${errorsdate}<form:errors path="sdate"/></font>
    	</td>
     
          <td><fmt:message key="AlDyNonFinishDetail.edate"/></td>
          <td style="width: 200px;" >
          		<input type ="text"  value="${edate}" name="edate" id="edate" size="20" maxlength="16" width="80px">
   			 <img alt="calendar" title="Click to choose the start date" id="chooseedate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
    		<font color="red">${erroredate}<form:errors path="edate"/></font>
    	</td>
    	<script type="text/javascript">
	
		    Calendar.setup({
		        inputField		:	"sdate",	// id of the input field
		        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
		        button			:   "choosesdate",  	// trigger for the calendar (button ID)
		        showsTime		:	true,
		        singleClick		:   false					// double-click mode
		    });
		    Calendar.setup({
		        inputField		:	"edate",	// id of the input field
		        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
		        button			:   "chooseedate",  	// trigger for the calendar (button ID)
		        showsTime		:	true,
		        singleClick		:   false					// double-click mode
		    });
		   
		</script>
    	
    	<td colspan="2"></td>
			</c:otherwise>
		</c:choose> 	
      </tr> 
      <c:if test="${netWork == '3G' && alarmType == 'DOWN_CELL'}">
			<tr>
	           	<td><fmt:message key="AlDyNonFinishDetail.cardError"/></td>
		        <td> 
					<form:input type ="text" path="cardError" maxlength="200" style="width:150px;" rows="3"/>
				 </td>
		        <td><fmt:message key="AlDyNonFinishDetail.type"/></td>
		        <td> 
					<form:input type ="text" path="type" maxlength="200" style="width:150px;" rows="3"/>
		        </td>
		        <td colspan="2"></td>
       		</tr>
	   	</c:if>
		<tr>
           <td><fmt:message key="AlDyNonFinishDetail.teamProcess"/></td>
           <td>
           <select name="teamProcess" id="teamProcess" style="width: 160px;height:20px;">
           		<c:forEach var="item" items="${teamList}">
					<c:choose>
		                <c:when test="${item.deptCode == team}">
		                    <option value="${item.deptCode}" selected="selected">${item.deptCode}</option>
		                </c:when>
						<c:otherwise>
							<option value="${item.deptCode}">${item.deptCode}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> 
           </td>
         	
           	<c:choose>
	            <c:when test="${alarmType == 'DOWN_SITE'}">
	            	<td><fmt:message key="AlDyNonFinishDetail.causebySystem"/></td>
		           	<td> 
						<select name="causebySystem" id="causebySystem" style="width: 200px;height:20px;">
			           		<option value=""><fmt:message key="global.Chosse"/></option>
			           		<c:forEach var="item" items="${causebySyList}">
								<c:choose>
					                <c:when test="${item.name == causebySystem}">
					                    <option value="${item.name}" selected="selected">${item.value}</option>
					                </c:when>
									<c:otherwise>
										<option value="${item.name}">${item.value}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select> 
		           	</td>
	              	<td><fmt:message key="AlDyNonFinishDetail.transtype"/></td>
		           	<td> 
						<form:input type ="text" path="transType" maxlength="250" style="width:150px;" rows="3" id = "transType" name="transType"/>
		           	</td>
	            </c:when>
				<c:otherwise>
					<td><fmt:message key="AlDyNonFinishDetail.causebySystem"/></td>
		           	<td > 
						<select name="causebySystem" id="causebySystem" style="width: 200px;height:20px;">
			           		<option value=""><fmt:message key="global.Chosse"/></option>
			           		<c:forEach var="item" items="${causebySyList}">
								<c:choose>
					                <c:when test="${item.name == causebySystem}">
					                    <option value="${item.name}" selected="selected">${item.value}</option>
					                </c:when>
									<c:otherwise>
										<option value="${item.name}">${item.value}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select> 
		           	</td>
		           	<td colspan="2"></td>
				</c:otherwise>
			</c:choose>
		</tr>
		<tr>
           <td><fmt:message key="AlDyNonFinishDetail.causebyContent"/></td>
	        <td colspan="5"> 
				<form:textarea path="causebyContent" style="font-family:tahoma;width:100%; font-size:12px;height:30px;" name="causebyContent" id="causebyContent" rows="10" maxlength="900"/>
	         </td>
       </tr>
       <tr>
           <td><fmt:message key="AlDyNonFinishDetail.resultContent"/></td>
         <td colspan="5"> 
        	 <form:textarea path="resultContent" style="font-family:tahoma;width:100%; font-size:12px;height:30px;" name="resultContent" id="resultContent" rows="10" maxlength="900"/>
         </td>
       </tr>
       <tr>
           <td></td>
           <td colspan="5">
               <input type="submit" class="button" value="<fmt:message key="button.save"/>" />
           	   <input type="button" class="button" value="<fmt:message key="button.cancel"/>" onClick='window.location="danhsach.htm?alarmType=${alarmType}&netWork=${netWork}"'>
					
           </td>
       </tr>
    </table>
</form:form>

<style>
    .error {
    	color: red;
    }
</style> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>

<link type="text/css" rel="Stylesheet" href="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.8.23.custom.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui.min-1.8.9.js"></script>



<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.filter.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/assets/style.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/selectStyle/assets/prettify.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/jquery.multiselect.filter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectStyle/assets/prettify.js"></script>


<script type="text/javascript">

function focusIt()
{
	if(document.checkform.bscid.value==""){
	  var mytext = document.getElementById("bscid");
	  mytext.focus();
	}
}
onload = focusIt;
</script>
<script type="text/javascript">
	$(function() {
		var availableTags2 = [];
		var i = 0;
		<c:forEach items="${transTypeList}" var="listOfNames1">
			availableTags2[i] = "<c:out value='${listOfNames1}' escapeXml='false' />";
			i = i + 1;
		</c:forEach>
		loadtransType(availableTags2);
	});
	function loadtransType(availableTags){
		function split( val ) {
		return val.split( /,\s*/ );
		}
		function extractLast( term ) {
		return split( term ).pop();
		}
		$("#transType")
		// don't navigate away from the field on tab when selecting an item
		.bind( "keydown", function( event ) {
		if ( event.keyCode === $.ui.keyCode.TAB &&
		$( this ).data( "autocomplete" ).menu.active ) {
		event.preventDefault();
		}
		})
		.autocomplete({
		minLength: 0,
		source: function( request, response ) {
		// delegate back to autocomplete, but extract the last term
		response( $.ui.autocomplete.filter(
		availableTags, extractLast( request.term ) ) );
		},
		focus: function() {
		// prevent value inserted on focus
		return false;
		},
		select: function( event, ui ) {
		//var terms = split( this.value );
		// remove the current input
		//terms.pop();
		//terms.push( ui.item.value );
		// add placeholder to get the comma-and-space at the end
		//terms.push( "" );
		//this.value = terms;
		this.value = ui.item.value;
		return false;
		}
		});
	}	
</script>
  