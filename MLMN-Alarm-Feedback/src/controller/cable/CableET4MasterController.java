package controller.cable;

import java.io.IOException;
import java.text.ParseException;
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

import vo.CHighlightConfigs;
import vo.CableET4Master; 
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.CableET4MasterFilter;
import vo.alarm.utils.Helper;
import vo.alarm.utils.UploadTools;

import controller.BaseController;
import dao.CHighlightConfigsDAO;
import dao.CableET4MasterDAO;
import dao.SysUsersDAO;

@Controller
@RequestMapping("/alarm/cableET4Master/*")
public class CableET4MasterController extends BaseController {
	@Autowired
	private CableET4MasterDAO cableET4MasterDAO;
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
	public ModelAndView list(@ModelAttribute("filter") CableET4MasterFilter filter, 
			@RequestParam(required = false) Integer delData, ModelMap model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> title = cableET4MasterDAO.titleForm("ET4_AT_AREA_LIST");
		model.addAttribute("title", title.get(0).getValue());
		
		int order = 0;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("cableET4Master").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("cableET4Master").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
	
		List<CableET4Master> cableET4MasterList = new ArrayList<CableET4Master>();
		try
		{
			cableET4MasterList = cableET4MasterDAO.getList(filter, column, order, delData);
		}
		catch (Exception exp)
		{
			cableET4MasterList= null;
		} 
		 
		model.addAttribute("cableET4MasterList", cableET4MasterList);
		model.addAttribute("filter", 		filter);
		model.addAttribute("sizeList", 		cableET4MasterList==null?0:cableET4MasterList.size());
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "Cabledropet4tl_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		//Check Role User(Administrator)
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SysUsers> userList = sysUserDao.checkRole(username);
		Helper.checkRole(userList, model);		
		return new ModelAndView("jspalarm/cable/cableET4MasterList");
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) Integer id, HttpServletRequest request) {
		
		try {
			cableET4MasterDAO.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch (Exception e) {
			saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return "redirect:list.htm";
	}
	
	@RequestMapping(value="form", method=RequestMethod.GET)
    public String showForm(@RequestParam(required = false) Integer id, ModelMap model) {
		
		CableET4Master cableET4Master;
		
		if (id == null) {
			List<SYS_PARAMETER> titleF = cableET4MasterDAO.titleForm("ET4_MARTER_ADD");		 
			model.addAttribute("titleF", titleF.get(0).getValue());
			cableET4Master = new CableET4Master();
		} else {
			List<SYS_PARAMETER> titleF = cableET4MasterDAO.titleForm("ET4_MARTER_UPDATE");	
			model.addAttribute("titleF", titleF.get(0).getValue());
			cableET4Master = cableET4MasterDAO.selectById(id);
		}

		model.addAttribute("cableET4Master", cableET4Master);
		
		return "jspalarm/cable/cableET4MarterForm";
	}
	
	@RequestMapping(value="form", method=RequestMethod.POST)
	public String onSubmit(@Valid CableET4Master cableET4Master,BindingResult result, HttpServletRequest request,Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if (!result.hasErrors())
		{
			CableET4Master oldCableET4Master= cableET4MasterDAO.selectByPrimaryKey(cableET4Master.getArea(), cableET4Master.getExchange(), cableET4Master.getSnt());
			if (cableET4Master.getId() == null) {
				if (oldCableET4Master == null) {
					
					cableET4Master.setCreatedBy(username);
					cableET4Master.setCreateDate(new Date());
					cableET4MasterDAO.insert(cableET4Master);
					saveMessage(request, "Thêm mới ET4 AREA thành công.");
				}
				else {
					saveMessage(request, "ET4 AREA đã tồn tại");
					model.addAttribute("cableET4Master", cableET4Master);
					List<SYS_PARAMETER> titleF = cableET4MasterDAO.titleForm("ET4_MARTER_ADD");		 
					model.addAttribute("titleF", titleF.get(0).getValue());
					return "jspalarm/cable/cableET4MarterForm";
				}
			} else {
				if (oldCableET4Master!=null && !oldCableET4Master.getId().equals(cableET4Master.getId()))
				{
					saveMessage(request, "ET4 AREA đã tồn tại");
					model.addAttribute("cableET4Master", cableET4Master);
					List<SYS_PARAMETER> titleF = cableET4MasterDAO.titleForm("ET4_MARTER_UPDATE");	
					model.addAttribute("titleF", titleF.get(0).getValue());
					return "jspalarm/cable/cableET4MarterForm";
				}
				cableET4Master.setCreatedBy(username);
				cableET4Master.setCreateDate(new Date());
				cableET4MasterDAO.updateByPrimaryKey(cableET4Master);
				saveMessage(request, "ET4 AREA được cập nhật thành công.");
			}
			return "redirect:list.htm";
		}
		else
		{
			model.addAttribute("cableET4Master", cableET4Master);

			return "jspalarm/cable/cableET4MarterForm";
		}
	}
	
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(Model model) {
		List<SYS_PARAMETER> titleU = cableET4MasterDAO.titleForm("ET4_MARTER_UPLOAD"); 
		model.addAttribute("titleU", titleU.get(0).getValue());
		return "jspalarm/cable/cableET4MarterUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("filePath") MultipartFile filePath, Model model, HttpServletRequest request) throws IOException, ParseException {
		
		List<SYS_PARAMETER> titleU = cableET4MasterDAO.titleForm("ET4_MARTER_UPLOAD"); 
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
		return "jspalarm/cable/cableET4MarterUpload"; 
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent (List sheetData, Model model, HttpServletRequest request) throws IOException, ParseException {
		List<SYS_PARAMETER> titleU = cableET4MasterDAO.titleForm("ET4_MARTER_UPLOAD"); 
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<CableET4Master> successList = new ArrayList<CableET4Master>();
		List<CableET4Master> failedList = new ArrayList<CableET4Master>();
		List<CableET4Master> success = new ArrayList<CableET4Master>();
		
		String area;
		String exchange;
		String snt;
		String sdip;
		String pos;
		String posNew;
		String ddf;
		String ddfNew;
		String node;
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 9) {
        		model.addAttribute("titleU", titleU.get(0).getValue());
        		saveMessageKey(request, "Định dạng tệp không đúng"); 
        		return "jspalarm/cable/cableET4MarterUpload";
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
        			
        			CableET4Master cableET4MasterList = new CableET4Master();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=9; j++) {
     					data.add("");
     				}
        			 
        			exchange		= data.get(0).toString().equals("")?null:data.get(0).toString();
        			snt				= data.get(1).toString().equals("")?null:data.get(1).toString();
        			sdip			= data.get(2).toString().equals("")?null:data.get(2).toString();
        			pos				= data.get(3).toString().equals("")?null:data.get(3).toString();
        			posNew			= data.get(4).toString().equals("")?null:data.get(4).toString();
        			ddf				= data.get(5).toString().equals("")?null:data.get(5).toString();
        			ddfNew			= data.get(6).toString().equals("")?null:data.get(6).toString();
        			node			= data.get(7).toString().equals("")?null:data.get(7).toString();
        			area			= data.get(8).toString().equals("")?null:data.get(8).toString();
        			
					if (area == null || exchange == null || snt == null 
							|| (area != null && area.length() >= 20)
							|| (exchange != null && exchange.length() >= 255)
							|| (snt != null && snt.length() >= 50)
							|| (sdip != null && sdip.length() >= 20)
							|| (pos != null && pos.length() >= 100)
							|| (posNew != null && posNew.length() >= 100)
							|| (ddf != null && ddf.length() >= 255)
							|| (ddfNew != null && ddfNew.length() >= 255)
							|| (node != null && node.length() >= 255)) 
					{ 
						error = true;
					}
					
					cableET4MasterList.setArea(area);
					cableET4MasterList.setExchange(exchange);
					cableET4MasterList.setSnt(snt);
					cableET4MasterList.setSdip(sdip);
					cableET4MasterList.setPos(pos);
					cableET4MasterList.setPosNew(posNew);
					cableET4MasterList.setDdf(ddfNew);
					cableET4MasterList.setDdfNew(ddfNew);
					cableET4MasterList.setNode(node); 
					
					if (area == null && exchange == null  && snt == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(cableET4MasterList);
    					} else  {
    						successList.add(cableET4MasterList);
    					}
        			} 
				}
        	}
		}
		
		for (CableET4Master item: successList) {  
			
			CableET4Master cableET4MasterOld = cableET4MasterDAO.selectByPrimaryKey(
					item.getArea(), item.getExchange(),item.getSnt());
			
				if (cableET4MasterOld != null) {  
					item.setCreatedBy(username);
					item.setCreateDate(new Date());
					item.setId(cableET4MasterOld.getId());
					
					cableET4MasterDAO.updateByPrimaryKey(item);
				} 
				else
				{  
					item.setCreatedBy(username);
					item.setCreateDate(new Date());
					cableET4MasterDAO.insertSelective(item);
				}
				success.add(item);
		}
		
		if (failedList.size() == 0 && successList.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			model.addAttribute("status", "Dữ liệu ET4 AT AREA không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");					// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", successList.size());
		model.addAttribute("totalNum", failedList.size()+successList.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList); 
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "ET4Master_" + dateNow;
				
		model.addAttribute("exportFileName", exportFileName);
		model.addAttribute("titleU", titleU.get(0).getValue());		
		return "jspalarm/cable/cableET4MarterUpload";
	}
}
