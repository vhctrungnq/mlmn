<%@ include file="/includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title>THỜI GIAN CHẠY MÁY NỔ (${loaiThanhToan})</title>
<!-- <content tag="heading"> THỜI GIAN CHẠY MÁY NỔ</content>     -->
 <h2 > DANH SÁCH MÁY ${loaiThanhToan} TÌM THẤY</h2> 

<table style = "border:0; width:100%;cellspacing:0; cellpadding:0;class:form">  
    <tr>
        <td style = "align:left;">
            <form:form method = "get" action = "list.htm">
            <input type = "hidden" name = "loaiThanhToan" value = "${loaiThanhToan }" />
                <table> 
                    <tr>
                        <td> Ngày chạy máy phát từ </td>
                        <td> <input id = "sdate" value = "${sdate}" name = "sdate"/> 
                                <img alt="calendar" title="Chon ngay bat dau" id="chooseSdate" 
                                style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
                        </td>
                        <td style="padding-left: 30px;">  đến </td>
                        <td> <input id = "edate" value = "${edate}" name = "edate"/> 
                                <img alt="calendar" title="Chon ngay ket thuc" id="chooseEdate" 
                                style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
                        </td>
                        <td style="padding-left: 30px;">
                            <input class="button" type="submit" id="submit" value="Tìm kiếm" />
                        </td>
                    </tr>
                </table>
            </form:form>
        
    </tr>
    <tr>
        <td>
                <div id='jqxWidget' style="margin-top:5px;">
        
                <div style="float: right;margin-bottom:3px;width:300px;">
                        <table style = "border:0; cellspacing:1; cellpadding:0; width:100%;">
                            <tr>
                                <td align="right">
                                    <input type="button" value="<fmt:message key="global.button.addNew"/>" id='addNew' />
                                    <input type="button" value="<fmt:message key="qldn.import"/>" id='importFile' />
                                </td>           
                                <td style="width:33px"><div style="float: right;" id="jqxlistbox"></div></td>
                            </tr>   
                    </table>
                </div><br>  
               
                <div id="jqxgrid"></div>
                <div id='Menu'>
                    <ul>
                        <li><fmt:message key="global.button.editSelected" /></li>
                        <li><fmt:message key="global.button.deleteMultiSelected" /></li>
                        <li><fmt:message key="global.button.addNew" /></li>
                
                        <li><fmt:message key="global.button.exportExcel" /></li>
                    </ul>
                </div>
            </div>
        </td>
    </tr>


</table>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqx-all.js"></script>
<script type="text/javascript">
    var theme = getTheme();
    ${gridManage}
    var loaiThanhToan = "<c:out value = '${loaiThanhToan}'/>";
    //$('#submit').jqxButton({ theme: theme });
    $('#addNew').jqxButton({ theme: theme });
    $('#importFile').jqxButton({ theme: theme });
    $('#addNew').click(function () {    
        window.location = 'form.htm?loaiThanhToan=' + loaiThanhToan;
        
    });
     $('#importFile').click(function () {
        window.location = ' ${pageContext.request.contextPath}/import-qldn/upload.htm?typeFile=QLDN_TRAM_TT_NHIEN_LIEU';
    }); 
    
    // handle context menu clicks.
    $("#Menu").on('itemclick', function (event) {
        var args = event.args;
     // add new row
        if ($.trim($(args).text()) == '<fmt:message key="global.button.addNew"/>') {    
            window.location = 'form.htm?loaiThanhToan=' + loaiThanhToan;
        }
        if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
            var rowindex = $("#jqxgrid").jqxGrid('getselectedrowindex');
            var row = $("#jqxgrid").jqxGrid('getrowdata', rowindex);
           // alert(row.id);
            window.location = 'form.htm?id='+row.id + "&loaiThanhToan=" + loaiThanhToan;;   
             
        }
        if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteMultiSelected"/>')  {
            var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
            if (answer)
            {
                var selectedrowindexes = $('#jqxgrid').jqxGrid('getselectedrowindexes'); 
                var idList="";
                var cond="";
                //alert(selectedrowindexes);
                //var rowIndexList = selectedrowindexes.split(",");
                if (selectedrowindexes != null) {
                    for (var i=0; i<selectedrowindexes.length; i++) {
                        var row = $("#jqxgrid").jqxGrid('getrowdata', selectedrowindexes[i]);
                        idList+=cond+row.id;
                        cond=",";
                    }
//                  alert("<c:out value = '${caTruc}'/>");
                    window.location = 'delete.htm?idList='+idList + "&loaiThanhToan=" + loaiThanhToan;    
                }
            }
        }
        
        var exportFileName =  "${exportFileName}";
        if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
            $("#jqxgrid").jqxGrid('exportdata', 'xls', exportFileName);
        }
        
    });
      
</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
<script>


$(document).ready(function(){
    Calendar.setup({
        inputField      :   "sdate",    // id of the input field
        ifFormat        :   "%d/%m/%Y",     // format of the input field
        button          :   "chooseSdate",      // trigger for the calendar (button ID)
        showsTime       :   true,
        singleClick     :   false                   // double-click mode
    }); 
    
    Calendar.setup({
        inputField      :   "edate",    // id of the input field
        ifFormat        :   "%d/%m/%Y",     // format of the input field
        button          :   "chooseEdate",      // trigger for the calendar (button ID)
        showsTime       :   true,
        singleClick     :   false                   // double-click mode
    }); 

});
</script>
