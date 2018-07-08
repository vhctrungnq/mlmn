<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<c:choose>
  <c:when test="${UsersAddEdit == 'N'}">
      <title><fmt:message key="sidebar.admin.usersFormAdd"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.usersFormAdd"/></content>
  </c:when>
  <c:when test="${UsersAddEdit == 'Y'}">
      <title><fmt:message key="sidebar.admin.usersFormEdit"/></title>
	  <content tag="heading"><fmt:message key="sidebar.admin.usersFormEdit"/></content>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxwindow.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxdropdownbutton.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxtree.js"></script>
<form:form modelAttribute="usersform" commandName="usersform" name="checkform" method="post" action="form.htm"> 
	<div class="body-content"></div>
	<div>
		<form:input id="id" path="id" type="hidden"/>
		<form:input id="receivingSms" path="receivingSms" type="hidden"/>
		<form:input id="receivingEmail" path="receivingEmail" type="hidden"/>
		<form:input id="rolesAddUsers" path="rolesAddUsers" type="hidden"/>
	</div>
    <table class="simple2"> 
    	<tr>
      		<td class="wid15 mwid110"><fmt:message key="qLNguoiDung.taiKhoan"/>&nbsp;<font color="red">(*)</font></td>
           	<td class="wid35">
           		<c:choose>
	                <c:when test="${UsersAddEdit == 'N'}">
	                    <form:input path="username" maxlength="20" cssClass="wid60"/>
	                </c:when>
	                <c:when test="${UsersAddEdit == 'Y'}">
	                    <b><i>${ usersform.username}</i></b><form:hidden path="username" />
	                </c:when>
	                <c:otherwise></c:otherwise>
            	</c:choose>&nbsp;<form:errors path="username" cssClass="errorMessages"/>
           </td> 
           <td class="wid15 mwid110"><fmt:message key="qLNguoiDung.matKhau"/></td>
           <td><form:input type="password" path="password" maxlength="100" cssClass="wid60"/></td>
      </tr>
      <tr>
      	<td><fmt:message key="qLNguoiDung.email"/>&nbsp;<font color="red">(*)</font></td>
       	<td><form:input path="email" id="email" name="email" maxlength="80" cssClass="wid60"/>&nbsp;<form:errors path="email" cssClass="errorMessages"/></td>
		<td><fmt:message key="qLNguoiDung.nhomTruyCap"/>&nbsp;<font color="red">(*)</font></td>
       	<td><div id="groupIdCBB" ></div><form:input path="groupId" id="groupId" type="hidden"/></td>
       	
      </tr>
      <tr>
      	<td><fmt:message key="qLNguoiDung.tenDayDu"/>&nbsp;<font color="red">(*)</font></td>
        <td><form:input path="fullname" maxlength="40" cssClass="wid60"/>&nbsp;<form:errors path="fullname" cssClass="errorMessages"/></td>
        <td><fmt:message key="qLNguoiDung.dienThoai"/>&nbsp;<font color="red">(*)</font></td>
        <td><form:input path="phone" maxlength="20" cssClass="wid60"/>&nbsp;<form:errors path="phone" cssClass="errorMessages"/></td>
      </tr>
      <tr>
      	<td><fmt:message key="qLNguoiDung.phongBan"/>&nbsp;<font color="red">(*)</font></td>
        <td>
       		<form:select path="maPhong" class="wid60" >
   				<c:forEach var="items" items="${maPhongList}">
	              <c:choose>
	                <c:when test="${items.deptCode == maPhongCBB}">
	                    <option value="${items.deptCode}" selected="selected">${items.deptCode}</option>
	                </c:when>
	                <c:otherwise>
	                    <option value="${items.deptCode}">${items.deptCode}</option>
	                </c:otherwise>
	              </c:choose>
			    </c:forEach>
           	</form:select>
       		&nbsp;<form:errors path="maPhong" cssClass="errorMessages"/>
         </td>
         <td><fmt:message key="qLNguoiDung.team"/></td>
         <td>
         	<form:select path="team" class="wid60" >
         		<option value=""></option>
   				<c:forEach var="items" items="${teamList}">
	              <c:choose>
	                <c:when test="${items.deptValue == teamCBB}">
	                    <option value="${items.deptValue}" selected="selected">${items.deptValue}</option>
	                </c:when>
	                <c:otherwise>
	                    <option value="${items.deptValue}">${items.deptValue}</option>
	                </c:otherwise>
	              </c:choose>
			    </c:forEach>
           	</form:select>
         </td>
      </tr>
      <tr>
      	<td><fmt:message key="qLNguoiDung.subTeam"/></td>
        <td>
        	<form:select path="subTeam" class="wid60" >
        		<option value=""></option>
   				<c:forEach var="items" items="${subTeamList}">
	              <c:choose>
	                <c:when test="${items.deptValue == subTeamCBB}">
	                    <option value="${items.deptValue}" selected="selected">${items.deptValue}</option>
	                </c:when>
	                <c:otherwise>
	                    <option value="${items.deptValue}">${items.deptValue}</option>
	                </c:otherwise>
	              </c:choose>
			    </c:forEach>
           	</form:select>
        </td>
        <td><fmt:message key="qLNguoiDung.regionRole"/></td>
       	<td><div id="regionRoleCBB"></div><form:input path="regionRole"  id="regionRole" type="hidden"/></td>
        
      </tr>
      <tr>
      	<td><fmt:message key="qLNguoiDung.trangThai"/></td>
        <td>
       		<form:select path="isEnable" class="wid60" id="selectIsEnable">
  				<c:forEach var="items" items="${trangThaiList}">
		              <c:choose>
		                <c:when test="${items.value == trangThaiCBB}">
		                    <option value="${items.value}" selected="selected">${items.name}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.value}">${items.name}</option>
		                </c:otherwise>
		              </c:choose>
		    	</c:forEach>
	    	</form:select>
       		&nbsp;<form:errors path="isEnable" cssClass="errorMessages"/>
         </td>  
      	<td><fmt:message key="qLNguoiDung.gioiTinh"/></td>
       	<td>
       		<form:select path="sex" cssClass="wid40">
				<form:option value="M" label="Nam"/>
				<form:option value="F" label="Nữ"/>
       		</form:select>
       	</td>
       	
      </tr>
      <tr>
      <td><fmt:message key="qLNguoiDung.chucDanh"/></td>
        <td>
           	<form:select path="position" class="wid60" id="selectPosition">
   				<c:forEach var="items" items="${positionList}">
		              <c:choose>
		                <c:when test="${items.value == positionCBB}">
		                    <option value="${items.value}" selected="selected">${items.value}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.value}">${items.value}</option>
		                </c:otherwise>
		              </c:choose>
			    </c:forEach>
		    </form:select>
         </td>
      	<td><fmt:message key="qLNguoiDung.thoiGianHieuLuc"/></td>
      	<td>
          <form:input path="activeDate" cssClass="wid15" maxlength="12"/>
          <img alt="calendar" title="Click to choose the active date" id="chooseActiveDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;<form:errors path="activeDate" cssClass="errorMessages"/>	
        	&nbsp;&nbsp;&nbsp;<fmt:message key="qLNguoiDung.hieuLucDenNgay"/>
        	<form:input path="expired" cssClass="wid15" maxlength="12"/>
        	<img alt="calendar" title="Click to choose the expired" id="chooseExpired" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>&nbsp;<form:errors path="expired" cssClass="errorMessages"/>
        </td>
      </tr>
      <tr>
      	<td><fmt:message key="qLNguoiDung.hinhThucDangNhap"/></td>
	    <td>
	      	<form:select path="loginBy" class="wid60" >
   				<c:forEach var="items" items="${formsLoginList}">
	              <c:choose>
	                <c:when test="${items.value == loginByCBB}">
	                    <option value="${items.value}" selected="selected">${items.name}</option>
	                </c:when>
	                <c:otherwise>
	                    <option value="${items.value}">${items.name}</option>
	                </c:otherwise>
	              </c:choose>
			    </c:forEach>
           	</form:select>
	    </td>
      	<td><fmt:message key="qLNguoiDung.dienGiai"/></td>
	    <td>
	      	<form:input path="description" maxlength="500" cssClass="wid60" />
	    </td>
      </tr>
      <tr>
           <td></td>
           <td colspan="3">
               <input class="button" type="submit" name="save" value="<fmt:message key="global.form.luulai"/>" />
               <input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="danh-sach.htm"'>
           </td>
       </tr>
    </table>
</form:form>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<script type="text/javascript">

Calendar.setup({
    inputField		:	"activeDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseActiveDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"expired",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseExpired",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

function focusIt()
{
	var activeDateError = '<c:out value="${activeDateError}"/>';
	var expiredError = '<c:out value="${expiredError}"/>';
	var emailError = '<c:out value="${emailError}"/>';
	var phoneError = '<c:out value="${phoneError}"/>';
	
	if(document.checkform.username.value==""){
		  var mytext = document.getElementById("username");
		  mytext.focus();
	}
	else if(document.checkform.email.value=="")
	{
			var mytext = document.getElementById("email");
			  mytext.focus();
	}
	else if(document.checkform.fullname.value=="")
	{
			var mytext = document.getElementById("fullname");
			  mytext.focus();
	}
	else if(emailError !="")
	{
			var mytext = document.getElementById("email");
			  mytext.focus();
	}
	else if(document.checkform.phone.value=="")
	{
			var mytext = document.getElementById("phone");
			  mytext.focus();
	}
	else if(phoneError != "")
	{
			var mytext = document.getElementById("phone");
			  mytext.focus();
	}
	else if(expiredError != "")
	{
		var mytext = document.getElementById("expired");
		  mytext.focus();
		}
	else if(activeDateError != "")
		{
		var mytext = document.getElementById("activeDate");
		  mytext.focus();
		}
	else if(document.checkform.password.value=="")
	{
			var mytext = document.getElementById("password");
			  mytext.focus();
	}
}
onload = focusIt;

</script>
<script type="text/javascript">
$(document).ready(function(){
	var theme =  getTheme(); 
	
	urlGroup = "${pageContext.request.contextPath}/admin/user/sysGroupUserList.htm";
	// prepare the data
    var sourceGroup =
    {
        datatype: "json",
        url: urlGroup,
        datafields: [
                     { name: 'id'},
                     { name: 'groupName'}
                 ],
        async: false
    };
    var dataAdapterGroup = new $.jqx.dataAdapter(sourceGroup);
    $("#groupIdCBB").jqxDropDownList({source: dataAdapterGroup, displayMember: "groupName", valueMember: "id",checkboxes: true, width:'60%', height: 20, theme: theme,autoOpen: true,enableHover: true });           
    var groupIdCBB = '<c:out value="${groupIdCBB}"/>';
    
    if(groupIdCBB=="")
		$('#groupIdCBB').val('Choose');
	else
	{
		$('#groupId').val(groupIdCBB);
		var groupIdVar = groupIdCBB.split(",");
		if (groupIdVar != null) {
			for (var i=0; i<groupIdVar.length; i++) {
				$("#groupIdCBB").jqxDropDownList('checkItem', groupIdVar[i] ); 
			}
		}
	}
    $("#groupIdCBB").on('checkChange', function (event) {
        if (event.args) {
            var item = event.args.item;
            if (item) {
                var items = $("#groupIdCBB").jqxDropDownList('getCheckedItems');
                var checkedItems = "";
                var con = "";
                $.each(items, function (index) {
                    checkedItems += con + this.value  ;    
                    con=",";
                });
                $("#groupIdCBB").val(checkedItems);
                $("#groupId").val(checkedItems);
            }
        }
    });
    
    
    urlRegion= "${pageContext.request.contextPath}/ajax/getRegionList.htm";
	// prepare the data
    var sourceRegion =
    {
        datatype: "json",
        url: urlRegion,
        datafields: [
                     { name: 'name'},
                     { name: 'value'}
                 ],
        async: false
    };
    var dataAdapterRegion = new $.jqx.dataAdapter(sourceRegion);
    $("#regionRoleCBB").jqxDropDownList({source: dataAdapterRegion, displayMember: "value", valueMember: "name",checkboxes: true, width:'60%', height: 20, theme: theme,autoOpen: true,enableHover: true });           
    var regionCBB = '<c:out value="${regionCBB}"/>';
    
    if(regionCBB=="")
		$('#regionRoleCBB').val('Choose');
	else
	{
		$('#regionRole').val(regionCBB);
		var regionVar = regionCBB.split(",");
		if (regionVar != null) {
			for (var i=0; i<regionVar.length; i++) {
				$("#regionRoleCBB").jqxDropDownList('checkItem', regionVar[i] ); 
			}
		}
	}
    $("#regionRoleCBB").on('checkChange', function (event) {
        if (event.args) {
            var item = event.args.item;
            if (item) {
                var items = $("#regionRoleCBB").jqxDropDownList('getCheckedItems');
                var checkedItems = "";
                var con="";
                $.each(items, function (index) {
                    checkedItems += con+this.value ;   
                    con=",";
                });
                $("#regionRole").val(checkedItems);
                $("#regionRoleCBB").val(checkedItems);
            }
        }
    });
});
</script>
<script type="text/javascript">
$('#maPhong').change(function(){
	$('#load').remove();
	$('.body-content').append('<span id="load">LOADING...</span>');
	$('#load').fadeIn('normal', loadContent);
	function loadContent() {
		$('#result').load('', '', showNewContent());
	}
	function showNewContent() {
		$('#result').show('normal', hideLoader());
	}
	function hideLoader() {
		$('#load').fadeOut('normal');
	}
	
	loadTeam();
});

$('#team').change(function(){
	$('#load').remove();
	$('.body-content').append('<span id="load">LOADING...</span>');
	$('#load').fadeIn('normal', loadContent);
	function loadContent() {
		$('#result').load('', '', showNewContent());
	}
	function showNewContent() {
		$('#result').show('normal', hideLoader());
	}
	function hideLoader() {
		$('#load').fadeOut('normal');
	}
	
	loadSubTeam($('#team').val());
});

function loadTeam(){
	$.getJSON("${pageContext.request.contextPath}/admin/user/loadTeam.htm", {maPhong: $('#maPhong').val()}, function(j){
		 var options = '';
		 options += '<option value=""></option>';
		  for (var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i].deptValue + '">' + j[i].deptValue + '</option>';
			}
		$("#team").html(options);
		$('#team option:first').attr('selected', 'selected');
		
		if(j.length > 0){
			loadSubTeam(j[0].deptValue);
		}
	});
};

function loadSubTeam(team){
	$.getJSON("${pageContext.request.contextPath}/admin/user/loadSubTeam.htm", {team: team}, function(j){
		 var options = '';
		 options += '<option value=""></option>';
		  for (var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i].deptValue + '">' + j[i].deptValue + '</option>';
			}
		$("#subTeam").html(options);
		$('#subTeam option:first').attr('selected', 'selected');
	});
};
</script>