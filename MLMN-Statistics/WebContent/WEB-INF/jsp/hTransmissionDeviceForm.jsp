<%@ include file="/commons/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<title>${titleF}</title>
<content tag="heading">${titleF}</content>
<div class="ui-tabs-panel">
<form:form commandName="hTransmissionDevice" method="post"
	action="form.htm">
	<form:hidden path="id" id="id"/>
	<table style="border: 1px solid #ccc;border-collapse: collapse;width: 100%">
		<tr>
			<td style="width: 120px;"><b><fmt:message
						key="hTransmissionDevice.region" /></b> </td>
			<td><form:select path="region" id="region"
					style="width: 165px;height:20px; padding-top: 0px;">
					<c:forEach var="item" items="${regionList}">
						<c:choose>
							<c:when test="${item.region == hTransmissionDevice.region}">
								<option value="${item.region}" selected="selected">${item.region}</option>
							</c:when>
							<c:otherwise>
								<option value="${item.region}">${item.region}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select></td>
		</tr>
		<tr>
			<td style="width: 120px;"><b><fmt:message
						key="hTransmissionDevice.province" /></b> </td>
			<td><form:select path="province"  id="province"
					style="width: 165px;height:20px; padding-top: 0px;">
					<c:forEach var="item" items="${provinceList}">
						<c:choose>
							<c:when test="${item.province == hTransmissionDevice.province}">
								<option value="${item.province}" selected="selected">${item.province}</option>
							</c:when>
							<c:otherwise>
								<option value="${item.province}">${item.province}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select></td>
		</tr>
		<tr>
           <td><b><fmt:message key="hTransmissionDevice.site"/></b> </td>
           <td>
           		<form:input type ="text"  id="site" path="site" rows="3" maxlength=""/>
           		<font color="red"><form:errors path="site" cssClass="error"/></font>
           </td>
       	</tr> 
		<tr>
           <td><b><fmt:message key="hTransmissionDevice.ne"/></b> </td>
           <td>
           		<form:input type ="text"  id="ne" path="ne" rows="3" maxlength=""/>
           		<font color="red"><form:errors path="ne" cssClass="error"/></font>
           </td>
       	</tr> 
		<tr>
			<td style="width: 120px;"><b><fmt:message
						key="hTransmissionDevice.neType" /></b> </td>
			<td>
				<form:input type ="text" id="neType" path="neType" rows="3" value="${neType}" maxlength=""/>
			</td>
		</tr>
		<tr>
           <td><b><fmt:message key="hTransmissionDevice.oam"/></b> </td>
           <td>
           		<form:input type ="text" id="oam" path="oam" rows="3" maxlength=""/>
           		<font color="red"><form:errors path="oam" cssClass="error"/></font>
           </td>
       	</tr> 
		<tr>
           <td><b><fmt:message key="hTransmissionDevice.chucNang"/></b> </td>
           <td>
           		<form:input type ="text" id="chucNang" path="chucNang" rows="3" maxlength=""/>
           		<font color="red"><form:errors path="chucNang" cssClass="error"/></font>
           </td>
       	</tr> 
		<tr>
<%--     notnull       <td><b><fmt:message key="hTransmissionDevice.ghiChu"/></b><font color="red">(*)</font></td> --%>
           <td><b><fmt:message key="hTransmissionDevice.ghiChu"/></b> </td>
           <td>
           		<form:input type ="text" id="ghiChu" path="ghiChu" rows="3" maxlength=""/>
           		<font color="red"><form:errors path="ghiChu" cssClass="error"/></font>
           </td>
       	</tr> 
		<tr>
			<td></td>
			<td >
				<input type="submit" name="form.htm" value="save" />
				<input type="button" name="form.htm" value="cancel" onClick='window.location="list.htm"' />
			</td>
		</tr>
	</table>
</form:form>
</div>

<style>
    .error {
    	color: red;
    }
</style> 

<script type="text/javascript">
$(document).ready(function() {

$("select#region").change(function(){
	 var region = $("select#region").val();
	 var options = "";
	 $.ajax({
		type: "GET",
	  url: "${pageContext.request.contextPath}/transmission-device/ajax/getProvinceByRegion.htm",
	  data:{
	   region: region
	  },
	  dataType: 'json',
	  contentType: 'application/json',
	  mimeType: 'application/json',
	  error: function(){
	   alert('Quá trình tải dữ liệu bị lỗi');
	  },
	  beforeSend: function(){},
	  complete: function(){},
	  success: function(provinceList){
	   for (var i = 0; i < provinceList.length; i++) {
		   options += '<option value="' + provinceList[i].province + '">' + provinceList[i].province  + '</option>'; 
	   }
	   $("#province").html(options);
	  }
	 });
});

});
</script>

<script type="text/javascript">
$(function() {
	var cache = {},
	lastXhr;
	$( "#neType" ).autocomplete({
		minLength: 2,
		source: function( request, response ) {
			var term = request.term;
			if ( term in cache ) {
				response( cache[ term ] );
				return;
			}

			lastXhr = $.getJSON( "${pageContext.request.contextPath}/transmission-device/ajax/searchNeType.htm", request, function( data, status, xhr ) {
				cache[ term ] = data;
				if ( xhr === lastXhr ) {
					response( data );
				}
			});
		}
	});
});	 
</script>