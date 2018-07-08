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

<form:form commandName="contactNumber" method="post" action="form.htm">
	<div><form:hidden path="id"/></div> 
    <table class="simple2">
        <tr>
			<td><fmt:message key="contactNumber.area"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td>
				<form:input type ="text" path="area" class="wid70" maxlength="500"/>
				<br/><form:errors path="area" class="error"/>
			</td>
			
			<td><fmt:message key="contactNumber.name"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td>
				<form:input type ="text" path="name" class="wid70" maxlength="200"/>
				<br/><form:errors path="name" class="error"/>
			</td> 
		</tr>
		 
		
		<tr>
			<td><fmt:message key="contactNumber.phoneNumber"/><span style="color:red; float:right;padding-right: 7px;">(*)</span></td>
			<td>
				<form:input type ="text" path="phoneNumber" class="wid70" maxlength="100"/>
				<br/><form:errors path="phoneNumber" class="error"/>
			</td>
			
			<td><fmt:message key="contactNumber.email"/></td>
			<td>
				<form:input type ="text" path="email" class="wid70" maxlength="100"/>
				<br/><form:errors path="email" class="error"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="contactNumber.typeError"/></td>
			<td>
				<form:input type="text" path ="typeError" class="wid70"/>
			</td>
			
			<td><fmt:message key="contactNumber.regency"/></td>
			<td>
			<form:input type="text" path ="regency" class="wid70"/>
				
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="contactNumber.ghiChu"/></td>
			<td>
				<form:textarea type ="text" path="ghiChu" class="wid70" maxlength="900"/>
				<br/><form:errors path="ghiChu" class="error"/>
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
var theme = getTheme();
$("#typeError").jqxInput({ width: '80%', height: 20, minLength: 1, theme: theme });
$("#phoneNumber").jqxInput({ width: '80%', height: 20, minLength: 1, theme: theme });
$("#name").jqxInput({ width: '80%', height: 20, minLength: 1, theme: theme });
$("#area").jqxInput({ width: '80%', height: 20, minLength: 1, theme: theme });
$("#email").jqxInput({ width: '80%', height: 20, minLength: 1, theme: theme });
$("#regency").jqxInput({ width: '80%', height: 20, minLength: 1, theme: theme });
//Create a jqxMultile Input
var renderer = function (itemValue, inputValue) {
    var terms = inputValue.split(/,\s*/);
    // remove the current input
    terms.pop();
    // add the selected item
    terms.push(itemValue);
    // add placeholder to get the comma-and-space at the end
    terms.push("");
    var value = terms.join("");
    return value;
};

//Input projectType
${teamList}
$("#typeError").jqxInput({ placeHolder: "", height: 20, width: '80%', theme: theme,
    source: function (query, response) {
        var item = query.split(/,\s*/).pop();
        // update the search query.
        $("#typeError").jqxInput({ query: item });
        response(teamList);
    },
    renderer: renderer
});
var projectType =  "<c:out value='${typeError}'/>";
if(projectType!="")
	$('#typeError').val(projectType);

//Input Chuc vu

${regencyList}
$("#regency").jqxInput({ placeHolder: "", height: 20, width: '80%', theme: theme,
    source: function (query, response) {
        var item = query.split(/,\s*/).pop();
        // update the search query.
        $("#regency").jqxInput({ query: item });
        response(regencyList);
    },
    renderer: renderer
});
var regency =  "<c:out value='${regency}'/>";
if(regency!="")
	$('#regency').val(regency);	
</script>