<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- <title>Cáp truyền dẫn</title>
<content tag="heading">Cáp truyền dẫn</content>--%>
<title>${titleF}</title>
<content tag="heading">${titleF}</content> 
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

<div class="fl wid50" >
<form:form method="post" commandName="cableE1" action="form.htm?typeC=${typeC}">
	<table style="padding-right: 15px;">
		<tr>
			<td style="width:20%; min-width:170px;"><form:hidden path="id"/></td>
			<td></td>
		</tr>
		
		<tr>
			<td><fmt:message key="cableE1.directionId"/><span class="note">(*)</span></td>
			<td>
				<form:input type ="text" path="directionId" class="wid100" maxlength="250"/>
				<br/><form:errors path="directionId" class="error"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cableE1.rp"/></td>
			<td>
				<form:input type ="text" path="rp" class="wid100 detail" maxlength="20"/>
				<br/><form:errors path="rp" class="error"/>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cableE1.em"/></td>
			<td>
				<form:input type ="text" path="em" class="wid100 detail" maxlength="20"/>
				
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cableE1.dev"/><span class="note">(*)</span></td>
			<td>
				<form:input type ="text" path="dev" class="wid100 detail" maxlength="250"/>
				<br/><form:errors path="dev" class="error"/>
			</td>
		</tr>
		
		<tr>
			<td>
				   	<fmt:message key="cableE1.snt"/>
		    </td>
			<td>
				<form:input type ="text" path="snt" class="wid100 detail" maxlength="50"/>
				
			</td>
		</tr>
		
		<tr>
			<td>
			     	<fmt:message key="cableE1.dip"/>		
			</td>
			<td>
				<form:input type ="text" path="dip" class="wid100 detail" maxlength="50"/>
				
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="cableE1.sntinl"/>
			</td>
			<td>
				<form:input type ="text" path="sntinl" class="wid100 detail" maxlength="20"/>
				<br/><form:errors path="sntinl" class="error"/>
			</td>
		</tr>
		<tr>
			<td>
						<fmt:message key="cableE1.ddfOut"/>
			</td>
			<td>
				<form:input type ="text" path="ddfOut" class="wid100 detail" maxlength="250"/>
				
			</td>
		</tr>
		<tr >
			<td><fmt:message key="cableE1.state"/></td>
			<td>
				<form:input type ="text" path="state" class="wid100 detail" maxlength="250"/>
				
			</td>
		</tr>
		<tr>
			<td><fmt:message key="cableE1.node"/></td>
			<td>
				<form:input type ="text" path="node" class="wid100 detail" maxlength="250"/>
				
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="cableE1.planeNext"/></td>
			<td>
				<form:input type ="text" path="planeNext" class="wid100 detail" maxlength="500"/>
				
			</td>
		</tr>
		<c:if test="${typeC == 'E1_BSG'}">
			<tr>
				<td><fmt:message key="cableE1.directionTransmission"/></td>
				<td>
					<form:input type ="text" path="directionTransmission" class="wid100 detail" maxlength="250"/>
					
				</td>
			</tr>
		</c:if>
		<tr>
			<td><fmt:message key="cableTransmission.description"/></td>
			<td>
				<form:textarea path="description" style="height: 50px" class="wid100" maxlength="900"/>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<input type="submit" class="button" id="save"  value="<fmt:message key="global.form.luulai"/>" />
               	<input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="<%=request.getContextPath() %>/alarm/cableE1/list.htm?typeC=${typeC}"'>
			</td>
		</tr>
	</table>
</form:form>
</div>

<script type="text/javascript">
$("#directionId").autocomplete({
	source: '${pageContext.request.contextPath}/alarm/cableE1/ajax/get-cableE1-inf-search.htm?focus=directionId&typeC=${typeC}',
});

$("#dev").autocomplete({
	source: '${pageContext.request.contextPath}/alarm/cableE1/ajax/get-cableE1-inf-search.htm?focus=dev&typeC=${typeC}',
});

$("#snt").autocomplete({
	source: '${pageContext.request.contextPath}/alarm/cableE1/ajax/get-cableE1-inf-search.htm?focus=snt&typeC=${typeC}',
});

$("#dip").autocomplete({
	source: '${pageContext.request.contextPath}/alarm/cableE1/ajax/get-cableE1-inf-search.htm?focus=dip&typeC=${typeC}',
});

$("#ddfOut").autocomplete({
	source: '${pageContext.request.contextPath}/alarm/cableE1/ajax/get-cableE1-inf-search.htm?focus=ddfOut&typeC=${typeC}',
});

$("#state").autocomplete({
	source: '${pageContext.request.contextPath}/alarm/cableE1/ajax/get-cableE1-inf-search.htm?focus=state&typeC=${typeC}',
});

$("#planeNext").autocomplete({
	source: '${pageContext.request.contextPath}/alarm/cableE1/ajax/get-cableE1-inf-search.htm?focus=planeNext',
});
</script>