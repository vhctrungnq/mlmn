<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style><title>Báo cáo QoS Cell 3G ${cellid}</title>

<c:choose>
    <c:when test="${function=='accessibility' }">
        <content tag="heading">CELL ${cellid} ACCESSIBILITY HOURLY REPORT</content>
    </c:when>
    <c:when test="${function=='mobility' }">
        <content tag="heading">CELL ${cellid} MOBILITY HOURLY REPORT</content>  
    </c:when>
    <c:when test="${function=='retainability' }">
        <content tag="heading">CELL ${cellid} RETAINABILITY HOURLY REPORT</content> 
    </c:when>
    <c:when test="${function=='traffic' }">
        <content tag="heading">CELL ${cellid} TRAFFIC HOURLY REPORT</content>   
    </c:when>
    <c:otherwise>
        <content tag="heading">CELL ${cellid} QOS HOURLY REPORT</content>   
    </c:otherwise>
</c:choose>
<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/cell/hr/detail.htm?function=${function}&cellid=${cellid}&bscid=${bscid}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/cell/dy/detail.htm?function=${function}&cellid=${cellid}&bscid=${bscid}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/cell/wk/detail.htm?function=${function}&cellid=${cellid}&bscid=${bscid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/cell/mn/detail.htm?function=${function}&cellid=${cellid}&bscid=${bscid}"><span>Báo cáo tháng</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/cell/dy/bhDetails.htm?function=${function}&cellid=${cellid}&bscid=${bscid}&endDate=${endDate}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/cell/wk/bhDetails.htm?function=${function}&cellid=${cellid}&bscid=${bscid}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/cell/mn/bhDetails.htm?function=${function}&cellid=${cellid}&bscid=${bscid}"><span>Báo cáo BH tháng</span></a></li>
</ul>
    <div class="ui-tabs-panel">

      <form method="get" action="detail.htm" name = "frmSample" onSubmit = "return ValidateForm()">
        <table width="100%" class="form">
            <tr>
                <td align="left">
                    RNC 
                    <select name="bscid" id="bscid">
                        <option value="">--Select RNC--</option>
                        <c:forEach var="bsc" items="${bscList}">
                          <c:choose>
                            <c:when test="${bsc == bscid}">
                                <option value="${bsc}" selected="selected">${bsc}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${bsc}">${bsc}</option>
                            </c:otherwise>
                          </c:choose>
                        </c:forEach>
                    </select>
                    <input value="${function}" id="function" name="function" type="hidden"/>
                    &nbsp;CELL <input value="${cellid}" name="cellid" id="cellid" size="10">
                    &nbsp;Từ <input value="${startHour}" name="startHour" id="startHour" size="5" maxlength="5"> giờ
                    &nbsp;<input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
                    &nbsp;&nbsp;Tới <input value="${endHour}" name="endHour" id="endHour" size="5" maxlength="5"> giờ
                    &nbsp;<input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
                    &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
                </td>
            </tr>
        </table>
      </form>
        <br/>
        <table class="form">
            <tr>
                <td>
                    <b>Chọn chỉ số hiển thị: </b>
                </td>
            </tr>
            <tr>
                <td>${checkColumns}</td>
            </tr>
        </table>
    <br/> 
         <div id="doublescroll"> 
        <div class="tableStandar">
            <display:table name="${vRpHrCellList}" id="vRpHrCell" requestURI="" pagesize="${pageSize }"   export="true" partialList="true" size="${totalSize}">
                <display:column property="day" format="{0,date,dd/MM/yyyy}" title="Date" headerClass="master-header-1" sortable="true"/>
                <display:column title="HOUR" headerClass="master-header-1" sortable="true">
                    ${vRpHrCell.hour} 
                </display:column> 
                <display:column property="province" titleKey="Province" headerClass="master-header-1" sortable="true" />
               <%--  <display:column title="Province" headerClass="master-header-1" media="html" style = "width:120px" sortable="true">
                    <a href="${pageContext.request.contextPath}/report/radio3g/province/hr/detail.htm?province=${vRpHrCell.province}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrCell.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrCell.day}"/>">${vRpHrCell.province}</a>
                </display:column> --%>
                <display:column property="bscid" titleKey="RNC" sortable="true" headerClass="hide" class="hide"/>
                <display:column title="RNC" headerClass="master-header-1" media="html" sortable="true">
                    <a href="${pageContext.request.contextPath}/report/radio3g/rnc/hr/detail.htm?bscid=${vRpHrCell.bscid}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrCell.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrCell.day}"/>">${vRpHrCell.bscid}</a>
                </display:column>
                <display:column property="siteName" titleKey ="SITENAME" headerClass="master-header-1" sortable="true"/>
                <display:column property="cellid"  titleKey = "CELLID" headerClass="master-header-1" sortable="true"/>
                <display:column property="cellname"  titleKey = "CELLNAME" headerClass="master-header-1" sortable="true"/>
      
                <c:choose>
                    <c:when test="${function=='accessibility' }">
                        <display:column property ="cellDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELL_DOWNTIME" sortable="true" headerClass="master-header-2"/>
                        <display:column property ="hsDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HS_DOWNTIME" sortable="true" headerClass="master-header-2"/>
                        <display:column property ="eulDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EUL_DOWNTIME" sortable="true" headerClass="master-header-2 margin" class="margin"/>
                        <display:column property="speechCssr" titleKey="SPEECH_CSSR" sortable="true" headerClass="hide" class="hide"/>
                        <display:column titleKey="SPEECH_CSSR" class="SPEECH_CSSR" sortable="true" headerClass="master-header-4" media="html">
                            <a href="${pageContext.request.contextPath}/report/radio3g/cell/cssr/hr.htm?bscid=${vRpHrCell.bscid}&cellid=${vRpHrCell.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrCell.day}"/>">${vRpHrCell.speechCssr}</a>
                        </display:column>
                        <display:column property ="pasr3g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" 
                            titleKey="PASR3G" sortable="true" headerClass="master-header-4"/>
                        <display:column property ="cs64Cssr" titleKey="CS64_CSSR" class="CS64_CSSR" sortable="true" headerClass="master-header-4"/>
                        <display:column property="psr99Cssr" titleKey="PSR99_CSSR" class="PSR99_CSSR" sortable="true" headerClass="master-header-4"/>               
                        <display:column property ="hsupaCssr" titleKey="HSUPA_CSSR" class="HSUPA_CSSR" sortable="true" headerClass="master-header-4"/>
                        <display:column property="hsdpaCssr" titleKey="HSDPA_CSSR" class="HSDPA_CSSR margin" sortable="true" headerClass="master-header-4 margin"/>
                        
                    </c:when>
                    <c:when test="${function=='retainability' }">
                        <display:column property ="speechDrop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SPEECH_DROP" sortable="true" headerClass="master-header-5"/>
                        <display:column property="speechDrpr" titleKey="SPEECH_DRPR" sortable="true" headerClass="hide" class="hide"/>
                        <display:column titleKey="SPEECH_DRPR" class="SPEECH_DRPR" sortable="true"headerClass="master-header-5" media="html">
                            <a href="${pageContext.request.contextPath}/report/radio3g/cell/drop/hr.htm?bscid=${vRpHrCell.bscid}&cellid=${vRpHrCell.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrCell.day}"/>">${vRpHrCell.speechDrpr}</a>
                        </display:column>
                        <display:column property ="padr3g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"
                             titleKey="PADR3G" sortable="true" headerClass="master-header-5"/>
                        <display:column property="cs64Drop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CS64_DROP" sortable="true" headerClass="master-header-5"/>
                        <display:column property="cs64Drpr" titleKey="CS64_DRPR" class="CS64_DRPR" sortable="true" headerClass="master-header-5"/>
                        <display:column property="psr99Drop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DROP" sortable="true" headerClass="master-header-5"/>
                        <display:column property="psr99Drpr" titleKey="PSR99_DRPR" class="PSR99_DRPR" sortable="true" headerClass="master-header-5"/>
                        <display:column property="hsupaDrop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_DROP" sortable="true" headerClass="master-header-5"/>
                        <display:column property ="hsupaDrpr" titleKey="HSUPA_DRPR" class="HSUPA_DRPR" sortable="true" headerClass="master-header-5"/>
                        <display:column property="hsdpaDrop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DROP" sortable="true" headerClass="master-header-5"/>
                        <display:column property="hsdpaDrpr" titleKey="HSDPA_DRPR" class="HSDPA_DRPR margin" sortable="true" headerClass="master-header-5 margin"/>
                        
                    </c:when>
                    <c:when test="${function=='mobility' }">
                        <display:column property="speechShoSrOut" titleKey="SPEECH_SHO_SR_OUT" sortable="true" headerClass="hide" class="hide"/>
                        <display:column titleKey="SPEECH_SHO_SR_OUT" sortable="true" headerClass="master-header-6" media="html">
                            <a href="${pageContext.request.contextPath}/report/radio3g/cell/ho-cell-to-cell/hrCellToCells.htm?bscid=${vRpHrCell.bscid}&cellid=${vRpHrCell.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrCell.day}"/>">${vRpHrCell.speechShoSrOut}</a>
                        </display:column>
                        <display:column property="shoSrOut" titleKey="SHO_SR_OUT" sortable="true" headerClass="master-header-6"/>
                        <display:column property="shoSrIn" titleKey="SHO_SR_IN" sortable="true" headerClass="master-header-6"/>
                        <display:column property="csIratHoSr" titleKey="CS_IRAT_HO_SR" sortable="true" headerClass="master-header-6"/>
                        <display:column property ="psIratHoSr" titleKey="PS_IRAT_HO_SR" sortable="true" headerClass="master-header-6 margin" class="margin"/>
                    </c:when>
                    <c:when test="${function=='traffic' }">
                         <display:column property="speechTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SPEECH_TRAFF" class="SPEECH_TRAFF"  sortable="true" headerClass="master-header-3"/>
                        <display:column property="cs64Traff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CS64_TRAFF" sortable="true" headerClass="master-header-3"/>
                        <display:column property="psr99UlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_TRAFF_CELL"  sortable="true" headerClass="master-header-3"/>
                        <display:column property="psr99DlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_TRAFF_CELL"  sortable="true" headerClass="master-header-3"/>
                        <display:column property="hsupaUlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_TRAFF_CELL"  sortable="true" headerClass="master-header-3"/>
                        <display:column property ="hsdpaDlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_TRAFF_CELL"  sortable="true" headerClass="master-header-3"/>
                        <display:column property="psr99UlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_THROUGHTPUT" sortable="true" headerClass="master-header-3"/>
                        <display:column property="psr99DlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_THROUGHTPUT" sortable="true" headerClass="master-header-3"/>
                        <display:column property="hsupaUlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_THROUGHTPUT" sortable="true" headerClass="master-header-3"/>
                        <display:column property="hsdpaDlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_THROUGHTPUT" sortable="true" headerClass="master-header-3 margin" class="margin"/>
                        
                    </c:when>
                    <c:otherwise>
                         <display:column property ="cellDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELL_DOWNTIME" sortable="true" headerClass="master-header-2"/>
                        <display:column property ="hsDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HS_DOWNTIME" sortable="true" headerClass="master-header-2"/>
                        <display:column property ="eulDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EUL_DOWNTIME" sortable="true" headerClass="master-header-2 margin" class="margin"/> 
                        <display:column property="speechCssr" titleKey="SPEECH_CSSR" sortable="true" headerClass="hide" class="hide"/>
                        <display:column titleKey="SPEECH_CSSR" class="SPEECH_CSSR_CELL" sortable="true" headerClass="master-header-4" media="html">
                            <a href="${pageContext.request.contextPath}/report/radio3g/cell/cssr/hr.htm?bscid=${vRpHrCell.bscid}&cellid=${vRpHrCell.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrCell.day}"/>">${vRpHrCell.speechCssr}</a>
                        </display:column>
                         <display:column property ="cs64Cssr" titleKey="CS64_CSSR" class="CS64_CSSR" sortable="true" headerClass="master-header-4"/>
                        <display:column property="psr99Cssr" titleKey="PSR99_CSSR" class="PSR99_CSSR" sortable="true" headerClass="master-header-4"/>               
                        <display:column property ="hsupaCssr" titleKey="HSUPA_CSSR" class="HSUPA_CSSR" sortable="true" headerClass="master-header-4"/> 
                        <display:column property="hsdpaCssr" titleKey="HSDPA_CSSR" class="HSDPA_CSSR margin" sortable="true" headerClass="master-header-4 margin"/>
                      
                        <display:column property="speechDrpr" titleKey="SPEECH_DRPR" sortable="true" headerClass="hide" class="hide"/>
                        <display:column titleKey="SPEECH_DRPR" class="SPEECH_DRPR" sortable="true"headerClass="master-header-5" media="html">
                            <a href="${pageContext.request.contextPath}/report/radio3g/cell/drop/hr.htm?bscid=${vRpHrCell.bscid}&cellid=${vRpHrCell.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpHrCell.day}"/>">${vRpHrCell.speechDrpr}</a>
                        </display:column>
                        
                        <display:column property ="pasr3g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" 
                            titleKey="PASR3G" sortable="true" headerClass="master-header-4"/>
                        <display:column property ="padr3g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator"
                             titleKey="PADR3G" sortable="true" headerClass="master-header-5"/>
                        
                        <display:column property ="speechDrop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SPEECH_DROP" sortable="true" headerClass="master-header-5"/>
         
                       <display:column property="cs64Drop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CS64_DROP" sortable="true" headerClass="master-header-5"/>
                        <display:column property="cs64Drpr" titleKey="CS64_DRPR" class="CS64_DRPR" sortable="true" headerClass="master-header-5"/>
                        <display:column property="psr99Drop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DROP" sortable="true" headerClass="master-header-5"/>
                        <display:column property="psr99Drpr" titleKey="PSR99_DRPR" class="PSR99_DRPR" sortable="true" headerClass="master-header-5"/>
                        <display:column property="hsupaDrop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_DROP" sortable="true" headerClass="master-header-5"/>
                        <display:column property ="hsupaDrpr" titleKey="HSUPA_DRPR" class="HSUPA_DRPR" sortable="true" headerClass="master-header-5"/>
                        <display:column property="hsdpaDrop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DROP" sortable="true" headerClass="master-header-5"/>
                        <display:column property="hsdpaDrpr" titleKey="HSDPA_DRPR" class="HSDPA_DRPR margin" sortable="true" headerClass="master-header-5 margin"/>
                        <display:column property="speechTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SPEECH_TRAFF" class="SPEECH_TRAFF"  sortable="true" headerClass="master-header-3"/>
                        <display:column property="cs64Traff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CS64_TRAFF" sortable="true" headerClass="master-header-3"/>
                        <display:column property="psr99UlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_TRAFF_CELL"  sortable="true" headerClass="master-header-3"/>
                        <display:column property="psr99DlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_TRAFF_CELL"  sortable="true" headerClass="master-header-3"/>
                        <display:column property="hsupaUlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_TRAFF_CELL"  sortable="true" headerClass="master-header-3"/>
                        <display:column property ="hsdpaDlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_TRAFF_CELL"  sortable="true" headerClass="master-header-3"/>
                        <display:column property="psr99UlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_THROUGHTPUT" sortable="true" headerClass="master-header-3"/>
                        <display:column property="psr99DlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_THROUGHTPUT" sortable="true" headerClass="master-header-3"/>
                        <display:column property="hsupaUlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_THROUGHTPUT" sortable="true" headerClass="master-header-3"/>
                        <display:column property="hsdpaDlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_THROUGHTPUT" sortable="true" headerClass="master-header-3"/>
                        <display:column property="doKhaDung3g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DO_KHA_DUNG_3G" sortable="true" headerClass="master-header-3 margin" />
                    </c:otherwise>
                </c:choose>
            </display:table>
            </div>
           </div> 
    <br/>
    <div id="speechCssrChart" style="width: 1000px; margin: 1em auto"></div>
    <br/>
    <div id="cs64CssrChart" style="width: 1000px; margin: 1em auto"></div>
    <br/>
    <div id="psr99CssrChart" style="width: 1000px; margin: 1em auto"></div>
    <br/>
    <div id="hsupaCssrChart" style="width: 1000px; margin: 1em auto"></div>
    <br/>
    <div id="hsdpaCssrChart" style="width: 1000px; margin: 1em auto"></div>
    <br/>
    <div id="speechDrprChart" style="width: 1000px; margin: 1em auto"></div>
    <br/>
    <div id="cs64DrprChart" style="width: 1000px; margin: 1em auto"></div>
    <br/>
    <div id="psr99DrprChart" style="width: 1000px; margin: 1em auto"></div>
    <br/>
    <div id="hsupaDrprChart" style="width: 1000px; margin: 1em auto"></div>
    <br/>
    <div id="hsdpaDrprChart" style="width: 1000px; margin: 1em auto"></div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
 ${speechCssrChart}
${cs64CssrChart}
${psr99CssrChart}
${hsupaCssrChart}
${hsdpaCssrChart}
${speechDrprChart}
${cs64DrprChart}
${psr99DrprChart}
${hsupaDrprChart}
${hsdpaDrprChart}

 <script>
    $(document).ready(function(){
        var webFunction = "<c:out value = '${function}' />";
        if (webFunction.length != null && webFunction.length > 0) {
            $("table#vRpHrCell").css("width", "100%");
        } else {
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
        }
    });
</script> 

<script type="text/javascript">
    $(document).ready(function() {
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
        
        var cacheCell = {}, lastXhrCell;
        $("#cellid" ).autocomplete({
            minLength: 3,
            source: function( request, response ) {
                var term = request.term;
                if ( term in cacheCell ) {
                    response( cacheCell[ term ] );
                    return;
                }

                lastXhrCell = $.getJSON( "${pageContext.request.contextPath}/ajax/getCell3G.htm", request, function( data, status, xhr ) {
                    cacheCell[ term ] = data;
                    if ( xhr === lastXhrCell ) {
                        response( data );
                    }
                });
            }
        });     
        ${highlight}
    });
</script>