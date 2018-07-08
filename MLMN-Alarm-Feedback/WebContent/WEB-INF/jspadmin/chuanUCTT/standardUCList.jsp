<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<title>${titleSystem}</title>
<content tag="heading">${titleSystem}</content>

<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left">
				<form:form commandName="filter" method="post" action="danh-sach.htm">
				<table >
				    <tr>
				   		<td class="wid5"><fmt:message key="chuanUCTT.region"/>  </td>
						<td class="wid10">
		    				<div id='region'></div>
						</td>
			  
				    	<td class="wid7"><fmt:message key="chuanUCTT.dept"/> </td>
				    	<td class="wid20">
				    		<div id='dept'></div>
				    	</td>
				    	
				    	<td class="wid7"><fmt:message key="chuanUCTT.team"/> </td>
				    	<td class="wid20">
				    		<input type="text" name="team" id="team" class="wid90"/>
				    	</td>
				    	<td>
				    		<input type="submit" class="button" value="<fmt:message key="global.form.timkiem"/>" />
				    	</td>
				    </tr>
			    </table>	
				</form:form>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="right">
				 <a href="form.htm" title="<fmt:message key="global.form.themmoi"/>"><fmt:message key="global.form.themmoi"/> </a>
		 		<a href="upload.htm"><fmt:message key="global.button.import"/></a>&nbsp;
		 	</td> 
		 </tr>
		 <tr>
		 	<td colspan="2">
		 		<div style="width: 100%; height: 100%; overflow:auto;">
					<display:table name="${standardList}" class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
						<display:column class="centerColumnMana" title="STT" > <c:out value="${item_rowNum}"/> </display:column>
						<display:column property="region" titleKey="chuanUCTT.region" sortable="true" sortName="REGION"/>  
						<display:column property="dept" titleKey="chuanUCTT.dept" sortable="true" sortName="DEPT"/>  
						<display:column property="team" titleKey="chuanUCTT.team" sortable="true" sortName="TEAM"/>  
						<display:column class="centerColumnMana"  property="numberSite2g" titleKey="chuanUCTT.numberSite2g" sortable="true" sortName="NUMBER_SITE_2G"/>  
						<display:column class="centerColumnMana"  property="numberSite3g" titleKey="chuanUCTT.numberSite3g" sortable="true" sortName="NUMBER_SITE_3G"/>  
						<display:column class="centerColumnMana" property="numberAlarmService" titleKey="chuanUCTT.numberAlarmService" sortable="true" sortName="NUMBER_ALARM_SERVICE"/>  
						<display:column class="centerColumnMana" property="uctt" titleKey="chuanUCTT.uctt" sortable="true" sortName="UCTT"/>
						<display:column property="planResult" titleKey="chuanUCTT.planResult" sortable="true" sortName="PLAN_RESULT"/>  
						<display:column property="actualResult" titleKey="chuanUCTT.actualResult" sortable="true" sortName="ACTUAL_RESULT"/>  
						<display:column media="html" titleKey="global.management" class="centerColumnMana">
							<a href="form.htm?id=${item.id}" title="<fmt:message key="global.form.sua"/>"><fmt:message key="global.form.sua"/></a>&nbsp;
							<a href="delete.htm?id=${item.id}" onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>?')" title="<fmt:message key="global.form.xoa"/>"><fmt:message key="global.form.xoa"/></a>&nbsp;
						</display:column>
						<display:setProperty name="export.csv.filename" value="${exportFileName}.csv"/>
						<display:setProperty name="export.excel.filename" value="${exportFileName}.xls"/>
						<display:setProperty name="export.xml.filename" value="${exportFileName}.xml"/>
					</display:table>
					</div> 
		 	</td>
		 </tr>
</table>
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
// Create a jqxComboBox
$("#region").jqxComboBox({ source: dataAdapterregion, displayMember: "value", valueMember: "name", width: 150,height: 17,itemHeight: 25,theme: theme, autoOpen: true, autoDropDownHeight: true });
var regionCBB =  "<c:out value='${region}'/>";
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
$("#dept").jqxComboBox({source: dataAdapterdept, displayMember: "deptName", valueMember: "deptName", width: 250,height: 17,itemHeight: 25,theme: theme, autoOpen: true });           

var dept = '<c:out value="${dept}"/>';
// alert(dept);
if(dept=="")
	$('#dept').val('');
else
{
  $("#dept").find('input').val(dept);
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
$("#team").jqxInput({ height: 20, width: 200, theme: theme,
   source: function (query, response) {
       var item = query.split(/,\s*/).pop();
       // update the search query.
       $("#team").jqxInput({ query: item });
       response(teamList);
   },
   renderer: renderer
});
var team =  "<c:out value='${team}'/>";
// alert(bscid);
if(team!="")
	$('#team').val(team);

$("#cbdept").on('change', function (event) 
{
    var args = event.args;
    if (args) {
    	var item = args.item;
        var value = item.value;
   		$.getJSON("${pageContext.request.contextPath}/ajax/getTeamListAlarm.htm",{dept:value}, function(j){
   			   teamList =j;
   			   $("#team").jqxInput({source: function (query, response) {
   		           var item = query.split(/,\s*/).pop();
   		           // update the search query.
   		           $("#team").jqxInput({ query: item });
   		           response(teamList);
   		       },
   		       renderer: renderer
   			    });
   		   });	
    }
});

</script>