<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>cell to cells handover list</title>
<content tag="heading">LIST CELLS TO CELL HANDOVER FROM ${startMonth}/${startYear} TO ${endMonth}/${endYear}</content>
<ul class="ui-tabs-nav">
	<!-- li class=""><a href="${pageContext.request.contextPath}/report/radio/cells-to-cell/hr/list.htm?bscid=${bscid}&toCell=${toCell}"><span>Báo cáo giờ</span></a></li> -->
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cells-to-cell/dy/list.htm?bscid=${bscid}&toCell=${toCell}"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/cells-to-cell/wk/list.htm?bscid=${bscid}&toCell=${toCell}"><span>Báo cáo tuần</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/cells-to-cell/mn/list.htm?bscid=${bscid}&toCell=${toCell}"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm" name="frmSample" onSubmit="return ValidateFormYear()">
				  TO BSC 
			        <select name="bscid" id="bscid" onchange="xl()">
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
				    &nbsp;&nbsp;TO CELL 
			        <select name="toCell" id="toCell" onchange="xl()">
						<option  value="">--Select Cell--</option>
				        <c:forEach var="cell" items="${cellList}">
			              <c:choose>
			                <c:when test="${cell.cellid == toCell}">
			                    <option value="${cell.cellid}" selected="selected">${cell.cellid}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${cell.cellid}">${cell.cellid}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
					</select>
			       &nbsp;Từ tháng <select name="startMonth" id="startMonth" onchange="xl()">
	            				<c:forEach var="month" items="${monthList}">
						              <c:choose>
						                <c:when test="${month == startMonth}">
						                    <option value="${month}" selected="selected">${month}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${month}">${month}</option>
						                </c:otherwise>
						              </c:choose>
						    </c:forEach>
			               	 </select>&nbsp;
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.startYear);">
	            	&nbsp;Tới tháng <select name="endMonth" id="endMonth" onchange="xl()">
	            				<c:forEach var="month" items="${monthList}">
						              <c:choose>
						                <c:when test="${month == endMonth}">
						                    <option value="${month}" selected="selected">${month}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${month}">${month}</option>
						                </c:otherwise>
						              </c:choose>
						    </c:forEach>
			               	 </select>&nbsp;
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.endYear);">
	            	&nbsp;<input type="submit" class="button" name="submit" id="submit"value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
	<div  style="overflow: auto;">
	<display:table name="${mnCellsToCellQos}" id="mnCellsToCell" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
		    <display:column property ="month" titleKey="MONTH" sortable="true" sortName="month"/>
		    <display:column property ="year" titleKey="YEAR" sortable="true" sortName="year"/>
		    <display:column property ="bscid" titleKey="FROM BSC" sortable="true" sortName="bscid"/>
		    <display:column property="fromCell" titleKey="FROM CELL" sortable="true" sortName="from_Cell"/> 
		    <display:column property ="toBsc" titleKey="TO BSC" sortable="true" sortName="to_Bsc"/>
		    <display:column property="toCell" titleKey="TO CELL" sortable="true" sortName="to_Cell"/>
		    <display:column property ="hovercnt" titleKey="HO ATT" sortable="true" sortName="hovercnt"/>
		    <display:column property="hoversuc" titleKey="HO SUC" sortable="true" sortName="hoversuc"/>
		    <display:column property="hovesucr" titleKey="HO SUCR(%)" sortable="true" sortName="hovesucr"/>
	</display:table>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript">
function CalcKeyCode(aChar) {
	  var character = aChar.substring(0,1);
	  var code = aChar.charCodeAt(0);
	  return code;
	}

function checkNumber(val) {
	  var strPass = val.value;
	  var strLength = strPass.length;
	  for(var i =0;i<strLength+1;i++){
	  var lchar = val.value.charAt((strLength) - i);
	  var cCode = CalcKeyCode(lchar);
	  if (cCode < 48 || cCode > 57 ) {
	    var myNumber = val.value.substring(0, (strLength) - i);
	    val.value = myNumber;
	  }
	  }
	  var sub = document.getElementById("submit");
		sub.focus();
	  return false;
	}
function xl(){
		var sub = document.getElementById("submit");
		sub.focus();
	} 
	$(function() {
		$("select#bscid").change(function(){
			$.getJSON("${pageContext.request.contextPath}/ajax/getCellOfBsc.htm",{bscid: $(this).val()}, function(j){
				var options = '<option  value="">--Select Cell--</option>';
				for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].cellid + '">' + j[i].cellid + '</option>';
				}
				$("#toCell").html(options);
				$('#toCell option:first').attr('selected', 'selected');
			})
		})
	});
</script>
