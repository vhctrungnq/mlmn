package controller.cable;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import bsh.ParseException;

import vo.CHighlightConfigs;
import vo.CableDropEt4direction;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.CableDropEt4directionFilter;
import vo.alarm.utils.Helper;
import vo.alarm.utils.UploadTools;

import controller.BaseController;
import dao.CHighlightConfigsDAO;
import dao.CableDropEt4directionDAO;
import dao.SysUsersDAO;
/**
 * 
 * @author QuangBD
 * @UpdateBy ThangPX
 * @UpdatedDate 01/04/2014
 * @Purpose Display Cable List and check role.
 *
 */
@Controller
@RequestMapping("/alarm/cableET4Direction/*")
public class CableET4DirectionController extends BaseController{
	@Autowired
	private CableDropEt4directionDAO cableET4directionDAO;
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	@Autowired 
	private SysUsersDAO  sysUserDao;
	
	@ModelAttribute("highlight")
	public String highlight() {
		String highlight = "";
		List<CHighlightConfigs> highlightConfigList2 = cHighlightConfigsDAO.getByKey("NOT_NULL");
		if (highlightConfigList2.size()>0)
		{
			System.out.println("Highligh:"+highlightConfigList2.get(0).getStyle());
			highlight = " $(this).find('.NOT_NULL').css({"+highlightConfigList2.get(0).getStyle()+"});";
		}
		return highlight;
	}
	
	@RequestMapping(value = "list")
	public ModelAndView list(@ModelAttribute("filter") CableDropEt4directionFilter filter, 
			@RequestParam(required = false) Integer delData, ModelMap model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> title = cableET4directionDAO.titleForm("CABLE_DROP_ET4_DIRECTION_LIST");
		model.addAttribute("title", title.get(0).getValue());
		
		int order = 0;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("cableDropeT4Driection").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("cableDropeT4Driection").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
	
		List<CableDropEt4direction> cableDropEt4directionList = new ArrayList<CableDropEt4direction>();
		cableDropEt4directionList = cableET4directionDAO.getList(filter, column, order, delData);
		 
		model.addAttribute("cableDropEt4directionList", cableDropEt4directionList);
		model.addAttribute("filter", filter);
		model.addAttribute("sizeList", 		cableDropEt4directionList==null?0:cableDropEt4directionList.size());
		 
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "Cabledropet4dircetion_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		//Check Role User(Administrator)
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SysUsers> userList = sysUserDao.checkRole(username);
		Helper.checkRole(userList, model);		
		return new ModelAndView("jspalarm/cable/cableET4directionList");
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) Integer id, HttpServletRequest request) {
		
		try {
			cableET4directionDAO.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch (Exception e) {
			saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return "redirect:list.htm";
	}
	
	@RequestMapping(value="form", method=RequestMethod.GET)
    public String showForm(@RequestParam(required = false) Integer id, ModelMap model) {
		
		CableDropEt4direction cableDropEt4direction;
		
		if (id == null) {
			List<SYS_PARAMETER> titleF = cableET4directionDAO.titleForm("ET4_DIRECTION_ADD");		 
			model.addAttribute("titleF", titleF.get(0).getValue());
			cableDropEt4direction = new CableDropEt4direction();
		} else {
			List<SYS_PARAMETER> titleF = cableET4directionDAO.titleForm("ET4_DIRECTION_UPDATE");	
			model.addAttribute("titleF", titleF.get(0).getValue());
			cableDropEt4direction = cableET4directionDAO.selectById(id);
		}

		model.addAttribute("cableDropEt4direction", cableDropEt4direction);
		
		return "jspalarm/cable/cableET4directionForm";
	}
	
	@RequestMapping(value="form", method=RequestMethod.POST)
	public String onSubmit(@Valid CableDropEt4direction cableDropEt4direction,BindingResult result, HttpServletRequest request,Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if (!result.hasErrors())
		{
			CableDropEt4direction oldCableDropEt4direction= cableET4directionDAO.selectByPrimaryKey(cableDropEt4direction.getCircuit(), cableDropEt4direction.getDdfHead(), cableDropEt4direction.getDdfFinish());
			if (cableDropEt4direction.getId() == null) {
				if (oldCableDropEt4direction == null) {
					
					cableDropEt4direction.setCreatedBy(username);
					cableDropEt4direction.setCreatDate(new Date());
					cableET4directionDAO.insert(cableDropEt4direction);
					saveMessage(request, "Thêm mới ET4 Direction thành công.");
				}
				else {
					saveMessage(request, "ET4 Direction đã tồn tại");
					model.addAttribute("cableDropEt4direction", cableDropEt4direction);
					List<SYS_PARAMETER> titleF = cableET4directionDAO.titleForm("ET4_DIRECTION_ADD");		 
					model.addAttribute("titleF", titleF.get(0).getValue());
					return "jspalarm/cable/cableET4directionForm";
				}
			} else {
				if (oldCableDropEt4direction!=null && !oldCableDropEt4direction.getId().equals(cableDropEt4direction.getId()))
				{
					saveMessage(request, "ET4 AREA đã tồn tại");
					model.addAttribute("cableDropEt4direction", cableDropEt4direction);
					List<SYS_PARAMETER> titleF = cableET4directionDAO.titleForm("ET4_DIRECTION_UPDATE");	
					model.addAttribute("titleF", titleF.get(0).getValue());
					return "jspalarm/cable/cableET4directionForm";
				}
				cableDropEt4direction.setCreatedBy(username);
				cableDropEt4direction.setCreatDate(new Date());
				cableET4directionDAO.updateByPrimaryKey(cableDropEt4direction);
				saveMessage(request, "ET4 DIRECTION được cập nhật thành công.");
			}
			return "redirect:list.htm";
		}
		else
		{
			model.addAttribute("cableDropEt4direction", cableDropEt4direction);

			return "jspalarm/cable/cableET4directionForm";
		}
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(Model model) {
		List<SYS_PARAMETER> titleU = cableET4directionDAO.titleForm("ET4_DIRECTION_UPLOAD"); 
		model.addAttribute("titleU", titleU.get(0).getValue());
		return "jspalarm/cable/cableET4directionUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("filePath") MultipartFile filePath, Model model, HttpServletRequest request) throws IOException, ParseException {
		
		List<SYS_PARAMETER> titleU = cableET4directionDAO.titleForm("ET4_DIRECTION_UPLOAD"); 
		if (!filePath.isEmpty()) { 
			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xlsx")||fileExtn.equals("xls")) {
				if (fileExtn.equals("xlsx")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFileForCable(filePath.getInputStream());
					importContent(sheetData,model,request);
				}
				
				if (fileExtn.equals("xls")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFileForCable(filePath.getInputStream());
					importContent(sheetData,model,request);
				}
			}
			else {
				 saveMessageKey(request, "cautruc.typeFIle");
			}
		}
		else {
			saveMessageKey(request, "cautruc.emptyFile");
		}
		model.addAttribute("titleU", titleU.get(0).getValue());
		return "jspalarm/cable/cableET4directionUpload"; 
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent (List sheetData, Model model, HttpServletRequest request) throws IOException, ParseException {
		List<SYS_PARAMETER> titleU = cableET4directionDAO.titleForm("ET4_DIRECTION_UPLOAD"); 
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<CableDropEt4direction> successList = new ArrayList<CableDropEt4direction>();
		List<CableDropEt4direction> failedList = new ArrayList<CableDropEt4direction>();
		List<CableDropEt4direction> success = new ArrayList<CableDropEt4direction>();

		String circuit;
		String dipName;
		String direction;
		String dipp;
		String ddfHead;
		String dipFinish;
		String ddfFinish;
		String description;
		String status;

		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 9) {
        		model.addAttribute("titleU", titleU.get(0).getValue());
        		saveMessageKey(request, "Định dạng tệp không đúng"); 
        		return "jspalarm/cable/CableET4directionUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		List footer= (List) sheetData.get(sheetData.size() - 1);
        		
        		while (footer.size() != 9 && sheetData.size() > 2) {
        			boolean kt=true;
        			for (int k=0;k<footer.size();k++) {
        				if (!footer.get(k).toString().trim().equals("")) {
        					kt=false;
        				}
        			}
        			if (kt==true) {
	        			sheetData.remove(sheetData.size()-1);
	        		//	maxsize=sheetData.size();
        			}
        			else {
        				for (int j=footer.size(); j<=9; j++) {
        					footer.add("");
         				}
        			}
        			
	        		footer= (List)sheetData.get(sheetData.size()-1);
        		}
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			CableDropEt4direction cableDropEt4directionList = new CableDropEt4direction();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=9; j++) {
     					data.add("");
     				}
        			 
        			dipName			= data.get(0).toString().equals("")?null:data.get(0).toString();
        			direction		= data.get(1).toString().equals("")?null:data.get(1).toString();
        			dipp			= data.get(2).toString().equals("")?null:data.get(2).toString();
        			ddfHead			= data.get(3).toString().equals("")?null:data.get(3).toString();
        			dipFinish		= data.get(4).toString().equals("")?null:data.get(4).toString();
        			ddfFinish		= data.get(5).toString().equals("")?null:data.get(5).toString();
        			description		= data.get(6).toString().equals("")?null:data.get(6).toString();
        			status			= data.get(7).toString().equals("")?null:data.get(7).toString();
        			circuit			= data.get(8).toString().equals("")?null:data.get(8).toString();
					if (circuit == null || ddfHead == null  || ddfFinish == null
							|| (dipName != null && dipName.length() >= 20)
							|| (direction != null && direction.length() >= 50)
							|| (dipp != null && dipp.length() >= 20)
							|| (description != null && description.length() >= 500)
							|| (status != null && status.length() >= 50))
					{ 
						error = true;
					}
					
					cableDropEt4directionList.setCircuit(circuit);
					cableDropEt4directionList.setDipName(dipName);
					cableDropEt4directionList.setDirection(direction);
					cableDropEt4directionList.setDipp(dipp);
					cableDropEt4directionList.setDdfHead(ddfHead);
					cableDropEt4directionList.setDipFinish(dipFinish);
					cableDropEt4directionList.setDdfFinish(ddfFinish);
					cableDropEt4directionList.setDescription(description);
					cableDropEt4directionList.setStatus(status);
					
					
					if (circuit == null && ddfHead == null  && ddfFinish == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(cableDropEt4directionList);
    					} else  {
    						successList.add(cableDropEt4directionList);
    					}
					}
				}
        	}
		}
		
		
		for (CableDropEt4direction item: successList) {  
			
			CableDropEt4direction cableDropEt4directionOld = cableET4directionDAO.selectByPrimaryKey(
					item.getCircuit(), item.getDdfHead(),item.getDdfFinish());
			
				if (cableDropEt4directionOld != null) {  
					item.setCreatedBy(username);
					item.setCreatDate(new Date());
					item.setId(cableDropEt4directionOld.getId());
					
					cableET4directionDAO.updateByPrimaryKey(item);
				} 
				else
				{  
					item.setCreatedBy(username);
					item.setCreatDate(new Date());
					cableET4directionDAO.insertSelective(item);
				}
				success.add(item);
		}
		
		if (failedList.size() == 0 && successList.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			model.addAttribute("status", "Dữ liệu ET4 Direction không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");					// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", successList.size());
		model.addAttribute("totalNum", failedList.size()+successList.size());
		model.addAttribute("successList", successList);
		model.addAttribute("failedList", failedList);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "DROPET4Direction_" + dateNow;
				
		model.addAttribute("exportFileName", exportFileName);
		model.addAttribute("titleU", titleU.get(0).getValue());		
		return "jspalarm/cable/cableET4directionUpload";
	}
}
