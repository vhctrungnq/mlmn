<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<table width="100%" border="0" cellspacing="3" cellpadding="0">
<tr><td>
<!-- Dia danh -->
<div style="float: left;width:100%;background-color: #fff;">
	<div style="float: left;width: 30%;">
		<span class="contentRolesGrid">Danh sách quận/huyện</span>
		<div id="selectiveProvince" style="overflow-y: auto; overflow-x: hidden; max-height: 400px;">
			<display:table name="${provincesCodeList}" class="simple2" id="itemProvinces" requestURI="" >
				<display:column class="centerColumnMana" style="width:40px;" title="<input type='checkbox' name='selectAllCheckProvinces' onClick='javascript:funcSelectAll(\"langProvinces\",\"selectAllCheckProvinces\")' value='Select All' />" media="html">
						<input class="selectall" type="checkbox" name="langProvinces" value="${itemProvinces.code}"/>
       			</display:column>
       			<display:column property="region" titleKey="qLPhongBan.region" />
				<display:column property="province" titleKey="qLNguoiDung.province" />
				<display:column property="district" titleKey="qLNguoiDung.district"/>
				<display:column property="code" titleKey="qLNguoiDung.code"/>
			</display:table>
		</div>
	</div>
	<div align="center" style="float: left;width: 10%;">
		<br>
		<a id="addProvinces">
			<img src="${pageContext.request.contextPath}/images/icon/1382366479_navigation-right-button_basic_blue.png" id="picture1" />
		</a><br><br>
		<a id="removeProvinces" >
			<img src="${pageContext.request.contextPath}/images/icon/1382366411_navigation-left-button_basic_blue.png" id="picture2" />
		</a>
	</div>
	<div style="float: right;width: 60%;">
		<span class="contentRolesGrid">Danh sách quận/huyện quản lý</span>
		<div id="selectiveArea" style="overflow-y: auto; overflow-x: hidden; max-height: 400px;">
			<display:table name="${sysUserAreaListByUsername}" class="simple2" id="itemArea" requestURI="" >
				<display:column class="centerColumnMana" style="width:40px;" title="<input type='checkbox' name='selectAllCheckArea' onClick='javascript:funcSelectAll(\"langArea\",\"selectAllCheckArea\")' value='Select All' />" media="html">
						<input class="selectall" type="checkbox" name="langArea" value="${itemArea.id}"/>
       			</display:column>
       			<display:column property="region" titleKey="qLPhongBan.region" />
       			<display:column property="province" titleKey="qLNguoiDung.province"/>
       			<display:column property="districtName" titleKey="qLNguoiDung.district"/>
				<display:column property="district" titleKey="qLNguoiDung.code"/>
				<display:column class="centerColumnMana" style="width:40px;" title="Enable&nbsp;<input type='checkbox' name='selectAllCheckEnableArea' onClick='javascript:funcSelectAll(\"langEnableArea\",\"selectAllCheckEnableArea\")' value='Select All' />" media="html">
					<c:choose>
						<c:when test="${itemArea.isEnable == 'Y'}">
							<input class="selectall" type="checkbox" name="langEnableArea" value="${itemArea.id}" checked="checked"/>
						</c:when>
						<c:otherwise>
							<input class="selectall" type="checkbox" name="langEnableArea" value="${itemArea.id}"/>
						</c:otherwise>
					</c:choose>
				</display:column>
				<display:column class="centerColumnMana" style="width:180px;" 
				title="
				Role&nbsp;
				<select id='selectAllcomboboxRoleArea' name='selectAllcomboboxRoleArea' onClick='javascript:funcSelectAllcomboboxRole(\"comboboxRoleArea\",\"selectAllcomboboxRoleArea\")'  class='wid90'>
					<option value=''/>--Select--</option>
					<option value='0'/>All Alarm &amp; KPI</option>
					<option value='1'/>All Alarm</option>
					<option value='2'/>All KPI</option>
				</select>
				" media="html">
					<select id="combobox_${itemArea.id}" name="comboboxRoleArea" class="wid90">
						<c:choose>
							<c:when test="${itemArea.alarmKpi == 0}"><option value="0" selected="selected"/>Alarm & KPI</option></c:when>
							<c:otherwise><option value="0"/>Alarm &amp; KPI</option></c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${itemArea.alarmKpi == 1}"><option value="1" selected="selected"/>Alarm</option></c:when>
							<c:otherwise><option value="1" />Alarm</option></c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${itemArea.alarmKpi == 2}"><option value="2" selected="selected"/>KPI</option></c:when>
							<c:otherwise><option value="2" />KPI</option></c:otherwise>
						</c:choose>
		       		</select>
				</display:column>
			</display:table>
		</div>
	</div>
</div>

<!-- Thiet bi -->
<div style="float: left;width:100%;background-color: #fff">
	<div style="float: left;width: 30%;">
		<span class="contentRolesGrid">Danh sách tổng đài</span>
		<div id="selectiveNeName" style="overflow-y: auto; overflow-x: hidden; max-height: 400px;">
			<display:table name="${neList}" class="simple2" id="itemNeName" requestURI="" >
				<display:column class="centerColumnMana" style="width:40px;" title="<input type='checkbox' name='selectAllCheckNeName' onClick='javascript:funcSelectAll(\"langNeName\",\"selectAllCheckNeName\")' value='Select All' />" media="html">
						<input class="selectall" type="checkbox" name="langNeName" value="${itemNeName.neName}"/>
       			</display:column>
				<display:column property="region" titleKey="qLPhongBan.region" />
				<display:column property="neType" titleKey="qLNguoiDung.neType" />
				<display:column property="neName" titleKey="qLNguoiDung.neName"/>
				<display:column property="vendor" titleKey="qLNguoiDung.vendor"/>
			</display:table>
		</div>
	</div>
	<div align="center" style="float: left;width: 10%;">
		<br>
		<a id="addEquipment">
			<img src="${pageContext.request.contextPath}/images/icon/1382366479_navigation-right-button_basic_blue.png" id="picture3" />
		</a><br><br>
		<a id="removeEquipment" >
			<img src="${pageContext.request.contextPath}/images/icon/1382366411_navigation-left-button_basic_blue.png" id="picture4" />
		</a>
	</div>
	<div style="float: right;width: 60%;">
		<span class="contentRolesGrid">Danh sách tổng đài quản lý</span>
		<div id="selectiveEquipment" style="overflow-y: auto; overflow-x: hidden; max-height: 400px;">
			<display:table name="${equipmentListByUsername}" class="simple2" id="itemEquipment" requestURI="" >
				<display:column class="centerColumnMana" style="width:40px;" title="<input type='checkbox' name='selectAllCheckEquipment' onClick='javascript:funcSelectAll(\"langEquipment\",\"selectAllCheckEquipment\")' value='Select All' />" media="html">
						<input class="selectall" type="checkbox" name="langEquipment" value="${itemEquipment.id}"/>
       			</display:column>
       			<display:column property="region" titleKey="qLPhongBan.region" />
       			<display:column property="neType" titleKey="qLNguoiDung.neType"/>
       			<display:column property="equipmentName" titleKey="qLNguoiDung.neName"/>
       			<display:column property="vendor" titleKey="qLNguoiDung.vendor"/>
				<display:column class="centerColumnMana" style="width:40px;" title="Enable&nbsp;<input type='checkbox' name='selectAllCheckEnableEquipment' onClick='javascript:funcSelectAll(\"langEnableEquipment\",\"selectAllCheckEnableEquipment\")' value='Select All' />" media="html">
					<c:choose>
						<c:when test="${itemEquipment.isEnable == 'Y'}">
							<input class="selectall" type="checkbox" name="langEnableEquipment" value="${itemEquipment.id}" checked="checked"/>
						</c:when>
						<c:otherwise>
							<input class="selectall" type="checkbox" name="langEnableEquipment" value="${itemEquipment.id}"/>
						</c:otherwise>
					</c:choose>
				</display:column>
				<display:column class="centerColumnMana" style="width:180px;" 
				title="
				Role&nbsp;
				<select id='selectAllcomboboxRoleEquipment' name='selectAllcomboboxRoleEquipment' onClick='javascript:funcSelectAllcomboboxRole(\"comboboxRoleEquipment\",\"selectAllcomboboxRoleEquipment\")' class='wid90'>
					<option value=''/>--Select--</option>
					<option value='0'/>All Alarm &amp; KPI</option>
					<option value='1'/>All Alarm</option>
					<option value='2'/>All KPI</option>
				</select>
				" media="html">
					<select id="equipment_${itemEquipment.id}" name="comboboxRoleEquipment" class="wid90">
						<c:choose>
							<c:when test="${itemEquipment.alarmKpi == 0}"><option value="0" selected="selected"/>Alarm & KPI</option></c:when>
							<c:otherwise><option value="0"/>Alarm & KPI</option></c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${itemEquipment.alarmKpi == 1}"><option value="1" selected="selected"/>Alarm</option></c:when>
							<c:otherwise><option value="1"/>Alarm</option></c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${itemEquipment.alarmKpi == 2}"><option value="2" selected="selected"/>KPI</option></c:when>
							<c:otherwise><option value="2"/>KPI</option></c:otherwise>
						</c:choose>
		       		</select>
				</display:column>
			</display:table>
		</div>
	</div>
</div></td></tr>
</table>
<!-- danh sach quan/huyen -->
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
$($('#itemProvinces > tbody tr')[0]).before('<tr><td></td><td><input type="text" id="search_region" class="wid90" /></td><td><input type="text" id="search_province" class="wid90" /></td><td><input type="text" id="search_district" class="wid90"/></td><td><input type="text" id="search_code" class="wid90"/></td></tr>');
$($('#itemArea > tbody tr')[0]).before('<tr><td></td><td><input type="text" id="search_regionArea" class="wid90" /></td><td><input type="text" id="search_provinceArea" class="wid90" /></td><td><input type="text" id="search_districtArea" class="wid90"/></td><td><input type="text" id="search_codeArea" class="wid90"/></td><td></td><td></td></tr>');
$("#search_province").delayKeyup(function() {
	$.getJSON("${pageContext.request.contextPath}/admin/user/hProvincesCodeByUsername.htm", {username : $('#username').val(), province : $('#search_province').val(),district : $('#search_district').val(),code : $('#search_code').val(),region : $('#search_region').val()}, function(j){
		loadHProvincesCode(j);
		var input = $("#search_province").focus();
        input.focus();
        var tmpStr = input.val();
        input.val('');
        input.val(tmpStr);
	});
},1000);
$("#search_district").delayKeyup(function() {
	$.getJSON("${pageContext.request.contextPath}/admin/user/hProvincesCodeByUsername.htm", {username : $('#username').val(), province : $('#search_province').val(),district : $('#search_district').val(),code : $('#search_code').val(),region : $('#search_region').val()}, function(j){
		loadHProvincesCode(j);
		var input = $("#search_district").focus();
        input.focus();
        var tmpStr = input.val();
        input.val('');
        input.val(tmpStr);
	}); 
},1000);
$("#search_code").delayKeyup(function() {
	$.getJSON("${pageContext.request.contextPath}/admin/user/hProvincesCodeByUsername.htm", {username : $('#username').val(), province : $('#search_province').val(),district : $('#search_district').val(),code : $('#search_code').val(),region : $('#search_region').val()}, function(j){
		loadHProvincesCode(j);
		var input = $("#search_code").focus();
        input.focus();
        var tmpStr = input.val();
        input.val('');
        input.val(tmpStr);
	});
},1000);

$("#search_region").delayKeyup(function() {
	$.getJSON("${pageContext.request.contextPath}/admin/user/hProvincesCodeByUsername.htm", {username : $('#username').val(), province : $('#search_province').val(),district : $('#search_district').val(),code : $('#search_code').val(),region : $('#search_region').val()}, function(j){
		loadHProvincesCode(j);
		var input = $("#search_region").focus();
        input.focus();
        var tmpStr = input.val();
        input.val('');
        input.val(tmpStr);
	});
},1000);

$("#search_provinceArea").delayKeyup(function() {
	$.getJSON("${pageContext.request.contextPath}/admin/user/sysUserAreaListByUsername.htm", {username : $('#username').val(), province : $('#search_provinceArea').val(),district : $('#search_districtArea').val(),code : $('#search_codeArea').val(),region: $('#search_regionArea').val()}, function(j){
		loadSysUserArea(j);
		var input = $("#search_provinceArea").focus();
        input.focus();
        var tmpStr = input.val();
        input.val('');
        input.val(tmpStr);
	});
},1000);
$("#search_districtArea").delayKeyup(function() {
	$.getJSON("${pageContext.request.contextPath}/admin/user/sysUserAreaListByUsername.htm", {username : $('#username').val(), province : $('#search_provinceArea').val(),district : $('#search_districtArea').val(),code : $('#search_codeArea').val(),region: $('#search_regionArea').val()}, function(j){
		loadSysUserArea(j);
		var input = $("#search_districtArea").focus();
        input.focus();
        var tmpStr = input.val();
        input.val('');
        input.val(tmpStr);
	});
},1000);
$("#search_codeArea").delayKeyup(function() {
	$.getJSON("${pageContext.request.contextPath}/admin/user/sysUserAreaListByUsername.htm", {username : $('#username').val(), province : $('#search_provinceArea').val(),district : $('#search_districtArea').val(),code : $('#search_codeArea').val(),region: $('#search_regionArea').val()}, function(j){
		loadSysUserArea(j);
		var input = $("#search_codeArea").focus();
        input.focus();
        var tmpStr = input.val();
        input.val('');
        input.val(tmpStr);
	});
},1000);

$("#search_regionArea").delayKeyup(function() {
	$.getJSON("${pageContext.request.contextPath}/admin/user/sysUserAreaListByUsername.htm", {username : $('#username').val(), province : $('#search_provinceArea').val(),district : $('#search_districtArea').val(),code : $('#search_codeArea').val(),region: $('#search_regionArea').val()}, function(j){
		loadSysUserArea(j);
		var input = $("#search_regionArea").focus();
        input.focus();
        var tmpStr = input.val();
        input.val('');
        input.val(tmpStr);
	});
},1000);

$('#addProvinces').click(function(){
	var addCheckedList = "";
	var temp1 = "";
	$("input[type='checkbox'][name='langProvinces']").each(function(){
		if($(this).is(':checked'))
		{
			if($(this).val() != "Select All" && $(this).val() != "on"){
				addCheckedList += temp1 + $(this).val(); temp1 = "-";}
		}
		
	});

	$.getJSON("${pageContext.request.contextPath}/admin/user/sysUserAreaListByUsername.htm", {username : $('#username').val(), province : $('#search_provinceArea').val(),district : $('#search_districtArea').val(),code : $('#search_codeArea').val(), addCheckedList : addCheckedList, removeCheckedList: null}, function(j){
		loadSysUserArea(j);
		$.getJSON("${pageContext.request.contextPath}/admin/user/hProvincesCodeByUsername.htm", {username : $('#username').val(),province : $('#search_province').val(),district : $('#search_district').val(),code : $('#search_code').val()}, function(j){
			loadHProvincesCode(j);
		});
	});
	
	
});

$('#removeProvinces').click(function(){
	var removeCheckedList = "";
	var temp1 = "";
	$("input[type='checkbox'][name='langArea']").each(function(){
		if($(this).is(':checked'))
		{
			if($(this).val() != "Select All" && $(this).val() != "on"){
				removeCheckedList += temp1 + $(this).val(); temp1 = "-";}
		}
	});
	
	$.getJSON("${pageContext.request.contextPath}/admin/user/sysUserAreaListByUsername.htm", {username : $('#username').val(), province : $('#search_provinceArea').val(),district : $('#search_districtArea').val(),code : $('#search_codeArea').val(), addCheckedList : null, removeCheckedList: removeCheckedList}, function(j){
		loadSysUserArea(j);
		$.getJSON("${pageContext.request.contextPath}/admin/user/hProvincesCodeByUsername.htm", {username : $('#username').val(),province : $('#search_province').val(),district : $('#search_district').val(),code : $('#search_code').val()}, function(j){
			loadHProvincesCode(j);
		});
	});
	
	
});

function convertUndefinedToString(str){
	
	if(str == null){
		str = "";
	}
	return str;
}

function loadHProvincesCode(j){
	var tableProvince = '<table id="itemProvinces" class="simple2">';
	tableProvince += '<thead><tr>';
	tableProvince += '<th style="width:40px;"><input type="checkbox" name="selectAllCheckProvinces" onclick="javascript:funcSelectAll(\'langProvinces\',\'selectAllCheckProvinces\')" value="Select All"></th>';
	tableProvince += '<th><fmt:message key="qLPhongBan.region"/></th>';
	tableProvince += '<th><fmt:message key="qLNguoiDung.province"/></th>';
	tableProvince += '<th><fmt:message key="qLNguoiDung.district"/></th>';
	tableProvince += '<th><fmt:message key="qLNguoiDung.code"/></th>';
	tableProvince += '</tr></thead>';
	tableProvince += '<tbody>';	
	tableProvince += '<tr><td></td><td><input type="text" id="search_region" value="' + convertUndefinedToString($('#search_region').val()) + '" class="wid90" /></td><td><input type="text" id="search_province" value="' + convertUndefinedToString($('#search_province').val()) + '" class="wid90" /></td><td><input type="text" id="search_district" value="' + convertUndefinedToString($('#search_district').val()) + '" class="wid90"/></td><td><input type="text" id="search_code" value="' + convertUndefinedToString($('#search_code').val()) + '" class="wid90"/></td></tr>';
	
	for (var i = 0; i < j.length; i++) {
		tableProvince += '<tr>';
		tableProvince += '<td style="width: 40px;" class="centerColumnMana">';
		tableProvince += '<input class="selectall" type="checkbox" name="langProvinces" value="' + j[i].code + '">';
		tableProvince += '</td>';
		tableProvince += '<td>' + j[i].region + '</td>';
		tableProvince += '<td>' + j[i].province + '</td>';
		tableProvince += '<td>' + j[i].district + '</td>';
		tableProvince += '<td>' + j[i].code + '</td>';
		tableProvince += '</tr>';
	}
	tableProvince += '</tbody>';
	tableProvince += '</table>';
	$("#selectiveProvince").html(tableProvince);
	
	$("#search_province").delayKeyup(function() {
		$.getJSON("${pageContext.request.contextPath}/admin/user/hProvincesCodeByUsername.htm", {username : $('#username').val(), province : $('#search_province').val(),district : $('#search_district').val(),code : $('#search_code').val(),region : $('#search_region').val()}, function(j){
			loadHProvincesCode(j);
			var input = $("#search_province").focus();
	        input.focus();
	        var tmpStr = input.val();
	        input.val('');
	        input.val(tmpStr);
		});
	},1000);
	
	$("#search_district").delayKeyup(function() {
		$.getJSON("${pageContext.request.contextPath}/admin/user/hProvincesCodeByUsername.htm", {username : $('#username').val(), province : $('#search_province').val(),district : $('#search_district').val(),code : $('#search_code').val(),region : $('#search_region').val()}, function(j){
			loadHProvincesCode(j);
			var input = $("#search_district").focus();
	        input.focus();
	        var tmpStr = input.val();
	        input.val('');
	        input.val(tmpStr);
		}); 
	},1000);
	$("#search_code").delayKeyup(function() {
		$.getJSON("${pageContext.request.contextPath}/admin/user/hProvincesCodeByUsername.htm", {username : $('#username').val(), province : $('#search_province').val(),district : $('#search_district').val(),code : $('#search_code').val(),region : $('#search_region').val()}, function(j){
			loadHProvincesCode(j);
			var input = $("#search_code").focus();
	        input.focus();
	        var tmpStr = input.val();
	        input.val('');
	        input.val(tmpStr);
		});
	},1000);
	$("#search_region").delayKeyup(function() {
		$.getJSON("${pageContext.request.contextPath}/admin/user/hProvincesCodeByUsername.htm", {username : $('#username').val(), province : $('#search_province').val(),district : $('#search_district').val(),code : $('#search_code').val(),region : $('#search_region').val()}, function(j){
			loadHProvincesCode(j);
			var input = $("#search_region").focus();
	        input.focus();
	        var tmpStr = input.val();
	        input.val('');
	        input.val(tmpStr);
		});
	},1000);
}

function loadSysUserArea(j)
{
	var tableArea = '<table id="itemArea" class="simple2">';
	tableArea += '<thead><tr>';
	tableArea += '<th style="width:40px;"><input type="checkbox" name="selectAllCheckArea" onclick="javascript:funcSelectAll(\'langArea\',\'selectAllCheckArea\')" value="Select All"></th>';
	tableArea += '<th><fmt:message key="qLPhongBan.region"/></th>';
	tableArea += '<th><fmt:message key="qLNguoiDung.province"/></th>';
	tableArea += '<th><fmt:message key="qLNguoiDung.district"/></th>';
	tableArea += '<th><fmt:message key="qLNguoiDung.code"/></th>';
	tableArea += '<th><fmt:message key="qLNguoiDung.enable"/>&nbsp;<input type="checkbox" name="selectAllCheckEnableArea" onClick="javascript:funcSelectAll(\'langEnableArea\',\'selectAllCheckEnableArea\')" value="Select All" /></th>';
	tableArea += '<th><fmt:message key="qLNguoiDung.role"/>&nbsp;<select id="selectAllcomboboxRoleArea" name="selectAllcomboboxRoleArea" onclick="javascript:funcSelectAllcomboboxRole(\'comboboxRoleArea\',\'selectAllcomboboxRoleArea\')" class="wid90">';
	tableArea += '<option value="">--Select--</option>';
	tableArea += '<option value="0">All Alarm &amp; KPI</option>';
	tableArea += '<option value="1">All Alarm</option>';
	tableArea += '<option value="2">All KPI</option>';
	tableArea += '</select></th>';
	tableArea += '</tr></thead>';
	tableArea += '<tbody>';
	tableArea += '<tr><td></td><td><input type="text" id="search_regionArea" value="' + convertUndefinedToString($('#search_regionArea').val()) + '" class="wid90" /></td><td><input type="text" id="search_provinceArea" value="' + convertUndefinedToString($('#search_provinceArea').val()) + '" class="wid90" /></td><td><input type="text" id="search_districtArea" value="' + convertUndefinedToString($('#search_districtArea').val()) + '" class="wid90"/></td><td><input type="text" id="search_codeArea" value="' + convertUndefinedToString($('#search_codeArea').val()) + '" class="wid90"/></td><td></td><td></td></tr>';
	for (var i = 0; i < j.length; i++) {
		tableArea += '<tr>' ;
		
		tableArea += '<td style="width: 40px;" class="centerColumnMana">';
		tableArea += '<input class="selectall" type="checkbox" name="langArea" value="' + j[i].id + '"/>';
		tableArea += '</td>';
		tableArea += '<td>' + j[i].region + '</td>';
		tableArea += '<td>' + j[i].province + '</td>';
		tableArea += '<td>' + j[i].districtName + '</td>';
		tableArea += '<td>' + j[i].district + '</td>';
		
		tableArea += '<td style="width: 40px;" class="centerColumnMana">';
		if(j[i].isEnable == 'Y')
			tableArea += '<input class="selectall" type="checkbox" name="langEnableArea" value="' + j[i].id + '" checked="checked"/>';
		else
			tableArea += '<input class="selectall" type="checkbox" name="langEnableArea" value="' + j[i].id + '"/>';
		tableArea += '</td>';
		tableArea += '<td style="width:180px;" class="centerColumnMana">';
		tableArea += '<select id="combobox_' + j[i].id + '" name="comboboxRoleArea" class="wid90">';
		if(j[i].alarmKpi == 0)
			tableArea += '<option value="0" selected="selected">Alarm &amp; KPI</option>';
		else
			tableArea += '<option value="0">Alarm &amp; KPI</option>';	
		if(j[i].alarmKpi == 1)
			tableArea += '<option value="1" selected="selected">Alarm</option>';
		else
			tableArea += '<option value="1">Alarm</option>';	
		if(j[i].alarmKpi == 2)
			tableArea += '<option value="2" selected="selected">KPI</option>';
		else
			tableArea += '<option value="2">KPI</option>';	
		tableArea += '</select>';
		tableArea += '</td>';
		tableArea += '</tr>';
	}
	tableArea += '</tbody>';
	tableArea += '</table>';
	$("#selectiveArea").html(tableArea);   
	$("#search_provinceArea").delayKeyup(function() {
		$.getJSON("${pageContext.request.contextPath}/admin/user/sysUserAreaListByUsername.htm", {username : $('#username').val(), province : $('#search_provinceArea').val(),district : $('#search_districtArea').val(),code : $('#search_codeArea').val(),region: $('#search_regionArea').val()}, function(j){
			loadSysUserArea(j);
			var input = $("#search_provinceArea").focus();
	        input.focus();
	        var tmpStr = input.val();
	        input.val('');
	        input.val(tmpStr);
		});
	},1000);
	$("#search_districtArea").delayKeyup(function() {
		$.getJSON("${pageContext.request.contextPath}/admin/user/sysUserAreaListByUsername.htm", {username : $('#username').val(), province : $('#search_provinceArea').val(),district : $('#search_districtArea').val(),code : $('#search_codeArea').val(),region: $('#search_regionArea').val()}, function(j){
			loadSysUserArea(j);
			var input = $("#search_districtArea").focus();
	        input.focus();
	        var tmpStr = input.val();
	        input.val('');
	        input.val(tmpStr);
		});
	},1000);
	$("#search_codeArea").delayKeyup(function() {
		$.getJSON("${pageContext.request.contextPath}/admin/user/sysUserAreaListByUsername.htm", {username : $('#username').val(), province : $('#search_provinceArea').val(),district : $('#search_districtArea').val(),code : $('#search_codeArea').val(),region: $('#search_regionArea').val()}, function(j){
			loadSysUserArea(j);
			var input = $("#search_codeArea").focus();
	        input.focus();
	        var tmpStr = input.val();
	        input.val('');
	        input.val(tmpStr);
		});
	},1000);
	$("#search_regionArea").delayKeyup(function() {
		$.getJSON("${pageContext.request.contextPath}/admin/user/sysUserAreaListByUsername.htm", {username : $('#username').val(), province : $('#search_provinceArea').val(),district : $('#search_districtArea').val(),code : $('#search_codeArea').val(),region: $('#search_regionArea').val()}, function(j){
			loadSysUserArea(j);
			var input = $("#search_regionArea").focus();
	        input.focus();
	        var tmpStr = input.val();
	        input.val('');
	        input.val(tmpStr);
		});
	},1000);
}
</script>

<!-- danh sach tong dai -->
<script type="text/javascript">
$($('#itemNeName > tbody tr')[0]).before('<tr><td></td><td><input type="text" id="search_neRegion" class="wid90" /></td><td><input type="text" id="search_neType" class="wid90" /></td><td><input type="text" id="search_neName" class="wid90"/></td><td><input type="text" id="search_vendor" class="wid90"/></td></tr>');
$($('#itemEquipment > tbody tr')[0]).before('<tr><td></td><td><input type="text" id="search_EquipmentRegion" class="wid90" /></td><td><input type="text" id="search_neTypeEquipment" class="wid90" /></td><td><input type="text" id="search_neNameEquipment" class="wid90"/></td><td><input type="text" id="search_vendorEquipment" class="wid90"/></td><td></td><td></td></tr>');

// Danh sach tong dai
$("#search_neType").delayKeyup(function() {
	$.getJSON("${pageContext.request.contextPath}/admin/user/bscByUsername.htm", {username : $('#username').val(), neType : $('#search_neType').val(),neName : $('#search_neName').val(),vendor : $('#search_vendor').val(),region:$('#search_neRegion').val()}, function(j){
		loadBsc(j);
		var input = $("#search_neType").focus();
        input.focus();
        var tmpStr = input.val();
        input.val('');
        input.val(tmpStr);
	});
},1000);
$("#search_neName").delayKeyup(function() {
	$.getJSON("${pageContext.request.contextPath}/admin/user/bscByUsername.htm", {username : $('#username').val(), neType : $('#search_neType').val(),neName : $('#search_neName').val(),vendor : $('#search_vendor').val(),region:$('#search_neRegion').val()}, function(j){
		loadBsc(j);
		var input = $("#search_neName").focus();
        input.focus();
        var tmpStr = input.val();
        input.val('');
        input.val(tmpStr);
	});
},1000);
$("#search_vendor").delayKeyup(function() {
	$.getJSON("${pageContext.request.contextPath}/admin/user/bscByUsername.htm", {username : $('#username').val(), neType : $('#search_neType').val(),neName : $('#search_neName').val(),vendor : $('#search_vendor').val(),region:$('#search_neRegion').val()}, function(j){
		loadBsc(j);
		var input = $("#search_vendor").focus();
        input.focus();
        var tmpStr = input.val();
        input.val('');
        input.val(tmpStr);
	});
},1000);
$("#search_neRegion").delayKeyup(function() {
	$.getJSON("${pageContext.request.contextPath}/admin/user/bscByUsername.htm", {username : $('#username').val(), neType : $('#search_neType').val(),neName : $('#search_neName').val(),vendor : $('#search_vendor').val(),region:$('#search_neRegion').val()}, function(j){
		loadBsc(j);
		var input = $("#search_neRegion").focus();
        input.focus();
        var tmpStr = input.val();
        input.val('');
        input.val(tmpStr);
	});
},1000);


// Danh sach tong dai quan ly
$("#search_neTypeEquipment").delayKeyup(function() {
	$.getJSON("${pageContext.request.contextPath}/admin/user/equipmentListByUsername.htm", {username : $('#username').val(), neType : $('#search_neTypeEquipment').val(),neName : $('#search_neNameEquipment').val(),vendor : $('#search_vendorEquipment').val(),region:$('#search_EquipmentRegion').val()}, function(j){
		loadSysUserEquipment(j);
		var input = $("#search_neTypeEquipment").focus();
        input.focus();
        var tmpStr = input.val();
        input.val('');
        input.val(tmpStr);
	});
},1000);
$("#search_neNameEquipment").delayKeyup(function() {
	$.getJSON("${pageContext.request.contextPath}/admin/user/equipmentListByUsername.htm", {username : $('#username').val(), neType : $('#search_neTypeEquipment').val(),neName : $('#search_neNameEquipment').val(),vendor : $('#search_vendorEquipment').val(),region:$('#search_EquipmentRegion').val()}, function(j){
		loadSysUserEquipment(j);
		var input = $("#search_neNameEquipment").focus();
        input.focus();
        var tmpStr = input.val();
        input.val('');
        input.val(tmpStr);
	});
},1000);
$("#search_vendorEquipment").delayKeyup(function() {
	$.getJSON("${pageContext.request.contextPath}/admin/user/equipmentListByUsername.htm", {username : $('#username').val(), neType : $('#search_neTypeEquipment').val(),neName : $('#search_neNameEquipment').val(),vendor : $('#search_vendorEquipment').val(),region:$('#search_EquipmentRegion').val()}, function(j){
		loadSysUserEquipment(j);
		var input = $("#search_vendorEquipment").focus();
        input.focus();
        var tmpStr = input.val();
        input.val('');
        input.val(tmpStr);
	});
},1000);
$("#search_EquipmentRegion").delayKeyup(function() {
	$.getJSON("${pageContext.request.contextPath}/admin/user/equipmentListByUsername.htm", {username : $('#username').val(), neType : $('#search_neTypeEquipment').val(),neName : $('#search_neNameEquipment').val(),vendor : $('#search_vendorEquipment').val(),region:$('#search_EquipmentRegion').val()}, function(j){
		loadSysUserEquipment(j);
		var input = $("#search_EquipmentRegion").focus();
        input.focus();
        var tmpStr = input.val();
        input.val('');
        input.val(tmpStr);
	});
},1000);

$('#addEquipment').click(function(){
	var addCheckedList = "";
	var temp1 = "";
	$("input[type='checkbox'][name='langNeName']").each(function(){
		if($(this).is(':checked'))
		{
			if($(this).val() != "Select All" && $(this).val() != "on"){
				addCheckedList += temp1 + $(this).val(); temp1 = "-";}
		}
		
	});

	$.getJSON("${pageContext.request.contextPath}/admin/user/equipmentListByUsername.htm", {username : $('#username').val(), neType : $('#search_neTypeEquipment').val(),neName : $('#search_neNameEquipment').val(),vendor : $('#search_vendorEquipment').val(), addCheckedList : addCheckedList, removeCheckedList: null}, function(j){
		loadSysUserEquipment(j);
		$.getJSON("${pageContext.request.contextPath}/admin/user/bscByUsername.htm", {username : $('#username').val(), neType : $('#search_neType').val(),neName : $('#search_neName').val(),vendor : $('#search_vendor').val()}, function(j){
			loadBsc(j);
		});
	});
	
});

$('#removeEquipment').click(function(){
	var removeCheckedList = "";
	var temp1 = "";
	$("input[type='checkbox'][name='langEquipment']").each(function(){
		if($(this).is(':checked'))
		{
			if($(this).val() != "Select All" && $(this).val() != "on"){
				removeCheckedList += temp1 + $(this).val(); temp1 = "-";}
		}
	});
	
	$.getJSON("${pageContext.request.contextPath}/admin/user/equipmentListByUsername.htm", {username : $('#username').val(), neType : $('#search_neTypeEquipment').val(),neName : $('#search_neNameEquipment').val(),vendor : $('#search_vendorEquipment').val(), addCheckedList : null, removeCheckedList: removeCheckedList}, function(j){
		loadSysUserEquipment(j);
		$.getJSON("${pageContext.request.contextPath}/admin/user/bscByUsername.htm", {username : $('#username').val(), neType : $('#search_neType').val(),neName : $('#search_neName').val(),vendor : $('#search_vendor').val()}, function(j){
			loadBsc(j);
		});
	});
});

function loadBsc(j){
	var tableNeName = '<table id="itemNeName" class="simple2">';
	tableNeName += '<thead><tr>';
	tableNeName += '<th style="width:40px;"><input type="checkbox" name="selectAllCheckNeName" onclick="javascript:funcSelectAll(\'langNeName\',\'selectAllCheckNeName\')" value="Select All"></th>';
	tableNeName += '<th><fmt:message key="qLPhongBan.region"/></th>';
	tableNeName += '<th><fmt:message key="qLNguoiDung.neType"/></th>';
	tableNeName += '<th><fmt:message key="qLNguoiDung.neName"/></th>';
	tableNeName += '<th><fmt:message key="qLNguoiDung.vendor"/></th>';
	tableNeName += '</tr></thead>';
	tableNeName += '<tbody>';	
	tableNeName += '<tr><td></td><td><input type="text" id="search_neRegion" value="' + convertUndefinedToString($('#search_neRegion').val()) + '"  class="wid90" /></td><td><input type="text" id="search_neType" value="' + convertUndefinedToString($('#search_neType').val()) + '" class="wid90" /></td><td><input type="text" id="search_neName" value="' + convertUndefinedToString($('#search_neName').val()) + '" class="wid90"/></td><td><input type="text" id="search_vendor" value="' + convertUndefinedToString($('#search_vendor').val()) + '" class="wid90"/></td></tr>';
	for (var i = 0; i < j.length; i++) {
		tableNeName += '<tr>';
		tableNeName += '<td style="width: 40px;" class="centerColumnMana">';
		tableNeName += '<input class="selectall" type="checkbox" name="langNeName" value="' + j[i].neName + '">';
		tableNeName += '</td>';
		tableNeName += '<td>' + j[i].region + '</td>';
		tableNeName += '<td>' + j[i].neType + '</td>';
		tableNeName += '<td>' + j[i].neName + '</td>';
		tableNeName += '<td>' + j[i].vendor + '</td>';
		tableNeName += '</tr>';
	}
	tableNeName += '</tbody>';
	tableNeName += '</table>';
	$("#selectiveNeName").html(tableNeName);  
	$("#search_neType").delayKeyup(function() {
		$.getJSON("${pageContext.request.contextPath}/admin/user/bscByUsername.htm", {username : $('#username').val(), neType : $('#search_neType').val(),neName : $('#search_neName').val(),vendor : $('#search_vendor').val(),region:$('#search_neRegion').val()}, function(j){
			loadBsc(j);
			var input = $("#search_neType").focus();
	        input.focus();
	        var tmpStr = input.val();
	        input.val('');
	        input.val(tmpStr);
		});
	},1000);
	$("#search_neName").delayKeyup(function() {
		$.getJSON("${pageContext.request.contextPath}/admin/user/bscByUsername.htm", {username : $('#username').val(), neType : $('#search_neType').val(),neName : $('#search_neName').val(),vendor : $('#search_vendor').val(),region:$('#search_neRegion').val()}, function(j){
			loadBsc(j);
			var input = $("#search_neName").focus();
	        input.focus();
	        var tmpStr = input.val();
	        input.val('');
	        input.val(tmpStr);
		});
	},1000);
	$("#search_vendor").delayKeyup(function() {
		$.getJSON("${pageContext.request.contextPath}/admin/user/bscByUsername.htm", {username : $('#username').val(), neType : $('#search_neType').val(),neName : $('#search_neName').val(),vendor : $('#search_vendor').val(),region:$('#search_neRegion').val()}, function(j){
			loadBsc(j);
			var input = $("#search_vendor").focus();
	        input.focus();
	        var tmpStr = input.val();
	        input.val('');
	        input.val(tmpStr);
		});
	},1000);
	$("#search_neRegion").delayKeyup(function() {
		$.getJSON("${pageContext.request.contextPath}/admin/user/bscByUsername.htm", {username : $('#username').val(), neType : $('#search_neType').val(),neName : $('#search_neName').val(),vendor : $('#search_vendor').val(),region:$('#search_neRegion').val()}, function(j){
			loadBsc(j);
			var input = $("#search_neRegion").focus();
	        input.focus();
	        var tmpStr = input.val();
	        input.val('');
	        input.val(tmpStr);
		});
	},1000);
}

function loadSysUserEquipment(j)
{
	var tableEquipment = '<table id="itemEquipment" class="simple2">';
	tableEquipment += '<thead><tr>';
	tableEquipment += '<th style="width:40px;"><input type="checkbox" name="selectAllCheckEquipment" onclick="javascript:funcSelectAll(\'langEquipment\',\'selectAllCheckEquipment\')" value="Select All"></th>';
	tableEquipment += '<th><fmt:message key="qLPhongBan.region"/></th>';
	tableEquipment += '<th><fmt:message key="qLNguoiDung.neType"/></th>';
	tableEquipment += '<th><fmt:message key="qLNguoiDung.neName"/></th>';
	tableEquipment += '<th><fmt:message key="qLNguoiDung.vendor"/></th>';
	tableEquipment += '<th><fmt:message key="qLNguoiDung.enable"/>&nbsp;<input type="checkbox" name="selectAllCheckEnableEquipment" onClick="javascript:funcSelectAll(\'langEnableEquipment\',\'selectAllCheckEnableEquipment\')" value="Select All" /></th>';
	tableEquipment += '<th><fmt:message key="qLNguoiDung.role"/>&nbsp;<select id="selectAllcomboboxRoleEquipment" name="selectAllcomboboxRoleEquipment" onClick="javascript:funcSelectAllcomboboxRole(\'comboboxRoleEquipment\',\'selectAllcomboboxRoleEquipment\')" class="wid90">';
	tableEquipment += '<option value="">--Select--</option>';
	tableEquipment += '<option value="0">All Alarm &amp; KPI</option>';
	tableEquipment += '<option value="1">All Alarm</option>';
	tableEquipment += '<option value="2">All KPI</option>';
	tableEquipment += '</select></th>';
	tableEquipment += '</tr></thead>';
	tableEquipment += '<tbody>';	
	tableEquipment += '<tr><td></td><td><input type="text" id="search_EquipmentRegion" value="' + convertUndefinedToString($('#search_EquipmentRegion').val()) + '" class="wid90" /></td><td><input type="text" id="search_neTypeEquipment" value="' + convertUndefinedToString($('#search_neTypeEquipment').val()) + '" class="wid90" /></td><td><input type="text" id="search_neNameEquipment" value="' + convertUndefinedToString($('#search_neNameEquipment').val()) + '" class="wid90"/></td><td><input type="text" id="search_vendorEquipment" value="' + convertUndefinedToString($('#search_vendorEquipment').val()) + '" class="wid90"/></td><td></td><td></td></tr>';
	for (var i = 0; i < j.length; i++) {
		tableEquipment += '<tr>' ;
		
		tableEquipment += '<td style="width: 40px;" class="centerColumnMana">';
		tableEquipment += '<input class="selectall" type="checkbox" name="langEquipment" value="' + j[i].id + '"/>';
		tableEquipment += '</td>';
		tableEquipment += '<td>' + j[i].region + '</td>';
		tableEquipment += '<td>' + j[i].neType + '</td>';
		tableEquipment += '<td>' + j[i].equipmentName + '</td>';
		tableEquipment += '<td>' + j[i].vendor + '</td>';
		tableEquipment += '<td style="width: 40px;" class="centerColumnMana">';
		if(j[i].isEnable == 'Y')
			tableEquipment += '<input class="selectall" type="checkbox" name="langEnableEquipment" value="' + j[i].id + '" checked="checked"/>';
		else
			tableEquipment += '<input class="selectall" type="checkbox" name="langEnableEquipment" value="' + j[i].id + '"/>';
		tableEquipment += '</td>';
		tableEquipment += '<td style="width:180px;" class="centerColumnMana">';
		tableEquipment += '<select id="equipment_' + j[i].id + '" name="comboboxRoleEquipment" class="wid90">';
		if(j[i].alarmKpi == 0)
			tableEquipment += '<option value="0" selected="selected">Alarm &amp; KPI</option>';
		else
			tableEquipment += '<option value="0">Alarm &amp; KPI</option>';	
		if(j[i].alarmKpi == 1)
			tableEquipment += '<option value="1" selected="selected">Alarm</option>';
		else
			tableEquipment += '<option value="1">Alarm</option>';	
		if(j[i].alarmKpi == 2)
			tableEquipment += '<option value="2" selected="selected">KPI</option>';
		else
			tableEquipment += '<option value="2">KPI</option>';	
		tableEquipment += '</select>';
		tableEquipment += '</td>';
		tableEquipment += '</tr>';
	}
	tableEquipment += '</tbody>';
	tableEquipment += '</table>';
	$("#selectiveEquipment").html(tableEquipment);   
	
	$("#search_neTypeEquipment").delayKeyup(function() {
		$.getJSON("${pageContext.request.contextPath}/admin/user/equipmentListByUsername.htm", {username : $('#username').val(), neType : $('#search_neTypeEquipment').val(),neName : $('#search_neNameEquipment').val(),vendor : $('#search_vendorEquipment').val(),region:$('#search_EquipmentRegion').val()}, function(j){
			loadSysUserEquipment(j);
			var input = $("#search_neTypeEquipment").focus();
	        input.focus();
	        var tmpStr = input.val();
	        input.val('');
	        input.val(tmpStr);
		});
	},1000);
	$("#search_neNameEquipment").delayKeyup(function() {
		$.getJSON("${pageContext.request.contextPath}/admin/user/equipmentListByUsername.htm", {username : $('#username').val(), neType : $('#search_neTypeEquipment').val(),neName : $('#search_neNameEquipment').val(),vendor : $('#search_vendorEquipment').val(),region:$('#search_EquipmentRegion').val()}, function(j){
			loadSysUserEquipment(j);
			var input = $("#search_neNameEquipment").focus();
	        input.focus();
	        var tmpStr = input.val();
	        input.val('');
	        input.val(tmpStr);
		});
	},1000);
	$("#search_vendorEquipment").delayKeyup(function() {
		$.getJSON("${pageContext.request.contextPath}/admin/user/equipmentListByUsername.htm", {username : $('#username').val(), neType : $('#search_neTypeEquipment').val(),neName : $('#search_neNameEquipment').val(),vendor : $('#search_vendorEquipment').val(),region:$('#search_EquipmentRegion').val()}, function(j){
			loadSysUserEquipment(j);
			var input = $("#search_vendorEquipment").focus();
	        input.focus();
	        var tmpStr = input.val();
	        input.val('');
	        input.val(tmpStr);
		});
	},1000);
	
	$("#search_EquipmentRegion").delayKeyup(function() {
		$.getJSON("${pageContext.request.contextPath}/admin/user/equipmentListByUsername.htm", {username : $('#username').val(), neType : $('#search_neTypeEquipment').val(),neName : $('#search_neNameEquipment').val(),vendor : $('#search_vendorEquipment').val(),region:$('#search_EquipmentRegion').val()}, function(j){
			loadSysUserEquipment(j);
			var input = $("#search_EquipmentRegion").focus();
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
            $("#picture1").jqxTooltip({ content: '<i>Di chuyển quận/huyện</i>', position: 'mouse', name: 'movieTooltip'});
            $("#picture2").jqxTooltip({ content: '<i>Gỡ bỏ quận/huyện quản lý</i>', position: 'mouse', name: 'movieTooltip'});
            $("#picture3").jqxTooltip({ content: '<i>Di chuyển tổng đài</i>', position: 'mouse', name: 'movieTooltip'});
            $("#picture4").jqxTooltip({ content: '<i>Gỡ bỏ tổng đài quản lý</i>', position: 'mouse', name: 'movieTooltip'});
        });
</script>