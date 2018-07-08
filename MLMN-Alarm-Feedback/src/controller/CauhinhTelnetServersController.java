package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
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

import vo.CTelnetServers;
import vo.HRouter;
import vo.SYS_PARAMETER;
import vo.alarm.utils.CauhinhTelnetFilter;
import vo.alarm.utils.DateTools;
import dao.CTelnetServersDAO;
import dao.SYS_PARAMETERDAO;

@Controller
@RequestMapping("/cauhinh/telnet/*")
public class CauhinhTelnetServersController extends BaseController{

	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private CTelnetServersDAO cTelnetServersDAO;
	
	private DateFormat df_full = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

	@RequestMapping("list")
	public ModelAndView list(@ModelAttribute("filter") CauhinhTelnetFilter filter,
			Model model, HttpServletRequest request) { 
				
		int order = 0;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		List<CTelnetServers> serverTelnetList = new ArrayList<CTelnetServers>(); 		

		List<SYS_PARAMETER> alarmTypeList = sysParameterDao.getSysParameterByCode("TELNET_ALARM_TYPE");  
		List<SYS_PARAMETER> nodeList = sysParameterDao.getSysParameterByCode("VENDOR"); 
		List<SYS_PARAMETER> moduleList = sysParameterDao.getSysParameterByCode("MODULE"); 
		List<SYS_PARAMETER> typeList = sysParameterDao.getSysParameterByCode("TELNET_TYPE"); 
		
		serverTelnetList = cTelnetServersDAO.getTelnetServerList(filter,order,column);
				
		model.addAttribute("serverTelnet", serverTelnetList);
		model.addAttribute("alarmTypeList", alarmTypeList); 
		model.addAttribute("nodeList", nodeList); 
		model.addAttribute("typeList", typeList); 
		model.addAttribute("moduleList", moduleList);
		model.addAttribute("filter", filter);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "TelnetServer_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);

		return new ModelAndView("jspalarm/cauhinh/telnetServerList");
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) String id,
			HttpServletRequest request, Model model) {
		
			try {
				cTelnetServersDAO.deleteById(Integer.parseInt(id));
				saveMessageKey(request, "messsage.confirm.deletesuccess");
			}
			catch (Exception e) {
				saveMessageKey(request, "messsage.confirm.deletefailure");
			}
			
			return "redirect:list.htm";
		}
	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, ModelMap model, HttpServletRequest request) {
		
		SYS_PARAMETER titleS= sysParameterDao.getValueByCodeName("TITLE_SERVER_TELNET", "CAU_HINH_TELNET_EDIT");
		if (titleS!=null)
			model.addAttribute("titleF", titleS.getValue());
		else
			model.addAttribute("titleF", "TITLE_SERVER_TELNET.CAU_HINH_TELNET_EDIT");
		
		CTelnetServers alarm = (id == null) ? new CTelnetServers() : cTelnetServersDAO.selectById(Integer.parseInt(id));		
		
		List<SYS_PARAMETER> alarmTypeList = sysParameterDao.getSysParameterByCode("TELNET_ALARM_TYPE"); 
		List<SYS_PARAMETER> nodeList = sysParameterDao.getSysParameterByCode("VENDOR");  
		List<SYS_PARAMETER> moduleList = sysParameterDao.getSysParameterByCode("MODULE"); 
		List<SYS_PARAMETER> typeList = sysParameterDao.getSysParameterByCode("TELNET_TYPE"); 
		
		model.addAttribute("cTelnetServer", alarm);
		model.addAttribute("alarmTypeList", alarmTypeList);
		model.addAttribute("nodeList", nodeList);
		model.addAttribute("typeList", typeList);
		model.addAttribute("moduleList", moduleList);
		
		return "jspalarm/cauhinh/telnetServerForm";
	} 
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("cTelnetServer") @Valid CTelnetServers cTelnetServer,
			BindingResult result, ModelMap model, HttpServletRequest request) throws ParseException {
		
		SYS_PARAMETER titleS= sysParameterDao.getValueByCodeName("TITLE_SERVER_TELNET", "CAU_HINH_TELNET_ADD");
		if (titleS!=null)
			model.addAttribute("titleF", titleS.getValue());
		else
			model.addAttribute("titleF", "TITLE_SERVER_TELNET.CAU_HINH_TELNET_ADD");

		List<SYS_PARAMETER> alarmTypeList = sysParameterDao.getSysParameterByCode("TELNET_ALARM_TYPE");
		List<SYS_PARAMETER> nodeList = sysParameterDao.getSysParameterByCode("VENDOR");
		List<SYS_PARAMETER> moduleList = sysParameterDao.getSysParameterByCode("MODULE"); 
		List<SYS_PARAMETER> typeList = sysParameterDao.getSysParameterByCode("TELNET_TYPE");
		 
		 
		
		if (result.hasErrors()) {
			model.addAttribute("moduleList", moduleList);
			saveMessageKey(request, "alarmExtend.errorField");
			return "jspalarm/cauhinh/telnetServerForm";
		}
		else
		{  
			 
			
			if(cTelnetServer != null){
				String ne = cTelnetServer.getNe();
				String vendor = "";
				String network = "";
				String netype = "";
				
				if(ne.substring(ne.length()-1).equals("E")){
					vendor = "ERICSSON";
				}else if(ne.substring(ne.length()-1).equals("N")){
					vendor = "NOKIA SEIMENS";
				}else if(ne.substring(ne.length()-1).equals("H")){
					vendor = "HUAWEI";
				}else if(ne.substring(ne.length()-1).equals("A")){
					vendor = "ALCATEL";
				}
				
				if(ne.substring(0, 1).equals("B")){
					network = "2G";
					netype = "BSC";
				}else if(ne.substring(0, 1).equals("R")){
					network = "3G";
					netype = "RNC";
				}else if(ne.substring(0, 1).equals("SG")){
					network = "PS CORE";
					netype = "SGSN";
				}else if(ne.substring(0, 1).equals("F")){
					network = "PS CORE";
					netype = "GGSN";
				}
				
				if(ne.substring(0,2).equals("MS")){
					network = "CS CORE";
					netype = "MSC";
				}else if(ne.substring(0,2).equals("MG")){
					network = "CS CORE";
					netype = "MGW";
				}else if(ne.substring(0,2).equals("ST")){
					network = "CS CORE";
					netype = "STP";
				}
				
				cTelnetServer.setVendor(vendor);
				cTelnetServer.setNetwork(network);
				cTelnetServer.setNeType(netype);
			} 
			
			if(cTelnetServer.getId() == null){
				cTelnetServersDAO.insertSelectiveById(cTelnetServer);
				saveMessageKey(request, "Thêm mới thành công");
			}else{
				cTelnetServersDAO.updateByIdSelective(cTelnetServer);
				saveMessageKey(request, "Cập nhật thành công");	
			} 
		}
		
		model.addAttribute("alarmTypeList", alarmTypeList); 
		model.addAttribute("nodeList", nodeList); 
		model.addAttribute("typeList", typeList);
		model.addAttribute("moduleList", moduleList);
		
		return "redirect:list.htm";
	} 
}