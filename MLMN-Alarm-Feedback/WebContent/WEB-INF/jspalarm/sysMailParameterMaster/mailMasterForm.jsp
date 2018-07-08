
<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>${titleF}</title>
<content tag="heading">${titleF}</content>

<form:form commandName="mailMaster" method="post" action="form.htm?" enctype="multipart/form-data"> 
	<form:hidden path="id"/>
	<table class="simple2"> 
	<tr>
	        <td style="width: 100px;"><fmt:message key="emailMaster.mailId"/></td>
			<td colspan="5">
				<form:hidden path="mailId"  class = "long" rows="10" maxlength="900"/><b>${mailMaster.mailId}</b>
			</td> 
      </tr> 
      <tr>
	        <td style="width: 100px;"><fmt:message key="emailMaster.mailName"/></td>
			<td colspan="5">
				<form:input path="mailName"  class = "long" rows="10" />
			</td> 
      </tr> 
      <tr>
      		<td><fmt:message key="emailMaster.mailLevel"/></td>
			<td>
				<div id='mailLevel'></div>
			</td>
			
           	<td style="width: 100px;"><fmt:message key="emailMaster.sendingTime"/></td>
			<td>
				<form:input path="sendingTime"  class = "long" rows="10" maxlength="2"/>
				<font color="red"><form:errors path="sendingTime"/></font>
			</td> 
			<td style="width: 100px;"><fmt:message key="emailMaster.sendingETime"/></td>
			<td>
				<form:input path="sendingEtime"  class = "long" rows="10" maxlength="2"/>
				<font color="red"><form:errors path="sendingEtime"/></font>
			</td> 
      </tr> 
      <tr>
      		<td><fmt:message key="emailMaster.mailTo"/></td>
			<td style="width: 180px;">
				<div id="sendToCb" align="left"></div>
			</td>
			<td colspan="4">
				<form:input path="mailTo"  class = "long" rows="10"/>
			</td>
      </tr>
       <tr>
      		<td><fmt:message key="emailMaster.ccMail"/></td>
			<td style="width: 180px;">
				<div id="ccMailCb" align="left"></div>
			</td>
			<td colspan="4">
				<form:input path="ccMail"  class = "long" rows="10" />
			</td>
      </tr>
       <tr>
      		<td><fmt:message key="emailMaster.bccMail"/></td>
			<td style="width: 180px;">
				<div id="bccMailCb" align="left"></div>
			</td>
			<td colspan="4">
				<form:input path="bccMail"  class = "long" rows="10" />
			</td>
      </tr>
      <tr>  
      		<td style="width:140px;text-align:left"><fmt:message key="emailMaster.contentHeader"/></td>
        	<td colspan="5"> 
        		<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/ckeditor.js"></script>
				<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/sample.js"></script>
          		<textarea cols="80" rows="5"  id="contentHeader" name="contentHeader"  >${mailMaster.contentHeader}</textarea>
			 <script type="text/javascript">
				CKEDITOR.replace( 'contentHeader',
				{
					toolbar :
						[
							['NewPage','-','Undo','Redo'],
							['Find','Replace','-','SelectAll','RemoveFormat'],
							['Link', 'Unlink', 'Image'],
							['FontSize', 'Bold', 'Italic','Underline'],
							['NumberedList','BulletedList','-','Blockquote'],
							['TextColor', '-', 'Smiley','SpecialChar', '-', 'Maximize']
						]
				});
		  	</script>
       	</td>
      </tr> 
      
      <tr>  
       		<td style="width:140px;text-align:left"><fmt:message key="emailMaster.contentRooter"/></td>
         	<td colspan="5"> 
         		<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/ckeditor.js"></script>
				<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/sample.js"></script>
           		<textarea cols="80" rows="5"  id="contentRooter"  name="contentRooter"  >${mailMaster.contentRooter}</textarea>
           		<script type="text/javascript">
					CKEDITOR.replace( 'contentRooter',
					{
						toolbar :
							[
								['NewPage','-','Undo','Redo'],
								['Find','Replace','-','SelectAll','RemoveFormat'],
								['Link', 'Unlink', 'Image'],
								['FontSize', 'Bold', 'Italic','Underline'],
								['NumberedList','BulletedList','-','Blockquote'],
								['TextColor', '-', 'Smiley','SpecialChar', '-', 'Maximize']
							]
					});
			  	</script>
        	</td>
       </tr> 
      
       <tr>
           <td><fmt:message key="emailMaster.isEnable"/></td>
           <td> 
           	<div id='isEnable'></div>
          </td>
           <td><fmt:message key="emailMaster.rootFileAttached"/></td>
           <td colspan="3" > 
           	<form:input path="rootFileAttached"  class = "long" rows="10"/>
          </td>
       </tr>
       <tr>
           <td></td>
           <td colspan="5">
           		<input type="submit" class="button" value="<fmt:message key="global.form.luulai"/>" />
           	  	<input type="button" class="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm"'>	
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
$(document).ready(function(){
	
	//input Mail Name
    $("#mailName").jqxInput({placeHolder: "Tên mail", height: 25, width: '100%', minLength: 0, theme: theme});
  //input Mail Level
   // $("#mailLevel").jqxInput({placeHolder: "Mức mail VD:DY,HR...", height: 25, width: '100%', minLength: 0, maxLength: 900, theme: theme});
  //input Mail To
    $("#mailTo").jqxInput({placeHolder: "Danh sách người nhận", height: 25, width: '100%', minLength: 0,  theme: theme});
  //input ccMail
    $("#ccMail").jqxInput({placeHolder: "Danh sách người nhận CC", height: 25, width: '100%', minLength: 0, theme: theme});
  //input bccMail
    $("#bccMail").jqxInput({placeHolder: "Danh sách người nhận BCC", height: 25, width: '100%', minLength: 0,  theme: theme});
 //input Content Header
    $("#contentHeader").jqxInput({placeHolder: "Nội dung đầu mail", height: 25, width: '100%', minLength: 0, theme: theme});
	//input Content Rooter
    $("#contentRooter").jqxInput({placeHolder: "Nội dung cuối mail", height: 25, width: '100%', minLength: 0,theme: theme});
  //input Root File Attached
    $("#rootFileAttached").jqxInput({placeHolder: "Đường dẫn tới file đính kèm", height: 25, width: '100%', minLength: 0, theme: theme});
  //input Is Enable
  //  $("#isEnable").jqxInput({placeHolder: "Trạng thái gửi", height: 25, width: '100%', minLength: 0, maxLength: 900, theme: theme}); 
  //input Sending Time
    $("#sendingTime").jqxInput({placeHolder: "Thời gian gửi mail/Thời gian bắt đầu gửi mail (Đối với mail hàng giờ)", height: 25, width: 150, minLength: 0, maxLength: 2, theme: theme}); 
  //input Sending Time
    $("#sendingEtime").jqxInput({placeHolder: "Thời gian kết thúc gửi mail (Đối với mail hàng giờ)", height: 25, width: 150, minLength: 0, maxLength: 2, theme: theme}); 

 // Create a jqxComboBox Type Email Hour
   	var urlTypeMail = "${pageContext.request.contextPath}/ajax/getTypeEmailHourList.htm";
    // prepare the data
    var sourceTypeMail =
    {
        datatype: "json",
        datafields: [
            { name: 'value' },
            { name: 'name' }
        ],
        url: urlTypeMail,
        async: false
    };
    var dataAdapterTypeMail = new $.jqx.dataAdapter(sourceTypeMail);
    // Create a jqxComboBox
    $("#mailLevel").jqxDropDownList({ source: dataAdapterTypeMail, displayMember: "value", valueMember: "name", width: 150,height: '20px',itemHeight: 30,theme: theme,autoDropDownHeight: true,autoOpen: true });
    var typeMail =  "<c:out value='${mailMaster.mailLevel}'/>";
    if(typeMail=="")
    	$('#mailLevel').jqxDropDownList({selectedIndex: 0 }); 
	else
		$('#mailLevel').val(typeMail);
    
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
    $("#isEnable").jqxComboBox({ source: dataAdapterisEnable, displayMember: "value", valueMember: "name", width: 120, height: 20, theme: theme,autoOpen: true ,autoDropDownHeight: true});
    var isEnable =  "<c:out value='${mailMaster.isEnable}'/>";
   	if(isEnable!=""&&isEnable!=null){
    	$('#isEnable').val(isEnable);}
   	else
   		{
   			$('#isEnable').jqxComboBox({selectedIndex: 1 }); 
   		}
   	${groupList}
    $("#sendToCb").jqxComboBox({ source: groupList, height: 20, width: 150, theme: theme ,autoDropDownHeight: true });
    $('#sendToCb').on('select', function (event) 
  		{
  			var itemB= $('#mailTo').val();
  			var item = $("#sendToCb").jqxComboBox('getSelectedItem');
  			if (itemB!='')
  				{
  					if (itemB.indexOf(item.label)==-1)
  						{itemB+=","+item.label ;}
  				}
  			else
  				{
  					itemB=item.label ;
  				}
  			$("#mailTo").val(itemB);
  			//alert(itemB);
  		});
    
    $("#ccMailCb").jqxComboBox({ source: groupList, height: 20, width: 150, theme: theme ,autoDropDownHeight: true });
    $('#ccMailCb').on('select', function (event) 
  		{
  			var itemB= $('#ccMail').val();
  			var item = $("#ccMailCb").jqxComboBox('getSelectedItem');
  			if (itemB!='')
  				{
  					if (itemB.indexOf(item.label)==-1)
  						{itemB+=","+item.label ;}
  				}
  			else
  				{
  					itemB=item.label ;
  				}
  			$("#ccMail").val(itemB);
  			//alert(itemB);
  		});
    
    $("#bccMailCb").jqxComboBox({ source: groupList, height: 20, width: 150, theme: theme ,autoDropDownHeight: true });
    $('#bccMailCb').on('select', function (event) 
  		{
  			var itemB= $('#bccMail').val();
  			var item = $("#bccMailCb").jqxComboBox('getSelectedItem');
  			if (itemB!='')
  				{
  					if (itemB.indexOf(item.label)==-1)
  						{itemB+=","+item.label ;}
  				}
  			else
  				{
  					itemB=item.label ;
  				}
  			$("#bccMail").val(itemB);
  			//alert(itemB);
  		});
});

</script>
<!-- 
<script type="text/javascript">
$(document).ready(function(){
 // Create a jqxComboBox Mail Level
   var urlMailLevel = "${pageContext.request.contextPath}/ajax/getMailLevel.htm";
    // prepare the data
    var sourceMailLevel =
        {
            datatype: "json",
            datafields: [
                { name: 'value' },
                { name: 'name' }
            ],
            url: urlMailLevel,
            async: false
        };
    var dataAdapter = new $.jqx.dataAdapter(sourceMailLevel);
    
 // Create a jqxDropDownList
 // Create a jqxComboBox
     $("#mailLevel").jqxComboBox({ source: dataAdapter, displayMember: "value", valueMember: "name", width: 120,height: '20px',itemHeight: 30,theme: theme,autoDropDownHeight: true,autoOpen: true });
     var typeMail =  "<c:out value='${mailLevel}'/>";
     if(typeMail!=''&&typeMail!=null)
     	$('#mailLevel').val(typeMail);
 	else
 		$('#mailLevel').val('HR');
}); 
</script> -->
</body>
</html>