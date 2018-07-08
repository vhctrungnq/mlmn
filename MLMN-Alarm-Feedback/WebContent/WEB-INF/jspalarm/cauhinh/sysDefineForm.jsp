
<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>${titleF}</title>
<content tag="heading">${titleF}</content>

<form:form commandName="define" method="post" action="form.htm?type=${type}"> 
	<form:hidden path="id"/>
	<table class="simple2"> 
      <tr>
           	<td style="width: 100px;"><fmt:message key="define.groupName"/><font color="red">(*)</font></td>
			<td colspan="3">
				<form:input path="groupName"  class = "long" rows="10" maxlength="900"/>
				<font color="red"><form:errors path="groupName"/></font>
			</td>
	</tr> 
   		 <tr>
           	<td><fmt:message key="define.groupUser"/></td>
			<td style="width:150px;">
				<c:choose>
					<c:when test="${type !='SMS'}">
						<div id='cbUserMail'></div>
					</c:when>
					<c:otherwise>
						<div id='cbUserSMS'></div>
					</c:otherwise>
				</c:choose>
			</td>
			<td colspan="2">
				<form:input path="groupUser"  class = "long" rows="10" maxlength="900"/>
			</td>
      </tr> 
       <tr>
           <td><fmt:message key="define.isEnable"/></td>
           <td> 
           		<div id='isEnable' style = "width: 100%"></div>
          	</td>
           <td  style="width:100px;"><fmt:message key="define.ordering"/></td>
           <td> 
           		<form:input type ="text" path="ordering" maxlength="10"  class = "long" rows="3" />
           		<font color="red"><form:errors path="ordering"/></font>
          </td>
       </tr>
	 <tr>
           <td><fmt:message key="define.description"/></td>
           <td colspan="3"> 
           	<form:input path="description"  class = "long" rows="10" maxlength="900"/>
          </td>
       </tr>
       <tr>
           <td></td>
           <td colspan="3">
           		<input type="submit" id='btsubmit' class="button" value="<fmt:message key="global.form.luulai"/>" />
           	  	<input type="button" id='btCancel' class="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm?type=${type}"'>	
           </td>
       </tr>
    </table>
</form:form>
<style>
    .error {
    	color: red;
    }
</style> 

<script type="text/javascript">
var theme =  getTheme(); 
$('#btsubmit').jqxButton({ theme: theme });
$('#btCancel').jqxButton({ theme: theme });

$(document).ready(function(){
	
	//input reportProcess
    $("#description").jqxInput({height: 20, width: '100%', minLength: 0, maxLength: 900, theme: theme});
    $("#groupName").jqxInput({ height: 20, width: '50%', minLength: 1, maxLength: 200, theme: theme});
    $("#groupUser").jqxInput( { height: 20, width: '100%', minLength: 0, maxLength: 500, theme: theme});
    $("#ordering").jqxInput({  height: 20, width: 100, minLength: 0, maxLength: 10, theme: theme});
 	// Create a jqxDropDownList AlarmType
	var urlUserSMS = "${pageContext.request.contextPath}/ajax/getUserSMS.htm?maPhong=";
    // prepare the data
    var sourceUserSMS =
    {
        datatype: "json",
        datafields: [
              { name: 'username' },
              { name: 'phone' }
        ],
        url: urlUserSMS,
        async: false
    };
    var dataAdapterUserSMS = new $.jqx.dataAdapter(sourceUserSMS);
    $("#cbUserSMS").jqxDropDownList({ source: dataAdapterUserSMS, displayMember: "username", valueMember: "phone", width: 150, height: 20,itemHeight: 30, theme: theme,autoOpen: true });
    var alarmType =  "<c:out value='${cbUserSMS}'/>";
    if(alarmType=='')
		$('#cbUserSMS').val('ALL');
	
 // Create a jqxComboBox user mail
    // prepare the data
    var urlUserMail = "${pageContext.request.contextPath}/ajax/getUserSMS.htm?maPhong=";
    
    var sourceUserMail =
    {
        datatype: "json",
        datafields: [
				{ name: 'username' },
				{ name: 'email' }
        ],
        url: urlUserMail,
        async: false
    };
    var dataAdapterUserMail = new $.jqx.dataAdapter(sourceUserMail);
    // Create a jqxDropDownList
   
 // Create a jqxDropDownList
    $("#cbUserMail").jqxDropDownList({ source: dataAdapterUserMail, displayMember: "username", valueMember: "email", width: 150,height: '20px',itemHeight: 30,theme: theme,autoOpen: true });
   	var vendor =  "<c:out value='${cbUserMail}'/>";
    if(vendor!=''&&vendor!=null)
    	$('#cbUserMail').val(vendor);
	
 // Create a jqxDropDownList alarmName

	var urlisEnable = "${pageContext.request.contextPath}/ajax/getEnable.htm";
    // prepare the data
    var sourceisEnable=
    {
        datatype: "json",
        datafields: [
				{ name: 'name'},
				{ name: 'value'}
       ],
        url: urlisEnable,
        async: false
    };
    var dataAdapterisEnable= new $.jqx.dataAdapter(sourceisEnable);
    // Create a jqxDropDownList
    $("#isEnable").jqxComboBox({ source: dataAdapterisEnable, displayMember: "value", valueMember: "name", width: "100%", height: 20, theme: theme,autoOpen: true ,autoDropDownHeight: true});
    var isEnable =  "<c:out value='${define.isEnable}'/>";
   	if(isEnable!=""&&isEnable!=null){
    	$('#isEnable').val(isEnable);}
	
   	$("#cbUserSMS").change(function(){
		var cbUserSMS = $("#cbUserSMS").val();
		if (cbUserSMS!=null && cbUserSMS!='')
			{
				var groupUser = $("#groupUser").val();
				var groupList = groupUser.split(",");
				var kt = false;
				if (groupUser != null && groupUser != '') 
				{
					for (var i=0; i<groupList.length; i++) {
						if (cbUserSMS ==groupList[i] )
							kt= true;
					}
					if (kt == false)
					{
						groupUser+=','+cbUserSMS;
					}
					
				}
				else
				{
					groupUser+=cbUserSMS;
				}
				$("#groupUser").val(groupUser);	
			}
		
	});
   	
   	$("#cbUserMail").change(function(){
		var cbUserMail = $("#cbUserMail").val();
		if (cbUserMail!=null && cbUserMail!='')
			{
				var groupUser = $("#groupUser").val();
				var groupList = groupUser.split(",");
				var kt = false;
				if (groupUser != null && groupUser != '') 
				{
					for (var i=0; i<groupList.length; i++) {
						if (cbUserMail ==groupList[i] )
							kt= true;
					}
					if (kt == false)
					{
						groupUser+=','+cbUserMail;
					}
					
				}
				else
				{
					groupUser+=cbUserMail;
				}
				$("#groupUser").val(groupUser);	
			}
		
	});
});
</script>
</body>
</html>