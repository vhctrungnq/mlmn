<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title><fmt:message key="sidebar.admin.usersFormDetals"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.usersFormDetals"/></content>
<form:form id="checkform" modelAttribute="usersform" commandName="usersform" method="post" action="details.htm">
	<div>
		<form:input id="id" path="id" type="hidden"/>
		<input id="groupID" name="groupID" value="${groupID}" type="hidden"/>
		<input id="system" name="system" value="${system}" type="hidden"/>
		<input id="checkedList" name="checkedList" type="hidden"/>
		<input id="uncheckedList" name="uncheckedList" type="hidden"/>
		<input id="checkedAreaList" name="checkedAreaList" type="hidden"/>
		<input id="uncheckedAreaList" name="uncheckedAreaList" type="hidden"/>
		<input id="checkedEquipmentList" name="checkedEquipmentList" type="hidden"/>
		<input id="uncheckedEquipmentList" name="uncheckedEquipmentList" type="hidden"/>
		<input id="checkedAlarmIdList" name="checkedAlarmIdList" type="hidden"/>
		<input id="uncheckedAlarmIdList" name="uncheckedAlarmIdList" type="hidden"/>
	</div>
	<table style="padding-top:10px;padding-bottom:10px;" width="100%" border="0" cellspacing="3" cellpadding="0">
		<tr>
			<td class="wid10 mwid80"><fmt:message key="qLNguoiDung.taiKhoan"/>:</td>
			<td class="wid15"><b><i>${ usersform.username}</i></b><form:hidden path="username" /></td>
			<td class="wid10 mwid80"><fmt:message key="qLNguoiDung.tenDayDu"/>:</td>
			<td class="wid15"><b><i>${ usersform.fullname}</i></b><form:hidden path="fullname" /></td>
			<td class="wid8 mwid70"><fmt:message key="qLNguoiDung.email"/>:</td>
			<td class="wid15"><b><i>${ usersform.email}</i></b><form:hidden path="email" /></td>
			<td class="wid10 mwid80"><fmt:message key="qLNguoiDung.dienThoai"/>:</td>
			<td class="wid15"><b><i>${ usersform.phone}</i></b><form:hidden path="phone" /></td>
		</tr>
		<tr>
			<td><fmt:message key="qLNguoiDung.chucDanh"/>:</td>
			<td><b><i>${ usersform.position}</i></b><form:hidden path="position" /></td>
			<td><fmt:message key="qLNguoiDung.phongBan"/>:</td>
			<td><b><i>${ usersform.maPhong}</i></b><form:hidden path="maPhong" /></td>
			<td><fmt:message key="qLNguoiDung.team"/>:</td>
			<td><b><i>${ usersform.team}</i></b><form:hidden path="team" /></td>
			<td><fmt:message key="qLNguoiDung.subTeam"/>:</td>
			<td><b><i>${ usersform.subTeam}</i></b><form:hidden path="subTeam" /></td>
		</tr>
	</table>
	<div class="cssHeaderDivRole">
		<div>Nhóm 1: Quyền nhận cảnh báo và tạo người dùng
			<div style="float: right;">
				<a id="imageDivLink1" href="javascript:toggle('togGroup1', 'imageDivLink1');">
					<img src="${pageContext.request.contextPath}/images/icon/collapse_40b.png">
				</a>
			</div>
		</div>
	</div>
	<div id="togGroup1" style="display: block;">
		<table width="100%" border="0" cellspacing="3" cellpadding="0">
			<tr>
				<%-- <td class="wid8 mwid70"><fmt:message key="qLNguoiDung.nhanEmail"/></td>
				<td class="wid20">
					<form:checkbox id="receivingEmailChanged" path="receivingEmail" value="Y" />
				</td> --%>
				<td class="wid8 mwid70"><fmt:message key="qLNguoiDung.nhanSms"/></td>
				<td class="wid8">
					<form:checkbox id="receivingSmsChanged" path="receivingSms" value="Y" />
				</td>
				<td class="wid8 mwid80"><fmt:message key="qLNguoiDung.isRoleSystem"/></td>
				<td class="wid8">
					<form:checkbox id="isRoleSystemChanged" path="isRoleSystem" value="Y" />
				</td>
				<td class="wid8 mwid90"><fmt:message key="qLNguoiDung.isSmsLeaseline"/></td>
				<td class="wid8">
					<form:checkbox id="isSmsLeaselineChanged" path="isSmsLeaseline" value="Y" />
				</td>
				<td class="wid8 mwid90"><fmt:message key="qLNguoiDung.isRoleManager"/></td>
				<td class="wid8">
					<form:checkbox id="isRoleManagerChanged" path="isRoleManager" value="Y" />
				</td>
				<td class="wid9 mwid130"><fmt:message key="qLNguoiDung.quyenTaoND"/></td>
				<td class="wid8">
					<form:checkbox id="rolesAddUsersChanged" path="rolesAddUsers" value="Y" />
				</td>
				<td class="wid6 mwid70"><fmt:message key="qLNguoiDung.smsOther"/></td>
				<td><div id='ccSms'></div></td>
			</tr>
		</table>
	</div>
	<div class="cssHeaderDivRole">
		Nhóm 2: Phân quyền quản lý theo địa danh và thiết bị
		<div style="float: right;">
			<a id="imageDivLink2" href="javascript:toggle('togGroup2', 'imageDivLink2');">
				<img src="${pageContext.request.contextPath}/images/icon/collapse_40b.png">
			</a>
		</div>
	</div>
	<div id="togGroup2" style="display: block;">
		<%@ include file="/WEB-INF/jspadmin/roleGroup2.jsp" %>
	</div>
	<div id="titleGroup3" class="cssHeaderDivRole">
		Nhóm 3: Cấu hình không nhận sms
		<div style="float: right;">
			<a id="imageDivLink3" href="javascript:toggle('togGroup3', 'imageDivLink3');">
				<img src="${pageContext.request.contextPath}/images/icon/collapse_40b.png">
			</a>
		</div>
	</div>
	<div id="togGroup3" style="display: block;">
		<%@ include file="/WEB-INF/jspadmin/roleGroup3.jsp" %>
	</div>
	<div style="display: block;" class="cssHeaderDivRole">
		Nhóm 4: Quyền truy cập chức năng
		<div style="float: right;">
			<a id="imageDivLink4" href="javascript:toggle('togGroup4', 'imageDivLink4');">
				<img src="${pageContext.request.contextPath}/images/icon/collapse_40b.png">
			</a>
		</div>
	</div>
	<div id="togGroup4" style="display: block;">
		<table width="100%" border="0" cellspacing="3" cellpadding="0"><tr><td>
		<div id="result" style="float: left;width:100%">
				<div id="phanQuyenNguoiDungLeft" class="phanQuyenNguoiDungLeft">
					<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
						<tr>
							<td id="tableLeft">
								<div style="overflow-y: auto; overflow-x: hidden; max-height: 500px;">
									<display:table name="${sysGroupUserList}" class="simple2" id="item" requestURI="">
										<display:column property="name" titleKey="qLyMenu.heThong"/>
										<display:column media="html" titleKey="qLNhomNguoiDung.tenNhom">
											<a id="${item.id}" class="link" href="details.htm?id=${usersform.id}&groupID=${item.id}&system=${item.system}">${item.groupName}</a>
										</display:column>	
									</display:table>
								</div>
							</td>
						</tr>
					</table>
				</div>
			
			<div class="phanQuyenNguoiDungRight">
					<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
						<tr>
							<td>
								<div id="selective" style="overflow-y: auto; overflow-x: hidden; max-height: 500px;">
									<display:table name="${sysResponsibilitiesList}" class="simple2" id="item1" requestURI="" >			
										<display:column property="stt" titleKey="qLNguoiDung.level" class="LEVEL_MENU centerColumnMana"/>
										<display:column property="menuCon" titleKey="pQuyenNhomNguoiDung.tenChucNang"/>
										<display:column class="centerColumnMana" style="width:60px;" title="<input type='checkbox' name='selectAllCheck' onClick='javascript:funcSelectAll(\"lang\",\"selectAllCheck\")' value='Select All' />" media="html">
											<c:choose>
												<c:when test="${item1.checked == 1}">
													<input class="selectall" type="checkbox" name="lang" value="${item1.id}" checked="checked"/>
												</c:when>
												<c:otherwise>
													<input class="selectall" type="checkbox" name="lang" value="${item1.id}"/>
												</c:otherwise>
											</c:choose>
			          					</display:column>
									</display:table>
								</div>
							</td>
						</tr>
					</table>
			</div>
		</div></td></tr>
		</table>
	</div>
	<div style="width:100%;float:left;padding-top: 10px">
		<div align="right" >	
			<input class="button" type="submit" name="save" value="<fmt:message key="global.form.luulai"/>" />
	        <input class="button" type="button" value="<fmt:message key="button.back"/>" onClick='window.location="danh-sach.htm"'>
		</div>
	</div>
	
</form:form>
<script type="text/javascript">
	$.fn.delayKeyup = function(callback, ms){
	    var timer = 0;
	    var el = $(this);
	    $(this).keyup(function(){                   
	        clearTimeout (timer);
	        timer = setTimeout(function(){
	            callback(el)
	                }, ms);
	    });
	    return $(this);
	};
	
	function funcSelectAll(checkboxName, checkboxNameAll)
	{
		checkboxes = document.getElementsByName(checkboxName);
	   if(document.getElementsByName(checkboxNameAll)[0].checked==true)
	   {
	           for(var i=0, n=checkboxes.length;i<n;i++) {
	        	    checkboxes[i].checked = true;
	        	  }
	     }
	     else
	     {
	           for(var i=0, n=checkboxes.length;i<n;i++) {
	       	    checkboxes[i].checked = false;
	       	  }
	     }          
	}
	
	function toggle(showHideDiv, switchImgTag) {
	    var ele = document.getElementById(showHideDiv);
	    var imageEle = document.getElementById(switchImgTag);
	    if(ele.style.display == "block") {
				imageEle.innerHTML = '<img src="${pageContext.request.contextPath}/images/icon/collapse_40b_collapsed.png">';
	    }
	    else {
	            imageEle.innerHTML = '<img src="${pageContext.request.contextPath}/images/icon/collapse_40b.png">';
	    }
	    $("#" + showHideDiv).slideToggle();
	}
	
	function funcSelectAllcomboboxRole(comboboxRole,selectAllcomboboxRole){
		var selectAllcomboboxRoleArea = $('#' + selectAllcomboboxRole).val();
		comboboxRole = document.getElementsByName(comboboxRole);
		if(selectAllcomboboxRoleArea == '0'){
			for(var i=0, n=comboboxRole.length;i<n;i++) {
	       	    comboboxRole[i].selectedIndex = 0;
	       	  }
		}
		if(selectAllcomboboxRoleArea == '1'){
			for(var i=0, n=comboboxRole.length;i<n;i++) {
	       	    comboboxRole[i].selectedIndex = 1;
	       	  }
		}
		if(selectAllcomboboxRoleArea == '2'){
			for(var i=0, n=comboboxRole.length;i<n;i++) {
	       	    comboboxRole[i].selectedIndex = 2;
	       	  }
		}
	}
</script>

<script type="text/javascript">

$(document).ready(function(){
	$('#' + '<c:out value="${groupID}"/>').addClass("selected");
});
	
$(function() {
    $('#checkform').submit(function() {
    	// Nhom 4
    	var checkedList = "";
    	var uncheckedList = "";
    	var temp1 = "";
    	var temp2 = "";
    	$("input[type='checkbox'][name='lang']").each(function(){ //$('#selective input:checkbox').each(function(i){
    		if($(this).is(':checked'))
    		{
    			if($(this).val() != "Select All" && $(this).val() != "on"){
    			checkedList += temp1 + $(this).val(); temp1 = "-";}
    		}
    		else
    		{
    			if($(this).val() != "Select All" && $(this).val() != "on"){
    			uncheckedList += temp2 + $(this).val(); temp2 = "-";
    			}
    		}
    		
    	});
    	$("#checkedList").val(checkedList);
    	$("#uncheckedList").val(uncheckedList);
    	
    	// Nhom 2
    	checkedList = "";
    	uncheckedList = "";
    	temp1 = "";
    	temp2 = "";
    	$("input[type='checkbox'][name='langEnableArea']").each(function(){ //$('#selective input:checkbox').each(function(i){
    		if($(this).is(':checked'))
    		{
    			if($(this).val() != "Select All" && $(this).val() != "on"){
    				checkedList += temp1 + $(this).val(); 
    				checkedList += "-";
    				checkedList += $('#combobox_' + $(this).val() +'').val();
    				temp1 = ";";
    			}
    		}
    		else
    		{
    			if($(this).val() != "Select All" && $(this).val() != "on"){
    				uncheckedList += temp2 + $(this).val(); 
    				uncheckedList += "-";
    				uncheckedList += $('#combobox_' + $(this).val() +'').val();
    				temp2 = ";";
    			}
    		}
    		
    	});
    	$("#checkedAreaList").val(checkedList);
    	$("#uncheckedAreaList").val(uncheckedList);
    	
    	
    	checkedList = "";
    	uncheckedList = "";
    	temp1 = "";
    	temp2 = "";
    	$("input[type='checkbox'][name='langEnableEquipment']").each(function(){ 
    		if($(this).is(':checked'))
    		{
    			if($(this).val() != "Select All" && $(this).val() != "on"){
    				checkedList += temp1 + $(this).val(); 
    				checkedList += "-";
    				checkedList += $('#equipment_' + $(this).val() +'').val();
    				temp1 = ";";
    			}
    		}
    		else
    		{
    			if($(this).val() != "Select All" && $(this).val() != "on"){
    				uncheckedList += temp2 + $(this).val(); 
    				uncheckedList += "-";
    				uncheckedList += $('#equipment_' + $(this).val() +'').val();
    				temp2 = ";";
    			}
    		}
    		
    	});
    	$("#checkedEquipmentList").val(checkedList);
    	$("#uncheckedEquipmentList").val(uncheckedList);
    	
    	// Nhom 3
    	checkedList = "";
    	uncheckedList = "";
    	temp1 = "";
    	temp2 = "";
    	$("input[type='checkbox'][name='langEnableBlacklist']").each(function(){ 
    		if($(this).is(':checked'))
    		{
    			if($(this).val() != "Select All" && $(this).val() != "on"){
    				checkedList += temp1 + $(this).val(); 
    				checkedList += "-";
    				checkedList += $('#blacklist_' + $(this).val() +'').val();
    				temp1 = ";";
    			}
    		}
    		else
    		{
    			if($(this).val() != "Select All" && $(this).val() != "on"){
    				uncheckedList += temp2 + $(this).val(); 
    				uncheckedList += "-";
    				uncheckedList += $('#blacklist_' + $(this).val() +'').val();
    				temp2 = ";";
    			}
    		}
    		
    	});
    	$("#checkedAlarmIdList").val(checkedList);
    	$("#uncheckedAlarmIdList").val(uncheckedList);
        return true; // return false to cancel form action
    });
    	
    ${highlight}		
});
</script>

<!-- Nhom 1 -->
<script type="text/javascript">
$(document).ready(function(){
	var theme =  getTheme(); 
	
	urlGroup = "${pageContext.request.contextPath}/admin/user/smsOtherList.htm";
	// prepare the data
    var sourceGroup =
    {
        datatype: "json",
        url: urlGroup,
        datafields: [
                     { name: 'value'},
                     { name: 'name'}
                 ],
        async: false
    };
    var dataAdapterGroup = new $.jqx.dataAdapter(sourceGroup);
    $("#ccSms").jqxDropDownList({source: dataAdapterGroup, displayMember: "name", valueMember: "value",checkboxes: true, width:'90%', height: 20, theme: theme,autoOpen: true,enableHover: true });           
    var ccSmsCBB = '<c:out value="${ccSmsCBB}"/>';
    
    if(ccSmsCBB=="")
		$('#ccSms').val('Choose');
	else
	{
		var ccSmsVar = ccSmsCBB.split(",");
		if (ccSmsVar != null) {
			for (var i=0; i<ccSmsVar.length; i++) {
				$("#ccSms").jqxDropDownList('checkItem', ccSmsVar[i] ); 
			}
		}
	}
    $("#ccSms").on('checkChange', function (event) {
        if (event.args) {
            var item = event.args.item;
            if (item) {
                var items = $("#ccSms").jqxDropDownList('getCheckedItems');
                var checkedItems = "";
                $.each(items, function (index) {
                    checkedItems += this.value + ", ";                          
                });
                $("#ccSms").val(checkedItems);
            }
        }
    });
    
});
</script>