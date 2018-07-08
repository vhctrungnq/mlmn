package controller.cost;

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

import controller.BaseController;

import vo.CHighlightConfigs;
import vo.CostExpenses;
import vo.CostWork;
import vo.SYS_PARAMETER;
import vo.alarm.utils.Helper;
import vo.alarm.utils.UploadTools;
import dao.CHighlightConfigsDAO;
import dao.CostExpensesDAO;
import dao.CostWorkDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;

@Controller
@RequestMapping("/cost/costWork/*")
public class CostWorkController extends BaseController {
	@Autowired
	private CostExpensesDAO costExpensesDAO;
	
	@Autowired
	private CostWorkDAO costWorkDAO;
	
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private MDepartmentDAO mDepartmentDAO;
	
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	@ModelAttribute("isEnableList")
	public List<SYS_PARAMETER> isEnableList() {
		return sysParameterDao.getSysParameterByCode("EXPENSES_STATUS");
	}
	
	@ModelAttribute("highlight1")
	public String highlight1() {
		String highlight = "";
		List<CHighlightConfigs> highlightConfigList2 = cHighlightConfigsDAO.getByKey("NOT_NULL");
		if (highlightConfigList2.size()>0)
		{
			highlight = " $(this).find('.NOT_NULL').css({"+highlightConfigList2.get(0).getStyle()+"});";
		}
		return highlight;
	}
	
	@ModelAttribute("highlight")
	public String highlight() {
		List<CHighlightConfigs> highlightConfigList = cHighlightConfigsDAO.getByKey("ROOT_NO");
		return Helper.highLightCost(highlightConfigList, "item");
		
	}
	
	@RequestMapping(value="list")
    public String list(CostWork costWork,
    		@RequestParam(required = false) String isEnable,
    		ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		List<SYS_PARAMETER> sysParemeterTitle = costWorkDAO.titleForm(null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		if (costWork.getExpensesName()!= null) {
			costWork.setExpensesName(costWork.getExpensesName().trim());
		}
		System.out.println(costWork.getExpensesCode());
		costWork.setIsEnable(isEnable);
		int order = 0;
		String column = "EXPENSES_CODE";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) { }
		
		List<CostWork> expensesList = costWorkDAO.getCostWorkFilter(costWork, column, order);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "DanhSachNghiepVu_" + dateNow;
				
		model.addAttribute("exportFileName", exportFileName);
			
		model.addAttribute("costWork", costWork);
		model.addAttribute("isEnable", isEnable);
		
		model.addAttribute("workList", expensesList);
		
		
		List<CostExpenses> expensesCodeList = costExpensesDAO.getAll();
		model.addAttribute("expensesCodeList", expensesCodeList);
		
		return "jspcost/costWorkList";
		
	}
	
	private void updateRootNo(Integer id,Integer idSup,int num)
	{
		costWorkDAO.updateRootNo(id,idSup,num);
		if (num>0)
		{
			List<CostWork> childWork = costWorkDAO.getCostWorkChild(id);
			for (CostWork costWork : childWork) {
				updateRootNo(costWork.getId(), id,1);
			}
		}
	}
	
	@RequestMapping(value="form", method=RequestMethod.GET)
    public String cableForm(@RequestParam(required = false) Integer id, ModelMap model) {
		
		List<SYS_PARAMETER> sysParemeterTitle = costWorkDAO.titleForm("ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
		}
		CostWork costWork;
		List<CostExpenses> expensesCodeList = costExpensesDAO.getAll();
		String expensesCode="";
		
		if (id == null) {
			costWork = new CostWork();
			if (expensesCodeList.size()>0)
			{
				expensesCode = expensesCodeList.get(0).getExpensesCode();
			}	
				
		} else {
			costWork = costWorkDAO.selectByPrimaryKey(id);
			expensesCode = costWork.getExpensesCode();
			model.addAttribute("expensesCode", 	costWork.getExpensesCode());
			model.addAttribute("workSuper", 	costWork.getWorkSuper());
			model.addAttribute("isEnable", 	costWork.getIsEnable());
			model.addAttribute("oldSuper", 	costWork.getWorkSuper());
		}
		model.addAttribute("costWork", 	costWork);
		
		model.addAttribute("expensesCodeList", 	expensesCodeList);
		List<CostWork> workSuperList = costWorkDAO.getWorkSuperList(expensesCode,id);
		model.addAttribute("workSuperList", 	workSuperList);
		
		return "jspcost/costWorkForm";
	}
	
	@RequestMapping(value="form", method=RequestMethod.POST)
    public String cableFormPost(@ModelAttribute("costWork") @Valid CostWork costWork, BindingResult result, 
    		@RequestParam(required = true) String isEnable,
    		@RequestParam(required = false) Integer workSuper,
    		@RequestParam(required = false) String expensesCode,
    		@RequestParam(required = false) Integer oldSuper,
    		ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		List<SYS_PARAMETER> sysParemeterTitle = costWorkDAO.titleForm("ADD");
		String title="";
		if(sysParemeterTitle.size() > 0)
		{
			title=sysParemeterTitle.get(0).getValue();
			
		}
		
		//AddOrEdit(costWork.getId(),model);
		List<CostWork> workSuperList = costWorkDAO.getWorkSuperList(expensesCode,costWork.getId());
		model.addAttribute("workSuperList", 	workSuperList);
		
		List<CostExpenses> expensesCodeList = costExpensesDAO.getAll();
		model.addAttribute("expensesCodeList", 	expensesCodeList);
		
		if (result.hasErrors()) {
			model.addAttribute("costWork", costWork);
			model.addAttribute("isEnable", isEnable);
			model.addAttribute("workSuper", workSuper);
			model.addAttribute("expensesCode", expensesCode);
			model.addAttribute("oldSuper", oldSuper);
			saveMessageKey(request, "costWork.InsertError");
			model.addAttribute("titleF", title);
			return "jspcost/costWorkForm";
		}
		else
		{
			CostWork costWorkExit = costWorkDAO.checkExit(costWork.getExpensesCode(),costWork.getWorkSuper(),costWork.getWorkName());
			costWork.setExpensesCode(expensesCode);
			costWork.setWorkSuper(workSuper);
			costWork.setIsEnable(isEnable);
			
			if (costWork.getId() == null) {
				if (costWorkExit!=null)
				{
					model.addAttribute("costWork", costWork);
					model.addAttribute("isEnable", isEnable);
					model.addAttribute("workSuper", workSuper);
					model.addAttribute("expensesCode", expensesCode);
					model.addAttribute("oldSuper", oldSuper);
					saveMessageKey(request, "costWork.exits");
					model.addAttribute("titleF", title);
					return "jspcost/costWorkForm";
				}
				System.out.println("super:    "+ costWork.getWorkSuper());
				if (costWork.getWorkSuper()==0)
				{
					costWork.setRootNo(0);
					costWorkDAO.insertSelective(costWork);
				}
				else
				{
					costWorkDAO.insertSelective(costWork);
					updateRootNo(null,workSuper,1);
				}
					
				
				saveMessageKey(request, "costWork.InsertSuccess");
				
			} else {
				if (costWorkExit!=null)
				{
					if (costWorkExit.getId().intValue()!=costWork.getId().intValue())
					{
					System.out.println("ID cable: "+costWorkExit.getId()+"---id cableE1: "+costWork.getId());
					model.addAttribute("costWork", costWork);
					model.addAttribute("isEnable", 		isEnable);
					model.addAttribute("workSuper", workSuper);
					model.addAttribute("expensesCode", expensesCode);
					model.addAttribute("oldSuper", oldSuper);
					saveMessageKey(request, "costWork.exits");
					model.addAttribute("titleF", title);
					return "jspcost/costWorkForm";
					}
				}
				if (costWork.getId().intValue()!=costWork.getWorkSuper().intValue())
				{
					System.out.println("oldSuper: "+oldSuper);
					if (costWork.getWorkSuper()==0)
					{
						costWork.setRootNo(0);
					}
					else
					{
						costWork.setRootNo(null);
					}
					costWorkDAO.updateByPrimaryKey(costWork);
					updateRootNo(null,oldSuper,-1);
					updateRootNo(costWork.getId(),costWork.getWorkSuper(),1);
				}
				saveMessageKey(request, "costWork.UpdateSuccess");	

			}
		}
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value="ajax/get-costWork-inf-search")
    public @ResponseBody 
    List<String> ajaxGetCostWorkInfoSearch(@RequestParam(required = false) String focus,@RequestParam(required = false) String term,
    		HttpServletRequest request, HttpServletResponse response) {
		
		List<String> paramList = new ArrayList<String>();
		paramList = costWorkDAO.getCostWorkSearch(focus, term);
		
		return paramList;
	}
	
	@RequestMapping(value="ajax/getWorkSuperList")
    public @ResponseBody 
    List<CostWork> getWorkSuperList(@RequestParam(required = false) String expensesCode,@RequestParam(required = false) Integer id,
    		HttpServletRequest request, HttpServletResponse response) {
		
		List<CostWork> workSuperList = costWorkDAO.getWorkSuperList(expensesCode,id);
		
		return workSuperList;
	}
	
	@RequestMapping(value="ajax/checkBeforeDelete")
    public @ResponseBody 
    Integer checkBeforeDelete(Integer id, HttpServletRequest request, HttpServletResponse response) {	
		
		int kt = costWorkDAO.checkBeforeDalete(id);
		System.out.println("check delete: "+ kt);
		System.out.println("id: "+ id);
		if (kt > 0)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	@RequestMapping(value="ajax/deletecostWork")
    public @ResponseBody 
    Integer deletecostWork(Integer id,Integer workSuper, HttpServletRequest request, HttpServletResponse response) {	
		
			costWorkDAO.deleteByPrimaryKey(id);
			updateRootNo(null,workSuper,-1);
			return 1;
		
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(@RequestParam(required = false) String typeC,Model model) {
		List<SYS_PARAMETER> sysParemeterTitle = costWorkDAO.titleForm("UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		return "jspcost/costWorkUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("filePath") MultipartFile filePath, Model model, HttpServletRequest request) throws IOException, ParseException {
		
		List<SYS_PARAMETER> sysParemeterTitle = costWorkDAO.titleForm("UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		if (!filePath.isEmpty()) {

			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xls")) {
				@SuppressWarnings("rawtypes")
				List sheetData = UploadTools.readXlsFileForCost(filePath.getInputStream());
				importContent(sheetData,model,request);
				
			}
			else {
				 saveMessageKey(request, "cautruc.typeFIle");
			}
		}
		else {
			saveMessageKey(request, "cautruc.emptyFile");
		}
		
		return "jspcost/costWorkUpload";
		
	}

	private String importContent(List sheetData, Model model,
			HttpServletRequest request) {
		
		List<SYS_PARAMETER> sysParemeterTitle = costWorkDAO.titleForm("UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		List<CostWork> successList = new ArrayList<CostWork>();
		List<CostWork> failedList = new ArrayList<CostWork>();
		List<CostWork> success = new ArrayList<CostWork>();
		List<CostExpenses> expensesCodeList = costExpensesDAO.getAll();
		String expensesCode;
		String workSuperStr;
		String workName;
		String workContent;
		String isEnable;
		String ordering;
		
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 7) {
        		saveMessageKey(request, "Định dạng tệp không đúng");
        		
        		return "jspcost/costWorkUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		CostWork costWork;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			costWork = new CostWork();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=6; j++) {
     					data.add("");
     				}
        			expensesCode		= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			workSuperStr		= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			workName			= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			workContent			= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			isEnable			= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			ordering			= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			
        			costWork.setExpensesCode(expensesCode);
        			costWork.setWorkSuperStr(workSuperStr);
        			costWork.setWorkName(workName);
        			costWork.setWorkContent(workContent);
        			costWork.setIsEnable(isEnable);
        			costWork.setOrderingStr(ordering);
        			
        			if (expensesCode==null || workName == null 
							|| expensesCode.length() >= 20
							|| workName.length() >= 400
							//||	(workId != null && workId.length() >= 50)
							||	(workSuperStr != null && workSuperStr.length() >= 400)
							|| (workContent != null && workContent.length() >= 400)
							|| (ordering != null && ordering.length() >= 10)
							|| (isEnable != null && isEnable.length() >= 10)
					) {
						
						error = true;
					}
        			System.out.println("error: "+error);
        			//Kiem tra ordering
        			int orderingI = 0;
        			if (ordering!=null)
        			{
	        			try
	        			{
	        				orderingI = Integer.parseInt(ordering);
	        				costWork.setOrdering(orderingI);
	        			}
	        			catch(Exception exp)
	        			{
	        				error = true;
	        			}
        			}
        			
        			//Kiem tra ma khoan muc
        			boolean kt2 = false;
        			for (CostExpenses object : expensesCodeList) {
						if (object.getExpensesCode().equals(expensesCode))
						{
							kt2= true;
						}
						
					}
        			System.out.println(kt2);
        			if (kt2==false)
        			{
        				error = true;
        			}
        			
        			if (expensesCode == null && workName == null ) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(costWork);
    					} else  {
    						successList.add(costWork);
    					}
        			}
        		}
        	}
		}
		////Dang lam o day
		System.out.println("successList.size(): " + successList.size());
		for (CostWork cab: successList) {
			Integer workSuper = 0;
			//Kiem tra nghiep cha da ton tai chua
			boolean kt = true;
			if (cab.getWorkSuperStr()!=null && !cab.getWorkSuperStr().equals(""))
			{
				CostWork csuper = costWorkDAO.checkExit(null,null,cab.getWorkSuperStr());
				if (csuper==null)
				{
					failedList.add(cab);
					kt = false;
				}
				else
				{
					workSuper = csuper.getId();
					cab.setWorkSuper(workSuper);
				}
				
			}
			
			if (kt==true)
			{
				try {
					
					if (cab.getWorkSuperStr() == null)
					{
						cab.setWorkSuper(0);
						cab.setRootNo(0);
					}
					
					CostWork costWorkExit = costWorkDAO.checkExit(cab.getExpensesCode(),workSuper,cab.getWorkName());
					if (costWorkExit != null) {
	    				
	    				cab.setId(costWorkExit.getId());
	    				costWorkDAO.updateByPrimaryKey(cab);
	    				updateRootNo(costWorkExit.getId(),cab.getWorkSuper(),1);
	    			} 
	    			else
	    			{
	    				costWorkDAO.insertSelective(cab);
	    				//updateRootNo(cab.getWorkSuper(),1);
	    				if (cab.getWorkSuper().intValue()!=0)
						{
							updateRootNo(null,cab.getWorkSuper(),1);
						}
	    				
	    			}
					
					success.add(cab);
				} catch (Exception ex) {
					failedList.add(cab);
				}
			}
			
		}
		
		if (failedList.size() == 0 && success.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && success.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			model.addAttribute("status", "Dữ liệu không hợp lệ có thể do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép , định dạng dữ liệu không chính xác, hoặc mã khoản mục không là khoản mục con");					// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "DanhSachNghiepVu_" + dateNow;
				
		model.addAttribute("exportFileName", exportFileName);
				
		
		return "jspcost/costWorkUpload";
		
	}
}
