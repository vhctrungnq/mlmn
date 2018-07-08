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

<form:form commandName="cableET4Master" method="post" action="form.htm">
    <table class="simple2">
        <tr>
			<td style="width:20%; min-width:170px;"><form:hidden path="id"/></td>
			<td></td>
		</tr>
        
        <tr>
			<td><fmt:message key="cableET4Master.area"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td>
				<form:input type ="text" path="area" class="wid70" maxlength="20"/>
				<br/><form:errors path="area" class="error"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cableET4Master.exchange"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td>
				<form:input type ="text" path="exchange" class="wid70" maxlength="250"/>
				<br/><form:errors path="exchange" class="error"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cableET4Master.snt"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td>
				<form:input type ="text" path="snt" class="wid70" maxlength="50"/>
				<br/><form:errors path="snt" class="error"/>
			</td>
		</tr>
			
		<tr>
			<td><fmt:message key="cableET4Master.sdip"/></td>
			<td>
				<form:input type ="text" path="sdip" class="wid70" maxlength="20"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="cableET4Master.pos"/></td>
			<td>
				<form:input type ="text" path="pos" class="wid70" maxlength="100"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="cableET4Master.posNew"/></td>
			<td>
				<form:input type ="text" path="posNew" class="wid70" maxlength="100"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="cableET4Master.ddf"/></td>
			<td>
				<form:input type ="text" path="ddf" class="wid70" maxlength="250"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="cableET4Master.ddfNew"/></td>
			<td>
				<form:input type ="text" path="ddfNew" class="wid70" maxlength="250"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="cableET4Master.node"/></td>
			<td>
				<form:input type ="text" path="node" class="wid70" maxlength="250"/>
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