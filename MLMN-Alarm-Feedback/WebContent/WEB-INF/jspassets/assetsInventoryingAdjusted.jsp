<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title><fmt:message key="sidebar.admin.assetsInventoryingDieuChinh"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.assetsInventoryingDieuChinh"/></content>

<table style="border:0; width:100%; cellspacing:0; cellpadding:0;" class="form">
	<tr>
		<td colspan="2" align="right">
			<input id="dieuChinh" name="dieuChinh" class="button" type="button" value="<fmt:message key="assetsInventorying.button.xacNhanDieuChinh"/>" >
		</td> 
	</tr>
	<tr>
		<td colspan="2">
			<form:form name="displ"> 
				<display:table name="${assetsInventoryingList}" class="simple2" id="item" requestURI="" pagesize="100" sort="external" defaultsort="1" export="true" >
					<display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item_rowNum}"/> </display:column>
					<display:column class="centerColumnMana" style="width:25px;" title="<input type='checkbox' name='selectAllCheck' onClick='javascript:funcSelectAll()' value='Select All' />" media="html">
								<input class="selectall" type="checkbox" id="${item.id}" name="lang" value="${item.id}"/>
					</display:column>
					<display:column property="productCode" titleKey="assetsInventorying.productCode" sortable="true" sortName="PRODUCT_CODE"/>
					<display:column property="productName" titleKey="assetsInventorying.productName" sortable="true" sortName="PRODUCT_NAME"/>
					<display:column property="serialNo" titleKey="assetsInventorying.serialNo" sortable="true" sortName="SERIAL_NO"/>
					<display:column class="centerColumnMana" property="dangSD" titleKey="assetsInventorying.dangSD" />
					<display:column class="centerColumnMana" property="khongSD" titleKey="assetsInventorying.khongSD" />
					<display:column class="centerColumnMana" property="baoHanh" titleKey="assetsInventorying.baoHanh" />
					<display:column class="centerColumnMana" property="ss" titleKey="assetsInventorying.ss" />
					<display:column class="centerColumnMana" property="amount" titleKey="assetsInventorying.amount" />
					<display:column class="centerColumnMana" property="lech" titleKey="assetsInventorying.lech" />
					<display:column property="departmentUse" titleKey="assetsInventorying.departmentUse" sortable="true" sortName="DEPARTMENT_USE"/>
					<display:column property="departmentId" titleKey="assetsInventorying.departmentId" sortable="true" sortName="DEPARTMENT_ID"/>
					<display:column property="users" titleKey="assetsInventorying.users" sortable="true" sortName="USERS"/>
					<display:column class="centerColumnMana" property="createdBy" titleKey="assetsInventorying.createdBy" sortable="true" sortName="CREATED_BY"/>
					<display:column property="description" titleKey="assetsInventorying.description" sortable="true" sortName="DESCRIPTION"/>
					
					<display:setProperty name="export.csv.include_header" value="true" />
					<display:setProperty name="export.excel.include_header" value="true" />
					<display:setProperty name="export.xml.include_header" value="true" />
					<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
					<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
					<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
						
				</display:table>
			</form:form>	
		</td>
	</tr>
</table>

<script type="text/javascript">
function funcSelectAll()
{
   if(document.forms['displ'].selectAllCheck.checked==true)
   {
            for (var a=0; a < document.forms['displ'].lang.length; a++){
                 document.displ.lang[a].checked = true;            
           }
     }
     else
     {
           for (var a=0; a < document.forms['displ'].lang.length; a++){
                  document.displ.lang[a].checked = false;          
           }
     }          

}

$('#dieuChinh').click(function(){
	var val = [];
	var checkedList = "";
	
	$(':checkbox').each(function(){
		if($(this).is(':checked'))
		{
			if($(this).val() != "Select All" && $(this).val() != "on"){   
				checkedList += $(this).val() + "-";
				}
		}
		
	});
	
	if(checkedList != "")
		window.location = '${pageContext.request.contextPath}/assets/kiem-ke-tai-san/dieu-chinh.htm?checkedList=' + checkedList;
		
});
</script>

 <script type="text/javascript">
$(document).ready(function(){
		var listCheck = $('#checkedList').val().split("-");
		
		for(var i = 0; i < listCheck.length; i++){
			var str = listCheck[i];

			document.getElementById(str).checked = true;
		}
	});

</script> 