<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<table width="100%" border="0" cellspacing="3" cellpadding="0">
<tr>
<td>    
<div style="float: left;width:100%;background-color: #fff">
	<div style="float: left;width: 30%;">
		<span class="contentRolesGrid">Không nhận SMS theo SMS ID</span>
		<div id="selectiveAlarmId" style="overflow-y: auto; overflow-x: hidden; max-height: 300px;">
			<display:table name="${cConfigAlarmTypeList}" class="simple2" id="itemAlarmId" requestURI="" >
				<display:column class="centerColumnMana" style="width:40px;" title="<input type='checkbox' name='selectAllCheckAlarmId' onClick='javascript:funcSelectAll(\"langAlarmId\",\"selectAllCheckAlarmId\")' value='Select All' />" media="html">
						<input class="selectall" type="checkbox" name="langAlarmId" value="${itemAlarmId.alarmMappingId}"/>
       			</display:column>
				<display:column property="alarmType" titleKey="SMS Type" />
				<display:column property="alarmMappingId" titleKey="SMS ID"/>
				<display:column property="alarmMappingName" titleKey="SMS Name"/>
			</display:table>
		</div><br>
		<span class="contentRolesGrid">Không nhận SMS theo cảnh báo khác</span>
		<div id="selectiveAlarmIdSysPara" style="overflow-y: auto; overflow-x: hidden; max-height: 190px;">
			<display:table name="${sysParameterAlarmIdList}" class="simple2" id="itemAlarmIdSysPara" requestURI="" >
				<display:column class="centerColumnMana" style="width:40px;" title="<input type='checkbox' name='selectAllCheckAlarmIdSysPara' onClick='javascript:funcSelectAll(\"langAlarmIdSysPara\",\"selectAllCheckAlarmIdSysPara\")' value='Select All' />" media="html">
						<input class="selectall" type="checkbox" name="langAlarmIdSysPara" value="${itemAlarmIdSysPara.id}"/>
       			</display:column>
				<display:column property="name" titleKey="SMS Type" />
				<display:column property="value" titleKey="SMS ID"/>
				<display:column property="remark" titleKey="SMS Name"/>
			</display:table>
		</div>
	</div>
	<div align="center" style="float: left;width: 10%;">
		<br>
		<a id="addAlarmId">
			<img src="${pageContext.request.contextPath}/images/icon/1382366479_navigation-right-button_basic_blue.png" id="picture5" />
		</a><br><br>
		<a id="removeAlarmId" >
			<img src="${pageContext.request.contextPath}/images/icon/1382366411_navigation-left-button_basic_blue.png" id="picture6" />
		</a>
	</div>
	<div style="float: right;width: 60%;">
		<span class="contentRolesGrid">Danh sách các loại cảnh báo người dùng sẽ không nhận</span>
		<div id="selectiveBlacklist" style="overflow-y: auto; overflow-x: hidden; max-height: 500px;">
			<display:table name="${sysBlacklist}" class="simple2" id="itemBlacklist" requestURI="" >
				<display:column class="centerColumnMana" style="width:40px;" title="<input type='checkbox' name='selectAllCheckBlacklist' onClick='javascript:funcSelectAll(\"langBlacklist\",\"selectAllCheckBlacklist\")' value='Select All' />" media="html">
						<input class="selectall" type="checkbox" name="langBlacklist" value="${itemBlacklist.id}"/>
       			</display:column>
       			<display:column property="alarmType" titleKey="SMS Type"/>
       			<display:column property="alarmId" titleKey="SMS ID"/>
       			<display:column property="alarmName" titleKey="SMS Name"/>
				<display:column class="centerColumnMana" style="width:40px;" title="Enable&nbsp;<input type='checkbox' name='selectAllCheckEnableBlacklist' onClick='javascript:funcSelectAll(\"langEnableBlacklist\",\"selectAllCheckEnableBlacklist\")' value='Select All' />" media="html">
					<c:choose>
						<c:when test="${itemBlacklist.isEnable == 'Y'}">
							<input class="selectall" type="checkbox" name="langEnableBlacklist" value="${itemBlacklist.id}" checked="checked"/>
						</c:when>
						<c:otherwise>
							<input class="selectall" type="checkbox" name="langEnableBlacklist" value="${itemBlacklist.id}"/>
						</c:otherwise>
					</c:choose>
				</display:column>
				<%-- <display:column class="centerColumnMana" style="width:180px;" title="
				Role&nbsp;
				<select id='selectAllcomboboxRoleBlacklist' name='selectAllcomboboxRoleBlacklist' onClick='javascript:funcSelectAllcomboboxRole(\"comboboxRoleBlacklist\",\"selectAllcomboboxRoleBlacklist\")' class='wid90'>
					<option value=''/>--Select--</option>
					<option value='0'/>All SMS &amp; Email</option>
					<option value='1'/>All SMS</option>
					<option value='2'/>All Email</option>
				</select>
				" media="html">
					<select id="blacklist_${itemBlacklist.id}" name="comboboxRoleBlacklist" class="wid90">
						<c:choose>
							<c:when test="${itemBlacklist.smsEmail == 0}"><option value="0" selected="selected"/>SMS & Email</option></c:when>
							<c:otherwise><option value="0" />SMS & Email</option></c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${itemBlacklist.smsEmail == 1}"><option value="1" selected="selected"/>SMS</option></c:when>
							<c:otherwise><option value="1" />SMS</option></c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${itemBlacklist.smsEmail == 2}"><option value="2" selected="selected"/>Email</option></c:when>
							<c:otherwise><option value="2"/>Email</option></c:otherwise>
						</c:choose>
		       		</select>
				</display:column> --%>
			</display:table>
		</div>
	</div>
</div>
</td>
</tr>
</table>
<!-- Nhom 3 -->
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
$($('#itemAlarmId > tbody tr')[0]).before('<tr><td></td><td><input type="text" id="search_alarmTypeConfig" class="wid90" /></td><td><input type="text" id="search_alarmIdConfig" class="wid90"/></td><td><input type="text" id="search_alarmNameConfig" class="wid90"/></td></tr>');
$($('#itemAlarmIdSysPara > tbody tr')[0]).before('<tr><td></td><td><input type="text" id="search_alarmTypeSysPara" class="wid90" /></td><td><input type="text" id="search_alarmIdSysPara" class="wid90"/></td><td><input type="text" id="search_alarmNameSysPara" class="wid90"/></td></tr>');
$($('#itemBlacklist > tbody tr')[0]).before('<tr><td></td><td><input type="text" id="search_alarmTypeBlacklist" class="wid90" /></td><td><input type="text" id="search_alarmIdBlacklist" class="wid90"/></td><td><input type="text" id="search_alarmNameBlacklist" class="wid90"/></td><td></td></tr>');
// Bang C_CONFIG_ALARM_TYPE
$("#search_alarmTypeConfig").delayKeyup(function() {
	$.getJSON("${pageContext.request.contextPath}/admin/user/alarmTypeListByUsername.htm", {username : $('#username').val(), alarmType : $('#search_alarmTypeConfig').val(),alarmId : $('#search_alarmIdConfig').val(),alarmName : $('#search_alarmNameConfig').val()}, function(j){
		loadConfigAlarmType(j);
		var input = $("#search_alarmTypeConfig").focus();
        input.focus();
        var tmpStr = input.val();
        input.val('');
        input.val(tmpStr);
	});
},1000);
$("#search_alarmIdConfig").delayKeyup(function() {
	$.getJSON("${pageContext.request.contextPath}/admin/user/alarmTypeListByUsername.htm", {username : $('#username').val(), alarmType : $('#search_alarmTypeConfig').val(),alarmId : $('#search_alarmIdConfig').val(),alarmName : $('#search_alarmNameConfig').val()}, function(j){
		loadConfigAlarmType(j);
		var input = $("#search_alarmIdConfig").focus();
        input.focus();
        var tmpStr = input.val();
        input.val('');
        input.val(tmpStr);
	});
},1000);
$("#search_alarmNameConfig").delayKeyup(function() {
	$.getJSON("${pageContext.request.contextPath}/admin/user/alarmTypeListByUsername.htm", {username : $('#username').val(), alarmType : $('#search_alarmTypeConfig').val(),alarmId : $('#search_alarmIdConfig').val(),alarmName : $('#search_alarmNameConfig').val()}, function(j){
		loadConfigAlarmType(j);
		var input = $("#search_alarmNameConfig").focus();
        input.focus();
        var tmpStr = input.val();
        input.val('');
        input.val(tmpStr);
	});
},1000);

// Bang SYS_PARAMETER
$("#search_alarmTypeSysPara").delayKeyup(function() {
	$.getJSON("${pageContext.request.contextPath}/admin/user/alarmIdSysParaByUsername.htm", {username : $('#username').val(), alarmType : $('#search_alarmTypeSysPara').val(),alarmId : $('#search_alarmIdSysPara').val(),alarmName : $('#search_alarmNameSysPara').val()}, function(j){
		loadAlarmIdSysPara(j);
		var input = $("#search_alarmTypeSysPara").focus();
        input.focus();
        var tmpStr = input.val();
        input.val('');
        input.val(tmpStr);
	});
},1000);
$("#search_alarmIdSysPara").delayKeyup(function() {
	$.getJSON("${pageContext.request.contextPath}/admin/user/alarmIdSysParaByUsername.htm", {username : $('#username').val(), alarmType : $('#search_alarmTypeSysPara').val(),alarmId : $('#search_alarmIdSysPara').val(),alarmName : $('#search_alarmNameSysPara').val()}, function(j){
		loadAlarmIdSysPara(j);
		var input = $("#search_alarmIdSysPara").focus();
        input.focus();
        var tmpStr = input.val();
        input.val('');
        input.val(tmpStr);
	});
},1000);
$("#search_alarmNameSysPara").delayKeyup(function() {
	$.getJSON("${pageContext.request.contextPath}/admin/user/alarmIdSysParaByUsername.htm", {username : $('#username').val(), alarmType : $('#search_alarmTypeSysPara').val(),alarmId : $('#search_alarmIdSysPara').val(),alarmName : $('#search_alarmNameSysPara').val()}, function(j){
		loadAlarmIdSysPara(j);
		var input = $("#search_alarmNameSysPara").focus();
        input.focus();
        var tmpStr = input.val();
        input.val('');
        input.val(tmpStr);
	});
},1000);

//Bang SYS_BLACKLIST
$("#search_alarmTypeBlacklist").delayKeyup(function() {
	$.getJSON("${pageContext.request.contextPath}/admin/user/blacklistByUsername.htm", {username : $('#username').val(), alarmType : $('#search_alarmTypeBlacklist').val(),alarmId : $('#search_alarmIdBlacklist').val(),alarmName : $('#search_alarmNameBlacklist').val()}, function(j){
		loadBlacklist(j);
		var input = $("#search_alarmTypeBlacklist").focus();
        input.focus();
        var tmpStr = input.val();
        input.val('');
        input.val(tmpStr);
	});
},1000);
$("#search_alarmIdBlacklist").delayKeyup(function() {
	$.getJSON("${pageContext.request.contextPath}/admin/user/blacklistByUsername.htm", {username : $('#username').val(), alarmType : $('#search_alarmTypeBlacklist').val(),alarmId : $('#search_alarmIdBlacklist').val(),alarmName : $('#search_alarmNameBlacklist').val()}, function(j){
		loadBlacklist(j);
		var input = $("#search_alarmIdBlacklist").focus();
        input.focus();
        var tmpStr = input.val();
        input.val('');
        input.val(tmpStr);
	});
},1000);
$("#search_alarmNameBlacklist").delayKeyup(function() {
	$.getJSON("${pageContext.request.contextPath}/admin/user/blacklistByUsername.htm", {username : $('#username').val(), alarmType : $('#search_alarmTypeBlacklist').val(),alarmId : $('#search_alarmIdBlacklist').val(),alarmName : $('#search_alarmNameBlacklist').val()}, function(j){
		loadBlacklist(j);
		var input = $("#search_alarmNameBlacklist").focus();
        input.focus();
        var tmpStr = input.val();
        input.val('');
        input.val(tmpStr);
	});
},1000);
</script>
<script type="text/javascript">

$('#addAlarmId').click(function(){
	var addCheckedList = "";
	var temp1 = "";
	$("input[type='checkbox'][name='langAlarmId']").each(function(){
		if($(this).is(':checked'))
		{
			if($(this).val() != "Select All" && $(this).val() != "on"){
				addCheckedList += temp1 + $(this).val(); temp1 = "-";}
		}
		
	});
	
	var addCheckedSysParaList = "";
	var temp2 = "";
	$("input[type='checkbox'][name='langAlarmIdSysPara']").each(function(){
		if($(this).is(':checked'))
		{
			if($(this).val() != "Select All" && $(this).val() != "on"){
				addCheckedSysParaList += temp2 + $(this).val(); temp2 = ";";}
		}
		
	});

	$.getJSON("${pageContext.request.contextPath}/admin/user/insertBlacklistByAlarmType.htm", {username : $('#username').val(),addCheckedList : addCheckedList, removeCheckedList: null, addCheckedSysParaList : addCheckedSysParaList}, function(j){
		$.getJSON("${pageContext.request.contextPath}/admin/user/blacklistByUsername.htm", {username : $('#username').val(), alarmType : $('#search_alarmTypeBlacklist').val(),alarmId : $('#search_alarmIdBlacklist').val(),alarmName : $('#search_alarmNameBlacklist').val()}, function(j){
			loadBlacklist(j);
			$.getJSON("${pageContext.request.contextPath}/admin/user/alarmIdSysParaByUsername.htm", {username : $('#username').val(), alarmType : $('#search_alarmTypeSysPara').val(),alarmId : $('#search_alarmIdSysPara').val(),alarmName : $('#search_alarmNameSysPara').val()}, function(j){
				loadAlarmIdSysPara(j);
				$.getJSON("${pageContext.request.contextPath}/admin/user/alarmTypeListByUsername.htm", {username : $('#username').val(), alarmType : $('#search_alarmTypeConfig').val(),alarmId : $('#search_alarmIdConfig').val(),alarmName : $('#search_alarmNameConfig').val()}, function(j){
					loadConfigAlarmType(j);
			
				});
			});
		});
	});
	
});

$('#removeAlarmId').click(function(){
	var val = [];
	var removeCheckedList = "";
	var temp1 = "";
	$("input[type='checkbox'][name='langBlacklist']").each(function(){
		if($(this).is(':checked'))
		{
			if($(this).val() != "Select All" && $(this).val() != "on"){
				removeCheckedList += temp1 + $(this).val(); temp1 = "-";}
		}
	});
	$.getJSON("${pageContext.request.contextPath}/admin/user/insertBlacklistByAlarmType.htm", {username : $('#username').val(),addCheckedList : null, removeCheckedList: removeCheckedList, addCheckedSysParaList : null}, function(j){
		$.getJSON("${pageContext.request.contextPath}/admin/user/blacklistByUsername.htm", {username : $('#username').val(), alarmType : $('#search_alarmTypeBlacklist').val(),alarmId : $('#search_alarmIdBlacklist').val(),alarmName : $('#search_alarmNameBlacklist').val()}, function(j){
			loadBlacklist(j);
			$.getJSON("${pageContext.request.contextPath}/admin/user/alarmIdSysParaByUsername.htm", {username : $('#username').val(), alarmType : $('#search_alarmTypeSysPara').val(),alarmId : $('#search_alarmIdSysPara').val(),alarmName : $('#search_alarmNameSysPara').val()}, function(j){
				loadAlarmIdSysPara(j);
				$.getJSON("${pageContext.request.contextPath}/admin/user/alarmTypeListByUsername.htm", {username : $('#username').val(), alarmType : $('#search_alarmTypeConfig').val(),alarmId : $('#search_alarmIdConfig').val(),alarmName : $('#search_alarmNameConfig').val()}, function(j){
					loadConfigAlarmType(j);
			
				});
			});
		});
	});

});

function loadBlacklist(j)
{
	var tableBlacklist = '<table id="itemBlacklist" class="simple2">';
	tableBlacklist += '<thead><tr>';
	tableBlacklist += '<th style="width:40px;"><input type="checkbox" name="selectAllCheckBlacklist" onclick="javascript:funcSelectAll(\'langBlacklist\',\'selectAllCheckBlacklist\')" value="Select All"></th>';
	tableBlacklist += '<th><fmt:message key="qLNguoiDung.alarmType"/></th>';
	tableBlacklist += '<th><fmt:message key="qLNguoiDung.alarmId"/></th>';
	tableBlacklist += '<th><fmt:message key="qLNguoiDung.alarmName"/></th>';
	tableBlacklist += '<th><fmt:message key="qLNguoiDung.enable"/>&nbsp;<input type="checkbox" name="selectAllCheckEnableBlacklist" onClick="javascript:funcSelectAll(\'langEnableBlacklist\',\'selectAllCheckEnableBlacklist\')" value="Select All" /></th>';
	
	/* tableBlacklist += '<th><fmt:message key="qLNguoiDung.role"/>&nbsp;<select id="selectAllcomboboxRoleBlacklist" name="selectAllcomboboxRoleBlacklist" onClick="javascript:funcSelectAllcomboboxRole(\'comboboxRoleBlacklist\',\'selectAllcomboboxRoleBlacklist\')" class="wid90">';
	tableBlacklist += '<option value="">--Select--</option>';
	tableBlacklist += '<option value="0">All SMS &amp; Email</option>';
	tableBlacklist += '<option value="1">All SMS</option>';
	tableBlacklist += '<option value="2">All Email</option>';
	tableBlacklist += '</select></th>'; */
	tableBlacklist += '</tr></thead>';
	tableBlacklist += '<tbody>';
	tableBlacklist += '<tr><td></td><td><input type="text" id="search_alarmTypeBlacklist" value="' + convertUndefinedToString($('#search_alarmTypeBlacklist').val()) + '" class="wid90" /></td><td><input type="text" id="search_alarmIdBlacklist" value="' + convertUndefinedToString($('#search_alarmIdBlacklist').val()) + '" class="wid90"/></td><td><input type="text" id="search_alarmNameBlacklist" value="' + convertUndefinedToString($('#search_alarmNameBlacklist').val()) + '" class="wid90"/></td><td></td></tr>';
	for (var i = 0; i < j.length; i++) {
		tableBlacklist += '<tr>' ;
		
		tableBlacklist += '<td style="width: 40px;" class="centerColumnMana">';
		tableBlacklist += '<input class="selectall" type="checkbox" name="langBlacklist" value="' + j[i].id + '"/>';
		tableBlacklist += '</td>';
		
		tableBlacklist += '<td>' + j[i].alarmType + '</td>';
		tableBlacklist += '<td>' + j[i].alarmId + '</td>';
		tableBlacklist += '<td>' + j[i].alarmName + '</td>';
		tableBlacklist += '<td style="width: 40px;" class="centerColumnMana">';
		if(j[i].isEnable == 'Y')
			tableBlacklist += '<input class="selectall" type="checkbox" name="langEnableBlacklist" value="' + j[i].id + '" checked="checked"/>';
		else
			tableBlacklist += '<input class="selectall" type="checkbox" name="langEnableBlacklist" value="' + j[i].id + '"/>';
		tableBlacklist += '</td>';
		/* tableBlacklist += '<td style="width:180px;" class="centerColumnMana">';
		tableBlacklist += '<select id="blacklist_' + j[i].id + '" name="comboboxRoleBlacklist" class="wid90">';
		if(j[i].smsEmail == '0')
			tableBlacklist += '<option value="0" selected="selected">SMS &amp; Email</option>';
		else
			tableBlacklist += '<option value="0">SMS &amp; Email</option>';	
		if(j[i].smsEmail == '1')
			tableBlacklist += '<option value="1" selected="selected">SMS</option>';
		else
			tableBlacklist += '<option value="1">SMS</option>';	
		if(j[i].smsEmail == '2')
			tableBlacklist += '<option value="2" selected="selected">Email</option>';
		else
			tableBlacklist += '<option value="2" >Email</option>';	
		tableBlacklist += '</select>';
		tableBlacklist += '</td>'; */
		tableBlacklist += '</tr>';
	}
	tableBlacklist += '</tbody>';
	tableBlacklist += '</table>';
	$("#selectiveBlacklist").html(tableBlacklist);   
	$("#search_alarmTypeBlacklist").delayKeyup(function() {
		$.getJSON("${pageContext.request.contextPath}/admin/user/blacklistByUsername.htm", {username : $('#username').val(), alarmType : $('#search_alarmTypeBlacklist').val(),alarmId : $('#search_alarmIdBlacklist').val(),alarmName : $('#search_alarmNameBlacklist').val()}, function(j){
			loadBlacklist(j);
			var input = $("#search_alarmTypeBlacklist").focus();
	        input.focus();
	        var tmpStr = input.val();
	        input.val('');
	        input.val(tmpStr);
		});
	},1000);
	$("#search_alarmIdBlacklist").delayKeyup(function() {
		$.getJSON("${pageContext.request.contextPath}/admin/user/blacklistByUsername.htm", {username : $('#username').val(), alarmType : $('#search_alarmTypeBlacklist').val(),alarmId : $('#search_alarmIdBlacklist').val(),alarmName : $('#search_alarmNameBlacklist').val()}, function(j){
			loadBlacklist(j);
			var input = $("#search_alarmIdBlacklist").focus();
	        input.focus();
	        var tmpStr = input.val();
	        input.val('');
	        input.val(tmpStr);
		});
	},1000);
	$("#search_alarmNameBlacklist").delayKeyup(function() {
		$.getJSON("${pageContext.request.contextPath}/admin/user/blacklistByUsername.htm", {username : $('#username').val(), alarmType : $('#search_alarmTypeBlacklist').val(),alarmId : $('#search_alarmIdBlacklist').val(),alarmName : $('#search_alarmNameBlacklist').val()}, function(j){
			loadBlacklist(j);
			var input = $("#search_alarmNameBlacklist").focus();
	        input.focus();
	        var tmpStr = input.val();
	        input.val('');
	        input.val(tmpStr);
		});
	},1000);
}

function loadConfigAlarmType(j){
	var tableAlarmId = '<table id="itemAlarmId" class="simple2">';
	tableAlarmId += '<thead><tr>';
	tableAlarmId += '<th style="width:40px;"><input type="checkbox" name="selectAllCheckAlarmId" onclick="javascript:funcSelectAll(\'langAlarmId\',\'selectAllCheckAlarmId\')" value="Select All"></th>';
	tableAlarmId += '<th><fmt:message key="qLNguoiDung.alarmType"/></th>';
	tableAlarmId += '<th><fmt:message key="qLNguoiDung.alarmId"/></th>';
	tableAlarmId += '<th><fmt:message key="qLNguoiDung.alarmName"/></th>';
	tableAlarmId += '</tr></thead>';
	tableAlarmId += '<tbody>';	
	tableAlarmId += '<tr><td></td><td><input type="text" id="search_alarmTypeConfig" value="' + convertUndefinedToString($('#search_alarmTypeConfig').val()) + '" class="wid90" /></td><td><input type="text" id="search_alarmIdConfig" value="' + convertUndefinedToString($('#search_alarmIdConfig').val()) + '" class="wid90"/></td><td><input type="text" id="search_alarmNameConfig" value="' + convertUndefinedToString($('#search_alarmNameConfig').val()) + '" class="wid90"/></td></tr>';
	for (var i = 0; i < j.length; i++) {
		tableAlarmId += '<tr>';
		tableAlarmId += '<td style="width: 40px;" class="centerColumnMana">';
		tableAlarmId += '<input class="selectall" type="checkbox" name="langAlarmId" value="' + j[i].alarmMappingId + '">';
		tableAlarmId += '</td>';
		tableAlarmId += '<td>' + j[i].alarmType + '</td>';
		tableAlarmId += '<td>' + j[i].alarmMappingId + '</td>';
		tableAlarmId += '<td>' + j[i].alarmMappingName + '</td>';
		tableAlarmId += '</tr>';
	}
	tableAlarmId += '</tbody>';
	tableAlarmId += '</table>';
	$("#selectiveAlarmId").html(tableAlarmId); 
	$("#search_alarmTypeConfig").delayKeyup(function() {
		$.getJSON("${pageContext.request.contextPath}/admin/user/alarmTypeListByUsername.htm", {username : $('#username').val(), alarmType : $('#search_alarmTypeConfig').val(),alarmId : $('#search_alarmIdConfig').val(),alarmName : $('#search_alarmNameConfig').val()}, function(j){
			loadConfigAlarmType(j);
			var input = $("#search_alarmTypeConfig").focus();
	        input.focus();
	        var tmpStr = input.val();
	        input.val('');
	        input.val(tmpStr);
		});
	},1000);
	$("#search_alarmIdConfig").delayKeyup(function() {
		$.getJSON("${pageContext.request.contextPath}/admin/user/alarmTypeListByUsername.htm", {username : $('#username').val(), alarmType : $('#search_alarmTypeConfig').val(),alarmId : $('#search_alarmIdConfig').val(),alarmName : $('#search_alarmNameConfig').val()}, function(j){
			loadConfigAlarmType(j);
			var input = $("#search_alarmIdConfig").focus();
	        input.focus();
	        var tmpStr = input.val();
	        input.val('');
	        input.val(tmpStr);
		});
	},1000);
	$("#search_alarmNameConfig").delayKeyup(function() {
		$.getJSON("${pageContext.request.contextPath}/admin/user/alarmTypeListByUsername.htm", {username : $('#username').val(), alarmType : $('#search_alarmTypeConfig').val(),alarmId : $('#search_alarmIdConfig').val(),alarmName : $('#search_alarmNameConfig').val()}, function(j){
			loadConfigAlarmType(j);
			var input = $("#search_alarmNameConfig").focus();
	        input.focus();
	        var tmpStr = input.val();
	        input.val('');
	        input.val(tmpStr);
		});
	},1000);
}

function loadAlarmIdSysPara(j){
	var tableAlarmIdSysPara = '<table id="itemAlarmIdSysPara" class="simple2">';
	tableAlarmIdSysPara += '<thead><tr>';
	tableAlarmIdSysPara += '<th style="width:40px;"><input type="checkbox" name="selectAllCheckAlarmIdSysPara" onclick="javascript:funcSelectAll(\'langAlarmIdSysPara\',\'selectAllCheckAlarmIdSysPara\')" value="Select All"></th>';
	tableAlarmIdSysPara += '<th><fmt:message key="qLNguoiDung.alarmType"/></th>';
	tableAlarmIdSysPara += '<th><fmt:message key="qLNguoiDung.alarmId"/></th>';
	tableAlarmIdSysPara += '<th><fmt:message key="qLNguoiDung.alarmName"/></th>';
	tableAlarmIdSysPara += '</tr></thead>';
	tableAlarmIdSysPara += '<tbody>';	
	tableAlarmIdSysPara += '<tr><td></td><td><input type="text" id="search_alarmTypeSysPara" value="' + convertUndefinedToString($('#search_alarmTypeSysPara').val()) + '" class="wid90" /></td><td><input type="text" id="search_alarmIdSysPara" value="' + convertUndefinedToString($('#search_alarmIdSysPara').val()) + '" class="wid90"/></td><td><input type="text" id="search_alarmNameSysPara" value="' + convertUndefinedToString($('#search_alarmNameSysPara').val()) + '" class="wid90"/></td></tr>';
	for (var i = 0; i < j.length; i++) {
		tableAlarmIdSysPara += '<tr>';
		tableAlarmIdSysPara += '<td style="width: 40px;" class="centerColumnMana">';
		tableAlarmIdSysPara += '<input class="selectall" type="checkbox" name="langAlarmIdSysPara" value="' + j[i].id + '">';
		tableAlarmIdSysPara += '</td>';
		tableAlarmIdSysPara += '<td>' + j[i].name + '</td>';
		tableAlarmIdSysPara += '<td>' + j[i].value + '</td>';
		tableAlarmIdSysPara += '<td>' + j[i].remark + '</td>';
		tableAlarmIdSysPara += '</tr>';
	}
	tableAlarmIdSysPara += '</tbody>';
	tableAlarmIdSysPara += '</table>';
	$("#selectiveAlarmIdSysPara").html(tableAlarmIdSysPara);   
	$("#search_alarmTypeSysPara").delayKeyup(function() {
		$.getJSON("${pageContext.request.contextPath}/admin/user/alarmIdSysParaByUsername.htm", {username : $('#username').val(), alarmType : $('#search_alarmTypeSysPara').val(),alarmId : $('#search_alarmIdSysPara').val(),alarmName : $('#search_alarmNameSysPara').val()}, function(j){
			loadAlarmIdSysPara(j);
			var input = $("#search_alarmTypeSysPara").focus();
	        input.focus();
	        var tmpStr = input.val();
	        input.val('');
	        input.val(tmpStr);
		});
	},1000);
	$("#search_alarmIdSysPara").delayKeyup(function() {
		$.getJSON("${pageContext.request.contextPath}/admin/user/alarmIdSysParaByUsername.htm", {username : $('#username').val(), alarmType : $('#search_alarmTypeSysPara').val(),alarmId : $('#search_alarmIdSysPara').val(),alarmName : $('#search_alarmNameSysPara').val()}, function(j){
			loadAlarmIdSysPara(j);
			var input = $("#search_alarmIdSysPara").focus();
	        input.focus();
	        var tmpStr = input.val();
	        input.val('');
	        input.val(tmpStr);
		});
	},1000);
	$("#search_alarmNameSysPara").delayKeyup(function() {
		$.getJSON("${pageContext.request.contextPath}/admin/user/alarmIdSysParaByUsername.htm", {username : $('#username').val(), alarmType : $('#search_alarmTypeSysPara').val(),alarmId : $('#search_alarmIdSysPara').val(),alarmName : $('#search_alarmNameSysPara').val()}, function(j){
			loadAlarmIdSysPara(j);
			var input = $("#search_alarmNameSysPara").focus();
	        input.focus();
	        var tmpStr = input.val();
	        input.val('');
	        input.val(tmpStr);
		});
	},1000);
}
</script>

<script type="text/javascript">
$(document).ready(function () {
    $("#picture5").jqxTooltip({ content: '<i>Di chuyển cảnh báo</i>', position: 'mouse', name: 'movieTooltip'});
    $("#picture6").jqxTooltip({ content: '<i>Gỡ bỏ cảnh báo</i>', position: 'mouse', name: 'movieTooltip'});
});
</script>