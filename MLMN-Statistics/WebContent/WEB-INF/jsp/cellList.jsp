<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">
    #doublescroll { overflow: auto; overflow-y: hidden; }
    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
</style>
<title><fmt:message key="title.hCell.list"/></title>
<content tag="heading"><fmt:message key="title.hCell.list"/></content>
<div class="body-content"></div>
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
    <tr>
    	<td align="left">
			<form:form commandName="filter" method="post" action="list.htm">
				<table width="100%" border="0" cellspacing="3" cellpadding="0">
					<tr>
						<td class="wid8 mwid70"><fmt:message key="hCell.bscid"/></td>
						<td class="wid15">
							<form:input path="bscid" cssClass="wid90"/>
						</td>
					    <td class="wid8 mwid90"><fmt:message key="hCell.siteid"/></td>
					    <td class="wid15"><form:input path="siteid" cssClass="wid90" /></td>
					    <td class="wid8 mwid80"><fmt:message key="hCell.cellid"/></td>
						<td class="wid15"><form:input path="cellid" cssClass="wid90" /></td>
						
			        </tr>
			        <tr>
			        	<td><fmt:message key="hCell.vendor"/></td>
						<td>
							<select name="vendor" class="wid90" id="vendor">
		           				<option value="">--Tất cả--</option>
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
			        	<td><fmt:message key="hCell.province"/></td>
					    <td>
					    	<select name="province" class="wid90" id="province">
		           				<option value="">--Tất cả--</option>
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
					    <td><fmt:message key="hCell.district"/></td>
					    <td>
					    	<select name="district" class="wid90" id="district">
		           				<option value="">--Tất cả--</option>
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
					    <td><input class="button" type="submit" name="filter"
								value="<fmt:message key="global.form.timkiem"/>" /></td>
			        </tr>		
				</table>
			</form:form>
        </td>
        <td></td>
    </tr>
    <c:if test="${checkRoleManager==true}">	
		<tr>
	    	<td>
			</td>
			<td class="wid10 mwid140" align="right">
				<a href="form.htm"><fmt:message key="global.form.themmoi"/></a>&nbsp;
				<a href="upload.htm"><fmt:message key="global.button.import"/></a>&nbsp;
			</td> 
		</tr>
	</c:if>
</table>
<div id="doublescroll">
		<display:table name="${cellList}" class="simple2" id="item" requestURI="" pagesize="100" partialList="true" sort="external" defaultsort="1" size="${totalSize}">
	    <display:column class="centerColumnMana" titleKey="global.list.STT">
			<c:out value="${item_rowNum + startRecord}" />
		</display:column>
		<display:column property="bscid" titleKey="hCell.bscid" sortable="true" sortName="BSCID"/>
		<display:column class="link" href="${pageContext.request.contextPath}/map/list.htm" paramId="siteid" paramProperty="siteid" titleKey="hCell.siteid" media="html">
			${item.siteid}
		</display:column>
		<display:column property ="cellid" titleKey="hCell.cellid" sortable="true" sortName="CELLID"/>
	    <display:column property="transType" titleKey="hCell.transType" sortable="true" sortName="TRANS_TYPE"/>  
	    <display:column property ="vendor" titleKey="hCell.vendor" sortable="true" sortName="VENDOR"/> 
	    <display:column property ="location" titleKey="hCell.location" sortable="true" sortName="LOCATION"/>
	    <display:column property ="province" titleKey="hCell.province" sortable="true" sortName="PROVINCE"/>
	    <display:column property ="district" titleKey="hCell.district" sortable="true" sortName="DISTRICT"/>    
	    <display:column property ="villageName" titleKey="hCell.villageName" sortable="true" sortName="VILLAGE_NAME"/>
	    <display:column property ="longitude" titleKey="hCell.longitude" sortable="true" sortName="LONGITUDE"/>
	    <display:column property ="latitude" titleKey="hCell.latitude" sortable="true" sortName="LATITUDE"/>
	    <display:column property ="address" titleKey="hCell.address" sortable="true" sortName="ADDRESS"/>
	    <display:column property ="isIbc" titleKey="hCell.isIbc" sortable="true" sortName="IS_IBC"/> 
	     <display:column property ="cellBorder" titleKey="hCell.cellBorder" sortable="true" sortName="CELL_BORDER"/> 
	     <display:column property ="cellBlacklist" titleKey="hCell.cellBlacklist" sortable="true" sortName="CELL_BLACKLIST"/> 
	     <display:column property ="seaCell" titleKey="hCell.seaCell" sortable="true" sortName="SEA_CELL"/> 
	     <display:column property ="specialCell" titleKey="hCell.specialCell" sortable="true" sortName="SPECIAL_CELL"/> 
	     <display:column property ="siteLevel" titleKey="hCell.siteLevel" sortable="true" sortName="SITE_LEVEL"/> 
	     <display:column property ="transConfigs" titleKey="hCell.transConfigs" sortable="true" sortName="TRANS_CONFIGS"/> 
	     <display:column property ="batteryDuration" titleKey="hCell.batteryDuration" sortable="true" sortName="BATTERY_DURATION"/>
	     <display:column property ="tgUcttdhNgay" titleKey="hCell.tgUcttdhNgay" sortable="true" sortName="TG_UCTTDH_NGAY"/> 
	     <display:column property ="tgUcttdhDem" titleKey="hCell.tgUcttdhDem" sortable="true" sortName="TG_UCTTDH_DEM"/>
	     <display:column property ="tgBackupAcquyMoi" titleKey="hCell.tgBackupAcquyMoi" sortable="true" sortName="TG_BACKUP_ACQUY_MOI"/> 
	     <display:column property ="tgBackupAcquyCu" titleKey="hCell.tgBackupAcquyCu" sortable="true" sortName="TG_BACKUP_ACQUY_CU"/>
	     <display:column property ="timeCapnhatMoi" format="{0,date,dd/MM/yyyy}" titleKey="hCell.timeCapnhatMoi" style="max-width:110px;" sortable="true" sortName="TIME_CAPNHAT_MOI"/> 
	     <display:column property ="timeCapnhatCu" format="{0,date,dd/MM/yyyy}" titleKey="hCell.timeCapnhatCu" style="max-width:110px;" sortable="true" sortName="TIME_CAPNHAT_CU"/>
	     <display:column property ="mcc" titleKey="Mcc" sortable="true" sortName="MCC"/>
		 <display:column property ="mnc" titleKey="Mnc" sortable="true" sortName="MNC"/>
		 <display:column property ="addressEng" titleKey="Address Eng" sortable="true" sortName="ADDRESS_ENG"/>
		 <display:column property ="bandwidthStr" titleKey="hcell.banwidthStr" sortable="true" sortName="BANDWIDTH_STR"/>
		 <display:column property ="siteDistance" titleKey="hcell.siteDistance" sortable="true" sortName="SITE_DISTANCE"/>
		 <display:column property ="subscribersInfo" titleKey="hcell.subscribersInfo" sortable="true" sortName="SUBSCRIBERS_INFO"/>
		 <display:column property ="khoangCachTram" titleKey="hcell.khoangCachTram" sortable="true" sortName="KHOANG_CACH_TRAM"/>
		 <display:column property ="azimuth" titleKey="Azimuth" sortable="true" sortName="AZIMUTH"/>
		 <display:column property ="launchDate" format="{0,date,dd/MM/yyyy}" titleKey="hCell.launchDate" style="max-width:110px;" sortable="true" sortName="LAUNCH_DATE"/> 
	   	<c:if test="${checkRoleManager==true}">	
			<display:column class="centerColumnMana" titleKey="global.management" media="html">
		    	<a href="form.htm?bscid=${item.bscid}&cellid=${item.cellid}"><fmt:message key="global.form.sua"/></a>&nbsp;
		    	<a href="delete.htm?bscid=${item.bscid}&cellid=${item.cellid}"
		    	   onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')" ><fmt:message key="global.form.xoa"/></a>&nbsp;
		    </display:column>
		</c:if>
	    <%-- <display:setProperty name="export.csv.include_header" value="true"/>
    	<display:setProperty name="export.excel.include_header" value="true"/>
    	<display:setProperty name="export.xml.include_header" value="true"/>
   		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml"/>
   		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv"/>
    	<display:setProperty name="export.excel.filename" value="${exportFileName}.xls"/> --%>
	</display:table>
	<div class="exportlinks">Export options:
		<a href="${pageContext.request.contextPath}/network/cell/exportExcel.htm?bscid=${bscid}&siteid=${siteid}&cellid=${cellid}&vendor=${vendor}&province=${province}&district=${district}"><span class="export excel">Excel </span></a>
	</div>
</div>
<script type="text/javascript">
function focusIt()
{
  var mytext = document.getElementById("bscid");
  mytext.focus();
}
onload = focusIt;
</script>

<script type="text/javascript">
$(function() {
	var cacheBsc = {}, lastXhrBsc, cacheCell = {}, lastXhrCell, cacheSite = {}, lastXhrSite;
	$( "#bscid" ).autocomplete({
		minLength: 2,
		source: function( request, response ) {
			var term = request.term;
			if ( term in cacheBsc ) {
				response( cacheBsc[ term ] );
				return;
			}

			lastXhrBsc = $.getJSON( "${pageContext.request.contextPath}/ajax/getBsc.htm", request, function( data, status, xhr ) {
				cacheBsc[ term ] = data;
				if ( xhr === lastXhrBsc ) {
					response( data );
				}
			});
		}
	});
	$( "#cellid" ).autocomplete({
		minLength: 3,
		source: function( request, response ) {
			var term = request.term;
			if ( term in cacheCell ) {
				response( cacheCell[ term ] );
				return;
			}

			lastXhrCell = $.getJSON( "${pageContext.request.contextPath}/ajax/getCell.htm", request, function( data, status, xhr ) {
				cacheCell[ term ] = data;
				if ( xhr === lastXhrCell ) {
					response( data );
				}
			});
		}
	});
	$( "#siteid" ).autocomplete({
		minLength: 3,
		source: function( request, response ) {
			var term = request.term;
			if ( term in cacheSite ) {
				response( cacheSite[ term ] );
				return;
			}
			lastXhrSite = $.getJSON( "${pageContext.request.contextPath}/ajax/getSite.htm", request, function( data, status, xhr ) {
				cacheSite[ term ] = data;
				if ( xhr === lastXhrSite ) {
					response( data );
				}
			});
		}
	});
});	
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
		  options += '<option value="">--Tất cả--</option>';
		  for (var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i].district + '">' + j[i].district + '</option>';
			}
		$("#district").html(options);
		$('#district option:first').attr('selected', 'selected');
	});
	
});
</script>
<script type="text/javascript">
    function DoubleScroll(element) {
        var scrollbar= document.createElement('div');
        scrollbar.appendChild(document.createElement('div'));
        scrollbar.style.overflow= 'auto';
        scrollbar.style.overflowY= 'hidden';
        scrollbar.firstChild.style.width= element.scrollWidth+'px';
        scrollbar.firstChild.style.paddingTop= '1px';
        scrollbar.firstChild.appendChild(document.createTextNode('\xA0'));
        scrollbar.onscroll= function() {
            element.scrollLeft= scrollbar.scrollLeft;
        };
        element.onscroll= function() {
            scrollbar.scrollLeft= element.scrollLeft;
        };
        element.parentNode.insertBefore(scrollbar, element);
    }

    DoubleScroll(document.getElementById('doublescroll'));
</script>