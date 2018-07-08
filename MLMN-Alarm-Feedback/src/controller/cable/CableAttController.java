package controller.cable;

import java.text.SimpleDateFormat;
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
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vo.CableAttributesMaster;
import vo.CableLineAttributes;
import vo.CableTransmission;
import vo.CableType;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.Helper;
import vo.alarm.utils.URLCoderTools;
import controller.BaseController;
import dao.CableAttributesMasterDAO;
import dao.CableLineAttributesDAO;
import dao.CableTransmissionDAO;
import dao.CableTypeDAO;
import dao.SYS_PARAMETERDAO;
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
@RequestMapping("/alarm/*")
public class CableAttController extends BaseController {

	@Autowired
	private CableTypeDAO cableTypeDAO;
	//@Autowired
	//private CableLineDAO cableLineDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private CableTransmissionDAO cableTransmissionDAO;
	@Autowired
	private CableLineAttributesDAO cableLineAttributesDAO;
	@Autowired
	private CableAttributesMasterDAO cableAttributesMasterDAO;
	@Autowired 
	private SysUsersDAO  sysUserDao;
	/*
	 * Cable Attibute
	*/
	@RequestMapping(value="cable-att/list")
    public String list(CableLineAttributes cableLineAttributes, 
					   ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		if (cableLineAttributes.getCableLineId() != null) {
			cableLineAttributes.setCableLineId(cableLineAttributes.getCableLineId().trim());
		}
		
		if (cableLineAttributes.getCableTypeId() != null) {
			cableLineAttributes.setCableTypeId(cableLineAttributes.getCableTypeId().trim());
		}
		
		if (cableLineAttributes.getLabel() != null) {
			cableLineAttributes.setLabel(cableLineAttributes.getLabel().trim());
		}
		
		if (cableLineAttributes.getValue() != null) {
			cableLineAttributes.setValue(cableLineAttributes.getValue().trim());
		}
		
		int order = 2;
		String column = "CABLE_TYPE_ID";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) { }
		
		List<CableLineAttributes> cableLineAttributesList = cableLineAttributesDAO.getCableAttFilter(cableLineAttributes, order, column);
		
		List<SYS_PARAMETER> fieldKeyList = sysParameterDao.getSysParameterByCode("CABLE_ATT_FIELD_KEY");
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "DSThuocTinhCap_" + dateNow;
				
		model.addAttribute("exportFileName", exportFileName);
		
		model.addAttribute("fieldKeyList", fieldKeyList);
		model.addAttribute("cableLineAttributesList", cableLineAttributesList);
		//Check Role User(Administrator)
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SysUsers> userList = sysUserDao.checkRole(username);
		Helper.checkRole(userList, model);
		return "jspalarm/cable/attlist";
	}
	
	@RequestMapping(value="cable-att/form", method=RequestMethod.GET)
    public String cableForm(@RequestParam(required = false) Integer id, ModelMap model) {
		
		CableLineAttributes cableLineAttributes;
		
		if (id == null) {
			cableLineAttributes = new CableLineAttributes();
		} else {
			cableLineAttributes = cableLineAttributesDAO.selectByPrimaryKey(id);
		}
		
		List<CableTransmission> cableList = cableTransmissionDAO.getCableTransmissionAll();
		List<CableType> cableTypeList = cableTypeDAO.getAll();
		List<SYS_PARAMETER> fieldKeyList = sysParameterDao.getSysParameterByCode("CABLE_ATT_FIELD_KEY");
		
		model.addAttribute("cableList", cableList);
		model.addAttribute("cableTypeList", cableTypeList);
		model.addAttribute("fieldKeyList", fieldKeyList);
		
		model.addAttribute("cableLineAttributes", cableLineAttributes);
		
		return "jspalarm/cable/attform";
	}
	
	@RequestMapping(value="cable-att/form", method=RequestMethod.POST)
    public String cableFormPost(@ModelAttribute("cableLineAttributes") @Valid CableLineAttributes cableLineAttributes, 
    		BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if (cableLineAttributes.getId() == null) {
			if (result.hasErrors()) {
				List<CableTransmission> cableList = cableTransmissionDAO.getCableTransmissionAll();
				List<CableType> cableTypeList = cableTypeDAO.getAll();
				List<SYS_PARAMETER> fieldKeyList = sysParameterDao.getSysParameterByCode("CABLE_ATT_FIELD_KEY");
				
				model.addAttribute("cableList", cableList);
				model.addAttribute("cableTypeList", cableTypeList);
				model.addAttribute("fieldKeyList", fieldKeyList);
				
				model.addAttribute("cableLineAttributes", cableLineAttributes);
				
				saveMessageKey(request, "cableLineAttributes.InsertError");
				
				return "jspalarm/cable/attform";
			}
			
			cableLineAttributes.setCreatedBy(username);
			cableLineAttributes.setCreateDate(new Date());
			
			cableLineAttributesDAO.insertSelective(cableLineAttributes);
			
			saveMessageKey(request, "cableLineAttributes.InsertSuccess");
		} else {
			if (result.hasErrors()) {
				List<CableTransmission> cableList = cableTransmissionDAO.getCableTransmissionAll();
				List<CableType> cableTypeList = cableTypeDAO.getAll();
				List<SYS_PARAMETER> fieldKeyList = sysParameterDao.getSysParameterByCode("CABLE_ATT_FIELD_KEY");
				
				model.addAttribute("cableList", cableList);
				model.addAttribute("cableTypeList", cableTypeList);
				model.addAttribute("fieldKeyList", fieldKeyList);
				
				model.addAttribute("cableLineAttributes", cableLineAttributes);
				
				saveMessageKey(request, "cableLineAttributes.UpdateError");
				
				return "jspalarm/cable/attform";
			}
			
			cableLineAttributes.setModifiedBy(username);
			cableLineAttributes.setModifyDate(new Date());
			
			cableLineAttributesDAO.updateByPrimaryKeySelective(cableLineAttributes);
			
			saveMessageKey(request, "cableLineAttributes.UpdateSuccess");
		}
		
		return "redirect:list.htm";
	}
	@ModelAttribute("cableTypeIdList")
	public List<CableType> cableTypeIdList() {
		return cableTypeDAO.getAll();
	}
	@RequestMapping(value="cable-att-master/list")
    public String attMaster(CableAttributesMaster cableAttributesMaster,@RequestParam(required = false) String cableTypeId, ModelMap model, 
    		HttpServletRequest request, HttpServletResponse response) {
		
		if (cableAttributesMaster.getLabel() != null) {
			cableAttributesMaster.setLabel(cableAttributesMaster.getLabel().trim());
		}
		
		if (cableTypeId != null) {
			cableAttributesMaster.setCableTypeId(cableTypeId);
		}
		
		if (cableAttributesMaster.getDescription() != null) {
			cableAttributesMaster.setDescription(cableAttributesMaster.getDescription());
		}
		
		model.addAttribute("cableTypeId", cableTypeId);
		int order = 2;
		String column = "LABEL";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) { }
		
		List<CableAttributesMaster> cableAttributesMasterList = 
				cableAttributesMasterDAO.getCableAttributesMasterFilter(cableAttributesMaster, order, column);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "DinhNghiaThuocTinhCap_" + dateNow;
				
		model.addAttribute("exportFileName", exportFileName);
		model.addAttribute("cableAttributesMaster", cableAttributesMaster);
		model.addAttribute("cableAttributesMasterList", cableAttributesMasterList);
		//Check Role User(Administrator)
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SysUsers> userList = sysUserDao.checkRole(username);
		Helper.checkRole(userList, model);
		
		return "jspalarm/cable/masterlist";
	}
	
	@RequestMapping(value="cable-att-master/form", method=RequestMethod.GET)
    public String masterForm(@RequestParam(required = false) Integer id, ModelMap model) {
		
		CableAttributesMaster cableAttributesMaster;
		
		if (id == null) {
			cableAttributesMaster = new CableAttributesMaster();
		} else {
			cableAttributesMaster = cableAttributesMasterDAO.selectByPrimaryKey(id);
		}
		
		List<CableType> cableTypeList = cableTypeDAO.getAll();
		
		model.addAttribute("cableTypeList", cableTypeList);
		model.addAttribute("cableAttributesMaster", cableAttributesMaster);
		
		return "jspalarm/cable/masterform";
	}
	
	@RequestMapping(value="cable-att-master/form", method=RequestMethod.POST)
    public String masterFormPost(@ModelAttribute("cableAttributesMaster") @Valid CableAttributesMaster cableAttributesMaster, 
    		BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		if (cableAttributesMaster.getId() == null) {
			if (result.hasErrors()) {
				List<CableType> cableTypeList = cableTypeDAO.getAll();
				
				model.addAttribute("cableTypeList", cableTypeList);
				model.addAttribute("cableAttributesMaster", cableAttributesMaster);
				
				saveMessageKey(request, "cableType.InsertError");
				
				return "jspalarm/cable/masterform";
			}
			
			cableAttributesMasterDAO.insertSelective(cableAttributesMaster);
			
			saveMessageKey(request, "cableAttributesMaster.InsertSuccess");
			System.out.println("Insert Thanh cong");
		} else {
			if (result.hasErrors()) {
				List<CableType> cableTypeList = cableTypeDAO.getAll();
				
				model.addAttribute("cableTypeList", cableTypeList);
				model.addAttribute("cableAttributesMaster", cableAttributesMaster);
				
				saveMessageKey(request, "cableType.UpdateError");
				
				return "jspalarm/cable/masterform";
			}
			
			cableAttributesMasterDAO.updateByPrimaryKeySelective(cableAttributesMaster);
			
			saveMessageKey(request, "cableAttributesMaster.UpdateSuccess");
			
			System.out.println("Update Thanh cong");
		}
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value="cable-att-master/ajax/getAttMasByLabelCabTypeId")
    public @ResponseBody 
    CableAttributesMaster ajaxGetAttMasByLabelCabTypeId(@RequestParam(required = false) String label, @RequestParam(required = false) String cableTypeId, 
    		HttpServletRequest request, HttpServletResponse response) {
		
		label = URLCoderTools.decode(label);
		cableTypeId = URLCoderTools.decode(cableTypeId);
		
		CableAttributesMaster cableAttributesMaster = cableAttributesMasterDAO.getByLabelAndCableTypeId(label, cableTypeId);
		
		if (cableAttributesMaster != null)
			return cableAttributesMaster;
		return new CableAttributesMaster();
	}
	
	/*
	 * Cable Type
	*/
	@RequestMapping(value="cable-type/list")
    public String typelist(CableType cableType, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		if (cableType.getCableTypeId() != null) {
			cableType.setCableTypeId(cableType.getCableTypeId().trim());
		}
		
		if (cableType.getCableTypeName() != null) {
			cableType.setCableTypeName(cableType.getCableTypeName().trim());
		}
		
		if (cableType.getDescription() != null) {
			cableType.setDescription(cableType.getDescription().trim());
		}
		
		int order = 2;
		String column = "CABLE_TYPE_ID";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) { }
		
		List<CableType> cableTypeList = cableTypeDAO.getCableTypeFilter(cableType, order, column);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "LoaiCap_" + dateNow;
		
		model.addAttribute("exportFileName", exportFileName);
		model.addAttribute("cableType", cableType);
		model.addAttribute("cableTypeList", cableTypeList);
		//Check Role User(Administrator)
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SysUsers> userList = sysUserDao.checkRole(username);
		Helper.checkRole(userList, model);
		return "jspalarm/cable/typelist";
	}
	
	@RequestMapping(value="cable-type/form", method=RequestMethod.GET)
    public String cableTypeForm(@RequestParam(required = false) Integer id, ModelMap model) {
		
		CableType cableType;
		
		if (id == null) {
			cableType = new CableType();
		} else {
			cableType = cableTypeDAO.getCableTypeById(id);
		}
		
		model.addAttribute("cableType", cableType);
		
		return "jspalarm/cable/typeform";
	}
	
	@RequestMapping(value="cable-type/form", method=RequestMethod.POST)
    public String cableTypeFormPost(@ModelAttribute("cableType") @Valid CableType cableType, BindingResult result, ModelMap model,
    		HttpServletRequest request, HttpServletResponse response) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if (cableType.getId() == null) {
			if (result.hasErrors()) {
				model.addAttribute("cableType", cableType);
				
				saveMessageKey(request, "cableType.InsertError");
				
				return "jspalarm/cable/typeform";
			}
			
			cableType.setCreatedBy(username);
			cableType.setCreateDate(new Date());
			
			cableTypeDAO.insertSelective(cableType);
			
			saveMessageKey(request, "cableType.InsertSuccess");
			System.out.println("Insert Thanh cong");
		} else {
			if (result.hasErrors()) {
				model.addAttribute("cableType", cableType);
				
				saveMessageKey(request, "cableType.UpdateError");
				
				return "jspalarm/cable/typeform";
			}
			
			cableType.setModifiedBy(username);
			cableType.setModifyDate(new Date());
			
			cableTypeDAO.updateByPrimaryKeySelective(cableType);
			
			saveMessageKey(request, "cableType.UpdateSuccess");
			
			System.out.println("Update Thanh cong");
		}
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value="cable-type/ajax/getCableTypeByCableTypeId")
    public @ResponseBody 
    CableType ajaxGetCableTypeByCableTypeId(@RequestParam(required = false) String cableTypeId, 
    		HttpServletRequest request, HttpServletResponse response) {
		
		cableTypeId = URLCoderTools.decode(cableTypeId);
		
		CableType cableType = cableTypeDAO.selectByPrimaryKey(cableTypeId);
		
		if (cableType != null)
			return cableType;
		return new CableType();
	}
	
	@RequestMapping(value="cable-att-master/ajax/deleteAttMaster")
    public @ResponseBody 
    int ajaxDeleteAttMaster(@RequestParam(required = false) Integer id, 
    		HttpServletRequest request, HttpServletResponse response) {
		
		Integer countUse = cableAttributesMasterDAO.checkUsingAttMaster(id);
		
		if (countUse > 0)
			return -1;
		
		cableAttributesMasterDAO.deleteByPrimaryKey(id);
		
		return 1;
	}
	
	@RequestMapping(value="cable-type/ajax/deleteCableType")
    public @ResponseBody 
    int ajaxDeleteCableType(@RequestParam(required = false) String cableTypeId, 
    		HttpServletRequest request, HttpServletResponse response) {
		
		cableTypeId = URLCoderTools.decode(cableTypeId);
		
		Integer countUse = cableTypeDAO.checkUsingCableType(cableTypeId);
		
		if (countUse > 0)
			return -1;
		
		cableTypeDAO.deleteByPrimaryKey(cableTypeId);
		
		return 1;
	}
	
	@RequestMapping(value="cable-att/ajax/deleteCableAtt")
    public @ResponseBody 
    int ajaxDeleteCableAtt(@RequestParam(required = false) Integer id, 
    		HttpServletRequest request, HttpServletResponse response) {
		
		cableLineAttributesDAO.deleteByPrimaryKey(id);
		
		return 1;
	}
}
