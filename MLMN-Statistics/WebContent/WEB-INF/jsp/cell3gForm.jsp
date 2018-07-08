<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:choose>
  <c:when test="${HCell3gAddEdit == 'N'}">
      <title><fmt:message key="title.hCell3g.formAdd"/></title>
	  <content tag="heading"><fmt:message key="title.hCell3g.formAdd"/></content>
  </c:when>
  <c:when test="${HCell3gAddEdit == 'Y'}">
      <title><fmt:message key="title.hCell3g.formEdit"/></title>
	  <content tag="heading"><fmt:message key="title.hCell3g.formEdit"/></content>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>

<form:form method="post" name="checkform" commandName="cell3g" action="form.htm">
	<form:hidden path="launchDate" />
	<form:input path="id" type="hidden" />
	<div class="body-content"></div>
    <table class="simple2">
    	<tr>
           <td class="wid15 mwid110"><fmt:message key="hCell3g.bscid"/>&nbsp;<font color = "red">(*)</font></td>
           <td class="wid35">
                <form:select path="bscid" cssClass="wid50">
   					<c:forEach var="items" items="${bscList}">
		              <c:choose>
		                <c:when test="${items.bscid == bscidCBB}">
		                    <option value="${items.bscid}" selected="selected">${items.bscid}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.bscid}">${items.bscid}</option>
		                </c:otherwise>
		              </c:choose>
				    </c:forEach>
           		</form:select> 		 
           </td>
            <td><fmt:message key="hCell3g.vendor"/></td>
            <td>
            	<select name="vendor" class="wid50" id="vendor">
       				<c:forEach var="items" items="${vendorList}">
		              <c:choose>
		                <c:when test="${items.value == vendorCBB}">
		                    <option value="${items.value}" selected="selected">${items.value}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.value}">${items.value}</option>
		                </c:otherwise>
		              </c:choose>
				    </c:forEach>
      			</select>
			</td>
        </tr>
         <tr>
            <td><fmt:message key="hCell3g.siteid"/></td>
            <td><form:input path="siteid" maxlength="60" cssClass="wid50"/></td>
            <td><fmt:message key="hCell3g.sitename"/></td>
            <td><form:input path="siteName" maxlength="80" cssClass="wid50"/></td>
        </tr>
        <tr>
        	<td class="wid15 mwid110"><fmt:message key="hCell3g.cellid"/>&nbsp;<font color = "red">(*)</font></td>
      	   <td ><form:input path="cellid" maxlength="60" cssClass="wid50"/>&nbsp;<form:errors path="cellid" cssClass="error"/></td>       
     
            <td><fmt:message key="hCell3g.cellname"/></td>
            <td>            	
            	<form:input path="cellName" maxlength="80" cssClass="wid50"/>  
            </td>
           
        </tr>
       
        <tr>
            <td><fmt:message key="hCell3g.type"/></td>
            <td><form:input path="type" maxlength="15" cssClass="wid50"/></td>
             <td><fmt:message key="hCell3g.transType"/></td>
            <td><form:input path="transType" maxlength="40" cssClass="wid50"/><br/></td>
        </tr>
        <tr>
        	<td><fmt:message key="hCell.vendorTrans"/></td>
           	<td><form:input  type ="text" path="vendorTrans" maxlength="40" class="wid50"/></td>
           	<td><fmt:message key="hCell.bandwidth"/></td>
           	<td><form:input  type ="text" path="bandwidth" maxlength="8" class="wid30"/>&nbsp;<form:errors path="bandwidth" cssClass="error"/></td>
        </tr>
        <tr>
        	<td><fmt:message key="hCell.nextHop"/></td>
           	<td><form:input  type ="text" path="nextHop" maxlength="40" class="wid50"/></td>
           	<td><fmt:message key="hCell.subscriber"/></td>
           	<td><form:input  type ="text" path="subscriber" maxlength="8" class="wid30"/>&nbsp;<form:errors path="subscriber" cssClass="error"/></td>
        </tr>
         <tr>
           
            <td><fmt:message key="hCell3g.region"/></td>
			<td>
				<select name="region" class="wid50" id="region">
       				<c:forEach var="items" items="${regionList}">
		              <c:choose>
		                <c:when test="${items.region == regionCBB}">
		                    <option value="${items.region}" selected="selected">${items.region}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.region}">${items.region}</option>
		                </c:otherwise>
		              </c:choose>
				    </c:forEach>
      			</select>
			</td>
			<td><fmt:message key="hCell3g.area"/></td>
           	<td><form:input  type ="text" path="area" maxlength="40" class="wid50"/></td>
        </tr>
        <tr>
            <td><fmt:message key="hCell3g.province"/></td>
            <td>
            	<select name="province" class="wid50" id="province">
       				<c:forEach var="items" items="${provinceList}">
		              <c:choose>
		                <c:when test="${items.province == provinceCBB}">
		                    <option value="${items.province}" selected="selected">${items.province}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.province}">${items.province}</option>
		                </c:otherwise>
		              </c:choose>
				    </c:forEach>
       			</select>
            </td>
            <td><fmt:message key="hCell3g.district"/></td>
            <td>
            	<select name="district" class="wid50" id="district"> 				
       				<c:forEach var="items" items="${districtList}">
		              <c:choose>
		                <c:when test="${items.district == districtCBB}">
		                    <option value="${items.district}" selected="selected">${items.district}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.district}">${items.district}</option>
		                </c:otherwise>
		              </c:choose>
				    </c:forEach>
       			</select>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="hCell3g.villageName"/></td>
            <td><form:input  type ="text" path="villageName" maxlength="80" class="wid50"/></td>
            <td><fmt:message key="hCell3g.address"/></td>
            <td><form:input  type ="text" path="address" maxlength="80" class="wid50"/></td>
        </tr>
        <tr>
            <td><fmt:message key="hCell3g.longitude"/></td>
            <td><form:input  type ="text" path="longitude" maxlength="18" class="wid50"/>&nbsp;<form:errors path="longitude" cssClass="error"/></td>
            <td><fmt:message key="hCell3g.latitude"/></td>
            <td><form:input  type ="text" path="latitude" maxlength="18" class="wid50"/>&nbsp;<form:errors path="latitude" cssClass="error"/></td>
 
        </tr>
        <tr>
        	<td><fmt:message key="hCell3g.cid"/></td>
           	<td><form:input  type ="text" path="cid" maxlength="8" class="wid30"/>&nbsp;<form:errors path="cid" cssClass="error"/></td>
           	<td><fmt:message key="hCell3g.lac"/></td>
           	<td><form:input  type ="text" path="lac" maxlength="8" class="wid30"/>&nbsp;<form:errors path="lac" cssClass="error"/></td>
        </tr>
        
       <tr>
            <td><fmt:message key="hCell.cellBorder"/></td>
            
            <td>
	            <form:select path="cellBorder" class="wid30">
	            <option value="">--Choose--</option>
		   				<c:forEach var="items" items="${yesNoList}">
			              <c:choose>
			                <c:when test="${items.value == cellBorder}">
			                    <option value="${items.value}" selected="selected">${items.value}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${items.value}">${items.value}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
					</form:select>
            <%-- <form:input  type ="text" path="cellBorder" maxlength="80" class="wid50"/> --%>
            </td>
            <td><fmt:message key="hCell.cellBlacklist"/></td>
            <td>
            	<form:select path="cellBlacklist" class="wid30">
            	<option value="">--Choose--</option>
	   				<c:forEach var="items" items="${yesNoList}">
		              <c:choose>
		                <c:when test="${items.value == cellBlacklistCBB}">
		                    <option value="${items.value}" selected="selected">${items.value}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.value}">${items.value}</option>
		                </c:otherwise>
		              </c:choose>
				    </c:forEach>
				</form:select>
            </td>
        </tr>
        <tr>
        	<td><fmt:message key="hCell.seaCell"/></td>
            <td>
            	<form:select path="seaCell" class="wid30">
            		<option value="">--Choose--</option>
	   				<c:forEach var="items" items="${yesNoList}">
		              <c:choose>
		                <c:when test="${items.value == seaCell}">
		                    <option value="${items.value}" selected="selected">${items.value}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.value}">${items.value}</option>
		                </c:otherwise>
		              </c:choose>
				    </c:forEach>
				</form:select>
            </td>
            <td><fmt:message key="hCell.specialCell"/></td>
            <td>
            	<form:select path="specialCell" class="wid30">
            		<option value="">--Choose--</option>
	   				<c:forEach var="items" items="${yesNoList}">
		              <c:choose>
		                <c:when test="${items.value == specialCell}">
		                    <option value="${items.value}" selected="selected">${items.value}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.value}">${items.value}</option>
		                </c:otherwise>
		              </c:choose>
				    </c:forEach>
				</form:select>
            </td>
        </tr>
        <tr>
        	
            <td><fmt:message key="hCell.isIbc"/></td>
            <td>
            	<form:select path="isIbc" class="wid30">
	   				<c:forEach var="items" items="${yesNoList}">
		              <c:choose>
		                <c:when test="${items.value == isIbcCBB}">
		                    <option value="${items.value}" selected="selected">${items.value}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.value}">${items.value}</option>
		                </c:otherwise>
		              </c:choose>
				    </c:forEach>
				</form:select>
           	</td>
           	<td><fmt:message key="hCell.siteLevel"/></td>  
            <td>
	         	<form:input  type ="text" path="siteLevel" maxlength="80" class="wid50"/>&nbsp;<form:errors path="siteLevel" cssClass="error"/>
            </td>
        </tr>
        <tr>
        	<td><fmt:message key="hCell.transConfigs"/></td>  
            <td>
	         	<form:input  type ="text" path="transConfigs" maxlength="80" class="wid50"/>
            </td>
 			<td><fmt:message key="hCell.delayStandar"/></td>  
            <td>
	         	<form:input  type ="text" path="delayStandar" maxlength="80" class="wid50"/>&nbsp;<form:errors path="delayStandar" cssClass="error"/>
            </td>
        </tr>
        <tr>
        	<td><fmt:message key="hCell.batteryDuration"/></td>
        	<td><form:input  type ="text" path="batteryDuration" maxlength="4" class="wid50"/>&nbsp;<form:errors path="batteryDuration" cssClass="error"/></td>
           	<td><fmt:message key="hCell.phone"/></td>
            <td><form:input type="text" path="phone" maxlength="11" class="wid50"/>&nbsp;<form:errors path="phone" cssClass="error"/></td>
        </tr>
        <tr>
        	<td><fmt:message key="hCell3g.tgUcttdhNgay"/></td>
        	<td><form:input  type ="text" path="tgUcttdhNgay" maxlength="4" class="wid50"/>&nbsp;<form:errors path="tgUcttdhNgay" cssClass="error"/></td>
           	<td><fmt:message key="hCell3g.tgUcttdhDem"/></td>
            <td><form:input  type ="text" path="tgUcttdhDem" maxlength="4" class="wid50"/>&nbsp;<form:errors path="tgUcttdhDem" cssClass="error"/></td>
        </tr>
        <tr>
        	<td><fmt:message key="hCell.tgBackupAcquyMoi"/></td>
        	<td><form:input  type ="text" path="tgBackupAcquyMoi" maxlength="4" class="wid50"/>&nbsp;<form:errors path="tgBackupAcquyMoi" cssClass="error"/></td>
           	<td><fmt:message key="hCell.tgBackupAcquyCu"/></td>
            <td><form:input  type ="text" path="tgBackupAcquyCu" maxlength="4" class="wid50"/>&nbsp;<form:errors path="tgBackupAcquyCu" cssClass="error"/></td>
        </tr>
        <tr>
        	<td><fmt:message key="hCell.timeCapnhatMoi"/></td>
        	<td><input  type ="text" id="timeCapnhatMoi" name="timeCapnhatMoi" value="${timeCapnhatMoi}" maxlength="10" class="wid50"/>&nbsp;</td>
           	<td><fmt:message key="hCell.timeCapnhatCu"/></td>
            <td><input  type ="text" id="timeCapnhatCu" name="timeCapnhatCu" value="${timeCapnhatCu}" maxlength="10" class="wid50"/>&nbsp;</td>
        </tr>
        <tr>
        	<td><fmt:message key="hCell.doLechTgBackupAcquy"/></td>
        	<td><form:input  type ="text" path="doLechTgBackupAcquy" maxlength="4" class="wid50"/>&nbsp;<form:errors path="doLechTgBackupAcquy" cssClass="error"/></td>
           	<td><fmt:message key="hCell.targetUCTT"/></td>  
            <td><form:input  type ="text" path="targetUctt" maxlength="80" class="wid50"/>&nbsp;<form:errors path="targetUctt" cssClass="error"/> </td>
        </tr>
        <tr >
         <tr>
        	<td><fmt:message key="hcell.siteDistance"/></td>
        	<td><form:input  type ="text" path="siteDistance" maxlength="10" class="wid50"/>&nbsp;<form:errors path="siteDistance" cssClass="error"/></td>
           	<td><fmt:message key="hcell.khoangCachTram"/></td>  
            <td><form:input  type ="text" path="khoangCachTram" class="wid50"/>&nbsp;<form:errors path="khoangCachTram" cssClass="error"/> </td>
        </tr>
         <tr>
        	<td><fmt:message key="hcell.subscribersInfo"/></td>
        	<td><form:input  type ="text" path="subscribersInfo" maxlength="200" class="wid50"/>
           	<td><fmt:message key="hcell.bandwidthStr"/></td>  
            <td><form:input  type ="text" path="bandwidthStr"  maxlength="" class="wid50"/> </td>
        </tr>
        <tr>
        	<td><fmt:message key="hcell.createDate"/></td>
        	<td><form:input  type ="text" path="createDate" maxlength="10" class="wid50"/>&nbsp;<form:errors path="createDate" cssClass="error"/></td>
        	<td><fmt:message key="hCell.description"/></td>
            <td><form:input  type ="text" path="description" maxlength="200" class="wid50"/></td>
        </tr>
        <tr>
            <td></td>
            <td colspan="3">
                <input class="button" type="submit" name="save" value="<fmt:message key="global.form.luulai"/>" />
              	<input class="button" type="button" value="<fmt:message key="global.form.huybo"/>" onClick='window.location="list.htm"'>
            </td>
        </tr>
    </table>
</form:form>
<script type="text/javascript">
	$(function() {
		$( "#timeCapnhatMoi" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
		$( "#timeCapnhatCu" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
		$( "#createDate").datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
		$( "#timeCapnhatCu" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
	});
</script>
<script type="text/javascript">
function focusIt()
{
	var longitudeError = '<c:out value="${longitudeError}"/>';
	var latitudeError = '<c:out value="${latitudeError}"/>';
	var cidError = '<c:out value="${cidError}"/>';
	var lacError = '<c:out value="${lacError}"/>';
	var bandwidthError = '<c:out value="${bandwidthError}"/>';
	var subscriberError = '<c:out value="${subscriberError}"/>';
	
	if(document.checkform.cellid.value==""){
		  var mytext = document.getElementById("cellid");
		  mytext.focus();
	}
	else if(longitudeError !="")
	{
			var mytext = document.getElementById("longitude");
			  mytext.focus();
	}
	else if(latitudeError !="")
	{
			var mytext = document.getElementById("latitude");
			  mytext.focus();
	}
	else if(cidError !="")
	{
			var mytext = document.getElementById("cid");
			  mytext.focus();
	}
	else if(lacError !="")
	{
			var mytext = document.getElementById("lac");
			  mytext.focus();
	}
	else if(bandwidthError !="")
	{
			var mytext = document.getElementById("bandwidth");
			  mytext.focus();
	}
	else if(subscriberError !="")
	{
			var mytext = document.getElementById("subscriber");
			  mytext.focus();
	}
}
onload = focusIt;
</script>
<script type="text/javascript">
$('#province').change(function(){
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
	
	$.getJSON("${pageContext.request.contextPath}/network/cell/loadQuanHuyen.htm", {province: $('#province').val()}, function(j){
		 var options = '';
		  for (var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i].district + '">' + j[i].district + '</option>';
			}
		$("#district").html(options);
		$('#district option:first').attr('selected', 'selected');
	});
});
</script>
