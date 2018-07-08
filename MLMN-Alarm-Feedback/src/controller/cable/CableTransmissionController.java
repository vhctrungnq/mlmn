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
import vo.CableLineAttributes;
import vo.CableParameter;
import vo.CableTransmission;
import vo.CableType;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.Helper;
import vo.alarm.utils.URLCoderTools;
import vo.alarm.utils.UploadTools;
import controller.BaseController;
import dao.CHighlightConfigsDAO;
import dao.CableLineAttributesDAO;
import dao.CableParameterDAO;
import dao.CableTransmissionDAO;
import dao.CableTypeDAO;
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
@RequestMapping("/alarm/cable/*")
public class CableTransmissionController extends BaseController {
	
	@Autowired
	private CableLineAttributesDAO cableLineAttributesDAO;
	@Autowired
	private CableTransmissionDAO cableTransmissionDAO;
	@Autowired
	private CableParameterDAO cableParameterDAO;
	@Autowired
	private CableTypeDAO cableTypeDAO;
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
    public String list(CableTransmission cableTransmission,
    		@RequestParam(required = false) String typeC,
    		@RequestParam(required = false) String directionIdCheck,
    		@RequestParam(required = false) Integer delData,
					   ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		List<SYS_PARAMETER> sysParemeterTitle = cableTransmissionDAO.titleForm(typeC,null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		model.addAttribute("typeC",typeC);
		System.out.println("directionIdCheck: "+directionIdCheck);
		if (directionIdCheck==null)
		{
			cableTransmission.setDirectionId(null);			
		}
		if (cableTransmission.getId() == null) {
			cableTransmission.setOdf1("Y");
			cableTransmission.setOdf3("Y");
			
			cableTransmission.setId(-1);
		}
		
		if (cableTransmission.getDirectionId() != null) {
			cableTransmission.setDirectionId(cableTransmission.getDirectionId().trim());
		}
		
		if (cableTransmission.getCableId() != null) {
			cableTransmission.setCableId(cableTransmission.getCableId().trim());
		}
		
		if (cableTransmission.getVendor() != null) {
			cableTransmission.setVendor(cableTransmission.getVendor().trim());
		}
		
		if (cableTransmission.getEnSource() != null) {
			cableTransmission.setEnSource(cableTransmission.getEnSource().trim());
		}
		
		if (cableTransmission.getDescription() != null) {
			cableTransmission.setDescription(cableTransmission.getDescription().trim());
		}
		
		if (cableTransmission.getCardPortSource() != null) {
			cableTransmission.setCardPortSource(cableTransmission.getCardPortSource().trim());
		}
		
		if (cableTransmission.getOdfSource() != null) {
			cableTransmission.setOdfSource(cableTransmission.getOdfSource().trim());
		}
		cableTransmission.setTypeCarb(typeC);
		
		int order = 1;
		String column = "DIRECTION_ID";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) { }
		
		List<CableTransmission> cableTransmissionList = cableTransmissionDAO.getCableTransmissionFilter(cableTransmission, column, order, delData);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "DSCap_" + dateNow;
				
		model.addAttribute("exportFileName", exportFileName);
				
		model.addAttribute("cableTransmission", cableTransmission);
		
		model.addAttribute("cableTransmissionList", cableTransmissionList);
		model.addAttribute("directionIdCheck", directionIdCheck);
		model.addAttribute("sizeList", 			cableTransmissionList.size()); 
		
		//Check Role User(Administrator)
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SysUsers> userList = sysUserDao.checkRole(username);
		Helper.checkRole(userList, model);
		
		return "jspalarm/cable/cableTmslist";
	}
	
	@RequestMapping(value="form", method=RequestMethod.GET)
    public String cableForm(@RequestParam(required = false) Integer id,
    		@RequestParam(required = false) String typeC,ModelMap model) {
		List<SYS_PARAMETER> sysParemeterTitle = cableTransmissionDAO.titleForm(typeC,"ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
		}
		CableTransmission cableTransmission;
		
		if (id == null) {
			cableTransmission = new CableTransmission();
		} else {
			cableTransmission = cableTransmissionDAO.getCableById(id);
		}
		
		List<CableType> cableTypeList = cableTypeDAO.getAll();
		
		model.addAttribute("cableTransmission", 	cableTransmission);
		model.addAttribute("cableTypeList", 		cableTypeList);
		model.addAttribute("typeC", 		typeC);
		
		return "jspalarm/cable/cableTmsform";
	}
	
	@RequestMapping(value="form", method=RequestMethod.POST)
    public String cableFormPost(@ModelAttribute("cableTransmission") @Valid CableTransmission cableTransmission, BindingResult result, 
    		@RequestParam(required = true) String typeC,
    		ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		List<SYS_PARAMETER> sysParemeterTitle = cableTransmissionDAO.titleForm(typeC,"ADD");
		String title="";
		if(sysParemeterTitle.size() > 0)
		{
			title=sysParemeterTitle.get(0).getValue();
			
		}
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if (result.hasErrors()) {
			List<CableType> cableTypeList = cableTypeDAO.getAll();
			model.addAttribute("cableTypeList", cableTypeList);
			model.addAttribute("cableTransmission", cableTransmission);
			model.addAttribute("typeC", 		typeC);
			saveMessageKey(request, "cableTransmission.InsertError");
			model.addAttribute("titleF", title);
			return "jspalarm/cable/cableTmsform";
		}
		else
		{
			if (cableTransmission.getId() == null) {
				cableTransmission.setCreatedBy(username);
				cableTransmission.setCreateDate(new Date());
				cableTransmission.setTypeCarb(typeC);
				cableTransmissionDAO.insertSelective(cableTransmission);
				
				saveMessageKey(request, "cableTransmission.InsertSuccess");
				
			} else {
				
				cableTransmission.setModifiedBy(username);
				cableTransmission.setModifyDate(new Date());
				
				cableTransmissionDAO.updateByPrimaryKeySelective(cableTransmission);
				
				saveMessageKey(request, "cableTransmission.UpdateSuccess");
				
			}
		}
		
		return "redirect:list.htm?typeC="+typeC;
	}
	
	@RequestMapping(value="ajax/get-cable-inf-search")
    public @ResponseBody 
    List<String> ajaxGetCableInfoSearch(@RequestParam(required = false) String focus, @RequestParam(required = false) String term,
    		HttpServletRequest request, HttpServletResponse response) {
		
		List<String> paramList = new ArrayList<String>();
		
		if (focus.equals("enSource") && term.length() < 3) {
			return paramList;
		}
		
		List <CableParameter> resultParameter = cableParameterDAO.getCabParameterValue(focus, term);
		for (CableParameter item: resultParameter) {
			paramList.add(item.getValue());
		}
		
		return paramList;
	}
	
	@RequestMapping(value="ajax/get-CableTransmission-inf-search")
    public @ResponseBody 
    List<String> ajaxGetCableTransmissionInfoSearch(@RequestParam(required = false) String focus,@RequestParam(required = false) String typeC, @RequestParam(required = false) String term,
    		HttpServletRequest request, HttpServletResponse response) {
		
		List<String> paramList = new ArrayList<String>();
		
		if (focus.equals("enSource") && term.length() < 3) {
			return paramList;
		}
	
		paramList = cableTransmissionDAO.getCableTransmissionSearch(focus,typeC, term);
		
		return paramList;
	}
	@RequestMapping(value="ajax/getAttByBscAndCabletype")
    public @ResponseBody 
    List<CableLineAttributes> ajaxGetAttByBscAndCabletype(@RequestParam(required = false) String cableTypeId, @RequestParam(required = false) String cableId, 
    		@RequestParam(required = false) String fieldColumnKey, HttpServletRequest request, HttpServletResponse response) {
		
		cableTypeId = URLCoderTools.decode(cableTypeId);
		cableId = URLCoderTools.decode(cableId);
		fieldColumnKey = URLCoderTools.decode(fieldColumnKey);
		
		List<CableLineAttributes> cableLineAttList = cableLineAttributesDAO.getAttByBscAndCabletype(cableId, cableTypeId, fieldColumnKey);
		
		return cableLineAttList;
	}
	
	@RequestMapping(value="ajax/checkExistsCableTms")
    public @ResponseBody 
    CableTransmission ajaxCheckExistsCableTms(String directionId, String cableId, String flowConnection, String enSource, 
    		String enDestination, HttpServletRequest request, HttpServletResponse response) {
		
		directionId 	= URLCoderTools.decode(directionId);
		cableId 		= URLCoderTools.decode(cableId);
		flowConnection 	= URLCoderTools.decode(flowConnection);
		enSource 		= URLCoderTools.decode(enSource);
		enDestination 	= URLCoderTools.decode(enDestination);
		
		CableTransmission cableTransmission = new CableTransmission();
		
		cableTransmission.setDirectionId(directionId);
		cableTransmission.setCableId(cableId);
		cableTransmission.setFlowConnection(flowConnection);
		cableTransmission.setEnSource(enSource);
		cableTransmission.setEnDestination(enDestination);
		
		CableTransmission cable = cableTransmissionDAO.getCableByCable(cableTransmission);
		
		if (cable != null) {
			return cable;
		}
		
		return new CableTransmission();
	}
	
	@RequestMapping(value="ajax/checkDeleteCableTms")
    public @ResponseBody 
    Integer ajaxCheckDeleteCableTms(String cableId, HttpServletRequest request, HttpServletResponse response) {
		
		cableId = URLCoderTools.decode(cableId);
		
		List<CableLineAttributes> attList = cableLineAttributesDAO.getCableAttByCableLineId(cableId);
		
		return attList.size();
	}
	
	@RequestMapping(value="ajax/deleteCableTms")
    public @ResponseBody 
    Integer ajaxDeleteCableLine(String cableId, Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		cableId = URLCoderTools.decode(cableId);
		
		cableLineAttributesDAO.deleteByCableLineId(cableId);
		
		cableTransmissionDAO.deleteByPrimaryKey(id);
		
		return 1;
	}
	
	@RequestMapping(value="ajax/deleteCableLineAtt")
    public @ResponseBody 
    Integer ajaxDeleteCableLineAtt(Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		cableLineAttributesDAO.deleteByPrimaryKey(id);
		
		return 1;
	}
	
	@RequestMapping(value="ajax/saveCableLineAtt")
    public @ResponseBody 
    Integer ajaxCableLineAtt(CableLineAttributes cableLineAtt, HttpServletRequest request, HttpServletResponse response) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		int result = 0;
		
		cableLineAtt.setCableLineId(URLCoderTools.decode(cableLineAtt.getCableLineId()));
		cableLineAtt.setCableTypeId(URLCoderTools.decode(cableLineAtt.getCableTypeId()));
		cableLineAtt.setLabel(URLCoderTools.decode(cableLineAtt.getLabel()));
		cableLineAtt.setValue(URLCoderTools.decode(cableLineAtt.getValue()));
		
		if (cableLineAtt.getId().intValue() == 0) {
			
			
			cableLineAtt.setId(null);
			cableLineAtt.setCreatedBy(username);
			cableLineAtt.setCreateDate(new Date());
			
			result = cableLineAttributesDAO.insertSelectiveAndReturn(cableLineAtt);
			
			System.out.println("Insert: " + cableLineAtt.getValue());
		} else {
			cableLineAtt.setModifiedBy(username);
			cableLineAtt.setModifyDate(new Date());
			
			cableLineAttributesDAO.updateByPrimaryKeySelective(cableLineAtt);
			System.out.println("Update: " + cableLineAtt.getValue());
		}
		
		return result;
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(@RequestParam(required = false) String typeC,Model model) {
		model.addAttribute("typeC", 		typeC);
		List<SYS_PARAMETER> sysParemeterTitle = cableTransmissionDAO.titleForm(typeC,"UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		return "jspalarm/cable/cableupload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("filePath") MultipartFile filePath,@RequestParam(required = false) String typeC, Model model, HttpServletRequest request) throws IOException, ParseException {
		model.addAttribute("typeC", 		typeC);
		List<SYS_PARAMETER> sysParemeterTitle = cableTransmissionDAO.titleForm(typeC,"UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		if (!filePath.isEmpty()) {

			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xls")) {
				/*if (fileExtn.equals("xlsx")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsxFile(filePath.getInputStream());
					importContent(sheetData,model,request);
				}*/
				
				if (fileExtn.equals("xls")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFileForCable(filePath.getInputStream());
					if (typeC.equals("GMSC"))
					{
						importContent(sheetData,typeC,model,request);
					}
					else if (typeC.equals("NodeTDQH"))
					{
						importContentNodeTDQH(sheetData,typeC,model,request);
					}
					else if (typeC.equals("BSC"))
					{
						importContentBSC(sheetData,typeC,model,request);
					}
					else if (typeC.equals("BSC_MGW"))
					{
						importContentBSC_MGW(sheetData,typeC,model,request);
					}
					else if (typeC.equals("IPBB_LTT"))
					{
						importContentIPBB_LTT(sheetData,typeC,model,request);
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
		
		return "jspalarm/cable/cableupload";
		
	}
	
	private String importContentIPBB_LTT(List sheetData, String typeC,
			Model model, HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("typeC", 		typeC);
		List<SYS_PARAMETER> sysParemeterTitle = cableTransmissionDAO.titleForm(typeC,"UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		List<CableTransmission> successList = new ArrayList<CableTransmission>();
		List<CableTransmission> failedList = new ArrayList<CableTransmission>();
		List<CableTransmission> success = new ArrayList<CableTransmission>();
		
		String directionId;
		String cableId;
		String flowConnection;
		String enSource;
		String cardPortSource;
		String odfSource;
		String odf1;
		String odf2;
		String odfVendor1;
		String transmission;
		String odfVendor2;
		String odfOsnLtt3;
		String odfOsnLtt4;
		String odfDestination;
		String cardPortDestination;
		String enDestination;
		String vendor;
		String bandwith;
		String description;
		
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 20) {
        		saveMessageKey(request, "Định dạng tệp không đúng");
        		
        		return "jspalarm/cable/cableupload";
        	}
        	
        	if (sheetData.size() > 1) {
        		CableTransmission cableTransmission;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			cableTransmission = new CableTransmission();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=20; j++) {
     					data.add("");
     				}
        			directionId				= data.get(19).toString().trim().equals("")?null:data.get(19).toString().trim().toUpperCase();
        			cableId					= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			flowConnection			= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			enSource				= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			cardPortSource			= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			odfSource				= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			odf1					= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			odf2					= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			odfVendor1				= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			transmission			= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			odfVendor2				= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			odfOsnLtt3				= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			odfOsnLtt4				= data.get(12).toString().trim().equals("")?null:data.get(12).toString().trim();
        			odfDestination			= data.get(13).toString().trim().equals("")?null:data.get(13).toString().trim();
        			cardPortDestination		= data.get(14).toString().trim().equals("")?null:data.get(14).toString().trim();
        			enDestination			= data.get(15).toString().trim().equals("")?null:data.get(15).toString().trim();
        			vendor					= data.get(16).toString().trim().equals("")?null:data.get(16).toString().trim();
        			bandwith				= data.get(17).toString().trim().equals("")?null:data.get(17).toString().trim();
        			description				= data.get(18).toString().trim().equals("")?null:data.get(18).toString().trim();
        			
        			cableTransmission.setDirectionId(directionId);
        			cableTransmission.setCableId(cableId);
        			cableTransmission.setFlowConnection(flowConnection);
        			cableTransmission.setEnSource(enSource);
        			cableTransmission.setCardPortSource(cardPortSource);
        			cableTransmission.setOdfSource(odfSource);
        			cableTransmission.setOdf1(odf1);
        			cableTransmission.setOdf2(odf2);
        			cableTransmission.setOdfVendor1(odfVendor1);
        			cableTransmission.setTransmission(transmission);
        			cableTransmission.setOdfVendor2(odfVendor2);
        			cableTransmission.setOdfDestination(odfDestination);
        			cableTransmission.setCardPortDestination(cardPortDestination);
        			cableTransmission.setEnDestination(enDestination);
        			cableTransmission.setVendor(vendor);
        			cableTransmission.setDescription(description);
        			cableTransmission.setTypeCarb(typeC);
        			cableTransmission.setOdfOsnLtt3(odfOsnLtt3);
        			cableTransmission.setOdfOsnLtt4(odfOsnLtt4);
        			cableTransmission.setBandwith(bandwith);
        			if (directionId==null || cableId == null || flowConnection == null  || enSource == null 
							|| cableId.length() >= 255
							|| flowConnection.length() >= 255
							|| enSource.length() >= 50
							||	(enDestination != null && enDestination.length() >= 50)
							||	(cardPortSource != null && cardPortSource.length() >= 50)
							|| (odfSource != null && odfSource.length() >= 255)
							|| (odfOsnLtt3 != null && odfOsnLtt3.length() >= 255)
							|| (odfOsnLtt4 != null && odfOsnLtt4.length() >= 255)
							|| (odf1 != null && odf1.length() >= 255)
							|| (odf2 != null && odf2.length() >= 255)
							|| (odfVendor1 != null && odfVendor1.length() >= 255)
							|| (transmission != null && transmission.length() >= 255)
							|| (odfVendor2 != null && odfVendor2.length() >= 255)
							|| (odfDestination != null && odfDestination.length() >= 255)
							|| (cardPortDestination != null && cardPortDestination.length() >= 50)
							|| (vendor != null && vendor.length() >= 50)
							|| (bandwith != null && bandwith.length() >= 250)
							|| (description != null && description.length() >= 900)
						) {
						
						error = true;
					}
        			
        			if (cableId == null && flowConnection == null  && enSource == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(cableTransmission);
    					} else  {
    						successList.add(cableTransmission);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (CableTransmission cab: successList) {
			
			try {
				CableTransmission cable = cableTransmissionDAO.getCableByCable(cab);
    			if (cable != null) {
    				cab.setModifyDate(new Date());
    				cab.setModifiedBy(username);
    				cab.setId(cable.getId());
    				cableTransmissionDAO.updateByPrimaryKeySelective(cab);
    			} 
    			else
    			{
    				cab.setCreateDate(new Date());
    				cab.setCreatedBy(username);
    				cableTransmissionDAO.insertSelective(cab);
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
				
		
		return "jspalarm/cable/cableupload";
		
	}

	private String importContentBSC_MGW(List sheetData, String typeC,
			Model model, HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("typeC", 		typeC);
		List<SYS_PARAMETER> sysParemeterTitle = cableTransmissionDAO.titleForm(typeC,"UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		List<CableTransmission> successList = new ArrayList<CableTransmission>();
		List<CableTransmission> failedList = new ArrayList<CableTransmission>();
		List<CableTransmission> success = new ArrayList<CableTransmission>();
		
		String directionId;
		String cableId;
		String flowConnection;
		String enSource;
		String cardPortSource;
		String odfSource;
		String positionEt;
		String cardPort1;
		String odf1;
		String cardPort2;
		String odf2;
		String odfVendor1;
		String transmission;
		String odfVendor2;
		String odfReplace;
		String odfDestination;
		String cardPortDestination;
		String enDestination;
		String vendor;
		String bandwith;
		String description;
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 22) {
        		saveMessageKey(request, "Định dạng tệp không đúng");
        		
        		return "jspalarm/cable/cableupload";
        	}
        	
        	if (sheetData.size() > 1) {
        		CableTransmission cableTransmission;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			cableTransmission = new CableTransmission();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=22; j++) {
     					data.add("");
     				}
        			directionId				= data.get(21).toString().trim().equals("")?null:data.get(21).toString().trim().toUpperCase();
        			cableId					= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			flowConnection			= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			enSource				= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			cardPortSource			= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			odfSource				= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			positionEt				= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			cardPort1				= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			odf1					= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			cardPort2				= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			odf2					= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			odfVendor1				= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			transmission			= data.get(12).toString().trim().equals("")?null:data.get(12).toString().trim();
        			odfVendor2				= data.get(13).toString().trim().equals("")?null:data.get(13).toString().trim();
        			odfReplace				= data.get(14).toString().trim().equals("")?null:data.get(14).toString().trim();
        			odfDestination			= data.get(15).toString().trim().equals("")?null:data.get(15).toString().trim();
        			cardPortDestination		= data.get(16).toString().trim().equals("")?null:data.get(16).toString().trim();
        			enDestination			= data.get(17).toString().trim().equals("")?null:data.get(17).toString().trim();
        			vendor					= data.get(18).toString().trim().equals("")?null:data.get(18).toString().trim();
        			bandwith				= data.get(19).toString().trim().equals("")?null:data.get(19).toString().trim();
        			description				= data.get(20).toString().trim().equals("")?null:data.get(20).toString().trim();
        			
        			cableTransmission.setDirectionId(directionId);
        			cableTransmission.setCableId(cableId);
        			cableTransmission.setFlowConnection(flowConnection);
        			cableTransmission.setEnSource(enSource);
        			cableTransmission.setCardPortSource(cardPortSource);
        			cableTransmission.setOdfSource(odfSource);
        			cableTransmission.setPositionEt(positionEt);
        			cableTransmission.setCardPort1(cardPort1);
        			cableTransmission.setOdf1(odf1);
        			cableTransmission.setCardPort2(cardPort2);
        			cableTransmission.setOdf2(odf2);
        			cableTransmission.setOdfVendor1(odfVendor1);
        			cableTransmission.setTransmission(transmission);
        			cableTransmission.setOdfVendor2(odfVendor2);
        			cableTransmission.setOdfDestination(odfDestination);
        			cableTransmission.setCardPortDestination(cardPortDestination);
        			cableTransmission.setEnDestination(enDestination);
        			cableTransmission.setVendor(vendor);
        			cableTransmission.setDescription(description);
        			cableTransmission.setTypeCarb(typeC);
        			cableTransmission.setOdfReplace(odfReplace);
        			cableTransmission.setBandwith(bandwith);
        			if (directionId==null || cableId == null || flowConnection == null  || enSource == null 
							|| cableId.length() >= 255
							|| flowConnection.length() >= 255
							|| enSource.length() >= 50
							||	(enDestination != null && enDestination.length() >= 50)
							||	(cardPortSource != null && cardPortSource.length() >= 50)
							|| (odfSource != null && odfSource.length() >= 255)
							|| (positionEt != null && positionEt.length() >= 50)
							|| (cardPort1 != null && cardPort1.length() >= 50)
							|| (odf1 != null && odf1.length() >= 255)
							|| (cardPort2 != null && cardPort2.length() >= 50)
							|| (odf2 != null && odf2.length() >= 255)
							|| (odfVendor1 != null && odfVendor1.length() >= 255)
							|| (transmission != null && transmission.length() >= 255)
							|| (odfVendor2 != null && odfVendor2.length() >= 255)
							|| (odfReplace != null && odfReplace.length() >= 255)
							|| (odfDestination != null && odfDestination.length() >= 255)
							|| (cardPortDestination != null && cardPortDestination.length() >= 50)
							|| (vendor != null && vendor.length() >= 50)
							|| (bandwith != null && bandwith.length() >= 250)
							|| (description != null && description.length() >= 900)
						) {
						
						error = true;
					}
        			
        			if (cableId == null && flowConnection == null  && enSource == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(cableTransmission);
    					} else  {
    						successList.add(cableTransmission);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (CableTransmission cab: successList) {
			
			try {
				CableTransmission cable = cableTransmissionDAO.getCableByCable(cab);
    			if (cable != null) {
    				cab.setModifyDate(new Date());
    				cab.setModifiedBy(username);
    				cab.setId(cable.getId());
    				cableTransmissionDAO.updateByPrimaryKeySelective(cab);
    			} 
    			else
    			{
    				cab.setCreateDate(new Date());
    				cab.setCreatedBy(username);
    				cableTransmissionDAO.insertSelective(cab);
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
				
		
		return "jspalarm/cable/cableupload";
	}

	private String importContentNodeTDQH(List sheetData, String typeC,
			Model model, HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("typeC", 		typeC);
		List<SYS_PARAMETER> sysParemeterTitle = cableTransmissionDAO.titleForm(typeC,"UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		List<CableTransmission> successList = new ArrayList<CableTransmission>();
		List<CableTransmission> failedList = new ArrayList<CableTransmission>();
		List<CableTransmission> success = new ArrayList<CableTransmission>();
		
		String directionId;
		String cableId;
		String flowConnection;
		String enSource;
		String cardPortSource;
		String odfSource;
		String positionEt;
		String cardPort1;
		String odf1;
		String cardPort2;
		String odf2;
		String odfVendor1;
		String transmission;
		String odfVendor2;
		String odfDestination;
		String cardPortDestination;
		String enDestination;
		String vendor;
		String bandwith;
		String description;
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 21) {
        		saveMessageKey(request, "Định dạng tệp không đúng");
        		
        		return "jspalarm/cable/cableupload";
        	}
        	
        	if (sheetData.size() > 1) {
        		/*List footer= (List) sheetData.get(sheetData.size() - 1);
        		
        		while (footer.size() != 20 && sheetData.size() > 2) {
        			System.out.println("footer.size(): " + footer.size());
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
        				for (int j=footer.size(); j<=20; j++) {
        					footer.add("");
         				}
        			}
        			
	        		footer= (List)sheetData.get(sheetData.size()-1);
        		}*/
        		CableTransmission cableTransmission;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			cableTransmission = new CableTransmission();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=21; j++) {
     					data.add("");
     				}
        			directionId				= data.get(20).toString().trim().equals("")?null:data.get(20).toString().trim().toUpperCase();
        			cableId					= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			flowConnection			= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			enSource				= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			cardPortSource			= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			odfSource				= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			positionEt				= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			cardPort1				= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			odf1					= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			cardPort2				= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			odf2					= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			odfVendor1				= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			transmission			= data.get(12).toString().trim().equals("")?null:data.get(12).toString().trim();
        			odfVendor2				= data.get(13).toString().trim().equals("")?null:data.get(13).toString().trim();
        			odfDestination			= data.get(14).toString().trim().equals("")?null:data.get(14).toString().trim();
        			cardPortDestination		= data.get(15).toString().trim().equals("")?null:data.get(15).toString().trim();
        			enDestination			= data.get(16).toString().trim().equals("")?null:data.get(16).toString().trim();
        			vendor					= data.get(17).toString().trim().equals("")?null:data.get(17).toString().trim();
        			bandwith				= data.get(18).toString().trim().equals("")?null:data.get(18).toString().trim();
        			description				= data.get(19).toString().trim().equals("")?null:data.get(19).toString().trim();
        			
        			cableTransmission.setDirectionId(directionId);
        			cableTransmission.setCableId(cableId);
        			cableTransmission.setFlowConnection(flowConnection);
        			cableTransmission.setEnSource(enSource);
        			cableTransmission.setCardPortSource(cardPortSource);
        			cableTransmission.setOdfSource(odfSource);
        			cableTransmission.setPositionEt(positionEt);
        			cableTransmission.setCardPort1(cardPort1);
        			cableTransmission.setOdf1(odf1);
        			cableTransmission.setCardPort2(cardPort2);
        			cableTransmission.setOdf2(odf2);
        			cableTransmission.setOdfVendor1(odfVendor1);
        			cableTransmission.setTransmission(transmission);
        			cableTransmission.setOdfVendor2(odfVendor2);
        			cableTransmission.setOdfDestination(odfDestination);
        			cableTransmission.setCardPortDestination(cardPortDestination);
        			cableTransmission.setEnDestination(enDestination);
        			cableTransmission.setVendor(vendor);
        			cableTransmission.setDescription(description);
        			cableTransmission.setTypeCarb(typeC);
        			cableTransmission.setBandwith(bandwith);
        			
        			if (directionId==null || cableId == null || flowConnection == null  || enSource == null 
							|| cableId.length() >= 255
							|| flowConnection.length() >= 255
							|| enSource.length() >= 50
							||	(enDestination != null && enDestination.length() >= 50)
							||	(cardPortSource != null && cardPortSource.length() >= 50)
							|| (odfSource != null && odfSource.length() >= 255)
							|| (positionEt != null && positionEt.length() >= 50)
							|| (cardPort1 != null && cardPort1.length() >= 50)
							|| (odf1 != null && odf1.length() >= 255)
							|| (cardPort2 != null && cardPort2.length() >= 50)
							|| (odf2 != null && odf2.length() >= 255)
							|| (odfVendor1 != null && odfVendor1.length() >= 255)
							|| (transmission != null && transmission.length() >= 255)
							|| (odfVendor2 != null && odfVendor2.length() >= 255)
							|| (odfDestination != null && odfDestination.length() >= 255)
							|| (cardPortDestination != null && cardPortDestination.length() >= 50)
							|| (vendor != null && vendor.length() >= 50)
							|| (bandwith != null && bandwith.length() >= 250)
							|| (description != null && description.length() >= 900)
						) {
						
						error = true;
					}
        			
        			if (cableId == null && flowConnection == null  && enSource == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(cableTransmission);
    					} else  {
    						successList.add(cableTransmission);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (CableTransmission cab: successList) {
			
			try {
				CableTransmission cable = cableTransmissionDAO.getCableByCable(cab);
    			if (cable != null) {
    				cab.setModifyDate(new Date());
    				cab.setModifiedBy(username);
    				cab.setId(cable.getId());
    				cableTransmissionDAO.updateByPrimaryKeySelective(cab);
    			} 
    			else
    			{
    				cab.setCreateDate(new Date());
    				cab.setCreatedBy(username);
    				cableTransmissionDAO.insertSelective(cab);
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
				
		
		return "jspalarm/cable/cableupload";
	}

	private String importContent(List sheetData, String typeC,
			Model model, HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("typeC", 		typeC);
		List<SYS_PARAMETER> sysParemeterTitle = cableTransmissionDAO.titleForm(typeC,"UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		List<CableTransmission> successList = new ArrayList<CableTransmission>();
		List<CableTransmission> failedList = new ArrayList<CableTransmission>();
		List<CableTransmission> success = new ArrayList<CableTransmission>();
		
		String directionId;
		String cableId;
		String flowConnection;
		String enSource;
		String cardPortSource;
		String odfSource;
		String odfVendor1;
		String transmission;
		String odfVendor2;
		String odfDestination;
		String cardPortDestination;
		String enDestination;
		String vendor;
		String bandwith;
		String description;
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 16) {
        		saveMessageKey(request, "Định dạng tệp không đúng");
        		
        		return "jspalarm/cable/cableupload";
        	}
        	
        	if (sheetData.size() > 1) {
        		CableTransmission cableTransmission;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			cableTransmission = new CableTransmission();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=16; j++) {
     					data.add("");
     				}
        			directionId				= data.get(15).toString().trim().equals("")?null:data.get(15).toString().trim().toUpperCase();
        			cableId					= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			flowConnection			= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			enSource				= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			cardPortSource			= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			odfSource				= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			odfVendor1				= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			transmission			= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			odfVendor2				= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			odfDestination			= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			cardPortDestination		= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			enDestination			= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			vendor					= data.get(12).toString().trim().equals("")?null:data.get(12).toString().trim();
        			bandwith				= data.get(13).toString().trim().equals("")?null:data.get(13).toString().trim();
        			description				= data.get(14).toString().trim().equals("")?null:data.get(14).toString().trim();
        			
        			cableTransmission.setDirectionId(directionId);
        			cableTransmission.setCableId(cableId);
        			cableTransmission.setFlowConnection(flowConnection);
        			cableTransmission.setEnSource(enSource);
        			cableTransmission.setCardPortSource(cardPortSource);
        			cableTransmission.setOdfSource(odfSource);
        			cableTransmission.setOdfVendor1(odfVendor1);
        			cableTransmission.setTransmission(transmission);
        			cableTransmission.setOdfVendor2(odfVendor2);
        			cableTransmission.setOdfDestination(odfDestination);
        			cableTransmission.setCardPortDestination(cardPortDestination);
        			cableTransmission.setEnDestination(enDestination);
        			cableTransmission.setVendor(vendor);
        			cableTransmission.setDescription(description);
        			cableTransmission.setTypeCarb(typeC);
        			cableTransmission.setBandwith(bandwith);
        			
        			if (directionId==null || cableId == null || flowConnection == null  || enSource == null 
							|| cableId.length() >= 255
							|| flowConnection.length() >= 255
							|| enSource.length() >= 50
							||	(enDestination != null && enDestination.length() >= 50)
							||	(cardPortSource != null && cardPortSource.length() >= 50)
							|| (odfSource != null && odfSource.length() >= 255)
							|| (odfVendor1 != null && odfVendor1.length() >= 255)
							|| (transmission != null && transmission.length() >= 255)
							|| (odfVendor2 != null && odfVendor2.length() >= 255)
							|| (odfDestination != null && odfDestination.length() >= 255)
							|| (cardPortDestination != null && cardPortDestination.length() >= 50)
							|| (vendor != null && vendor.length() >= 50)
							|| (bandwith != null && bandwith.length() >= 250)
							|| (description != null && description.length() >= 900)
						) {
						
						error = true;
					}
        			
        			if (cableId == null && flowConnection == null  && enSource == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(cableTransmission);
    					} else  {
    						successList.add(cableTransmission);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (CableTransmission cab: successList) {
			
			try {
				CableTransmission cable = cableTransmissionDAO.getCableByCable(cab);
    			if (cable != null) {
    				cab.setModifyDate(new Date());
    				cab.setModifiedBy(username);
    				cab.setId(cable.getId());
    				cableTransmissionDAO.updateByPrimaryKeySelective(cab);
    			} 
    			else
    			{
    				cab.setCreateDate(new Date());
    				cab.setCreatedBy(username);
    				cableTransmissionDAO.insertSelective(cab);
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
				
		
		return "jspalarm/cable/cableupload";
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContentBSC(List sheetData,@RequestParam(required = false) String typeC, Model model, HttpServletRequest request) throws IOException, ParseException {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("typeC", 		typeC);
		List<SYS_PARAMETER> sysParemeterTitle = cableTransmissionDAO.titleForm(typeC,"UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		List<CableTransmission> successList = new ArrayList<CableTransmission>();
		List<CableTransmission> failedList = new ArrayList<CableTransmission>();
		List<CableTransmission> success = new ArrayList<CableTransmission>();
		
		String directionId;
		String cableId;
		String flowConnection;
		String enSource;
		String cardPortSource;
		String odfSource;
		String positionEt;
		String cardPort1;
		String odf1;
		String cardPort2;
		String odf2;
		String odfVendor1;
		String transmission;
		String odfVendor2;
		String odfDestination;
		String cardPortDestination;
		String enDestination;
		String vendor;
		String bandwith;
		String description;
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 21) {
        		saveMessageKey(request, "Định dạng tệp không đúng");
        		
        		return "jspalarm/cable/cableupload";
        	}
        	
        	if (sheetData.size() > 1) {
        		/*List footer= (List) sheetData.get(sheetData.size() - 1);
        		
        		while (footer.size() != 20 && sheetData.size() > 2) {
        			System.out.println("footer.size(): " + footer.size());
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
        				for (int j=footer.size(); j<=20; j++) {
        					footer.add("");
         				}
        			}
        			
	        		footer= (List)sheetData.get(sheetData.size()-1);
        		}*/
        		CableTransmission cableTransmission;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			cableTransmission = new CableTransmission();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=21; j++) {
     					data.add("");
     				}
        			directionId				= data.get(20).toString().trim().equals("")?null:data.get(20).toString().trim().toUpperCase();
        			cableId					= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			flowConnection			= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			enSource				= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			cardPortSource			= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			odfSource				= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			positionEt				= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			cardPort1				= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			odf1					= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			cardPort2				= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			odf2					= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			odfVendor1				= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			transmission			= data.get(12).toString().trim().equals("")?null:data.get(12).toString().trim();
        			odfVendor2				= data.get(13).toString().trim().equals("")?null:data.get(13).toString().trim();
        			odfDestination			= data.get(14).toString().trim().equals("")?null:data.get(14).toString().trim();
        			cardPortDestination		= data.get(15).toString().trim().equals("")?null:data.get(15).toString().trim();
        			enDestination			= data.get(16).toString().trim().equals("")?null:data.get(16).toString().trim();
        			vendor					= data.get(17).toString().trim().equals("")?null:data.get(17).toString().trim();
        			bandwith				= data.get(18).toString().trim().equals("")?null:data.get(18).toString().trim();
        			description				= data.get(19).toString().trim().equals("")?null:data.get(19).toString().trim();
        			
        			cableTransmission.setDirectionId(directionId);
        			cableTransmission.setCableId(cableId);
        			cableTransmission.setFlowConnection(flowConnection);
        			cableTransmission.setEnSource(enSource);
        			cableTransmission.setCardPortSource(cardPortSource);
        			cableTransmission.setOdfSource(odfSource);
        			cableTransmission.setPositionEt(positionEt);
        			cableTransmission.setCardPort1(cardPort1);
        			cableTransmission.setOdf1(odf1);
        			cableTransmission.setCardPort2(cardPort2);
        			cableTransmission.setOdf2(odf2);
        			cableTransmission.setOdfVendor1(odfVendor1);
        			cableTransmission.setTransmission(transmission);
        			cableTransmission.setOdfVendor2(odfVendor2);
        			cableTransmission.setOdfDestination(odfDestination);
        			cableTransmission.setCardPortDestination(cardPortDestination);
        			cableTransmission.setEnDestination(enDestination);
        			cableTransmission.setVendor(vendor);
        			cableTransmission.setDescription(description);
        			cableTransmission.setTypeCarb(typeC);
        			cableTransmission.setBandwith(bandwith);
        			
        			if (directionId==null || cableId == null || flowConnection == null  || enSource == null 
							|| cableId.length() >= 255
							|| flowConnection.length() >= 255
							|| enSource.length() >= 50
							||	(enDestination != null && enDestination.length() >= 50)
							||	(cardPortSource != null && cardPortSource.length() >= 50)
							|| (odfSource != null && odfSource.length() >= 255)
							|| (positionEt != null && positionEt.length() >= 50)
							|| (cardPort1 != null && cardPort1.length() >= 50)
							|| (odf1 != null && odf1.length() >= 255)
							|| (cardPort2 != null && cardPort2.length() >= 50)
							|| (odf2 != null && odf2.length() >= 255)
							|| (odfVendor1 != null && odfVendor1.length() >= 255)
							|| (transmission != null && transmission.length() >= 255)
							|| (odfVendor2 != null && odfVendor2.length() >= 255)
							|| (odfDestination != null && odfDestination.length() >= 255)
							|| (cardPortDestination != null && cardPortDestination.length() >= 50)
							|| (vendor != null && vendor.length() >= 50)
							|| (bandwith != null && bandwith.length() >= 250)
							|| (description != null && description.length() >= 900)
						) {
						
						error = true;
					}
        			
        			if (cableId == null && flowConnection == null  && enSource == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(cableTransmission);
    					} else  {
    						successList.add(cableTransmission);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (CableTransmission cab: successList) {
			
			try {
				CableTransmission cable = cableTransmissionDAO.getCableByCable(cab);
    			if (cable != null) {
    				cab.setModifyDate(new Date());
    				cab.setModifiedBy(username);
    				cab.setId(cable.getId());
    				cableTransmissionDAO.updateByPrimaryKeySelective(cab);
    			} 
    			else
    			{
    				cab.setCreateDate(new Date());
    				cab.setCreatedBy(username);
    				cableTransmissionDAO.insertSelective(cab);
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
				
		
		return "jspalarm/cable/cableupload";
	}
	
}
