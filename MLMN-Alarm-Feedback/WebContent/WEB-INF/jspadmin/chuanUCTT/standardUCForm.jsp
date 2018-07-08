<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<title><fmt:message key="${titleSystem}"/></title>
<content tag="heading"><fmt:message key="${titleSystem}"/></content>
  
<form:form commandName="standardForm" name="checkform" method="post" action="form.htm">
	<form:hidden path="id"/>
	<table class="simple2">
		<tr>
			<td>
       			<fmt:message key="chuanUCTT.region"/>&nbsp;<font color="red">(*)</font>
	        </td>
	        <td colspan="3">
				<div id='region'></div><form:errors path="region" cssClass="error"/>
	      	</td>
		</tr>
		<tr>
			   <td  style="width:120px;"><fmt:message key="chuanUCTT.dept"/><font color="red">(*)</font></td>
	           <td  style="width:200px;">
	           		<div id='dept'></div>
					<font color="red"><form:errors path="dept"/></font>
	           </td>
	           <td  style="width:120px;"><fmt:message key="chuanUCTT.team"/></td>
           		<td  style="width:200px;">
	          		<div id='team'></div>
					<font color="red"><form:errors path="team"/></font>
           		</td>
		</tr>	
		
		<tr>
		   <td><fmt:message key="chuanUCTT.numberSite2g"/>&nbsp;</td>
			<td>
				<form:input path="numberSite2g" style = "width: 250px;"/> 
				&nbsp;<font color = "red"><form:errors path="numberSite2g"/></font>
			</td>
			<td><fmt:message key="chuanUCTT.numberSite3g"/>&nbsp;</td>
			<td>
				<form:input path="numberSite3g" style = "width: 250px;"/> 
				&nbsp;<font color = "red"><form:errors path="numberSite3g"/></font>
			</td>
		</tr>	
		<tr>
			 <td><fmt:message key="chuanUCTT.numberAlarmService"/>&nbsp;</td>
			<td>
				<form:input path="numberAlarmService" style = "width: 250px;"/> 
				&nbsp;<font color = "red"><form:errors path="numberAlarmService"/></font>
			</td>
			<td><fmt:message key="chuanUCTT.uctt"/>&nbsp;</td>
			<td>
				<form:input path="uctt" style = "width: 250px;"/> 
				&nbsp;<font color = "red"><form:errors path="uctt"/></font>
			</td>
		</tr>	
		
		<tr>
			<td  style="width:120px;"><fmt:message key="chuanUCTT.planResult"/></td>
          	<td  style="width:200px;">
          		</div><form:input path="planResult" style = "width: 250px;"/> 
				<font color="red"><form:errors path="planResult"/></font>
          	</td>
			<td  style="width:120px;"><fmt:message key="chuanUCTT.actualResult"/></td>
          	<td  style="width:200px;">
          		<div id='actualResult'></div>
				<font color="red"><form:errors path="actualResult"/></font>
          	</td>
		</tr>
		<tr>
			<td></td>
			<td colspan="3">
       			 <input type="submit" class="button" name="save" value="<fmt:message key="global.form.luulai"/>">  
       			 <input type="button" class="button" onclick='window.location="danh-sach.htm"' value="<fmt:message key="global.form.huybo"/>" >
			</td>
		</tr>
	</table>
</form:form>
	
<script type="text/javascript">
var theme = getTheme();
//create dropbox region
var urlregion = "${pageContext.request.contextPath}/ajax/getRegionList.htm";
//prepare the data
var sourceregion =
{
datatype: "json",
datafields: [
    { name: 'name' },
    { name: 'value' }
],
url: urlregion,
async: false
};
var dataAdapterregion = new $.jqx.dataAdapter(sourceregion);
//Create a jqxComboBox
$("#region").jqxComboBox({ source: dataAdapterregion, displayMember: "value", valueMember: "name", width: 250,height: 20,itemHeight: 30,theme: theme, autoOpen: true,autoDropDownHeight: true });
var regionCBB =  "<c:out value='${standardForm.region}'/>";
if(regionCBB!=""){
	 $("#region").val(regionCBB);
}

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
$("#dept").jqxComboBox({source: dataAdapterdept, displayMember: "deptCode", valueMember: "deptCode", width: 250,height: 20,itemHeight: 30,theme: theme, autoOpen: true });           

var dept = '<c:out value="${standardForm.dept}"/>';
//alert(dept);
if(dept=="")
	$('#dept').val('');
else
{
$("#dept").find('input').val(dept);
} 


//combobox dept
var urlTeam = "${pageContext.request.contextPath}/ajax/getTeamListAlarmFull.htm?dept="+$('#dept').val();
var sourceTeam =

{
	datatype: "json",
	url: urlTeam,
	datafields: [
	             { name: 'deptCode'},
	             { name: 'deptName'},
	             { name: 'abbreviated'}, 
	         ],
	
	async: false

};

var dataAdapterTeam = new $.jqx.dataAdapter(sourceTeam);
$("#team").jqxComboBox({source: dataAdapterTeam, displayMember: "deptCode", valueMember: "deptCode", width: 250,height: 20,itemHeight: 30,theme: theme, autoOpen: true });           

var team = '<c:out value="${standardForm.team}"/>';
//alert(dept);
if(team=="")
	$('#team').val('');
else
{
	$("#team").find('input').val(team);
} 

$("#dept").on('change', function (event) 
{
  var args = event.args;
  if (args) {
  	var item = args.item;
      var value = item.value;
      var urlTeam = "${pageContext.request.contextPath}/ajax/getTeamListAlarmFull.htm?dept="+value;
      var sourceTeam =
	 {
      	datatype: "json",
      	url: urlTeam,
      	datafields: [
      	             { name: 'deptCode'},
      	             { name: 'deptName'},
      	             { name: 'abbreviated'}, 
      	         ],
      	
      	async: false

      };
		var dataAdapterTeam = new $.jqx.dataAdapter(sourceTeam);
      $("#team").jqxComboBox({source: dataAdapterTeam, displayMember: "deptCode", valueMember: "deptCode", width: 250,height: 20,itemHeight: 30,theme: theme, autoOpen: true , autoDropDownHeight: true});           
 }
});

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
   $("#actualResult").jqxComboBox({ source: dataAdapterstatusUCTT, displayMember: "value", valueMember: "name", width: 150, height: 20, theme: theme,autoDropDownHeight: true  });
   var actualResult =  "<c:out value='${standardForm.actualResult}'/>";
   $('#actualResult').val(actualResult);
</script>