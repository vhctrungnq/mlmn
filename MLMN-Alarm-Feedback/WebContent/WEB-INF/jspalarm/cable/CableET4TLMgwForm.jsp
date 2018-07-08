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

<form:form name="checkform" commandName="cabledropet4tlmgw" method="post" action="form.htm">
	<div><form:hidden path="id"/></div>
    <table class="simple2">
        <tr>
			<td class="wid15 mwid90"><fmt:message key="cabledropet4tlmgw.system"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td>
				<form:input type ="text" path="system" class="wid70" maxlength="40"/>
				&nbsp;<form:errors path="system" class="error"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cabledropet4tlmgw.frSlotPort"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td>
				<form:input type ="text" path="frSlotPort" class="wid70" maxlength="40"/>
				&nbsp;<form:errors path="frSlotPort" class="error"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cabledropet4tlmgw.ddfEt4"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td>
				<form:input type ="text" path="ddfEt4" class="wid70" maxlength="40"/>
				&nbsp;<form:errors path="ddfEt4" class="error"/>
			</td>
		</tr>
			
		<tr>
			<td><fmt:message key="cabledropet4tlmgw.description"/></td>
			<td>
				<form:input type ="text" path="description" class="wid70" maxlength="480"/>
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
function focusIt()
{
	if(document.checkform.system.value==""){
		  var mytext = document.getElementById("system");
		  mytext.focus();
		}
		else if(document.checkform.frSlotPort.value == "")
			{
			var mytext = document.getElementById("frSlotPort");
			  mytext.focus();
			}
		else if(document.checkform.ddfEt4.value == "")
			{
				var mytext = document.getElementById("ddfEt4");
			  	mytext.focus();
			}
		
}

onload = focusIt;
</script>