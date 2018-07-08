package controller.feedback;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vo.FbType;
import vo.HProvinceFb;
import vo.MDepartment;
import vo.SYS_PARAMETER;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.HelpTableConfigs;

import controller.BaseController;
import dao.CConfigAlarmTypeDAO;
import dao.CTableConfigDAO;
import dao.FbProcessDAO;
import dao.FbTypeDAO;
import dao.HProvinceFbDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;

@Controller
@RequestMapping("/chart/feedback/*")
public class FeedbackChartController extends BaseController {

	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired 
	private SysUsersDAO sysUsersDao;
	
	@Autowired
 	private FbProcessDAO fbProcessDAO;
	
	@Autowired
 	private HProvinceFbDAO hProvinceFbDAO;
	
	@Autowired
 	private FbTypeDAO FbTypeDAO;
	
	@Autowired
	private CConfigAlarmTypeDAO cConfigAlarmTypeDAO;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
	
	/*Tong hop so luong feedback theo cac tieu chuan: to VT, DaiVT, quan/huyen, loại phan anh
	 * @param sdate: Thoi gian bat dau
	 * @param edate: Thoi gian ket thuc
	 * @param region: Khu vuc
	 * @param province: Tinh /thanh pho
	 * @param team: To
	 * @param district: Quan huyen
	 * function : TEAM,DEPT,DSITRICT,PROVINCE,fb type
	*/
		@RequestMapping(value = "{function}")
		public ModelAndView list(
				@RequestParam(required = false) String sdate,
				@RequestParam(required = false) String edate,
				@RequestParam(required = false) String region,
				@RequestParam(required = false) String province,
				@RequestParam(required = false) String fb_type,
				@RequestParam(required = false) String team,
				@RequestParam(required = false) String district,
				@RequestParam(required = false) String dept,
				@PathVariable String function,
				Model model, HttpServletRequest request) throws ParseException {
			
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			
			List<SYS_PARAMETER> sysParemeterTitle = fbProcessDAO.titleFBChart(function);
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("title", sysParemeterTitle.get(0).getValue());
			}
			
			//Kiem tra dieu kien tim kiem 
			Calendar cal = Calendar.getInstance();	
			cal.setTime(new Date());
			String sdateChar ="";
			String edateChar="";
			if (edate == null || edate.equals("")
					||(edate!=null && !edate.equals("") && DateTools
								.isValid("dd/MM/yyyy", edate)==false))
			{
				edate = df.format(cal.getTime());
			}
			
			if (sdate == null || sdate.equals("")
					||(sdate!=null && !sdate.equals("") && DateTools
								.isValid("dd/MM/yyyy", sdate)==false))
			{
				Calendar cal2 = Calendar.getInstance();	
				cal2.setTime(df.parse(edate));
				cal2.add(Calendar.DATE, -7);
				sdate = df.format(cal2.getTime());
				
			}
			sdateChar = df2.format(df.parse(sdate));
			edateChar = df2.format(df.parse(edate));
			
			System.out.println("sdateChar:"+sdateChar);
			System.out.println("edateChar:"+edateChar);
			model.addAttribute("sdate", sdate);			
			model.addAttribute("edate", edate);
			
			
			if (fb_type!=null)
			{
				fb_type=fb_type.trim();
			}
			else
			{
				fb_type="";
			}
			
			if (province!=null)
			{
				province=province.trim();
			}
			else
			{
				province="";
			}
			
			if (district!=null)
			{
				district=district.trim();
			}
			else
			{
				district="";
			}
			if (dept!=null)
			{
				dept=dept.trim();
			}
			else
			{
				dept="";
			}
			if (team!=null)
			{
				team=team.trim();
			}
			else
			{
				team="";
			}
			
			if (region!=null)
			{
				region=region.trim();
			}
			else
			{
				region="";
			}
			String cn="";
			
			// Loai phan anh
	 		List<FbType> loaiPAList = FbTypeDAO.getFBTypeList();
	 		List<String> fbTypeList = new ArrayList<String>();
	 		String fbTypeArray="var fbTypeList = new Array(";//Danh sach Loai phan anh cho cobobox
			cn="";
			for (FbType dis : loaiPAList) {
				fbTypeArray = fbTypeArray + cn +"\""+dis.getName()+"\"";
				cn=",";
				fbTypeList.add(dis.getName());
			}
			fbTypeArray = fbTypeArray+");";
			model.addAttribute("fbTypeList", fbTypeArray);
			
			// Phong/Dai
	 		List<MDepartment> deptOList = fbProcessDAO.getDepartmentFbList();
	 		List<String> deptList = new ArrayList<String>();
	 		String deptArray="var deptList = new Array(";//Danh sach Dai VT cho cobobox
			cn="";
			for (MDepartment dis : deptOList) {
				deptArray = deptArray + cn +"\""+dis.getDeptCode()+"\"";
				cn=",";
				deptList.add(dis.getDeptCode());
			}
			deptArray = deptArray+");";
			model.addAttribute("deptList", deptArray);
			
	 		// To VT
	 		List<HProvinceFb> teamOList = hProvinceFbDAO.getTeamFbList(dept);
	 		List<String> teamList = new ArrayList<String>();
	 		String teamArray="var teamList = new Array(";//Danh sach ToVT cho cobobox
			cn="";
			for (HProvinceFb dis : teamOList) {
				teamArray = teamArray + cn +"\""+dis.getTeam()+"\"";
				cn=",";
				teamList.add(dis.getTeam());
			}
			teamArray = teamArray+");";
			model.addAttribute("teamList", teamArray);
			
			//Thanh Pho FB
			List<HProvinceFb> provinceOList = hProvinceFbDAO.getProvinceByRegion(region);
			List<String> provinceList = new ArrayList<String>();
			String provinceArray="var provinceList = new Array(";//Danh sach province cho cobobox
			cn="";
			for (HProvinceFb dis : provinceOList) {
				provinceArray = provinceArray + cn +"\""+dis.getProvince()+"\"";
				cn=",";
				provinceList.add(dis.getProvince());
			}
			provinceArray = provinceArray+");";
			model.addAttribute("provinceList", provinceArray);
			
			// Quan huyen FB
			List<HProvinceFb> districtOList = hProvinceFbDAO.getDistrictByRegionProvine(region,province);
			List<String> districtList = new ArrayList<String>();
			String districtArray="var districtList = new Array(";//Danh sach district cho cobobox
			cn="";
			for (HProvinceFb dis : districtOList) {
				districtArray = districtArray + cn +"\""+dis.getDistrict()+"\"";
				cn=",";
				districtList.add(dis.getDistrict());
			}
			districtArray = districtArray+");";
			model.addAttribute("districtList", districtArray);
			
			//Lay du lieu bao cao so luong ve bieu do
			List<String> dataChartList = fbProcessDAO.getDataFeedbackChart(sdateChar,edateChar,region,province,district,dept,team,fb_type,username,function);
			String chart="";
			List<String> seriesList = new ArrayList<String>();
			if (function.equalsIgnoreCase("team"))
			{
				//neu khong chon bsc thi lay danh sach tat ca team neu khong thi lay cac teams duoc chon
				if (team!=null && !team.equals(""))
				{
					//remove nhung bsc khong duoc chon
					String[] teamL = team.split(",");
					for (String item : teamL) {
						seriesList.add(item);
					}
				}
				else
					seriesList = teamList;
			}
			else if (function.equalsIgnoreCase("dept"))
			{
				//neu khong chon bsc thi lay danh sach tat ca dept neu khong thi lay cac dept duoc chon
				if (dept!=null && !dept.equals(""))
				{
					//remove nhung bsc khong duoc chon
					String[] deptL = dept.split(",");
					for (String item : deptL) {
						seriesList.add(item);
					}
				}
				else
					seriesList = deptList;
			}
			else if (function.equalsIgnoreCase("fb_type"))
			{
				//neu khong chon bsc thi lay danh sach tat ca fb_type neu khong thi lay cac fb_type duoc chon
				if (fb_type!=null && !fb_type.equals(""))
				{
					//remove nhung bsc khong duoc chon
					String[] fb_typeL = fb_type.split(",");
					for (String item : fb_typeL) {
						seriesList.add(item);
					}
				}
				else
					seriesList = fbTypeList;
			}
			else if (function.equalsIgnoreCase("district"))
			{
				//neu khong chon bsc thi lay danh sach tat ca district neu khong thi lay cac district duoc chon
				if (district!=null && !district.equals(""))
				{
					//remove nhung bsc khong duoc chon
					String[] districtL = district.split(",");
					for (String item : districtL) {
						seriesList.add(item);
					}
				}
				else
					seriesList = districtList;
			}
			else if (function.equalsIgnoreCase("province"))
			{
				//neu khong chon bsc thi lay danh sach tat ca province neu khong thi lay cac province duoc chon
				if (province!=null && !province.equals(""))
				{
					String[] provinceL = province.split(",");
					for (String item : provinceL) {
						seriesList.add(item);
					}
				}
				else
					seriesList = provinceList;
			}
			// //ve bieu do
			chart = HelpTableConfigs.lineChart("FEEDBACK","FEEDBACK", sdate+ " - "+edate,dataChartList,sdateChar,edateChar,"0","80", seriesList,"10");
			model.addAttribute("chart", chart);
			
			model.addAttribute("function", function);
			model.addAttribute("province", province);
			model.addAttribute("district", district);
			model.addAttribute("dept",dept);
			model.addAttribute("team", team);
			model.addAttribute("fb_type", fb_type);
			model.addAttribute("username", username);
			model.addAttribute("region", region);
			
			return new ModelAndView("jspfeedback/chart/chartFB");
		}
	
}
