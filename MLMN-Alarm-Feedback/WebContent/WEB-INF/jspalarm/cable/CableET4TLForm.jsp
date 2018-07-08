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

<form:form commandName="cabledropet4tl" method="post" action="form.htm">
	<table class="simple2">
        <tr>
			<td style="width:20%; min-width:170px;"><form:hidden path="id"/></td>
			<td></td>
		</tr>
        
        <tr>
			<td style="width:20%; min-width:170px;"><fmt:message key="cabledropet4tl.area"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td>
				<form:input type ="text" path="area" class="wid70" maxlength="30"/>
				&nbsp;<form:errors path="area" class="error"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cabledropet4tl.directionCon"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td>
				<form:input type ="text" path="directionCon" class="wid70" maxlength="50"/>
				&nbsp;<form:errors path="directionCon" class="error"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cabledropet4tl.dipL2Name"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td>
				<form:input type ="text" path="dipL2Name" class="wid70" maxlength="100"/>
				&nbsp;<form:errors path="dipL2Name" class="error"/>
			</td>
		</tr>
			
		<tr>
			<td><fmt:message key="cabledropet4tl.labelL2Name"/></td>
			<td>
				<form:input type ="text" path="labelL2Name" class="wid70" maxlength="30"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="cabledropet4tl.locationL2"/></td>
			<td>
				<form:input type ="text" path="locationL2" class="wid70" maxlength="30"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="cabledropet4tl.dipL10Name"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td>
				<form:input type ="text" path="dipL10Name" class="wid70" maxlength="100"/>
				&nbsp;<form:errors path="dipL10Name" class="error"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="cabledropet4tl.labelL10Name"/></td>
			<td>
				<form:input type ="text" path="labelL10Name" class="wid70" maxlength="30"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="cabledropet4tl.locationL10"/></td>
			<td>
				<form:input type ="text" path="locationL10" class="wid70" maxlength="30"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="cabledropet4tl.locationL10New"/></td>
			<td>
				<form:input type ="text" path="locationL10New" class="wid70" maxlength="30"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="cabledropet4tl.locationSgsn"/></td>
			<td>
				<form:input type ="text"  path="locationSgsn" class="wid70" maxlength="10"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="cabledropet4tl.et4L2L10"/></td>
			<td>
				<form:input type ="text" path="et4L2L10" class="wid70" maxlength="30"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="cabledropet4tl.description"/></td>
			<td>
				<form:input type ="text" path="description" class="wid70" maxlength="300"/>
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
<script type="text/javascript">
function keypress(e){
var keypressed = null;
if (window.event)
{
keypressed = window.event.keyCode; //IE
}
else {

keypressed = e.which; //NON-IE, Standard
}
if (keypressed < 48 || keypressed > 57)
{ 
if (keypressed == 8 || keypressed == 127)
{
return;
}
return false;
}
}
</script>