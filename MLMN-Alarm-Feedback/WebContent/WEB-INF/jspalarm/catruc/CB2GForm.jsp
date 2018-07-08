
<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxwindow.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxdropdownbutton.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxtree.js"></script>
  <style>
 .shiftId
{
color:blue;
}

.isLLine {
	color:red;
	display: none;
}
 </style>  
 <style>
    .error {
    	color: red;
    }
    
    p.small {
	    display: none;
	}
	
	#contents {
	    border: 0 none;
	}
	
	#alAlarmWorks {
	    bottom: 20px;
	    left: 0;
	    overflow: hidden;
	    position: absolute;
	    right: 10px;
	    top: 125px;
	    z-index: 1000;
	}
	
	
#contain1 {
    height: 250px;
}
	
	
#contain2 {
    left: 0;
    overflow-x: hidden;
    overflow-y: auto;
    position: absolute;
    right: 0;
    top: 350px;
    bottom:2px;
}
</style>      
<title>${titleF}</title>
<content tag="heading">${titleF}</content>

<form:form commandName="alAlarmWorks" method="post" action="form23G.htm?warningTp=${warningTp}"> 
	<form:hidden path="id"/>
	<form:hidden path="shiftId"/>
	<input type="hidden" name="warningTpe" id="warningTpe" value="${warningTp}">
	<input type="hidden" name="region" id=region value="${region}">
	<input type="hidden" name="oldOperator" id="oldOperator" value="${oldOperator}">
	<input type="hidden" name="oldSystem" id="oldSystem" value="${oldSystem}">
	<input type="hidden" name="oldSdate" id="oldSdate" value="${oldSdate}">
	<input type="hidden" name="oldEdate" id="oldEdate" value="${oldEdate}">
	<input type="hidden" name="idShift" id="idShift" value="${idShift}">
	<input type="hidden" name="highlight" id="highlight" value="${highlight}">
	<input type="hidden" name="note" id="note" value="${note}">
	
	<c:if test="${not empty globalCaTruc}">
		<input type="hidden" name="shiftRegion" id=shiftRegion value="${globalCaTruc.region}">
	</c:if>
	
	<div id="contain1">
	<table class="simple2"> 
      <tr>
           <td style="width: 120px"><fmt:message key="vAlAlarmLog.alarmName"/><font color="red">(*)</font></td>
           <td colspan="5">
           		<form:input type ="text" path="alarm" class = "long" maxlength="200" rows="3"/>
           		<font color="red"><span id='erroralarm'><form:errors path="alarm"/></span></font>
					
           </td>
            <td style="width: 150px"><fmt:message key="AlAlarmWork.isSpecial"/>&nbsp;&nbsp;
           		<c:choose>
					<c:when test="${alAlarmWorks.isSpecial=='Y'}">
						<input id="isSpecialCK" name="isSpecialCK" type="checkbox" checked="checked">
					</c:when>
					<c:otherwise>
						<input id="isSpecialCK" name="isSpecialCK" type="checkbox" >
					</c:otherwise>
				</c:choose>		
				<form:hidden path="isSpecial"/>
           </td>
           <td style="width: 100px"><fmt:message key="AlAlarmWork.isLeaseLine"/>&nbsp;&nbsp;
           		<c:choose>
					<c:when test="${isLeaseLine=='Y'}">
						<input id="isLeaseLineCK" name="isLeaseLineCK" type="checkbox" checked="checked">
					</c:when>
					<c:otherwise>
						<input id="isLeaseLineCK" name="isLeaseLineCK" type="checkbox" >
					</c:otherwise>
				</c:choose>		
				<input type="hidden" id="isLeaseLine" name="isLeaseLine" value="${isLeaseLine}"/>
			 </td>
      </tr> 
   		 <tr>
		           <td><fmt:message key="vAlAlarmLog.NE"/><font color="red">(*)</font></td>
		           <td style="width: 180px">
		           		<form:input path="operator"  id ="operator"/>
		           		<font color="red"><span id='errorOperation'>${errorOperation}<form:errors path="operator"/></span></font>
					
		            </td>
		           <td style="width: 70px"><fmt:message key="vAlAlarmLog.system"/><font color="red">(*)</font></td>
		           	<td colspan="5">
						<form:input type="text" path="system" />
						<font color="red"><span id='errorsystem'>${errorsystem}<form:errors path="system"/></span></font>
						&nbsp;&nbsp;
          			<fmt:message key="warning.countSiteF"/>&nbsp;
           			<form:input type ="text" path="countSite" size="7" maxlength="10" style="width:70px;"/><font color="red"><form:errors path="countSite"/></font>
           			&nbsp;&nbsp;
           			<fmt:message key="warning.siteList"/><span class="isLLine" >(*)</span>
           			&nbsp;<form:input type="text" path="siteList" />
					<font color="red"><span id='errorsystem'>${errorsiteList}<form:errors path="siteList"/></span></font>
					&nbsp;&nbsp;
          			<fmt:message key="warning.siteQuantity"/>&nbsp;
           			<form:input type ="text" path="siteQuantity" size="7" maxlength="10" style="width:70px;"/><font color="red"><form:errors path="siteQuantity"/></font>
           			
	    	</td>
      </tr> 
      <tr>
           <td><fmt:message key="vAlAlarmLog.sdate"/><font color="red">(*)</font></td>
           <td>
           		<input type ="text"  value="${sdate}" name="sdate" id="sdate" size="20" maxlength="16" class = "sdate" >
	   			 <img alt="calendar" title="Click to choose the start date" id="choosesdate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red"><span id='errorsdate' >${errorsdate}<form:errors path="stime"/></span></font>
	    	</td>
           <td><fmt:message key="vAlAlarmLog.edate"/></td>
           <td colspan="5">
           		<input type ="text"  value="${edate}" name="edate" id="edate" size="20" maxlength="16" class = "edate">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseedate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red">${erroredate}<form:errors path="etime"/></font>
	    	</td>
	    	
	    	
      </tr> 
      <tr>
           <td ><fmt:message key="vAlAlarmLog.alarmType"/></td>
			<td><div id='alarmType'></div></td>
           <td  style="width:120px;"><fmt:message key="vAlAlarmLog.dept"/></td>
           <td  style="width:200px;">
           		<div id='cbdept'></div><form:hidden path="dept"  id ="dept"/>
				<font color="red"><form:errors path="dept"/></font>
           </td>
           <td><fmt:message key="vAlAlarmLog.teamProcess"/></td>
           <td>
          <!--   <div id='teamProcess'></div> -->
          <form:input type ="text" path="teamProcess" maxlength="500" style="width:95%;" rows="3"/>
			<font color="red"><form:errors path="teamProcess"/></font>
           </td>
       	<td><fmt:message key="vAlAlarmLog.groups"/></td>
           <td>
          <!--   <div id='teamProcess'></div> -->
          <form:input type ="text" path="groups" maxlength="500" style="width:95%;" rows="3"/>
			<font color="red"><form:errors path="groups"/></font>
           </td>
        
       </tr>
       <tr>
	     	
           <td><fmt:message key="vAlAlarmLog.contactTime"/><span class="isLLine" >(*)</span></td>
           <td >
           		<input type ="text"  value="${contactTime}" name="contactTime" id="contactTime" size="20" maxlength="16" class = "contactTime">
	   			 <img alt="calendar" title="Click to choose the contact time" id="choosecontactTime" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red">${errorcontactTime}<form:errors path="contactTime"/></font>
	    	</td>
	    	<td><fmt:message key="vAlAlarmLog.actionProcess"/><span class="isLLine" >(*)</span></td>
           <td > 
          		 <div id='actionProcess'></div>
           	<%-- <form:input path="actionProcess"  class = "long" rows="10" maxlength="900"/> --%>
          </td>
          <td><fmt:message key="vAlAlarmLog.leaseLine"/><span class="isLLine" >(*)</span></td>
	       <td colspan="2"> 
	         	<div id='leaseLineCB' style = "width: 100%"></div>
	         	<form:hidden path="leaseLine"  id ="leaseLine"/>
	        </td>
	        <td style="width: 100px"><fmt:message key="AlAlarmWork.isSendLeaseLine"/>&nbsp;&nbsp;
           		<c:choose>
					<c:when test="${alAlarmWorks.isSendLeaseLine=='Y'}">
						<input id="isSendLeaseLineCK" name="isSendLeaseLineCK" type="checkbox" checked="checked">
					</c:when>
					<c:otherwise>
						<input id="isSendLeaseLineCK" name="isSendLeaseLineCK" type="checkbox" >
					</c:otherwise>
				</c:choose>		
				<input type="hidden" id="isSendLeaseLine" name="isSendLeaseLine" value="${alAlarmWorks.isSendLeaseLine}"/>
			 </td>
       	</tr>
        <tr>
        	<td><fmt:message key="vAlAlarmLog.userExcute"/></td>
           <td style="border:0;">
           		<%-- <div id='cbUserProcess'></div><form:hidden path="userProcess"  id ="userProcess"/> --%>
        		<!-- <select name="userProcess" id="userProcess" multiple="multiple" style="width:160px;"></select> -->
				<form:input type ="text" path="userProcess" maxlength="500" style="width:95%;" rows="3"/>
            <font color="red"><span id='erroruserProcess' >${erroruserProcess}<form:errors path="stime"/></span></font>
           </td>
           <td><fmt:message key="vAlAlarmLog.causebySystem"/></td>
           <td>
      			<div id='causebySystem' style = "width: 100%"></div>
	    	</td>
	    	 <td><fmt:message key="vAlAlarmLog.causeby"/></td>
           <td  colspan="4"> 
           		<form:input type ="text" path="causeby"   class = "long" rows="3" />
          </td>
	    </tr>
	    <tr>
	    	<td ><fmt:message key="vAlAlarmLog.standardName"/></td>
			<td colspan="3"><div id='standardName' style = "width: 100%"></div></td>
	     	<td ><fmt:message key="vAlAlarmLog.statusUCTT"/></td>
			<td><div id='statusUCTT'></div></td>
			<td><fmt:message key="vAlAlarmLog.timeUCTT"/></td>
           	<td > 
           		<form:input path="timeUCTT" class = "long" /><font color="red"><form:errors path="timeUCTT"/></font>
          	</td>
		</tr>
		 <tr>
	    	<td  ><fmt:message key="vAlAlarmLog.mdSdate"/></td>
           <td>
           		<input type ="text"  value="${mdSdate}" name="mdSdate" id="mdSdate" size="20" maxlength="16" class = "edate" >
	   			 <img alt="calendar" title="Click to choose the start date" id="choosemdSdate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	    		<font color="red">${errormdSdate}<form:errors path="mdSdate"/></font>
	    	</td>
	     	<td  ><fmt:message key="vAlAlarmLog.powerSite"/></td>
			<td><form:input path="powerSite" class = "long" /> - Baterry: <label id ='baterryOfSite'></label>
			<br>
			<font color="red"><form:errors path="accuLeast"/></font></td>
			<td style="width:150px;"><fmt:message key="vAlAlarmLog.baterryDuar"/></td>
           	<td > 
           		<form:input path="baterryDuar" class = "long" /><font color="red"><form:errors path="baterryDuar"/></font>
          	</td>
          	<td><fmt:message key="vAlAlarmLog.accuLeast"/></td>
           	<td > 
           		<c:choose>
					<c:when test="${alAlarmWorks.accuLeast=='Y'}">
						<input id="accuLeastCK" name="accuLeastCK" type="checkbox" checked="checked">
					</c:when>
					<c:otherwise>
						<input id="accuLeastCK" name="accuLeastCK" type="checkbox" >
					</c:otherwise>
				</c:choose>		
				<form:input type="hidden" path="accuLeast"/>
          	</td>
		</tr>
        <tr>
         	
           <td><fmt:message key="vAlAlarmLog.alarmInfo"/></td>
           <td colspan="7"> 
           	<form:input path="alarmInfo"  class = "long" rows="10" maxlength="900"/>
          </td>
       </tr>
       <tr>
           <td><fmt:message key="vAlAlarmLog.reportTreatment"/></td>
           <td colspan="7"> 
           	<form:input path="reportProcess"  class = "long" rows="10" maxlength="900"/>
          </td>
       </tr>
       <tr>
           <td></td>
           <td colspan="7">
           		<!-- <input type="hidden" id="action" name="action"> -->
           		<input type="submit" class="button" id="submit"   value="<fmt:message key="global.form.luulai"/>" title="Lưu lại và quay trở lại màn hình danh sách"/>
           	  	<input class="button" type="button" id="btsaveAndAdd" name = "btsaveAndAdd" value="<fmt:message key="global.form.SaveAndAdd"/>" title="Lưu lại thông tin và tạo mới"/>
           	  	<input type="button" class="button"  id="btSaveAction" value="<fmt:message key="global.form.saveAction"/>">
				<input type="button" class="button"  id="reset" value="<fmt:message key="global.form.reset"/>">
					<c:choose>
	                <c:when test="${note == 'filter'}">
		                   <input type="button" class="button" id="btCancel" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=${warningTp}&region=${region}"'>
						
		             </c:when>
					<c:otherwise>
						 <input type="button" class="button" id="btHuybo" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm?warningTp=${warningTp}&region=${region}"'>	
					</c:otherwise>
				</c:choose>	
				
           </td>
           
       </tr>
    </table>
    </div>
    
    <div id="contain2">
    <div style="color: blue; padding-top: 5px;">
	<span style="color: blue; padding-top: 10px;"><fmt:message key="vAlAlarmLog.listSame"/></span>
</div>
<%-- <c:if test="${alAlarmWorks.id == null}"> --%>
	<div id="filter" style="padding-bottom:5px;">
	<table>
		<tr>
		<td style="width:50px;"><fmt:message key="vAlAlarmLog.day"/></td>
				<td><div id='day'></div>
				</td>
				<td style="padding-left: 10px;width: 80px;"><fmt:message key="vAlAlarmLog.alarmType"/></td>
				<td><div id='alarmTypeTK'></div>
				</td>
				<td style="padding-left: 10px;"><fmt:message key="vAlAlarmLog.statusKT"/></td>
				<td><div id='status'></div>
				</td>
				<td style="padding-left: 10px;width: 50px;"><fmt:message key="vAlAlarmLog.NE"/></td>
				<td>
				    <input type ="text"  value="${bscidTK}" name="bscidTK" id="bscidTK" size="25" width="80px">
				</td>
				<td style="padding-left: 10px;width: 50px;"><fmt:message key="vAlAlarmLog.system"/></td>
				<td>
					<input type ="text"  value="${cellidTK}" name="cellidTK" id="cellidTK" size="20"  width="80px">
					</td>
				<td style="padding-left: 10px;"><input class="button" type="button" id="filterTK" name="filterTK"
								value="<fmt:message key="global.form.timkiem"/>" />
				</td>
			</tr>
	</table>
	</div>
<%-- </c:if> --%>
<div id="jqxWidget" style="font-size: 13px; font-family: Verdana; float: left;width:100%;">
       	<div style="float: right;" id="jqxlistbox"></div>
        <div id="jqxgrid"></div>
 </div>
 </div>
    
</form:form>
<div id="jqxwindow">
		<div><fmt:message key="handingLibrary.titleAdd"/></div>
		<div>
       	<table class="simple2">
       		<tr>
       			<td style="width:70px;"><fmt:message key="alarmLog.alarmType"/></td>
				<td>
					<div id='alarmTypeH'></div>
				</td>
				<td style="width:120px;"><fmt:message key="alarmLog.alarmMappingId"/></td>
				<td>
					<div id='alarmMappingIdH'></div>
					<!-- <input type ="text"  name="alarmMappingIdH" id="alarmMappingIdH" size="20" maxlength="80"> -->
				</td>
			</tr>
			<tr>
				<td style="width:150px;"><fmt:message key="alarmLog.alarmMappingName"/></td>
				<td colspan="3">
					<div id='alarmMappingNameH'></div>
					<!-- <input type ="text"  name="alarmMappingNameH" id="alarmMappingNameH" size="20" maxlength="80"> -->
				</td>
       		</tr>
       		<tr>
       			<td style="width:100px;"><fmt:message key="handingLibrary.causeby"/></td>
       			<td colspan="3"><input type ="text"  name="causebyH" id="causebyH" size="20" maxlength="80">
       			</td>
       		</tr>
       		<tr>
       			<td><fmt:message key="handingLibrary.actionProcess"/></td>
       			<td colspan="3"><input type ="text"  name="actionProcessH" id="actionProcessH" size="20" maxlength="80" width="80px"></td>
       		</tr>
       		<tr>
       			<td><fmt:message key="handingLibrary.description"/></td>
       			<td colspan="3"><input type ="text"  name="descriptionH" id="descriptionH" size="20" maxlength="80" width="80px"></td>
       		</tr>
       		<tr>
       			<td></td>
       			<td colspan="3">
       				<input type="button" class="button" value="Save" id="btSave" />
                    <input type="button" class="button" value="Cancel" id="btCancel" />
                </td>
       		</tr>
       </table>
     </div>
</div>

 <script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>

 
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
    
    Calendar.setup({
        inputField		:	"mdSdate",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "choosemdSdate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
    
    Calendar.setup({
        inputField		:	"contactTime",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "choosecontactTime",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
    $("#submit").click(function(){
        if ($('#isLeaseLine').val()=='Y' && ($('#sdate').val()==''||$('#contactTime').val()==''
				||$("#system").val()==''||$("#siteList").val()==''||$("#leaseLine").val()==''||$("#actionProcess").val()==''))
        	{
        		alert('Sự cố lease line cần cập nhật đầy đủ thông tin (SDate, Devices, Contact Time,  Contact, Site List, Lease Line)');
        	}
    });
	$("#isSpecialCK").change( function() {
		if ($(this).is(':checked')) {
			$('#isSpecial').val("Y");
		} else {
			$('#isSpecial').val("N");
		}
	});
	
	
	if ($('#isSpecialCK').is(':checked')) {
		$('#isSpecial').val("Y");
	} else {
		$('#isSpecial').val("N");
	}
	
	//ACCU yeu
	$("#accuLeastCK").change( function() {
		if ($(this).is(':checked')) {
			$('#accuLeast').val("Y");
		} else {
			$('#accuLeast').val("N");
		}
	});
	
	
	if ($('#accuLeastCK').is(':checked')) {
		$('#accuLeast').val("Y");
	} else {
		$('#accuLeast').val("N");
	}
	
	//tram lease line
	$("#isLeaseLineCK").change( function() {
		
		if ($(this).is(':checked')) {
			$('#isLeaseLine').val("Y");
			$('.isLLine').show();
			$('#isSendLeaseLine').val("Y");
			$('#isSendLeaseLineCK').prop( "checked", true );
			
		} else {
			$('#isLeaseLine').val("N");
			$('.isLLine').hide();
			$('#isSendLeaseLine').val("N");
			$('#isSendLeaseLineCK').prop( "checked", false );
		}
	});
	
	if ($('#isLeaseLineCK').is(':checked')) {
		$('#isLeaseLine').val("Y");
		$('#isSendLeaseLine').val("Y");
		$('#isSendLeaseLineCK').prop( "checked", true );
		$('.isLLine').show();
	} else {
		$('#isLeaseLine').val("N");
		$('.isLLine').hide();
		$('#isSendLeaseLine').val("N");
		$('#isSendLeaseLineCK').prop( "checked", false );
	}
	
	$("#isSendLeaseLineCK").change( function() {
		
		if ($(this).is(':checked')) {
			$('#isSendLeaseLine').val("Y");
			
		} else {
			$('#isSendLeaseLine').val("N");
			
		}
	});
	
	if ($('#isSendLeaseLineCK').is(':checked')) {
		$('#isSendLeaseLine').val("Y");
		
	} else {
		$('#isSendLeaseLine').val("N");
	
	}
	
</script>
<script type="text/javascript">
function dateToYMDHMS(date) {
	//alert(date);
    var d = date.getDate();
    var m = date.getMonth()+1;
    var y = date.getFullYear();
    var h = date.getHours();
    var mi = date.getMinutes();
    var s = date.getSeconds();
    //alert(d+'-'+m+'-'+y);
    return '' + (d <= 9 ? '0' + d : d) + '/' + (m<=9 ? '0' + m : m) + '/' + y+ ' ' + (h<=9 ? '0' + h : h)+ ':' + (mi<=9 ? '0' + mi : mi)+ ':' + (s<=9 ? '0' + s : s) ;
}
$(document).ready(function(){
	
	var urlLLProvider= "${pageContext.request.contextPath}/ajax/getLLProvider.htm";
	// prepare the data
	var sourceaLLProvider =
	{
	    datatype: "json",
	    url: urlLLProvider,
	    datafields: [
	                 { name: 'llProvider' }
	             ],
	    async: false
	};
	var dataAdapterLLProvider = new $.jqx.dataAdapter(sourceaLLProvider);
	// Create a jqxComboBox
	$("#actionProcess").jqxComboBox({ source: dataAdapterLLProvider, displayMember: "llProvider", valueMember: "llProvider",  width: 170, height: 20, theme: theme  });
	var actionProcess =  "<c:out value='${alAlarmWorks.actionProcess}'/>";
	if(actionProcess=="")
		$('#actionProcess').val('ALL');
	else
		$('#actionProcess').val(actionProcess);
		
	var siteList = $("#siteList").val();
	var llProvider = $("#actionProcess").val();
	//Create a jqxDropDownList LeaseLine
	var urlLeaseLine = "${pageContext.request.contextPath}/ajax/getLeaseLine.htm?llNode="+siteList+"&llProvider="+llProvider;
	// prepare the data
	var sourceLeaseLine =
	{
	    datatype: "json",
	    
        datafields: [
                     { name: 'leaseLine' }
                 ],
                 url: urlLeaseLine,
	    async: false
	};
	 var dataAdapterLeaseLine = new $.jqx.dataAdapter(sourceLeaseLine);
	$("#leaseLineCB").jqxComboBox({ source: dataAdapterLeaseLine, displayMember: "leaseLine", valueMember: "leaseLine",  width: '100%',height: 20,theme: theme,enableHover: true,
		autoOpen: true,autoComplete: true });
	var leaseLine =  "<c:out value='${alAlarmWorks.leaseLine}'/>";
	if(leaseLine!=null&&leaseLine!="")
		$('#leaseLineCB').val(leaseLine);
		
	$('#actionProcess').on('change', function (event) { 
		var siteList = $("#siteList").val();
		var llProvider = $("#actionProcess").val();
		var urlLeaseLine = "${pageContext.request.contextPath}/ajax/getLeaseLine.htm?llNode="+siteList+"&llProvider="+llProvider;
		// prepare the data
		var sourceLeaseLine =
		{
		    datatype: "json",
		    
	        datafields: [
	                     { name: 'leaseLine' }
	                 ],
	                 url: urlLeaseLine,
		    async: false
		};
		 var dataAdapterLeaseLine = new $.jqx.dataAdapter(sourceLeaseLine);
		 $("#leaseLineCB").jqxComboBox({ source: dataAdapterLeaseLine, displayMember: "leaseLine", valueMember: "leaseLine",  width: '100%',height: 20,theme: theme,enableHover: true,
			 autoOpen: true,autoComplete: true });
			
	}); 
	
	 $("#leaseLineCB").change(function(){
		   var leaseLine = $("#leaseLineCB").val();
			$('#leaseLine').val(leaseLine);
	 }); 	 
	
	var renderer = function (itemValue, inputValue) {
        var terms = inputValue.split(/,\s*/);
        var value = inputValue;
     
         if (inputValue.indexOf(itemValue) < 0)
	    	{
      
        	 // remove the current input
             terms.pop();
             // add the selected item
	    	 terms.push(itemValue);
	         // add placeholder to get the comma-and-space at the end
	        // terms.push("");
	         value = terms.join(",");
	    	}
       
        return value;
    };
	var theme =  getTheme(); 
	$('#filterTK').jqxButton({ theme: theme });
	$('#btCancel').jqxButton({ theme: theme });
    $('#btHuybo').jqxButton({ theme: theme });
    $('#btSave').jqxButton({ theme: theme });
    $('#reset').jqxButton({ theme: theme });
    $('#btSaveAction').jqxButton({ theme: theme });
    $('#submit').jqxButton({ theme: theme });
    $('#btsaveAndAdd').jqxButton({ theme: theme });
   //input alarm
 //   $("#alarm").jqxInput({placeHolder: "Enter alarm", height: 20, width: '100%', minLength: 0, maxLength: 170, theme: theme});
    ${alarmList}
	 $("#alarm").jqxInput({ placeHolder: "Enter alarm",  height: 20, width: '98%', minLength: 0, maxLength: 170, theme: theme,
        source: alarmList
    });
    var alarm =  "<c:out value='${alAlarmWorks.alarm}'/>";
   // alert(bscid);
    if(alarm!="")
		$('#alarm').val(alarm);
    
    
	//ComboBox operator
 	${operatorList}
 	 $("#operator").jqxInput({ placeHolder: "Enter a BSC/RNC", height: 20, width: 200, theme: theme,
         source: function (query, response) {
             var item = query.split(/,\s*/).pop();
             // update the search query.
             $("#operator").jqxInput({ query: item });
             response(operatorList);
         },
         renderer: renderer
     });
     var operator =  "<c:out value='${alAlarmWorks.operator}'/>";
    // alert(bscid);
     if(operator!="")
 		$('#operator').val(operator);
 	
 	//input countSite
    $("#countSite").jqxInput({ height: 20, width: 30, minLength: 0, maxLength: 10, theme: theme});
 	  var countSite =  "<c:out value='${alAlarmWorks.countSite}'/>";
    if(countSite!=null)
		{
    		$('#countSite').val(countSite);
		} 
    
    
    $("#siteQuantity").jqxInput({ height: 20, width: 30, minLength: 0, maxLength: 10, theme: theme});
	  var siteQuantity =  "<c:out value='${alAlarmWorks.siteQuantity}'/>";
  if(siteQuantity!=null)
		{
  		$('#siteQuantity').val(siteQuantity);
		} 
  
	//Create a jqxDropDownList causeby sys
   	var urlCausebySys = "${pageContext.request.contextPath}/ajax/getCausebySys.htm";
    // prepare the data
    var sourceCausebySys =
    {
        datatype: "json",
        datafields: [
            { name: 'value' },
            { name: 'name' }
        ],
        url: urlCausebySys,
        async: false
    };
    var dataAdapterCausebySys = new $.jqx.dataAdapter(sourceCausebySys);
    $("#causebySystem").jqxDropDownList({ source: dataAdapterCausebySys, displayMember: "value", valueMember: "name", width: 250,height: 20,theme: theme,enableHover: true });
    var causebySys =  "<c:out value='${alAlarmWorks.causebySystem}'/>";
    $("#causebySystem").jqxDropDownList('addItem', { label: '', value: ''}, 0 );
 	if(causebySys!=null&&causebySys!="")
		$('#causebySystem').val(causebySys);
 	${systemList}
 	$("#system").jqxInput({ height: 20, width: 300, theme: theme,
        source: function (query, response) {
            var item = query.split(/,\s*/).pop();
            // update the search query.
            $("#system").jqxInput({ query: item });
            response(systemList);
        },
        renderer: renderer
    });
 	var system =  "<c:out value='${alAlarmWorks.system}'/>";
 	//alert(cellid);
    if(system!="")
		$('#system').val(system);
    
    $("#siteList").jqxInput({ height: 20, width: 250, theme: theme,
        source: function (query, response) {
            var item = query.split(/,\s*/).pop();
            // update the search query.
            $("#siteList").jqxInput({ query: item });
            response(systemList);
        },
        renderer: renderer
    });
 	var siteList = "<c:out value='${alAlarmWorks.siteList}'/>"; 
 	//alert(cellid);
    if(siteList!="")
		$('#siteList').val(siteList);
    
    //tram mat dien
    $("#powerSite").jqxInput({ height: 20, width: 100, theme: theme,
        source: function (query, response) {
            var item = query.split(/,\s*/).pop();
            // update the search query.
            $("#powerSite").jqxInput({ query: item });
            response(systemList);
        },
        renderer: renderer
    });
 	var powerSite = "<c:out value='${alAlarmWorks.powerSite}'/>"; 
	var network =  "<c:out value='${warningTpe}'/>";
 	if(powerSite!=""){
		$('#powerSite').val(powerSite);
		$.getJSON("${pageContext.request.contextPath}/ajax/getBaterrySite.htm",{site: site,network:network}, function(j){
			alert(j);
   			$("#baterryOfSite").text(j);	
    	});
 	}
 	$('#powerSite').on('change', function () { 
		var site = $('#powerSite').val(); 
		$.getJSON("${pageContext.request.contextPath}/ajax/getBaterrySite.htm",{site: site,network:network}, function(j){
			var baterryOld = j;
			$("#baterryOfSite").text(baterryOld);	
			var baterryNew = $("#baterryDuar").val();	
			if (baterryOld !='' && baterryNew!='' && baterryNew < baterryOld){
				$('#accuLeastCK').prop( "checked", true );
				$('#accuLeast').val("Y");
			}
			else {
			$('#accuLeastCK').prop( "checked", false );
			$('#accuLeast').val("N");}		
		});
	});
    //cell
    $("#cellidTK").jqxInput({placeHolder: "Enter device", height: 20, width: 200, theme: theme,
        source: function (query, response) {
            var item = query.split(/,\s*/).pop();
            // update the search query.
            $("#cellidTK").jqxInput({ query: item });
            response(systemList);
        },
        renderer: renderer
    });
 	var cellidTK =  "<c:out value='${cellidTK}'/>";
 	//alert(cellid);
    if(cellidTK!="")
		$('#cellidTK').val(cellidTK);
    
   //input causeby
    $("#causeby").jqxInput({placeHolder: "Enter causeby", height: 20, width: '100%', minLength: 0, maxLength: 200, theme: theme});
  //input alarmInfo
    $("#alarmInfo").jqxInput({placeHolder: "Enter alarmInfo", height: 20, width: '100%', minLength: 0, maxLength: 900, theme: theme});

  //input day
  	var day =  "<c:out value='${day}'/>";
	$("#day").jqxDateTimeInput({ width: '100px', height: '20px',  formatString: 'dd/MM/yyyy' });
	$('#inputday').val(day);
	//input reportProcess
    $("#reportProcess").jqxInput({placeHolder: "Enter reportProcess", height: 20, width: '100%', minLength: 0, maxLength: 900, theme: theme});
   
	
 // Create a jqxComboBox AlarmType
  	var network =  "<c:out value='${warningTp}'/>";
	var urlalarmType = "${pageContext.request.contextPath}/ajax/getAlarmTypeAtShift.htm?network="+network;
    // prepare the data
    var sourcealarmType =
    {
        datatype: "json",
        datafields: [
            { name: 'alarmType'}
        ],
        url: urlalarmType,
        async: false
    };
    var dataAdapteralarmType = new $.jqx.dataAdapter(sourcealarmType);
    // Create a jqxComboBox
    $("#alarmTypeTK").jqxComboBox({ source: dataAdapteralarmType, displayMember: "alarmType", valueMember: "alarmType", width: 150, height: 20, theme: theme,autoDropDownHeight: true  });
    var alarmTypeTK =  "<c:out value='${alarmTypeTK}'/>";
    if(alarmTypeTK=="")
		$('#alarmTypeTK').val('ALL');
	else
		$('#alarmTypeTK').val(alarmTypeTK);
    
    
    $("#alarmType").jqxComboBox({ source: dataAdapteralarmType, displayMember: "alarmType", valueMember: "alarmType", width: 150, height: 20, theme: theme,autoDropDownHeight: true  });
    var alarmType =  "<c:out value='${alAlarmWorks.alarmType}'/>";
    if(alarmType=="")
		$('#alarmType').val('ALL');
	else
		$('#alarmType').val(alarmType);
    // Create a jqxComboBox status uctt
    var urlstatusUCTT = "${pageContext.request.contextPath}/ajax/getStatusUCTT.htm";
       // prepare the data
       var sourcestatusUCTT =
       {
           datatype: "json",
           datafields: [
                        { name: 'value'},
                        { name: 'name'}
           ],
           url: urlstatusUCTT,
           async: false
       };
       var dataAdapterstatusUCTT = new $.jqx.dataAdapter(sourcestatusUCTT);
       // Create a jqxComboBox
       $("#statusUCTT").jqxComboBox({ source: dataAdapterstatusUCTT, displayMember: "value", valueMember: "name", width: 150, height: 20, theme: theme,autoDropDownHeight: true  });
       var statusUCTT =  "<c:out value='${alAlarmWorks.statusUCTT}'/>";
       $('#statusUCTT').val(statusUCTT);
       
       // Create a jqxComboBox standard error
      var urlStandardName = "${pageContext.request.contextPath}/ajax/getStandardName.htm?vendor=&network=";
      // prepare the data
      var sourceStandardName =
      {
          datatype: "json",
          datafields: [
                       { name: 'alarmNameMapping'}
          ],
          url: urlStandardName,
          async: false
      };
      var dataAdapterStandardName = new $.jqx.dataAdapter(sourceStandardName);
      // Create a jqxComboBox
      $("#standardName").jqxComboBox({ source: dataAdapterStandardName, displayMember: "alarmNameMapping", valueMember: "alarmNameMapping", width: 570, height: 20, theme: theme,autoDropDownHeight: true  });
      var standardName =  "<c:out value='${alAlarmWorks.standardName}'/>";
  		$('#standardName').val(standardName);
          
       
    //Create a jqxDropDownList StatusKT
   	var urlstatus = "${pageContext.request.contextPath}/ajax/getStatusFinishList.htm";
    // prepare the data
    var sourcestatus =
    {
        datatype: "json",
        datafields: [
            { name: 'value'},
            { name: 'name'}
        ],
        url: urlstatus,
        async: false
    };
    var dataAdapterstatus = new $.jqx.dataAdapter(sourcestatus);
    $("#status").jqxComboBox({ source: dataAdapterstatus, displayMember: "value", valueMember: "name", width: 160,height: 20,theme: theme,autoDropDownHeight: true  });
 	var status =  "<c:out value='${status}'/>";
    if(status!=null&&status!="")
    	$("#status").val(status);
    //input BSC, Cell
    //Input BSC
  
    ${bscTLList}
    $("#bscidTK").jqxInput({ placeHolder: "Enter a BSC/RNC", height: 20, width: 200, theme: theme,
        source: function (query, response) {
            var item = query.split(/,\s*/).pop();
            // update the search query.
            $("#bscidTK").jqxInput({ query: item });
            response(bscTLList);
        },
        renderer: renderer
    });
    var bscid =  "<c:out value='${bscidTK}'/>";
   // alert(bscid);
    if(bscid!="")
		$('#bscidTK').val(bscid);
  // create jqxWindow.
    $("#jqxwindow").jqxWindow({ resizable: true, theme: theme, autoOpen: false, minWidth: 1000, maxWidth: 1800, minHeight: 250,isModal: true});
  //input alarmType
    $("#alarmTypeH").jqxDropDownList({ source: dataAdapteralarmType, displayMember: "alarmType", valueMember: "alarmType", width: 150, height: 20, theme: theme,autoDropDownHeight: true  });
  //combobox alarm Maping
    var urlalarmMappingName = "${pageContext.request.contextPath}/ajax/getAlarmMappingName.htm?vendor=&alarmType="+$("#alarmTypeH").val()+"&network="+$("#warningTpe").val();
	// prepare the data
	var sourcealarmMappingName =
	{
	    datatype: "json",
	    datafields: [
	        { name: 'alarmMappingId'},
	        { name: 'alarmMappingName'}
	    ],
	    url: urlalarmMappingName,
	    async: false
	};
	var dataAdapteralarmMappingName = new $.jqx.dataAdapter(sourcealarmMappingName);
	// Create a jqxComboBox
	$("#alarmMappingIdH").jqxComboBox({ source: dataAdapteralarmMappingName, displayMember: "alarmMappingId", valueMember: "alarmMappingId", width: 200, height: 20, theme: theme });
	$("#alarmMappingNameH").jqxComboBox({ source: dataAdapteralarmMappingName, displayMember: "alarmMappingName", valueMember: "alarmMappingName", width: 450, height: 20, theme: theme });
	$('#alarmMappingNameH').val($("#alarm").val());
	$("#alarmTypeH").change(function(){
		if ($("#alarmTypeH").val() != null && $("#alarmTypeH").val() != 'ALL') {
			var urlalarmMappingName = "${pageContext.request.contextPath}/ajax/getAlarmMappingName.htm?vendor=&alarmType="+$("#alarmTypeH").val()+"&network="+$("#warningTpe").val();
			// prepare the data
			var sourcealarmMappingName =
			{
			    datatype: "json",
			    datafields: [
			        { name: 'alarmMappingId'},
			        { name: 'alarmMappingName'}
			    ],
			    url: urlalarmMappingName,
			    async: false
			};
			var dataAdapteralarmMappingName = new $.jqx.dataAdapter(sourcealarmMappingName);
			// Create a jqxComboBox
			$("#alarmMappingIdH").jqxComboBox({ source: dataAdapteralarmMappingName, displayMember: "alarmMappingId", valueMember: "alarmMappingId", width: 200, height: 20, theme: theme });
			$("#alarmMappingNameH").jqxComboBox({ source: dataAdapteralarmMappingName, displayMember: "alarmMappingName", valueMember: "alarmMappingName", width: 200, height: 20, theme: theme });
		}
	});
	
	
  //input causeby
    $("#causebyH").jqxInput({placeHolder: "Enter causeby", height: 30, width: '100%', minLength: 0, maxLength: 80, theme: theme});
  	//input actionProcess
    $("#actionProcessH").jqxInput({placeHolder: "Enter action process", height: 30, width: '100%', minLength: 0, maxLength: 150, theme: theme});
  	//input description
    $("#descriptionH").jqxInput({placeHolder: "Enter description", height: 30, width: '100%', minLength: 0, maxLength: 150, theme: theme});
 	
  	$("#btSaveAction").click(function (event) {
       	$("#jqxwindow").jqxWindow('open');
        $("#causebyH").val($("#causeby").val());
        $("#actionProcessH").val($("#actionProcess").val());
        $("#descriptionH").val($("#description").val());
    });

    $("#btCancel").click(function (event) {
        $("#jqxwindow").jqxWindow('close');
    });


    $("#btSave").click(function (event) {
    	var handling = {};
    	handling.vendor    = $("#vendor").val();
    	var alarmId = "<c:out value='${alarmLog.alarmId}'/>";
    	if (alarmId!='')
    		{
    			handling.alarmId = parseInt(alarmId);
    		}
    	
    	handling.alarmMappingId    = $("#alarmMappingIdH").val();
    	handling.alarmMappingName    = $("#alarmMappingNameH").val();
    	handling.alarmName = $("#alarm").val();
    	handling.alarmInfo = $("#alarmInfo").val();
    	handling.causeby = $("#causebyH").val();
    	handling.actionProcess = $("#actionProcessH").val();
    	handling.description = $("#descriptionH").val();
    	handling.alarmType =$("#alarmTypeH").val();
    	 	
    	$.ajax({
    	    type: "POST",
    	    url: "${pageContext.request.contextPath}/alarmLog/insertHanding.htm",
    	    data: JSON.stringify(handling),
    	    dataType: 'json',
    	    contentType: 'application/json',
    	    mimeType: 'application/json',
    	    beforeSend: function(){},
    	    complete: function(){},
    	     
    	    success: function(data){
    	    	$("#jqxwindow").jqxWindow('close');
    	    }
    	});
       
    });
    
    $("#btsaveAndAdd").click(function (event) {
        	if ($("#alarm").val()==''||$("#operator").val()==''||$("#sdate").val()==''||$("#system").val()=='')
       		{
        		if ($("#sdate").val()=='')
        			{
        				$("#errorsdate").text("*");
        			}
        		if ($("#operator").val()=='')
    			{
    				$("#errorOperation").text("*");
    			}
        		if ($("#alarm").val()=='')
    			{
    				$("#erroralarm").text("*");
    			}
        		if ($("#system").val()=='')
    			{
    				$("#errorsystem").text("*");
    			}
       			
       		}
        	else
        	{
    	    	var alarmWork = {};
    	 		
    			alarmWork.warningType  = "<c:out value='${warningTp}'/>";
    	    	//alarmWork.vendor  = $("#vendor").val();
    	    	alarmWork.alarm = $("#alarm").val();
    	    	alarmWork.operator = $("#operator").val();
    	    	alarmWork.system = $("#system").val();
    	    	alarmWork.countSite = $("#countSite").val();
    	    	alarmWork.siteList = $("#siteList").val();
    	    	alarmWork.siteQuantity = $("#siteQuantity").val();
    	    	
    	    	if ($("#sdate").val()!='')
   	    		{
   	    			alarmWork.stimeStr = $("#sdate").val()+":00";
   	    		}
    	    	if ($("#edate").val()!='')
	    		{
    	    		alarmWork.etimeStr = $("#edate").val()+":00";
	    		}
    	    	if ($("#mdSdate").val()!='')
	    		{
    	    		alarmWork.mdSdateStr = $("#mdSdate").val()+":00";
	    		}
    	    	if ($("#contactTime").val()!='')
	    		{
    	    		alarmWork.contactDateStr=$("#contactTime").val()+":00";
	    		}
    	    	alarmWork.teamProcess = $("#teamProcess").val();
    	    	alarmWork.causebySystem = $("#causebySystem").val();
    	    	alarmWork.userProcess = $("#userProcess").val();
    	    	alarmWork.dept=$("#dept").val();
    	    	alarmWork.alarmInfo = $("#alarmInfo").val();
    	    	alarmWork.causeby = $("#causeby").val();
    	    	alarmWork.actionProcess = $("#actionProcess").val();
    	    	alarmWork.alarmType =$("#alarmType").val();
    	    	alarmWork.reportProcess =$("#reportProcess").val();
    	    	alarmWork.groups=$("#groups").val();
    	    
    	    	alarmWork.leaseLine=$("#leaseLine").val();	
    	    	alarmWork.isSendLeaseLine=$("#isSendLeaseLine").val();	
    	    	$.ajax({
    	    	    type: "POST",
    	    	    url: "${pageContext.request.contextPath}/alarmLog/insertAlarmWork.htm",
    	    	    data: JSON.stringify(alarmWork),
    	    	    dataType: 'json',
    	    	    contentType: 'application/json',
    	    	    mimeType: 'application/json',
    	    	    beforeSend: function(){
    	    	    	$("#btsaveAndAdd").prop('disabled', true);
    	    	    },
    	    	    complete: function(){
    	    	    	$("#btsaveAndAdd").prop('disabled', false);
    	    	    },
    	    	    success: function(data){
    	    	    	if (data=="1")
    	    	    		{
    	    	    				//alert("");
    	    	    				$("#mesage").text("Thêm mới thành công");
    			        	    	$("#alarm").val("");
    			    				$("#operator").val("");
    			    				$("#system").val("");
    			    				$("#countSite").val("");
    			    				$("#edate").val("");
    			    				$("#teamProcess").val("");
    			    				$("#alarmType").val("");
    			    				 $("#alarmInfo").val("");
    			    				 $("#causeby").val("");
    			    				 $("#actionProcess").val("");
    			    				 $("#userProcess").val("");
    			    				 $("#cbdept").jqxDropDownList('clearSelection'); 
    			    				 $("#cbdept").jqxDropDownList('uncheckAll'); 
    			    				 $("#causebySystem").jqxDropDownList('clearSelection'); 
    			    				 $("#dept").val("");
    			    				 $("#reportProcess").val("");
    			    				 $("#errorOperation").text("");
   			    					$("#errorsystem").text("");
   			    					$("#errorsdate").text("");
   			    					$("#erroredate").text("");
   			    					$("#erroralarm").text("");
   			    					$("#erroruserProcess").text("");
   			    					$("#sdate").val(dateToYMDHMS(new Date()));
   			    					$("#siteList").val("");
    			    				$("#siteQuantity").val("");
    			    				$("#mdSdate").val("");
    			    				$("#groups").val("");
    			    				 $("#contactTime").val("");
    			    			
    			    				 $("#leaseLine").val("");
    	    	    		}
    	    	    	else
    	   	    		{
    	   	    			alert("Định dạng ngày tháng không đúng hoặc chưa nhập các thông tin bắt buộc");
    	    	    		//$("#mesage").text("Định dạng ngày tháng không đúng hoặc chưa nhập các thông tin bắt buộc");
    	   	    		}
    	    	    	
    	    	    }
    	    	});
        	}
           
        });
}); 
</script>
<script type="text/javascript">
function dateToYMDHMS(date) {
	//alert(date);
    var d = date.getDate();
    var m = date.getMonth()+1;
    var y = date.getFullYear();
    var h = date.getHours();
    var mi = date.getMinutes();
    var s = date.getSeconds();
    //alert(d+'-'+m+'-'+y);
    return '' + (d <= 9 ? '0' + d : d) + '/' + (m<=9 ? '0' + m : m) + '/' + y+ ' ' + (h<=9 ? '0' + h : h)+ ':' + (mi<=9 ? '0' + mi : mi)+ ':' + (s<=9 ? '0' + s : s) ;
}
var theme =  getTheme(); 
	function focusIt()
	{
	  var mytext = document.getElementById("alarm");
	  mytext.focus();
	}
	onload = focusIt;
	 // prepare the data
//combobox dept
	var urldept = "${pageContext.request.contextPath}/ajax/getDeptListAlarm.htm";
    var sourcedept =

    {

       datatype: "json",

       url: urldept,

        datafields: [
                     { name: 'deptCode'},
                     { name: 'deptName'},
                     { name: 'abbreviated'}, 
                 ],

        async: false

   };

    var dataAdapterdept = new $.jqx.dataAdapter(sourcedept);
    $("#cbdept").jqxDropDownList({source: dataAdapterdept, displayMember: "deptName", valueMember: "deptName",checkboxes: true, width: 250, height: 20, theme: theme,enableHover: true });           

    var dept = '<c:out value="${alAlarmWorks.dept}"/>';
   // alert(dept);
	if(dept=="")
		$('#cbdept').val('Choose');
	else
	{
		var deptVar = dept.split(",");
		if (deptVar != null) {
			for (var i=0; i<deptVar.length; i++) {
				$("#cbdept").jqxDropDownList('checkItem', deptVar[i] ); 
			}
		}
	}
   
   // Create a jqxMultile Input
	var renderer = function (itemValue, inputValue) {
        var terms = inputValue.split(/,\s*/);
        var value = inputValue;
     
         if (inputValue.indexOf(itemValue) < 0)
	    	{
      
        	 // remove the current input
             terms.pop();
             // add the selected item
	    	 terms.push(itemValue);
	         // add placeholder to get the comma-and-space at the end
	        // terms.push("");
	         value = terms.join(",");
	    	}
       
        return value;
    };
   //Input team
	 var teamList=[];
	$.getJSON("${pageContext.request.contextPath}/ajax/getTeamListAlarm.htm",{dept:dept}, function(j){
		   teamList =j;
	   });
    $("#teamProcess").jqxInput({ height: 20, width: 200, theme: theme,
        source: function (query, response) {
            var item = query.split(/,\s*/).pop();
            // update the search query.
            $("#teamProcess").jqxInput({ query: item });
            response(teamList);
        },
        renderer: renderer
    });
    var teamProcess =  "<c:out value='${alAlarmWorks.teamProcess}'/>";
   // alert(bscid);
    if(teamProcess!="")
		$('#teamProcess').val(teamProcess);
    
  //Input team
	 var groupList=[];
	$.getJSON("${pageContext.request.contextPath}/ajax/getGroupListAlarm.htm",{dept:dept,team:teamProcess}, function(j){
		groupList =j;
	   });
   $("#groups").jqxInput({ height: 20, width: 200, theme: theme,
       source: function (query, response) {
           var item = query.split(/,\s*/).pop();
           // update the search query.
           $("#groups").jqxInput({ query: item });
           response(groupList);
       },
       renderer: renderer
   });
  /*  var groups =  "<c:out value='${alAlarmWorks.groups}'/>";
  // alert(bscid);
   if(groups!="")
		$('#groups').val(groups); */
   
  //Input user
	 var userList=[];
	$.getJSON("${pageContext.request.contextPath}/ajax/getUserByDeparmentTeam.htm",{dept:dept,team:teamProcess}, function(k){
		userList =k;
	   });
   $("#userProcess").jqxInput({ height: 20, width: "100%", theme: theme,
       source: function (query, response) {
           var item = query.split(/,\s*/).pop();
           // update the search query.
           $("#userProcess").jqxInput({ query: item });
           response(userList);
       },
       renderer: renderer
   });
  /*  var userProcess =  "<c:out value='${alAlarmWorks.userProcess}'/>";
  // alert(bscid);
   if(userProcess!="")
		$('#userProcess').val(userProcess); */
   
   $("#cbdept").on('checkChange', function (event) {
       if (event.args) {
           var item = event.args.item;
           if (item) {
               var items = $("#cbdept").jqxDropDownList('getCheckedItems');
               var checkedItems = "";
               var con="";
               $.each(items, function (index) {
                   checkedItems += con+this.value ;
                   con= ",";                          
               });
               $("#dept").val(checkedItems);
            var teamProcess = $("#teamProcess").val();
       	   	var dept = $("#dept").val();
	       	 if (teamProcess==null||teamProcess=='null')
	 		  {
	 			teamProcess='';
	 		  }
	 	 	if (dept==null||dept=='null')
			  {
	 			dept='';
			  }
       		var actionProcess = 'Báo đài ' ;
       		if (teamProcess!='')
   			{
   				actionProcess = actionProcess+ teamProcess ;
   			}
   			else
   			{
   				actionProcess = actionProcess+ dept ;
   			}
       		$("#actionProcess").val(actionProcess);	
       		
       		$.getJSON("${pageContext.request.contextPath}/ajax/getTeamListAlarm.htm",{dept:dept}, function(j){
       			   teamList =j;
       			   $("#teamProcess").jqxInput({source: function (query, response) {
       		           var item = query.split(/,\s*/).pop();
       		           // update the search query.
       		           $("#teamProcess").jqxInput({ query: item });
       		           response(teamList);
       		       },
       		       renderer: renderer
       			    });
       		   });	
       		
       		$.getJSON("${pageContext.request.contextPath}/ajax/getUserByDeparmentTeam.htm",{dept:dept,team:teamProcess}, function(k){
       			userList =k;
       			$("#userProcess").jqxInput({source: function (query, response) {
       		           var item = query.split(/,\s*/).pop();
       		           // update the search query.
       		           $("#userProcess").jqxInput({ query: item });
       		           response(userList);
       		       },
       		       renderer: renderer
       		    
       		   });
       		});	
       	 $.getJSON("${pageContext.request.contextPath}/ajax/getGroupListAlarm.htm",{dept:dept,team:teamProcess}, function(l){
		   		groupList =l;
			   $("#groups").jqxInput({source: function (query, response) {
		           var item = query.split(/,\s*/).pop();
		           // update the search query.
		           $("#groups").jqxInput({ query: item });
		           response(groupList);
		       },
		       renderer: renderer
			    });
		   });	
           }
       }
   });
   
   $("#teamProcess").change(function(){
	   var teamProcess = $("#teamProcess").val();
	 
  	   	var dept = $("#dept").val();
  	  if (teamProcess==null||teamProcess=='null')
  		  {
  			teamProcess='';
  		  }
  	 	if (dept==null||dept=='null')
		  {
  			dept='';
		  }
  	 	var actionProcess = 'Báo đài ' ;
   		if (teamProcess!='')
			{
				actionProcess = actionProcess+ teamProcess ;
			}
			else
			{
				actionProcess = actionProcess+ dept ;
			}
   		
   		$("#actionProcess").val(actionProcess);	  
	   $.getJSON("${pageContext.request.contextPath}/ajax/getUserByDeparmentTeam.htm",{dept:dept,team:teamProcess}, function(k){
  			userList =k;
  			$("#userProcess").jqxInput({source: function (query, response) {
  		           var item = query.split(/,\s*/).pop();
  		           // update the search query.
  		           $("#userProcess").jqxInput({ query: item });
  		           response(userList);
  		       },
  		       renderer: renderer
  		    
  		   });
  		});	
	   
	   $.getJSON("${pageContext.request.contextPath}/ajax/getGroupListAlarm.htm",{dept:dept,team:teamProcess}, function(j){
		   		groupList =j;
			   $("#groups").jqxInput({source: function (query, response) {
		           var item = query.split(/,\s*/).pop();
		           // update the search query.
		           $("#groups").jqxInput({ query: item });
		           response(groupList);
		       },
		       renderer: renderer
			    });
		   });	
   });	
	
	$("#operator").on('change', function (event) {
	   			var systemV = $("#system").val();	  
	               $.getJSON("${pageContext.request.contextPath}/ajax/getSystemByOperator.htm",{operator: $("#operator").val(),warningTp:$("#warningTpe").val()}, function(j){

	       			var systemList = new Array();
	       			for (var i = 0; i < j.length; i++) {
	       				systemList.push(j[i] ) ;
	       			}
	       			
	       			$("#system").jqxInput({source: function (query, response) {
	   		           var item = query.split(/,\s*/).pop();
	   		           // update the search query.
	   		           $("#system").jqxInput({ query: item });
	   		           response(systemList);
		   		       },
		   		       renderer: renderer
	   		    
	   		   		});
	       			$("#system").val(systemV);	
	       			//$("#systemChosse").jqxDropDownList({ source: systemList, width: 160,height: 20,itemHeight: 30,theme: theme,enableHover: true  }); 
	       		});
	       		 
	       		/* $("#countSite").val(0);
	       		$("#system").val('');	 */	
	   });
	$("#system").change(function(){
		
		var system= $("#system").val();
		var siteList = $("#siteList").val();
		var siteQuantity =$("#siteQuantity").val();
		//alert(system);
		if (system!=null && system!='')
			{
				var count=0;
				var systemVar = system.split(",");
				count=systemVar.length;
				//alert(systemVar[systemVar.length-2]+'--'+systemVar[systemVar.length-1]);
				if (systemVar[systemVar.length-1]==''||systemVar[systemVar.length-1]=='undefined')
					{
						count = count-1;
					}
				$("#countSite").val(count);
				//site list
				var con="";
				if (siteList!=null && siteList!='')
				{
					con=",";
				}
				
				if (siteQuantity==null||siteQuantity=='')
				{
					siteQuantity=0;
				}
				for ( var i = 0; i < systemVar.length; i++) {
					var cell = systemVar[i];
					var site=cell;
					if (cell.length>6)
						{
							site = cell.substr(0,6);
						}
					if (siteList.indexOf(site)<0 )
					{
						siteList=siteList+con+site;
						con=",";
						siteQuantity=parseInt(siteQuantity)+1;
					}	
				}
			}
		else
		{
			$("#countSite").val(0);
		}
		$("#siteList").val(siteList);
		$("#siteQuantity").val(siteQuantity);
	});
	
	$("#siteList").change(function(){
		var siteList= $("#siteList").val();
		
		if (siteList!=null && siteList!='')
			{
				var count=0;
				var siteListVar = siteList.split(",");
				count=siteListVar.length;
				if (siteListVar[siteListVar.length-1]==''||siteListVar[siteListVar.length-1]=='undefined')
					{
						count = count-1;
					}
				$("#siteQuantity").val(count);
			}
		else
		{
			$("#siteQuantity").val(0);
		}
	});
	
	$("#totalTime").focus(function(){
		var sdate= $("#sdate").val();
		var edate= $("#edate").val(); 
		var totalTime=0;
		if (sdate!=null&&sdate!=''&&!sdate.match(/^[0-3][0-9]\:[0-1][0-9]\:[0-2][0-9][0-9][0-9]\:[0-2][0-9]\:[0-6][0-9]\:[0-6][0-9]$/))
			{
				var stime = new Date(Date.parse(sdate));
				var etime= new Date();
				if (edate!=null&&edate!=''&&!edate.match(/^[0-3][0-9]\:[0-1][0-9]\:[0-2][0-9][0-9][0-9]\:[0-2][0-9]\:[0-6][0-9]\:[0-6][0-9]$/))
					{
					etime = new Date(Date.parse(edate));
					
					}
				totalTime =(etime.getTime()- stime.getTime())/60000;	
			}
		else
			{
				totalTime =0;
			}
	
		$("#totalTime").val(Math.round(totalTime));
	});

	
	$("#bscidTK").on('change', function (event) {
			var cellidTK = $("#cellidTK").val();	  
           $.getJSON("${pageContext.request.contextPath}/ajax/getSystemByOperator.htm",{operator: bscidTK,warningTp:$("#warningTpe").val()}, function(j){

   			var systemList = new Array();
   			for (var i = 0; i < j.length; i++) {
   				systemList.push(j[i] ) ;
   			}
   			
   			$("#cellidTK").jqxInput({source: function (query, response) {
		           var item = query.split(/,\s*/).pop();
		           // update the search query.
		           $("#cellidTK").jqxInput({ query: item });
		           response(systemList);
   		       },
   		       renderer: renderer
		    
		   		});
   			$("#cellidTK").val(cellidTK);	
   		});
           
       
});

 document.getElementById("reset").onclick = function(){
	 document.getElementById("alarm").value = "";
     document.getElementById("causeby").value = "";
     document.getElementById("alarmInfo").value = "";
     document.getElementById("actionProcess").value = "";
     document.getElementById("reportProcess").value = "";
     document.getElementById("countSite").value = "";
     document.getElementById("system").value = "";
     $("#operator").val("");
	$("#teamProcess").val("");
     $("#errorOperation").text("");
		$("#errorsystem").text("");
		$("#errorsdate").text("");
		$("#erroralarm").text("");
		$("#erroruserProcess").text("");
		$("#erroredate").text("");
		$("#sdate").val(dateToYMDHMS(new Date()));
		$("#edate").val("");
		$("#alarmType").val("");
		$("#userProcess").val("");
		 $("#cbdept").jqxDropDownList('clearSelection'); 
		 $("#cbdept").jqxDropDownList('uncheckAll'); 
		 $("#siteList").val("");
			$("#siteQuantity").val("");
		 $("#causebySystem").jqxDropDownList('clearSelection'); 
		 $("#dept").val("");
		 $("#groups").val("");
		 $("#mdSdate").val("");
		 $("#contactTime").val("");
		 $("#leaseLine").val("");
		
    /*  document.getElementById("operator").value = document.getElementById("operator").options[0].value;
     document.getElementById("systemChosse").value = document.getElementById("systemChosse").options[0].value;
     document.getElementById("teamProcess").value = document.getElementById("teamProcess").options[0].value;
     document.getElementById("causebySystem").value = document.getElementById("causebySystem").options[0].value; */
 }
/* Grid cac alarm log cung loi*/
$(document).ready(function () {
var theme = getTheme();
    
	//Get URL for get data
    var url = "<c:out value='${url}'/>";
    url = url.replace(/amp;/g,'');
    
	// prepare the data source
    ${datafields}
   
    var source =
    {
    	datatype: "json",
        datafields: datafields,
        url: url,
        pagenum: 0,
        pagesize: 100
	   
    };
    
    var dataAdapter = new $.jqx.dataAdapter(source);
    var colorrowrenderer = function (row, columnfield, value, defaulthtml, columnproperties) {
    	var valueKey = $('#jqxgrid').jqxGrid('getrowdata', row).shiftId;
    	 if( valueKey!=null){ 
    		  $("#row"+row+"jqxgrid").addClass("shiftId");
    		}; 
      	};	  
 	// renderer for grid cells. 
   var numberrenderer = function (row, column, value) {
        return '<div style="text-align: center; margin-top: 5px;">' + (1 + value) + '</div>';
    };
 
    //call funtion add column	
   	${column}
 
    // filter
    $("#jqxgrid").jqxGrid(
    {
    	width: '100%',
       	source: dataAdapter,
        theme: theme,
        showfilterrow: true,
        filterable: true,
        sortable: true,
        pageable: true,
        autoheight: true,
        altrows: true,
        columnsresize: true,
        columnsreorder: true,
        scrollmode: 'logical',
        selectionmode: 'singlerow',
        pagesizeoptions: ['50', '100', '200', '300', '400', '500'],
        columns: column
    });
  //dropdownlist an hien cot
    //call funtion add listSource	
   	${listSource}
   	
    $("#jqxlistbox").jqxDropDownList({ checkboxes: true, source: listSource, width: 15, height: 15, theme: theme, dropDownHorizontalAlignment: 'right',dropDownWidth: 120 });
   // $("#jqxlistbox").jqxDropDownList('checkIndex', 0);
    // subscribe to the checkChange event.
    $("#jqxlistbox").on('checkChange', function (event) {
    	if (event.args) {
            var item = event.args.item;
            if (item) {
               if (item.checked==true)
                {
                  
                	$("#jqxgrid").jqxGrid('showcolumn', item.value);                      
            	}
               else
                {
         	   		$("#jqxgrid").jqxGrid('hidecolumn', item.value);
                }
        	}
    	}
    });
    $("#filterTK").click(function(){
		var warningTp=$("#warningTpe").val();
		var bsc = $("#bscidTK").val();
		var status ='';
		if ($("#status").val()!='')
			status = $("#status").jqxComboBox('getSelectedItem').value;
		source.url = "data.htm?warningTp="+warningTp+"&day="+$("#day").val()+"&alarmTypeTK="+$("#alarmTypeTK").val()+"&status="+status+"&operator="+bsc+"&system="+$("#cellidTK").val();
        // passing "cells" to the 'updatebounddata' method will refresh only the cells values when the new rows count is equal to the previous rows count.
        $("#jqxgrid").jqxGrid('updatebounddata');
        $('#jqxgrid').jqxGrid('refresh');
		/* $.getJSON("${pageContext.request.contextPath}/ajax/getAlarmLogAtShift.htm",{netWork:warningTp,day: $("#day").val(),alarmType:$("#alarmType").val(),statusKT:$("#status").val(),bscidTK:bsc,cellidTK:$("#cellidTK").val()}, function(j){
				
			}); */
	});
});
    
   
    
$('#jqxgrid').on('rowclick', function (event) 
		{
		    var rowindex = args.rowindex;
		    $("#row"+rowindex+"jqxgrid").addClass("shiftId");
		    //alert(rowindex);
		    var row = $("#jqxgrid").jqxGrid('getrowdata', rowindex);
		    var shiftID = row.shiftId;
		  //  alert(shiftID);
		    if (shiftID!=null && shiftID!='null' )
	    	{
	    		alert("Cảnh báo đã được đưa vào ca trực");
	    	}
		    else
	    	{
		    	$('#causeby').val(row.causeby);
			    $('#actionProcess').val(row.actionProcess);
			    $("#alarm").val(row.alarmMappingName);
			    $("#alarmInfo").val(row.alarmInfo);
			    $("#teamProcess").val(row.groups);
			    $('#causebySystem').val(row.causebySys);
			    $('#alarmType').val(row.alarmType);
			    if (row.dept!=null&& row.dept!='')
		    	{
		     		$("#cbdept").jqxDropDownList('checkItem', row.dept); 
		    	}
			    if (row.sdate!=null && row.sdate!='')
		    	{
		    		$("#sdate").val(dateToYMDHMS(new Date(row.sdate)));
		    	}
			    if (row.edate!=null && row.edate!='')
		    	{
		    		$("#edate").val(dateToYMDHMS(new Date(row.edate)));
		    	}
			    
			   //$('#causebySystem').val("Thiết bị");
			    var warningTp = $("#warningTp").val();
				if (row.ne!='' &&row.ne!=null)
				{
					var bscF =  $("#operator").val();
					if (bscF!=null&&bscF!='')
						{
							if (bscF.indexOf(row.ne)<0 )
								{bscF=bscF+","+row.ne;}	
						}
					else
						{
							bscF=row.ne;
						}
					$("#operator").val(bscF);
						 $.getJSON("${pageContext.request.contextPath}/ajax/getSystemByOperator.htm",{operator: $("#operator").val(),warningTp: $("#warningTpe").val()}, function(j){
								
							 var systemList = new Array();
				       			for (var i = 0; i < j.length; i++) {
				       				systemList.push(j[i] ) ;
				       			}
				       			
				       			$("#system").jqxInput({source: function (query, response) {
				   		           var item = query.split(/,\s*/).pop();
				   		           // update the search query.
				   		           $("#system").jqxInput({ query: item });
				   		           response(systemList);
					   		       },
					   		       renderer: renderer
				   		    
				   		   		});
				       			
			       			$("#siteList").jqxInput({source: function (query, response) {
				   		           var item = query.split(/,\s*/).pop();
				   		           // update the search query.
				   		           $("#siteList").jqxInput({ query: item });
				   		           response(systemList);
					   		       },
					   		       renderer: renderer
				   		    
				   		   		});
							});
					  
				}
				//system,countsite
				if (row.system!='' &&row.system!=null)
				{ 	
					var value = row.system;
					var system =$("#system").val();
					var siteList =$("#siteList").val();
					var siteQuantity =$("#siteQuantity").val();
					var site=value;
					var con="";
					if (siteList!=null && siteList!='')
					{
						con=",";
					}
					if (siteQuantity==null||siteQuantity=='')
					{
						siteQuantity=0;
					}
					if (value.length>6)
					{
						site = value.substr(0,6);
					}
					if (siteList.indexOf(site)<0 )
					{
						siteList=siteList+con+site;
						con=",";
						siteQuantity=parseInt(siteQuantity)+1;
					}	
					$("#siteList").val(siteList);
					$("#siteQuantity").val(siteQuantity);
					//system
					var systemList = system.split(",");
					var kt = false;
					if (system != null && system != ''&& system != 'null') {
							/* for (var i=0; i<systemList.length; i++) {
								if (value ==systemList[i] )
									kt= true;
							} */
							//alert(system.indexOf(value));
							if (system.indexOf(value)<0)
							{
								
								system+=','+value;
								$("#countSite").val(systemList.length+1);
								//to mau va  luu vao ca truc
								//${highlight}
								
								//$("#row"+rowindex+"jqxgrid").addClass("shiftId");
								 $.getJSON("${pageContext.request.contextPath}/ajax/updateShiftID_AlLog.htm",{id:row.id,shiftId:$("#idShift").val(),netWork:$("#warningTpe").val()}, function(j){
									
								});
							}
							/* else
								{
								
								$("#countSite").val(systemList.length);
								} */
						}
						else
							{
								system =value;
								if (value!=null&& value !='')
									{
									
									$("#countSite").val(1);}
								else
									{
									$("#countSite").val(0);}
								//to mau va  luu vao ca truc
								//${highlight}
								//$("#row"+rowindex+"jqxgrid").addClass("shiftId");
								$.getJSON("${pageContext.request.contextPath}/ajax/updateShiftID_AlLog.htm",{id:row.id,shiftId:$("#idShift").val(),netWork:$("#warningTpe").val()}, function(j){
								
								});
							} 
						$("#system").val(system);	
				}
	    	
	    	}
		   	
});

$("#baterryDuar").focus(function(){
	var sdate= $("#sdate").val();
	var edate= $("#mdSdate").val(); 
	var baterryDuar=0;
	if (sdate!=null&&sdate!=''&&!sdate.match(/^[0-3][0-9]\:[0-1][0-9]\:[0-2][0-9][0-9][0-9]\:[0-2][0-9]\:[0-6][0-9]\:[0-6][0-9]$/))
		{
			var stime = new Date(Date.parse(sdate));
			var etime= new Date();
			if (edate!=null&&edate!=''&&!edate.match(/^[0-3][0-9]\:[0-1][0-9]\:[0-2][0-9][0-9][0-9]\:[0-2][0-9]\:[0-6][0-9]\:[0-6][0-9]$/))
				{
				etime = new Date(Date.parse(edate));
				
				}
			baterryDuar =(stime.getTime()- etime.getTime())/60000;	
			var baterryOld = $("#baterryOfSite").text();	
 			if (baterryOld !=''  && baterryDuar < baterryOld){
 				$('#accuLeastCK').prop( "checked", true );
 				$('#accuLeast').val("Y");
 			}
 			else {
 				$('#accuLeastCK').prop( "checked", false );
				$('#accuLeast').val("N");}
		}
	else
		{
		baterryDuar =0;
		}

	$("#baterryDuar").val(Math.round(baterryDuar));
});


$("#baterryDuar").on('change', function () { 
	var baterryDuar = $("#baterryDuar").val();	
	var baterryOld = $("#baterryOfSite").text();	
		if (baterryOld !=''  && baterryDuar < baterryOld){
			$('#accuLeastCK').prop( "checked", true );
			$('#accuLeast').val("Y");
		}
		else {
			$('#accuLeastCK').prop( "checked", false );
			$('#accuLeast').val("N");}
});


	
</script>
</body>
</html>