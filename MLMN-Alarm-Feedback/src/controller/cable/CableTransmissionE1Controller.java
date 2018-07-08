package controller.cable;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

import vo.CHighlightConfigs;
import vo.CableTransmissionE1;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.Helper;
import vo.alarm.utils.UploadTools;
import controller.BaseController;
import dao.CHighlightConfigsDAO;
import dao.CableTransmissionE1DAO;
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
@RequestMapping("/alarm/cableE1/*")
public class CableTransmissionE1Controller extends BaseController {
	@Autowired
	private CableTransmissionE1DAO cableTransmissionE1DAO;
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
	
	@RequestMapping(value="list")
    public String list(CableTransmissionE1 cableE1,
    		@RequestParam(required = false) String typeC,
    		@RequestParam(required = false) Integer delData,
					   ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		List<SYS_PARAMETER> sysParemeterTitle = cableTransmissionE1DAO.titleForm(typeC,null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		model.addAttribute("typeC",typeC);
		
		if (cableE1.getDirectionId() != null) {
			cableE1.setDirectionId(cableE1.getDirectionId().trim());
		}
		
		if (cableE1.getDev() != null) {
			cableE1.setDev(cableE1.getDev().trim());
		}
		
		if (cableE1.getSnt()!= null) {
			cableE1.setSnt(cableE1.getSnt().trim());
		}
		
		if (cableE1.getDip() != null) {
			cableE1.setDip(cableE1.getDip().trim());
		}
		
		if (cableE1.getDdfOut() != null) {
			cableE1.setDdfOut(cableE1.getDdfOut().trim());
		}
		
		if (cableE1.getState() != null) {
			cableE1.setState(cableE1.getState().trim());
		}
		
		if (cableE1.getPlaneNext() != null) {
			cableE1.setPlaneNext(cableE1.getPlaneNext().trim());
		}
		
		if (cableE1.getDescription() != null) {
			cableE1.setDescription(cableE1.getDescription().trim());
		}
		cableE1.setTypeCab(typeC);
		
		int order = 1;
		String column = "DIRECTION_ID";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) { }
		
		List<CableTransmissionE1> cableE1List = cableTransmissionE1DAO.getCableE1Filter(cableE1, column, order, delData);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "DSTruyenDanCapE1_" + dateNow;
				
		model.addAttribute("exportFileName", exportFileName);
				
		model.addAttribute("cableE1", cableE1);
		
		model.addAttribute("cableE1List", cableE1List);
		model.addAttribute("sizeList", 			cableE1List.size());
		//Check Role User(Administrator)
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SysUsers> userList = sysUserDao.checkRole(username);
		Helper.checkRole(userList, model);		
		return "jspalarm/cable/cableE1List";
	}
	
	@RequestMapping(value="form", method=RequestMethod.GET)
    public String cableForm(@RequestParam(required = false) Integer id,
    		@RequestParam(required = false) String typeC,ModelMap model) {
		
		List<SYS_PARAMETER> sysParemeterTitle = cableTransmissionE1DAO.titleForm(typeC,"ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
		}
		CableTransmissionE1 cableE1;
		
		if (id == null) {
			cableE1 = new CableTransmissionE1();
		} else {
			cableE1 = cableTransmissionE1DAO.selectByPrimaryKey(id);
		}
		model.addAttribute("cableE1", 	cableE1);
		model.addAttribute("typeC", 		typeC);
		
		return "jspalarm/cable/cableE1Form";
	}
	
	@RequestMapping(value="form", method=RequestMethod.POST)
    public String cableFormPost(@ModelAttribute("cableE1") @Valid CableTransmissionE1 cableE1, BindingResult result, 
    		@RequestParam(required = true) String typeC,
    		ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		List<SYS_PARAMETER> sysParemeterTitle = cableTransmissionE1DAO.titleForm(typeC,"ADD");
		String title="";
		if(sysParemeterTitle.size() > 0)
		{
			title=sysParemeterTitle.get(0).getValue();
			
		}
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if (result.hasErrors()) {
			model.addAttribute("cableE1", cableE1);
			model.addAttribute("typeC", 		typeC);
			saveMessageKey(request, "cableTransmission.InsertError");
			model.addAttribute("titleF", title);
			return "jspalarm/cable/cableE1Form";
		}
		else
		{
			CableTransmissionE1 cable = cableTransmissionE1DAO.checkExitByDev(cableE1.getDirectionId(),cableE1.getDev(),typeC);
			if (cableE1.getId() == null) {
				if (cable!=null)
				{
					model.addAttribute("cableE1", cableE1);
					model.addAttribute("typeC", 		typeC);
					saveMessageKey(request, "cableE1.exits");
					model.addAttribute("titleF", title);
					return "jspalarm/cable/cableE1Form";
				}
				cableE1.setCreatedBy(username);
				cableE1.setCreateDate(new Date());
				cableE1.setTypeCab(typeC);
				cableTransmissionE1DAO.insertSelective(cableE1);
				
				saveMessageKey(request, "cableE1.InsertSuccess");
				
			} else {
				if (cable!=null)
				{
					if (cable.getId().intValue()!=cableE1.getId().intValue())
					{
					System.out.println("ID cable: "+cable.getId()+"---id cableE1: "+cableE1.getId());
					model.addAttribute("cableE1", cableE1);
					model.addAttribute("typeC", 		typeC);
					saveMessageKey(request, "cableE1.exits");
					model.addAttribute("titleF", title);
					return "jspalarm/cable/cableE1Form";
					}
				}
				cableE1.setModifiedBy(username);
				cableE1.setModifyDate(new Date());
				cableTransmissionE1DAO.updateByPrimaryKeySelective(cableE1);
				
				saveMessageKey(request, "cableE1.UpdateSuccess");	

				
			}
		}
		
		return "redirect:list.htm?typeC="+typeC;
	}
	
	/*@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) Integer id,@RequestParam(required = true) String typeC,  HttpServletRequest request) {
		
		try {
			cableTransmissionE1DAO.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch (Exception e) {
			saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return "redirect:list.htm?typeC="+typeC;
	}*/
	
	@RequestMapping(value="ajax/get-cableE1-inf-search")
    public @ResponseBody 
    List<String> ajaxGetCableTransmissionInfoSearch(@RequestParam(required = false) String focus,@RequestParam(required = false) String typeC,@RequestParam(required = false) String term,
    		HttpServletRequest request, HttpServletResponse response) {
		
		List<String> paramList = new ArrayList<String>();
		paramList = cableTransmissionE1DAO.getCableE1Search(focus,typeC, term);
		
		return paramList;
	}
	
	@RequestMapping(value="ajax/deleteCableE1")
    public @ResponseBody 
    Integer ajaxDeleteCableLine(Integer id, HttpServletRequest request, HttpServletResponse response) {	
		cableTransmissionE1DAO.deleteByPrimaryKey(id);
		return 1;
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(@RequestParam(required = false) String typeC,Model model) {
		model.addAttribute("typeC", 		typeC);
		List<SYS_PARAMETER> sysParemeterTitle = cableTransmissionE1DAO.titleForm(typeC,"UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		return "jspalarm/cable/cableE1Upload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("filePath") MultipartFile filePath,@RequestParam(required = false) String typeC, Model model, HttpServletRequest request) throws IOException, ParseException {
		model.addAttribute("typeC", 		typeC);
		List<SYS_PARAMETER> sysParemeterTitle = cableTransmissionE1DAO.titleForm(typeC,"UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		if (!filePath.isEmpty()) {

			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xls")) {
				if (fileExtn.equals("xls")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFileForCable(filePath.getInputStream());
					if (typeC.equals("E1_BSG"))
					{
						importContentE1_BSG(sheetData,typeC,model,request);
					}
					else
					{
						importContent(sheetData,typeC,model,request);
					}
				}
			}
			else {
				 saveMessageKey(request, "cautruc.typeFIle");
			}
		}
		else {
			saveMessageKey(request, "cautruc.emptyFile");
		}
		
		return "jspalarm/cable/cableE1Upload";
		
	}

	private String importContent(List sheetData, String typeC, Model model,
			HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("typeC", 		typeC);
		List<SYS_PARAMETER> sysParemeterTitle = cableTransmissionE1DAO.titleForm(typeC,"UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		List<CableTransmissionE1> successList = new ArrayList<CableTransmissionE1>();
		List<CableTransmissionE1> failedList = new ArrayList<CableTransmissionE1>();
		List<CableTransmissionE1> success = new ArrayList<CableTransmissionE1>();
		
		String directionId;
		String rp;
		String em;
		String dev;
		String snt;
		String dip;
		String sntinlStr;
		String ddfOut;
		String state;
		String node;
		String planeNext;
		String description;
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 13) {
        		saveMessageKey(request, "Định dạng tệp không đúng");
        		
        		return "jspalarm/cable/cableE1Upload";
        	}
        	
        	if (sheetData.size() > 1) {
        		CableTransmissionE1 cableTransmission;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			cableTransmission = new CableTransmissionE1();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=13; j++) {
     					data.add("");
     				}
        			directionId			= data.get(12).toString().trim().equals("")?null:data.get(12).toString().trim().toUpperCase();
        			rp					= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			em					= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			dev					= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			snt					= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			sntinlStr			= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			dip					= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			ddfOut				= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			state				= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			node				= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			planeNext			= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			description			= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			
        			cableTransmission.setDirectionId(directionId);
        			cableTransmission.setRp(rp);
        			cableTransmission.setEm(em);
        			cableTransmission.setDev(dev);
        			cableTransmission.setSnt(snt);
        			cableTransmission.setDip(dip);
        			cableTransmission.setSntinlStr(sntinlStr);
        			cableTransmission.setDdfOut(ddfOut);
        			cableTransmission.setState(state);
        			cableTransmission.setNode(node);
        			cableTransmission.setPlaneNext(planeNext);
        			cableTransmission.setTypeCab(typeC);
        			cableTransmission.setDescription(description);
        			
        			if (directionId==null || dev == null 
							|| dev.length() >= 255
							|| (rp != null && rp.length() >= 20)
							|| (em != null && em.length() >= 20)
							|| (snt != null && snt.length() >= 50)
							|| (dip != null && dip.length() >= 50)
							|| (ddfOut != null && ddfOut.length() >= 255)
							|| (state != null && state.length() >= 255)
							|| (node != null && node.length() >= 255)
							|| (planeNext != null && planeNext.length() >= 500)
							|| (description != null && description.length() >= 1000)
						) {
						
						error = true;
					}
        			
        			int sntinl = 0;
        			if (sntinlStr!=null)
        			{
	        			try
	        			{
	        				sntinl = Integer.parseInt(sntinlStr);
	        				cableTransmission.setSntinl(sntinl);
	        			}
	        			catch(Exception exp)
	        			{
	        				sntinl=-1;
	        			}
        			}
        			if ((dev == null && directionId == null)) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error||sntinl==-1) {
    						failedList.add(cableTransmission);
    					} else  {
    						successList.add(cableTransmission);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (CableTransmissionE1 cab: successList) {
			
			try {
				CableTransmissionE1 cable = cableTransmissionE1DAO.checkExitByDev(cab.getDirectionId(),cab.getDev(),typeC);
    			if (cable != null) {
    				cab.setModifyDate(new Date());
    				cab.setModifiedBy(username);
    				cab.setId(cable.getId());
    				cableTransmissionE1DAO.updateByPrimaryKeySelective(cab);
    			} 
    			else
    			{
    				cab.setCreateDate(new Date());
    				cab.setCreatedBy(username);
    				cableTransmissionE1DAO.insertSelective(cab);
    			}
				success.add(cab);
			} catch (Exception ex) {
				failedList.add(cab);
			}
		}
		
		if (failedList.size() == 0 && successList.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			model.addAttribute("status", "Dữ liệu cáp không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");					// Upload lỗi
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
		String exportFileName = "DSCap_" + dateNow;
				
		model.addAttribute("exportFileName", exportFileName);
				
		
		return "jspalarm/cable/cableE1Upload";
	}

	private String importContentE1_BSG(List sheetData, String typeC, Model model,
			HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("typeC", 		typeC);
		List<SYS_PARAMETER> sysParemeterTitle = cableTransmissionE1DAO.titleForm(typeC,"UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		List<CableTransmissionE1> successList = new ArrayList<CableTransmissionE1>();
		List<CableTransmissionE1> failedList = new ArrayList<CableTransmissionE1>();
		List<CableTransmissionE1> success = new ArrayList<CableTransmissionE1>();
		
		String directionId;
		String rp;
		String em;
		String dev;
		String snt;
		String dip;
		String sntinlStr;
		String ddfOut;
		String state;
		String node;
		String planeNext;
		String directionTransmission;
		String description;
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 14) {
        		saveMessageKey(request, "Định dạng tệp không đúng");
        		
        		return "jspalarm/cable/cableE1Upload";
        	}
        	
        	if (sheetData.size() > 1) {
        		CableTransmissionE1 cableTransmission;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			cableTransmission = new CableTransmissionE1();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=14; j++) {
     					data.add("");
     				}
        			directionId			= data.get(13).toString().trim().equals("")?null:data.get(13).toString().trim().toUpperCase();
        			rp					= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			em					= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			dev					= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			snt					= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			sntinlStr			= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			dip					= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			ddfOut				= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			state				= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			node				= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			planeNext			= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			directionTransmission= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			description			= data.get(12).toString().trim().equals("")?null:data.get(12).toString().trim();
        			
        			cableTransmission.setDirectionId(directionId);
        			cableTransmission.setRp(rp);
        			cableTransmission.setEm(em);
        			cableTransmission.setDev(dev);
        			cableTransmission.setSnt(snt);
        			cableTransmission.setDip(dip);
        			cableTransmission.setSntinlStr(sntinlStr);
        			cableTransmission.setDdfOut(ddfOut);
        			cableTransmission.setState(state);
        			cableTransmission.setNode(node);
        			cableTransmission.setPlaneNext(planeNext);
        			cableTransmission.setDirectionTransmission(directionTransmission);
        			cableTransmission.setTypeCab(typeC);
        			cableTransmission.setDescription(description);
        			
        			if (directionId==null || dev == null 
							|| dev.length() >= 255
							|| (rp != null && rp.length() >= 20)
							|| (em != null && em.length() >= 20)
							|| (snt != null && snt.length() >= 50)
							|| (dip != null && dip.length() >= 50)
							|| (ddfOut != null && ddfOut.length() >= 255)
							|| (state != null && state.length() >= 255)
							|| (node != null && node.length() >= 255)
							|| (planeNext != null && planeNext.length() >= 500)
							|| (directionTransmission != null && directionTransmission.length() >= 255)
							|| (description != null && description.length() >= 1000)
						) {
						
						error = true;
					}
        			int sntinl = 0;
        			if (sntinlStr!=null)
        			{
	        			try
	        			{
	        				sntinl = Integer.parseInt(sntinlStr);
	        				cableTransmission.setSntinl(sntinl);
	        			}
	        			catch(Exception exp)
	        			{
	        				sntinl=-1;
	        			}
        			}
        			if ((dev == null && directionId == null)) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error||sntinl==-1) {
    						failedList.add(cableTransmission);
    					} else  {
    						successList.add(cableTransmission);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (CableTransmissionE1 cab: successList) {
			
			try {
				CableTransmissionE1 cable = cableTransmissionE1DAO.checkExitByDev(cab.getDirectionId(),cab.getDev(),typeC);
    			if (cable != null) {
    				cab.setModifyDate(new Date());
    				cab.setModifiedBy(username);
    				cab.setId(cable.getId());
    				cableTransmissionE1DAO.updateByPrimaryKeySelective(cab);
    			} 
    			else
    			{
    				cab.setCreateDate(new Date());
    				cab.setCreatedBy(username);
    				cableTransmissionE1DAO.insertSelective(cab);
    			}
				success.add(cab);
			} catch (Exception ex) {
				failedList.add(cab);
			}
		}
		
		if (failedList.size() == 0 && successList.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			model.addAttribute("status", "Dữ liệu cáp không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");					// Upload lỗi
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
		String exportFileName = "DSCap_" + dateNow;
				
		model.addAttribute("exportFileName", exportFileName);
				
		
		return "jspalarm/cable/cableE1Upload";
		
	}
	
}
