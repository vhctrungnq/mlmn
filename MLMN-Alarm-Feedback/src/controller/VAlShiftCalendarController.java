package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

import vo.AlShiftCalendar;
import vo.AlShiftCalendarTemp;
import vo.SYS_PARAMETER;
import vo.VAlShiftCalendar;
import vo.VAlShiftFilter;
import vo.VAlShiftLeaderCalendar;
import vo.alarm.utils.UploadTools;
import bsh.ParseException;
import dao.AlShiftCalendarDAO;
import dao.AlShiftCalendarTempDAO;
import dao.AlShiftDAO;
import dao.FilePatternDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;
import dao.VAlShiftCalendarDAO;
import dao.VAlShiftLeaderCalendarDAO;

/*
 * @Author: ThangPX
 * @Return: Form Alarm shift report
 */
@Controller
@RequestMapping("/system/alshiftcalendar/*")
public class VAlShiftCalendarController extends BaseController {
	@Autowired
	private SYS_PARAMETERDAO sysParamDao;
	@Autowired
	private VAlShiftCalendarDAO vAlShiftCalendarDAO;
	@Autowired
	private AlShiftCalendarDAO alShiftCalendarDAO;
	@Autowired
	private VAlShiftLeaderCalendarDAO alShiftLeaderCalendarDAO;
	@Autowired
	private AlShiftCalendarTempDAO alShiftCalendartempDAO;
	@Autowired
	private FilePatternDAO filePatternDao;
	@Autowired
	private SysUsersDAO sysUsersDao;
	@Autowired
	private AlShiftDAO alShiftDAO;

	// Return list Alarm shift by team, area, week, year
	@RequestMapping(value = "list")
	public ModelAndView list(@RequestParam(required = false) String team,
			@RequestParam(required = false) String area,
			@RequestParam(required = false) Integer week,
			@RequestParam(required = false) Integer year,
			@RequestParam(required = false) String edit, Model model,
			HttpServletRequest request) {

		// Check shift
		// Get user login
		String checkCaTruc = "0";
		boolean ed = false;
		String username = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		// Get users, teams, areas has defined in Sysparameter table
		List<SYS_PARAMETER> userList = sysParamDao.getSPByCodeAndName(
				"V_AL_SHIFT_USER", "");
		List<SYS_PARAMETER> teamList = sysParamDao.getSPByCodeAndName(
				"V_AL_SHIFT_TEAM", "");
		List<SYS_PARAMETER> areaList = sysParamDao.getSPByCodeAndName(
				"V_AL_SHIFT", "");
		// Check null params
		if (edit != null)
			ed = true;
		Calendar sDateCalendar = Calendar.getInstance();
		int currentWeek = sDateCalendar.get(Calendar.WEEK_OF_YEAR);
		if (week == null)
			week = currentWeek;
		for (int i = 0; i < userList.size(); i++) {
			if (username.equals(userList.get(i).getValue())) {
				checkCaTruc = "2";
				break;
			}
		}
		if(checkCaTruc.equals("0") && week > currentWeek)
			checkCaTruc = "1";
	
		if (year == null)
			year = sDateCalendar.get(Calendar.YEAR);
		if (team == null)
			team = "";
		if (area == null)
			area = "";
		// Order table by column
		int order = 0;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder(
					"vAlShiftList")
					.encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("vAlShiftList")
					.encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		// Define list need show
		List<VAlShiftCalendar> vAlShiftList = new ArrayList<VAlShiftCalendar>();
		List<VAlShiftLeaderCalendar> vAlShiftLeader = new ArrayList<VAlShiftLeaderCalendar>();
		VAlShiftFilter filter = new VAlShiftFilter();

		// Set values of filter object
		filter.setTeam(team);
		filter.setArea(area);
		filter.setWeek(week);
		filter.setYear(year);
		// Filter and addrows
		try {

			vAlShiftList = vAlShiftCalendarDAO.getList(filter, column, order);
			for (int i = 0; i < vAlShiftList.size(); i++) {
				if (i == 1) {
					VAlShiftCalendar vAlShiftCalendar0 = new VAlShiftCalendar();
					vAlShiftCalendar0.setSession(vAlShiftList.get(i).getArea());
					vAlShiftList.add(i, vAlShiftCalendar0);
				}
				if (i == 26) {
					VAlShiftCalendar vAlShiftCalendar1 = new VAlShiftCalendar();
					vAlShiftCalendar1.setSession(vAlShiftList.get(i).getArea());
					vAlShiftList.add(i, vAlShiftCalendar1);
				}
				if (i == 30) {
					VAlShiftCalendar vAlShiftCalendar2 = new VAlShiftCalendar();
					vAlShiftCalendar2.setSession(vAlShiftList.get(i).getArea());
					vAlShiftList.add(i, vAlShiftCalendar2);
				}
			}

			vAlShiftLeader = alShiftLeaderCalendarDAO.filter(week, year);

		} catch (Exception exp) {
			vAlShiftList = null;
		}

		// Get row_num, notice
		int row_num = 0;
		String rowId = "", notice = "", sDate = "", eDate = "";

		if (vAlShiftList.size() != 0) {

			sDate = vAlShiftList.get(0).getThu2();
			eDate = vAlShiftList.get(0).getCn();
			notice = vAlShiftList.get(0).getNotice();
			if (notice != null) {
				if (ed == true) {
					notice = notice.replace("<br>", "\n");
					notice = notice.replace("\t", "");
				} else {
					notice = "<br>" + notice;
					notice = notice.replace("\n", "<br>");
					notice = notice.replace("\t", "");
				}
			}
			for (int i = 0; i < vAlShiftList.size(); i++) {
				rowId += vAlShiftList.get(i).getId() + ",";
			}
			row_num = vAlShiftList.size();
			rowId = rowId.substring(0, rowId.lastIndexOf(","));
		}

		// AddAttribute get out to file.jsp
		model.addAttribute("sDate", sDate);
		model.addAttribute("eDate", eDate);
		model.addAttribute("teamList", teamList);
		model.addAttribute("areaList", areaList);
		model.addAttribute("week", week);
		model.addAttribute("year", year);
		model.addAttribute("rowId", rowId);
		model.addAttribute("edit", ed);
		model.addAttribute("row_num", row_num);
		model.addAttribute("checkCaTruc", checkCaTruc);
		model.addAttribute("team", team);
		model.addAttribute("area", area);
		model.addAttribute("notice", notice);
		model.addAttribute("vAlShiftList", vAlShiftList);
		model.addAttribute("vAlShiftLeader", vAlShiftLeader);
		// return file alShiftCalendarList.jsp

		return new ModelAndView("jsp/AlShiftCalendar/alShiftCalendarList");
	}

	// Delete list Alarm shift by id
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) Integer week,@RequestParam(required = true) Integer year,
			@RequestParam(required = true) String team,@RequestParam(required = true) String area,
			HttpServletRequest request) {

		try {
			//alShiftCalendarDAO.deleteByPrimaryKey(id);
			alShiftCalendarDAO.deleteAllData(week, year, team, area);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		} catch (Exception e) {
			saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return "redirect:list.htm?week="+week+"&year="+year+"&team="+team+"&area="+area;
	}

	// Delete list Leader Alarm shift by id
	@RequestMapping(value = "deleteLeader", method = RequestMethod.GET)
	public String onDeleteLeader(@RequestParam(required = true) Integer id,
			HttpServletRequest request) {

		try {
			alShiftLeaderCalendarDAO.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		} catch (Exception e) {
			saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return "redirect:list.htm";
	}

	//
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) Integer id,
			ModelMap model) {

		AlShiftCalendar vAlShift;
		if (id == null) {
			vAlShift = new AlShiftCalendar();
		} else {
			vAlShift = alShiftCalendarDAO.selectByPrimaryKey(id);
		}
		model.addAttribute("vAlShift", vAlShift);
		return "jsp/AlShiftCalendar/alShiftCalendarForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String formPost(
			@ModelAttribute("vAlShift") @Valid AlShiftCalendarTemp vAlShift,
			BindingResult result, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {

		int id = vAlShift.getId();
		try {
			alShiftCalendartempDAO.deleteByPrimaryKey(id);
		} catch (Exception e) {
			saveMessageKey(request, "Đăng kí thất bại");
		}
		try {
			alShiftCalendartempDAO.insert(vAlShift);
			saveMessageKey(request, "Đăng kí thành công! Đang chờ duyệt.");
		} catch (Exception e) {
			saveMessageKey(request, "Đăng kí thất bại");
		}
		return "redirect:list.htm";
	}

	@RequestMapping(value = "formduyet", method = RequestMethod.GET)
	public String showFormDuyet(Model model) {

		List<AlShiftCalendarTemp> vAlShift;
		vAlShift = alShiftCalendartempDAO.getAll();
		model.addAttribute("vAlShifttemp", vAlShift);
		return "jsp/AlShiftCalendar/alShiftCalendarDuyet";
	}

	@RequestMapping(value = "formduyet", method = RequestMethod.POST)
	public String formDuyetPost(ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {

		List<AlShiftCalendarTemp> vAlShift = alShiftCalendartempDAO.getAll();
		AlShiftCalendar ls = new AlShiftCalendar();
		int id;
		for (int i = 0; i < vAlShift.size(); i++) {
			id = vAlShift.get(i).getId();
			ls.setId(id);
			ls.setSession(vAlShift.get(i).getSession());
			ls.setThu2(vAlShift.get(i).getThu2());
			ls.setThu3(vAlShift.get(i).getThu3());
			ls.setThu4(vAlShift.get(i).getThu4());
			ls.setThu5(vAlShift.get(i).getThu5());
			ls.setThu6(vAlShift.get(i).getThu6());
			ls.setThu7(vAlShift.get(i).getThu7());
			ls.setCn(vAlShift.get(i).getCn());
			ls.setNotice(vAlShift.get(i).getNotice());
			ls.setShiftCheck("Y");
			alShiftCalendarDAO.updateByPrimaryKeySelective(ls);
			alShiftCalendartempDAO.deleteByPrimaryKey(id);
		}

		saveMessage(request, "Duyệt thành công");
		return "redirect:list.htm";
	}

	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(Model model) {
		List<SYS_PARAMETER> title = sysParamDao.getSPByCodeAndName(
				"V_AL_SHIFT_TITLE", "UPLOAD");
		model.addAttribute("title", title.get(0).getValue());
		return "jsp/AlShiftCalendar/alShiftCalendarUpload";
	}

	// Upload File Excel
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("filePath") MultipartFile filePath,
			Model model, HttpServletRequest request) throws IOException,
			ParseException {
		List<SYS_PARAMETER> title = sysParamDao.getSPByCodeAndName(
				"V_AL_SHIFT_TITLE", "UPLOAD");
		if (!filePath.isEmpty()) {
			String[] ten = filePath.getOriginalFilename().split("\\.");
			String fileName = ten[0];

			String fileExtn = ten[ten.length - 1];

			if (fileExtn.equals("xlsx") || fileExtn.equals("xls")) {
				if (fileExtn.equals("xlsx")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsxFile(filePath
							.getInputStream());
					if (fileName.contains("Shift_calendar_leader")) {
						importContentFileShiftLeader(sheetData, model, request);
					} else {
						importContent(sheetData, model, request);
					}

				}

				if (fileExtn.equals("xls")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFile(filePath
							.getInputStream());
					if (fileName.contains("Shift_calendar_leader")) {
						importContentFileShiftLeader(sheetData, model, request);
					} else {
						importContent(sheetData, model, request);
					}
				}
			} else {
				saveMessageKey(request, "cautruc.typeFIle");
			}
		} else {
			saveMessageKey(request, "cautruc.emptyFile");
		}
		model.addAttribute("title", title.get(0).getValue());
		return "jsp/AlShiftCalendar/alShiftCalendarUpload";

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContentFileShiftLeader(List sheetData, Model model,
			HttpServletRequest request) throws IOException, ParseException {
		List<SYS_PARAMETER> title = sysParamDao.getSPByCodeAndName(
				"V_AL_SHIFT_TITLE", "UPLOAD");
		model.addAttribute("title", title.get(0).getValue());
		String username = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		List<VAlShiftLeaderCalendar> successList = new ArrayList<VAlShiftLeaderCalendar>();
		List<VAlShiftLeaderCalendar> failedList = new ArrayList<VAlShiftLeaderCalendar>();
		String year, week, team, name, regency;
		Date date = new Date();

		if (sheetData.size() > 0) {

			List sheet1 = (List) sheetData.get(0);

			if (sheet1.size() != 5) {

				saveMessageKey(request, "Định dạng tệp không đúng");
				return "jsp/AlShiftCalendar/alShiftCalendarUpload";
			}

			if (sheetData.size() > 1) {
				List footer = (List) sheetData.get(sheetData.size() - 1);

				while (footer.size() != 5 && sheetData.size() > 2) {
					boolean kt = true;
					for (int k = 0; k < footer.size(); k++) {
						if (!footer.get(k).toString().trim().equals("")) {
							kt = false;
						}
					}
					if (kt == true) {
						sheetData.remove(sheetData.size() - 1);
						// maxsize=sheetData.size();
					} else {
						for (int j = footer.size(); j <= 4; j++) {
							footer.add("");
						}
					}

					footer = (List) sheetData.get(sheetData.size() - 1);
				}

				for (int i = 1; i < sheetData.size(); i++) {
					boolean error = false;

					VAlShiftLeaderCalendar alshiftList = new VAlShiftLeaderCalendar();

					List data = (List) sheetData.get(i);

					for (int j = data.size(); j <= 4; j++) {
						data.add("");
					}

					year = data.get(0).toString().equals("") ? null : data.get(
							0).toString();
					week = data.get(1).toString().equals("") ? null : data.get(
							1).toString();
					team = data.get(2).toString().equals("") ? null : data.get(
							2).toString();
					name = data.get(3).toString().equals("") ? null : data.get(
							3).toString();
					regency = data.get(4).toString().equals("") ? null : data
							.get(4).toString();

					if (year == null || week == null || team == null
							|| name == null) {
						error = true;
					}
					else if (week != null && year != null)
					{
						if(isNumeric(week)== false || isNumeric(year) == false)
							error = true;
					}
					if(error == false)
					{
						alshiftList.setTimeDistance(year);
						alshiftList.setWeek(Integer.parseInt(week));
					}
					else
					{
						if(isNumeric(week))
							alshiftList.setWeek(Integer.parseInt(week));
						if(isNumeric(year))
							alshiftList.setTimeDistance(year);
					}
					alshiftList.setTeam(team);
					alshiftList.setName(name);
					alshiftList.setRegency(regency);
					alshiftList.setCreateBy(username);
					alshiftList.setCreateDate(date);

					if (error) {
						failedList.add(alshiftList);
					} else {
						successList.add(alshiftList);
					}
				}
			}
		}

		if (failedList.size() == 0 && successList.size() > 0) {
			for (VAlShiftLeaderCalendar item : successList) {
				alShiftLeaderCalendarDAO.insertSelective(item);
			}
			saveMessage(request, "Upload Lịch trực lãnh đạo thành công"); // Upload
																			// thành
																			// công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			saveMessage(request, "Không có dữ liệu"); // Không có dữ liệu
		} else {
			saveMessage(request, "Upload lỗi"); // Upload lỗi
		}
		int successNumLeader = 0;
		int failedNumLeader = 0;
		successNumLeader = successList.size();
		failedNumLeader = failedList.size();
		model.addAttribute("successList", successList); // thành
		model.addAttribute("failedList", failedList);
		model.addAttribute("successNumLeader", successNumLeader);
		model.addAttribute("failedNumLeader", failedNumLeader);

		model.addAttribute("totalNum", successNumLeader + failedNumLeader);
		return "jsp/AlShiftCalendar/alShiftCalendarUpload";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, Model model,
			HttpServletRequest request) throws IOException, ParseException {
		List<SYS_PARAMETER> title = sysParamDao.getSPByCodeAndName(
				"V_AL_SHIFT_TITLE", "UPLOAD");
		model.addAttribute("title", title.get(0).getValue());

		List<AlShiftCalendar> successList = new ArrayList<AlShiftCalendar>();
		List<AlShiftCalendar> failedList = new ArrayList<AlShiftCalendar>();
		String year, week, team, area, session, thu2, thu3, thu4, thu5, thu6, thu7, cn;
		int failedNum = 0, successNum = 0;
		if (sheetData.size() > 0) {

			List sheet1 = (List) sheetData.get(0);

			if (sheet1.size() != 12) {

				saveMessageKey(request, "Định dạng tệp không đúng");
				return "jsp/AlShiftCalendar/alShiftCalendarUpload";
			}

			if (sheetData.size() > 1) {
				List footer = (List) sheetData.get(sheetData.size() - 1);

				while (footer.size() != 12 && sheetData.size() > 2) {
					boolean kt = true;
					for (int k = 0; k < footer.size(); k++) {
						if (!footer.get(k).toString().trim().equals("")) {
							kt = false;
						}
					}
					if (kt == true) {
						sheetData.remove(sheetData.size() - 1);
						// maxsize=sheetData.size();
					} else {
						for (int j = footer.size(); j <= 4; j++) {
							footer.add("");
						}
					}

					footer = (List) sheetData.get(sheetData.size() - 1);
				}

				for (int i = 1; i < sheetData.size(); i++) {
					boolean error = false;

					AlShiftCalendar alshiftList = new AlShiftCalendar();

					List data = (List) sheetData.get(i);

					for (int j = data.size(); j <= 4; j++) {
						data.add("");
					}

					year = data.get(0).toString().equals("") ? null : data.get(
							0).toString();
					week = data.get(1).toString().equals("") ? null : data.get(
							1).toString();
					team = data.get(2).toString().equals("") ? null : data.get(
							2).toString();
					area = data.get(3).toString().equals("") ? null : data.get(
							3).toString();
					session = data.get(4).toString().equals("") ? null : data
							.get(4).toString();
					thu2 = data.get(5).toString().equals("") ? null : data.get(
							5).toString();
					thu3 = data.get(6).toString().equals("") ? null : data.get(
							6).toString();
					thu4 = data.get(7).toString().equals("") ? null : data.get(
							7).toString();
					thu5 = data.get(8).toString().equals("") ? null : data.get(
							8).toString();
					thu6 = data.get(9).toString().equals("") ? null : data.get(
							9).toString();
					thu7 = data.get(10).toString().equals("") ? null : data
							.get(10).toString();
					cn = data.get(11).toString().equals("") ? null : data.get(
							11).toString();

					if (year == null || week == null || team == null || area == null
							|| session == null) {
						error = true;
					} 
					else if (week != null && year != null)
					{
						if(isNumeric(week)== false || isNumeric(year) == false)
							error = true;
					}
						

					thu2 = replaceString(thu2);
					thu3 = replaceString(thu3);
					thu4 = replaceString(thu4);
					thu5 = replaceString(thu5);
					thu6 = replaceString(thu6);
					thu7 = replaceString(thu7);
					cn = replaceString(cn);
					if(error == false)
					{
						alshiftList.setYear(Integer.parseInt(year));
						alshiftList.setWeek(Integer.parseInt(week));
					}
					else
					{
						if(isNumeric(week))
							alshiftList.setWeek(Integer.parseInt(week));
						if(isNumeric(year))
							alshiftList.setYear(Integer.parseInt(year));

					}

					alshiftList.setTeam(team);
					alshiftList.setArea(area);
					alshiftList.setSession(session);
					alshiftList.setThu2(thu2);
					alshiftList.setThu3(thu3);
					alshiftList.setThu4(thu4);
					alshiftList.setThu5(thu5);
					alshiftList.setThu6(thu6);
					alshiftList.setThu7(thu7);
					alshiftList.setCn(cn);

					if (error) {
						failedList.add(alshiftList);
					} else {
						successList.add(alshiftList);
					}
				}
			}
		}

		if (failedList.size() == 0 && successList.size() > 0) {
			for (AlShiftCalendar item : successList) {
				alShiftCalendarDAO.insertSelective(item);
			}
			saveMessage(request, "Upload lịch trực ca thành công"); // Upload
		} else if (failedList.size() == 0 && successList.size() == 0) {
			saveMessage(request, "Không có dữ liệu"); // Không có dữ liệu
		} else {
			saveMessage(request, "Upload lỗi"); // Upload lỗi

		}
		successNum = successList.size();
		failedNum = failedList.size();
		model.addAttribute("successList", successList); // thành
		model.addAttribute("failedList", failedList);
		model.addAttribute("successNum", successNum);
		model.addAttribute("failedNum", failedNum);

		model.addAttribute("totalNum", successNum + failedNum);

		return "jsp/AlShiftCalendar/alShiftCalendarUpload";
	}

	private String replaceString(String str) {
		if (str.contains("DVT")) {
			str = str.substring(0, 6);
		}
		return str;
	}

	// Update data in grid
	// return: status
	@RequestMapping(value = "form/save-alshift")
	public @ResponseBody
	Map<String, Object> saveData(String key_t2, String key_t3, String key_t4,
			String key_t5, String key_t6, String key_t7, String key_cn,
			String key_id, String key_notice, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> data = new HashMap<String, Object>();

		String[] value_t2 = key_t2.split(",");
		String[] value_t3 = key_t3.split(",");
		String[] value_t4 = key_t4.split(",");
		String[] value_t5 = key_t5.split(",");
		String[] value_t6 = key_t6.split(",");
		String[] value_t7 = key_t7.split(",");
		String[] value_cn = key_cn.split(",");
		String[] value_Id = key_id.split(",");
		AlShiftCalendar record = new AlShiftCalendar();

		for (int i = 0; i < value_Id.length; i++) {
			if (value_Id[i] != null && !value_Id[i].toString().equals("null")
					&& !value_Id[i].toString().equals("")) {
				if (i == 0) {
					record.setNotice(key_notice);
				} else {
					record.setNotice("");
				}
				record.setId(Integer.parseInt(value_Id[i]));
				record.setThu2(value_t2[i]);
				record.setThu3(value_t3[i]);
				record.setThu4(value_t4[i]);
				record.setThu5(value_t5[i]);
				record.setThu6(value_t6[i]);
				record.setThu7(value_t7[i]);
				record.setCn(value_cn[i]);
				alShiftCalendarDAO.updateByPrimaryKeySelective(record);
			}

		}
		data.put("status", "success");
		return data;
	}

	// Check Number
	public boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional
												// '-' and decimal.
	}
}
