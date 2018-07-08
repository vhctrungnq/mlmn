package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.Region;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import vo.AlShift;
import vo.AlWorkCalendar;
import vo.CSystemConfigs;
import vo.CTableConfig;
import vo.M_FILE_ATTACHMENT;
import vo.SYS_PARAMETER;
import vo.SysDefineSmsEmail;
import vo.SysUsers;
import vo.WWorkingAtShift;
import vo.W_WORKING_FEEDBACKS;
import vo.W_WORKING_MANAGEMENTS;
import vo.W_WORKING_PROCESSES;
import vo.W_WORKING_TYPES;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.alarm.utils.UploadTools;

import com.google.gson.Gson;

import dao.AlShiftDAO;
import dao.AlWorkCalendarDAO;
import dao.CHighlightConfigsDAO;
import dao.CSystemConfigsDAO;
import dao.CTableConfigDAO;
import dao.M_FILE_ATTACHMENTDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;
import dao.WWorkingAtShiftDAO;
import dao.W_WORKING_FEEDBACKSDAO;
import dao.W_WORKING_MANAGEMENTSDAO;
import dao.W_WORKING_PROCESSESDAO;
import dao.W_WORKING_TYPESDAO;

@Controller
@RequestMapping("/working")
public class WWorkingAtShiftController extends BaseController {
	
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired 
	private AlShiftDAO alShiftDAO;
	
	@Autowired 
	private SysUsersDAO sysUsersDao;
	
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	
	@Autowired
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	@Autowired
	private AlWorkCalendarDAO alWorkCalendarDAO;
	
	@Autowired
	private W_WORKING_MANAGEMENTSDAO working_managementDao;
	@Autowired
	private M_FILE_ATTACHMENTDAO attachmentDao;
	@Autowired
	private W_WORKING_PROCESSESDAO processesDao;
	@Autowired
	private W_WORKING_FEEDBACKSDAO feedbacksDao;
	
	@Autowired
	private W_WORKING_TYPESDAO working_typesDao;
	
	@Autowired
	private CSystemConfigsDAO cSystemConfigsDAO;
	
	@Autowired
	private WWorkingAtShiftDAO wWorkingAtShiftDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat timeformat = new SimpleDateFormat("HH:mm:ss");
	//private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	// Lay id truc ca hien tai
		private AlShift getCaTrucHT(String region)
		{
			List<SYS_PARAMETER> caList = new ArrayList<SYS_PARAMETER>();
			caList = sysParameterDao.getSysParameterByCode("CA_TRUC_VALUE");
			String sang = caList.get(0).getValue();
			String chieu = caList.get(1).getValue();
			String toi = caList.get(2).getValue();
			String caT="";
			String ngayT="";
			AlShift shift = new AlShift();
			Date currentTime = new Date();
			int hour = currentTime.getHours();
			ngayT= df.format(currentTime);
			if (hour >= Integer.parseInt(sang) && hour < Integer.parseInt(chieu)) {
				caT = "Sáng";
			}
			if (hour >= Integer.parseInt(chieu) && hour < Integer.parseInt(toi)) {
				caT = "Chiều";
			}
			if (hour >= Integer.parseInt(toi) || hour < Integer.parseInt(sang)) {
				caT = "Tối";
			}
			// Lay idshift
			 shift = alShiftDAO.getCaTrucGanNhat(region);
			if (shift==null)
			{
				shift= alShiftDAO.getCaTruc(caT,ngayT,region);
			}
			return shift;
		}
			
		
	/*List danh sach thu vien xu ly
	 * @param sdate: Thoi gian bat dau
	 * function : 2G/3G
	 */
	@RequestMapping("/list")
	public ModelAndView list(
			@RequestParam(required = false) String ngayTK,
			@RequestParam(required = false) String ngayTKTo,
			@RequestParam(required = false) String caTK,
			@RequestParam(required = false) String userDelivered,
			@RequestParam(required = false) String workName,
			@RequestParam(required = true) String type,
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String regionTK,
			Model model, HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		AlShift shift = getCaTrucHT(region);
		boolean checkCaTruc = false;
		if ((shift!=null && shift.getNhanUsername().equals(username))||userLogin.getIsRoleManager().equals("Y") )
		{
			checkCaTruc = true;
			model.addAttribute("checkCaTruc", checkCaTruc);
		}
		List<SYS_PARAMETER> sysParemeterTitle = wWorkingAtShiftDAO.titleForm(type,null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		if (ngayTK == null || ngayTK.equals("")||(ngayTK!=null && !ngayTK.equals("") && DateTools.isValid("dd/MM/yyyy", ngayTK)==false))
		{
			ngayTK = df.format(new Date());
		}
		if (ngayTKTo == null || ngayTKTo.equals("")||(ngayTKTo!=null && !ngayTKTo.equals("") && DateTools.isValid("dd/MM/yyyy", ngayTKTo)==false))
		{
			ngayTKTo = df.format(new Date());
		}	
		if (region!=null && !region.equals(""))
		{
			regionTK = region;
		}
		if (userDelivered==null)
		{
			userDelivered="";
		}
		else
			userDelivered = userDelivered.trim();
		if (workName==null)
		{
			workName="";
		}
		else
			workName = workName.trim();
		if (type==null)
		{
			type="";
		}
		else
			type = type.trim();
		
		if (caTK==null)
		{
			caTK="";
		}
		else
			caTK = caTK.trim();
		if (regionTK==null)
		{
			regionTK="";
		}
		else
			regionTK = regionTK.trim();
		
		String tablename="W_WORKING_SHIFT";
		if (type!=null && !type.equals(""))
		{
			tablename+="_"+type.toUpperCase();
		}
		else
		{
			tablename ="W_WORKING_AT_SHIFT_CVCL";
		}
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(tablename);
		//Lay du lieu cac cot an hien cua grid 
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList(tablename);
		//url
		String url = "data.htm?type="+type+"&ngayTK="+ngayTK+"&ngayTKTo="+ngayTKTo+"&userDelivered="+userDelivered+"&workName="+workName+"&caTK="+caTK+"&region="+regionTK;
		//Grid
		String gridReport = HelpTableConfigs.getGridPagingUrl(configList, "gridReport", url, "listColumn", listSource, "menuReport", null, "multiplerowsextended","Y");
		model.addAttribute("gridReport", gridReport);	
		
		List<SysUsers> fullNameList = sysUsersDao.getUserByMaPhong(userLogin.getMaPhong());
		model.addAttribute("fullNameList", fullNameList);
		
		//lay danh sach khu vuc
		List<SYS_PARAMETER> regionList = sysParameterDao.getRegionList();
		model.addAttribute("regionList", regionList);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = tablename+"-"+ dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		model.addAttribute("type", type);
		model.addAttribute("userDelivered", userDelivered);
		model.addAttribute("workName", workName);
		model.addAttribute("caTK", caTK);
		model.addAttribute("ngayTK",ngayTK);
		model.addAttribute("ngayTKTo", ngayTKTo);
		model.addAttribute("region", region);
		model.addAttribute("regionTK", regionTK);
		List<SYS_PARAMETER> caListName = new ArrayList<SYS_PARAMETER>();
		caListName = sysParameterDao.getSysParameterByCode("CA_TRUC_NAME");
		model.addAttribute("caList", caListName);
		
		return new ModelAndView("jspalarm/workShift/workShiftList");
	}
	@RequestMapping("data")
	public @ResponseBody 
	void dataSuccessList(@RequestParam(required = false) String type,
			@RequestParam(required = false) String ngayTK,
			@RequestParam(required = false) String ngayTKTo,
			@RequestParam(required = false) String userDelivered,
			@RequestParam(required = false) String workName,
			@RequestParam(required = false) String caTK,
			@RequestParam(required = false) String region,
			HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		
		List<WWorkingAtShift> dataList = new ArrayList<WWorkingAtShift>();
		if (type.equalsIgnoreCase("cvcd")){
			dataList = wWorkingAtShiftDAO.getListFilter(type,null,null,userDelivered,workName,caTK,region);
		}else
		{
			dataList = wWorkingAtShiftDAO.getListFilter(type,ngayTK,ngayTKTo,userDelivered,workName,caTK,region);
		}
		Gson gs = new Gson();
		String jsonDataList = gs.toJson(dataList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonDataList);
		out.close();
	 }
	
	//Xoa
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) String idList,@RequestParam (required = false) String type,
			@RequestParam (required = false) String note,
			@RequestParam (required = false) String region,
			HttpServletRequest request, Model model) {
		//Duong dan tra ve
		String path="";
		if (note!=null && note.equals("caTruc"))
		{
			path="redirect:/caTruc/list.htm?region="+region;
		}
		else if (note!=null && note.equals("filter"))
		{
			path="redirect:/caTruc/listFilter.htm?region="+region;
		}
		else
			path="redirect:list.htm?type="+type+"&region="+region;
				
		try {
			
			String[] idArray = idList.split(",");
			for (String id : idArray) {
				System.out.println(id);
				if (type!=null && !type.equals(""))
				{
					wWorkingAtShiftDAO.deleteByPrimaryKey(Integer.parseInt(id));
				}
				else
				{
					working_managementDao.deleteWorkingManagerMail(Integer.parseInt(id));
					working_managementDao.deleteByPrimaryKey(Integer.parseInt(id));
				}
				
			}
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch (Exception e) {
			saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return path;
	}
	
	//Them, sua
		@RequestMapping(value = "form", method = RequestMethod.GET)
		public String form(@RequestParam (required = false) String id,
				@RequestParam (required = true) String type,
				@RequestParam (required = false) String note,
				@RequestParam (required = false) String region,
				HttpServletRequest request, Model model) {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
			
			List<SysUsers> fullNameList = sysUsersDao.getUserByMaPhong(userLogin.getMaPhong());
			model.addAttribute("fullNameList", fullNameList);
			
			List<SYS_PARAMETER> sysParemeterTitle = wWorkingAtShiftDAO.titleForm(type,"ADD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
			}
			
			List<SYS_PARAMETER> caListName = new ArrayList<SYS_PARAMETER>();
			caListName = sysParameterDao.getSysParameterByCode("CA_TRUC_NAME");
			model.addAttribute("caList", caListName);
			//cong viec co dinh
			/*if (type!=null && !type.equals(""))
			{*/
				WWorkingAtShift record = new WWorkingAtShift();
					if (id!=null&&!id.equals(""))
					{
						record = wWorkingAtShiftDAO.selectByPrimaryKey(Integer.parseInt(id));
						if (record!=null)
						{
							model.addAttribute("shift",record.getShift());	
							model.addAttribute("deadline",record.getDeadline());	
							model.addAttribute("regionCB",record.getRegion());
							model.addAttribute("employeeAssessment",record.getEmployeeAssessment());	
							model.addAttribute("superiorAssessment",record.getSuperiorAssessment());	
							String fileList = "";
							List<M_FILE_ATTACHMENT> file_attachment1 = attachmentDao.fileName_file(id);
							String hello = "";
							for(int i=0;i<file_attachment1.size();i++){
								fileList += hello + file_attachment1.get(i).getId();
								hello = ",";
							}
							
							model.addAttribute("file_attachment", fileList);
						}
					}
					else
					{
						Calendar cal = Calendar.getInstance();
						cal.setTime(new Date());
						String deadline = timeformat.format(cal.getTime());
						model.addAttribute("deadline", deadline);
						String day = df.format(cal.getTime());
						model.addAttribute("day", day);	
						record.setUserDelivered(username);
					}
					model.addAttribute("workShift", record);		
					model.addAttribute("type",type);	
					model.addAttribute("region",region);	
					
					model.addAttribute("note", note);	
					return "jspalarm/workShift/workShiftForm";
		}
		
		@RequestMapping(value = "form", method = RequestMethod.POST)
		public String onSubmit(@ModelAttribute("workShift") @Valid WWorkingAtShift workShift, BindingResult result,
				@RequestParam(required = false) String type,
				@RequestParam(required = false) String deadline,
				@RequestParam(required = false) String shift,
				@RequestParam(required = false) String day,
				@RequestParam (required = false) String note,
				@RequestParam(required = false) String fileId,
				@RequestParam(required = false) String region,
				@RequestParam(required = false) String regionCB,
				ModelMap model, HttpServletRequest request) throws ParseException {
			
				
			model.addAttribute("note", note);	
			
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
			/*AlShift shiftHT = getCaTrucHT();
			boolean checkCaTruc= false;
			int shiftID = 1;
			if (shiftHT!=null)
			{
				String[] user = shiftHT.getNhanCaVien().split(",");
				if (shiftHT.getNhanUsername().equals(username)||userLogin.getIsRoleManager().equals("Y"))
				{
					checkCaTruc=true;
				}
				for (String item : user) {
					if (item.equals(username))
					{
						checkCaTruc=true;
						break;
					}
				}
				model.addAttribute("checkCaTruc", checkCaTruc);
				shiftID = shiftHT.getShiftId();
			}
			else
			{
				checkCaTruc=true;
			}*/
			
			
			List<SysUsers> fullNameList = sysUsersDao.getUserByMaPhong(userLogin.getMaPhong());
			model.addAttribute("fullNameList", fullNameList);
			
			List<SYS_PARAMETER> sysParemeterTitle = wWorkingAtShiftDAO.titleForm(type,"ADD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
			}
			List<SYS_PARAMETER> caListName = new ArrayList<SYS_PARAMETER>();
			caListName = sysParameterDao.getSysParameterByCode("CA_TRUC_NAME");
			model.addAttribute("caList", caListName);
			
			
			
			/*model.addAttribute("employeeAssessment",employeeAssessment);	
			model.addAttribute("superiorAssessment",superiorAssessment);*/	
		
			System.out.println("eror:"+result.getObjectName());
			System.out.println("deadline:"+deadline);
			/*if (checkCaTruc==false)
			{
				saveMessageKey(request, "cautruc.KhongCoQuyen");
				return "jspalarm/workShift/workShiftForm";
			}*/
			String error="";
			if (deadline != null && !deadline.equals("")&&deadline.length()>0&&deadline.length()<9) {
				workShift.setDeadline(deadline);
			}
			else
			{
				model.addAttribute("errordeadline", "*");
				error="define.errorField";
			}
	
			if (workShift.getWorkName()==null||workShift.getWorkName().equals("") ||(workShift.getWorkName()!=null &&workShift.getWorkName().length()>2000 && workShift.getWorkName().length()<0))
			{
				model.addAttribute("errorWorkName", "*");
				error="define.errorField";
			}
			if (regionCB==null&&region.equals(""))
			{
				model.addAttribute("errorRegion", "*");
				error="define.errorField";
			}
			if (result.hasErrors() ||!error.equals("") ) {
				model.addAttribute("type",type);	
				model.addAttribute("day",day);	
				model.addAttribute("shift",shift);	
				model.addAttribute("deadline",deadline);	
				model.addAttribute("regionCB",regionCB);	
				model.addAttribute("region",region);	
				error="define.errorField";
				saveMessageKey(request, error);
				 return "jspalarm/workShift/workShiftForm";
			}
			else
			{
				workShift.setShift(shift);
				/*workShift.setEmployeeAssessment(employeeAssessment);
				workShift.setSuperiorAssessment(superiorAssessment);
			*/
				if (day != null && !day.equals("")) {
					workShift.setDay((new SimpleDateFormat("dd/MM/yyyy")).parse(day));
				}
				if (regionCB!=null &&!regionCB.equals(""))
				{
					workShift.setRegion(regionCB);
				}
				else
				{
					workShift.setRegion(region);
				}
				
				if (type!=null && !type.equals(""))
				{
					
					/*try
					{*/
						if (workShift.getId()==null)
						{
							workShift.setCreateDate(new Date());
							workShift.setCreatedBy(username);
							//workShift.setUserDelivered(username);
							workShift.setWorkType(type);
							wWorkingAtShiftDAO.insert(workShift);
							saveMessageKey(request, "define.insertSucceFull");
						}
						else
						{
							workShift.setModifiedBy(username);
							workShift.setModifyDate(new Date());
							wWorkingAtShiftDAO.updateByPrimaryKeySelective(workShift);
							saveMessageKey(request, "define.updateSuccelfull");	
						}
					/*}
					catch(Exception exp)
					{
						saveMessageKey(request, "cautruc.loiKichThuoc");
					}*/
				}
				else
				{
					/*try
					{*/
					String nhanCaTruong ="";
					String nhanCaVien="";
					List<AlWorkCalendar> nextShift = alWorkCalendarDAO.getShift(day,shift,workShift.getRegion());
					if (nextShift.size()>0)
					{
						String con1="";
						String con2="";
						for (AlWorkCalendar alWorkCalendar : nextShift) {
							String funtion=alWorkCalendar.getFunction();
							
							if (funtion!=null && funtion.toLowerCase().contains("trưởng ca")&&!nhanCaTruong.contains(alWorkCalendar.getEmail()))
							{
								nhanCaTruong+= con1+ alWorkCalendar.getEmail();
								con1=",";
							}
							/*else if (funtion.contains("ca viên")&&!nhanCaVien.contains(alWorkCalendar.getEmail()))*/
							else if (!nhanCaVien.contains(alWorkCalendar.getEmail()))
							{
								nhanCaVien+= con2+ alWorkCalendar.getEmail();
								con2=",";
							}
						}
					}
					if (!nhanCaTruong.equals("")&&!nhanCaVien.equals(""))
					{
						Integer id_mana =wWorkingAtShiftDAO.addWorkInShift(workShift,workShift.getDay(),workShift.getShift(),nhanCaTruong,nhanCaVien,userLogin.getMaPhong(),username);
						if(fileId != "" && fileId != null){
							String[] fileList = fileId.split(",");
							for(int i=0;i<fileList.length;i++){
								M_FILE_ATTACHMENT record = new M_FILE_ATTACHMENT();
								record.setIdWorkMana(id_mana);
								if(!fileList[i].equals(""))
									record.setId(Integer.parseInt(fileList[i]));
								attachmentDao.updateByPrimaryKeySelective(record);
							}
						}
						saveMessageKey(request, "define.insertSucceFull");
					}
					else
					{
						model.addAttribute("type",type);	
						model.addAttribute("day",day);	
						model.addAttribute("shift",shift);	
						model.addAttribute("deadline",deadline);	
						model.addAttribute("regionCB",regionCB);	
						model.addAttribute("region",region);	
						saveMessageKey(request, "shift.dontexit");
						 return "jspalarm/workShift/workShiftForm";
					}
					/*}
					catch(Exception exp)
					{
						saveMessageKey(request, "cautruc.loiKichThuoc");
					}*/
				}
			}
			//Duong dan tra ve
			String path="";
			if (note.equals("caTruc"))
			{
				path="redirect:/caTruc/list.htm?region="+region;
			}
			else if (note.equals("filter"))
			{
				path="redirect:/caTruc/listFilter.htm?region="+region;
			}
			else
				path="redirect:list.htm?type="+type+"&region="+region;
				
			return path;
		}
		
		@RequestMapping("getWorkFilterCondition")
	 	public @ResponseBody 
	 	List<String> getWorkFilterCondition(String columnName, String type ) {
	 		List<String> bscList = wWorkingAtShiftDAO.getWorkFilterCondition(columnName,type);
	 		return bscList;
	   	}
		
		
		/* CHUYEN CONG VIEC SANG CA TIEP THEO*/
		@RequestMapping(value = "forwardNextShift", method = RequestMethod.GET)
		public String forwardNextShift(@RequestParam (required = false) String id,
				@RequestParam (required = true) String type,
				@RequestParam (required = false) String note,
				@RequestParam (required = false) String region,
				HttpServletRequest request, Model model) throws IOException {
			
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
			
			List<SysUsers> fullNameList = sysUsersDao.getUserByMaPhong(userLogin.getMaPhong());
			model.addAttribute("fullNameList", fullNameList);
			
			List<SYS_PARAMETER> sysParemeterTitle = wWorkingAtShiftDAO.titleForm(type,"ADD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
			}
			
			List<SYS_PARAMETER> caListName = new ArrayList<SYS_PARAMETER>();
			caListName = sysParameterDao.getSysParameterByCode("CA_TRUC_NAME");
			model.addAttribute("caList", caListName);
			model.addAttribute("region", region);
			boolean allowForward=false;
			//chuyen cong viec sang ca trưc tiep theo voi thoi gian ket thuc la thoi diem end time của ca truc. cap nhat trang thai hoàn thanh cua ca truoc la Đạt
			W_WORKING_MANAGEMENTS w_working_details = new W_WORKING_MANAGEMENTS();
			if (id!=null)
			{
				w_working_details= working_managementDao.selectJoinByPrimaryKey(Integer.parseInt(id));
				
				Calendar currentDate = Calendar.getInstance();
				SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyyhhmmss");
				String dateNow = formatter.format(currentDate.getTime());
				String path="";
				CSystemConfigs config = cSystemConfigsDAO.selectByPrimaryKey("upload.dir");
				if (config!=null)
				{
					path=config.getParamValue();
				}
		    	String fileList = "";
		    	String hello = "";
				List<M_FILE_ATTACHMENT> file_attachment1 = attachmentDao.fileName_file(id);
				
				for (M_FILE_ATTACHMENT record : file_attachment1) {
					
					String pathsource=record.getFilePath();
					String fileOld=record.getFileName();
					String fileName = dateNow+"_"+ fileOld.substring(fileOld.indexOf("_"));
					String uploadPath=path.concat("/work");
			        // Tạo ra các thư mục nếu nó không tồn tại
			        File uploadDir = new File(uploadPath);
			        if (!uploadDir.exists()) {
			            uploadDir.mkdir();
			        }
			        String pathdest = uploadPath.concat("/").concat(fileName);
					copyFileUsingApacheCommonsIO( pathsource,  pathdest);
					record.setId(null);
					record.setFileName(fileName);
					record.setFilePath(pathdest);
					
					attachmentDao.insertAndResult(record);
					fileList += hello + record.getId();
					hello = ",";
					
				}
				
				model.addAttribute("file_attachment", fileList);
			}
			WWorkingAtShift workShift = new WWorkingAtShift();
			if (w_working_details!=null)
			{
				if ((w_working_details.getNguoiGiaoViec()!=null && w_working_details.getNguoiGiaoViec().equalsIgnoreCase(username))
						|| userLogin.getIsRoleManager().equals("Y"))
				{
					allowForward = true;
				}
				if (allowForward==true)
				{
					String shift = w_working_details.getCaTruc();
					Date day = w_working_details.getDayShift();
					String dayStr="";
					String deadline="";
					workShift.setUserDelivered(w_working_details.getNguoiGiaoViec());
					workShift.setWorkName(w_working_details.getTenCongViec());
					
					if (shift.startsWith("S"))
					{
						workShift.setShift("Chiều");
						dayStr=df.format(day);
						deadline="18:30:00";
					}
					else if (shift.startsWith("C"))
					{
						workShift.setShift("Tối");
						dayStr=df.format(day);
						deadline="06:30:00";
					}
					else if (shift.startsWith("T"))
					{
						workShift.setShift("Sáng");
						Calendar cal = Calendar.getInstance();
						cal.setTime(day);
						cal.add(Calendar.DATE,1);
						dayStr=df.format(cal.getTime());
						deadline="12:30:00";
					}
					model.addAttribute("deadline", deadline);
					model.addAttribute("day", dayStr);	
					model.addAttribute("shift",workShift.getShift());	
					
				}
				else
				{
					saveMessageKey(request, "forward.insertFall");	
					return "redirect:list.htm?region="+workShift.getRegion();
				}
			}
			
			model.addAttribute("workShift", workShift);		
			model.addAttribute("type",type);	
			model.addAttribute("note", note);	
			return "jspalarm/workShift/workShiftForm";
		
			
			
		}
		private void copyFileUsingApacheCommonsIO(String pathsource, String pathdest)
		         throws IOException {
			File source = new File(pathsource);
			File dest = new File(pathdest); 
		    FileUtils.copyFile(source, dest);
		}
		
		
}
