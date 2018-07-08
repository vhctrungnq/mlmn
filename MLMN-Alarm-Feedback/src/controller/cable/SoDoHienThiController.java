package controller.cable;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dao.CableOdfManagementsDAO;
import dao.CableOdfTypesDAO;
import dao.SYS_PARAMETERDAO;

import vo.CableOdfManagements;
import vo.CableOdfTypes;
import vo.SYS_PARAMETER;

@Controller
@RequestMapping("/cable/so-do-hien-thi-odf-lien-tang/*")
public class SoDoHienThiController {

	@Autowired
	private CableOdfTypesDAO cableOdfTypesDAO;
	@Autowired
	private CableOdfManagementsDAO cableOdfManagementsDAO;
	@Autowired
 	private SYS_PARAMETERDAO sysParameterDao;
	
	@RequestMapping(value="list")
    public String list(@RequestParam(required = false) String idOdfTypes, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		List<CableOdfTypes> cableOdfTypesList = cableOdfTypesDAO.getCableOdfTypesList();
		model.addAttribute("cableOdfTypesList", cableOdfTypesList);
		model.addAttribute("idOdfTypesCBB", idOdfTypes);
		
		
		List<SYS_PARAMETER> colorOdfLienTang = sysParameterDao.getColorOdfLienTang();
		
		String html = "";
		String tinyTips = "";
		if(idOdfTypes != null && idOdfTypes != ""){
			if(colorOdfLienTang.size() > 4){
				CableOdfTypes cableOdfTypes = cableOdfTypesDAO.selectByPrimaryKey(Integer.parseInt(idOdfTypes));
				List<CableOdfManagements> cableOdfManList = cableOdfManagementsDAO.testInsertPortSchemaname(idOdfTypes, null, null);
				html += "<div align=\"center\">";
				tinyTips = tinyTipsFun(tinyTips, cableOdfManList);
				html += htmlSoDo(cableOdfTypes, cableOdfManList, colorOdfLienTang);
				html +="</div>";
			}
			
		}
		else{
			if(colorOdfLienTang.size() > 4){
				html = "<table border=\"0\" style=\"margin: 0 auto;width:100%;\" cellspacing=\"10\" cellpadding=\"0\">";
				html += "<tr><td class=\"wid50\"></td><td class=\"wid50\"></td></tr>";
				
				if((cableOdfTypesList.size()%2) == 1){
					int j = cableOdfTypesList.size() - 1;
					String html1 = "";
					for(int i=j-1;i>0;i-=2){
						String record = "<tr><td align=\"left\"><h3>" + cableOdfTypesList.get(i-1).getSchemaName() + "</h3></td><td align=\"left\"><h3>" + cableOdfTypesList.get(i).getSchemaName() + "</h3></td></tr>";
						record += "<tr><td style=\"vertical-align:top\"><div align=\"left\">";
						CableOdfTypes cableOdfTypes1 = cableOdfTypesDAO.selectByPrimaryKey(cableOdfTypesList.get(i-1).getId());
						List<CableOdfManagements> cableOdfManList1 = cableOdfManagementsDAO.testInsertPortSchemaname(cableOdfTypesList.get(i-1).getId().toString(), null, null);
						if(cableOdfTypes1 != null && cableOdfManList1.size() > 0){
							tinyTips += tinyTipsFun(tinyTips, cableOdfManList1);
							record += htmlSoDo(cableOdfTypes1, cableOdfManList1, colorOdfLienTang);
						}
						record += "</div></td><td style=\"vertical-align:top\"><div align=\"left\">";
						CableOdfTypes cableOdfTypes2 = cableOdfTypesDAO.selectByPrimaryKey(cableOdfTypesList.get(i).getId());
						List<CableOdfManagements> cableOdfManList2 = cableOdfManagementsDAO.testInsertPortSchemaname(cableOdfTypesList.get(i).getId().toString(), null, null);
						if(cableOdfTypes2 != null && cableOdfManList2.size() > 0){
							tinyTips += tinyTipsFun(tinyTips, cableOdfManList2);
							record += htmlSoDo(cableOdfTypes2, cableOdfManList2, colorOdfLienTang);
						}
						record += "</div></td></tr>";
						html1 = record + html1;
					}
					html += html1;
					html += "<tr><td align=\"center\" colspan=\"2\"><h3>" + cableOdfTypesList.get(j).getSchemaName() + "</h3></td></tr>";
					html += "<tr><td colspan=\"2\"><div align=\"center\">";
					CableOdfTypes cableOdfTypes = cableOdfTypesDAO.selectByPrimaryKey(cableOdfTypesList.get(j).getId());
					List<CableOdfManagements> cableOdfManList = cableOdfManagementsDAO.testInsertPortSchemaname(cableOdfTypesList.get(j).getId().toString(), null, null);
					if(cableOdfTypes != null && cableOdfManList.size() > 0){
						tinyTips += tinyTipsFun(tinyTips, cableOdfManList);
						html += htmlSoDo(cableOdfTypes, cableOdfManList, colorOdfLienTang);
					}
					html += "</div></td></tr>";
				}
				else{
					for(int i=0;i<cableOdfTypesList.size() - 1;i+=2){
						String record = "<tr><td align=\"left\"><h3>" + cableOdfTypesList.get(i).getSchemaName() + "</h3></td><td align=\"left\"><h3>" + cableOdfTypesList.get(i+1).getSchemaName() + "</h3></td></tr>";
						record += "<tr><td style=\"vertical-align:top\"><div align=\"left\">";
						CableOdfTypes cableOdfTypes1 = cableOdfTypesDAO.selectByPrimaryKey(cableOdfTypesList.get(i).getId());
						List<CableOdfManagements> cableOdfManList1 = cableOdfManagementsDAO.testInsertPortSchemaname(cableOdfTypesList.get(i).getId().toString(), null, null);
						if(cableOdfTypes1 != null && cableOdfManList1.size() > 0){
							tinyTips += tinyTipsFun(tinyTips, cableOdfManList1);
							record += htmlSoDo(cableOdfTypes1, cableOdfManList1, colorOdfLienTang);
						}
						
						record += "</div></td><td style=\"vertical-align:top\"><div align=\"left\">";
						CableOdfTypes cableOdfTypes2 = cableOdfTypesDAO.selectByPrimaryKey(cableOdfTypesList.get(i+1).getId());
						List<CableOdfManagements> cableOdfManList2 = cableOdfManagementsDAO.testInsertPortSchemaname(cableOdfTypesList.get(i+1).getId().toString(), null, null);
						if(cableOdfTypes2 != null && cableOdfManList2.size() > 0){
							tinyTips += tinyTipsFun(tinyTips, cableOdfManList2);
							record += htmlSoDo(cableOdfTypes2, cableOdfManList2, colorOdfLienTang);
						}
						record += "</div></td></tr>";
						
						html += record;
					}
				}
				
				html += "</table>";
			}
		}
		
		model.addAttribute("tinyTips", tinyTips);
		model.addAttribute("html", html);
		
		return "jspcable/soDoHienThiOdf";
	}
	
	private String tinyTipsFun(String tinyTips, List<CableOdfManagements> cableOdfManList){
		for(int i =0;i<cableOdfManList.size();i++){
			tinyTips += "<script type=\"text/javascript\">";
			tinyTips += "$(document).ready(function() {";
			tinyTips +="$('a.imgTip_" +  cableOdfManList.get(i).getId() + "').tinyTips('green',";
			tinyTips +="'<b>Vị trí/port:</b> "+ cableOdfManList.get(i).getPort() +"<br/>' +";
			tinyTips +="'<b>Tên:</b> ";
			if(cableOdfManList.get(i).getName() != null)
				tinyTips += cableOdfManList.get(i).getName();
			tinyTips += "<br/>' +";
			tinyTips +="'<b>Trạng thái:</b> "+ cableOdfManList.get(i).getNameTrangThai() +"<br/>'";
			tinyTips +=" + '<b>Diễn giải:</b> ";
			if(cableOdfManList.get(i).getDescription() != null)
				tinyTips += cableOdfManList.get(i).getDescription();
			tinyTips +="<br/>'";
			tinyTips +=");";
			tinyTips += "});</script>";
		}
		return tinyTips;
	}
	
	
	private String htmlSoDo(CableOdfTypes cableOdfTypes, List<CableOdfManagements> cableOdfManList, List<SYS_PARAMETER> colorOdfLienTang){
			
		String html = "<table class=\"simple2\" border=\"0\" style=\"width:80%;\" cellspacing=\"0\" cellpadding=\"0\">";
		int locationPort = cableOdfTypes.getLocationPort();
		int count = 0;
		int temp = 0;
		while(count < cableOdfManList.size()){
			// Port
			html += "<tr>";
			for(int i=0;i<locationPort;i++){
				html += "<td align=\"center\" style=\"background-color: " + colorOdfLienTang.get(0).getValue() +";\">" + cableOdfManList.get(temp).getPort() + "</td>";
				temp++;
				if(temp == cableOdfManList.size())
					break;
			}
			html += "</tr><tr>";
			for(int i=0;i<locationPort;i++){
				if(cableOdfManList.get(count).getIsEnable().equals("Y")){
					html += "<td align=\"center\" style=\"background-color: " + colorOdfLienTang.get(1).getValue() + ";\"><a class=\"imgTip_" + cableOdfManList.get(count).getId() +"\" href=\"#\">";
					if(cableOdfManList.get(count).getName() != null)
						html += cableOdfManList.get(count).getName();
					html += "&nbsp;</a></td>";
				}
				else if(cableOdfManList.get(count).getIsEnable().equals("N")){
					html += "<td align=\"center\" style=\"background-color: " + colorOdfLienTang.get(2).getValue() + ";\"><a class=\"imgTip_" + cableOdfManList.get(count).getId() +"\" href=\"#\">";
					if(cableOdfManList.get(count).getName() != null)
						html += cableOdfManList.get(count).getName();
					html += "&nbsp;</a></td>";
				}
				else if(cableOdfManList.get(count).getIsEnable().equals("E")){
						html += "<td align=\"center\" style=\"background-color: " + colorOdfLienTang.get(3).getValue() + ";\"><a class=\"imgTip_" + cableOdfManList.get(count).getId() +"\" href=\"#\">";
						if(cableOdfManList.get(count).getName() != null)
							html += cableOdfManList.get(count).getName();
						html += "&nbsp;</a></td>";
				}
				else if(cableOdfManList.get(count).getIsEnable().equals("O")){
						html += "<td align=\"center\" style=\"background-color: " + colorOdfLienTang.get(4).getValue() + ";\"><a class=\"imgTip_" + cableOdfManList.get(count).getId() +"\" href=\"#\">";
						if(cableOdfManList.get(count).getName() != null)
							html += cableOdfManList.get(count).getName();
						html += "&nbsp;</a></td>";
					
				}
				count++;
				if(count == cableOdfManList.size())
					break;
			}
			html += "</tr>";
		}
		html += "</table>";
		return html;
	}
}