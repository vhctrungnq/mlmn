<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxwindow.js"></script>

<title>${titleForm}</title>
<content tag="heading">${titleForm}</content> 	   
<form:form commandName="alarmLog" id="myform" method="post" action="form.htm?netWork=${netWork}&function=${function}"> 
	<form:hidden path="id"/>
	<form:hidden path="day"/>
	<table class="simple2"> 
      <tr>
           <td style="width: 100px;"><fmt:message key="alarmLog.bscid"/></td>
			<td style="width: 300px;">
				 <form:hidden path='ne'/><span style="font-weight: bold;">${bscid}</span>
			</td>
			<td style="width: 120px;"><fmt:message key="alarmLog.site"/></td>
			<td style="width: 250px;">
				<form:hidden path='site'/><span style="font-weight: bold;">${alarmLog.site}</span>
			</td>
			<td style="width: 150px;"><fmt:message key="alarmLog.cellid"/></td>
			<td>
				<form:hidden path='cellid'/><span style="font-weight: bold;">${alarmLog.cellid}</span>
			</td>
      </tr> 
		<tr>
           <td><fmt:message key="alarmLog.sdate"/></td>
			<td style="width:150px">
				<form:hidden path='sdate'/><span style="font-weight: bold;">${sdate}</span>
			</td>
			<td><fmt:message key="alarmLog.vendor"/></td>
			<td>
				<form:hidden path='vendor'/><span style="font-weight: bold;">${alarmLog.vendor}</span>
			</td>
			<td><fmt:message key="alarmLog.severity"/></td>
			<td>
				<form:hidden path='severity'/><span style="font-weight: bold;">${alarmLog.severity}</span>
			</td>
			
			
         </tr>
         <tr>
       		<td><fmt:message key="alarmLog.alarmType"/></td>
			<td>
				<form:hidden path='alarmType'/><span style="font-weight: bold;">${alarmLog.alarmType}</span>
			</td>
			<td><fmt:message key="alarmLog.alarmMappingId"/></td>
			<td>
				<form:hidden path="alarmMappingId" /><span style="font-weight: bold;">${alarmLog.alarmMappingId}</span>
			</td>
			<td><fmt:message key="alarmLog.alarmMappingName"/></td>
			<td>
				<form:hidden path="alarmMappingName"/><span style="font-weight: bold;">${alarmLog.alarmMappingName}</span>
			</td>
		</tr>	
      <tr>
      		<td><fmt:message key="alarmLog.alarmName"/></td>
			<td>
				<form:hidden path="alarmName"/><span style="font-weight: bold;">${alarmLog.alarmName}</span>
			</td>
      		<td><fmt:message key="alarmLog.team"/></td>
			<td>
				<form:hidden path="team"/><span style="font-weight: bold;">${alarmLog.team}</span>
			</td>
      		<td><fmt:message key="alarmLog.district"/></td>
			<td>
				<form:hidden path="district"/><span style="font-weight: bold;">${alarmLog.district}</span>
			</td>
			
      </tr>     
      <tr>
      		<%-- <td><fmt:message key="alarmLog.edate"/></td>
			<td > 
				<form:hidden path='sdate'/><span style="font-weight: bold;">${edate}</span>
				<font color="red"><form:errors path="edate"/></font>
				<!-- <div id='edate'></div> -->
			</td> --%>
      		
        	<td><fmt:message key="alarmLog.causebySys"/></td>
			<td colspan="5">
				<div id='causebySys' style = "width: 100%"></div>
				
			</td>
		</tr>
		<tr>
			<td><fmt:message key="alarmLog.causeby"/></td>
			<td colspan="5">
				<form:input type="text" path="causeby"  id ="causeby"/>
				<font color="red">${errorcauseby}<form:errors path="causeby"/></font>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="alarmLog.resultContent"/></td>
			<td colspan="5">
				<form:input type="text" path="resultContent" id ="resultContent" />
				<font color="red">${errorresultContent}<form:errors path="resultContent"/></font>
			</td>
		</tr>
     
      <tr>
           <td></td>
           <td colspan="5">
               	<input type="submit" class="button" id="submit" value="<fmt:message key="global.form.luulai"/>" />
           	  	<input type="button" class="button" id="btCancel" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="detail/${function}.htm?netWork=${netWork}"'>
		 </td>
       </tr>
    </table>

</form:form>
<script type="text/javascript">
var theme =  getTheme();
$('#submit').jqxButton({ theme: theme });
$('#btCancel').jqxButton({ theme: theme });   
$(document).ready(function(){
	// Khai bao sdate, edate
	
	
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
    $("#causebySys").jqxDropDownList({ source: dataAdapterCausebySys, displayMember: "value", valueMember: "name", width: 250,height: 25,theme: theme,autoOpen: true });
    $("#causebySys").jqxDropDownList('insertAt', { label: '', value: ''}, 0 );
    var causebySys =  "<c:out value='${alarmLog.causebySys}'/>";
    if(causebySys!=null&&causebySys!="")
		$('#causebySys').val(causebySys);
 	
  	//input causeby
    $("#causeby").jqxInput({placeHolder: "Enter causeby", height: 25, width: '95%', minLength: 0, maxLength: 200, theme: theme});
  	//input actionProcess
    $("#resultContent").jqxInput({placeHolder: "Enter rresult process", height: 25, width: '100%', minLength: 0, maxLength: 350, theme: theme});
  	//input description
});
</script>