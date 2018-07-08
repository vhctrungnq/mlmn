package controller.cost;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

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

import vo.CHighlightConfigs;
import vo.CostDetail;
import vo.CostExpenses;
import vo.CostPlan;
import vo.CostSum;
import vo.CostWork;
import vo.MDepartment;
import vo.SYS_PARAMETER;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.Helper;

import controller.BaseController;
import dao.CHighlightConfigsDAO;
import dao.CostDetailDAO;
import dao.CostExpensesDAO;
import dao.CostPlanDAO;
import dao.CostWorkDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;

@Controller
@RequestMapping("/cost/costDetail/*")
public class CostDetailController extends BaseController {

	@Autowired
	private CostExpensesDAO costExpensesDAO;
	
	@Autowired
	private CostDetailDAO costDetailDAO;
	
	@Autowired
	private CostWorkDAO costWorkDAO;
	
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private MDepartmentDAO mDepartmentDAO;
	
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	@Autowired
	private CostPlanDAO costPlanDAO;
	
	private DateFormat df_full2 = new SimpleDateFormat("dd/MM/yyyy");
	
	@ModelAttribute("departmentList")
	public List<MDepartment> departmentList() {
		return mDepartmentDAO.getDepartmentList();
	}
	
	@ModelAttribute("highlight")
	public String highlight() {
		List<CHighlightConfigs> highlightConfigList = cHighlightConfigsDAO.getByKey("ROOT_NO");
		return Helper.highLightCost(highlightConfigList, "item");
		
	}
	
	@ModelAttribute("statusPlanList")
	public List<SYS_PARAMETER> statusPlanList() {
		return sysParameterDao.getSysParameterByCode("COST_STATUS");
	}
	
	@RequestMapping(value="list")
    public String list(CostDetail costDetail,@RequestParam(required = false) String departmentId,
    		@RequestParam(required = false) Integer costYear,
    		@RequestParam(required = false) Integer costMonth,
    		ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		List<SYS_PARAMETER> sysParemeterTitle = costDetailDAO.titleForm(null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		
		if (costDetail.getWorkName()!= null) {
			costDetail.setWorkName(costDetail.getWorkName().trim());
		}
		if (costDetail.getId() == null) {
			costDetail.setId(-1);
		}
		if (departmentId==null)
		{
			List<MDepartment> departL = departmentList();
			if (departL.size()>0)
			{
				departmentId = departL.get(0).getDeptCode();
				
			}
		}
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter1= new SimpleDateFormat("yyyy");
		String year = formatter1.format(currentDate.getTime());
		if (costYear == null)
		{
			costYear = Integer.parseInt(year);
		}
		
		SimpleDateFormat formatter2= new SimpleDateFormat("MM");
		String month = formatter2.format(currentDate.getTime());
		if (costMonth == null)
		{
			costMonth = Integer.parseInt(month);
		}
		costDetail.setDepartmentId(departmentId);
		costDetail.setCostYear(costYear);
		costDetail.setCostMonth(costMonth);
		
		int order = 0;
		String column = "EXPENSES_CODE";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) { }
		
		List<CostDetail> costDetailList = new ArrayList<CostDetail>();
		costDetailList = costDetailDAO.getCostDetailFilter(costDetail, column, order);
		
		int order1 = 0;
		String column1 = "EXPENSES_CODE";
		try {
			order1 = Integer.parseInt(request.getParameter((new ParamEncoder("item1").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column1 = request.getParameter((new ParamEncoder("item1").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) { }
		
		List<CostSum> sumList = new ArrayList<CostSum>();
		sumList = costDetailDAO.getSumCostByJobType(costDetail, column1, order1);
		
		int order2 = 0;
		String column2 = "EXPENSES_CODE";
		try {
			order2 = Integer.parseInt(request.getParameter((new ParamEncoder("item2").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column2 = request.getParameter((new ParamEncoder("item2").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) { }
		
		List<CostDetail> notDoneList = new ArrayList<CostDetail>();
		notDoneList = costDetailDAO.getCostNotDone(costDetail, column2, order2);
		
		int order3 = 0;
		String column3 = "EXPENSES_CODE";
		try {
			order3 = Integer.parseInt(request.getParameter((new ParamEncoder("item3").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column3 = request.getParameter((new ParamEncoder("item3").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) { }
		
		List<CostDetail> ariseNewList = new ArrayList<CostDetail>();
		ariseNewList = costDetailDAO.getCostAriseNew(costDetail, column3, order3);
		
		List<CostExpenses> expensesList = costDetailDAO.getExpensesByYear(null,costYear);
		
		Integer cost_monthLast = costMonth;
		if (costMonth>1)
		{
			cost_monthLast = costMonth - 1;
		}
		
		// Lay ten file export
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "ChiTietKeHoachChiPhi_" + dateNow;
				
		model.addAttribute("exportFileName", exportFileName);
		model.addAttribute("exportFileName1", "TongHopThucCP_"+dateNow);
		model.addAttribute("exportFileName2", "ChuaThucHienCP_"+dateNow);
		
		model.addAttribute("departmentId", departmentId);
		model.addAttribute("costYear", costYear);
		model.addAttribute("costMonth", costMonth);
		model.addAttribute("cost_monthLast", cost_monthLast);
		model.addAttribute("costDetail", costDetail);
		
		model.addAttribute("expensesList", 	expensesList);
		
		model.addAttribute("costDetailList", costDetailList);
		model.addAttribute("sumList", sumList);
		model.addAttribute("notDoneList", notDoneList);
		model.addAttribute("ariseNewList", ariseNewList);
		
		return "jspcost/costDetailList";
		
	}
	
	@RequestMapping(value="ajax/checkBeforDelete")
    public @ResponseBody 
    Integer checkBeforDelete(Integer id) {	
		int count = costDetailDAO.countChilden(id);
		if (count>0)
		{
			return 1;
		}
		
		return 0;
	}

	@RequestMapping(value="ajax/deletecostDetail")
    public @ResponseBody 
    Integer deletecostPlan(Integer id,String expensesSupper,String departmentId,Integer costYear, HttpServletRequest request, HttpServletResponse response) {	
		
		costDetailDAO.deleteByPrimaryKey(id);
		//updateCostSuper(expensesSupper,departmentId,costYear);
		return 1;
	}
	
	@RequestMapping(value="ajax/get-costDetail-inf-search")
    public @ResponseBody 
    List<String> ajaxGetCostDetailInfoSearch(@RequestParam(required = false) String focus,@RequestParam(required = false) String term,
    		HttpServletRequest request, HttpServletResponse response) {
		
		List<String> paramList = new ArrayList<String>();
		paramList = costDetailDAO.getCostDetailSearch(focus, term);
		
		return paramList;
	}
	
	
	@RequestMapping(value="ajax/get-workName-inf-search")
    public @ResponseBody 
    List<String> ajaxGetWorkNameInfoSearch(@RequestParam(required = false) String expensesCode,@RequestParam(required = false) String term,
    		HttpServletRequest request, HttpServletResponse response) {
		
		List<String> paramList = new ArrayList<String>();
		//paramList = costDetailDAO.getWorkNameSearch(expensesCode, term);
		
		return paramList;
	}
	
	private void updateCostSuper(Integer idSuper) {
		
		CostDetail detail = costDetailDAO.selectByPrimaryKey(idSuper);
		if (detail!=null)
		{
			costDetailDAO.updateCostDetailSupper(idSuper);
			
			if (detail.getWorkSuper() != 0)
			{
				updateCostSuper(detail.getWorkSuper());
			}
		}
	
}
	public int coutCostWorkIDNotNull(String departmentID, Integer costMonth, Integer costYear)
	{
		//String workID="";
		int cout=1;
		cout = costDetailDAO.countDetailPlan(departmentID,costMonth,costYear,"NOT NULL");
		//workID = "0DCM"+costMonth.toString()+String.valueOf(cout);
		return cout;
	}
	@RequestMapping(value="form", method=RequestMethod.GET)
    public String cableForm(
    		@RequestParam(required = false) Integer id,
    		@RequestParam(required = false) String deptName,
    		@RequestParam(required = false) Integer superID,
    		
    		ModelMap model ,HttpServletRequest request ) {
		
		List<SYS_PARAMETER> sysParemeterTitle = costDetailDAO.titleForm("ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
		}
		CostDetail costDetail;
		CostDetail costDetailSup = new CostDetail();
		costDetailSup = costDetailDAO.selectByPrimaryKey(superID);
		String expensesCode="";
		//List<CostDetail> costDetailSameList = costDetailDAO.getCostDetailSameList();
		String superName="";
		if (id == null) {
			costDetail = new CostDetail();
			if (costDetailSup!=null)
			{
				expensesCode = costDetailSup.getExpensesCode();
				costDetail.setWorkSuper(costDetailSup.getId());
				costDetail.setDepartmentId(costDetailSup.getDepartmentId());
				costDetail.setCostMonth(costDetailSup.getCostMonth());
				costDetail.setCostYear(costDetailSup.getCostYear());
				costDetail.setExpensesCode(costDetailSup.getExpensesCode());
				costDetail.setDeptName(costDetailSup.getDeptName());
				superName = costDetailSup.getWorkName();
				model.addAttribute("superName", 	superName);
				model.addAttribute("superRoot", 	costDetailSup.getRootNo());
				//Tinh gia tri so thu tu nghiep vu
				String taskNoTemp = costDetailDAO.getTaskNoChildMax(superID, costDetail.getDepartmentId(), costDetail.getCostMonth(), costDetail.getCostYear());
				String taskNo = "";
				
				if (taskNoTemp.equals("0"))
				{
					taskNo = costDetailSup.getTaskNo()+".1";
				}
				else
				{
					String subfirst = taskNoTemp.substring(0, taskNoTemp.lastIndexOf(".")+1);
					String subEnd = taskNoTemp.substring(taskNoTemp.lastIndexOf(".")+1,taskNoTemp.length());
					taskNo = subfirst + String.valueOf(Integer.parseInt(subEnd)+1);
				
					
				}
				
				costDetail.setTaskNo(taskNo);
				int stt = coutCostWorkIDNotNull(costDetail.getDepartmentId(), costDetail.getCostMonth(), costDetail.getCostYear())+1;
				//String workCode= "0DCM"+costDetail.getCostMonth().toString()+String.valueOf(stt);		
				costDetail.setWorkCode(getworkcode(costDetail.getCostMonth(),stt));
			}
			
		} 
		else {
			costDetail = costDetailDAO.selectByPrimaryKey(id);
			expensesCode = costDetail.getExpensesCode();
			model.addAttribute("contractDate", 	costDetail.getContractDateStr());
		}
		
		List<CostDetail> detailSame = new ArrayList<CostDetail>();
		if (superID!=0)
		{
			 detailSame = costDetailDAO.getDetailSame(superID);
		}
		else
		{
			detailSame = costDetailDAO.getDetailSame(id);
		}
		model.addAttribute("costDetailList", 	detailSame);
		
		List<CostWork> workList = costWorkDAO.getWorkSuperList(expensesCode, null);
		model.addAttribute("workList", 	workList);
		
		model.addAttribute("costDetail", 	costDetail);
		model.addAttribute("superID", 	superID);
		
		Integer cost_monthLast = costDetail.getCostMonth();
		if (costDetail.getCostMonth()>1)
		{
			cost_monthLast = costDetail.getCostMonth() - 1;
		}
		model.addAttribute("cost_monthLast", 	cost_monthLast);
		model.addAttribute("costMonth", 	costDetail.getCostMonth());
		model.addAttribute("deptName", 	deptName);
		
		return "jspcost/costDetailForm";
	}
	
	@RequestMapping(value="form", method=RequestMethod.POST)
    public String cableFormPost(@ModelAttribute("costDetail") @Valid CostDetail costDetail, BindingResult result, 
    		@RequestParam(required = false) String action,
    		@RequestParam(required = false) Integer superID,
    		@RequestParam(required = false) String superName,
    		@RequestParam(required = false) String taskSup,
    		@RequestParam(required = false) String contractDate,
    		@RequestParam(required = false) Integer superRoot,
    		@RequestParam(required = false) String deptName,
    		ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		List<SYS_PARAMETER> sysParemeterTitle = costDetailDAO.titleForm("ADD");
		String title="";
		if(sysParemeterTitle.size() > 0)
		{
			title=sysParemeterTitle.get(0).getValue();
			
		}
		System.out.println("action: "+action);
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Integer id;
		id = costDetail.getId();
		List<CostWork> workList = costWorkDAO.getWorkSuperList(costDetail.getExpensesCode(), null);
		model.addAttribute("workList", 	workList);
		model.addAttribute("deptName", 	deptName);
		if ( contractDate!=null && !contractDate.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", contractDate+" 00:00:00")==false)
		{
			model.addAttribute("errorContractDate", "*");
			model.addAttribute("costDetail",    costDetail);
			model.addAttribute("superID", 	    superID);
			model.addAttribute("superName", 	superName);
			model.addAttribute("taskSup", 		taskSup);
			model.addAttribute("superRoot", 	superRoot);
			model.addAttribute("contractDate", 	contractDate);
			List<CostDetail> detailSame = costDetailDAO.getDetailSame(superID);
			model.addAttribute("costDetailList", 	detailSame);
			saveMessageKey(request, "costDetail.InsertError");
			model.addAttribute("titleF", title);
			return "jspcost/costDetailForm";
		}
		if (result.hasErrors()) {
			model.addAttribute("costDetail",    costDetail);
			model.addAttribute("superID", 	    superID);
			model.addAttribute("superName", 	superName);
			model.addAttribute("taskSup", 		taskSup);
			model.addAttribute("superRoot", 	superRoot);
			model.addAttribute("contractDate", 	contractDate);
			List<CostDetail> detailSame = costDetailDAO.getDetailSame(superID);
			model.addAttribute("costDetailList", 	detailSame);
			saveMessageKey(request, "costDetail.InsertError");
			model.addAttribute("titleF", title);
			return "jspcost/costDetailForm";
		}
		else
		{
			if (contractDate!=null && !contractDate.equals(""))
			{
				try {
					costDetail.setContractDate(df_full2.parse(contractDate));
				} catch (ParseException e) {
					model.addAttribute("costDetail",    costDetail);
					model.addAttribute("superID", 	    superID);
					model.addAttribute("superName", 	superName);
					model.addAttribute("taskSup", 		taskSup);
					model.addAttribute("superRoot", 	superRoot);
					model.addAttribute("contractDate", 	contractDate);
					List<CostDetail> detailSame = costDetailDAO.getDetailSame(superID);
					model.addAttribute("costDetailList", 	detailSame);
					saveMessageKey(request, "costDetail.dateError");
					model.addAttribute("titleF", title);
					return "jspcost/costDetailForm";
				}
			}
			if (id == null && (costDetail.getWorkId()== null ||costDetail.getWorkId().equals("")))
			{
				CostWork newWork = new CostWork();
				newWork.setExpensesCode(costDetail.getExpensesCode());
				newWork.setWorkSuper(0);
				newWork.setWorkName(costDetail.getWorkName());
				newWork.setIsEnable("Y");
				newWork.setRootNo(0);
				CostWork costWorkExit = costWorkDAO.checkExit(newWork.getExpensesCode(),newWork.getWorkSuper(),newWork.getWorkName());
				Integer workId;
				if(costWorkExit==null)
				{
					workId = costWorkDAO.insertReturnIDnewWork(newWork);
				}
				else
				{
					workId = costWorkExit.getId();
				}
				costDetail.setWorkId(String.valueOf(workId));
			}
			
			CostDetail costDetailExit = costDetailDAO.checkExits(costDetail.getDepartmentId(),costDetail.getCostYear(),costDetail.getCostMonth(), costDetail.getWorkSuper(),costDetail.getExpensesCode(), costDetail.getWorkId());
	
			if (id == null) {
				if (costDetailExit !=null)
				{
					model.addAttribute("costDetail", costDetail);
					model.addAttribute("superID", 	superID);
					model.addAttribute("superName", 	superName);
					model.addAttribute("taskSup", 		taskSup);
					model.addAttribute("superRoot", 	superRoot);
					model.addAttribute("contractDate", 	contractDate);
					saveMessageKey(request, "costDetail.exits");
					List<CostDetail> detailSame = costDetailDAO.getDetailSame(superID);
					model.addAttribute("costDetailList", 	detailSame);
					model.addAttribute("titleF", title);
					return "jspcost/costDetailForm";
				}
				costDetail.setCreatedBy(username);
				costDetail.setCreateDate(new Date());
				costDetail.setWorkSuper(superID);
				
				if (superRoot!=null && superRoot >=100)
				{
					costDetail.setRootNo(superRoot+1);
				}
				else
				{
					costDetail.setRootNo(100);
				}
				
				costDetailDAO.insertSelective(costDetail);
				updateCostSuper(superID);
				saveMessageKey(request, "costDetail.InsertSuccess");
				
			} else {
				if (costDetailExit!=null)
				{
					if (costDetailExit.getId().intValue()!=costDetail.getId().intValue())
					{
						model.addAttribute("costDetail", costDetail);
						model.addAttribute("superID", 	superID);
						model.addAttribute("superName", 	superName);
						model.addAttribute("taskSup", 		taskSup);
						model.addAttribute("superRoot", 	superRoot);
						model.addAttribute("contractDate", 	contractDate);
						saveMessageKey(request, "costDetail.exits");
						List<CostDetail> detailSame = costDetailDAO.getDetailSame(superID);
						model.addAttribute("costDetailList", 	detailSame);
						model.addAttribute("titleF", title);
						return "jspcost/costDetailForm";
					}
				}
				costDetail.setModifiedBy(username);
				costDetail.setModifyDate(new Date());
				
				costDetailDAO.updateByPrimaryKeySelective(costDetail);
				updateCostSuper(superID);
				
				saveMessageKey(request, "costDetail.UpdateSuccess");	

			}
			//thuc thi caulenh load lại form nào khi chon save hay saveAndAdd
			if (action.equals("saveAndAdd"))
			{	
				model.addAttribute("titleF", title);
				System.out.print("luu lai va them moi + superID: "+superID);
				costDetail = new CostDetail();
				CostDetail costDetailSup = new CostDetail();
				if (superID != 0 )
				{
					costDetailSup = costDetailDAO.selectByPrimaryKey(superID);
				}
				else
				{
					costDetailSup = costDetailDAO.selectByPrimaryKey(id);
					superID = id;
				}
				if (costDetailSup!=null)
				{
					costDetail.setWorkSuper(costDetailSup.getId());
					
					costDetail.setDepartmentId(costDetailSup.getDepartmentId());
					costDetail.setCostMonth(costDetailSup.getCostMonth());
					costDetail.setCostYear(costDetailSup.getCostYear());
					costDetail.setExpensesCode(costDetailSup.getExpensesCode());
					costDetail.setDeptName(costDetailSup.getDeptName());
					
					superName = costDetailSup.getWorkName();
					//Tinh gia tri so thu tu nghiep vu
					String taskNoTemp = costDetailDAO.getTaskNoChildMax(superID, costDetail.getDepartmentId(), costDetail.getCostMonth(), costDetail.getCostYear());
					String taskNo = "";
					
					if (taskNoTemp.equals("0"))
					{
						System.out.print("taskSup: "+costDetailSup.getTaskNo());
						taskNo = costDetailSup.getTaskNo()+".1";
					}
					else
					{
						String subfirst = taskNoTemp.substring(0, taskNoTemp.lastIndexOf(".")+1);
						String subEnd = taskNoTemp.substring(taskNoTemp.lastIndexOf(".")+1,taskNoTemp.length());
						taskNo = subfirst + String.valueOf(Integer.parseInt(subEnd)+1);
						
					}
					
					costDetail.setTaskNo(taskNo);
					
					int stt = coutCostWorkIDNotNull(costDetail.getDepartmentId(), costDetail.getCostMonth(), costDetail.getCostYear())+1;
					//String workCode= "0DCM"+costDetail.getCostMonth().toString()+String.valueOf(stt);		
					costDetail.setWorkCode(getworkcode(costDetail.getCostMonth(),stt));
					
				}
				model.addAttribute("superName", 	superName);
				model.addAttribute("costDetail", 	costDetail);
				model.addAttribute("superID", 	superID);
				model.addAttribute("taskSup", 		taskSup);
				model.addAttribute("superRoot", 	superRoot);
				model.addAttribute("contractDate", 	contractDate);
				List<CostDetail> detailSame = costDetailDAO.getDetailSame(superID);
				model.addAttribute("costDetailList", 	detailSame);
				return "jspcost/costDetailForm";
				
			}
			
		}
		model.addAttribute("highlight", 	"");
		
		return "redirect:list.htm";
	}
	
	
	
	@RequestMapping(value="createPlan", method=RequestMethod.GET)
    public String createPlan(String departmentId,Integer costMonth,Integer costYear,ModelMap model, HttpServletRequest request,HttpServletResponse response) {	
		List<CostPlan> planList = new ArrayList<CostPlan>();
		//Lay danh sach ben lap ke hoach cua thang 9 
		planList = costPlanDAO.getCostPlanByMonth(departmentId,costYear,costMonth);
		System.out.println("planList: "+planList.size());
		String deptname="";
		List<MDepartment> departList = departmentList();
		for (MDepartment mDepartment : departList) {
			if (mDepartment.getDeptCode().equals(departmentId))
			{
				deptname = mDepartment.getDeptName().toUpperCase();
				break;
			}
		}
		CostPlan costPlan= new CostPlan();
		switch(costMonth)
		{
			case 1:
				costPlan.setDistributionPlan1Str("Y");
				costPlan.setDeliveryPlan1Str("Y");
				break;
			case 2:
				costPlan.setDistributionPlan2Str("Y");
				costPlan.setDeliveryPlan2Str("Y");
				break;
			case 3:
				costPlan.setDistributionPlan3Str("Y");
				costPlan.setDeliveryPlan3Str("Y");
				break;
			case 4:
				costPlan.setDistributionPlan4Str("Y");
				costPlan.setDeliveryPlan4Str("Y");
				break;
			case 5:
				costPlan.setDistributionPlan5Str("Y");
				costPlan.setDeliveryPlan5Str("Y");
				break;
			case 6:
				costPlan.setDistributionPlan6Str("Y");
				costPlan.setDeliveryPlan6Str("Y");
				break;
			case 7:
				costPlan.setDistributionPlan7Str("Y");
				costPlan.setDeliveryPlan7Str("Y");
				break;
			case 8:
				costPlan.setDistributionPlan8Str("Y");
				costPlan.setDeliveryPlan8Str("Y");
				break;
			case 9:
				costPlan.setDistributionPlan9Str("Y");
				costPlan.setDeliveryPlan9Str("Y");
				break;
			case 10:
				costPlan.setDistributionPlan10Str("Y");
				costPlan.setDeliveryPlan10Str("Y");
				break;
			case 11:
				costPlan.setDistributionPlan11Str("Y");
				costPlan.setDeliveryPlan11Str("Y");
				break;
			case 12:
				costPlan.setDistributionPlan12Str("Y");
				costPlan.setDeliveryPlan12Str("Y");
				break;
			
		}
		model.addAttribute("costPlan", 	costPlan);
		model.addAttribute("planList", 	planList);
		model.addAttribute("departmentId", 	departmentId);
		model.addAttribute("deptname", 	deptname);
		model.addAttribute("costMonth", 	costMonth);
		model.addAttribute("costYear", 	costYear);
		return "jspcost/costDetailPlan";
	}
	Integer insertSuperPlan(CostPlan costPlan,Integer costMonth)
	{
		String expenCode = costPlan.getExpensesSupper();
		String departmentId = costPlan.getDepartmentId();
		Integer costYear = costPlan.getCostYear();
		
		if (!expenCode.equals("0"))
		{
			CostPlan plan = costPlanDAO.getPlanByCode_Year_Department(expenCode,departmentId, costYear);
			
			Integer id=null;
			CostDetail costExit = costDetailDAO.checkExits(departmentId, costYear, costMonth, null,plan.getExpensesCode(),null);
			
			if (costExit==null)
			{
				CostDetail cost = new CostDetail();
				Integer idSuper=0;
				String taskSup="";
				CostDetail exSup=null;
				
				cost.setDepartmentId(departmentId);
				cost.setCostMonth(costMonth);
				cost.setCostYear(costYear);
				cost.setExpensesCode(plan.getExpensesCode());
				cost.setWorkId(plan.getExpensesCode());
				cost.setWorkName(plan.getExpensesName());
				cost.setDeliveryPlanYear(plan.getDeliveryYear());
				cost.setAdjustmentPlanYear(plan.getDistributionYear());
				cost.setRootNo(plan.getRootNo());
				
				if (plan.getExpensesSupper().equals("0"))
				{
					cost.setWorkSuper(0);
					cost.setRootNo(1);
					idSuper = 0;
				}
				else
				{
					idSuper = insertSuperPlan(plan,costMonth);
					cost.setWorkSuper(idSuper);
					exSup = costDetailDAO.selectByPrimaryKey(idSuper);
					taskSup = exSup.getTaskNo();
					//exSup = costDetailDAO.getCostDetailMonth(departmentId, costYear, costMonth, plan.getExpensesSupper(),null);
					
					//if (exSup != null)
					//{
						//idSuper = exSup.getId();
						//taskSup = exSup.getTaskNo();
						//cost.setWorkSuper(idSuper);
					//}
					/*else
					{
						cost.setWorkSuper(0);
						idSuper = 0;
					}*/
					
				}
				// insert
				/*CostDetail costDetailExit = costDetailDAO.checkExits(cost.getDepartmentId(),cost.getCostYear(),cost.getCostMonth(), cost.getWorkSuper(),cost.getExpensesCode(), cost.getWorkId());
				if (costDetailExit== null)
				{*/
					//Tinh gia tri so thu tu nghiep vu
					String taskNoTemp = costDetailDAO.getTaskNoChildMax(idSuper, departmentId, costMonth, costYear);
					String taskNo = "";
					if (idSuper == 0)
					{
						taskNo = String.valueOf(Integer.parseInt(taskNoTemp)+1);
						
					}
					else
					{
						if (taskNoTemp.equals("0"))
						{
							taskNo = taskSup+".1";
						}
						else
						{
							String subfirst = taskNoTemp.substring(0, taskNoTemp.lastIndexOf(".")+1);
							String subEnd = taskNoTemp.substring(taskNoTemp.lastIndexOf(".")+1,taskNoTemp.length());
							taskNo = subfirst + String.valueOf(Integer.parseInt(subEnd)+1);
						}
					}
					
					cost.setTaskNo(taskNo);
					id = costDetailDAO.insertCost(cost);
				/*}*/
				
			}
			else
			{
				id = costExit.getId();
			}
			return id;
		}
		return 0;
	}
	
	@RequestMapping(value="createPlan", method=RequestMethod.POST)
    public String createPlanPost(@RequestParam(required = false) String departmentId,
    		@RequestParam(required = false) Integer costMonth,
    		@RequestParam(required = false) Integer costYear,
    		@RequestParam(required = false) String checkedList,
			 HttpServletRequest request) {	
		
		System.out.println("Tao ke hoach");
		System.out.println("departmentId: "+departmentId);
		System.out.println("costMonth: "+costMonth);
		System.out.println("checkedList: "+checkedList);
		
		String[] checkList=null;
		if(checkedList != "" )
		{
			checkList = checkedList.split("-");
		}
		
		int stt = coutCostWorkIDNotNull(departmentId,costMonth,costYear);
		
		for (String idStr : checkList) {
			Integer idPlan = Integer.valueOf(idStr);
			CostPlan costPlan = costPlanDAO.selectByPrimaryKey(idPlan);
			//insertSuper
			insertSuperPlan(costPlan,costMonth);
			
			
			Integer idSuper=0;
			String taskSup="";
			CostDetail exSup=null;
			CostDetail cost = new CostDetail();
			
			cost.setDepartmentId(departmentId);
			cost.setCostMonth(costMonth);
			cost.setCostYear(costYear);
			cost.setExpensesCode(costPlan.getExpensesCode());
			cost.setWorkId(costPlan.getExpensesCode());
			if (costPlan.getExpensesSupper().equals("0"))
			{
				cost.setWorkSuper(0);
				cost.setRootNo(0);
			}
			else
			{
				
				exSup = costDetailDAO.getCostDetailMonth(departmentId, costYear, costMonth, costPlan.getExpensesSupper(),null);
				
				if (exSup != null)
				{
					idSuper = exSup.getId();
					taskSup = exSup.getTaskNo();
					cost.setWorkSuper(idSuper);
				}
				else
				{
					cost.setWorkSuper(0);
					idSuper = 0;
				}
				
			}
			cost.setWorkName(costPlan.getExpensesName());
			cost.setDeliveryPlanYear(costPlan.getDeliveryYear());
			cost.setAdjustmentPlanYear(costPlan.getDistributionYear());
			switch(costMonth)
			{
				case 1:
					cost.setDeliveryPlanCurrentm(costPlan.getDeliveryPlan1());
					break;
				case 2:
					cost.setDeliveryPlanCurrentm(costPlan.getDeliveryPlan2());
					break;
				case 3:
					cost.setDeliveryPlanCurrentm(costPlan.getDeliveryPlan3());
					break;
				case 4:
					cost.setDeliveryPlanCurrentm(costPlan.getDeliveryPlan4());
					break;
				case 5:
					cost.setDeliveryPlanCurrentm(costPlan.getDeliveryPlan5());
					break;
				case 6:
					cost.setDeliveryPlanCurrentm(costPlan.getDeliveryPlan6());
					break;
				case 7:
					cost.setDeliveryPlanCurrentm(costPlan.getDeliveryPlan7());
					break;
				case 8:
					cost.setDeliveryPlanCurrentm(costPlan.getDeliveryPlan8());
					break;
				case 9:
					cost.setDeliveryPlanCurrentm(costPlan.getDeliveryPlan9());
					break;
				case 10:
					cost.setDeliveryPlanCurrentm(costPlan.getDeliveryPlan10());
					break;
				case 11:
					cost.setDeliveryPlanCurrentm(costPlan.getDeliveryPlan11());
					break;
				default:
					cost.setDeliveryPlanCurrentm(costPlan.getDeliveryPlan12());
			}
			
			//Tinh cac thong tin chi phi cua thang truoc
			CostDetail costLast = costDetailDAO.getCostDetailMonth(departmentId,costYear,costMonth-1,costPlan.getExpensesCode(),null);
			if (costLast!=null)
			{
				Double doneTolastm = 0.0;
				Double doneCurrent = 0.0;
				Double deliveryPlan = 1.0;
				if (costLast.getDoneToLastm()!= null)
				{
					doneTolastm = costLast.getDoneToLastm();
				}
				if (costLast.getDoneCurrentm()!= null)
				{
					doneCurrent = costLast.getDoneCurrentm();
				}
				if (costLast.getDeliveryPlanCurrentm()!= null)
				{
					deliveryPlan = costLast.getDeliveryPlanCurrentm();
				}
				
				cost.setDoneToLastm(doneTolastm + doneCurrent);
				//cost.setRateDoneLastm(doneCurrent*100/deliveryPlan);
			}
			cost.setDoneCurrentm(0.0);
			if(cost.getDoneToLastm()==null)
			{
				cost.setDoneToLastm(0.0);
			}
			if(cost.getDoneCurrentm()==null)
			{
				cost.setDoneCurrentm(0.0);
			}
			if(cost.getDeliveryPlanYear()==null)
			{
				cost.setDeliveryPlanYear(0.0);
			}
			if(cost.getDeliveryPlanCurrentm()==null)
			{
				cost.setDeliveryPlanCurrentm(0.0);
			}
			cost.setComulativeCurrentm(cost.getDoneToLastm() + cost.getDoneCurrentm());
			cost.setRemainingCost(cost.getDeliveryPlanYear() - cost.getDoneToLastm() - cost.getDeliveryPlanCurrentm());
			cost.setRootNo(costPlan.getRootNo());
			//tinh phan tram
			Double deliveryPlanCurrentm =1.0;
			if (cost.getDeliveryPlanCurrentm()>0.0)
			{
				deliveryPlanCurrentm = cost.getDeliveryPlanCurrentm();
				cost.setRateDoneLastm(cost.getDoneCurrentm()*100/deliveryPlanCurrentm);
			}

			// insert
			CostDetail costDetailExit = costDetailDAO.checkExits(cost.getDepartmentId(),cost.getCostYear(),cost.getCostMonth(), cost.getWorkSuper(),cost.getExpensesCode(), cost.getWorkId());
			if (costDetailExit== null)
			{
				//Tinh gia tri so thu tu nghiep vu
				String taskNoTemp = costDetailDAO.getTaskNoChildMax(idSuper, departmentId, costMonth, costYear);
				String taskNo = "";
				if (idSuper == 0)
				{
					taskNo = String.valueOf(Integer.parseInt(taskNoTemp)+1);
					
				}
				else
				{
					if (taskNoTemp.equals("0"))
					{
						taskNo = taskSup+".1";
					}
					else
					{
						String subfirst = taskNoTemp.substring(0, taskNoTemp.lastIndexOf(".")+1);
						String subEnd = taskNoTemp.substring(taskNoTemp.lastIndexOf(".")+1,taskNoTemp.length());
						taskNo = subfirst + String.valueOf(Integer.parseInt(subEnd)+1);
						
						
					}
				}
				
				cost.setTaskNo(taskNo);
				//ma cong viec
				stt++;
				//String workCode= "0DCM"+costMonth.toString()+String.valueOf(stt);		
				cost.setWorkCode(getworkcode(costMonth,stt));
				
				costDetailDAO.insertSelective(cost);
				
			}
			else
			{
				cost.setId(costDetailExit.getId());
				costDetailDAO.updateByPrimaryKeySelective(cost);
				
			}
			if (idSuper!=0)
			{
				updateCostSuper(idSuper);
			}
			
		}
		//Lay danh sach ke hoach chi phi chi tiet cua t8 ma co trang thai huy/chuyen de cap nhat vao thang 9
		List<CostDetail> costDetailLast = costDetailDAO.getCostDetailLastMonthNotDone(departmentId,costYear,costMonth-1);
		for (CostDetail costDetail : costDetailLast) {
			System.out.println("cap nhat ke hoach thang truoc: WorkId:  "+costDetail.getWorkId());
			//voi moi nghiep vu, neu thang 9 da ton tai thi se cap nhat lai mot so thong tin sau khi tinh toan xong. neu chua co thi them moi vao cho ke hoach thang 9
			CostDetail detailCurrent = costDetailDAO.getCostDetailMonth(departmentId, costYear, costMonth, costDetail.getExpensesCode(),costDetail.getWorkId());
			if (detailCurrent!=null)
			{
				System.out.println("cap nhat ke hoach thang truoc: ID:  "+detailCurrent.getId());
				
				if(detailCurrent.getDoneToLastm()==null)
				{
					detailCurrent.setDoneToLastm(0.0);
				}
				if(detailCurrent.getDoneCurrentm()==null)
				{
					detailCurrent.setDoneCurrentm(0.0);
				}
				if(detailCurrent.getDeliveryPlanYear()==null)
				{
					detailCurrent.setDeliveryPlanYear(0.0);
				}
				if(detailCurrent.getDeliveryPlanCurrentm()==null)
				{
					detailCurrent.setDeliveryPlanCurrentm(0.0);
				}
				if(detailCurrent.getAdjustLastm()==null)
				{
					detailCurrent.setAdjustLastm(0.0);
				}
				detailCurrent.setAdjustLastm(costDetail.getDoneCurrentm());
				detailCurrent.setDoneToLastm(detailCurrent.getDoneToLastm() - detailCurrent.getAdjustLastm());
				detailCurrent.setComulativeCurrentm(detailCurrent.getDoneToLastm() + detailCurrent.getDoneCurrentm());
				detailCurrent.setRemainingCost(detailCurrent.getDeliveryPlanYear() - detailCurrent.getDoneToLastm() - detailCurrent.getDeliveryPlanCurrentm());
				//update
				costDetailDAO.updateByPrimaryKeySelective(detailCurrent);
				//update du lieu khoan muc cha???
				if (detailCurrent.getRootNo()!=null && detailCurrent.getRootNo()>=100)
				{
					updateCostSuper(detailCurrent.getWorkSuper());
				}
				
				
			}
			else
			{
				//insert vao du lieu cua thang 9
				detailCurrent = costDetail;
				Integer workSup;
				String taskSup="";
				CostDetail exSup=null;
				
				if (costDetail.getWorkSuper()!=0)
				{
					workSup = moveCostDetailSupper(detailCurrent);
					exSup = costDetailDAO.selectByPrimaryKey(workSup);
					taskSup = exSup.getTaskNo();
				}
				else
					workSup = 0;
				//Tinh lai cac gia tri
				detailCurrent.setId(null);
				detailCurrent.setStatusPlan(null);
				detailCurrent.setCostMonth(costMonth);
				detailCurrent.setAdjustLastm(costDetail.getDoneCurrentm());
				detailCurrent.setWorkSuper(workSup);
				//task no
				String taskNo = "";
				String taskNoTemp = costDetailDAO.getTaskNoChildMax(workSup, departmentId, costMonth, costYear);
				
				if (workSup == 0)
				{
					taskNo = String.valueOf(Integer.parseInt(taskNoTemp)+1);
				}
				else
				{
					if (taskNoTemp.equals("0"))
					{
						taskNo = taskSup+".1";
					}
					else
					{
						String subfirst = taskNoTemp.substring(0, taskNoTemp.lastIndexOf(".")+1);
						String subEnd = taskNoTemp.substring(taskNoTemp.lastIndexOf(".")+1,taskNoTemp.length());
						taskNo = subfirst + String.valueOf(Integer.parseInt(subEnd)+1);
					}
				}
				detailCurrent.setTaskNo(taskNo);
				
				//chi phi
				Double doneCurrent = 0.0;
				Double deliveryPlan = 1.0;
				if (costDetail.getDoneCurrentm()!= null)
				{
					doneCurrent = costDetail.getDoneCurrentm();
				}
				if (costDetail.getDeliveryPlanCurrentm()!= null)
				{
					deliveryPlan = costDetail.getDeliveryPlanCurrentm();
				}
				detailCurrent.setRateDoneLastm(doneCurrent*100/deliveryPlan);
				
				/*Integer superID = costDetailDAO.getIDSuperOfLastMonth(costDetail.getDepartmentId(),costDetail.getCostYear(),costDetail.getCostMonth(),costDetail.getExpensesCode(),costDetail.getId());
				detailCurrent.setWorkSuper(superID);*/
				
				//insert
				stt++;
				//String workCode= "0DCM"+costMonth.toString()+String.valueOf(stt);		
				detailCurrent.setWorkCode(getworkcode(costMonth, stt));
				
				costDetailDAO.insertSelective(detailCurrent);
				
				if (detailCurrent.getRootNo()!=null && detailCurrent.getRootNo()>=100)
				{
					updateCostSuper(detailCurrent.getWorkSuper());
				}
			}		
		}
		return "redirect:list.htm";
	}
	public Integer moveCostDetailSupper(CostDetail costDetaillast)
	{
		int idSuper = costDetaillast.getWorkSuper();
		Integer idS=null;
		if (idSuper!=0)
		{
			CostDetail costSup = new CostDetail();
			String taskSup="";
			CostDetail exSup=null;
			
			costSup = costDetailDAO.selectByPrimaryKey(idSuper);
			Integer costMonth= costSup.getCostMonth();
			Integer costYear = costSup.getCostYear();
			if (costMonth==12)
			{
				costMonth=1;
				costYear++;
			}
			else
			{
				costMonth++;
			}
			//Kiem tra costSup da ton tai chua?. Neu chua thi inset.
			CostDetail costExit = costDetailDAO.checkExits(costSup.getDepartmentId(), costSup.getCostYear(), costSup.getCostMonth()+1, null, costSup.getExpensesCode(),costSup.getWorkId());
			if (costExit==null)
			{
				//cost super chua ton tai
				costSup.setId(null);
				costSup.setStatusPlan(null);
				costSup.setCostMonth(costMonth);
				costSup.setCostYear(costYear);
				costSup.setAdjustLastm(costSup.getDoneCurrentm());
				Integer workSup;
				if (costSup.getWorkSuper()!=0)
				{
					workSup = moveCostDetailSupper(costSup);
					exSup = costDetailDAO.selectByPrimaryKey(idSuper);
					taskSup = exSup.getTaskNo();
				}
				else
				{
					workSup=0;
					costSup.setRootNo(1);
				}
				costSup.setWorkSuper(workSup);
				//task no
				String taskNoTemp = costDetailDAO.getTaskNoChildMax(workSup, costSup.getDepartmentId(), costMonth, costYear);
				String taskNo = "";
				if (workSup == 0)
				{
					taskNo = String.valueOf(Integer.parseInt(taskNoTemp)+1);
					
				}
				else
				{
					if (taskNoTemp.equals("0"))
					{
						taskNo = taskSup+".1";
					}
					else
					{
						String subfirst = taskNoTemp.substring(0, taskNoTemp.lastIndexOf(".")+1);
						String subEnd = taskNoTemp.substring(taskNoTemp.lastIndexOf(".")+1,taskNoTemp.length());
						taskNo = subfirst + String.valueOf(Integer.parseInt(subEnd)+1);
					}
				}
				
				costSup.setTaskNo(taskNo);
				idS = costDetailDAO.insertCost(costSup);
			}
			else
			{
				//cost super da ton tai nen lay id cua super
				idS = costExit.getId();
			}
		}
		
		return idS;
	}
	
	@RequestMapping("ajax/movePlan")
	public @ResponseBody
	int checkedList(@RequestParam(required = false) String departmentId,
    		@RequestParam(required = false) Integer costMonth,
    		@RequestParam(required = false) Integer costYear,
    		@RequestParam(required = false) String checkedList,
			HttpServletRequest request) {
		System.out.println("update trang thai thanh chuyen");
		System.out.println("departmentId: "+departmentId);
		System.out.println("costMonth: "+costMonth);
		//cap nhat trang thai chuyen
		if(checkedList != "" )
		{
			String[] checkList = checkedList.split("-");
			for(int i=0;i<checkList.length;i++)
			{
				Integer id = Integer.valueOf(checkList[i]);
				costDetailDAO.updateStatusPlan(id);
			}
			
			// Chuyen sang thang sau
			if (costMonth==12)
			{
				costMonth=1;
				costYear=costYear+1;
			}
			else
			{
				costMonth++;
			}
			for (String idPlan : checkList) {
				Integer id = Integer.valueOf(idPlan);
				CostDetail costDetail = costDetailDAO.selectByPrimaryKey(id);
				System.out.println("cap nhat ke hoach thang truoc: WorkId:  "+costDetail.getWorkId());
				//voi moi nghiep vu, neu thang 9 da ton tai thi se cap nhat lai mot so thong tin sau khi tinh toan xong. neu chua co thi them moi vao cho ke hoach thang 9
				CostDetail detailCurrent = costDetailDAO.getCostDetailMonth(departmentId, costYear, costMonth, costDetail.getExpensesCode(),costDetail.getWorkId());
				if (detailCurrent!=null)
				{
					System.out.println("cap nhat ke hoach thang truoc: ID:  "+detailCurrent.getId());
					
					if(detailCurrent.getDoneToLastm()==null)
					{
						detailCurrent.setDoneToLastm(0.0);
					}
					if(detailCurrent.getDoneCurrentm()==null)
					{
						detailCurrent.setDoneCurrentm(0.0);
					}
					if(detailCurrent.getDeliveryPlanYear()==null)
					{
						detailCurrent.setDeliveryPlanYear(0.0);
					}
					if(detailCurrent.getDeliveryPlanCurrentm()==null)
					{
						detailCurrent.setDeliveryPlanCurrentm(0.0);
					}
					if(detailCurrent.getAdjustLastm()==null)
					{
						detailCurrent.setAdjustLastm(0.0);
					}
					detailCurrent.setAdjustLastm(costDetail.getDoneCurrentm());
					detailCurrent.setDoneToLastm(detailCurrent.getDoneToLastm() - detailCurrent.getAdjustLastm());
					detailCurrent.setComulativeCurrentm(detailCurrent.getDoneToLastm() + detailCurrent.getDoneCurrentm());
					detailCurrent.setRemainingCost(detailCurrent.getDeliveryPlanYear() - detailCurrent.getDoneToLastm() - detailCurrent.getDeliveryPlanCurrentm());
					//update
					costDetailDAO.updateByPrimaryKeySelective(detailCurrent);
					//update du lieu khoan muc cha???
					if (detailCurrent.getRootNo()!=null && detailCurrent.getRootNo()>=100)
					{
						updateCostSuper(detailCurrent.getWorkSuper());
					}
					
					
				}
				else
				{
					//insert vao du lieu cua thang 9
					detailCurrent = costDetail;
					Integer workSup;
					String taskSup="";
					CostDetail exSup=null;
					if (costDetail.getWorkSuper()!=0)
					{
						workSup = moveCostDetailSupper(detailCurrent);
						exSup = costDetailDAO.selectByPrimaryKey(workSup);
						taskSup = exSup.getTaskNo();
					}
					else
					{
						workSup = 0;
						detailCurrent.setRootNo(0);
					}
					
					//Tinh lai cac gia tri
					detailCurrent.setId(null);
					detailCurrent.setStatusPlan(null);
					detailCurrent.setCostMonth(costMonth);
					detailCurrent.setWorkSuper(workSup);
					//task no
					String taskNoTemp = costDetailDAO.getTaskNoChildMax(workSup, detailCurrent.getDepartmentId(), costMonth, costYear);
					String taskNo = "";
					if (workSup == 0)
					{
						taskNo = String.valueOf(Integer.parseInt(taskNoTemp)+1);
						
					}
					else
					{
						if (taskNoTemp.equals("0"))
						{
							taskNo = taskSup+".1";
						}
						else
						{
							String subfirst = taskNoTemp.substring(0, taskNoTemp.lastIndexOf(".")+1);
							String subEnd = taskNoTemp.substring(taskNoTemp.lastIndexOf(".")+1,taskNoTemp.length());
							taskNo = subfirst + String.valueOf(Integer.parseInt(subEnd)+1);
						}
					}
					
					detailCurrent.setTaskNo(taskNo);
					Double doneCurrent = 0.0;
					Double deliveryPlan = 1.0;
					if (costDetail.getDoneCurrentm()!= null)
					{
						if (costDetail.getDeliveryPlanCurrentm()!= null)
						{
							Double kq= (doneCurrent*100)/deliveryPlan;
							System.out.println(kq);
							detailCurrent.setRateDoneLastm(kq);
						}
						detailCurrent.setAdjustLastm(costDetail.getDoneCurrentm());
						
					}
					
					/*Integer superID = costDetailDAO.getIDSuperOfLastMonth(costDetail.getDepartmentId(),costDetail.getCostYear(),costDetail.getCostMonth(),costDetail.getExpensesCode(),costDetail.getId());
					detailCurrent.setWorkSuper(superID);*/
					
					//insert
					
					int stt = coutCostWorkIDNotNull(costDetail.getDepartmentId(), costDetail.getCostMonth(), costDetail.getCostYear())+1;
					detailCurrent.setWorkCode(getworkcode(costDetail.getCostMonth(),stt));
					
					costDetailDAO.insertSelective(detailCurrent);
					if (detailCurrent.getRootNo()!=null && detailCurrent.getRootNo()>=100)
					{
						updateCostSuper(detailCurrent.getWorkSuper());
					}
				}		
			}
			
			
			
		}
		
		return 1;
	}
	public String getworkcode(int month, int stt)
	{
		String monthStr="";
		String sttStr="";
		if (month<10)
		{
			monthStr="0"+String.valueOf(month);
		}
		else
		{
			monthStr=String.valueOf(month);
		}
		if (stt<10)
		{
			sttStr="0"+String.valueOf(stt);
		}
		else
		{
			sttStr= String.valueOf(stt);
		}
		String workcode= "0DCM"+monthStr+sttStr;
		return workcode;
	}
	
	@RequestMapping(value="reportKH")
    public void report(@RequestParam(required = false) String departmentId,
    		@RequestParam(required = false) Integer costYear,
    		@RequestParam(required = false) Integer costMonth,
    		ModelMap model, HttpServletRequest request,HttpServletResponse response) throws JRException, IOException {
		
		CostDetail costDetail = new CostDetail();
		List<MDepartment> departL = departmentList();
		String departName="";
		if (departmentId==null)
		{
			if (departL.size()>0)
			{
				departmentId = departL.get(0).getDeptCode();
				departName   = departL.get(0).getDeptName();
			}
		}
		else
		{
			for (MDepartment mDepartment : departL) {
				if (mDepartment.getDeptCode().equals(departmentId))
				{
					departName = mDepartment.getDeptName();
				}
			}
		}
		costDetail.setDepartmentId(departmentId);
		
		Calendar currentDate = Calendar.getInstance();
		if (costYear==null)
		{
			SimpleDateFormat formatter1= new SimpleDateFormat("yyyy");
			String year = formatter1.format(currentDate.getTime());
			if (costYear == null)
			{
				costYear = Integer.parseInt(year);
			}
		}
		costDetail.setCostYear(costYear);
		
		SimpleDateFormat formatter2= new SimpleDateFormat("MM");
		String month = formatter2.format(currentDate.getTime());
		if (costMonth == null)
		{
			costMonth = Integer.parseInt(month);
		}
		costDetail.setCostMonth(costMonth);
		
		List<CostDetail> costDetailList = new ArrayList<CostDetail>();
		costDetailList = costDetailDAO.getCostDetailFilter(costDetail, "EXPENSES_CODE", 0);
		
		List<CostSum> sumList = new ArrayList<CostSum>();
		sumList = costDetailDAO.getSumCostByJobType(costDetail, "EXPENSES_CODE", 0);
		
		List<CostDetail> notDoneList = new ArrayList<CostDetail>();
		notDoneList = costDetailDAO.getCostNotDone(costDetail, "EXPENSES_CODE", 0);
		
		// Lay ten file export
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		
		SimpleDateFormat formatter3 = new SimpleDateFormat("dd/MM/yyyy");
		String dateNow2 = formatter3.format(currentDate.getTime());
			
		Map<String, Object> parameters = new HashMap<String, Object>();
				
		parameters.put("costMonth", costMonth);
		parameters.put("costYear", costYear);
		parameters.put("deptName", departName);
		parameters.put("SUBREPORT_DIR", request.getSession().getServletContext().getRealPath("/report/"));
		parameters.put("costDetailList", costDetailList);
		parameters.put("notDoneList", notDoneList);
		parameters.put("sumList", sumList);
		parameters.put("title", "KẾ HOẠCH CHI PHÍ THÁNG "+costMonth+"/"+costYear);
		parameters.put("titlePB", "Đơn vị triển khai: "+departName);
		parameters.put("titleGC", "(Ban hành kèm theo QĐ số 1632/2013/QĐ-VMSC2 ngày  "+dateNow2+")");
		parameters.put("headerLuyKeLast", "Đã ký HĐ lũy kế đến T"+(costMonth-1));
		parameters.put("headerKHGiao", "KH giao T"+costMonth+"(đến bước ký HĐ)");
		parameters.put("headerDaTH", "Đã TH T"+costMonth+"(Ký HĐ)");
		parameters.put("headerLuyKeCR", "Lũy kế TH hết T"+costMonth);
	
		//System.out.println(request.getSession().getServletContext().getRealPath("/report")+"/");
		
		/*//Doc du lieu do vao file main report 
		InputStream inputStream = new FileInputStream (request.getSession().getServletContext().getRealPath("/report/CostDetailMain.jrxml")); 
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(null);	
		//Map parameters = new HashMap();
		
		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
				
		//Doc du lieu do vao file report 
		InputStream inputStream1 = new FileInputStream (request.getSession().getServletContext().getRealPath("/report/CostDetail.jrxml")); 
		JRBeanCollectionDataSource beanColDataSource1 = new JRBeanCollectionDataSource(costDetailList);	
		//Map parameters = new HashMap();
		
		JasperDesign jasperDesign1 = JRXmlLoader.load(inputStream1);
		JasperReport jasperReport1 = JasperCompileManager.compileReport(jasperDesign1);
		JasperPrint jasperPrint1 = JasperFillManager.fillReport(jasperReport1, parameters, beanColDataSource1);
		
		//Doc du lieu do vao file report 
		InputStream inputStream2 = new FileInputStream (request.getSession().getServletContext().getRealPath("/report/SumDetail.jrxml")); 
		JRBeanCollectionDataSource beanColDataSource2 = new JRBeanCollectionDataSource(sumList);	
		//Map parameters = new HashMap();
		
		JasperDesign jasperDesign2 = JRXmlLoader.load(inputStream2);
		JasperReport jasperReport2 = JasperCompileManager.compileReport(jasperDesign2);
		JasperPrint jasperPrint2 = JasperFillManager.fillReport(jasperReport2, parameters, beanColDataSource2);
		
		//Doc du lieu do vao file report 
		InputStream inputStream3 = new FileInputStream (request.getSession().getServletContext().getRealPath("/report/CostDetailNotDone.jrxml")); 
		JRBeanCollectionDataSource beanColDataSource3 = new JRBeanCollectionDataSource(notDoneList);	
		//Map parameters = new HashMap();
		
		JasperDesign jasperDesign3 = JRXmlLoader.load(inputStream3);
		JasperReport jasperReport3 = JasperCompileManager.compileReport(jasperDesign3);
		JasperPrint jasperPrint3 = JasperFillManager.fillReport(jasperReport3, parameters, beanColDataSource3);

		List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
		
		//jprintlist.add(jasperPrint);
		jprintlist.add(jasperPrint2);
        jprintlist.add(jasperPrint1);
        jprintlist.add(jasperPrint3);
        */
		
		
		
		InputStream inputStream = new FileInputStream (request.getSession().getServletContext().getRealPath("/report/CostDetailMain.jrxml")); 
		//JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(null);	

		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
		
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment; filename=\"BaoCaoKeHoachChiPhi_"+dateNow+".xls\"");
		JRXlsExporter exporterXLS = new JRXlsExporter();
		//exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
		exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
		exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
	//	exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		exporterXLS.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
		exporterXLS.setParameter(JRXlsExporterParameter.OFFSET_X, 0);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.FALSE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		exporterXLS.exportReport();	
		response.getOutputStream().flush();
		response.getOutputStream().close();
		
		
	}
	
	@RequestMapping(value="reportTH")
    public void reportTH(@RequestParam(required = false) String departmentId,
    		@RequestParam(required = false) Integer costYear,
    		@RequestParam(required = false) Integer costMonth,
    		ModelMap model, HttpServletRequest request,HttpServletResponse response) throws JRException, IOException {
		
		CostDetail costDetail = new CostDetail();
		List<MDepartment> departL = departmentList();
		String departName="";
		if (departmentId==null)
		{
			if (departL.size()>0)
			{
				departmentId = departL.get(0).getDeptCode();
				departName   = departL.get(0).getDeptName();
			}
		}
		else
		{
			for (MDepartment mDepartment : departL) {
				if (mDepartment.getDeptCode().equals(departmentId))
				{
					departName = mDepartment.getDeptName();
				}
			}
		}
		costDetail.setDepartmentId(departmentId);
		
		Calendar currentDate = Calendar.getInstance();
		if (costYear==null)
		{
			SimpleDateFormat formatter1= new SimpleDateFormat("yyyy");
			String year = formatter1.format(currentDate.getTime());
			if (costYear == null)
			{
				costYear = Integer.parseInt(year);
			}
		}
		costDetail.setCostYear(costYear);
		
		SimpleDateFormat formatter2= new SimpleDateFormat("MM");
		String month = formatter2.format(currentDate.getTime());
		if (costMonth == null)
		{
			costMonth = Integer.parseInt(month);
		}
		costDetail.setCostMonth(costMonth);
		
		List<CostDetail> costDetailList = new ArrayList<CostDetail>();
		costDetailList = costDetailDAO.getCostDetailFilter(costDetail, "EXPENSES_CODE", 0);
		
		List<CostSum> sumList = new ArrayList<CostSum>();
		sumList = costDetailDAO.getSumCostByJobType(costDetail, "EXPENSES_CODE", 0);
		
		// Lay ten file export
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		
		SimpleDateFormat formatter3 = new SimpleDateFormat("dd/MM/yyyy");
		String dateNow2 = formatter3.format(currentDate.getTime());
			
		Map<String, Object> parameters = new HashMap<String, Object>();
				
		parameters.put("costMonth", costMonth);
		parameters.put("costYear", costYear);
		parameters.put("deptName", departName);
		parameters.put("SUBREPORT_DIR", request.getSession().getServletContext().getRealPath("/report/"));
		parameters.put("costDetailList", costDetailList);
		parameters.put("sumList", sumList);
		parameters.put("title", "KẾ HOẠCH THỰC HIỆN CHI PHÍ THÁNG "+costMonth+"/"+costYear);
		parameters.put("titlePB", "Đơn vị triển khai: "+departName);
		parameters.put("headerDaTHLast", "Đã TH T"+(costMonth-1));
		parameters.put("headerGiaoCR", "KH giao T"+costMonth);
	
		
		
		
		InputStream inputStream = new FileInputStream (request.getSession().getServletContext().getRealPath("/report/CostDetailTHMain.jrxml")); 
		//JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(null);	

		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
		
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment; filename=\"BaoCaoThucHienChiPhi_"+dateNow+".xls\"");
		JRXlsExporter exporterXLS = new JRXlsExporter();
		//exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
		exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
		exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
	//	exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		exporterXLS.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
		exporterXLS.setParameter(JRXlsExporterParameter.OFFSET_X, 0);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.FALSE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		exporterXLS.exportReport();	
		response.getOutputStream().flush();
		response.getOutputStream().close();
		
		
	}
	
}

