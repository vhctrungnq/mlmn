<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
	.wid40 {width:40%;}
	.wid50 {width:50%;}
	.wid100 {width:100%;}
	td {padding: 1px 0;}
	.error {color:red;}
	.editbox {display:none}
	.editbox {
		background-color: #FAFAFA;
	    border: 1px solid #DDDDDD;
	    border-radius: 2px 2px 2px 2px;
	    box-shadow: 1px 0 1px rgba(0, 0, 0, 0.1) inset;
	    font-size: 14px;
	    padding: 4px;
	    width: 270px;
	}
	.edit_td, .delete_td {width: 16px;cursor:pointer;}
	.note{color:red; float:right;padding-right: 7px;}
</style>
<title>Đăng kí đổi ca</title>
<body class="section-5" />
<content tag="heading">Đăng kí đổi ca</content>

<form:form commandName="vAlShift" method="post" action="form.htm" onload="xl_load()">
    <table class="simple2">
        <tr>
			<td style="width:20%; min-width:170px;"><form:hidden path="id"/></td>
			<td></td>
		</tr>
        
        <tr>
			<td><fmt:message key="Phiên làm việc"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td>
				<form:input type ="text" path="session" class="wid70" maxlength="500" id = "session_id" />
				<br/><form:errors path="session" class="error"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="Thứ 2"/></td>
			<td>
				<form:input type ="text" path="thu2" class="wid70" maxlength="200" id = "thu2" onchange="onchanges(this.id);" />
				<br/><form:errors path="thu2" class="error"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="Thứ 3"/></td>
			<td>
				<form:input type ="text" path="thu3" class="wid70" maxlength="200" id = "thu3" onchange="onchanges(this.id);" />
				<br/><form:errors path="thu3" class="error"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="Thứ 4"/></td>
			<td>
				<form:input type ="text" path="thu4" class="wid70" maxlength="200" id = "thu4" onchange="onchanges(this.id);" />
				<br/><form:errors path="thu4" class="error"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="Thứ 5"/></td>
			<td>
				<form:input type ="text" path="thu5" class="wid70" maxlength="200" id = "thu5" onchange="onchanges(this.id);" />
				<br/><form:errors path="thu5" class="error"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="Thứ 6"/></td>
			<td>
				<form:input type ="text" path="thu6" class="wid70" maxlength="200" id = "thu6" onchange="onchanges(this.id);" />
				<br/><form:errors path="thu2" class="error"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="Thứ 7"/></td>
			<td>
				<form:input type ="text" path="thu7" class="wid70" maxlength="200" id = "thu7" onchange="onchanges(this.id);" />
				<br/><form:errors path="thu7" class="error"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="Chủ nhật"/></td>
			<td>
				<form:input type ="text" path="cn" class="wid70" maxlength="200" id = "cn" onchange="onchanges(this.id);" />
				<br/><form:errors path="cn" class="error"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="lý do đổi ca"/></td>
			<td>
				<textarea cols="80" path = "notice" rows="10" name="notice" id="notice" maxlength="900" style="width:700px" >
				</textarea>
			</td>
		</tr>
		
        <tr>
           <td></td>
			<td>
				<input type="submit" class="button" id="save"  value="<fmt:message key="global.form.luulai"/>" />
               	<input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm"'>
			</td>
        </tr>
    </table>
    
</form:form>


<script type="text/javascript">
var body = document.getElementsByTagName("body")[0];

body.addEventListener("load", init(), false);

function init() {
    //    document.getElementById("session_id").disabled = true;
        document.getElementById("notice").focus();
};
function onchanges(myid)
{
	
	var s = document.getElementById(myid).value;
	document.getElementById(myid).value = document.getElementById(myid).value + " (*)";
	
	
}

</script>