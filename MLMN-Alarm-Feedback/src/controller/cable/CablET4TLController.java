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
import vo.CableDropEt4tl;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.CableDropEt4tlFilter;
import vo.alarm.utils.Helper;
import vo.alarm.utils.UploadTools;

import controller.BaseController;

import dao.CHighlightConfigsDAO;
import dao.CableDropEt4tlDAO;
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
@RequestMapping("/alarm/cableET4TL/*")
public class CablET4TLController extends BaseController{
	@Autowired
	private CableDropEt4tlDAO cableet4tlDao;
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
	public ModelAndView list(@ModelAttribute("filter") CableDropEt4tlFilter filter, 
			@RequestParam(required = false) Integer delData, ModelMap model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> title = cableet4tlDao.titleForm("CABLE_DROP_ET4_TL_LIST");
		model.addAttribute("title", title.get(0).getValue());
		
		int order = 0;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("cableDropeT4Tl").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("cableDropeT4Tl").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
	
		if(filter.getArea() != null)
			filter.setArea(filter.getArea().trim());
		if(filter.getDirectionCon() != null)
			filter.setDirectionCon(filter.getDirectionCon().trim());
		if(filter.getInfo() != null)
			filter.setInfo(filter.getInfo().trim());
		List<CableDropEt4tl> cabledropet4tlList = cableet4tlDao.getList(filter, column, order, delData);

		model.addAttribute("cabledropet4tlList", cabledropet4tlList);
		model.addAttribute("filter", filter);
		model.addAttribute("sizeList", 		cabledropet4tlList==null?0:cabledropet4tlList.size());
		 
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
		return new ModelAndView("jspalarm/cable/CableET4TLList");
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) Integer id, HttpServletRequest request) {
		
		try {
			cableet4tlDao.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch (Exception e) {
			saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return "redirect:list.htm";
	}
	
	@RequestMapping(value="form", method=RequestMethod.GET)
    public String showForm(@RequestParam(required = false) Integer id, ModelMap model) {
		
		CableDropEt4tl cabledropet4tl;
		
		if (id == null) {
			List<SYS_PARAMETER> titleF = cableet4tlDao.titleForm("ET4TL_NUMBER_ADD");		 
			model.addAttribute("titleF", titleF.get(0).getValue());
			cabledropet4tl = new CableDropEt4tl();
		} else {
			List<SYS_PARAMETER> titleF = cableet4tlDao.titleForm("ET4TL_NUMBER_UPDATE");	
			model.addAttribute("titleF", titleF.get(0).getValue());
			cabledropet4tl = cableet4tlDao.selectById(id);
		}

		model.addAttribute("cabledropet4tl", cabledropet4tl);
		
		return "jspalarm/cable/CableET4TLForm";
	}
	
	
	@RequestMapping(value="form", method=RequestMethod.POST)
	public String onSubmit(@ModelAttribute("cabledropet4tl") @Valid CableDropEt4tl cabledropet4tl,BindingResult result, HttpServletRequest request,Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if (!result.hasErrors())
		{
			CableDropEt4tl oldCableDropEt4tl= cableet4tlDao.selectByPrimaryKey(cabledropet4tl.getArea(), cabledropet4tl.getDirectionCon(), 
					cabledropet4tl.getDipL2Name(), cabledropet4tl.getDipL10Name());
			
			if (cabledropet4tl.getId() == null) {
				if (oldCableDropEt4tl == null) {
					
					cabledropet4tl.setCreatedBy(username);
					cabledropet4tl.setCreatDate(new Date());
					cableet4tlDao.insert(cabledropet4tl);
					saveMessage(request, "Thêm mới ET4 TL thành công.");
					
					return "redirect:list.htm";
				}
				else {
					saveMessage(request, "ET4 TL đã tồn tại");
					model.addAttribute("cabledropet4tl", cabledropet4tl);
					List<SYS_PARAMETER> titleF = cableet4tlDao.titleForm("ET4TL_NUMBER_ADD");
					model.addAttribute("titleF", titleF.get(0).getValue());
					
					return "jspalarm/cable/CableET4TLForm";
				}
			} else {
				if (oldCableDropEt4tl!=null && !oldCableDropEt4tl.getId().equals(cabledropet4tl.getId()))
				{
					saveMessage(request, "ET4 AREA đã tồn tại");
					model.addAttribute("cabledropet4tl", cabledropet4tl); 	
					List<SYS_PARAMETER> titleF = cableet4tlDao.titleForm("ET4_DIRECTION_UPDATE");	
					model.addAttribute("titleF", titleF.get(0).getValue());
					
					return "jspalarm/cable/CableET4TLForm";
				}
				
				cabledropet4tl.setCreatedBy(username);
				cabledropet4tl.setCreatDate(new Date());
				cableet4tlDao.updateByPrimaryKey(cabledropet4tl);
				saveMessage(request, "ET4 TL được cập nhật thành công.");
				
				return "redirect:list.htm";
			} 
		}
		else
		{
			model.addAttribute("cabledropet4tl", cabledropet4tl);

			return "jspalarm/cable/CableET4TLForm";
		}
	}
	
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(Model model) {
		List<SYS_PARAMETER> titleU = cableet4tlDao.titleForm("ET4TL_NUMBER_UPLOAD"); 
		model.addAttribute("titleU", titleU.get(0).getValue());
		return "jspalarm/cable/CableET4TLUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("filePath") MultipartFile filePath, Model model, HttpServletRequest request) throws IOException, ParseException {
		
		List<SYS_PARAMETER> titleU = cableet4tlDao.titleForm("ET4TL_NUMBER_UPLOAD"); 
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
		return "jspalarm/cable/CableET4TLUpload"; 
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent (List sheetData, Model model, HttpServletRequest request) throws IOException, ParseException {
		List<SYS_PARAMETER> titleU = cableet4tlDao.titleForm("ET4TL_NUMBER_UPLOAD"); 
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<CableDropEt4tl> successList = new ArrayList<CableDropEt4tl>();
		List<CableDropEt4tl> failedList = new ArrayList<CableDropEt4tl>();
		List<CableDropEt4tl> success = new ArrayList<CableDropEt4tl>();

		String area;
		String directionCon;
		String dipL2Name;
		String labelL2Name;
		String locationL2;
		String dipL10Name;
		String labelL10Name;
		String locationL10;
		String locationL10New;
		String locationSgsn;
		String et4L2L10;
		String description;
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 12) {
        		model.addAttribute("titleU", titleU.get(0).getValue());
        		saveMessageKey(request, "Định dạng tệp không đúng"); 
        		return "jspalarm/cable/CableET4TLUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		List footer= (List) sheetData.get(sheetData.size() - 1);
        		
        		while (footer.size() != 12 && sheetData.size() > 2) {
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
        				for (int j=footer.size(); j<=11; j++) {
        					footer.add("");
         				}
        			}
        			
	        		footer= (List)sheetData.get(sheetData.size()-1);
        		}
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			CableDropEt4tl cableDropEt4tlList = new CableDropEt4tl();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=11; j++) {
     					data.add("");
     				}
        			 
        			area			= data.get(0).toString().equals("")?null:data.get(0).toString();
        			dipL2Name		= data.get(1).toString().equals("")?null:data.get(1).toString();
        			labelL2Name		= data.get(2).toString().equals("")?null:data.get(2).toString();
        			locationL2		= data.get(3).toString().equals("")?null:data.get(3).toString();
        			dipL10Name		= data.get(4).toString().equals("")?null:data.get(4).toString();
        			labelL10Name	= data.get(5).toString().equals("")?null:data.get(5).toString();
        			locationL10		= data.get(6).toString().equals("")?null:data.get(6).toString();
        			locationL10New	= data.get(7).toString().equals("")?null:data.get(7).toString();
        			locationSgsn	= data.get(8).toString().equals("")?null:data.get(8).toString();
        			et4L2L10		= data.get(9).toString().equals("")?null:data.get(9).toString();
        			description		= data.get(10).toString().equals("")?null:data.get(10).toString();
        			directionCon	= data.get(11).toString().equals("")?null:data.get(11).toString();
        			
					if (area == null || directionCon == null || dipL2Name == null || dipL10Name == null
							|| (area != null && area.length() >= 30)
							|| (directionCon != null && directionCon.length() >= 50)
							|| (labelL2Name != null && labelL2Name.length() >= 30)
							|| (labelL10Name != null && labelL10Name.length() >= 30)
							|| (locationL10 != null && locationL10.length() >= 30)
							|| (locationL10New != null && locationL10New.length() >= 30)
							|| (locationSgsn != null && locationSgsn.length() >= 10 )
							|| (et4L2L10 != null && et4L2L10.length() >= 30)
							|| (description != null && description.length() >= 300)) 
					{ 
						error = true;
					}
					
					cableDropEt4tlList.setArea(area);
					cableDropEt4tlList.setDirectionCon(directionCon);
					cableDropEt4tlList.setDipL2Name(dipL2Name);
					cableDropEt4tlList.setLabelL2Name(labelL2Name);
					cableDropEt4tlList.setLocationL2(locationL2);
					cableDropEt4tlList.setDipL10Name(dipL10Name);
					cableDropEt4tlList.setLabelL10Name(labelL10Name);
					cableDropEt4tlList.setLocationL10(locationL10);
					cableDropEt4tlList.setLocationL10New(locationL10New);
					cableDropEt4tlList.setLocationSgsn(locationSgsn);
					cableDropEt4tlList.setEt4L2L10(et4L2L10);
					cableDropEt4tlList.setDescription(description);
					
					if (area == null && directionCon == null  && dipL2Name == null && dipL10Name == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(cableDropEt4tlList);
    					} else  {
    						successList.add(cableDropEt4tlList);
    					}
        			} 
				}
        	}
		}
		
		for (CableDropEt4tl item: successList) {  
			
			CableDropEt4tl cableDropEt4tlOld = cableet4tlDao.selectByPrimaryKey(
					item.getArea(), item.getDirectionCon(), item.getDipL2Name(), item.getDipL10Name());
			
				if (cableDropEt4tlOld != null) {  
					item.setCreatedBy(username);
					item.setCreatDate(new Date());
					item.setId(cableDropEt4tlOld.getId());
					
					cableet4tlDao.updateByPrimaryKey(item);
				} 
				else
				{  
					item.setCreatedBy(username);
					item.setCreatDate(new Date());
					cableet4tlDao.insertSelective(item);
				}
				success.add(item);
		}
		
		if (failedList.size() == 0 && successList.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			model.addAttribute("status", "Dữ liệu ET4 TL không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");					// Upload lỗi
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
		String exportFileName = "ET4TL_" + dateNow;
				
		model.addAttribute("exportFileName", exportFileName);
		model.addAttribute("titleU", titleU.get(0).getValue());		
		return "jspalarm/cable/CableET4TLUpload";
	}

}
