<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>${title}</title>
<content tag="heading">${title}</content> 	

<form:form commandName="filter" method="post" action="list.htm">
	<div class="body-content"></div>
	<table  style="width:100%;" class="form">
		<tr>
			
            <td class="wid9 mwid90"><fmt:message key="hWards.provinceCode"/></td>
			 <td class="wid15">
            	<select name="provinceCode" id="provinceCode" class="wid90">
					<option value=""><fmt:message key="global.All"/></option>
	           		<c:forEach var="item" items="${provinceCodeList}">
						<c:choose>
			                <c:when test="${item.code == provinceCode}">
			                    <option value="${item.code}" selected="selected">${item.province}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.code}">${item.province}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> 
            </td>
            <td class="wid9 mwid80"><fmt:message key="hWards.districtCode"/></td>
			 <td class="wid15">
            	<select name="districtCode" id="districtCode" class="wid90">
					<option value=""><fmt:message key="global.All"/></option>
					<c:forEach var="item" items="${districtCodeList}">
						<c:choose>
			                <c:when test="${item.district == districtCode}">
			                    <option value="${item.district}" selected="selected">${item.districtName}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.district}">${item.districtName}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> 
            </td>
            <td class="wid9 mwid90"><fmt:message key="hWards.villageName"/></td>
			 <td class="wid15">
            	<form:input path="villageNameTK" id="villageNameTK" class="wid90"/>
            </td>
           <td>
				<input type="submit" class="button" value="<fmt:message key="global.form.timkiem"/>"/>
            </td>
        </tr>		
        <tr>
			<td class="ftsize12" align="right" colspan="7">
	            <a href="form.htm"><fmt:message key="button.add"/></a>
	            <a href="upload.htm"><fmt:message key="button.upload"/></a>
	        </td>
	    </tr>
	</table>
</form:form>
      
<div>
	<display:table name="${wardsList}"  class="simple2" id="wards" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	    <display:column class="centerColumnMana" titleKey="global.list.No" style="width:30px;"> <c:out value="${wards_rowNum}"/> </display:column>
		<display:column property="village" titleKey="hWards.village" sortable="true" sortName="VILLAGE"/>  
	    <display:column property="villageName" titleKey="hWards.villageName" sortable="true" sortName="VILLAGE_NAME"/>
	    <display:column property="districtCode" titleKey="hWards.districtCode" sortable="true" sortName="DISTRICT_CODE"/>
	    <display:column property ="provinceCode" titleKey="hWards.provinceCode" sortable="true" sortName="PROVINCE_CODE" />
	    <display:column class="rightColumnMana" property ="ordering" titleKey="hWards.ordering" sortable="true" sortName="ORDERING" />
	    <display:column property="description" titleKey="hWards.description" sortable="true" sortName="DESCRIPTION"/>
   		<display:column titleKey="title.quanLy" media="html" class="centerColumnMana">
			<a href="form.htm?id=${wards.id}"><fmt:message key="global.form.sua"/></a>&nbsp;
  				<a href="delete.htm?id=${wards.id}"
					onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')"><fmt:message key="global.form.xoa"/></a>&nbsp;
  		</display:column>
		
		<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />
		</display:table>
</div>
<script type="text/javascript">
	$("#provinceCode").change(function(){
		
		loadToolbar();
		$.getJSON("${pageContext.request.contextPath}/ajax/districtCodeList.htm",{provinceCode: $(this).val()}, function(j){
			
			var options = '<option value=""><fmt:message key="global.All"/></option>';
			for (var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i].district + '">' + j[i].districtName+ '</option>';
			}
			$("#districtCode").html(options);
		});
	
	});
	
	function loadToolbar(){
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
	};
	
	function focusIt()
	{
	  var mytext = document.getElementById("villageNameTK");
	  mytext.focus();
	}

	onload = focusIt;
</script>
	