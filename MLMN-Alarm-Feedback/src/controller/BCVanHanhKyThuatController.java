package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import vo.AlAlarmWorks;
import vo.AlMonitorTemperatureSite;
import vo.AlShift;
import vo.CHighlightConfigs;
import vo.CTableConfig;
import vo.RAlarmLog;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.WWorkingAtShift;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.HelpTableConfigs;

import dao.AlAlarmWorksDAO;
import dao.AlShiftDAO;
import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUserAreaDAO;
import dao.SysUserEquipmentNameDAO;
import dao.SysUsersDAO;

@Controller
@RequestMapping("/report/*")
public class BCVanHanhKyThuatController extends BaseController {
	@Autowired
	private AlAlarmWorksDAO alAlarmWorksDAO;

	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;

	@Autowired
	private MDepartmentDAO mDepartmentDAO;
	
	@Autowired
	private AlShiftDAO alShiftDAO;

	@Autowired
	private SysUsersDAO sysUsersDao;
	
	@Autowired
	private SysUserEquipmentNameDAO sysUserEquipmentNameDAO;
	
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	
	@Autowired
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	@Autowired
	private SysUserAreaDAO sysUserAreaDAO;
	
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat df_full2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	@RequestMapping(value = "bao-cao-van-hanh-kt")
	public ModelAndView list(
			@RequestParam(required = false) String function,
			@RequestParam(required = false) String dateF,
			@RequestParam(required = false) String dateT,
			@RequestParam(required = false) String caTK,
			@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String cellid,
			@RequestParam(required = false) String alarmName,
			@RequestParam(required = false) String groups,
			@RequestParam(required = false) String team,
			Model model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> sysParemeterTitle = alAlarmWorksDAO.titleBCVanHanhKyThuat(function);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		
		List<SYS_PARAMETER> caListName = new ArrayList<SYS_PARAMETER>();
		caListName = sysParameterDao.getSysParameterByCode("CA_TRUC_NAME");
		model.addAttribute("caList", caListName);
		
		if (dateF == null || dateF.equals("")||(dateF!=null && !dateF.equals("") && DateTools.isValid("dd/MM/yyyy", dateF)==false))
		{
			Calendar cal = Calendar.getInstance();	
			cal.setTime(new Date());
			cal.add(Calendar.DATE,-7);
			dateF = df_full.format(cal.getTime());
		}
		
		if (dateT == null || dateT.equals("")||(dateT!=null && !dateT.equals("") && DateTools.isValid("dd/MM/yyyy", dateT)==false))
		{
			dateT = df_full.format(new Date());
		}	
		if (bscid!=null)
		{
			bscid=bscid.replaceAll(" ", "");
		}
		else
		{
			bscid="";
		}
		if (cellid!=null)
		{
			cellid=cellid.replaceAll(" ", "");
		}
		else
		{
			cellid="";
		}
		
		if (alarmName!=null)
		{
			alarmName=alarmName.trim();
		}
		else
		{
			alarmName="";
		}
		
		if (groups!=null)
		{
			groups=groups.trim();
		}
		else
		{
			groups="";
		}
		if (team!=null)
		{
			team=team.trim();
		}
		else
		{
			team="";
		}
		String cn="";
		//Lay danh sach team theo user
				List<String> teamList = sysUserAreaDAO.getSubTeamList(null,null);
				String teamArray="var teamList = new Array(";//Danh sach district cho cobobox
				cn="";
				for (String dis : teamList) {
					teamArray = teamArray + cn +"\""+dis+"\"";
					cn=",";
				}
				teamArray = teamArray+");";
				model.addAttribute("teamList", teamArray);
				
				//Lay danh sach BSC theo user
				List<String> bscList = sysUserEquipmentNameDAO.getBSCList(null,null,null);
				String bscArray="var bscList = new Array(";//Danh sach bsc cho cobobox
				cn="";
				for (String bsc : bscList) {
					bscArray = bscArray + cn +"\""+bsc+"\"";
					cn=",";
				}
				bscArray = bscArray+");";
				model.addAttribute("bscList", bscArray);  
				//Lay danh sach Cell theo user
				List<String> cellList = sysUserEquipmentNameDAO.getCellList(null,bscid,null,null);
				//String cellListStr ="";
				String cellArray="var cellList = new Array(";
				cn="";
				for (String cell : cellList) {
				//	cellListStr = cellListStr + cn +cell;
					cellArray = cellArray + cn +"\""+cell+"\"";
					cn=",";
				}
				cellArray = cellArray+");";
				model.addAttribute("cellList", cellArray);    
		String table_name="BC_VAN_HANH_KT";
		if (function.equalsIgnoreCase("HIEU_CHINH_MO_RONG"))
		{
			table_name="BC_VAN_HANH_KT_HCMR";
		}
		//do du lieu ra grid 
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(table_name);
		//Lay du lieu cac cot an hien cua grid 
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList(table_name);
		
		String url = "dataList.htm?dateF="+dateF+"&dateT="+dateT+"&caTK="+caTK+"&bscid="+bscid+"&cellid="+cellid+"&alarmName="+alarmName
				+"&groups="+groups+"&team="+team+"&function="+function;
		String grid = HelpTableConfigs.getGridPagingUrl(configList ,"grid", url,"jqxlistbox", listSource, "menuReport", null, null, null);
		model.addAttribute("grid", grid);
		
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "BaoCaoVanHanhKyThuat_"+function+"_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		model.addAttribute("dateF", dateF);
		model.addAttribute("dateT", dateT);
		model.addAttribute("caTK", caTK);
		model.addAttribute("bscid", bscid);
		model.addAttribute("cellid", cellid);
		model.addAttribute("alarmName", alarmName);
		model.addAttribute("groups", groups);
		model.addAttribute("team", team);
		model.addAttribute("function", function);
		
		return new ModelAndView("jspalarm/report/reportBCVanHanhKT");
	}

	@RequestMapping("/dataList")
	public void doGet(
			@RequestParam(required = true) String dateF,
			@RequestParam(required = true) String dateT,
			@RequestParam(required = true) String caTK,
			@RequestParam(required = true) String bscid,
			@RequestParam(required = true) String cellid,
			@RequestParam(required = true) String alarmName,
			@RequestParam(required = true) String groups,
			@RequestParam(required = true) String team,
			@RequestParam(required = true) String function,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		
			List<AlAlarmWorks> larmWorkList = alAlarmWorksDAO.getBCVanHanhKyThuat(dateF, dateT,caTK,bscid,cellid,alarmName,groups,team,function);
			Gson gs = new Gson();
			String jsonCartList = gs.toJson(larmWorkList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.println(jsonCartList);
			out.close();
		
		
	}
	

}
