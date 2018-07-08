<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>Báo cáo Gprs-Cs District</title>
<content tag="heading">GPRS-CS DISTRICT DAILY REPORT</content>
<ul class="ui-tabs-nav">
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/district-gprs-cs/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/district-gprs-cs/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/district-gprs-cs/mn/list.htm"><span>Báo cáo tháng</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/district-gprs-cs/qr/list.htm"><span>Báo cáo quý</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/district-gprs-cs/yr/list.htm"><span>Báo cáo năm</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm" name="frmSample" onSubmit="return ValidateForm()">
					Trung tâm 
			  		<select name="region" id="region" onchange="xl()">
			  			<option value="">Tất cả</option>
			              <c:forEach var="items" items="${regionList}">
				              <c:choose>
				                <c:when test="${items.region == region}">
				                    <option value="${items.region}" selected="selected">${items.region}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.region}">${items.region}</option>
				                </c:otherwise>
				              </c:choose>
						    </c:forEach>
			        </select>
			        PROVINCE
			        <select name="province" onchange="xl()">
			        	<option value="">Tất cả</option>
				        <c:forEach var="prv" items="${provinceList}">
			              <c:choose>
			                <c:when test="${prv.province == province}">
			                    <option value="${prv.province}" selected="selected">${prv.province}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${prv.province}">${prv.province}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
				    </select>
				    &nbsp;&nbsp;DISTRICT <input value="${district}" name="district" id="district" size="10" maxlength="50">
	                &nbsp;&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
		<div  id="doublescroll">
<display:table style = "width: 150%" name="${vRpDyDistrictGprsCs}" id="vRpDyDistrictGprsCs" requestURI="" pagesize="100" class="simple2" export="true">
	    <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY"/>
	    <display:column property ="region" titleKey="TT" />
	    <display:column property ="province" titleKey="PROVINCE" class="hide" headerClass="hide" />
	    <display:column titleKey="PROVINCE" media="html" style = "width: 120px">
    	<a href="${pageContext.request.contextPath}/report/radio/province-gprs-cs/hr/details.htm?province=${vRpDyDistrictGprsCs.province}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyDistrictGprsCs.day}"/>">${vRpDyDistrictGprsCs.province}</a>&nbsp;
    	</display:column>
	    <display:column property ="district" titleKey="DISTRICT" headerClass="hide" class="hide"/> 
	    <display:column titleKey="DISTRICT" media="html" style = "width: 120px">
    	<a href="${pageContext.request.contextPath}/report/radio/district-gprs-cs/hr/details.htm?province=${vRpDyDistrictGprsCs.province}&district=${vRpDyDistrictGprsCs.district}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyDistrictGprsCs.day}"/>">${vRpDyDistrictGprsCs.district}</a>&nbsp;
    	</display:column> 
	    <display:column property ="csxLevel1" titleKey="CSX_LEVEL1" />
	    <display:column property ="csxLevel2" titleKey="CSX_LEVEL2" />
	    <display:column property ="csxLevel3" titleKey="CSX_LEVEL3" />
	    <display:column property ="csxLevel4" titleKey="CSX_LEVEL4"/>
	    <display:column property ="mcsxLevel1" titleKey="MCSX_LEVEL1" />
	    <display:column property ="mcsxLevel2" titleKey="MCSX_LEVEL2" />
	    <display:column property ="mcsxLevel3" titleKey="MCSX_LEVEL3" />
	    <display:column property ="mcsxLevel4" titleKey="MCSX_LEVEL4" />
	    <display:column property ="mcsxLevel5" titleKey="MCSX_LEVEL5" />
	    <display:column property ="mcsxLevel6" titleKey="MCSX_LEVEL6"/>
	    <display:column property ="mcsxLevel7" titleKey="MCSX_LEVEL7" />
	    <display:column property ="mcsxLevel8" titleKey="MCSX_LEVEL8" />
	    <display:column property ="mcsxLevel9" titleKey="MCSX_LEVEL9" />
	    <display:column property="dataload" titleKey="DATALOAD" />
	</display:table>
</div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript">
function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
}
	$(function() {
		$( "#startDate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
		$( "#endDate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
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