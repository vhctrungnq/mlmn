<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:choose>
  <c:when test="${HCellAddEdit == 'N'}">
      <title><fmt:message key="title.hCell.formAdd"/></title>
	  <content tag="heading"><fmt:message key="title.hCell.formAdd"/></content>
  </c:when>
  <c:when test="${HCellAddEdit == 'Y'}">
      <title><fmt:message key="title.hCell.formEdit"/></title>
	  <content tag="heading"><fmt:message key="title.hCell.formEdit"/></content>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose>

<form:form method="post" name="checkform" commandName="cell" action="form.htm">
	<form:hidden path="launchDate" />
	<form:input path="id" type="hidden" />
	<div class="body-content"></div>
    <table class="simple2">
    	<tr>
           <td class="wid15 mwid110"><fmt:message key="hCell.bscid"/>&nbsp;<font color = "red">(*)</font></td>
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
           <td><fmt:message key="hCell.vendor"/></td>
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
            <td><fmt:message key="hCell.siteid"/></td>
            <td><form:input path="siteid" maxlength="60" cssClass="wid50"/></td>
            <td><fmt:message key="hCell.sitename"/></td>
            <td><form:input path="sitename" maxlength="80" cssClass="wid50"/></td>
        </tr>
        <tr>
        	<td class="wid15 mwid110"><fmt:message key="hCell.cellid"/>&nbsp;<font color = "red">(*)</font></td>
      	   <td ><form:input path="cellid" maxlength="60" cssClass="wid50"/>&nbsp;<form:errors path="cellid" cssClass="error"/></td>       
      
            <td><fmt:message key="hCell.cellname"/></td>
            <td>            	
            	<form:input path="cellname" maxlength="80" cssClass="wid50"/>  
            </td>
            
        </tr>
       
        <tr>
            <td><fmt:message key="hCell.cgi"/></td>
            <td><form:input path="cgi" maxlength="20" cssClass="wid50"/><br/></td>
            <td><fmt:message key="hCell.cid"/></td>
           	<td><form:input  type ="text" path="cid" maxlength="8" class="wid50"/>&nbsp;<form:errors path="cid" cssClass="error"/></td>
           	
        </tr>
        <tr>
        	<td><fmt:message key="hCell.lac"/></td>
           	<td><form:input  type ="text" path="lac" maxlength="8" class="wid50"/>&nbsp;<form:errors path="lac" cssClass="error"/></td>
      		<td><fmt:message key="hCell.type"/></td>
            <td><form:input path="type" maxlength="15" cssClass="wid50"/><br/></td>
        </tr>
        <tr>
         	<td><fmt:message key="hCell.transType"/></td>
            <td><form:input path="transType" maxlength="40" cssClass="wid50"/><br/></td>
        	<td><fmt:message key="hCell.vendorTrans"/></td>
           	<td><form:input  type ="text" path="vendorTrans" maxlength="40" class="wid50"/></td>
        </tr>
        <tr>
        	<td><fmt:message key="hCell.nextHop"/></td>
           	<td><form:input  type ="text" path="nextHop" maxlength="40" class="wid50"/></td>
           	<td><fmt:message key="hCell.bandwidth"/></td>
           	<td><form:input  type ="text" path="bandwidth" maxlength="8" class="wid50"/>&nbsp;<form:errors path="bandwidth" cssClass="error"/></td>
        </tr>
         <tr>
           <td><fmt:message key="hCell.region"/></td>
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
            <td><fmt:message key="hCell.location"/></td>
            <td>
            	<select name="location" class="wid50" id="location">
       				<c:forEach var="items" items="${locationList}">
		              <c:choose>
		                <c:when test="${items.location == locationCBB}">
		                    <option value="${items.location}" selected="selected">${items.location}</option>
		                </c:when>
		                <c:otherwise>
		                    <option value="${items.location}">${items.location}</option>
		                </c:otherwise>
		              </c:choose>
				    </c:forEach>
      			</select>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="hCell.area"/></td>
           	<td><form:input  type ="text" path="area" maxlength="40" class="wid50"/></td>
       
            <td><fmt:message key="hCell.province"/></td>
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
        </tr>
        <tr>
            <td><fmt:message key="hCell.district"/></td>
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
            <td><fmt:message key="hCell.subscriber"/></td>
           	<td><form:input  type ="text" path="subscriber" maxlength="8" class="wid50"/>&nbsp;<form:errors path="subscriber" cssClass="error"/></td>
        </tr>
        <tr>
            <td><fmt:message key="hCell.villageName"/></td>
            <td><form:input  type ="text" path="villageName" maxlength="80" class="wid50"/></td>
            <td><fmt:message key="hCell.address"/></td>
            <td><form:input  type ="text" path="address" maxlength="80" class="wid90"/></td>
        </tr>
        <tr>
            <td><fmt:message key="hCell.longitude"/></td>
            <td><form:input  type ="text" path="longitude" maxlength="18" class="wid50"/>&nbsp;<form:errors path="longitude" cssClass="error"/></td>
            <td><fmt:message key="hCell.latitude"/></td>
            <td><form:input  type ="text" path="latitude" maxlength="18" class="wid50"/>&nbsp;<form:errors path="latitude" cssClass="error"/></td>
 
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
            <td><form:input  type ="text" path="phone" maxlength="500" class="wid50"/>&nbsp;<form:errors path="phone" cssClass="error"/></td>
        </tr>
        <tr>
        	<td><fmt:message key="hCell.tgUcttdhNgay"/></td>
        	<td><form:input  type ="text" path="tgUcttdhNgay" maxlength="4" class="wid50"/>&nbsp;<form:errors path="tgUcttdhNgay" cssClass="error"/></td>
           	<td><fmt:message key="hCell.tgUcttdhDem"/></td>
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
            <td><form:input  type ="text" path="targetUCTT" maxlength="80" class="wid50"/>&nbsp;<form:errors path="targetUCTT" cssClass="error"/> </td>
        </tr>
        
        <tr>
        	<td><fmt:message key="hCell.mcc"/></td>
        	<td><form:input  type ="text" path="mcc" maxlength="20" class="wid50"/>&nbsp;<form:errors path="mcc" cssClass="error"/></td>
           	<td><fmt:message key="hCell.mnc"/></td>  
            <td><form:input  type ="text" path="mnc" maxlength="20" class="wid50"/>&nbsp;<form:errors path="mnc" cssClass="error"/> </td>
        </tr>
        
        <tr>
        	<td><fmt:message key="hCell.addressEng"/></td>
        	<td><form:input  type ="text" path="addressEng" maxlength="200" class="wid50"/>&nbsp;<form:errors path="addressEng" cssClass="error"/></td>
           	<td><fmt:message key="hCell.azimuth"/></td>  
            <td><form:input  type ="text" path="azimuth" class="wid50"/>&nbsp;<form:errors path="azimuth" cssClass="error"/> </td>
        </tr>
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
            <td><form:input  type ="text" path="bandwidthStr"  maxlength="50"  class="wid50"/> </td>
        </tr>
        <tr >
        	<td><fmt:message key="hCell.description"/></td>
            <td colspan="3"><form:input  type ="text" path="description" maxlength="200" class="wid96"/></td>
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
