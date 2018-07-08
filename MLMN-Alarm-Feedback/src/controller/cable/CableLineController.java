package controller.cable;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import vo.CableLine;
import vo.CableLineAttributes;
import vo.CableType;
import vo.SysUsers;
import vo.alarm.utils.Helper;
import vo.alarm.utils.URLCoderTools;
import vo.alarm.utils.UploadTools;

import dao.CableAttributesMasterDAO;
import dao.CableLineAttributesDAO;
import dao.CableLineDAO;
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
@RequestMapping("/alarm/cable/*")
public class CableLineController extends BaseController {
	
	@Autowired
	private CableLineDAO cableLineDAO;
	@Autowired
	private CableTypeDAO cableTypeDAO;
	@Autowired
	private CableLineAttributesDAO cableLineAttributesDAO;
	@Autowired
	private CableAttributesMasterDAO cableAttributesMasterDAO;
	@Autowired 
	private SysUsersDAO  sysUserDao;
	
	@RequestMapping(value="list")
    public String list(CableLine cableLine, @RequestParam(required = false) String thuocTinh, 
					   ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		if (cableLine.getsBsc() != null) {
			cableLine.setsBsc(cableLine.getsBsc().trim());
		}
		
		if (cableLine.getTransmission1() != null) {
			cableLine.setTransmission1(cableLine.getTransmission1().trim());
		}
		
		if (cableLine.getLocation1() != null) {
			cableLine.setLocation1(cableLine.getLocation1().trim());
		}
		
		int order = 2;
		String column = "CABLE_LINE_ID";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) { }
		
		List<CableLine> cableList = cableLineDAO.getCableLineFilter(cableLine, thuocTinh, order, column);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "DSCap_" + dateNow;
				
		model.addAttribute("exportFileName", exportFileName);
		
		model.addAttribute("cableLine", cableLine);
		model.addAttribute("cableList", cableList);
		
		model.addAttribute("thuocTinh", thuocTinh);
		//Check Role User(Administrator)
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SysUsers> userList = sysUserDao.checkRole(username);
		Helper.checkRole(userList, model);		
		return "jspalarm/cable/cablelist";
	}
	
	@RequestMapping(value="form", method=RequestMethod.GET)
    public String cableForm(@RequestParam(required = false) Integer id, ModelMap model) {
		
		CableLine cableLine;
		
		if (id == null) {
			cableLine = new CableLine();
		} else {
			cableLine = cableLineDAO.getCableById(id);
		}
		
		List<CableType> cableTypeList = cableTypeDAO.getAll();
		
		model.addAttribute("cableLine", cableLine);
		model.addAttribute("cableTypeList", cableTypeList);
		
		return "jspalarm/cable/cableform";
	}
	
	@RequestMapping(value="form", method=RequestMethod.POST)
    public String cableFormPost(@ModelAttribute("cableLine") @Valid CableLine cableLine, BindingResult result, 
    		ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if (cableLine.getId() == null) {
			if (result.hasErrors()) {
				List<CableType> cableTypeList = cableTypeDAO.getAll();
				
				model.addAttribute("cableTypeList", cableTypeList);
				model.addAttribute("cableLine", cableLine);
				
				saveMessageKey(request, "cableLine.InsertError");
				
				return "jspalarm/cable/cableform";
			}
			
			cableLine.setCreatedBy(username);
			cableLine.setCreateDate(new Date());
			
			cableLineDAO.insertSelective(cableLine);
			
			saveMessageKey(request, "cableLine.InsertSuccess");
			System.out.println("Insert Thanh cong");
		} else {
			System.out.println("Sua");
			if (result.hasErrors()) {
				List<CableType> cableTypeList = cableTypeDAO.getAll();
				
				model.addAttribute("cableTypeList", cableTypeList);
				model.addAttribute("cableLine", cableLine);
				
				saveMessageKey(request, "cableLine.UpdateError");
				
				return "jspalarm/cable/cableform";
			}
			
			cableLine.setModifiedBy(username);
			cableLine.setModifyDate(new Date());
			
			cableLineDAO.updateByPrimaryKeySelective(cableLine);
			
			saveMessageKey(request, "cableLine.UpdateSuccess");
			
			System.out.println("Update Thanh cong");
		}
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(Model model) {
		return "jspalarm/cable/cableupload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("filePath") MultipartFile filePath, Model model, HttpServletRequest request) throws IOException, ParseException {
		
		if (!filePath.isEmpty()) {

			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xlsx")||fileExtn.equals("xls")) {
				if (fileExtn.equals("xlsx")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsxFile(filePath.getInputStream());
					importContent(sheetData,model,request);
				}
				
				if (fileExtn.equals("xls")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFile(filePath.getInputStream());
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
		
		return "jspalarm/cable/cableupload";
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent (List sheetData, Model model, HttpServletRequest request) throws IOException, ParseException {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<CableLine> successList = new ArrayList<CableLine>();
		List<CableLine> failedList = new ArrayList<CableLine>();

		String cableLineId;
		String sBsc;
		String eBsc;
		String transmission1;
		String transmission2;
		String transmission3;
		String transmission4;
		String transmission5;
		String transmission6;
		
		String location1;
		String location2;
		String location3;
		String location4;
		String location5;
		String location6;
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 15) {
        		saveMessageKey(request, "Định dạng tệp không đúng");
        		
        		return "jspalarm/cable/cableupload";
        	}
        	
        	if (sheetData.size() > 1) {
        		List footer= (List) sheetData.get(sheetData.size() - 1);
        		
        		while (footer.size() != 15 && sheetData.size() > 2) {
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
        				for (int j=footer.size(); j<=14; j++) {
        					footer.add("");
         				}
        			}
        			
	        		footer= (List)sheetData.get(sheetData.size()-1);
        		}
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			CableLine cableLine = new CableLine();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=14; j++) {
     					data.add("");
     				}
        			
        			cableLineId 	= data.get(0).toString().equals("")?null:data.get(0).toString();
        			sBsc			= data.get(1).toString().equals("")?null:data.get(1).toString();
        			
        			transmission1	= data.get(2).toString().equals("")?null:data.get(2).toString();
        			transmission2	= data.get(4).toString().equals("")?null:data.get(4).toString();
        			transmission3	= data.get(6).toString().equals("")?null:data.get(6).toString();
        			transmission4	= data.get(8).toString().equals("")?null:data.get(8).toString();
        			transmission5	= data.get(10).toString().equals("")?null:data.get(10).toString();
        			transmission6	= data.get(12).toString().equals("")?null:data.get(12).toString();
        			
        			location1		= data.get(3).toString().equals("")?null:data.get(3).toString();
        			location2		= data.get(5).toString().equals("")?null:data.get(5).toString();
        			location3		= data.get(7).toString().equals("")?null:data.get(7).toString();	
        			location4		= data.get(9).toString().equals("")?null:data.get(9).toString();
        			location5		= data.get(11).toString().equals("")?null:data.get(11).toString();
        			location6		= data.get(13).toString().equals("")?null:data.get(13).toString();
        			
        			eBsc			= data.get(14).toString().equals("")?null:data.get(14).toString();
        			
					if (cableLineId == null || sBsc == null || eBsc == null 
							|| cableLineId.length() >= 255 
							|| sBsc.length() >= 255
							|| (transmission1 != null && transmission1.length() >= 255)
							|| (transmission2 != null && transmission2.length() >= 255)
							|| (transmission3 != null && transmission3.length() >= 255)
							|| (transmission4 != null && transmission4.length() >= 255)
							|| (transmission5 != null && transmission5.length() >= 255)
							|| (transmission6 != null && transmission6.length() >= 255)
							|| (location1 != null && location1.length() >= 255)
							|| (location2 != null && location2.length() >= 255)
							|| (location3 != null && location3.length() >= 255)
							|| (location4 != null && location4.length() >= 255)
							|| (location5 != null && location5.length() >= 255)
							|| (location6 != null && location6.length() >= 255)
							|| eBsc.length() >= 255
						) {
						
						error = true;
					}
					
					CableLine cable = cableLineDAO.getCableLineByCablelineId(cableLineId);
					
					if (cable != null) {
						error = true;
						
						model.addAttribute("statusExists", "Đã tồn tại cáp truyền dẫn trong cơ sở dữ liệu");
					} else {
						for (CableLine item: successList) {
							if (item.getCableLineId().equals(cableLineId)) {
								error = true;
								
								model.addAttribute("statusExists", "Dữ liệu cáp truyền dẫn bị trùng");
								break;
							}
						}
					}
					
					cableLine.setCableLineId(cableLineId);
					
					cableLine.setsBsc(sBsc);
					
					cableLine.setTransmission1(transmission1);
					cableLine.setTransmission2(transmission2);
					cableLine.setTransmission3(transmission3);
					cableLine.setTransmission4(transmission4);
					cableLine.setTransmission5(transmission5);
					cableLine.setTransmission6(transmission6);
					
					cableLine.setLocation1(location1);
					cableLine.setLocation2(location2);
					cableLine.setLocation3(location3);
					cableLine.setLocation4(location4);
					cableLine.setLocation5(location5);
					cableLine.setLocation6(location6);
					
					cableLine.seteBsc(eBsc);
					
					if (error) {
						failedList.add(cableLine);
					} else  {
						successList.add(cableLine);
					}
				}
        		
        	}
		}
		
		if (failedList.size() == 0 && successList.size() > 0) {
			// import
			for (CableLine item: successList) {
				item.setCreateDate(new Date());
				item.setCreatedBy(username);
				
				cableLineDAO.insertSelective(item);
			}
			
			model.addAttribute("status", "Upload thành công.");			// Upload thành công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");			// Không có dữ liệu
		} else {
			model.addAttribute("status", "Upload lỗi.");					// Upload lỗi
		}
		
		model.addAttribute("successList", successList);
		model.addAttribute("failedList", failedList);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "DSCap_" + dateNow;
				
		model.addAttribute("exportFileName", exportFileName);
				
		
		return "jspalarm/cable/cableupload";
	}
	
	
	@RequestMapping(value="ajax/getCableLineByCablelineId")
    public @ResponseBody 
    CableLine ajaxGetCableLineByCablelineId(@RequestParam(required = false) String cableLineId, 
    		HttpServletRequest request, HttpServletResponse response) {
		
		cableLineId = URLCoderTools.decode(cableLineId);
		
		CableLine cableLine = cableLineDAO.getCableLineByCablelineId(cableLineId);
		
		if (cableLine != null)
			return cableLine;
		return new CableLine();
	}
	
	@RequestMapping(value="ajax/getAttByBscAndCabletype")
    public @ResponseBody 
    List<CableLineAttributes> ajaxGetAttByBscAndCabletype(@RequestParam(required = false) String cableTypeId, @RequestParam(required = false) String cableLineId, 
    		@RequestParam(required = false) String fieldColumnKey, HttpServletRequest request, HttpServletResponse response) {
		
		cableTypeId = URLCoderTools.decode(cableTypeId);
		cableLineId = URLCoderTools.decode(cableLineId);
		fieldColumnKey = URLCoderTools.decode(fieldColumnKey);
		
		List<CableLineAttributes> cableLineAttList = cableLineAttributesDAO.getAttByBscAndCabletype(cableLineId, cableTypeId, fieldColumnKey);
		//CableLine cableLine = cableLineDAO.getCableLineByCablelineId(cableLineId);
		
		return cableLineAttList;
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
	
	@RequestMapping(value="ajax/checkDeleteCableLine")
    public @ResponseBody 
    Integer ajaxCheckDeleteCableLine(String cableLineId, HttpServletRequest request, HttpServletResponse response) {
		
		cableLineId = URLCoderTools.decode(cableLineId);
		
		List<CableLineAttributes> attList = cableLineAttributesDAO.getCableAttByCableLineId(cableLineId);
		
		return attList.size();
	}
	
	@RequestMapping(value="ajax/deleteCableLine")
    public @ResponseBody 
    Integer ajaxDeleteCableLine(String cableLineId, HttpServletRequest request, HttpServletResponse response) {
		
		cableLineId = URLCoderTools.decode(cableLineId);
		
		cableLineAttributesDAO.deleteByCableLineId(cableLineId);
		
		cableLineDAO.deleteByPrimaryKey(cableLineId);
		
		return 1;
	}
	
	@RequestMapping(value="ajax/deleteCableLineAtt")
    public @ResponseBody 
    Integer ajaxDeleteCableLineAtt(Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		cableLineAttributesDAO.deleteByPrimaryKey(id);
		
		return 1;
	}
}
