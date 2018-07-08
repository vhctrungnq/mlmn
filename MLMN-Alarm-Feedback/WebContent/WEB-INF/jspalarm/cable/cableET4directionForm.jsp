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
<title>${titleF}</title>
<body class="section-5"/>
<content tag="heading">${titleF}</content>

<form:form commandName="cableDropEt4direction" method="post" action="form.htm">
    <table class="simple2">
        <tr>
			<td style="width:20%; min-width:170px;"><form:hidden path="id"/></td>
			<td></td>
		</tr>
        
        <tr>
			<td ><fmt:message key="cableDropEt4direction.circuit"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td>
				<form:input type ="text" path="circuit" class="wid70" maxlength="20"/>
				<br/><form:errors path="circuit" class="error"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cableDropEt4direction.dipName"/></td>
			<td>
				<form:input type ="text" path="dipName" class="wid70" maxlength="20"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cableDropEt4direction.direction"/></td>
			<td>
				<form:input type ="text" path="direction" class="wid70" maxlength="50"/>
			</td>
		</tr>
			
		<tr>
			<td><fmt:message key="cableDropEt4direction.dipp"/></td>
			<td>
				<form:input type ="text" path="dipp" class="wid70" maxlength="20"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cableDropEt4direction.ddfHead"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td>
				<form:input type ="text" path="ddfHead" class="wid70" maxlength="30"/>
				<br/><form:errors path="ddfHead" class="error"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cableDropEt4direction.dipFinish"/></td>
			<td>
				<form:input type ="text" path="dipFinish" class="wid70" maxlength="30"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cableDropEt4direction.ddfFinish"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td>
				<form:input type ="text" path="ddfFinish" class="wid70" maxlength="30"/>
				<br/><form:errors path="ddfFinish" class="error"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cableDropEt4direction.description"/></td>
			<td>
				<form:input type ="text" path="description" class="wid70" maxlength="500"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cableDropEt4direction.status"/></td>
			<td>
				<form:input type ="text" path="status" class="wid70" maxlength="50"/>
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
function xl(){
	var sub = document.getElementById("save");
	sub.focus();
}
</script>