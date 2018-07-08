<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxtreegrid.js"></script>   --%>
<title>${title}</title>
<content tag="heading">${title}</content> 	
<style type="text/css">    
#doublescroll { overflow: auto; overflow-y: hidden; } #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; } 
tr.total td, tr.subtotal td {
    background-color: #eee;
    font-weight: bold;
}
</style>

<div class="ui-tabs-panel">
	<form:form commandName="filter" method="post" action="${function}.htm">
		<table >
			<tr> 
				 <c:choose>
	                <c:when test="${function== 'week'}">
	                   	<td style="width:50px">Tuần</td>
						<td style="width:150px">
								<input value="${sweek}" name="sweek" id="sweek" size="2">
								<img alt="calendar" titleKey="Click to choose the start week number" id="chooseStartWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
		            			&nbsp;Năm <input value="${syear}" name="syear" id="syear" size="4" maxlength="4">
		            	</td>
						<%-- <td style="width:70px">Đến tuần</td>
						<td style="width:130px">
								<input value="${eweek}" name="eweek" id="eweek" size="2">
								<img alt="calendar" titleKey="Click to choose the start week number" id="chooseEndWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
		            			&nbsp;Năm <input value="${eyear}" name="eyear" id="eyear" size="4" maxlength="4">
						</td> --%>
	                </c:when>
	                 <c:when test="${function== 'month'}">
	                   	<td style="width:50px">Tháng</td>
						<td style="width:150px">
							<select name="smonth" id="smonth" onchange="xl()">
		            				<c:forEach var="month" items="${monthList}">
							              <c:choose>
							                <c:when test="${month == smonth}">
							                    <option value="${month}" selected="selected">${month}</option>
							                </c:when>
							                <c:otherwise>
							                    <option value="${month}">${month}</option>
							                </c:otherwise>
							              </c:choose>
							    </c:forEach>
				              </select>
				             &nbsp;Năm <input value="${syear}" name="syear" id="syear" size="4" maxlength="4">
				         </td>
						<%-- <td style="width:70px">Đến tháng</td>
						<td style="width:130px">
							<select name="emonth" id="emonth" onchange="xl()">
	            				<c:forEach var="month" items="${monthList}">
						              <c:choose>
						                <c:when test="${month == emonth}">
						                    <option value="${month}" selected="selected">${month}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${month}">${month}</option>
						                </c:otherwise>
						              </c:choose>
							    </c:forEach>
				              </select>
				             &nbsp;Năm <input value="${eyear}" name="eyear" id="eyear" size="4" maxlength="4">
						</td> --%>
	                </c:when>
	                 <c:when test="${function== 'quarter'}">
	                   	<td style="width:50px">Quý</td>
						<td style="width:150px">
							<select name="smonth" id="smonth" onchange="xl()">
		            				<c:forEach var="month" items="${quarterList}">
							              <c:choose>
							                <c:when test="${month == smonth}">
							                    <option value="${month}" selected="selected">${month}</option>
							                </c:when>
							                <c:otherwise>
							                    <option value="${month}">${month}</option>
							                </c:otherwise>
							              </c:choose>
							    </c:forEach>
				              </select>
				             &nbsp;Năm <input value="${syear}" name="syear" id="syear" size="4" maxlength="4">
				         </td>
						<%-- <td style="width:70px">Đến quý </td>
						<td style="width:130px">
							<select name="emonth" id="emonth" onchange="xl()">
	            				<c:forEach var="month" items="${quarterList}">
						              <c:choose>
						                <c:when test="${month == emonth}">
						                    <option value="${month}" selected="selected">${month}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${month}">${month}</option>
						                </c:otherwise>
						              </c:choose>
							    </c:forEach>
				              </select>
				             &nbsp;Năm <input value="${eyear}" name="eyear" id="eyear" size="4" maxlength="4">
						</td> --%>
	                </c:when>
	                <c:when test="${function== 'year'}">
	                   	<td style="width:50px">Năm</td>
						<td style="width:90px">
							<input type ="text"  value="${syear}" name="syear" id="syear" size="10" maxlength="10" style="width:70px">
					 	</td>
						<%-- <td style="width:70px">Đến năm</td>
						<td style="width:90px">
							<input type ="text"  value="${eyear}" name="eyear" id="eyear" size="10" maxlength="10" style="width:70px">
					   	</td> --%>
	                </c:when>
	              <c:when test="${function== 'luy-ke'}">
						<td>
								<input type ="hidden"  name="edate" id="edate" size="10" maxlength="10" style="width:70px">
						</td>
						<td style="width:70px">Đến ngày</td>
						<td style="width:90px">
								<input type ="text"  value="${sdate}" name="sdate" id="sdate" size="10" maxlength="10" style="width:70px">
					   			 <img alt="calendar" title="Click to choose the from date" id="chooseSDate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
						</td>
	                </c:when>
	                 <c:when test="${function== 'dy'}">
						<td style="width:50px">Ngày</td>
						<td style="width:90px">
								<input type ="text"  value="${sdate}" name="sdate" id="sdate" size="10" maxlength="10" style="width:70px">
					   			 <img alt="calendar" title="Click to choose the from date" id="chooseSDate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
						</td>
	                </c:when>
	                <c:otherwise>
	                    <td style="width:50px">Từ Ngày</td>
						<td style="width:90px">
								<input type ="text"  value="${sdate}" name="sdate" id="sdate" size="10" maxlength="10" style="width:70px">
					   			 <img alt="calendar" title="Click to choose the from date" id="chooseSDate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
						</td>
						<td style="width:70px">Đến ngày</td>
						<td style="width:90px">
								<input type ="text"  value="${edate}" name="edate" id="edate" size="10" maxlength="10" style="width:70px">
					   			 <img alt="calendar" title="Click to choose the from date" id="chooseEDate" style="cursor: pointer;position: absolute;" src="${pageContext.request.contextPath}/images/calendar.png"/>
						</td>
	                </c:otherwise>
	             </c:choose>
				
				<td>Khu vực</td>
				<td>
			        <div id='region'></div>
		        </td>
				<td style="padding-left: 5px;width:70px"><fmt:message key="alarmLog.province"/></td>
				<td>
					 <div id='province'></div>
				</td>
				<td><fmt:message key="baoCaoSoLuongPA.loaiBaoCao"/></td>
				<td>
			        <div id='fbType'></div>
		        </td>
		        <td><spring:message code="qLThongTinPhanAnh.trangThai"/></td>
				<td>
			        <div id='status'></div>
		        </td>
				<td style="padding-left: 5px;">
					<input class="button" type="submit" id="button" value="<fmt:message key="global.form.timkiem"/>" />
					<input class="button" type="button" id="btExport" value="Report" />
				</td>
			</tr>
			
		</table>
	</form:form>
</div>

<!-- Trinh bay cac grid -->
<br/>
<h3>1. Báo cáo xử lý FB theo loại phản ánh</h3>
<div style="width:100%;overflow:auto;"  class="tableStandar">
	<display:table name="${reportFBFBType}"  id="itemreportFBFBType" requestURI="" pagesize="50" sort="external" export="true"  >
		<display:column class="centerColumnMana" titleKey="global.list.No"> <c:out value="${itemreportFBFBType_rowNum}"/> 
		</display:column>
		<c:choose>
			<c:when test="${function == 'week'}">
				<display:column property="week" titleKey="baoCaoSoLuongPA.week" />
			</c:when>
			<c:when test="${function == 'month'}">
				<display:column property="mon" titleKey="baoCaoSoLuongPA.mon" sortable="true"  />
			</c:when>
			<c:when test="${function == 'quarter'}">
				<display:column property="quarters" titleKey="baoCaoSoLuongPA.quarters" sortable="true"  />
			</c:when>
			<c:when test="${function == 'year'}">
				<display:column property="years" titleKey="baoCaoSoLuongPA.years" sortable="true"  />
			</c:when>
			<c:otherwise>
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="baoCaoSoLuongPA.ngay"/>
			</c:otherwise>
		</c:choose>
		<display:column property="fbType" titleKey="qLThongTinPhanAnh.loaiPhanAnh" sortable="true"  />
		<c:choose>
			<c:when test="${function  != 'dy'}">
			<display:column class="rightColumnMana" property="total" titleKey="baoCaoSoLuongPA.total"  />
			<display:column class="rightColumnMana" property="total_1" titleKey="baoCaoSoLuongPA.${function}_total_1"  />
			<display:column class="rightColumnMana" property="chuaXuLy_1" titleKey="baoCaoSoLuongPA.${function}_chuaXuLy_1"  />
			<display:column class="rightColumnMana" property="canXuLy" titleKey="baoCaoSoLuongPA.canXuLy"  />
			<display:column class="rightColumnMana" property="daXuLyTrongHan" titleKey="baoCaoSoLuongPA.daXuLyTrongHan"  />
			<display:column class="rightColumnMana" property="daXuLyQuaHan" titleKey="baoCaoSoLuongPA.daXuLyQuaHan"  />
			<display:column class="rightColumnMana" property="daXuLy" titleKey="baoCaoSoLuongPA.daXuLy"  />
	  		<display:column class="rightColumnMana" property="chuaXuLyTrongHan" titleKey="baoCaoSoLuongPA.chuaXuLyTrongHan"  />
	  		<display:column class="rightColumnMana" property="chuaXuLyQuaHan" titleKey="baoCaoSoLuongPA.chuaXuLyQuaHan"  />
	  		<display:column class="rightColumnMana" property="chuaXuLy" titleKey="baoCaoSoLuongPA.chuaXuly"  />
	  		<display:column class="rightColumnMana" property="tlXuLy" titleKey="baoCaoSoLuongPA.tlXuLy"  />
	  		<display:column class="rightColumnMana" property="tLTrongHan" titleKey="baoCaoSoLuongPA.tLTrongHan"  />
	  		<display:column class="rightColumnMana" property="tLTrongHan_1" titleKey="baoCaoSoLuongPA.${function}_tLTrongHan_1"  />
	  		<display:column class="rightColumnMana" property="soSanhTyLe" titleKey="baoCaoSoLuongPA.soSanhTyLe"/>
	  		<display:column class="rightColumnMana" property="soSanhTong" titleKey="baoCaoSoLuongPA.soSanhTong"  />
	  	</c:when>
		<c:otherwise>
	  		<display:column class="rightColumnMana" property="num2G" titleKey="baoCaoSoLuongPA.num2G"  />
			<display:column class="rightColumnMana" property="num3G" titleKey="baoCaoSoLuongPA.num3G"  />
			<display:column class="rightColumnMana" property="num4G" titleKey="baoCaoSoLuongPA.num4G"  />
			<display:column class="rightColumnMana" property="total" titleKey="baoCaoSoLuongPA.total"  />
			<display:column class="rightColumnMana" property="tgxlTbHcm" titleKey="baoCaoSoLuongPA.Tgxl_Tb_Hcm"  />
	  		<display:column class="rightColumnMana" property="tgxlTbMd" titleKey="baoCaoSoLuongPA.Tgxl_Tb_Md"  />
	  		<display:column class="rightColumnMana" property="tgxlTbMt" titleKey="baoCaoSoLuongPA.Tgxl_Tb_Mt"  />
  		</c:otherwise>
		</c:choose>
		<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<%-- <display:setProperty name="decorator.media.excel" value="org.displaytag.sample.decorators.HssfTotalWrapper"/> --%>
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileNameFBType}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileNameFBType}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileNameFBType}.xls" /> 
			
	</display:table>
</div>

<br/>
<h3>2. Báo cáo xử lý Feedback theo khu vực Tổ Viễn Thông</h3>
<div style="width:100%;overflow:auto;"  class="tableStandar">
	<display:table name="${reportFBDVTProcess}"   id="itemDVTProcess" requestURI="" pagesize="50" sort="external" export="true" >
		<display:column class="centerColumnMana" titleKey="global.list.No"> <c:out value="${itemDVTProcess_rowNum}"/> </display:column>
		<c:choose>
			<c:when test="${function == 'week'}">
				<display:column property="week" titleKey="baoCaoSoLuongPA.week" />
			</c:when>
			<c:when test="${function == 'month'}">
				<display:column property="mon" titleKey="baoCaoSoLuongPA.mon" sortable="true"  />
			</c:when>
			<c:when test="${function == 'quarter'}">
				<display:column property="quarters" titleKey="baoCaoSoLuongPA.quarters" sortable="true"  />
			</c:when>
			<c:when test="${function == 'year'}">
				<display:column property="years" titleKey="baoCaoSoLuongPA.years" sortable="true"  />
			</c:when>
			<c:otherwise>
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="baoCaoSoLuongPA.ngay"/>
			</c:otherwise>
		</c:choose>
		<display:column property="region" titleKey="qLThongTinPhanAnh.region" sortable="true"  />
		<display:column property="dept" titleKey="qLThongTinPhanAnh.dept" sortable="true"  />
		<display:column property="team" titleKey="qLThongTinPhanAnh.team" sortable="true"  />
		<display:column class="rightColumnMana" property="num2G" titleKey="baoCaoSoLuongPA.num2G"  />
		<display:column class="rightColumnMana" property="num3G" titleKey="baoCaoSoLuongPA.num3G"  />
		<display:column class="rightColumnMana" property="num4G" titleKey="baoCaoSoLuongPA.num4G"  />
		<display:column class="rightColumnMana" property="total" titleKey="baoCaoSoLuongPA.total"  />
		<display:column class="rightColumnMana" property="total_1" titleKey="baoCaoSoLuongPA.${function}_total_1"  />
		<c:choose>
			<c:when test="${function  != 'dy'}">
			<display:column class="rightColumnMana" property="chuaXuLy_1" titleKey="baoCaoSoLuongPA.${function}_chuaXuLy_1"  />
			<display:column class="rightColumnMana" property="canXuLy" titleKey="baoCaoSoLuongPA.canXuLy"  />
			<display:column class="rightColumnMana" property="daXuLyTrongHan" titleKey="baoCaoSoLuongPA.daXuLyTrongHan"  />
			<display:column class="rightColumnMana" property="daXuLyQuaHan" titleKey="baoCaoSoLuongPA.daXuLyQuaHan"  />
			<display:column class="rightColumnMana" property="daXuLy" titleKey="baoCaoSoLuongPA.daXuLy"  />
	  		<display:column class="rightColumnMana" property="chuaXuLyTrongHan" titleKey="baoCaoSoLuongPA.chuaXuLyTrongHan"  />
	  		<display:column class="rightColumnMana" property="chuaXuLyQuaHan" titleKey="baoCaoSoLuongPA.chuaXuLyQuaHan"  />
	  		<display:column class="rightColumnMana" property="chuaXuLy" titleKey="baoCaoSoLuongPA.chuaXuly"  />
	  		<display:column class="rightColumnMana" property="tlXuLy" titleKey="baoCaoSoLuongPA.tlXuLy"/>
	  	</c:when>
			<c:otherwise>
	  		<display:column class="rightColumnMana" property="soSanhTong" titleKey="baoCaoSoLuongPA.soSanhTong"  />
	  		<display:column class="rightColumnMana" property="daXuLyTrongHan" titleKey="baoCaoSoLuongPA.daXuLyTrongHan"  />
	  		<display:column class="rightColumnMana" property="daXuLy" titleKey="baoCaoSoLuongPA.daXuLy"  />
	  		<display:column class="rightColumnMana" property="chuaXuLyTrongHan" titleKey="baoCaoSoLuongPA.chuaXuLyTrongHan"  />
	  		</c:otherwise>
		</c:choose>
		<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileNameDVTProcess}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileNameDVTProcess}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileNameDVTProcess}.xls" /> 
			
	</display:table>
</div>

<br/>
<h3>3. Báo cáo xử lý Feedback chất lượng mạng lưới (Feedback Request - lũy kế từ đầu năm)</h3>
<div style="width:100%;overflow:auto;"  class="tableStandar">
	<display:table name="${reportFBRequest}"   id="itemRequest" requestURI="" pagesize="50" sort="external" export="true" >
		<display:column class="centerColumnMana" titleKey="global.list.No"> <c:out value="${itemRequest_rowNum}"/> </display:column>
		<display:column property="dept" titleKey="qLThongTinPhanAnh.dept" sortable="true"  />
		<display:column property="team" titleKey="qLThongTinPhanAnh.team" sortable="true"  />
		<display:column class="rightColumnMana" property="total" titleKey="baoCaoSoLuongPA.total"  />
		<display:column class="rightColumnMana" property="guiDVT" titleKey="baoCaoSoLuongPA.guiDVT"  />
		<display:column class="rightColumnMana" property="canXuLy" titleKey="baoCaoSoLuongPA.canXuLy"  />
		<display:column class="rightColumnMana" property="trongHan" titleKey="baoCaoSoLuongPA.trongHan"  />
		<display:column class="rightColumnMana" property="tLTrongHan" titleKey="baoCaoSoLuongPA.tLTrongHan"/>
		<display:column class="rightColumnMana" property="quaHan" titleKey="baoCaoSoLuongPA.quaHan"  />
		<display:column class="rightColumnMana" property="tLQuaHan" titleKey="baoCaoSoLuongPA.tLQuaHan"/>
  		<display:column class="rightColumnMana" property="dangXuLy" titleKey="baoCaoSoLuongPA.dangXuLy"  />
  		<display:column class="rightColumnMana" property="tLDangXuLy" titleKey="baoCaoSoLuongPA.tLDangXuLy"/>
	  		
		<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileNameRequest}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileNameRequest}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileNameRequest}.xls" /> 
			
	</display:table>
</div>

<br/>
<h3>4. Feedback tăng cao</h3>
<div style="width:100%;overflow:auto;"  class="tableStandar">
	<display:table name="${reportFBRise}"   id="itemRise" requestURI="" pagesize="50" sort="external" export="true" >
		<display:column class="centerColumnMana" titleKey="global.list.No"> <c:out value="${itemRise_rowNum}"/> </display:column>
		<c:choose>
			<c:when test="${function == 'week'}">
				<display:column property="week" titleKey="baoCaoSoLuongPA.week" />
			</c:when>
			<c:when test="${function == 'month'}">
				<display:column property="mon" titleKey="baoCaoSoLuongPA.mon" sortable="true"  />
			</c:when>
			<c:when test="${function == 'quarter'}">
				<display:column property="quarters" titleKey="baoCaoSoLuongPA.quarters" sortable="true"  />
			</c:when>
			<c:when test="${function == 'year'}">
				<display:column property="years" titleKey="baoCaoSoLuongPA.years" sortable="true"  />
			</c:when>
			<c:otherwise>
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="baoCaoSoLuongPA.ngay"/>
			</c:otherwise>
		</c:choose>
		<display:column property="fbContent" titleKey="qLThongTinPhanAnh.fbContent" sortable="true"  />
		<display:column class="rightColumnMana" property="num2G" titleKey="baoCaoSoLuongPA.num2G"  />
		<display:column class="rightColumnMana" property="num3G" titleKey="baoCaoSoLuongPA.num3G"  />
		<display:column class="rightColumnMana" property="num4G" titleKey="baoCaoSoLuongPA.num4G"  />
		<display:column class="rightColumnMana" property="total" titleKey="baoCaoSoLuongPA.total"  />
		<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileNameRise}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileNameRise}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileNameRise}.xls" /> 
			
	</display:table>
</div>

<div id="jqxwindowGUI_MAIL">
			<div><b>Báo cáo Feedback qua email</b></div>
			<div>
			<table class="simple2">
				<tr>
					<td style="width:140px;text-align:left; padding-left:10px">Người nhận</td>
					<td style="width:150px;">
						<div id="sendToCb" align="left"></div>
					</td>
					<td><input type="text" name="e_email" id="e_email"  value="${email}"/></td>
				</tr>
				<tr>
					<td style="width:140px;text-align:left; padding-left:10px">Tiêu đề mail:</td>
					<td colspan="2"><input type="text" name="subject" id="subject"  value="${subject}"/></td>
				</tr>
				<tr>
					<td style="width:140px;text-align:left; padding-left:10px">Đề xuất & Kiến nghị</td>
					<td colspan="2">
						<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/ckeditor.js"></script>
						<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/sample.js"></script>
						<textarea style="width:100%; height: 220px" name="e_content" id="e_content" maxlength="900" >${e_content}</textarea>
						<script type="text/javascript">
							CKEDITOR.replace( 'e_content',
							{
								toolbar :
									[
										['NewPage','-','Undo','Redo'],
										['Find','Replace','-','SelectAll','RemoveFormat'],
										['Link', 'Unlink', 'Image'],
										['FontSize', 'Bold', 'Italic','Underline'],
										['NumberedList','BulletedList','-','Blockquote'],
										['TextColor', '-', 'Smiley','SpecialChar', '-', 'Maximize']
									]
							});
					  	</script>
					</td>
				</tr>
				
				<tr>
					<td></td>
					<td style="text-align: left">
						<input type="button" id="btnEmailSubmit" value="Gửi" class="button">
					</td>
					<td>
						<span style="display:none;" id="statusSumit"><img height="10px" src="${pageContext.request.contextPath}/images/loading.gif"></span>
					</td>
				</tr>
	       </table>
	     </div>
	</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script type="text/javascript">
var func = '<c:out value='${funct}'/>'
 if (func=='dy' || func=='luy-ke'|| func=='option' ){
	Calendar.setup({
	    inputField		:	"sdate",	// id of the input field
	    ifFormat		:	"%d/%m/%Y",   	// format of the input field
	    button			:   "chooseSDate",  	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	}); 
 }
 if ( func=='luy-ke'|| func=='option' ){
	Calendar.setup({
	    inputField		:	"edate",	// id of the input field
	    ifFormat		:	"%d/%m/%Y",   	// format of the input field
	    button			:   "chooseEDate",  	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	});
} 
else if (func=='week')
{
	Calendar.setup({
	    inputField		:	"sweek",	// id of the input field
	    ifFormat		:	"%W",   	// format of the input field
	    button			:   "chooseStartWeek",  	// trigger for the calendar (button ID)
	    singleClick		:   false					// double-click mode
	});
	/* Calendar.setup({
	    inputField		:	"eweek",	// id of the input field
	    ifFormat		:	"%W",   	// format of the input field
	    button			:   "chooseEndWeek",  	// trigger for the calendar (button ID)
	    singleClick		:   false					// double-click mode
	}); */
}

</script>
<script type="text/javascript">
$(document).ready(function(){
	
	var theme =  getTheme();	
		//combobox region
		var urlRegion = "${pageContext.request.contextPath}/ajax/getRegionList.htm";
		var sourceRegion =
		{
		   datatype: "json",
		   url: urlRegion,
		   datafields: [
		                 { name: 'name'},
		                 { name: 'value'}
		             ],
		    async: false
		};
		var dataAdapterregion = new $.jqx.dataAdapter(sourceRegion);
		$("#region").jqxDropDownList({source: dataAdapterregion, displayMember: "value", valueMember: "name",checkboxes: true, width: 120, autoDropDownHeight: true, theme: theme,enableHover: true });           
		
		var cbregion = '<c:out value="${region}"/>';
		// alert(dept);
		if(cbregion=="")
			$('#region').val('Choose');
		else
		{
			var regionVar = cbregion.split(",");
			if (regionVar != null) {
				for (var i=0; i<regionVar.length; i++) {
					$("#region").jqxDropDownList('checkItem', regionVar[i] ); 
				}
			}
		}  
		var region = $("#region").val();
		//combobox province
		var urlprovince = "${pageContext.request.contextPath}/ajax/getProvinceList.htm?region="+region;
		var sourceprovince =
		{
		   datatype: "json",
		   url: urlprovince,
		   datafields: [
		                 { name: 'province'}
		             ],
		    async: false
		};
		var dataAdapterprovince = new $.jqx.dataAdapter(sourceprovince);
		$("#province").jqxDropDownList({source: dataAdapterprovince, displayMember: "province", valueMember: "province",checkboxes: true, width: 120, autoDropDownHeight: true, theme: theme,enableHover: true });           
		
		var cbprovince = '<c:out value="${province}"/>';
		// alert(dept);
		if(cbprovince=="")
			$('#province').val('Choose');
		else
		{
			var provinceVar = cbprovince.split(",");
			if (provinceVar != null) {
				for (var i=0; i<provinceVar.length; i++) {
					$("#province").jqxDropDownList('checkItem', provinceVar[i] ); 
				}
			}
		}  
		
		//combobox fbType
		var urlfbType = "${pageContext.request.contextPath}/ajax/getFBTypeList.htm";
		var sourceFbType =
		{
		   datatype: "json",
		   url: urlfbType,
		   datafields: [
		                 { name: 'code'},
		                 { name: 'name'}
		             ],
		    async: false
		};
		var dataAdapterFbType = new $.jqx.dataAdapter(sourceFbType);
		$("#fbType").jqxDropDownList({source: dataAdapterFbType, displayMember: "name", valueMember: "code",checkboxes: true, width: 170, autoDropDownHeight: true,height: '25', theme: theme,enableHover: true });           
		
		var cbfbType = '<c:out value="${fbType}"/>';
		// alert(dept);
		if(cbfbType=="")
			$('#fbType').val('Choose');
		else
		{
			var fbTypeVar = cbfbType.split(",");
			if (fbTypeVar != null) {
				for (var i=0; i<fbTypeVar.length; i++) {
					$("#fbType").jqxDropDownList('checkItem', fbTypeVar[i] ); 
				}
			}
		}  
		
		
		//combobox fb status
		var urlFbStatus = "${pageContext.request.contextPath}/ajax/getStatusFBList.htm";
		var sourceFbStatus =
		{
		   datatype: "json",
		   url: urlFbStatus,
		   datafields: [
		                 { name: 'code'},
		                 { name: 'name'}
		             ],
		    async: false
		};
		var dataAdapterFbStatus = new $.jqx.dataAdapter(sourceFbStatus);
		$("#status").jqxDropDownList({source: dataAdapterFbStatus, displayMember: "name", valueMember: "code", width: 120, autoDropDownHeight: true, theme: theme,enableHover: true });           
		
		var cbstatus = '<c:out value="${status}"/>';
		// alert(dept);
		if(cbstatus=="")
			$('#status').val('Choose');
		else
		{
			$('#status').val(cbstatus);
		}  
		

		// gui email
		 $("#subject").jqxInput({ height: 20, width: '100%', theme: theme});
		////Create a jqxDropDownList user to send mail
			var renderer = function (itemValue, inputValue) {
		        var terms = inputValue.split(/,\s*/);
		        // remove the current input
		        terms.pop();
		        // add the selected item
		        terms.push(itemValue);
		        // add placeholder to get the comma-and-space at the end
		        terms.push("");
		        var value = terms.join(",");
		        return value;
		    };
		   ${emailList}
		    $("#e_email").jqxInput({ height: 20, width: '100%', theme: theme,
		        source: function (query, response) {
		            var item = query.split(/,\s*/).pop();
		            // update the search query.
		            $("#e_email").jqxInput({ query: item });
		            response(emailList);
		        },
		        renderer: renderer
		    });
		    
		  //Create a jqxDropDownList
		    var urlGroupEmail = "${pageContext.request.contextPath}/ajax/getGroupEmail.htm";
		    // prepare the data
		    var sourceGroupEmail =
		    {
		        datatype: "json",
		        url: urlGroupEmail,
		        datafields: [
		                     { name: 'groupName'},
		                     { name: 'groupUser'}
		                 ],
		        async: false
		    };
		    var dataAdapterGroupEmail = new $.jqx.dataAdapter(sourceGroupEmail);
		    
		    $("#sendToCb").jqxComboBox({ source: dataAdapterGroupEmail,displayMember: "groupName", valueMember: "groupUser", height: 20, width: '100%', theme: theme ,autoDropDownHeight: true });
		    $('#sendToCb').on('select', function (event) 
		  		{
		  			var itemB= $('#e_email').val();
		  			var item = $("#sendToCb").jqxComboBox('getSelectedItem');
		  			
		  			if (itemB!='')
		  				{
		  					if (itemB.indexOf(item.value)==-1)
		  						{itemB+=","+item.value ;}
		  				}
		  			else
		  				{
		  					itemB=item.value ;
		  				}
		  			$("#e_email").val(itemB);
		  			//alert(itemB);
		  		});
		    var region = '<c:out value="${region}"/>';
		    var sdate = '<c:out value="${sdate}"/>';
		    var edate = '<c:out value="${edate}"/>';
		    var sweek = '<c:out value="${sweek}"/>';
		    var smonth = '<c:out value="${smonth}"/>';
		    var syear = '<c:out value="${syear}"/>';
		    var province = '<c:out value="${province}"/>';
		    var fbType = '<c:out value="${fbType}"/>';
		    var status = '<c:out value="${status}"/>';
		    var sqlWhere = '<c:out value="${sqlWhere}"/>';
		    var func = '<c:out value="${function}"/>';
		    var titlePage = '<c:out value="${title}"/>';
		    $('#btnEmailSubmit').jqxButton({ theme: theme });
		    $('#btnEmailSubmit').click(function () {
		    		//alert(content);
		    		var content	= CKEDITOR.instances['e_content'].getData();
		    		var email	= $('#e_email').val();
		    		var subject	= $('#subject').val();
		    		$.ajax({
		    			type: "POST",
		    			url: "${pageContext.request.contextPath}/report-feedback/sendMail.htm",
		    			data:{ 
		    				//tham so thong tin gui mail
		    				content: encodeURI(content), 
		    				subject: encodeURI(subject),
		    				email: encodeURI(email),
		    				titlePage: encodeURI(titlePage),
		    				//tham so tim kiem theo trang
		    				sdate: encodeURI(sdate),
		    				edate: encodeURI(edate),
		    				sweek: encodeURI(sweek),
		    				smonth: encodeURI(smonth),
		    				syear: encodeURI(syear),
		    				region: encodeURI(region),
		    				province: encodeURI(province),
		    				fbType: encodeURI(fbType),
		    				status: encodeURI(status),
		    				sqlWhere: encodeURI(sqlWhere),
		    				func: encodeURI(func)
		    			},
		    			error: function(){
		    				$('#statusSumit').css("display","none");
		    				alert('Quá trình tải dữ liệu bị lỗi. Vui lòng kiểm tra mật khẩu');
		    			},
		    			beforeSend: function(){
		    				//toggleStatusMessage('Đang tải dữ liệu..');
		    				$('#statusSumit').css("display","block");
		    			},
		    			
		    			complete: function(){},
		    			
		    			success: function(data){
		    				$('#statusSumit').css("display","none");
		    				
		    				if (data.value == '1') {
		    					alert('Cập nhật thông tin gửi email thành công. Vui lòng chờ trong ít phút.');
		    			//		$("#formEmail").dialog('close');
		    				} else {
		    					alert('Xảy ra lỗi trong quá trình gửi email');
		    				}
		    			}
		    		});
		    		
		    });		
});
</script>
 <script type="text/javascript">
   $(document).ready(function(){
	   var fun= '<c:out value="${function}"/>';
	   
		var trs='<tr><th rowspan="2">STT</th>';
		if (fun == 'week')
			trs=trs + '<th rowspan="2">Tuần</th>';
		else if (fun == 'month')
			trs=trs + '<th rowspan="2">Tháng</th>';
		else if (fun == 'quarter')
			trs=trs + '<th rowspan="2">Quý</th>';
		else if (fun == 'year')
			trs=trs + '<th rowspan="2">Năm</th>';
		else
			trs=trs + '<th rowspan="2">Ngày</th>';
		trs=trs + '<th rowspan="2">Loại phản ánh</th>';
		
	    if (fun != 'dy')
    	{
	    	trs=trs + '<th rowspan="2">Tổng PAKH</th>';
	    	if (fun == 'week')
	    		trs=trs + '<th colspan="2">Tuần trước</th>';
			else if (fun == 'month')
				trs=trs + '<th colspan="2">Tháng trước</th>';
			else if (fun == 'quarter')
				trs=trs + '<th colspan="2">Quý trước</th>';
			else if (fun == 'year')
				trs=trs + '<th colspan="2">Năm trước</th>';
	    	trs=trs + '<th rowspan="2">Cần xử lý</th>';
		    trs=trs + '<th colspan="3">Đã xử lý</th>';
		    trs=trs + '<th colspan="3">Chưa xử lý</th>';
		    trs=trs + '<th rowspan="2">Tỷ lệ xử lý PAKH</th>';
		    trs=trs + '<th colspan="2">Tỷ lê trong hạn</th>';
		    trs=trs + '<th colspan="2">So sánh</th>';
		    trs=trs + '</tr>';
		    trs=trs +'<tr><th >Tổng PAKH</th>';
		    trs=trs +'<th >Chưa xử lý</th>';
		    trs=trs +'<th >Trong hạn</th>';
		    trs=trs +'<th >Quá hạn</th>';
		    trs=trs +'<th >Tổng</th>';
		    trs=trs +'<th >Trong hạn</th>';
		    trs=trs +'<th >Quá hạn</th>';
		    trs=trs +'<th >Tổng</th>';
		    if (fun == 'week')
		    {
			 	trs=trs +'<th >Tuần này</th>';
			 	trs=trs +'<th >Tuần trước</th>';
			    
			}
			else if (fun == 'month')
			{
			 	trs=trs +'<th >Tháng này</th>';
			 	trs=trs +'<th >Tháng trước</th>';
			    
			}
			else if (fun == 'quarter')
			{
			 	trs=trs +'<th >Quý này</th>';
			 	trs=trs +'<th >Quý trước</th>';
			    
			}
			else if (fun == 'year')
			{
			 	trs=trs +'<th >Năm này</th>';
			 	trs=trs +'<th >Năm trước</th>';
			    
			}
			
		    trs=trs +'<th >Tỷ lệ</th>';
		    trs=trs +'<th >Tổng PAKH</th>';
			trs=trs +'</tr>';
		    
    	}
	    else
    	{
	    	trs=trs + '<th rowspan="2">2G</th>';
		    trs=trs + '<th rowspan="2">3G</th>';
		    trs=trs + '<th rowspan="2">4G</th>';
		    trs=trs + '<th rowspan="2">Tổng</th>';
		    trs=trs + '<th colspan="3">Thời gian xử lý (Phút)</th>';
		    trs=trs + '</tr>';
		    trs=trs +'<tr><th >TP HCM</th>';
		    trs=trs +'<th >Miền Đông</th>';
		    trs=trs +'<th >Miền Tây</th>';
			trs=trs +'</tr>';
    	}

		$('#itemreportFBFBType thead').html(trs);
		
		var trs='<tr><th rowspan="2">STT</th>';
		trs=trs + '<th rowspan="2">Đài VT</th>';
		trs=trs + '<th rowspan="2">Tổ VT</th>';
	    trs=trs + '<th rowspan="2">Tổng</th>';
	    trs=trs + '<th rowspan="2">Gửi ĐVT</th>';
	    trs=trs + '<th rowspan="2">Cần xử lý</th>';
	    trs=trs + '<th colspan="2">Xử lý trong hạn</th>';
	    trs=trs + '<th colspan="2">Xử lý quá hạn</th>';
	    trs=trs + '<th colspan="2">Đang xử lý</th>';
	    trs=trs + '</tr>';
	   	
	    trs=trs +'<tr><th >Số lượng</th>';
	    trs=trs +'<th >Tỷ lệ</th>';
	    trs=trs +'<th >Số lượng</th>';
	    trs=trs +'<th >Tỷ lệ</th>';
	    trs=trs +'<th >Số lượng</th>';
	    trs=trs +'<th >Tỷ lệ</th>';
		trs=trs +'</tr>';
	    
		$('#itemRequest thead').html(trs);
		
		
	    if (fun != 'dy')
    	{
	    	var trs='<tr><th rowspan="2">STT</th>';
			if (fun == 'week')
				trs=trs + '<th rowspan="2">Tuần</th>';
			else if (fun == 'month')
				trs=trs + '<th rowspan="2">Tháng</th>';
			else if (fun == 'quarter')
				trs=trs + '<th rowspan="2">Quý</th>';
			else if (fun == 'year')
				trs=trs + '<th rowspan="2">Năm</th>';
			else
				trs=trs + '<th rowspan="2">Ngày</th>';
			trs=trs + '<th rowspan="2">Khu vực</th>';
	    	trs=trs + '<th rowspan="2">Đài VT</th>';
		    trs=trs + '<th rowspan="2">Tổ VT</th>';
		    trs=trs + '<th rowspan="2">2G</th>';
		    trs=trs + '<th rowspan="2">3G</th>';
		    trs=trs + '<th rowspan="2">4G</th>';
		    trs=trs +'<th rowspan="2" >Tổng PAKH</th>';
		    if (fun == 'week')
		    	trs=trs + '<th colspan="2">Tuần trước</th>';
			else if (fun == 'month')
				trs=trs + '<th colspan="2">Tháng trước</th>';
			else if (fun == 'quarter')
				trs=trs + '<th colspan="2">Quý trước</th>';
			else if (fun == 'year')
				trs=trs + '<th colspan="2">Năm trước</th>';
			else
				trs=trs + '<th rowspan="2">Ngày hôm qua</th>';
		    trs=trs + '<th rowspan="2">Cần xử lý</th>';
		    trs=trs + '<th colspan="3">Đã xử lý</th>';
		    trs=trs + '<th colspan="3">Chưa xử lý</th>';
		    trs=trs + '<th rowspan="2">Tỷ lệ xử lý PAKH</th>';
		    trs=trs + '</tr>';
		    trs=trs +'<tr><th >Tổng PAKH</th>';
		    trs=trs +'<th >Chưa xử lý</th>';
		    trs=trs +'<th >Trong hạn</th>';
		    trs=trs +'<th >Quá hạn</th>';
		    trs=trs +'<th >Tổng</th>';
		    trs=trs +'<th >Trong hạn</th>';
		    trs=trs +'<th >Quá hạn</th>';
		    trs=trs +'<th >Tổng</th>';
			trs=trs +'</tr>';
			 $('#itemDVTProcess thead').html(trs);
    	}
	   
		
	});
</script>
