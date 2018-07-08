
<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>BSC Hourly Report</title>
<content tag="heading">BSC Hourly Report</content>
<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/bsc/hr/detail.htm?bscid=${bsc.bscid}&day=${day}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/core/bsc/dy/detail.htm?bscid=${bsc.bscid}&day=${day}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/core/bsc/wk/detail.htm?bscid=${bsc.bscid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/core/bsc/mn/detail.htm?bscid=${bsc.bscid}"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	<form method="get" action="detail.htm" name = "frmSample" onSubmit = "return ValidateForm()">
		<table width="100%" class="form">
			<tr>
			    <td align="left">
					BSC 
					<select name="bscid" id="bscid" style="width: 163px">
			        <option value="">--Select BSC--</option>
				        <c:forEach var="bsc" items="${bscList}">
			              <c:choose>
			                <c:when test="${bsc.bscid == bscid}">
			                    <option value="${bsc.bscid}" selected="selected">${bsc.bscid}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${bsc.bscid}">${bsc.bscid}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
					</select>
	                Ngày <input value="${day}" name="day" id="day" size="10" maxlength="10"/>
	                Giờ <input value="${hour}" name="hour" id="hour" size="2"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	</form>
	<br/>
	
	<table>
		<tr>
			<td width="20%" valign="top">
			    <table class="simple2">
			        <tr>
			            <th><b>Bsc</b></th>
			            <th><b><i>${bsc.bscid}</i></b></th>
			        </tr>
			        <tr>
			            <td>%</td>
			            <td>N/A</td>
			        </tr>
			        <tr>
			            <td>CSSR (%)</td>
			            <td><a href="#cssr">${hrBscCore.cssr} %</a></td>
			        </tr>
			        <tr>
			            <td>PSR (%)</td>
			            <td><a href="#psr">${hrBscCore.psr} %</a></td>
			        </tr>
			        <tr>
			            <td>DCRS (%)</td>
			            <td><a href="#dcrs">${hrBscCore.dcrs} %</a></td>
			        </tr>
			        <tr>
			            <td>HSR (%)</td>
			            <td><a href="#hsr">${hrBscCore.hsr} %</a></td>
			        </tr>
			        <tr>
			            <td>DCRT (%)</td>
			            <td><a href="#dcrt">${hrBscCore.dcrt} %</a></td>
			        </tr>
			        <tr>
			            <td>TRAUCR (%)</td>
			            <td><a href="#traucr">${hrBscCore.traucr} %</a></td>
			        </tr>
			    </table>
	    	</td>
	    	<td width="5%" valign="top"/>
			<td valign="top">
				<div id="notaccordion" style="width: 800px; margin: 0 auto">
					<h3><a href="#" name="cssr">CSSR</a></h3>				
					<div>			
					    <table class="simple2">
					        <tr>
            					<td>No. of SDCCH Seizure Attempts</td>
           					 	<td>${hrBscCssrQos.f1}</td>
        					</tr>
					        <tr>
					            <td>Congestion based on Seizure Attempts (%)</td>
					            <td>${hrBscCssrQos.f2} %</td>
					        </tr>
					        <tr>
					            <td>No. of SDCCH Connections</td>
					            <td>${hrBscCssrQos.f3}</td>
					        </tr>
					        <tr>
					            <td>SDCCH Establishments OL/UL No Congestion (%)</td>
					            <td>${hrBscCssrQos.f4} %</td>
					        </tr>
					        <tr>
					            <td>SDCCH Time Congestion UL (%)</td>
					            <td>${hrBscCssrQos.f5} %</td>
					        </tr>
					        <tr>
					            <td>Total Dropped SDCCH Connections</td>
					            <td>${hrBscCssrQos.f6}</td>
					        </tr>
					        <tr>
					            <td>SDCCH Drop (%)</td>
					            <td>${hrBscCssrQos.f7} %</td>
					        </tr>
					        <tr>
					            <td>SDCCH Mean Holding Time OL/UL (Sec.)</td>
					            <td>${hrBscCssrQos.f8}</td>
					        </tr>
					        <tr>
					            <td>Total Assignment Attempts</td>
					            <td>${hrBscCssrQos.f9}</td>
					        </tr>
					        <tr>
					            <td>TCH Assignment Success (%)</td>
					            <td>${hrBscCssrQos.f10} %</td>
					        </tr>
					        <tr>
					            <td>Data Availability (%)</td>
					            <td>${hrBscCssrQos.f11} %</td>
					        </tr>
					    </table>
					</div>
					<h3><a href="#" name="psr">PSR</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
					            <td>No. of Received Paging Messages</td>
					            <td>${hrBscPsrQos.f1}</td>
					        </tr>
					        <tr>
					            <td>Discarded Paging due to Congestion (%)</td>
					            <td>${hrBscPsrQos.f2} %</td>
					        </tr>
					        <tr>
					            <td><b>Paging successful rate (%)</b></td>
					            <td><b>${hrBscPsrQos.f3} %</b></td>
					        </tr>
					        <tr>
					            <td>Data Availability (%) </td>
					            <td>${hrBscPsrQos.f4} %</td>
					        </tr>
					    </table>
					</div>
					<h3><a href="#" name="dcrs">DCRS</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
					            <td>Total No. of Dropped SDCCH Connections</td>
					            <td>${hrBscDcrsQos.f1}</td>
					        </tr>
					        <tr>
					            <td><b>SDCCH Drop (%)</b></td>
					            <td><b>${hrBscDcrsQos.f2} %</b></td>
					        </tr>
					        <tr>
					            <td>SDCCH Erlang Minutes per Drop</td>
					            <td>${hrBscDcrsQos.f3}</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Low SS (%)</td>
					            <td>${hrBscDcrsQos.f4} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Bad Quality (%)</td>
					            <td>${hrBscDcrsQos.f5} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Excessive TA (%)</td>
					            <td>${hrBscDcrsQos.f6} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Other (%)</td>
					            <td>${hrBscDcrsQos.f7} %</td>
					        </tr>
					        <tr>
					            <td>Handover Lost of all SDCCH Drop (%)</td>
					            <td>${hrBscDcrsQos.f8} %</td>
					        </tr>
					        <tr>
					            <td>Data Availability (%)</td>
					            <td>${hrBscDcrsQos.f9} %</td>
					        </tr>
					    </table>
					</div>
					<h3><a href="#" name="dcrt">DCRT</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
					            <td>Total No. of Dropped TCH Connections</td>
					            <td>${hrBscDcrtQos.f1}</td>
					        </tr>
					        <tr>
					            <td><b>TCH Drop (%)</b></td>
					            <td><b>${hrBscDcrtQos.f2} %</b></td>
					        </tr>
					        <tr>
					            <td>TCH Erlang Minutes per Drop</td>
					            <td>${hrBscDcrtQos.f3}</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Low SS DL (%)</td>
					            <td>${hrBscDcrtQos.f4} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Low SS UL (%)</td>
					            <td>${hrBscDcrtQos.f5} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Low SS UL/DL (%)</td>
					            <td>${hrBscDcrtQos.f6} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Bad Quality DL (%)</td>
					            <td>${hrBscDcrtQos.f7} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Bad Quality UL (%)</td>
					            <td>${hrBscDcrtQos.f8} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Bad Quality UL/DL (%)</td>
					            <td>${hrBscDcrtQos.f9} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Suddenly Lost Connections (%)</td>
					            <td>${hrBscDcrtQos.f10} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Excessive TA (%)</td>
					            <td>${hrBscDcrtQos.f11} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, FER DL (%)</td>
					            <td>${hrBscDcrtQos.f12} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, FER UL (%)</td>
					            <td>${hrBscDcrtQos.f13} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, FER UL/DL (%)</td>
					            <td>${hrBscDcrtQos.f14} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Other (%)</td>
					            <td>${hrBscDcrtQos.f15} %</td>
					        </tr>
					    </table>
					</div>
					<h3><a href="#" name="traucr">TRAUCR</a></h3>
					<div>
					    <table class="simple2">
						  <c:forEach var="hrBscTrapeventQos" items="${hrBscTrapeventQosList}">
					        <tr>
					            <td>TRAU type</td>
					            <td>${hrBscTrapeventQos.type}</td>
					        </tr>
					        <tr>
					            <td>TRAU active resource</td>
					            <td>${hrBscTrapeventQos.f1}</td>
					        </tr>
					        <tr>
					            <td>TRAU idle</td>
					            <td>${hrBscTrapeventQos.f2}</td>
					        </tr>
					        <tr>
					            <td>TRAU Available</td>
					            <td>${hrBscTrapeventQos.f3}</td>
					        </tr>
					        <tr>
					            <td>Number of Transcoder Resource Allocation Attempts</td>
					            <td>${hrBscTrapeventQos.f4}</td>
					        </tr>
					        <tr>
					            <td>Transcoder Resource Congestion</td>
					            <td>${hrBscTrapeventQos.f5}</td>
					        </tr>
					        <tr>
					            <td><b>TRAU congestion rate (%)</b></td>
					            <td><b>${hrBscTrapeventQos.f6} %</b></td>
					        </tr>
					        <tr>
					            <td>Number of TFO establishment attempts</td>
					            <td>${hrBscTrapeventQos.f7}</td>
					        </tr>
					        <tr>
					            <td>Number of successful TFO establishments</td>
					            <td>${hrBscTrapeventQos.f8}</td>
					        </tr>
					        <tr>
					            <td>Accumulated number of seconds all TFO capable devices have been
					used in a speech call</td>
					            <td>${hrBscTrapeventQos.f9}</td>
					        </tr>
					        <tr>
					            <td>Accumulated number of seconds all TFO capable devices have been in
					TFO operation mode</td>
					            <td>${hrBscTrapeventQos.f10}</td>
					        </tr>
					        <tr><td colspan="2"><br/></td></tr>
						  </c:forEach>
					        <tr>
					            <td>Unsuccessful Transcoder Resource Allocation Attempts due to missing
					transcoder pool for Full Rate speech Version 1</td>
					            <td>${hrBscTrapcomQos.f1}</td>
					        </tr>
					        <tr>
					            <td>Unsuccessful Transcoder Resource Allocation Attempts due to missing
					transcoder pool for Full Rate speech Version 2</td>
					            <td>${hrBscTrapcomQos.f2}</td>
					        </tr>
					        <tr>
					            <td>Unsuccessful Transcoder Resource Allocation Attempts due to missing
					transcoder pool for Full Rate speech Version 3</td>
					            <td>${hrBscTrapcomQos.f3}</td>
					        </tr>
					        <tr>
					            <td>Unsuccessful Transcoder Resource Allocation Attempts due to missing
					transcoder pool for Haft Rate speech Version 1</td>
					            <td>${hrBscTrapcomQos.f4}</td>
					        </tr>
					        <tr>
					            <td>Unsuccessful Transcoder Resource Allocation Attempts due to missing
					transcoder pool for Haft Rate speech Version 2</td>
					            <td>${hrBscTrapcomQos.f5}</td>
					        </tr>
					        <tr>
					            <td>Unsuccessful Transcoder Resource Allocation Attempts due to missing
					transcoder pool for Haft Rate speech Version 3</td>
					            <td>${hrBscTrapcomQos.f6}</td>
					        </tr>
					    </table>
					</div>
					<h3><a href="#" name="hsr">HSR</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
					            <td>Incoming HO Attempts </td>
					            <td>${hrBscHsrQos.f1} </td>
					        </tr>
					        <tr>
					            <td><b>Incoming HO Success (%)</b></td>
					            <td><b>${hrBscHsrQos.f3} %</b></td>
					        </tr>
					        <tr>
					            <td>Outgoing HO Attempts </td>
					            <td>${hrBscHsrQos.f4} </td>
					        </tr>
					        <tr>
					            <td><b>Outgoing HO Success (%)</b></td>
					            <td><b>${hrBscHsrQos.f6} %</b></td>
					        </tr>
					        <tr>
					            <td>Data Availability (%)</td>
					            <td>${hrBscHsrQos.f8} %</td>
					        </tr>
					    </table>
					</div>
				</div>
			</td>			
	    	<td width="5%" valign="top"/>
		</tr>
	</table>
	</div>

<script type="text/javascript">
	$(function() {
		$( "#day" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});

		$("#notaccordion").addClass("ui-accordion ui-widget ui-helper-reset ui-accordion-icons")
		.find("h3")
			.addClass("ui-accordion-header ui-helper-reset ui-state-active ui-corner-top ui-state-focus")
			.prepend('<span class="ui-icon ui-icon-triangle-1-s"/>')
			.click(function() {
				$(this).toggleClass("ui-state-active ui-corner-top ui-state-focus ui-state-default ui-corner-all")
				.find("> .ui-icon").toggleClass("ui-icon-triangle-1-e ui-icon-triangle-1-s")
				.end().next().toggleClass("ui-icon-triangle-1-e ui-icon-triangle-1-s").toggle();
				return false;
			})
			.next().addClass("ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom").show();
		
		var cache = {},
		lastXhr;
		$( "#bscid" ).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cache ) {
					response( cache[ term ] );
					return;
				}

				lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getBsc.htm", request, function( data, status, xhr ) {
					cache[ term ] = data;
					if ( xhr === lastXhr ) {
						response( data );
					}
				});
			}
		});
	});
</script>
